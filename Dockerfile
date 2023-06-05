FROM maven:3.8.1-openjdk-17 as build
RUN mkdir /home/maven
RUN mkdir /home/maven/src
COPY  . /home/maven/src
WORKDIR /home/maven/src
RUN mvn clean package


FROM openjdk:17.0.1-jdk-slim

LABEL maintainer="GuBee"
LABEL version="1.0"

ENV DB_NAME="postgres"
ENV DB_HOSTNAME="localhost"
ENV DB_PORT="5432"
ENV DB_USERNAME="gubee"
ENV DB_PWD="gubee"

EXPOSE 8080


#COPY maven/*.jar interview.jar

COPY --from=build /home/maven/src/core/target/*.jar interview.jar

ENTRYPOINT [ "java" , "-Xms128m",  "-Xmx400m", "-jar", "-DLog4jContextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector", "interview.jar", "--jdbc.url=jdbc:postgresql://${DB_HOSTNAME}:${DB_PORT}/${DB_NAME}", "--jdbc.password=${DB_PWD}", "--jdbc.username=${DB_USERNAME}"]