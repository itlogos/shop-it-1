package ag.selm.manager.config;

import ag.selm.manager.client.RestClientProductsRestClient;
import ag.selm.manager.security.OAuthClientHttpRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientBeans {

    @Bean
    public RestClientProductsRestClient productsRestClient(
            @Value("${selmag.service.catalogue.uri:http://back:8081}") String baseUri,
            OAuth2AuthorizedClientManager authorizedClientManager) {

        return new RestClientProductsRestClient(
                RestClient.builder()
                        .baseUrl(baseUri)
                        .requestInterceptor(
                                new OAuthClientHttpRequestInterceptor(authorizedClientManager, "keycloak"))
                        .build()
        );
    }
}
