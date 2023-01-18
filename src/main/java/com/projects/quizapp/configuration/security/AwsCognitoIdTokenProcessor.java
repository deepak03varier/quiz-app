package com.projects.quizapp.configuration.security;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.projects.quizapp.exception.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static com.projects.quizapp.constants.ExceptionConstants.AUTHENTICATION_FAILED_ERROR_MESSAGE;
import static com.projects.quizapp.constants.ExceptionConstants.SECURITY_ISSUER_MISMATCH_GENERIC_ERROR_MESSAGE;
import static com.projects.quizapp.constants.GenericConstants.EMPTY_STRING;
import static com.projects.quizapp.constants.SecurityConstants.BEARER_ACCESS_TOKEN_PREFIX;

@Component
public class AwsCognitoIdTokenProcessor {

    private final JwtConfiguration jwtConfiguration;

    private final ConfigurableJWTProcessor configurableJWTProcessor;

    @Autowired
    public AwsCognitoIdTokenProcessor(JwtConfiguration jwtConfiguration,
                                      ConfigurableJWTProcessor configurableJWTProcessor) {
        this.jwtConfiguration = jwtConfiguration;
        this.configurableJWTProcessor = configurableJWTProcessor;
    }

    public Authentication authenticate(HttpServletRequest request) throws Exception {
        String idToken = request.getHeader(this.jwtConfiguration.getHttpHeader());
        if (idToken != null) {
            JWTClaimsSet claims = this.configurableJWTProcessor.process(this.getBearerToken(idToken), null);
            validateIssuer(claims);
            String username = getUserNameFrom(claims);
            List<String> groups = getGroupsFrom(claims);
            if (username != null) {
                List<GrantedAuthority> grantedAuthorities = groups.stream().map(SimpleGrantedAuthority::new).collect(
                        Collectors.toList());
                User user = new User(username, EMPTY_STRING, grantedAuthorities);
                return new JwtAuthentication(user, claims, grantedAuthorities);
            }
        }
        throw new UnauthorizedException(AUTHENTICATION_FAILED_ERROR_MESSAGE);
    }

    private List<String> getGroupsFrom(JWTClaimsSet claims) {

        return (List<String>) claims.getClaims().get(jwtConfiguration.getGroupsField());
    }

    private String getUserNameFrom(JWTClaimsSet claims) {
        return claims.getClaims().get(this.jwtConfiguration.getUserNameField()).toString();
    }

    private void validateIssuer(JWTClaimsSet claims) throws Exception {
        if (!claims.getIssuer().equals(this.jwtConfiguration.getCognitoIdentityPoolUrl())) {
            throw new Exception(String.format(SECURITY_ISSUER_MISMATCH_GENERIC_ERROR_MESSAGE, claims.getIssuer(),
                                              this.jwtConfiguration.getCognitoIdentityPoolUrl()));
        }
    }

    private String getBearerToken(String token) {
        return token.startsWith(BEARER_ACCESS_TOKEN_PREFIX) ? token.substring(BEARER_ACCESS_TOKEN_PREFIX.length())
                                                            : token;
    }
}
