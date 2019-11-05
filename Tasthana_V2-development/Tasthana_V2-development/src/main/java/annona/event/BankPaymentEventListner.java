package annona.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import annona.domain.Deposit;;

public class BankPaymentEventListner implements ApplicationListener<BankPaymentEvent> {

	 public BankPaymentEventListner() {
		    super();
		    System.out.println("Application context listener is created!");
		  }

	
	@Override
	public void onApplicationEvent(BankPaymentEvent event) {
		
		Deposit deposit = event.getDeposit();
		System.out.println("Receive BankPaymentEvent deposit id : " + deposit.getId() + " , deposit date : " + deposit.getCreatedDate());

	}
}
