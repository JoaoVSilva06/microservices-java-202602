package br.edu.atitus.currencyapi.components;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<String> handleMissingParameter(MissingServletRequestParameterException e) {
		String message = "Required parameter missing: " + e.getParameterName();
		return ResponseEntity.badRequest().body(message);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception e) {
		String message = e.getMessage() == null ? "Unexpected error" : e.getMessage().replaceAll("[\\r\\n]", "");
		return ResponseEntity.badRequest().body(message);
	}

}
