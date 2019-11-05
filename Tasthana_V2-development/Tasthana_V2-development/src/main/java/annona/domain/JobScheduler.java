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
public class JobScheduler {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	private String cronExpression;
	
	private String jobName;
	
	private Date startDate;
	
	private Date endDate;
	
	private String cronMeaning;
	
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getCronMeaning() {
		return cronMeaning;
	}
	public void setCronMeaning(String cronMeaning) {
		this.cronMeaning = cronMeaning;
	}
	
	
}
