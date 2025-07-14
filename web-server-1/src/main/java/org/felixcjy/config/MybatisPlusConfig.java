package org.felixcjy.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Mybatis Plus 配置类
 * 动态配置分页器，请务必配置 mybatis-plus.db-type 的值。
 *
 * @author: Felix(蔡济阳)
 * @since : 2025/7/11 09:37
 */
@Configuration
@MapperScan({"org.felixcjy.**.mapper*"})
public class MybatisPlusConfig {
    @Autowired
    private DataSource dataSource;

    @Value("${mybatis-plus.db-type}")
    private String dbType;

    /**
     * 注册 MyBatis-Plus 插件拦截器
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(resolveDbType()));
        return interceptor;
    }

    /** 动态识别数据库类型 */
    private DbType resolveDbType() {
        try (Connection conn = dataSource.getConnection()) {
            String url = conn.getMetaData().getURL().toLowerCase();
            if (url.contains("mysql")) {
                return DbType.MYSQL;
            } else if (url.contains("oracle")) {
                return DbType.ORACLE;
            } else if (DbType.getDbType(dbType) != null) {
                return DbType.getDbType(dbType);
            } else {
                throw new UnsupportedOperationException("不支持的数据库类型: " + url);
            }
        } catch (SQLException e) {
            throw new RuntimeException("无法获取数据库连接，无法识别数据库类型", e);
        }
    }
}