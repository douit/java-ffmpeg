package xyz.carbule8.video.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public interface ExecuteCommand {

    default BufferedReader startCommand(Object ...args) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder(getCommand(args));
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();
        return new BufferedReader(new InputStreamReader(process.getInputStream()));
    }

    String execute(Object ...args) throws IOException;

    List<String> getCommand(Object... args);
}
