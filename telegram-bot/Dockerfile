FROM amazoncorretto:17-alpine-jdk
LABEL authors="quada"
COPY target/*.jar telegram.jar
ENTRYPOINT ["java","-XX:MaxRAM=100M", "-jar", "/telegram.jar"]