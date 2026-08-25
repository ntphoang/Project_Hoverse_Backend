FROM eclipse-temurin:23-jre

WORKDIR /app

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B

COPY src ./src

RUN ./mvnw clean package -DskipTests

EXPOSE 10000

CMD ["java", "-Xms128m", "-Xmx384m", "-jar", "target/backend-0.0.1-SNAPSHOT.jar"]