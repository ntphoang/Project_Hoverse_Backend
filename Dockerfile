FROM eclipse-temurin:23-jdk

WORKDIR /app

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B

COPY src ./src

RUN ./mvnw clean package -DskipTests

EXPOSE 8088

CMD ["java", "-jar", "target/backend-0.0.1-SNAPSHOT.jar"]