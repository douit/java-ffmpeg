package xyz.carbule8.video.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "oss")
@Data
public class OSSConfig {
    private  String endPoint;

    private String accessKeyId;

    private String accessKeySecret;

    private String bucketName;
}
