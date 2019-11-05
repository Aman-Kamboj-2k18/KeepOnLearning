<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="right-container" id="right-container">
	<div class="container-fluid">

		<div class="role-page"
			style="border: none; padding: 0; box-shadow: none; background:none;">

			<div class="Flexi_deposit">

				<div class="header_customer" style="margin-bottom: 30px;">
					<h3 align="center">Manage Menu Preference</h3>
				</div>
				<div class="exit" style="font-size: 19px;">
					<h3 class="errorMsg">${error}</h3>
					<h3 style="color: green">${success}</h3>
				</div>

				<div class="list-of-rates-table"
					style="box-shadow: none !important;">

					<form:form action="#" id="manageMenuPreference" commandName="manageMenuPreference"
						class="form-horizontal" autocomplete="false">
						<form:hidden id="jsonData" path="jsonData" />
						
						<div class="col-sm-12">
							<div class="add-customer-table col-sm-4 col-xs-12">
								<div class="form-group">
									<label class="control-label">Role<span
										style="color: red">*</span></label>
									<div class="col-sm">
										<select name="roleId" id="roleId"
											onchange="getByRoleId($(this))">
											<option value="Select">Select</option>
											<c:forEach items="${roles}" var="role">
												<option value="${role.id}">${role.roleDisplayName}</option>
											</c:forEach>
										</select>

									</div>
								</div>



							</div>

							
								<div class="row" style="width: 100%; float: left;">
									<div class="col-md-6">
											<div><h4>Assign Menu</h4></div>
											<ul id="sortable1" class="connectedSortable">
											<c:forEach items="${listOfMenus}" var="menu">
										<c:if test="${menu.childMenuItems.size() ==  0 }">
										<li class="ui-state-default sortableByMenuId" menuId = "${menu.id}">
										${menu.name}</li>
										</c:if>
										<c:if test="${menu.childMenuItems.size() > 0 }">
										<li class="ui-state-default sortableByMenuId" menuId = "${menu.id}">
										<span style="border: solid 1px #c5c5c5 ;width: 100%;float: left;padding: 7px;position: relative;">${menu.name}</span>
											<ol class = "sortableChild2" style="margin-top: 45px;">
											<c:forEach items="${menu.childMenuItems}" var="subMenu">
											<li class="ui-state-default" subMenuId = "${subMenu.id}">${subMenu.name}</li>
											</c:forEach>
											</ol>
											</li>
											</c:if>
										</c:forEach>
										
										</ul>
									</div>
									<div class="col-md-6">
										<div><h4>Sorted Menu</h4></div>
										<ul id="sortable2" class="connectedSortable">
										<c:forEach items="${sortedMenuList}" var="menu">
										<c:if test="${menu.childMenuItems.size() ==  0 }">
										<li class="ui-state-default sortableByMenuId" menuId = "${menu.id}">
										${menu.name}</li>
										</c:if>
										<c:if test="${menu.childMenuItems.size() > 0 }">
										<li class="ui-state-default sortableByMenuId" menuId = "${menu.id}">
										<span style="border: solid 1px #c5c5c5 ;width: 100%;float: left;padding: 7px;position: relative;">${menu.name}</span>
										
											<ol class ="sortableChild2" style="margin-top: 45px;">
											<c:forEach items="${menu.childMenuItems}" var="subMenu">
											<li class="ui-state-default" subMenuId = "${subMenu.id}">${subMenu.name}</li>
											</c:forEach>
											</ol>
											</li>
											</c:if>
										</c:forEach>
											
										</ul>
									</div>

								</div>
						</div>
				
				<div class="space-10"></div>
				<div class="col-sm-12 col-md-12 col-lg-12"
					style="text-align: center;margin-top: 36px;">
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


<style>
#sortable1, #sortable2 {
	border: 1px solid #eee;
	width: 142px;
	min-height: 20px;
	list-style-type: none;
	margin: 0;
	padding: 5px 0 0 0;
	float: left;
	margin-right: 10px;
}

#sortable1 li, #sortable2 li {
	margin: 0 5px 5px 5px;
	padding: 5px;
	font-size: 1.2em;
	width: 120px;
}
</style>

<script>
$( function() {
    $( "#sortable1, #sortable2" ).sortable({
      connectWith: ".connectedSortable"
    }).disableSelection();

    $( ".sortableChild1, .sortableChild2" ).sortable({
      }).disableSelection();
  } );


$( document ).ready(function() {
	if("${manageMenuPreference.roleId}" != "")
   $("#roleId").val("${manageMenuPreference.roleId}");
	else
	$("#roleId").val("Select");
});
  </script>
<script>
	function submitOnButton() {
			var roleId = $("#roleId").val();
			var isPresentSortable1 = $("#sortable1 li" ).size();
			if(isPresentSortable1 > 0){
				alert("Please drag all menus to sorted menu box ! ");
				return false;
			}
			jsonData = [];
			manageMenuPreferencesJsonObj = {};
			manageMenuPreferences = [];
			$("#sortable2 li.sortableByMenuId" ).each(function( index ) {
				var menuId = $(this).attr('menuId');
			  manageMenuPreferencesObj = {}
			  manageMenuPreferencesObj["menuId"] = menuId;
			  manageMenuPreferencesObj["menuIndex"] = index;
			  manageMenuPreferencesSubMenuIds = [];
			  	$(this).find("ol li").each(function( index ) {
			  		var subMenuId = $(this).attr('subMenuId');
			  		manageMenuPreferencesSubMenuIds.push(subMenuId);	
				});
			  	 manageMenuPreferencesObj["subMenuIds"] = manageMenuPreferencesSubMenuIds;
			  	
			  manageMenuPreferences.push(manageMenuPreferencesObj);
			});
			manageMenuPreferencesJsonObj["roleId"] = roleId;
			manageMenuPreferencesJsonObj["manageMenuPreferences"] = manageMenuPreferences;
			
			jsonData.push(manageMenuPreferencesJsonObj);
			var jsonString = "{}";
			if (jsonData.length > 0) {
				jsonString = JSON.stringify(jsonData);
				$("#jsonData").val(jsonString);
				$("#errorMsg").text("").hide();
			} else {
				$("#errorMsg").text("Please  drag assign menus in sorted menu box !")
						.show();
				return false;
			}
		
			var isPresentSortable2 = $("#sortable2 li" ).size();
			if(isPresentSortable2 == 0){
				alert("Sorted menu box is empty Please drag menus ! ");
				return false;
			}
			
		$("#manageMenuPreference").attr("action", "manageMenuPreference");
		$("#manageMenuPreference").submit(); 
	}
	function getByRoleId(obj){
		var roleId = obj.val();
		$("#roleId").val(roleId);
		$("#manageMenuPreference").attr("action","getRoleIdByMenusDetails");
		$("#manageMenuPreference").submit();
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
	color: tomato;
	font-family: serif;
	letter-spacing: 0.5px;
	font-size: 1.2em;
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

#sortable1, #sortable2 {
	border: 1px solid #358cce;
	width: 100%;
	min-height: 20px;
	list-style-type: none;
	margin: 0;
	padding: 5px 0 0 0;
	float: left;
	margin-right: 10px;
}

#sortable1 li, #sortable2 li {
	margin: 0 5px 5px 5px;
	padding: 5px;
	font-size: 1.2em;
	width: 98%;
}
</style>