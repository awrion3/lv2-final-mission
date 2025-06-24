package finalmission.time.service;

import finalmission.time.domain.ReservationTime;
import finalmission.time.dto.TimeResponse;
import finalmission.time.repository.TimeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TimeService {

    private final TimeRepository timeRepository;

    public List<TimeResponse> findAll() {
        final List<ReservationTime> reservationTimes = timeRepository.findAll();
        return reservationTimes.stream()
                .map(TimeResponse::new)
                .toList();
    }
}
