package annona.form;

import java.util.Date;
import java.util.List;


import org.springframework.stereotype.Component;

import annona.domain.BenificiaryDetails;
import annona.domain.Deposit;
import annona.domain.DepositHolder;
import annona.domain.ProductConfiguration;
@Component
public class HolderForm {
	private Deposit deposit;

	private DepositHolder depositHolder;
	
	private BeneficiaryForm benificiary;
	
	private List<DepositHolder> depositHolderList;
	
	private List<BenificiaryDetails> benificiaryList;
	
	private EditDepositForm editDepositForm;
	
	private String customerName;
	
	private Long depositId;
	
    private String depositConversion;
	
	private Integer deductionDay;
	
	private Date newMaturityDate;  // for tenure change
	
	private String depositChange;

	private String newDepositTenureType;

	private Integer newDepositDaysValue;
	
	private Integer newDepositTenure;

	private String newPaymentType;
	
	private Integer newDepositDeductionDay;
	
	private ProductConfiguration productConfiguration;
	
	/*private String accountAccessType;
	
	 public String getAccountAccessType() {
		return accountAccessType;
	}

	public void setAccountAccessType(String accountAccessType) {
		this.accountAccessType = accountAccessType;
	}*/

	public HolderForm() {
	    }
	 
	/*public HolderForm(Deposit deposit, List<DepositHolder> depositHolderList) {
		
		this.deposit = deposit;
		this.depositHolderList = depositHolderList;
	}
*/
	
	public HolderForm(Deposit deposit, DepositHolder depositHolder) {
		
		this.deposit = deposit;
		this.depositHolder = depositHolder;
	}
	public Deposit getDeposit() {
		return deposit;
	}

	public void setDeposit(Deposit deposit) {
		this.deposit = deposit;
	}

	public List<DepositHolder> getDepositHolderList() {
		return depositHolderList;
	}

	public void setDepositHolderList(List<DepositHolder> depositHolderList) {
		this.depositHolderList = depositHolderList;
	}
	public DepositHolder getDepositHolder() {
		return depositHolder;
	}
	public void setDepositHolder(DepositHolder depositHolder) {
		this.depositHolder = depositHolder;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public EditDepositForm getEditDepositForm() {
		return editDepositForm;
	}

	public void setEditDepositForm(EditDepositForm editDepositForm) {
		this.editDepositForm = editDepositForm;
	}


	public List<BenificiaryDetails> getBenificiaryList() {
		return benificiaryList;
	}

	public void setBenificiaryList(List<BenificiaryDetails> benificiaryList) {
		this.benificiaryList = benificiaryList;
	}

	public BeneficiaryForm getBenificiary() {
		return benificiary;
	}

	public void setBenificiary(BeneficiaryForm benificiary) {
		this.benificiary = benificiary;
	}

	public Long getDepositId() {
		return depositId;
	}

	public void setDepositId(Long depositId) {
		this.depositId = depositId;
	}

	public String getDepositConversion() {
		return depositConversion;
	}

	public void setDepositConversion(String depositConversion) {
		this.depositConversion = depositConversion;
	}

	public Integer getDeductionDay() {
		return deductionDay;
	}

	public void setDeductionDay(Integer deductionDay) {
		this.deductionDay = deductionDay;
	}

	public String getDepositChange() {
		return depositChange;
	}

	public void setDepositChange(String depositChange) {
		this.depositChange = depositChange;
	}

	public String getNewDepositTenureType() {
		return newDepositTenureType;
	}

	public void setNewDepositTenureType(String newDepositTenureType) {
		this.newDepositTenureType = newDepositTenureType;
	}

	public Integer getNewDepositDaysValue() {
		return newDepositDaysValue;
	}

	public void setNewDepositDaysValue(Integer newDepositDaysValue) {
		this.newDepositDaysValue = newDepositDaysValue;
	}

	public Integer getNewDepositTenure() {
		return newDepositTenure;
	}

	public void setNewDepositTenure(Integer newDepositTenure) {
		this.newDepositTenure = newDepositTenure;
	}

	public String getNewPaymentType() {
		return newPaymentType;
	}

	public void setNewPaymentType(String newPaymentType) {
		this.newPaymentType = newPaymentType;
	}

	public Integer getNewDepositDeductionDay() {
		return newDepositDeductionDay;
	}

	public void setNewDepositDeductionDay(Integer newDepositDeductionDay) {
		this.newDepositDeductionDay = newDepositDeductionDay;
	}

	public Date getNewMaturityDate() {
		return newMaturityDate;
	}

	public void setNewMaturityDate(Date newMaturityDate) {
		this.newMaturityDate = newMaturityDate;
	}

	public ProductConfiguration getProductConfiguration() {
		return productConfiguration;
	}

	public void setProductConfiguration(ProductConfiguration productConfiguration) {
		this.productConfiguration = productConfiguration;
	}

	
}
