package com.Inventory_Management_API.Inventory_API.Exception;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.Inventory_Management_API.Inventory_API.dto.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
        
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = ex.getMessage();

        if (ex instanceof CategoryNotFoundException || ex instanceof ProductNotFoundException) {
            status = HttpStatus.NOT_FOUND;
        } 
        else if (ex instanceof DuplicateNameException) {
            status = HttpStatus.CONFLICT;
        } 
        else if (ex instanceof MethodArgumentNotValidException validationEx) {
            status = HttpStatus.BAD_REQUEST;
            StringBuilder sb = new StringBuilder();
            List<FieldError> errors = validationEx.getBindingResult().getFieldErrors();
            for (FieldError error : errors) {
                sb.append(error.getField());
                sb.append(": ");
                sb.append(error.getDefaultMessage());
                sb.append(", ");
            }

            message = sb.toString();
            if (message.length() > 0) {
                message = message.substring(0, message.length() - 2);
            }
        }

        ErrorResponse error = new ErrorResponse(LocalDateTime.now(),message,request.getDescription(false).replace("uri=", "")
        		,status.value());
        return new ResponseEntity<>(error, status);
    }
}