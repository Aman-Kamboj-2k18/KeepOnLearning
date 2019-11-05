<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<div class="right-container" id="right-container">
        <div class="container-fluid">
		

<div class="bank-emp-list">
	<div class="header_customer">
		<h3 align="center"><spring:message code="label.approval"/></h3>
	</div>
	<form:form name="suspendCustomerUpdate" action="suspendBankEmpConfrim"
		method="post" commandName="endUserForm" onsubmit="return val();">
		<table style="margin:0px auto;" width="600">

			<tr>
				<td><b><spring:message code="label.id" /></b><span
					style="color: red">*</span></td>
				<td><form:input path="id" id="id" class="myform-control" value="${model.endUserForm.id}" readonly="true" /></td>
			</tr>
			<tr>
				<td><b><spring:message
							code="label.userName" /></b></td>
				<td><form:input path="userName" id="userName" class="myform-control"
						value="${model.endUserForm.userName}" readonly="true" />
			</tr>

			<%-- <tr>
							<td><b>Display Name :</b></td>
							<td><form:input path="displayName"
									value="${model.endUserForm.displayName}" readonly="true" /></td>
						</tr> --%>
			<tr>
				<td><b><spring:message code="label.email" /></b></td>
				<td><form:input path="email" class="myform-control"
						value="${model.endUserForm.email}" readonly="true" /></td>

			</tr>

			<tr>
				<td><b><spring:message
							code="label.contactNumber" /></b></td>
				<td><form:input path="contactNo" class="myform-control"
						value="${model.endUserForm.contactNo}" readonly="true" /></td>
				<form:hidden path="transactionId" value="" />
			</tr>


			<tr>
				<td><b><spring:message code="label.status" /></b><span
					style="color: red">*</span></td>
				<td><form:select path="status" id="status" class="myform-control"
						width="205px;!important">
						<form:option value="">
							<spring:message code="label.selectValue" />
						</form:option>
						
						<form:option value="Suspended">
							<spring:message code="label.suspend" />
						</form:option>
						
					</form:select>
				</td>

			</tr>
			<tr>
				<td></td>
				<td id="statusError" class="error" style="display: none;"><font
					color="red"><spring:message code="label.selectValue" /></font></td>
				<td id="statusError" class="error" style="display: none"><spring:message
						code="label.selectValue" /></td>
			</tr>
			<tr>
				<td><b><spring:message
							code="label.comment" /></b><span style="color: red">*</span></td>
				<td><form:textarea path="comment" id="comment" class="myform-control" maxlength="255"
						placeholder="Enter Comment" style="height:120px;"></form:textarea></td>
				
			</tr>
			<tr>
				<td></td>
				<td id="commentError" class="error" style="display: none;"><font
					color="red"><spring:message code="label.commentValidation" /></font></td>
				<td id="commentError" class="error" style="display: none"><spring:message
						code="label.commentValidation" /></td>
			</tr>
		</table>

		<div class="space-10"></div>
			<table align="center" style="position: relative;right: 3.3%;">
				<tr>
					<td class="col-sm-8"><a href="bankEmpSuspend" class="btn btn-success"><spring:message
								code="label.back" /></a> <input type="submit"
						value="<spring:message code="label.confirm"/>"
						class="btn btn-primary"></td>
				</tr>
			</table>

	</form:form>
</div>

<style>
select#select {
	width: 205px;
}
</style>
<script>
		 
				
	function val() {

		if (document.getElementById('comment').value == ''){
			document.getElementById('comment').style.borderColor="red";
			return false;
		}
		else{
			document.getElementById('comment').style.borderColor="green";
		}
		if (document.getElementById('status').value == ''){
			document.getElementById('status').style.borderColor="red";
			return false;
		}
		else{
			document.getElementById('status').style.borderColor="green";
		}
	}
</script>
