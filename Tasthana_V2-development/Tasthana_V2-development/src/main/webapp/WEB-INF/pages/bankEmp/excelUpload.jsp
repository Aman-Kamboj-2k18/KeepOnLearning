<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>
function validation()
{
	if(document.excelForm.files.value)
		return true;
	else{
		alert("Please select the file");
		return false;
	}
}
function checkfile(sender) {
    var validExts = new Array(".xlsx", ".xls");
    var fileExt = sender.value;
    fileExt = fileExt.substring(fileExt.lastIndexOf('.'));
    if (validExts.indexOf(fileExt) < 0) {
    	$('input[type=file]').each(function(){
    	    $(this).after($(this).clone(true)).remove();
    	});
      alert("Invalid file selected, valid files are of " +
               validExts.toString() + " types.");
      return false;
    }
    else return true;
}
</script>
<div class="right-container" id="right-container">
	<div class="container-fluid">
<section class="container-fluid padding-top15">
	<div class="row-fluid">
		<div class="span9">
	    	<div class="header_customer" style="margin-bottom: 30px;">
				<h3>
					<spring:message code="label.excelUpload" />
					
				</h3>
			</div>
				
				    <form:form name="excelForm" id="excelForm" action="excelUpdate" modelAttribute="newClientForm" onsubmit="return validation()" method="post" enctype="multipart/form-data">
				      <div class="col-sm-12 col-md-12 col-lg-12" align="center"> <spring:message code="label.selectFile"/>   
				        <input name=files id="files" onchange="checkfile(this);"  accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" type="file"/>
				      </div>
				      &nbsp;<br>
				       <div class="col-sm-12 col-md-12 col-lg-12" align="center">
				       		 <input type="submit" value="Upload" class="btn btn-primary"/>
				       		 <a href="depositRate" class="btn btn-success"><spring:message code="label.back"/></a>
				        </div>
				    </form:form>
			
		</div>
	</div>
</section>
<style>
	section.container-fluid.padding-top15 {
    margin-top: 47px;
}
</style>
    