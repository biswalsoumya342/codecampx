package com.codecampx.codecampx.service;

import com.codecampx.codecampx.payload.executionhistory.ExecutionInputDto;
import com.codecampx.codecampx.payload.executionhistory.ExecutionOutputDto;

import java.io.IOException;

public interface ExecutionService {
    public ExecutionOutputDto executeCode(ExecutionInputDto inputDto) throws IOException;
}
