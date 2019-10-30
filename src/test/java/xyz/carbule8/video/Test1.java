package xyz.carbule8.video;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.carbule8.video.config.OSSConfig;
import xyz.carbule8.video.mapper.VideoMapper;
import xyz.carbule8.video.service.AccountService;
import xyz.carbule8.video.service.VideoService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class Test1 {

    @Autowired
    private VideoService videoService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private OSSConfig ossConfig;

    @Test
    public void t1() {
        String tmp = null;
        if (true) {
            tmp = "张三李四";
        }
        System.out.println(tmp);
    }


}
