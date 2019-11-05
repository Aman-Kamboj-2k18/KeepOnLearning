package annona.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Configurable;

@Entity
@Configurable
@XmlRootElement

public class ModeOfPayment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private Long id;

	private String paymentMode;
	
	private String isVisibleInCustomerSide;
	
	private String isVisibleInBankSide;
	
	private Integer isLinkedWithCASAAccount; //is Linked with Current or Saving Account? if yes then 
											  //  in any transaction with this mop will get affect in CASA account

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getIsVisibleInCustomerSide() {
		return isVisibleInCustomerSide;
	}

	public void setIsVisibleInCustomerSide(String isVisibleInCustomerSide) {
		this.isVisibleInCustomerSide = isVisibleInCustomerSide;
	}

	public String getIsVisibleInBankSide() {
		return isVisibleInBankSide;
	}

	public void setIsVisibleInBankSide(String isVisibleInBankSide) {
		this.isVisibleInBankSide = isVisibleInBankSide;
	}

	public Integer getIsLinkedWithCASAAccount() {
		return isLinkedWithCASAAccount;
	}

	public void setIsLinkedWithCASAAccount(Integer isLinkedWithCASAAccount) {
		this.isLinkedWithCASAAccount = isLinkedWithCASAAccount;
	}

	
}

