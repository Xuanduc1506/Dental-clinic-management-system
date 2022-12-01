FROM maven:3.8.6-jdk-11 as buildder
WORKDIR /app
COPY . .
RUN mvn clean install

FROM openjdk
WORKDIR /app
EXPOSE 8080
COPY --from=buildder /app/target/DentalClinicManagementSystem-0.0.1-SNAPSHOT.jar .
CMD java -jar DentalClinicManagementSystem-0.0.1-SNAPSHOT.jar
