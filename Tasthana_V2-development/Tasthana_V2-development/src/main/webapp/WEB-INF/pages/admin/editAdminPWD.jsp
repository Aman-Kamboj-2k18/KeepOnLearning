<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div class="right-container" id="right-container">
        <div class="container-fluid">
		
<div class="edit-admin-pwd">
			<div class="header_customer">
				<h3>Change Password</h3>
			</div>
	<div class="col-sm-12 col-md-12 col-lg-12">
		<div class="p_content">
			<form:form action="updateEditAdminPWD" name="editProfile"
				autocomplete="off" onsubmit="return validateForm()"
				commandName="endUserForm">

				<div class="form_page">
					<form:hidden path="id" />
					<form:hidden path="transactionId" />
					<div id="alertPWD" style="display: none; color: red;">
						<spring:message code="label.passVal" />
					</div>
					<div id="alertPWD" style="display: none; color: red;">
						<spring:message code="label.passVal" />
					</div>

					<table width="600">

						<tr>
							<td><label><spring:message code="label.newPass" />
								:</label></td>

							<td><form:password path="newPassword"
									class="myform-control" id="newPassword"
									placeholder="Enter new Password" autocomplete="off"></form:password>
							</td>
						</tr>
						<tr>
							<td></td>
							<td id="newPasswordError" style="display: none; color: red;"><spring:message
										code="label.passVal1" /></td>
							 <td id="newPasswordError" style="display: none; color: red;"><spring:message
										code="label.passVal1" /></td>
						</tr>
						<tr>
							<td><label><spring:message
										code="label.confirmPass" /> :</label></td>
							<td><form:password
									path="confirmNewPassword" class="myform-control"
									id="confirmNewPassword"
									placeholder="Enter confirm New Password" autocomplete="off"></form:password>
							</td>
							
						</tr>
						<tr>
							<td></td>
							<td id="confirmNewPasswordError"
								style="display: none; color: red;"><spring:message
										code="label.passVal2" /></td> <td id="confirmNewPasswordError"
								style="display: none; color: red;"><spring:message
										code="label.passVal2" /></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td></td>
							<td colspan="2"><a href="adminPage"
								class="btn btn-success"><spring:message code="label.back" /></a> <input type="submit"
								value="<spring:message code="label.update"/>"
								class="btn btn-primary"> </td>
						</tr>

					</table>
				</div>
			</form:form>
		</div>
	</div>

</div>
<style>
	.edit-admin-pwd{
		margin-bottom:160px;
	}
</style>
<script>
	function validateForm() {

		var newPassword = document.getElementById('newPassword');

		var confirmNewPassword = document.getElementById('confirmNewPassword');
		var password = /^[A-Za-z0-9!@#$%^&*()_]{6,20}$/;
		var canSubmit = true;

		if (newPassword.value.match(/^[A-Za-z0-9!@#$%^&*()_]{6,20}$/)
				&& !(document.getElementById('newPassword').value == '')) {

			document.getElementById('newPasswordError').style.display = 'none';

		} else {
			document.getElementById('newPasswordError').style.display = 'block';
			canSubmit = false;
		}

		if (document.getElementById('confirmNewPassword').value == '') {
			document.getElementById('confirmNewPassword').style.borderColor = "red";
			return false;
		} else {
			document.getElementById('confirmNewPassword').style.borderColor = "green";
		}

		if (document.editProfile.newPassword.value == document.editProfile.confirmNewPassword.value) {
			document.getElementById('alertPWD').style.display = 'none';
		} else {

			document.getElementById('alertPWD').style.display = 'block';
			canSubmit = false;
		}

		if (canSubmit == false) {
			return false;
		}

	}
</script>