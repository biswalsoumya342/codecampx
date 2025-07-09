package com.codecampx.codecampx.controller;

import com.codecampx.codecampx.payload.executionhistory.ExecutionInputDto;
import com.codecampx.codecampx.payload.executionhistory.ExecutionOutputDto;
import com.codecampx.codecampx.service.ExecutionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ExecutionController {

    private final ExecutionService service;

    public ExecutionController(ExecutionService service) {
        this.service = service;
    }

    @PostMapping("/execute")
    public ResponseEntity<?> executeCode(@RequestBody ExecutionInputDto inputDto){
        try {
            ExecutionOutputDto output = service.executeCode(inputDto);
            return new ResponseEntity<>(
                    output, HttpStatus.OK
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
