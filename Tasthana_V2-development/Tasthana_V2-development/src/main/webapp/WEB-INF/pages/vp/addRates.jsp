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
					<spring:message code="label.addRate" />

				</h3>
			</div>
			<div class="Id">
				<div class="successMsg" style="text-align: center; color: red;">
					${Id}</div>
			</div>

			<div class="Success_msg">
				<div class="successMsg" style="text-align: center; color: red;">
					${error}</div>
			</div>
			<form:form class="form-horizontal" autocomplete="off"
				commandName="addRateForm" id="addRateForm" name="addRate">
				<div class="flexi_table" style="margin-top: 7px;">


					<!-- action="addRatePost" -->
					<div class="col-md-6">

						<div class="form-group" id="ripDiv">
							<label class="col-md-4 control-label"
								style="padding-top: 14px; margin-left: 0;"><spring:message
									code="label.citizen" /><span style="color: red">*</span></label>
							<div class="col-md-8"
								style="margin-top: 12px; margin-left: -15px;">

								<label for="radio"> <form:radiobutton path="citizen"
										id="RIRadio" name="citizen" value="RI" onclick="citizenFun()"
										checked="true"></form:radiobutton></label>
								<spring:message code="label.ri" />
								<label for="radio"> <form:radiobutton path="citizen"
										id="NRIRadio" name="citizen" value="NRI"
										onclick="citizenFun()"></form:radiobutton></label>
								<spring:message code="label.nri" />
							</div>
						</div>

					</div>

					<div class="col-md-6">
						<div class="form-group" id="accountTypeDIV" style="display:none">
							<label class="col-md-4 control-label"
								style="padding-top: 14px; margin-left: 70px;"><spring:message
									code="label.accountType" /><span style="color: red">*</span></label>


							<div class="col-md-4"
								style="margin-left: -15px; min-width: 165px;" >
								<form:select id="nriAccountType" path="nriAccountType"
									class="myform-control" onchange="accountTypeVal(this.value);resetCurrency(this.value)">
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
					</div>

					<div style="clear: both; height: 20px;"></div>
					<div class="col-md-4">

						<label class="col-md-6 control-label" style="padding-top: 14px;"><spring:message
								code="label.depositType" /><span style="color: red">*</span></label>
						<div class="col-md-6" style="margin-left: -15px;">
							<form:select id="depositClassification"
								path="depositClassification" class="myform-control"
								required="true">
								<option value="">Select</option>
								<c:forEach var="item" items="${depositClassificationList}">
									<option value="${item}"
										<c:if test="${item eq selectedDepositClassification}">selected</c:if>>${item}</option>
								</c:forEach>

							</form:select>
						</div>
					</div>



					<div class="col-md-4" style="margin-left: -45px;">
						<div id="addRateCategory" class="collapse in">
							<div class="form-group">
								<label class="col-md-6 control-label" style="padding-top: 16px;"><spring:message
										code="label.customerCategory" /><span style="color: red">*</span></label>

								<c:choose>
									<c:when test="${setCategory!=null}">
										<div class="col-md-6"
											style="min-width: 125px !important; margin-left: -20px;">

											<form:select id="setCategory" path="category"
												class="myform-control" required="true"
												onchange="changeCategory($(this))">
												<option value="select" selected ="selected">Select</option>
												<c:forEach var="item" items="${setCategory}">

													<option nriAccountType="${item.nriAccountType}" citizen="${item.citizen}" value="${item.customerCategory}"
														<c:if test="${item.customerCategory eq selectedCategory}"><spring:message code="label.selected" /></c:if>>${item.customerCategory}</option>
												</c:forEach>
											</form:select>


										</div>
									</c:when>
									<c:otherwise>
										<div class="col-md-6">
											<form:select id="category" path="type" class="myform-control">
												<option value="select"><spring:message
														code="label.select" /></option>

											</form:select>
										</div>
									</c:otherwise>
								</c:choose>

							</div>

						</div>
					</div>

					<div class="col-md-4" style="margin-left: -70px;">
						<div class="form-group">
							<label class="col-md-6 control-label" style="padding-top: 16px;"><spring:message
									code="label.effectiveDate" /><span style="color: red">*</span></label>
							<div class="col-md-6"
								style="min-width: 185px !important; margin-left: -15px;">
								<form:input path="effectiveDate" id="effectiveDate"
									readonly="true" placeholder="Select Date"
									class="myform-control datepicker-here" dateFormat="dd-mm-yyy"
									required="true" />
							</div>
						</div>

					</div>





					<div
						style="clear: both; margin-left: 55px; margin-top: 35px margin-bottom: -5px;"
						class="col-md-12">
						<h4>
							<spring:message code="label.amountSlab" />

						</h4>
					</div>
					<div
						style="clear: both; width: 455px; border-top: 1px dotted #cfcfcf; margin-left: 85px;"></div>
					<div style="clear: both" class="col-md-12">
						<div class="form-group" style="margin-left: 3px;">
							<div class="form-group col-md-4">
								<label class="col-md-6 control-label" style="padding-top: 16px;"><spring:message
										code="label.from" /><span style="color: red">*</span></label>
								<div class="col-md-6"
									style="min-width: 150px !important; margin-left: -15px;">
									<form:input path="amountSlabFrom" id="amountSlabFrom"
										class="myform-control" required="true" />
									<span class="errmsg" id="errmsgSF"></span>

								</div>
							</div>



							<div class="form-group col-md-4">
								<label class="col-md-6 control-label"
									style="padding-top: 16px; margin-left: -140px;"><spring:message
										code="label.to" /></label>
								<div class="col-md-6"
									style="min-width: 150px !important; margin-left: -15px;">
									<form:input path="amountSlabTo" id="amountSlabTo"
										class="myform-control" required="true"/>
									<span class="errmsg" id="errmsgST"></span>

								</div>
								<input type="button" value ="Get Slabs" class="btn btn-info" style="margin-top: 6px;padding-right: 17px;margin-left: 38px;" onclick="categoryAndDepositOrAccount()">
							</div>


							<div class="form-group col-md-4">
								<label class="col-md-4 control-label" style="margin-left: 10px;"><spring:message
										code="label.currency" /><span style="color: red">*</span></label>

								<div class="col-md-7"
									style="margin-top: -14px; margin-left: -15px; min-width: 145px;">
									<form:select id="currency" path="currency"
										class="input myform-control" >
										<form:option value="Select"></form:option>
									</form:select>

									<script>

									if('${currency}'!=""){
										if('${nriAccType}' == 'FCNR' || '${nriAccType}' == 'PRP')
											populateCurrencyEditFCNR("currency","${currency}");
										else if('${nriAccType}' == 'RFC')
											populateCurrencyEditRFC("currency","${currency}");
										else
											populateCurrencyEdit("currency","${currency}");
									}
									else{
										if('${nriAccType}' == 'FCNR' || '${nriAccType}' == 'PRP')
											populateCurrencyFCNR("currency");
										else if('${nriAccType}' == 'RFC')
											populateCurrencyRFC("currency");
										else
											populateCurrency("currency");
									}
									</script>
									<span id="currencyError" style="display: none; color: red"><spring:message
											code="label.selectCurrency" /></span>
											

								</div>
							</div>


						</div>
					</div>

				</div>

				<div style="clear: both;"></div>

				<span id="errorMsg" style="display: none; color: red"><spring:message
						code="label.selectCategory" /></span>
				<span id="validationMsg" style="display: none; color: red"><spring:message
						code="label.startDayEndDayError" /> </span>
				<span id="graterMsg" style="display: none; color: red"><spring:message
						code="label.compareError" /> </span>
				<span id="negativeError" style="display: none; color: red"><spring:message
						code="label.negativeError" /> </span>
				<span id="duplicateError" style="display: none; color: red"><spring:message
						code="label.duplicateError" /> </span>
				<span id="effectiveDateError" style="display: none; color: red"><spring:message
						code="label.effectiveDateError" /> </span>
				<span id="amountGreaterError" style="display: none; color: red"><spring:message
						code="label.compareAmount" /> </span>
				<span id="negativeError" style="display: none; color: red"><spring:message
						code="label.negativeError" /> </span>

				<div style="clear: both; height: 25px;"></div>
				<div style="text-align: center !important; margin-left: 225px;">
					<table class="table data jqtable example" id="my-table"
						style="width: 60%;">
						<thead>
							<tr>
								<th><spring:message code="label.description" /></th>
								<th>Enter Rate</th>

							</tr>
						</thead>
						<c:if test="${! empty ratesPeriod}">

							<c:forEach items="${ratesPeriod}" var="ratePeriod" varStatus="dl">
								<tr>
									<td style="width: 70%;"><c:out
											value="${ratePeriod.description}"></c:out></td>
									<td style="width: 30%;"><form:input path="rateList"
											name="rateList" type="number" class="input form-control"
											id="rateList[${ratePeriod.id}]"
											onkeyup="enterRate('${ratePeriod.id}','${dl.index}')"
											onkeypress="rateListValidation('${ratePeriod.id}',event)" /></td>

								</tr>
							</c:forEach>
						</c:if>

						</tbody>
					</table>
				</div>
				<div class="Id">
					<div class="successMsg" style="text-align: center; color: red;">
						${Id}</div>
				</div>

			</form:form>

			<div class="form-group">
				<div class="col-md-4"></div>
				<div class="col-md-8" style="padding-left: 25px;">

					<input type="button" id="goBtn" class="btn btn-info"
						data-toggle="tooltip" onclick="submitData()" value="Proceed">
					<span id="blankFormError" style="color: red;"> </span> <span
						id="radioError" style="color: red;"> </span> <span id="succMsg"
						style="color: blue;"> </span> <span id="nriAccountTypeError"
						style="color: red;"> </span> <span id="depositClassificationError"
						style="color: red;"> </span> <span id="addRateCategoryError"
						style="color: red;"> </span>
				</div>

				<div style="clear: both; height: 15px;"></div>
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

