package finalmission.reservation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import finalmission.reservation.domain.Reservation;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationMineResponse(
        Long reservationId,
        @JsonFormat(pattern = "yyyy-MM-dd") LocalDate date,
        @JsonFormat(pattern = "HH:mm") LocalTime time
) {
    public ReservationMineResponse(final Reservation reservation) {
        this(
                reservation.getId(),
                reservation.getDate(),
                reservation.getTime().getStartAt()
        );
    }
}
