/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.ghandalf.urban.mobility.restapi.controller.ng;

import ca.ghandalf.urban.mobility.domain.ng.Book;
import ca.ghandalf.urban.mobility.service.ng.BookService;
import ca.ghandalf.urban.mobility.service.ng.exception.BookException;
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
@RequestMapping()
public class BookController {
    
    @Autowired
    private BookService service;
    
    @RequestMapping("/rootbook")
    public String root() {
        return "Welcome to Book Rest Controller.";
    }
    
    @RequestMapping(method=RequestMethod.POST)
    public Book create(@RequestBody @Valid Book book) {
        return service.create(book);
    }
    
    @RequestMapping(method=RequestMethod.GET)
    public Book read(@RequestParam("id") Long id) throws BookException {
        return service.read(id);
    }
    
    @RequestMapping(method=RequestMethod.PUT)
    public Book update(@RequestBody @Valid Book book) {
        return service.update(book);
    }
    
    @RequestMapping(method=RequestMethod.DELETE)
    public Book delete(@RequestBody @Valid Book book) {
        service.delete(book);
        return book;
    }
    
    @RequestMapping(method=RequestMethod.GET, name = "findAll")
    public List<Book> findAll() {
        return service.findAll();
    }
}
