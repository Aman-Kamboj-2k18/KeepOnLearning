package annona.form;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class RatesForm {
	
	private long id;

	private String type;

	private float tds;

	private float ses;

	private float service;
	
	private float fdFixedPercent;

	private Date assignDate;

	private Date updateDate;
	
    private String transactionId;
    
    private Long minIntAmtForTDSDeduction;
    
    private Date loginDate;
    
    private String currency;
    
    private String depositClassification;
    
    private Double amountSlabFrom;
	
  	private Double amountSlabTo;
    	
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

	public Long getMinIntAmtForTDSDeduction() {
		return minIntAmtForTDSDeduction;
	}

	public void setMinIntAmtForTDSDeduction(Long minIntAmtForTDSDeduction) {
		this.minIntAmtForTDSDeduction = minIntAmtForTDSDeduction;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getDepositClassification() {
		return depositClassification;
	}

	public void setDepositClassification(String depositClassification) {
		this.depositClassification = depositClassification;
	}

	public Double getAmountSlabFrom() {
		return amountSlabFrom;
	}

	public void setAmountSlabFrom(Double amountSlabFrom) {
		this.amountSlabFrom = amountSlabFrom;
	}

	public Double getAmountSlabTo() {
		return amountSlabTo;
	}

	public void setAmountSlabTo(Double amountSlabTo) {
		this.amountSlabTo = amountSlabTo;
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
