package annona.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.*;

@Entity
@Table
public class ProductConfiguration {

	//----------Start- General Information of Product------------
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String productCode;
	
	private String productName;
	
	private String productType;
	
	private String citizen;
		
	private String nriAccountType;
	
	private String minimumTenure;
	
	private String maximumTenure;
	
	private Integer minimumDepositAmount;
	
	private Integer maximumDepositAmount;
	
	private String defaultCurrency;
	
	private Integer gracePeriodForRecurringPayment;
	
	private String interestCompoundingBasis;
	
	private String tdsCalculationOnBasis;
	
	private Float tdsPercentForNoPAN;
	
	private String interestCalculationBasis;
	
	private String annuityBasis; // reverseEmiOnBasis;
	
	private Integer isNomineeMandatory;
	
	private Integer nomineeRequiredForEachAccHolder;  // if nominee mandatory then only it will come in picture
	
	private Integer contributionRequiredForJointAccHolders; 
	
	private Date productStartDate;
	
	private Date productEndDate;
	
	
	private String paymentModeOnMaturity; // Fund Transfer/DD/Cheque
	//----------End- General Information of Product------------
	 
	

	//----------Start- TOP-UP Information of Product-----------
	
	private Integer isPrematureClosingioAllowedForTopup; 
	
	private Integer isTopupAllowed;
	

	private String lockingPeriodForTopup;
	
	private Integer minimumTopupAmount;
	
	private Integer maximumTopupAmount;
	
	private Integer isInterestAdjustmentRequiredForTopup;
	
	private Integer preventionOfTopUpBeforeMaturity;
	//----------End- TOP-UP Information of Product-----------
	
	
	
	//----------Start- Withdraw Information of Product-----------
	private Integer isPrematureClosingioAllowedForWithdraw; 
	private Integer isWithdrawAllowed;
	
	private String lockingPeriodForWithdraw;
	
	private Integer isSlabBasedPenaltyRequiredForWithdraw;
	
	private Integer isInterestAdjustmentRequiredForWithdraw;
	private Integer preventionOfWithdrawBeforeMaturity;
	//----------End- TOP-UP Information of Product-----------
	
	
	//----------Start- Penalty Information of Product-----------
	
	private String nonPenaltyPeriodForTopup;
	
	private Float penaltyPercentForTopup;
	
	private Float penaltyAmountForTopup;
	
	private Float penaltyPercentForNonSlabBasedWithdraw;
	
	private Float penaltyAmountForNonSlabBasedWithdraw;
	
	private Float penaltyforPrematureClosing;
	
	private Float penaltyForMissedPaymentForRecurring;
	
	private String nonPenaltyPeriodForWithdraw;
	
	
	// This modificationPenalty is for TenureChange/AmountChange for Recurring
	//---------------------------------
	
	private String nonPenaltyPeriodForModification;
	private String modificationBasis; //Monthly/Quarterly

	private Integer maximumLimitOfModification; // 3 times

	private Float modificationPenaltyPercent;
	
	private Float modificationPenaltyFlatAmount;
	
	private Float penaltyPercntForDepositConversion;
	//---------------------------------
	
	//----------End- Penalty Information of Product-----------
	
	//----------Start- GST Information of Product-------------
	private Float cgst;

	private Float sgst;

	private Float igst;
	//----------End- GST Information of Product---------------
	
	
	//----------Start- Sweep Information of Product-----------
	private Float minimumBalanceForSavingAccount;
	
	private Integer minimumSavingBalanceForSweepDeposit;
	
	private Integer minimumAmountRequiredForSweepDeposit;
	//----------End- Sweep Information of Product-----------
	
	private String createdBy;

	private Date createdDate;
	
	private String modifiedBy;

	private Date modifiedDate;
	
	private String paymentMode;
	
	//----------Start- Overdraft Information of Product-----------
	
	private Integer isOverdraftFacilityAvailable;
	
	private Integer minimumOverdraftPercentage;
	 
	private Integer maximumOverdraftPercentage;
	
	
	private Integer onlineMinimumOverdraftPercentage;
	 
