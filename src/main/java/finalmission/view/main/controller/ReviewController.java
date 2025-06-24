package finalmission.view.main.controller;

import finalmission.view.main.client.ReviewClient;
import finalmission.view.main.dto.ReviewResponse;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewClient reviewClient;

    @Operation(summary = "랜덤 리뷰 생성 API")
    @PostMapping
    public List<ReviewResponse> generateReviews() {
        return reviewClient.generateReview();
    }
}
