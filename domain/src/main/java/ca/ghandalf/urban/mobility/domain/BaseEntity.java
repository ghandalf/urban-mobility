package ca.ghandalf.urban.mobility.domain;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;

public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 4137977373223153996L;

	@Id
	@Column(name = "id", nullable = false, unique = true, updatable = false)
	private UUID id = UUID.randomUUID();

	public UUID getId() {
		return this.id;
	}

}
