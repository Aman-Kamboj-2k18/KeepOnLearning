package annona.form;

import org.springframework.stereotype.Component;

@Component
public class FileForm {
	
	private String fileName;
	
	private String url;

	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

		
}
