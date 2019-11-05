package annona.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import annona.domain.JobScheduler;

@Repository
public class JobSchedulerDAOImpl implements JobSchedulerDAO {
	@PersistenceContext
	EntityManager em;

	public JobScheduler findByJobName(String jobName) {

		Query query = em.createQuery("SELECT o FROM JobScheduler o where o.jobName=:jobName");
		query.setParameter("jobName", jobName);
		List<JobScheduler> jobs = query.getResultList();
		if (jobs.size() > 0)
			return jobs.get(0);
		else
			return null;
	}

	@Override
	public JobScheduler getByJobNameAndEndDate(String jobName, Date todaysDate) {

	
		 String sql ="SELECT * FROM JobScheduler o where jobName=:jobName AND "
					+ "CASE WHEN endDate IS NOT NULL THEN endDate>=:todaysDate ELSE 1=1 END";
		
		 Query query = em.createNativeQuery(sql, JobScheduler.class);
		
		query.setParameter("jobName",jobName);
		query.setParameter("todaysDate",todaysDate);
		@SuppressWarnings("unchecked")
		List<JobScheduler> jobs = query.getResultList();
		if (jobs.size() > 0)
			return jobs.get(0);
		else
			return null;

	}

	@Transactional
	public void saveJob(JobScheduler jobScheduler) {

		em.persist(jobScheduler);
		em.flush();

	}

	@Transactional
	public void updateJob(JobScheduler jobScheduler) {
		em.merge(jobScheduler);
		em.flush();

	}


	@Override
	public List<JobScheduler> findAllJobs(){
		
		Query query = em.createQuery("SELECT o FROM JobScheduler o");

		List<JobScheduler> jobs = query.getResultList();
		if (jobs.size() > 0)
			return jobs;
		else
			return null;

	}
}
