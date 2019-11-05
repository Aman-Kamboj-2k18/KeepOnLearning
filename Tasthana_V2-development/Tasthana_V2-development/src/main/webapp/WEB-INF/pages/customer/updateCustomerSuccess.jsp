
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="right-container" id="right-container">
	<div class="container-fluid">
		
		<div class="user-confirm-page-success">

			<div class="user-confirm-page-table">
				<div class="successMsg"
					style="text-align: center; color: green; font-size: 18px;">${success}</div>

				<table align="center" width="400">


						<td><b><spring:message code="label.transactionType" />:</b></td>
						<td><spring:message code="label.customerApproval" /></td>
					</tr>

					<tr>
						<td><b><spring:message code="label.status" />:</b></td>
						<td><font color="green"><spring:message
									code="label.updateSuccessfully" /></font></td>
					</tr>

				</table>


			</div>
		</div>
		</div>
		</div>
		<style>
.user-confirm-page-table {
	margin-bottom: 270px;
	margin-top: 30px;
}
</style>