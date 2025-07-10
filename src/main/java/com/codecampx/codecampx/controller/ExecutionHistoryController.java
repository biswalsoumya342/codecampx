package com.codecampx.codecampx.controller;

import com.codecampx.codecampx.payload.ApiErrorResponse;
import com.codecampx.codecampx.payload.ApiResponse;
import com.codecampx.codecampx.payload.codesnippet.CodeSnippetDescriptionInputDto;
import com.codecampx.codecampx.payload.executionhistory.ExecutionHistoryDto;
import com.codecampx.codecampx.service.ExecutionHistoryService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/history")
public class ExecutionHistoryController {

    private ExecutionHistoryService service;

    public ExecutionHistoryController(ExecutionHistoryService service) {
        this.service = service;
    }

    @GetMapping("/show")
    public ResponseEntity<?> showHistory(){
        List<ExecutionHistoryDto> history = service.showHistory();
        return new ResponseEntity<>(
                history, HttpStatus.OK
        );
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<?> showHistory(@PathVariable String id){
        ExecutionHistoryDto history = service.showHistory(id);
        return new ResponseEntity<>(
                history,HttpStatus.OK
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteHistory(@PathVariable String id){
        service.deleteHistory(id);
        return new ResponseEntity<>(
                new ApiErrorResponse(
                        LocalDateTime.now(),"History Delete Successful",HttpStatus.OK.value()
                ),HttpStatus.OK
        );
    }


    @PostMapping("/save/{id}")
    public ResponseEntity<?> saveToSnippet(HttpServletRequest request, @PathVariable String id, @RequestBody CodeSnippetDescriptionInputDto inputDto){
        service.saveToSnippet(request,id,inputDto);
        return new ResponseEntity<>(
                new ApiResponse(
                        LocalDateTime.now(),
                        "Code Saved To Your Library",
                        HttpStatus.OK.value()
                ),HttpStatus.OK
        );
    }

}
