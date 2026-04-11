package home.oauth2client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    private final OAuth2AuthorizedClientManager authorizedClientManager;

    public RestTemplateConfig(OAuth2AuthorizedClientManager authorizedClientManager) {
        this.authorizedClientManager = authorizedClientManager;
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getInterceptors().add((request, body, execution) -> {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest
                    .withClientRegistrationId("file-client")
                    .principal(authentication)
                    .build();

            OAuth2AuthorizedClient client =
                    authorizedClientManager.authorize(authorizeRequest);

            if (client == null) {
                throw new IllegalStateException("Не вдалося отримати access token");
            }

            String accessToken = client.getAccessToken().getTokenValue();

            request.getHeaders().setBearerAuth(accessToken);

            return execution.execute(request, body);
        });

        return restTemplate;
    }
}
