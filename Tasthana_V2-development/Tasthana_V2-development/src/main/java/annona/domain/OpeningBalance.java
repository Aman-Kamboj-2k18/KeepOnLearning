package annona.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Configurable;

@Entity
@Configurable
@Table(name = "OpeningBalance")
@XmlRootElement

public class OpeningBalance {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Date entryDate;
	
	private Long glId;

	private String glCode;

	private Double openingBalanceAmount;
	
	private Double closingBalanceAmount;

	private Date createdDate;  //Seems duplicate, but still we are keping
	
	private Date modifiedDate;
	
	private Long createdBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public Long getGlId() {
		return glId;
	}

	public void setGlId(Long glId) {
		this.glId = glId;
	}

	public String getGlCode() {
		return glCode;
	}

	public void setGlCode(String glCode) {
		this.glCode = glCode;
	}

	public Double getOpeningBalanceAmount() {
		return openingBalanceAmount;
	}

	public void setOpeningBalanceAmount(Double openingBalanceAmount) {
		this.openingBalanceAmount = openingBalanceAmount;
	}

	public Double getClosingBalanceAmount() {
		return closingBalanceAmount;
	}

	public void setClosingBalanceAmount(Double closingBalanceAmount) {
		this.closingBalanceAmount = closingBalanceAmount;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	
}

