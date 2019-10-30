package xyz.carbule8.video;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.carbule8.video.service.VideoService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyVideoApplicationTests {

    @Autowired
    private VideoService videoService;

    @Test
    public void contextLoads() {
    }

}
