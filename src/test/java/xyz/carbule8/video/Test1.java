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

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

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

    @Test
    public void t2() {
        List<String> names = new ArrayList<>();
        try (
                FileReader fileReader = new FileReader("C:\\Users\\carbu\\Desktop\\test\\output\\output.m3u8");
//                FileWriter fileWriter = new FileWriter("C:\\Users\\carbu\\Desktop\\test\\output\\output.m3u8")
        ) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains(".ts") && !line.contains("#")) {
                    System.out.println(line);
                    String random = Base64.getUrlEncoder().encodeToString(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));
                    System.out.println(random);
                    names.add(random);
                    File file = new File("C:\\Users\\carbu\\Desktop\\test\\output\\" + line);
                    file.renameTo(new File("C:\\Users\\carbu\\Desktop\\test\\output\\" + random));
                } else {
                    names.add(line);
                }
            }
//            fileWriter.write("");
//            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (
                FileWriter writer = new FileWriter("C:\\Users\\carbu\\Desktop\\test\\output\\output.m3u8");
                FileWriter fileWriter = new FileWriter("C:\\Users\\carbu\\Desktop\\test\\output\\output.m3u8", true)
        ) {
            writer.write("");
            writer.flush();
            for (String line : names)
                fileWriter.write(line + "\n");
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
