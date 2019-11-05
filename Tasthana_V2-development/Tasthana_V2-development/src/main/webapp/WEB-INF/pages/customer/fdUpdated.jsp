<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="Flexi_deposit">
		<div class="col-sm-12 col-md-12 col-lg-12">
			<div class="successMsg"
				style="text-align: center; color: green; font-size: 18px;">${success}</div>
		</div>

	<div class="fdsaved-table">
		<table align="center" width="400">


			<tr>
				<td class="heading_text"><b><spring:message
							code="label.fdID" />:</b></td>
				<td>${model.fixedDepositForm.fdID}</td>
			<tr>
				<td><b><spring:message code="label.transactionType" />:</b></td>
				<td><spring:message code="label.fixedDeposit" /></td>
			</tr>

			<tr>
				<td><b><spring:message code="label.status" /></b></td>
				<td><font color="green"><spring:message
							code="label.changedSuccessfully" /></font></td>
			</tr>

		</table>
	</div>
	</div>
	<style>
		.Flexi_deposit {
    margin-bottom: 240px;
}
.fdsaved-table {
    margin-top: 52px;
}
	</style>