package finalmission.time.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import finalmission.time.domain.ReservationTime;
import java.time.LocalTime;

public record TimeResponse(
        Long id,
        @JsonFormat(pattern = "HH:mm") LocalTime startAt
) {
    public TimeResponse(final ReservationTime reservationTime) {
        this(reservationTime.getId(), reservationTime.getStartAt());
    }
}
