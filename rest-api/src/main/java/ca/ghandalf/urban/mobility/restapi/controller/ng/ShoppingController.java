/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.ghandalf.urban.mobility.restapi.controller.ng;

import ca.ghandalf.urban.mobility.domain.ng.Shopping;
import ca.ghandalf.urban.mobility.service.ng.ShoppingService;
import ca.ghandalf.urban.mobility.service.ng.exception.ShoppingException;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ghandalf
 */
@RestController
@CrossOrigin(origins = { "http://localhost:8020" })
@RequestMapping("shopping")
public class ShoppingController {
    
    @Autowired
    private ShoppingService service;
    
    @RequestMapping("/root.shopping")
    public String root() {
        return "Welcome to Shopping Rest Controller.";
    }
    
    @RequestMapping(method=RequestMethod.POST)
    public Shopping create(@RequestBody @Valid Shopping shopping) {
        return service.create(shopping);
    }
    
    @RequestMapping(method=RequestMethod.GET)
    public Shopping read(@RequestParam("id") Long id) throws ShoppingException {
        return service.read(id);
    }
    
    @RequestMapping(method=RequestMethod.PUT)
    public Shopping update(@RequestBody @Valid Shopping shopping) {
        return service.update(shopping);
    }
    
    @RequestMapping(method=RequestMethod.DELETE)
    public Shopping delete(@RequestBody @Valid Shopping shopping) {
        service.delete(shopping);
        return shopping;
    }
    
    @RequestMapping(method=RequestMethod.GET, name = "findAll")
    public List<Shopping> findAll() {
        return service.findAll();
    }
}
