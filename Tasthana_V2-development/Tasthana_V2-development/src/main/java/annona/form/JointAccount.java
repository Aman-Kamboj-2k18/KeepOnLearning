package annona.form;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import annona.domain.EndUser;
import annona.domain.JointConsortiumNominees;


@Component
public class JointAccount {
	
	private Long id;
	
	private String name;
	
	private String gender;
	
	private String age;
	
	private String contactNo;
	
	private String email;
	
	private String address;
	
	private String city;
	
	private String state;
	
	private String country;
	
	private String pincode;
	
	private String depositType;
	
	private Float contributionPercent;
	
	private String relationship;

	private Contributions contributions;
	
	private JointConsortiumNominees nominee;	
	
	 private String nomineePanNo;
		
	 private Long nomineeAadharNo;
	 
	 private Long gaurdianAadharNo;
	 
	 private String gaurdianPanNo;
	 private Long chequeNo;
	 private String chequeName;
	 private float chequeAmount;
	 private String deathCertificateSubmitted;
	 private Date chequeDate;
	 private String chequeBank;
	 private String chequeBranch;
	 
	
	public Long getChequeNo() {
		return chequeNo;
	}

	public void setChequeNo(Long chequeNo) {
		this.chequeNo = chequeNo;
	}

	public String getChequeName() {
		return chequeName;
	}

	public void setChequeName(String chequeName) {
		this.chequeName = chequeName;
	}

	public float getChequeAmount() {
		return chequeAmount;
	}

	public void setChequeAmount(float chequeAmount) {
		this.chequeAmount = chequeAmount;
	}

	public String getDeathCertificateSubmitted() {
		return deathCertificateSubmitted;
	}

	public void setDeathCertificateSubmitted(String deathCertificateSubmitted) {
		this.deathCertificateSubmitted = deathCertificateSubmitted;
	}

	public Date getChequeDate() {
		return chequeDate;
	}

	public void setChequeDate(Date chequeDate) {
		this.chequeDate = chequeDate;
	}

	public String getChequeBank() {
		return chequeBank;
	}

	public void setChequeBank(String chequeBank) {
		this.chequeBank = chequeBank;
	}

	public String getChequeBranch() {
		return chequeBranch;
	}

	public void setChequeBranch(String chequeBranch) {
		this.chequeBranch = chequeBranch;
	}

	public Float getContributionPercent() {
		return contributionPercent;
	}

	public void setContributionPercent(Float contributionPercent) {
		this.contributionPercent = contributionPercent;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getDepositType() {
		return depositType;
	}

	public void setDepositType(String depositType) {
		this.depositType = depositType;
	}


	public Contributions getContributions() {
		return contributions;
	}

	public void setContributions(Contributions contributions) {
		this.contributions = contributions;
	}

	public JointConsortiumNominees getNominee() {
		return nominee;
	}

	public void setNominee(JointConsortiumNominees nominee) {
		this.nominee = nominee;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public String getNomineePanNo() {
		return nomineePanNo;
	}

	public void setNomineePanNo(String nomineePanNo) {
		this.nomineePanNo = nomineePanNo;
	}

	public Long getNomineeAadharNo() {
		return nomineeAadharNo;
	}

	public void setNomineeAadharNo(Long nomineeAadharNo) {
		this.nomineeAadharNo = nomineeAadharNo;
	}

	public Long getGaurdianAadharNo() {
		return gaurdianAadharNo;
	}

	public void setGaurdianAadharNo(Long gaurdianAadharNo) {
		this.gaurdianAadharNo = gaurdianAadharNo;
	}

	public String getGaurdianPanNo() {
		return gaurdianPanNo;
	}

	public void setGaurdianPanNo(String gaurdianPanNo) {
		this.gaurdianPanNo = gaurdianPanNo;
	}

	// Maturity Disbursement
		//----------------------------------------------
		private Integer isMaturityDisbrsmntInLinkedAccount;
		
		private Integer isMaturityDisbrsmntInSameBank;
		
		private String maturityDisbrsmntAccHolderName;
		
		private String maturityDisbrsmntAccNumber;
		
		private String maturityDisbrsmntTransferType;
		
		private String maturityDisbrsmntBankName;

		private String maturityDisbrsmntBankIFSCCode;
		//----------------------------------------------

		public Integer getIsMaturityDisbrsmntInLinkedAccount() {
			return isMaturityDisbrsmntInLinkedAccount;
		}

		public void setIsMaturityDisbrsmntInLinkedAccount(Integer isMaturityDisbrsmntInLinkedAccount) {
			this.isMaturityDisbrsmntInLinkedAccount = isMaturityDisbrsmntInLinkedAccount;
		}

		public Integer getIsMaturityDisbrsmntInSameBank() {
			return isMaturityDisbrsmntInSameBank;
		}

		public void setIsMaturityDisbrsmntInSameBank(Integer isMaturityDisbrsmntInSameBank) {
			this.isMaturityDisbrsmntInSameBank = isMaturityDisbrsmntInSameBank;
		}

		public String getMaturityDisbrsmntAccHolderName() {
			return maturityDisbrsmntAccHolderName;
		}

		public void setMaturityDisbrsmntAccHolderName(String maturityDisbrsmntAccHolderName) {
			this.maturityDisbrsmntAccHolderName = maturityDisbrsmntAccHolderName;
		}

		public String getMaturityDisbrsmntAccNumber() {
			return maturityDisbrsmntAccNumber;
		}

		public void setMaturityDisbrsmntAccNumber(String maturityDisbrsmntAccNumber) {
			this.maturityDisbrsmntAccNumber = maturityDisbrsmntAccNumber;
		}

		public String getMaturityDisbrsmntTransferType() {
			return maturityDisbrsmntTransferType;
		}

		public void setMaturityDisbrsmntTransferType(String maturityDisbrsmntTransferType) {
			this.maturityDisbrsmntTransferType = maturityDisbrsmntTransferType;
		}

		public String getMaturityDisbrsmntBankName() {
			return maturityDisbrsmntBankName;
		}

		public void setMaturityDisbrsmntBankName(String maturityDisbrsmntBankName) {
			this.maturityDisbrsmntBankName = maturityDisbrsmntBankName;
		}

		public String getMaturityDisbrsmntBankIFSCCode() {
			return maturityDisbrsmntBankIFSCCode;
		}

		public void setMaturityDisbrsmntBankIFSCCode(String maturityDisbrsmntBankIFSCCode) {
			this.maturityDisbrsmntBankIFSCCode = maturityDisbrsmntBankIFSCCode;
		}
	
}
