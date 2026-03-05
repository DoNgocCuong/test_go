FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copy toàn bộ project (vì build context đã là test_golden_owl)
COPY . .

RUN mvn clean package -DskipTests


FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]