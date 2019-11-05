package annona.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Configurable;

@Entity
@Configurable
@XmlRootElement
public class OverdraftWithdrawDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Long overdraftWithdrawMasterId;
	
	private Long overdraftId;
	
	private Long depositHolderId;
	
	private Double contributionWithdrawAmount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOverdraftWithdrawMasterId() {
		return overdraftWithdrawMasterId;
	}

	public void setOverdraftWithdrawMasterId(Long overdraftWithdrawMasterId) {
		this.overdraftWithdrawMasterId = overdraftWithdrawMasterId;
	}

	public Long getOverdraftId() {
		return overdraftId;
	}

	public void setOverdraftId(Long overdraftId) {
		this.overdraftId = overdraftId;
	}

	public Long getDepositHolderId() {
		return depositHolderId;
	}

	public void setDepositHolderId(Long depositHolderId) {
		this.depositHolderId = depositHolderId;
	}

	public Double getContributionWithdrawAmount() {
		return contributionWithdrawAmount;
	}

	public void setContributionWithdrawAmount(Double contributionWithdrawAmount) {
		this.contributionWithdrawAmount = contributionWithdrawAmount;
	}
	
	
	

}
