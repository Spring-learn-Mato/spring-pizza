package me.iqpizza6349.springpizza.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.iqpizza6349.springpizza.domain.member.entity.Member;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor @NoArgsConstructor
public class SignDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public Member toEntity(Member.Role role) {
        return Member.builder()
                .username(username)
                .password(password)
                .role(role)
                .build();
    }
}
