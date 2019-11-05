package annona.form;

import org.springframework.stereotype.Component;

@Component
public class WithdrawPenaltyForm {

	private Long id;

	private Long withdrawPenaltyMasterId;
	private Long productConfigurationId;
	private String withdrawType;
	private Short isPrematureWithdraw; // 1: Premature, 0: Part

	private String effectiveDate;

	private Integer amountFrom;

	private Integer amountTo;

	private String penaltyRate;

	private String tenureSign;
	private String amountSign;

	private String tenure;
	
	private Double amount;

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	private String penaltyFlatAmount;

	public Short getIsPrematureWithdraw() {
		return isPrematureWithdraw;
	}

	public void setIsPrematureWithdraw(Short isPrematureWithdraw) {
		this.isPrematureWithdraw = isPrematureWithdraw;
	}

	private transient String tempDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getWithdrawPenaltyMasterId() {
		return withdrawPenaltyMasterId;
	}

	public void setWithdrawPenaltyMasterId(Long withdrawPenaltyMasterId) {
		this.withdrawPenaltyMasterId = withdrawPenaltyMasterId;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Integer getAmountFrom() {
		return amountFrom;
	}

	public String getWithdrawType() {
		return withdrawType;
	}

	public void setWithdrawType(String withdrawType) {
		this.withdrawType = withdrawType;
	}

	public void setAmountFrom(Integer amountFrom) {
		this.amountFrom = amountFrom;
	}

	public Integer getAmountTo() {
		return amountTo;
	}

	public void setAmountTo(Integer amountTo) {
		this.amountTo = amountTo;
	}

	public String getPenaltyRate() {
		return penaltyRate;
	}

	public void setPenaltyRate(String penaltyRate) {
		this.penaltyRate = penaltyRate;
	}

	public String getTenureSign() {
		return tenureSign;
	}

	public void setTenureSign(String tenureSign) {
		this.tenureSign = tenureSign;
	}
	
	public String getAmountSign() {
		return amountSign;
	}

	public void setAmountSign(String amountSign) {
		this.amountSign = amountSign;
	}

	public String getTenure() {
		return tenure;
	}

	public void setTenure(String tenure) {
		this.tenure = tenure;
	}

	public String getPenaltyFlatAmount() {
		return penaltyFlatAmount;
	}

	public void setPenaltyFlatAmount(String penaltyFlatAmount) {
		this.penaltyFlatAmount = penaltyFlatAmount;
	}

	public String getTempDate() {
		return tempDate;
	}

	public void setTempDate(String tempDate) {
		this.tempDate = tempDate;
	}

	public Long getProductConfigurationId() {
		return productConfigurationId;
	}

	public void setProductConfigurationId(Long productConfigurationId) {
		this.productConfigurationId = productConfigurationId;
	}

}
