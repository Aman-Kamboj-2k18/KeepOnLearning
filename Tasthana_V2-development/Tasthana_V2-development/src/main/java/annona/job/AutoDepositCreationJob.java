package annona.job;


import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import annona.scheduler.NotificationsScheduler;
import annona.services.DateService;

public class AutoDepositCreationJob implements Job {

	@Autowired
	NotificationsScheduler notificationsScheduler;
	
	
	public void setNotificationsScheduler(NotificationsScheduler notificationsScheduler) {
		this.notificationsScheduler = notificationsScheduler;
	}

	
	
	@Override
	public void execute(JobExecutionContext context)
		throws JobExecutionException {
		System.out.println("createAutoDeposit is runing........."+DateService.loginDate);
		//notificationsScheduler.createAutoDeposit();
	}

}