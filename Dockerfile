FROM openjdk:21

COPY target/geist_service-0.0.1-SNAPSHOT.jar geist_service-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "geist_service-0.0.1-SNAPSHOT.jar"]

EXPOSE 8081