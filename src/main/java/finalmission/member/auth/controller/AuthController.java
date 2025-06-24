package finalmission.member.auth.controller;

import finalmission.member.auth.dto.request.MemberLoginRequest;
import finalmission.member.auth.dto.response.MemberLoginCheckResponse;
import finalmission.member.auth.infrastructure.util.CookieHandler;
import finalmission.member.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private static final String COOKIE_TOKEN = "token";

    private final AuthService authService;
    private final CookieHandler cookieHandler;

    @Operation(summary = "로그인 API")
    @PostMapping("/login")
    public void login(@RequestBody @Valid final MemberLoginRequest request, final HttpServletResponse response) {
        final String token = authService.createToken(request);
        final ResponseCookie cookie = cookieHandler.generateLoginCookie(token);

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    @Operation(summary = "로그아웃 API")
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(final HttpServletResponse response) {
        final ResponseCookie cookie = cookieHandler.generateLogoutCookie();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    @Operation(summary = "로그인 확인 API")
    @GetMapping("/login/check")
    @ResponseBody
    public MemberLoginCheckResponse checkLogin(@CookieValue(name = COOKIE_TOKEN, required = false) String token) {
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("토큰 쿠키가 존재하지 않습니다.");
        }
        return authService.checkLogin(token);
    }
}
