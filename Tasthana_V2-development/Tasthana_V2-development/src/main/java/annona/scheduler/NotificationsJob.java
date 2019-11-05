package annona.scheduler;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class NotificationsJob extends QuartzJobBean implements StatefulJob{
	
	private NotificationsScheduler notificationsScheduler;
	
	public void setNotificationsScheduler(NotificationsScheduler notificationsScheduler) {
		this.notificationsScheduler = notificationsScheduler;
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		
//		notificationsScheduler.caculateInterest(); // for interest and tds deduction
//		
//		notificationsScheduler.caculatePayOff();
//		
//		notificationsScheduler.calculateModificationPenalty();
//		
// 		notificationsScheduler.autoDeduction();
//		
//		notificationsScheduler.transferMoneyOnMaturity();
//		
//		notificationsScheduler.deductReverseEMI();
//		
//		notificationsScheduler.paymentRemindMail();
		
		//------------------------------------------------
		//notificationsScheduler.deductTDS();
		//notificationsScheduler.createAutoDeposit();
		
	
	}

}
