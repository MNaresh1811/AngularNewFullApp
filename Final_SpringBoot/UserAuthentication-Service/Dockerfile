From java:8-jre
WORKDIR /home/ubuntu/Desktop/FSE/Spring
ENV MYSQL_DATABASE = UserAuthDB
ENV MYSQL_USER = root
ENV MYSQL_PASSWORD = root
ENV MYSQL_CI_URL = jdbc:mysql://localhost:3306/UserAuthDB
ADD ./target/UserAuthentication-Service-0.0.1-SNAPSHOT.jar /home/ubuntu/Desktop/FSE/Spring/UserAuthentication-Service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar", "UserAuthentication-Service-0.0.1-SNAPSHOT.jar"]