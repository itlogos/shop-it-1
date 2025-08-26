# ---------- Build stage ----------
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy Maven descriptor files first to leverage Docker layer caching
COPY pom.xml ./
COPY back-end/pom.xml back-end/pom.xml
COPY manager/pom.xml manager/pom.xml

# Pre-fetch dependencies
RUN mvn -q -DskipTests dependency:go-offline

# Copy sources (only the module we build + any shared code if added later)
COPY back-end/src back-end/src

# Build only the back-end module (and any required modules via -am)
RUN mvn -q -pl back-end -am -DskipTests package

# ---------- Runtime stage ----------
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the built fat JAR
COPY --from=build /app/back-end/target/back-*.jar app.jar

# Default profile loads application-standalone.yaml in this project
ENV SPRING_PROFILES_ACTIVE=standalone

# Let Spring Boot know the port (can be overridden)
ENV SERVER_PORT=8081

# Expose the HTTP port
EXPOSE 8081

# JVM opts can be injected via JAVA_OPTS if needed
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
