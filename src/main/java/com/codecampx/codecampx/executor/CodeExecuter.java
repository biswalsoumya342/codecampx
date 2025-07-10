package com.codecampx.codecampx.executor;

import com.codecampx.codecampx.exception.IllegalInputException;
import com.codecampx.codecampx.payload.executionhistory.ExecutionInputDto;
import com.codecampx.codecampx.payload.executionhistory.ExecutionOutputDto;
import com.codecampx.codecampx.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;

@Slf4j
@Component
public class CodeExecuter {

    private static final Logger logger = LoggerFactory.getLogger(CodeExecuter.class);

    public ExecutionOutputDto executeCode(ExecutionInputDto inputDto) throws IOException {

        logger.debug("Request Reach To CodeExecuter");

        String language = inputDto.getLanguage();
        String code = inputDto.getCode();
        String input = inputDto.getInput();

        Executer executer;
        logger.debug("Try To create New Temporary Directory");
        Path directory = FileUtil.createTempDirectory();
        logger.debug("New Temp Directory Created Successfully");
        logger.debug("Checking User Selected Language For Compilation");
        switch (language.toLowerCase()){
            case "java" ->{
                logger.debug("User Selected Java For Compilation");
                executer = new JavaCodeExecuter();
            }
            case "python" ->{
                logger.debug("User Selected Python For Compilation");
                executer = new PythonCodeExecuter();
            }
            default -> {
                logger.debug("User Selected UnSupported Language");
                throw new IllegalInputException(
                        language.toLowerCase() + " Language Not Supported"
                );
            }
        }
        logger.debug("Code Execution Started Here And Request Send To {} Executer",language);
        return executer.execute(directory,code,input);
    }
}
