<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="Flexi_deposit">
	
	<div class="header_customer">
		<h3 align="center">
			<spring:message code="label.confirmChequeDeposit" />
		</h3>
	</div>
<div class="Success_msg">
					<div class="successMsg" style="text-align: center; color: green; font-size: 18px;"><h2>${success}</h2></div>
				</div>
				<div class="flexi_table">
	<form:form action="saveChequeData" method="post" name="fixedDeposit" commandName="chequePaymentForm"
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
				<td ><form:input path="fdAmount"  class="myform-control" 
						id="fdAmount" />
						
						</td>
			</tr>
			   <tr>
					<td><label><spring:message code="label.chequeNo" /><span
							style="color: red">*</span></label></td>
					<td><form:input path="chequeNo" class="myform-control"
							id="ddNo" /></td>
				</tr>
				<tr>
				
					<td><label><spring:message code="label.date" /><span
							style="color: red">*</span></label></td>
					<td><form:input path="chequeDate" class="myform-control datepicker-here"
							id="ddDate" readonly="true"/></td>
				</tr>
				
				<tr>
					<td><label><spring:message code="label.bank" /><span
							style="color: red">*</span></label></td>
					<td><form:input path="chequeBank" class="myform-control"
							id="ddBank" /></td>
				</tr>
			
				<tr>
					<td><label><spring:message code="label.branch" /><span
							style="color: red">*</span></label></td>
					<td><form:input path="chequeBranch" class="myform-control"
							 id="ddBranch" /></td>
				</tr>
			
			
		</table>

		<div class="col-sm-12 col-md-12 col-lg-12">
		
			<table align="center" class="f_deposit_btn">
				<tr>
					<td><input type="submit" class="btn btn-info" onclick="showDialog(); return false;" value="<spring:message code="label.save"/>"></td>
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