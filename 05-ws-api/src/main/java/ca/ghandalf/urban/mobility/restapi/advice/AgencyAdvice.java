package ca.ghandalf.urban.mobility.restapi.advice;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import ca.ghandalf.urban.mobility.restapi.error.AgencyError;
import ca.ghandalf.urban.mobility.restapi.exception.AgencyException;

@RestControllerAdvice
public class AgencyAdvice extends ResponseEntityExceptionHandler {

	@ResponseBody
	@ExceptionHandler(AgencyException.class)
	public ResponseEntity<AgencyError> handleExceptions(AgencyException exception, WebRequest request) {
		
		return new ResponseEntity<AgencyError>(new AgencyError(
				new Date(), exception.getMessage(), request.getDescription(false)), 
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
