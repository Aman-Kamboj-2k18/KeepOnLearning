package annona.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import annona.domain.Deposit;

@Component
public class BankPaymentEventPublisher implements ApplicationEventPublisherAware// EmployeeManager, ApplicationEventPublisherAware
{

	// Local ApplicationEventPublisher variable.
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	

	// Must override this method, Spring framework will inject ApplicationEventPublisher object automatically.
	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.eventPublisher = applicationEventPublisher;
	}
	
	public void publishBankPaymentEvent(Object source, String eventType, Deposit deposit)
	{
		System.out.println("Will publish BankPayment event, depositId id : " + deposit.getId() + " , date : " + deposit.getCreatedDate());
		
		
		// Instantiate a new BankPaymentEvent object.
		BankPaymentEvent dEvent = new BankPaymentEvent(this, eventType, deposit);
		// Use Spring injected event publisher to publish the event object.		
		// AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
		//				BankPaymentEventPublisher.class);
		// BankPaymentEventPublisher bean = context.getBean(BankPaymentEventPublisher.class);
		// bean.eventPublisher.publishEvent(dEvent);
		
		try{
		
			
			 //publishing the veent here
			this.eventPublisher.publishEvent(dEvent);
		}
		catch(Exception exp)
		{
			System.out.println(exp.getMessage());
		}

	}
}