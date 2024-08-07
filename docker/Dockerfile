FROM openjdk:17-jre-slim
ARG JAR_FILE=/home/ec2-user/app/coupon-system-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /home/ec2-user/app/coupon-server.jar
ENTRYPOINT ["java", "-jar", "/home/ec2-user/app/coupon-server.jar"]
