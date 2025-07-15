# Use a lightweight OpenJDK base image
FROM openjdk:17-jdk-slim


# Set the working directory
WORKDIR /app

# Copy and build the project (use Maven Wrapper or install maven separately)
COPY target/email-service-0.0.1-SNAPSHOT.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
