<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- To fetch the request url -->
<c:set var="req"
	value="${requestScope['javax.servlet.forward.request_uri']}" />
<c:set var="baseURL" value="${fn:split(req,'/')}" />
<link href="<%=request.getContextPath()%>/resources/css/Validation.css"
	rel="stylesheet"><link
	href="<%=request.getContextPath()%>/resources/css/datepicker.min.css"
	rel="stylesheet">
<script
	src="<%=request.getContextPath()%>/resources/js/ccvalidate-tapan.min.js"></script>


<div class="right-container" id="right-container">
	<div class="container-fluid">



		<div class="Flexi_deposit">

			<div class="header_customer">
				<h3>
					<spring:message code="label.citizenAndCustomerCategory" />

				</h3>
				
			</div>
			<div class="Success_msg">
				<div class="successMsg" style="text-align: center; color: red;">
					${error}</div>
			</div>

			<form:form action="citizenAndCustomerCategory"
				id="citizenAndCustomerCategory"
				commandName="citizenAndCustomerCategory" method="post">
				<input type="hidden" name="customerCategoryIds"
					id="customerCategoryIds">
				<input type="hidden" name="nriTypes" id="nriTypes">
 <input type="hidden" name="menuId" id="menuId" value="${menuId}" />

				<div class="">

					<label class="checkbox-custom"> Citizen </label> <label
						class="checkbox-custom"><input type="radio" name="citizen"
						value="RI" checked="checked"
						onchange="onChangeCitizenType(this.value)"> RI</label> <label
						class="checkbox-custom"><input type="radio" name="citizen"
						value="NRI" onchange="onChangeCitizenType(this.value)">
						NRI</label>
				</div>



				<table class="table_" id="riTable" style="width: 45%; margin-left: 175px;">
					<thead>
						<tr>
							<th style="width: 88%; text-align: center;">Options</th>
							<th style="text-align: center;">Action</th>
						</tr>
					</thead>
					<tbody>

						<c:forEach items="${customerCategories}" var="customerCategory">

							<tr>
								<td>${customerCategory.customerCategory}</td>
								<c:if test="${customerCategory.riFlag == true}">
									<td class="regular"><input class="riAction"
										type='checkbox' style='margin-left: 44px; margin-top: 10px;'
										value="${customerCategory.id}" checked="checked" /></td>
								</c:if>
								<c:if test="${customerCategory.riFlag == false}">
									<td class="regular"><input class="riAction"
										type='checkbox' style='margin-left: 44px; margin-top: 10px;'
										value="${customerCategory.id}" /></td>
								</c:if>

							</tr>
						</c:forEach>
					</tbody>
				</table>

				<table class="table_" style="display: none; width: 45%; margin-left: 175px;" id="nriTable">
					<thead>
						<tr>
							<!-- <th style="text-align: center;">Options</th> -->
							<th style="width: 88%; text-align: center;">Options</th>
							<th style="text-align: center;">Action</th>
							<!-- 	<th colspan="5" style="text-align: center;">Account Type</th> -->
						</tr>
					</thead>
					<tbody>

						<c:forEach items="${customerCategories}" var="customerCategory"
							varStatus="orderIndex">
							<tr>

								<td>${customerCategory.customerCategory}</td>

								<c:if test="${customerCategory.nriFlag == true}">
									<td class="regular"><input class="riAction"
										type='checkbox' style='margin-left: 44px; margin-top: 10px;'
										value="${customerCategory.id}" checked="checked" /></td>
								</c:if>
								<c:if test="${customerCategory.nriFlag == false}">
									<td class="regular"><input class="riAction"
										type='checkbox' style='margin-left: 44px; margin-top: 10px;'
										value="${customerCategory.id}" /></td>
								</c:if>
								<td style="display: none;"><input type='checkbox'
									id="nre${orderIndex.index}"
									style='margin-left: 44px; margin-top: 10px;' class="chk"
									name="nriAccountType" value="NRE" checked="checked"
									onclick="setNriTypeValue(this,this.id)" /> <b>NRE</b></td>
								<td style="display: none;"><input type='checkbox'
									id="nro${orderIndex.index}"
									style='margin-left: 44px; margin-top: 10px;' class="chk"
									name="nriAccountType" value="NRO" checked="checked"
									onclick="setNriTypeValue(this,this.id)" /> <b>NRO</b></td>
								<td style="display: none;"><input type='checkbox'
									id="fcnr${orderIndex.index}"
									style='margin-left: 44px; margin-top: 10px;' class="chk"
									name="nriAccountType" value="FCNR" checked="checked"
									onclick="setNriTypeValue(this,this.id)" /> <b>FCNR</b></td>
								<td style="display: none;"><input type='checkbox'
									id="rfc${orderIndex.index}"
									style='margin-left: 44px; margin-top: 10px;' class="chk"
									name="nriAccountType" value="RFC" checked="checked"
									onclick="setNriTypeValue(this,this.id)" /> <b>RFC</b></td>
								<td style="display: none;"><input type='checkbox'
									id="prp${orderIndex.index}"
									style='margin-left: 44px; margin-top: 10px;' class="chk"
									name="nriAccountType" value="PRP" checked="checked"
									onclick="setNriTypeValue(this,this.id)" /> <b>PRP</b></td>
							</tr>

						</c:forEach>
					</tbody>
				</table>

				<div style="text-align: center; margin-top: 24px; margin-bottom: 27px; margin-right: 310px;">
					<input type="button" value="Submit" class="btn btn-primary"
						onclick="return submitOnButton()" />
				</div>

			</form:form>



		</div>
	</div>
