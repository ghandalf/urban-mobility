
package ca.ghandalf.urban.mobility.service;

import ca.ghandalf.urban.mobility.domain.Book;
import ca.ghandalf.urban.mobility.repository.BookRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        return bookRepository.save(book);
    }

    @Override
    public Book read(UUID id) throws BookException {
    	Optional<Book> result = bookRepository.findById(id); //.orElseThrow(() -> new BookException("Book with id: " + id + " was not found."));
        return result.get();
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
