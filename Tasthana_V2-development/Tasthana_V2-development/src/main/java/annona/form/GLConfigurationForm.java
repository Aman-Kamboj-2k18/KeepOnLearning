package annona.form;

import java.util.HashMap;

import org.springframework.stereotype.Component;

@Component
public class GLConfigurationForm {

	private Long id;

	private String glAccount;

	private String glCode;

	private String glNumber;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGlAccount() {
		return glAccount;
	}

	public void setGlAccount(String glAccount) {
		this.glAccount = glAccount;
	}

	public String getGlCode() {
		return glCode;
	}

	public void setGlCode(String glCode) {
		this.glCode = glCode;
	}

	public String getGlNumber() {
		return glNumber;
	}

	public void setGlNumber(String glNumber) {
		this.glNumber = glNumber;
	}
	
	private String glCodeList;

	private  String glNumberList;

	public String getGlCodeList() {
		return glCodeList;
	}

	public void setGlCodeList(String glCodeList) {
		this.glCodeList = glCodeList;
	}

	public String getGlNumberList() {
		return glNumberList;
	}

	public void setGlNumberList(String glNumberList) {
		this.glNumberList = glNumberList;
	}

	
}
