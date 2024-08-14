package com.employeeManagement.leavesService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        Map<String,String> resp = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
            String filedName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            resp.put(filedName,message);
        });
        return new ResponseEntity<Map<String ,String>>(resp, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
        return new ResponseEntity<>("LEAVE NOT FOUND", HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<Object> sqlIntegrityConstraintViolationException(DuplicateUsernameException ex){
        return new ResponseEntity<>("Duplicate Entry For Leaves", HttpStatus.ALREADY_REPORTED);
    }
    @ExceptionHandler(NoLeaveFoundException.class)
    public ResponseEntity<Object> noLeaveFoundExceptionHandler(NoLeaveFoundException ex){
        return new ResponseEntity<>("Please Check Available Leaves", HttpStatus.ALREADY_REPORTED);
    }
    @ExceptionHandler(NoDateFoundException.class)
    public ResponseEntity<Object> NoDateFoundExceptionHandler(NoDateFoundException ex){
        return new ResponseEntity<>("NO DATA FOUND FOR THIS USER", HttpStatus.ALREADY_REPORTED);
    }
    @ExceptionHandler(AlreadyApproveException.class)
    public ResponseEntity<Object> AlreadyApproveExceptionHandler(AlreadyApproveException ex){
        return new ResponseEntity<>("Already Approve Or Reject Exception", HttpStatus.ALREADY_REPORTED);
    }

}
