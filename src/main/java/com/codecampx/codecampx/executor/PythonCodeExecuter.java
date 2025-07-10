package com.codecampx.codecampx.executor;

import com.codecampx.codecampx.payload.executionhistory.ExecutionOutputDto;
import com.codecampx.codecampx.util.FileUtil;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

public class PythonCodeExecuter implements Executer {

    @Override
    public ExecutionOutputDto execute(Path directory, String code,String input) throws IOException {
        Path codePath = directory.resolve("Main.py");
        Files.writeString(codePath,code);

        ProcessBuilder builder = new ProcessBuilder("python","Main.py");
        builder.directory(directory.toFile());
        Process process = builder.start();

        //Put Input
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))) {
            writer.write(input);
            writer.flush();
            writer.close();
        }

        //
        boolean finished = false;
        try {
            finished = process.waitFor(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
        if (!finished) {
            process.destroyForcibly();
            return new ExecutionOutputDto("Execution Timeout", "Code took too long For Output", false);
        }
        //

        String errorOutput = getStream(process.getErrorStream());
        if (!errorOutput.isEmpty()){
            FileUtil.deleteTempDirectory(codePath);
            FileUtil.deleteTempDirectory(directory);
            return new ExecutionOutputDto(
                    "Code Execution Failed",errorOutput,false
            );
        }
        String successOutput = getStream(process.getInputStream());
        FileUtil.deleteTempDirectory(codePath);
        FileUtil.deleteTempDirectory(directory);
        return new ExecutionOutputDto(
                "Code Execution Successful",successOutput,true
        );
    }

    public String getStream(InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder stringBuilder = new StringBuilder();
        String str;
        while ((str = reader.readLine()) != null){
            stringBuilder.append(str).append("\n");
        }
        return stringBuilder.toString();
    }


}
