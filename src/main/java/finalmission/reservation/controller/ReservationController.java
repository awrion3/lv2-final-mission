package finalmission.reservation.controller;

import finalmission.member.auth.dto.response.MemberLoginResponse;
import finalmission.reservation.dto.request.ReservationAdminSearchRequest;
import finalmission.reservation.dto.request.ReservationRequest;
import finalmission.reservation.dto.response.ReservationMineResponse;
import finalmission.reservation.dto.response.ReservationResponse;
import finalmission.reservation.service.ReservationService;
import finalmission.time.dto.AvailableTimeResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
@Validated
public class ReservationController {

    private final ReservationService reservationService;

    @Operation(summary = "관리자용 예약 검색 조회 API")
    @GetMapping
    public List<ReservationResponse> findReservationsByCriteria(
            @ModelAttribute final ReservationAdminSearchRequest request) {
        return reservationService.findReservationsByCriteria(request);
    }

    @Operation(summary = "예약 가능 시간대 조회 API")
    @GetMapping("/times")
    public List<AvailableTimeResponse> findAllAvailableTimes(
            @NotNull @RequestParam final LocalDate date
    ) {
        return reservationService.findAllAvailableTime(date);
    }

    @Operation(summary = "예약 추가 API")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponse saveReservation(
            @Valid @RequestBody final ReservationRequest request,
            final MemberLoginResponse member
    ) {
        return reservationService.saveReservation(request, member);
    }

    @Operation(summary = "예약 취소 API")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReservation(@PathVariable final Long id) {
        reservationService.deleteReservation(id);
    }

    @Operation(summary = "회원별 예약 조회 API")
    @GetMapping("/mine")
    public List<ReservationMineResponse> findMyReservations(final MemberLoginResponse loginMember) {
        return reservationService.findMyReservations(loginMember);
    }
}
