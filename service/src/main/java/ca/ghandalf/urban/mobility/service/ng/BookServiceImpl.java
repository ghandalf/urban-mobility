/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.ghandalf.urban.mobility.service.ng;

import ca.ghandalf.urban.mobility.domain.ng.Book;
import ca.ghandalf.urban.mobility.repository.ng.BookRepository;
import ca.ghandalf.urban.mobility.service.ng.exception.BookException;
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
    private BookRepository repository;
    
    @Override
    public Book create(Book book) {
        return repository.save(book);
    }

    @Override
    public Book read(Long id) throws BookException {
        return repository.findById(id).orElseThrow(() -> new BookException("Book with id: " + id + " was not found."));
    }

    @Override
    public Book update(Book book) {
        return repository.saveAndFlush(book);
        
    }

    @Override
    public void delete(Book book) {
        repository.delete(book);
    }

    @Override
    public List<Book> findAll() {
        return repository.findAll();
    }
    
}
