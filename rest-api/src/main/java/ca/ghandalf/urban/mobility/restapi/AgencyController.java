package ca.ghandalf.urban.mobility.restapi;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ca.ghandalf.urban.mobility.domain.Agency;
import ca.ghandalf.urban.mobility.service.AgencyService;

/**
 * 
 * @author ghandalf
 *
 */
@RestController
@RequestMapping(path = {"/agency"})
@CrossOrigin(origins = { "http://localhost:8020" })
public class AgencyController {

	private static final Logger logger = LoggerFactory.getLogger(AgencyController.class);
	
	@Autowired
	private AgencyService service;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody List<Agency> findAll() {
		if (service.findAll().isPresent()) {
			return service.findAll().get();
		} else {
			// Manage error in here
			logger.debug("No data...");
			return null; // For now
		}
		
	}
}
