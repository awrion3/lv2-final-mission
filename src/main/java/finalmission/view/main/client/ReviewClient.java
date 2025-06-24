package finalmission.view.main.client;

import finalmission.view.main.dto.ReviewResponse;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@Slf4j
public class ReviewClient {

    private final String RANDOM_API_URI_PATH = "https://randommer.io/api/Text/Review";

    private final RestClient restClient;
    private final RandomReviewErrorHandler errorHandler;

    public ReviewClient(
            RestClient restClient,
            RandomReviewErrorHandler errorHandler
    ) {
        this.restClient = restClient;
        this.errorHandler = errorHandler;
    }

    public List<ReviewResponse> generateReview() {
        String[] content = restClient.post()
                .uri(RANDOM_API_URI_PATH, uriBuilder -> uriBuilder
                        .queryParam("product", "Website")
                        .queryParam("quantity", 10)
                        .build())
                .retrieve()
                .onStatus(errorHandler)
                .body(String[].class);
        return convertToResponse(content);
    }

    private List<ReviewResponse> convertToResponse(String[] content) {
        return Arrays.stream(content)
                .map(ReviewResponse::new)
                .toList();
    }
}
