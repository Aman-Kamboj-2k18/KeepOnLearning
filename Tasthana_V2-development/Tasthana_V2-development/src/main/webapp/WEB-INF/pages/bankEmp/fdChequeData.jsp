<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script>

function val() {
	
	var fdAmount = parseFloat(document.getElementById('fdAmount').value);
		var ddNo = document.getElementById('chequeNo').value;
		var ddDate = document.getElementById('chequeDate').value;
		var ddBank = document.getElementById('chequeBank').value;
		var ddBranch = document.getElementById('chequeBranch').value;
	   
		if (fdAmount == '0.0') {
			document.getElementById('fdAmount').style.borderColor = "red";
			return false;
		} else {
			document.getElementById('fdAmount').style.borderColor = "green";
		}

		if (ddNo == '') {
			document.getElementById('chequeNo').style.borderColor = "red";
			return false;
		} else {
			document.getElementById('chequeNo').style.borderColor = "green";
		}
	
		if (ddDate == '') {
			document.getElementById('chequeDate').style.borderColor = "red";
			return false;
		} else {
			document.getElementById('chequeDate').style.borderColor = "green";
		}
		if (ddBank == '') {
			document.getElementById('chequeBank').style.borderColor = "red";
			return false;
		} else {
			document.getElementById('chequeBank').style.borderColor = "green";
		}
		if (ddBranch == '') {
			document.getElementById('chequeBranch').style.borderColor = "red";
			return false;
		} else {
			document.getElementById('chequeBranch').style.borderColor = "green";
		}
		
		
	}
	
	

</script>

<div class="Flexi_deposit">
	
	<div class="header_customer">
		<h3 align="center">
			<spring:message code="label.chequeDeposit" />
		</h3>
	</div>
<div class="Success_msg">
					<div class="successMsg" style="text-align: center; color: green; font-size: 18px;"><h2>${success}</h2></div>
				</div>
				<div class="flexi_table">
	<form:form action="confirmChequeData" method="post" name="fixedDeposit" commandName="chequePaymentForm"
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
					
			</tr>
			
			
			   <tr>
					<td><label><spring:message code="label.chequeNo" /><span
							style="color: red">*</span></label></td>
					<td><form:input path="chequeNo" class="myform-control"
							style="background: whitesmoke; cursor: text;" id="chequeNo" /></td>
				</tr>
				<tr>
				<td></td>
					<td id="ddNoError" style="display: none; color: red;">
						<spring:message code="label.enterData" />
					</td>
					
			</tr>
				<tr>
					<td><label><spring:message code="label.date" /><span
							style="color: red">*</span></label></td>
					<td><form:input path="chequeDate" class="myform-control datepicker-here"
							style="background: whitesmoke; cursor: pointer;" id="chequeDate" readonly="true"/></td>
				</tr>
				<tr>
				<td></td>
					<td id="ddDateError" style="display: none; color: red;">
						<spring:message code="label.enterData" />
					</td>
					
			</tr>
				<tr>
					<td><label><spring:message code="label.bank" /><span
							style="color: red">*</span></label></td>
					<td><form:input path="chequeBank" class="myform-control"
							style="background: whitesmoke; cursor: text;" id="chequeBank" /></td>
				</tr>
				<tr>
				<td></td>
					<td id="ddBankError" style="display: none; color: red;">
						<spring:message code="label.enterData" />
					</td>
					
			</tr>
				<tr>
					<td><label><spring:message code="label.branch" /><span
							style="color: red">*</span></label></td>
					<td><form:input path="chequeBranch" class="myform-control"
							style="background: whitesmoke; cursor: text;" id="chequeBranch" /></td>
				</tr>
			<tr>
				<td></td>
					<td id="ddBranchError" style="display: none; color: red;">
						<spring:message code="label.enterData" />
					</td>
					
			</tr>
			
		</table>

		<div class="col-sm-12 col-md-12 col-lg-12">
		
			<table align="center" class="f_deposit_btn">
				<tr>
					<td><input type="submit" class="btn btn-info" onclick="showDialog(); return false;" value="<spring:message code="label.confirm"/>"></td>
					<td><a href="payment" class="btn btn-success"><spring:message code="label.back"/></a></td>
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