package com.codecampx.codecampx.controller;

import com.codecampx.codecampx.payload.ApiResponse;
import com.codecampx.codecampx.payload.AppUserDto;
import com.codecampx.codecampx.payload.LoginDto;
import com.codecampx.codecampx.service.AppUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class AppUserController {

    private AppUserService service;

    public AppUserController(AppUserService service) {
        this.service = service;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody AppUserDto userDto){
        service.signup(userDto);
        return new ResponseEntity<>(
                new ApiResponse(LocalDateTime.now(),"SignUp Successful",HttpStatus.CREATED.value()),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto){
        String token = service.login(loginDto);
        return new ResponseEntity<>(
                token,HttpStatus.OK
        );
    }

    @GetMapping("/test")
    public String test(){
        return "JWT Working Fine!";
    }
}
