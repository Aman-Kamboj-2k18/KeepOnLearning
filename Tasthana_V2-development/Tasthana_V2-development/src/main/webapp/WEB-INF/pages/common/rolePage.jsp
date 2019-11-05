
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%-- <%@page import="javax.servlet.http.Cookie"%> --%>



<script>

var roles = [];
var rolesIds = [];
function removeRole(value){
	var index = roles.indexOf(value);
	 if (index > -1) {
		roles.splice(index, 1);
		rolesIds.splice(index, 1);
	} 
	 $("#"+value.replace(/ /g, "_")).remove();
	 
}

function selectRoleInDropDown(value,id) {
	debugger;
	var isExist = roles.includes(value);
	var html="";
	if(!isExist){
	roles.push(value);
	rolesIds.push(id);
	var spanId  = value.replace(/ /g, "_");
	html="<span class='role_close_icone' id="+spanId+" style='background-color: gray;padding: 2px; border-radius: 5px; color: white;margin:3px'>"+value+"<input type='button' onclick = removeRole(this.value) value='"+value+"'/></span>";
	$("#tagsId").append(html);
	
	}
	
}

	$(document).ready(
			function() {
				if('${error}'){
					document.getElementById('username').style.borderColor = "red";
				}
				document.getElementById('displayName').value = "";
				document.getElementById('bankId').value = "";
				//document.getElementById('designation').value = "";
				//document.getElementById('currentRole').value = "";
				document.getElementById('contactNo').value = "";
				document.getElementById('email').value = "";
				document.getElementById('username').value = "";
				//document.getElementById("password").value = "";
				$("#startDate").datepicker({
					format : 'dd/mm/yyyy'
				});
				$("#endDate").datepicker({
					format : 'dd/mm/yyyy'
				});
				$(':input', '#catgoryForm').not(
						':button, :submit, :reset, :hidden').val('')
						.removeAttr('checked').removeAttr('selected');

			});



	function val() {
		
		var name = document.getElementById('displayName').value;
		var bankId = document.getElementById('bankId').value;
		var designation = document.getElementById('designation').value;
		var currentRole = document.getElementById('currentRole').value;
		var contactNo = document.getElementById('contactNo').value;
		var email = document.getElementById('email').value;
		var username = document.getElementById('username').value;
		var password = document.getElementById("password").value;
		var altContactNo = document.getElementById("altContactNo").value;
		var altEmail = document.getElementById("altEmail").value;

		var startDate = document.getElementById("startDate").value;
		var endDate = document.getElementById("endDate").value;

		var phoneNum = /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;
		var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
		/* var password            = /^[A-Za-z0-9!@#$%^&*()_]{6,20}$/; */

		var canSubmit = true;

		if (name == '') {
			document.getElementById('displayName').style.borderColor = "red";

			canSubmit = false;
		} else {
			document.getElementById('displayName').style.borderColor = "black";
		}
		if (bankId == '') {
			document.getElementById('bankId').style.borderColor = "red";

			canSubmit = false;
		} else {
			document.getElementById('bankId').style.borderColor = "black";
		}
		if (designation == '') {
			document.getElementById('designation').style.borderColor = "red";

			canSubmit = false;
		} else {
			document.getElementById('designation').style.borderColor = "black";
		}

		if (currentRole == '') {
			document.getElementById('currentRole').style.borderColor = "red";

			canSubmit = false;
		} else {
			document.getElementById('currentRole').style.borderColor = "black";
		}

		if (contactNo == '') {

			document.getElementById('contactNo').style.borderColor = "red";

			canSubmit = false;
		} else {
			document.getElementById('contactNo').style.borderColor = "black";
		}

		if (contactNo
				.match(/^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/)
				&& !(contactNo == '')) {

			document.getElementById('contactNo1Error').style.display = 'none';
		} else {

			document.getElementById('contactNo1Error').style.display = 'block';

			canSubmit = false;
		}

		if (altContactNo == '') {
		} else {
			if (altContactNo
					.match(/^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/)) {

				document.getElementById('contactNum2Error').style.display = 'none';
			} else {

				document.getElementById('contactNum2Error').style.display = 'block';

				canSubmit = false;
			}
		}
		if (roles == 0) {
			document.getElementById('tagsId').style.borderColor = "red";
			canSubmit = false;
		} else {
			document.getElementById('tagsId').style.borderColor = "black";
			
				$("#rolesId").val(rolesIds);
			
		}
		
		
		if (email == '') {

			document.getElementById('email').style.borderColor = "red";

			canSubmit = false;
		} else {
			document.getElementById('email').style.borderColor = "black";
		}
		if (email
				.match(/^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/)
				&& !(email == '')) {

			document.getElementById('emailError1').style.display = 'none';

		} else {
			document.getElementById('emailError1').style.display = 'block';

			canSubmit = false;
		}
		if (altEmail == '') {
		} else {
			if (altEmail
					.match(/^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/)) {

				document.getElementById('altemailError').style.display = 'none';

			} else {
				document.getElementById('altemailError').style.display = 'block';

				canSubmit = false;
			}
		}

		if (username == '') {
			document.getElementById('username').style.borderColor = "red";

			canSubmit = false;
		} else {
			
			document.getElementById('username').style.borderColor = "black";
		}

		if (password == '') {
			document.getElementById('password').style.borderColor = "red";

			canSubmit = false;
		} else {
			document.getElementById('password').style.borderColor = "black";
		}
		if (startDate == '') {
			document.getElementById('startDate').style.borderColor = "red";

			canSubmit = false;
		} else {
			document.getElementById('startDate').style.borderColor = "black";
		}
		if (endDate == '') {
			document.getElementById('endDate').style.borderColor = "red";

			canSubmit = false;
		} else {
			document.getElementById('endDate').style.borderColor = "black";
		}
		
		
		return canSubmit;

	}
