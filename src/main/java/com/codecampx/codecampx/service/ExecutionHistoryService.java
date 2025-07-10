package com.codecampx.codecampx.service;

import com.codecampx.codecampx.payload.codesnippet.CodeSnippetDescriptionInputDto;
import com.codecampx.codecampx.payload.executionhistory.ExecutionHistoryDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface ExecutionHistoryService {
    public List<ExecutionHistoryDto> showHistory();
    public void deleteHistory(String id);
    public ExecutionHistoryDto showHistory(String id);
    public void saveToSnippet(HttpServletRequest request, String id, CodeSnippetDescriptionInputDto inputDto);
}
