FROM gradle:6.7.1-jdk8 as builder
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:8-jre-alpine

ENV APPLICATION_USER ktor
RUN adduser -D -g '' $APPLICATION_USER

RUN mkdir /app
RUN chown -R $APPLICATION_USER /app

USER $APPLICATION_USER

COPY ./build/libs/company-0.0.1.jar /app/company-0.0.1.jar
WORKDIR /app

CMD ["java", "-server", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-XX:InitialRAMFraction=2", "-XX:MinRAMFraction=2", "-XX:MaxRAMFraction=2", "-XX:+UseG1GC", "-XX:MaxGCPauseMillis=100", "-XX:+UseStringDeduplication", "-jar", "company-0.0.1.jar"]