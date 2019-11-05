<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="req"
	value="${requestScope['javax.servlet.forward.request_uri']}" />
<c:set var="baseURL" value="${fn:split(req,'/')}" />
<script src="<%=request.getContextPath()%>/resources/js/loginDate.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-1.11.1.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui-1.11.1.js"></script>
		
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui.multidatespicker.js"></script>
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/prettify.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/lang-css.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/mdp.css">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/prettify.css">



<div class="right-container" id="right-container">
	<div class="container-fluid">

		


		<div class="Flexi_deposit">

			<div class="header_customer" style="margin-bottom: 30px;">
				<h3>
					
					Withdraw From Overdraft
				</h3>
			</div>
			
			<div class="flexi_table">
				<form:form class="form-horizontal" action="saveWithdrawOverdraft"  onsubmit="return validate();" id="withdrawOverdraft" method="post"
					  commandName="depositForm"  autocomplete="off">


					<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.fdAccountNo" /><span style="color: red">*</span></label>
						<div class="col-md-4">
							<form:input path="accountNumber" class="myform-control" id="accountNumber" 
								placeholder="Enter Account Number"  required="true" autocomplete="off"/>

						</div>
					</div>
					
					<div class="col-sm-12 col-md-12 col-lg-12"
							style="margin-left: 402px;">
							<input type="button" class="btn btn-primary"
								 value="Search" onClick="myFunction()">

						</div>
						
						<table  id="my-table" class="table table-striped table-bordered" style="width: 95%; margin-left: 23px;">
							<tr>
								<td><b><spring:message code="label.customerID" /></b></td>
								<td><b><spring:message code="label.customerName" /></b></td>
								<td><b><spring:message code="label.dateOfBirth" /></b></td>
								<td><b><spring:message code="label.email" /></b></td>
								<td><b><spring:message code="label.contactNumber" /></b></td>
								<td><b><spring:message code="label.address" /></b></td>
								<td><b><spring:message code="label.select" /></b></td>
							</tr>

							

						</table>
						
					
						<div class="form-group">
							<label class="col-md-4 control-label">Overdraft Number<span style="color: red">*</span></label>
							<div class="col-md-4">
								

<select name="overdraftNumber" onChange="getOverdraftDetails($(this));" class="myform-control" id="overdraftNumber"><option value="Select">Select</option></select>
							</div>
	
						</div>
						
						<div class="form-group">
						<label class="col-md-4 control-label">Current Balance<span style="color: red">*</span></label>
						<div class="col-md-4">
							<input class="myform-control" id="currentBalance"
								 autocomplete="off" readonly="true" />

						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label">Withdrawable Amount<span style="color: red">*</span></label>
						<div class="col-md-4">
							<form:input path="withdrawableAmount" class="myform-control" id="withdrawableAmount"
								placeholder=" "  autocomplete="off" readonly="true" />

						</div><span id="withdrwableAmountError" style="display: none; color: red;"><spring:message code="label.validation" /></span>
					</div>
					
					  <div class="form-group">
						<label class="col-md-4 control-label">Total Sanctioned Amount<span style="color: red">*</span></label>
						<div class="col-md-4">
							<input name="defaultOverdraftAmount" id="totalSanctionedAmount" class="form-control"
								placeholder="" autocomplete="off"    readonly="true"/>
							<span id="totalSanctionedAmountError" style="display: none; color: red;"><spring:message code="label.validation" /></span>
						</div>
					</div> 
					
					<div class="form-group">
						<label class="col-md-4 control-label">Total Sanctioned Percentage<span style="color: red">*</span></label>
						<div class="col-md-4">
							<input name="defaultOverdraftPercentage" id="totalSanctionedPercentage" class="form-control"
								placeholder="" autocomplete="off"    readonly="true"/>
							<span id="totalSanctionedAmountError" style="display: none; color: red;"><spring:message code="label.validation" /></span>
						</div>
					</div> 
					<div class="form-group">
							<label class="col-md-4 control-label">Withdraw Date<span style="color: red">*</span></label>
							<div class="col-md-4">
								<form:input path="createdDate" value="${todaysDate}"
									placeholder="Select Date" class="form-control"
									style="background: whitesmoke; cursor: pointer;"
									id="datepicker" required="true" readonly="true" />

							</div>

						</div>
					
					
					 <div class="form-group">
						<label class="col-md-4 control-label">Withdraw Amount<span style="color: red">*</span></label>
						<div class="col-md-4">
							<form:input path="withdrawAmount" id="withdrawAmount" class="form-control"
								placeholder="" />
						
						</div>
					</div>
					
					<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.modeOfPay" /><span style="color: red">*</span></label>
							<div class="col-md-4">
								<form:select id="fdPay" placeholder="Payment Mode"
									path="paymentMode" onchange="showDetails()"
									class="myform-control">
									<form:option value="Select">
										<spring:message code="label.select" />
									</form:option>
									<form:option value="Cash">
										<spring:message code="label.cashPayment" />
									</form:option>
									<form:option value="DD">
										<spring:message code="label.ddPayment" />
									</form:option>
									<form:option value="Cheque">
										<spring:message code="label.chequePayment" />
									</form:option>
									<%-- <form:option value="onLinePayment">
										Net Banking
									</form:option> --%>
									<form:option value="Card Payment">
								      Fund Transfer to linked Account
							</form:option>
								</form:select>
								 <span id="fdPayError"
									style="display: none; color: red;">Please select Mode of
									Payment</span>
							</div>
						</div>

						<div class="form-group" id="typeOfPayment"
							style="margin-top: 25px;">
							<div class="col-sm-3"></div>
							</div>
                  
                   <div class="col-md-offset-4 col-md-8"><span id="errorMsg" style="display: none; color: red;"><spring:message
									code="label.validation" /></span></div>
									<div class="col-sm-12 col-md-12 col-lg-12">
					<div class="errorMsg">
						<font color="red" style="text-align:center;width:100%;float:left;" id="validationError">${error}</font>
					</div>
				</div>
					
					 <div class="form-group">
						<div class="col-md-offset-4 col-md-8">
							<input type="submit" class="btn btn-info" id="goBtn" 
							
								value="Save" > 
								
						</div>
					</div> 
					
			
   
				</form:form>
			</div>

		</div>
		</div>
		</div>
		
		
