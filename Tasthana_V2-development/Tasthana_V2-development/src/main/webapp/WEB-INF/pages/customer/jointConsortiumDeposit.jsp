<!DOCTYPE html>
<html lang="en">
<head>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- To fetch the request url -->
<c:set var="req"
	value="${requestScope['javax.servlet.forward.request_uri']}" />
<c:set var="baseURL" value="${fn:split(req,'/')}" />

<link href="<%=request.getContextPath()%>/resources/css/Validation.css"
	rel="stylesheet">
<script
	src="<%=request.getContextPath()%>/resources/js/ccvalidate-tapan.min.js"></script>

<meta charset="UTF-8">
<title>Document</title>
</head>



<style>
input[type="radio"] {
	position: relative;
	top: 2px;
}

.buttons {
	text-align: center;
	margin-top: 52px;
}

.table-1 {
	display: inline-block;
	float: left;
}

.form-horizontal .control-label {
	padding-top: 0;
}

.secure-font {
	-webkit-text-security: disc;
}
</style>

<script>
$( document ).ready(function() {
   if('${role.role}' == 'ROLE_USER'){
	   $("#branchDIV").hide();
	   $("#areaDIV").hide();
	   
   }
});
var today = null;
function getLoginDate()
{

	$.ajax({  
	    type: "GET",  
	    async: false,
	    url: "<%=request.getContextPath()%>/common/loginDateForJsp", 
	    contentType: "application/json",
	    dataType: "json",

	    success: function(response){  
	    //	window.loginDateForFront = new Date(parseInt(response));
	    	today = new Date(parseInt(response))
	    },  
	    error: function(e){  
	    	 $('#error').html("Error occured!!")
	    	 // window.loginDateForFront = getTodaysDate();
	    }  
	  });  
	return today;
}


function validationAccount(event){
	
	var minimumBalance1_ = document.getElementById(event.target.id);

		 var keycode = event.which;
	    if (!(event.shiftKey == false && (keycode == 8 || keycode == 37 ||  (keycode >= 48 && keycode <= 57)))) {
	        event.preventDefault();
	        return false;
	    } 
	   
	    
	    if (minimumBalance1_.value.length>10 && event.keyCode != 8){
	    	 event.preventDefault();
	    	return false;
	    }
	
	
	 
	}
	
	
	
	
function validationAadhar(event){
	
	var aadhar_ = document.getElementById(event.target.id);

		 var keycode = event.which;
	    if (!(event.shiftKey == false && (keycode == 8 || keycode == 37 ||  (keycode >= 48 && keycode <= 57)))) {
	        event.preventDefault();
	        return false;
	    } 
	   
	    
	    if (aadhar_.value.length>11 && event.keyCode != 8){
	    	 event.preventDefault();
	    	return false;
	    }
	
	
	 
	}
	
	
function validName(event){
	
	var valiName = document.getElementById(event.target.id);
	var regex = new RegExp("^[a-zA-Z0-9 ]+$");
	 var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
	
	if(valiName.value.length==0 && event.keyCode ==32)
		{
		  event.preventDefault();
		return false;
		}
	 if (!regex.test(key)) {
	    	//alert("false")
	        event.preventDefault();
	        return false;
	   }
	 }
	 
