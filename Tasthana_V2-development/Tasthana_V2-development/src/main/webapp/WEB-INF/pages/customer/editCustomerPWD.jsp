<%@include file="taglib_includes.jsp"%>

  <div class="customer-edit-password" style="margin-top: 140px;">

				<div class="col-sm-12 col-md-12 col-lg-12">
				<div class="p_content">
					<form:form action="updateEditCustomerPWD" name="editProfile" autocomplete="off" onsubmit="return validateForm()" commandName="endUserForm">
						
						<div class="form_page">
						<form:hidden path="id"/>
						<form:hidden path="transactionId"/>
				<div id="alertPWD" style="display: none; color: red; margin-left: 720px !important;margin-top: 25px !important;"><spring:message code="label.bothShouldMatch"/></div>
						
							<table style="margin:20px auto 0;" width="600">
							<tr>
								<td>
									<label for=""><spring:message code="label.newPassword"/></label></td>
									
									<td><form:password path="newPassword" class="myform-control"
										id="newPassword" placeholder="Enter new Password" autocomplete="off" ></form:password>
										
								</td>
							</tr>
							<tr>
								<td></td>
									<td id="newPasswordError" style="display: none; color: red;"><spring:message code="label.newPasswordMandatory"/></td>
			              			
								
							</tr>
							<tr>
									<td><label for=""><spring:message code="label.confirmPassword"/></label></td>
									<td><form:password path="confirmNewPassword" class="myform-control"
										id="confirmNewPassword" placeholder="Enter confirm New Password" autocomplete="off"></form:password>
									</td>
							</tr>
							<tr>
								<td></td>
									<td id="confirmNewPasswordError" style="display: none; color: red;"><spring:message code="label.confirmPasswordMandatory"/></td>
				             		<td id="confirmNewPasswordError" style="display: none; color: red;"><spring:message code="label.confirmPasswordMandatory"/></td>
							
							</tr>
							<tr><td>&nbsp;</td></tr>	
								<tr>
									<td>									
									<td><a href="user" class="btn btn-success" ><spring:message code="label.back"/></a> <input type="submit"  value="<spring:message code="label.update"/>" class="btn btn-primary">
									</td>
								</tr>
							</table>
						</div>
				</form:form>
				</div>
		</div>
	</div>


<script>
	function validateForm() {

		var newPassword = document.getElementById('newPassword');
		alert("newPassword..."+newPassword);
		var confirmNewPassword = document.getElementById('confirmNewPassword');
	    var password = /^[A-Za-z0-9!@#$%^&*()_]{6,20}$/;
		var canSubmit = true;

	 	if(newPassword.value.match(/^[A-Za-z0-9!@#$%^&*()_]{6,20}$/) &&!(document.getElementById('newPassword').value == ''))
	  	{
	  		
	  		document.getElementById('newPasswordError').style.display='none';
	  	
	  	}
	  	else{
	  		document.getElementById('newPasswordError').style.display='block';
	  	     canSubmit = false;
	  	} 
		
		if (document.getElementById('confirmNewPassword').value == '') {
			document.getElementById('confirmNewPasswordError').style.display = 'block';
			canSubmit = false;
		} else {
			document.getElementById('confirmNewPasswordError').style.display = 'none';
		}
		
		if(document.editProfile.newPassword.value == document.editProfile.confirmNewPassword.value )
			{
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