package dev.chipichapa.memestore.client.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "s3-aws")
public class S3Properties {
    private String url;
    private String accessKey;
    private String secretKey;
}
