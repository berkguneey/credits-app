FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY src ./src

RUN ./mvnw clean package -DskipTests

CMD ["java", "-jar", "target/credits-0.0.1-SNAPSHOT.jar"]
