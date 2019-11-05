<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<div class="right-container" id="right-container">
	<div class="container-fluid">

		<div class="role-page">

			<div class="col-sm-12 col-md-12 col-lg-12 header_customer">
				<h3 align="center">
					<spring:message code="label.approvalManager" />
				</h3>
			</div>
			<div class="col-sm-12 col-md-12 col-lg-12">
				<div class="successMsg"
					style="text-align: center; color: green; font-size: 18px;">${success}</div>
			</div>

			<form:form name="aprrovalManagerUpdate"
				action="aprrovalManagerUpdate" commandName="endUserForm"
				onsubmit="return validateForm()">
				<div class="role-page-table">
					<table style="margin: 0px auto;" width="600">

						<tr>
							<td><b><spring:message code="label.id" /> :</b></td>
							<td><form:input path="id" id="id" class="myform-control"
									value="${model.endUserForm.id}" readonly="true" /></td>

						</tr>


						<tr>
							<td><b><spring:message code="label.role" /> :</b></td>
							<td><form:input path="role" class="myform-control"
									value="${model.endUserForm.role}" readonly="true" /></td>
						</tr>


						<tr>
							<td><b><spring:message code="label.contactNumber" /> :</b></td>
							<td><form:input path="contactNo" class="myform-control"
									value="${model.endUserForm.contactNo}" readonly="true" /></td>
						</tr>

						<tr>
							<td><b><spring:message code="label.email" /> :</b></td>
							<td><form:input path="email" class="myform-control"
									value="${model.endUserForm.email}" readonly="true" /></td>
						<tr>
							<td><b><spring:message code="label.userName" /> :</b></td>
							<td><form:input path="userName" class="myform-control"
									value="${model.endUserForm.userName}" readonly="true" /></td>
							<form:hidden path="transactionId" value="" />
						</tr>

				

					</table>
				</div>
				<div class="space-10"></div>
				<table align="center">
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
</div>
<style>
.myform-control {
	background: #AFAFAF;
	color: black;
	cursor: no-drop;
}
</style>