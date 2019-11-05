package annona.form;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class ScheduleTaskForm {

	private String jobName;

	private String schedulerDuration;

	private Date startdate;
	
	private String starttime;
	
	private Date enddate;
	
	private String monthOfYear;
	
	private Integer dayOfMonth;
	
	private String week;
	
	private Date calculateTillDate;
	
	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getSchedulerDuration() {
		return schedulerDuration;
	}

	public void setSchedulerDuration(String schedulerDuration) {
		this.schedulerDuration = schedulerDuration;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public String getMonthOfYear() {
		return monthOfYear;
	}

	public void setMonthOfYear(String monthOfYear) {
		this.monthOfYear = monthOfYear;
	}

	public Integer getDayOfMonth() {
		return dayOfMonth;
	}

	public void setDayOfMonth(Integer dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public Date getCalculateTillDate() {
		return calculateTillDate;
	}

	public void setCalculateTillDate(Date calculateTillDate) {
		this.calculateTillDate = calculateTillDate;
	}

	
}
