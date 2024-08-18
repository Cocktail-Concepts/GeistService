FROM openjdk:21

ADD target/geist_service-0.0.1-SNAPSHOT.jar geist_service-0.0.1-SNAPSHOT

ENTRYPOINT [ "java","-jar","geist_service-0.0.1-SNAPSHOT" ]

EXPOSE 8081