package annona.form;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"id",
"relationship"
})
public class CustomerDetailsParser {

@JsonProperty("id")
private String id;
@JsonProperty("relationship")
private String relationship;

private String isPrimaryHolder;


private String email;
private String address;
private String contactNum;
private String customerName;
private String gender;

@JsonProperty("id")
public String getId() {
return id;
}

@JsonProperty("id")
public void setId(String id) {
this.id = id;
}

@JsonProperty("relationship")
public String getRelationship() {
return relationship;
}

@JsonProperty("relationship")
public void setRelationship(String relationship) {
this.relationship = relationship;
}



private List<CustomerDetailsParser> data = null;


public List<CustomerDetailsParser> getData() {
	return data;
}

public void setData(List<CustomerDetailsParser> data) {
	this.data = data;
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

public String getContactNum() {
	return contactNum;
}

public void setContactNum(String contactNum) {
	this.contactNum = contactNum;
}

public String getCustomerName() {
	return customerName;
}

public void setCustomerName(String customerName) {
	this.customerName = customerName;
}

public String getGender() {
	return gender;
}

public void setGender(String gender) {
	this.gender = gender;
}

public String getIsPrimaryHolder() {
	return isPrimaryHolder;
}

public void setIsPrimaryHolder(String isPrimaryHolder) {
	this.isPrimaryHolder = isPrimaryHolder;
}




}