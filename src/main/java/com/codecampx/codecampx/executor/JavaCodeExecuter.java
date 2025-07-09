package com.codecampx.codecampx.executor;

import com.codecampx.codecampx.payload.executionhistory.ExecutionOutputDto;
import com.codecampx.codecampx.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class JavaCodeExecuter implements Executer {

    Logger logger = LoggerFactory.getLogger(JavaCodeExecuter.class);

    @Override
    public ExecutionOutputDto execute(Path directory, String code) throws IOException {
        logger.debug("Java Code Execution Started");
        Path executionPath = directory.resolve("Main.java");
        Files.writeString(executionPath,code);

        ProcessBuilder compileBuilder = new ProcessBuilder("javac","Main.java");
        compileBuilder.directory(directory.toFile());
        Process compileProcess = compileBuilder.start();
        logger.debug("Code Execution Finished");

        String errorOutput = getStream(compileProcess.getErrorStream());
        if (!errorOutput.isEmpty()){
            logger.debug("Error Found In Exection");
            logger.debug("Delete Temp Directory");
            FileUtil.deleteTempDirectory(executionPath);
            FileUtil.deleteTempDirectory(directory.resolve("Main.class"));
            FileUtil.deleteTempDirectory(directory);
            return new ExecutionOutputDto("Code Execution Failed",errorOutput,false);
        }

        ProcessBuilder runBuilder = new ProcessBuilder("java","Main");
        runBuilder.directory(directory.toFile());
        Process runProcess = runBuilder.start();

        String successOutput = getStream(runProcess.getInputStream());
        logger.debug("No Error In Execution");

        FileUtil.deleteTempDirectory(executionPath);
        logger.debug("Delete Temp Code File");
        FileUtil.deleteTempDirectory(directory.resolve("Main.class"));
        FileUtil.deleteTempDirectory(directory);
        logger.debug("Delete Temp Directory");
        return new ExecutionOutputDto("Code Execution Successful",successOutput,true);
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
