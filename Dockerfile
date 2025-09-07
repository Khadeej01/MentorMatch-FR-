# Stage 1: Build the application
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# Copy pom.xml first for better layer caching
COPY pom.xml .

# Copy source code
COPY src ./src

# Build the application with retry mechanism
RUN mvn clean package -DskipTests -Dmaven.test.skip=true -Dmaven.wagon.http.retryHandler.count=3

# Stage 2: Create the final image
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Create non-root user for security
RUN groupadd -r mentormatch && useradd -r -g mentormatch mentormatch

# Copy the jar file
COPY --from=build /app/target/MentorMatch-0.0.1-SNAPSHOT.jar app.jar

# Change ownership
RUN chown mentormatch:mentormatch app.jar

# Switch to non-root user
USER mentormatch

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
