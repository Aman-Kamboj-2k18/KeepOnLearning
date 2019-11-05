package annona.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.ApplicationEvent;
import annona.domain.Deposit;;

public class TDSEventPublisher implements ApplicationEventPublisherAware// EmployeeManager, ApplicationEventPublisherAware
{
	private ApplicationEventPublisher publisher;

	public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
	this.publisher = publisher;
	}

	public void publish() {
		//this.publisher.publishEvent(new TDSEvent(this));
	}
}