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
@Table(name = "DepositSummaryHolderWise")
@XmlRootElement

public class DepositSummaryHolderWise {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Long depositId;
	
	private Long depositHolderId;
	
	private Long customerId;
	
	private Double totalPrincipal; 
	
	private Double totalFixedPrincipal;              
	
	private Double totalVariablePrincipal;           
	
	private Double totalFixedInterestInHand; // uncompounded interest
	
	private Double totalVariableInterestInHand; // uncompounded interest
	
	private Double totalFixedInterestCompounded;  // Fixed Intetest Balance
	
	private Double totalVariableInterestCompounded; // Variable Interest Balance
	
	private Double totalInterestAccumulated;
	
	private Double totalFixedInterestAccumulated;
	
	private Double totalVariableInterestAccumulated;
	
	private Double totalFixedInterestPaidOff;
	
	private Double totalVariableInterestPaidOff;
		
	private Double totalFixedInterestAdjusted;
	
	private Double totalVariableIneterestAdjusted;

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

	public Long getDepositHolderId() {
		return depositHolderId;
	}

	public void setDepositHolderId(Long depositHolderId) {
		this.depositHolderId = depositHolderId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Double getTotalPrincipal() {
		return totalPrincipal;
	}

	public void setTotalPrincipal(Double totalPrincipal) {
		this.totalPrincipal = totalPrincipal;
	}

	public Double getTotalFixedPrincipal() {
		return totalFixedPrincipal;
	}

	public void setTotalFixedPrincipal(Double totalFixedPrincipal) {
		this.totalFixedPrincipal = totalFixedPrincipal;
	}

	public Double getTotalVariablePrincipal() {
		return totalVariablePrincipal;
	}

	public void setTotalVariablePrincipal(Double totalVariablePrincipal) {
		this.totalVariablePrincipal = totalVariablePrincipal;
	}

	public Double getTotalFixedInterestInHand() {
		return totalFixedInterestInHand;
	}

	public void setTotalFixedInterestInHand(Double totalFixedInterestInHand) {
		this.totalFixedInterestInHand = totalFixedInterestInHand;
	}

	public Double getTotalVariableInterestInHand() {
		return totalVariableInterestInHand;
	}

	public void setTotalVariableInterestInHand(Double totalVariableInterestInHand) {
		this.totalVariableInterestInHand = totalVariableInterestInHand;
	}

	public Double getTotalFixedInterestCompounded() {
		return totalFixedInterestCompounded;
	}

	public void setTotalFixedInterestCompounded(Double totalFixedInterestCompounded) {
		this.totalFixedInterestCompounded = totalFixedInterestCompounded;
	}

	public Double getTotalVariableInterestCompounded() {
		return totalVariableInterestCompounded;
	}

	public void setTotalVariableInterestCompounded(Double totalVariableInterestCompounded) {
		this.totalVariableInterestCompounded = totalVariableInterestCompounded;
	}

	public Double getTotalInterestAccumulated() {
		return totalInterestAccumulated;
	}

	public void setTotalInterestAccumulated(Double totalInterestAccumulated) {
		this.totalInterestAccumulated = totalInterestAccumulated;
	}

	public Double getTotalFixedInterestAccumulated() {
		return totalFixedInterestAccumulated;
	}

	public void setTotalFixedInterestAccumulated(Double totalFixedInterestAccumulated) {
		this.totalFixedInterestAccumulated = totalFixedInterestAccumulated;
	}

	public Double getTotalVariableInterestAccumulated() {
		return totalVariableInterestAccumulated;
	}

	public void setTotalVariableInterestAccumulated(Double totalVariableInterestAccumulated) {
		this.totalVariableInterestAccumulated = totalVariableInterestAccumulated;
	}

	public Double getTotalFixedInterestPaidOff() {
		return totalFixedInterestPaidOff;
	}

	public void setTotalFixedInterestPaidOff(Double totalFixedInterestPaidOff) {
		this.totalFixedInterestPaidOff = totalFixedInterestPaidOff;
	}

	public Double getTotalVariableInterestPaidOff() {
		return totalVariableInterestPaidOff;
	}

	public void setTotalVariableInterestPaidOff(Double totalVariableInterestPaidOff) {
		this.totalVariableInterestPaidOff = totalVariableInterestPaidOff;
	}

	public Double getTotalFixedInterestAdjusted() {
		return totalFixedInterestAdjusted;
	}

	public void setTotalFixedInterestAdjusted(Double totalFixedInterestAdjusted) {
		this.totalFixedInterestAdjusted = totalFixedInterestAdjusted;
	}

	public Double getTotalVariableIneterestAdjusted() {
		return totalVariableIneterestAdjusted;
	}

	public void setTotalVariableIneterestAdjusted(Double totalVariableIneterestAdjusted) {
		this.totalVariableIneterestAdjusted = totalVariableIneterestAdjusted;
	}
	
}
