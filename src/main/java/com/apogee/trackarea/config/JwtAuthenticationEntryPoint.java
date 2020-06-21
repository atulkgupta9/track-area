package com.apogee.trackarea.config;


import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Log4j2
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {


   @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException e) throws IOException, ServletException {
        if(e.getClass().equals(LockedException.class)){
            res.sendError(HttpServletResponse.SC_FORBIDDEN, "Please verify your email");
            return;
        }

        log.error("Responding to unauthorized error - {} ",e.getMessage());
        res.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
    }
}
