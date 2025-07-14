package org.felixcjy.security;

import lombok.AllArgsConstructor;
import org.felixcjy.properties.SecurityProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Spring Security 配置动态过滤器
 *
 * @author: Felix(蔡济阳)
 * @since : 2025/7/11 11:10
 */
@Component
@AllArgsConstructor
public class DynamicSecurityFilter extends OncePerRequestFilter {
    private final SecurityProperties securityProperties;
    private final PermissionService permissionService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        // 白名单：和 SecurityConfig 中保持一致，
        // Spring Security 的“路径放行”和自己定义的 DynamicSecurityFilter 不是一个体系，所以再过滤一次。
        List<String> whiteList = new ArrayList<>(securityProperties.getPermitPaths());

        // 路径放行判断
        AntPathMatcher matcher = new AntPathMatcher();
        for (String whitePath : whiteList) {
            if (matcher.match(whitePath, requestURI)) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        // 认证信息校验
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getAuthorities().isEmpty()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "未授权");
            return;
        }

        // 权限校验
        Map<String, List<String>> permissionRoles = permissionService.getRolePermissions();
        boolean matched = false;

        for (Map.Entry<String, List<String>> entry : permissionRoles.entrySet()) {
            if (matcher.match(entry.getKey(), requestURI)) {
                matched = true;
                List<String> requiredRoles = entry.getValue();
                boolean hasAuthority = authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .anyMatch(requiredRoles::contains);
                if (!hasAuthority) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "权限不足！");
                    return;
                }
                break; // 匹配并校验通过
            }
        }

        if (!matched) {
            // 未配置权限的接口默认禁止访问
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "该接口未配置权限，不允许访问！");
            return;
        }

        filterChain.doFilter(request, response);
    }
}

