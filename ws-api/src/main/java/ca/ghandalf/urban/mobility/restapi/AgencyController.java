package ca.ghandalf.urban.mobility.restapi;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.ghandalf.urban.mobility.dto.AgencyDTO;
import ca.ghandalf.urban.mobility.service.AgencyService;

/**
 * 
 * TODO Implement logstack: http://callistaenterprise.se/blogg/teknik/2017/09/13/building-microservices-part-8-logging-with-ELK/
 * 
 * @author ghandalf
 *
 */
@RestController
@RequestMapping(path = { "/agency" })
@CrossOrigin(origins = { "http://localhost:8020" }, methods = { RequestMethod.DELETE, RequestMethod.GET,
		RequestMethod.HEAD, RequestMethod.OPTIONS, RequestMethod.POST, RequestMethod.PUT, RequestMethod.TRACE })
public class AgencyController {

	private static final Logger logger = LoggerFactory.getLogger(AgencyController.class);

	@Autowired
	private AgencyService service;
	
	/**
	 * Information: If the data doesn't exist Return in the Body: using postman Get:
	 * http://localhost:8020/agency/agencies { "timestamp":
	 * "2018-10-20T14:06:20.519+0000", "message": "could not extract ResultSet; SQL
	 * [n/a]; nested exception is org.hibernate.exception.SQLGrammarException: could
	 * not extract ResultSet", "details": "uri=/agency/agencies" } I think that it
	 * come from the ResponseStatus annotations
	 * 
	 * Hibernate interprets fareUrl as fare_url, so I change in createTables fareUrl
	 * to fareurl
	 * 
	 * Postman, you need to add in Headers: Content-Type: application/json --
	 * Mandatory
	 * 
	 * 
	 * @return
	 */
	@GetMapping(path = "/agencies", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AgencyDTO>> findAll() {
		List<AgencyDTO> result = service.findAll().isPresent() ? service.findAll().get() : null;

		if ( result != null ) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			// Manage error in here
			logger.debug("No data...");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * I had always [http 403 forbidden] or in the body [Invalid CORS request] using
	 * Postman. To fix it in Postman you need to add in Headers: Content-Type:
	 * application/json -- Mandatory Origin: http://localhost:8020 -- Mandatory
	 * Access-Control-Request-Method: POST -- Mandatory
	 * Access-Control-Request-Headers: Content-Type -- Optional
	 * 
	 * @param entity
	 * @return
	 */
	@PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AgencyDTO> create(@RequestBody @Valid AgencyDTO dto) {
		
		AgencyDTO result = service.create(dto).isPresent() ? service.create(dto).get() : null;
		
		if ( result != null ) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			// Error management, distributed logs or something like that in cloud deployment
			logger.error("Unable to create the agency:[{}]", dto);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * http://localhost:8020/agency/read?id=76ed93a9-f1b4-4ea8-a402-e1a36e286451
	 * 
	 * Postman, you need to add in Headers: Content-Type: application/json --
	 * Mandatory
	 * 
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(path = "/read", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AgencyDTO> read(@RequestParam(name = "id", required = true) UUID id) {
		
		AgencyDTO result = service.read(id).isPresent() ? service.read(id).get() : null;

		if (result != null) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			logger.error("The agency identifier:[{}] don't exist.", id);
			// FIXME I need to create an error send back to the user telling that the uri is good but the entity didn't exist
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		}
	}

	/**
	 * Like Post or method create I had always [http 403 forbidden] or in the body
	 * [Invalid CORS request] using Postman. To fix it in Postman you need to add in
	 * Headers: Content-Type: application/json -- Mandatory Origin:
	 * http://localhost:8020 -- Mandatory Access-Control-Request-Method: PUT --
	 * Mandatory
	 * 
	 * @param entity
	 * @return
	 */
	@PutMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AgencyDTO> update(@RequestBody @Valid AgencyDTO dto) {
		AgencyDTO result = service.update(dto).isPresent() ? service.update(dto).get() : null;
		
		if ( result != null ) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			logger.error("Unable to update the entity:[{}].", dto);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Like Post (create(...)) or Put (update(...) I had always [http 403 forbidden]
	 * or in the body [Invalid CORS request] using Postman. To fix it in Postman you
	 * need to add in Headers: Content-Type: application/json -- Mandatory Origin:
	 * http://localhost:8020 -- Mandatory Access-Control-Request-Method: DELETE --
	 * Mandatory
	 * 
	 * @param entity
	 */
	@DeleteMapping(path = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void delete(@RequestBody @Valid AgencyDTO dto) {
		service.delete(dto);
	}

	/**
	 * http://localhost:8020/agency/findByAgencyId?agencyId=STM
	 * 
	 * like Get (read) Postman, you need to add in Headers: Content-Type:
	 * application/json -- Mandatory
	 * 
	 * @param agencyId
	 * @return
	 */
	@GetMapping(path = "/findByAgencyId", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AgencyDTO> findByAgencyId(@RequestParam(name = "agencyId", required = true) String agencyId) {
		logger.info("There we are in findByAgencyId calling for this current agencyId[{}]", agencyId);
		
		AgencyDTO result = service.findByAgencyId(agencyId).isPresent() ? service.findByAgencyId(agencyId).get() : null;
		
		if ( result != null ) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			String message = String.format("This agencyId: [%s] doesn't exist.", agencyId);
			logger.debug(message);
			
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
