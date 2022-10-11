package me.iqpizza6349.springpizza.domain.member.ro;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import me.iqpizza6349.springpizza.domain.member.entity.Member;

@Getter
public class RegisterRO {

    @JsonProperty("user_id")
    private final long userId;

    public RegisterRO(Member member) {
        this.userId = member.getId();
    }
}
