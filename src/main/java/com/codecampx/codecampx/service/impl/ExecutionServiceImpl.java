package com.codecampx.codecampx.service.impl;

import com.codecampx.codecampx.exception.ResorceNotFoundException;
import com.codecampx.codecampx.executor.CodeExecuter;
import com.codecampx.codecampx.model.AppUser;
import com.codecampx.codecampx.model.ExecutionHistory;
import com.codecampx.codecampx.payload.executionhistory.ExecutionInputDto;
import com.codecampx.codecampx.payload.executionhistory.ExecutionOutputDto;
import com.codecampx.codecampx.repository.AppUserRepository;
import com.codecampx.codecampx.repository.ExecutionHistoryRepository;
import com.codecampx.codecampx.service.ExecutionService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Service
public class ExecutionServiceImpl implements ExecutionService {

    private static final Logger logger = LoggerFactory.getLogger(ExecutionServiceImpl.class);

    private final CodeExecuter executer;
    private final AppUserRepository userRepo;
    private final ExecutionHistoryRepository historyRepo;

    public ExecutionServiceImpl(CodeExecuter executer, AppUserRepository userRepo, ExecutionHistoryRepository historyRepo) {
        this.executer = executer;
        this.userRepo = userRepo;
        this.historyRepo = historyRepo;
    }


    @Override
    public ExecutionOutputDto executeCode(ExecutionInputDto inputDto) throws IOException {

        logger.debug("User Request Hit To ExecutionService");

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser user = userRepo.findByUserName(userName).orElseThrow(
                ()-> new ResorceNotFoundException(
                        "User","UserName",userName
                )
        );
        logger.debug("Execution Started and CodeExecutor Called");
        ExecutionOutputDto outputDto = executer.executeCode(inputDto);
        ExecutionHistory executionHistory = new ExecutionHistory();
        logger.debug("Execution Successfully Executed");
        executionHistory.setLanguage(inputDto.getLanguage());
        executionHistory.setCode(inputDto.getCode());
        executionHistory.setOutput(outputDto.getOutput());
        executionHistory.setStatus(outputDto.isStatus());
        executionHistory.setCreatedAt(LocalDateTime.now());
        executionHistory.setUser(user);
        historyRepo.save(executionHistory);
        logger.debug("Execution Detail Saved On DataBase");
        return outputDto;
    }
}
