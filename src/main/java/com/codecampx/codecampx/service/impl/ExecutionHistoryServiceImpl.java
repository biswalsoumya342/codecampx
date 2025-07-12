package com.codecampx.codecampx.service.impl;

import com.codecampx.codecampx.exception.ResorceNotFoundException;
import com.codecampx.codecampx.model.AppUser;
import com.codecampx.codecampx.model.CodeSnippet;
import com.codecampx.codecampx.model.ExecutionHistory;
import com.codecampx.codecampx.payload.codesnippet.CodeSnippetDescriptionInputDto;
import com.codecampx.codecampx.payload.executionhistory.ExecutionHistoryDto;
import com.codecampx.codecampx.repository.AppUserRepository;
import com.codecampx.codecampx.repository.CodeSnippetRepository;
import com.codecampx.codecampx.repository.ExecutionHistoryRepository;
import com.codecampx.codecampx.service.ExecutionHistoryService;
import com.codecampx.codecampx.util.SecurityUtil;
import com.codecampx.codecampx.util.UrlUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ExecutionHistoryServiceImpl implements ExecutionHistoryService {

   private final ExecutionHistoryRepository historyRepository;
   private final ModelMapper mapper;
   private final AppUserRepository userRepository;
   private final CodeSnippetRepository snippetRepository;

    public ExecutionHistoryServiceImpl(ExecutionHistoryRepository historyRepository, ModelMapper mapper, AppUserRepository userRepository, CodeSnippetRepository snippetRepository) {
        this.historyRepository = historyRepository;
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.snippetRepository = snippetRepository;
    }


    @Override
    public List<ExecutionHistoryDto> showHistory() {
        String userName = SecurityUtil.extractUserName();
        List<ExecutionHistory> history = historyRepository.findByUser_UserName(userName);
         return history.stream()
                 .map(l->mapper.map(l,ExecutionHistoryDto.class))
                 .toList();

    }


    @Override
    public void deleteHistory(String id) {
        String userName = SecurityUtil.extractUserName();
        ExecutionHistory history = historyRepository.findById(id)
                .orElseThrow(()->new ResorceNotFoundException("Execution History","",""));
        historyRepository.deleteById(history.getId());
    }

    @Override
    public ExecutionHistoryDto showHistory(String id) {
        ExecutionHistory history = historyRepository.findById(id)
                .orElseThrow(()->new ResorceNotFoundException("Execution History","",""));
        return mapper.map(history,ExecutionHistoryDto.class);
    }

    @Override
    public void saveToSnippet(HttpServletRequest request, String id, CodeSnippetDescriptionInputDto inputDto) {
        String uuid = UUID.randomUUID().toString();
        String userName = SecurityUtil.extractUserName();
        AppUser user = userRepository
                .findByUserName(userName)
                .orElseThrow(
                        ()->new ResorceNotFoundException(
                                "User","UserName",userName
                        )
                );
        ExecutionHistory history =historyRepository
                .findById(id)
                .orElseThrow(
                        ()->new ResorceNotFoundException("Execution History","","")
                );

        CodeSnippet snippet = new CodeSnippet();
        snippet.setId(uuid);
        snippet.setLanguage(history.getLanguage());
        snippet.setCode(history.getCode());
        snippet.setDescription(inputDto.getDescription());
        snippet.setShared(false);
        snippet.setCreatedAt(LocalDateTime.now());
        snippet.setShareLink(UrlUtil.getBaseUrl(request)+"/api/snippet/show/"+uuid);
        snippet.setUser(user);
        snippetRepository.save(snippet);
    }
}
