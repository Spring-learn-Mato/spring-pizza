package me.iqpizza6349.springpizza.domain.member.service;

import lombok.RequiredArgsConstructor;
import me.iqpizza6349.springpizza.domain.member.dto.SignDto;
import me.iqpizza6349.springpizza.domain.member.entity.Member;
import me.iqpizza6349.springpizza.domain.member.repository.MemberRepository;
import me.iqpizza6349.springpizza.domain.member.ro.LoginRO;
import me.iqpizza6349.springpizza.domain.member.ro.RegisterRO;
import me.iqpizza6349.springpizza.global.jwt.JwtProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    public RegisterRO register(final SignDto signDto) {
        Member.Role role = Member.Role.MEMBER;
        if (signDto.getUsername().equalsIgnoreCase("admin")) {
            role = Member.Role.LIBRARIAN;
        }

        final Member.Role memberRole = role;
        return new RegisterRO(memberRepository.save(signDto.toEntity(memberRole)));
    }

    public LoginRO login(final SignDto signDto) {
        Member member = memberRepository.findByUsernameAndPassword(
                signDto.getUsername(), signDto.getPassword()
        ).orElseThrow();
        final long memberId = member.getId();
        String accessToken = jwtProvider.generateAccessToken(memberId);
        return new LoginRO(accessToken, jwtProvider.getExpirationSecond());
    }
}
