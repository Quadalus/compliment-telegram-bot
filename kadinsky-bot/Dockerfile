FROM amazoncorretto:17-alpine-jdk
LABEL authors="quada"
COPY target/*.jar kandinsky.jar
ENTRYPOINT ["java", "-jar", "/kandinsky.jar"]