package com.onlineshop.orderservice.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomizedExceptionHandling extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleProductException(ProductNotFoundException exception) {

		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setStatuserrorCode(HttpStatus.NOT_FOUND.toString());

		errorResponse.setMessage(exception.getMessage());
		errorResponse.setTimeStamp(System.currentTimeMillis());
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(OrderCustomException.class)
	public ResponseEntity<ErrorResponse> handleCustomException(OrderCustomException exception) {

		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setStatuserrorCode(HttpStatus.NOT_FOUND.toString());

		errorResponse.setMessage(exception.getMessage());
		errorResponse.setTimeStamp(System.currentTimeMillis());
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleidNotFoundException(IdNotFoundException idNotFoundException) {

		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setStatuserrorCode(HttpStatus.NOT_FOUND.toString());

		errorResponse.setMessage(idNotFoundException.getMessage());
		errorResponse.setTimeStamp(System.currentTimeMillis());
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ProductCustomException.class)
	public ResponseEntity<ErrorResponse> handleIProductCustomException(ProductCustomException productCustomException) {

		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setStatuserrorCode(HttpStatus.NOT_FOUND.toString());

		errorResponse.setMessage(productCustomException.getMessage());
		errorResponse.setTimeStamp(System.currentTimeMillis());
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<ErrorResponse> handleIProductCustomException(Exception exception) {

		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setStatuserrorCode(HttpStatus.BAD_REQUEST.toString());

		errorResponse.setMessage(exception.getMessage());
		errorResponse.setTimeStamp(System.currentTimeMillis());
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleIUserNotFoundException(ProductCustomException userNotFoundException) {

		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setStatuserrorCode(HttpStatus.NOT_FOUND.toString());

		errorResponse.setMessage(userNotFoundException.getMessage());
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
