<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html lang="en">

<head>
		  <meta charset="utf-8">
		  <meta name="viewport" content="width=device-width, initial-scale=1">
		  <meta name="author" content="banking">
		  <title>Tasthana - Admin</title>
		
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
		<!-- Themes -->
		<link href="<%=request.getContextPath()%>/resources/css/themeBlue.css" rel="stylesheet">
		<link href="<%=request.getContextPath()%>/resources/css/themeOrange.css" rel="stylesheet">
		<link href="<%=request.getContextPath()%>/resources/css/themeGreen.css" rel="stylesheet">
		<link href="<%=request.getContextPath()%>/resources/css/themeRed.css" rel="stylesheet">
		<link href="css/font-awesome.css" rel="stylesheet" type="text/css">
		<link href="<%=request.getContextPath()%>/resources/css/admin.css" rel="stylesheet">
		<link href="<%=request.getContextPath()%>/resources/css/style.css" rel="stylesheet">
		<link href="https://fonts.googleapis.com/css?family=Raleway" rel="stylesheet">
		<script src="https://use.fontawesome.com/07b0ce5d10.js"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
		<script src="<%=request.getContextPath()%>/resources/js/search.js"></script>
		<script src="<%=request.getContextPath()%>/resources/js/countries.js"></script>
 </head>

<body>
<!-- Header -->
<div class="header_forgot">
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
				<div class="content">
					<form:form action="updateLoginChangePwd" autocomplete="off" class="form-horizontal" onsubmit="return validateForm()" commandName="endUserForm" id="pwdForm">
						
						<div class="form_page">
						<form:hidden path="id"/>
						<form:hidden path="transactionId"/>
						<div class="header_customer">
							<h3>Details</h3>
						</div>
						<div style="text-align:center;">
							<font color="red" >${error}</font>
						</div>
				<div id="pwdUnEqualError" style="text-align:center; display: none; color: red;">New and Confirm Passwords must match</div>
						
						
						
						<div class="form-group">
						    <label class="col-md-4 control-label">Old Password :</label>
						    <div class="col-md-6">
						     <form:password path="password" class="form-control"
								id="password" placeholder="Old Password" autocomplete="off"></form:password>
							<span id="passwordError" style="display: none; color: red;">Password is Mandatory</span>	
						    </div>
						</div>
						<div class="form-group">
						    <label class="col-md-4 control-label">New Password :</label>
						    <div class="col-md-6">
						     <form:password path="newPassword" class="form-control" id="newPassword"
								placeholder="New Password" autocomplete="off"></form:password>
							<span id="newPwdError" style="display: none; color: red;">Password
							is Mandatory</span>	
						    </div>
						</div>
						<div class="form-group">
						    <label class="col-md-4 control-label">Confirm New Password :</label>
						    <div class="col-md-6">
						    	 <form:password path="confirmNewPassword" class="form-control" id="confirmNewPassword"
									placeholder="Confirm New Password" autocomplete="off"></form:password>
								<span id="cfmPwdError" style="display: none; color: red;">Password
								is Mandatory</span>
						    </div>
						</div>
						
							<div class="form-group">
							    <label class="col-md-4 control-label"></label>
							    <div class="col-md-6">
							     <form:hidden path="id" value="${endUserForm.id}"/>
									<button class="button2" type="submit"><spring:message code="label.save"/></button>
									<button type="button" name="Back" onclick="javascript:window.location='<%=request.getContextPath()%>/';" class="button"><spring:message code="label.cancel"/></button>
							    </div>
							</div>
				
						</div>
				</form:form>
				</div>
	</div>
<script>
	function validateForm() {

		var oldPassword = document.getElementById('password').value;
		var newPassword = document.getElementById('newPassword').value;
		var confirmNewPassword = document.getElementById('confirmNewPassword').value;

		var canSubmit = true;

		if (oldPassword == '') {
			document.getElementById('passwordError').style.display = 'block';
			canSubmit = false;
		} else {
			document.getElementById('passwordError').style.display = 'none';
		}

		if (newPassword == '') {
			document.getElementById('newPwdError').style.display = 'block';
			canSubmit = false;
		} else {
			document.getElementById('newPwdError').style.display = 'none';
		}
		
		if (confirmNewPassword == '') {
			document.getElementById('cfmPwdError').style.display = 'block';
			canSubmit = false;
		} else {
			document.getElementById('cfmPwdError').style.display = 'none';
		}
		
		if(newPassword != confirmNewPassword) {
			document.getElementById("pwdForm").reset();
			document.getElementById('pwdUnEqualError').style.display = 'block';
			canSubmit = false;
		} else {
			document.getElementById('pwdUnEqualError').style.display = 'none';
		}

		if (canSubmit == false) {
			return false;
		}

	}
	
</script>
<style>
body {
    padding: 0px;
    margin: 0px;
    background: rgba(101, 101, 101, 0.2);
    overflow-x: hidden;
}
.header_forgot{
	background: #FFF;
    width: 100%;
    z-index: 1;
}

.header_customer{
	margin-bottom:30px;
}
.content {
    width: 1000px;
    margin: 0 auto;
    background: #ffffff;
    float: inherit;
    box-shadow: 0px 0px 3px 0px #988787;
    display: table;
    position: relative;
    top: 114px;
        border: 1px solid #03a9f4;
}
.header_forgot {
    background: #FFF;
    width: 100%;
    z-index: 1;
    padding: 10px;
}
.container1 {
    width: 100%;
    margin: 0 auto;
    display: table;
}

.button {
    display: inline-block;
    border-radius: 4px;
    background-color: #f4511e;
    border: none;
    color: #FFFFFF;
    text-align: center;
    font-size: 20px;
    padding: 5px;
    width: 100px;
    transition: all 0.5s;
    cursor: pointer;
    margin-top: 18px;
}
.button2 {
    display: inline-block;
    border-radius: 4px;
    background-color: rgb(17, 173, 70) !important;
    border: none;
    color: #FFFFFF;
    text-align: center;
    font-size: 20px;
    padding: 5px;
    width: 100px;
    transition: all 0.5s;
    cursor: pointer;
    margin-top: 18px;
}
.myfooter {
    margin-top: 16% !important;
    background: rgba(171, 171, 171, 0.44);
    width: 100%;
    padding: 20px 0px;
    text-align: center;
}
	</style>
