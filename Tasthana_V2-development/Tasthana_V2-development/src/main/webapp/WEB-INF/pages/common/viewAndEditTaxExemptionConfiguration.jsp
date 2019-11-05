
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
	rel="stylesheet">
<script
	src="<%=request.getContextPath()%>/resources/js/ccvalidate-tapan.min.js"></script>

<div class="right-container" id="right-container">
	<div class="container-fluid">



		<div class="Flexi_deposit">

			<div class="header_customer">
				<h3>
					View And Edit Tax Exemption Configuration

				</h3>
			</div>
			<div class="Success_msg">
				<div class="successMsg" style="text-align: center; color: red;">
					<h2 id="error">${error }</h2>
				</div>
				
				<div class="successMsg" style="text-align: center; color: green;">
					<h2 id="error" style="font-size: 21px;font-family: none">${sucess}</h2>
				</div>
			</div>
			<div class="flexi_table">
				<form:form class="form-horizontal" autocomplete="off"
					id="taxExemptionConfiguration" commandName="taxExemptionConfiguration">
					<div class="fd-list-table">

						<c:choose>
							<c:when test="${effectiveDateList!=null}">
								<div class="col-md-4" style="margin-left: -30px;">
								<label>Date</label>
									<form:select id="effectiveDateList" path="effectiveDate"
										class="myform-control">
										<option value="">
											<spring:message code="label.select" />
										</option>
										<c:forEach var="item" items="${effectiveDateList}">
											<option value="${item}"<%-- <c:if test="${item}"><spring:message code="label.selectedSmall"/></c:if> --%>>
												<fmt:formatDate pattern="yyyy-MM-dd" value="${item}" />
											</option>
										</c:forEach>
									</form:select>

								</div>
							</c:when>
							<c:otherwise>
								<form:select id="effectiveDateList" path="effectiveDate"
									class="myform-control">
									<form:option value="select"></form:option>

								</form:select>

							</c:otherwise>
						</c:choose>

						<input type="hidden" id="hdnEffectiveDate"
							value="${effectiveDate}" />

					</div>
						<div class="form-group col-md-2"
						style="margin-top: 29px; margin-left: 15px;">
						<input type="button" class="btn btn-info add-row" value="Get Exemptions"
							onclick="findAllByDateTaxExemptionConfiguration()">
					</div>
					<div style="clear: both;"></div>





					<div class="form-group col-md-12">


						<div style="clear: both; height: 5px;"></div>


						<table id="taxExemptionConfigurationTable" style="margin-left: 15px;">

							<tbody>
								<tr>

									<th>Id</th>
									<th>Tax Exemption Age</th>
									<th>Tax Exemption Sign</th>
									<th>Tax Exemption Amount </th>
									<th>Action</th>

								</tr>
							</tbody>
						</table>

					</div>
			</div>
			</form:form>
		</div>

	</div>


<div class="container">

	<!-- Trigger the modal with a button -->


	<!-- Modal -->
	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h3 class="header_customer">EDIT TAX EXEMPTION</h3>
				</div>
				<div class="modal-body">

					<form:form class="form-horizontal" autocomplete="off"
						id="taxExemptionConfigurationEdit" commandName="taxExemptionConfiguration"
						action="viewAndEditTaxExemptionConfiguration" method="post">
						 <input type="hidden" name="menuId" id="menuId" value="${menuId}" />
						<input id="effectiveDateTempDate" name="tempDate"
							type="hidden" class="input form-control" />
						<form:input id="effectiveDateHidden" path="effectiveDate"
							type="hidden" class="input form-control" />
						<br>
						<div class="">
							<label>ID</label>
							<form:input path="id" id="ID_TaxExemptionCounfiguration"
								class="input form-control" readonly="true"/>
							<br>
						</div>
						<div class="">
							<label>Tax Exemption Age </label>
							<form:input
									id="exemptionAgeModelInputPram" path="exemptionAge" class="input form-control" /><br>
						</div>
						<div class="">
							<label>Tax Exemption Sign</label>
				<form:select  path="exemptionComparisonSign" class="myform-control" id="exemptionComparisonSignModelInputPram">
				<form:option value="Select">Select</form:option>
				<form:option value="<">&lt; </form:option>
				<form:option value = ">">&gt;</form:option>
				<form:option value = "GREATER_THAN_EQUAL_TO">&ge;</form:option>
				<form:option value = "LESS_THAN_EQUAL_TO">&le;</form:option>
				</form:select>
							
							<br>
						</div>
						<div class="">
							<lable>Tax Exemption Amount </lable>
							<form:input path="exemptionLimitAmount" id="exemptionLimitAmountModelInputPram"
								class="input form-control"/>
							<br>
						</div>


						<input type="submit" onclick="modelSubmit(event)"
							class="btn btn-info add-row" style="" />
					</form:form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
				</div>
			</div>

		</div>
	</div>

</div>


</div>
</div>

<style type="text/css">
form {
	margin: 20px 0;
}

form input, button {
	padding: 5px;
}

table {
	width: 100%;
	margin-bottom: 20px;
	border-collapse: collapse;
}

table, th, td {
	border: 1px solid #cdcdcd;
}

