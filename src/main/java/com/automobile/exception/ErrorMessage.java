package com.automobile.exception;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ErrorMessage extends ResponseEntityExceptionHandler  {

	@ExceptionHandler(NotfoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public GetMessage exceptionHandler(NotfoundException exception) {
		return new GetMessage(exception.getMessage());
	}
	
}
