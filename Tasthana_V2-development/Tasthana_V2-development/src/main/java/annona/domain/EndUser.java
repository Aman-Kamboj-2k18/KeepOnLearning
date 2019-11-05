package annona.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Configurable;

@Entity
@Configurable
@XmlRootElement
public class EndUser {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	private String userName;

	private Integer role;

	private String displayName;

	private String roleType;

	private String password;

	private String userEmail;

	private String currentRole;

	private String customerHeadKey;

	private String customerHeadName;

	private String theme;

	private String prefferedLanguage;

	private String contactNo;

	private String altContactNo;

	private String email;

	private String altEmail;

	private String status;

	private float approvallimit;

	private String transactionId;

	private String notificationStatus;

	private Integer passwordFlag;

	private Date pwdExpiryDate;

	private String accessStatus;

	private Date accExpiryDate;

	private String accRenewStatus;

	private String imageName;

	private byte[] image;

	private String bankId;

	private String designation;
	
	private Long organisationId;
	
	private Date startDate;
	
	private Long customerId;
	
	
	
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "ENDUSER_ROLE",
            joinColumns = {@JoinColumn(name = "ENDUSER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")})
    private List<Role> roles;
	
	
	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public EndUser() {
		super();
	}

	public EndUser(Long id, String userName, String currentRole, String accessStatus, Date accExpiryDate,
			String accRenewStatus) {
		super();
		this.id = id;
		this.userName = userName;
		this.currentRole = currentRole;
		this.accessStatus = accessStatus;
		this.accExpiryDate = accExpiryDate;
		this.accRenewStatus = accRenewStatus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getCurrentRole() {
		return currentRole;
	}

	public void setCurrentRole(String currentRole) {
		this.currentRole = currentRole;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getPrefferedLanguage() {
		return prefferedLanguage;
	}

	public void setPrefferedLanguage(String prefferedLanguage) {
		this.prefferedLanguage = prefferedLanguage;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getAltContactNo() {
		return altContactNo;
	}

	public void setAltContactNo(String altContactNo) {
		this.altContactNo = altContactNo;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public float getApprovallimit() {
		return approvallimit;
	}

	public void setApprovallimit(float approvallimit) {
		this.approvallimit = approvallimit;
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

	public String getCustomerHeadKey() {
		return customerHeadKey;
	}

	public void setCustomerHeadKey(String customerHeadKey) {
		this.customerHeadKey = customerHeadKey;
	}

	public String getCustomerHeadName() {
		return customerHeadName;
	}

	public void setCustomerHeadName(String customerHeadName) {
		this.customerHeadName = customerHeadName;
	}

	public Integer getPasswordFlag() {
		return passwordFlag;
	}

	public void setPasswordFlag(Integer passwordFlag) {
		this.passwordFlag = passwordFlag;
	}

	public Date getPwdExpiryDate() {
		return pwdExpiryDate;
	}

	public void setPwdExpiryDate(Date pwdExpiryDate) {
		this.pwdExpiryDate = pwdExpiryDate;
	}

	public String getAccessStatus() {
		return accessStatus;
	}

	public void setAccessStatus(String accessStatus) {
		this.accessStatus = accessStatus;
	}

	public Date getAccExpiryDate() {
		return accExpiryDate;
	}

	public void setAccExpiryDate(Date accExpiryDate) {
		this.accExpiryDate = accExpiryDate;
	}

	public String getAccRenewStatus() {
		return accRenewStatus;
	}

	public void setAccRenewStatus(String accRenewStatus) {
		this.accRenewStatus = accRenewStatus;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public Long getOrganisationId() {
		return organisationId;
	}

	public void setOrganisationId(Long organisationId) {
		this.organisationId = organisationId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	



}
