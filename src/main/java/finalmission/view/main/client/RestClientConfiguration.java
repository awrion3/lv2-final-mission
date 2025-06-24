package finalmission.view.main.client;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
@Profile("mission")
public class RestClientConfiguration {
    private static final Duration TIMEOUT_DURATION = Duration.ofSeconds(5);

    private final String authorizationKey;

    public RestClientConfiguration(@Value("${randommer.api.key}") String authorizationKey) {
        this.authorizationKey = authorizationKey;
    }

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .defaultHeader("X-Api-Key", authorizationKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .requestFactory(createClientHttpRequestFactory())
                .build();
    }

    private ClientHttpRequestFactory createClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(TIMEOUT_DURATION);
        factory.setConnectTimeout(TIMEOUT_DURATION);
        return factory;
    }
}