<script>
		
function myFunction() {
	debugger;
	
	var accountNumber = document.getElementById('accountNumber').value;
	
	// data string
    var dataString = 'accountNumber=' + accountNumber;
	data='';
	 
	  $.ajax({  
	    type: "GET",  
	    url: "<%=request.getContextPath()%>/bnkEmp/getHolderDetails", 
	    contentType: "application/json",
	    dataType: "json",
	    data: dataString,
        async: false,
	    success: function(response){ 
	    	
	    	var customers=[];
	    	var drafts=[];
	    	var trHTML = '';
	    	var overdrafts='';
	    	for(i=0; i< response.length; i++){
		    	var data = response[i];
		    	if(!customers.includes(data.customerName))
		    	{
		    		customers.push(data.customerName);
		    	trHTML += '<tr><td>' + data.customerId  + '</td><td>' + data.customerName
				   + '</td><td>' +data.dateOfBirth + '</td><td>' +data.email + '</td><td>' +data.contactNum + '</td><td>' +data.address + '</td><td><input type="checkbox" id="myCheck" name="type"  /> </td></tr>'; 
		    	}
		    	
		    	if(!drafts.includes(data.overdraftNumber))
		    	{
		    		drafts.push(data.overdraftNumber);
		    		overdrafts += '<option value="'+data.overdraftNumber+'">'+data.overdraftNumber+'</option>'; 
		    	}
	    	}
	    	$('#overdraftNumber').append(overdrafts);
	    	$('#my-table').append(trHTML);
	    	document.getElementById('currentBalance').value= response[0].currentBalance;
	    	 
	    },  
	    error: function(e){ 
	    	alert("overdraft details not exist")
	    	$('#error').html("Error occured !!")
	    	 
	    }  
	  });  
	return data;
	}  




function showDetails() {
	
	var paymentMode = document.getElementById('fdPay').value;
	var selectRadio = false;
	if ($("input:checkbox:checked").length > 0)
	{
		selectRadio = true;
	}
	
	

	if (selectRadio == true && paymentMode != 'Select') 
		document.getElementById("fdPay").title = "Please select mode of payment";
	 	
}

