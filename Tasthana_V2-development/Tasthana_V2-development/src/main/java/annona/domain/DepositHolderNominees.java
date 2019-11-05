package annona.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Configurable;

@Entity
@Configurable
@Table
@XmlRootElement
public class DepositHolderNominees {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private Long id;
	
	private Long depositId;

	private Long depositHolderId;

	private String nomineeName;

	private String nomineeAge;

	private String nomineeAddress;

	private String nomineeRelationship;

	private String gaurdianName;

	private String gaurdianAddress;

	private String gaurdianRelation;

	private Float gaurdianAge;

	private String nomineePan;

	private Long nomineeAadhar;

	private Long gaurdianAadhar;

	private String gaurdianPan;
	
	private Integer isMaturityAmountTransferred;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public Long getDepositId() {
		return depositId;
	}

	public void setDepositId(Long depositId) {
		this.depositId = depositId;
	}

	public Long getDepositHolderId() {
		return depositHolderId;
	}

	public void setDepositHolderId(Long depositHolderId) {
		this.depositHolderId = depositHolderId;
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

	public String getNomineeRelationship() {
		return nomineeRelationship;
	}

	public void setNomineeRelationship(String nomineeRelationship) {
		this.nomineeRelationship = nomineeRelationship;
	}

	public String getGaurdianName() {
		return gaurdianName;
	}

	public void setGaurdianName(String gaurdianName) {
		this.gaurdianName = gaurdianName;
	}

	public String getGaurdianAddress() {
		return gaurdianAddress;
	}

	public void setGaurdianAddress(String gaurdianAddress) {
		this.gaurdianAddress = gaurdianAddress;
	}

	public String getGaurdianRelation() {
		return gaurdianRelation;
	}

	public void setGaurdianRelation(String gaurdianRelation) {
		this.gaurdianRelation = gaurdianRelation;
	}

	public Float getGaurdianAge() {
		return gaurdianAge;
	}

	public void setGaurdianAge(Float gaurdianAge) {
		this.gaurdianAge = gaurdianAge;
	}

	public String getNomineePan() {
		return nomineePan;
	}

	public void setNomineePan(String nomineePan) {
		this.nomineePan = nomineePan;
	}

	public Long getNomineeAadhar() {
		return nomineeAadhar;
	}

	public void setNomineeAadhar(Long nomineeAadhar) {
		this.nomineeAadhar = nomineeAadhar;
	}

	public Long getGaurdianAadhar() {
		return gaurdianAadhar;
	}

	public void setGaurdianAadhar(Long gaurdianAadhar) {
		this.gaurdianAadhar = gaurdianAadhar;
	}

	public String getGaurdianPan() {
		return gaurdianPan;
	}

	public void setGaurdianPan(String gaurdianPan) {
		this.gaurdianPan = gaurdianPan;
	}

	public Integer getIsMaturityAmountTransferred() {
		return isMaturityAmountTransferred;
	}

	public void setIsMaturityAmountTransferred(Integer isMaturityAmountTransferred) {
		this.isMaturityAmountTransferred = isMaturityAmountTransferred;
	}

}
