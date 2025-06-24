package finalmission.reservation.service;

import finalmission.member.auth.dto.response.MemberLoginResponse;
import finalmission.member.domain.Member;
import finalmission.member.repository.MemberRepository;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.dto.request.ReservationAdminRequest;
import finalmission.reservation.dto.request.ReservationAdminSearchRequest;
import finalmission.reservation.dto.request.ReservationRequest;
import finalmission.reservation.dto.response.ReservationMineResponse;
import finalmission.reservation.dto.response.ReservationResponse;
import finalmission.reservation.repository.ReservationRepository;
import finalmission.time.domain.ReservationTime;
import finalmission.time.dto.AvailableTimeResponse;
import finalmission.time.repository.TimeRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;
    private final MemberRepository memberRepository;

    public List<ReservationResponse> findReservationsByCriteria(final ReservationAdminSearchRequest request) {
        final List<Reservation> reservations = reservationRepository.findByCriteria(
                request.memberId(), request.dateFrom(), request.dateTo());
        return reservations.stream()
                .map(ReservationResponse::new)
                .toList();
    }

    public List<AvailableTimeResponse> findAllAvailableTime(final LocalDate date) {
        return timeRepository.findAllAvailable(date);
    }

    public List<ReservationMineResponse> findMyReservations(final MemberLoginResponse loginMember) {
        final Member member = Member.from(loginMember);
        return reservationRepository.findAllByMember(member).stream()
                .map(ReservationMineResponse::new)
                .toList();
    }

    public ReservationResponse saveReservation(final ReservationRequest request,
                                               final MemberLoginResponse loginMember) {
        final ReservationTime reservationTime = timeRepository.getById(request.timeId());

        if (reservationRepository.existsByDateAndTime(request.date(), reservationTime)) {
            throw new IllegalArgumentException("이미 예약되어 있습니다.");
        }

        Reservation savedReservation = bookReservation(request.date(), reservationTime, loginMember);
        return new ReservationResponse(savedReservation);
    }

    private Reservation bookReservation(
            LocalDate date, ReservationTime reservationTime, MemberLoginResponse loginMember
    ) {
        final Member member = Member.from(loginMember);
        final Reservation reservation = Reservation.of(date, reservationTime, member, LocalDateTime.now());

        return reservationRepository.save(reservation);
    }

    public ReservationResponse saveReservationByAdmin(final ReservationAdminRequest request) {
        final ReservationTime reservationTime = timeRepository.getById(request.timeId());
        final Member member = memberRepository.getById(request.memberId());

        if (reservationRepository.existsByDateAndTime(request.date(), reservationTime)) {
            throw new IllegalArgumentException("해당 시간은 이미 예약되어있습니다.");
        }
        final Reservation reservation = Reservation.admin(
                request.date(), reservationTime, member, LocalDateTime.now()
        );
        final Reservation newReservation = reservationRepository.save(reservation);
        return new ReservationResponse(newReservation);
    }

    public void deleteReservation(final Long id) {
        reservationRepository.deleteById(id);
    }
}
