 <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<script type="text/javascript">
function validateForm() {
	
 	var Document = document.getElementById('document'); 
 	var file=document.getElementById('file'); 
	//var uploadComment = document.getElementById('uploadComment');
	//var reason = document.getElementById('reason');
    var pattern='^.*\.(jpg|JPG|gif|GIF|doc|DOC|pdf|PDF)$';
	var canSubmit = true;
	
	 if (document.getElementById('Document').value == '') {
		document.getElementById('documentError').style.display = 'block';
		canSubmit = false;
	} else {
		document.getElementById('documentError').style.display = 'none';
	} 
		
	if (canSubmit == false) {
		return false;
	}
}


function myFunction(a)
{
	
	var files = document.getElementById("fileValue"+a).files; 
	document.getElementById("fileName"+a).value="";
	var validExts = new Array("jpg", "jpeg", "png", "gif", "doc", "pdf");
    for (var i = 0; i < files.length; i++){    	
    	var fileExt= /[^.]+$/.exec( files[i].name);    	
    	
    	if (validExts.indexOf(fileExt.toString()) < 0) {
    	      alert("Invalid file selected, valid files are of " +
    	               validExts.toString() + " types."); 
    	      document.getElementById("fileValue"+a).value="";
    	    }
    	else {
    		document.getElementById("fileName"+a).value += files[i].name+",";	
    	}
    	
    }
}
</script>
<div class="col-sm-12 col-md-9 col-lg-9 addvassi">
<div class="body_fixed">
	<div class="col-sm-12 col-md-12 col-lg-12">
		<div class="successMsg"
			style="text-align: center; color: green; font-size: 18px;">${success}</div>
	</div>
	<div class="col-sm-12 col-md-12 col-lg-12 header_customer"></div>
	<form:form method="post" enctype="multipart/form-data"
		name="fileUpload1" commandName="uploadedFileForm" action="fileUploadDoc"
		onsubmit="return validateForm();">


		<div class="col-sm-12 col-md-12 col-lg-12 header_customer">
			<div class="successMsg"
				style="text-align: center; color: green; font-size: 18px;">${success}</div>
		</div>

		<div class="col-sm-12 col-md-12 col-lg-12 header_customer">
			<h3 align="center">
				<spring:message code="label.uploadDocs" />
			</h3>
		</div>
		<div class="col-sm-12 col-md-12 col-lg-12">
			<table width="600" style="margin:0px auto;">
				<form:hidden path="headName"/>
				
				<tr>
					<td><b><spring:message
								code="label.docName" /></b><span style="color: red">*</span></td>
					<td><form:input path="document" id="Document" class="form-control"
							placeholder="Enter Document Name" />

						<div id="documentError" style="display: none; color: red;">
							<spring:message code="label.validation" />
						</div>
						<div id="documentError" style="display: none; color: red;">
							<spring:message code="label.validation" />
						</div></td>
				</tr>
		
				
			</table>
		</div>
		<div class="col-sm-12 col-md-12">
		
		</div>
		<div class="col-sm-12 col-md-12" style="text-align: center;">
			<input name="files" type="file" id="fileValue${status.index}"
				multiple="multiple" onchange="myFunction(${status.index})" />
		</div>

		<div class="col-sm-12 col-md-12 col-lg-12">
			<p align="center" style="padding-top: 10px;">
				<input type="submit" class="btn btn-primary"
					value="<spring:message code="label.save"/>"> 
					 
					 
			</p>
		</div>
	</form:form>
</div>
</div>