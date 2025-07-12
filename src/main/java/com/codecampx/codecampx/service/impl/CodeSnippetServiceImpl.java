package com.codecampx.codecampx.service.impl;

import com.codecampx.codecampx.exception.ResorceNotFoundException;
import com.codecampx.codecampx.exception.UnauthorizeAccessException;
import com.codecampx.codecampx.model.AppUser;
import com.codecampx.codecampx.model.CodeSnippet;
import com.codecampx.codecampx.payload.codesnippet.CodeSnippetDto;
import com.codecampx.codecampx.payload.codesnippet.CodeSnippetInputDto;
import com.codecampx.codecampx.payload.codesnippet.CodeSnippetOutputDto;
import com.codecampx.codecampx.repository.AppUserRepository;
import com.codecampx.codecampx.repository.CodeSnippetRepository;
import com.codecampx.codecampx.service.CodeSnippetService;
import com.codecampx.codecampx.util.SecurityUtil;
import com.codecampx.codecampx.util.UrlUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CodeSnippetServiceImpl implements CodeSnippetService {

    private final CodeSnippetRepository repo;
    private final ModelMapper mapper;
    private final AppUserRepository userRepository;

    public CodeSnippetServiceImpl(CodeSnippetRepository repo, ModelMapper mapper, AppUserRepository userRepository) {
        this.repo = repo;
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    @Override
    public void saveToCodeSnippet(HttpServletRequest request, CodeSnippetInputDto snippetDto) {
        String uuid = UUID.randomUUID().toString();
        AppUser user = userRepository.findByUserName(SecurityUtil.extractUserName()).orElseThrow(
                ()-> new ResorceNotFoundException("User","UserName",SecurityUtil.extractUserName())
        );
        CodeSnippet snippet = mapper.map(snippetDto, CodeSnippet.class);
        snippet.setId(uuid);
        snippet.setLanguage(snippetDto.getLanguage());
        snippet.setCode(snippetDto.getCode());
        snippet.setDescription(snippetDto.getDescription());
        snippet.setShared(false);
        snippet.setCreatedAt(LocalDateTime.now());
        snippet.setShareLink(UrlUtil.getBaseUrl(request)+"/api/snippet/show/"+uuid);
        snippet.setUser(user);
        repo.save(snippet);
    }

    @Override
    public List<CodeSnippetDto> showCodeSnippet() {
        return repo.findByUser_UserName(SecurityUtil.extractUserName())
                .stream()
                .map(l-> mapper.map(l,CodeSnippetDto.class))
                .toList();
    }

    @Override
    public Object showCodeSnippet(String id) {
        CodeSnippet snippet = repo.findById(id).orElseThrow(
                ()->new ResorceNotFoundException(
                        "Code Snippet","Id",""
                )
        );
        if (SecurityUtil.extractUserName().equals(snippet.getUser().getUserName())){
            return mapper.map(snippet,CodeSnippetDto.class);
        }else {
            if (snippet.isShared()){
                return mapper.map(snippet,CodeSnippetOutputDto.class);
            }else {
                throw new UnauthorizeAccessException("You Are Not Allowed To Access");
            }
        }
    }

    @Override
    public boolean setAccess(String id) {
        CodeSnippet snippet = repo.findById(id).orElseThrow(
                ()-> new ResorceNotFoundException("Code","Id",id)
        );
        if (snippet.isShared()){
            snippet.setShared(false);
            repo.save(snippet);
            return false;
        }else {
            snippet.setShared(true);
            repo.save(snippet);
            return true;
        }
    }

    @Override
    public void removeFromCodeSnippet(String id) {
        CodeSnippet snippet = repo
                .findById(id)
                .orElseThrow(
                        ()-> new ResorceNotFoundException(
                                "CodeSnippet","Id",id
                        )
                );
        repo.deleteById(id);
    }
}