$(document)
.ready(
		function() {
			debugger
			 citizenFun();
          $("#amountSlabTo").keypress(function (e) {
 		     //if the letter is not digit then display error and don't type anything
 		     if (e.which != 8 && e.which != 0 && (e.which< 48 || e.which > 57)) {
 		        //display error message
 		        $("#errmsgST").html("Digits Only").show().fadeOut("slow");
 		               return false;
 		    }
 		   });
          
          $("#amountSlabFrom").keypress(function (e) {
  		     //if the letter is not digit then display error and don't type anything
  		     if (e.which != 8 && e.which != 0 && (e.which< 48 || e.which > 57)) {
  		        //display error message
  		        $("#errmsgSF").html("Digits Only").show().fadeOut("slow");
  		               return false;
  		    }
  		   });
           
          
         var formattedValueAmountSlabTo  =  '<fmt:formatNumber type="number" pattern="#.##" value="${addRateForm.amountSlabTo}"/>';
        $("#amountSlabTo").val(formattedValueAmountSlabTo);
        $("#amountSlabFrom").val('<fmt:formatNumber type="number" pattern="#.##" value="${addRateForm.amountSlabFrom}"/>');
      
		   });
		
function accountTypeVal(value) {

	var FCNR = [ "USD", "GBP", "EUR", "JPY", "CAD", "AUD", "SGD", "HKD" ];
	var RFC = [ "USD", "GBP", "EUR", "JPY", "CAD", "AUD" ];
	$('#currency option').hide();
	
	$('#currency option').each(function() {
		if (value == "FCNR" || value == "PRP") {
			var length = $.inArray($(this).val(), FCNR);
			if (length > -1)
				$(this).show();
		} else if (value == "RFC") {
			var length = $.inArray($(this).val(), RFC);
			if (length > -1)
				$(this).show();
		} else {
			
			$('#currency option').show();
		}

	});
	
}
		
