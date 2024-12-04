FROM openjdk:21-jdk-slim as build

WORKDIR /app

COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
COPY src ./src

RUN chmod +x mvnw

RUN ./mvnw clean package -DskipTests

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=build /app/target/couriertracking-0.0.1-SNAPSHOT.jar /app/couriertracking.jar

ENTRYPOINT ["java", "-jar", "/app/couriertracking.jar", "-web -webAllowOthers -tcp -tcpAllowOthers -browser"]

EXPOSE 8080
