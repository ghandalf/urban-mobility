package ca.ghandalf.urban.mobility.restapi.error;

import java.util.Date;

public class AgencyError extends UrbanMobilityError {

	public AgencyError(Date timestamp, String message, String details) {
		super(timestamp, message, details);
	}

}
