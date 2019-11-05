<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script>
$(document).ready(function(){
	
document.getElementById("withdrawBtn").disabled = ${readonly};
})

function validate(){
	var canSubmit=true;
	var withdrawAmount=parseFloat(document.getElementById("withdrawAmount").value);
	var totalBalance=parseFloat(document.getElementById("totalBalance").value);
	
	
	if(withdrawAmount>totalBalance || withdrawAmount<0){
		document.getElementById("withdrawAmountError").style.display='block';
		
		canSubmit=false;
	}
	else{
		document.getElementById("withdrawAmountError").style.display='none';
	}
	
	return canSubmit;
	
}
function isNumber(event)
{
   var keycode = event.which;
   if (!(event.shiftKey == false && (keycode == 8 || keycode == 37 ||  (keycode >= 48 && keycode <= 57)))) {
       event.preventDefault();
       return false;
   } 
}

</script>

<div class="right-container" id="right-container">
	<div class="container-fluid">
	
		<div class="Flexi_deposit">

			<div class="header_customer" style="margin-bottom: 25px;">
				<h3>
					<spring:message code="label.withdrawFromSaving" />
				</h3>
			</div>
			<div class="col-md-12" style="padding: 10px; text-align: center;">
				<span style="color: red;">${error}</span>
			</div>
			<div class="flexi_table">
				<form:form action="withdrawFromSavingConfirm" class="form-horizontal"
					onsubmit="return validate()"  name="withdrawForm"
					commandName="withdrawForm">
					<form:hidden path="customerId" />
					<form:hidden path="accountBalance" />
					<form:hidden path="depositId" />
					<form:hidden path="depositHolderId" />
				

	               <div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.savingAccountNo" /></label>
						<div class="col-md-4">
							<form:input path="accountNumber" id="accountNumber" 
								 class="myform-control" readonly="true"/>
								
						</div>

					</div>

					<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.withDrawAmount" /><span style="color: red">*</span></label>
						<div class="col-md-4">
							<form:input path="withdrawAmount" type="number" min="0" onkeypress="return isNumber(event)" id="withdrawAmount" placeholder="Enter Amount"
								 class="myform-control" required="true" readonly="${readonly}"/>
								 <span style="display:none;color: red" id="withdrawAmountError">Insufficient Balance</span>

						</div>

					</div>
					<div class="form-group" style="margin-left: 25px">
						<label class="col-md-1 control-label"><spring:message
								code="label.totalBalance" /></label>
						<div class="col-md-2">
							<fmt:formatNumber value="${totalBalance}" pattern="#.##" var="totalBalance"/>       
							<form:input path="compoundFixedAmt" value="${totalBalance}" id="totalBalance" class="myform-control" readonly="true" />
						</div>		
						<%--  <label class="col-md-2 control-label">Saving Amount Balance</label>
						<div class="col-md-2">
							<form:input path="accountBalance" class="myform-control"
								readonly="true" />
						</div>  --%>
						<label class="col-md-2 control-label">Auto Deposit Balance</label>

						<div class="col-md-2">
							<fmt:formatNumber value="${withdrawForm.compoundVariableAmt}" pattern="#.##" var="compoundVariableAmt"/>       
							<form:input path="compoundVariableAmt" class="myform-control" value="${compoundVariableAmt}" 
								readonly="true" />

						</div>
						<label class="col-md-2 control-label">Saving Amount Balance</label>

						<div class="col-md-2">
							      <fmt:formatNumber value="${totalBalance-compoundVariableAmt}" pattern="#.##" var="savingBal"/> 
							<form:input path="compoundVariableAmt" class="myform-control" value="${savingBal}" 
								readonly="true" />

						</div>
					</div>





					<div class="form-group">
						<label class="col-md-4 control-label"></label>
						<div class="col-md-6">
							<input type="submit" id="withdrawBtn" class="btn btn-info"
								value="Withdraw">
						</div>
					</div>

				</form:form>
			</div>

		</div>

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