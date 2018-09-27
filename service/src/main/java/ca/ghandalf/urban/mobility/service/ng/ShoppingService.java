/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.ghandalf.urban.mobility.service.ng;

import ca.ghandalf.urban.mobility.domain.ng.Shopping;
import ca.ghandalf.urban.mobility.service.ng.exception.ShoppingException;
import java.util.List;

/**
 *
 * @author ghandalf
 */
public interface ShoppingService {
    
    public Shopping create(Shopping shopping);
    
    public Shopping read(Long id) throws ShoppingException;
    
    public Shopping update(Shopping shopping);
    
    public void delete(Shopping shopping);
    
    public List<Shopping> findAll();
}
