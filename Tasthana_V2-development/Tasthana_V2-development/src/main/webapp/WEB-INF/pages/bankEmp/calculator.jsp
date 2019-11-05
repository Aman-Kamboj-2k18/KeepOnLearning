
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
					<spring:message code="label.earnOneCrore" />

				</h3>
			</div>

			<div class="Success_msg">
				<div class="successMsg" style="text-align: center; color: #red;">
					${error}</div>
			</div>



			<div class="flexi_table" style="margin-top: 7px;">
				<form:form action="resultOfCalculation" class="form-horizontal"
					autocomplete="off" commandName="interestCalculationForm"
					name="resultOfCalculation">
					<div
						style="margin: 7px 7px 0 7px; padding: 1px 3px 1px 10px; font-size: 0.75em; background-color: #1aa0de; color: #fff;">
						<h4 style="font-size: 1.75em; color: #fff;">
							<spring:message code="label.interestAndMaturityAmount" />

						</h4>
					</div>
					<div class="fd-list-table">
						<div class="col-md-3" style="margin-left: -15px;" id="principalAmountDiv">
							<div class="form-group">
								<label class="col-md-4 control-label" style="padding-top: 5px;"><spring:message
										code="label.principalAmount" /><span style="color: red">*</span></label>
								<div class="col-md-8" style="min-width: 165px; margin-left: -5px;">
									<form:input path="principalAmount" id="principalAmount"
										placeholder="Principal Amount" class="myform-control" />&nbsp;<span class="errmsg" id="errmsgPA"></span>
								</div>
							</div>
						</div>

						<div class="col-md-3" style="margin-left: 15px;" id="tenureDiv">
							<div class="form-group">
								<label class="col-md-6 control-label" style="padding-top: 16px; margin-left: -15px;"><spring:message
										code="label.fdTenure" /><span style="color: red">*</span></label>
								<div class="col-md-6" style="margin-left: -15px;">
									<form:input path="tenure" type="number" id="tenure"
										placeholder="Tenure" class="myform-control" />&nbsp;<span class="errmsg" id="errmsgTenure"></span>
								</div>
							</div>
						</div>

						<div class="col-md-3" style="margin-left: 15px;"
							id="tenureTypeDiv">
							
							<label class="col-md-5 control-label" style="padding-top: 6px;"><spring:message
									code="label.typeOfTenure" /></label>
							
							<div class="col-md-7" style="width: 125px;">
								<form:select path="tenureType" id="tenureType"
									class="myform-control">
									<form:option value="">
										<spring:message code="label.select" />
									</form:option>
									<form:option value="Month">
										<spring:message code="label.Month" />
									</form:option>
									<form:option value="Year">
										<spring:message code="label.Year" />
									</form:option>

								</form:select>
							</div>
						</div>

						<div class="col-md-3" style="margin-left: -45px;" id="roiDiv">
							<label class="col-md-6 control-label" style="padding-top: 16px;">R.O.I. (%)</label>
							<div class="col-md-6" style="min-width: 120px; margin-left: -15px;">
								<form:select path="rateOfInterest" id="rateOfInterest"
									class="myform-control">
									<form:option value="">
										<spring:message code="label.select" />
									</form:option>
									<form:option value="3.0">
										3.0
									</form:option>
									<form:option value="3.25">
										3.25
									</form:option>
									<form:option value="3.5">
										3.5
									</form:option>
									<form:option value="3.75">
										3.75
									</form:option>
									<form:option value="4.0">
										4.0
									</form:option>
									<form:option value="4.25">
										4.25
									</form:option>
									<form:option value="4.50">
										4.5
									</form:option>
									<form:option value="4.75">
										4.75
									</form:option>
									<form:option value="5.0">
										5.0
									</form:option>
									<form:option value="5.25">
										5.25
									</form:option>

									<form:option value="5.50">
										5.5
									</form:option>

									<form:option value="5.75">
										5.75
									</form:option>
									<form:option value="6.0">
										6.0
									</form:option>
									<form:option value="6.25">
										6.25
									</form:option>
									<form:option value="6.50">
										6.5
									</form:option>
									<form:option value="6.75">
										6.75
									</form:option>
									<form:option value="7.0">
										7.0
									</form:option>
									<form:option value="7.25">
										7.25
									</form:option>
									<form:option value="7.50">
										7.5
									</form:option>
									<form:option value="7.75">
										7.75
									</form:option>
									<form:option value="8.0">
										8.0
									</form:option>
									<form:option value="8.25">
										8.25
									</form:option>
									<form:option value="8.50">
										8.5
									</form:option>
									<form:option value="8.75">
										8.75
									</form:option>
									<form:option value="9.0">
										9.0
									</form:option>
									<form:option value="9.25">
										9.25
									</form:option>

									<form:option value="9.50">
										9.5
									</form:option>
									<form:option value="9.75">
										9.75
									</form:option>
									<form:option value="10.0">
										10.0
									</form:option>
								</form:select>
							</div>
						</div>
						<div style="clear:both;"></div>
						<div class="col-md-5" style="margin-left: -10px;" id="roiDiv">

							<div class="form-group" id="buttonDiv">


								<div class="form-group col-md-10"
									style="text-align: center; margin-top: 3px;">
									<input type="button" onclick="getInterestAmoun()"
										class="btn btn-info" value="Calculate Interest">
								</div>



							</div>
						</div>


						<div class="col-md-7"
							style="margin-left: -75px; text-align: -webkit-left;"
							id="interestDiv">
							<div class="form-group">
								<label class="col-md-6 control-label"
									style="padding-top: 16px; text-align: -webkit-left;"><spring:message
										code="label.maturityAmount" /><span style="color: red">*</span></label>
								<div class="col-md-6"
									style="text-align: -webkit-left; margin-left: -150px;">
									<form:input path="interest" readonly="true"
										id="interest" placeholder="Calculated Interest"
										class="myform-control" style="width: 150px;" />
								</div>
							</div>
						</div>
                        <input type="button" onclick="resetAmount()"
							class="btn btn-warning pull-right" value="Reset"
							style="margin-right: 9px; margin-bottom: 33px;" />
						
					</div>
					<div style="clear: both; height: 3px;"></div>




					<div
						style="margin: 7px 7px 0 7px; padding: 1px 3px 1px 10px; font-size: 0.75em; background-color: #1aa0de; color: #fff;">
						<h4 style="font-size: 1.75em; color: #fff;">
							<spring:message code="label.calculateTenure" />

						</h4>
					</div>
					<div class="fd-list-table">
						<div class="col-md-3" style="margin-left: -25px;"
							id="principalAmountDiv">
							<div class="form-group">
								<label class="col-md-6 control-label" style="padding-top: 16px;"><spring:message
										code="label.principalAmount" /><span style="color: red">*</span></label>
								<div class="col-md-6">
									<form:input path="principalAmountTenure"
										id="principalAmountTenure" placeholder="Principal Amount"
										class="myform-control" />&nbsp;<span class="errmsg" id="errmsgPAT"></span>
								</div>
							</div>
						</div>
								
						<div class="col-md-3"style="margin-left: 5px;"
							id="interestDiv">
							<div class="form-group">
								<label class="col-md-6 control-label"
									style="padding-top: 16px; text-align: -webkit-left;"><spring:message
										code="label.maturityAmount" /><span style="color: red">*</span></label>
								<div class="col-md-6"
									style="text-align: -webkit-left; margin-left: -40px;">
									<form:input path="interestTenure"  placeholder="Enter Amount" id="interestTenure"
									 class="myform-control"
										style="width: 150px;" />&nbsp;<span class = "errmsg" id="errmsgIT"></span>
								</div>
							</div>
						</div>

					

						<div class="col-md-3" id="roiDiv">
							<label class="col-md-6 control-label" style="padding-top: 16px;">R.O.I. (%)</label>
							<div class="col-md-6">
								<form:select path="rateOfInterestTenure"
									id="rateOfInterestTenure" class="myform-control">
									<form:option value="">
										<spring:message code="label.select" />
									</form:option>
									<form:option value="3.0">
										3.0
									</form:option>
									<form:option value="3.25">
										3.25
									</form:option>
									<form:option value="3.5">
										3.5
									</form:option>
									<form:option value="3.75">
										3.75
									</form:option>
									<form:option value="4.0">
										4.0
									</form:option>
									<form:option value="4.25">
										4.25
									</form:option>
									<form:option value="4.50">
										4.5
									</form:option>
									<form:option value="4.75">
										4.75
									</form:option>
									<form:option value="5.0">
										5.0
									</form:option>
									<form:option value="5.25">
										5.25
									</form:option>

									<form:option value="5.50">
										5.5
									</form:option>

									<form:option value="5.75">
										5.75
									</form:option>
									<form:option value="6.0">
										6.0
									</form:option>
									<form:option value="6.25">
										6.25
									</form:option>
									<form:option value="6.50">
										6.5
									</form:option>
									<form:option value="6.75">
										6.75
									</form:option>
									<form:option value="7.0">
										7.0
									</form:option>
									<form:option value="7.25">
										7.25
									</form:option>
									<form:option value="7.50">
										7.5
									</form:option>
									<form:option value="7.75">
										7.75
									</form:option>
									<form:option value="8.0">
										8.0
									</form:option>
									<form:option value="8.25">
										8.25
									</form:option>
									<form:option value="8.50">
										8.5
									</form:option>
									<form:option value="8.75">
										8.75
									</form:option>
									<form:option value="9.0">
										9.0
									</form:option>
									<form:option value="9.25">
										9.25
									</form:option>

									<form:option value="9.50">
										9.5
									</form:option>
									<form:option value="9.75">
										9.75
									</form:option>
									<form:option value="10.0">
										10.0
									</form:option>
								</form:select>
							</div>
						</div>

