package com.bcnc.techtest.infraestructure.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.bcnc.techtest.constants.ErrorConstants;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PriceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PriceNotFoundException() {
        super(ErrorConstants.ERROR_PRICE_NOT_FOUND);
    }
}
