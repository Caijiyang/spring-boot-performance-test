# ============================
# Build Stage
# ============================
# 改成更通用的基础镜像，arm64/amd64 都支持
FROM maven:3.9.6-eclipse-temurin-8-jammy AS builder
WORKDIR /app

# 复制项目配置和源码
COPY pom.xml .
COPY src ./src

# 使用 Maven 构建
RUN mvn clean package -DskipTests

# ============================
# Run Stage
# ============================
# 原来的 alpine 没了，换成 jammy 版（arm64 也有）
FROM eclipse-temurin:8-jre-jammy

# 创建非 root 用户
RUN addgroup --system spring && adduser --system --ingroup spring spring

WORKDIR /app
COPY --from=builder /app/target/web-server-1-*.jar app.jar

# 变更 app.jar 所有者为 spring 用户
RUN chown spring:spring app.jar
USER spring:spring

EXPOSE 8080

# JVM 内存参数
ENTRYPOINT ["java", "-Xms256m", "-Xmx1024m", "-jar", "app.jar"]
