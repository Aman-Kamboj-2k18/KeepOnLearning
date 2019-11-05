<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script>
function searchDepost(){

       $("#fdForm").attr("action", "withdrawList");
       $("#fdForm").submit();

 }

function onclickRadio(depositId,depositHolderId,appStatus){
	
	document.getElementById('depositHolderId').value= depositHolderId;
	document.getElementById('depositId').value= depositId;
	
	 
	  document.getElementById('appStatusError').style.display = 'none';
	  if (appStatus == 'Pending') {
			document.getElementById('appStatusError').style.display = 'block';
			document.getElementById("goBtn").disabled = true;
		} else {
			document.getElementById("goBtn").disabled = false;
		}
	 }


function onchangeTransferMode(value){
	if(value!='Select'){
		 document.getElementById("goBtn").title="";
	}

	
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
			Withdrawal List
		</h3>
	</div>
	<div class="col-md-12" style="padding:30px;text-align:center;">
			<span style="color:red;">${error}</span>
	</div>
<div class="flexi_table">
	<form:form action="withdrawList" class="form-horizontal"  id="fdForm" method="post" name="deposit" commandName="depositForm">
					<form:hidden path="depositId" id="depositId"/>
					<form:hidden id="depositHolderId" path="depositHolderId" />
						
                  <c:if test="${empty deposit}">

							<div class="form-group">
									<label class="col-md-4 control-label"><spring:message code="label.fdAccountNum" /><span style="color:red">*</span></label>
									<div class="col-md-4">
										<form:input path="accountNumber" placeholder="Enter Account Number" id="fdID"  class="myform-control" required="true" />
										
									
									</div>
								
							</div>
								<div class="col-md-4"></div>
								<div class="col-md-6">
				                     <input type="submit" id="goBtn"  class="btn btn-info" data-toggle="tooltip" onclick="return valPay()"  value="Proceed">
				
			                    </div>
						</c:if>
						
						
							<span id="appStatusError" style="display: none; color: red;">This deposit is not
								yet approved</span>
								
								<%-- <c:if test="${! empty deposit}">
								
							<div class="form-group">
									<label class="col-md-4 control-label"><spring:message code="label.fdAccountNum" /><span style="color:red">*</span></label>
									<div class="col-md-4">
										<form:input path="accountNumber" class="myform-control" style="background-color: #968c8c2b;margin-bottom: 10px;" readonly="true"/>
							</div>
								
							</div>
							
								   <table class="table table-striped table-bordered" style="margin-left: 15px;width: 949px;">
								    <tr>
								     <td><b><spring:message code="label.customerID" /></b></td>
								     <td><b><spring:message code="label.customerName" /></b></td>
								     <td><b><spring:message code="label.dateOfBirth" /></b></td>
								     <td><b><spring:message code="label.email" /></b></td>
								     <td><b><spring:message code="label.contactNumber" /></b></td>
								     <td><b><spring:message code="label.address" /></b></td>
								     <td><b><spring:message code="label.select" /></b></td>
								 
								     </tr>
					
								     <c:forEach items="${deposit}" var="d">
								   
								     <tr>
								     
								      <td><b> <c:out value="${d.customerId}" ></c:out></b></td>
								     <td><b> <c:out value="${d.customerName}" ></c:out></b></td>
								     <td><b> <c:out value="${d.dateOfBirth}" ></c:out></b></td>
								     <td><b> <c:out value="${d.email}" ></c:out></b></td>
								     <td><b> <c:out value="${d.contactNum}" ></c:out></b></td>
								     <td><b> <c:out value="${d.address}" ></c:out></b></td>
								     <td><b><form:radiobutton onclick="onclickRadio('${d.depositId}','${d.depositHolderId}','${d.approvalStatus}')" path="customerId" value="${d.customerId}"/></b></td>
								    
								    
								   </tr>
								   
								   </c:forEach>
								    
								   </table>
								        
							

		<div class="form-group">
			<label class="col-md-4 control-label"></label>
			<div class="col-md-6">
				<input type="submit" id="goBtn" disabled="true" class="btn btn-info" data-toggle="tooltip" onclick="return valPay()"  value="Proceed">
				
			</div>
				</div>
						
								
								   </c:if>
								


							
 --%>
	</form:form>
	</div>

	</div>
	
<script>
$(document).ready(function(){
    

});
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