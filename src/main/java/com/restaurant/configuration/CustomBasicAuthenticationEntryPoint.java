package com.restaurant.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomBasicAuthenticationEntryPoint.class);

    @Override
    public void commence(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
            throws IOException {

        if (authEx instanceof BadCredentialsException) {
            LOGGER.error("Forbidden : wrong credentials received", authEx);
            response.setStatus(HttpStatus.FORBIDDEN.value());
        } else {
            LOGGER.error("Unauthorized", authEx);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
    }


    @Override
    public void afterPropertiesSet() {
        setRealmName("tan-service");
        //super.afterPropertiesSet();
    }
}
