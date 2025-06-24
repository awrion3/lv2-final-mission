package finalmission.time.repository;

import finalmission.time.domain.ReservationTime;
import finalmission.time.dto.AvailableTimeResponse;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TimeRepository extends JpaRepository<ReservationTime, Long> {

    @Override
    default ReservationTime getById(Long id) {
        return findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약 시간입니다. id: " + id));
    }

    @Query("""
            select distinct
              new finalmission.time.dto.AvailableTimeResponse(
                rt.id, rt.startAt, (r.id is not null) as alreadyBooked
              )
            from ReservationTime rt
            left join Reservation r
              on rt.id = r.time.id
              and r.date = :date
            order by rt.startAt
            """)
    List<AvailableTimeResponse> findAllAvailable(
            @Param("date") final LocalDate date
    );
}
