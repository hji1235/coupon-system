FROM openjdk:17-jdk-slim
ARG JAR_FILE=coupon-system-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} coupon-server.jar
ENTRYPOINT ["java", "-jar", "/coupon-server.jar"]
