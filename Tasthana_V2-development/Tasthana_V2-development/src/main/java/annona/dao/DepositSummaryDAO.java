package annona.dao;


import java.util.List;

import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import annona.domain.DepositSummaryHolderWise;
import annona.domain.Interest;
import annona.domain.DepositSummary;



public interface DepositSummaryDAO 
 {

	//---------------------DepositSummary Starts---------------------------------------------------
	
	public DepositSummary insert(DepositSummary depositSummary);
	
	public DepositSummary update(DepositSummary depositSummary);
	
	public DepositSummary getDepositSummary(Long depositId);
	
	public Double getCurrentInterestBalance(Long depositId);
	
	//---------------------DepositSummary Ends---------------------------------------------------
	
	
	//---------------------DepositSummaryHolderWise Starts-----------------------------------------
	
	public DepositSummaryHolderWise insertDepositSummaryHolderWise(DepositSummaryHolderWise depositSummaryHolderWise);
	
	public DepositSummaryHolderWise updateDepositSummaryHolderWise(DepositSummaryHolderWise depositSummaryHolderWise);

	public List<DepositSummaryHolderWise> getHolderWiseDepositSummary(Long depositId);
	
	public DepositSummaryHolderWise getHolderWiseDepositSummary(Long depositId, Long depositHolderId);
	
	//---------------------DepositSummaryHolderWise Ends--------------------------------------------
	
//	
//	
//	
//	//---------------------DepositHolderWiseConsolidatedInterest Starts---------------------------
//	
//	public DepositSummaryHolderWise insertInDepositHolderWiseConsolidatedInterest(DepositSummaryHolderWise holderWiseInterestConsolidation);
//	
//	public void updateInDepositHolderWiseConsolidatedInterest(DepositSummaryHolderWise holderWiseInterestConsolidation);
//	
//	public DepositSummaryHolderWise getDepositHolderInterestConsolidation(Long depositId, Long depositHolderId);
//
//	//---------------------DepositHolderWiseConsolidatedInterest Ends---------------------------
//	
//	
//	
//	//---------------------InterestConsolidation Starts-----------------------------------------
//	
//		public ConsolidatedInterest insertInConsolidatedInterest(ConsolidatedInterest consolidatedInterest);
//		
//		public void updateInConsolidatedInterest(ConsolidatedInterest consolidatedInterest);
//		
//		public ConsolidatedInterest getConsolidatedInterest(Long depositId);
//
//	//---------------------ConsolidatedInterest Ends---------------------------------------------
//	
//	
//	
 }
