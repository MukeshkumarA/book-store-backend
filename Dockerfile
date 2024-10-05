# define base docker image
FROM openjdk:22
VOLUME /app/logs
EXPOSE 8080
ARG JAR_FILE=target/book-store-backend-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
