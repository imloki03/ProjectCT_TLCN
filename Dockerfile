# Stage build: Tạo ứng dụng bằng Gradle
FROM ubuntu:latest AS build

# Cập nhật và cài đặt OpenJDK 21 và Gradle
RUN apt-get update && \
    apt-get install -y openjdk-21-jdk wget curl

# Cài đặt Gradle 8.10.2
RUN wget https://services.gradle.org/distributions/gradle-8.10.2-bin.zip -P /tmp && \
    unzip /tmp/gradle-8.10.2-bin.zip -d /opt && \
    ln -s /opt/gradle-8.10.2/bin/gradle /usr/bin/gradle

# Sao chép mã nguồn vào container
COPY . /app

# Thiết lập thư mục làm việc
WORKDIR /app

# Build ứng dụng Spring Boot với Gradle
RUN ./gradlew bootJar --no-daemon

# Stage runtime: Chạy ứng dụng với OpenJDK 21 slim
FROM openjdk:21-jdk-slim

# Expose port mặc định của Spring Boot
EXPOSE 8080

# Sao chép file jar đã build từ container build
COPY --from=build /app/build/libs/*.jar app.jar

# Khởi động ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]
