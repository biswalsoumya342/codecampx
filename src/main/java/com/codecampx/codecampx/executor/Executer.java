package com.codecampx.codecampx.executor;

import com.codecampx.codecampx.payload.executionhistory.ExecutionOutputDto;

import java.io.IOException;
import java.nio.file.Path;

public interface Executer {
    public ExecutionOutputDto execute(Path directory,String code,String input) throws IOException;
}
