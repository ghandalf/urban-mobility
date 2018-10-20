package ca.ghandalf.urban.mobility.restapi.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ca.ghandalf.urban.mobility.restapi.exception.AgencyNotFoundException;

@ControllerAdvice
public class AgencyNotFoundAdvice {

	@ResponseBody
	@ExceptionHandler(AgencyNotFoundException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public String agencyNotFoundHandler(AgencyNotFoundException exception) {
		return exception.getMessage();
	}
}
