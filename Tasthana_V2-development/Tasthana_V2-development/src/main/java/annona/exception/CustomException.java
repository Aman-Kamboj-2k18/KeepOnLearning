package annona.exception;

import java.util.List;
import annona.domain.Menu;

public class CustomException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String errorCode;
	private String errorMessage;
	//private List<Menu> menus;
	
	public CustomException(String message) {
		this.errorMessage=message;
	}

	
	public CustomException(String errorCode,String message) {
		this.errorCode = errorCode;
		this.errorMessage=message;
	
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	
	
	
}