package com.codecampx.codecampx.controller;

import com.codecampx.codecampx.payload.ApiErrorResponse;
import com.codecampx.codecampx.payload.ApiResponse;
import com.codecampx.codecampx.payload.appuser.AppUserUpdateDto;
import com.codecampx.codecampx.service.AppUserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/account")
public class AppUserController {

    private static final Logger logger = LoggerFactory.getLogger(AppUserController.class);

    private final AppUserService service;

    public AppUserController(AppUserService service) {
        this.service = service;
    }

    //User Update Controller only update userName And Email
    @PutMapping("/update/{userName}")
    public ResponseEntity<?> update(@PathVariable String userName,@RequestBody AppUserUpdateDto userDto){
        boolean status = service.update(userName,userDto);
        if (status){
            logger.info("Update Successful For User: {}",userName);
            return new ResponseEntity<>(
                    new ApiResponse(
                            LocalDateTime.now(),"Update Successful",HttpStatus.OK.value()
                    ),HttpStatus.OK
            );
        }else {
            logger.info("Update Failed For User: {}",userName);
            return new ResponseEntity<>(
                    new ApiErrorResponse(
                    LocalDateTime.now(),"Update Failed",HttpStatus.INTERNAL_SERVER_ERROR.value()
            ),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{userName}")
    public ResponseEntity<?> delete(@PathVariable String userName){
        boolean status = service.delete(userName);
        if (status) return new ResponseEntity<>(
                new ApiResponse(
                        LocalDateTime.now(),"Account Delete Successful", HttpStatus.OK.value()
                ),HttpStatus.OK

        );
        else return new ResponseEntity<>(
                new ApiErrorResponse(
                        LocalDateTime.now(),"Account Delete Failed",HttpStatus.INTERNAL_SERVER_ERROR.value()
                ),HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @GetMapping("/test")
    public String test(){
        return "JWT Working Fine!";
    }
}
