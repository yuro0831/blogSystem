# Build stage
FROM maven:3-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -Dmaven.test.skip=true

# Run stage
FROM eclipse-temurin:21-alpine
WORKDIR /app
COPY --from=build /app/target/blog-management-system-0.0.1-SNAPSHOT.jar demo.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "demo.jar"]