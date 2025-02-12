package com.naitech.cursomc.controllers.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.naitech.cursomc.services.exceptions.AuthorizationException;
import com.naitech.cursomc.services.exceptions.DataIntegrityException;
import com.naitech.cursomc.services.exceptions.ObjectNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
		StandardError standardError = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(),
				System.currentTimeMillis());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standardError);
	}

	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest request) {
		StandardError standardError = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
				System.currentTimeMillis());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> handleValidationExceptions(MethodArgumentNotValidException e) {
		ValidationError validationError = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Validation error",
				System.currentTimeMillis());

		e.getBindingResult().getFieldErrors()
				.forEach(error -> validationError.addError(error.getField(), error.getDefaultMessage()));

		return ResponseEntity.badRequest().body(validationError);
	}

	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<StandardError> authorization(AuthorizationException e, HttpServletRequest request) {
		StandardError standardError = new StandardError(HttpStatus.FORBIDDEN.value(), e.getMessage(),
				System.currentTimeMillis());

		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(standardError);
	}
}
