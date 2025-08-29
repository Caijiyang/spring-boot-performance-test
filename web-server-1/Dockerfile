# ============================
# Build Stage
# ============================
FROM maven:3.8.7-openjdk-8 AS builder
WORKDIR /app

# 复制项目配置和源码
COPY pom.xml .
COPY src ./src

# 如果项目有 mvnw，推荐用下面这行（项目自带 Maven Wrapper，更稳定）
# COPY mvnw .
# COPY .mvn .mvn
# RUN chmod +x mvnw && ./mvnw clean package -DskipTests

# 当前没有 mvnw，用系统 Maven 构建
RUN mvn clean package -DskipTests

# ============================
# Run Stage
# ============================
FROM openjdk:8-jdk-alpine

# 创建非 root 用户（安全）
# Alpine 或 Debian 用这个
#RUN addgroup --system spring && adduser --system spring --ingroup spring
# Rocky 用这个
RUN groupadd -r spring && useradd -r -g spring spring

WORKDIR /app
COPY --from=builder /app/target/web-server-1-*.jar app.jar

# 变更 app.jar 文件所有者为 spring 用户
RUN chown spring:spring app.jar

# 切换到 spring 用户
USER spring:spring

EXPOSE 8080

# 健康检查（如果没有 actuator，可以注释掉）
# HEALTHCHECK --interval=30s --timeout=5s --retries=3 \
#   CMD wget -qO- http://localhost:8080/actuator/health || exit 1

# JVM 内存参数（适合 2核4G 的测试机）
ENTRYPOINT ["java", "-Xms256m", "-Xmx1024m", "-jar", "app.jar"]
