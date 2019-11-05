package binding.FormBinding.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Employee {
	private String name;
	@Id
	private long id;
	private String contactNumber;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

}
