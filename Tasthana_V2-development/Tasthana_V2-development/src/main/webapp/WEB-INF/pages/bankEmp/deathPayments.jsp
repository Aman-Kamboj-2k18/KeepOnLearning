<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- To fetch the request url -->
<c:set var="req" value="${requestScope['javax.servlet.forward.request_uri']}"/>      
<c:set var="baseURL" value="${fn:split(req,'/')}" />
<div class="right-container" id="right-container">
        <div class="container-fluid">
		

<div class="Flexi_deposit">
	
	<div class="header_customer">
		<h3>
			Death Payment
		</h3>
	</div>

<div class="flexi_table">
	<form:form action="getDepositPayment" class="form-horizontal" method="post" name="fixedDeposit" commandName="fixedDepositForm" onsubmit="return val();">
		
			<div class="form-group">
				<label class="col-md-4 control-label"><spring:message code="label.fdID" /></label>
				<div class="col-md-4">
							<form:select id="fdID" path="fdID" class="myform-control">
							<form:option value="select"></form:option>
							<form:options items="${fixedDepositForm.fixedDeposits}" itemValue="fdID" itemLabel="fdID" />
							</form:select>
							<span id="fdIdError" style="display: none; color: red;"><spring:message code="label.validation"/></span>
				</div>
			</div>
			<div class="form-group">
			<c:if test="${baseURL[1] == 'bnkEmp'}"><c:set var="back" value="bankEmp"/></c:if>
					<c:if test="${baseURL[1] == 'user'}"><c:set var="back" value="users"/></c:if>
				<div class="col-md-offset-4 col-md-8">
					<input type="submit" class="btn btn-info" onclick="showDialog(); return false;" value="<spring:message code="label.go"/>">
					<a href="${back}" class="btn btn-success"><spring:message code="label.back"/></a>
				</div>
			</div>

	</form:form>
	</div>

	</div>
	<script>
	function val() {

		var accountNo = document.getElementById('fdID').value;
		var account = accountNo.toString();

		if (account == 'select') {
			document.getElementById('fdID').style.borderColor = "red";
			return false;
		} else {
			document.getElementById('fdID').style.borderColor = "green";
		}
	
	}
</script>
<style>
	.flexi_table {
    margin-bottom: 210px;
}
</style>