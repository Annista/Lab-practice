FROM amazoncorretto:26
COPY ./target/classes/org /tmp/org
WORKDIR /tmp
ENTRYPOINT ["java", "org.example.Main"]