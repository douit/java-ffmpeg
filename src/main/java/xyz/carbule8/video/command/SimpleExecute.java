package xyz.carbule8.video.command;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@Slf4j
public abstract class SimpleExecute implements ExecuteCommand {

    @Override
    public String execute(Object... args) throws IOException {
        BufferedReader bufferedReader = this.startCommand(args);
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            log.info(line);
            stringBuilder.append(line);
        }
        bufferedReader.close();
        return stringBuilder.toString();
    }

    @Override
    public abstract List<String> getCommand(Object... args);
}
