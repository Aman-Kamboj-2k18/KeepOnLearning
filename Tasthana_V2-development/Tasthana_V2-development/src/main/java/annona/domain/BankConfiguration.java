package annona.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class BankConfiguration {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String interestCalculationBasis;

	private Integer minimumSavingBalanceForAutodeposit;
	
	private Float minBalForSavingAccount;

	private Integer maximumTimeOfModification;

	private String modificationBasedOn;

	private Double panality;

	private Double prematurePanality;

	private String currency;

	private Integer minDepositAmount;

	private Integer penalityFlatAmount;

	private Float cgst;

	private Float sgst;

	private Float igst;
	
	private Float emiInterestRate;
	
	private Double panaltyForWithdraw;
	
	private Double minTopUpAmount;
	
	private Integer payAndWithdrawTenure;
	
	private Float tdsPercentForNoPAN;
	
	private String tdsCalculationOnBasis;
	
	private String reverseEmiOnBasis;
	
    private String citizen;
	
    private String nriAccountType;
		
	private String interestCompoundingBasis;
	
    private Float penaltyForDepositConversion;
	
	private Integer adjustmentForDepositConversion;
	
	private Integer minimumTenure;
	
	private Integer minimumAmountRequiredForAutoDeposit;
	
	private Float penaltyForMissedPaymentForRecurring;
	
	private Integer gracePeriodForRecurringPayment;
	
	private Integer lockingPeriodForWithdraw;
	
	public Integer getMaximumTimeOfModification() {
		return maximumTimeOfModification;
	}

	public void setMaximumTimeOfModification(Integer maximumTimeOfModification) {
		this.maximumTimeOfModification = maximumTimeOfModification;
	}

	public String getModificationBasedOn() {
		return modificationBasedOn;
	}

	public void setModificationBasedOn(String modificationBasedOn) {
		this.modificationBasedOn = modificationBasedOn;
	}

	public Double getPanality() {
		return panality;
	}

	public void setPanality(Double panality) {
		this.panality = panality;
	}

	

	public Double getPrematurePanality() {
		return prematurePanality;
	}

	public void setPrematurePanality(Double prematurePanality) {
		this.prematurePanality = prematurePanality;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInterestCalculationBasis() {
		return interestCalculationBasis;
	}

	public void setInterestCalculationBasis(String interestCalculationBasis) {
		this.interestCalculationBasis = interestCalculationBasis;
	}

	public Integer getMinimunSavingBalanceForAutodeposit() {
		return minimumSavingBalanceForAutodeposit;
	}
	
	
	//
	// public Integer getMinimumSavingBalanceForAutodeposit() {
	// return minimumSavingBalanceForAutodeposit;
	// }

	public Float getMinBalForSavingAccount() {
		return minBalForSavingAccount;
	}

	public void setMinBalForSavingAccount(Float minBalForSavingAccount) {
		this.minBalForSavingAccount = minBalForSavingAccount;
	}

	public void setMinimumSavingBalanceForAutodeposit(Integer minimumBalanceForAutodeposit) {
		this.minimumSavingBalanceForAutodeposit = minimumBalanceForAutodeposit;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Integer getMinDepositAmount() {
		return minDepositAmount;
	}

	public void setMinDepositAmount(Integer minDepositAmount) {
		this.minDepositAmount = minDepositAmount;
	}

	public Integer getPenalityFlatAmount() {
		return penalityFlatAmount;
	}

	public void setPenalityFlatAmount(Integer penalityFlatAmount) {
		this.penalityFlatAmount = penalityFlatAmount;
	}

	public Integer getMinimumSavingBalanceForAutodeposit() {
		return minimumSavingBalanceForAutodeposit;
	}

	public Float getCgst() {
		return cgst;
	}

	public void setCgst(Float cgst) {
		this.cgst = cgst;
	}

	public Float getSgst() {
		return sgst;
	}

	public void setSgst(Float sgst) {
		this.sgst = sgst;
	}

	public Float getIgst() {
		return igst;
	}

	public void setIgst(Float igst) {
		this.igst = igst;
	}

	public Float getEmiInterestRate() {
		return emiInterestRate;
	}

	public void setEmiInterestRate(Float emiInterestRate) {
		this.emiInterestRate = emiInterestRate;
	}

	public Double getPanaltyForWithdraw() {
		return panaltyForWithdraw;
	}

	public void setPanaltyForWithdraw(Double panaltyForWithdraw) {
		this.panaltyForWithdraw = panaltyForWithdraw;
	}

	public Double getMinTopUpAmount() {
		return minTopUpAmount;
	}

	public void setMinTopUpAmount(Double minTopUpAmount) {
		this.minTopUpAmount = minTopUpAmount;
	}

	public Integer getPayAndWithdrawTenure() {
		return payAndWithdrawTenure;
	}

	public void setPayAndWithdrawTenure(Integer payAndWithdrawTenure) {
		this.payAndWithdrawTenure = payAndWithdrawTenure;
	}

	public Float getTdsPercentForNoPAN() {
		return tdsPercentForNoPAN;
	}

	public void setTdsPercentForNoPAN(Float tdsPercentForNoPAN) {
		this.tdsPercentForNoPAN = tdsPercentForNoPAN;
	}

	public String getTdsCalculationOnBasis() {
		return tdsCalculationOnBasis;
	}

	public void setTdsCalculationOnBasis(String tdsCalculationOnBasis) {
		this.tdsCalculationOnBasis = tdsCalculationOnBasis;
	}

	public String getReverseEmiOnBasis() {
		return reverseEmiOnBasis;
	}

	public void setReverseEmiOnBasis(String reverseEmiOnBasis) {
		this.reverseEmiOnBasis = reverseEmiOnBasis;
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

	public String getInterestCompoundingBasis() {
		return interestCompoundingBasis;
	}

	public void setInterestCompoundingBasis(String interestCompoundingBasis) {
		this.interestCompoundingBasis = interestCompoundingBasis;
	}

	public Float getPenaltyForDepositConversion() {
		return penaltyForDepositConversion;
	}

	public void setPenaltyForDepositConversion(Float penaltyForDepositConversion) {
		this.penaltyForDepositConversion = penaltyForDepositConversion;
	}

	public Integer getAdjustmentForDepositConversion() {
		return adjustmentForDepositConversion;
	}

	public void setAdjustmentForDepositConversion(Integer adjustmentForDepositConversion) {
		this.adjustmentForDepositConversion = adjustmentForDepositConversion;
	}

	public Integer getMinimumTenure() {
		return minimumTenure;
	}

	public void setMinimumTenure(Integer minimumTenure) {
		this.minimumTenure = minimumTenure;
	}

	public Integer getMinimumAmountRequiredForAutoDeposit() {
		return minimumAmountRequiredForAutoDeposit;
	}

	public void setMinimumAmountRequiredForAutoDeposit(Integer minimumAmountRequiredForAutoDeposit) {
		this.minimumAmountRequiredForAutoDeposit = minimumAmountRequiredForAutoDeposit;
	}

	public Float getPenaltyForMissedPaymentForRecurring() {
		return penaltyForMissedPaymentForRecurring;
	}

	public void setPenaltyForMissedPaymentForRecurring(Float penaltyForMissedPaymentForRecurring) {
		this.penaltyForMissedPaymentForRecurring = penaltyForMissedPaymentForRecurring;
	}

	public Integer getGracePeriodForRecurringPayment() {
		return gracePeriodForRecurringPayment;
	}

	public void setGracePeriodForRecurringPayment(Integer gracePeriodForRecurringPayment) {
		this.gracePeriodForRecurringPayment = gracePeriodForRecurringPayment;
	}

	public Integer getLockingPeriodForWithdraw() {
		return lockingPeriodForWithdraw;
	}

	public void setLockingPeriodForWithdraw(Integer lockingPeriodForWithdraw) {
		this.lockingPeriodForWithdraw = lockingPeriodForWithdraw;
	}
	
	
}
