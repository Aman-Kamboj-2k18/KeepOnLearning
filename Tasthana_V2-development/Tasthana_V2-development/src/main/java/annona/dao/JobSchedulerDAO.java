package annona.dao;

import java.util.Date;
import java.util.List;

import annona.domain.FormSubmission;
import annona.domain.JobScheduler;

public interface JobSchedulerDAO {

	public JobScheduler findByJobName(String jobName);
	
	public JobScheduler getByJobNameAndEndDate(String jobName, Date date);
	
	public void saveJob(JobScheduler jobScheduler);
	
	public void updateJob(JobScheduler jobScheduler);
	

	public List<JobScheduler> findAllJobs();
	
}
