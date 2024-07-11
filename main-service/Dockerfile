#Use a base image with Java 17
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven build artifact (jar file) to the working directory
COPY target/pos-system-0.0.1-SNAPSHOT.jar pos-system.jar

# Expose the port the application runs on
EXPOSE 8081

# Copy the application's jar to the container
ARG JAR_FILE=target/pos-system-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} pos-system.jar


# Run the jar file
ENTRYPOINT ["java", "-jar", "pos-system.jar"]

#TODO Database connection