# Multi-stage build for Java 21 Maven application

# Stage 1: Build stage
FROM maven:3.9-amazoncorretto-21 AS build

# Set working directory
WORKDIR /app

# Copy pom.xml first for better layer caching
COPY pom.xml .

# Download dependencies (this layer will be cached if pom.xml doesn't change)
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application (skip tests for faster builds, remove -DskipTests to run tests)
RUN mvn clean package -DskipTests

# Stage 2: Runtime stage
FROM amazoncorretto:21-alpine

# Set working directory
WORKDIR /app

# Copy the built JAR from build stage
COPY --from=build /app/target/jashsports-1.0-SNAPSHOT.jar app.jar

# Copy .env file if it exists in your project root
COPY .env .env

# Expose port 7000
EXPOSE 7000

# Set JVM options (optional, adjust memory as needed)
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]