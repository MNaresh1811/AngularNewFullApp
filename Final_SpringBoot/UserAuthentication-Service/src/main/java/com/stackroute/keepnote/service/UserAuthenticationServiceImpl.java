package com.stackroute.keepnote.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.stackroute.keepnote.exception.UserAlreadyExistsException;
import com.stackroute.keepnote.exception.UserNotFoundException;
import com.stackroute.keepnote.model.User;
import com.stackroute.keepnote.repository.UserAutheticationRepository;

/*
* Service classes are used here to implement additional business logic/validation 
* This class has to be annotated with @Service annotation.
* @Service - It is a specialization of the component annotation. It doesn't currently 
* provide any additional behavior over the @Component annotation, but it's a good idea 
* to use @Service over @Component in service-layer classes because it specifies intent 
* better. Additionally, tool support and additional behavior might rely on it in the 
* future.
* */


@Service
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

    /*
	 * Autowiring should be implemented for the UserAuthenticationRepository. (Use
	 * Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */


     private UserAutheticationRepository userAutheticationRepository;
     
     public UserAuthenticationServiceImpl(UserAutheticationRepository userAutheticationRepository )
     {
    	 this.userAutheticationRepository = userAutheticationRepository;
     }


     /*
	 * This method should be used to validate a user using userId and password.
	 *  Call the corresponding method of Respository interface.
	 * 
	 */
    @Override
    public User findByUserIdAndPassword(String userId, String password) throws UserNotFoundException {

    	return userAutheticationRepository.findByUserIdAndUserPassword(userId, password);
    }




	/*
	 * This method should be used to save a new user.Call the corresponding method
	 * of Respository interface.
	 */

    @Override
    public boolean saveUser(User user) throws UserAlreadyExistsException {
       
    	try
    	{
    		if(findByUserIdAndPassword(user.getUserId(),user.getUserPassword())== null) {
	    		User userAdded = userAutheticationRepository.save(user);
		    	if(userAdded!=null)
		    	{
		    		return  true;
		    	}
    		}else
    		{
    			throw new UserAlreadyExistsException("User Already Exists");
    		}
    	}
    	catch(Exception exception)
    	{
    		throw new UserAlreadyExistsException("Cannot Register User");
    	}
    	throw new UserAlreadyExistsException("Cannot Register User");
    }
}
