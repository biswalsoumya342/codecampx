package com.codecampx.codecampx.service;

import com.codecampx.codecampx.payload.codesnippet.CodeSnippetDto;
import com.codecampx.codecampx.payload.codesnippet.CodeSnippetInputDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface CodeSnippetService {
    public void saveToCodeSnippet(HttpServletRequest request, CodeSnippetInputDto snippetDto);
    public List<CodeSnippetDto> showCodeSnippet();
    public Object showCodeSnippet(String id);
    public boolean setAccess(String id);
    public void removeFromCodeSnippet(String id);
}
