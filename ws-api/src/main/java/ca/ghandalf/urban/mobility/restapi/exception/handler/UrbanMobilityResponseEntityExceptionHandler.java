package ca.ghandalf.urban.mobility.restapi.exception.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import ca.ghandalf.urban.mobility.restapi.error.UrbanMobilityError;

/**
 * 404 - RESOURCE NOT FOUND
 * 400 - BAD REQUEST
 * 401 - UNAUTHORIZED
 * 415 - UNSUPPORTED TYPE - Representation not supported for the resource
 * 500 - SERVER ERROR
 * 
 * @author ghandalf
 *
 */
@RestControllerAdvice
public class UrbanMobilityResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<UrbanMobilityError> handleExceptions(Exception exception, WebRequest request) {
		UrbanMobilityError currentError = new UrbanMobilityError(new Date(), exception.getMessage(), request.getDescription(false));
		
		return new ResponseEntity<>(currentError, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
