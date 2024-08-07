FROM openjdk:17-jre-slim
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} coupon-server.jar
ENTRYPOINT ["java", "-jar", "/coupon-server.jar"]
