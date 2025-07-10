package com.codecampx.codecampx.repository;

import com.codecampx.codecampx.model.CodeSnippet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CodeSnippetRepository extends JpaRepository<CodeSnippet,String> {
    public List<CodeSnippet> findByUser_UserName(String username);
}
