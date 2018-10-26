package ca.ghandalf.urban.mobility.service.concrete;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ghandalf.urban.mobility.domain.Agency;
import ca.ghandalf.urban.mobility.dto.AgencyDTO;
import ca.ghandalf.urban.mobility.mapper.AgencyMapper;
import ca.ghandalf.urban.mobility.repository.AgencyRepository;
import ca.ghandalf.urban.mobility.service.AgencyService;

@Service
public class AgencyServiceImpl implements AgencyService {

	@Autowired
	private AgencyRepository repository;

	@Autowired
	private AgencyMapper mapper;

	public Optional<AgencyDTO> create(AgencyDTO dto) {
		Agency entity = mapper.fromDTO(dto);
		
		entity = repository.save(entity);

		return Optional.of(mapper.fromEntity(entity));
	}

	public Optional<Agency> read(UUID id) {
		return repository.findById(id);
	}

	public Optional<Agency> update(Agency entity) {
		return Optional.of(repository.save(entity));
	}

	public void delete(Agency entity) {
		repository.delete(entity);
	}

	public Optional<List<Agency>> findAll() {
		return Optional.of(repository.findAll());
	}

	public Optional<Agency> findByAgencyId(String agencyId) {
		return Optional.of(repository.findByAgencyId(agencyId));
	}

}
