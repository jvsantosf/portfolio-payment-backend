FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .

RUN apt-get install maven -y
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim

COPY --from=build /target/paymentport-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]