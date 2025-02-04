package com.bcnc.techtest.common.exceptions;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class ErrorDetailsDTO {
	private LocalDateTime timestamp;
	private int status;
	private String error;
	private String message;
}