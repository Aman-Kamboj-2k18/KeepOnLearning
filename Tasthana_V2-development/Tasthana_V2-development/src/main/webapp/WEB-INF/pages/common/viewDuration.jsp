
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
					View Duration

				</h3>
			</div>
			<div class="Success_msg">
				<div class="successMsg" style="text-align: center; color: red;">
					${error}</div>
			</div>
			<div class="flexi_table">
				<form:form class="form-horizontal" autocomplete="off"
					commandName="addRateForm" name="addRate">
					
					
<!-- 					<div class="fd-list-table"> -->
					
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


							<div class="col-md-6" style="min-width: 120px !important; margin-left: 105px; margin-top: -45px;">
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
					

						<div class="col-md-6">
						<label class="col-md-6 control-label" style="padding-top: 14px; text-align: left;"><spring:message
								code="label.depositType" /><span style="color: red">*</span></label>


						<div class="col-md-4" style="margin-left: -135px; min-width: 200px;">
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

							<div class="form-group col-md-3"
								style="text-align: left; margin-top: 7px; margin-left: -210px;">
								<input type="button" onclick="getDurations()"
									class="btn btn-info" value="Go"
								style="text-align: center; padding-left: 20px; padding-right: 20px;">
							</div>
							
						<div style="clear: both; height: 25px;"></div>


						<table id="tableDuration" style="width: 95%; Margin-left: 20px;">

							<tbody>
								<tr>
									
									<th style="width: 30%;"><spring:message code="label.startDay" /></th>
									<th style="width: 30%;"><spring:message code="label.endDay" /></th>
									<th style="width: 40%;"><spring:message code="label.description" /></th>
									

								</tr>
							</tbody>
						</table>
						</form:form>
			
			</div>

		</div></div></div>


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

	

	function getDurations(){
		
		var customerCategory = document.getElementById('setCategory').value;	
		var depositClassification = document.getElementById('depositClassification').value;
		var RIRadioChecked = document.getElementById("RIRadio").checked;
		var NRIRadioChecked = document.getElementById("NRIRadio").checked;
		
		var nriAccountType = "";
		var citizen = "RI";
		if(RIRadioChecked==true)
		{
			var citizen = "RI";
			nriAccountType = ""
		}
		else
		{
			citizen = "NRI";	
			nriAccountType = document.getElementById('nriAccountType').value;
		}
		
		
		// data string
	    var dataString = 'citizen='+ citizen
	                    + '&nriAccountType=' + nriAccountType        
	                    + '&customerCategory=' + customerCategory
	                    + '&depositClassification=' + depositClassification;
		

		// delete all rows except the first row from the table
		$("#tableDuration").find("tr:gt(0)").remove();

		
		  $.ajax({  
		    type: "GET",  
		    url: "<%=request.getContextPath()%>/common/getDurations", 
		    contentType: "application/json",
		    dataType: "json",
		    data: dataString,  
	        async: false,
		    success: function(response){  
			
		    	debugger;
		    	var trHTML = '';
				for (i = 0; i < response.length; ++i) {
				   var data = response[i];
				   trHTML += '<tr id="myTableRow"><td>' + data.fromDay + '</td><td>' + data.toDay  + '</td><td>' + data.description+ '</td></tr>';
				}
				$('#tableDuration').append(trHTML);
		
		        
		    },

		    error: function(e){  
		    	console.log(e)
		    	
		    	 $('#error').html("Error occured!!")
		    	 document.getElementById('emiAmt').value=0;
		    }  
		  });  

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
		debugger;
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
		$('#setCategory').val('select').change();
	}
	$(document).ready(function(){
		 citizenFun();
	});
</script>