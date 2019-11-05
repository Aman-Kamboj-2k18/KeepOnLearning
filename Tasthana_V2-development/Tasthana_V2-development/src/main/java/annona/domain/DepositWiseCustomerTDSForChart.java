package annona.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Configurable;

//@Entity
@Configurable
@XmlRootElement

public class DepositWiseCustomerTDSForChart {
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	private Long id;

	private String depositNo;

	private Double tdsAmount; // Total TDS of Deposit

	public String getDepositNo() {
		return depositNo;
	}

	public void setDepositNo(String depositNo) {
		this.depositNo = depositNo;
	}

	public Double getTdsAmount() {
		return tdsAmount;
	}

	public void setTdsAmount(Double tdsAmount) {
		this.tdsAmount = tdsAmount;
	}
	
	
}
