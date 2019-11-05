<%@include file="taglib_includes.jsp"%>
<div class="right-container" id="right-container">
        <div class="container-fluid">
			
<div class="edit-bank-password">
<div class="header_customer" style="margin-bottom:30px;">
				<h3>Change Password</h3>
			</div>
	<div class="col-sm-12 col-md-12 col-lg-12">
		<div class="p_content">
			<form:form action="updateEditBankPWD" name="editProfile" class="form-horizontal" autocomplete="off" onsubmit="return validateForm()"
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

					
					<div class="form-group">
								<label class="col-md-4 control-label"><spring:message code="label.newPass" /></label>
								<div class="col-md-4">
									<form:password path="newPassword" class="form-control" id="newPassword" placeholder="Enter new Password" autocomplete="off"></form:password>
									<span id="newPasswordError" style="display: none; color: red;">
									<spring:message code="label.passVal1" />
									</span>
									<span id="newPasswordError" style="display: none; color: red;">
										<spring:message code="label.passVal1" />
									</span>
								</div>
					</div>
					<div class="form-group">
								<label class="col-md-4 control-label"><spring:message code="label.confirmPass" /></label>
								<div class="col-md-4">
									<form:password path="confirmNewPassword" class="form-control" id="confirmNewPassword" placeholder="Enter confirm New Password" autocomplete="off"></form:password>
									<span id="confirmNewPasswordError"
									style="display: none; color: red;">
									<spring:message code="label.passVal2" />
									</span>
									<span id="confirmNewPasswordError"
										style="display: none; color: red;">
										<spring:message code="label.passVal2" />
									</span>
								</div>
					</div>
					<div class="form-group">
								<label class="col-md-4 control-label"></label>
								<div class="col-md-6">
									<button class="btn btn-md btn-primary btn-block" type="submit"
									style="width: 100px;">
									<spring:message code="label.update" />
								</button>
								</div>
					</div>
					
					
					
				</div>
			</form:form>
		</div>
	</div>
</div>
<style>

	.p_content {
    float: left;
    width: 100%;
    margin-bottom: 100px;
}
</style>
<script>
	
	function validateForm() {

		var newPassword = document.getElementById('newPassword');

		var confirmNewPassword = document.getElementById('confirmNewPassword');

		var canSubmit = true;

		if (document.getElementById('newPassword').value == '') {
			document.getElementById('newPasswordError').style.display = 'block';
			canSubmit = false;
		} else {
			document.getElementById('newPasswordError').style.display = 'none';
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