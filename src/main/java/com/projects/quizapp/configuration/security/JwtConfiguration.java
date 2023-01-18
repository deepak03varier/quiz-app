package com.projects.quizapp.configuration.security;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import static com.projects.quizapp.constants.SecurityConstants.JWK_URL_PATH;

@Component
@ConfigurationProperties(
        prefix = "aws.cognito.jwt"
)
@Data
@NoArgsConstructor
public class JwtConfiguration {
    private String userPoolId;

    private String identityPoolId;

    private String jwkUrl;

    private String region;

    private String userNameField;

    private String groupsField;

    private int connectionTimeout;

    private int readTimeout;

    private String httpHeader;

    private String genericIdentityPoolUrl;

    public String getJwkUrl() {
        return this.jwkUrl != null && !this.jwkUrl.isEmpty() ? this.jwkUrl : String.format(
                genericIdentityPoolUrl + JWK_URL_PATH, this.region, this.userPoolId);
    }

    public String getCognitoIdentityPoolUrl() {
        return String.format(genericIdentityPoolUrl, this.region, this.userPoolId);
    }
}