<div class="col-md-3" style="margin-left: 15px;"
							id="tenureTypeDiv1">
							
							<label class="col-md-5 control-label" style="padding-top: 6px;"><spring:message
									code="label.typeOfTenure" /></label>
							
							<div class="col-md-7" style="width: 125px;">
								<form:select path="tenureType" id="tenureType1"
									class="myform-control">
									<form:option value="Day(s)">
										<spring:message code="label.Day" />
									</form:option>
									
									<form:option value="Month(s)">
										<spring:message code="label.Month" />
									</form:option>
									<form:option value="Year(s)">
										<spring:message code="label.Year" />
									</form:option>

								</form:select>
							</div>
						</div>


						<div class="col-md-5" style="margin-left: -10px;" id="roiDiv">

							<div class="form-group" id="buttonDiv">


								<div class="form-group col-md-10"
									style="text-align: center; margin-top: 3px;">
									<input type="button" onclick="getTenure()" class="btn btn-info"
										value="Calculate Tenure">
								</div>



							</div>
						</div>


						
                       <div class="col-md-6" style="margin-left: -35px;" id="tenureTenureDiv">
							<div class="form-group">
								<label class="col-md-5 control-label" style="padding-top: 16px;"><spring:message
										code="label.fdTenure" /><span style="color: red">*</span></label>
								<div class="col-md-4">
									<form:input path="tenureTenure" readonly="true" type="number" id="tenureTenure"
										placeholder="Tenure" class="myform-control" />
										
								</div>
								<div class="col-md-3"><p style="padding-top:15px;" id="lblType"></p></div>
								
							</div>
						</div>
						<input type="button" onclick="resetTenure()"
							class="btn btn-warning pull-right" value="Reset"
							style="margin-right: 9px; margin-bottom: 33px;" />
					</div>
					<div style="clear: both; height: 3px;"></div>


					<div style="clear: both; height: 3px; margin-bottom: 25px;"></div>
				</form:form>
			</div>



		</div>

	</div>



