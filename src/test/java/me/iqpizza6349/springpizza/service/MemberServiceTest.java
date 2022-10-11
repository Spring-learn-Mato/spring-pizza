package me.iqpizza6349.springpizza.service;

import me.iqpizza6349.springpizza.domain.member.dto.SignDto;
import me.iqpizza6349.springpizza.domain.member.entity.Member;
import me.iqpizza6349.springpizza.domain.member.repository.MemberRepository;
import me.iqpizza6349.springpizza.domain.member.ro.LoginRO;
import me.iqpizza6349.springpizza.domain.member.ro.RegisterRO;
import me.iqpizza6349.springpizza.domain.member.service.MemberService;
import me.iqpizza6349.springpizza.global.jwt.JwtProperties;
import me.iqpizza6349.springpizza.global.jwt.JwtProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    private final JwtProperties jwtProperties = new JwtProperties(
            "ThisSecretThisSecretThisSecretThisSecretThisSecretThisSecret",
            3600000
    );

    @Mock
    private MemberRepository memberRepository;

    @Spy
    private JwtProvider jwtProvider = new JwtProvider(jwtProperties, memberRepository);

    @InjectMocks
    private MemberService memberService;

    private Member member(String username, String password, Member.Role role) {
        return Member.builder()
                .id(1L)
                .username(username)
                .password(password)
                .role(role)
                .build();
    }

    private Member member(SignDto signDto, Member.Role role) {
        return member(signDto.getUsername(), signDto.getPassword(), role);
    }

    private Member member(SignDto signDto) {
        return member(signDto, Member.Role.MEMBER);
    }

    @Test
    @DisplayName("회원가입 테스트 성공")
    void registerSuccessTest() {
        // given
        String username = "username";
        String password = "password";
        SignDto signDto = new SignDto(username, password);

        // when
        lenient().when(memberRepository.save(any()))
                        .thenReturn(member(signDto));
        RegisterRO registerRO = memberService.register(signDto);

        // then
        assertThat(registerRO).isNotNull();
    }

    @Test
    @DisplayName("로그인 테스트 실패")
    void loginFailedTest() {
        // given
        lenient().when(memberRepository.findByUsernameAndPassword(
                anyString(), anyString()
        )).thenThrow(RuntimeException.class);

        // when
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> memberRepository.findByUsernameAndPassword(anyString(), anyString())
        );

        // then
        assertThat(exception).isNotNull();
    }

    @Test
    @DisplayName("로그인 테스트 성공")
    void loginSuccessTest() {
        // given
        String username = "admin";
        String password = "password";
        SignDto signDto = new SignDto(username, password);
        lenient().when(memberRepository.findByUsernameAndPassword(
                anyString(), anyString()
        )).thenReturn(Optional.of(member(signDto)));

        // when
        LoginRO loginRO = memberService.login(signDto);

        assertThat(loginRO).isNotNull();
        assertThat(loginRO.getAccessToken()).isNotBlank();
        assertThat(loginRO.getExpirationSecond()).isNotZero();
    }
}
