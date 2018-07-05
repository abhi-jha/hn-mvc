package com.jha.abhishek.hackernews.exceptionhandling;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jha.abhishek.hackernews.error.errorreport.ErrorReport;

@ControllerAdvice
public class GlobalExceptionHandler {

	private final static String ERRORS = "errors";

	private ErrorReport reportError;

	@Autowired
	public void setReportError(ErrorReport reportError) {
		this.reportError = reportError;
	}

	private Map<String, Object> createErrorResponse(Errors errors) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> errorResults = new HashMap<String, String>();
		for (ObjectError error : errors.getAllErrors()) {
			errorResults.put(error.getCode(), error.getDefaultMessage());
		}
		result.put(ERRORS, errorResults);
		return result;
	}

	private ErrorMessageHolder createErrorResponse(NonCriticalException exception) {
		ErrorMessageHolder errorHolder = new ErrorMessageHolder();
		if (exception.getServiceCode() != null && !exception.getServiceCode().isEmpty()) {
			errorHolder.setServiceCode(exception.getServiceCode());
		}
		if (exception.getErrorCode() != null && !exception.getErrorCode().isEmpty()) {
			errorHolder.setErrorCode(exception.getErrorCode());
		}
		if (exception.getMessage() != null && !exception.getMessage().isEmpty()) {
			errorHolder.setMessage(exception.getMessage());
		}
		return errorHolder;
	}

	private ErrorMessageHolder createErrorResponse(CriticalException exception) {
		ErrorMessageHolder errorHolder = new ErrorMessageHolder();
		if (exception.getServiceCode() != null && !exception.getServiceCode().isEmpty()) {
			errorHolder.setServiceCode(exception.getServiceCode());
		}
		if (exception.getMessage() != null && !exception.getMessage().isEmpty()) {
			errorHolder.setMessage("Something unexpected happened. We surely would be looking into the same");
		}
		return errorHolder;
	}


	@ExceptionHandler(NoSuchElementException.class)
	@ResponseBody
	public ResponseEntity<?> handleNoSuchElementException(NoSuchElementException exception) {
		//Map<String, Object> result = createErrorResponse(exception.g);
		//return new ResponseEntity<Map<String, Object>>(result, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>("no such element", HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(BadRequestException.class)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> handleBadRequestException(BadRequestException exception) {
		Map<String, Object> result = createErrorResponse(exception.getErrors());
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NonCriticalException.class)
	@ResponseBody
	public ResponseEntity<ErrorMessageHolder> handleNonCriticalException(NonCriticalException exception) {
		ErrorMessageHolder holder = createErrorResponse(exception);
		return new ResponseEntity<ErrorMessageHolder>(holder, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CriticalException.class)
	@ResponseBody
	public ResponseEntity<ErrorMessageHolder> handleCriticalException(CriticalException exception) {
		ErrorMessageHolder holder = createErrorResponse(exception);
		reportError.reportError(exception);
		return new ResponseEntity<ErrorMessageHolder>(holder, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}

class ErrorMessageHolder {
	String serviceCode;
	String errorCode;
	private String message;

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
