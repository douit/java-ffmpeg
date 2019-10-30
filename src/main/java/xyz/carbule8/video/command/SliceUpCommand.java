package xyz.carbule8.video.command;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Slf4j
public class SliceUpCommand extends SimpleExecute {

    @Override
    public String execute(Object... args) throws IOException {
        BufferedReader bufferedReader = this.startCommand(args);
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            log.info(line);
        }
        bufferedReader.close();
        return null; // 切片日志输出过大 不应存储内存中
    }

    @Override
    public List<String> getCommand(Object... args) {
        List<String> commands = new ArrayList<>();
        commands.add("ffmpeg");
        commands.add("-i");
        commands.add(String.valueOf(args[0]));
        commands.add("-c:v");
        commands.add("libx264");
        commands.add("-c:a");
        commands.add("aac");
        commands.add("-strict");
        commands.add("-2");
        commands.add("-f");
        commands.add("hls");
        commands.add("-hls_list_size");
        commands.add("0");
        commands.add("-threads"); // 利用多线程 降低cpu占用率
        commands.add("2");
        commands.add("-hls_time");
        commands.add("5");
        commands.add(args[1] + "/video.m3u8");
        return commands;
    }
}
