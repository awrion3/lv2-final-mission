package finalmission.reservation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import finalmission.member.dto.MemberResponse;
import finalmission.reservation.domain.Reservation;
import finalmission.time.dto.TimeResponse;
import java.time.LocalDate;

public record ReservationResponse(
        Long id,
        @JsonFormat(pattern = "yyyy-MM-dd") LocalDate date,
        TimeResponse time,
        MemberResponse member
) {

    public ReservationResponse(Reservation reservation) {
        this(
                reservation.getId(),
                reservation.getDate(),
                new TimeResponse(reservation.getTime()),
                new MemberResponse(reservation.getMember())
        );
    }
}
