package finalmission.member.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record MemberSignupRequest(
        @Email @NotBlank String email,
        @NotBlank String password,
        @NotBlank String name
) {
}
