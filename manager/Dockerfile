# ---------- Build stage ----------
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy Maven descriptor files first to leverage Docker layer caching
COPY pom.xml ./
COPY manager/pom.xml manager/pom.xml
COPY back-end/pom.xml back-end/pom.xml

# Pre-fetch dependencies
RUN mvn -q -DskipTests dependency:go-offline

# Copy sources
COPY manager/src manager/src

# Build only the manager module
RUN mvn -q -pl manager -am -DskipTests package

# ---------- Runtime stage ----------
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the built fat JAR
COPY --from=build /app/manager/target/manager-*.jar app.jar

# Default profile loads application-standalone.yaml in this project
ENV SPRING_PROFILES_ACTIVE=standalone

# Let Spring Boot know the port (can be overridden)
ENV SERVER_PORT=8080

# Expose the HTTP port
EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
