<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- To fetch the request url -->
<c:set var="req" value="${requestScope['javax.servlet.forward.request_uri']}"/>      
<c:set var="baseURL" value="${fn:split(req,'/')}" />
<script>

function val() {
	
		var fdAmount = document.getElementById('accountBalance').value;
	    var withDrawAmount =document.getElementById('fdChangeable').value;
		var canSubmit = true;
		
		if (withDrawAmount == '') {
			
			document.getElementById('withDrawAmountError').style.display = 'block';
			document.getElementById('withDrawAmount1Error').style.display = 'none';
			canSubmit = false;
		} else {
			if(withDrawAmount > fdAmount)
				{
				document.getElementById('withDrawAmount1Error').style.display = 'block';
				document.getElementById('withDrawAmountError').style.display = 'none';
				canSubmit = false;
				}
			else
				{
			document.getElementById('withDrawAmountError').style.display = 'none';
			document.getElementById('withDrawAmount1Error').style.display = 'none';
				}
		}
			 
		if (canSubmit == false) {
			return false;
		}
	}
	
	

</script>

<div class="Flexi_deposit">
	
	<div class="header_customer">
		<h3 align="center">
			<spring:message code="label.withDrawFD" />
		</h3>
	</div>
<div class="Success_msg">
					<div class="successMsg" style="text-align: center; color: green; font-size: 18px;"><h2>${success}</h2></div>
				</div>
				<div class="flexi_table">
	<form:form action="confirmWithDrawData" method="post" name="fixedDeposit" commandName="fixedDepositForm"
		onsubmit="return val();">
		<table style="margin:0px auto;" width="600">
		<tr>
		
		<form:hidden path="fdID" value="" />
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
				<td ><label>Withdrawal Balance<span style="color: red">*</span></label></td>
				<td ><form:input path="fdChangeable"  class="myform-control"
						id="fdChangeable" />
						
						</td>
			</tr>
			
			<tr>
				<td ><label><spring:message
							code="label.withDrawAmount" /><span style="color: red">*</span></label></td>
				<td ><form:input path="withdDrawAmount"  class="myform-control" style="background: whitesmoke; cursor:text;"
						id="withdDrawAmount" />
						
						</td>
			</tr>
			
			
			<tr>
				<td></td>
					<td id="withDrawAmountError" style="display: none; color: red;max-width: 390px;">
						<spring:message code="label.enterData" />
					</td>
					<td id="withDrawAmount1Error" style="display: none; color: red;max-width: 390px;">
						<spring:message code="label.lessAmount" />
					</td>
					
			</tr>
			
		</table>

		<div class="col-sm-12 col-md-12 col-lg-12">
		<c:if test="${baseURL[1] == 'bnkEmp'}"><c:set var="back" value="withDrawFDList"/></c:if>
					<c:if test="${baseURL[1] == 'users'}"><c:set var="back" value="withDrawFDList"/></c:if>
		
			<table align="center" class="f_deposit_btn">
				<tr>
					<td><input type="submit" class="btn btn-info" onclick="showDialog(); return false;" value="<spring:message code="label.confirm"/>"></td>
					<td><a href="${back}" class="btn btn-success"><spring:message code="label.back"/></a></td>
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