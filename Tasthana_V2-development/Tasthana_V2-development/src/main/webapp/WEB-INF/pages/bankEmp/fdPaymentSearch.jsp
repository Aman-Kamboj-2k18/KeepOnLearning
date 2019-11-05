<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script>
function searchDepost(){
	

	var accno=document.getElementById('fdID').value;

	if(accno!=''){
		document.getElementById('accError').style.display='none';
       $("#fdForm").attr("action", "withdrawPaymentList");
       $("#fdForm").submit();
	}
	else{
		
			document.getElementById('accError').style.display='block';
	}
 }
</script>
<script>
function onclickRadio(depositHolderId,appStatus){
	
	document.getElementById('depositHolderId').value= depositHolderId;
	  document.getElementById("goBtn").disabled = false;
	  document.getElementById('appStatusError').style.display = 'none';
	  if (appStatus == 'Pending') {
			document.getElementById('appStatusError').style.display = 'block';
			document.getElementById("goBtn").disabled = true;
		} else {
			document.getElementById("goBtn").disabled = false;
		}
	 }

</script>



<script>
function validate(){
var fdPay=document.getElementById('fdPay').value;
if(fdPay=='')
	return false;
else
	return true;
	
}



function search(){

    $("#fdForm").attr("action", "depositPayment");
       $("#fdForm").submit();
  
 }
</script>
<div class="right-container" id="right-container">
        <div class="container-fluid">
			
<div class="Flexi_deposit">
	
	<div class="header_customer" style="margin-bottom:25px;">
		<h3>
			<spring:message code="label.withdraw" />
		</h3>
	</div>
	<div class="col-md-12" style="padding:30px;text-align:center;">
			<span style="color:red;">${error}</span>
	</div>
<div class="flexi_table">
	<form:form action="withdrawFdUser" class="form-horizontal"  onsubmit="validate()" id="fdForm" method="post" name="deposit" commandName="depositForm">
					<form:hidden path="depositId" />
					<form:hidden id="depositHolderId" path="depositHolderId" />
						


							<div class="form-group">
									<label class="col-md-4 control-label"><spring:message code="label.fdAccountNum" /><span style="color:red">*</span></label>
									<div class="col-md-4">
										<form:input path="accountNumber" placeholder="Enter Account Number" id="fdID"  class="myform-control" required="true" />
										<span id='accError' style="display: none;"><span color="red"></span></span>
										<span id="appStatusError" style="display: none; color: red;">This deposit is not
								yet approved</span>
									</div>
									<div class="col-md-2">
										 <input type="submit" class="btn btn-primary" onclick="searchDepost()" value="Search">
									</div>
							</div>
							<div class="col-sm-12">
								<div class="space-45"></div>
							</div>
								<c:if test="${! empty deposit}">
								   <table class="table table-striped table-bordered">
								    <tr>
								     <td><b><spring:message code="label.customerID" /></b></td>
								     <td><b><spring:message code="label.customerName" /></b></td>
								     <td><b><spring:message code="label.dateOfBirth" /></b></td>
								     <td><b><spring:message code="label.email" /></b></td>
								     <td><b><spring:message code="label.contactNumber" /></b></td>
								     <td><b><spring:message code="label.address" /></b></td>
								     <td><b><spring:message code="label.select" /></b></td>
								    <%--  <td><b><spring:message code="label.select" /></b></td> --%>
								     </tr>
					
								     <c:forEach items="${deposit}" var="d">
								   
								     <tr>
								     
								      <td><b> <c:out value="${d.customerId}" ></c:out></b></td>
								     <td><b> <c:out value="${d.customerName}" ></c:out></b></td>
								     <td><b> <c:out value="${d.dateOfBirth}" ></c:out></b></td>
								     <td><b> <c:out value="${d.email}" ></c:out></b></td>
								     <td><b> <c:out value="${d.contactNum}" ></c:out></b></td>
								     <td><b> <c:out value="${d.address}" ></c:out></b></td>
								     <td><b><form:radiobutton onclick="onclickRadio('${d.depositHolderId}','${d.approvalStatus}')" path="customerId" value="${d.customerId}"/></b></td>
								    
								    
								   </tr>
								   
								   </c:forEach>
								    
								   </table>
								        
								   </c:if>
								


								<div class="form-group">
											<label class="col-md-4 control-label"><spring:message code="label.modeOfPay" /><span style="color:red">*</span></label>
											<div class="col-md-4">
												<form:select id="fdPay" placeholder="Payment Mode" path="paymentMode" class="myform-control">
												<form:option value="Select"><spring:message code="label.select"/></form:option>
												<form:option value="DD"><spring:message code="label.ddPayment"/></form:option>
												 <form:option value="Cheque"><spring:message code="label.chequePayment"/></form:option>
												<form:option value="onLinePayment"><spring:message code="label.onLinePayment"/></form:option> 
												</form:select>
												<span id="fdPayError" style="display: none; color: red;"><spring:message code="label.validation"/></span>
											</div>
								</div>
								


								<div class="form-group">
												<label class="col-md-4 control-label"></label>
												<div class="col-md-6">
													<input type="submit" id="goBtn" disabled="true" class="btn btn-info" data-toggle="tooltip" title="Please first select the customer to click on search" onclick="return valPay()"  value="<spring:message code="label.go"/>">
													<a href="bankEmp" class="btn btn-info"><spring:message code="label.back" /></a>
												</div>
								</div>

	</form:form>
	</div>

	</div>
	
<script>
$(document).ready(function(){
    $('[data-toggle="tooltip"]').tooltip();   
});
</script>
	<script>
	function valPay() {

		if (document.getElementById('fdPay').value == 'Select') {
			document.getElementById('fdPay').style.borderColor = "red";
			return false;
		} 


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