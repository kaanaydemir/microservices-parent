FROM amazoncorretto:18
VOLUME /tmp
ARG JAR_FILE=target/product-service.jar
COPY ${JAR_FILE} /app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]