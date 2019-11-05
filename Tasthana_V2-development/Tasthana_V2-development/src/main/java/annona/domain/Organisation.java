package annona.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Configurable;

@Entity
@Configurable
@XmlRootElement
public class Organisation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "organisationId")
	private Long organisationId;
	
    private String organisationName;
    
    private String organisationShortName;
	
	private String organisationEmail;
	
	private String organisationContact;
	
	private String organisationAddress;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="organisationId")
	private Set<EndUser> users; 
	

	public Set<EndUser> getUsers() {
		return users;
	}

	public void setUsers(Set<EndUser> users) {
		this.users = users;
	}

	
	public Long getOrganisationId() {
		return organisationId;
	}

	public void setOrganisationId(Long organisationId) {
		this.organisationId = organisationId;
	}

	public String getOrganisationName() {
		return organisationName;
	}

	public void setOrganisationName(String organisationName) {
		this.organisationName = organisationName;
	}

	public String getOrganisationShortName() {
		return organisationShortName;
	}

	public void setOrganisationShortName(String organisationShortName) {
		this.organisationShortName = organisationShortName;
	}

	public String getOrganisationEmail() {
		return organisationEmail;
	}

	public void setOrganisationEmail(String organisationEmail) {
		this.organisationEmail = organisationEmail;
	}

	public String getOrganisationContact() {
		return organisationContact;
	}

	public void setOrganisationContact(String organisationContact) {
		this.organisationContact = organisationContact;
	}

	public String getOrganisationAddress() {
		return organisationAddress;
	}

	public void setOrganisationAddress(String organisationAddress) {
		this.organisationAddress = organisationAddress;
	}
	


}
