package finalmission.reservation.repository;

import finalmission.member.domain.Member;
import finalmission.reservation.domain.Reservation;
import finalmission.time.domain.ReservationTime;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Override
    default Reservation getById(Long id) {
        return findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약입니다. id: " + id));
    }

    @EntityGraph(attributePaths = {"member", "time"})
    @Query("""
            select r from Reservation r 
              where (:memberId is null or r.member.id = :memberId)
              and (:localDateFrom is null or r.date >= :localDateFrom)
              and (:localDateTo is null or r.date <= :localDateTo)
            """)
    List<Reservation> findByCriteria(
            @Param("memberId") Long memberId,
            @Param("localDateFrom") LocalDate localDateFrom,
            @Param("localDateTo") LocalDate localDateTo
    );

    List<Reservation> findAllByMember(final Member member);

    boolean existsByDateAndTime(final LocalDate date, final ReservationTime time);
}
