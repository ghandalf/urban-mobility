package ca.ghandalf.urban.mobility.restapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ca.ghandalf.urban.mobility.domain.Agency;
import ca.ghandalf.urban.mobility.restapi.configuration.ApplicationProperties;

@RestController
public class MontrealController {

    @Autowired
    private ApplicationProperties applicationProperties;

    @RequestMapping("/um/configuration/properties")
    public ApplicationProperties getApplicationProperties() {
        System.out.println("ApplicationProperties has been called, return will be \n\t" + this.applicationProperties.toString() + "\n");

        return this.applicationProperties;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/um/create/agency")
    public Agency create(@RequestBody Agency agency) {
        System.out.println("Create agency has been called \n\t" + agency.toString() + "\n");

        return agency;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/um/read/agency")
    public Agency read(@RequestParam(value = "id") int id) {
        Agency agency = new Agency();
        agency.setId(id);
        agency.setName("Montréal Métropolitain");
        agency.setUrl("http://test");
        agency.setTimezone("Canada Est");
        agency.setLanguage("Le français s'il vous plait");
        agency.setPhone("514-333-2222");
        agency.setFareUrl("http://fare.url.com");
        
        System.out.println("Read agency has been called with id = " + id + " return will be \n\t" + agency.toString() + "\n");
        
        return agency;
    }
    
    @RequestMapping(method = RequestMethod.PUT, value = "/um/update/agency")
    public Agency update(@RequestBody Agency agency) {
        System.out.println("Update agency has been called \n\t" + agency.toString() + "\n");
        return agency;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/um/delete/agency")
    public Agency delete(@RequestParam(value = "id") int id) {
        Agency agency = new Agency();
        agency.setId(id);
        agency.setName("Montréal Métropolitain deleted");
        agency.setUrl("http://test.ca/deleted");
        agency.setTimezone("Canada Est/deleted");
        agency.setLanguage("Le français s'il vous plait/deleted");
        agency.setPhone("514-333-2222/deleted");
        agency.setFareUrl("http://fare.url.com/deleted");
        
        System.out.println("Delete agency has been called with id = " + id + " return will be \n\t" + agency.toString() + "\n");
        
        return agency;
    }

    

// RequestMethod enum see: https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RequestMethod.html
// https://developer.mozilla.org/en-US/docs/Web/HTTP/Methods
/**
 * 
 * GET
The GET method requests a representation of the specified resource. Requests using GET should only retrieve data.
HEAD
The HEAD method asks for a response identical to that of a GET request, but without the response body.
POST
The POST method is used to submit an entity to the specified resource, often causing a change in state or side effects on the server.
PUT
The PUT method replaces all current representations of the target resource with the request payload.

DELETE
The DELETE method deletes the specified resource.
CONNECT
The CONNECT method establishes a tunnel to the server identified by the target resource.

OPTIONS
The OPTIONS method is used to describe the communication options for the target resource.
TRACE
The TRACE method performs a message loop-back test along the path to the target resource.

PATCH
The PATCH method is used to apply partial modifications to a resource.
 */

 
}