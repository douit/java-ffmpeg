package xyz.carbule8.video.config.thread;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "task.pool")
@Component
@Data
public class TaskThreadPoolConfig {
    private int corePoolSize; // 核心线程大小

    private int maxPoolSize; // 最大线程数量

    private int keepAliveSeconds; // 线程活跃时间

    private int queueCapacity; // 队列容量
}