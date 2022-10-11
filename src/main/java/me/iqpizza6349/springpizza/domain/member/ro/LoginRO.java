package me.iqpizza6349.springpizza.domain.member.ro;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRO {
    @JsonProperty("access_token")
    private final String accessToken;

    @JsonProperty("expiration_second")
    private final long expirationSecond;
}
