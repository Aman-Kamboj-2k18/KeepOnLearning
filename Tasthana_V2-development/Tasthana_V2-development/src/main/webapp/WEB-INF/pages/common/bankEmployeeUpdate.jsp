<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<div class="right-container" id="right-container">
	<div class="container-fluid">

		<div class="role-page">

			<div class="col-sm-12 col-md-12 col-lg-12 header_customer">
				<div class="header_customer">
					<h3>
						<spring:message code="label.confirmationScreen" />
					</h3>
				</div>
			</div>
			<div class="col-sm-12 col-md-12 col-lg-12">
				<div class="successMsg"
					style="text-align: center; color: green; font-size: 18px;">${success}</div>
			</div>
			<form:form name="aprrovalManagerUpdate"
				action="aprrovalManagerUpdate" commandName="endUserForm"
				onsubmit="return validateForm()">
				<div class="role-page-table col-sm-12">
					<table width="600">
                          <form:hidden path="id"/>
						<tr>
							<td><b><spring:message code="label.id" /></b></td>
							
							<td><form:input path="bankId" id="bankId" class="myform-control"
									value="${model.endUserForm.bankId}" readonly="true" />
						</tr>

						<tr>
							<td><b><spring:message code="label.displayName" /></b></td>
							<td><form:input path="userName" class="myform-control"
									value="${model.endUserForm.displayName}" readonly="true" /></td>
							<form:hidden path="transactionId" />
						</tr>

						<tr>
							<td><b><spring:message code="label.contactNumber" /></b></td>
							<td><form:input path="contactNo" class="myform-control"
									value="${model.endUserForm.contactNo}" readonly="true" /></td>
						</tr>

						<tr>
							<td><b><spring:message code="label.email" /></b></td>
							<td><form:input path="email" class="myform-control"
									value="${model.endUserForm.email}" readonly="true" /></td>
					</table>
				</div>
				<div class="col-sm-12 col-md-12 col-lg-12">
					<div class="space-10"></div>
				</div>
				<table class="col-sm-offset-3"
					style="position: relative; right: 5%;">
					<tr>
						<td class="col-sm-8"><a href="createRole?menuId=96" class="btn btn-success"><spring:message
									code="label.back" /></a> <input type="submit"
							value="<spring:message code="label.update"/>"
							class="btn btn-primary"></td>

					</tr>
				</table>

			</form:form>
		</div>
	</div>

	<style>
.space-10 {
	margin-top: 30px;
}

.myform-control {
	background: #AFAFAF;
	color: black;
	cursor: no-drop;
}
</style>