package annona.dao;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import annona.domain.GLConfiguration;
import annona.domain.Journal;
import annona.domain.Ledger;
import annona.domain.ModeOfPayment;
import annona.form.LedgerReportForm;


@Repository
public class LedgerDAOImpl implements LedgerDAO {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public Journal insertJournal(Journal journal) {
		
		// insert in journal 
		em.persist(journal);
		return journal;
	}


	@Transactional
	public void insertLedger(Ledger ledger) {
		
		// insert in ledger 
		em.persist(ledger);

	}
	

	@Override
	public List<GLConfiguration> getGLConfiguration() {

		Query q = em.createQuery("SELECT o FROM GLConfiguration o order by o.id");
			
		@SuppressWarnings("unchecked")
		
		List list = q.getResultList();
		if(list!=null && list.size()> 0)
		{
			List<GLConfiguration> glConfigurationList = list;
			return glConfigurationList;
		}
		else
			return null;
	}

	
	public GLConfiguration insertGLConfiguration(GLConfiguration glConfiguration)
	{
		// insert in GLConfiguration 
		em.persist(glConfiguration);
		return glConfiguration;
	}
	
	public GLConfiguration updateGLConfiguration(GLConfiguration glConfiguration)
	{
		// insert in GLConfiguration 
		em.merge(glConfiguration);
		return glConfiguration;
	}


	@Override
	public GLConfiguration findById(Long id) {
		// TODO Auto-generated method stub
		return em.find(GLConfiguration.class, id);
	}
	
	@Override
	public GLConfiguration getGLConfigurationByGLAccount(String glAccount) {

		Query q = em.createQuery("SELECT o FROM GLConfiguration o WHERE o.glAccount=:glAccount");
		q.setParameter("glAccount", glAccount);	
		@SuppressWarnings("unchecked")
		
		List list = q.getResultList();
		if(list!=null && list.size()> 0)
		{
			return (GLConfiguration)list.get(0);
		}
		else
			return null;
	}
	
	public Long countById(){
		Long rowCnt= (Long) em.createQuery("SELECT count(g.id) FROM GLConfiguration g").getSingleResult();  
		return rowCnt;
	}


	@Override
	public List<ModeOfPayment> getModeOfPayments() {

		Query q = em.createQuery("SELECT o FROM ModeOfPayment o order by o.id");
			
		@SuppressWarnings("unchecked")
		
		List list = q.getResultList();
		if(list!=null && list.size()> 0)
		{
			List<ModeOfPayment> glModeOfPaymentList = list;
			return glModeOfPaymentList;
		}
		else
			return null;
	}
	
	
	@Override
	public Long getMaxSerialNumberFromJournal() {

		Query q = em.createQuery("SELECT Max(o.serialNo) FROM Journal o");
		@SuppressWarnings("unchecked")
		
		List list = q.getResultList();
		Long maxSerialNo = 0l;
		if(list!=null && list.size()> 0)
		{
			
			try
			{
				maxSerialNo = (Long)list.get(0);
				if(maxSerialNo == null)
					maxSerialNo = 0l;
			}
			catch(Exception e)
			{
				// It is coming as null in Zero th index
				maxSerialNo = 0l;
			}
			
		}
		
		return maxSerialNo;
	}
	
	@Override
	public List<Journal> getUnPostedJournals() {

		Query q = em.createQuery("SELECT o FROM Journal o where o.isPosted=0 order by o.serialNo");
		@SuppressWarnings("unchecked")
		
		List list = q.getResultList();
		if(list!=null && list.size()> 0)
		{
			return (List<Journal>)list;
		}
		else
			return null;
	}
	
	@Override
	public List<LedgerReportForm> getLedgersOfDeposit(Long id, Date fromDate, Date toDate) {
		Query q = em.createNativeQuery(
				"select d.accountnumber,ld.ledgerDateDebit,ld.particularsDebit,ld.debitAmount,ld.ledgerDateCredit,ld.particularsCredit,ld.creditAmount,d.Id"
						+ " from deposit d right outer join ledger ld on d.id = ld.depositid WHERE d.id=:id and "
						+ "((ld.ledgerDateDebit>=:fromDate and ld.ledgerDateDebit<=:toDate) OR (ld.ledgerDateCredit>=:fromDate and ld.ledgerDateCredit<=:toDate))");

		q.setParameter("id", id);
		q.setParameter("fromDate", fromDate);
		q.setParameter("toDate", toDate);

		@SuppressWarnings("unchecked")
		List<Object[]> emiList = q.getResultList();

		List<LedgerReportForm> ledgerReportFormList = new ArrayList<LedgerReportForm>();

		for (int i = 0; i < emiList.size(); i++) {
			LedgerReportForm reportForm = new LedgerReportForm();
			reportForm.setAccountNumber((String) emiList.get(i)[0]);
			reportForm.setLedgerDateDebit((Date) emiList.get(i)[1]);
			reportForm.setParticularsDebit((String) emiList.get(i)[2]);
			reportForm.setDebitAmount((Double) emiList.get(i)[3]);
			reportForm.setLedgerDateCredit((Date) emiList.get(i)[4]);
			reportForm.setParticularsCredit((String) emiList.get(i)[5]);
			reportForm.setCreditAmount((Double) emiList.get(i)[6]);
			Long depositId = Long.parseLong(emiList.get(i)[7].toString());
			reportForm.setDepositId(depositId);
			ledgerReportFormList.add(reportForm);
		}
		return ledgerReportFormList;
	}
	
	@Override
	public List<LedgerReportForm> getLedgers(Date fromDate, Date toDate) {
		Query q = em.createNativeQuery(
				"select d.accountnumber,ld.ledgerDateDebit,ld.particularsDebit,ld.debitAmount,ld.ledgerDateCredit,ld.particularsCredit,ld.creditAmount,d.Id"
						+ " from deposit d right outer join ledger ld on d.id = ld.depositid WHERE "
						+ "((ld.ledgerDateDebit>=:fromDate and ld.ledgerDateDebit<=:toDate) OR (ld.ledgerDateCredit>=:fromDate and ld.ledgerDateCredit<=:toDate))");


		q.setParameter("fromDate", fromDate);
		q.setParameter("toDate", toDate);

		@SuppressWarnings("unchecked")
		List<Object[]> emiList = q.getResultList();

		List<LedgerReportForm> ledgerReportFormList = new ArrayList<LedgerReportForm>();

		for (int i = 0; i < emiList.size(); i++) {
			LedgerReportForm reportForm = new LedgerReportForm();
			reportForm.setAccountNumber((String) emiList.get(i)[0]);
			reportForm.setLedgerDateDebit((Date) emiList.get(i)[1]);
			reportForm.setParticularsDebit((String) emiList.get(i)[2]);
			reportForm.setDebitAmount((Double) emiList.get(i)[3]);
			reportForm.setLedgerDateCredit((Date) emiList.get(i)[4]);
			reportForm.setParticularsCredit((String) emiList.get(i)[5]);
			reportForm.setCreditAmount((Double) emiList.get(i)[6]);
			Long depositId = emiList.get(i)[7]!=null?Long.parseLong(emiList.get(i)[7].toString()):null;
			reportForm.setDepositId(depositId);
			ledgerReportFormList.add(reportForm);
		}
		return ledgerReportFormList;
	}


	@Transactional
	public void updateJournal(Journal journal) {
		
		// update in journal 
		em.merge(journal);
		em.flush();
	}
	
	@Transactional
	public void updateLedger(Ledger ledger) {
		
		// update in journal 
		em.merge(ledger);
		em.flush();
	}

	
}
