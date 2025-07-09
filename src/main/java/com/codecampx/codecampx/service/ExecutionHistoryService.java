package com.codecampx.codecampx.service;

import com.codecampx.codecampx.payload.executionhistory.ExecutionHistoryDto;

public interface ExecutionHistoryService {
    public ExecutionHistoryDto showHistory();
    public boolean deleteHistory();
}
