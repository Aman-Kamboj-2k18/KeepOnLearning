package annona.form;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class OrnamentsForm {
	
	private Long id;
	
	private String ornament;
	
    private Long ornamentSubmissionMasterId;
	
	private Float purity;
	
	private Double weight;
	
	private Integer carat;
	
	private String comment;
	
	private Double price;
	
    private Long customerId;
	
	private Date submissionDate;
	
	private Double goldRate;
	
	private Double totalWeight;
	
	private Double totalPrice;
	
	private Integer isDepositCreated;
	
	private Long ornamentSubmissionDetailsId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	

	public String getOrnament() {
		return ornament;
	}

	public void setOrnament(String ornament) {
		this.ornament = ornament;
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

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	public Double getGoldRate() {
		return goldRate;
	}

	public void setGoldRate(Double goldRate) {
		this.goldRate = goldRate;
	}

	public Double getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getIsDepositCreated() {
		return isDepositCreated;
	}

	public void setIsDepositCreated(Integer isDepositCreated) {
		this.isDepositCreated = isDepositCreated;
	}

	public Long getOrnamentSubmissionDetailsId() {
		return ornamentSubmissionDetailsId;
	}

	public void setOrnamentSubmissionDetailsId(Long ornamentSubmissionDetailsId) {
		this.ornamentSubmissionDetailsId = ornamentSubmissionDetailsId;
	}
	
	
	

}
