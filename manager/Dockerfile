# syntax=docker/dockerfile:1

FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /build

# Родительский pom (если есть) и модуль manager
COPY ./pom.xml ./pom.xml
COPY ./manager/pom.xml ./manager/pom.xml
COPY ./manager/src ./manager/src

# Скачиваем зависимости и собираем
RUN mvn -q -f manager/pom.xml -DskipTests dependency:go-offline
RUN mvn -q -f manager/pom.xml -DskipTests package

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /build/manager/target/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
