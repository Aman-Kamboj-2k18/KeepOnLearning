<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<div class="header_new">
	<div class="tasthana_header">
		<div class="container">
			<div class="col-md-6 col-sm-6 col-xs-6">
				<img src="<%=request.getContextPath()%>/resources/images/logo_header.png" style="width:299px;" class="img-responsive logo_header">
			</div>
			<div class="col-md-6 col-sm-6 col-xs-6" align="center">
				<img src="<%=request.getContextPath()%>/resources/images/tasthana_header.PNG" class="img-responsive tastana_header">
			</div>
		</div>
	</div>
</div>
<div class="container">
<div class="newpassword">
<div class="panel panel-default">
  <div class="panel-heading">Reset Password</div>
  <div class="panel-body">
					<form:form action="newForgotPassword" class="form-horizontal" name="editProfile" autocomplete="off" onsubmit="return validateForm()" commandName="endUserForm">
						
						
						<form:hidden path="id"/>
						<form:hidden path="transactionId"/>
				
						<div id="alertPWD" style="display: none; color: red;">Both password should match</div>
						<div id="alertPWD" style="display: none; color: red;">Both password should match</div>
						
							<div class="form-group">
								<label class="col-md-4 control-label">New password</label>
								<div class="col-md-7">
									<form:password path="newPassword" class="form-control fpw" id="newPassword" placeholder="new Password" autocomplete="off" ></form:password>
										<span id="newPasswordError" style="display: none; color: red;">New Password is Mandatory</span>
										<span id="newPasswordError" style="display: none; color: red;">New Password is Mandatory</span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label">Confirm New password</label>
								<div class="col-md-7">
								<form:password path="confirmNewPassword" class="form-control fpw"
										id="confirmNewPassword" placeholder="confirm New Password" autocomplete="off"></form:password>
										<span id="confirmNewPasswordError" style="display: none; color: red;">Confirm New Password is Mandatory</span>
										<span id="confirmNewPasswordError" style="display: none; color: red;">Confirm New Password is Mandatory</span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label"></label>
								<div class="col-md-7">
									<button class="btn btn-primary" type="submit" ><span></span>Update</button>
									<a href="login" ><input type="button"  class="btn btn-danger" value="Cancel"/></a>
								</div>
							</div>
					
				</form:form>
	</div>
	</div></div></div>
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
<style>
body {
    font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;
    font-size: 14px;
    line-height: 1.42857143;
    color: #333;
    background-color: #eee;
}
img.img-responsive.tastana_header {
	    width: 327px;
	}
.newpassword {
    width: 675px;
    margin: 0px auto;
    padding: 150px 0px;
}
		.p_content {
    margin-top: 122px;
}	h3 {
    text-align: center;
    color: #797979;
    font-family: sans-serif;
}
.header_new {
    background: #FFF;
    padding: 10px;
}
	</style>