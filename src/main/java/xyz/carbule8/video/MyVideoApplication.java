package xyz.carbule8.video;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@MapperScan("xyz.carbule8.video.mapper")
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
@EnableConfigurationProperties
public class MyVideoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyVideoApplication.class, args);
    }
}
