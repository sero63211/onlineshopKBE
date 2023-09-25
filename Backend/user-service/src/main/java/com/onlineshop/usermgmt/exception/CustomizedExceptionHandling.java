package com.onlineshop.usermgmt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomizedExceptionHandling extends ResponseEntityExceptionHandler {

	@ExceptionHandler(UserMgmtException.class)
	public ResponseEntity<ErrorResponse> handleProductException(UserMgmtException exception) {

		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setStatuserrorCode(HttpStatus.BAD_REQUEST.toString());

		errorResponse.setMessage(exception.getMessage());
		errorResponse.setTimeStamp(System.currentTimeMillis());
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
	}

//	@ExceptionHandler({ MethodArgumentNotValidException.class, HttpMessageNotReadableException.class })
//	public ResponseEntity<Object> handleBindingErrors(MethodArgumentNotValidException ex) {
//		List<String> errorList = ex.getBindingResult().getFieldErrors().stream()
//				.map(fieldError -> fieldError.getDefaultMessage()).collect(Collectors.toList());
//		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), errorList,
//				System.currentTimeMillis());
//		return new ResponseEntity<Object>(errorResponse, HttpStatus.BAD_REQUEST);
//	}

}
