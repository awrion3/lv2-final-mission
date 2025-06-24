package finalmission.member.repository;

import finalmission.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Override
    default Member getById(Long id) {
        return findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 멤버입니다. id: " + id));
    }

    Optional<Member> findByEmailAndPassword(final String email, final String password);

    default Member getByEmailAndPassword(final String email, final String password) {
        return findByEmailAndPassword(email, password)
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 패스워드가 올바르지 않습니다."));
    }

    boolean existsByEmail(final String email);
}
