package com.wipro.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {

	@ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<?> handlerNotSupported(HttpRequestMethodNotSupportedException e) {
		return new ResponseEntity<>("Please make a valid request", HttpStatus.METHOD_NOT_ALLOWED);
	}

	@ExceptionHandler(value = HttpMessageNotReadableException.class)
	public ResponseEntity<?> handlerNotReadable(HttpMessageNotReadableException e) {
		return new ResponseEntity<>("Please provide emp details", HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = NotFoundException.class)
	public ResponseEntity<?> hadlerNotFound(NotFoundException e) {
		return new ResponseEntity<>("Not Found with given id", HttpStatus.NOT_FOUND);
	}

	// NoDataException
	@ExceptionHandler(value = NoDataException.class)
	public ResponseEntity<?> hadlerNoData(NoDataException e) {
		return new ResponseEntity<>("Empty No data available", HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = TransactionException.class)
	public ResponseEntity<?> hadlerTransaction(TransactionException e) {
		return new ResponseEntity<>("Exception while transaction", HttpStatus.EXPECTATION_FAILED);
	}
	
	@ExceptionHandler(value = InValidAmout.class)
	public ResponseEntity<?> hadlerInValidAmount(InValidAmout e) {
		return new ResponseEntity<>("Enter Valid Amount..!", HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = ResourceNotFoundException.class)
	public ResponseEntity<?> hadlerResourceNotFound(ResourceNotFoundException e) {
		return new ResponseEntity<>("Resouce not found", HttpStatus.NOT_FOUND);
	}
}
