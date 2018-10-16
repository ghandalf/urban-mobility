
package ca.ghandalf.urban.mobility.restapi;

import ca.ghandalf.urban.mobility.domain.Book;
import ca.ghandalf.urban.mobility.service.BookService;
import ca.ghandalf.urban.mobility.service.BookException;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ghandalf
 */
@RestController
//@CrossOrigin(origins = { "http://localhost:8020" })
@RequestMapping("/book")
public class BookController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);
   
    @Autowired
    private BookService service;
    
//    @CrossOrigin
    @RequestMapping("/info")
    @ResponseStatus(value = HttpStatus.OK)
    public String root() {
        LOGGER.info("\n\n\t Root method has been called.==============================\n");
        return "Welcome to Book Rest Controller.";
    }
    
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Book create(@RequestBody @Valid Book book) {
        return service.create(book);
    }
    
//    @RequestMapping(method=RequestMethod.GET)
//    public Book read(@RequestParam("id") Long id) throws BookException {
//        return service.read(id);
//    }
//    
//    @RequestMapping(method=RequestMethod.PUT)
//    public Book update(@RequestBody @Valid Book book) {
//        return service.update(book);
//    }
//    
//    @RequestMapping(method=RequestMethod.DELETE)
//    public Book delete(@RequestBody @Valid Book book) {
//        service.delete(book);
//        return book;
//    }
//    
//    @RequestMapping(method=RequestMethod.GET, name = "findAll")
//    public List<Book> findAll() {
//        return service.findAll();
//    }
}
