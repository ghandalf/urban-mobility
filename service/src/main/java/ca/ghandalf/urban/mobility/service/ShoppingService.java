
package ca.ghandalf.urban.mobility.service;

import ca.ghandalf.urban.mobility.domain.Shopping;
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
