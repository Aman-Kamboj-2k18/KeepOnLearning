package annona.form;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class PayOfForm {

private Long id;
	
	private Long depositid;
	
	private Long depositHolderId;
	
	private Date unSuccessfulPayoffDetailsDateFrom;

	private Date unSuccessfulPayoffDetailsDateTo;
	
	private Double payOffAmount;

	private Double availableVariableInterestAmountForPayOff;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDepositid() {
		return depositid;
	}

	public void setDepositid(Long depositid) {
		this.depositid = depositid;
	}

	public Long getDepositHolderId() {
		return depositHolderId;
	}

	public void setDepositHolderId(Long depositHolderId) {
		this.depositHolderId = depositHolderId;
	}

	
	public Date getUnSuccessfulPayoffDetailsDateFrom() {
		return unSuccessfulPayoffDetailsDateFrom;
	}

	public void setUnSuccessfulPayoffDetailsDateFrom(Date unSuccessfulPayoffDetailsDateFrom) {
		this.unSuccessfulPayoffDetailsDateFrom = unSuccessfulPayoffDetailsDateFrom;
	}

	public Date getUnSuccessfulPayoffDetailsDateTo() {
		return unSuccessfulPayoffDetailsDateTo;
	}

	public void setUnSuccessfulPayoffDetailsDateTo(Date unSuccessfulPayoffDetailsDateTo) {
		this.unSuccessfulPayoffDetailsDateTo = unSuccessfulPayoffDetailsDateTo;
	}

	public Double getPayOffAmount() {
		return payOffAmount;
	}

	public void setPayOffAmount(Double payOffAmount) {
		this.payOffAmount = payOffAmount;
	}

	public Double getAvailableVariableInterestAmountForPayOff() {
		return availableVariableInterestAmountForPayOff;
	}

	public void setAvailableVariableInterestAmountForPayOff(Double availableVariableInterestAmountForPayOff) {
		this.availableVariableInterestAmountForPayOff = availableVariableInterestAmountForPayOff;
	}

	
	
}
