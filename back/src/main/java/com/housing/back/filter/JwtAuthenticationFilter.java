package com.housing.back.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.housing.back.entity.UserEntity;
import com.housing.back.provider.JwtProvider;
import com.housing.back.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // String requestURI = request.getRequestURI();
            // if (requestURI.startsWith("/api/v1/auth/")) {
            // System.out.println("씨발");
            // filterChain.doFilter(request, response);
            // return;
            // }
            String token = parseBearerToken(request);
            if (token == null) {
                filterChain.doFilter(request, response);
                return;
            }
            String userId = jwtProvider.validate(token);
            if (userId == null) {
                filterChain.doFilter(request, response);
                return;
            }

            UserEntity userEntity = userRepository.findByUserId(userId);
            String role = userEntity.getRole(); // ROLE_USER, ROLE_ADMIN

            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(role));

            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            AbstractAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, null,
                    authorities);
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            securityContext.setAuthentication(authenticationToken);
            SecurityContextHolder.setContext(securityContext); // 7강
        } catch (Exception e) {
            e.printStackTrace();
        }
        filterChain.doFilter(request, response);
    }

    private String parseBearerToken(HttpServletRequest req) {
        String authorization = req.getHeader("Authorization");
        boolean hasAuthorization = StringUtils.hasText(authorization);
        if (!hasAuthorization)
            return null;

        boolean isBearer = authorization.startsWith("Beaer ");
        if (!isBearer)
            return null;

        return authorization.substring(7);
    }
}
