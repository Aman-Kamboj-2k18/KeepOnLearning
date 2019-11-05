package annona.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Rates {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String type;

	private float tds;
	

	private float rate;


	private float ses;
	
	private float fdFixedPercent;

	private float service;

	private Date assignDate;

	private Date updateDate;
	
	private String transactionId;
	
	private Long minIntAmtForTDSDeduction;
	
    private String citizen;
	
	private String nriAccountType; 
	
	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	public float getTds() {
		return tds;
	}

	public void setTds(float tds) {
		this.tds = tds;
	}

	public float getSes() {
		return ses;
	}

	public void setSes(float ses) {
		this.ses = ses;
	}

	public float getService() {
		return service;
	}

	public void setService(float service) {
		this.service = service;
	}

	public Date getAssignDate() {
		return assignDate;
	}

	public void setAssignDate(Date assignDate) {
		this.assignDate = assignDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public float getFdFixedPercent() {
		return fdFixedPercent;
	}

	public void setFdFixedPercent(float fdFixedPercent) {
		this.fdFixedPercent = fdFixedPercent;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public Long getMinIntAmtForTDSDeduction() {
		return minIntAmtForTDSDeduction;
	}

	public void setMinIntAmtForTDSDeduction(Long minIntAmtForTDSDeduction) {
		this.minIntAmtForTDSDeduction = minIntAmtForTDSDeduction;
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

	
}
