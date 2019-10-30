package xyz.carbule8.video.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Slf4j
@ConfigurationProperties(prefix = "video")
@Component
public class VideoConfig {
    private String localPath;

    private String accessUrl;
}