function populateRate(){
	
	var depositClassification = document.getElementById('depositClassification').value;

	
	$.ajax({  
		async: false,
		type: "GET",  
	    url: "<%=request.getContextPath()%>/vp/getRateDurationsByDepositClaasification", 
	    contentType: "application/json",
	    dataType: "json",
	    data: "depositClassification="+depositClassification,
   
	    success: function(response){  	
	    	$('#amountSlabFrom').html('');
	  		
    		
        // Check result isnt empt
        if(response != '')
        {
        	var len = response.length;

        	 
            $('#amountSlabFrom').empty();
            $('#amountSlabFrom').append("<option value='Select'>Select</option>");
        	 for( var i = 0; i<len; i++){
                 var val = response[i];
            
                 $('#amountSlabFrom').append("<option value='"+val+"'>"+val+"</option>");
                

             }
        }
	        
    },
    error: function(e){  
    	 $('#error').html("Error occured!!")
    	 alert("error");
    	 confirmation = false;
    	//return false;
    	}  
	});  
}
function enterRate(id,index) {
	
	var rate = document.getElementById("rateList[" + id
			+ "]").value;
	var arrayId = [];
	
	var taskArray = new Array();
	$("input[name=rateList]").each(function() {
		taskArray.push($(this).val());
	});
	
	
}



