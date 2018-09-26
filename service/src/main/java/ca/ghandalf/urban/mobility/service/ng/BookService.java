/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.ghandalf.urban.mobility.service.ng;

import ca.ghandalf.urban.mobility.domain.ng.Book;
import java.util.List;

/**
 *
 * @author ghandalf
 */
public interface BookService {
    
    public Book create(Book book);
    
    public Book read(Long id);
    
    public Book update(Book book);
    
    public void delete(Book book);
    
    public List<Book> findAll();
}
