package ca.ghandalf.urban.mobility.service.concrete;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ghandalf.urban.mobility.domain.Agency;
import ca.ghandalf.urban.mobility.dto.AgencyDTO;
import ca.ghandalf.urban.mobility.mapper.AgencyMapper;
import ca.ghandalf.urban.mobility.repository.AgencyRepository;
import ca.ghandalf.urban.mobility.service.AgencyService;

@Service
public class AgencyServiceImpl implements AgencyService {

	private static final Logger logger = LoggerFactory.getLogger(AgencyServiceImpl.class);
	
	@Autowired
	private AgencyRepository repository;

	@Autowired
	private AgencyMapper mapper;

	public Optional<AgencyDTO> create(AgencyDTO dto) {
		Agency entity = mapper.fromDTO(dto);
		
		entity = repository.save(entity);

		return Optional.of(mapper.fromEntity(entity));
	}

	public Optional<AgencyDTO> read(UUID id) {
		Optional<Agency> entity = repository.findById(id);
		
		AgencyDTO dto = entity.isPresent() ? mapper.fromEntity(entity.get()) : null;
		
		return Optional.of(dto);
	}

	public Optional<AgencyDTO> update(AgencyDTO dto) {
		Agency entity = repository.save(mapper.fromDTO(dto));
		
		return Optional.of(mapper.fromEntity(entity));
	}

	public void delete(AgencyDTO dto) {
		repository.delete(mapper.fromDTO(dto));
	}

	public Optional<List<AgencyDTO>> findAll() {
		return Optional.of(
				repository.findAll().stream().map(entity -> mapper.fromEntity(entity)).collect(Collectors.toList()));
	}

	public Optional<AgencyDTO> findByAgencyId(String agencyId) {
		logger.info("\n\n\t\tIn service looking for this agencyId=[{}]", agencyId);
		
		Optional<Agency> result = repository.findByAgencyId(agencyId);
		
		AgencyDTO dto = result.isPresent() ? mapper.fromEntity(result.get()) : null;
		
		logger.info("\n\t\tResearch done for agencyId=[{}] and the restult dto=[{}].\n", agencyId, dto);
		
		return Optional.of(dto);
	}

}