</script>

<div class="right-container" id="right-container">
	<div class="container-fluid">

		<div class="role-page"
			style="border: none; padding: 0; box-shadow: none; background: none;">
			
			<div class="Flexi_deposit">
			
			<div class="header_customer" style="margin-bottom: 30px;">
				<h3 align="center">
					<spring:message code="label.newUser" />
				</h3>
			</div>
			
			<div class="exit">
				<h3 class="errorMsg">${error}</h3>
			</div>

			<div class="list-of-rates-table" style="box-shadow: none !important;">
		
				<form:form action="updateRole" id="createUserForm"
					onsubmit="return val();" commandName="endUserForm"
					class="form-horizontal" autocomplete="false">
					<form:input type="hidden" id="rolesId" path="rolesId"/>

					<div class="col-sm-12">

						<div class="add-customer-table col-sm-6 col-xs-12">
							<div class="form-group">
								<label class="control-label col-sm-4" for="displayName"><spring:message
										code="label.name" /><span style="color: red">*</span></label>
								<div class="col-sm-8">
									<form:input path="displayName" class="form-control"
										placeholder="Enter Name" id="displayName" />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-4" for="bankId"><spring:message
										code="label.bankId" /></b><span style="color: red">*</span></label>
								<div class="col-sm-8">
									<form:input path="bankId" class="form-control"
										placeholder="Enter Bank Id" id="bankId" />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-4" for="designation"><spring:message
										code="label.designation" /></b><span style="color: red">*</span></label>
								<div class="col-sm-8">
									<form:input path="designation" class="form-control"
										placeholder="Enter Designation" id="designation" pattern="[a-zA-Z ]+"/>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-4" for="currentRole"><spring:message
										code="label.selectRole" /></b><span style="color: red">*</span></label>
								<div class="col-sm-8">
									<form:select multiple="multiple"  path="currentRole" class=""
										id="currentRole">
										<form:option value="">
											<spring:message code="label.select" />
										</form:option>
										
										<c:if test="${! empty roles}">
											<c:forEach items="${roles}" var="role">
										<form:option data-roleValue="${role.roleDisplayName}" value="${role.role}" onclick="selectRoleInDropDown('${role.roleDisplayName}','${role.id}')">${role.roleDisplayName}</form:option>
										</c:forEach>
										</c:if>
									</form:select>
								</div>
								
              				
						<span id="currentRoleError" style="display: none; color: red;">
									<spring:message code="label.selectValue" />
								</span>
							</div>
							
							
							<div class="form-group">
								<label class="control-label col-sm-4" for="contactNo"></label>
								<div class="col-sm-8">
									<div id="tagsId" style="padding: 4px;border: 1px solid gray;margin: 4px 0;min-height:25px;">
							</div>
								</div>

							</div>

							<div class="form-group">
								<label class="control-label col-sm-4" for="contactNo"><spring:message
										code="label.contactNumber" /></b><span style="color: red">*</span></label>
								<div class="col-sm-8">
									<form:input path="contactNo" class="form-control"
										placeholder="Enter Contact Number" id="contactNo" type="number"/>
									<span id="contactNo1Error" style="display: none; color: red;">
										<spring:message code="label.contactNumberValidation" />
									</span>
								</div>

							</div>
							<div class="form-group">
								<label class="control-label col-sm-4" for="altContactNo"><spring:message
										code="label.altcontactNumber" /></b><span style="color: red"></span></label>
								<div class="col-sm-8">
									<form:input path="altContactNo" class="form-control"
										placeholder="Enter Alternate Contact Number" id="altContactNo" type="number"/>
									<span id="contactNum2Error" style="display: none; color: red;">Alternate
										Contact Number is not correct Format</span>
								</div>
							</div>
							
						</div>

						<div class="add-customer-table col-sm-6 col-xs-12">
							<div class="form-group">
								<label class="control-label col-sm-4" for="email"><spring:message
										code="label.email" /></b><span style="color: red">*</span></label>
								<div class="col-sm-8">
									<form:input path="email" class="form-control"
										placeholder="Enter Email" id="email" onblur="validateEmail(this);"/>
									<span id="emailError1" style="display: none; color: red;"><spring:message
											code="label.emailValidation" /> </span>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-4" for="altEmail"><spring:message
										code="label.altEmail" /></b><span style="color: red"></span></label>
								<div class="col-sm-8">
									<form:input path="altEmail" class="form-control"
										placeholder="Enter Alternate Email" id="altEmail" onclick="validateAltEmail(this);"/>
									<span id="altemailError" style="display: none; color: red;">Alternate
										Email Format is not correct</span>
								</div>
							</div>
							<div class="form-group" style="display: none">


								<div class="col-sm-8">
									<form:input path="userName" />
								</div>
							</div>
							
							<div class="form-group">
								<label class="control-label col-sm-4"><spring:message
										code="label.userName" /></b><span style="color: red">*</span></label>

								<div class="col-sm-8">
							
									<form:input path="userName" class="form-control"
										placeholder="Enter User Name" id="username"  />
										<span class="status" style="color:red"></span>

								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-4" for="password"><spring:message
										code="label.password" /></b><span style="color: red">*</span></label>
								<div class="col-sm-8">
								<input style="opacity: 0;position: absolute;width:0">
								<input type="password" style="opacity: 0;position: absolute;width:0">
								
								
									<form:input type="text" path="password"
										class="form-control secure-font" placeholder="Enter Password"
										id="password" />
										
								</div>
								
							</div>


							<div class="form-group">
								<label class="col-md-4 control-label">Account Start Date<span
									style="color: red">*</span></label>
										
								<div class="col-md-8">
								
									<form:input path="startDate" id="startDate" readonly="true"
										placeholder="Select Date"
										class="myform-control datepicker-here" dateFormat="dd-mm-yyy"
										required="true" />
								</div>
							</div>

							<div class="form-group">
								<label class="col-md-4 control-label">Account Expiry
									Date<span style="color: red">*</span>
								</label>
								<div class="col-md-8">
									<%-- <form:input path="accExpiryDate" id="endDate" readonly="true"
										placeholder="Select Date"
										class="myform-control datepicker-here" required="true" /> --%>

									<form:input path="accExpiryDate" id="endDate" readonly="true"
										placeholder="Select Date"
										class="myform-control datepicker-here" autocomplete="fasle"/>




								</div>


							</div>


						</div>
					</div>
					<div class="space-10"></div>
					<div class="col-sm-12 col-md-12 col-lg-12">
						<table align="center" style="position: relative;">
							<tr>
								<td><a href="dashboard?roleId=4" class="btn btn-success"><spring:message
											code="label.back" /></a> <input type="submit" class="btn btn-primary"
									value="<spring:message code="label.confirm"/>"></td>
							</tr>

						</table>
					</div>
				</form:form>
			</div>
			<!-- <div class="col-sm-12 col-md-12 col-lg-12 header_customer">
		
			<hr/>
	</div> -->
			
			
			</div>
			
			
			<div class="Flexi_deposit" style="margin-top: 15px !important;">
			<div class="header_customer">
				<h3 align="center">
					<spring:message code="label.userTable" />
				</h3>
			</div>


			<div class="employee_details">
				<input type="text" id="kwd_search" value=""
					placeholder="Search Here..." style="float: right;" />

				<div class="space-10"></div>
				<table class="table with-pager data jqtable example" id="my-table">
					<thead>
						<tr>
							<th><spring:message code="label.id" /></th>

							<th><spring:message code="label.currentRole" /></th>
							<th><spring:message code="label.empId" /></th>
							<th><spring:message code="label.empName" /></th>
							<th><spring:message code="label.contactNumber" /></th>
							<th><spring:message code="label.status" /></th>
							<%-- 	<th><spring:message code="label.comment" /></th> --%>
							<th><spring:message code="label.email" /></th>
							<th><spring:message code="label.userName" /></th>
							<th><spring:message code="label.action" /></th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${! empty endUsers}">
							<c:forEach items="${endUsers}" var="roles">
								<tr>
									<td><c:out value="${roles.id}"></c:out></td>
									<td><c:out value="${roles.currentRole}"></c:out></td>
									<td><c:out value="${roles.bankId}"></c:out></td>
									<td><c:out value="${roles.displayName}"></c:out></td>
									<td><c:out value="${roles.contactNo}"></c:out></td>
									<td><c:out value="${roles.status}"></c:out></td>
									<%-- 							<td><c:out value="${roles.status}"></c:out></td> --%>
									<td><c:out value="${roles.email}"></c:out></td>
									<td><c:out value="${roles.userName}"></c:out></td>


									<td><a href="selectRole?id=${roles.id}"
										class="btn btn-primary"><spring:message code="label.edit" /></a></td>
								</tr>
							</c:forEach>
						</c:if>

					</tbody>
				</table>

			</div>
		</div></div>
	</div>
	<script>
	
	
	function validateAltEmail(emailField){
        var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;

        if (reg.test(emailField.value) == false) 
        {
            $("#altemailError").show();
            return false;
        }else{
        	 $("#altemailError").hide();
        	   return true;
        	
        }

     

}
	function validateEmail(emailField){
        var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;

        if (reg.test(emailField.value) == false) 
        {
            $("#emailError1").show();
            return false;
        }else{
        	 $("#emailError1").hide();
        	   return true;
        	
        }

     

}
	
	
		$(document).ready(
				function() {
					$(':input', '#endUserForm').not(
							':button, :submit, :reset, :hidden').val('');
					
					
					
					$('#username').bind('keypress', function (event) {
					    var regex = new RegExp("^[a-zA-Z0-9]+$");
					    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
					    if (!regex.test(key)) {
					    	//alert("false")
					        event.preventDefault();
					        return false;
					    }
					});
					
					var altContactNo = document.getElementById('altContactNo');
					
					altContactNo.onkeydown = function(e) {
					    if(!((e.keyCode > 95 && e.keyCode < 106)
					      || (e.keyCode > 47 && e.keyCode < 58) 
					      || e.keyCode == 8)) {
					        return false;
					    }
					    if (altContactNo.value.length > 9 
					    		&& e.keyCode != 8){
					    	return false;
					    }
					}
					
					
					var contactNo = document.getElementById('contactNo');
					
					contactNo.onkeydown = function(e) {
					    if(!((e.keyCode > 95 && e.keyCode < 106)
					      || (e.keyCode > 47 && e.keyCode < 58) 
					      || e.keyCode == 8)) {
					        return false;
					    }
					    if (contactNo.value.length > 9 
					    		&& e.keyCode != 8){
					    	return false;
					    }
					}
					
					
					$('#bankId').bind('keypress', function (event) {
						var regex = new RegExp("^[a-zA-Z0-9]+$");
					    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
					    if (!regex.test(key)) {
					    	//alert("false")
					        event.preventDefault();
					        return false;
					    }
					});
					
					
					$('#designation').bind('keypress', function (event) {
						var regex = new RegExp("^[a-zA-Z ]+$");
					    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
					    if (!regex.test(key)) {
					    	//alert("false")
					        event.preventDefault();
					        return false;
					    }
					});
					
					
					$('#displayName').bind('keypress', function (event) {
						var regex = new RegExp("^[a-zA-Z0-9 ]+$");
					    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
					    if (!regex.test(key)) {
					    	//alert("false")
					        event.preventDefault();
					        return false;
					    }
					});
					
					
					

				});
		
	</script>
	<style>
