FROM ewolff/docker-java
COPY target/event-sourcing-kafka-queries-1.0-SNAPSHOT.jar .
CMD /usr/bin/java -Xmx256m -Xms256m -jar event-sourcing-kafka-queries-1.0-SNAPSHOT.jar
EXPOSE 8080