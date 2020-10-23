FROM gcr.io/distroless/java:11

WORKDIR /app
COPY ./target/lib /app/lib
COPY ./target/plant-server-*-runner.jar /app/plant-server.jar

EXPOSE 8080

CMD ["plant-server.jar"]