function validate() {
	var canSubmit=true;	
    var	maxFdAmount = 100000000000;
	var fdAmount = parseInt(document.getElementById('fdAmount').value);
	var branch="";
	if($('#branchCode').length>0){
	 branch = document.getElementById('branchCode').value;
	}
	//var fdTenureType = document.getElementById('fdTenureType').value;
	var fdTenure=0;
	var fdTenureYears1 = document.getElementById('fdTenureYears').value;
	document.getElementById('tenureInYears').value = fdTenureYears1;
	
	var fdTenureMonths1 = document.getElementById('fdTenureMonths').value;
	document.getElementById('tenureInMonths').value = fdTenureMonths1;
	var fdTenureDays1 = document.getElementById('fdTenureDays').value;
	document.getElementById('tenureInDays').value=fdTenureDays1;
	today = getLoginDate();
	var year  = today.getFullYear();
	var month = today.getMonth();
	var day = today.getDate();
	if(fdTenureYears1!=""){
		var newDate = new Date(year+parseInt(fdTenureYears1),month,day);
		var _days = (newDate - today)/(1000*60*60*24);

		fdTenure+= parseInt(_days);
	}
	if(fdTenureMonths1!=""){
		var newMonthDate = new Date(year,month+parseInt(fdTenureMonths1),day);
		fdTenure+= parseInt((newMonthDate - today)/(1000*60*60*24));
	}
	if(fdTenureDays1!=""){
		fdTenure+= parseInt(fdTenureDays1);
	}
	
	
	document.getElementById('fdTenure').value=fdTenure;
	var paymentType = document.getElementById('paymentType').value;
	var transferType = document.getElementById('transferType').value;
	var accountNo = document.getElementById('accountNo').value;
	var deductionDay="";
	if('${productConfiguration.productType}'!='Regular Deposit' && '${productConfiguration.productType}'!= 'Tax Saving Deposit'){
    deductionDay= document.getElementById('deductionDay').value;
	}
   // var dayValue= parseInt(document.getElementById('fdDayValue').value);
	var maturityInstruction= document.getElementById('maturityInstructionId').value;
	var accountBalance_= 0;
	if(document.getElementById('accountBalance1') != null && document.getElementById('accountBalance1') != '')
		var accountBalance_ = document.getElementById('accountBalance1').value;

	document.getElementById("paymentOk").style.display='none';
	document.getElementById("paymentCross").style.display='none';
	document.getElementById("payoffOk").style.display='none';
	document.getElementById("payoffCross").style.display='none';
	
	document.getElementById("maturityOk").style.display='none';
	document.getElementById("maturityCross").style.display='none';
	
	
	document.getElementById("contributionOk").style.display='none';
	document.getElementById("contributionCross").style.display='none';
	document.getElementById("nomineeOk").style.display='none';
	document.getElementById("nomineeCross").style.display='none';
	document.getElementById("depositOk").style.display='none';
	document.getElementById("depositCross").style.display='none';
	document.getElementById("accountHolderOk").style.display='none';
	document.getElementById("accountHolderCross").style.display='none';

	var inp = document.querySelectorAll("#primaryDetails .input");
	for(i=0;i<inp.length;i++){
		inp[i].style.borderColor = "#cccccc";
	}


	var validationError=document.getElementById('validationError');
	validationError.innerHTML="";
	var errorMsg = "";
	
      if(paymentType==""){
		
		document.getElementById('paymentType').style.borderColor = "red";
		errorMsg = "Please Enter payment type";
		validationError.innerHTML += errorMsg;
		document.getElementById('depositCross').style.display='block';
		canSubmit= false;
	}
     
      if(maturityInstruction=="select"){
  		document.getElementById('maturityInstructionId').style.borderColor = "red";
  		errorMsg = "<br>Please Select Maturity Instruction";
  		validationError.innerHTML += errorMsg;
  		document.getElementById('depositCross').style.display='block';
  		canSubmit = false;
  	}
  	else{
  		document.getElementById('maturityInstructionId').style.borderColor = "black";
  		
  	}
  	
	if(fdAmount==0 || isNaN(fdAmount)){
		
		document.getElementById('fdAmount').style.borderColor = "red";
		errorMsg =" <br>Please Enter deposit amount";
		validationError.innerHTML += errorMsg;
		document.getElementById('depositCross').style.display='block';
		canSubmit= false;
	}
	
	if(canSubmit==false){
		return false;	
		}

	var minDepositAmount= parseInt('${productConfiguration.minimumDepositAmount}');
	
	var maxDepositAmount = parseInt('${productConfiguration.maximumDepositAmount}');
	
	if(minDepositAmount>fdAmount){
		document.getElementById('fdAmount').style.borderColor = "red";
		errorMsg =" <br>Please enter minimum deposit amount"+minDepositAmount+".";
		validationError.innerHTML += errorMsg;
		document.getElementById('depositCross').style.display='block';
		canSubmit= false;
	}
	if(fdTenureYears1=="" && fdTenureMonths1=="" && fdTenureDays1==""){
		errorMsg = "<br>Please Select atleast one tenure type";
		validationError.innerHTML += errorMsg;

		canSubmit = false;
	}
	
	if (maxDepositAmount < fdAmount) {
		document.getElementById('fdAmount').style.borderColor = "red";
		errorMsg = "<br>Please don't  enter higher than Maximum deposit amount "
				+ maxDepositAmount + ".";
		validationError.innerHTML += errorMsg;

		canSubmit = false;
	}
	debugger;
	if(branch != ""  && '${role.role}' != 'ROLE_USER'){
	if (branch == "select") {
		document.getElementById('branchCode').style.borderColor = "red";
		errorMsg = "<br>Please Select Branch Code";
		validationError.innerHTML += errorMsg;
		document.getElementById('depositCross').style.display = 'block';
		canSubmit = false;
	} else {
		document.getElementById('branchCode').style.borderColor = "black";

	} 
	}
	
	if($("#depositArea").val() == "" && '${role.role}' != 'ROLE_USER'){
		document.getElementById("depositArea").style.borderColor = "red"
		errorMsg = "<br>Area can not be empty";
		validationError.innerHTML += errorMsg;
		
		canSubmit = false;
	}
	if(fdAmount>maxFdAmount){
		document.getElementById('fdAmount').style.borderColor = "red";
		errorMsg = "<br>Maximum limit of deposit is less than Thousand crore.";
		validationError.innerHTML += errorMsg;
		canSubmit = false;
	}
	
	
	var amount = 0;
	$('*[id*=interestPayAmount]').each(function() {
		var doubleAmount = parseFloat($(this).val());
	   amount +=doubleAmount;
	});

	if(canSubmit==false){
		return false;	
		}		
	
	
	var depositAmountAndAmount = $("#fdAmount").val();
	
	if(amount > parseFloat(depositAmountAndAmount) ){
		
		$('[name="interestPayAmount"]').css("border-color","red");
		document.getElementById('fdAmount').style.borderColor = "red";
		errorMsg = "<br>Payoff amount should not more than deposit amount.";
		validationError.innerHTML += errorMsg;
		canSubmit = false;
		
		
	}
	
	
	
	if(canSubmit==false){
		return false;	
		}
	

	
	 var paymentMode_ = $('option:selected', $('#paymentMode')).attr('mode');
	//alert(paymentMode_);
	if(paymentMode_ == "Net Banking" || paymentMode_ == "Debit Card" ||  paymentMode_ == "Credit Card" || paymentMode_ == "Cash"  || paymentMode_ ==  'Cheque' || paymentMode_ ==  'DD'){
	
		
		
}else{
	
	if(paymentMode_ == 'Fund Transfer'){
	if (parseFloat(fdAmount) > parseFloat(accountBalance_)) {
		document.getElementById('fdAmount').style.borderColor = "red";
		errorMsg = "<br>Insufficent fund ";
		validationError.innerHTML += errorMsg;
		canSubmit = false;
	}
	}
	
}
	if(paymentType!='One-Time' && deductionDay == ""){
		   document.getElementById("deductionDay").style.borderColor = "red";
		    errorMsg ="<br>Please enter deduction day";
		      validationError.innerHTML += errorMsg;
		          document.getElementById('depositCross').style.display='block';
		          canSubmit= false;
		  }
	
    /*  if(fdTenureType==""){
		
		document.getElementById('fdTenureType').style.borderColor = "red";
		errorMsg =" <br>Please Enter tenure type";
		validationError.innerHTML += errorMsg;
		document.getElementById('depositCross').style.display='block';
		canSubmit= false;
	} */
	/* if(fdTenure==0 || isNaN(fdAmount)){
		
		document.getElementById('fdTenure').style.borderColor = "red";
		errorMsg =" <br>Please Enter tenure</br>";
		validationError.innerHTML += errorMsg;
			document.getElementById('depositCross').style.display='block';
			canSubmit= false;
	} */
	if(canSubmit==false){
		return false;	
		}

	
	//allowed secondary contributor to have 0 contribution

	/* if(contributionJoint < 0 || contributionJoint >= 100 || isNaN(contributionJoint)){
		
		$("#"+contributionJointId).attr("borderColor","red");
						//document.getElementById(contributionJointId).style.borderColor = "red"
						errorMsg ="<br> ";
						validationError.innerHTML += errorMsg;
						document.getElementById('contributionCross').style.display='block';
						canSubmit= false;
					} */



	/////////

	//allor primary contribution to be 100
		/* if(userContribution > 100){
				document.getElementById("primaryCont").style.borderColor = "red"
				document.getElementById("primaryContributionError").innerHTML="Please enter a value less than 100";
				return
			}
 */
	/* if(currentVal > 100){
				document.getElementById("contribution["+index+"]").style.borderColor = "red"
				document.getElementById("contributionError["+index+"]").innerHTML="Please enter a value less than 100";
				return
			} */

			var primaryCont = parseFloat(document.getElementById("primaryCont").value);

	//check wether contribution is greater than 0 and more more than 100
		if(primaryCont<0 || primaryCont>100 || isNaN(primaryCont)){
			document.getElementById("primaryCont").style.borderColor = "red"
			document.getElementById("primaryContributionError").innerHTML="Please enter a value greater than 0";
			errorMsg ="Incorrect contribution for primary account holder";
			validationError.innerHTML += errorMsg;
			
			document.getElementById('contributionCross').style.display='block';
			canSubmit= false;
		}
		if(primaryCont > 100){
			document.getElementById("primaryCont").style.borderColor = "red"
			document.getElementById("primaryContributionError").innerHTML="Please enter a value not greater than 0";
			errorMsg ="Incorrect contribution for primary account holder";
			validationError.innerHTML += errorMsg;
			
			document.getElementById('contributionCross').style.display='block';
			canSubmit= false;
		}

	
	/* if(dayValue<0){
		document.getElementById('dayValue').style.borderColor = "red";
		errorMsg = "<br>Entered  Days value can not be negative";
		validationError.innerHTML += errorMsg;
		
		canSubmit= false;
	}
	 */
	/*if (fdTenureType == "") {

		document.getElementById('fdTenureType').style.borderColor = "red";
		errorMsg = "<br>Please enter tenure type";
		validationError.innerHTML += errorMsg;

		canSubmit = false;
	}  else if (fdTenureType == 'Year') {
		if (parseInt(fdTenure) >= 10) {
			document.getElementById('fdTenureType').style.borderColor = "red";
			errorMsg = "<br>Please enter correct tenure";
			validationError.innerHTML += errorMsg;

			canSubmit = false;
		}

	} else if (fdTenureType == 'Day') {*/
		var minimumDays;
		var miniumMonths;
		var minimumYears;
		var totalMinimumTenure = 0;
		var productConfMiniumTenure = "${productConfiguration.minimumTenure}";
		var splitMinTenure = productConfMiniumTenure.split(",");
		for(var i = 0; i < splitMinTenure.length; i++) {
			var daysOrMonthOrYearVal = splitMinTenure[i];
			if(daysOrMonthOrYearVal.includes("Day")){
				var  splitDaysVal = daysOrMonthOrYearVal.split(" ");
					minimumDays = splitDaysVal[0];
			}else if(daysOrMonthOrYearVal.includes("Month")){
				var  splitMonthVal = daysOrMonthOrYearVal.split(" ");
				miniumMonths = splitMonthVal[0];
			}else if(daysOrMonthOrYearVal.includes("Year")){
				var  splitYearVal = daysOrMonthOrYearVal.split(" ");
				minimumYears = splitYearVal[0];
			}
			
		}
		 if(minimumDays != undefined){
			 totalMinimumTenure += parseInt(minimumDays);
			 }
		 if(miniumMonths != undefined){ 
			 var newMonthDateMinTenure = new Date(year,month+parseInt(miniumMonths),day);
			 totalMinimumTenure += parseInt((newMonthDateMinTenure - today)/(1000*60*60*24));
		 
		 }
		 if(minimumYears != undefined){ 
			 var newDateForMinTenure = new Date(year+parseInt(minimumYears),month,day);
				var _daysMinTenure = (newDateForMinTenure - today)/(1000*60*60*24);
			 totalMinimumTenure += parseInt(_daysMinTenure);
			 }
		
		 productConfMiniumTenure.replace(/,/g, ',');
		if (parseInt(fdTenure) < parseInt(totalMinimumTenure)) {
			document.getElementById('fdTenure').style.borderColor = "red";
			errorMsg = "<br>Minimum tenure for this deposit is "+ (productConfMiniumTenure.replace(/,/g, ' '))+".";
			validationError.innerHTML += errorMsg;
			canSubmit = false;
		}
		if(canSubmit==false){
			return false;	
			}
		
		
		var maximumDays;
		var maximumMonths;
		var maximumYears;
		var totalMaximumTenure = 0;
		var productConfMaximumTenure = "${productConfiguration.maximumTenure}";
		var splitMaxTenure = productConfMaximumTenure.split(",");
		for(var i = 0; i < splitMaxTenure.length; i++) {
			var daysOrMonthOrYearValMax = splitMaxTenure[i];
			if(daysOrMonthOrYearValMax.includes("Day")){
				var  splitDaysValMax = daysOrMonthOrYearValMax.split(" ");
				maximumDays = splitDaysValMax[0];
			}else if(daysOrMonthOrYearValMax.includes("Month")){
				var  splitMonthValMax = daysOrMonthOrYearValMax.split(" ");
				maximumMonths = splitMonthValMax[0];
			}else if(daysOrMonthOrYearValMax.includes("Year")){
				var  splitYearValMax = daysOrMonthOrYearValMax.split(" ");
				maximumYears = splitYearValMax[0];
			}
			
		}
		 if(maximumDays != undefined){
			 totalMaximumTenure += parseInt(maximumDays);
			 }
		 
		 if(maximumMonths != undefined){
			 var newMonthDateMaxTenure = new Date(year,month+parseInt(maximumMonths),day);
			 totalMaximumTenure += parseInt((newMonthDateMaxTenure - today)/(1000*60*60*24));
		 }
		 if(maximumYears != undefined) {
			 var newDateForMaxTenure = new Date(year+parseInt(maximumYears),month,day);
				var _daysMaxTenure = (newDateForMaxTenure - today)/(1000*60*60*24);
			 totalMaximumTenure += parseInt(_daysMaxTenure);
		 }
		
		 productConfMaximumTenure.replace(/,/g, ',');
		if (parseInt(fdTenure) > parseInt(totalMaximumTenure)) {
			document.getElementById('fdTenure').style.borderColor = "red";
			errorMsg = "<br>Maximum tenure for this deposit is "+ (productConfMaximumTenure.replace(/,/g, ' '))+".";
			validationError.innerHTML += errorMsg;
			canSubmit = false;
		}
		
		
		if(canSubmit==false){
			return false;	
			}
		
		
			 if(paymentType == 'Monthly'){
			if (parseInt(fdTenure) < 30) {
				document.getElementById('fdTenure').style.borderColor = "red";
				errorMsg = "<br>Minimum tenure should be 30 days for Monthly recurring deposit.";
				validationError.innerHTML += errorMsg;
				canSubmit = false;
			
			}
			}
			
			if(paymentType == 'Quarterly'){
				if (parseInt(fdTenure) < 90) {
					document.getElementById('fdTenure').style.borderColor = "red";
					errorMsg = "<br>Minimum tenure should be 90 days for Quarterly recurring deposit.";
					validationError.innerHTML += errorMsg;
					canSubmit = false;
				
				}
			
			}
			if(paymentType == 'Half Yearly'){
				if (parseInt(fdTenure) < 180) {
					document.getElementById('fdTenure').style.borderColor = "red";
					errorMsg = "<br>Minimum tenure should be 180 days for Half Yearly recurring deposit.";
					validationError.innerHTML += errorMsg;
					canSubmit = false;
				
				}
			
			}
			if(paymentType == 'Annually'){
				if (parseInt(fdTenure) < 365) {
					document.getElementById('fdTenure').style.borderColor = "red";
					errorMsg = "<br>Minimum tenure should be 365 days for Yearly recurring deposit.";
					validationError.innerHTML += errorMsg;
					canSubmit = false;
				
				}
			
			} 
			if($("#branchCode").length>0){
				var branchCode=document.getElementById("branchCode").value;
				if (branchCode.length<3 || branchCode>100) {

					document.getElementById("branchCode").style.borderColor = "red"
					errorMsg = "<br>Branch Code must be between 3 and 100 characters";
					validationError.innerHTML += errorMsg;
					nomineeCross.style.display = 'block';
					canSubmit = false;
				}
			}
			
			if($("#accountAccessType").val()=="select"){
				
					document.getElementById("accountAccessType").style.borderColor = "red"
					errorMsg = "<br>Select Account Access Type";
					validationError.innerHTML += errorMsg;
					nomineeCross.style.display = 'block';
					canSubmit = false;
				
			}
			
			

	/* } */
	
	/* else {
		document.getElementById('fdTenureType').style.borderColor = "black";
	} */
	
	
	if(canSubmit==false){
	return false;	
	}

	
	document.getElementById('depositOk').style.display='block';
	
	/*holder relationship validation...starts....*/

	var inp = document.querySelectorAll("#secondaryDetails .form-control");
	for(i=0;i<inp.length;i++){
		inp[i].style.borderColor = "#cccccc";
	}
	
	
 			var arrayLength = parseInt('${customerDetailsParsers.size()}');

			
				
			if(canSubmit== false){
				errorMsg ="Please enter relationship of the secondary account holder";
				validationError.innerHTML = errorMsg;
				document.getElementById('accountHolderCross').style.display='block';
				return false;}

			
			document.getElementById('accountHolderOk').style.display='block';

	/*contribution validation...starts....*/
	
	 inp = document.querySelectorAll("#contributionDetails .input");
	for(i=0;i<inp.length;i++){
		inp[i].style.borderColor = "#cccccc";
	}
	
	var primaryCont = parseFloat(document.getElementById("primaryCont").value);
	
     document.getElementById("primaryContributionError").innerHTML="";
	debugger
	if(primaryCont<0 || primaryCont>100 || isNaN(primaryCont)){
		document.getElementById("primaryCont").style.borderColor = "red"
		document.getElementById("primaryContributionError").innerHTML="Please enter a value greater than 0";
		errorMsg ="Incorrect primary holder contribution";
		validationError.innerHTML += errorMsg;
		
		document.getElementById('contributionCross').style.display='block';
		canSubmit= false;
	}
	
			for(var i=0;i<arrayLength;i++){
			
				var contributionJointId="contribution["+i+"]";
				var contributionJoint= parseFloat(document.getElementById(contributionJointId).value);
			
				document.getElementById("contributionError["+i+"]").innerHTML="";
				
				if(contributionJoint<0 || contributionJoint>=100 || isNaN(contributionJoint)){
					document.getElementById(contributionJointId).style.borderColor = "red"
					errorMsg ="<br>Incorrect secondary holder contribution";
					validationError.innerHTML += errorMsg;
					document.getElementById('contributionCross').style.display='block';
					canSubmit= false;
				}
				primaryCont=primaryCont+contributionJoint;
			}
			
			if(primaryCont < 0 || primaryCont > 100 ){
				document.getElementById("primaryCont").style.borderColor = "red"
				  document.getElementById('contributionCross').style.display='block';
				  errorMsg ="<br>Incorrect primary holder contribution";
				  validationError.innerHTML += errorMsg;
				  canSubmit= false;
				}
			if(canSubmit==false){return false}
			document.getElementById('contributionOk').style.display='block';
			
			/*.........................contribution js ended...............................*/
			
			
			/*...............................nominee js started............................*/	
			
			var isNomineeMandatory=$('#isNomineeMandatory').val();
				if(isNomineeMandatory==1 || isNomineeMandatory=="1"){
			 inp = document.querySelectorAll("#nomineeDetails .input");
			 var nomineeCross=document.getElementById("nomineeCross");
			for(i=0;i<inp.length;i++){
				inp[i].style.borderColor = "#cccccc";
			}
			var regExp1=new RegExp("[^a-z|^A-Z]");
			var nomineeName = document.getElementById("nomineeName").value; 
			var nomineeAddress= document.getElementById("nomineeAddress").value;
			var nomineeRelationShip= document.getElementById("nomineeRelationShip").value;
			var nomineeAge = document.getElementById("nomineeAge").value; 
			var nomineePan = document.getElementById("nomineePan").value; 
			var nomineeAadhar= document.getElementById("nomineeAadhar").value;
			var nomineeAadharLen = document.getElementById("nomineeAadhar").value.length; 
		/* zz */	
			if (nomineeName == "") {

						document.getElementById("nomineeName").style.borderColor = "red"
						errorMsg = "<br>Please enter nominee name";
						validationError.innerHTML += errorMsg;
						nomineeCross.style.display = 'block';
						canSubmit = false;
					}
					if (nomineeName.length<3 || nomineeName.length>100) {

						document.getElementById("nomineeName").style.borderColor = "red"
						errorMsg = "<br>Nominee name must be between 3 and 100 characters.";
						validationError.innerHTML += errorMsg;
						nomineeCross.style.display = 'block';
						canSubmit = false;
					}
			if(nomineeAge=="" || isNaN(nomineeAge)){
				
				document.getElementById("nomineeAge").style.borderColor = "red"
					nomineeCross.style.display='block';
				 errorMsg ="<br>Please enter nominee age";
				 validationError.innerHTML += errorMsg;
				canSubmit= false;
			}
			if(nomineeAddress==""){
				document.getElementById("nomineeAddress").style.borderColor = "red";
				 errorMsg ="<br>Please enter nominee address";
				 validationError.innerHTML += errorMsg;
				 nomineeCross.style.display='block';
				canSubmit= false;
			}
			
			
			if (nomineeAddress.length<3 || nomineeAddress.length>200) {

				document.getElementById("nomineeAddress").style.borderColor = "red"
				errorMsg = "<br>Nominee address must be between 3 and 200 characters.";
				validationError.innerHTML += errorMsg;
				
				canSubmit = false;
			}
			if(nomineeRelationShip==""){
				document.getElementById("nomineeRelationShip").style.borderColor = "red";
				nomineeCross.style.display='block';
				 errorMsg ="<br>Please enter nominee relationship";
				 validationError.innerHTML += errorMsg;
				canSubmit= false;
			}
			if (nomineeRelationShip.length<3 || nomineeRelationShip.length>100) {

				document.getElementById("nomineeRelationShip").style.borderColor = "red"
				errorMsg = "<br>Nominee relationship must be between 3 and 100 characters.";
				validationError.innerHTML += errorMsg;
				canSubmit = false;
			}
			if(regExp1.test(nomineeRelationShip)){
			
				document.getElementById('nomineeRelationShip').style.borderColor = "red";
				 errorMsg ="<br>Special characters and numbers are not allowed";
				 validationError.innerHTML += errorMsg;
				nomineeCross.style.display='block';
				canSubmit= false;
				/* document.getElementById("nomineeAadhar").style.borderColor = "red";
				nomineeCross.style.display='block';
				 errorMsg ="<br>Please enter nominee Aadhar card number";
				 validationError.innerHTML += errorMsg;
				 canSubmit= false; */
			}
			
			
			if(nomineeAadharLen!=12 && nomineeAadharLen>0){
	    		document.getElementById("nomineeAadhar").style.borderColor = "red";
				nomineeCross.style.display='block';
				 errorMsg ="<br>Aadhar card number is not valid. Please insert 12 digit number.";
				 validationError.innerHTML += errorMsg;
				 canSubmit= false;
			}
			
			var currency_ = $("#currency").val();
			if(currency_ == 'Select'){
	    		document.getElementById("currency").style.borderColor = "red";
				nomineeCross.style.display='block';
				 errorMsg ="<br>Please select currency.";
				 validationError.innerHTML += errorMsg;
				 canSubmit= false;
			}
	
			if(canSubmit== false){return false;}
			
			 nomineeAge = parseInt(nomineeAge);
			
			if(nomineeAge<18){
				var guardianName= document.getElementById("guardianName").value;
				var guardianAge= parseInt(document.getElementById("guardianAge").value) || 0;
             	var guardianAddress= document.getElementById("guardianAddress").value;
				var guardianRelationShip= document.getElementById("guardianRelationShip").value;
				var gaurdianPan= document.getElementById("gaurdianPan").value;
				var gaurdianAadhar = document.getElementById("gaurdianAadhar").value; 
				var gaurdianAadharLen = document.getElementById("gaurdianAadhar").value.length; 
				
			

				if (guardianName == "" && guardianAge == ""
					&& guardianAddress == ""
					&& guardianRelationShip == ""
					) { 
					document.getElementById("guardianName").style.borderColor = "red";
					document.getElementById("guardianAge").style.borderColor = "red";
					document.getElementById("guardianAddress").style.borderColor = "red";
					document.getElementById("guardianRelationShip").style.borderColor = "red";
					//document.getElementById("gaurdianPan").style.borderColor = "red";
					//document.getElementById("gaurdianAadhar").style.borderColor = "red";
				errorMsg = "<br>Please fill all the guardian details";
				validationError.innerHTML += errorMsg;
				nomineeCross.style.display = 'block';
				canSubmit = false;
				return canSubmit;
			}
					
					if (guardianName == "")
						document.getElementById("guardianName").style.borderColor = "red";
					if (guardianName.length<3 || guardianName>100) {
						document.getElementById("guardianName").style.borderColor = "red"
						errorMsg = "<br>Guardian name must be between 3 and 100 characters";
						validationError.innerHTML += errorMsg;
						canSubmit = false;
					}
					if (guardianAge == "" || isNaN(guardianAge))
						document.getElementById("guardianAge").style.borderColor = "red";
					if (guardianAddress == "")
						document.getElementById("guardianAddress").style.borderColor = "red";
					if (guardianAddress.length < 3 || guardianAddress > 200) {

						document.getElementById("guardianAddress").style.borderColor = "red"
						errorMsg = "<br>Guardian address must be between 3 and 200 characters";
						validationError.innerHTML += errorMsg;
						canSubmit = false;
					}
					if (guardianRelationShip == "")
						document.getElementById("guardianRelationShip").style.borderColor = "red";
					if (guardianRelationShip.length<3 || guardianRelationShip>100) {

						document.getElementById("guardianRelationShip").style.borderColor = "red"
						errorMsg = "<br>Guardian relationship must be between 3 and 100 characters";
						validationError.innerHTML += errorMsg;
						canSubmit = false;
					}
					/* if (gaurdianPan == "")
						document.getElementById("gaurdianPan").style.borderColor = "red";
					if (gaurdianAadhar == "" || isNaN(guardianAge))
						document.getElementById("gaurdianAadhar").style.borderColor = "red";
					 */
					
				
				if(guardianAge< 18 || guardianAge >100){
				
					document.getElementById('guardianAge').style.borderColor = "red";
					 errorMsg ="<br>Guardian age should be between 18 to 100 years";
					 validationError.innerHTML += errorMsg;
					 nomineeCross.style.display='block';
					canSubmit= false;
				}
				
				
			
				if(regExp1.test(guardianRelationShip)){
					
					document.getElementById('guardianRelationShip').style.borderColor = "red";
					 errorMsg ="<br>Special characters and numbers are not allowed";
					 validationError.innerHTML += errorMsg;
					 nomineeCross.style.display='block';
					canSubmit= false;
				}
				if(gaurdianAadharLen!=12 && gaurdianAadharLen>0){
					
					document.getElementById('gaurdianAadhar').style.borderColor = "red";
					errorMsg ="<br>Aadhar card number is not valid. Please insert 12 digit number.";
					 validationError.innerHTML += errorMsg;
					 nomineeCross.style.display='block';
					 canSubmit= false;
				}
			
				
				var panVal = $('#gaurdianPan').val();
				
				
				var regpan = /^([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}?$/;
				
				if(regpan.test(panVal)){
				
					document.getElementById("gaurdianPan").style.borderColor = "black"
				}else if(panVal!="") {
					
					document.getElementById('gaurdianPan').style.borderColor = "red";
					errorMsg ="<br>Please input correct pan card number.";
					nomineeCross.style.display='block';
					 validationError.innerHTML += errorMsg;
					 canSubmit= false;
				}		
				
				if(gaurdianAadhar==nomineeAadhar && nomineeAadhar!=""){
					
					document.getElementById('nomineeAadhar').style.borderColor = "red";
					document.getElementById('gaurdianAadhar').style.borderColor = "red";
					errorMsg ="<br>Nominee Aadhar and Guardian Aadhar should not be same.";
					 validationError.innerHTML += errorMsg;
					 nomineeCross.style.display='block';
					 canSubmit = false;
					
				}
				
			}
			else{
				var panVal = $('#nomineePan').val();
				
				
				var regpan = /^([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}?$/;
				
				if(regpan.test(panVal)){
				
					document.getElementById("nomineePan").style.borderColor = "black"
				}else if(panVal!="") {
					
					document.getElementById('nomineePan').style.borderColor = "red";
					errorMsg ="<br>Please input correct pan card number.";
					nomineeCross.style.display='block';
					 validationError.innerHTML += errorMsg;
					 canSubmit= false;
				}	
			
				var nomineePan = document.getElementById('nomineePan').value;
			
				 /* if(nomineePan==""){
					
						document.getElementById("nomineePan").style.borderColor = "red";
						 errorMsg ="<br>Please enter nominee Pan card Number";
						 validationError.innerHTML += errorMsg;
						 nomineeCross.style.display='block';
						 canSubmit= false;
					} */
			}
			if(canSubmit==false){return false}
			
			var jointAccSize=jointAccSize = parseInt('${customerDetailsParsers.size()}');
			
			
			for (i = 0; i < jointAccSize; i++){
				
				var nomineeNameSecId = "nomineeNameSec["+i+"]";
				var nomineeNameSec= document.getElementById(nomineeNameSecId).value;
				
				if(nomineeNameSec==""){
					document.getElementById(nomineeNameSecId).style.borderColor = "red";
					 errorMsg ="<br>Please enter secondary nominee name";
					 validationError.innerHTML += errorMsg;
					 nomineeCross.style.display='block';
					canSubmit= false;
				}
				
				if (nomineeNameSec.length<3 || nomineeNameSec.length>100) {
					document.getElementById(nomineeNameSecId).style.borderColor = "red"
					errorMsg = "<br>Secondray Nominee name must be between 3 and 100 characters.";
					validationError.innerHTML += errorMsg;
					nomineeCross.style.display = 'block';
					canSubmit = false;
				}
				var nomineeSecAddressId = "nomineeAddress["+i+"]";
				var nomineeSecAddress= document.getElementById(nomineeSecAddressId).value;
				
				
				if(nomineeSecAddress==""){
					document.getElementById(nomineeSecAddressId).style.borderColor = "red";
					 errorMsg ="<br>Please enter secondary nominee age";
					 validationError.innerHTML += errorMsg;
					 nomineeCross.style.display='block';
					canSubmit= false;
				}
				
				
				if (nomineeSecAddress.length<3 || nomineeSecAddress.length>200) {
					document.getElementById(nomineeSecAddressId).style.borderColor = "red"
					errorMsg = "<br>Secondray Nominee Address must be between 3 and 200 characters.";
					validationError.innerHTML += errorMsg;
					nomineeCross.style.display = 'block';
					canSubmit = false;
				}
				
				var nomineeRelationShipSecId = "nomineeRelationShip["+i+"]";
				var nomineeRelationShipSec= document.getElementById(nomineeRelationShipSecId).value;
				
				
				if(nomineeRelationShipSec==""){
					document.getElementById(nomineeRelationShipSecId).style.borderColor = "red";
					 errorMsg ="<br>Please enter secondary nominee relationship";
					 validationError.innerHTML += errorMsg;
					nomineeCross.style.display='block';
					canSubmit= false;
				}
				
				
				if (nomineeRelationShipSec.length<3 || nomineeRelationShipSec.length>100) {
					document.getElementById(nomineeRelationShipSecId).style.borderColor = "red"
					errorMsg = "<br>Secondray Nominee Relationship  must be between 3 and 100 characters.";
					validationError.innerHTML += errorMsg;
					nomineeCross.style.display = 'block';
					canSubmit = false;
				}
				
				
				if(regExp1.test(nomineeRelationShipSec)){
					
					document.getElementById(nomineeRelationShipSecId).style.borderColor = "red";
					 errorMsg ="<br>Special characters are not allowed for nominee relationShip field";
					 validationError.innerHTML += errorMsg;
					 nomineeCross.style.display='block';
					canSubmit= false;
				}
				var nomineeAgeSecId = "nomineeAgeSec["+i+"]";
				var nomineeAgeSec= document.getElementById(nomineeAgeSecId).value;
				 if(nomineeAgeSec==""){
					document.getElementById(nomineeAgeSecId).style.borderColor = "red";
					 errorMsg ="<br>Please enter secondary holder nominee age";
					 validationError.innerHTML += errorMsg;
					 nomineeCross.style.display='block';
					canSubmit= false;
				} 
				 
				 
				 
				 
		        var nomineeSecAadharCard = "nomineeAadhar["+i+"]";
			    var nomineeSecAadharCardLen= document.getElementById(nomineeSecAadharCard).value.length;
			    if(nomineeSecAadharCardLen!=12 && nomineeSecAadharCardLen>0){
			    	document.getElementById("nomineeAadhar["+i+"]").style.borderColor = "red";
			    	
			    	 errorMsg ="<br>Aadhar card number is not valid. Please insert 12 digit number.";
					 validationError.innerHTML += errorMsg;
					 nomineeCross.style.display='block';
					canSubmit= false;
			    }
				if(canSubmit==false){return false}
				var nomineeAgeSec= parseInt(nomineeAgeSec);
				
				
				
				//////////
				if(nomineeAgeSec<18){
				var guardianNameSec= document.getElementById("guardianName["+i+"]").value;
				var guardianAgeSec= document.getElementById("guardianAge["+i+"]").value;
				var guardianAddressSec= document.getElementById("guardianAddress["+i+"]").value;
				var guardianRelationShipSec= document.getElementById("guardianRelationShip["+i+"]").value;
				if(guardianNameSec=="" && guardianAgeSec =="" && guardianAddressSec=="" && guardianRelationShipSec==""){
					
					if(guardianNameSec=="" )
						document.getElementById("guardianName["+i+"]").style.borderColor = "red";
					if(guardianAgeSec=="" )
						document.getElementById("guardianAge["+i+"]").style.borderColor = "red";
					if(guardianAddressSec=="" )
						document.getElementById("guardianAddress["+i+"]").style.borderColor = "red";
					if(guardianRelationShipSec=="" )
						document.getElementById("guardianRelationShip["+i+"]").style.borderColor = "red";
					
					 errorMsg ="<br>Please enter all the  details of secondary nominee guardian";
					 validationError.innerHTML += errorMsg;
						nomineeCross.style.display='block';
					canSubmit= false;
				}
				
				
				if (guardianNameSec == "")
					document.getElementById("guardianName").style.borderColor = "red";
				if (guardianNameSec.length<3 || guardianNameSec>100) {
					document.getElementById("guardianName["+i+"]").style.borderColor = "red"
					errorMsg = "<br>Guardian name must be between 3 and 100 characters";
					validationError.innerHTML += errorMsg;
					canSubmit = false;
				}
				if (guardianAgeSec == "" || isNaN(guardianAgeSec))
					document.getElementById("guardianAge["+i+"]").style.borderColor = "red";
				if (guardianAddressSec == "")
					document.getElementById("guardianAddress").style.borderColor = "red";
				if (guardianAddressSec.length < 3 || guardianAddressSec > 200) {

					document.getElementById("guardianAddress["+i+"]").style.borderColor = "red"
					errorMsg = "<br>Guardian address must be between 3 and 200 characters";
					validationError.innerHTML += errorMsg;
					canSubmit = false;
				}
				if (guardianRelationShipSec == "")
					document.getElementById("guardianRelationShip").style.borderColor = "red";
				if (guardianRelationShipSec.length<3 || guardianRelationShipSec>100) {
					document.getElementById("guardianRelationShip["+i+"]").style.borderColor = "red"
					errorMsg = "<br>Guardian relationship must be between 3 and 100 characters";
					validationError.innerHTML += errorMsg;
					canSubmit = false;
				}
				
			
				 guardianAgeSec= parseInt(guardianAgeSec);
				 
					if(guardianAgeSec< 18 || guardianAgeSec >100){
						document.getElementById("guardianAge["+i+"]").style.borderColor = "red";
						 errorMsg ="<br>Guardian age should be between 18 to 100 years";
						 validationError.innerHTML += errorMsg;
						nomineeCross.style.display='block';
						canSubmit= false;
					}
					if(regExp1.test(guardianRelationShipSec)){
						
						document.getElementById("guardianRelationShip["+i+"]").style.borderColor = "red";
						 errorMsg ="<br>Special characters are not allowed in guardian relationShip field";
						 validationError.innerHTML += errorMsg;
						 nomineeCross.style.display='block';
						 canSubmit= false;
					}
					var gaurdianPanSecCard = "gaurdianPan["+i+"]";
					var gaurdianPanSecCardVal= document.getElementById(gaurdianPanSecCard).value;
					var regpan = /^([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}?$/;
					
					if(regpan.test(gaurdianPanSecCardVal)){
					
						document.getElementById("gaurdianPan["+i+"]").style.borderColor = "black";
					}else if(gaurdianPanSecCardVal!="") {
						
						document.getElementById("gaurdianPan["+i+"]").style.borderColor = "red";
						 errorMsg ="<br>Please input correct pan card number.";
						 validationError.innerHTML += errorMsg;
						 nomineeCross.style.display='block';
						canSubmit = false;
					}
				    var guardianSecAadharCard = "gaurdianAadhar["+i+"]";
				    var guardianSecAadharCardLen= document.getElementById(guardianSecAadharCard).value.length;
				    var gaurdianAadharValue= document.getElementById("gaurdianAadhar["+i+"]").value;
				    var nomineeAadharValue= document.getElementById("nomineeAadhar["+i+"]").value;
				  
				    if(guardianSecAadharCardLen!=12 && guardianSecAadharCardLen>0){
				    	document.getElementById("gaurdianAadhar["+i+"]").style.borderColor = "red";
				    	
				    	 errorMsg ="<br>Aadhar card number is not valid. Please insert 12 digit number.";
						 validationError.innerHTML += errorMsg;
						 nomineeCross.style.display='block';
						canSubmit= false;
				    }
				    if(nomineeAadharValue==gaurdianAadharValue && gaurdianAadharValue!=""){
						
						document.getElementById("nomineeAadhar["+i+"]").style.borderColor = "red";
						document.getElementById("gaurdianAadhar["+i+"]").style.borderColor = "red";
						errorMsg ="<br>Nominee Aadhar and Guardian Aadhar should not be same.";
						 validationError.innerHTML += errorMsg;
						 nomineeCross.style.display='block';
						 canSubmit = false;
						
					}
				}
				else
				{
					  var nomineePanSecHolder = "nomineePan["+i+"]";
	                    var nomineePanSecHolderVal= document.getElementById(nomineePanSecHolder).value;
						
						var regpan = /^([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}?$/;
						
						if(regpan.test(nomineePanSecHolderVal)){
						
							document.getElementById("nomineePan["+i+"]").style.borderColor = "black";
						}else if(nomineePanSecHolderVal!="") {
							
							document.getElementById("nomineePan["+i+"]").style.borderColor = "red";
							 errorMsg ="<br>Please input correct pan card number.";
							 validationError.innerHTML += errorMsg;
							 nomineeCross.style.display='block';
							canSubmit = false;
						}
					
				}
		
			}
			
			if(canSubmit==false){return false}
			document.getElementById("nomineeOk").style.display='block';
				}
			
			
					
					
					
				  
				   
					
					
					
						
						
						
						
						
					  
						
					
			
					
					
		/*...........................nominee js ended..........................................*/		
		
		
		/*.................pay off validation started........................*/
		
		 inp = document.querySelectorAll("#payOffDetails .input");
				for(i=0;i<inp.length;i++){
					inp[i].style.borderColor = "#cccccc";
				}
		    var sumPercent=0;	
		    var oneTimePayOffDate = document.getElementById('oneTimePayOffDate').value;
		    var parts = oneTimePayOffDate.split('/');
		    var mydate = new Date(parts[2], parts[1] - 1, parts[0]);
		    var userDate = mydate.getFullYear()+'/'+(mydate.getMonth()+1)+'/'+mydate.getDate(); 
		    var today = getTodaysDate();	

		    var curr_date = today.getDate();
		    var curr_month = today.getMonth() + 1; //Months are zero based
		    var curr_year = today.getFullYear();
		    var nowDate = (curr_year + "/" + curr_month + "/" + curr_date);
		
		    var payOffInterestType= document.getElementById('payOffInterestType').value;
		    if(payOffInterestType!=""){
			
			if(payOffInterestType=='One-Time'){
				if (document.getElementById('oneTimePayOffDate').value == '') {

				    document.getElementById('oneTimePayOffDate').style.borderColor = "red";
				   
				    canSubmit= false;
				   } 
			
				    if (userDate<nowDate) {
				    	document.getElementById('oneTimePayOffDate').style.borderColor = "red";
						errorMsg = "<br>Past Date Selected for one time payOff Date.";
						validationError.innerHTML += errorMsg;
						payoffCross.style.display='block';
						canSubmit = false;
				    }
				
			}
			
			document.getElementById("partInterest").style.boxShadow = "none";
		    document.getElementById('percentInterest').style.boxShadow = "none";
		    
		var partInterest = document.getElementById('partInterest').checked;
		var percentInterest = document.getElementById('percentInterest').checked;
		
		if(partInterest==true){
			var interestPayAmount=document.getElementById('interestPayAmount').value
			if(interestPayAmount==""){
				document.getElementById('interestPayAmount').style.borderColor = "red";
				errorMsg ="Please enter interest payoff amount";
				 validationError.innerHTML += errorMsg;
				canSubmit= false;
				
			}
		}
		else if(percentInterest==true){
		
			var interestPercent=document.getElementById('interestPercent').value;
			
			if(interestPercent==""){
				document.getElementById('interestPercent').style.borderColor = "red";
				errorMsg ="<br>Please enter interest payoff percent";
				validationError.innerHTML += errorMsg;
				canSubmit= false;
			
			}
			else{
			
			 interestPercent=parseFloat(interestPercent);
			sumPercent=interestPercent;
			}
			
		}
		
		else{
			document.getElementById("partInterest").style.boxShadow = "0 0 5px 0px red inset";
		    document.getElementById('percentInterest').style.boxShadow = "0 0 5px 0px red inset";
			errorMsg ="Please select part/percent payoff option";
			 validationError.innerHTML += errorMsg;
			canSubmit= false;
		}
         
		
		
		var otherName= document.getElementById('otherName').value;
		var otherAccount= document.getElementById('otherAccount').value;
		
		/* for both same and different bank */
		if(otherName==""){
			document.getElementById('otherName').style.borderColor = "red";
			errorMsg ="<br>Please enter beneficiary name";
			validationError.innerHTML += errorMsg;
			canSubmit= false;
		
		}
		if(otherAccount==""){
			document.getElementById('otherAccount').style.borderColor = "red";
			errorMsg ="<br>Please enter beneficiary account number";
			validationError.innerHTML += errorMsg;
			canSubmit= false;
			
		}
				    var sameBankAcc = document.getElementById('sameBankAcc').checked;
					var otherBankAcc = document.getElementById('otherBankAcc').checked;
					
					document.getElementById('sameBankAcc').style.boxShadow = "none";
					document.getElementById('otherBankAcc').style.boxShadow = "none";
					
					
					if(sameBankAcc==false && otherBankAcc==false){

						document.getElementById('sameBankAcc').style.boxShadow = "0 0 5px 0px red inset";
						document.getElementById('otherBankAcc').style.boxShadow = "0 0 5px 0px red inset";
						
							errorMsg ="<br>Please select same/different bank for interest payoff";
							validationError.innerHTML += errorMsg;
							canSubmit= false;
							
					}
					
					else{
					
						/* for different bank */
						if(otherBankAcc==true){
						
							var imps = document.getElementById('imps').checked;
							var rtgs = document.getElementById('rtgs').checked;
							var neft = document.getElementById('neft').checked;
							document.getElementById('neft').style.boxShadow = "none";
							document.getElementById('imps').style.boxShadow = "none";
							document.getElementById('rtgs').style.boxShadow = "none";
						
				           if(neft==false && imps==false && rtgs==false){
								
								document.getElementById('neft').style.boxShadow = "0 0 5px 0px red inset";
								document.getElementById('imps').style.boxShadow = "0 0 5px 0px red inset";
								document.getElementById('rtgs').style.boxShadow = "0 0 5px 0px red inset";
								
									errorMsg ="<br>Please choose transfer mode";
									validationError.innerHTML += errorMsg;
									canSubmit= false;
									
							}

							var otherBank= document.getElementById('otherBank').value;
							var otherIFSC= document.getElementById('otherIFSC').value;
							
							if(otherBank==""){
								document.getElementById('otherBank').style.borderColor = "red";
								errorMsg ="<br>Please enter payoff bank details";
								validationError.innerHTML += errorMsg;
							    canSubmit= false;
								
							}
							if(otherIFSC==""){
								document.getElementById('otherIFSC').style.borderColor = "red";
								errorMsg ="<br>Please enter payoff bank ifsc";
								validationError.innerHTML += errorMsg;
								
								canSubmit= false;
								
							}
							
						}
						
					}
					
		if(canSubmit== false){
			document.getElementById("payoffCross").style.display='block';
			return false;}
		
		/* payoff validation for secondary holder */

		
		for (i = 0; i < arrayLength; i++){
			
			
			var partInterest = document.getElementById("jointPartInterest["+i+"]").checked;
			var percentInterest = document.getElementById("jointPercentInterest["+i+"]").checked;
		
			document.getElementById("jointPartInterest["+i+"]").style.boxShadow = "none";
		    document.getElementById("jointPercentInterest["+i+"]").style.boxShadow = "none";
		    
			if(partInterest==true){
				var interestPayAmount=document.getElementById("interestPayAmount["+i+"]").value
				if(interestPayAmount==""){
					document.getElementById("interestPayAmount["+i+"]").style.borderColor = "red";
					errorMsg ="<br>Please enter interest payoff amount";
					validationError.innerHTML += errorMsg;
					canSubmit= false;
				
				}
			}
			
			else if(percentInterest==true){
				
				var interestPercent=parseFloat(document.getElementById("jointIntPercent["+i+"]").value);
				
				if(interestPercent==""||interestPercent==0){
					
					document.getElementById("jointIntPercent["+i+"]").style.borderColor = "red";
					errorMsg ="<br>Please enter interest payoff percent";
					validationError.innerHTML += errorMsg;
					canSubmit= false;
					
				}
				else{
					sumPercent=parseFloat(sumPercent)+parseFloat(interestPercent);
				}
			}
			
			
			else{
				document.getElementById("jointPartInterest["+i+"]").style.boxShadow = "0 0 5px 0px red inset";
			    document.getElementById("jointPercentInterest["+i+"]").style.boxShadow = "0 0 5px 0px red inset";
			    errorMsg ="<br>Please select part/percent payoff for joint holder";
				validationError.innerHTML += errorMsg;
				canSubmit= false;
			}
			
			/* for both same and different bank */
			var otherName= document.getElementById("jointAccounts["+i+"].contributions.beneficiaryOne").value;
			var otherAccount= document.getElementById("jointAccounts["+i+"].contributions.beneficiaryAccOne").value;
			
			
				if(otherName==""){
					document.getElementById("jointAccounts["+i+"].contributions.beneficiaryOne").style.borderColor = "red";
					errorMsg ="<br>Please enter beneficiary name for interest payoff";
					validationError.innerHTML += errorMsg;
					canSubmit= false;
				
				}
				if(otherAccount==""){
					document.getElementById("jointAccounts["+i+"].contributions.beneficiaryAccOne").style.borderColor = "red";
					errorMsg ="<br>Please enter beneficiary bank for interest payoff";
					validationError.innerHTML += errorMsg;
					canSubmit= false;
			
				}
				
				
				var sameBankAcc = document.getElementById("jointSameBankAcc["+i+"]").checked;
				var otherBankAcc = document.getElementById("jointOtherBankAcc["+i+"]").checked;
				 document.getElementById("jointSameBankAcc["+i+"]").style.boxShadow = "none";
				 document.getElementById("jointOtherBankAcc["+i+"]").style.boxShadow = "none";
				 
				if(sameBankAcc==false && otherBankAcc==false){
					
					 document.getElementById("jointSameBankAcc["+i+"]").style.boxShadow = "0 0 5px 0px red inset";
					 document.getElementById("jointOtherBankAcc["+i+"]").style.boxShadow = "0 0 5px 0px red inset";
					errorMsg ="<br>Please choose same/different bank";
					validationError.innerHTML += errorMsg;
					canSubmit= false;
				}
				else{
					
					 if(otherBankAcc==true){
						 var otherBank= document.getElementById("jointAccounts["+i+"].contributions.beneficiaryBankOne").value;
						var otherIFSC= document.getElementById("jointAccounts["+i+"].contributions.beneficiaryIFSCOne").value;

						if(otherBank==""){
							document.getElementById("jointAccounts["+i+"].contributions.beneficiaryBankOne").style.borderColor = "red";
							errorMsg ="<br>Please enter beneficiary bank for interest payoff";
							validationError.innerHTML += errorMsg;
							canSubmit= false;
						
						}
						if(otherIFSC==""){
							document.getElementById("jointAccounts["+i+"].contributions.beneficiaryIFSCOne").style.borderColor = "red";
							errorMsg ="<br>Please enter bank ifsc for interest payoff";
							validationError.innerHTML += errorMsg;
							canSubmit= false;
							
						}
						
						document.getElementById('imps['+i+']').style.boxShadow = "none";
						document.getElementById('neft['+i+']').style.boxShadow = "none";
						document.getElementById('rtgs['+i+']').style.boxShadow = "none";
						
						var imps = document.getElementById("imps["+i+"]").checked;
						var rtgs = document.getElementById("rtgs["+i+"]").checked;
						var neft = document.getElementById("neft["+i+"]").checked;
						
						
						if(neft==false && imps==false && rtgs==false){
							
							document.getElementById('neft['+i+']').style.boxShadow = "0 0 5px 0px red inset";
							document.getElementById('imps['+i+']').style.boxShadow = "0 0 5px 0px red inset";
							document.getElementById('rtgs['+i+']').style.boxShadow = "0 0 5px 0px red inset";
							
								errorMsg ="<br>Please choose transfer mode";
								validationError.innerHTML += errorMsg;
								canSubmit= false;
								
						}
					}
					 
				}
				
		}
		}
		
		
		if(canSubmit== false){
			document.getElementById("payoffCross").style.display='block';
			return false;}
		
		document.getElementById("payoffOk").style.display='block';
		
	
	      /*............ payoff validation ended........................ */	
			
		/********* ...............maturity validation starts.......... ****************/
		
				inp = document.querySelectorAll("#maturityDetails .form-control");
						for (i = 0; i < inp.length; i++) {
							inp[i].style.borderColor = "#cccccc";
						}

					var paymentModeOnMaturity=$('#paymentModeOnMaturity').val();
					if(paymentModeOnMaturity=="Fund Transfer"){
						
						var inLinked=false;
						if(document.getElementById("inLinked")!=null){
							document.getElementById('inLinked').style.boxShadow = "none";
							inLinked=document.getElementById("inLinked").checked;
						}
						
						var notInLinked= false;
						if(document.getElementById("notInLinked")!=null){
							notInLinked=document.getElementById("notInLinked").checked;
							document.getElementById("notInLinked").style.boxShadow = "none";
						}
						
						
						
						if (inLinked == false && notInLinked == false) {

							document.getElementById("inLinked").style.boxShadow = "0 0 5px 0px red inset";
							document.getElementById('notInLinked').style.boxShadow = "0 0 5px 0px red inset";

							document.getElementById("maturityCross").style.display = 'block';
							canSubmit = false;
						}
						if(notInLinked==true){
							var savingBankRadioMr = document
							.getElementById("savingBankRadioMr").checked;
					var differentBankRadioMr = document
							.getElementById("differentBankRadioMr").checked;

					document.getElementById("savingBankRadioMr").style.boxShadow = "none";
					document.getElementById('differentBankRadioMr').style.boxShadow = "none";

							
							if (savingBankRadioMr == false && differentBankRadioMr == false) {

								document.getElementById("savingBankRadioMr").style.boxShadow = "0 0 5px 0px red inset";
								document.getElementById('differentBankRadioMr').style.boxShadow = "0 0 5px 0px red inset";

								document.getElementById("maturityCross").style.display = 'block';
								canSubmit = false;
							}

							var beneficiaryNameMr = document
									.getElementById("beneficiaryNameMr") != null ? document
									.getElementById("beneficiaryNameMr").value
									: "";
							var beneficiaryAccountMr = document
									.getElementById("beneficiaryAccountMr") != null ? document
									.getElementById("beneficiaryAccountMr").value
									: "";

							if (beneficiaryNameMr == "") {
								document.getElementById("beneficiaryNameMr").style.borderColor = "red";

								canSubmit = false;

							}
							if (beneficiaryAccountMr == "") {
								document.getElementById("beneficiaryAccountMr").style.borderColor = "red";

								canSubmit = false;
							}

							
							
							
							if (differentBankRadioMr == true) {

								var impsMr = document.getElementById("impsMr").checked;
								var rtgsMr = document.getElementById("rtgsMr").checked;
								var neftMr = document.getElementById("neftMr").checked;

								document.getElementById("impsMr").style.boxShadow = "none";
								document.getElementById('rtgsMr').style.boxShadow = "none";
								document.getElementById('neftMr').style.boxShadow = "none";

								if (impsMr == false && rtgsMr == false && neftMr == false) {
									document.getElementById("impsMr").style.boxShadow = "0 0 5px 0px red inset";
									document.getElementById('rtgsMr').style.boxShadow = "0 0 5px 0px red inset";
									document.getElementById('neftMr').style.boxShadow = "0 0 5px 0px red inset";

									canSubmit = false;
								}

								var beneficiaryBankMr = document
										.getElementById("beneficiaryBankMr").value;
								var beneficiaryIfscMr = document
										.getElementById("beneficiaryIfscMr").value;

								if (beneficiaryBankMr == "") {
									document.getElementById("beneficiaryBankMr").style.borderColor = "red";

									canSubmit = false;
								}
								if (beneficiaryIfscMr == "") {
									document.getElementById("beneficiaryIfscMr").style.borderColor = "red";

									canSubmit = false;
								}

							}
						}

						if (canSubmit == false) {
							validationError.innerHTML = "<br>Please enter all the maturity details";
							document.getElementById("maturityCross").style.display = 'block';
							return false;
						}

						document.getElementById("maturityOk").style.display = 'block';
						for (i = 0; i < arrayLength; i++){
							
							var inLinked=false;
							if(document.getElementById("inLinked["+i+"]")!=null){
								inLinked=document.getElementById("inLinked["+i+"]").checked;
								document.getElementById("inLinked["+i+"]").style.boxShadow = "none";
							}
							 
							var notInLinked=false;
							if(document.getElementById("notInLinked["+i+"]")!=null){
								notInLinked=document.getElementById("notInLinked["+i+"]").checked;
								document.getElementById("notInLinked["+i+"]").style.boxShadow = "none";
							}
							
							
							
							if (inLinked == false && notInLinked == false) {

								document.getElementById("inLinked["+i+"]").style.boxShadow = "0 0 5px 0px red inset";
								document.getElementById("notInLinked["+i+"]").style.boxShadow = "0 0 5px 0px red inset";

								document.getElementById("maturityCross").style.display = 'block';
								canSubmit = false;
							}
							if(notInLinked==true){
								var savingBankRadioMr = document
								.getElementById("savingBankRadioMr["+i+"]").checked;
						var differentBankRadioMr = document
								.getElementById("differentBankRadioMr["+i+"]").checked;

						document.getElementById("savingBankRadioMr["+i+"]").style.boxShadow = "none";
						document.getElementById("differentBankRadioMr["+i+"]").style.boxShadow = "none";

								
								if (savingBankRadioMr == false && differentBankRadioMr == false) {

									document.getElementById("savingBankRadioMr["+i+"]").style.boxShadow = "0 0 5px 0px red inset";
									document.getElementById("differentBankRadioMr["+i+"]").style.boxShadow = "0 0 5px 0px red inset";

									document.getElementById("maturityCross").style.display = 'block';
									canSubmit = false;
								}

								var beneficiaryNameMr = document
										.getElementById("beneficiaryNameMr["+i+"]") != null ? document
										.getElementById("beneficiaryNameMr["+i+"]").value
										: "";
								var beneficiaryAccountMr = document
										.getElementById("beneficiaryAccountMr["+i+"]") != null ? document
										.getElementById("beneficiaryAccountMr["+i+"]").value
										: "";

								if (beneficiaryNameMr == "") {
									document.getElementById("beneficiaryNameMr["+i+"]").style.borderColor = "red";

									canSubmit = false;

								}
								if (beneficiaryAccountMr == "") {
									document.getElementById("beneficiaryAccountMr["+i+"]").style.borderColor = "red";

									canSubmit = false;
								}

								
								
								
								if (differentBankRadioMr == true) {

									var impsMr = document.getElementById("impsMr["+i+"]").checked;
									var rtgsMr = document.getElementById("rtgsMr["+i+"]").checked;
									var neftMr = document.getElementById("neftMr["+i+"]").checked;

									document.getElementById("impsMr["+i+"]").style.boxShadow = "none";
									document.getElementById('rtgsMr['+i+']').style.boxShadow = "none";
									document.getElementById('neftMr['+i+']').style.boxShadow = "none";

									if (impsMr == false && rtgsMr == false && neftMr == false) {
										document.getElementById("impsMr["+i+"]").style.boxShadow = "0 0 5px 0px red inset";
										document.getElementById('rtgsMr['+i+']').style.boxShadow = "0 0 5px 0px red inset";
										document.getElementById('neftMr['+i+']').style.boxShadow = "0 0 5px 0px red inset";

										canSubmit = false;
									}

									var beneficiaryBankMr = document
											.getElementById("beneficiaryBankMr["+i+"]").value;
									var beneficiaryIfscMr = document
											.getElementById("beneficiaryIfscMr["+i+"]").value;

									if (beneficiaryBankMr == "") {
										document.getElementById("beneficiaryBankMr["+i+"]").style.borderColor = "red";

										canSubmit = false;
									}
									if (beneficiaryIfscMr == "") {
										document.getElementById("beneficiaryIfscMr["+i+"]").style.borderColor = "red";

										canSubmit = false;
									}

								}
							}
							
							if (canSubmit == false) {
								validationError.innerHTML = "<br>Please enter all the maturity details";
								document.getElementById("maturityCross").style.display = 'block';
								return false;
							}

							document.getElementById("maturityOk").style.display = 'block';
						}

					}
						
						
						

	      
	      /*.........payment validation started..................*/
	      

			 inp = document.querySelectorAll("#paymentDetails .input");
					for(i=0;i<inp.length;i++){
						inp[i].style.borderColor = "#cccccc";
					}
					
					
	      
	     // var paymentMode = document.getElementById('paymentMode').value;
	      	var paymentMode = $('option:selected', $('#paymentMode')).attr('mode');
	     	
				if (paymentMode == "" || paymentMode == 'Select' || paymentMode == undefined) {
					
					document.getElementById('paymentMode').style.borderColor = "red";
					errorMsg ="<br>Please select mode of payment";
					validationError.innerHTML += errorMsg;
					document.getElementById("paymentCross").style.display='block';
					return false;
				}
				if (paymentMode == 'Cheque'
						|| paymentMode == 'DD') {

					var chequeNo = document.getElementById('chequeNo').value;
					var chequeBank = document.getElementById('chequeBank').value;
					var chequeBranch = document.getElementById('chequeBranch').value;

					if (chequeNo == "" || chequeBank == ""
							|| chequeBranch == "") {
				
					
						if (chequeNo == "") 
							document.getElementById('chequeNo').style.borderColor = "red";
						if (chequeBank == "")
							document.getElementById('chequeBank').style.borderColor = "red";
						if (chequeBranch == "")
							document.getElementById('chequeBranch').style.borderColor = "red";
						
						errorMsg ="<br>Please enter all the payment details";
						validationError.innerHTML += errorMsg;
						document.getElementById("paymentCross").style.display='block';
						
						return false;
					}

				}

				if (paymentMode == 'Debit Card' || paymentMode == 'Credit Card') {

					//var cardType = document.getElementById('cardType').value;
					var cardNo = document.getElementById('cardNo').value;
					var expiryMonth = document.getElementById('expiryMonth').value;
					//var cardType = document.getElementById('cardType').value;
					var expiryYear = document.getElementById('expiryYear').value;
					var cardNo = document.getElementById('cardNo').value;
					var cvv = document.getElementById('cvv').value;
					var cardNoSplit = cardNo.split("-");
					var cardNoZeroIndex = cardNoSplit[0];
					var cardNoFirstIndex = cardNoSplit[1];
					var cardNoSecondIndex = cardNoSplit[2];
					var cardNoThirdIndex = cardNoSplit[3];
					var cardNoAfterConcatination = cardNoZeroIndex + cardNoFirstIndex + cardNoSecondIndex + cardNoThirdIndex;
					var cardNoAfterConcatinationLen = cardNoAfterConcatination.length;
				 if (cardNo == "" || cvv == "") {
						if (cardNo == "")
							document.getElementById('cardNo').style.borderColor = "red";
						if (cvv == "")
							document.getElementById('cvv').style.borderColor = "red";
						
						errorMsg ="<br>Please enter all the card details";
						validationError.innerHTML += errorMsg;
						document.getElementById("paymentCross").style.display='block';
						return false;
					} 
					
					 if(isNaN(cardNoAfterConcatination)){
							document.getElementById('cardNo').style.borderColor = "red";
							errorMsg = '<br> Please input correct card number';
							document.getElementById("paymentCross").style.display='block';
							return false;
						}
					
                    if(expiryMonth == "MM" || expiryYear == "YY"){
                    	
                    	if (expiryMonth == "MM")
							document.getElementById('expiryMonth').style.borderColor = "red";
						if (expiryYear == "YY")
							document.getElementById('expiryYear').style.borderColor = "red";
						
                    	errorMsg ="<br>Please enter all the card details";
						validationError.innerHTML += errorMsg;
						document.getElementById("paymentCross").style.display='block';
						return false;
	
                     }

				}

				if (paymentMode == 'Fund Transfer') {
					var fdAmount = parseFloat(document
							.getElementById('fdAmount').value);
					var linkedAccountBalance = parseFloat(document
							.getElementById('linkedAccountBalance').value);

					if (linkedAccountBalance < fdAmount) {
						document.getElementById('linkedAccountBalance').style.borderColor = "red";
						errorMsg ="Insufficient linked account balance. Please choose another payment mode";
						validationError.innerHTML += errorMsg;
						document.getElementById("paymentCross").style.display='block';
						return false;
					}
				}

			if (paymentMode == 'Net Banking') {
				var bankType = document.getElementById('bankType').value;
				
				if(bankType==""){
					
					document.getElementById('bankType').style.borderColor = "red";
					errorMsg ="Please select Bank Type";
					validationError.innerHTML += errorMsg;
					document.getElementById("paymentCross").style.display='block';
				
					canSubmit= false;
				}
				else{
					var beneficiaryName = document.getElementById('beneficiaryName').value;
					var beneficiaryAccount = document.getElementById('beneficiaryAccount').value;

					if(bankType=="differentBank"){
						var beneficiaryBank = document.getElementById('beneficiaryBank').value;
						var beneficiaryIfsc = document.getElementById('beneficiaryIfsc').value;
						 if(transferType=="Select"){
						  		
						  		document.getElementById('transferType').style.borderColor = "red";
						  		errorMsg = " <br>Please select transfer type";
						  		validationError.innerHTML += errorMsg;
						  		document.getElementById('depositCross').style.display='block';
						  		canSubmit= false;
						  	}
						if(beneficiaryBank=="" || beneficiaryIfsc=="" || beneficiaryName=="" || 
								beneficiaryAccount=="" ){
						
							
								if(beneficiaryBank=="")
									document.getElementById('beneficiaryBank').style.borderColor = "red";
								if(beneficiaryIfsc=="")
									document.getElementById('beneficiaryIfsc').style.borderColor = "red";
								if( beneficiaryName=="")
									document.getElementById('beneficiaryName').style.borderColor = "red";
								if(beneficiaryAccount=="")
									document.getElementById('beneficiaryAccount').style.borderColor = "red";
								
								errorMsg ="<br>Please select All beneficiary details";
								validationError.innerHTML += errorMsg;
								document.getElementById("paymentCross").style.display='block';
								canSubmit= false;
						}
						
						
					}
					if(bankType=="sameBank"){
						
						if(beneficiaryName=="" || beneficiaryAccount=="" ){
					
							
							 if( beneficiaryName=="")
									document.getElementById('beneficiaryName').style.borderColor = "red";
								if(beneficiaryAccount=="")
									document.getElementById('beneficiaryAccount').style.borderColor = "red";
								
								errorMsg ="<br>Please select All beneficiary details";
								validationError.innerHTML += errorMsg;
								document.getElementById("paymentCross").style.display='block';
								canSubmit= false;
						}
							}	
				}
				} 
			
			if(canSubmit== false){return false;}
			 document.getElementById("paymentOk").style.display='block';
				
			
			/*..............................payment validation ended....................................*/
			
			
	return true;

}
	 function validationAGE(id) {
		
		 var valAge = document.getElementById('guardianAge['+id+']').value
		 if (valAge > 100){
			    alert("Nominee age should be 1 to 100 years");
			    document.getElementById('guardianAge['+id+']').value =  "100";
			  }
		 
		
	}
	 
	 function validationAGENomineeSec(id) {
		 var valAge = document.getElementById('nomineeAgeSec['+id+']').value
		 if (valAge > 100){
			    alert("Nominee age should be 1 to 100 years");
			    document.getElementById('nomineeAgeSec['+id+']').value =  "100";
			  }
		 
		
	}
	 
	 
	 
	 
function blockSpecialChar(e){
    var k;
  
    document.all ? k = e.keyCode : k = e.which;
    return ((k > 64 && k < 91) || (k > 96 && k < 123) || k == 8 || k == 32 || (k >= 48 && k <= 57));
    }



function validIFSC(event){
	var valiName = document.getElementById(event.target.id);

	
	var regex = new RegExp("^[A-Za-z]{4}[a-zA-Z0-9]{7}$");
    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);

   var a =  blockSpecialChar(event);
   if(a==false){
	   event.preventDefault();
	   return false;
	   }
    if (valiName.value.length >10 && event.keyCode != 8){
    	 var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
    	    if (!regex.test(key)) {
    	    	alert("invalid ifsc code")
    	        event.preventDefault();
    	        return false;
    	 }
    }
   
    

	
	
}



$(document).ready(function(){
	
	
	
	
	$('#nomineeAge').keyup(function(){
		  if ($(this).val() > 100){
		    alert("Nominee age should be 1 to 100 years");
		    $(this).val('100');
		  }
		});

	


	
	$('#guardianAge').keyup(function(){
		  if ($(this).val() > 100){
		    alert("Guardian age should be 1 to 100 years");
		    $(this).val('100');
		  }
		});

	
	
	
	
	if('${productConfiguration.productType}'=='Regular Deposit'||'${productConfiguration.productType}'== 'Tax Saving Deposit'){
		$('#paymentType').val('One-Time').change();
		document.getElementById("paymentType").disabled = true;
		}
	if("users"=="${baseURL[1]}"){
		$('.user_valid').hide();
	}
	var paymentType2 = document.getElementById("paymentType").value;
	
	var citizenCustomer = $("#citizenCustomer").val();
	/* if(citizenCustomer == 'NRI')
		//$("#taxSavingDepositId").hide();
	else
		//$("#taxSavingDepositId").show();
	 */
	var payoffTypee='${productConfiguration.interestCalculationBasis}';
	
	if(payoffTypee=='Quarterly'){
		$('#days').hide();
	}
	if(payoffTypee=='HALF YEARLY'){
		$('#days').hide();
		$('#quarterly').hide();
	}
	if(payoffTypee=='Annually'){
		$('#days').hide();
		$('#quarterly').hide();
		$('#semi').hide();
	}
	var nriAccType = "${customer.nriAccountType}";
	$("#nriAccountType").val(nriAccType).change();
	
	resetPaymentDetails();
	  displayDaysInTenure();
	  if('${productConfiguration.productType}'!='Regular Deposit' && '${productConfiguration.productType}'!= 'Tax Saving Deposit'){

document.getElementById('deductionDay').value='${fixedDepositForm.deductionDay}';
	  }

//document.getElementById('dayId').style.display = 'block';
var arrayLength = parseInt("${fixedDepositForm.accountList.size()}");
//var fdTenureType = document.getElementById('fdTenureType').value;

if(arrayLength==0){
	document.getElementById('linkedAccountDiv').style.display = 'none';
	//document.getElementById('fundTransferLinkedAccount').style.display = 'none';
	
}

/* if (fdTenureType == 'Day') {
	
	document.getElementById('dayId').style.display='block';
	document.getElementById('monthId').style.display='none';
	document.getElementById('yearId').style.display='none';
	document.getElementById('dayValue').style.display='none';
	
} else if (fdTenureType == 'Month') {
	
	document.getElementById('dayId').style.display='none';
	document.getElementById('monthId').style.display='block';
	document.getElementById('yearId').style.display='none';
	document.getElementById('dayValue').style.display='none';
} else {

	document.getElementById('dayId').style.display='none';
	document.getElementById('monthId').style.display='none';
	document.getElementById('yearId').style.display='block';
} */

var isEmpty= <c:out value="${empty fixedDepositForm.accountList}"/>;

if(isEmpty==false){
var account=document.getElementById('accountNo').value;

if(account!=""){
	var account = account.split(",");
	document.getElementById('accountType').value=account[1];
	
	document.getElementById('accountBalance').value=parseFloat(account[2]).toFixed(2).toLocaleString("en-US");
	document.getElementById('accountBalance1').value=parseFloat(account[2]);
	
	document.getElementById('accountTypeDiv').style.display='block';
	document.getElementById('accountBalanceDiv').style.display='block';
	
	//document.getElementById('fundTransferLinkedAccount').style.display='block';
	}
	else{
		document.getElementById('accountTypeDiv').style.display='none';
		document.getElementById('accountBalanceDiv').style.display='none';
		//document.getElementById('fundTransferLinkedAccount').style.display='none';
	}
}


/*......................nominee.ready.function.started.....................*/

var age =parseInt('${fixedDepositForm.nomineeAge}');
if(age < 18) {
	$('#minor').attr('checked', true);
	$("#gaurdian").show();
}else {
	$('#major').attr('checked', true);
	$("#gaurdian").hide();	
}
var arrayLength = 0;

var isEmpty= <c:out value="${empty customerDetailsParsers}"/>;
if(isEmpty==false){
	arrayLength = parseInt('${customerDetailsParsers.size()}');
}


for(var i=0;i<arrayLength;i++){
	
	 var guardianNameI="guardianName["+i+"]";
	 var guardianAgeI="guardianAge["+i+"]";
	 var guardianAddressI="guardianAddress["+i+"]";
	 var guardianRelationShipI="guardianRelationShip["+i+"]";
	 var gaurdianPanI="gaurdianPan["+i+"]";
	 var gaurdianAadharI="gaurdianAadhar["+i+"]";
	 var nomineePanI="nomineePan["+i+"]";
	
	
	    var nomineePanIndex="nomineePanCard["+i+"]";
		var nomineeAgeId="nomineeAgeSec["+i+"]";
		if(parseInt(document.getElementById(nomineeAgeId).value)<18){
			var guardianId="jointGaurdian["+i+"]";
			document.getElementById(guardianId).style.display='block';
			document.getElementById(nomineePanI).value="";
			
		}
		else{
			
			document.getElementById(nomineePanIndex).style.display='block';
		}
}
/*......................nominee.ready.function.end.....................*/
 
 /* ....pay off.ready function...started... */


 
 var payOffInterestType= document.getElementById('payOffInterestType').value;
 if(payOffInterestType==""){
	 document.getElementById('showPartPercent').style.display = 'none';
	 document.getElementById('partPercentValueDiv').style.display = 'none';
	 document.getElementById('payOffOtherDetails').style.display = 'none'; 
	document.getElementById("partPercentValueDiv").style.display='none';
	document.getElementById("sameBankDifferentBankDiv").style.display='none';
	
	 
 
 for(var i=0;i<arrayLength;i++){
	 
	// document.getElementById('showPartPercent['+i+']').style.display = 'none';
	 document.getElementById('partPercentValueDiv['+i+']').style.display = 'none';
	 document.getElementById('payOffOtherDetails['+i+']').style.display = 'none';
	 document.getElementById('jointInterestPercent['+i+']').style.display = 'none';
	 document.getElementById('jointPartInterestAmt['+i+']').style.display = 'none';

 }
		
 }
 else{
		
	 document.getElementById('showPartPercent').style.display = 'block';
	 document.getElementById("sameBankDifferentBankDiv").style.display='block';
	 document.getElementById("partPercentValueDiv").style.display='block';

	 var partInterest= document.getElementById("partInterest").checked;
	 var percentInterest= document.getElementById("percentInterest").checked;
	 
	 if(partInterest==false && percentInterest==false){
		 
		 document.getElementById("partPercentValueDiv").style.display='none';
		 /* no payoff for primary */
	 }

	 else{
		 document.getElementById('partPercentValueDiv').style.display = 'block';
		 
	 }
				 /* sameBankAcc/diff bank hide-show */
				 
				 var sameBankAcc= document.getElementById("sameBankAcc").checked;
				 var otherBankAcc= document.getElementById("otherBankAcc").checked;
				
				  if(otherBankAcc==true){
					 document.getElementById('payOffOtherDetails').style.display = 'block';
					 
				 }
				 else{
					 document.getElementById('payOffOtherDetails').style.display = 'none';
				 }
				 

				 /* part/percent hide-show */
				 var interestPayAmount= document.getElementById('interestPayAmount').value;
				 var interestPercent= document.getElementById('interestPercent').value;

				 if(interestPayAmount!=""){
					 document.getElementById("partInterest").checked=true;
					 document.getElementById("percentInterest").checked=false;
					 document.getElementById('interestPercentTr').style.display='none';
				 }
				 else if(interestPercent!=""){
					 document.getElementById("partInterest").checked=false;
					 document.getElementById("percentInterest").checked=true;
					 document.getElementById('firstPartInterestAmtTr').style.display='none';
				}
				 else{
					 document.getElementById("percentInterest").checked=false;
					 document.getElementById("percentInterest").checked=false;
					 document.getElementById('firstPartInterestAmtTr').style.display='none';
					 document.getElementById('interestPercentTr').style.display='none';
				 }



	 
	 
	
	 /* joint account */
	 for(var i=0;i<arrayLength;i++){
		 
		
		 var jointPartInterest= document.getElementById("jointPartInterest["+i+"]").checked;
		 var jointPercentInterest= document.getElementById("jointPercentInterest["+i+"]").checked;
		 
		 if(jointPartInterest==false && jointPercentInterest==false){
			 document.getElementById('partPercentValueDiv['+i+']').style.display = 'none';
			 /* no payoff for this secondary */
		 }
		 else{
			 
			 document.getElementById('partPercentValueDiv['+i+']').style.display = 'block';
		 }
			 var jointSameBankAcc= document.getElementById("jointSameBankAcc["+i+"]").checked;
			 var jointOtherBankAcc= document.getElementById("jointOtherBankAcc["+i+"]").checked;
			 
			 if(jointOtherBankAcc==true){
					document.getElementById("payOffOtherDetails["+i+"]").style.display = 'block';
				 }
			 
			 else{
				 document.getElementById("payOffOtherDetails["+i+"]").style.display = 'none';
			 }
			 
			 
			 /* part/percent hide-show -joint */
			 var jointIntPayAmount= document.getElementById('interestPayAmount['+i+']').value;
			 var jointIntPercent= document.getElementById('jointIntPercent['+i+']').value;
			 
			 if(jointIntPayAmount!=""){
				 document.getElementById('jointPartInterest['+i+']').checked=true;
				 document.getElementById('jointPercentInterest['+i+']').checked=false;
				 document.getElementById('jointInterestPercent['+i+']').style.display = 'none';
			 }
			 else if(jointIntPercent!=""){
				document.getElementById('jointPartInterest['+i+']').checked=false;
				 document.getElementById('jointPercentInterest['+i+']').checked=true;
				 document.getElementById('jointPartInterestAmt['+i+']').style.display = 'none';
			}
			 else{
				 document.getElementById('jointPartInterest['+i+']').checked=false;
				 document.getElementById('jointPercentInterest['+i+']').checked=false;
				 document.getElementById('jointInterestPercent['+i+']').style.display = 'none';
				 document.getElementById('jointPartInterestAmt['+i+']').style.display = 'none';
			 }

		  
	 }

 }
 



/* ....pay off. function..ended.... */



 /*...................payment.ready function.....started..............*/
 
 
 	// Initiate CC Validation
          InitiateValidation();
          showDetails();
         var expiryDate= '${fixedDepositForm.depositForm.expiryDate}';
        
         if(expiryDate!=''){
        	 var expiryDateArray= expiryDate.split('-');
        	 document.getElementById('expiryMonth').value=expiryDateArray[0];
        	 document.getElementById('expiryYear').value=expiryDateArray[1];
         }
         else{
        	 document.getElementById('expiryMonth').value='MM';
        	 document.getElementById('expiryYear').value='YY';
         }
        
         onchangeBankType();
       
        	 var nriTypeAccountVal = $("#nriAccountType").val();
			accountTypeVal(nriTypeAccountVal);
     		var currvalue = '${fixedDepositForm.currency}';
     		
     		var bankConfiguration_ = "${productConfiguration.defaultCurrency}";
     		if(currvalue == "")
     		$("#currency").val("Select").change();
     		else
     		$("#currency").val(currvalue).change();
     		document.getElementById('currency').value = '${productConfiguration.defaultCurrency}';
     		
    		if('${productConfiguration.productType}'=='Regular Deposit'||'${productConfiguration.productType}'== 'Tax Saving Deposit'){
     				$('#paymentType').val('One-Time').change();
     				document.getElementById("paymentType").disabled = true;
     		}	
     });

     		function getTodaysDate()
     		{
     			var today = null;
     			$.ajax({  
     			    type: "GET",  
     			    async: false,
     			    url: "<%=request.getContextPath()%>/common/loginDateForJsp", 
     			    contentType: "application/json",
     			    dataType: "json",

     			    success: function(response){  
     			    //	window.loginDateForFront = new Date(parseInt(response));
     			    	today = new Date(parseInt(response))
     			    },  
     			    error: function(e){  
     			    	 $('#error').html("Error occured!!")
     			    	 // window.loginDateForFront = getTodaysDate();
     			    }  
     			  });  
     			return today;
     		}


	
	
     		

	/* func for on change of primary contribution */
	function estimateRemaining() {	
	/* 	var fdAmount = ${fixedDepositForm.fdAmount}; */
		var userContribution = $("#primaryCont").val();
		
		document.getElementById("primaryContributionError").innerHTML = "";
		document.getElementById("primaryCont").style.borderColor = "#cccccc"
		
		if(userContribution < 0){
			document.getElementById("primaryCont").style.borderColor = "red"
			document.getElementById("primaryContributionError").innerHTML="Please enter a value greater than 0";
			return
		}
		if(userContribution > 100){
			document.getElementById("primaryCont").style.borderColor = "red"
			document.getElementById("primaryContributionError").innerHTML="Please enter a value less than 100";
			return
		}
		var secContribution=parseFloat(100 - parseFloat(userContribution)).toFixed(2);
		document.getElementById("contribution[0]").value = secContribution;
	}
	
	
totalContribution=0;
	/* func for on change of joint contribution */
	function assignRemaining(index) {
		var userContribution = parseFloat(document.getElementById("primaryCont").value);
		
		if(userContribution<0 || isNaN(userContribution) ){
			document.getElementById("primaryCont").style.borderColor="red";
			document.getElementById("primaryContributionError").innerHTML="Please enter primary contribution percent first";
			return
		}
		if(userContribution>100){
			document.getElementById("primaryCont").style.borderColor="red";
			document.getElementById("primaryContributionError").innerHTML="Please enter a value less than 100";
			return
		}
		
		var currentVal = parseInt(document.getElementById("contribution["+index+"]").value);
		document.getElementById("contributionError["+index+"]").innerHTML="";
		document.getElementById("contribution["+index+"]").style.borderColor = "#cccccc"
		if(currentVal < 0){
			document.getElementById("contribution["+index+"]").style.borderColor = "red";
			document.getElementById("contributionError["+index+"]").innerHTML="Please enter a value greater than 0";
			return
		}
		if(currentVal >= 100){
			document.getElementById("contribution["+index+"]").style.borderColor = "red"
			document.getElementById("contributionError["+index+"]").innerHTML="Please enter a value less than 100";
			return
		}
		totalContribution=0;
		for(var i=index;i>=0;i--){
			totalContribution+=parseFloat(document.getElementById("contribution["+i+"]").value);
		}
		 var arrayLength =$('.contribution').length;
		var indexValue=index+1;
		
		if(index==arrayLength && (totalContribution<0 || (totalContribution+userContribution)!=100)){
			 document.getElementById("contributionSumError").style.display='block';
			 document.getElementById("contribution["+index+"]").style.borderColor = "red"
			return false;
		 }
		
		if(indexValue<arrayLength && (totalContribution+userContribution)<100){
		document.getElementById("contribution["+indexValue+"]").value = parseFloat(100-(parseFloat(totalContribution)+parseFloat(userContribution)));
		}
		 
		/* 	var isEmpty= <c:out value="${empty customerDetailsParsers}"/>;
			if(isEmpty==false){
				arrayLength = <c:out value="${customerDetailsParsers.size()}"/>;
			}
			 */
		
			 
			 
			 
		/*  index= index+1;	
		 if(index<arrayLength){
			 var remainingPer=parseFloat(100-(parseFloat(currentVal)+parseFloat(userContribution)));
			 document.getElementById("contributionSumError").style.display='none';
			 document.getElementById("contribution["+index+"]").style.borderColor ="#cccccc"
			 if(remainingPer<=0){
				 document.getElementById("contributionSumError").style.display='block';
				 document.getElementById("contribution["+index+"]").style.borderColor = "red"
				return
			 }
			 //document.getElementById("contribution["+index+"]").value = parseFloat(100-(parseFloat(currentVal)+parseFloat(userContribution)));

			 
		 } */
			 
		}
	
	/* function showTenureDetails(value){
		var tenure = value;
		if(tenure=="Day"){
			document.getElementById('dayId').style.display = 'block';
			document.getElementById('monthId').style.display = 'none';
			document.getElementById('yearId').style.display = 'none';
			document.getElementById('dayValue').style.display = 'none';
			document.getElementById('fdDayValue').value = "";
			document.getElementById('fdTenure').value = "";
		}
		if(tenure=="Month"){
			document.getElementById('dayId').style.display = 'none';
			document.getElementById('monthId').style.display = 'block';
			document.getElementById('yearId').style.display = 'none';
			document.getElementById('dayValue').style.display = 'none';
			document.getElementById('fdDayValue').value = "";
			document.getElementById('fdTenure').value = "";
		}
		if(tenure=="Year"){
			document.getElementById('dayId').style.display = 'none';
			document.getElementById('monthId').style.display = 'none';
			document.getElementById('yearId').style.display = 'block';
			document.getElementById('dayValue').style.display = 'block';
			document.getElementById('fdTenure').value = "";
		}
		
	} */
	
	 function showGuardian(value,guardianId,nomineeAgeId,nomineePanIndex,guardianNameI,guardianAgeI,guardianAddressI,guardianRelationShipI,gaurdianPanI,gaurdianAadharI,nomineePanI){
	
		 value=parseInt(value);
		 
		   if( isNaN(value) || value>100 || value<0){
				document.getElementById(nomineeAgeId).value='';
				document.getElementById(guardianId).style.display='none';
			}
		 
		   else if(value<18){
			document.getElementById(guardianId).style.display='block';
			//document.getElementById('nomineePanCard').style.display = 'none';
			document.getElementById(nomineePanIndex).style.display='none';
			document.getElementById(nomineePanI).value="";
			
			}
			else{
				
				document.getElementById(guardianId).style.display='none';
				document.getElementById('nomineePanCard').style.display = 'block';
				//document.getElementById(nomineePanI).value="";
				//document.getElementById("nomineePan").value="";
		   		
		   		document.getElementById(nomineePanIndex).style.display='block';
		   		document.getElementById(guardianNameI).value="";
				document.getElementById(guardianAgeI).value="";
				document.getElementById(guardianAddressI).value="";
				document.getElementById(guardianRelationShipI).value="";
				document.getElementById(gaurdianPanI).value="";
				document.getElementById(gaurdianAadharI).value="";
				
				 /* document.getElementById("guardianName").value="";
				document.getElementById("guardianAge").value="";
				document.getElementById("guardianAddress").value="";
				document.getElementById("guardianRelationShip").value="";
				document.getElementById("gaurdianPan").value="";
				document.getElementById("gaurdianAadhar").value="";  */
				
			}
		   
		  
		} 
	 
	function refresh(value,guardianId,nomineeAgeId,nomineePan,guardianName,guardianAge,guardianAddress,guardianRelationShip,gaurdianPan,gaurdianAadhar){
		debugger
		var emptyValue = value;
		value=parseInt(value);
		   if( isNaN(value) || value>100 || value<0){
			  
				document.getElementById(nomineeAgeId).value='';
				document.getElementById(guardianId).style.display='none';
				document.getElementById('nomineePanCard').style.display = 'block';
			}
		 
		   else if(value<18){
		    document.getElementById("nomineePan").value="";
			document.getElementById(guardianId).style.display='block';
			document.getElementById('nomineePanCard').style.display = 'none';
			//document.getElementById(nomineePanIndex).style.display='none';
			//document.getElementById(nomineePanI).value="";
			
			}else if(value>=18 || emptyValue == ""){
				 document.getElementById('nomineePanCard').style.display = 'block';
				 
					document.getElementById(guardianId).style.display='none';
			}
			else{
				    document.getElementById(guardianId).style.display='none';
				    document.getElementById("guardianName").value="";
					document.getElementById("guardianAge").value="";
					document.getElementById("guardianAddress").value="";
					document.getElementById("guardianRelationShip").value="";
					document.getElementById("gaurdianPan").value="";
					document.getElementById("gaurdianAadhar").value=""; 
					
					/* tunk */
					//document.getElementById("nomineePan").value="";
			}
		
	}
	
	
</script>
<body>
	<div class="right-container" id="right-container">
		<div class="container-fluid">

			<div class="Flexi_deposit">


				<div class="header_customer" style="display:none">
					<h3>Joint/Consortium Deposit</h3>
				</div>




				<form:form id="jointForm" name="fixedDeposit"
					class="form-horizontal" commandName="fixedDepositForm"
					autocomplete="off">
					<form:hidden path="productConfigurationId"
						value="${model.productConfiguration.id}" id="productConfigurationId" />
					<form:hidden path="id" value="${customer.id}" />
					<form:hidden path="depositAccountType" value="${fixedDepositForm.depositAccountType}" />
					<form:hidden path="category" value="${customer.category}" />
					<form:hidden id="citizenCustomer" path="citizen"
						value="${customer.citizen}" />
						<input type="hidden" value="${model.productConfiguration.isNomineeMandatory}" id="isNomineeMandatory" />
						<input type="hidden" value="${model.productConfiguration.paymentModeOnMaturity}" id="paymentModeOnMaturity" />
					<input type="hidden" name="customerDetails" value='${customerDetails}'/>

					<form:hidden path="customer.id" />
					

					<div class="header_customer  col-sm-12 col-md-12 col-lg-12"
						data-toggle="collapse" data-target="#primaryDetails"
						style="border-bottom: 1px dotted #05007e; margin-bottom: 10px; padding-top: 7px; padding-bottom: 7px;">
						<h3>
							<b>Deposit Details</b><i
								class="fa fa-chevron-circle-down pull-right" aria-hidden="true"></i>
						</h3>
						<span id="depositOk"
							style="display: none; color: #14c514; margin-right: 25px; margin-top: -12px; float: right;"
							class="glyphicon glyphicon-ok"></span> <span id="depositCross"
							style="display: none; color: red; margin-right: 25px; margin-top: -12px; float: right;"
							class="glyphicon glyphicon-remove"></span>
					</div>

					<div id="primaryDetails" class="collapse in">
						<div class="Success_msg">
							<div class="successMsg" style="text-align: center; color: red;">
								${error}</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label class="col-md-4 control-label">Customer Name</label>
								<div class="col-md-6">
									<form:input path="id" class="form-control"
										value="${customer.customerName}" readonly="true"
										style="width:242px;" />

								</div>
							</div>


							<c:if test="${customer.citizen=='NRI'}">
								<div class="form-group" style="clear: both;" id="accountTypeDIV">
									<label class="col-md-4 control-label"><spring:message
											code="label.nriAccountType" /><span style="color: red">*</span></label>

									<div class="col-md-6">
										<form:select id="nriAccountType" path="nriAccountType"
											class="input form-control"
											onchange="accountTypeVal(this.value)">
											<form:option value="">Select</form:option>
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
							</c:if>
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.typeOfDeposit" /></label>
								<div class="col-md-6">
									<label >${fixedDepositForm.depositAccountType}</label>
								</div>
							</div>
							<div id="linkedAccountDiv">
								<div class="form-group">
									<label class="col-md-4 control-label"><spring:message
											code="label.accountNo" /></label>
									<div class="col-md-6">
										<form:select path="accountNo" class="form-control"
											id="accountNo" onchange="onChangeAccountNo(this.value)">
											<form:option value="">Select</form:option>
											<c:forEach items="${fixedDepositForm.accountList}"
												var="account">


												<option
													value="${account.accountNo},${account.accountType},${account.accountBalance}" selected="true">${account.accountNo}
												</option>
											</c:forEach>
										</form:select>
									</div>

								</div>
								<c:if test="${! empty fixedDepositForm.accountList}">
									<div class="form-group" id="accountTypeDiv">
										<label class="col-md-4 control-label"><spring:message
												code="label.accountType" /></label>
										<div class="col-md-6">
											<form:input path="accountType" value="" class="form-control"
												id="accountType" readonly="true" />
										</div>
									</div>


									<div class="form-group" id="accountBalanceDiv">
										<label class="col-md-4 control-label"><spring:message
												code="label.accountBalance" /></label>
										<div class="col-md-6">
											<form:hidden path="accountBalance" id="accountBalance1" />
											<input class="form-control" id="accountBalance" readonly />


										</div>

									</div>
								</c:if>

							</div>
					
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.selectFormat" /><span style="color: red">*</span></label>
								<div class="col-md-6">
								<input type="hidden" name="paymentType" id="paymentType1" />
									<form:select path="" class="input form-control"
										id="paymentType" onchange="displayDaysInTenure()">
										<form:option value="">
											Select
										</form:option>
										<c:if test="${productConfiguration.productType == 'Regular Deposit' || productConfiguration.productType == 'Tax Saving Deposit'}">
										<form:option value="One-Time">
											<spring:message code="label.oneTimePayment" />
										</form:option>
										</c:if>
										<c:if test="${productConfiguration.productType == 'Recurring Deposit'}">
										<form:option value="Monthly">
											<spring:message code="label.monthly" />
										</form:option>
										<form:option value="Quarterly">
											<spring:message code="label.quarterly" />
										</form:option>
										<form:option value="Half Yearly">
											<spring:message code="label.halfYearly" />
										</form:option>
										<form:option value="Annually">Yearly
								        </form:option>
										</c:if>
									</form:select>

								</div>
							</div>

							<div class="form-group" id="maturityIns">
								<label class="col-md-6 control-label" for=""
									style="margin-left: -88px;">Maturity Instruction<span
									style="color: red">*</span>
								</label>

								<div class="col-md-6">
									<form:select id="maturityInstructionId"
										path="maturityInstruction" class="form-control"
										required="true">
										<form:option value="select">
											<spring:message code="label.select" />
										</form:option>

										<form:option selected="true" value="autoRenew">
											<spring:message code="label.autoRenew" />
										</form:option>
										<form:option value="repay">
											<spring:message code="label.repayPricipalAndInterest" />
										</form:option>

										<form:option value="autoRenewPrincipalAndRepayInterest">
											<spring:message
												code="label.autoRenewPrincipalAndRepayInterest" />
										</form:option>

										<form:option value="autoRenewInterestAndRepayPrincipal">
											<spring:message
												code="label.autoRenewInterestAndRepayPrincipal" />
										</form:option>

									</form:select>
								</div>


							</div>

							<div class="form-group" id="branchDIV">
								<label class="col-md-6 control-label" for=""
									style="margin-left: -90px;"> <spring:message
										code="label.branchCode" /><span style="color: red">*</span>
								</label>


								<div class="col-md-6">
									<form:input type="text" id="branchCode"
										path="branchCode" class="form-control"
										required="true" readonly="true" value="${branchCode} - ${branchName}"/>

								</div>
							</div>
                            <div class="form-group" id = 'areaDIV'>
								<label class="col-md-4 control-label">Area<span style="color: red">*</span></label>
								<div class="col-md-6">
									
									<form:input path="depositArea" type="text"
										class="input form-control" id="depositArea" required="true" 
										placeholder="Enter Deposit Area" />


								</div>
							</div>
							
							<div class="form-group">
								<label class="col-md-4 control-label">Account Access Type<span style="color: red">*</span></label>
								<div class="col-md-6">
									
									<form:select id="accountAccessType"
										path="accountAccessType" class="form-control"
										required="true">
										<form:option value="select" selected="true">
											<spring:message code="label.select" />
										</form:option>
										<form:option value="EitherOrSurvivor">
											Either Or Survivor
										</form:option>
										<form:option value="AnyoneOrSurvivor">
											Anyone Or Survivor
										</form:option>
										<form:option value="FormerOrSurvivor">
											Former Or Survivor
										</form:option>
										<form:option value="LatterOrSurvivor">
											Latter Or Survivor
										</form:option>
										<form:option value="Jointly">
											Jointly
										</form:option>
										<form:option value="JointlyOrSurvivor">
											Jointly Or Survivor
										</form:option>
										
																		
                                     </form:select>

								</div>
							</div>
						</div>

						<div class="col-md-6">

							<div class="form-group">
								<label class="col-md-4 control-label" style="padding-top: 2px;"><spring:message
										code="label.fdAmount" /><span style="color: red">*</span></label>
								<div class="col-md-6">
									<fmt:formatNumber value="${fixedDepositForm.fdAmount}"
										pattern="#.##" var="fdAmount" />
									<form:input path="fdAmount" class="input form-control"
										value="${fdAmount}"
										onkeyup="onchangeDepositAmount(this.value)"
										style="background: #fff; cursor:text;" id="fdAmount"
										onkeypress="validationAccount(event)" />


								</div>
							</div>

							<div class="form-group" style="clear: both;">
								<label class="col-md-4 control-label"><spring:message
										code="label.currency" /><span style="color: red">*</span></label>

								<div class="col-md-6">
									<form:select id="currency" path="currency"
										class="input form-control">
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

							<div class="form-group" style="clear: both;">
								<label class="col-md-4 control-label"><spring:message
										code="label.fdDate" /><span style="color: red">*</span></label>
								<div class="col-md-6">
									<form:input path="fdDeductDate" value="${todaysDate}"
										placeholder="Select Date" class="form-control datepicker-here"
										style="background: whitesmoke; cursor: pointer;"
										id="datepicker" readonly="true" />

								</div>
							</div>
<c:if test="${productConfiguration.productType == 'Recurring Deposit'}">
							<div class="form-group">
								<label class="col-md-4 control-label">Amount Deducting
									Day<span style="color: red">*</span>
								</label>
								<div class="col-md-3">
									<form:select path="deductionDay" class="form-control"
										id="deductionDay">
										<option value="">Day</option>
										<%
											for (int i = 1; i <= 30; i++) {
										%>
										<option value="<%=i%>"><%=i%></option>
										<%
											}
										%>

									</form:select>
								</div>
							</div>
</c:if>
<div class="form-group">
							<label class="col-md-4 control-label">Tenure Years<span style="color: red">*</span></label>
							<div class="col-md-6">
							<input name="totalTenureInDays" id="fdTenure" type="hidden">
							<form:hidden path="tenureInYears" id="tenureInYears"/>
							
							<input name="tenureInMonths" id="tenureInMonths" type="hidden">
							<input name="tenureInDays" id="tenureInDays" type="hidden">
								<select class="input form-control" id="fdTenureYears">
<option value="">Select</option><option value="1">1</option>
<option value="2">2</option>
<option value="3">3</option>
<option value="4">4</option>
<option value="5">5</option>
<option value="6">6</option>
<option value="7">7</option>
<option value="8">8</option>
<option value="9">9</option>
<option value="10">10</option>

</select>
							</div>

						</div>
						
						<div class="form-group">
							<label class="col-md-4 control-label">Tenure Months<span style="color: red">*</span></label>

<div class="col-md-6">
<select class="input form-control" id="fdTenureMonths">
<option value="">Select</option>
<option value="1">1</option>
<option value="2">2</option>
<option value="3">3</option>
<option value="4">4</option>
<option value="5">5</option>
<option value="6">6</option>
<option value="7">7</option>
<option value="8">8</option>
<option value="9">9</option>
<option value="10">10</option>
<option value="11">11</option>
<option value="12">12</option>
</select>
								
							</div>

							


						</div>
												<div class="form-group">
							<label class="col-md-4 control-label">Tenure Days<span style="color: red">*</span></label>

<div class="col-md-6">
								<select class="input form-control" id="fdTenureDays">
								<option value="">Select</option>
<option value="1">1</option>
<option value="2">2</option>
<option value="3">3</option>
<option value="4">4</option>
<option value="5">5</option>
<option value="6">6</option>
<option value="7">7</option>
<option value="8">8</option>
<option value="9">9</option>
<option value="10">10</option>
<option value="11">11</option>
<option value="12">12</option>
<option value="13">13</option>
<option value="14">14</option>
<option value="15">15</option>
<option value="16">16</option>
<option value="17">17</option>
<option value="18">18</option>
<option value="19">19</option>
<option value="20">20</option>
<option value="21">21</option>
<option value="22">22</option>
<option value="23">23</option>
<option value="24">24</option>
<option value="25">25</option>
<option value="26">26</option>
<option value="27">27</option>
<option value="28">28</option>
<option value="29">29</option>
<option value="30">30</option>
<option value="31">31</option>
</select>
							</div>
</div>
							<%-- <div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.typeOfTenure" /><span style="color: red">*</span></label>
								<div class="col-md-6">
									<form:select path="fdTenureType" class="input form-control"
										id="fdTenureType" onchange="showTenureDetails(this.value)">
										<form:option value="">
											<spring:message code="label.selectValue" />
										</form:option>
										<form:option value="Day">
											<spring:message code="label.Day" />
										</form:option>
										<form:option value="Month">
											<spring:message code="label.Month" />
										</form:option>
										<form:option value="Year">
											<spring:message code="label.Year" />
										</form:option>
									</form:select>

								</div>
							</div> --%>

							<%-- <div class="form-group">
								<label class="col-md-4 control-label"> <span id="dayId"
									style="display: none"><spring:message code="label.days" /><span
										style="color: red">*</span></span><span id="monthId"
									style="display: none"><spring:message code="label.month" /><span
										style="color: red">*</span></span> <span id="yearId"
									style="display: none"><spring:message code="label.Year" /><span
										style="color: red">*</span></span></label>
								<div class="col-md-2">
									<form:input path="fdTenure" class="input form-control"
										id="fdTenure" onkeypress="validationAccount(event)"
										type="number" min="0" />

								</div>
								<div id="dayValue">
									<label class="col-md-2 control-label"
										style="text-align: right; padding-left: 45px;"><spring:message
											code="label.days" /></label>
									<div class="col-md-2">
										<form:input path="daysValue" type="number" min="0"
											class="input form-control" id="fdDayValue"
											onkeypress="validationAccount(event)" />
										<span id="dayValueError" style="display: none; color: red">Please
											enter Days</span>

									</div>

								</div>
							</div> --%>


							<div class="form-group" id="taxSavingDepositId"
							style="display: none">
							<label class="col-md-4 control-label" style="margin-top: 7px;"><spring:message
									code="label.taxSavingDeposit" /></label>
<c:if test="${productConfiguration.productType != 'Tax Saving Deposit'}">
							<div class="col-md-2">
								<form:checkbox path="taxSavingDeposit" value="1"
									class="form-control" id="taxSavingDeposit"
									style=" width: 16px;" onclick="checkFunction()" />
							</div>
							</c:if>
							<c:if test="${productConfiguration.productType == 'Tax Saving Deposit'}">
							<div class="col-md-2">
								<form:checkbox path="taxSavingDeposit" value="1"
									class="form-control" id="taxSavingDeposit"
									style=" width: 16px;" checked="checked" onclick="checkFunction()" />
							</div>
							</c:if>
						</div>

						</div>
						<input type="button" onclick="resetBasicDetails()"
							class="btn btn-warning pull-right" value="Reset Deposit Details"
							style="margin-top: -5px; margin-right: 9px; margin-bottom: 15px" />


					</div>

					<!-- *************************************************Joint Account holder details*********************************************************** -->


					<c:if test="${!empty customerDetailsParsers}">

						<div class="header_customer  col-sm-12 col-md-12 col-lg-12"
							data-toggle="collapse" data-target="#secondaryDetails"
							style="border-bottom: 1px dotted #05007e; margin-bottom: 10px; padding-top: 7px; padding-bottom: 7px;">
							<h3>
								<b>Secondary Account Holder Information</b><i
									class="fa fa-chevron-circle-down pull-right" aria-hidden="true"></i>
							</h3>
							<span id="accountHolderOk"
								style="display: none; color: #14c514; margin-right: 25px; margin-top: -12px; float: right;"
								class="glyphicon glyphicon-ok"></span><span
								id="accountHolderCross"
								style="display: none; color: red; margin-right: 25px; margin-top: -12px; float: right;"
								class="glyphicon glyphicon-remove"></span>
						</div>
						<div id="secondaryDetails" class="collapse in">
							<div style="clear: both;"></div>
							<table class="table table-bordered table-striped table-hover "
								align="center" id="my-table"  style="width: 95%; margin-left: 20px;">
								<thead>
									<tr class="success">
										<th width="3px">Serial</th>
										<th width="5px">Name</th>
										<th width="3px">Contact</th>
										<th width="3px">Gender</th>
										<th width="3px">Email</th>
										<th width="20px">Address</th>
										<th width="3px">Relationship</th>
										<!-- <th width="1px">Remove</th> -->

									</tr>
								</thead>
								<tbody>
									<c:forEach items="${customerDetailsParsers}"
										var="jointAcc" varStatus="status">
										<tr>
													<td> <c:out value="${jointAcc.id}"/></td>
													<td> <c:out value="${jointAcc.customerName}"/></td>
													<td> <c:out value="${jointAcc.contactNum}"/></td>
													<td> <c:out value="${jointAcc.gender}"/></td>
													<td> <c:out value="${jointAcc.email}"/></td>
													<td> <c:out value="${jointAcc.address}"/></td>
													<td> <c:out value="${jointAcc.relationship}"/></td>

					 					<form:hidden path="jointAccounts[${status.index}].id" value="${jointAcc.id}"/>
										<form:hidden path="jointAccounts[${status.index}].name" value="${jointAcc.customerName}"/>
										<form:hidden path="jointAccounts[${status.index}].contactNo" value="${jointAcc.contactNum}"/>
										<form:hidden path="jointAccounts[${status.index}].gender" value="${jointAcc.gender}"/>
										<form:hidden path="jointAccounts[${status.index}].age"
											value="" />
										<form:hidden path="jointAccounts[${status.index}].email" value="${jointAcc.email}"/>
										<form:hidden path="jointAccounts[${status.index}].address" value="${jointAcc.address}"/>
										<form:hidden path="jointAccounts[${status.index}].city" />
										<form:hidden path="jointAccounts[${status.index}].pincode" />
										<form:hidden path="jointAccounts[${status.index}].state" />
										<form:hidden path="jointAccounts[${status.index}].country" />
										<form:hidden path="jointAccounts[${status.index}].relationship" value="${jointAcc.relationship}"/>
									</c:forEach>

								</tbody>
							</table>

						</div>
					</c:if>



					<!-- contribution section ...started  -->

					<div class="header_customer  col-sm-12 col-md-12 col-lg-12"
						data-toggle="collapse" data-target="#contributionDetails"
						style="border-bottom: 1px dotted #05007e; margin-bottom: 10px; padding-top: 7px; padding-bottom: 7px;">
						<h3>
							<b>Account Holders Contribution</b><i
								class="fa fa-chevron-circle-down pull-right" aria-hidden="true"></i>
						</h3>
						<span id="contributionOk"
							style="display: none; color: #14c514; margin-right: 25px; margin-top: -12px; float: right;"
							class="glyphicon glyphicon-ok"></span><span
							id="contributionCross"
							style="display: none; color: red; margin-right: 25px; margin-top: -12px; float: right;"
							class="glyphicon glyphicon-remove"></span>
					</div>
					<div id="contributionDetails" class="collapse">
						<div class="header_me col-md-12">
							<h4>
								<b>Primary Account Holder Contribution</b>
							</h4>
						</div>

						<div class="col-sm-12">

							<div class="form-group">
								<label class="col-md-4 control-label">Primary Account
									Holder</label>
								<div class="col-md-4">
									<input class="form-control" value="${customer.customerName}"
										readonly="true" />

								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label">Contribution %</label>
								<div class="col-md-4">
									<form:input type="text" step="0.01" class="input form-control"
										id="primaryCont" min="0" placeholder="Contribution in %"
										path="userContribution" onblur="estimateRemaining()"
										onkeypress="return isNumberKey1(event,$(this))" />
									<span id="primaryContributionError" style="color: red"></span>

								</div>
							</div>
						</div>

						<c:if test="${! empty customerDetailsParsers}">
							<div class="header_me col-md-12">
								<h4>
									<b>Secondary Account Holder Contribution</b>
								</h4>
							</div>
							<c:set var="jointSize"
								value="${customerDetailsParsers.size()}"></c:set>

							<c:forEach items="${customerDetailsParsers}"
								var="jointAcc" varStatus="status">
								<div class="col-sm-12" style="margin-top: 20px;">
								
								

									<div class="form-group">
										<c:set var="depositType"
											value="${fixedDepositForm.depositType}"></c:set>
										<label class="col-md-4 control-label"> Secondary
											Account Holder: ${status.index+1}
										</label>
										<div class="col-md-4">
											<input class="form-control"
												value="${jointAcc.customerName}"
												readonly />
										</div>
										
										
									</div>

									<div class="form-group">
										<label class="col-md-4 control-label">Contribution %</label>
										<div class="col-md-4">
											<form:input type="text" step="0.01"
												class="input form-control contribution"
												id="contribution[${status.index}]"
												placeholder="Contribution in %"
												path="jointAccounts[${status.index}].contributionPercent"
												onBlur="assignRemaining(${status.index})"
												onkeypress="return isNumberKey1(event,$(this))" />
											<span id="contributionError[${status.index}]"
												style="color: red"></span>
										</div>
									</div>
									
									<div class="form-group">
										<c:choose>
											<c:when test="${baseURL[1] == 'common' || baseURL[0] == 'common'}">
												<c:set var="back" value="applyOnlineFD" />
											</c:when>
											<c:otherwise>
												<c:set var="back" value="jointConsortiumDeposit" />
											</c:otherwise>
										</c:choose>

									</div>
								</div>

							</c:forEach>
						</c:if>
						<label class="col-md-4 control-label"></label>
						<div class="col-md-4">
							<span id="contributionSumError" style="display: none; color: red">Sum
								of total contribution is not 100</span>

						</div>

						<div style="clear: both;"></div>
						<input type="button"
							style="margin-top:-5px;margin-right:9px; margin-bottom:15px"
							onclick="resetContributionDetails()"
							class="btn btn-warning pull-right" value="Reset Contribution" />
						</td>

					</div>

					<!-- ...contibution section  ended	......-->



					<!-- .....nominee section  started.......... -->

					<div class="header_customer  col-sm-12 col-md-12 col-lg-12"
						data-toggle="collapse" data-target="#nomineeDetails"
						style="border-bottom: 1px dotted #05007e; margin-bottom: 10px; padding-top: 7px; padding-bottom: 7px;">
						<h3>
							<b>Nominee Details</b><i
								class="fa fa-chevron-circle-down pull-right" aria-hidden="true"></i>
						</h3>
						<span id="nomineeOk"
							style="display: none; color: #14c514; margin-right: 25px; margin-top: -12px; float: right;"
							class="glyphicon glyphicon-ok"></span><span id="nomineeCross"
							style="display: none; color: red; margin-right: 25px; margin-top: -12px; float: right;"
							class="glyphicon glyphicon-remove"></span>
					</div>

					<div id="nomineeDetails" class="collapse">
						<div class="header_me col-md-12">
							<h4>
								<b>Primary Account Holder Nominee</b>
							</h4>
						</div>
						<div class="col-md-12"
							style="border: none; margin-top: 1px; margin-bottom: 15px;">Primary
							Account Holder: ${customer.customerName}</div>
						<div class="col-md-6">


							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.nomineeName" /><span style="color: red">*</span></label>
								<div class="col-md-6">
									<form:input path="nomineeName" class="input form-control"
										required="true" id="nomineeName" onkeypress="validName(event)" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.nomineeAge" /><span style="color: red">*</span></label>
								<div class="col-md-6">
									<form:input path="nomineeAge" required="true"
										onkeyup="refresh(this.value,'gaurdian','nomineeAge','nomineePan','guardianName','guardianAge','guardianAddress','guardianRelationShip','gaurdianPan','gaurdianAadhar')"
										type="text" class="input form-control" id="nomineeAge"
										onkeypress="validationAccount(event)" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.nomineeAddress" /><span style="color: red">*</span></label>
								<div class="col-md-6">
									<form:input path="nomineeAddress" required="true"
										class="input form-control" id="nomineeAddress" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.nomineeRelationShip" /><span style="color: red">*</span></label>
								<div class="col-md-6">
									<form:input path="nomineeRelationShip" required="true"
										class="input form-control" id="nomineeRelationShip"
										onkeypress="validName(event)" />

								</div>
							</div>
							<div class="form-group" id="nomineePanCard">
								<label class="control-label col-sm-4" for="">Nominee PAN</label>
								<div class="col-sm-6">
									<form:input path="nomineePan" id="nomineePan"
										class="input form-control" style="text-transform:uppercase"
										onkeypress="panvalidation(event,this.id)"></form:input>
									<span id="contactNum2Error" class="error"
										style="display: none;"><font color="red"><spring:message
												code="label.incorrectFormat" /></font></span>
								</div>
							</div>
							<div class="successMsg">
								<b><font color="error">${errorPan}</font></b>
							</div>

							<div class="form-group">
								<label class="col-md-4 control-label">Nominee Aadhar</label>
								<div class="col-md-6">
									<form:input path="nomineeAadhar" type="text" id="nomineeAadhar"
										class="input form-control"
										onkeypress="validationAadhar(event)"></form:input>
									<span id="contactNum2Error" class="error"
										style="display: none;"><font color="red"><spring:message
												code="label.incorrectFormat" /></font></span>
								</div>
							</div>
							<div class="successMsg">
								<b><font color="error">${errorAadhar}</font></b>
							</div>


						</div>


						<div class="col-md-6" style="display: none; margin-top: -22px;"
							id="gaurdian">

							<div class="form-group">
								<label class="col-md-4 control-label"></label>
								<div class="col-md-6"></div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.guardianName" /><span style="color: red">*</span></label>
								<div class="col-md-6">
									<form:input path="guardianName" class="input form-control"
										id="guardianName" onkeypress="validName(event)" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.guardianAge" /><span style="color: red">*</span></label>
								<div class="col-md-6">
									<form:input path="guardianAge" type="text"
										class="input form-control" id="guardianAge" onkeypress="validationAccount(event)"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.guardianAddress" /><span style="color: red">*</span></label>
								<div class="col-md-6">
									<form:input path="guardianAddress" class="input form-control"
										id="guardianAddress" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.guardianRelationShip" /><span style="color: red">*</span></label>
								<div class="col-md-6">
									<form:input path="guardianRelationShip"
										class="input form-control" id="guardianRelationShip"
										onkeypress="validName(event)" />
								</div>
							</div>



							<div class="form-group">
								<label class="col-md-4 control-label">Guardian Pan</label>
								<div class="col-md-6">
									<form:input path="gaurdianPan" id="gaurdianPan"
										class="input form-control" style="text-transform:uppercase"
										onkeypress="panvalidation(event,this.id)"></form:input>
									<span id="contactNum2Error" class="error"
										style="display: none;"><font color="red"><spring:message
												code="label.incorrectFormat" /></font></span>
								</div>
							</div>

							<div class="form-group">
								<label class="col-md-4 control-label">Guardian Aadhar</label>
								<div class="col-md-6">
									<form:input path="gaurdianAadhar" type="text"
										id="gaurdianAadhar" class="input form-control"
										onkeypress="validationAadhar(event)"></form:input>
									<span id="contactNum2Error" class="error"
										style="display: none;"><font color="red"><spring:message
												code="label.incorrectFormat" /></font></span>
								</div>
							</div>


						</div>

						<!-- *************************************************Joint Account holder details*********************************************************** -->
						
						<c:if test="${!empty customerDetailsParsers}">
							<c:set var="jointSize"
								value="${customerDetailsParsers.size()}"></c:set>
								
								

							<div class="header_me col-md-12">
								<h4>
									<b>Secondary Account Holder Nominee</b>
								</h4>

							</div>
							

							<c:forEach items="${customerDetailsParsers}"
								var="jointAcc" varStatus="status">
								
								<div class="col-md-12"
									style="border: none; margin-top: 20px; margin-bottom: 15px;">Secondary
									Account Holder ${status.index+1}: ${jointAcc.customerName}</div>

								<div class=" col-md-6">


									<div class="form-group">
										<label class="col-md-4 control-label"><spring:message
												code="label.nomineeName" /><span style="color: red">*</span></label>
										<div class="col-md-6">
											<form:input
												path="jointAccounts[${status.index}].nominee.name"
												class="input form-control"
												id="nomineeNameSec[${status.index}]" required="true"
												onkeypress="validName(event)" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-4 control-label"><spring:message
												code="label.nomineeAge" /><span style="color: red">*</span></label>
										<div class="col-md-6">
											<form:input path="jointAccounts[${status.index}].nominee.age"
												type="text" required="true" class="input form-control"
												id="nomineeAgeSec[${status.index}]" onkeypress="validationAccount(event)"
												onkeyup="validationAGENomineeSec(${status.index});showGuardian(this.value,'jointGaurdian[${status.index}]','nomineeAgeSec[${status.index}]','nomineePanCard[${status.index}]','guardianName[${status.index}]','guardianAge[${status.index}]','guardianAddress[${status.index}]','guardianRelationShip[${status.index}]','gaurdianPan[${status.index}]','gaurdianAadhar[${status.index}]','nomineePan[${status.index}]')" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-4 control-label"><spring:message
												code="label.nomineeAddress" /><span style="color: red">*</span></label>
										<div class="col-md-6">
											<form:input
												path="jointAccounts[${status.index}].nominee.address"
												class="input form-control"
												id="nomineeAddress[${status.index}]" required="true" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-4 control-label"><spring:message
												code="label.nomineeRelationShip" /><span style="color: red">*</span></label>
										<div class="col-md-6">
											<form:input
												path="jointAccounts[${status.index}].nominee.relationship"
												class="input form-control"
												id="nomineeRelationShip[${status.index}]" required="true"
												onkeypress="validName(event)" />
										</div>
									</div>

									<div class="form-group" id="nomineePanCard[${status.index}]">
										<label class="col-md-4 control-label">Nominee PAN</label>
										<div class="col-md-6">
											<form:input
												path="jointAccounts[${status.index}].nominee.panNo"
												class="input form-control" id="nomineePan[${status.index}]"
												required="true" style="text-transform:uppercase"
												onkeypress="panvalidationIndexNomine(event,${status.index})" />
										</div>
									</div>

									<div class="form-group">
										<label class="col-md-4 control-label">Nominee Aadhar</label>
										<div class="col-md-6">
											<form:input
												path="jointAccounts[${status.index}].nominee.aadharNo"
												class="input form-control" type="text"
												id="nomineeAadhar[${status.index}]" required="true"
												onkeypress="validationAadhar(event)" />
										</div>
									</div>



								</div>


								<div class="col-md-6" style="display: none; margin-top: -22px;"
									id="jointGaurdian[${status.index}]">

									<div class="form-group">
										<label class="col-md-4 control-label"></label>
										<div class="col-md-6"></div>
									</div>
									<div class="form-group">
										<label class="col-md-4 control-label"><spring:message
												code="label.guardianName" /><span style="color: red">*</span></label>
										<div class="col-md-6">
											<form:input
												path="jointAccounts[${status.index}].nominee.gaurdianName"
												class="input form-control"
												id="guardianName[${status.index}]"
												onkeypress="validName(event)" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-4 control-label"><spring:message
												code="label.guardianAge" /><span style="color: red">*</span></label>
										<div class="col-md-6">
											<form:input
												path="jointAccounts[${status.index}].nominee.gaurdianAge"
												type="text" class="input form-control"
												id="guardianAge[${status.index}]"
												onkeyup="validationAGE(${status.index})" onkeypress="validationAccount(event)"/>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-4 control-label"><spring:message
												code="label.guardianAddress" /><span style="color: red">*</span></label>
										<div class="col-md-6">
											<form:input
												path="jointAccounts[${status.index}].nominee.gaurdianAddress"
												class="input form-control"
												id="guardianAddress[${status.index}]" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-4 control-label"><spring:message
												code="label.guardianRelationShip" /><span
											style="color: red">*</span></label>
										<div class="col-md-6">
											<form:input
												path="jointAccounts[${status.index}].nominee.gaurdianRelation"
												class="input form-control"
												id="guardianRelationShip[${status.index}]"
												onkeypress="validName(event)" />
											<!-- pattern="[a-zA-Z ]+"  -->
										</div>
									</div>

									<div class="form-group">
										<label class="col-md-4 control-label">Gaurdian Pan</label>
										<div class="col-md-6">
											<form:input
												path="jointAccounts[${status.index}].nominee.gaurdianPanNo"
												class="input form-control" id="gaurdianPan[${status.index}]"
												style="text-transform:uppercase"
												onkeypress="panvalidationIndex(event,${status.index})" />
											<!-- pattern="[a-zA-Z ]+"  -->
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-4 control-label">Gaurdian Aadhar</label>
										<div class="col-md-6">
											<form:input
												path="jointAccounts[${status.index}].nominee.gaurdianAadharNo"
												class="input form-control" type="text"
												id="gaurdianAadhar[${status.index}]"
												onkeypress="validationAadhar(event)" />
											<!-- pattern="[a-zA-Z ]+"  -->
										</div>
									</div>

								</div>

							</c:forEach>
						</c:if>
						<div style="clear: both;"></div>
						<input type="button"
							style="margin-top: 15px; margin-right: 9px; margin-bottom: 15px"
							onclick="resetNomineeDetails()"
							class="btn btn-warning pull-right" value="Reset Nominee Details" />
						</td>

					</div>
					<!-- ..........nominee section ended........ -->


					<!-- ..........payoff section started........ -->

					<div class="header_customer  col-sm-12 col-md-12 col-lg-12"
						data-toggle="collapse" data-target="#payOffDetails"
						style="border-bottom: 1px dotted #05007e; margin-bottom: 10px; padding-top: 7px; padding-bottom: 7px;">
						<h3>
							<b><spring:message code="label.payOffDetails" /> (OPTIONAL)</b><i
								class="fa fa-chevron-circle-down pull-right" aria-hidden="true"></i>
						</h3>
						<span id="payoffOk"
							style="display: none; color: #14c514; margin-right: 25px; margin-top: -12px; float: right;"
							class="glyphicon glyphicon-ok"></span><span id="payoffCross"
							style="display: none; color: red; margin-right: 25px; margin-top: -12px; float: right;"
							class="glyphicon glyphicon-remove"></span>
					</div>
					<div id="payOffDetails" class="collapse">

						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.payOffInterestType" /></label>
							<div class="col-md-2">
								<form:select path="payOffInterestType"
									class="input form-control" style=" cursor: pointer;"
									id="payOffInterestType"
									onchange="onChangePayOffInterestType(this.value)">
									<form:option value="">
										<spring:message code="label.selectValue" />
									</form:option>
									<form:option style="display:none" value="One-Time">
										<spring:message code="label.oneTimePayment" />
									</form:option>
									<form:option value="Monthly" id="days">
										<spring:message code="label.fdPayOffType1" />
									</form:option>
									<form:option value="Quarterly" id="quarterly">
										<spring:message code="label.fdPayOffType3" />
									</form:option>
									<form:option value="Semiannual" id="semi">
										<spring:message code="label.fdPayOffType4" />
									</form:option>
									<form:option value="Annual" id="annual">
										<spring:message code="label.fdPayOffType5" />
									</form:option>
									<form:option value="End of Tenure">
										<spring:message code="label.fdPayOffType2" />
									</form:option>
								</form:select>
							</div>
						</div>

						<div class="form-group" id="oneTimeDatePayoff"
							style="display: none;">
							<label class="col-md-2 control-label">One Time PayOff
								Interest Date<span style="color: red">*</span>
							</label>
							<div class="col-md-7">
								<form:input path="payoffDate" id="oneTimePayOffDate"
									readonly="true" placeholder="Select Date"
									class="form-control datepicker-here" />

							</div>
						</div>


						<div id="showPartPercent">
							<div class="form-group">
								<label class="col-md-4 control-label">Primary User</label>
								<div class="col-md-2">
									<b><c:out value="${customer.customerName}" /></b>
								</div>
							</div>

							<div class="form-group" id="everyMonthDiv">
								<label class="col-md-4 control-label"><spring:message
										code="label.interestType" /><span style="color: red">*</span></label>
								<div class="col-md-8">
									<label for="radio"><form:radiobutton
											path="interstPayType" value="PART" id="partInterest"
											onclick="displayDiv(this.value)"></form:radiobutton> </label>
									<spring:message code="label.interestType2" />
									<label for="radio"><form:radiobutton
											path="interstPayType" value="PERCENT" id="percentInterest"
											onclick="displayDiv(this.value)"></form:radiobutton> </label>Percentage
								</div>
							</div>
						</div>

						<div id="partPercentValueDiv">
							<div class="form-group" id="interestPercentTr">
								<label class="col-md-4 control-label">Percentage<span
									style="color: red">*</span></label>
								<div class="col-md-6">
									<form:input path="interestPercent" type="text" min="1"
										max="100" class="input form-control"
										onkeyup="validatePercent(this.value,this.id)"
										placeholder="Enter Percentage" id="interestPercent"
										onkeypress="return isNumberKey1(event,$(this))" />
								</div>
							</div>
							<div class="form-group" id="firstPartInterestAmtTr">
								<label class="col-md-4 control-label"><spring:message
										code="label.amount" /><span style="color: red">*</span></label>
								<div class="col-md-6">
									<form:input path="interestPayAmount" type="text" min="0"
										placeholder="Enter Interest Amount" class="input form-control"
										onkeyup="accountPart(event,this.id)" id="interestPayAmount"
										onkeypress="return isNumberKey1(event,$(this))" />
								</div>
							</div>

						</div>
						<div id="sameBankDifferentBankDiv">
							<label class="col-md-4 control-label"><spring:message
									code="label.payOffAccount1" /><span style="color: red">*</span></label>
							<div class="col-md-6">

								<label for="radio"> <form:radiobutton
										path="fdPayOffAccount" value="Saving Account" id="sameBankAcc"
										onclick="showSameBankInfo()"></form:radiobutton></label>
								<spring:message code="label.savingAccount" />
								&nbsp; <label for="radio"> <form:radiobutton
										path="fdPayOffAccount" value="Other" id="otherBankAcc"
										onclick="showDiffBankInfo()"></form:radiobutton></label>
								<spring:message code="label.otherBan" />
							</div>

							<div id="beneficiaryDetails">
								<div class="form-group">
									<label class="col-md-4 control-label"><spring:message
											code="label.name" /><span style="color: red">*</span></label>
									<div class="col-md-6">
										<form:input path="otherName" placeholder="Enter Name"
											class="input form-control" onkeypress="validName(event)" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label"><spring:message
											code="label.accountNo" /><span style="color: red">*</span></label>
									<div class="col-md-6">
										<form:input path="otherAccount" placeholder="Enter AccountNo"
											class="input form-control"
											onkeypress="validationAccount(event)" />
									</div>
								</div>
							</div>
						</div>


						<div id="payOffOtherDetails">

							<div class="form-group" id="transferMode">
								<label class="col-md-4 control-label"><spring:message
										code="label.transfer" /><span style="color: red">*</span></label>
								<div class="col-md-8">
									<label for="radio"><form:radiobutton
											path="otherPayTransfer" value="NEFT" id="neft"></form:radiobutton></label>
									<spring:message code="label.transfer1" />
									<label for="radio"><form:radiobutton
											path="otherPayTransfer" value="IMPS" id="imps"></form:radiobutton></label>
									<spring:message code="label.transfer2" />
									<label for="radio"><form:radiobutton
											path="otherPayTransfer" value="RTGS" id="rtgs"></form:radiobutton></label>
									<spring:message code="label.transfer3" />
								</div>
							</div>

							<div id="otherBankDetails">
								<div class="form-group">
									<label class="col-md-4 control-label"><spring:message
											code="label.bank" /><span style="color: red">*</span></label>
									<div class="col-md-6">
										<form:input path="otherBank" placeholder="Enter Bank Name"
											class="input form-control" onkeypress="validName(event)" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label"><spring:message
											code="label.ifsc" /><span style="color: red">*</span></label>
									<div class="col-md-6">
										<form:input path="otherIFSC" placeholder="Enter IFSC Code"
											class="input form-control" onkeypress="validIFSC(event)" />
									</div>
								</div>
							</div>


						</div>
						<!--************************** For Joint Account Holders ************************** -->
						<div class="col-md-12">

							<div class="joint_ac" style="display:none" id ="joint_acDIV">
								<c:forEach items="${customerDetailsParsers}"
								var="jointAcc" varStatus="status">
								 
									<hr>

									<div id="showPartPercent[${status.index}]">

										<div class="form-group">
											<label class="col-md-4 control-label">Joint Account
												User</label>
											<div class="col-md-8">
												<b><c:out value="${jointAcc.customerName}" /></b>
											</div>
										</div>

										<div class="form-group"
											id="jointEveryMonthDiv[${status.index}]">
											<label class="col-md-4 control-label"><spring:message
													code="label.interestType" /><span style="color: red">*</span></label>
											<div class="col-md-8">
												<label><form:radiobutton
														path="jointAccounts[${status.index}].contributions.paymentType"
														value="PART" id="jointPartInterest[${status.index}]"
														onclick="onClickJointPartPercent(${status.index},this.value)"></form:radiobutton>
												</label>
												<spring:message code="label.interestType2" />
												<form:radiobutton
													path="jointAccounts[${status.index}].contributions.paymentType"
													value="PERCENT" id="jointPercentInterest[${status.index}]"
													onclick="onClickJointPartPercent(${status.index},this.value)"></form:radiobutton>
												Percentage
												<%-- <span style="display: none"> <form:radiobutton
															path="jointAccounts[${status.index}].contributions.paymentType"
															checked="false"></form:radiobutton>
													</span>  --%>
											</div>
										</div>


										<div class="form-group"
											id="jointPartInterestAmt[${status.index}]">
											<label class="col-md-4 control-label"><spring:message
													code="label.amount" /><span style="color: red">*</span></label>
											<div class="col-md-6">
												<form:input type="text" min="1"
													path="jointAccounts[${status.index}].contributions.interestAmtPart"
													class="input form-control account"
													onkeyup="accountId(event,${status.index})"
													id="interestPayAmount[${status.index}]"
													onkeypress="return isNumberKey1(event,$(this))" />
											</div>
										</div>

										<div id="partPercentValueDiv[${status.index}]">
											<div class="form-group"
												id="jointInterestPercent[${status.index}]">
												<label class="col-md-4 control-label">Placement of
													Percentage<span style="color: red">*</span>
												</label>
												<div class="col-md-6">
													<form:input
														path="jointAccounts[${status.index}].contributions.interestPercentage"
														type="text" min="1" max="100"
														onkeyup="validatePercent(this.value,'jointIntPercent[${status.index}]')"
														name="jointAccPercent"
														class="input form-control percentage"
														placeholder="Enter Percentage"
														id="jointIntPercent[${status.index}]"
														onkeypress="return isNumberKey1(event,$(this))" />
												</div>
											</div>

										</div>

										<label class="col-md-4 control-label"><spring:message
												code="label.payOffAccount1" /><span style="color: red">*</span></label>
										<div class="col-md-8">

											<label for="radio"> <form:radiobutton
													path="jointAccounts[${status.index}].contributions.payOffAccPartOne"
													value="Saving Account"
													id="jointSameBankAcc[${status.index}]"
													onclick="onClickJointSameDiffBank(this.value,${status.index})"></form:radiobutton></label>
											<spring:message code="label.savingAccount" />
											<label for="radio"> <form:radiobutton
													path="jointAccounts[${status.index}].contributions.payOffAccPartOne"
													value="Other" id="jointOtherBankAcc[${status.index}]"
													onclick="onClickJointSameDiffBank(this.value,${status.index})"></form:radiobutton></label>
											<spring:message code="label.otherBan" />
										</div>

										<div class="form-group">
											<label class="col-md-4 control-label"><spring:message
													code="label.name" /><span style="color: red">*</span></label>
											<div class="col-md-6">
												<form:input
													path="jointAccounts[${status.index}].contributions.beneficiaryOne"
													id="jointAccounts[${status.index}].contributions.beneficiaryOne"
													placeholder="Enter Name" class="input form-control"
													onkeypress="validName(event)" />
											</div>
										</div>
										<div class="form-group">
											<label class="col-md-4 control-label"><spring:message
													code="label.accountNo" /><span style="color: red">*</span></label>
											<div class="col-md-6">
												<form:input
													path="jointAccounts[${status.index}].contributions.beneficiaryAccOne"
													id="jointAccounts[${status.index}].contributions.beneficiaryAccOne"
													placeholder="Enter AccountNo" class="input form-control"
													onkeypress="validationAccount(event)" />
											</div>
										</div>
									</div>
									<div id="payOffOtherDetails[${status.index}]">



										<div class="form-group">
											<label class="col-md-4 control-label"><spring:message
													code="label.transfer" /><span style="color: red">*</span></label>
											<div class="col-md-8">
												<label for="radio"><form:radiobutton
														path="jointAccounts[${status.index}].contributions.transferModeOne"
														value="NEFT" id="neft[${status.index}]"></form:radiobutton></label>
												<spring:message code="label.transfer1" />
												&nbsp;&nbsp; <label for="radio"><form:radiobutton
														path="jointAccounts[${status.index}].contributions.transferModeOne"
														value="IMPS" id="imps[${status.index}]"></form:radiobutton></label>
												<spring:message code="label.transfer2" />
												<label for="radio"><form:radiobutton
														path="jointAccounts[${status.index}].contributions.transferModeOne"
														value="RTGS" id="rtgs[${status.index}]"></form:radiobutton></label>
												<spring:message code="label.transfer3" />
											</div>
										</div>


										<div class="form-group">
											<label class="col-md-4 control-label"><spring:message
													code="label.bank" /><span style="color: red">*</span></label>
											<div class="col-md-6">
												<form:input
													path="jointAccounts[${status.index}].contributions.beneficiaryBankOne"
													id="jointAccounts[${status.index}].contributions.beneficiaryBankOne"
													placeholder="Enter Bank Name" class="input form-control"
													onkeypress="validName(event)" />
											</div>
										</div>
										<div class="form-group">
											<label class="col-md-4 control-label"><spring:message
													code="label.ifsc" /><span style="color: red">*</span></label>
											<div class="col-md-8">
												<form:input
													path="jointAccounts[${status.index}].contributions.beneficiaryIFSCOne"
													id="jointAccounts[${status.index}].contributions.beneficiaryIFSCOne"
													placeholder="Enter IFSC Code" class="input form-control"
													onkeypress="validIFSC(event)" />
											</div>
										</div>
									</div>

								</c:forEach>
							</div>

						</div>
<div></div>
						<input type="button" onclick="resetPayoffDetails()"
							style="margin-bottom: 9px; margin-right: 9px;"
							class="btn btn-warning pull-right" value="Reset PayOff Details  " />

					</div>
<!-- ...................payoff section ended..................... -->



<!-- ..........maturity section started........ -->

					<div class="header_customer  col-sm-12 col-md-12 col-lg-12"
						data-toggle="collapse" data-target="#maturityDetails"
						style="border-bottom: 1px dotted #05007e; margin-bottom: 10px; padding-top: 7px; padding-bottom: 7px;">
						<h3>
							<b>Fund transfer on maturity</b><i
								class="fa fa-chevron-circle-down pull-right" aria-hidden="true"></i>
						</h3>
						<span id="maturityOk"
							style="display: none; color: #14c514; margin-right: 25px; margin-top: -12px; float: right;"
							class="glyphicon glyphicon-ok"></span><span id="maturityCross"
							style="display: none; color: red; margin-right: 25px; margin-top: -12px; float: right;"
							class="glyphicon glyphicon-remove"></span>
					</div>
					
					<div id="maturityDetails" class="collapse">
					<div class="form-group">
								<label class="col-md-4 control-label">Primary Holder</label>
								<div class="col-md-8">
									<b><c:out value="${customer.customerName}" /></b>
								</div>
							</div>
						<div class="form-group" id="everyMonthInt">
							<label class="col-md-4 control-label" for="radio"
								style="margin-top: 10px;">Transfer To Linked Account</label>
							<div class="col-sm-8">
							<c:if test="${! empty fixedDepositForm.accountList}">
								 <label id="fd1" for="radio"> <form:radiobutton
										path="isMaturityDisbrsmntInLinkedAccount" id="inLinked"
										name="isMaturityDisbrsmntInLinkedAccount" value="1"
										onclick="inLinkedAccount()" class="isLinked" checked="checked"></form:radiobutton>
									Yes </label>
									</c:if>
									<label id="fd3"
									for="radio"> <form:radiobutton
										path="isMaturityDisbrsmntInLinkedAccount" id="notInLinked"
										name="isMaturityDisbrsmntInLinkedAccounts" onclick="notInLinkedAccount()" value="0"
										></form:radiobutton>
									No	</label> </div>
						</div>
						
						<input type="hidden" path="isLinkedAccount" id="isLinkedAccount" value="1" />

						

						<div class="form-group" id="payoffAccountTypeMr" style="display:none;">
							<label class="control-label col-sm-4" for=""
								style="margin-top: 10px;"><spring:message
									code="label.payOffAccount1" /></label>
							<div class="col-sm-8">
								
								<form:radiobutton path="isMaturityDisbrsmntInSameBank"
									onclick="showDetailsSameBankM()" id="savingBankRadioMr"
									value="1"></form:radiobutton>
								<spring:message code="label.savingAccount" />
								<form:radiobutton path="isMaturityDisbrsmntInSameBank"
									onclick="showDetailsDiffBankM()" id="differentBankRadioMr"
									value="0"></form:radiobutton>
								<spring:message code="label.otherBan" />

							</div>
						</div>
						<div class="form-group" id="beneficiaryDivMr"
							style="display: none;">
							<label class="control-label col-sm-4" for=""><spring:message
									code="label.benificiaryName" /> <span style="color: red">*</span>
							</label>
							<div class="col-sm-8">
								<form:input path="maturityDisbrsmntAccHolderName" onkeypress="validName(event)" value="" class="input form-control"
									style="text: pointer;" id="beneficiaryNameMr" />
							</div>
						</div>
						<div class="form-group" id="accountNoMr" style="display: none;">
							<label class="control-label col-sm-4" for=""><spring:message
									code="label.accno" /> <span style="color: red">*</span> </label>
							<div class="col-sm-8">
								<form:input path="maturityDisbrsmntAccNumber" type="text"
									class="input form-control" onkeypress="validationAccount(event)" id="beneficiaryAccountMr" />
							</div>
						</div>

						<div class="form-group" id="transferModeMr" style="display: none;">
							<label class="control-label col-sm-4" for=""
								style="margin-top: 7px;"><spring:message
									code="label.transfer" /><span style="color: red">*</span></label>
							<div class="col-sm-8">
								<label for="radio"><form:radiobutton
										path="maturityDisbrsmntTransferType" id="neftMr" value="NEFT"></form:radiobutton></label>
								<spring:message code="label.transfer1" />
								<label for="radio"><form:radiobutton
										path="maturityDisbrsmntTransferType" id="impsMr" value="IMPS"></form:radiobutton></label>
								<spring:message code="label.transfer2" />
								<label for="radio"><form:radiobutton
										path="maturityDisbrsmntTransferType" id="rtgsMr" value="RTGS"></form:radiobutton></label>
								<spring:message code="label.transfer3" />
							</div>
						</div>
						<div class="form-group" id="bankNameMr" style="display: none;">
							<label class="control-label col-sm-4" for=""><spring:message
									code="label.bankName" /><span style="color: red">*</span></label>
							<div class="col-sm-8">
								<form:input path="maturityDisbrsmntBankName" onkeypress="validName(event)" class="input form-control"
									id="beneficiaryBankMr" />
							</div>
						</div>
						<div class="form-group" id="ifscMr" style="display: none;">
							<label class="control-label col-sm-4" for=""><spring:message
									code="label.ifs" /><span style="color: red">*</span></label>
							<div class="col-sm-8">
								<form:input path="maturityDisbrsmntBankIFSCCode" onkeypress="validIFSC(event)" class="input form-control"
									id="beneficiaryIfscMr" />
							</div>

						</div>
						
					
					

						<!--************************** For Joint Account Holders ************************** -->
						<div class="col-md-12">

							<div class="joint_ac" id ="joint_acDIV">
								<c:forEach items="${customerDetailsParsers}"
								var="jointAcc" varStatus="status">
								 
									<hr>	
<div class="form-group">
											<label class="col-md-4 control-label">Joint Account
												Holder</label>
											<div class="col-md-8">
												<b><c:out value="${jointAcc.customerName}" /></b>
											</div>
										</div>

						<div class="form-group" id="everyMonthInt">
							<label class="col-md-4 control-label" for="radio"
								style="margin-top: 10px;">Transfer To Linked Account</label>
							<div class="col-sm-8">
								<c:if test="${! empty fixedDepositForm.accountList}">
								 <label id="fd1" for="radio"> <form:radiobutton
										path="jointAccounts[${status.index}].isMaturityDisbrsmntInLinkedAccount" id="inLinked[${status.index }]"
										name="jointAccounts[${status.index}].isMaturityDisbrsmntInLinkedAccount" value="1"
										onclick="inLinkedAccountJoint(${status.index })" class="isLinked" checked="checked"></form:radiobutton>
									Yes</label>
									</c:if>
									 <label id="fd3"
									for="radio"> <form:radiobutton
										path="jointAccounts[${status.index}].isMaturityDisbrsmntInLinkedAccount" id="notInLinked[${status.index }]"
										name="jointAccounts[${status.index}].isMaturityDisbrsmntInLinkedAccounts" onclick="notInLinkedAccountJoint(${status.index })" value="0"
										></form:radiobutton>
									No	</label> </div>
						</div>
						
						

						

						<div class="form-group" id="payoffAccountTypeMr[${status.index }]" style="display:none;">
							<label class="control-label col-sm-4" for=""
								style="margin-top: 10px;"><spring:message
									code="label.payOffAccount1" /></label>
							<div class="col-sm-8">
								
								<form:radiobutton path="jointAccounts[${status.index}].isMaturityDisbrsmntInSameBank"
									onclick="showDetailsSameBankMJoint(${status.index })" id="savingBankRadioMr[${status.index }]"
									value="1"></form:radiobutton>
								<spring:message code="label.savingAccount" />
								<form:radiobutton path="jointAccounts[${status.index}].isMaturityDisbrsmntInSameBank"
									onclick="showDetailsDiffBankMJoint(${status.index })" id="differentBankRadioMr[${status.index }]"
									value="0"></form:radiobutton>
								<spring:message code="label.otherBan" />

							</div>
						</div>
						<div class="form-group" id="beneficiaryDivMr[${status.index }]"
							style="display: none;">
							<label class="control-label col-sm-4" for=""><spring:message
									code="label.benificiaryName" /> <span style="color: red">*</span>
							</label>
							<div class="col-sm-8">
								<form:input path="jointAccounts[${status.index}].maturityDisbrsmntAccHolderName" value="" onkeypress="validName(event)" class="input form-control beneficiaryNameMr"
									style="text: pointer;" id="beneficiaryNameMr[${status.index }]" />
							</div>
						</div>
						<div class="form-group" id="accountNoMr[${status.index }]" style="display: none;">
							<label class="control-label col-sm-4" for=""><spring:message
									code="label.accno" /> <span style="color: red">*</span> </label>
							<div class="col-sm-8">
								<form:input path="jointAccounts[${status.index}].maturityDisbrsmntAccNumber" type="text"
									class="input form-control beneficiaryAccountMr" onkeypress="validationAccount(event)" id="beneficiaryAccountMr[${status.index }]" />
							</div>
						</div>

						<div class="form-group" id="transferModeMr[${status.index }]" style="display: none;">
							<label class="control-label col-sm-4" for=""
								style="margin-top: 7px;"><spring:message
									code="label.transfer" /><span style="color: red">*</span></label>
							<div class="col-sm-8">
								<label for="radio"><form:radiobutton
										path="jointAccounts[${status.index}].maturityDisbrsmntTransferType" id="neftMr[${status.index }]" value="NEFT"></form:radiobutton></label>
								<spring:message code="label.transfer1" />
								<label for="radio"><form:radiobutton
										path="jointAccounts[${status.index}].maturityDisbrsmntTransferType" id="impsMr[${status.index }]" value="IMPS"></form:radiobutton></label>
								<spring:message code="label.transfer2" />
								<label for="radio"><form:radiobutton
										path="jointAccounts[${status.index}].maturityDisbrsmntTransferType" id="rtgsMr[${status.index }]" value="RTGS"></form:radiobutton></label>
								<spring:message code="label.transfer3" />
							</div>
						</div>
						<div class="form-group" id="bankNameMr[${status.index }]" style="display: none;">
							<label class="control-label col-sm-4" for=""><spring:message
									code="label.bankName" /><span style="color: red">*</span></label>
							<div class="col-sm-8">
								<form:input path="jointAccounts[${status.index}].maturityDisbrsmntBankName" onkeypress="validName(event)" class="input form-control beneficiaryBankMr"
									id="beneficiaryBankMr[${status.index }]" />
							</div>
						</div>
						<div class="form-group" id="ifscMr[${status.index }]" style="display: none;">
							<label class="control-label col-sm-4" for=""><spring:message
									code="label.ifs" /><span style="color: red">*</span></label>
							<div class="col-sm-8">
								<form:input path="jointAccounts[${status.index}].maturityDisbrsmntBankIFSCCode" onkeypress="validIFSC(event)" class="input form-control beneficiaryIfscMr"
									id="beneficiaryIfscMr[${status.index }]" />
							</div>

						</div>
						
					
					
			
								</c:forEach>
							</div>

						</div>
<div></div>
						

					</div>
<!-- ...................maturity section ended..................... -->


					<!-- ...................deposit payment section started................ -->

					<div class="header_customer  col-sm-12 col-md-12 col-lg-12"
						data-toggle="collapse" data-target="#paymentDetails"
						style="border-bottom: 1px dotted #05007e; margin-bottom: 10px; padding-top: 7px; padding-bottom: 7px;">
						<h3>
							<b><spring:message code="label.payments" /></b> <i
								class="fa fa-chevron-circle-down pull-right" aria-hidden="true"></i>
						</h3>
						<span id="paymentOk"
							style="display: none; color: #14c514; margin-right: 25px; margin-top: -12px; float: right;"
							class="glyphicon glyphicon-ok"></span><span id="paymentCross"
							style="display: none; color: red; margin-right: 25px; margin-top: -12px; float: right;"
							class="glyphicon glyphicon-remove"></span>
					</div>

					<div id="paymentDetails" class="collapse">



						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.modeOfPay" /></label>
							<div class="col-md-6">
								<form:select id="paymentMode" path="depositForm.paymentMode"
									class="input form-control" onchange="showDetails(this)"
									required="true">
									<form:option value="Select" selected="true">
										Select

				</form:option>
									

									<c:forEach items="${paymentMode}" var="mode">
												<option mode="${mode.paymentMode}"	value="${mode.id}">${mode.paymentMode}</option>
									</c:forEach>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.fdAmount" /><span style="color: red">*</span></label>
							<fmt:formatNumber value="${depositAmount}" pattern="#.##"
								var="depositAmt" />
							<div class="col-md-6">
								<form:input path="depositForm.fdPay" class="form-control"
									id="fdAmountPayment" value="${depositAmt}" readonly="true" />
							</div>
						</div>

						<div class="form-group" id="linkedAccountTr">
							<label class="col-md-4 control-label">Account Number<span
								style="color: red">*</span></label>
							<div class="col-md-6">
								<form:input path="depositForm.linkedAccountNo"
									class="form-control" id="linkedAccount" readonly="true" />
							</div>
						</div>
						<div class="form-group" id="linkedAccountBalanceTr">
							<label class="col-md-4 control-label">Account Balance<span
								style="color: red">*</span></label>
							<div class="col-md-6">
								<fmt:formatNumber value="${depositForm.accountBalance}"
									pattern="#.##" var="accountBalance" />
								<form:input path="${accountBalance}" value="${accountBalance}"
									class="form-control" id="linkedAccountBalance"
									onkeypress="validationAccount(event)" />
							</div>
						</div>



						<div id="bankDetailsTr">
							<div class="form-group" id="chequeNoTr">
								<label class="col-md-4 control-label"><span
									id="chequeNoLabel" style="display: none">Cheque Number<span
										style="color: red">*</span></span><span id="ddNoLabel"
									style="display: none">DD Number<span style="color: red">*</span></span></label>
								<div class="col-md-6">
									<form:input path="depositForm.chequeNo"
										class="input form-control" id="chequeNo"
										onkeypress="validationAccount(event)" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label"><span
									id="chequeDateLabel" style="display: none">Cheque Date<span
										style="color: red">*</span></span><span id="ddDateLabel"
									style="display: none">DD Date<span style="color: red">*</span></span></label>
								<div class="col-md-6">
									<form:input path="depositForm.chequeDate" value="${todaysDate}"
										class="input form-control datepicker-here"
										style="background: whitesmoke; cursor: pointer;"
										id="chequeDate" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.bank" /><span style="color: red">*</span></label>
								<div class="col-md-6">
									<form:input path="depositForm.chequeBank"
										class="input form-control" id="chequeBank"
										onkeypress="validName(event)" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.branch" /><span style="color: red">*</span></label>
								<div class="col-md-6">
									<form:input path="depositForm.chequeBranch"
										class="input form-control" id="chequeBranch"
										onkeypress="validName(event)" />
								</div>
							</div>

						</div>
						<div id="cardDetailsTr">
							<%-- <div class="form-group">
								<label class="col-md-4 control-label">Select Card</label>
								<div class="col-md-6">
									<form:select path="depositForm.cardType" placeholder="12/20"
										id="cardType" class="input form-control">
										<form:option value="Select Card Type">
											<spring:message code="label.selectCard" />
										</form:option>
										<form:option value="Debit Card">
											<spring:message code="label.debit" />
										</form:option>
										<form:option value="Credit Card">
											<spring:message code="label.credit" />
										</form:option>
										<form:option value="Other">
											<spring:message code="label.other" />
										</form:option>
									</form:select>
								</div>
							</div> --%>
							<div class="form-group">
								<label class="col-md-4 control-label">Enter Card No</label>
								<div class="col-md-6">
									<form:input path="depositForm.cardNo"
										placeholder="xxxx-xxxx-xxxx-xxxx" id="cardNo"
										class="input form-control d" filtertype="CCNo" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label">Expiry Date</label>
								<div class="col-md-2">
									<select class="input form-control" id="expiryMonth"
										onchange="changeExpiryDate()">

										<option value="MM">MONTH</option>
										<option value="01">01</option>
										<option value="02">02</option>
										<option value="03">03</option>
										<option value="04">04</option>
										<option value="05">05</option>
										<option value="06">06</option>
										<option value="07">07</option>
										<option value="08">08</option>
										<option value="09">09</option>
										<option value="10">10</option>
										<option value="11">11</option>
										<option value="12">12</option>

									</select>
								</div>
								<div class="col-md-2">
									<select id="expiryYear" class="input form-control"
										onchange="changeExpiryDate()">

										<option value="YY">YEAR</option>
										<option value="2017">2017</option>
										<option value="2018">2018</option>
										<option value="2019">2019</option>
										<option value="2020">2020</option>
										<option value="2021">2021</option>
										<option value="2022">2022</option>
										<option value="2023">2023</option>
										<option value="2024">2024</option>
										<option value="2025">2025</option>
										<option value="2026">2026</option>
										<option value="2027">2027</option>
										<option value="2028">2028</option>
										<option value="2029">2029</option>
										<option value="2030">2030</option>
										<option value="2031">2031</option>
										<option value="2032">2032</option>
										<option value="2033">2033</option>
										<option value="2034">2034</option>
										<option value="2035">2035</option>

									</select>
								</div>
								<div class="col-md-2">
									<form:hidden path="depositForm.expiryDate" id="expiryDate" />
									<form:input id="cvv" onkeypress="validcvv(event,$(this))"
										path="depositForm.cvv" placeholder="CVV" type="password"
										filtertype="text" class="input form-control" />
								</div>
							</div>
						</div>
						<div id="netBankingDetailsTr">
							<div class="form-group">
								<label class="col-md-4 control-label">Bank Type<span
									style="color: red">*</span></label>
								<div class="col-md-6">
									<form:select path="fdPayType" id="bankType"
										onchange="onchangeBankType()" class="input form-control">
										<form:option value="">
										Select
									</form:option>
										<form:option value="sameBank">
										Same Bank
									</form:option>
										<form:option value="differentBank">
										Different Bank
									</form:option>
									</form:select>
								</div>
							</div>
						</div>
						<div class="form-group" id="transferModeTr">
							<label class="control-label col-sm-4" for=""><spring:message
									code="label.transfer" /><span style="color: red">*</span></label>
							<div class="col-sm-6">
								<form:select path="otherPayTransfer1" id="transferType"
									class="input form-control">
									<form:option value="Select" selected="true">
										Select
									</form:option>
									<form:option value="NEFT">
										NEFT
									</form:option>
									<form:option value="IMPS">
										IMPS
									</form:option>
									<form:option value="RTGS">
										RTGS
									</form:option>
								</form:select>
							</div>
						</div>
						<div class="form-group" id="beneficiaryNameTr">
							<label class="control-label col-sm-4" for="">Beneficiary
								Name<span style="color: red">*</span>
							</label>
							<div class="col-sm-6">
								<form:input path="otherName1" class="input form-control"
									id="beneficiaryName" onkeypress="validName(event)" />
							</div>
						</div>
						<div class="form-group" id="accountNoTr">
							<label class="control-label col-sm-4" for="">Account
								Number<span style="color: red">*</span>
							</label>
							<div class="col-sm-6">
								<form:input path="otherAccount1" type="text"
									class="input form-control" id="beneficiaryAccount"
									onkeypress="validationAccount(event)" />
							</div>
						</div>
						<div class="form-group" id="bankNameTr">
							<label class="control-label col-sm-4" for="">Bank Name<span
								style="color: red">*</span></label>
							<div class="col-sm-6">
								<form:input path="otherBank1" class="input form-control"
									id="beneficiaryBank" pattern="[a-zA-Z ]+"
									onkeypress="validName(event)" />
							</div>
						</div>
						<div class="form-group" id="ifscTr">
							<label class="control-label col-sm-4" for="">IFSC<span
								style="color: red">*</span></label>
							<div class="col-sm-6">
								<form:input path="otherIFSC1" class="input form-control"
									id="beneficiaryIfsc" onkeypress="validIFSC(event)" />
							</div>
						</div>
						<input type="button" onclick="resetPaymentDetails()"
							class="btn btn-warning pull-right" value="Reset Payment Details"
							style="margin-right: 9px; margin-bottom: 33px;" />
					</div>
					<!--......................... all section ended....................... -->
					<div style="clear: both; height: 7px;"></div>
					<c:choose>
						<c:when test="${baseURL[1] == 'common' || baseURL[0] == 'common'}">
							<c:set var="back" value="applyOnlineFD?depositType=joint" />
						</c:when>
						<c:otherwise>
							<c:set var="back" value="user" />
						</c:otherwise>
					</c:choose>
					<div class="successMsg">
						<b><font color="error">${errorPanJoint}</font></b>
					</div>
					<div class="successMsg">
						<b><font color="error">${errorAadharJoint}</font></b>
					</div>
					<div class="form-group">
						<div class="col-md-4"></div>
						<div class="col-md-6">
							<span id='validationError' style="color: red"></span>
							<div
								style="margin-top: 19px; margin-right: 9px; margin-bottom: 15px">
								<%-- <a href="${back}" style="margin-left: -88px;"
									class="btn btn-warning"> <spring:message code="label.back" /></a> --%>
								<input type="button" onclick=" submitForm()"
									style="margin-left: 2px;" class="btn btn-primary"
									value="Submit" />
							</div>
						</div>

					</div>

				</form:form>

			</div>
</body>
<script>

function isNumberKey1(evt,obj)
{
	
   var charCode = (evt.which) ? evt.which : evt.keyCode;
   if (charCode != 46 && charCode > 31 
     && (charCode<48||charCode>57)){
      return false;
   }else{
 	  var splitValue = obj.val();
          var res = splitValue.split(".");
          if(splitValue.includes(".")){
          var spVal = res[1];
          if(spVal.length > 1){
        	  evt.preventDefault();
        	  return false;
          }
          return true;
          }else{
      
         
 	  return true;
          }
   }
  }
  
function validcvv(evt,obj)
{
	
   var charCode = (evt.which) ? evt.which : evt.keyCode;
   if (charCode != 46 && charCode > 31 
     && (charCode<48||charCode>57)){
	   event.preventDefault();
      return false;
   }
   else{
	   if(obj.val().length==3)
		   event.preventDefault();
	      return false;
   }
 }

function abc(event,id){
	
	var id_ =	document.getElementById("jointIntPercent["+id+"]");
	
	  if (parseInt(id_.value) > 100){
		  document.getElementById("jointIntPercent["+id+"]").style.borderColor = "red";
	    	alert("Payoff should not more than 100%");
	    	
	    }
	
}

function accountId(event,id){
	
	var id_ =	document.getElementById("interestPayAmount["+id+"]");
	var depositeAmount = $("#fdAmount").val();
	if(depositeAmount == "" && event.keyCode != 144){
		document.getElementById("fdAmount").style.borderColor="red";
		alert("Deposite Amount should not be empty");
	} 
	if(parseInt(id_.value) > depositeAmount && event.keyCode != 144){
		var valuePART = $("#partInterest").val();
		
		if(valuePART == 'PART'){
			document.getElementById("interestPayAmount["+id+"]").style.borderColor="red";
			alert("Payoff amount should not more than Deposit amount.");
		}
		
	}
	if(id_.value <= depositeAmount){
		
		document.getElementById("interestPayAmount["+id+"]").style.borderColor="grey";
	}
	
}

function accountPart(event,id){
	
	var id_ =	document.getElementById(id);
	var depositeAmount = $("#fdAmount").val();
	if(depositeAmount == ""  && event.keyCode != 144){
		document.getElementById("fdAmount").style.borderColor="red";
		alert("Deposite Amount should not be empty");
		
	} 
	
	if(parseInt(id_.value) > depositeAmount && event.keyCode != 144){
	var valuePART = $("#partInterest").val();
		
		if(valuePART == 'PART'){
		document.getElementById(id).style.borderColor="red";
		alert("Payoff amount should not more than Deposit amount.");
		event.preventDefault();
		}
	}
	if(parseInt(id_.value) <= depositeAmount){
		document.getElementById(id).style.borderColor="grey";
	}
	
}
/*.......primaryDetails functions ended.......*/


	function displayDaysInTenure() {
		var r = document.getElementById('paymentType').value;
		$('#paymentType1').val(r);
         if (r == 'One-Time') {
	    $("#days").show();
	    document.getElementById("paymentType").disabled=true;
	    
	    
         } 
           else if(r =='Monthly' || r=='Quarterly' || r=='Half Yearly' || r=='Annually')
            {
        	   document.getElementById("deductionDay").value ="";
	          document.getElementById("deductionDay").disabled=false;
            }  
	

       else {
        	$("#days").hide();
            }

	     }

	$(function() {
		$("#jointDiv").hide();
	
	});

	

	function appendSemiColon() {
		var address = document.getElementById("address").value;
		document.getElementById("address").value = address + ";";
	}

	function onchangeDepositAmount(value){
		
		document.getElementById('fdAmountPayment').value=value;
	}
	
	function onChangeAccountNo(account) {
		
		if(account!=""){
		var account = account.split(",");
		document.getElementById('accountType').value = account[1];
		document.getElementById('accountBalance').value = parseFloat(account[2]).toFixed(2).toLocaleString("en-US");
		document.getElementById('accountBalance1').value = parseFloat(account[2]);
		
		
		document.getElementById('accountTypeDiv').style.display='block';
		document.getElementById('accoprimaryContuntBalanceDiv').style.display='block';
		
		//document.getElementById('fundTransferLinkedAccount').style.display='block';
		
		if($('option:selected', $('#paymentMode')).attr('mode')=='Fund Transfer'){
			
			document.getElementById('linkedAccount').value=account[0];
			document.getElementById('linkedAccountBalance').value = account[2];
			
		}
		}
		else{
			document.getElementById('accountTypeDiv').style.display='none';
			document.getElementById('accountBalanceDiv').style.display='none';
			
			//document.getElementById('fundTransferLinkedAccount').style.display='none';
			if($('option:selected', $('#paymentMode')).attr('mode')=='Fund Transfer'){
				document.getElementById('paymentMode').value="Select";
			}
		}
	}
	
	
	
	/* function searchCustomer() {
		
		 var custName = document.getElementById('customerName').value;
		    var conNum = document.getElementById('contactNum').value;
		    var emailCust = document.getElementById('email').value;
		    var customerID = document.getElementById('customerId').value;
		    if(custName=="" && emailCust=="" && conNum == ""  && customerID==""){
		     document.getElementById('errorMsg').style.display='block';
		    document.getElementById('errorMsg').value="Please Insert at least one field.";
		     event.preventDefault();
		    }
		    else
		    {   
		    	$("#jointForm").attr("action", "searchSecondaryCustomer");
				$("#jointForm").submit();
		    }
		   
		    displayDaysInTenure();
	} */
	function submitForm() {
	
		document.getElementById('validationError').innerHTML="";
		
		 /*  if(${empty fixedDepositForm.jointAccounts}){
			  document.getElementById('validationError').innerHTML="Please select at least one holder"
			 event.preventDefault(); 
		 }
		  
		else */
		
		if(validate()==true){
		$("#jointForm").attr("action", "jointConsortiumDepositSummary");
		$("#jointForm").submit();
		}
		
		else{event.preventDefault();} 
	}
	
	function enableAdd(id){
		var addId = "add["+id+"]";
		 document.getElementById(addId).disabled = false;
	}
	
	

	function isNumberKey(evt)
    {
		 var valueNum = document.getElementById(evt.target.id).value;
       var charCode = (evt.which) ? evt.which : event.keyCode
       if (charCode > 31 && (charCode<48||charCode>57))
          return false;

       
       return true;
    }

		function myFunction(event)
		{
			if((event.charCode > 64 && event.charCode < 91) || (event.charCode > 96 && event.charCode < 123)) {
             return ture;
            }else{
            	return false;
            }
		}
		
	
	/* function removeAllConsotium(){
		$("#jointForm").attr("action", "removeAllConsotium");
		$("#jointForm").submit();
	} */
	
	/* function removeSecondaryCustomer(id){
		$("#jointForm").attr("action", "removeSecondaryCustomer?id="+id+"&customerId="+${customer.id});
		$("#jointForm").submit();
	} */
	
	/*.......primaryDetails functions ended.......*/
	
	/*.......pay off functions..started.....*/
	
	function onClickJointPartPercent(index,value) {
	
		document.getElementById('partPercentValueDiv['+index+']').style.display='block';
		
		
	var percent = "jointInterestPercent["+index+"]";
	var part = "jointPartInterestAmt["+index+"]";
	
	if(value=='PART'){
		document.getElementById(percent).style.display='none';
		document.getElementById(part).style.display='block';
		document.getElementById('jointIntPercent['+index+']').value="";	
		
	}
	if(value=='PERCENT'){
		
		document.getElementById(percent).style.display='block';
		document.getElementById(part).style.display='none';
	    document.getElementById('interestPayAmount['+index+']').value="";
	}
	
}



function hideAllDiv(index){	
	var percent = "jointInterestPercent["+index+"]";
	var divIdSecondAmt = "jointSecondPartInterestAmt["+index+"]";
	var divIdSecond = "jointSecondPartInterest["+index+"]";	
	
	document.getElementById(percent).style.display='none';
	
	document.getElementById(divIdSecondAmt).style.display='none';
	document.getElementById(divIdSecond).style.display='none';
}

function onClickJointSameDiffBank(value,index) {
	
	var jointOtherBankDetails = "payOffOtherDetails["+index+"]";
	if(value=='Saving Account') {
		document.getElementById(jointOtherBankDetails).style.display='none';
	}else {
		document.getElementById(jointOtherBankDetails).style.display='block';
		
	}
	
}
function displayDiv(value){
	
	var id1='firstPartInterestAmtTr';
	var id2='interestPercentTr';
	
	document.getElementById("partPercentValueDiv").style.display='block';
	if(value=='PART'){
		document.getElementById("interestPercent").value="";
		document.getElementById(id1).style.display='block';
		document.getElementById(id2).style.display='none';
		
	}
	if(value=='PERCENT'){
		document.getElementById("interestPayAmount").value="";
		document.getElementById(id1).style.display='none';
		document.getElementById(id2).style.display='block';
		
	}
}

function showSameBankInfo(){
var payOffOtherDetails = "payOffOtherDetails";
	document.getElementById(payOffOtherDetails).style.display='none';
	}

function showDiffBankInfo(){

var payOffOtherDetails = "payOffOtherDetails";
	
	document.getElementById(payOffOtherDetails).style.display='block';
	
	
}

function hideAllBankDetailsPrimary(){
	
	var transferMode = "transferMode";
	var beneficiaryDetails = "beneficiaryDetails";
	var otherBankDetails = "otherBankDetails";
	
	document.getElementById(transferMode).style.display='none';
	document.getElementById(beneficiaryDetails).style.display='none';
	document.getElementById(otherBankDetails).style.display='none';
	
	
}


function validatePercent(value,id){
	var percentValue=parseInt(value);
	if(percentValue>100){
		document.getElementById(id).value="";
		
		
	}
}

  function onChangePayOffInterestType(value){
	
	  
   if(document.getElementById('payOffInterestType').value == ''){
	   document.getElementById('joint_acDIV').style.display = 'none';
		document.getElementById('showPartPercent').style.display = 'none';
	 
    }
    else{
    	
       }
		var arrayLength = parseInt('${customerDetailsParsers.size()}');

	if(value==""){
		hidePayOffDiv();
	}
	else{
		document.getElementById('joint_acDIV').style.display = 'block';
		document.getElementById('showPartPercent').style.display = 'block';
		document.getElementById("sameBankDifferentBankDiv").style.display='block';
	}
}

/*.......pay off functions..end.....*/
 /* payment function started........... */
 
 function showDetails(obj) {

	 var paymentMode = $('option:selected', obj).attr('mode');
	 
	var linkedAccountNo = document.getElementById('linkedAccount').value;

	if (paymentMode == 'Select') {
	
		document.getElementById('bankDetailsTr').style.display = 'none';
		document.getElementById('cardDetailsTr').style.display = 'none';
		document.getElementById('netBankingDetailsTr').style.display = 'none';
		document.getElementById('transferModeTr').style.display = 'none';	
		document.getElementById('beneficiaryNameTr').style.display = 'none';
		document.getElementById('accountNoTr').style.display = 'none';
		document.getElementById('bankNameTr').style.display = 'none';		
		document.getElementById('ifscTr').style.display = 'none';	
		document.getElementById('linkedAccountBalanceTr').style.display = 'none';
		document.getElementById('linkedAccountTr').style.display = 'none';
	}
	
	if (paymentMode == 'Fund Transfer') {
		var isEmpty= <c:out value="${empty fixedDepositForm.accountList}"/>;
		
		if(isEmpty==true){
			alert("Customer do not have linked account. Please select another mode");
			document.getElementById('paymentMode').value='Select';
			return false;
		}
		document.getElementById('bankDetailsTr').style.display = 'none';
		document.getElementById('cardDetailsTr').style.display = 'none';
		document.getElementById('netBankingDetailsTr').style.display = 'none';
        document.getElementById('transferModeTr').style.display = 'none';	
		document.getElementById('beneficiaryNameTr').style.display = 'none';
	    document.getElementById('accountNoTr').style.display = 'none';
		document.getElementById('bankNameTr').style.display = 'none';		
		document.getElementById('ifscTr').style.display = 'none';		
		document.getElementById('linkedAccountBalanceTr').style.display = 'block';
		document.getElementById('linkedAccountTr').style.display = 'block';
	var accArray= $('#accountNo').val().split(',');
		document.getElementById('linkedAccount').value=accArray[0];
		document.getElementById('linkedAccountBalance').value = parseFloat(accArray[2]);
		
		var accArray= $('#accountNo').val().split(',');
		document.getElementById('linkedAccount').value=accArray[0];
		document.getElementById('linkedAccountBalance1').value=$('#accountBalance1').val();
		document.getElementById('linkedAccountBalance').value=parseFloat($('#accountBalance1').val()).toLocaleString("en-US");
	}
	if (paymentMode == 'Cash') {
		document.getElementById('bankDetailsTr').style.display = 'none';
		document.getElementById('cardDetailsTr').style.display = 'none';
		document.getElementById('netBankingDetailsTr').style.display = 'none';
        document.getElementById('transferModeTr').style.display = 'none';	
		document.getElementById('beneficiaryNameTr').style.display = 'none';
	    document.getElementById('accountNoTr').style.display = 'none';
		document.getElementById('bankNameTr').style.display = 'none';		
		document.getElementById('ifscTr').style.display = 'none';		
		document.getElementById('linkedAccountBalanceTr').style.display = 'none';
		document.getElementById('linkedAccountTr').style.display = 'none';
}
	if (paymentMode == 'DD') {
		document.getElementById('bankDetailsTr').style.display = 'block';
		document.getElementById('cardDetailsTr').style.display = 'none';
		document.getElementById('netBankingDetailsTr').style.display = 'none';
		document.getElementById('linkedAccountTr').style.display = 'none';
        document.getElementById('transferModeTr').style.display = 'none';	
		document.getElementById('beneficiaryNameTr').style.display = 'none';
	    document.getElementById('accountNoTr').style.display = 'none';
		document.getElementById('bankNameTr').style.display = 'none';		
		document.getElementById('ifscTr').style.display = 'none';		
		document.getElementById('linkedAccountBalanceTr').style.display = 'none';
		document.getElementById('linkedAccountTr').style.display = 'none';
		
		document.getElementById('ddNoLabel').style.display = 'block';
		document.getElementById('ddDateLabel').style.display = 'block';
		document.getElementById('chequeNoLabel').style.display = 'none';
		document.getElementById('chequeDateLabel').style.display = 'none';
		
	}
	if (paymentMode == 'Cheque') {

		document.getElementById('bankDetailsTr').style.display = 'block';
		document.getElementById('cardDetailsTr').style.display = 'none';
		document.getElementById('netBankingDetailsTr').style.display = 'none';
        document.getElementById('transferModeTr').style.display = 'none';	
		document.getElementById('beneficiaryNameTr').style.display = 'none';
	    document.getElementById('accountNoTr').style.display = 'none';
		document.getElementById('bankNameTr').style.display = 'none';		
		document.getElementById('ifscTr').style.display = 'none';		
		document.getElementById('linkedAccountBalanceTr').style.display = 'none';
		document.getElementById('linkedAccountTr').style.display = 'none';
		
		document.getElementById('ddNoLabel').style.display = 'none';
		document.getElementById('ddDateLabel').style.display = 'none';
		document.getElementById('chequeNoLabel').style.display = 'block';
		document.getElementById('chequeDateLabel').style.display = 'block';
	}
	if (paymentMode == 'Debit Card' || paymentMode == 'Credit Card') {

		document.getElementById('bankDetailsTr').style.display = 'none';
		document.getElementById('netBankingDetailsTr').style.display = 'none';
        document.getElementById('transferModeTr').style.display = 'none';	
		document.getElementById('beneficiaryNameTr').style.display = 'none';
	    document.getElementById('accountNoTr').style.display = 'none';
		document.getElementById('bankNameTr').style.display = 'none';		
		document.getElementById('ifscTr').style.display = 'none';	
		document.getElementById('linkedAccountBalanceTr').style.display = 'none';
		document.getElementById('linkedAccountTr').style.display = 'none';
		
		document.getElementById('cardDetailsTr').style.display = 'block';
		//$('#cardType').val("Select Card Type").change();
		
	}

	if (paymentMode == 'Net Banking') {

		document.getElementById('bankDetailsTr').style.display = 'none';
		document.getElementById('cardDetailsTr').style.display = 'none';
		document.getElementById('linkedAccountBalanceTr').style.display = 'none';
		document.getElementById('linkedAccountTr').style.display = 'none';
		
		document.getElementById('netBankingDetailsTr').style.display = 'block';
		
	}

}


 function changeExpiryDate() {

		document.getElementById('expiryDate').value = document
				.getElementById('expiryMonth').value
				+ "-" + document.getElementById('expiryYear').value;
	}

 
	function onchangeBankType() {
		
		var value= document.getElementById('bankType').value;
		
		if (value == 'sameBank') {
			document.getElementById('transferModeTr').style.display = 'block';
			document.getElementById('beneficiaryNameTr').style.display = 'block';
			document.getElementById('accountNoTr').style.display = 'block';
			document.getElementById('bankNameTr').style.display = 'none';
			document.getElementById('ifscTr').style.display = 'none';
		    document.getElementById('transferModeTr').style.display = 'none';
 
			document.getElementById("beneficiaryBank").value = "";
			document.getElementById("beneficiaryIfsc").value = "";
		} else if (value == 'differentBank') {
			document.getElementById('transferModeTr').style.display = 'block';
			document.getElementById('beneficiaryNameTr').style.display = 'block';
			document.getElementById('accountNoTr').style.display = 'block';
			document.getElementById('bankNameTr').style.display = 'block';
			document.getElementById('ifscTr').style.display = 'block';
			document.getElementById('transferModeTr').style.display = 'block';
 
		} else {
			document.getElementById('transferModeTr').style.display = 'none';
			document.getElementById('beneficiaryNameTr').style.display = 'none';
			document.getElementById('accountNoTr').style.display = 'none';
			document.getElementById('bankNameTr').style.display = 'none';
			document.getElementById('ifscTr').style.display = 'none';

		}

	}
/*  ........................payment function ended...............................*/
	
	function resetBasicDetails(){
		document.getElementById('paymentType').value='';
		
		if('${productConfiguration.productType}'=='Regular Deposit'||'${productConfiguration.productType}'== 'Tax Saving Deposit'){
			$('#paymentType').val('One-Time').change();
			document.getElementById("paymentType").disabled = true;
			}
		
		document.getElementById('fdAmount').value='';
		//document.getElementById('fdTenureType').value='';
		document.getElementById('fdTenure').value=0;
		document.getElementById('fdAmountPayment').value='';
		document.getElementById('currency').value='${productConfiguration.defaultCurrency}';
		
	}
	
	function resetContributionDetails(){
	  document.getElementById('primaryCont').value='';
		var arrayLength = parseInt('${customerDetailsParsers.size()}');
		for(var i=0;i<arrayLength;i++){
			document.getElementById('contribution['+i+']').value='';
		}
		}
	
	
	   function resetNomineeDetails(){
		document.getElementById('nomineeName').value='';
		document.getElementById('nomineeAge').value='';
		document.getElementById('nomineeAddress').value='';
		document.getElementById('nomineeRelationShip').value='';
		document.getElementById('gaurdian').style.display = 'none';
		
		document.getElementById('guardianName').value='';
		document.getElementById('guardianAge').value='';
		document.getElementById('guardianAddress').value='';
		document.getElementById('guardianRelationShip').value='';
		var arrayLength = parseInt('${customerDetailsParsers.size()}');
for(var i=0;i<arrayLength;i++){
		document.getElementById('nomineeNameSec['+i+']').value='';
			document.getElementById('nomineeAgeSec['+i+']').value='';
			document.getElementById('nomineeAddress['+i+']').value='';
			document.getElementById('nomineeRelationShip['+i+']').value='';
			document.getElementById('guardianName['+i+']').value='';
			document.getElementById('guardianAge['+i+']').value='';
			document.getElementById('guardianAddress['+i+']').value='';
			document.getElementById('guardianRelationShip['+i+']').value='';
			
			document.getElementById('jointGaurdian['+i+']').style.display = 'none';
		}
	}
		function hidePayOffDiv(){
			document.getElementById('showPartPercent').style.display = 'none';
			document.getElementById('payOffOtherDetails').style.display = 'none';
			document.getElementById('partPercentValueDiv').style.display = 'none';
			document.getElementById('sameBankDifferentBankDiv').style.display = 'none';
			var arrayLength =parseInt('${customerDetailsParsers.size()}');
			for(var i=0;i<arrayLength;i++){
				//document.getElementById('showPartPercent['+i+']').style.display = 'none';
				document.getElementById('payOffOtherDetails['+i+']').style.display = 'none';
				document.getElementById('partPercentValueDiv['+i+']').style.display = 'none';
				}
			}
		 function resetPayoffDetails(){
		    document.getElementById('payOffInterestType').value='';
		 document.getElementById('percentInterest').checked=false;
		 document.getElementById('partInterest').checked=false;
		
		  document.getElementById('interestPercent').value='';
		document.getElementById('interestPayAmount').value='';
		 document.getElementById('sameBankAcc').checked=false;
		 document.getElementById('otherBankAcc').checked=false;
 document.getElementById('neft').checked=false;
		 document.getElementById('imps').checked=false;
		 document.getElementById('rtgs').checked=false;
		    document.getElementById('otherName').value='';
			document.getElementById('otherAccount').value='';
			document.getElementById('otherBank').value='';
			document.getElementById('otherIFSC').value='';
			document.getElementById('showPartPercent').style.display = 'none';
			document.getElementById('joint_acDIV').style.display = 'none';
			document.getElementById('partPercentValueDiv').style.display = 'none';
			document.getElementById('payOffOtherDetails').style.display = 'none';
			hidePayOffDiv();
			
			var arrayLength = parseInt('${customerDetailsParsers.size()}');

			for(var i=0;i<arrayLength;i++){
				//document.getElementById('showPartPercent['+i+']').style.display = 'none';
			document.getElementById('partPercentValueDiv['+i+']').style.display = 'none';
			document.getElementById('payOffOtherDetails['+i+']').style.display = 'none';
			 document.getElementById('jointPercentInterest['+i+']').checked=false;
			 document.getElementById('jointPartInterest['+i+']').checked=false;
			 
			 document.getElementById('jointIntPercent['+i+']').value='';
			 document.getElementById('interestPayAmount['+i+']').value='';
			
			 document.getElementById('jointSameBankAcc['+i+']').checked=false;
			 document.getElementById('jointOtherBankAcc['+i+']').checked=false;
			 document.getElementById('jointOtherBankAcc['+i+']').checked=false;
			 document.getElementById('neft['+i+']').checked=false;
			 document.getElementById('imps['+i+']').checked=false;
			 document.getElementById('rtgs['+i+']').checked=false;
					
			 document.getElementById('jointAccounts['+i+'].contributions.beneficiaryOne').value='';
			 document.getElementById('jointAccounts['+i+'].contributions.beneficiaryAccOne').value='';
			document.getElementById('jointAccounts['+i+'].contributions.beneficiaryBankOne').value='';
			document.getElementById('jointAccounts['+i+'].contributions.beneficiaryIFSCOne').value='';
			
			}

			
	}
	
	function resetPaymentDetails(){
		
		debugger
		document.getElementById('cardDetailsTr').style.display='none';
		document.getElementById('netBankingDetailsTr').style.display='none';
		document.getElementById('bankNameTr').style.display='none';
		document.getElementById('paymentMode').value='Select';
		
		document.getElementById('bankDetailsTr').style.display = 'none';
	    document.getElementById('linkedAccountBalanceTr').style.display = 'none';
		document.getElementById('linkedAccountTr').style.display = 'none';		
		document.getElementById('transferModeTr').style.display = 'none';		
		document.getElementById('beneficiaryNameTr').style.display = 'none';
	    document.getElementById('accountNoTr').style.display = 'none';
		document.getElementById('bankNameTr').style.display = 'none';		
		document.getElementById('ifscTr').style.display = 'none';		
		//document.getElementById('cardType').value='';
	    document.getElementById('cardNo').value='';
		document.getElementById('expiryMonth').value='';
		document.getElementById('expiryYear').value='';
		document.getElementById('cvv').value='';
		document.getElementById('bankType').value='';
	    document.getElementById('transferType').value='Select';
		document.getElementById('beneficiaryName').value='';
		document.getElementById('beneficiaryAccount').value='';
		document.getElementById('beneficiaryBank').value='';
		document.getElementById('beneficiaryIfsc').value='';
		
		
	}
	
	/*function checkFunction(){
		var paymentType= document.getElementById('paymentType').value;
		//var taxSavingDepositChecked= document.getElementById("taxSavingDeposit").checked;
		//var fdTenureType= document.getElementById('fdTenureType').value;
		//var taxSavingDepositValue = document.getElementById("taxSavingDeposit").value;
		if(taxSavingDepositChecked==true){
			 if('${productConfiguration.productType}'=='Regular Deposit' ||'${productConfiguration.productType}'== 'Tax Saving Deposit'){
				$('#paymentType').val('One-Time').change();
				document.getElementById("paymentType").disabled = true;
				} 
			//document.getElementById("deductionDay").disabled=true;
			//document.getElementById('fdTenureType').value ='Year';
			
			
			 if(taxSavingDepositValue=="Days"){
				document.getElementById('dayId').style.display= 'none';
				document.getElementById('monthId').style.display = 'none';
				document.getElementById('yearId').style.display = 'block';
				
			}else if(taxSavingDepositValue=="Months"){
				document.getElementById('dayId').style.display= 'none';
				document.getElementById('monthId').style.display = 'none';
				document.getElementById('yearId').style.display = 'block';
			}else{
				document.getElementById('dayId').style.display= 'none';
				document.getElementById('monthId').style.display = 'none';
				document.getElementById('yearId').style.display = 'block';
			} 
			//document.getElementById("fdTenureType").disabled=true;
			//document.getElementById('fdTenure').value ='5';
			//document.getElementById("fdTenure").disabled=true;
			//document.getElementById('dayValue').style.display = 'none';
		}
		
		if(taxSavingDepositChecked==false){
			document.getElementById('paymentType').value ='';
			document.getElementById("paymentType").disabled=false;
			//document.getElementById("deductionDay").disabled=false;
			//document.getElementById('fdTenureType').value ='';
			//document.getElementById("fdTenureType").disabled=false;
			document.getElementById('fdTenure').value ='';
			document.getElementById("fdTenure").disabled=false;
			//document.getElementById('dayValue').style.display = 'block';

		}
		}*/
	
	
	function panvalidation(e,id){
		var panVal = $('#'+id).val();
		
			var regex = new RegExp("^[a-zA-Z0-9]+$");
			 var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
			
			
			 if (!regex.test(key)) {
			    	  event.preventDefault();
			        return false;
			   }
		
			var regpan = /^([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}?$/;
		    if(panVal.length > 9){
			if(regpan.test(panVal)){
				
				if(e.keyCode != 144){
			  
				}
			} else {
				if(e.keyCode != 144){
				alert("invalid pan number");
				return false;
				}
			}
		}
		    
		    if(panVal.length > 9 &&  e.keyCode != 8 ){
		    	  e.preventDefault();
		    	  return false;
		    }
		
	}
	
	function panvalidationIndex(e,id){
		var panVal = document.getElementById('gaurdianPan['+id+']').value;
			
		var regex = new RegExp("^[a-zA-Z0-9]+$");
		 var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
		
		
		 if (!regex.test(key)) {
		    	//alert("false")
		        event.preventDefault();
		        return false;
		   }
			var regpan = /^([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}?$/;
		    if(panVal.length > 9){
			if(regpan.test(panVal)){
				
				if(e.keyCode != 144){
			  
				}
			} else {
				if(e.keyCode != 144){
				alert("invalid pan number");
				return false;
				}
			}
		}
		     if(panVal.length > 9 &&  e.keyCode != 8 ){
		    	  e.preventDefault();
		    	  return false;
		    }
			
	}
	
	function panvalidationIndexNomine(e,id){
		var panVal = document.getElementById('nomineePan['+id+']').value;
		var regex = new RegExp("^[a-zA-Z0-9]+$");
		 var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
		 if (!regex.test(key)) {
		    	 event.preventDefault();
		        return false;
		   }
			var regpan = /^([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}?$/;
		    if(panVal.length > 9){
			if(regpan.test(panVal)){
			if(e.keyCode != 144){
			  
				}
			} else {
				if(e.keyCode != 144){
				alert("invalid pan number");
				return false;
				}
			}
		}
		 if(panVal.length > 9 &&  e.keyCode != 8 ){
		    	  e.preventDefault();
		    	  return false;
		    }
		}
	
	
	function accountTypeVal(value){
		
	$("#jointForm").attr("action", "jointConsortiumDeposit?nriAccType="+value);
		 if(value!=""){
		//	$("#taxSavingDepositId").css({ display: "none" });
		document.getElementById('paymentType').value ='';
		document.getElementById("paymentType").disabled=false;
			 if('${productConfiguration.productType}'=='Regular Deposit'||'${productConfiguration.productType}'== 'Tax Saving Deposit'){
					$('#paymentType').val('One-Time').change();
					document.getElementById("paymentType").disabled = true;
					}
		 	
			
			
			//document.getElementById('fdTenureType').value ='';
			//document.getElementById("fdTenureType").disabled=false;
			document.getElementById('fdTenure').value ='';
			document.getElementById("fdTenure").disabled=false;
			//$("#taxSavingDeposit").prop('checked', false);
		}else{
			//$("#taxSavingDepositId").css({ display: "block" });
		}
		 var FCNR=["USD","GBP","EUR","JPY","CAD","AUD","SGD","HKD"];
			var RFC=["USD","GBP","EUR","JPY","CAD","AUD"];
			  $('#currency option').hide();
			  $('#currency').val('Select').show().change();
		     $('#currency option').each(function(){
		    	
		    	 if(value=="FCNR"  || value=="PRP"){
		    		var length=$.inArray( $(this).val(), FCNR );
		    		if(length>-1)$(this).show();
		    	 }else if(value=="RFC"){
		    		 var length=$.inArray( $(this).val(), RFC );
		    		 if(length>-1)$(this).show();
		    	 }else{
		    		 $('#currency option').show();
		    	 }
		    	 
		    	
		     }); 
		
	}
	
	function autoFillRestrict(id){
		//alert(id)
		document.getElementById("jointAccounts["+id+"].relationship").autocomplete="off";
		
		
	}
	$(document).ready(function(){
		if('${baseURL[1]}' == 'common' || '${baseURL[0]}' == 'common'){
			$('#branch').show();
			
		}
		else{
			$('#branch').remove();
		}
		$('.beneficiaryIfscMr')
		.bind(
				'keypress',
				function(event) {
					var regex = new RegExp(
							"^[a-zA-Z0-9]+$");
					var key = String
							.fromCharCode(!event.charCode ? event.which
									: event.charCode);
					if (!regex.test(key)) {
						//alert("false")
						event.preventDefault();
						return false;
					}
				});
		
		
		

	});
	
	function inLinkedAccountJoint(value) {

		
		var linked=($('.isLinked').length);
		var linkedChecked=($('.isLinked:checked').length);
		if(linkedChecked==linked){
			document.getElementById('isLinkedAccount').value = '1';
		}

		/* document.getElementById('depositAccountRadio').checked=false; */
		document.getElementById('savingBankRadioMr['+value+']').checked = false;
		document.getElementById('differentBankRadioMr['+value+']').checked = false;

		document.getElementById('neftMr['+value+']').checked = false;
		document.getElementById('impsMr['+value+']').checked = false;
		document.getElementById('rtgsMr['+value+']').checked = false;

		document.getElementById('beneficiaryNameMr['+value+']').value = '';
		document.getElementById('beneficiaryAccountMr['+value+']').value = '';
		document.getElementById('beneficiaryBankMr['+value+']').value = '';
		document.getElementById('beneficiaryIfscMr['+value+']').value = '';

		
		document.getElementById('transferModeMr['+value+']').style.display = 'none';
		document.getElementById('payoffAccountTypeMr['+value+']').style.display = 'none';
		document.getElementById('beneficiaryDivMr['+value+']').style.display = 'none';
		
		document.getElementById('accountNoMr['+value+']').style.display = 'none';
		document.getElementById('bankNameMr['+value+']').style.display = 'none';
		document.getElementById('ifscMr['+value+']').style.display = 'none';
		if(document.getElementById('inLinked['+value+']')!=null)
		document.getElementById('inLinked['+value+']').checked = true;
		if(document.getElementById('notInLinked['+value+']')!=null)
		document.getElementById('notInLinked['+value+']').checked = false;
	}

       function notInLinkedAccountJoint(value) {

			

			
    	   document.getElementById('isLinkedAccount').value = '0';
			/* document.getElementById('depositAccountRadio').checked=false; */
			
			
			document.getElementById('neftMr['+value+']').checked = false;
			document.getElementById('impsMr['+value+']').checked = false;
			document.getElementById('rtgsMr['+value+']').checked = false;

			document.getElementById('beneficiaryNameMr['+value+']').value = '';
			document.getElementById('beneficiaryAccountMr['+value+']').value = '';
			document.getElementById('beneficiaryBankMr['+value+']').value = '';
			document.getElementById('beneficiaryIfscMr['+value+']').value = '';

			
			document.getElementById('transferModeMr['+value+']').style.display = 'none';
			document.getElementById('payoffAccountTypeMr['+value+']').style.display = 'block';
			document.getElementById('beneficiaryDivMr['+value+']').style.display = 'none';
			
			document.getElementById('accountNoMr['+value+']').style.display = 'none';
			document.getElementById('bankNameMr['+value+']').style.display = 'none';
			document.getElementById('ifscMr['+value+']').style.display = 'none';
			if(document.getElementById('notInLinked['+value+']')!=null)
			document.getElementById('notInLinked['+value+']').checked = true;
			if(document.getElementById('inLinked['+value+']')!=null)
			document.getElementById('inLinked['+value+']').checked = false;
			document.getElementById('differentBankRadioMr['+value+']').checked = false;
			document.getElementById('savingBankRadioMr['+value+']').checked = false;
		}

		function showDetailsSameBankMJoint(value) {

			document.getElementById('transferModeMr['+value+']').style.display = 'none';
			document.getElementById('beneficiaryDivMr['+value+']').style.display = 'block';
			document.getElementById('accountNoMr['+value+']').style.display = 'block';
			document.getElementById('bankNameMr['+value+']').style.display = 'none';
			document.getElementById('ifscMr['+value+']').style.display = 'none';
			document.getElementById('neftMr['+value+']').checked = false;
			document.getElementById('impsMr['+value+']').checked = false;
			document.getElementById('rtgsMr['+value+']').checked = false;
if(document.getElementById("beneficiaryBankMr['+value+']")!=null){
			document.getElementById("beneficiaryBankMr['+value+']").value = "";
			document.getElementById("beneficiaryIfscMr['+value+']").value = "";
}
		}

		
		function showDetailsDiffBankMJoint(value) {

			document.getElementById('transferModeMr['+value+']').style.display = 'block';
			document.getElementById('beneficiaryDivMr['+value+']').style.display = 'block';
			document.getElementById('accountNoMr['+value+']').style.display = 'block';
			document.getElementById('bankNameMr['+value+']').style.display = 'block';
			document.getElementById('ifscMr['+value+']').style.display = 'block';

		}	
	function inLinkedAccount() {

		

		var linked=($('.isLinked').length);
		var linkedChecked=($('.isLinked:checked').length);
		if(linkedChecked==linked){
			document.getElementById('isLinkedAccount').value = '1';
		}
		/* document.getElementById('depositAccountRadio').checked=false; */
		document.getElementById('savingBankRadioMr').checked = false;
		document.getElementById('differentBankRadioMr').checked = false;

		document.getElementById('neftMr').checked = false;
		document.getElementById('impsMr').checked = false;
		document.getElementById('rtgsMr').checked = false;

		document.getElementById('beneficiaryNameMr').value = '';
		document.getElementById('beneficiaryAccountMr').value = '';
		document.getElementById('beneficiaryBankMr').value = '';
		document.getElementById('beneficiaryIfscMr').value = '';

		
		document.getElementById('transferModeMr').style.display = 'none';
		document.getElementById('payoffAccountTypeMr').style.display = 'none';
		document.getElementById('beneficiaryDivMr').style.display = 'none';
		
		document.getElementById('accountNoMr').style.display = 'none';
		document.getElementById('bankNameMr').style.display = 'none';
		document.getElementById('ifscMr').style.display = 'none';
		if(document.getElementById('inLinked')!=null)
		document.getElementById('inLinked').checked = true;
		if(document.getElementById('notInLinked')!=null)
		document.getElementById('notInLinked').checked = false;
	}

       function notInLinkedAccount() {

			

    	   document.getElementById('isLinkedAccount').value = '0';
    	   
			/* document.getElementById('depositAccountRadio').checked=false; */
			document.getElementById('savingBankRadioMr').checked = false;
			document.getElementById('differentBankRadioMr').checked = false;

			document.getElementById('neftMr').checked = false;
			document.getElementById('impsMr').checked = false;
			document.getElementById('rtgsMr').checked = false;

			document.getElementById('beneficiaryNameMr').value = '';
			document.getElementById('beneficiaryAccountMr').value = '';
			document.getElementById('beneficiaryBankMr').value = '';
			document.getElementById('beneficiaryIfscMr').value = '';

			
			document.getElementById('transferModeMr').style.display = 'none';
			document.getElementById('payoffAccountTypeMr').style.display = 'block';
			document.getElementById('beneficiaryDivMr').style.display = 'none';
			
			document.getElementById('accountNoMr').style.display = 'none';
			document.getElementById('bankNameMr').style.display = 'none';
			document.getElementById('ifscMr').style.display = 'none';
			if(document.getElementById('inLinked')!=null)
			document.getElementById('inLinked').checked = false;
			if(document.getElementById('notInLinked')!=null)
			document.getElementById('notInLinked').checked = true;
		
		}

		function showDetailsSameBankM() {

			document.getElementById('transferModeMr').style.display = 'none';
			document.getElementById('beneficiaryDivMr').style.display = 'block';
			document.getElementById('accountNoMr').style.display = 'block';
			document.getElementById('bankNameMr').style.display = 'none';
			document.getElementById('ifscMr').style.display = 'none';
			document.getElementById('neftMr').checked = false;
			document.getElementById('impsMr').checked = false;
			document.getElementById('rtgsMr').checked = false;

			document.getElementById("beneficiaryBankMr").value = "";
			document.getElementById("beneficiaryIfscMr").value = "";

		}

		
		function showDetailsDiffBankM() {

			document.getElementById('transferModeMr').style.display = 'block';
			document.getElementById('beneficiaryDivMr').style.display = 'block';
			document.getElementById('accountNoMr').style.display = 'block';
			document.getElementById('bankNameMr').style.display = 'block';
			document.getElementById('ifscMr').style.display = 'block';

		}

	
</script>



</html>

