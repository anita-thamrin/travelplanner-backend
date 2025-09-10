# BUILD STAGE
# Use Eclipse Temurin 21 JDK image as the build environment
FROM eclipse-temurin:21-jdk AS build

# Set the working directory
WORKDIR /app

# Copy Maven wrapper, .mvn directory, and pom.xml first
# This is to maximize layer caching
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Download dependencies (so they are cached unless pom.xml changes)
# This is useful if your source code changes but the dependencies do not
# Docker can reuse the cached dependencies layer
# Maven will download dependencies into local repo (~/.m2/repository)
# -B flag is used for batch mode, which disables interactive prompts
RUN ./mvnw dependency:go-offline -B

# Copy the rest of the application source code
COPY . .

# Build the app
RUN ./mvnw clean package -DskipTests

# RUN STAGE
# Use the smaller JRE image for the final runtime
FROM eclipse-temurin:21-jre AS run

# Set the working directory
WORKDIR /app

# Copy jar file from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
# CMD ["java", "-jar", "app.jar"]
# Use ENTRYPOINT instead because it allows flexibility in passing arguments
ENTRYPOINT [ "java", "-jar", "app.jar" ]
