package annona.job;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.QuartzJobBean;

import annona.dao.AccountDetailsDAO;
import annona.dao.CustomerDAO;
import annona.dao.DepositHolderDAO;
import annona.dao.DepositModificationDAO;
import annona.dao.EndUserDAO;
import annona.dao.FixedDepositDAO;
import annona.dao.InterestDAO;
import annona.dao.DepositSummaryDAO;
import annona.dao.PaymentDAO;
import annona.dao.PaymentDistributionDAO;
import annona.dao.RatesDAO;
import annona.dao.TDSDAO;
import annona.dao.UnSuccessfulRecurringDAO;
import annona.domain.AccountDetails;
import annona.domain.Customer;
import annona.domain.Deposit;
import annona.domain.DepositHolder;
import annona.domain.DepositModification;
import annona.domain.Interest;
import annona.domain.Payment;
import annona.domain.Rates;
import annona.domain.TDS;
import annona.domain.UnSuccessfulRecurringDeposit;
import annona.exception.CustomException;
import annona.form.FixedDepositForm;
import annona.scheduler.NotificationsScheduler;
import annona.services.DateService;
import annona.services.FDService;
import annona.utility.Constants;


public class AutoDeductionJob extends QuartzJobBean implements StatefulJob{

	@Autowired
	NotificationsScheduler notificationsScheduler;
	
	
	public void setNotificationsScheduler(NotificationsScheduler notificationsScheduler) {
		this.notificationsScheduler = notificationsScheduler;
	}


	@Override
	public void executeInternal(JobExecutionContext context)
		throws JobExecutionException {
		System.out.println("autoDeduction is runing....."+ DateService.loginDate);
		try {
			notificationsScheduler.autoDeduction();
		} catch (CustomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	
}