package ca.ghandalf.urban.mobility.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 
 * @author ghandalf
 *
 */
@RestController
@RequestMapping("api")
public class ApiController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApiController.class);
	
	@CrossOrigin
	@RequestMapping("/root")
	public String root() {
		LOGGER.info("A call as been done for root method");
		return "Welcome to Api";
	}

}
