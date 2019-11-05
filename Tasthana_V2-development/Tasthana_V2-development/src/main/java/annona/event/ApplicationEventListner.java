package annona.event;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import annona.dao.DepositHolderDAO;
import annona.dao.ProductConfigurationDAO;
import annona.dao.RatesDAO;
import annona.domain.BankConfiguration;
import annona.domain.Deposit;
import annona.domain.DepositHolder;
import annona.domain.ProductConfiguration;
import annona.domain.TDS;
import annona.services.DateService;
import annona.services.FDService;

public class ApplicationEventListner implements ApplicationListener<ApplicationEvent> {

	@Autowired
	RatesDAO ratesDAO;

	@Autowired
	FDService fdService;
	
	@Autowired
	DepositHolderDAO depositHolderDAO;
	
	@Autowired
	ProductConfigurationDAO productConfigurationDAO;

	@Override
	public void onApplicationEvent(ApplicationEvent applicationEvent) {

		// process event
		if (applicationEvent instanceof BankPaymentEvent) {

		} else if (applicationEvent instanceof InterestCalculationEvent) {
//			InterestCalculationEvent interestCalculationEvent = (InterestCalculationEvent) applicationEvent;
//			BankConfiguration bankConfiguration = ratesDAO.findAll();
//
//			if (bankConfiguration.getTdsCalculationOnBasis().equalsIgnoreCase("OnIntCompounding")
//					|| bankConfiguration.getTdsCalculationOnBasis().equalsIgnoreCase("both")) {
				
				System.out.println("Within InterestCalculation TDS id going to calculate ....");
				//fdService.deductTDS();
				System.out.println("Within CloseDepositEvent TDS id going to calculate ....");
				String financialYear = DateService.getFinancialYear(DateService.getCurrentDate());
				
				List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(((InterestCalculationEvent)applicationEvent).getDeposit().getId());
				for(int i=0; i< depositHolderList.size(); i++)
				{
//					calculationService.calculateTDS(depositHolderList.get(i).getCustomerId(), ((InterestCalculationEvent)applicationEvent).getDeposit());
				}
								
//			}
		} else if (applicationEvent instanceof CloseDepositEvent) {
			CloseDepositEvent closeDepositEvent = (CloseDepositEvent) applicationEvent;
			System.out.println("Within CloseDepositEvent TDS id going to calculate ....");
			String financialYear = DateService.getFinancialYear(DateService.getCurrentDate());
			
			List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(closeDepositEvent.getDeposit().getId());
			for(int i=0; i< depositHolderList.size(); i++)
			{
//				fdService.calculateTDS(depositHolderList.get(i).getCustomerId(), closeDepositEvent.getDeposit());
			}
			
		
		} else if (applicationEvent instanceof WithdrawEvent) {
			try
			{
				WithdrawEvent withdrawEvent = (WithdrawEvent) applicationEvent;				
				ProductConfiguration productConfiguration = productConfigurationDAO.findById(withdrawEvent.getDeposit().getId());
				
				if (productConfiguration.getTdsCalculationOnBasis().equalsIgnoreCase("OnPayout")
						|| productConfiguration.getTdsCalculationOnBasis().equalsIgnoreCase("both")) {
					System.out.println("Within WithdrawEvent TDS id going to calculate ....");
					//fdService.deductTDS(withdrawEvent.getDeposit().getId());
					//fdService.calculateTDSForDeposit(withdrawEvent.getDeposit());
					
					List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(withdrawEvent.getDeposit().getId());
					for(int i=0; i< depositHolderList.size(); i++)
					{
//						fdService.calculateTDS(depositHolderList.get(i).getCustomerId(), withdrawEvent.getDeposit());
					}
				}
			}
			catch(Exception ex){
				System.out.println("From Close Event..." + ex.getMessage());
			}

		}
	}
}