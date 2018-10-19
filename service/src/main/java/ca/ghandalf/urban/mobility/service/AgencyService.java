package ca.ghandalf.urban.mobility.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import ca.ghandalf.urban.mobility.domain.Agency;

public interface AgencyService {

    public Optional<Agency> create(Agency entity);
    
    public Optional<Agency> read(UUID id);
    
    public Optional<Agency> update(Agency entity);
    
    public void delete(Agency entity);
    
    public Optional<List<Agency>> findAll();
    
    public Optional<Agency> findByAgencyId(String agencyId);
}
