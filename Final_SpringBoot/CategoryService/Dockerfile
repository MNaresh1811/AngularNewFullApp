From java:8-jre
WORKDIR /home/ubuntu/Desktop/FSE/Spring
ENV MONGO_INITDB_ROOT_DATABASE: CategoryServiceDB
ENV MONGO_INITDB_ROOT_USERNAME: 
ENV MONGO_INITDB_ROOT_PASSWORD: 
ADD ./target/CategoryService-0.0.1-SNAPSHOT.jar /home/ubuntu/Desktop/FSE/Spring/CategoryService-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar", "CategoryService-0.0.1-SNAPSHOT.jar"]