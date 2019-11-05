package annona.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class ModificationPenalty {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Long depositId ;
	
	private Date penaltyDate;
	
	private Double penaltyAmount;
	
	private Double penaltyAdjusted;
	
	private Double penaltyAdjustedFromFixedInterest;
	
	private Double penaltyAdjustedFromVariableInterest;
	
	private Double penaltyDue;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDepositId() {
		return depositId;
	}

	public void setDepositId(Long depositId) {
		this.depositId = depositId;
	}

	public Date getPenaltyDate() {
		return penaltyDate;
	}

	public void setPenaltyDate(Date penaltyDate) {
		this.penaltyDate = penaltyDate;
	}

	public Double getPenaltyAmount() {
		return penaltyAmount;
	}

	public void setPenaltyAmount(Double penaltyAmount) {
		this.penaltyAmount = penaltyAmount;
	}

	public Double getPenaltyAdjusted() {
		return penaltyAdjusted;
	}

	public void setPenaltyAdjusted(Double penaltyAdjusted) {
		this.penaltyAdjusted = penaltyAdjusted;
	}

	
	public Double getPenaltyAdjustedFromFixedInterest() {
		return penaltyAdjustedFromFixedInterest;
	}

	public void setPenaltyAdjustedFromFixedInterest(Double penaltyAdjustedFromFixedInterest) {
		this.penaltyAdjustedFromFixedInterest = penaltyAdjustedFromFixedInterest;
	}

	public Double getPenaltyAdjustedFromVariableInterest() {
		return penaltyAdjustedFromVariableInterest;
	}

	public void setPenaltyAdjustedFromVariableInterest(Double penaltyAdjustedFromVariableInterest) {
		this.penaltyAdjustedFromVariableInterest = penaltyAdjustedFromVariableInterest;
	}

	public Double getPenaltyDue() {
		return penaltyDue;
	}

	public void setPenaltyDue(Double penaltyDue) {
		this.penaltyDue = penaltyDue;
	}

}