</div>
<script>
	$(document).ready(function(){
		
		
		
		$("#principalAmount").keypress(function (e) {
		     //if the letter is not digit then display error and don't type anything
		     if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
		        //display error message
		        $("#errmsgPA").html("Digits Only").show().fadeOut("slow");
		               return false;
		    }
		   });
		
		
		$("#tenure").keypress(function (e) {
		     //if the letter is not digit then display error and don't type anything
		     if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
		        //display error message
		        $("#errmsgTenure").html("Digits Only").show().fadeOut("slow");
		               return false;
		    }
		   });
		
		
		$("#interestTenure").keypress(function (e) {
		     //if the letter is not digit then display error and don't type anything
		     if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
		        //display error message
		        $("#errmsgIT").html("Digits Only").show().fadeOut("slow");
		               return false;
		    }
		   });
		
		
		$("#principalAmountTenure").keypress(function (e) {
		     //if the letter is not digit then display error and don't type anything
		     if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
		        //display error message
		        $("#errmsgPAT").html("Digits Only").show().fadeOut("slow");
		               return false;
		    }
		   });
		
		 document.getElementById('interestDiv').style.display = 'none';
		 document.getElementById('tenureTenureDiv').style.display = 'none';
});
	
	
	function getInterestAmoun(){
		 var principalAmount= document.getElementById('principalAmount').value;
		 var tenure= document.getElementById('tenure').value;
		 var tenureType= document.getElementById('tenureType').value;
		 var rateOfInterest= document.getElementById('rateOfInterest').value;
		
			if(principalAmount==""){
				document.getElementById('principalAmount').style.borderColor = "red";
				submit = false;
				return submit;
			}
			else{
				document.getElementById('principalAmount').style.borderColor = "black";
				
			}
		
			if(tenure==""){
				document.getElementById('tenure').style.borderColor = "red";
				submit = false;
				return submit;
			}
			else{
				
				document.getElementById('tenure').style.borderColor = "black";

			}
			
			if(tenureType==""){
				document.getElementById('tenureType').style.borderColor = "red";
				submit = false;
				return submit;
			}
			else{
				
				document.getElementById('tenureType').style.borderColor = "black";

			}
			
			if(rateOfInterest==""){
				document.getElementById('rateOfInterest').style.borderColor = "red";
				submit = false;
				return submit;
			}
			else{
				
				document.getElementById('rateOfInterest').style.borderColor = "black";

			}
				
			
			    var dataString = 'principalAmount='+ principalAmount
	            + '&tenure=' + tenure + '&tenureType=' + tenureType + '&rateOfInterest=' + rateOfInterest;
		  
		 	
		  var tenure = 0;
		  $.ajax({  
		    type: "GET", 
		    async: false,
		    url: "<%=request.getContextPath()%>/bnkEmp/interestCalculation", 
		    contentType: "application/json",
		    dataType: "json",
		    data: dataString,
	    
		    success: function(response){  
		    	    interestAmountGround = response;
			    				    	document.getElementById('interest').value = interestAmountGround;
			    	

		    },  
		    error: function(e){  
		    	 $('#error').html("Error occured!!")
		    	document.getElementById('interest').value=0;
		    }  
		  });  
			  
		  if(interestAmountGround!=""){
				document.getElementById('interestDiv').style.display = 'block';

		  }
			return interestAmountGround;
			}  
	
	function getTenure(){
		
		 var principalAmount= document.getElementById('principalAmountTenure').value;
		 var interestTenure= document.getElementById('interestTenure').value;
		 var rateOfInterest= document.getElementById('rateOfInterestTenure').value;
		 
			if(principalAmount==""){
				document.getElementById('principalAmountTenure').style.borderColor = "red";
				submit = false;
				return submit;
			}
			else{
				document.getElementById('principalAmountTenure').style.borderColor = "black";
				
			}
		
			if(interestTenure==""){
				document.getElementById('interestTenure').style.borderColor = "red";
				submit = false;
				return submit;
			}
			else{
				
				document.getElementById('interestTenure').style.borderColor = "black";

			}
			
		
			if(rateOfInterest==""){
				document.getElementById('rateOfInterestTenure').style.borderColor = "red";
				submit = false;
				return submit;
			}
			else{
				
				document.getElementById('rateOfInterestTenure').style.borderColor = "black";

			}
				
			
			    var dataString = 'principalAmount='+ principalAmount
	            + '&interestTenure=' + interestTenure + '&rateOfInterest=' + rateOfInterest;
		  
		 	
		  var tenure = 0;
		  $.ajax({  
		    type: "GET", 
		    async: false,
		    url: "<%=request.getContextPath()%>/bnkEmp/tenureCalculation", 
		    contentType: "application/json",
		    dataType: "json",
		    data: dataString,
	    
		    success: function(response){  
		    	   finalTenureInt = response;
		    	   debugger;
		    	   var type=$('#tenureType1').val();
		    	   if(type=="Month(s)"){
		    		   finalTenureInt=finalTenureInt/30; 
		    	   }
		    	   if(type=="Year(s)"){
		    		   finalTenureInt=finalTenureInt/365;
		    	   }
		    	   finalTenureInt= Math.round(finalTenureInt*100)/100;
		    	   $('#lblType').html(type);

			    	document.getElementById('tenureTenure').value = finalTenureInt;

		    },  
		    error: function(e){  
		    	 $('#error').html("Error occured!!")
		    	document.getElementById('tenureTenure').value=0;
		    }  
		  });  
	
		  document.getElementById('tenureTenureDiv').style.display = 'block';
			return finalTenureInt;
	}
	
	function resetAmount(){
		document.getElementById('principalAmount').value = "" ;
		document.getElementById('tenure').value = "" ;
		document.getElementById('tenureType').value = "" ;
		document.getElementById('rateOfInterest').value = "" ;
		document.getElementById('interest').value = "" ;

	}
	
	
	function resetTenure(){
		document.getElementById('principalAmountTenure').value = "" ;
		document.getElementById('interestTenure').value = "" ;
		document.getElementById('rateOfInterestTenure').value = "" ;
		document.getElementById('tenureTenure').value = "" ;
		
	}
	
</script>

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

.errmsg
{
color: red;
}
</style>
<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
