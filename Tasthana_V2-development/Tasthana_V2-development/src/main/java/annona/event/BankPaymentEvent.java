package annona.event;

import org.springframework.context.ApplicationEvent;
import annona.domain.Deposit;;

public class BankPaymentEvent extends ApplicationEvent{

	 private static final long serialVersionUID = 1L;
     
	    private String eventType;
	    private Deposit deposit;
	     
	    //Constructor's first parameter must be source
	    public BankPaymentEvent(Object source, String eventType, Deposit deposit)
	    {
	        //Calling this super class constructor is necessary
	        super(source);
	        this.eventType = eventType;
	        this.deposit = deposit;
	    }
	 
	    public String getEventType() {
	        return eventType;
	    }
	 
	    public Deposit getDeposit() {
	        return this.deposit;
	    }
}
