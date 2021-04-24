FROM adoptopenjdk/openjdk11:latest

ENV TZ=America/Sao_Paulo
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /opt/app/logs
RUN mkdir -p /opt/app/arquivo


WORKDIR /opt/app

ARG JAR_FILE=target/tubosp-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]

EXPOSE 8080