function rateListValidation(id,event){
	
	var rate = document.getElementById("rateList[" + id
			+ "]").value;
	var keycode = event.which;
	
    if (!(event.shiftKey == false && (keycode == 46 || keycode == 8 || keycode == 37 || keycode == 39 || (keycode >= 48 && keycode <= 57)))) {
        event.preventDefault();
    }	
}

function fun2() {
	var local = globalVariable;

}

function submitData(){
	
	 var submit = true;
	 var citizen;
	 var NRIRadioChecked = document.getElementById("NRIRadio").checked;
	 var RIRadioChecked = document.getElementById("RIRadio").checked;
	 var amountSlabTo = document.getElementById('amountSlabTo').value;
	 var nriAccountType = document.getElementById('nriAccountType').value;
	 var category = document.getElementById('setCategory').value;
	 var currency = document.getElementById('currency').value;
	 var effectiveDate = document.getElementById('effectiveDate').value;
	 var amountSlabFrom = document.getElementById('amountSlabFrom').value;	
	 var depositClassification = document.getElementById('depositClassification').value;
	 var addRateCategory = document.getElementById('setCategory').value;
	 
	 if(NRIRadioChecked == false && RIRadioChecked == false ){
		    document.getElementById('succMsg').innerHTML='';
			document.getElementById('radioError').innerHTML='Please select the citizen radio button.<br>';
			return false; 
	 }
	 else
	 {
		 document.getElementById('radioError').innerHTML=''; 
	 }
	 if(NRIRadioChecked==true){
		  citizen = document.getElementById('NRIRadio').value;
		  if(nriAccountType ==""){
			    document.getElementById('succMsg').innerHTML='';
			    document.getElementById('nriAccountType').style.borderColor = 'red';
				document.getElementById('nriAccountTypeError').innerHTML='Please enter NRI account type.<br>';
				return false;
		  }else{
			   document.getElementById('nriAccountType').style.borderColor = 'blue';
			  document.getElementById('nriAccountTypeError').innerHTML='';
		  }
		  
	 }
	 if(RIRadioChecked == true){
		  citizen = document.getElementById('RIRadio').value;
	 }
	 
	 if(depositClassification==""){
		    document.getElementById('succMsg').innerHTML='';
		    document.getElementById('depositClassification').style.borderColor = 'red';
			document.getElementById('depositClassificationError').innerHTML='Please enter deposit type.<br>';
			return false;
	 }
	 else{
		 document.getElementById('depositClassificationError').innerHTML='';
		   document.getElementById('depositClassification').style.borderColor = 'blue';
	 }
	 if(addRateCategory=="select" ){
		    document.getElementById('succMsg').innerHTML='';
		    document.getElementById('setCategory').style.borderColor = 'red';
			document.getElementById('addRateCategoryError').innerHTML='Please enter category.<br>';
			return false;
	 }
	 else{
		  document.getElementById('setCategory').style.borderColor = 'blue';
		 document.getElementById('addRateCategoryError').innerHTML='';
	 }
	 
	 if (document.getElementById('effectiveDate').value == '') {

			document.getElementById('effectiveDate').style.borderColor = "red";
			submit = false;
		}

		else {
			document.getElementById('effectiveDate').style.borderColor = "blue";
		}
		if (amountSlabFrom==''|| isNaN(amountSlabFrom)) {
			document.getElementById('amountSlabFrom').style.borderColor = "red";
			return false;
		}

		else {
			document.getElementById('amountSlabFrom').style.borderColor = "blue";
		}
		if (amountSlabTo==''|| isNaN(amountSlabTo)) {
			document.getElementById('amountSlabTo').style.borderColor = "red";
			return false;
		}

		else {
			document.getElementById('amountSlabTo').style.borderColor = "blue";
		}
		
		if (currency=='Select') {
			document.getElementById('currency').style.borderColor = "red";
			return false;
		}

		else {
			document.getElementById('currency').style.borderColor = "blue";
		}
		
		if(amountSlabTo !== ''){
		    if((amountSlabFrom  > amountSlabTo )){
		
			document.getElementById('amountSlabFrom').style.borderColor = "red";
			document.getElementById('amountSlabTo').style.borderColor = "red";
			document.getElementById('amountGreaterError').style.display = 'blue';
			submit = false;
			

		   }
		     else{
			  document.getElementById('amountSlabFrom').style.borderColor = "blue";
			  document.getElementById('amountSlabTo').style.borderColor = "blue";
			  document.getElementById('amountGreaterError').style.display = 'none';
		  }
		 
		    if(amountSlabFrom < 0){
		    	document.getElementById('amountSlabFrom').style.borderColor = "red";
		    	document.getElementById('negativeError').style.display = 'block';
		    	submit = false;
		    }
		    else{
		    	document.getElementById('amountSlabFrom').style.borderColor = "blue";
		    	document.getElementById('negativeError').style.display = 'none';
		    }
		}
		
	 var nriAccountType = document.getElementById('nriAccountType').value;
	 var taskArray = new Array();
		$("input[name=rateList]").each(function() {
			if($(this).val()!="")
				{
					var rowVal = $(this).attr("id") + "|" + $(this).val() + "#";
					taskArray.push(rowVal);
				}
		});

		
		if(taskArray.length == 0)
		{
			
		    document.getElementById('succMsg').innerHTML='';
			document.getElementById('blankFormError').innerHTML='Please enter rate for atleast one deposit.<br>';
			return false;
		} 
		
		var dataString = 'category='+ category
	    + '&effectiveDate=' + effectiveDate + '&currency=' + currency + '&amountSlabFrom=' + amountSlabFrom + '&amountSlabTo=' + amountSlabTo + '&depositClassification=' + depositClassification + '&citizen=' + citizen + '&nriAccountType='+nriAccountType;
		
		$.ajax({
			  url: "<%=request.getContextPath()%>/vp/addRatePost",
						data : "rateArrList=" + taskArray + '&dataString='
								+ dataString,
						type : 'post',
						async : true,

						success : function(data) {
							succ = true;
							document.getElementById('blankFormError').innerHTML = '';
							document.getElementById('succMsg').innerHTML = 'Rate added successfully.<br>';
							$("input[name=rateList]")
									.each(
											function() {
												if ($(this).val() != "") {

													var id = $(this).attr("id");
													document
													.getElementById('nriAccountType').value = "";
													document
													.getElementById('depositClassification').value = "";
													document.getElementById(id).value = "";
													document
															.getElementById('setCategory').value = "select";
													document
															.getElementById('currency').value = "Rupee";
													document
															.getElementById('effectiveDate').value = "";
													document
													.getElementById('amountSlabFrom').value = "";
													document
													.getElementById('amountSlabTo').value = "";
													document.getElementById('amountGreaterError').style.display = 'none';

												}
											});
						}
					});
}






