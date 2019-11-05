package annona.form;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class LoginForm {

	private String userName;

	private String password;

	private String email;

	private String managerName;

	private String j_username;

	private String j_password;
	
	private String j_Organization;
	
	private Date loginDate;

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public String getJ_Organization() {
		return j_Organization;
	}

	public void setJ_Organization(String j_Organization) {
		this.j_Organization = j_Organization;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getJ_username() {
		return j_username;
	}

	public void setJ_username(String j_username) {
		this.j_username = j_username;
	}

	public String getJ_password() {
		return j_password;
	}

	public void setJ_password(String j_password) {
		this.j_password = j_password;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	
}
