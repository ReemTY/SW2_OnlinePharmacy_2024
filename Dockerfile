FROM ubuntu:latest
LABEL authors="hagar"

ENTRYPOINT ["top", "-b"]

# Use the official OpenJDK 11 image as a base
FROM maven:3.8.4-jdk-11 AS build

# Set the working directory in the container
WORKDIR /app/backend

# Copy the Maven build files
COPY backend/pom.xml ./

# Copy the whole project
COPY backend/src ./src

# Build the Maven project
RUN mvn clean package

# Copy the JAR file from the build stage to a new lightweight container
FROM openjdk:11-jre-slim

# Set the working directory in the container
WORKDIR /app/backend

# Copy the JAR file from the previous stage
COPY --from=build /app/backend/target/FinalPharma-*.jar app.jar

# Expose port 8080
EXPOSE 8080

# Specify the command to run the backend application
CMD ["java", "-jar", "app.jar"]
