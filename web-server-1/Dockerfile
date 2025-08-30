# ============================
# Run Stage
# ============================
# 使用 Eclipse Temurin JDK 8 通用版（full），支持 arm64/amd64
# 注意：不使用 slim/alpine，避免生产环境缺少依赖问题
FROM eclipse-temurin:8-jdk

# 创建非 root 用户（安全）
# Alpine 或 Debian 系统可以使用：
# RUN addgroup --system spring && adduser --system --ingroup spring spring
# Rocky Linux 用下面这个命令（通用且兼容）
RUN groupadd -r spring && useradd -r -g spring spring

# 从 hengyunabc/arthas:4.0.5-no-jdk 镜像复制 Arthas
COPY --from=hengyunabc/arthas:4.0.5-no-jdk /opt/arthas /opt/arthas

# 设置工作目录为 /app，更符合容器习惯
WORKDIR /app

# 复制 TeamCity 构建好的 jar 文件
# 注意：Docker build 的上下文里必须包含 target/web-server-1-0.1.jar
COPY target/web-server-1-*.jar app.jar
# 注释原因：不再使用 builder 阶段，直接使用 TeamCity 已构建 jar

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
ENTRYPOINT ["java", "-Xms256m", "-Xmx1024m", "-javaagent:/opt/arthas/arthas-boot.jar", "-jar", "app.jar"]
