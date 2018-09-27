/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.ghandalf.urban.mobility.service.ng;

import ca.ghandalf.urban.mobility.domain.ng.Shopping;
import ca.ghandalf.urban.mobility.repository.ng.ShoppingRepository;
import ca.ghandalf.urban.mobility.service.ng.exception.ShoppingException;
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
    private ShoppingRepository repository;
    
    @Override
    public Shopping create(Shopping shopping) {
        return repository.save(shopping);
    }

    @Override
    public Shopping read(Long id) throws ShoppingException {
        return repository.findById(id).orElseThrow(() -> new ShoppingException("Shopping with id: " + id + " was not found."));
    }

    @Override
    public Shopping update(Shopping shopping) {
        return repository.saveAndFlush(shopping);
    }

    @Override
    public void delete(Shopping shopping) {
        repository.delete(shopping);
    }

    @Override
    public List<Shopping> findAll() {
        return repository.findAll();
    }
    
}
