/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.ghandalf.urban.mobility.repository.ng;

import org.springframework.data.jpa.repository.JpaRepository;
import ca.ghandalf.urban.mobility.domain.ng.Book;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ghandalf
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
}
