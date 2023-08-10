# 使用Java环境作为基础镜像
FROM openjdk:8-jdk-alpine

# 维护者信息
LABEL maintainer="mazea@xuanyong.ai"

ARG JAR_FILE=/blubird-app-start/target/bluebird-app.jar
COPY ${JAR_FILE} app.jar

# 默认的命令行参数
ENTRYPOINT ["java","-jar","/app.jar"]
