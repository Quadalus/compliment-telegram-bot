FROM amazoncorretto:21-alpine-jdk
LABEL authors="quada"
COPY target/*.jar kandinsky.jar
ENTRYPOINT ["java", "-jar", "/kandinsky.jar"]