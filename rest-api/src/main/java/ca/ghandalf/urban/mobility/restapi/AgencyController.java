package ca.ghandalf.urban.mobility.restapi;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@GetMapping(path = "/agencies", produces = MediaType.APPLICATION_JSON_VALUE)
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

	@PostMapping(path = "/crateagency")
	public @ResponseBody Agency create(@RequestBody @Valid Agency entity) {
		if ( service.create(entity).isPresent() ) {
			return service.create(entity).get();
		} else {
			// Error management
			logger.debug("Unable to create the agency");
			return null;
		}
	}
	
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Agency read(@PathVariable(name = "id", required = true) UUID id ) {
		if ( service.read(id).isPresent() ) {
			return service.read(id).get();
		} else {
			logger.debug("The agency identifier don't exist.");
			return null;
		}
	}

	@PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Agency update(@RequestBody @Valid Agency entity) {
		if ( service.update(entity).isPresent() ) {
			return service.update(entity).get();
		} else {
			logger.debug("Unable to update the entity.");
			return null;
		}
	}
	
	@DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public void delete(@RequestBody @Valid Agency entity) {
		service.delete(entity);
	}
	
	@GetMapping(path = "/{agencyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Agency findByAgencyId(@PathVariable(name = "agencyId", required = true) String agencyId) {
		if ( service.findByAgencyId(agencyId).isPresent() ) {
			return service.findByAgencyId(agencyId).get();
		} else {
			logger.debug("This agencyId: [{}] doesn't exist.", agencyId);
			return null;
		}
	}
	
}
