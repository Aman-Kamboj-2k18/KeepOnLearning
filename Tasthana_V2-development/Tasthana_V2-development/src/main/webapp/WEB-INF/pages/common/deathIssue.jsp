<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script>

function searchDepost(){

    $("#fdForm").attr("action", "deathIssueSearchPerson");
       $("#fdForm").submit();
  
 }
 
// function myFunction(a)
// {

// 	var files = document.getElementById("fileValue"+a).files; 
// 	document.getElementById("fileName"+a).value="";
// 	var validExts = new Array("jpg", "jpeg", "png", "gif");
//     for (var i = 0; i < files.length; i++){    	
//     	var fileExt= /[^.]+$/.exec( files[i].name);    	
    	
//     	if (validExts.indexOf(fileExt.toString()) < 0) {
//     	      alert("Invalid file selected, valid files are of " +
//     	               validExts.toString() + " types."); 
//     	      document.getElementById("fileValue"+a).value="";
//     	    }
//     	else {
//     		document.getElementById("fileName"+a).value += files[i].name+",";	
//     	}
    	
//     }
// }

function myFunction()
{

	var files = document.getElementById("fileValue").files; 
	document.getElementById("fileName").value="";
	var validExts = new Array("jpg", "jpeg", "png", "gif");
    for (var i = 0; i < files.length; i++){    	
    	var fileExt= /[^.]+$/.exec( files[i].name);    	
    	
    	if (validExts.indexOf(fileExt.toString()) < 0) {
    	      alert("Invalid file selected, valid files are of " +
    	               validExts.toString() + " types."); 
    	      document.getElementById("fileValue").value="";
    	    }
    	else {
    		document.getElementById("fileName").value += files[i].name+",";	
    	}
    	
    }
    
    	
}
</script>


<div class="right-container" id="right-container">
	<div class="container-fluid">

		<div class="Flexi_deposit">

			<div class="header_customer" style="margin-bottom: 30px;">
				<h3>
					<spring:message code="label.deathIssue" />
				</h3>
			</div>
			<div class="col-md-12" style="padding: 30px; text-align: center;">
				<span style="color: red;">${error}</span>
			</div>
			<div class="flexi_table">
				<form:form action="savedDeathCertificate" class="form-horizontal"
					enctype="multipart/form-data" id="fdForm" method="post"
					name="deposit" commandName="depositForm" onsubmit="return val();">
					<form:hidden id="customerName" path="customerName"
						value="${deposit.customerName}" />
					<form:hidden id="depositId" path="depositId"
						value="${deposit.depositId}" />
					<form:hidden id="depositHolderId" path="depositHolderId"
						value="${deposit.depositHolderId}" />


					<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.fdAccountNum" /><span style="color: red">*</span></label>
						<div class="col-md-4">
							<form:input path="accountNumber"
								placeholder="Enter Account Number" id="fdID"
								class="myform-control"></form:input>
						</div>
						<div class="col-md-2">
							<input type="submit" class="btn btn-primary"
								onclick="searchDepost()" value="Search">
						</div>
					</div>
					<div class="col-sm-12">
						<div class="space-45"></div>
					</div>
					<c:if test="${! empty depositList}">
						<table class="table table-striped table-bordered">
							<tr>
								<td><b><spring:message code="label.customerID" /></b></td>
								<td><b><spring:message code="label.customerName" /></b></td>
								<td><b><spring:message code="label.dateOfBirth" /></b></td>
								<td><b><spring:message code="label.email" /></b></td>
								<td><b><spring:message code="label.contactNumber" /></b></td>
								<td><b><spring:message code="label.address" /></b></td>
								<td><b>Deposit id</b></td>
								<td><b>Deposit Holder id</b></td>
								<td><b><spring:message code="label.select" /></b></td>
								<%--  <td><b><spring:message code="label.select" /></b></td> --%>
							</tr>


							<c:forEach items="${depositList}" var="dp">

								<tr>
									<td><b> <c:out value="${dp.customerId}"></c:out></b></td>
									<td><b> <c:out value="${dp.customerName}"></c:out></b></td>
									<td><b> <c:out value="${dp.dateOfBirth}"></c:out></b></td>
									<td><b> <c:out value="${dp.email}"></c:out></b></td>
									<td><b> <c:out value="${dp.contactNum}"></c:out></b></td>
									<td><b> <c:out value="${dp.address}"></c:out></b></td>
									<td><b> <c:out value="${dp.depositId}"></c:out></b></td>
									<td><b> <c:out value="${dp.depositHolderId}"></c:out></b></td>
									<td><b><form:radiobutton path="customerId"
												onclick="onclickRadio('${dp.customerName}','${dp.depositHolderId}','${dp.depositId}')"
												value="${dp.customerId}" /></b></td>


								</tr>
							</c:forEach>
						</table>

					</c:if>
					<c:if test="${! empty depositList}">


						<div class="col-sm-12 col-md-12" style="text-align: center; margin-left: -100px;">
						<div class="form-group" id="depositStatus">

							<div class="form-group">

								<div class="col-sm-12 col-md-12">
									<span id="label0"><label for="radio"> <form:radiobutton
												path="status" id="fdPayOffType1" name="Close Deposit"
												value="CLOSE" checked="true"></form:radiobutton></label> Close The Deposit</span> <span
										id="label1"><label for="radio"> <form:radiobutton
												path="status" id="fdPayOffType2" name="Continue Deposit"
												value="OPEN" ></form:radiobutton></label> Continue The
										Deposit</span>

								</div>


							</div>


						</div>
						</div>
						
						<div class="col-sm-12 col-md-12">
