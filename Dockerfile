FROM ubuntu

ENV KAFKA_HOME /usr/local/kafka
ADD ./start-kafka.sh /scripts/

# install kafka
RUN wget https://dlcdn.apache.org/kafka/3.3.1/kafka_2.13-3.3.1.tgz && \
  tar -xzf kafka_2.13-3.3.1.tgz && \
  mv kafka_2.13-3.3.1.tgz $KAFKA_HOME

CMD ["/scripts/start-kafka.sh"]

FROM maven:3.8.6-jdk-11 as buildder
WORKDIR /app
COPY . .
RUN mvn clean install

FROM openjdk
WORKDIR /app
EXPOSE 80
COPY --from=buildder /app/target/DentalClinicManagementSystem-0.0.1-SNAPSHOT.jar .
CMD java -jar DentalClinicManagementSystem-0.0.1-SNAPSHOT.jar
