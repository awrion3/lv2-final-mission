package finalmission.member.auth.dto.response;

import finalmission.member.domain.Member;

public record MemberLoginCheckResponse(String name) {

    public MemberLoginCheckResponse(final Member member) {
        this(member.getName());
    }
}
