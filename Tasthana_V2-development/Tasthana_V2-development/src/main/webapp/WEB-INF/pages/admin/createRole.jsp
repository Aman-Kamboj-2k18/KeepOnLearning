
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%-- <%@page import="javax.servlet.http.Cookie"%> --%>



<script>
	

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

<div class="right-container" id="right-container">
	<div class="container-fluid">

		<div class="role-page"
			style="border: none; padding: 0; box-shadow: none; background: none;">
			
			<div class="Flexi_deposit">
			
			<div class="header_customer" style="margin-bottom: 10px;">
				<h3>
					Create Role
				</h3>
			</div>
			
			<div class="exit" style="font-size: 19px;">
				<h3 class="errorMsg">${error}</h3>
				<h3 style="color: green">${sucess}</h3>
			</div>

			<div class="list-of-rates-table" style="box-shadow: none !important;">
		
				<form:form action="#" id="createUserForm"
					onsubmit="return val();" commandName="role"
					class="form-horizontal" autocomplete="false">

					<div class="col-sm-10">

						

						<div class="add-customer-table col-sm-6 col-xs-12">

							<div class="form-group">
								<label class="control-label col-sm-6" for="displayName" style="padding-top: 16px;">Display Name<span style="color: red">*</span></label>
								<div class="col-sm-6">
									<form:input path="roleDisplayName" class="myform-control"
										placeholder="Enter Name" id="roleDisplayName" data-role="tagsinput"/>
								</div>
							</div>
							
							<div class="form-group">
								<label class="control-label col-sm-6" style="padding-top: 16px;">Role<span style="color: red">*</span></label>
								<div class="col-sm-6">
									<form:input path="role" class="myform-control"
										placeholder="Enter Role" id="roleName" />
								</div>
							</div>
							

							
						</div>
						
						
					
					<div class="add-customer-table col-sm-6 col-xs-12">

							<div class="form-group">
													<label class="col-md-6" style="padding-top: 16px; text-align: -webkit-right;">
														Rights To Approve?<span style="color: red">*</span>
													</label>
												
													
													<div class="col-md-6">
														<form:select path="rightsTOApprove" class="myform-control">
															<form:option value="0">No</form:option>
															<form:option value="1">Yes</form:option>
														</form:select>
                                                    </div>

												</div>
												
												<div class="form-group">
													<label class="col-md-6"
														style="padding-top: 16px; text-align: -webkit-right;">
														Amount Limit<span style="color: red"></span>
													</label>
													<div class="col-md-6">
														<form:input id="amountLimit" placeholder=""
															path="amountLimit" class="myform-control"
															 />


													</div>
												</div></div>
					<div class="space-10"></div>
					<div class="col-sm-12 col-md-12 col-lg-12">
						<table align="center" style="position: relative;">
							<tr>
								<td><a href="adminPage" class="btn btn-success"><spring:message
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
			
			</div>
			<div class="Flexi_deposit" style="margin-top: 15px !important;">
			<div class="header_customer">
				<h3>
					View Role
				</h3>
			</div>


			<div class="employee_details">
				<input type="text" id="kwd_search" value=""
					placeholder="Search Here..." style="float: right; margin: 15px;" />

				<div class="space-10"></div>
				 <table class="table with-pager data jqtable example" id="my-table" style="width: 98%; margin-left: 15px;">
					<thead>
						<tr>
							<th><spring:message code="label.id" /></th>
							<th>Display Name</th>
							<th>Role</th>
							<th>Created By</th>
							<th>Modified By</th>
							<th>Create On</th>
							<th>Modified On</th>
							<th>Status</th>
							<th>Action</th>
						
						</tr>
					</thead>
					<tbody>
						<c:if test="${! empty roles}">
							<c:forEach items="${roles}" var="role">
								<tr>
									<td><c:out value="${role.id}"></c:out></td>
									<td><c:out value="${role.roleDisplayName}"></c:out></td>
									<td><c:out value="${role.role}"></c:out></td>
									<td><c:out value="${role.createdBy}"></c:out></td>
									<td><c:out value="${role.modifiedBy}"></c:out></td>
									<td><c:out value="${role.createdOn}"></c:out></td>
									<td><c:out value="${role.modifiedOn}"></c:out></td>
									<td><c:out value="${role.isActive}"></c:out></td>
									
									<td><a href="editRole?id=${role.id}"
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

		$("input").tagsinput('items');
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
	color: #666;
	font-size: 0.85em;
	text-align: center;
}
</style>