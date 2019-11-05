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
			<div class="exit" style="font-size: 19px;">
				<h3 class="errorMsg">${error}</h3>
				<h3 style="color: green">${sucess}</h3>
			</div>
			<form:form name="updateRole" action="updateRole" method="post"
				commandName="role" onsubmit="return val();">



				<div class="role-page-table">
					<table width="600">
                        <form:hidden path="id"/>
                         <form:hidden path="createdOn"/>
                          <form:hidden path="createdBy"/>
						<tr>
							<td><b>Display Name</b></td>
							<td><form:input path="roleDisplayName" id="roleDisplayName"
									class="myform-control" /></td>

						</tr>
						<tr>
							<td><b>Role</b></td>
							<td><form:input path="role" id="roleName"
									class="myform-control" /></td>

						</tr>
						 <tr>
							<td><b>Rights To Approve?</b></td>
							<td><form:select path="rightsTOApprove" id="rightsTOApprove"
									class="myform-control" >
									<form:option value="0">No</form:option>
									<form:option value="1">Yes</form:option>
									
									</form:select></td>

						</tr>
						 

						<tr>
							<td><b>Amount Limit</b></td>
							<td><form:input path="amountLimit" id="amountLimit"
									class="myform-control" /></td>

						</tr>
						

						




					</table>
				</div>
				<div class="space-10"></div>
				<table class="col-sm-offset-2" style="position: relative; left: 2%;">
					<tr>
						<td class="col-sm-8"><a href="createRole?menuId=97" class="btn btn-success"><spring:message
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
		
		
	});
	
	
	
	
	
function val() {
		
		var name = document.getElementById('roleDisplayName').value;
		var roleName = document.getElementById('roleName').value;
		var canSubmit = true;

		if (name == '') {
			document.getElementById('roleDisplayName').style.borderColor = "red";

			canSubmit = false;
		} else {
			document.getElementById('roleDisplayName').style.borderColor = "black";
		}
		if (roleName == '') {
			document.getElementById('roleName').style.borderColor = "red";

			canSubmit = false;
		} else {
			document.getElementById('roleName').style.borderColor = "black";
		}
		

		
		
		
		return canSubmit;

	}
	</script>
	
	<style>
	.exit h3 {
	color: red;
	font-family: serif;
	letter-spacing: 0.5px;
	font-size: 1.2em;
	text-align: center;
}
	
	
	
	
	</style>