package annona.form;


import org.springframework.stereotype.Component;

@Component

public class BeneficiaryForm {

	private String benificiaryId;
	
	private String benificiaryName;

	private String bankAccountType;

	private String ifscCode;

	private String remarks;

	private String amountToTransfer;
	
	private String benificiaryAccountNumber;
	
	private String isActive;

	public String getBenificiaryName() {
		return benificiaryName;
	}

	public void setBenificiaryName(String benificiaryName) {
		this.benificiaryName = benificiaryName;
	}

	public String getBankAccountType() {
		return bankAccountType;
	}

	public void setBankAccountType(String bankAccountType) {
		this.bankAccountType = bankAccountType;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getAmountToTransfer() {
		return amountToTransfer;
	}

	public void setAmountToTransfer(String amountToTransfer) {
		this.amountToTransfer = amountToTransfer;
	}

	public String getBenificiaryAccountNumber() {
		return benificiaryAccountNumber;
	}

	public void setBenificiaryAccountNumber(String benificiaryAccountNumber) {
		this.benificiaryAccountNumber = benificiaryAccountNumber;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getBenificiaryId() {
		return benificiaryId;
	}

	public void setBenificiaryId(String benificiaryId) {
		this.benificiaryId = benificiaryId;
	}

	

	}
	