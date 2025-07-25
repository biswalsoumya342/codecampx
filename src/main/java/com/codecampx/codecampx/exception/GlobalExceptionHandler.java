package com.codecampx.codecampx.exception;

import com.codecampx.codecampx.payload.ApiErrorResponse;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ResorceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleResourceNotFoundException(ResorceNotFoundException ex){
        return new ResponseEntity<>(
                new ApiErrorResponse(
                        LocalDateTime.now(),
                        ex.getMessage(),
                        HttpStatus.NOT_FOUND.value()
                ), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = DuplicateResourceEntryException.class)
    public ResponseEntity<ApiErrorResponse> handelDuplicateResourceEntryException(DuplicateResourceEntryException ex){
        return new ResponseEntity<>(
                new ApiErrorResponse(
                        LocalDateTime.now(),
                        ex.getMessage(),
                        HttpStatus.CONFLICT.value()
                ), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = UnauthorizeAccessException.class)
    public ResponseEntity<?> handleUnauthorizeAccessException(UnauthorizeAccessException ex){
        return new ResponseEntity<>(
                new ApiErrorResponse(
                        LocalDateTime.now()
                        ,ex.getMessage(),
                        HttpStatus.FORBIDDEN.value()
                ),HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(value = InvalidPasswordException.class)
    public ResponseEntity<?> handleInvalidPasswordException(InvalidPasswordException ex){
        return new ResponseEntity<>(
                new ApiErrorResponse(
                        LocalDateTime.now(),
                        ex.getMessage(),
                        HttpStatus.UNAUTHORIZED.value()
                ),HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(value = IllegalInputException.class)
    public ResponseEntity<?> handleIllegalInputException(IllegalInputException ex){
        return new ResponseEntity<>(
                new ApiErrorResponse(
                        LocalDateTime.now(),ex.getMessage(),HttpStatus.BAD_REQUEST.value()
                ),
                HttpStatus.BAD_REQUEST
        );
    }


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        Map<String,String> errors = new HashMap<>();
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> errorList = bindingResult.getFieldErrors();
        errorList.forEach(e->errors.put(e.getField(),e.getDefaultMessage()));
        return new ResponseEntity<>(
                errors,HttpStatus.BAD_GATEWAY
        );
    }
}
