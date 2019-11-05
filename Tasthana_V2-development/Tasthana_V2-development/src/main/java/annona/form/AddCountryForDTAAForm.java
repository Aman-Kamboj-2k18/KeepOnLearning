package annona.form;

import org.springframework.stereotype.Component;

@Component
public class AddCountryForDTAAForm {

	private Long id;

	private String country;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
