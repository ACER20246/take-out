package com.sky.Filter;

import com.sky.context.BaseContext;
import com.sky.properties.JwtProperties;
import com.sky.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProperties jwtProperties;

    public JwtAuthenticationFilter(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }
    @Override
    protected boolean shouldNotFilterErrorDispatch() {
        return true;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 跳过OPTIONS预检请求
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        //在HttpserverRequest中获取请求头中的token
        String token = request.getHeader(jwtProperties.getAdminTokenName());
        System.out.println("=== JwtAuthenticationFilter 执行 ===");
        System.out.println("请求路径: " + request.getRequestURI());
        System.out.println("请求方法: " + request.getMethod());
        System.out.println("从请求头获取的 token: " + token);

        if(token != null && !token.isEmpty()){
            try {
                Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(), token);
                Long empId = Long.valueOf(claims.get("empId").toString());
                // The admin JWT identifies an authenticated backend operator.
                // Keep an authority so role checks remain compatible.
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        empId,
                        null,
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println("认证成功，当前用户ID: " + empId);
            } catch (Exception e) {
                // Token解析失败，清空SecurityContext
                e.printStackTrace();
                SecurityContextHolder.clearContext();
            }
        } else {
            System.out.println("请求头中未找到token");
        }

        System.out.println("进入 filterChain 前的认证对象: " + SecurityContextHolder.getContext().getAuthentication());
        filterChain.doFilter(request, response);
        System.out.println("离开 filterChain 后的认证对象: " + SecurityContextHolder.getContext().getAuthentication());
    }
}
