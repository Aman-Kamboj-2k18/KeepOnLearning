<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="right-container" id="right-container">
        <div class="container-fluid">
   
<script>

function val() {
	
		var fdAmount = parseFloat(document.getElementById('fdAmount').value);
	  
		if (fdAmount == '0.0') {
			document.getElementById('fdAmount').style.borderColor = "red";
			return false;
		} else {
			document.getElementById('fdAmount').style.borderColor = "green";
		}
	
		
	}
	
	

</script>

<div class="Flexi_deposit">
	
	<div class="header_customer">
		<h3 align="center">
			<spring:message code="label.payAmtToNominee" />
		</h3>
	</div>
<div class="Success_msg">
					<div class="successMsg" style="text-align: center; color: green; font-size: 18px;"><h2>${success}</h2></div>
				</div>
				<div class="flexi_table">
	<form:form action="payAmountToNominee" method="post" name="fixedDeposit" commandName="bankPaymentForm"
		onsubmit="return val();">
		<form:hidden path="paymentMode"/>
	
		<table style="margin:0px auto;" width="600">
		<tr>
		<form:hidden path="customerName" />
		<form:hidden path="customerId" />
		<form:hidden path="contactNum" value="" />
		<form:hidden path="email" value="" />
		<form:hidden path="depositHolderId" value="" />
		<form:hidden path="depositId" value="" />
		<form:hidden path="action" value="" />
		<form:hidden path="nomineeId" value="${nomineeId}" />
		
		<form:hidden path="bankPaymentId" value="${bankPaymentId}" />
		<form:hidden path="bankPaymnetDetailsId" value="${bankPaymentDetailsId}" />
		<%-- <form:hidden path="fdId" value="" /> --%>
		
				<td ><label><spring:message
							code="label.customerId" /></label></td>
				<td ><form:input path="customerId" class="myform-control"
						id="customerId" readonly="true"/></td>
						
			</tr>
			
			<tr>
			<td ><label><spring:message
							code="label.amt" /></label></td>
				<td ><form:input path="amount" value="${Amount}" class="myform-control"
						id="amount" readonly="true"/></td>
			</tr>
			
			<tr>
			<td ><label><spring:message
							code="label.nomineeName" /></label></td>
				<td ><form:input path="nomineeName" value="${nomineeName}" class="myform-control"
						id="nomineeName" readonly="true"/></td>
			</tr>
			<tr>
			<td ><label><spring:message
							code="label.nomineeAge" /></label></td>
				<td ><form:input path="nomineeAge"  value="${nomineeAge}" class="myform-control"
						id="nomineeAge" readonly="true"/></td>
			</tr>
			<tr>
			<td ><label><spring:message
							code="label.nomineeRelationShip" /></label></td>
				<td ><form:input path="nomineeRelationship" value="${relationship}" class="myform-control"
						id="nomineeRelationship" readonly="true"/></td>
			</tr>
			<tr>
			<td ><label><spring:message
							code="label.adress" /></label></td>
				<td ><form:input path="nomineeAddress" value="${address}" class="myform-control"
						id="nomineeAddress" readonly="true"/></td>
			</tr>
			
			<tr>
				<td></td>
					<td id="fdAmountError" style="display: none; color: red;">
						<spring:message code="label.enterData" />
					</td>
					
			</tr>
			
			<c:if test="${showBank eq 1}">
				   <tr>
					<td><label><spring:message code="label.chequeNo" /></label></td>
					<td><form:input path="chequeNo" class="myform-control"
							style="background: whitesmoke; cursor: text;" id="chequeNo" readonly="true"/></td>
				</tr>
				<tr>
				<td></td>
					<td id="ddNoError" style="display: none; color: red;">
						<spring:message code="label.enterData" />
					</td>
					
			</tr>
				<tr>
					<td><label><spring:message code="label.ddchequedate" /></label></td>
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
					<td><label><spring:message code="label.bank" /></label></td>
					<td><form:input path="chequeBank" class="myform-control"
							style="background: whitesmoke; cursor: text;" id="chequeBank" readonly="true"/></td>
				</tr>
				<tr>
				<td></td>
					<td id="ddBankError" style="display: none; color: red;">
						<spring:message code="label.enterData" />
					</td>
					
			</tr>
				<tr>
					<td><label><spring:message code="label.branch" /></label></td>
					<td><form:input path="chequeBranch" class="myform-control"
							style="background: whitesmoke; cursor: text;" id="chequeBranch" readonly="true"/></td>
				</tr>
				
				</c:if>
			
			
		</table>

		<div class="col-sm-12 col-md-12 col-lg-12">
		
			<table align="center" class="f_deposit_btn">
				<tr>
					<td><a href="bankPayment" class="btn btn-success"><spring:message code="label.back"/></a> <input type="submit" class="btn btn-info" onclick="showDialog(); return false;" value="<spring:message code="label.confirm"/>"></td>
					
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