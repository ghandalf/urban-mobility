/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.ghandalf.urban.mobility.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import ca.ghandalf.urban.mobility.domain.Book;

/**
 *
 * @author ghandalf
 */
@Repository
public interface BookRepository extends PagingAndSortingRepository<Book, UUID> {
    
	Optional<Book> findByIsbn(String isbn);
}
