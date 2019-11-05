
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
					<spring:message code="label.addDuration" />

				</h3>
			</div>
			<div class="Success_msg">
				<div class="successMsg" style="text-align: center; color: red;">
					${error}</div>
			</div>
			<div class="flexi_table">
				<form:form class="form-horizontal" autocomplete="off"
					commandName="addRateForm" name="addRate" onsubmit="return val();">
					
					
					<div class="fd-list-table">
					
					<div class="form-group col-md-6" id="ripDiv" style="text-align: -webkit-left; margin-top: -10px;">
							<label class="col-md-3 control-label" style="padding-top: 6px; margin-left:15px; text-align: -webkit-left;"><spring:message
									code="label.citizen" /><span style="color: red">*</span></label>
							<div class="col-md-8" style="margin: 2px 0 0 -45px;">

								<label for="radio"> <form:radiobutton path="citizen"
										id="RIRadio" name="citizen" value="RI" onclick="citizenFun()" checked="true"></form:radiobutton></label>
								<spring:message code="label.ri" />
								<label for="radio"> <form:radiobutton path="citizen"
										id="NRIRadio" name="citizen" value="NRI"
										onclick="citizenFun()"></form:radiobutton></label>
								<spring:message code="label.nri" />
							</div>
						</div>


						<div class="form-group col-md-3" style="display: none; margin: -5px 0 0 -95px;" id="accountTypeDIV">
							<label class="col-md-6 control-label"><spring:message
									code="label.accountType" /><span style="color: red">*</span></label>


							<div class="col-md-6" style="min-width: 120px !important; margin-left: 95px; margin-top: -45px;">
								<form:select id="nriAccountType" path="nriAccountType"
									class="myform-control" >
									<form:option value="">
										<spring:message code="label.select" />
									</form:option>
									<form:option value="NRE">
										<spring:message code="label.nre" />
									</form:option>
									<form:option value="NRO">
										<spring:message code="label.nro" />
									</form:option>
									<form:option value="FCNR">
										<spring:message code="label.fcnr" />
									</form:option>
									<form:option value="RFC">
										<spring:message code="label.rfc" />
									</form:option>
									<form:option value="PRP">
										<spring:message code="label.prp" />
									</form:option>
								</form:select>
							</div>
						</div>
						
						<div class="col-md-3" style="margin-top: -15px;">
						<div id="addRateCategory" class="collapse in">
							<div class="form-group">
								<label class="col-md-6 control-label" style="padding-top: 7px;"><spring:message
										code="label.customerCategory" /><span style="color: red">*</span></label>

								<c:choose>
									<c:when test="${setCategory!=null}">
										<div class="col-md-6" style="min-width: 175px !important; margin-left: 115px; margin-top: -45px;">

											<form:select id="setCategory" path="category"
												class="myform-control" required="true" onChange="changeCategory($(this))">
												<option value="select">Select</option>
												<c:forEach var="item" items="${setCategory}">

													<option nriAccountType="${item.nriAccountType}" citizen="${item.citizen}" value="${item.customerCategory}"
														<c:if test="${item.customerCategory eq selectedCategory}"><spring:message code="label.selected" /></c:if>>${item.customerCategory}</option>
												</c:forEach>
											</form:select>


										</div>
									</c:when>
									<c:otherwise>
										<div class="col-md-6">
											<form:select id="setCategory" path="type" class="myform-control">
												<option value="select"><spring:message
														code="label.select" /></option>

											</form:select>
										</div>
									</c:otherwise>
								</c:choose>

							</div>

						</div>
					</div>
						
						<div style="clear:both; height: 15px;"></div>
						
						<div class="form-group col-md-4">
								<label class="col-md-6 control-label" style="padding-top: 16px; text-align: -webkit-left;"><spring:message
										code="label.startDay" /><span style="color: red">*</span></label>
								<div class="col-md-6" style="margin-left: -60px;">
									<form:input path="fromDay" type="number" id="startDay"
										placeholder="Start Day" class="myform-control" />
								</div>
							</div>
						

						
							<div class="form-group col-md-4" style="margin-left: -45px;">
								<label class="col-md-6 control-label" style="padding-top: 16px; padding-left: 0px; margin-left: -35px;"><spring:message
										code="label.endDay" /><span style="color: red">*</span></label>
								<div class="col-md-6" style="margin-left: -15px;">
									<form:input path="toDay" type="number" id="endDay"
										placeholder="End Day" class="myform-control" />
								</div>
							</div>
						
						
						<div class="col-md-4" style="margin-left: -35px;">
						<label class="col-md-6 control-label" style="padding-top: 14px; margin-left: -35px;"><spring:message
								code="label.depositType" /><span style="color: red">*</span></label>


						<div class="col-md-4" style="margin-left: -15px; min-width: 200px;">
							<form:select id="depositClassification"
								path="depositClassification" class="myform-control"
								required="true">
								<form:option value="Regular Deposit" selected="true">
									<spring:message code="label.fixedDeposit" />
								</form:option>
								<form:option value="Recurring Deposit">
									<spring:message code="label.recurringDeposit" />
								</form:option>
								<form:option value="Tax Saving Deposit">
									<spring:message code="label.taxSavingDeposit" />
								</form:option>
								<form:option value="Annuity Deposit">
									<spring:message code="label.annuityDeposit" />
								</form:option>

							</form:select>
						</div>
						
						</div>
						<div style="clear: both;"></div>

						<input type="button" class="btn btn-info add-row" value="Add Row"
							onclick="addRateValueInArray()">

						<div style="clear: both; height: 10px;"></div>


						<table>

							<tbody>
								<tr>
									
									<th><spring:message code="label.startDay" /></th>
									<th><spring:message code="label.endDay" /></th>
									<th><spring:message code="label.description" /></th>
									

								</tr>
							</tbody>
						</table>
						
						<%-- <button type="button" class="btn btn-info delete-row">
							<spring:message code="label.deleteRow" />
						</button> --%>
