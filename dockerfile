# For Java 8, try this
FROM openjdk:8-jdk-alpine

# For Java 11, try this
#FROM adoptopenjdk/openjdk11:alpine-jre

VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} forum.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/forum.jar"]