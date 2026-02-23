FROM eclipse-temurin:17-jdk-alpine
ARG JAR_FILE=target/customer-service-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENV SERVER_PORT=8081
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app.jar"]
