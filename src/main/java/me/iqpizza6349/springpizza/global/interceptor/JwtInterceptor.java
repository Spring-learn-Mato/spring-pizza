package me.iqpizza6349.springpizza.global.interceptor;

import lombok.RequiredArgsConstructor;
import me.iqpizza6349.springpizza.domain.member.entity.Member;
import me.iqpizza6349.springpizza.global.jwt.AuthGuard;
import me.iqpizza6349.springpizza.global.jwt.JwtProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtProvider jwtProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if (!handlerMethod.getMethod().isAnnotationPresent(AuthGuard.class)) {
            return true;
        }

        String token = jwtProvider.getTokenFromHeader(request.getHeader(HttpHeaders.AUTHORIZATION));
        if (token == null) {
            throw new RuntimeException();
        }

        Member member = jwtProvider.findMemberFromToken(token);
        request.setAttribute("member", member);
        return true;
    }
}
