package annona.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

@Entity
@Table
public class OrnamentSubmissionDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	private Long ornamentSubmissionMasterId;
	
	private Float purity;
	
	private Double weight;
	
	private Integer carat;
	
	private String comment;
	
	private Double price;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrnamentSubmissionMasterId() {
		return ornamentSubmissionMasterId;
	}

	public void setOrnamentSubmissionMasterId(Long ornamentSubmissionMasterId) {
		this.ornamentSubmissionMasterId = ornamentSubmissionMasterId;
	}

	public Float getPurity() {
		return purity;
	}

	public void setPurity(Float purity) {
		this.purity = purity;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Integer getCarat() {
		return carat;
	}

	public void setCarat(Integer carat) {
		this.carat = carat;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	
	

}
