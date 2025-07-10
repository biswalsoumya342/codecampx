package com.codecampx.codecampx.controller;

import com.codecampx.codecampx.payload.ApiResponse;
import com.codecampx.codecampx.payload.codesnippet.CodeSnippetDto;
import com.codecampx.codecampx.payload.codesnippet.CodeSnippetInputDto;
import com.codecampx.codecampx.service.CodeSnippetService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/snippet")
public class CodeSnippetController {

    private CodeSnippetService service;

    public CodeSnippetController(CodeSnippetService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveToSnippet(HttpServletRequest request, @RequestBody CodeSnippetInputDto inputDto){
        service.saveToCodeSnippet(request,inputDto);
        return new ResponseEntity<>(
                new ApiResponse(
                        LocalDateTime.now(),"Code Snippet Saved Successfully", HttpStatus.OK.value()
                ),HttpStatus.OK
        );
    }


    @GetMapping("/show")
    public ResponseEntity<?> showCodeSnippet(){
        List<CodeSnippetDto> snippetDto = service.showCodeSnippet();
        return new ResponseEntity<>(
                snippetDto,HttpStatus.OK
        );
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<?> showCodeSnippet(@PathVariable String id){
        CodeSnippetDto snippetDto = service.showCodeSnippet(id);
        return new ResponseEntity<>(
                snippetDto,HttpStatus.OK
        );
    }

    @PutMapping("/set-access/{id}")
    public ResponseEntity<?> setAccess(@PathVariable String id){
        boolean status = service.setAccess(id);
        if (status){
            return new ResponseEntity<>(
                    new ApiResponse(
                            LocalDateTime.now(),
                            "Code Set To Public",
                            HttpStatus.OK.value()
                    ),HttpStatus.OK
            );
        }else {
            return new ResponseEntity<>(
                    new ApiResponse(
                            LocalDateTime.now(),
                            "Code Set To Private",
                            HttpStatus.OK.value()
                    ),HttpStatus.OK
            );
        }
    }
}
