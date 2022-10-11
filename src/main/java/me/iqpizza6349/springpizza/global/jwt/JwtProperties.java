package me.iqpizza6349.springpizza.global.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private String secret;
    private long expirationSecond;

    public JwtProperties(String secret, long expirationSecond) {
        this.secret = secret;
        this.expirationSecond = expirationSecond;
    }

    public JwtProperties() {
    }
}
