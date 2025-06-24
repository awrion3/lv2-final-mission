package finalmission.member.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record MemberLoginRequest(
        @Email @NotBlank String email,
        @NotBlank String password
) {
}
