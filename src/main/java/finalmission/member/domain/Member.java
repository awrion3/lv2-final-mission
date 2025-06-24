package finalmission.member.domain;

import finalmission.member.auth.dto.response.MemberLoginResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Builder
    private Member(final Long id,
                   @NonNull final String name,
                   @NonNull final String email,
                   final String password,
                   @NonNull final MemberRole role
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public static Member withDefaultRole(
            @NonNull final String name,
            @NonNull final String email,
            @NonNull final String password
    ) {
        return builder()
                .id(null)
                .name(name)
                .email(email)
                .password(password)
                .role(MemberRole.MEMBER)
                .build();
    }

    public static Member from(final MemberLoginResponse loginMember) {
        return builder()
                .id(loginMember.id())
                .name(loginMember.name())
                .email(loginMember.email())
                .password(null)
                .role(loginMember.role())
                .build();
    }
}
