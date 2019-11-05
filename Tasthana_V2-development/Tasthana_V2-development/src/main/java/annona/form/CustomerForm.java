package annona.form;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import annona.domain.AccountDetails;

@Component
public class CustomerForm {

	private long id;

	private String category;

	private String customerName;

	private String customerID;

	private String gender;

	private String age;

	private String nomineeName;

	private String nomineeAge;

	private String nomineeAddress;

	private String nomineeRelationShip;

	private String guardianName;

	private String guardianAge;

	private String guardianAddress;

	private String guardianRelationShip;

	private String address;

	private String city;

	private String pincode;

	private String country;

	private String state;

	private String contactNum;

	private String altContactNum;

	private String email;

	private String altEmail;

	private String accountId;

	private String accountNo;

	private String accountType;

	private String accountBalance;
	
	private String minimumBalance;

	private String uniqueKey;

	private String comment;

	private String status;

	private Date dateOfBirth;

	private Date approveDate;

	private String password;

	private String transactionId;

	private String notificationStatus;

	private String userName;

	private String pan;

	private Long aadhar;

	private Long guardianAadhar;

	private String guardianPan;

    private String citizen;
	
	private String nriAccountType;
	
	private List<AccountDetails> accountDetails;

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getContactNum() {
		return contactNum;
	}

	public void setContactNum(String contactNum) {
		this.contactNum = contactNum;
	}

	public String getAltContactNum() {
		return altContactNum;
	}

	public void setAltContactNum(String altContactNum) {
		this.altContactNum = altContactNum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAltEmail() {
		return altEmail;
	}

	public void setAltEmail(String altEmail) {
		this.altEmail = altEmail;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public void setAccountBalance(String accountBalance) {
		this.accountBalance = accountBalance;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAccountBalance() {
		return accountBalance;
	}

	
	public String getMinimumBalance() {
		return minimumBalance;
	}

	public void setMinimumBalance(String minimumBalance) {
		this.minimumBalance = minimumBalance;
	}

	public String getUniqueKey() {
		return uniqueKey;
	}

	public void setUniqueKey(String uniqueKey) {
		this.uniqueKey = uniqueKey;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getNotificationStatus() {
		return notificationStatus;
	}

	public void setNotificationStatus(String notificationStatus) {
		this.notificationStatus = notificationStatus;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getNomineeName() {
		return nomineeName;
	}

	public void setNomineeName(String nomineeName) {
		this.nomineeName = nomineeName;
	}

	public String getNomineeAge() {
		return nomineeAge;
	}

	public void setNomineeAge(String nomineeAge) {
		this.nomineeAge = nomineeAge;
	}

	public String getNomineeAddress() {
		return nomineeAddress;
	}

	public void setNomineeAddress(String nomineeAddress) {
		this.nomineeAddress = nomineeAddress;
	}

	public String getNomineeRelationShip() {
		return nomineeRelationShip;
	}

	public void setNomineeRelationShip(String nomineeRelationShip) {
		this.nomineeRelationShip = nomineeRelationShip;
	}

	public String getGuardianName() {
		return guardianName;
	}

	public void setGuardianName(String guardianName) {
		this.guardianName = guardianName;
	}

	public String getGuardianAge() {
		return guardianAge;
	}

	public void setGuardianAge(String guardianAge) {
		this.guardianAge = guardianAge;
	}

	public String getGuardianAddress() {
		return guardianAddress;
	}

	public void setGuardianAddress(String guardianAddress) {
		this.guardianAddress = guardianAddress;
	}

	public String getGuardianRelationShip() {
		return guardianRelationShip;
	}

	public void setGuardianRelationShip(String guardianRelationShip) {
		this.guardianRelationShip = guardianRelationShip;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<AccountDetails> getAccountDetails() {
		return accountDetails;
	}

	public void setAccountDetails(List<AccountDetails> accountDetails) {
		this.accountDetails = accountDetails;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public Long getAadhar() {
		return aadhar;
	}

	public void setAadhar(Long aadhar) {
		this.aadhar = aadhar;
	}

	public Long getGuardianAadhar() {
		return guardianAadhar;
	}

	public void setGuardianAadhar(Long guardianAadhar) {
		this.guardianAadhar = guardianAadhar;
	}

	public String getGuardianPan() {
		return guardianPan;
	}

	public void setGuardianPan(String guardianPan) {
		this.guardianPan = guardianPan;
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
