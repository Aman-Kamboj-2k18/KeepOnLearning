package annona.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Configurable;

@Entity
@Configurable
@Table(name = "unsuccessfulpayOff")
@XmlRootElement

public class UnSuccessfulPayOff {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private Long id;
	
	private Long depositid;
	
	private Long depositHolderId;
	
	private Date unSuccessfulPayoffDetailsDate;

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

	public Date getUnSuccessfulPayoffDetailsDate() {
		return unSuccessfulPayoffDetailsDate;
	}

	public void setUnSuccessfulPayoffDetailsDate(Date unSuccessfulPayoffDetailsDate) {
		this.unSuccessfulPayoffDetailsDate = unSuccessfulPayoffDetailsDate;
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
