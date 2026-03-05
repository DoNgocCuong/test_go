FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# copy folder project vào container
COPY test_golden_owl ./test_golden_owl

# vào đúng thư mục chứa pom.xml
WORKDIR /app/test_golden_owl

RUN mvn clean package -DskipTests


FROM eclipse-temurin:21-jdk
WORKDIR /app

COPY --from=build /app/test_golden_owl/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]