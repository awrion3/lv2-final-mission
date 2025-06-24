package finalmission.member.service;

import finalmission.member.auth.dto.request.MemberSignupRequest;
import finalmission.member.domain.Member;
import finalmission.member.dto.MemberResponse;
import finalmission.member.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void save(final MemberSignupRequest request) {
        if (memberRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다. 이메일: " + request.email());
        }
        memberRepository.save(
                Member.withDefaultRole(request.name(), request.email(), request.password())
        );
    }

    public List<MemberResponse> findAllMember() {
        return memberRepository.findAll()
                .stream()
                .map(MemberResponse::new)
                .toList();
    }
}
