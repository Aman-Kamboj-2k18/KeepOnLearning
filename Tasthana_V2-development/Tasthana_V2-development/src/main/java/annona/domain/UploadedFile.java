package annona.domain;



import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.beans.factory.annotation.Configurable;

@Entity
@Configurable
@XmlRootElement
public class UploadedFile{
	

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	private Long customerId;
	
	private String document;	
	
	private Date uploadDate;
	
	private String fileName;
	
	private String uploadComment;	
	
	private String customerName;
	
	private Long depositId;
	
	private String depositAccountNumber;
	
	
	
	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<byte[]> files;
	
	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<String> fileNames;
	
	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<String> fileContentType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getUploadComment() {
		return uploadComment;
	}

	public void setUploadComment(String uploadComment) {
		this.uploadComment = uploadComment;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Long getDepositId() {
		return depositId;
	}

	public void setDepositId(Long depositId) {
		this.depositId = depositId;
	}

	public Set<byte[]> getFiles() {
		return files;
	}

	public void setFiles(Set<byte[]> files) {
		this.files = files;
	}

	public Set<String> getFileNames() {
		return fileNames;
	}

	public void setFileNames(Set<String> fileNames) {
		this.fileNames = fileNames;
	}

	public Set<String> getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(Set<String> fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getDepositAccountNumber() {
		return depositAccountNumber;
	}

	public void setDepositAccountNumber(String depositAccountNumber) {
		this.depositAccountNumber = depositAccountNumber;
	}


	

}