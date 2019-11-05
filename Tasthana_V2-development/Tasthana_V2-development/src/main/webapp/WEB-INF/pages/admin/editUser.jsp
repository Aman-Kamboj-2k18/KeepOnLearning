<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<div class="right-container" id="right-container">
	<div class="container-fluid">

		<div class="role-page">

			<div class="col-sm-12 col-md-12 col-lg-12 header_customer">
				<h3 class="header_customer">
					<spring:message code="label.updateRole" />
				</h3>
			</div>
			<form:form name="updateRole1" action="updateRole1" method="post"
				commandName="endUserForm" onsubmit="return val();">

				<form:hidden path="transactionId"  />
				<form:hidden path="role" value="${endUserForm.role}" />

				<div class="role-page-table">
					<table width="600">
                        <form:hidden path="id"/>
						<tr>
							<td><b><spring:message code="label.empId" /></b></td>
							<td><form:input path="bankId" id="bankId"
									class="myform-control" readonly="true" style="background-color: #eee !important;" /></td>

						</tr>
						<tr>
							<td><b><spring:message code="label.empName" /></b></td>
							<td><form:input path="displayName" id="displayName"
									class="myform-control" readonly="true" style="background-color: #eee !important;" /></td>

						</tr>
						<tr>
							<td><b><spring:message code="label.contactNumber" /></b></td>
							<td><form:input path="contactNo" class="myform-control"
									placeholder="Enter contact Number"  id="contactNum" />
								<div id="contactNum1Error" style="display: none; color: red;">
									<spring:message code="label.validation" />
								</div></td>

						</tr>

						<tr>
							<td><b><spring:message code="label.email" /></b></td>
							<td><form:input path="email" class="myform-control"
									placeholder="Enter Email"  id="email" onblur="validateEmail(this);"/>
								<div id="emailError" style="display: none; color: red;">
									<spring:message code="label.validation" />
								</div></td>

						</tr>





					</table>
				</div>
				<div class="space-10"></div>
				<table class="col-sm-offset-2" style="position: relative; left: 2%;">
					<tr>
						<td class="col-sm-8"><a href="createRole" class="btn btn-success"><spring:message
									code="label.back" /></a> <input type="submit" size="3"
							value="<spring:message code="label.confirm"/>"
							class="btn btn-primary"></td>

					</tr>
				</table>

			</form:form>


		</div>
	</div>

	</body>

	<script>
	
	
	
	$( document ).ready(function() {
		
		var contactNum = document.getElementById('contactNum');
		
		contactNum.onkeydown = function(e) {
		    if(!((e.keyCode > 95 && e.keyCode < 106)
		      || (e.keyCode > 47 && e.keyCode < 58) 
		      || e.keyCode == 8)) {
		        return false;
		    }
		    if (contactNum.value.length > 9 
		    		&& e.keyCode != 8){
		    	return false;
		    }
		}
	});
	
	
	
	
	function validateEmail(emailField){
        var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;

        if (reg.test(emailField.value) == false) 
        {
            alert('Invalid Email Address');
            return false;
        }

        return true;

}
		function val() {

			var phoneNum = /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;
			var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;

			var contactNum = document.getElementById('contactNum');
			var email = document.getElementById('email');
			var userName = document.getElementById('userName');
			var canSubmit = true;

			if (contactNum.value
					.match(/^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/)
					&& !(document.getElementById('contactNum').value == '')) {

				document.getElementById('contactNum1Error').style.display = 'none';
			} else {

				document.getElementById('contactNum1Error').style.display = 'block';
				canSubmit = false;
			}

			if (email.value
					.match(/^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/)
					&& !(document.getElementById('email').value == '')) {

				document.getElementById('emailError').style.display = 'none';

			} else {
				document.getElementById('emailError').style.display = 'block';
				canSubmit = false;
			}
			if (document.getElementById('userName').value == '') {
				document.getElementById('userNameError').style.display = 'block';
				canSubmit = false;
			} else {
				document.getElementById('userNameError').style.display = 'none';
			}

			if (canSubmit == false) {
				return false;
			}
		}
	</script>