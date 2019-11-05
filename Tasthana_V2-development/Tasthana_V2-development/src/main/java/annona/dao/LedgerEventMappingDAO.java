package annona.dao;

import java.util.List;

import annona.domain.LedgerEventMapping;



public interface LedgerEventMappingDAO {
	
    public void save(LedgerEventMapping ledgerEventMapping);
	
	public void update(LedgerEventMapping ledgerEventMapping);
	
	public List<LedgerEventMapping> getAllLedgerEventMapping();
	
	public List<LedgerEventMapping> getLedgerEventMappingByEvent(String event);
	
	public void deleteLedgerEventMappingByEvent(String event);
	
	public LedgerEventMapping getLedgerEventMapping(String event, Long modeOfPaymentId);

}
