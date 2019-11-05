package annona.dao;


import java.util.List;

import annona.domain.AccountDetails;
import annona.domain.UploadFile;
import annona.form.CustomerForm;


public interface UploadFileDAO 
 {

	/**
	 * Method to save User  account details
	 */
	public void saveFile(UploadFile uploadFile);

	public List<UploadFile> getAllFile();
  }
