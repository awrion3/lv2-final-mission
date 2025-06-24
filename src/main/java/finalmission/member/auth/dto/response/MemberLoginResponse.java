package finalmission.member.auth.dto.response;

import finalmission.member.domain.MemberRole;
import lombok.NonNull;

public record MemberLoginResponse(
        @NonNull Long id,
        @NonNull String name,
        @NonNull String email,
        @NonNull MemberRole role
) {
    public boolean isAdmin() {
        return role.isAdmin();
    }
}
