
package ca.ghandalf.urban.mobility.restapi;

import ca.ghandalf.urban.mobility.domain.Book;
import ca.ghandalf.urban.mobility.service.BookService;
import ca.ghandalf.urban.mobility.service.BookException;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ghandalf
 */
@RestController
@CrossOrigin(origins = { "http://localhost:8020" })
@RequestMapping("/book")
public class BookController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);
   
    @Autowired
    private BookService service;
    
    @RequestMapping("/info")
    @ResponseStatus(value = HttpStatus.OK)
    public String root() {
        LOGGER.info("\n\n\t Root method has been called.==============================\n");
        return "Welcome to Book Rest Controller.";
    }
 
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody List<Book> findAll() {
        return service.findAll();
    }
    
    // POST : Should create new resource. Ideally return JSON with link to newly created resource. Same return codes as get possible. In addition - Return code 201 (CREATED) can be used.
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED, reason = "We can't save the current book, the server is not available.")
    public Book create(@RequestBody @Valid Book book) {
    	return service.create(book);
    }
    
    //GET : Should not update anything. Should be idempotent (same result in multiple calls). Possible Return Codes 200 (OK) + 404 (NOT FOUND) +400 (BAD REQUEST)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK, reason = "We can't find a book with this id.")
    public @ResponseBody Book read(@PathVariable("id") UUID id) throws BookException {
    	return service.read(id);
    }
    
    //PUT : Update a known resource. ex: update client details. Possible Return Codes : 200(OK) + 404 (NOT FOUND) +400 (BAD REQUEST)
    @PatchMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK, reason = "This object didn't exist.")
    public Book update(@RequestBody @Valid Book book) {
        return service.update(book);
    }
    
    //DELETE : Used to delete a resource. Possible Return Codes : 200(OK).
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@RequestBody @Valid Book book) {
        service.delete(book);
    }
    
}
