package annona.domain;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.beans.factory.annotation.Configurable;

import java.io.Serializable;
import java.util.*;

@SuppressWarnings("serial")
@Entity
@Configurable
@Table(name = "holiday_configuration")
@XmlRootElement
public class HolidayConfiguration implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name = "holiday_dates", joinColumns = @JoinColumn(name = "id"))
	@org.hibernate.annotations.Cascade(value=org.hibernate.annotations.CascadeType.ALL)
	
	@Column(name = "holiday_dates")
	private List<Date> date;
	@Override
	public String toString() {
		return "HolidayConfiguration [id=" + id + ", date=" + date + ", createdOn=" + createdOn + ", modifiedOn="
				+ modifiedOn + ", year=" + year + ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy
				+ ", branchCode=" + branchCode + ", state=" + state + "]";
	}
	@Column(name = "created_on")
	private java.util.Date createdOn;
	
	@Column(name = "modified_on")
	//@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date modifiedOn;
	@Column(name = "year")
	private int year;
	
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "modified_by")
	private String modifiedBy;
	
	private transient String dates;
	
	
	private  String branchCode;
	
	
	private String state;
	
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<Date> getDate() {
		return date;
	}
	public void setDate(List<Date> date) {
		this.date = date;
	}
	public java.util.Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(java.util.Date createdOn) {
		this.createdOn = createdOn;
	}
	public java.util.Date getModifiedOn() {
		return modifiedOn;
	}
	public void setModifiedOn(java.util.Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	public String getDates() {
		return dates;
	}
	public void setDates(String dates) {
		this.dates = dates;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	
}
