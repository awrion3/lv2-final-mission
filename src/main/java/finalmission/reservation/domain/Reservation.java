package finalmission.reservation.domain;

import finalmission.member.domain.Member;
import finalmission.time.domain.ReservationTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @JoinColumn(nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ReservationTime time;

    @JoinColumn(nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Member member;

    @Builder
    private Reservation(
            final Long id,
            @NonNull final LocalDate date,
            @NonNull final ReservationTime time,
            @NonNull final Member member,
            @NonNull final LocalDateTime currentDateTime
    ) {
        validateFutureOrPresent(currentDateTime, date, time);
        this.id = id;
        this.date = date;
        this.time = time;
        this.member = member;
    }

    public static Reservation of(
            final LocalDate date,
            final ReservationTime reservationTime,
            final Member member,
            final LocalDateTime currentDateTime
    ) {
        return builder()
                .id(null)
                .date(date)
                .time(reservationTime)
                .member(member)
                .currentDateTime(currentDateTime)
                .build();
    }

    public static Reservation admin(
            final LocalDate date,
            final ReservationTime reservationTime,
            final Member member,
            final LocalDateTime currentDateTime
    ) {
        return builder()
                .id(null)
                .date(date)
                .time(reservationTime)
                .member(member)
                .currentDateTime(currentDateTime)
                .build();
    }

    private void validateFutureOrPresent(LocalDateTime currentDateTime, LocalDate date, ReservationTime time) {
        final LocalDateTime reservationDateTime = LocalDateTime.of(date, time.getStartAt());
        if (reservationDateTime.isBefore(currentDateTime)) {
            throw new IllegalArgumentException("예약은 현재 시간 이후로 가능합니다.");
        }
    }
}
