# ============================
# Build Stage
# ============================
# 使用官方 Maven 镜像，基于 Eclipse Temurin 8，支持 arm64/amd64
# 由于 TeamCity 已经构建好了 jar，这里不需要再构建
FROM maven:3.9.6-eclipse-temurin-8 AS builder
WORKDIR /app

# 复制项目配置和源码
COPY pom.xml .
COPY src ./src

# 如果项目有 mvnw，推荐用下面这行（项目自带 Maven Wrapper，更稳定）
# COPY mvnw .
# COPY .mvn .mvn
# RUN chmod +x mvnw && ./mvnw clean package -DskipTests

# 使用 Maven 构建（已注释，因为 TeamCity 已经构建 jar）
# RUN mvn clean package -DskipTests
# 注释原因：TeamCity 已经执行过 mvn package，这里无需重复构建，节省时间和资源

# ============================
# Run Stage
# ============================
# 使用 Eclipse Temurin JRE 8 通用版（full），支持 arm64/amd64
# 注意：不使用 slim/alpine，避免生产环境缺少依赖问题
FROM eclipse-temurin:8-jre

# 创建非 root 用户（安全）
# Alpine 或 Debian 系统可以使用：
# RUN addgroup --system spring && adduser --system --ingroup spring spring
# Rocky Linux 用下面这个命令（通用且兼容）
RUN groupadd -r spring && useradd -r -g spring spring

WORKDIR /web-server-1

# 复制构建好的 jar 文件
# 这里不复制 Maven 缓存和源码，减小最终镜像体积
# 注意：确保 TeamCity 构建的 jar 在 target 目录下
COPY --from=builder /web-server-1/target/web-server-1-*.jar app.jar

# 变更 app.jar 文件所有者为 spring 用户
RUN chown spring:spring app.jar

# 切换到 spring 用户
USER spring:spring

# 暴露服务端口
EXPOSE 8080

# 健康检查（如果没有 actuator，可以注释掉）
# HEALTHCHECK --interval=30s --timeout=5s --retries=3 \
#   CMD wget -qO- http://localhost:8080/actuator/health || exit 1

# JVM 内存参数
ENTRYPOINT ["java", "-Xms256m", "-Xmx1024m", "-jar", "app.jar"]
