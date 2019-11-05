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
	<link href="<%=request.getContextPath()%>/resources/css/datepicker.min.css"
	rel="stylesheet">

<div class="right-container" id="right-container">
	<div class="container-fluid">



		<div class="Flexi_deposit">

			<div class="header_customer">
				<h3>
					Tax Exemption Configuration

				</h3>
			</div>
			
				<form:form class="form-horizontal" autocomplete="off"
				commandName="taxExemptionConfiguration" id="taxExemptionConfiguration" method="POST">
				<div class="flexi_table" style="margin-top: 7px;">
				 <input type="hidden" name="menuId" id="menuId" value="${menuId}" />
				</div>
				<div class="form-group col-md-9" style="margin-left: -16px;">
							<label class="col-md-3 control-label"
								style="padding-top: 15px; text-align: right;"><spring:message
									code="label.effectiveDate" /><span style="color: red">*</span></label>
							<div class="col-md-4" style="margin-left: 81px;">
								<form:input path="effectiveDate" id="effectiveDate"
									readonly="true" placeholder="Select Date"
									class="myform-control datepicker-here" dateFormat="dd-mm-yyy"
									required="true" />
									<input type="hidden" name="saveExemptionConfigurationList"
									id="saveExemptionConfigurationList">

							</div>
						</div>
				<div class= "col-md-12">
				<div class="col-md-3">
				<%-- <form:label path="exemptionAge" class ="control-label">Age</form:label> --%>
				<label class="control-label"
								style="padding-top: 15px; text-align: right;">Age<span style="color: red">*</span></label>
				<form:input path="exemptionAge" class="myform-control" id="age"/>
				</div>
				
				
				<div class="col-md-3">
				<label class="control-label"
								style="padding-top: 15px; text-align: right;">Exemption Comparison Sign<span style="color: red">*</span></label>
				<%-- <form:label path="exemptionComparisonSign" class ="control-label">Exemption Comparison Sign</form:label> --%>
				<form:select  path="exemptionComparisonSign" class="myform-control" id="exemptionComparisonSign">
				<form:option value="Select">Select</form:option>
				<form:option value="LESS_THAN">&lt; </form:option>
				<form:option value="GREATER_THAN">&gt;</form:option>
				<form:option value="GREATER_THAN_EQUAL_TO">&ge;</form:option>
				<form:option value="LESS_THAN_EQUAL_TO">&le;</form:option>
				</form:select>
				</div>
				
				<div class="col-md-3">
				
				
				<label class="control-label"
								style="padding-top: 15px; text-align: right;">Exemption Limit Amount<span style="color: red">*</span></label>
				<form:input path="exemptionLimitAmount" class="myform-control" id="exemptionLimitAmount"/>
				</div>
				<div class="col-md-3">
				<label class="control-label"
								style="padding-top: 15px; text-align: right;">Action</label>
				<input  class="btn btn-info add-row" type="button" style="margin-bottom: -60px;margin-left: -50px;" value="Add" onclick="addExemptionConfigurationData()"/>
				</div>
				</div>
				<div></div>
			</form:form>
			<div class="form-group col-md-12">


								<div style="clear: both; height: 35px;"></div>


								<table id="exemptionConfigurationTableId">

									<tbody>
										<tr>
											<th>Effective Date</th>
											<th>Exemption Comparison Sign</th>
											<th>Age</th>
											<th>Exemption Limit Amount</th>
											<th>Action</th>
											
										</tr>
									</tbody>
								</table>
								
								<div style="text-align: center;">
				
					<input type="button" value="Save" class= "btn btn-primary" style="margin-top: 20px;" disabled="disabled" id = "saveButtonIsDisabled" onclick="saveExemptionConfiguration()">
				
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