<input type="hidden" name="menuId" value="${menuId}"/>
						<div class="form-group">
							<div class="col-md-6"></div>
							<div class="col-md-6">
								<input type="submit" id="goBtn" class="btn btn-info"
									data-toggle="tooltip" onclick="submitData()" value="Proceed"
									title="Please select mode of transfer and deposit id">
							</div>
						</div>
						<span id="errorMsg" style="display: none; color: red"><spring:message
								code="label.categoryMsg" /></span> 
								<span id="radioError" style="display: none; color: red"><spring:message
								code="label.radioError" /></span> 
								<span id="validationMsg"
							style="display: none; color: red"><spring:message
								code="label.startDayEndDayError" /> </span> <span id="graterMsg"
							style="display: none; color: red"><spring:message
								code="label.compareError" /> </span> <span id="negativeError"
							style="display: none; color: red"><spring:message
								code="label.negativeError" /> </span>
							<span id="duplicateError"
							style="display: none; color: red"><spring:message
								code="label.duplicateError" /> </span></div>
								<span id="nriAccountTypeError"
							style="display: none; color: red"><spring:message
								code="label.nriAccountTypeError" /> </span></div>
								
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
	var myArray = new Array;
	var duplicate = 0;

	// Function called when the form is submitted.
	// Function adds a task to the global array.
	function addRateValueInArray() {

		var submit = false;
		// Get the task:
		var startDay = document.getElementById('startDay').value;
		var endDay = document.getElementById('endDay').value;
		
		//var currency = document.getElementById('currency').value;
		if (startDay == '') {
			document.getElementById('startDay').style.borderColor = "red";
			return submit;
		}

		else {
			document.getElementById('startDay').style.borderColor = "black";
		}

		if (endDay == '') {
			document.getElementById('endDay').style.borderColor = "red";
			return submit;
		}

		else {
			document.getElementById('endDay').style.borderColor = "black";
		}

	
		
		if (Number(startDay) > Number(endDay)) {

			document.getElementById('graterMsg').style.display = 'block';
			return submit;
		} else {

			document.getElementById('graterMsg').style.display = 'none';
		}

		if (Number(startDay) < 0 || Number(endDay) < 0) {
			document.getElementById('startDay').style.borderColor = "red";
			document.getElementById('endDay').style.borderColor = "red";
			document.getElementById('negativeError').style.display = 'block';
			return submit;
		}
		else{
			document.getElementById('startDay').style.borderColor = "black";
			document.getElementById('endDay').style.borderColor = "black";
			document.getElementById('negativeError').style.display = 'none';	
		}

	
		
		duplicate = 0;
		if (startDay && endDay)
			{
			// Add the item to the array:
			if(myArray.length>0)
				{
				
				for (var i = 0, count = myArray.length; i < count; i++) {
					
					if(Number(startDay) > Number(myArray[i].startDay)){
						if (Number(startDay) < Number(myArray[i].endDay)){
							duplicate = 1;
							break;
						}
					}
					if(Number(endDay) > Number(myArray[i].startDay)){
						if (Number(endDay) < Number(myArray[i].endDay)){
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

		// Reference to where the output goes:
		// var output = document.getElementById('output');

		// For the output:
		var message = '';
		
		if (startDay && endDay) {
			// Check for overlapping range
		

						// Add the item to the array:					
						myArray.push({
							startDay : startDay,
							endDay : endDay,
							
						})
			
						// Update the page:
						message = '<h2>Persons Entered</h2><ol>';
						for (var i = 0, count = myArray.length; i < count; i++) {
							message += '<li><span>' + myArray[i].startDay
									+ '</span><span> ' + myArray[i].endDay;
						}
						message += '</ol>';
						// output.innerHTML = message;
				

		} // End of IF
		// Return false to prevent submission:
			
			
		return false;

	}

	
	function submitData() {
		var jsonArray = JSON.stringify(myArray);
		$("#addRateForm").attr("action",
				"durationPost?myJson=" + encodeURI(jsonArray));
		$("#addRateForm").submit();
	}
var NRIAccount=[];
	$(document)
			.ready(
					function() {
						
						citizenFun();
						$(".add-row")
								.click(
										function() {
											
											var startDay = $("#startDay").val();
											var endDay = $("#endDay").val();
											
											if (startDay != '' && endDay != ''
													
													&& (Number(startDay) <= Number(endDay))
													&& Number(startDay) > 0
													&& Number(endDay) > 0 && Number(duplicate) == 0) {

												var markup = "<tr><td>"
														+ startDay
														+ "</td><td>"
														+ endDay
														+ "</td><td>"
														+ startDay +" day to " + endDay + " days"
														;

												$("table tbody").append(markup);
												document
														.getElementById('startDay').value = ''
												document
														.getElementById('endDay').value = ''
												
												document
														.getElementById('errorMsg').style.display = 'none';
												document
														.getElementById('graterMsg').style.display = 'none';
												document.
												        getElementById('negativeError').style.display = 'none';
											}
											
											
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
		var category = document.getElementById('setCategory').value;
		
		var depositClassification = document.getElementById('depositClassification').value;
		var startDay = document.getElementById('startDay').value;
		var endDay = document.getElementById('endDay').value;
		var RIRadioChecked = document.getElementById("RIRadio").checked;
		var NRIRadioChecked = document.getElementById("NRIRadio").checked;
		var accountType = document.getElementById('nriAccountType').value;
		if(RIRadioChecked== false && NRIRadioChecked==false){
			document.getElementById('radioError').style.display = 'block';
			submit = false;
		}
		
		if(category =="select"){
			document.getElementById('setCategory').style.borderColor = 'red';
			submit = false;	
			}
			else{
				document.getElementById('setCategory').style.borderColor = 'blue';

			}
		
		if(NRIRadioChecked==true){
			
			if(accountType ==""){
			document.getElementById('nriAccountType').style.borderColor = 'red';
			document.getElementById('nriAccountTypeError').style.display = 'block';
			submit = false;	
			}
			else{
				document.getElementById('nriAccountType').style.borderColor = 'blue';

			}
		}
		

		if (myArray.length == '0') {

			document.getElementById('startDay').style.borderColor = "red";
			document.getElementById('endDay').style.borderColor = "red";
			document.getElementById('errorMsg').style.display = 'block';
			submit = false;

		}

		if (startDay == '' && endDay == '' && myArray.length == '0') {
			document.getElementById('startDay').style.borderColor = "red";
			document.getElementById('endDay').style.borderColor = "red";
			document.getElementById('errorMsg').style.display = 'block';
			document.getElementById('validationMsg').style.display = 'none';

		} else if (startDay == '' && endDay == '') {
			document.getElementById('startDay').style.borderColor = "black";
			document.getElementById('endDay').style.borderColor = "black";
			document.getElementById('errorMsg').style.display = 'none';

		} else if (startDay != '' && endDay != '') {
			document.getElementById('startDay').style.borderColor = "black";
			document.getElementById('endDay').style.borderColor = "black";
			document.getElementById('validationMsg').style.display = 'none';
		} else {
			document.getElementById('startDay').style.borderColor = "red";
			document.getElementById('endDay').style.borderColor = "red";
			document.getElementById('validationMsg').style.display = 'block';
			submit = false;
		}

		return submit;
	}
	
	function changeCategory(obj){
		debugger;
		var html="<option value=''>Select</option>";
		var NRIRadioChecked = document.getElementById("NRIRadio").checked;
		var nriAccounts=$('option:selected', obj).attr('nriaccounttype');
		if(NRIRadioChecked== true && nriAccounts!=undefined){
			//var nriAccounts=obj.attr('nriaccounttype');
			//var nriAccounts=$('option:selected', obj).attr('nriaccounttype');
			var array = nriAccounts.split(',');
			$.each(array, function( index, value ) {
				html +="<option value='"+value+"'>"+value+"</option>";
				});
		}
		
		$('#nriAccountType').html(html).val('').change();
	}
	
	function citizenFun(){
		
		var RIRadioChecked = document.getElementById("RIRadio").checked;
		var NRIRadioChecked = document.getElementById("NRIRadio").checked;
		if(RIRadioChecked==true){
			document.getElementById('accountTypeDIV').style.display = 'none';
			document.getElementById('radioError').style.display = 'none';
			document.getElementById('nriAccountType').value="";
			$('#setCategory option').each(function(){
				if($(this).attr('citizen')=="RI"){
					$(this).show();
				}
				if($(this).attr('citizen')=="NRI"){
					$(this).hide();
				}
			});
		}
		if(NRIRadioChecked== true){
			document.getElementById('accountTypeDIV').style.display = 'block';
			document.getElementById('radioError').style.display = 'none';
			$('#setCategory option').each(function(){
				if($(this).attr('citizen')=="RI"){
					$(this).hide();
				}
				if($(this).attr('citizen')=="NRI"){
					$(this).show();
				}
			});
		}
		$('#setCategory').val('select').change();
	}
</script>tElementById('radioError').style.display = 'none';
		}
		
	}
</script>