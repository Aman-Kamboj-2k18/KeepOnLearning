<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

	<link href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css" rel="stylesheet">
<script>
	function validateForm() {

		var newPassword = document.getElementById('userName');

		var confirmNewPassword = document.getElementById('confirmNewPassword');

		var canSubmit = true;

		if (document.getElementById('userName').value == '') {
			document.getElementById('userNameError').style.display = 'block';
			canSubmit = false;
		} else {
			document.getElementById('userNameError').style.display = 'none';
		}

		if (document.getElementById('email').value == '') {
			document.getElementById('emailError').style.display = 'block';
			canSubmit = false;
		} else {
			document.getElementById('emailError').style.display = 'none';
		}

		if (canSubmit == false) {
			return false;
		}

	}
</script>
<style>
		.cmnyname{
			color: #2196F3;
		    display: -webkit-inline-box;
		    letter-spacing: 1px;
		    font-weight: 700;
		    font-family: sans-serif;
		    margin-top: 0.5em;
		    font-size:1em;
		}
		.tastana-img {
		    width: 315px;
		    height: 149px;
		    margin-top: 0px;
		    position: static;
		}
		.logo {
		    float: left;
		    width: 306px;
		    margin-top: 2%;
		}
		.logo img{
			 width: 125px;
   			 height: 98px;
		}
		.loginForm1 {
		    background-color: rgba(236, 236, 236, 0.41);
		    padding: 31px;
		}
		.footer1 {
		   	 margin-top: 0% !important;
		       background: rgba(171, 171, 171, 0.44);
			    width: 100%;
			    padding: 6px 4px;
			    text-align: center;
			    position: absolute;
		}
		

</style>

 
 
<div class="container">
			<div class="col-sm-6 col-md-6">
					<div class="logo" align="center">
						<img src="<%=request.getContextPath()%>/resources/images/logopng44.png">
						<span class="cmnyname">ANNONA IT SOLUTIONS PVT. LTD.</span>
					</div>
			</div>
			<div class="col-sm-6 col-md-6">
				<div class="tastana-img" align="center">
					<img src="<%=request.getContextPath()%>/resources/images/tasthana.PNG" class="tastana-img">
				</div>
			</div> <%-- 
			<div class="mascot">
				<img src="<%=request.getContextPath()%>/resources/images/mascot3.gif" class="mas-img">
			</div> --%>
</div>
	<div class="loginForm1">

		
		<div class="col-sm-12" align="center" style="margin-top:3em;">
			<font color="red">${success}</font>
		</div>
		<div class="container" style="margin-bottom: 3.1em;">
            <div id="passwordreset" style="margin-top:8px" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
                <div class="panel panel-info">
                    <div class="panel-heading">
                        <div class="panel-title">Create New Password</div>
                    </div>                     
                    <div class="panel-body">
                    <form:form action="checkForgotPassword"  id="signupform" class="form-horizontal" role="form" autocomplete="off" onsubmit="return validateForm()" commandName="endUserForm">
                      
                            <div class="form-group">
                                <label for="email" class=" control-label col-sm-3">User Name</label>
                                <div class="col-sm-9">
                                    <form:input path="userName" class="form-control fpw"
							id="userName" placeholder="user Name" autocomplete="off"></form:input>
						<div id="userNameError" style="display: none; color: red;">User
							Name is Mandatory</div>
						<div id="userNameError" style="display: none; color: red;">User
							Name is Mandatory</div></td>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="email" class=" control-label col-sm-3">User Email</label>
                                <div class="col-sm-9">
                                   <form:input path="email" class="form-control fpw" id="email"
											placeholder="Enter email" autocomplete="off"></form:input>
										<div id="emailError" style="display: none; color: red;">Email
											is Mandatory</div>
										<div id="emailError" style="display: none; color: red;">Email
											is Mandatory</div>
				                                </div>
                            </div>
                            <div class="form-group">
                                <!-- Button -->                                 
                                <div class="  col-sm-offset-3 col-sm-9">
                                    <button class="btn btn-primary" type="submit"><span></span><spring:message code="label.save"/></button>
                                    <button type="button" name="Back"
							onclick="javascript:window.location='<%=request.getContextPath()%>';"
							class="btn btn-danger"><spring:message code="label.cancel"/></button>
                                </div>
                            </div>                             
                        </form:form>
                    </div>
                </div>
            </div>             
        </div>
	</div>