# # Gradle 빌드를 위한 베이스 이미지
# FROM openjdk:17-jdk-slim

# WORKDIR /app
# COPY . /app

# RUN gradle build -x test

# # 실행을 위한 새로운 베이스 이미지
# FROM openjdk:17-jdk-slim

# WORKDIR /app
# COPY --from=builder /app/build/libs/gratitude-server-0.0.1.jar app.jar

# EXPOSE 8080

# # 환경 변수 출력 후 애플리케이션 실행
# CMD ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]

FROM openjdk:17-jdk-slim

WORKDIR /app

ARG JAR_FILE=./build/libs

COPY ${JAR_FILE}/*.jar app.jar

ENTRYPOINT ["java", "-jar","./app.jar"]