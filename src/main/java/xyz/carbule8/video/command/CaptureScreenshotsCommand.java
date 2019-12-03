package xyz.carbule8.video.command;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Slf4j
public class CaptureScreenshotsCommand extends SimpleExecute {
    @Override
    public String execute(Object... args) throws IOException {
        BufferedReader bufferedReader = this.startCommand(args);
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        bufferedReader.close();
        return stringBuilder.toString();
    }

    @Override
    public List<String> getCommand(Object... args) {
        List<String> command = new ArrayList<>();
        command.add("ffmpeg");
        command.add("-ss");
        command.add(String.valueOf(args[0])); // captureTimePointer
        command.add("-i");
        command.add(String.valueOf(args[1])); // originalVideoPath
        command.add("-y");
        command.add("-f");
        command.add("image2");
        command.add("-vframes");
        command.add("1");
        command.add(String.valueOf(args[2])); // screenshotsPath
        return command;
    }
}
