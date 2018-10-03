
package ca.ghandalf.urban.mobility.service;

import ca.ghandalf.urban.mobility.domain.Shopping;
import ca.ghandalf.urban.mobility.repository.ShoppingRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ghandalf
 */
@Service
public class ShoppingServiceImpl implements ShoppingService {

    @Autowired
    private ShoppingRepository shoppingRepository;
    
    @Override
    public Shopping create(Shopping shopping) {
        return null; //repository.save(shopping);
    }

    @Override
    public Shopping read(Long id) throws ShoppingException {
        return null; //repository.findById(id).orElseThrow(() -> new ShoppingException("Shopping with id: " + id + " was not found."));
    }

    @Override
    public Shopping update(Shopping shopping) {
        return null; //repository.saveAndFlush(shopping);
    }

    @Override
    public void delete(Shopping shopping) {
       //repository.delete(shopping);
    }

    @Override
    public List<Shopping> findAll() {
        return null; //repository.findAll();
    }
    
}
