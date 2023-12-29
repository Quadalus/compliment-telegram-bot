FROM amazoncorretto:17-alpine-jdk
LABEL authors="quada"
COPY target/*.jar telegram.jar
ENTRYPOINT ["java", "-jar", "/telegram.jar"]