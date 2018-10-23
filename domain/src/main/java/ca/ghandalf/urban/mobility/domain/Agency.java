package ca.ghandalf.urban.mobility.domain;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author ghandalf
 *
 * @see https://developers.google.com/transit/gtfs/reference/?hl=en-FR#agencytxt
 * @see https://code.google.com/archive/p/googletransitdatafeed/wikis/PublicFeeds.wiki
 * @see http://donnees.ville.montreal.qc.ca/dataset/stm-horaires-planifies-et-trajets-des-bus-et-du-metro/resource/5aeab6ed-bab3-4e51-981a-a83883dc4b94
 * 
 */
@Entity
@Table(name = "Agencies")
public class Agency implements Serializable { // extends BaseEntity {

	private static final long serialVersionUID = 4137977373223153997L;

	@Id
	@Column(name = "id", nullable = false, unique = true, updatable = false)
	private UUID id = UUID.randomUUID();

	public UUID getId() {
		return this.id;
	}
	
	@Column(name = "agency_id", insertable = true, length = 128, nullable = true, updatable = true)
	public String agencyId;
	
	@Column(name = "name", insertable = true, length = 128, nullable = false, updatable = true)
	public String name;

	@Column(name = "url", insertable = true, length = 256, nullable = false, updatable = true)
	public String url;

	@Column(name = "timezone", insertable = true, length = 4, nullable = false, updatable = true)
	public String timezone;

	@Column(name = "language", insertable = true, length = 8, nullable = true, updatable = true)
	public String language;

	@Column(name = "phone", insertable = true, length = 32, nullable = true, updatable = true)
	public String phone;

	@Column(name = "fareurl", insertable = true, length = 256, nullable = true, updatable = true)
	public String fareUrl;

	@Column(name = "email", insertable = true, length = 128, nullable = true, updatable = true)
	public String email;

	/**
	 * Convenient base constructor
	 */
	public Agency() {
	}

	public String getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFareUrl() {
		return fareUrl;
	}

	public void setFareUrl(String fareUrl) {
		this.fareUrl = fareUrl;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Agency [id=" + getId() + ", agencyId=" + agencyId + ", name=" + name + ", url=" + url + ", timezone=" + timezone
				+ ", language=" + language + ", phone=" + phone + ", fareUrl=" + fareUrl + ", email=" + email
				 + "]";
	}

}
