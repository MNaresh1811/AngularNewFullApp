From java:8-jre
WORKDIR /home/ubuntu/Desktop/FSE/Spring
ENV MONGO_INITDB_ROOT_DATABASE: NoteServiceDB
ENV MONGO_INITDB_ROOT_USERNAME: 
ENV MONGO_INITDB_ROOT_PASSWORD: 
ADD ./target/NoteService-0.0.1-SNAPSHOT.jar /home/ubuntu/Desktop/FSE/Spring/NoteService-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar", "NoteService-0.0.1-SNAPSHOT.jar"]