lable-custom {
    float: left;
    padding-top: 13px;
    padding-right: 10px;
    padding-left: 10px;

}
inputCustom{

    width: 30%;
    float: left;

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

$(document)
.ready(
		function() {
				$('#age').keyup(function(){
				  if ($(this).val() > 100){
				   
				    $(this).val('100');
				  }
				});
				
				var age_ = document
				.getElementById('age');
				
				age_.onkeydown = function(e) {
					if (!((e.keyCode > 95 && e.keyCode < 106)
							|| (e.keyCode > 47 && e.keyCode < 58) || e.keyCode == 8)) {
						return false;
					}
					if (age_.value.length > 2
							&& e.keyCode != 8) {
						return false;
					}
				}
				var exemptionLimitAmount_ = document
				.getElementById('exemptionLimitAmount');
				
				exemptionLimitAmount_.onkeydown = function(e) {
					if (!((e.keyCode > 95 && e.keyCode < 106)
							|| (e.keyCode > 47 && e.keyCode < 58) || e.keyCode == 8)) {
						return false;
					}
					
				}
				
  		   });
           
  
	function addExemptionConfigurationData() {
		var age = $("#age").val();
		var exemptionComparisonSign = $("#exemptionComparisonSign").val();
		var exemptionLimitAmount = $("#exemptionLimitAmount").val();
		var dateEffective = document.getElementById('effectiveDate').value;
		if(dateEffective == ""){
			$("#effectiveDate").css("borderColor","red");
			return false;
		}else{
			$("#effectiveDate").css("borderColor","grey");
		}
		if(age == ""){
			$("#age").css("borderColor","red");
			return false;
		}else{
			$("#age").css("borderColor","grey");
		}
		if(exemptionComparisonSign == "Select"){
			 $("#exemptionComparisonSign").css("borderColor","red");
			 return false;
		}else{
			 $("#exemptionComparisonSign").css("borderColor","grey");
		}
		if(exemptionLimitAmount == ""){
			$("#exemptionLimitAmount").css("borderColor","red");
			return false;
			
		}else{
			$("#exemptionLimitAmount").css("borderColor","grey");
		}
		
		
			var exemptionComparisonSignSelected = $("#exemptionComparisonSign option:selected").html();
			var exemptionConfigurationTableIdLength = document.getElementById("exemptionConfigurationTableId").rows.length;
			if(exemptionConfigurationTableIdLength > 0){
				$("#saveButtonIsDisabled").attr("disabled",false);
			}else{
				$("#saveButtonIsDisabled").attr("disabled",true);
			}
				trHTML ='<tr id = "'+exemptionConfigurationTableIdLength+'"><td>' + dateEffective + '</td>' + '<td>' + exemptionComparisonSignSelected + '</td><td>' + age
				+ '</td><td>' + exemptionLimitAmount + '</td>'
				+ '<td><input type = "button" value="Delete" class="btn btn-primary" onclick="removeRow('+exemptionConfigurationTableIdLength+')"/></td>'+
				'</tr>';
			$('#exemptionConfigurationTableId').append(trHTML);
	}
	
	
	
	
	
	function removeRow(id) {
		$("#"+id).remove();
		var exemptionConfigurationTableIdLength = document.getElementById("exemptionConfigurationTableId").rows.length;
		if(exemptionConfigurationTableIdLength > 1){
			$("#saveButtonIsDisabled").attr("disabled",false);
		}else{
			$("#saveButtonIsDisabled").attr("disabled",true);
		}
	}
	
	function saveExemptionConfiguration() {
		
		var exemptionConfigurationArray = new Array;
		var effectiveDate = document.getElementById('effectiveDate').value;
		var exemptionConfigurationTableIdData = document.getElementById('exemptionConfigurationTableId');
		var exemptionConfigurationTableIdNumberOfRows = exemptionConfigurationTableIdData.rows.length;
		
		for (var i = 1; i < exemptionConfigurationTableIdNumberOfRows; i += 1) {
			var exemptionConfigurationTableRow = exemptionConfigurationTableIdData.rows[i];
			var selectedDate = exemptionConfigurationTableRow.cells[0].innerText;
			var sign = exemptionConfigurationTableRow.cells[1].innerText;
			var age = exemptionConfigurationTableRow.cells[2].innerText;
			var amount = exemptionConfigurationTableRow.cells[3].innerText;
			exemptionConfigurationArray.push({
				effectiveDate : selectedDate,
				exemptionComparisonSign : sign,
				exemptionAge : age,
				exemptionLimitAmount : amount,
			})

		}
		var saveExemptionConfigurationList = JSON.stringify(exemptionConfigurationArray);
		saveExemptionConfigurationList = '{"saveExemptionConfigurationList":'
				+ saveExemptionConfigurationList + '}';
			$("#saveExemptionConfigurationList").val(saveExemptionConfigurationList);
			$("#taxExemptionConfiguration").attr("action", "saveTaxExemptionConfiguration");
			$("#taxExemptionConfiguration").submit();
		
		
	}
	
	
	
	
</script>


<style>
.errmsg {
	color: red;
}
</style>
