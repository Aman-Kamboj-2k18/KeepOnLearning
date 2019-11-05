<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script>

function val() {
	
		var fdAmount = parseFloat(document.getElementById('fdAmount').value);
	    var accountBalance = parseFloat(document.getElementById('accountBalance').value);
		var canSubmit = true;

		if (fdAmount == '0.0') {
			document.getElementById('fdAmountError').style.display = 'block';
			document.getElementById('fdAmount1Error').style.display = 'none';
			canSubmit = false;
		} else {
			if(accountBalance < fdAmount)
				{
				document.getElementById('fdAmount1Error').style.display = 'block';
				document.getElementById('fdAmountError').style.display = 'none';
				canSubmit = false;
				}
			else
				{
			document.getElementById('fdAmountError').style.display = 'none';
			document.getElementById('fdAmount1Error').style.display = 'none';
				}
		}
	
		if (document.fixedDeposit.payTransfer.value == '') {
			document.getElementById('otherPayTransferError').style.display = 'block';
			canSubmit = false;
		} else {
			document.getElementById('otherPayTransferError').style.display = 'none';
		}
		
		
		 
		if (canSubmit == false) {
			return false;
		}
	}
	
	

</script>

<div class="Flexi_deposit">
	
	<div class="header_customer">
		<h3 align="center">
			<spring:message code="label.fundTransfer" />
		</h3>
	</div>
<div class="Success_msg">
					<div class="successMsg" style="text-align: center; color: green; font-size: 18px;"><h2>${success}</h2></div>
				</div>
				<div class="flexi_table">
	<form:form action="confirmFDTransferData" method="post" name="fixedDeposit" commandName="fundTransferForm"
		onsubmit="return val();">
		<table style="margin:0px auto;" width="600">
		<tr>
		<form:hidden path="customerName" value="" />
		<form:hidden path="contactNum" value="" />
		<form:hidden path="email" value="" />
		<form:hidden path="category" value="" />
		<form:hidden path="fdId" value="" />
		<form:hidden path="fdTenureDate" value="" />
				<td ><label><spring:message
							code="label.customerId" /></label></td>
				<td ><form:input path="customerID" value="" class="myform-control"
						id="customerID" readonly="true"/></td>
			</tr>
			<tr>
				<td ><label><spring:message
							code="label.accountNo" /></label></td>
				<td ><form:input path="accountNo" value="" class="myform-control"
						id="accountNo" readonly="true"/></td>
			</tr>
			<tr>
				<td ><label><spring:message
							code="label.accountType" /></label></td>
				<td ><form:input path="accountType" value="" class="myform-control"
						id="accountType" readonly="true"/></td>
			</tr>			
			<tr>
				<td ><label><spring:message
							code="label.accountBalance" /></label></td>
				<td ><form:input path="accountBalance" value="" class="myform-control"
						id="accountBalance" readonly="true"/></td>
			</tr>
			<tr>
				<td><label><spring:message code="label.transfer" /><span style="color: red">*</span></label></td>
				<td style="font-weight: 700;color: #525252; font-family: monospace;width: 306px;"><label for="radio"><form:radiobutton path="payTransfer" id="otherPayTransfer" value="NEFT" ></form:radiobutton></label><spring:message code="label.transfer1" />
					<label for="radio"><form:radiobutton path="payTransfer" id="payTransfer"  value="IMPS"></form:radiobutton></label><spring:message code="label.transfer2" />
					<label for="radio"><form:radiobutton path="payTransfer" id="payTransfer"  value="RTGS"></form:radiobutton></label><spring:message code="label.transfer3" />
				</td>						
			</tr>
			<tr>
				<td></td>
					<td id="otherPayTransferError" style="display: none; color: red;">
						<spring:message code="label.selectValue" />
					</td>
			</tr>		
			
			<tr>
				<td ><label><spring:message
							code="label.fdAmount" /><span style="color: red">*</span></label></td>
				<td ><form:input path="fdAmount"  class="myform-control" style="background: whitesmoke; cursor:text;"
						id="fdAmount" />
						
						</td>
			</tr>
			<tr>
				<td></td>
					<td id="fdAmountError" style="display: none; color: red;">
						<spring:message code="label.enterData" />
					</td>
					<td id="fdAmount1Error" style="display: none; color: red;">
						<spring:message code="label.lessAmount" />
					</td>
					
			</tr>
			
		</table>

		<div class="col-sm-12 col-md-12 col-lg-12">
		
			<table align="center" class="f_deposit_btn">
				<tr>
					<td><input type="submit" class="btn btn-info" onclick="showDialog(); return false;" value="<spring:message code="label.confirm"/>"></td>
					<td><a href="fundTransferFD" class="btn btn-success"><spring:message code="label.back"/></a></td>
				</tr>

			</table>
		</div>
		
	</form:form>
	</div>

	</div>
	<style>
	.myform-control{
		    background: #999A95;
    		color: #000;
   			 cursor: no-drop;
	}
	

	
	</style>