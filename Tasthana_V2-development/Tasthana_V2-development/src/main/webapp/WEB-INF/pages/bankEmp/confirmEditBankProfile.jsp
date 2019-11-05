<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="right-container" id="right-container">
        <div class="container-fluid">
 
<div class="confirm-edit-profile">
	<div class="header_customer">
		<h3 style="text-align:center;">
			<spring:message code="label.profileDetails" />
		</h3>
	</div>
	<form:form name="bankDetails" action="updateBankDetails"
		commandName="endUserForm" onsubmit="return validateForm()">
		<table align="center" width="550">
			<form:hidden path="id" />


			<tr>
				<td><b><spring:message
							code="label.userName" /></b></td>
				<td><form:input path="userName" id="userName" class="myform-control"
						value="" readonly="true" />
			</tr>

			<tr>
				<td><b><spring:message
							code="label.displayName" /></b></td>
				<td><form:input path="displayName" class="myform-control"
						id="displayName" value="" readonly="true" />
			</tr>


			<tr>
				<td><b><spring:message
							code="label.contactNumber" /></b></td>
				<td><form:input path="contactNo" value="" class="myform-control"
						readonly="true" /></td>

			</tr>

			<tr>
				<td><b><spring:message
							code="label.altContactNo" /></b></td>
				<td><form:input path="altContactNo" value="" class="myform-control"
						readonly="true" /></td>

			</tr>

			<tr>
				<td><b><spring:message code="label.email" /></b></td>
				<td><form:input path="email" value="" class="myform-control"
						readonly="true" /></td>
			</tr>
			<tr>
				<td><b><spring:message
							code="label.altEmail" /></b></td>
				<td><form:input path="altEmail" value="" class="myform-control"
						readonly="true" /></td>
			</tr>

			<form:hidden path="transactionId" value="" />
		</table>
		<div class="space-10"></div>
		<div class="col-sm-12">
			<table align="center">
				<tr>
					<td class="col-sm-8"><a href="editBankProfile?id=${endUserForm.id }" class="btn btn-success"><spring:message
								code="label.back" /></a> <input type="submit"
						value="<spring:message code="label.update"/>"
						class="btn btn-primary"></td>
				</tr>
			</table>
		</div>

	</form:form>
</div>
<<style>
.myform-control {
    background: #9FA09C;
    color: black;
    cursor: no-drop;
}
</style>
