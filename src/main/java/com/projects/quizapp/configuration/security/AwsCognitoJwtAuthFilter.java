package com.projects.quizapp.configuration.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.GenericFilter;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.projects.quizapp.constants.ExceptionConstants.AUTHENTICATION_FAILED_ERROR_MESSAGE;

@Slf4j
@Component
public class AwsCognitoJwtAuthFilter extends GenericFilter {

    private final AwsCognitoIdTokenProcessor cognitoIdTokenProcessor;

    public AwsCognitoJwtAuthFilter(AwsCognitoIdTokenProcessor cognitoIdTokenProcessor) {
        this.cognitoIdTokenProcessor = cognitoIdTokenProcessor;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        Authentication authentication;
        var response = (HttpServletResponse) servletResponse;
        try {
            authentication = this.cognitoIdTokenProcessor.authenticate((HttpServletRequest) servletRequest);
            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception exception) {
            log.error(AUTHENTICATION_FAILED_ERROR_MESSAGE, exception);
            SecurityContextHolder.clearContext();
            handleAuthenticationFailed(response);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void handleAuthenticationFailed(HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
}
