# Use the official Gradle image to create a build artifact.
FROM gradle:7.5.1-jdk17 as builder
WORKDIR /app
COPY build.gradle settings.gradle ./
COPY src ./src
RUN gradle clean build -x test

# Use OpenJDK for running the application.
FROM openjdk:17-slim
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar order-it-spring.jar
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "mkdir -p /home/order-it/img/ && java -jar order-it-spring.jar --spring.profiles.active=${SPRING_PROFILES_ACTIVE}"]
