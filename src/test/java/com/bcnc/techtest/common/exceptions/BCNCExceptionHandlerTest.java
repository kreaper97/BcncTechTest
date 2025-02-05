package com.bcnc.techtest.common.exceptions;

import com.bcnc.techtest.common.ErrorConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BCNCExceptionHandlerTest {

    private BCNCExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new BCNCExceptionHandler();
    }

    @Test
    void handlePriceNotFoundException_ShouldReturnNotFound() {
    	
        PriceNotFoundException exception = new PriceNotFoundException();     
        WebRequest request = mock(WebRequest.class);
        
        ResponseEntity<ErrorDetailsDTO> response = exceptionHandler.handlePriceNotFoundException(exception, request);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ErrorConstants.ERROR_NOT_FOUND, response.getBody().getError());
        assertEquals(ErrorConstants.ERROR_PRICE_NOT_FOUND, response.getBody().getMessage());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    void handleIllegalArgumentException_ShouldReturnBadRequest() {
        
        IllegalArgumentException exception = new IllegalArgumentException(ErrorConstants.ERROR_PARAM_NULL);
        WebRequest request = mock(WebRequest.class);

        ResponseEntity<ErrorDetailsDTO> response = exceptionHandler.handleIllegalArgumentException(exception, request);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ErrorConstants.ERROR_BAD_REQUEST, response.getBody().getError());
        assertEquals(ErrorConstants.ERROR_PARAM_NULL, response.getBody().getMessage());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    void handleMissingParams_ShouldReturnBadRequest() {

    	MissingServletRequestParameterException exception = 
            new MissingServletRequestParameterException("Required request parameter 'applicationDate' for method parameter type LocalDateTime is present but converted to null", "String");

        ResponseEntity<ErrorDetailsDTO> response = exceptionHandler.handleMissingParams(exception);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ErrorConstants.ERROR_BAD_REQUEST, response.getBody().getError());
        assertTrue(response.getBody().getMessage().contains(ErrorConstants.ERROR_REQUIRED_PARAM));
        assertNotNull(response.getBody().getTimestamp());
    }
}
