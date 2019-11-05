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
public class LedgerEventMapping {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String event;
	
	private Long modeOfPaymentId;
	
	private String modeOfPayment;
	
	private String debitGLAccount;
	
	private String creditGLAccount;
	
	private String debitGLCode;
	
	private String creditGLCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public Long getModeOfPaymentId() {
		return modeOfPaymentId;
	}

	public void setModeOfPaymentId(Long modeOfPaymentId) {
		this.modeOfPaymentId = modeOfPaymentId;
	}

	public String getModeOfPayment() {
		return modeOfPayment;
	}

	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}

	public String getDebitGLAccount() {
		return debitGLAccount;
	}

	public void setDebitGLAccount(String debitGLAccount) {
		this.debitGLAccount = debitGLAccount;
	}

	public String getCreditGLAccount() {
		return creditGLAccount;
	}

	public void setCreditGLAccount(String creditGLAccount) {
		this.creditGLAccount = creditGLAccount;
	}

	public String getDebitGLCode() {
		return debitGLCode;
	}

	public void setDebitGLCode(String debitGLCode) {
		this.debitGLCode = debitGLCode;
	}

	public String getCreditGLCode() {
		return creditGLCode;
	}

	public void setCreditGLCode(String creditGLCode) {
		this.creditGLCode = creditGLCode;
	}
	
	
	
	

}