	private Integer onlineMaximumOverdraftPercentage;
	
	private Float higherInterestRate;
	
	// Incremental Payments
    private Integer incrementalPayment;
	
	private Integer incrementalWithdraw;
	
	private Integer maximumOnlineDepositLimit;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
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

	public String getMinimumTenure() {
		return minimumTenure;
	}

	public void setMinimumTenure(String minimumTenure) {
		this.minimumTenure = minimumTenure;
	}

	public String getMaximumTenure() {
		return maximumTenure;
	}

	public void setMaximumTenure(String maximumTenure) {
		this.maximumTenure = maximumTenure;
	}

	public Integer getMinimumDepositAmount() {
		return minimumDepositAmount;
	}

	public void setMinimumDepositAmount(Integer minimumDepositAmount) {
		this.minimumDepositAmount = minimumDepositAmount;
	}

	public Integer getMaximumDepositAmount() {
		return maximumDepositAmount;
	}

	public void setMaximumDepositAmount(Integer maximumDepositAmount) {
		this.maximumDepositAmount = maximumDepositAmount;
	}

	public String getDefaultCurrency() {
		return defaultCurrency;
	}

	public void setDefaultCurrency(String defaultCurrency) {
		this.defaultCurrency = defaultCurrency;
	}

	public Integer getGracePeriodForRecurringPayment() {
		return gracePeriodForRecurringPayment;
	}

	public void setGracePeriodForRecurringPayment(Integer gracePeriodForRecurringPayment) {
		this.gracePeriodForRecurringPayment = gracePeriodForRecurringPayment;
	}

	public String getInterestCompoundingBasis() {
		return interestCompoundingBasis;
	}

	public void setInterestCompoundingBasis(String interestCompoundingBasis) {
		this.interestCompoundingBasis = interestCompoundingBasis;
	}

	public String getTdsCalculationOnBasis() {
		return tdsCalculationOnBasis;
	}

	public void setTdsCalculationOnBasis(String tdsCalculationOnBasis) {
		this.tdsCalculationOnBasis = tdsCalculationOnBasis;
	}

	public Float getTdsPercentForNoPAN() {
		return tdsPercentForNoPAN;
	}

	public void setTdsPercentForNoPAN(Float tdsPercentForNoPAN) {
		this.tdsPercentForNoPAN = tdsPercentForNoPAN;
	}

	public String getInterestCalculationBasis() {
		return interestCalculationBasis;
	}

	public void setInterestCalculationBasis(String interestCalculationBasis) {
		this.interestCalculationBasis = interestCalculationBasis;
	}

	public String getAnnuityBasis() {
		return annuityBasis;
	}

	public void setAnnuityBasis(String annuityBasis) {
		this.annuityBasis = annuityBasis;
	}

	public Integer getIsNomineeMandatory() {
		return isNomineeMandatory;
	}

	public void setIsNomineeMandatory(Integer isNomineeMandatory) {
		this.isNomineeMandatory = isNomineeMandatory;
	}

	public Integer getNomineeRequiredForEachAccHolder() {
		return nomineeRequiredForEachAccHolder;
	}

	public void setNomineeRequiredForEachAccHolder(Integer nomineeRequiredForEachAccHolder) {
		this.nomineeRequiredForEachAccHolder = nomineeRequiredForEachAccHolder;
	}

	public Integer getContributionRequiredForJointAccHolders() {
		return contributionRequiredForJointAccHolders;
	}

	public void setContributionRequiredForJointAccHolders(Integer contributionRequiredForJointAccHolders) {
		this.contributionRequiredForJointAccHolders = contributionRequiredForJointAccHolders;
	}

	public Date getProductStartDate() {
		return productStartDate;
	}

	public void setProductStartDate(Date productStartDate) {
		this.productStartDate = productStartDate;
	}

	public Date getProductEndDate() {
		return productEndDate;
	}

	public void setProductEndDate(Date productEndDate) {
		this.productEndDate = productEndDate;
	}

