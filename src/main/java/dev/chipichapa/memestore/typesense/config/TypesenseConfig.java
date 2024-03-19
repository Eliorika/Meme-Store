package dev.chipichapa.memestore.typesense.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.typesense.api.Client;
import org.typesense.resources.Node;

import java.time.Duration;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class TypesenseConfig {

    private final TypesenseProperties typesenseProperties;

    @Bean
    List<Node> getAllNode() {
        Node node = new Node(
                typesenseProperties.getProtocol(),
                typesenseProperties.getHost(),
                typesenseProperties.getPort());
        return List.of(node);
    }

    @Bean
    Client typesenseClient() {
        org.typesense.api.Configuration configuration = new org.typesense.api.Configuration(
                getAllNode(),
                Duration.ofSeconds(2),
                typesenseProperties.getApiKey()
        );
        return new Client(configuration);
    }
}