.table>tbody>tr>td, .table>tbody>tr>th, .table>tfoot>tr>td, .table>tfoot>tr>th,
	.table>thead>tr>td, .table>thead>tr>th {
	border: 1px solid #B1B1B1;
}

.table>caption+thead>tr:first-child>td, .table>caption+thead>tr:first-child>th,
	.table>colgroup+thead>tr:first-child>td, .table>colgroup+thead>tr:first-child>th,
	.table>thead:first-child>tr:first-child>td, .table>thead:first-child>tr:first-child>th
	{
	border-top: 0;
	border: 2px solid #585858;
}

.secure-font{
-webkit-text-security:disc;}

.exit h3 {
	color: tomato;
	font-family: serif;
	letter-spacing: 0.5px;
	font-size: 1.2em;
	text-align: center;
}

.role_close_icone input {
    position: absolute;
    left: 0;
    top: 0;
    z-index: 1;
    width: 100%;
    height: 100%;
    opacity: 0;
}

.role_close_icone {
    position: relative;
    padding: 5px 25px 5px 5px !important;
    text-align: center;
    display: inline-block;
    background: #358cce !important;
    font-weight: 400;
}

.role_close_icone:after {
    content: "X";
    position: absolute;
    color: #fff;
    right: 0px;
    top: -3px;
    font-size: 11px;
    background: red;
    display: inline-block;
    width: 15px;
    height: 15px;
    line-height: 17px;
    border-radius: 100%;
}

</style>