	public String getPaymentModeOnMaturity() {
		return paymentModeOnMaturity;
	}

	public void setPaymentModeOnMaturity(String paymentModeOnMaturity) {
		this.paymentModeOnMaturity = paymentModeOnMaturity;
	}

	public Integer getIsPrematureClosingioAllowedForTopup() {
		return isPrematureClosingioAllowedForTopup;
	}

	public void setIsPrematureClosingioAllowedForTopup(Integer isPrematureClosingioAllowedForTopup) {
		this.isPrematureClosingioAllowedForTopup = isPrematureClosingioAllowedForTopup;
	}

	public Integer getIsTopupAllowed() {
		return isTopupAllowed;
	}

	public void setIsTopupAllowed(Integer isTopupAllowed) {
		this.isTopupAllowed = isTopupAllowed;
	}

	public String getLockingPeriodForTopup() {
		return lockingPeriodForTopup;
	}

	public void setLockingPeriodForTopup(String lockingPeriodForTopup) {
		this.lockingPeriodForTopup = lockingPeriodForTopup;
	}

	public Integer getMinimumTopupAmount() {
		return minimumTopupAmount;
	}

	public void setMinimumTopupAmount(Integer minimumTopupAmount) {
		this.minimumTopupAmount = minimumTopupAmount;
	}

	public Integer getMaximumTopupAmount() {
		return maximumTopupAmount;
	}

	public void setMaximumTopupAmount(Integer maximumTopupAmount) {
		this.maximumTopupAmount = maximumTopupAmount;
	}

	public Integer getIsInterestAdjustmentRequiredForTopup() {
		return isInterestAdjustmentRequiredForTopup;
	}

	public void setIsInterestAdjustmentRequiredForTopup(Integer isInterestAdjustmentRequiredForTopup) {
		this.isInterestAdjustmentRequiredForTopup = isInterestAdjustmentRequiredForTopup;
	}

	public Integer getPreventionOfTopUpBeforeMaturity() {
		return preventionOfTopUpBeforeMaturity;
	}

	public void setPreventionOfTopUpBeforeMaturity(Integer preventionOfTopUpBeforeMaturity) {
		this.preventionOfTopUpBeforeMaturity = preventionOfTopUpBeforeMaturity;
	}

	public Integer getIsPrematureClosingioAllowedForWithdraw() {
		return isPrematureClosingioAllowedForWithdraw;
	}

	public void setIsPrematureClosingioAllowedForWithdraw(Integer isPrematureClosingioAllowedForWithdraw) {
		this.isPrematureClosingioAllowedForWithdraw = isPrematureClosingioAllowedForWithdraw;
	}

	public Integer getIsWithdrawAllowed() {
		return isWithdrawAllowed;
	}

	public void setIsWithdrawAllowed(Integer isWithdrawAllowed) {
		this.isWithdrawAllowed = isWithdrawAllowed;
	}

	public String getLockingPeriodForWithdraw() {
		return lockingPeriodForWithdraw;
	}

	public void setLockingPeriodForWithdraw(String lockingPeriodForWithdraw) {
		this.lockingPeriodForWithdraw = lockingPeriodForWithdraw;
	}

	public Integer getIsSlabBasedPenaltyRequiredForWithdraw() {
		return isSlabBasedPenaltyRequiredForWithdraw;
	}

	public void setIsSlabBasedPenaltyRequiredForWithdraw(Integer isSlabBasedPenaltyRequiredForWithdraw) {
		this.isSlabBasedPenaltyRequiredForWithdraw = isSlabBasedPenaltyRequiredForWithdraw;
	}

	public Integer getIsInterestAdjustmentRequiredForWithdraw() {
		return isInterestAdjustmentRequiredForWithdraw;
	}

	public void setIsInterestAdjustmentRequiredForWithdraw(Integer isInterestAdjustmentRequiredForWithdraw) {
		this.isInterestAdjustmentRequiredForWithdraw = isInterestAdjustmentRequiredForWithdraw;
	}

