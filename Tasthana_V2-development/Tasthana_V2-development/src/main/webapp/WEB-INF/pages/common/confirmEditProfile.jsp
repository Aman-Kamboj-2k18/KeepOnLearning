<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="right-container" id="right-container">
        <div class="container-fluid">
		
<div class="edit-admin-profile">
<div class="col-md-12">
	<div class="header_customer">
		<h3>
			<spring:message code="label.proDetails" />
		</h3>
	</div>
	</div>
	<form:form name="bankDetails" action="updateProfileDetails"
		commandName="endUserForm" onsubmit="return validateForm()">
		<table width="600">
			<form:hidden path="id" />


			<tr>
				<td><b><spring:message code="label.userName" /> :</b></td>
				<td><form:input path="userName" id="userName" class="myform-control" value="" readonly="true" />
			</tr>

			<tr>
				<td><b><spring:message code="label.displayName" /> :</b></td>
				<td><form:input path="displayName" class="myform-control" id="displayName" value="" readonly="true" />
			</tr>


			<tr>
				<td><b><spring:message code="label.contactNumber" /> :</b></td>
				<td><form:input path="contactNo" class="myform-control" value="" readonly="true" /></td>

			</tr>

			<tr>
				<td><b><spring:message code="label.altContactNo" /> :</b></td>
				<td><form:input path="altContactNo" class="myform-control" value="" readonly="true" /></td>

			</tr>

			<tr>
				<td><b><spring:message code="label.email" /> :</b></td>
				<td><form:input path="email" value="" class="myform-control" readonly="true" /></td>
			</tr>
			<tr>
				<td><b><spring:message code="label.altEmail" /> :</b></td>
				<td><form:input path="altEmail" class="myform-control" value="" readonly="true" /></td>
			</tr>

			<form:hidden path="transactionId" value="" />
		</table>
		<div class="col-sm-12 col-md-12">&nbsp;</div>
		<div class="col-sm-offset-2">
			<table style=" position: relative;left: 11%;">
				<tr>
					<td class="col-sm-8"><a href="homePage?menuId=${menus[0].id }" class="btn btn-success"><spring:message
								code="label.back" /></a> <input type="submit"
						value="<spring:message code="label.update"/>"
						class="btn btn-primary"> </td>
				</tr>
			</table>
		</div>

	</form:form>
</div></div>
<style>
	.myform-control{
		background: #AFAFAF;
    color: black;
    cursor: no-drop;
	}
</style>