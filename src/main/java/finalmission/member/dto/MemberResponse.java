package finalmission.member.dto;

import finalmission.member.domain.Member;

public record MemberResponse(
        Long id,
        String name,
        String email,
        String password,
        String role
) {

    public MemberResponse(Member member) {
        this(member.getId(), member.getName(), member.getEmail(), member.getPassword(), member.getRole().name());
    }
}
