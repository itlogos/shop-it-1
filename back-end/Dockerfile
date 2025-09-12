# syntax=docker/dockerfile:1

FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /build

COPY ./pom.xml ./pom.xml
COPY ./back-end/pom.xml ./back-end/pom.xml
COPY ./back-end/src ./back-end/src

RUN mvn -q -f back-end/pom.xml -DskipTests dependency:go-offline
RUN mvn -q -f back-end/pom.xml -DskipTests package

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /build/back-end/target/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
