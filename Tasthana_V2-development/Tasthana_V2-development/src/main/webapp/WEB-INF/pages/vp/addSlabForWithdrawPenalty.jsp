
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

<div class="right-container" id="right-container" id = "topDIV">
	<div class="container-fluid">



		<div class="Flexi_deposit">

			<div class="header_customer">
				<h3>
					<spring:message code="label.addPenaltyForWithdraw" />

				</h3>
			</div>
<!-- 			<div class="Success_msg"> -->
<!-- 				<div class="successMsg" style="text-align: center; color: red;"> -->
<%-- 					${error}</div> --%>
<!-- 			</div> -->
			<div class="flexi_table">
				<form:form class="form-horizontal" autocomplete="off" id="wthdrawPenaltyForm" 
					commandName="wthdrawPenaltyForm">
					
					
					<div class="fd-list-table">
							
							<div class="form-group col-md-8" style="margin-top: -30px;">
								<label class="col-md-3 control-label" style="padding-top: 16px; text-align: -webkit-left;"><spring:message
										code="label.effectiveDate" /><span style="color: red">*</span></label>
								<div class="col-md-4" style="margin-left: -40px;">
									<form:input path="effectiveDate" id="effectiveDate"
									readonly="true" placeholder="Select Date"
									class="myform-control datepicker-here" dateFormat="dd-mm-yyy"
									required="true" />
									<input type="hidden" name="withdrawPenaltyFormList" id="withdrawPenaltyFormList">
								
								</div>
							</div>
							
							<div style="clear: both;"></div>
							
							<div class="form-group col-md-5">	
							<div style="clear: both; margin-top: 35px margin-bottom: -5px;" class="col-md-12">
								<h4>Amount Range</h4>
							</div>
							<div style="clear: both; width: 325px; border-top: 1px dotted #cfcfcf;"></div>		
							
							<div class="form-group col-md-11">
								<label class="col-md-6 control-label" style="padding-top: 16px; text-align: -webkit-left;">From<span style="color: red">*</span></label>
								<label class="col-md-6 control-label" style="padding-top: 16px; text-align: -webkit-left;">To<span style="color: red">*</span></label>
								
							</div>
						

						
							<div class="form-group col-md-11">
							
								<div class="col-md-6">
									<form:input path="amountFrom" type="number" id="amountFrom"
										placeholder="Amount From" class="myform-control" />
								</div>
								<div class="col-md-6" style="margin-left: -15px;">
									<form:input path="amountTo" type="number" id="amountTo"
										placeholder="Amount To" class="myform-control" />
								</div>
							</div>
							</div>
							
							<div class="form-group col-md-5">
							<div style="clear: both; text-align: -webkit-left;" class="col-md-12">
								<h4>Penalty</h4>
							</div>
							<div style="clear: both; width: 325px; border-top: 1px dotted #cfcfcf;"></div>
							<div class="form-group col-md-11">
									<label class="col-md-6 control-label" style="padding-top: 16px; text-align: -webkit-left;">Rate %<span style="color: red">*</span></label>
									<label class="col-md-6 control-label" style="padding-top: 16px; text-align: -webkit-left;">Flat Amount<span style="color: red">*</span></label>
									
							</div>
						
							<div class="form-group col-md-12">
									<div class="col-md-6">
										<form:input path="penaltyRate" type="number" id="penaltyRate"
											placeholder="Penalty Rate" class="myform-control" />
									</div>
									<div class="col-md-6" style="margin-left: -15px;">
										<form:input path="penaltyFlatAmount" type="number" id="penaltyFlatAmount"
											placeholder="Penalty Flat Amount" class="myform-control" />
									</div>
							</div>
						
							
							</div>
							<div class="form-group col-md-2" style="margin-top: 95px; margin-left: 15px;">
							<input type="button" class="btn btn-info add-row" value="Add Rate"
								onclick="addPenaltyRates()">
							</div>
							
							
							<div class="form-group col-md-12">
							
	
							<div style="clear: both; height: 5px;"></div>


							<table id="penaltyRateTable">
	
								<tbody>
									<tr>
										
										<th><spring:message code="label.amountFrom" /></th>
										<th><spring:message code="label.amountTo" /></th>
										<th><spring:message code="label.penaltyRate" /></th>
										<th><spring:message code="label.penaltyFlatAmount" /></th>
									</tr>
								</tbody>
							</table>
						
						<%-- <button type="button" class="btn btn-info delete-row">
							<spring:message code="label.deleteRow" />
						</button> --%>

							<div class="form-group">
								<div class="col-md-6"></div>
								<div class="col-md-6">
									<input type="button" id="goBtn" class="btn btn-info"
										data-toggle="tooltip" onclick="submitData()" value="Save"
										title="Please select mode of transfer and deposit id">
								</div>
							</div>
							<span id="errorMsg" style="display: none; color: red"><spring:message
									code="label.categoryMsg" /></span> 
									<span id="sucessMsg" style="display: none; color: green"><spring:message
									code="label.categoryMsg" /></span>
									<span id="radioError" style="display: none; color: red"><spring:message
									code="label.radioError" /></span> 
									<span id="validationMsg"
								style="display: none; color: red"><spring:message
									code="label.startDayEndDayError" /> 
									</span> <span id="graterMsg"
								style="display: none; color: red"><spring:message
									code="label.withdrawPenaltyError" /> </span> <span id="negativeError"
								style="display: none; color: red"><spring:message
									code="label.negativeError" /> </span>
								<span id="duplicateError"
								style="display: none; color: red"><spring:message
									code="label.withdrawPenaltyduplicateError"/> </span>
									<span id="nriAccountTypeError"
								style="display: none; color: red"><spring:message
									code="label.nriAccountTypeError" /> </span>
						</div>					
					</div>	
				</form:form>
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
	// global variable:
	 var mapValidater = new Map();
	var duplicate = 0;
	var myArray = new Array();

	// Function called when the form is submitted.
	// Function adds a task to the global array.
	var maxNumber = 0;
	
	function addPenaltyRates() {
		debugger;
		var submit = false;
		// Get the task:
		var amountFrom = document.getElementById('amountFrom').value;
		var amountTo = document.getElementById('amountTo').value;
		var penaltyRate = document.getElementById('penaltyRate').value;
		var penaltyFlatAmount = document.getElementById('penaltyFlatAmount').value;
		document.getElementById('errorMsg').style.display = 'none';
		
		
		//var currency = document.getElementById('currency').value;
		if (amountFrom == '') {
			document.getElementById('amountFrom').style.borderColor = "red";
			return submit;
		}

		else {
			document.getElementById('amountFrom').style.borderColor = "black";
		}

		if (amountTo == '') {
			document.getElementById('amountTo').style.borderColor = "red";
			return submit;
		}

		else {
			document.getElementById('amountTo').style.borderColor = "black";
		}

	
		
		if (Number(amountFrom) > Number(amountTo)) {
			document.getElementById('graterMsg').style.display = 'block';
			
			return submit;
		} else {

			document.getElementById('graterMsg').style.display = 'none';
			document.getElementById('errorMsg').style.display = 'none';
		}
		
		if (penaltyRate == "" && penaltyFlatAmount == "") {
			document.getElementById('penaltyRate').style.borderColor = "red";
			document.getElementById('penaltyFlatAmount').style.borderColor = "red";
			document.getElementById('negativeError').style.display = 'block';
			document.getElementById('negativeError').textContent  = 'At least one penalty rate needs to be entered';
			return submit;
		}else{
			document.getElementById('penaltyRate').style.borderColor = "black";
			document.getElementById('penaltyFlatAmount').style.borderColor = "black";
			document.getElementById('negativeError').style.display = 'none';
		}
		

		if (Number(amountFrom) < 0 || Number(amountTo) < 0) {
			document.getElementById('amountFrom').style.borderColor = "red";
			document.getElementById('amountTo').style.borderColor = "red";
			document.getElementById('negativeError').style.display = 'block';
			return submit;
		}
		else{
			document.getElementById('amountFrom').style.borderColor = "black";
			document.getElementById('amountTo').style.borderColor = "black";
			document.getElementById('negativeError').style.display = 'none';	
		}

	
		
 		duplicate = 0;
 		if (amountFrom && amountTo)
 			{
 			
 			if(amountFrom <= maxNumber){
 				document.getElementById('duplicateError').style.display = 'block';
 				return false;
 			}
 			
 			
 			
 			
 			
 			
 			
			// Add the item to the array:
 			if(myArray.length>0)
 				{
				
			for (var i = 0, count = myArray.length; i < count; i++) {
					if(Number(amountFrom) > Number(myArray[i].amountFrom)){
						if (Number(amountFrom) <= Number(myArray[i].amountTo)){
							duplicate = 1;
							break;						}
					}
					if(Number(amountTo) > Number(myArray[i].amountFrom)){
						if (Number(amountTo) <= Number(myArray[i].amountTo)){
 							duplicate = 1;
 							break;
 						}
					}
					
				}
 				if(duplicate == 1){
 					document.getElementById('duplicateError').style.display = 'block';
 				}
				else {
					document.getElementById('duplicateError').style.display = 'none';
					
 				}
	
 			}
 			if(duplicate == 1)
 				return false;
 			}


 		var message = '';	
		var trHTML = '';

		
		var mapSize = mapValidater.size;
		if(mapSize == 0 ){
			mapValidater.set(amountFrom,amountTo);
			document.getElementById('errorMsg').style.display = 'none';
			
		}else{
			
			if(mapValidater.has(amountFrom)){
				
				if(mapValidater.get(amountFrom) == amountTo){
					document.getElementById('amountFrom').style.borderColor = "red";
					document.getElementById('amountTo').style.borderColor = "red";
					document.getElementById('effectiveDate').style.borderColor = "black";
					document.getElementById('errorMsg').textContent="Amount From and Amount To can not be Duplicate!";
					document.getElementById('errorMsg').style.display = 'block';
					return false;
					
				}else{
					mapValidater.set(amountFrom,amountTo);
					document.getElementById('errorMsg').style.display = 'none';
				}
			}
			
			else{
				mapValidater.set(amountFrom,amountTo);
				document.getElementById('errorMsg').style.display = 'none';
			}
		}
			
			maxNumber = amountTo;
			
			trHTML = '<tr><td>' + amountFrom + '</td><td>' + amountTo  + '</td><td>' + penaltyRate
			   + '</td><td>' + penaltyFlatAmount + '</td></tr>';
			   

		$('#penaltyRateTable').append(trHTML)

		
 			if (amountFrom && amountTo) {
// 			// Check for overlapping range
		

 						// Add the item to the array:					
						myArray.push({
 							amountFrom : amountFrom,
 							amountTo : amountTo,
							
 						})
			
 						// Update the page:
						message = '<h2>Persons Entered</h2><ol>';
 						for (var i = 0, count = myArray.length; i < count; i++) {
							message += '<li><span>' + myArray[i].amountFrom
									+ '</span><span> ' + myArray[i].amountTo;
 						}
 						message += '</ol>';
 						document.getElementById('errorMsg').innerHTML = message;
				

 		} // End of IF
		// Return false to prevent submission:
			
		
			
		return false;

	}

	
	function submitData() {
		debugger;
		var myArray = new Array;
	
		var effectiveDate = document.getElementById('effectiveDate').value;
		
		 var tableData = document.getElementById('penaltyRateTable');
         var numberOfRows = tableData.rows.length;
         //var bodyStart = '{"data":[';
         for (var i = 1; i < numberOfRows; i += 1)
         {
             var row = tableData.rows[i];
             var amountFrom = row.cells[0].innerText;
             var amountTo = row.cells[1].innerText;
             var penaltyRate = row.cells[2].innerText;
             var penaltyFlatAmount = row.cells[3].innerText;
             var id = 0;
         	 var withdrawPenaltyMasterId = 0;
         	 var htmlArry = "";
            	   	
             myArray.push({
            	 	id : id,
            	 	withdrawPenaltyMasterId : withdrawPenaltyMasterId,
            	 	effectiveDate : effectiveDate,
					amountFrom : amountFrom,
					amountTo : amountTo,
					penaltyRate: penaltyRate,
					penaltyFlatAmount: penaltyFlatAmount
			})
				
			
            	
         }
		//withdrawPenaltyList
		var withdrawPenaltyFormList = JSON.stringify(myArray);
		withdrawPenaltyFormList = '{"withdrawPenaltyFormList":' + withdrawPenaltyFormList + '}';
		$("#withdrawPenaltyFormList").val(withdrawPenaltyFormList);
		 var validation = val();
		 if(validation){
		$("#wthdrawPenaltyForm").attr("action","saveWithdrawPenalty");
		$("#wthdrawPenaltyForm").submit();
		 }
		
		//debugger
// 		$("#wthdrawPenaltyForm").attr("action",
// 				"saveWithdrawPenalty?myJson=" + encodeURI(jsonArray));
// 		$("#wthdrawPenaltyForm").submit();
	<%-- 	$.ajax({  
			type: 'POST',
			async: false,
		    url: "<%=request.getContextPath()%>/vp/saveWithdrawPenalty", 
		    contentType: "application/json",
		    dataType: "*",
		    data: withdrawPenaltyFormList,
		    success: function(response){  
		    	url_ = "<%=request.getContextPath()%>/vp/vpWithdrawPenaltySaved";
		    	alert(url_);
		    	/* $.get(url, function( data ) {
		    		 $("#sucessMsg").text("Saved Successfully");
			    	 $("#sucessMsg").show();
		    		}); */
		    
	   		 },
		    error: function(e){  
		    	 $('#error').html("Error occured!!");
		    	 console.log(e);
		    	 alert("error");
		    	// window.location.href = "http://www.google.com";
	
		    	//return false;
		    }  
		});   --%>
	}

	$(document)
			.ready(
					function() {
						$(".add-row")
								.click(
										function() {
											
// 											var amountFrom = $("#amountFrom").val();
// 											var amountTo = $("#amountTo").val();
											
// 											if (amountFrom != '' && amountTo != ''
													
// 													&& (Number(amountFrom) <= Number(endDay))
// 													&& Number(amountFrom) > 0
// 													&& Number(amountTo) > 0 && Number(duplicate) == 0) {

// 												var markup = "<tr><td>"
// 														+ amountFrom
// 														+ "</td><td>"
// 														+ amountTo
// 														+ "</td><td>"
// 														+ amountFrom +" day to " + amountTo + " days"
// 														;

// 												$("table tbody").append(markup);
// 												document
// 														.getElementById('amountFrom').value = ''
// 												document
// 														.getElementById('amountTo').value = ''
												
// 												document
// 														.getElementById('errorMsg').style.display = 'none';
// 												document
// 														.getElementById('graterMsg').style.display = 'none';
// 												document.
// 												        getElementById('negativeError').style.display = 'none';
// 											}
											
											
										});

						// Find and remove selected table rows
						$(".delete-row").click(
								function() {
									$("table tbody").find(
											'input[name="record"]').each(
											function() {
												if ($(this).is(":checked")) {
													$(this).parents("tr")
															.remove();
												}
											});
								});
					});

	function val() {

		var submit = true;

		
		var effectiveDate = document.getElementById('effectiveDate').value;
		var amountFrom = document.getElementById('amountFrom').value;
		var penaltyFlatAmount = document.getElementById('penaltyFlatAmount').value;
		var amountTo = document.getElementById('amountTo').value;
		var  penaltyRate = document.getElementById('penaltyRate').value;
		 var tableData = document.getElementById('penaltyRateTable');
         var numberOfRows = tableData.rows.length;
		
// 		if (myArray.length == '0') {

// 			document.getElementById('amountFrom').style.borderColor = "red";
// 			document.getElementById('amountTo').style.borderColor = "red";
// 			document.getElementById('errorMsg').style.display = 'block';
// 			submit = false;

// 		}

// 		if (amountFrom == '' && amountTo == '' && myArray.length == '0') {
// 			document.getElementById('amountFrom').style.borderColor = "red";
// 			document.getElementById('amountTo').style.borderColor = "red";
// 			document.getElementById('errorMsg').style.display = 'block';
// 			document.getElementById('validationMsg').style.display = 'none';

// 		} else 
	
	
			
		 if(effectiveDate == ""){
			document.getElementById('effectiveDate').style.borderColor = "red";
			document.getElementById('errorMsg').textContent="Effective Date can not be empty!";
			document.getElementById('errorMsg').style.display = 'block';
			submit = false;
			return submit;
			
		}else if (amountFrom == '' && amountTo == '') {
			document.getElementById('amountFrom').style.borderColor = "red";
			document.getElementById('amountTo').style.borderColor = "red";
			document.getElementById('effectiveDate').style.borderColor = "black";
			document.getElementById('errorMsg').textContent="Amount From and Amount To can not be empty!";
			document.getElementById('errorMsg').style.display = 'block';
			submit = false;
			return submit;

		} else if(penaltyRate == "" && penaltyFlatAmount == ""){
			document.getElementById('penaltyRate').style.borderColor = "red";
			document.getElementById('penaltyFlatAmount').style.borderColor = "red";
			document.getElementById('errorMsg').textContent = "Penalty Rate and Penalty Flat Amount can not be empty both of them  atleast one is mandtory!";
			document.getElementById('errorMsg').style.display = 'block';
			submit = false;
			return submit;
			
		}else if(numberOfRows == 1){
			document.getElementById('errorMsg').textContent="Please add Rate!";
			document.getElementById('errorMsg').style.display = 'block';
			submit = false;
			return submit;
		}else{
			document.getElementById('amountFrom').style.borderColor = "black";
			document.getElementById('amountTo').style.borderColor = "black";
			document.getElementById('validationMsg').style.display = 'none';
			
		}
		
		 if(numberOfRows > 1){
			 
		 
			 
		 }
		
		
		
		

		return submit;
	}
	
</script>