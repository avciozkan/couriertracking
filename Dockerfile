FROM openjdk:17-jdk-slim as build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/target/couriertracking-0.0.1-SNAPSHOT.jar /app/couriertracking.jar

ENTRYPOINT ["java", "-jar", "/app/couriertracking.jar"]

EXPOSE 8080