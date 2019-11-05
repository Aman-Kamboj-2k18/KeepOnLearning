
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="right-container" id="right-container">
	<div class="container-fluid">

		<div class="role-page"
			style="border: none; padding: 0; box-shadow: none; background: none;">

			<div class="Flexi_deposit">

				<div class="header_customer" style="margin-bottom: 30px;">
					<h3>Manage Role</h3>
				</div>
				<div class="exit" style="font-size: 19px;">
					<h3 class="errorMsg">${error}</h3>
					<h3 style="color: green; font-size:0.85em;">${sucess}</h3>
				</div>

				<div class="list-of-rates-table"
					style="box-shadow: none !important;">

					<form:form action="#" id="manageRole" commandName="manageRole"
						class="form-horizontal" autocomplete="false">
						<form:hidden id="jsonData" path="jsonData" />

						<div class="col-sm-12">
							<div class="add-customer-table col-sm-4 col-xs-12">
								<div class="form-group">
									<label class="control-label">Role<span
										style="color: red">*</span></label>
									<div class="col-sm">
										<select name="roleId" id="roleId" onchange="getByRoleId($(this))">
										<option value="Select">Select</option>
											<c:forEach items="${roles}" var="role">
												<option value="${role.id}">${role.roleDisplayName}</option>
											</c:forEach>
										</select>

									</div>
								</div>



							</div>


							<div>
								<input type="text" id="kwd_search" value=""
									placeholder="Search Here..." style="float: right;" />
								<table class="table with-pager data jqtable example"
									id="my-table">
									<thead>
										<tr>
											<th scope="col">Serial no.</th>
											<th scope="col">Menu</th>
											<th scope="col" colspan="3">Possible Access Control</th>

										</tr>
									</thead>
									<tbody>
										<c:forEach items="${menuList}" var="menu"
											varStatus="menuIndex">
											<tr id = "${menu.id}">
												<td scope="row">${menu.id}</td>
												<td><b>${menu.name} </b></td>
												<td><c:forEach items="${menu.permission}"
														var="permission" varStatus="permissionIndex">
														<input class = "checkBoxPermission"
															id="permission_${menu.id}_${permission.id}"
															name="permission" value="${permission.id}"
															type="checkbox" />
														<b>${permission.action}</b>
													</c:forEach></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
						<div class="space-10"></div>
						<div class="col-sm-12 col-md-12 col-lg-12"
							style="text-align: center;">
							<span id="errorMsg"
								style="display: none; color: red; font-size: 19px;"></span>
							<table align="center" style="position: relative;">
								<tr>

									<td><a href="adminPage" class="btn btn-success"><spring:message
												code="label.back" /></a> <input type="button"
										class="btn btn-primary"
										value="<spring:message code="label.confirm"/>"
										onclick="submitOnButton()"></td>
								</tr>

							</table>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
	function submitOnButton() {
		var rolId = $("#roleId").val();
		if (rolId == 'Select') {
			$("#roleId").css('border', '1px solid red');
			$("#errorMsg").text("Please select role !").show();
			return false;
		} else {
			$("#roleId").css('border', '1px solid grey');
			$("#errorMsg").text("").hide();
		}
		jsonData = [];
		$('#my-table tbody tr').each(function() {
			var menuId = $(this).find('td:first').text();
			roleManageObj = {}
			var permisionsId = "";
			$(this).find('input[type=checkbox]:checked').each(function(index) {
				permisionsId += $(this).val() + ',';
			});
			if (permisionsId != "") {
				var length = permisionsId.length;
				permisionsId = permisionsId.substring(0, length - 1);
				roleManageObj["menuId"] = menuId;
				roleManageObj["permissionId"] = permisionsId;
				jsonData.push(roleManageObj);
			}
		});
		var jsonString = "{}";
		if (jsonData.length > 0) {
			jsonString = JSON.stringify(jsonData);
			$("#jsonData").val(jsonString);
			$("#errorMsg").text("").hide();
		} else {
			$("#errorMsg").text("Please select access control options !")
					.show();
			return false;
		}
		debugger;
		$("#manageRole").attr("action", "manageRoleSave");
		$("#manageRole").submit();
	}
	function getByRoleId(obj){
		var roleId = obj.val();
		$(".checkBoxPermission").prop("checked",false);
		$.ajax({  
		    type: "GET",  
		    async: false,
		    url: "<%=request.getContextPath()%>/admin/getByRoleIdAndMenuAndPermissionDetails/"+roleId, 
		    contentType: "application/json",
		    dataType: "json",
		    success: function(response){  
		    	for(var i = 0; i < response.length ;i++){
		    		var menuId = response[i][1];
		    		var permissionId = response[i][2];
		    		$("#permission_"+menuId+"_"+permissionId).prop("checked",true);
		    	} 	
		    },  
		    error: function(e){  
		    	 $('#error').html("Error occured!!")
		    	
		    }  
		  });  
		  
	}

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

.secure-font {
	-webkit-text-security: disc;
}

.exit h3 {
	color: 444;
	font-size: 0.75;
	text-align: center;
}

td, th {
	text-align: center;
}

.manage-role {
	float: left;
	margin-right: 3px;
	min-width: 139px;
}

.manage-role-content {
	max-height: 160px;
	overflow-y: auto;
}

.add-customer-table .col-sm- {
	display: inline-block;
	margin-right: 10px;
}

.add-customer-table .col-sm {
	display: inline-block;
	margin-right: 10px;
}

.add-customer-table .col-sm select {
	padding: 5px 5px;
	border-radius: 5px;
}

b {
	padding-right: 10px;
}
</style>