FROM bellsoft/liberica-runtime-container:jre-17-slim-glibc
EXPOSE 8080:8080
RUN mkdir /app
COPY server/build/libs/dev.junker.server.jar /app/dev.junker.server.jar
ENTRYPOINT ["java", "-jar", "/app/dev.junker.server.jar"]
