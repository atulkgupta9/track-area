package com.apogee.trackarea.config;

import com.apogee.trackarea.api.CustomUserDetailsService;
import com.apogee.trackarea.pojo.CustomUserDetails;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Log4j2

public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailsService api;

    @Autowired
    private JwtTokenProvider tokenProvider;



    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        try{
            String jwt = getJwtFromRequest(req);
            if(StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)){
                String username = tokenProvider.getUsernameFromJWT(jwt);
                CustomUserDetails userDetails = api.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            Cookie[] cookies = req.getCookies();
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("Bearer")){
                    String x = cookie.getValue();
                    if(tokenProvider.validateToken(x)){
                        String username = tokenProvider.getUsernameFromJWT(x);
                        CustomUserDetails userDetails = api.loadUserByUsername(username);
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
        }catch (Exception e){
            log.error("Could not set authentication in security Context", e);
        }
        filterChain.doFilter(req,res);
    }

    private String getJwtFromRequest(HttpServletRequest req){
        String bearerToken = req.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }
}
