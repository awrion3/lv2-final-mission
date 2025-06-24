package finalmission.reservation.controller.admin;

import finalmission.reservation.dto.request.ReservationAdminRequest;
import finalmission.reservation.dto.response.ReservationResponse;
import finalmission.reservation.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/admin/reservations")
@RestController
@RequiredArgsConstructor
public class AdminReservationController {

    private final ReservationService reservationService;

    @Operation(summary = "관리자용 예약 추가 API")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponse saveReservation(@Valid @RequestBody final ReservationAdminRequest request) {
        return reservationService.saveReservationByAdmin(request);
    }

    @Operation(summary = "관리자용 예약 취소 API")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelReservation(@PathVariable("id") final Long id) {
        reservationService.deleteReservation(id);
    }
}
