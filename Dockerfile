FROM amazoncorretto:17

WORKDIR /app

COPY build/libs/basket-0.0.1-SNAPSHOT.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "basket-0.0.1-SNAPSHOT.jar"]