function validate() {
	debugger;
	var submit=true;
	validationError.innerHTML='';
	var withdrawAmount = document.getElementById('withdrawAmount').value;
	
	if($("#withdrawAmount").val()==""){
		
		document.getElementById("withdrawAmount").style.borderColor = "red"
		errorMsg = "<br>withdrawAmount can not be empty";
		validationError.innerHTML += errorMsg;
		
		submit = false;
	}else {
		withdrawAmount = parseFloat(withdrawAmount);
		var withdrawableAmount = parseFloat(document.getElementById('withdrawableAmount').value);
		
		if (withdrawableAmount < withdrawAmount) {
			document.getElementById('withdrawAmount').style.borderColor = "red";
			errorMsg = "<br> Max limit withdraw amount is"
					+ withdrawableAmount + ".";
			validationError.innerHTML += errorMsg;

			submit = false;
		}
	}
 /* var withdrawAmount = document.getElementById('withdrawAmount').value;
if (withdrawAmount == "") {
	document.getElementById('withdrawAmount').style.borderColor = "red";
	errorMsg = "<br>Please enter withdraw amount";
	validationError.innerHTML += errorMsg;

	submit = false;

}    */
 
/* var withdrawAmount=document.getElementById('withdrawAmount').value;
var submit=true;
var errorMsg="";
var validationError=document.getElementById('validationError');
validationError.innerHTML="";
if ( withdrawAmount =="") {
		document.getElementById('withdrawAmount').style.borderColor = "red";
		errorMsg = "<br>Please enter withdrawAmount";
		validationError.innerHTML += errorMsg;
		
		submit = false;
	} else {
		withdrawAmount = parseFloat(withdrawAmount);
		var withdrawableAmount = document.getElementById('withdrawableAmount');
		
		if (withdrawableAmount < withdrawAmount) {
			document.getElementById('withdrawAmount').style.borderColor = "red";
			errorMsg = "<br> Max limit withdraw amount is"
					+ withdrawableAmount + ".";
			validationError.innerHTML += errorMsg;

			submit = false;
		}
        

	}
  */
var paymentMode = document.getElementById('fdPay').value;
if(paymentMode == 'Select'){
	//document.getElementById("fdPay").title = "Please select mode of payment";
	errorMsg = "<br>Please select mode of payment";
	validationError.innerHTML += errorMsg;
	submit=false;
}
var selectRadio = false;
if ($("input:checkbox:checked").length > 0)
{
	selectRadio = true;
}



if (selectRadio == true && paymentMode != 'Select') {
	document.getElementById("fdPay").title = "Please select mode of payment";
submit=false;
}
if(submit==false){
	return false;
}
}

	  
	
$(document).ready(function(){
	  
	  $('#withdrawAmount').bind('keypress', function (event) {
	        var regex = new RegExp("^[0-9 ]*$");
	        var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
	        if (!regex.test(key)) {
	            event.preventDefault();
	            return false;
	        }
	       
	        
	    });
	  
	  
	});
function getOverdraftDetails(obj){
	var draft=obj.val();
	
	
	 var dataString = 'overdraftNumber=' + draft;
		data='';
		 
		  $.ajax({  
		    type: "GET",  
		    url: "<%=request.getContextPath()%>/bnkEmp/getOverdraftIssueDetails", 
		    contentType: "application/json",
		    dataType: "json",
		    data: dataString,
	        async: false,
		    success: function(response){ 
		    	var data=response;
		    	//Withdraw Date
		    	//Cuttent Balance
		    	//Percent Sactioned 90
		    	//Withdrawable Amount 90,000
		    	//document.getElementById('overdraftNumber').value= data.overdraftNumber; 
		    	document.getElementById('totalSanctionedPercentage').value= data.sanctionedPercentage;
		    	document.getElementById('withdrawableAmount').value= data.withdrawableAmount.toFixed(2);
		    	document.getElementById('totalSanctionedAmount').value= data.sanctionedAmount.toFixed(2);
		    	 
		    },  
		    error: function(e){ 
		    	alert("overdraft details not exist")
		    	$('#error').html("Error occured !!")
		    	 
		    }  
		  });  
}

/* $(document).ready(function () {
    $(window).keydown(function(event){
        if(event.keyCode == 13) {
            e.preventDefault(); // Disable the " Entry " key
            return false;               
        }
    });
});

var form = document.getElementById("withdrawOverdraft");
form.addEventListener("submit",function(e){e.preventDefault(); return false;}); */



	
</script>
	