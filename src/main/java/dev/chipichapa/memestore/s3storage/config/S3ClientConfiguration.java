package dev.chipichapa.memestore.s3storage.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.util.AwsHostNameUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class S3ClientConfiguration {

    private final S3Properties properties;

    @Bean
    public AWSCredentials getCredentials() {
        return new BasicAWSCredentials(properties.getAccessKey(), properties.getSecretKey());
    }

    @Bean
    public AmazonS3 s3client() {
        return AmazonS3ClientBuilder
                .standard()
                .withPathStyleAccessEnabled(true)
                .withCredentials(new AWSStaticCredentialsProvider(getCredentials()))
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(
                                properties.getUrl(),
                                AwsHostNameUtils.parseRegion(properties.getUrl(), AmazonS3Client.S3_SERVICE_NAME)
                        )
                )
                .build();
    }
}
