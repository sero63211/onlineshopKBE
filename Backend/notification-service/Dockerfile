FROM openjdk:8
ADD target/notification-service-0.0.1-SNAPSHOT.jar notification-service-0.0.1-SNAPSHOT.jar
EXPOSE 1100-65535
ENTRYPOINT ["java" ,"-jar", "notification-service-0.0.1-SNAPSHOT.jar"]