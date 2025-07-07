package com.codecampx.codecampx.controller;

import com.codecampx.codecampx.payload.ApiErrorResponse;
import com.codecampx.codecampx.payload.ApiResponse;
import com.codecampx.codecampx.payload.appuser.AppUserSignUpDto;
import com.codecampx.codecampx.payload.appuser.LoginDto;
import com.codecampx.codecampx.service.AppUserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(AppUserController.class);

    private final AppUserService service;

    public LoginController(AppUserService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto){
        logger.info("User Hits The Login Endpoint With UserName: {}",loginDto.getUserName());
        String token = service.login(loginDto);
        logger.debug("Token Send To User: {}",loginDto.getUserName());
        return new ResponseEntity<>(
                token, HttpStatus.OK
        );
    }

    //
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody AppUserSignUpDto userDto){
        boolean status = service.signup(userDto);
        if (status){
            logger.info("User Signup Successful with UserName: {} Email: {}",userDto.getUserName(),userDto.getEmail());
            return new ResponseEntity<>(
                    new ApiResponse(LocalDateTime.now(),"SignUp Successful",HttpStatus.CREATED.value()),
                    HttpStatus.CREATED

            );
        }
        else{
            logger.error("User Signup UnSuccessful with UserName: {} Email: {}",userDto.getUserName(),userDto.getEmail());
            return new ResponseEntity<>(
                    new ApiErrorResponse(LocalDateTime.now(),"Signup Unsuccessful Due To Unexpected Error!",HttpStatus.INTERNAL_SERVER_ERROR.value()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
