
package ca.ghandalf.urban.mobility.service;

import ca.ghandalf.urban.mobility.domain.Book;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author ghandalf
 */
public interface BookService {
    
    public Book create(Book book);
    
    public Book read(UUID id) throws BookException;
    
    public Book update(Book book);
    
    public void delete(Book book);
    
    public List<Book> findAll();
}
