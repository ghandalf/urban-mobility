package ca.ghandalf.urban.mobility.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ca.ghandalf.urban.mobility.domain.Agency;

/**
 * 
 * @author ghandalf
 *
 */
@Repository
public interface AgencyRepository extends JpaRepository<Agency, UUID> {

	Optional<Agency> findByAgencyId(String agencyId);
}