</script>






<script type="text/javascript">







	// global variable:
	var myArray = new Array;
	var duplicate = 0;
	var globalVariable;

function categoryAndDepositOrAccount() {
	debugger;
	var NRIRadioChecked = document.getElementById("NRIRadio").checked;
	 var RIRadioChecked = document.getElementById("RIRadio").checked;
	 var amountSlabTo = document.getElementById('amountSlabTo').value;
	 var nriAccountType = document.getElementById('nriAccountType').value;
	 var category = document.getElementById('setCategory').value;
	 var currency = document.getElementById('currency').value;
	 var effectiveDate = document.getElementById('effectiveDate').value;
	 var amountSlabFrom = document.getElementById('amountSlabFrom').value;	
	 var depositClassification = document.getElementById('depositClassification').value;
	 var addRateCategory = document.getElementById('setCategory').value;
	 
	 if(NRIRadioChecked == false && RIRadioChecked == false ){
		    document.getElementById('succMsg').innerHTML='';
			document.getElementById('radioError').innerHTML='Please select the citizen radio button.<br>';
			return false; 
	 }
	 else
	 {
		 document.getElementById('radioError').innerHTML=''; 
	 }
	 if(NRIRadioChecked==true){
		  citizen = document.getElementById('NRIRadio').value;
		  if(nriAccountType ==""){
			    document.getElementById('succMsg').innerHTML='';
			    document.getElementById('nriAccountType').style.borderColor = 'red';
				document.getElementById('nriAccountTypeError').innerHTML='Please enter NRI account type.<br>';
				return false;
		  }else{
			   document.getElementById('nriAccountType').style.borderColor = 'blue';
			  document.getElementById('nriAccountTypeError').innerHTML='';
		  }
		  
	 }
	 if(RIRadioChecked == true){
		  citizen = document.getElementById('RIRadio').value;
	 }
	 
	 if(depositClassification==""){
		    document.getElementById('succMsg').innerHTML='';
		    document.getElementById('depositClassification').style.borderColor = 'red';
			document.getElementById('depositClassificationError').innerHTML='Please enter deposit type.<br>';
			return false;
	 }
	 else{
		 document.getElementById('depositClassificationError').innerHTML='';
		   document.getElementById('depositClassification').style.borderColor = 'blue';
	 }
	 if(addRateCategory=="select" ){
		    document.getElementById('succMsg').innerHTML='';
		    document.getElementById('setCategory').style.borderColor = 'red';
			document.getElementById('addRateCategoryError').innerHTML='Please enter category.<br>';
			return false;
	 }
	 else{
		  document.getElementById('setCategory').style.borderColor = 'blue';
		 document.getElementById('addRateCategoryError').innerHTML='';
	 }
	 
	 if (document.getElementById('effectiveDate').value == '') {

			document.getElementById('effectiveDate').style.borderColor = "red";
			submit = false;
		}

		else {
			document.getElementById('effectiveDate').style.borderColor = "blue";
		}
		if (amountSlabFrom==''|| isNaN(amountSlabFrom)) {
			document.getElementById('amountSlabFrom').style.borderColor = "red";
			return false;
		}

		else {
			document.getElementById('amountSlabFrom').style.borderColor = "blue";
		}
		if (amountSlabTo==''|| isNaN(amountSlabTo)) {
			document.getElementById('amountSlabTo').style.borderColor = "red";
			return false;
		}

		else {
			document.getElementById('amountSlabTo').style.borderColor = "blue";
		}
		
		if (currency=='Select') {
			document.getElementById('currency').style.borderColor = "red";
			return false;
		}

		else {
			document.getElementById('currency').style.borderColor = "blue";
		}
		
		if(amountSlabTo !== ''){
		    if((amountSlabFrom  > amountSlabTo )){
		
			document.getElementById('amountSlabFrom').style.borderColor = "red";
			document.getElementById('amountSlabTo').style.borderColor = "red";
			document.getElementById('amountGreaterError').style.display = 'blue';
			submit = false;
			

		   }
		     else{
			  document.getElementById('amountSlabFrom').style.borderColor = "blue";
			  document.getElementById('amountSlabTo').style.borderColor = "blue";
			  document.getElementById('amountGreaterError').style.display = 'none';
		  }
		 
		    if(amountSlabFrom < 0){
		    	document.getElementById('amountSlabFrom').style.borderColor = "red";
		    	document.getElementById('negativeError').style.display = 'block';
		    	submit = false;
		    }
		    else{
		    	document.getElementById('amountSlabFrom').style.borderColor = "blue";
		    	document.getElementById('negativeError').style.display = 'none';
		    }
		}
		getDepositClassification();
}
	
	function getDepositClassification(){
		
	
		var nriAccType= document.getElementById('nriAccountType').value;
		var category = document.getElementById('setCategory').value;
		var depositClassification= document.getElementById('depositClassification').value;
		 var currency_ = document.getElementById('currency').value;
		$("#addRateForm").attr("action", "getRateDurationsByDepositClaasification?depositClassification="+depositClassification+"&category="+category+"&nriAccType="+nriAccType+"&currency_="+currency_);
		$("#addRateForm").submit();


	}
	
	function changeCategory(obj){
		setTimeout(function(){
			var html="<option value=''>Select</option>";
			var NRIRadioChecked = document.getElementById("NRIRadio").checked;
			var nriAccounts=$('option:selected', obj).attr('nriaccounttype');
			if(nriAccounts == "" && "${nriAccType}" != ""){
				location.reload();
			}
			if(NRIRadioChecked== true && nriAccounts!= undefined){
				var array = nriAccounts.split(',');
				$.each(array, function( index, value ) {
					html +="<option value='"+value+"'>"+value+"</option>";
					});
			}
			$('#nriAccountType').html(html);
			
			if("${nriAccType}" != ""){
			$("#nriAccountType").val("${nriAccType}").change();
			$('#currency').val("${currency_}").change();
			
			}
			else{
				$("#nriAccountType").val("").change();
			}
			
			
		},1);
		
		if("${category}" != ""){
			 $("#currency").html("<option value='Select'>Select<option>");
	         populateCurrency("currency");
			$('#currency').val("${currency_}").change();
		}else{
			$('#currency').val("Select").change();
			
		}
		
	}
	
	
	function citizenFun(){
		
		var RIRadioChecked = document.getElementById("RIRadio").checked;
		var NRIRadioChecked = document.getElementById("NRIRadio").checked;
		if(RIRadioChecked==true){
			document.getElementById('accountTypeDIV').style.display = 'none';
			
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
			
			$('#setCategory option').each(function(){
				if($(this).attr('citizen')=="RI"){
					$(this).hide();
				}
				if($(this).attr('citizen')=="NRI"){
					$(this).show();
				}
			});
		}
		if("${category}" != ""){
		$('#setCategory').val('${category}').change();
		var currency_ = "${currency_}";
        // changeCategory($("#setCategory"));
         $("#currency").html("<option value='Select'>Select<option>");
         populateCurrency("currency");
         $("#currency").val(currency_).change();
         if("${nriAccType}"!= ""){
          accountTypeVal("${nriAccType}");
          
 
          $("#nriAccountType").val("${nriAccType}");
         }
		}
		else{
			$('#setCategory').val('select').change();
		}
		
		


		/* if("${currency_}" == ""){
		document.getElementById('currency').value="Select";
		}
		else{
		$('#currency').val("${currency_}").change();
		}
		
		$('#nriAccountType').val("${nriAccType}").change();
		
		 accountTypeVal("${nriAccType}"); */
			
	}
	/* $(document).ready(function(){
		 citizenFun();
	}); */
	function nriAccount(value){
		
		//$("#addRateForm").attr("action", "addRates?nriAccType="+value);
		//$("#addRateForm").submit();
		
	}
	
	function resetCurrency(value) {
		accountTypeVal(value);
		if(value != "")
		$('#currency').val('Select').change();
		
	}
	
</script>


<style>
.errmsg {
	color: red;
}
</style>
