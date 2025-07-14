package org.felixcjy.security;

import lombok.AllArgsConstructor;
import org.felixcjy.properties.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @author: Felix(蔡济阳)
 * @since : 2025/7/11 11:09
 */
@Configuration
@AllArgsConstructor
public class SecurityConfig {
    // 构造注入，Spring 会自动将 CustomUserDetailsServiceImpl 注入到这里
    private final UserDetailsService userDetailsService;
    private final DynamicSecurityFilter dynamicSecurityFilter;
    private final SecurityProperties securityProperties;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failureHandler;
    private final AccessDeniedHandler accessDeniedHandler;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final LogoutSuccessHandler logoutSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(auth -> {
                    for (String path : securityProperties.getPermitPaths()) {
                        auth.antMatchers(path).permitAll();
                    }
                    auth.anyRequest().authenticated();
                })

                //  默认的登录方式，避免采用
                // .formLogin(withDefaults())

                .formLogin(form -> form
                        .loginProcessingUrl("/login")
                        .successHandler(successHandler)
                        .failureHandler(failureHandler)
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler(logoutSuccessHandler)
                        .invalidateHttpSession(true)         // 清除 Session
                        .deleteCookies("JSESSIONID")         // 删除 Cookie
                        .permitAll()
                )

                // 使用 Basic 权限认证，一般不推荐使用。
                // .httpBasic(withDefaults())

                .httpBasic().disable()

                // 关闭 CSRF 保护（不推荐，仅用于测试或特定场景）
                // .csrf().disable();

                // 禁用特定路径的 CSRF 保护
                // .csrf(csrf -> csrf.ignoringAntMatchers("/test/minio/**"))

                /* 使用 cookie 存放CSRF标识，CookieCsrfTokenRepository 对于纯前后端分离可能仍不够用，如果是
                 * 如果是完全前后端分离 + Token 登录(如 JWT)，则禁用 csrf，并确保所有跨站攻击由 Token 管理和 Origin 校验来控制。*/
                // .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())

                .csrf(csrf -> csrf
                        .ignoringAntMatchers("/login", "/logout", "/captcha") // 仅放行部分接口
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                )

                // 异常处理
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler(accessDeniedHandler)
                        .authenticationEntryPoint(authenticationEntryPoint)
                )

                // 最后加上动态权限过滤器，位置在权限控制器前面
                .addFilterBefore(dynamicSecurityFilter, FilterSecurityInterceptor.class)
        ;
        return http.build();
    }

    /** 负责校验用户名和密码 */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    /**
     * Spring Boot 2.7 中，如果需要自己使用 AuthenticationManager 来做手动认证
     * （例如 REST 登录接口里 authenticationManager.authenticate(...)），则必须显式暴露这个 Bean。
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(authenticationProvider())
                .build();
    }

    /*
    // 简单配置版本
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(auth -> auth
                        .antMatchers("/public").permitAll() // 允许所有人访问
                        .antMatchers("/admin").hasRole("ADMIN") // 只有 ADMIN 角色可以访问
                        .antMatchers("/redis/**").hasRole("ADMIN") // 只有 ADMIN 角色可以访问
                        .antMatchers("/common").hasAnyRole("COMMON", "ADMIN") // COMMON 和 ADMIN 都可以访问
                        .anyRequest().authenticated() // 其他请求必须登录
                )
                .formLogin(withDefaults())  // 启用表单登录
                // .formLogin(form -> form.loginPage("/my-login").permitAll())  // 指定自定义登录页面
                .httpBasic(withDefaults()); // 启用 Basic 认证
        return http.build();
    } */

    /* 若是很复杂的角色配置，可采用此方法，一般来说，Spring Security 会自动配置一个。
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(myUserDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder()) // 密码加密
                .and()
                .build();
    } */

    /* @Bean
    public UserDetailsService userDetailsService() {
        // 创建一个内存中的用户，用户名是 user，密码是 password, 在内存中创建用户的行为，适合第一次使用 Spring Security 时测试。
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build());
        return manager;
    } */
}

/** 登录成功处理器 */
@Component
class JsonLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"code\":200,\"msg\":\"登录成功\"}");
    }
}

/** 登录失败处理器 */
@Component
class JsonLoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("{\"code\":401,\"msg\":\"登录失败：" + exception.getMessage() + "\"}");
    }
}

/** 未登录（401） */
@Component
class JsonAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("{\"code\":401,\"msg\":\"请先登录\"}");
    }
}

/** 权限不足（403） */
@Component
class JsonAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write("{\"code\":403,\"msg\":\"权限不足\"}");
    }
}

/** 登出处理器 */
@Component
class JsonLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"code\":200,\"msg\":\"退出登录成功\"}");
    }
}