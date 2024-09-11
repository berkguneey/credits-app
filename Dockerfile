FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY --from=build /app/target/credits-0.0.1-SNAPSHOT.jar /app/credits.jar

CMD ["java", "-jar", "credits.jar"]