/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.ghandalf.urban.mobility.repository.ng;

import ca.ghandalf.urban.mobility.domain.ng.Shopping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ghandalf
 */
@Repository
public interface ShoppingRepository extends JpaRepository<Shopping, Long>{
    
}