<%-- 						<input name="files" style="margin-left: 320px; margin-bottom: 25px;" type="file" id="fileValue${status.index}" --%>
<%-- 								multiple="multiple" onchange="myFunction(${status.index})" /> --%>
								<input name="files" style="margin-left: 320px; margin-bottom: 25px;" type="file" id="fileValue"
								multiple="multiple" onchange="myFunction()" />
								
								
						</div>
						

					</c:if>
<div style="clear: both;"></div>


						<div class="col-md-12" style="margin-left: 319px;margin-top: -18px; padding-bottom: 45px;">
						<span id="errorMsg" style="display:none;color:red;"> Please select a file for Death Certificate. </span>
						</div>

					<div id="documentError" style="display: none; color: red;">
						<spring:message code="label.validation" />
					</div>

					<div class="form-group">
						<div class="col-md-offset-4 col-md-8">
							<%-- <a href="bankEmp" class="btn btn-info"><spring:message
									code="label.back" /></a>  --%>
									
								<input type="submit" class="btn btn-info"
								disabled="true" data-toggle="tooltip"
								title="Please first select the customer to click on search"
								 id="goBtn"
								value="<spring:message code="label.go"/>">
						</div>
					</div>


				</form:form>
			</div>

		</div>



		<script>
		/* $(document).ready(function(){
		    $('[data-toggle="tooltip"]').tooltip();   
		});
		
		 */
   function onclickRadio(customerName,depositHolderId,depositId)
		 {
				document.getElementById('customerName').value= customerName;
				document.getElementById('depositHolderId').value= depositHolderId;
				document.getElementById('depositId').value= depositId;
				document.getElementById("goBtn").disabled = false;
	     }


	function val() 	
	{
		
		 if(document.getElementById("fileValue").value == "")
		 {
			// alert('ok');
			document.getElementById('errorMsg').style.display='block';
			 document.getElementById("errorMsg").value = "Please select the file for Deatch Certificate.";
			 return false;
		 }

		 return true;
	}
</script>
		<style>
.flexi_table {
	margin-bottom: 210px;
}

.space-45 {
	height: 27px;
}

input#fdID {
	margin-top: 0px;
}
</style>