package com.project.app;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.project.app.exceptions.InsufficientBalanceException;
import com.project.app.exceptions.InvalidLoginException;

@Controller
@ControllerAdvice
public class ExceptionHandlerControllerAdvice {
	@ExceptionHandler(value= {InvalidLoginException.class,InsufficientBalanceException.class})
	public final ResponseEntity<String> exceptionHandler(Exception e){
		return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
	}
}
