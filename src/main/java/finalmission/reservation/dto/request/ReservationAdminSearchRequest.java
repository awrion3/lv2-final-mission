package finalmission.reservation.dto.request;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

public record ReservationAdminSearchRequest(
        Long memberId,
        @DateTimeFormat LocalDate dateFrom,
        @DateTimeFormat LocalDate dateTo
) {
}
