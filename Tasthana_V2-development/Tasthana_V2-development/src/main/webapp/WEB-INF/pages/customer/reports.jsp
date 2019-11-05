<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- To fetch the request url -->
<c:set var="req" value="${requestScope['javax.servlet.forward.request_uri']}"/>      
<c:set var="baseURL" value="${fn:split(req,'/')}" />


<div class="Flexi_deposit">
	
	<div class="header_customer">
		<h3 align="center">
			<spring:message code="label.reports" />
		</h3>
	</div>

<div class="flexi_table">
	<form:form action="getReport" method="post" name="fixedDeposit" commandName="fixedDepositForm"
		onsubmit="return val();">
		<table align="center" width="400">
		<tr>
				<td><b><spring:message
							code="label.fdID" /></b></td>
				<td><form:select id="fdID" path="fdID" class="myform-control">
				<form:option value="select"></form:option>
								<form:options items="${fixedDepositForm.fixedDeposits}"
									itemValue="fdID" itemLabel="fdID" />
							</form:select>
							<div id="fdIdError" style="display: none; color: red;"><spring:message code="label.validation"/></div>
							</td>
			</tr>
			
		
		</table>

		<div class="col-sm-12 col-md-12 col-lg-12">
			<c:if test="${baseURL[1] == 'bnkEmp'}"><c:set var="back" value="bankEmp"/></c:if>
					<c:if test="${baseURL[1] == 'users'}"><c:set var="back" value="users"/></c:if>
			<table align="center" class="f_deposit_btn">
				<tr>
					<td><input type="submit" class="btn btn-info" onclick="showDialog(); return false;" value="<spring:message code="label.go"/>"></td>
					<td><a href="${back}" class="btn btn-success"><spring:message code="label.back"/></a></td>
				</tr>

			</table>
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