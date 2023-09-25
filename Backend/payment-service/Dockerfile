FROM openjdk:8
ADD target/payment-service-0.0.1-SNAPSHOT.jar payment-service-0.0.1-SNAPSHOT.jar
EXPOSE 1100-65535
ENTRYPOINT ["java" ,"-jar", "payment-service-0.0.1-SNAPSHOT.jar"]