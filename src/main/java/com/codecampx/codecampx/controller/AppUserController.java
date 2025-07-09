package com.codecampx.codecampx.controller;

import com.codecampx.codecampx.payload.ApiErrorResponse;
import com.codecampx.codecampx.payload.ApiResponse;
import com.codecampx.codecampx.payload.PasswordReset;
import com.codecampx.codecampx.payload.appuser.AppUserUpdateDto;
import com.codecampx.codecampx.service.AppUserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody AppUserUpdateDto userDto){
        boolean status = service.update(userDto);
        if (status){
            logger.info("Update Successful For User: {}",userDto.getUserName());
            return new ResponseEntity<>(
                    new ApiResponse(
                            LocalDateTime.now(),"Update Successful",HttpStatus.OK.value()
                    ),HttpStatus.OK
            );
        }else {
            logger.info("Update Failed For User: {}",userDto.getUserName());
            return new ResponseEntity<>(
                    new ApiErrorResponse(
                    LocalDateTime.now(),"Update Failed",HttpStatus.INTERNAL_SERVER_ERROR.value()
            ),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Delete User
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(){
        boolean status = service.delete();
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

    //Password Reset
    @PostMapping("/reset-password")
    public ResponseEntity<?> passwordReset(@RequestBody PasswordReset password){
        logger.info("User Hit Password Reset End point, userName");
        boolean status = service.passwordReset(password);
        if (status){
            logger.debug("User Password Changed ! , UserName ");
            return new ResponseEntity<>(
                    new ApiResponse(
                            LocalDateTime.now(),"Password Changed Successful",HttpStatus.OK.value()
                    ), HttpStatus.OK
            );
        }else {
            logger.debug("User Password Change Failed!");
            return new ResponseEntity<>(
                    new ApiErrorResponse(
                            LocalDateTime.now(),"Password Change Failed!",HttpStatus.UNAUTHORIZED.value()
                    ),HttpStatus.UNAUTHORIZED
            );
        }
    }

    @GetMapping("/test")
    public String test(){
        return "JWT Working Fine!";
    }
}