	public Integer getPreventionOfWithdrawBeforeMaturity() {
		return preventionOfWithdrawBeforeMaturity;
	}

	public void setPreventionOfWithdrawBeforeMaturity(Integer preventionOfWithdrawBeforeMaturity) {
		this.preventionOfWithdrawBeforeMaturity = preventionOfWithdrawBeforeMaturity;
	}

	public String getNonPenaltyPeriodForTopup() {
		return nonPenaltyPeriodForTopup;
	}

	public void setNonPenaltyPeriodForTopup(String nonPenaltyPeriodForTopup) {
		this.nonPenaltyPeriodForTopup = nonPenaltyPeriodForTopup;
	}

	public Float getPenaltyPercentForTopup() {
		return penaltyPercentForTopup;
	}

	public void setPenaltyPercentForTopup(Float penaltyPercentForTopup) {
		this.penaltyPercentForTopup = penaltyPercentForTopup;
	}

	public Float getPenaltyAmountForTopup() {
		return penaltyAmountForTopup;
	}

	public void setPenaltyAmountForTopup(Float penaltyAmountForTopup) {
		this.penaltyAmountForTopup = penaltyAmountForTopup;
	}

	public Float getPenaltyPercentForNonSlabBasedWithdraw() {
		return penaltyPercentForNonSlabBasedWithdraw;
	}

	public void setPenaltyPercentForNonSlabBasedWithdraw(Float penaltyPercentForNonSlabBasedWithdraw) {
		this.penaltyPercentForNonSlabBasedWithdraw = penaltyPercentForNonSlabBasedWithdraw;
	}

	public Float getPenaltyAmountForNonSlabBasedWithdraw() {
		return penaltyAmountForNonSlabBasedWithdraw;
	}

	public void setPenaltyAmountForNonSlabBasedWithdraw(Float penaltyAmountForNonSlabBasedWithdraw) {
		this.penaltyAmountForNonSlabBasedWithdraw = penaltyAmountForNonSlabBasedWithdraw;
	}

	public Float getPenaltyforPrematureClosing() {
		return penaltyforPrematureClosing;
	}

	public void setPenaltyforPrematureClosing(Float penaltyforPrematureClosing) {
		this.penaltyforPrematureClosing = penaltyforPrematureClosing;
	}

	public Float getPenaltyForMissedPaymentForRecurring() {
		return penaltyForMissedPaymentForRecurring;
	}

	public void setPenaltyForMissedPaymentForRecurring(Float penaltyForMissedPaymentForRecurring) {
		this.penaltyForMissedPaymentForRecurring = penaltyForMissedPaymentForRecurring;
	}

	public String getNonPenaltyPeriodForWithdraw() {
		return nonPenaltyPeriodForWithdraw;
	}

	public void setNonPenaltyPeriodForWithdraw(String nonPenaltyPeriodForWithdraw) {
		this.nonPenaltyPeriodForWithdraw = nonPenaltyPeriodForWithdraw;
	}

	public String getNonPenaltyPeriodForModification() {
		return nonPenaltyPeriodForModification;
	}

	public void setNonPenaltyPeriodForModification(String nonPenaltyPeriodForModification) {
		this.nonPenaltyPeriodForModification = nonPenaltyPeriodForModification;
	}

	public String getModificationBasis() {
		return modificationBasis;
	}

	public void setModificationBasis(String modificationBasis) {
		this.modificationBasis = modificationBasis;
	}

	public Integer getMaximumLimitOfModification() {
		return maximumLimitOfModification;
	}

	public void setMaximumLimitOfModification(Integer maximumLimitOfModification) {
		this.maximumLimitOfModification = maximumLimitOfModification;
	}

	public Float getModificationPenaltyPercent() {
		return modificationPenaltyPercent;
	}

	public void setModificationPenaltyPercent(Float modificationPenaltyPercent) {
		this.modificationPenaltyPercent = modificationPenaltyPercent;
	}

	public Float getModificationPenaltyFlatAmount() {
		return modificationPenaltyFlatAmount;
	}

