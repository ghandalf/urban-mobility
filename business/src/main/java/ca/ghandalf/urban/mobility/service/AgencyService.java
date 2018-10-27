package ca.ghandalf.urban.mobility.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import ca.ghandalf.urban.mobility.dto.AgencyDTO;

public interface AgencyService {

    public Optional<AgencyDTO> create(AgencyDTO dto);
    
    public Optional<AgencyDTO> read(UUID id);
    
    public Optional<AgencyDTO> update(AgencyDTO dto);
    
    public void delete(AgencyDTO dto);
    
    public Optional<List<AgencyDTO>> findAll();
    
    public Optional<AgencyDTO> findByAgencyId(String agencyId);
}
