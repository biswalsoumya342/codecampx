package com.codecampx.codecampx.util;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
/**
 * @deprecated UNUSED CLASS
 * <p>
 * This Class I created for testing and
 * i have already change it to modular format
 * So when the project will completed i will
 * remove this when i will deploy on cloud
 * </p>
 * **/

@Deprecated
public class CodeExecutionUtil {
    private boolean status = false;

    public String executeCode(String language, String code) throws IOException {
        String executionPath = "/temp/code"+System.currentTimeMillis()+ UUID.randomUUID();

        Path absolutePath = Paths.get(executionPath);

        Path codePath;

        if(!Files.exists(absolutePath)){
            Files.createDirectories(absolutePath);
        }
        switch(language.toLowerCase()){
            case "java" -> {
                codePath = absolutePath.resolve("Main.java");
                Files.writeString(codePath,code);
            }
            case "python" -> {
                codePath = absolutePath.resolve("Main.py");
                Files.writeString(codePath,code);
            }
            default -> {
                throw new IllegalArgumentException("Select A Supported Language");
            }
        }

        ProcessBuilder javaCompileBuilder = new ProcessBuilder("javac","Main.java");
        ProcessBuilder javaRunBuilder = new ProcessBuilder("java","Main");
        ProcessBuilder pythonBuilder = new ProcessBuilder("python","Main.py");

        switch (language.toLowerCase()){
            case "java" ->{
                javaCompileBuilder.directory(absolutePath.toFile());
                Process javaCompileProcess = javaCompileBuilder.start();
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(javaCompileProcess.getErrorStream())
                );
                StringBuilder javaCompileError = new StringBuilder();
                String errorReader;
                while((errorReader = bufferedReader.readLine())!=null){
                    javaCompileError.append(errorReader).append("\n");
                }
                if (!javaCompileError.isEmpty()){
                    Files.deleteIfExists(codePath);
                    Files.deleteIfExists(absolutePath.resolve("Main.class"));
                    Files.deleteIfExists(absolutePath);
                    return javaCompileError.toString();
                }
                javaRunBuilder.directory(absolutePath.toFile());
                Process javaRunProcess = javaRunBuilder.start();
                BufferedReader javaRunReader = new BufferedReader(
                        new InputStreamReader(javaRunProcess.getInputStream())
                );
                StringBuilder javaRunRead = new StringBuilder();
                String runReader;
                while((runReader = javaRunReader.readLine())!=null){
                    javaRunRead.append(runReader).append("\n");
                }
                Files.deleteIfExists(codePath);
                Files.deleteIfExists(absolutePath.resolve("Main.class"));
                Files.deleteIfExists(absolutePath);
                status = true;
                return javaRunRead.toString();
            }
            case "python"->{
                pythonBuilder.directory(absolutePath.toFile());
                Process pythonProcess = pythonBuilder.start();
                BufferedReader pythonErrorReader = new BufferedReader(
                        new InputStreamReader(pythonProcess.getErrorStream())
                );
                StringBuilder pythonErrorOutput = new StringBuilder();
                String pythonError;
                while ((pythonError = pythonErrorReader.readLine())!=null){
                    pythonErrorOutput.append(pythonError).append("\n");
                }
                if (!pythonErrorOutput.isEmpty()){
                    Files.deleteIfExists(codePath);
                    Files.deleteIfExists(absolutePath.resolve("Main.py"));
                    Files.deleteIfExists(absolutePath);
                    return pythonErrorOutput.toString();
                }

                BufferedReader pythonOutputReader = new BufferedReader(
                        new InputStreamReader(pythonProcess.getInputStream())
                );
                StringBuilder pythonOutputOutput = new StringBuilder();
                String pythonOutput;
                while((pythonOutput = pythonOutputReader.readLine())!=null){
                    pythonOutputOutput.append(pythonOutput).append("\n");
                }
                if (!pythonOutputOutput.isEmpty()){
                    Files.deleteIfExists(codePath);
                    Files.deleteIfExists(absolutePath.resolve("Main.py"));
                    Files.deleteIfExists(absolutePath);
                    status = true;
                    return pythonOutputOutput.toString();
                }
            }
            default -> {
               throw new IllegalArgumentException("Select A Supported Language");
            }
        }
            return null;
    }
}
