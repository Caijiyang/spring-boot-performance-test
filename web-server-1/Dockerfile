# ============================
# Run Stage
# ============================
# 使用 Eclipse Temurin JDK 8 通用版（full），支持 arm64/amd64
# 注意：不使用 slim/alpine，避免生产环境缺少依赖问题
FROM eclipse-temurin:8-jdk

# 创建非 root 用户（安全）
RUN groupadd -r spring && useradd -r -g spring spring

# 同步容器时间
RUN apt-get update && apt-get install -y ntpdate && ntpdate time.nist.gov

# 设置时区为北京时间（GMT+8）
RUN apt-get update && apt-get install -y tzdata && \
    ln -fs /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    dpkg-reconfigure --frontend noninteractive tzdata

# 从 hengyunabc/arthas:latest 镜像复制 Arthas
COPY --from=hengyunabc/arthas:latest /opt/arthas /opt/arthas

# 设置工作目录为 /app，更符合容器习惯
WORKDIR /app

# 复制 TeamCity 构建好的 jar 文件
# 注意：Docker build 的上下文里必须包含 target/web-server-1-*.jar
COPY target/web-server-1-*.jar app.jar

# 变更 app.jar 文件所有者为 spring 用户
RUN chown spring:spring app.jar

# 赋予 spring 用户执行权限（包括 /opt/arthas 和 /app 目录内的文件）
RUN chmod -R +x /opt/arthas /app

# 切换到 spring 用户
USER spring:spring

# 暴露服务端口
EXPOSE 8080

# 健康检查（如果没有 actuator，可以注释掉）
# HEALTHCHECK --interval=30s --timeout=5s --retries=3 \
#   CMD wget -qO- http://localhost:8080/actuator/health || exit 1

# JVM 内存参数
ENTRYPOINT ["java", "-Xms256m", "-Xmx1024m", "-javaagent:/opt/arthas/arthas-agent.jar", "-jar", "app.jar"]
