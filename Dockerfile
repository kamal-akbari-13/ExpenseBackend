# ----------- Step 1: Build Stage using Maven + Java 17 -----------
FROM maven:3.9.4-eclipse-temurin-17 AS builder

# Set working directory
WORKDIR /app

# Copy pom.xml and resolve dependencies early for layer caching
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the rest of the source code
COPY src ./src

# Package the application (skip tests for faster build)
RUN mvn clean package -DskipTests


# ----------- Step 2: Run Stage using JRE-only image -----------
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy jar from build stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the port your Spring Boot app runs on (change if different)
EXPOSE 8080

# Command to run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
