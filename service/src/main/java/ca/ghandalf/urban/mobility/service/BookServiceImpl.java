
package ca.ghandalf.urban.mobility.service;

import ca.ghandalf.urban.mobility.domain.Book;
import ca.ghandalf.urban.mobility.repository.BookRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ghandalf
 */
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;
    
    @Override
    public Book create(Book book) {
        return null; //repository.save(book);
    }

    @Override
    public Book read(Long id) throws BookException {
        return null; //repository.findById(id).orElseThrow(() -> new BookException("Book with id: " + id + " was not found."));
    }

    @Override
    public Book update(Book book) {
        return null; //repository.saveAndFlush(book);
        
    }

    @Override
    public void delete(Book book) {
        //repository.delete(book);
    }

    @Override
    public List<Book> findAll() {
        return null; //repository.findAll();
    }
    
}
