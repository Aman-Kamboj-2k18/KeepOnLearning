<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="right-container" id="right-container">
	<div class="container-fluid">


		<div class="confirm-edit-am-profile">

			<div class="col-sm-12 col-md-12 col-lg-12 header_customer">
				<h3 align="center">
					<spring:message code="label.confirmationScreen" />
				</h3>
			</div>
			<form:form name="amDetails" action="updateVPDetails"
				commandName="endUserForm" onsubmit="return validateForm()">
				<table style="margin: 0px auto;" width="600">
					<form:hidden path="id" />


					<tr>
						<td><b><spring:message code="label.userName" /></b></td>
						<td><form:input path="userName" id="userName"
								class="myform-control" value="" readonly="true" /></td>
					</tr>

					<tr>
						<td><b><spring:message code="label.displayName" /></b></td>
						<td><form:input path="displayName" id="displayName"
								class="myform-control" value="" readonly="true" /></td>
					</tr>


					<tr>
						<td><b><spring:message code="label.contactNumber" /></b></td>
						<td><form:input path="contactNo" value=""
								class="myform-control" readonly="true" /></td>

					</tr>

					<tr>
						<td><b><spring:message code="label.altcontactNumber" /></b></td>
						<td><form:input path="altContactNo" value=""
								class="myform-control" readonly="true" /></td>

					</tr>

					<tr>
						<td><b><spring:message code="label.email" /></b></td>
						<td><form:input path="email" value="" class="myform-control"
								readonly="true" /></td>
					</tr>
					<tr>
						<td><b><spring:message code="label.altEmail" /></b></td>
						<td><form:input path="altEmail" value=""
								class="myform-control" readonly="true" /></td>
					</tr>

					<form:hidden path="transactionId" value="" />
				</table>

				<div class="space-10"></div>
				<table style="margin: 0px auto; position: relative; left: 2.15em;">
					<tr>
						<td><a href="editVPProfile?id=${endUserForm.id}"
							class="btn btn-success"><spring:message code="label.back" /></a>
							<input type="submit"
							value="<spring:message code="label.update"/>"
							class="btn btn-primary"></td>
					</tr>
				</table>

			</form:form>
		</div>

		<style>
.myform-control {
	background: #AFAFAF;
	color: black;
	cursor: no-drop;
}
</style>