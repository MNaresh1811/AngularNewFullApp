From java:8-jre
WORKDIR /home/ubuntu/Desktop/FSE/Spring
ENV MONGO_INITDB_ROOT_DATABASE: ReminderServiceDB
ENV MONGO_INITDB_ROOT_USERNAME: 
ENV MONGO_INITDB_ROOT_PASSWORD: 
ADD ./target/ReminderService-0.0.1-SNAPSHOT.jar /home/ubuntu/Desktop/FSE/Spring/ReminderService-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar", "ReminderService-0.0.1-SNAPSHOT.jar"]