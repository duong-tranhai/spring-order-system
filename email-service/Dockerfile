# Use an official OpenJDK 21 runtime as a parent image
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# The JAR file will be named based on the artifactId and version in the pom.xml
# Copy the executable JAR file from the target directory to the container
COPY target/email-service-0.0.1-SNAPSHOT.jar app.jar

# Make port 8082 available to the world outside this container
EXPOSE 8081

# Run the JAR file when the container launches
ENTRYPOINT ["java", "-jar", "app.jar"]
