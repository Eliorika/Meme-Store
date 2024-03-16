FROM maven:3.9.6-amazoncorretto-21 AS BUILDER

WORKDIR /opt/app

COPY . .

RUN mvn clean package -DskipTests

FROM openjdk:21
WORKDIR /opt/app
COPY --from=builder /opt/app/target/*.jar ./app.jar
CMD [ "java", "-jar", "/opt/app/app.jar" ]