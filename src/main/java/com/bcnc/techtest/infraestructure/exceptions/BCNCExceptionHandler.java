package com.bcnc.techtest.infraestructure.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.bcnc.techtest.constants.ErrorConstants;
import com.bcnc.techtest.domain.model.ErrorDetailsDTO;

import java.time.LocalDateTime;

@ControllerAdvice
public class BCNCExceptionHandler {

    @ExceptionHandler(PriceNotFoundException.class)
    public ResponseEntity<?> handlePriceNotFoundException(PriceNotFoundException ex, WebRequest request) {
    	
    	ErrorDetailsDTO errorDetailsDTO = new ErrorDetailsDTO();
    	errorDetailsDTO.setTimestamp(LocalDateTime.now());
    	errorDetailsDTO.setStatus(HttpStatus.NOT_FOUND.value());
    	errorDetailsDTO.setError(ErrorConstants.ERROR_NOT_FOUND);
    	errorDetailsDTO.setMessage(ex.getMessage());

        return new ResponseEntity<>(errorDetailsDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
    	ErrorDetailsDTO errorDetailsDTO = new ErrorDetailsDTO();
    	errorDetailsDTO.setTimestamp(LocalDateTime.now());
    	errorDetailsDTO.setStatus(HttpStatus.BAD_REQUEST.value());
    	errorDetailsDTO.setError(ErrorConstants.ERROR_BAD_REQUEST);
    	errorDetailsDTO.setMessage(ex.getMessage());

        return new ResponseEntity<>(errorDetailsDTO, HttpStatus.BAD_REQUEST);
    }
}
