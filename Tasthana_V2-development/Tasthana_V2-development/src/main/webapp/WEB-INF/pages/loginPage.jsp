
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<!DOCTYPE HTML>
<html lang="us-en">
<head>
<title>Login | Registration Page</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css">
<link
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"
	rel="stylesheet"
	integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/registration.css">
<style>
.panel.panel-login {
	margin-bottom: 139px;
}
</style>
</head>

<body>

	<div class="jumbotron jumbotron-sm"
		style="background: #fff; color: #358cce;">
		<div class="container">
			<div class="row">


				<div class="col-sm-3 col-lg-3">
					<img
						src="<%=request.getContextPath()%>/resources/images/logo_header.png"
						style="width: 299px;" class="img-responsive logo_header">
				</div>
				<div class="col-sm-6 col-lg-6">
					<h1 class="h1" style="text-align: center;">Tasthana</h1>
				</div>
				<div class="col-md-3 col-sm-3 col-xs-3" align="center">
					<img
						src="<%=request.getContextPath()%>/resources/images/tasthana_header.PNG"
						class="img-responsive tastana_header">
				</div>
			</div>
		</div>
	</div>
	<div class="col-sm-12 col-md-12 col-lg-12">
		<div class="successMsg"
			style="text-align: center; color: #f0f0f0; font-size: 18px;">${success}</div>
		<div class="successMsg"
			style="text-align: center; color: #f0f0f0; font-size: 18px;">

			<c:if
				test='${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message=="User account has expired"}'>
				<b>Your account has been expired or not active. Please contact
					admin</b>
			</c:if>
			<c:if
				test='${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message=="Index: 0, Size: 0"}'>
				<b>${error}</b>
			</c:if>
			<c:if
				test='${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message=="User account is locked"}'>
				<b>Your account is locked. Please contact admin</b>
			</c:if>
			<c:if
				test='${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message=="Bad credentials"}'>
				<b>Invalid Password</b>
			</c:if>
		</div>


	</div>
	<div class="container">
		<div class="row">
			<div class="col-md-6 col-md-offset-3">
				<div class="successMsg"
					style="text-align: center; font-size: 1.3em; color: #fff;">
					<%-- <b><font color="error" style="color:#fff;"><b>${error}</b></font></b> --%>
				</div>
				<div class="panel panel-login">
					<div class="panel-heading">

						<div class="row">

							<div class="col-xs-6 tabs">
								<a href="#" class="active" id="login-form-link"><div
										class="login">LOGIN</div></a>
							</div>
							<div class="col-xs-6 tabs">
								<a href="#" id="register-form-link"><div class="register">REGISTER</div></a>
							</div>
						</div>
					</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-lg-12">
								<c:url value="/j_spring_security_check" var="loginUrl" />
								<form id="login-form" action="${loginUrl}" method="post"
									role="form" style="display: block;">
									<h2>LOGIN</h2>
									<div class="form-group">
										<input type="text" autocomplete="off" id="j_username"
											name="j_username" tabindex="1" class="form-control"
											placeholder="Username" value="">
									</div>
									<div class="form-group">
										<input type="password" autocomplete="off" id="j_password"
											name="j_password" tabindex="2" class="form-control"
											placeholder="Password">
									</div>


									<div class="col-xs-6 form-group pull-left checkbox">
										<!--  <input id="checkbox1" type="checkbox" name="remember">
                    <label for="checkbox1">Remember Me</label>   <br> -->
										<a
											href="${pageContext.request.contextPath}/auth/forgotPassword"
											class="forgot-password">Forgot the password?</a>

									</div>
									<div class="col-xs-6 form-group pull-right">
										<input type="submit" name="sub" id="login-submit" tabindex="4"
											class="form-control btn btn-login" value="Log In">
									</div>
								</form>


								<form:form
									action="${pageContext.request.contextPath}/auth/registerUser"
									id="register-form" name="registerUser"
									commandName="endUserForm" autocomplete="off"
									onsubmit="return validateForm()" style="display: none;">
									<form:hidden path="transactionId" value="" />

									<h2>Admin Registration</h2>
									<div class="form-group">
										<label for="username">Admin Name</label>
										<form:input path="displayName" autocomplete="off"
											name="username" id="adminName" tabindex="1"
											class="form-control" placeholder="Enter Admin Name"
											required="required" />
									</div>

									<div class="form-group">
										<label for="email">User Email</label>
										<form:input type="email" path="email" autocomplete="off"
											name="email" id="email" tabindex="1" class="form-control"
											placeholder="Email Address" required="required"
											onblur="validateEmail(this);" />
									</div>
									<div class="form-group">
										<label for="phone">Contact Number</label>
										<form:input type="number" path="contactNo" autocomplete="off"
											name="phone" id="phone" tabindex="1" class="form-control"
											placeholder="Enter Phone Number" required="required" />
									</div>
									<div class="form-group">
										<label for="username">User Name</label>
										<form:input path="userName" autocomplete="off" name="username"
											id="username" tabindex="1" class="form-control"
											placeholder="Enter Admin Username" value=""
											required="required" />
									</div>
									<div class="form-group">
										<label for="password1">Password</label>
										<form:input type="password" path="password" autocomplete="off"
											name="password1" id="password" tabindex="1"
											class="form-control" placeholder="Enter Admin Password"
											required="required" />
									</div>
									<div class="form-group">
										<label for="password2">Re-Password</label> <input
											type="password" autocomplete="off" name="password2"
											id="rePassword" tabindex="1" class="form-control"
											placeholder="Re-Enter Password" required="required">
									</div>
									<div id="passwordError" style="display: none; color: red;">
										<spring:message code="label.passwordError" />
									</div>



									<div class="form-group">
										<div class="row">
											<div class="col-sm-6 col-sm-offset-3">
												<input type="submit" name="register-submit"
													id="register-submit" tabindex="4"
													class="form-control btn btn-register" value="Register Now">
											</div>
										</div>
									</div>
								</form:form>
							</div>
						</div>
					</div>

				</div>
			</div>
		</div>
	</div>
	<script
		src="<%=request.getContextPath()%>/resources/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/jquery.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/jquery-1.7.2.min.js"></script>

	<script>
		$(function() {
			$('#login-form-link').click(function(e) {
				$("#login-form").delay(100).fadeIn(100);
				$("#register-form").fadeOut(100);
				$('#register-form-link').removeClass('active');
				$(this).addClass('active');
				e.preventDefault();
			});
			$('#register-form-link').click(function(e) {
				$("#register-form").delay(100).fadeIn(100);
				$("#login-form").fadeOut(100);
				$('#login-form-link').removeClass('active');
				$(this).addClass('active');
				e.preventDefault();
			});

			$('#adminName').bind(
					'keypress',
					function(event) {
						var regex = new RegExp("^[a-zA-Z0-9]+$");
						var key = String
								.fromCharCode(!event.charCode ? event.which
										: event.charCode);
						if (!regex.test(key)) {
							//alert("false")
							event.preventDefault();
							return false;
						}
					});

			$('#username').bind(
					'keypress',
					function(event) {
						var regex = new RegExp("^[a-zA-Z0-9]+$");
						var key = String
								.fromCharCode(!event.charCode ? event.which
										: event.charCode);
						if (!regex.test(key)) {
							//alert("false")
							event.preventDefault();
							return false;
						}
					});

			var phone = document.getElementById('phone');

			phone.onkeydown = function(e) {
				if (!((e.keyCode > 95 && e.keyCode < 106)
						|| (e.keyCode > 47 && e.keyCode < 58) || e.keyCode == 8)) {
					return false;
				}
				if (phone.value.length > 9 && e.keyCode != 8) {
					return false;
				}
			}

		});
	</script>


	<script type="text/javascript">
		function validateForm() {

			var canSubmit = true;
			if (!(document.getElementById('rePassword').value == (document
					.getElementById('password').value))) {

				document.getElementById('passwordError').style.display = 'block';
				canSubmit = false;
			}

			var d = getTodaysDate().getTime();
			var uuid = 'xxxxxx'.replace(/[xy]/g, function(c) {
				var r = (d + Math.random() * 16) % 16 | 0;
				d = Math.floor(d / 16);
				return (c == 'x' ? r : (r & 0x3 | 0x8)).toString(16);
			});
			document.registerUser.transactionId.value = uuid;

			return canSubmit;
		}

		function validateEmail(emailField) {
			var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;

			if (reg.test(emailField.value) == false) {
				alert('Invalid Email Address');
				return false;
			}

			return true;

		}
	</script>

</body>

</html>