FROM amazoncorretto:21-alpine-jdk
LABEL authors="quada"
COPY target/*.jar parser.jar
ENTRYPOINT ["java", "-jar", "/parser.jar"]