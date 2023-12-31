FROM amazoncorretto:21-alpine-jdk
LABEL authors="quada"
COPY target/*.jar telegram.jar
ENTRYPOINT ["java", "-jar", "/telegram.jar"]