</div>
<script>
	$(document).ready(function() {

	});

	function actionSelect(event, id) {
		var id_ = id.substr(6, id.length);
		if (event.checked) {
			$("#nre" + id_).attr("disabled", false);
			$("#nro" + id_).attr("disabled", false);
			$("#fcnr" + id_).attr("disabled", false);
			$("#rfc" + id_).attr("disabled", false);
			$("#prp" + id_).attr("disabled", false)

		} else {
			$("#nre" + id_).prop("checked", false);
			$("#nro" + id_).prop("checked", false);
			$("#fcnr" + id_).prop("checked", false);
			$("#rfc" + id_).prop("checked", false);
			$("#prp" + id_).prop("checked", false);

			$("#nre" + id_).attr("disabled", true);
			$("#nro" + id_).attr("disabled", true);
			$("#fcnr" + id_).attr("disabled", true);
			$("#rfc" + id_).attr("disabled", true);
			$("#prp" + id_).attr("disabled", true);

		}
	}

	var citizenType = "RI";
	function onChangeCitizenType(value) {
		if (value == 'RI') {
			citizenType = value;
			$("#riTable").show();
			/* $(".chk").prop("checked", false);
			$(".chk").attr("disabled", true);
			$(".action").prop("checked", false); */
			$("#nriTable").hide();

		} else {
			citizenType = value;
			$("#nriTable").show();
			//$(".riAction").prop("checked", false);
			$("#riTable").hide();
		}

	}

	function submitOnButton() {
		var ids = "";
		var nriTypeJson = "{";
		if (citizenType == 'RI') {
			$('#riTable tbody tr').each(function() {
				var isChecked = $(this).find('input').prop('checked');
				if (isChecked) {
					ids += $(this).find('input').val();
					ids += ","
				}

			});

			if (ids == "") {

				alert("Please select atleast one Check box !");
				return false;
			}

		} else {
			$('#nriTable tbody tr').each(
					function() {
						var isChecked = $(this).find('input').prop('checked');
						if (isChecked) {
							nriTypeJson += '"' + $(this).find("input").val()
									+ '"';
							ids += $(this).find('input').val();
							ids += ","
							nriTypeJson += ":";
							nriTypeJson += '"';
						}

						if (isChecked) {
							$(this).find('input.chk').each(function() {

								var isChecked = $(this).prop('checked');
								if (isChecked) {
									nriTypeJson += $(this).val() + ",";

								}
							});
						}
						if (isChecked) {
							nriTypeJson = nriTypeJson.substring(0,
									nriTypeJson.length - 1);
							nriTypeJson += '"';
							nriTypeJson += ',';
						}

					});
			if (nriTypeJson == "{") {

				alert("Please select atleast one Check box !");
				return false;
			}

			if (nriTypeJson.includes(",")) {
				var length = nriTypeJson.length;
				nriTypeJson = nriTypeJson.substring(0, length - 1);
			}
			nriTypeJson += "}";

		}
		var length = ids.length;
		ids = ids.substring(0, length - 1);
		$("#customerCategoryIds").val(ids);
		$("#nriTypes").val(nriTypeJson);
		$("#citizenAndCustomerCategory").submit();

	}

	function setNriTypeValue(event, id) {
		if (event.checked) {
			if (id.startsWith('nre')) {

				$("#" + id).val("NRE");
			} else if (id.startsWith('nro')) {
				$("#" + id).val("NRO");

			} else if (id.startsWith('fcnr')) {
				$("#" + id).val("FCNR");

			} else if (id.startsWith('rfc')) {
				$("#" + id).val("RFC");

			} else if (id.startsWith('prp')) {
				$("#" + id).val("PRP");

			}

		} else {
			if (id.startsWith('nre')) {

				$("#" + id).val("");
			} else if (id.startsWith('nro')) {
				$("#" + id).val("");

			} else if (id.startsWith('fcnr')) {
				$("#" + id).val("");

			} else if (id.startsWith('rfc')) {
				$("#" + id).val("");

			} else if (id.startsWith('prp')) {
				$("#" + id).val("");

			}
		}

	}
	var vars = [], hash;
	function getUrlVars()
	{
	   
	    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
	    for(var i = 0; i < hashes.length; i++)
	    {
	        hash = hashes[i].split('=');
	        vars.push(hash[0]);
	        vars[hash[0]] = hash[1];
	    }
	    return vars;
	}

	$(document).ready(function(){
		debugger;
		var menuId=getUrlVars()["menuId"];
		var permissions=$("#menu_"+menuId).attr("permissions");
		//alert(permissions);
		if(permissions.toLowerCase().indexOf("write")<0){
			$('input[type="button"]').remove();
		}
	});
</script>

<style>
table, th, td {
	border: 1px solid skyblue;
	border-collapse: collapse;
}

.table_ {
	width: 90%;
	margin-bottom: 100px;
	margin: 15px;
	float: none;
	position: relative;
}

th, td {
	padding: 5px;
	text-align: left;
	font-family: inherit;
}

.checkbox-custom {
	margin: 11px;
	margin: 10px;
	padding: 3px;
}

label.checkbox-custom input {
	margin-right: 4px;
}
</style>

