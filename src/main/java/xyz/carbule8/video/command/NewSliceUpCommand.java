package xyz.carbule8.video.command;

import java.io.BufferedReader;
import java.io.IOException;

public class NewSliceUpCommand extends SliceUpCommand {
    @Override
    public String execute(Object... args) throws IOException {
        BufferedReader bufferedReader = startCommand(args);
        while (bufferedReader.readLine() != null) {
            // 及时取走子进程的输出信息和错误信息 否则输出信息流和错误信息流可能因为信息太多导致被填满
            // 最终导致子进程阻塞住 不执行
        }
        bufferedReader.close();
        return null;
    }
}
