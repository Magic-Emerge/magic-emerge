# 使用 Maven 官方镜像作为构建环境
FROM maven:3.8.5-openjdk-8 AS build

# 设置工作目录
WORKDIR /app
# 复制整个项目文件
COPY . /app

# 使用 Maven 进行打包（跳过测试以加速）
RUN mvn clean package -DskipTests

# 使用轻量级的 OpenJDK 8 运行时环境
FROM openjdk:8-jdk-alpine

# 维护者信息
LABEL maintainer="zean@magicemerge.ai"

# 复制从构建阶段打包好的 JAR 文件到运行时镜像
ARG JAR_FILE=/app/bluebird-app-start/target/bluebird-app.jar
COPY --from=build ${JAR_FILE} /app.jar

# 暴露应用所使用的端口
EXPOSE 5000

# 设置应用的启动命令
ENTRYPOINT ["java", "-jar", "/app.jar"]