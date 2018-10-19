package ca.ghandalf.urban.mobility.domain;

import javax.persistence.Embeddable;

@Embeddable
public class Author {

	private String firstName;
	private String lastName;
	
	/**
	 * Use by Jackson
	 */
	protected Author() {}

	public Author(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "Author [firstName=" + firstName + ", lastName=" + lastName + "]";
	}

}
