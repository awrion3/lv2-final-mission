package finalmission.time.controller;

import finalmission.time.dto.TimeResponse;
import finalmission.time.service.TimeService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/times")
@RequiredArgsConstructor
public class TimeController {

    private final TimeService timeService;

    @Operation(summary = "예약시간 조회 API")
    @GetMapping
    public List<TimeResponse> findAll() {
        return timeService.findAll();
    }
}
