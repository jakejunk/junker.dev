FROM gradle:8.12.1-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle buildFatJar --no-daemon

FROM openjdk:17
EXPOSE 8080:8080
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/dev.junker.server.jar /app/dev.junker.server.jar
ENTRYPOINT ["java", "-jar", "/app/dev.junker.server.jar"]
