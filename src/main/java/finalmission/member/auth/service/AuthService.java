package finalmission.member.auth.service;

import finalmission.member.auth.dto.request.MemberLoginRequest;
import finalmission.member.auth.dto.response.MemberLoginCheckResponse;
import finalmission.member.auth.dto.response.MemberLoginResponse;
import finalmission.member.auth.infrastructure.jwt.TokenProvider;
import finalmission.member.domain.Member;
import finalmission.member.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private static final String MEMBER_ID = "memberId";

    private final TokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    public String createToken(final MemberLoginRequest loginRequest) {
        final Member member = memberRepository.getByEmailAndPassword(loginRequest.email(), loginRequest.password());
        return jwtTokenProvider.createToken(createClaims(member));
    }

    private Claims createClaims(final Member member) {
        return Jwts.claims()
                .subject(member.getId().toString())
                .build();
    }

    public MemberLoginCheckResponse checkLogin(final String token) {
        final Long memberId = parseMemberId(token);
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 회원입니다. 아이디: " + memberId));
        return new MemberLoginCheckResponse(member);
    }

    private Long parseMemberId(final String token) {
        try {
            return Long.valueOf(jwtTokenProvider.extractPrincipal(token));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }
    }

    public void checkAdmin(final HttpServletRequest request) {
        final MemberLoginResponse loginMember = extractMemberFromRequest(request);
        if (!loginMember.isAdmin()) {
            throw new IllegalArgumentException("관리자 권한이 필요합니다.");
        }
    }

    public MemberLoginResponse extractMemberFromRequest(final HttpServletRequest request) {
        try {
            return findMemberById(extractMemberId(request));
        } catch (IllegalArgumentException e) {
            log.debug(e.getMessage());
            throw new IllegalArgumentException("인증에 실패했습니다.");
        }
    }

    private Long extractMemberId(final HttpServletRequest request) {
        final Object raw = request.getAttribute(MEMBER_ID);

        return Optional.ofNullable(raw)
                .filter(Long.class::isInstance)
                .map(Long.class::cast)
                .orElseThrow(() -> new IllegalArgumentException("memberId 형식이 올바르지 않습니다. 멤버 아이디: " + raw));
    }

    private MemberLoginResponse findMemberById(final Long memberId) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다. 멤버 아이디: " + memberId));
        return new MemberLoginResponse(member.getId(), member.getName(), member.getEmail(), member.getRole());
    }
}