	public void setModificationPenaltyFlatAmount(Float modificationPenaltyFlatAmount) {
		this.modificationPenaltyFlatAmount = modificationPenaltyFlatAmount;
	}

	public Float getPenaltyPercntForDepositConversion() {
		return penaltyPercntForDepositConversion;
	}

	public void setPenaltyPercntForDepositConversion(Float penaltyPercntForDepositConversion) {
		this.penaltyPercntForDepositConversion = penaltyPercntForDepositConversion;
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

	public Float getMinimumBalanceForSavingAccount() {
		return minimumBalanceForSavingAccount;
	}

	public void setMinimumBalanceForSavingAccount(Float minimumBalanceForSavingAccount) {
		this.minimumBalanceForSavingAccount = minimumBalanceForSavingAccount;
	}

	public Integer getMinimumSavingBalanceForSweepDeposit() {
		return minimumSavingBalanceForSweepDeposit;
	}

	public void setMinimumSavingBalanceForSweepDeposit(Integer minimumSavingBalanceForSweepDeposit) {
		this.minimumSavingBalanceForSweepDeposit = minimumSavingBalanceForSweepDeposit;
	}

	public Integer getMinimumAmountRequiredForSweepDeposit() {
		return minimumAmountRequiredForSweepDeposit;
	}

	public void setMinimumAmountRequiredForSweepDeposit(Integer minimumAmountRequiredForSweepDeposit) {
		this.minimumAmountRequiredForSweepDeposit = minimumAmountRequiredForSweepDeposit;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public Integer getIsOverdraftFacilityAvailable() {
		return isOverdraftFacilityAvailable;
	}

	public void setIsOverdraftFacilityAvailable(Integer isOverdraftFacilityAvailable) {
		this.isOverdraftFacilityAvailable = isOverdraftFacilityAvailable;
	}

	public Integer getMinimumOverdraftPercentage() {
		return minimumOverdraftPercentage;
	}

	public void setMinimumOverdraftPercentage(Integer minimumOverdraftPercentage) {
		this.minimumOverdraftPercentage = minimumOverdraftPercentage;
	}

	public Integer getMaximumOverdraftPercentage() {
		return maximumOverdraftPercentage;
	}

	public void setMaximumOverdraftPercentage(Integer maximumOverdraftPercentage) {
		this.maximumOverdraftPercentage = maximumOverdraftPercentage;
	}

	
	
	public Integer getOnlineMinimumOverdraftPercentage() {
		return onlineMinimumOverdraftPercentage;
	}

	public void setOnlineMinimumOverdraftPercentage(Integer onlineMinimumOverdraftPercentage) {
		this.onlineMinimumOverdraftPercentage = onlineMinimumOverdraftPercentage;
	}

	public Integer getOnlineMaximumOverdraftPercentage() {
		return onlineMaximumOverdraftPercentage;
	}

	public void setOnlineMaximumOverdraftPercentage(Integer onlineMaximumOverdraftPercentage) {
		this.onlineMaximumOverdraftPercentage = onlineMaximumOverdraftPercentage;
	}

	
	

	public Float getHigherInterestRate() {
		return higherInterestRate;
	}

	public void setHigherInterestRate(Float higherInterestRate) {
		this.higherInterestRate = higherInterestRate;
	}

	public Integer getIncrementalPayment() {
		return incrementalPayment;
	}

	public void setIncrementalPayment(Integer incrementalPayment) {
		this.incrementalPayment = incrementalPayment;
	}

	public Integer getIncrementalWithdraw() {
		return incrementalWithdraw;
	}

	public void setIncrementalWithdraw(Integer incrementalWithdraw) {
		this.incrementalWithdraw = incrementalWithdraw;
	}

	public Integer getMaximumOnlineDepositLimit() {
		return maximumOnlineDepositLimit;
	}

	public void setMaximumOnlineDepositLimit(Integer maximumOnlineDepositLimit) {
		this.maximumOnlineDepositLimit = maximumOnlineDepositLimit;
	}
	
	
	
}
