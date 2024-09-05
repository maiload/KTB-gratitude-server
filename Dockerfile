# Gradle 빌드를 위한 베이스 이미지
FROM gradle:7.5-jdk17 AS builder

WORKDIR /app
COPY . /app

RUN gradle build -x test

# 실행을 위한 새로운 베이스 이미지
FROM openjdk:17-jdk-slim

WORKDIR /app
COPY --from=builder /app/build/libs/gratitude-server-0.0.1.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
