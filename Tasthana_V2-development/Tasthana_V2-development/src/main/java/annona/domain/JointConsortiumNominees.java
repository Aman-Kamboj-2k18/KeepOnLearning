package annona.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Configurable;

/**
 * Class for Joint/Consortium Account holders nominee details
 * 
 * @author Goutame
 *
 */
@Entity
@Configurable
public class JointConsortiumNominees {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	private Integer age;

	private String address;

	private String relationship;

	private String panNo;

	private Long aadharNo;

	private String gaurdianName;

	private Integer gaurdianAge;

	private String gaurdianAddress;

	private String gaurdianRelation;

	private Long gaurdianAadharNo;

	private String gaurdianPanNo;

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

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public String getGaurdianName() {
		return gaurdianName;
	}

	public void setGaurdianName(String gaurdianName) {
		this.gaurdianName = gaurdianName;
	}

	public Integer getGaurdianAge() {
		return gaurdianAge;
	}

	public void setGaurdianAge(Integer gaurdianAge) {
		this.gaurdianAge = gaurdianAge;
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

	
	public String getPanNo() {
		return panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	public Long getAadharNo() {
		return aadharNo;
	}

	public void setAadharNo(Long aadharNo) {
		this.aadharNo = aadharNo;
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

}
