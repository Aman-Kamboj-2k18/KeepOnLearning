package annona.form;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class AddCountryWiseTaxRateDTAAForm {

	private Long id;

	private Long dtaaCountryId;

	private String country;
	
	private Date effectiveDate;
	
	private Float taxRate;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDtaaCountryId() {
		return dtaaCountryId;
	}

	public void setDtaaCountryId(Long dtaaCountryId) {
		this.dtaaCountryId = dtaaCountryId;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Float getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(Float taxRate) {
		this.taxRate = taxRate;
	}

}
