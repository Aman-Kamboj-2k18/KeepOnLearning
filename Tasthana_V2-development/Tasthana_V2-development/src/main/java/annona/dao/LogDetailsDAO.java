package annona.dao;

import java.util.Date;

import javax.persistence.TypedQuery;

import annona.domain.LogDetails;
import annona.domain.LoginDate;

public interface LogDetailsDAO {

	public void save(LogDetails depositRatesImport);
	
	public TypedQuery<LogDetails> getData(String userName);
	
	public void updateLoginDate(LoginDate loginDate);
	
	 public Date findLoginDate();
	 
	 public void saveLoginDate(LoginDate loginDate);
	 
	 public LoginDate getLoginDate();
}
