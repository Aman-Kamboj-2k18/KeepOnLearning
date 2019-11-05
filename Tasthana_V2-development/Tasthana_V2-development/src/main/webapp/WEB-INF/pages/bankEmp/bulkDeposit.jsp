<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- To fetch the request url -->
<c:set var="req"
	value="${requestScope['javax.servlet.forward.request_uri']}" />
<c:set var="baseURL" value="${fn:split(req,'/')}" />
<link href="<%=request.getContextPath()%>/resources/css/Validation.css"
	rel="stylesheet">
<script src="<%=request.getContextPath()%>/resources/js/ccvalidate-tapan.min.js"></script>

<div class="right-container" id="right-container">
	<div class="container-fluid">



		<div class="Flexi_deposit">

			<div class="header_customer">
				<h3>
					Upload Bulk Deposit

				</h3>
			</div>
			<div class="Success_msg">
				<div class="successMsg" style="text-align: center; color: red;">
					${error}</div>
			</div>
			<div class="flexi_table" style="margin-top: 7px;">
				<form:form name="excelForm" id="excelForm" action="updateExistingExcel" modelAttribute="newClientForm" onsubmit="return validation()" method="post" enctype="multipart/form-data">
				 
					<div class="col-md-6">
						<div class="form-group">
							<label class="col-md-6 control-label" style="padding-top: 16px;">Upload Excel<span style="color: red">*</span></label>
							<div class="col-md-6">
								<input name=files id="files" onchange="checkfile(this);"  accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" type="file"/>
							</div>
						</div>
						<input type="hidden" name="currencies" id="currencies">

					</div>
					<div class="col-md-12"  style="text-align: left;margin-left: 272px;">
					
				    <tr>
								<td><span id='validationError' style="color: red"></span></td>
					</tr>
					<tr>
					
						<td><input type="submit" size="3"
							value="<spring:message code="label.confirm"/>"
							class="btn btn-primary"></td>
					</tr>
			</div>
				</form:form>
				
				<form:form action="downloadExcel">
				
				<div class="col-md-6">
						<div class="form-group">
							<label class="col-md-6 control-label" style="padding-top: 16px;">Download Excel<span style="color: red">*</span></label>
							<div class="col-md-6">
								<select name="filename">
				<c:if test="${! empty files}">
				<c:forEach items="${files}" var="file">
				<option value="${file}">${file}</option>
								</c:forEach>
								
								
								
				</c:if>
				</select>
							</div>
						</div>

					</div>
					<div class="col-md-12"  style="text-align: left;margin-left: 272px;">
					
				    <tr>
								<td><span id='validationError' style="color: red"></span></td>
					</tr>
					<tr>
					
						<td><input type="submit" size="3"
							value="Download"
							class="btn btn-primary"></td>
					</tr>
			</div>
				
				</form:form>
			</div>
</div>




<style type="text/css">
form {
	margin: 20px 0;
}

form input, button {
	padding: 5px;
}

table {
	width: 100%;
	margin-bottom: 20px;
	border-collapse: collapse;
}

table, th, td {
	border: 1px solid #cdcdcd;
}

table th, table td {
	padding: 10px;
	text-align: left;
}

input[type=checkbox], input[type=radio] {
	margin: 4px 1px 0px;
	margin-top: 1px\9;
	line-height: normal;
	zoom: 1.0;
}

.form-horizontal .control-label {
	padding-top: 0;
}
</style>
<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
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
var currencies;
$(document).ready(function(){
	debugger;
	currencies=getAllCurrencies().join();
	$('#currencies').val(currencies);
});
</script>

