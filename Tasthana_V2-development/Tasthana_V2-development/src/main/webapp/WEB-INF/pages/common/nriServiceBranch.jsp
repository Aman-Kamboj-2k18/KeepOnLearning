<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<script>
	
</script>
<div class="right-container" id="right-container">
	<div class="container-fluid">
		<div class="Flexi_deposit">

			<div class="select-nriServiceCentres">

			<form:form class="form-horizontal" id="nriServiceCentresForm"
				name="nriServiceCentres" action="saveNRIServiceBranches"
				method="post" commandName="NRIServiceBranches">
				<input type="hidden" name="menuId" value="${menuId}" id="menuId"/>

				<div class="list-of-rates" style="border: none;padding: 0;box-shadow: none;background: none;">
					<div class="header_customer">
						<h3>Select NRI Service Centres</h3>
					</div>


					<div class="list-of-rates" style="border: none;padding: 0;box-shadow: none;background: none;">
						<span class="counter pull-right"></span>


						<table class="table table-bordered" align="center" id="my-table" style="width: 85%; margin-top: 25px;">



							<thead>
								<tr>
									<th style="width: 12%;"><input type="checkbox" id="selectAll" name="type"
										onclick="checkUncheckAll();" value="Check All"> Select
										All</th>
									<th><spring:message code="label.branchName" /></th>
									<th><spring:message code="label.branchCode" /></th>
								</tr>
							</thead>
							<tbody>


								<c:forEach items="${branchList}" var="list">


									<tr>
										<c:set var="checkStatus" scope="session" value="${false}" />
										<c:forEach items="${nRIServiceBranchesList}"
											var="nRIServiceBranch">
											<c:if
												test="${ list.branchCode == nRIServiceBranch.branchCode}">
												<c:set var="checkStatus" scope="session" value="${true}" />
												<td><input type="checkbox" id="myCheck" name="type"
													checked="checked" /></td>
											</c:if>


										</c:forEach>
										<c:if test="${!checkStatus}">
											<td><input type="checkbox" name="type" /></td>
										</c:if>
										<td><c:out value="${list.branchName}"></c:out></td>
										<td><c:out value="${list.branchCode}"></c:out></td>

									</tr>

								</c:forEach>




							</tbody>

						</table>




						<div class="col-md-offset-4 col-md-8">
							<span id="errorMsg" style="display: none; color: red;"><spring:message
									code="label.validation" /></span>
						</div>
						<div class="col-sm-12 col-md-12 col-lg-12">
							<div class="errorMsg">
								<font color="red"
									style="text-align: center; width: 100%; float: left;"
									id="validationError">${error}</font>
							</div>
						</div>

						<div class="col-md-offset-4 col-md-8"
							style="padding-bottom: 22px;">
							<input type="submit" size="3" onclick="check()" value="Save"
								class="btn btn-primary">

						</div>





					</div>


				</div>
			</form:form>
		</div>
	</div>
</div></div>

<script>
	function checkUncheckAll() {

		if (document.getElementById('selectAll').checked == 1) {
			selectAll();

		} else {
			UnSelectAll();

		}
	}

	function selectAll() {
		var items = document.getElementsByName('type');
		for (var i = 0; i < items.length; i++) {
			if (items[i].type == 'checkbox')
				items[i].checked = true;
		}
	}

	function UnSelectAll() {
		var items = document.getElementsByName('type');
		for (var i = 0; i < items.length; i++) {
			if (items[i].type == 'checkbox')
				items[i].checked = false;
		}
	}

	function check() {
		var urlQueryString = window.location.search.split("=");
		var menuId = urlQueryString[1];
		 $("#menuId").val(menuId);
		//Reference the Table.
		var selectedNRIBranchesCheckBoxArray = new Array;
		var grid = document.getElementById("my-table").rows[0].cells.length;;

		//Reference the CheckBoxes in Table.
		var checkBoxes = document.getElementById("my-table").getElementsByTagName("INPUT");

		//Loop through the CheckBoxes.
		for (var i = 0; i < checkBoxes.length; i++) {
			if (checkBoxes[i].checked) {
				var row = checkBoxes[i].parentNode.parentNode;
				if(row.cells[2].innerHTML != "Branch Code")
				selectedNRIBranchesCheckBoxArray.push(row.cells[2].innerHTML);

			}
		}

		$("#nriServiceCentresForm").attr("action",
				"saveNRIServiceBranches?selectedNRIBranches=" + selectedNRIBranchesCheckBoxArray);
		$("#nriServiceCentresForm").submit();
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
			$('input[type="Submit"]').hide();
		}
	});
</script>