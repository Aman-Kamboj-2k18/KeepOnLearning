package annona.dao;

import java.util.List;

import annona.domain.EventOperations;

public interface EventOperationsDAO {
	
    public void save(EventOperations eventOperations);
	
	public void update(EventOperations eventOperations);
	
	public List<EventOperations> getAllEventOperationsDetails();
	
	public EventOperations getEventOperationsById(Long id);
	
	


}
