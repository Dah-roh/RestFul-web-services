FROM amazoncorretto:17
WORKDIR /app
EXPOSE 8080
COPY target/*.jar app1.jar
ENTRYPOINT ["java", "-jar", "app1.jar"]