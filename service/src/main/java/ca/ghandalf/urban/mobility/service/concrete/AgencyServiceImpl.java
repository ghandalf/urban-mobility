package ca.ghandalf.urban.mobility.service.concrete;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ghandalf.urban.mobility.domain.Agency;
import ca.ghandalf.urban.mobility.repository.AgencyRepository;
import ca.ghandalf.urban.mobility.service.AgencyService;

@Service
public class AgencyServiceImpl implements AgencyService {

	@Autowired
	private AgencyRepository repository;
	
	@Override
	public Optional<Agency> create(Agency entity) {
		// FIXME review this one
		return Optional.of(repository.save(entity));
	}

	@Override
	public Optional<Agency> read(UUID id) {
		return repository.findById(id);
	}

	@Override
	public Optional<Agency> update(Agency entity) {
		return Optional.of(repository.save(entity));
	}

	@Override
	public void delete(Agency entity) {
		repository.delete(entity);
	}

	@Override
	public Optional<List<Agency>> findAll() {
		return Optional.of(repository.findAll());
	}

	@Override
	public Optional<Agency> findByAgencyId(String agencyId) {
		return Optional.of(repository.findByAgencyId(agencyId));
	}

}
