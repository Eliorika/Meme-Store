package dev.chipichapa.memestore.typesense.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("typesense")
public class TypesenseProperties {
    private String protocol;
    private String host;
    private String port;
    private String apiKey;
}
