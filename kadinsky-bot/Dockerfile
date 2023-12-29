FROM amazoncorretto:17-alpine-jdk
LABEL authors="quada"
COPY target/*.jar kandinsky.jar
ENTRYPOINT ["java","-XX:MaxRAM=100M", "-jar", "/kandinsky.jar"]