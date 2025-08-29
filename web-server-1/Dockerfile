# ============================
# Build Stage
# ============================
FROM maven:3.9.6-eclipse-temurin-8 AS builder
WORKDIR /app

# 复制项目配置和源码
COPY pom.xml .
COPY src ./src

# 如果项目有 mvnw，优先使用 Maven Wrapper（更稳定）
# COPY mvnw .
# COPY .mvn .mvn
# RUN chmod +x mvnw && ./mvnw clean package -DskipTests

# 使用 Maven 构建
RUN mvn clean package -DskipTests

# ============================
# Run Stage
# ============================
FROM eclipse-temurin:8-jre-alpine

# 创建非 root 用户（Alpine）
RUN addgroup -S spring && adduser -S spring -G spring

WORKDIR /app
COPY --from=builder /app/target/web-server-1-*.jar app.jar

# 变更 app.jar 所有者为 spring 用户
RUN chown spring:spring app.jar
USER spring:spring

EXPOSE 8080

# 健康检查（可选）
# HEALTHCHECK --interval=30s --timeout=5s --retries=3 \
#   CMD wget -qO- http://localhost:8080/actuator/health || exit 1

# JVM 内存参数
ENTRYPOINT ["java", "-Xms256m", "-Xmx1024m", "-jar", "app.jar"]
