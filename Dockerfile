# # Gradle 빌드를 위한 베이스 이미지
# FROM gradle:7.5-jdk17 AS builder

# WORKDIR /app
# COPY . /app

# # Gradle 빌드를 실행하여 JAR 파일 생성
# RUN gradle build -x test

# # 실행을 위한 새로운 베이스 이미지
# FROM openjdk:17-jdk-slim

# WORKDIR /app

# # 빌드 결과물인 JAR 파일을 복사
# COPY --from=builder /app/build/libs/gratitude-server-0.0.1.jar app.jar

# # 포트 8080을 엽니다.
# EXPOSE 8080

# # 애플리케이션 실행
# CMD ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]

FROM openjdk:17-jdk-alpine

WORKDIR /app

# JAR 파일 경로 설정
ARG JAR_FILE=./build/libs

# JAR 파일을 컨테이너 내로 복사
COPY ${JAR_FILE}/*.jar app.jar

# 디버그 모드로 Spring Boot 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "./app.jar", "--spring.profiles.active=prod", "--debug"]
