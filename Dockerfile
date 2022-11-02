FROM openjdk:11
ADD target/spring-boot-aws-exe.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
