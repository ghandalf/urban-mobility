package ca.ghandalf.urban.mobility.mapper;

import org.springframework.stereotype.Component;

import ca.ghandalf.urban.mobility.domain.Agency;
import ca.ghandalf.urban.mobility.dto.AgencyDTO;

@Component
public class AgencyMapper {

	public Agency fromDTO(AgencyDTO dto) {

		Agency result = new Agency();

		// Here if the dto has been manipulated it will have his id, otherwise result
		// will created one once the instance is created.
		if (dto.getId() != null) {
			result.setId(dto.getId());
		}

		result.setAgencyId(dto.getAgencyId());
		result.setEmail(dto.getEmail());
		result.setFareUrl(dto.getFareUrl());
		result.setLanguage(dto.getLanguage());
		result.setName(dto.getName());
		result.setPhone(dto.getPhone());
		result.setTimezone(dto.getTimezone());
		result.setUrl(dto.getUrl());

		return result;
	}

	public AgencyDTO fromEntity(Agency entity) {
		AgencyDTO result = null;

		if (entity != null) {

			result = new AgencyDTO();

			result.setAgencyId(entity.getAgencyId());
			result.setEmail(entity.getEmail());
			result.setFareUrl(entity.getFareUrl());
			result.setId(entity.getId());
			result.setLanguage(entity.getLanguage());
			result.setName(entity.getName());
			result.setPhone(entity.getPhone());
			result.setTimezone(entity.getTimezone());
			result.setUrl(entity.getUrl());

		}

		return result;
	}
}