table th, table td {
	padding: 10px;
	text-align: left;
}

input[type=checkbox], input[type=radio] {
	margin: 4px 1px 0px;
	margin-top: 1px\9;
	line-height: normal;
	zoom: 1.0;
}

.form-horizontal .control-label {
	padding-top: 0;
}
</style>
<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<script type="text/javascript">

$( document ).ready(function() {
    error();
});

function error() {
	debugger
		if("${effectiveDate}" != ""){
			var modelAttributDate = new Date("${effectiveDate}");
			$("#effectiveDateList").val("${getEffectiveDate}").change();
			findAllByDateTaxExemptionConfiguration(modelAttributDate);
			
		
	}
}


	function findAllByDateTaxExemptionConfiguration(modelAttributDatePram) {
		debugger;
		var effectiveDate;
		if(modelAttributDatePram==undefined){
	    effectiveDate = document.getElementById('effectiveDateList').value;
		}
		else {
			var newDate=new Date(modelAttributDatePram);
			var day = newDate.getDate();
			if(day<10){day="0"+day;}
			var month = newDate.getMonth()+1;
			if(month<10){month="0"+month;}
			var year = newDate.getFullYear();
			effectiveDate=year+"-"+day+"-"+month+" 00:00:00.0";
			$('#effectiveDateList').val(effectiveDate).change();
		}
		var dateParse = new Date(effectiveDate);
		var day = dateParse.getDate();
		var month = dateParse.getMonth()+1;
		var year = dateParse.getFullYear();
		var fullDate  = day + "/" + month + "/" + year;
		
		$("#effectiveDateHidden").val(fullDate);
		$("#effectiveDateTempDate").val(effectiveDate);
	    
	    if(effectiveDate == ""){
	     document.getElementById('errorMsg').style.display='block';
	     document.getElementById('errorMsg').value="Please select effective date *.";
	     event.preventDefault();
	     return false;
	    }
	    else
	    {   

	    	// data string
		    var dataString = 'effectiveDate='+ effectiveDate;
		                   
				// delete all rows except the first row from the table
			$("#taxExemptionConfigurationTable").find("tr:gt(0)").remove();
			  $.ajax({  
			    type: "GET",  
			    url: "<%=request.getContextPath()%>/common/findAllByDateTaxExemptionConfiguration",
						contentType : "application/json",
						dataType : "json",
						data : dataString,
						async : false,
						success : function(response) {

							var trHTML = '';
							for (i = 0; i < response.length; ++i) {
								var data = response[i];
								var sign = "";
								if(data.exemptionComparisonSign == "GREATER_THAN_EQUAL_TO"){
									sign = "&ge;";
									
								}else if(data.exemptionComparisonSign == "LESS_THAN_EQUAL_TO"){
									sign = "&le;";
								}else{
									sign = data.exemptionComparisonSign;
								}
								 trHTML += '<tr><td>'
										+ data.id
										+ '</td><td>'
										+ data.exemptionAge
										+ '</td><td>'
										+ sign 
										+ '</td><td>'
										+ data.exemptionLimitAmount
										+ '</td><td> <button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#myModal" onclick="editValue('
										+ data.id + "," + data.exemptionAge + ","
										+ "'"+ data.exemptionComparisonSign +"'"+","
										+ data.exemptionLimitAmount
										+ ')">Edit</button></td></tr>'; 

							}
							 $('#taxExemptionConfigurationTable').append(trHTML);

						},

						error : function(e) {
							console.log(e);
							$('#error').html("Error occured!!");

						}
					});

		}

	}

	// 	$(document).ready(function() {
	// 		alert("Loading..");
	// 	});
	function modelSubmit(e) {
		debugger;

		if ($('#exemptionAgeModelInputPram').val() == "") {
			alert("Age can not be empty");
			e.preventDefault();
			return false;

		}
		if ($("#exemptionComparisonSignModelInputPram").val() == "Select" || $("#exemptionComparisonSignModelInputPram").val() == "") {
			alert("Please select Sign !");
			e.preventDefault();
			return false;

		}
		if ($('#exemptionLimitAmountModelInputPram').val() == "") {
			alert("Amount can not be empty");
			e.preventDefault();
			return false;

		}
		debugger;
		$("#taxExemptionConfigurationEdit").attr('action', "updateTaxExemptionConfiguration")
				.submit();

	}

	function editValue(id, exemptionAge,exemptionComparisonSign, exemptionLimitAmount) {
		$("#ID_TaxExemptionCounfiguration").val(id);
		$("#exemptionAgeModelInputPram").val(exemptionAge);
		if(exemptionComparisonSign == "GREATER_THAN_EQUAL_TO"){
			$("#exemptionComparisonSignModelInputPram").val("GREATER_THAN_EQUAL_TO").change();
		} else if(exemptionComparisonSign == "LESS_THAN_EQUAL_TO"){
			$("#exemptionComparisonSignModelInputPram").val("LESS_THAN_EQUAL_TO").change();
		}else{
		$("#exemptionComparisonSignModelInputPram").val(exemptionComparisonSign).change();
		}
		$("#exemptionLimitAmountModelInputPram").val(exemptionLimitAmount);
	}
</script>