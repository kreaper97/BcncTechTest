package com.bcnc.techtest.common;

public class ErrorConstants{
	
	private ErrorConstants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
	
	public static final String ERROR_PARAM_NULL= "Application Date, Product ID and Brand ID cannot be null";
	public static final String ERROR_REQUIRED_PARAM = "Required request parameter";
	public static final String ERROR_PRICE_NOT_FOUND= "Price not found";
	public static final String ERROR_NOT_FOUND= "Not Found";
	public static final String ERROR_BAD_REQUEST= "Bad Request";
}