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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
@CrossOrigin(origins = { "http://localhost:8020" }, 
	methods = { RequestMethod.DELETE, RequestMethod.GET, RequestMethod.HEAD, RequestMethod.OPTIONS, 
			RequestMethod.POST, RequestMethod.PUT, RequestMethod.TRACE })
public class AgencyController {

	private static final Logger logger = LoggerFactory.getLogger(AgencyController.class);
	
	@Autowired
	private AgencyService service;
	
	
	/**
	 * Information: If the data doesn't exist
	 * Return in the Body: using postman Get: http://localhost:8020/agency/agencies
	 * {
	 *     "timestamp": "2018-10-20T14:06:20.519+0000",
	 *     "message": "could not extract ResultSet; SQL [n/a]; nested exception is org.hibernate.exception.SQLGrammarException: could not extract ResultSet",
	 *     "details": "uri=/agency/agencies"
	 * }
	 * I think that it come from the ResponseStatus annotations
	 * 
	 * Hibernate interprets fareUrl as fare_url, so I change in createTables fareUrl to fareurl
	 * 
	 * Postman, you need to add in Headers:
	 *  Content-Type: application/json					-- Mandatory
	 * 
	 * 
	 * @return
	 */
	@GetMapping(path = "/agencies", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Agency> findAll() {
		// Fixme add the exception handler already created for Agency
		if (service.findAll().isPresent()) {
			return service.findAll().get();
		} else {
			// Manage error in here
			logger.debug("No data...");
			return null; // For now
		}
		
	}

	/**
	 * I had always [http 403 forbidden] or in the body [Invalid CORS request] using Postman.
	 * To fix it in Postman you need to add in Headers:
	 * Content-Type: application/json					-- Mandatory
	 * Origin: http://localhost:8020					-- Mandatory
	 * Access-Control-Request-Method: POST				-- Mandatory
	 * Access-Control-Request-Headers: Content-Type		-- Optional
	 * 
	 * @param entity
	 * @return
	 */
	@PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Agency create(@RequestBody @Valid Agency entity) {
		if ( service.create(entity).isPresent() ) {
			return service.create(entity).get();
		} else {
			// Error management
			logger.debug("Unable to create the agency");
			return null;
		}
	}
	
	/**
	 * http://localhost:8020/agency/read/76ed93a9-f1b4-4ea8-a402-e1a36e286451
	 * 
	 * Postman, you need to add in Headers:
	 *  Content-Type: application/json					-- Mandatory
	 * 
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(path = "/read/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Agency read(@PathVariable(name = "id", required = true) UUID id ) {
		if ( service.read(id).isPresent() ) {
			return service.read(id).get();
		} else {
			logger.debug("The agency identifier don't exist.");
			return null;
		}
	}
	
	@GetMapping(path = "/read", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Agency read2(@RequestParam(name = "id", required = true) UUID id ) {
		if ( service.read(id).isPresent() ) {
			return service.read(id).get();
		} else {
			logger.debug("The agency identifier don't exist.");
			return null;
		}
	}

	/**
	 * Like Post or method create
	 * I had always [http 403 forbidden] or in the body [Invalid CORS request] using Postman.
	 * To fix it in Postman you need to add in Headers:
	 * Content-Type: application/json					-- Mandatory
	 * Origin: http://localhost:8020					-- Mandatory
	 * Access-Control-Request-Method: PUT				-- Mandatory
	 * 
	 * @param entity
	 * @return
	 */
	@PutMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Agency update(@RequestBody @Valid Agency entity) {
		if ( service.update(entity).isPresent() ) {
			return service.update(entity).get();
		} else {
			logger.debug("Unable to update the entity.");
			return null;
		}
	}
	
	/**
	 * Like Post (create(...)) or Put (update(...)
	 * I had always [http 403 forbidden] or in the body [Invalid CORS request] using Postman.
	 * To fix it in Postman you need to add in Headers:
	 * Content-Type: application/json					-- Mandatory
	 * Origin: http://localhost:8020					-- Mandatory
	 * Access-Control-Request-Method: DELETE			-- Mandatory
	 * 
	 * @param entity
	 */
	@DeleteMapping(path = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void delete(@RequestBody @Valid Agency entity) {
		service.delete(entity);
	}
	
	/**
	 * http://localhost:8020/agency/find/STM
	 * 
	 * like Get (read)
	 * Postman, you need to add in Headers:
	 *  Content-Type: application/json					-- Mandatory
	 * 
	 * @param agencyId
	 * @return
	 */
	@GetMapping(path = "/find/{agencyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Agency findByAgencyId(@PathVariable(name = "agencyId", required = true) String agencyId) {
		if ( service.findByAgencyId(agencyId).isPresent() ) {
			return service.findByAgencyId(agencyId).get();
		} else {
			logger.debug("This agencyId: [{}] doesn't exist.", agencyId);
			return null;
		}
	}
	
}
