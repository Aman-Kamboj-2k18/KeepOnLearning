package annona.form;

import org.springframework.stereotype.Component;

@Component
public class Contributions {

	private String paymentType;

	private Float interestPercentage;

	private Float interestAmtPart;

	private String payOffAccPartOne;

	private String payOffAccPartTwo;

	private String transferModeOne;

	private String beneficiaryOne;

	private String beneficiaryAccOne;

	private String beneficiaryBankOne;

	private String beneficiaryIFSCOne;

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Float getInterestPercentage() {
		return interestPercentage;
	}

	public void setInterestPercentage(Float interestPercentage) {
		this.interestPercentage = interestPercentage;
	}

	public Float getInterestAmtPart() {
		return interestAmtPart;
	}

	public void setInterestAmtPart(Float interestAmtPart) {
		this.interestAmtPart = interestAmtPart;
	}

	public String getPayOffAccPartOne() {
		return payOffAccPartOne;
	}

	public void setPayOffAccPartOne(String payOffAccPartOne) {
		this.payOffAccPartOne = payOffAccPartOne;
	}

	public String getPayOffAccPartTwo() {
		return payOffAccPartTwo;
	}

	public void setPayOffAccPartTwo(String payOffAccPartTwo) {
		this.payOffAccPartTwo = payOffAccPartTwo;
	}

	public String getTransferModeOne() {
		return transferModeOne;
	}

	public void setTransferModeOne(String transferModeOne) {
		this.transferModeOne = transferModeOne;
	}

	public String getBeneficiaryOne() {
		return beneficiaryOne;
	}

	public void setBeneficiaryOne(String beneficiaryOne) {
		this.beneficiaryOne = beneficiaryOne;
	}

	public String getBeneficiaryAccOne() {
		return beneficiaryAccOne;
	}

	public void setBeneficiaryAccOne(String beneficiaryAccOne) {
		this.beneficiaryAccOne = beneficiaryAccOne;
	}

	public String getBeneficiaryBankOne() {
		return beneficiaryBankOne;
	}

	public void setBeneficiaryBankOne(String beneficiaryBankOne) {
		this.beneficiaryBankOne = beneficiaryBankOne;
	}

	public String getBeneficiaryIFSCOne() {
		return beneficiaryIFSCOne;
	}

	public void setBeneficiaryIFSCOne(String beneficiaryIFSCOne) {
		this.beneficiaryIFSCOne = beneficiaryIFSCOne;
	}

}
