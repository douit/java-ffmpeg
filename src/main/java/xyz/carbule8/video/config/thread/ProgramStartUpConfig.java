package xyz.carbule8.video.config.thread;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import xyz.carbule8.video.service.VideoService;

@Configuration
public class ProgramStartUpConfig implements CommandLineRunner {
    private final VideoService videoService;

    public ProgramStartUpConfig(VideoService videoService) {
        this.videoService = videoService;
    }

    @Override
    public void run(String... args) throws Exception {
        videoService.findAllNotCompleteVideos(); // 查询所有本地上传完成未转码完成的视频 放入任务队列
    }

}
