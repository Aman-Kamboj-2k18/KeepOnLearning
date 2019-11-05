package annona.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Configurable;

@Entity
@Configurable
@XmlRootElement

public class MonthEndProcess {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private Long id;

	private String month;

	private Integer year;
	
	private Date lastProcessingDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Date getLastProcessingDate() {
		return lastProcessingDate;
	}

	public void setLastProcessingDate(Date lastProcessingDate) {
		this.lastProcessingDate = lastProcessingDate;
	}

	
}
