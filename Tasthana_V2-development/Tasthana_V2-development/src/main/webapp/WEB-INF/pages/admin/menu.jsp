
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%-- <%@page import="javax.servlet.http.Cookie"%> --%>



<script>
	function val() {
		var name = document.getElementById('name').value;
		var roleName = document.getElementById('urlPattern').value;
		var description = $("#description").val();
		//var possibleAction = $("#possibleAction").val();

		var canSubmit = true;

		if (name == '') {
			document.getElementById('name').style.borderColor = "red";

			canSubmit = false;
		} else {
			document.getElementById('name').style.borderColor = "black";
		}
		if (roleName == '') {
			document.getElementById('urlPattern').style.borderColor = "red";

			canSubmit = false;
		} else {
			document.getElementById('urlPattern').style.borderColor = "black";
		}

		if (description == '') {
			document.getElementById('description').style.borderColor = "red";

			canSubmit = false;
		} else {
			document.getElementById('description').style.borderColor = "black";
		}
		/* if (possibleAction == '') {
			document.getElementById('possibleAction').style.borderColor = "red";
			canSubmit = false;
		} else {
			document.getElementById('possibleAction').style.borderColor = "black";
		} */
		if (canSubmit) {

			$("#menu").attr("action", "leftNavbarMenu");
			$("#menu").submit();

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
					<h3 align="center">Nav Bar Menu</h3>
				</div>

				<div class="exit" style="font-size: 19px;">
					<h3 class="errorMsg">${error}</h3>
					<h3 style="color: green">${sucess}</h3>
				</div>

				<div class="list-of-rates-table"
					style="box-shadow: none !important;">

					<form:form action="#" id="menu" commandName="menu"
						class="form-horizontal" autocomplete="false">

						<div class="col-sm-12">



							<div class="add-customer-table col-sm-6 col-xs-12">


								<div class="form-group">
									<label class="control-label col-sm-4" for="displayName">Name<span
										style="color: red">*</span></label>
									<div class="col-sm-8">
										<form:input path="name" class="form-control"
											placeholder="Enter Name" id="name" data-role="tagsinput" />
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-sm-4">URL Pattern<span
										style="color: red">*</span></label>
									<div class="col-sm-8">
										<form:input path="urlPattern" class="form-control"
											placeholder="Enter URL Pattern" id="urlPattern" />
									</div>
								</div>



							</div>

							<div class="add-customer-table col-sm-6 col-xs-12">
								<div class="form-group">
									<label class="control-label col-sm-4">Possible Action
										<span style="color: red">*</span>
									</label>
									<div class="col-sm-8">
										<c:forEach items="${permissions}" var="permission"
											varStatus="permissionIndex">
											<input class="checkBoxPermission"
												id="permission_${menu.id}_${permission.id}"
												name="possibleAction" value="${permission.id}" type="checkbox" />
											<b>${permission.action}</b>
										</c:forEach>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-sm-4">Description<span
										style="color: red">*</span></label>
									<div class="col-sm-8">
										<textarea rows="" cols="" class="textarea" name ="description" id="description"></textarea>

									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-sm-4">Is Active<span
										style="color: red">*</span></label>
									<div class="col-sm-8">
										<form:radiobutton path="isActive" value="true"
											checked="checked" />
										<b>True </b>
										<form:radiobutton path="isActive" value="false" />
										<b>False </b>
									</div>
								</div>





							</div>
						</div>
						<div class="space-10"></div>
						<div class="col-sm-12 col-md-12 col-lg-12">
							<table align="center" style="position: relative;">
								<tr>
									<td><a href="adminPage" class="btn btn-success"><spring:message
												code="label.back" /></a> <input type="button"
										onclick="return val();" class="btn btn-primary"
										value="<spring:message code="label.confirm"/>"></td>
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
	$(document).ready(function() {

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

.textarea {
	width: 100%;
	min-height: 100px;
	padding: 10px
}
</style>