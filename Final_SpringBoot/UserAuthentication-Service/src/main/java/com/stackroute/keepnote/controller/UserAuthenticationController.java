package com.stackroute.keepnote.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.stackroute.keepnote.model.User;
import com.stackroute.keepnote.service.UserAuthenticationService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/*
 * As in this assignment, we are working on creating RESTful web service, hence annotate
 * the class with @RestController annotation. A class annotated with the @Controller annotation
 * has handler methods which return a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 */
//@CrossOrigin(origins="http://localhost:4200/", allowedHeaders="*")
@RestController
public class UserAuthenticationController {

    /*
	 * Autowiring should be implemented for the UserAuthenticationService. (Use Constructor-based
	 * autowiring) Please note that we should not create an object using the new
	 * keyword
	 */
	long EXPIRATION_TIME = 9000000000L;
	
	UserAuthenticationService authicationService;
	
    public UserAuthenticationController(UserAuthenticationService authicationService) {
    	this.authicationService =authicationService;
	}

/*
	 * Define a handler method which will create a specific user by reading the
	 * Serialized object from request body and save the user details in the
	 * database. This handler method should return any one of the status messages
	 * basis on different situations:
	 * 1. 201(CREATED) - If the user created successfully. 
	 * 2. 409(CONFLICT) - If the userId conflicts with any existing user
	 * 
	 * This handler method should map to the URL "/api/v1/auth/register" using HTTP POST method
	 */
    @PostMapping("/api/v1/auth/register")
	public ResponseEntity<?> createUser(@RequestBody User user) {
		Map<String, String> map = new HashMap<>();
		try {
			user.setUserAddedDate(new Date());
			if(authicationService.saveUser(user))
			{
				map.put("status", "Success");
				return new ResponseEntity<>(map, HttpStatus.CREATED);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", "Failure");
			map.put("message", e.getMessage());
			return new ResponseEntity<>(map, HttpStatus.CONFLICT);
			
		}
		map.put("status", "Failure");
		return new ResponseEntity<>(map, HttpStatus.CONFLICT);
	}



	/* Define a handler method which will authenticate a user by reading the Serialized user
	 * object from request body containing the username and password. The username and password should be validated 
	 * before proceeding ahead with JWT token generation. The user credentials will be validated against the database entries. 
	 * The error should be return if validation is not successful. If credentials are validated successfully, then JWT
	 * token will be generated. The token should be returned back to the caller along with the API response.
	 * This handler method should return any one of the status messages basis on different
	 * situations:
	 * 1. 200(OK) - If login is successful
	 * 2. 401(UNAUTHORIZED) - If login is not successful
	 * 
	 * This handler method should map to the URL "/api/v1/auth/login" using HTTP POST method
	*/

    @PostMapping("/api/v1/auth/login")
   	public ResponseEntity<?> validateUser(@RequestBody User user) {
   		Map<String, String> map = new HashMap<>();
   		try {
   			user.setUserAddedDate(new Date());
   			if(authicationService.findByUserIdAndPassword(user.getUserId(), user.getUserPassword())!=null)
   			{
   				String token = getToken(user.getUserId(), user.getUserPassword());
   				map.clear();
   				map.put("message", "user succesfully loggedin.");
   				map.put("userId", user.getUserId());
   				map.put("token", token);
   				map.put("status","Success");
   				return new ResponseEntity<>(map, HttpStatus.OK);
   			}
   		} catch (Exception e) {
   			e.printStackTrace();
   			map.put("message", "User Name or Password is incorrect. Please try again");
   			map.put("status", "Failure");
   			return new ResponseEntity<>(map, HttpStatus.CONFLICT);
   		}
   		map.put("status", "Failure");
   		map.put("message", "User Name or Password is incorrect. Please try again");
   		return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
   	}

// Generate JWT token
	private String getToken(String username, String password) throws Exception {
      //Builds the JWT and serializes it to a compact, URL-safe string
	  return Jwts.builder().setSubject(username).
			  					setIssuedAt(new Date()).
			  						setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
			  							.signWith(SignatureAlgorithm.HS256, "secretkey").compact();
}

}