package annona.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import annona.domain.Branch;
import annona.domain.LedgerEventMapping;
import annona.domain.ModeOfPayment;

@Repository
public class LedgerEventMappingDAOImpl implements LedgerEventMappingDAO {
	
	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void save(LedgerEventMapping ledgerEventMapping) {
		// TODO Auto-generated method stub
		em.persist(ledgerEventMapping);
	}

	@Transactional
	public void update(LedgerEventMapping ledgerEventMapping) {
		// TODO Auto-generated method stub
		em.merge(ledgerEventMapping);
		em.flush();
	}

	@Override
	public List<LedgerEventMapping> getAllLedgerEventMapping() { 
		// TODO Auto-generated method stub
		List<LedgerEventMapping> ledgerEventMapping = (List<LedgerEventMapping>) em.createQuery("from LedgerEventMapping").getResultList();

		return ledgerEventMapping;
	}
	@Transactional
	public void deleteLedgerEventMappingByEvent(String event) {
		// TODO Auto-generated method stub
		Query query  =  em.createNativeQuery("Delete from LedgerEventMapping  where event=:event");
		query.setParameter("event", event);
		query.executeUpdate();
		
		
	}

	
	@Override
	public LedgerEventMapping getLedgerEventMapping(String event, Long modeOfPaymentId) {
		event = event.toLowerCase();
		// TODO Auto-generated method stub
		Query query  =  em.createQuery("select o from LedgerEventMapping o where lower(o.event)=:event and o.modeOfPaymentId=:modeOfPaymentId");
		query.setParameter("event", event);
		query.setParameter("modeOfPaymentId",modeOfPaymentId);
		List lst = query.getResultList();
		
		if(lst !=null && lst.size()>0)
			return (LedgerEventMapping)lst.get(0);
		else
			return null;

	}

	@Override
	public List<LedgerEventMapping> getLedgerEventMappingByEvent(String event) {
		Query query  =  em.createQuery("select o from LedgerEventMapping o where o.event=:event");
		query.setParameter("event", event);
		List lst = query.getResultList();

		return lst;
	}
	
	
}

