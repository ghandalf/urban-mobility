
package ca.ghandalf.urban.mobility.service;

import ca.ghandalf.urban.mobility.domain.Book;
import java.util.List;

/**
 *
 * @author ghandalf
 */
public interface BookService {
    
    public Book create(Book book);
    
    public Book read(Long id) throws BookException;
    
    public Book update(Book book);
    
    public void delete(Book book);
    
    public List<Book> findAll();
}
