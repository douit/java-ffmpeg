package xyz.carbule8.video.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class FFmpegCommandRunner {

//    执行语句
    private void execute(List<String> command) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();
        try (
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()))
        ) {
            String line;
            while ((line = bufferedReader.readLine()) != null)
                log.info(line);
        }
    }

//    转码至MP4
    public void transToMp4(String input, String output) {
        List<String> command = new ArrayList<>();
        command.add("ffmpeg");
        command.add("-i");
        command.add(input);
        command.add("-threads");
        command.add("2");
        command.add(output);
        try {
            execute(command);
        } catch (IOException e) {
            log.error("转码mp4异常: ", e);
        }
    }

//    转码至TS
    public void tranToTs(String inputFile, String output) {
        List<String> command = new ArrayList<>();
        command.add("ffmpeg");
        command.add("-i");
        command.add(inputFile);
        command.add("-c");
        command.add("copy");
        command.add("-vbsf");
        command.add("h264_mp4toannexb");
        command.add(output);
        try {
            execute(command);
        } catch (IOException e) {
            log.error("转码ts异常: ", e);
        }
    }

//    TS文件切片
    public void slice(String inputFile, String outputDir) {
        List<String> command = new ArrayList<>();
        command.add("ffmpeg");
        command.add("-i");
        command.add(inputFile);
        command.add("-c");
        command.add("copy");
        command.add("-map");
        command.add("0");
        command.add("-f");
        command.add("segment");
        command.add("-segment_list");
        command.add(outputDir + "/playlist.m3u8");
        command.add("-segment_time");
        command.add("5");
        command.add(outputDir + "/output%03d.ts");
        try {
            execute(command);
        } catch (IOException e) {
            log.error("切片异常: ", e);
        }
    }

//    截图
    public void capture(String input, String output, int capturePoint) {
        List<String> command = new ArrayList<>();
        command.add("ffmpeg");
        command.add("-ss");
        command.add(String.valueOf(capturePoint)); // captureTimePointer
        command.add("-i");
        command.add(input); // originalVideoPath
        command.add("-y");
        command.add("-f");
        command.add("image2");
        command.add("-vframes");
        command.add("1");
        command.add(output); // screenshotsPath
        try {
            execute(command);
        } catch (IOException e) {
            log.error("截图异常: ", e);
        }
    }
}
