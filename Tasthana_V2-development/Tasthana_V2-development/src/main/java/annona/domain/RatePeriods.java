package annona.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Configurable;

@Entity
@Configurable
@XmlRootElement
public class RatePeriods {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	private Integer fromDay;
	
	private Integer toDay;

    private String description;

    private String depositClassification;
    
    private String citizen;
	
	private String nriAccountType;
	
	private String category;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getFromDay() {
		return fromDay;
	}

	public void setFromDay(Integer fromDay) {
		this.fromDay = fromDay;
	}

	public Integer getToDay() {
		return toDay;
	}

	public void setToDay(Integer toDay) {
		this.toDay = toDay;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDepositClassification() {
		return depositClassification;
	}

	public void setDepositClassification(String depositClassification) {
		this.depositClassification = depositClassification;
	}

	public String getCitizen() {
		return citizen;
	}

	public void setCitizen(String citizen) {
		this.citizen = citizen;
	}

	public String getNriAccountType() {
		return nriAccountType;
	}

	public void setNriAccountType(String nriAccountType) {
		this.nriAccountType = nriAccountType;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
 
}
