package com.nh.wenet.nh.frame.security.filter;

import com.nh.wenet.nh.frame.security.server.JwtUserDetailsService;
import com.nh.wenet.nh.frame.security.utils.JwtTokenUtil;
import com.nh.wenet.nh.frame.security.vo.UserInfo;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthorizationTokenFilter extends OncePerRequestFilter {

    private final JwtUserDetailsService jwtUserDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final String tokenHeader;

    public JwtAuthorizationTokenFilter(@Qualifier("jwtUserDetailsService") JwtUserDetailsService userDetailsService,
                                       JwtTokenUtil jwtTokenUtil, @Value("${jwt.header}") String tokenHeader) {
        this.jwtUserDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.tokenHeader = tokenHeader;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        final String requestHeader = request.getHeader(this.tokenHeader);
        String username = null;
        String authToken = null;
        if (requestHeader != null && requestHeader.startsWith("Wenotid")) {
            authToken = requestHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(authToken);
            } catch (ExpiredJwtException e) {
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserInfo userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

            if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }
        chain.doFilter(request, response);
    }

}
