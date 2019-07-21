package com.apogee.trackarea.exceptions;//package com.example.easyeventsserver.exceptions;
//
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//@ControllerAdvice
//public class ApiExceptionHandler {
//    @ExceptionHandler(ApiException.class)
//    public ResponseEntity<?> handle(ApiException e, HttpServletRequest req, HttpServletResponse res){
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//    }
//}
