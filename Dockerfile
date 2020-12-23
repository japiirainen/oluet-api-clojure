FROM openjdk:8-alpine

COPY target/uberjar/oluet-api.jar /oluet-api/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/oluet-api/app.jar"]
