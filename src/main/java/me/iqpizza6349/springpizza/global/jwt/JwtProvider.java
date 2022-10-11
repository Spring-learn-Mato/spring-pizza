package me.iqpizza6349.springpizza.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import me.iqpizza6349.springpizza.domain.member.entity.Member;
import me.iqpizza6349.springpizza.domain.member.repository.MemberRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private static final String IDENT_ACCESS = "ACCESS";
    private static final String IDENT_REFRESH = "REFRESH";
    private final JwtProperties jwtProperties;
    private final SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;
    private final MemberRepository memberRepository;

    public String generateAccessToken(long id) {
        return generateToken(IDENT_ACCESS, id, jwtProperties.getExpirationSecond());
    }

    private String generateToken(String type, long id, long expSecond) {
        final Date tokenCreationDate = new Date();

        return Jwts.builder()
                .claim("type", type)
                .setSubject(String.valueOf(id))
                .setIssuedAt(tokenCreationDate)
                .setExpiration(new Date(tokenCreationDate.getTime() + expSecond))
                .signWith(generateKey(), algorithm)
                .compact();
    }

    private Claims validateToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(Base64.decodeBase64(jwtProperties.getSecret()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            throw new JwtException(e.getMessage());
        }
    }

    private String extractLoginFromToken(String token) {
        return validateToken(token).getSubject();
    }

    public Member findMemberFromToken(String token) {
        return memberRepository.findById(Long.parseLong(extractLoginFromToken(token)))
                .orElseThrow();
    }

    public String getTokenFromHeader(String headerValue) {
        if (headerValue != null && headerValue.startsWith("Bearer ")) {
            return headerValue.replace("Bearer ", "");
        }

        return null;
    }

    public long getExpirationSecond() {
        return jwtProperties.getExpirationSecond();
    }

    private Key generateKey() {
        byte[] apiKeySecret = Base64.decodeBase64(jwtProperties.getSecret());
        return new SecretKeySpec(apiKeySecret, algorithm.getJcaName());
    }
}
