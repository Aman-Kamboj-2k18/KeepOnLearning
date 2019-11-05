<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<link href="<%=request.getContextPath()%>/resources/css/Validation.css"
	rel="stylesheet">
<script
	src="<%=request.getContextPath()%>/resources/js/ccvalidate-tapan.min.js"></script>


<script>

function validationAccount(event){
	
	var minimumBalance1_ = document.getElementById(event.target.id);

		 var keycode = event.which;
	    if (!(event.shiftKey == false && (keycode == 8 || keycode == 37 ||  (keycode >= 48 && keycode <= 57)))) {
	        event.preventDefault();
	        return false;
	    } 
	   
	    
	    if (minimumBalance1_.value.length>10 && event.keyCode != 8){
	    	return false;
	    }
	
	  
	 
	}

function validName(event){
	var valiName = document.getElementById(event.target.id);
	var regex = new RegExp("^[a-zA-Z]+$");
	 var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
	 if (!regex.test(key) && !valiName.value.trim().length > 0) {
	    	//alert("false")
	        event.preventDefault();
	        return false;
	   }
	
	
	
}





	$(document)
			.ready(
					function() {
						debugger;
						InitiateValidation();
						
						document.getElementById('fdAmount').value = '';
						var paymentMode = document.getElementById('hdnPayment').value;
						debugger;
						
						if (paymentMode == 'DD') {

							document.getElementById('pageTitle').innerHTML = 'DD Payment';
							document.getElementById('chequeDdDiv').style.display = 'block';
							document.getElementById('chequeDDDateLabel').innerHTML = 'DD Date';
							document.getElementById('chequeDDNoLabel').innerHTML = 'DD Number';
							//document.getElementById('netBankingDetailsTr').style.display = 'none';
							//document.getElementById('cardDetailsTr').style.display = 'none';

						} else if (paymentMode == 'Cheque') {
							document.getElementById('pageTitle').innerHTML = 'Cheque Payment';
							document.getElementById('chequeDdDiv').style.display = 'block';
							document.getElementById('chequeDDDateLabel').innerHTML = 'Cheque Date';
							document.getElementById('chequeDDNoLabel').innerHTML = 'Cheque Number';
							//document.getElementById('netBankingDetailsTr').style.display = 'none';
							//document.getElementById('cardDetailsTr').style.display = 'none';

						} else if (paymentMode == 'Cash') {
							document.getElementById('pageTitle').innerHTML = 'Cash Payment';
							//document.getElementById('chequeDdDiv').style.display = 'none';
							//document.getElementById('netBankingDetailsTr').style.display = 'none';
							//document.getElementById('cardDetailsTr').style.display = 'none';

						}

						if (paymentMode == 'Card Payment') {
							document.getElementById('pageTitle').innerHTML = 'Card Payment';
							document.getElementById('cardDetailsTr').style.display = 'block';
							//document.getElementById('netBankingDetailsTr').style.display = 'none';
							//document.getElementById('chequeDdDiv').style.display = 'none';

						}
						
						if (paymentMode == 'Credit Card') {
							document.getElementById('pageTitle').innerHTML = 'Card Payment';
							document.getElementById('cardDetailsTr').style.display = 'block';
							//document.getElementById('netBankingDetailsTr').style.display = 'none';
							//document.getElementById('chequeDdDiv').style.display = 'none';

						}
						
						if (paymentMode == 'Debit Card') {
							document.getElementById('pageTitle').innerHTML = 'Card Payment';
							document.getElementById('cardDetailsTr').style.display = 'block';
							//document.getElementById('netBankingDetailsTr').style.display = 'none';
							//document.getElementById('chequeDdDiv').style.display = 'none';

						}

						else if (paymentMode == 'Net Banking') {
						
							document.getElementById('pageTitle').innerHTML = 'Net Banking Payment';

							document.getElementById('netBankingDetailsTr').style.display = 'block';
							//document.getElementById('cardDetailsTr').style.display = 'none';
							//document.getElementById('chequeDdDiv').style.display = 'none';
							
							onchangeBankType('');

						}

					});

	function cardValidation() {
		debugger;
		var canSubmit = true;
		var transferType = document.getElementById('transferType').value;
		var cardType = document.getElementById('cardType').value;
		var cardNo = document.getElementById('cardNo').value;
		var expiryMonth = document.getElementById('expiryMonth').value;
		var expiryYear = document.getElementById('expiryYear').value;
		var cvv = document.getElementById('cvv').value;
		var cardNoSplit = cardNo.split("-");
		var cardNoZeroIndex = cardNoSplit[0];
		var cardNoFirstIndex = cardNoSplit[1];
		var cardNoSecondIndex = cardNoSplit[2];
		var cardNoThirdIndex = cardNoSplit[3];
		var cardNoAfterConcatination = cardNoZeroIndex + cardNoFirstIndex + cardNoSecondIndex + cardNoThirdIndex;
		if (cardType == '' || cardNo == '' || expiryMonth == ''
				|| expiryYear == '' || cvv == '') {

			document.getElementById('cardType').style.borderColor = '#cccccc'
			document.getElementById('cardNo').style.borderColor = '#cccccc'
			document.getElementById('expiryMonth').style.borderColor = '#cccccc'
			document.getElementById('expiryYear').style.borderColor = '#cccccc'
			document.getElementById('cvv').style.borderColor = '#cccccc'
            
			if (cardType == 'Select Card Type') {
				document.getElementById('cardType').style.borderColor = 'red'
					canSubmit = false;
			}

			
			if (cardNo == '') {
				document.getElementById('cardNo').style.borderColor = 'red'
				canSubmit = false;
			}
			
			var visaRegEx = /^(?:4[0-9]{12}(?:[0-9]{3})?)$/;
			var mastercardRegEx = /^(?:5[1-5][0-9]{14})$/;
			var amexpRegEx = /^(?:3[47][0-9]{13})$/;
			var discovRegEx = /^(?:6(?:011|5[0-9][0-9])[0-9]{12})$/;
			var isValid = false;
			if (visaRegEx.test(cardNoAfterConcatination)) {
			  isValid = true;
			} else if(mastercardRegEx.test(cardNoAfterConcatination)) {
			  isValid = true;
			} else if(amexpRegEx.test(cardNoAfterConcatination)) {
			  isValid = true;
			} else if(discovRegEx.test(cardNoAfterConcatination)) {
			  isValid = true;
			}

			if(cardNoAfterConcatination.length == 16) {
				canSubmit= true;
				document.getElementById('cardNoError').style.display = 'none';
			} else {
				document.getElementById('cardNo').style.borderColor = "red";
				 document.getElementById('cardNoError').innerHTML = 'Please provide a valid card number!';
				canSubmit= false;
			}
			
			if (expiryMonth == '') {
				document.getElementById('expiryMonth').style.borderColor = 'red'
					canSubmit = false;
			}
			if (expiryYear == '') {
				document.getElementById('expiryYear').style.borderColor = 'red'
					canSubmit = false;
			}
			if (cvv == '') {
				document.getElementById('cvv').style.borderColor = 'red'
					canSubmit = false;
			}

			canSubmit = false;
		} else {
			canSubmit = true;
		}

		return canSubmit;
	}
	
	function netBankingValidation() {

		var canSubmit=true;
		document.getElementById('bankType').style.borderColor = '#cccccc'
		document.getElementById('transferType').style.borderColor = '#cccccc'
		document.getElementById('beneficiaryName').style.borderColor = '#cccccc'
		document.getElementById('beneficiaryAccount').style.borderColor = '#cccccc'
		document.getElementById('beneficiaryBank').style.borderColor = '#cccccc'
		document.getElementById('beneficiaryIfsc').style.borderColor = '#cccccc'

		var bankType = document.getElementById('bankType').value;
		var transferType = document.getElementById('transferType').value;
		var bankType = document.getElementById('bankType').value;

		if (bankType == '') {
			document.getElementById('bankType').style.borderColor = 'red'

			canSubmit = false;
			return canSubmit;
		}

		if (bankType!='SameBank' && transferType == '') {
			document.getElementById('transferType').style.borderColor = 'red'

			canSubmit = false;
			return canSubmit;
		}

		var beneficiaryName = document.getElementById('beneficiaryName').value;
		var beneficiaryAccount = document.getElementById('beneficiaryAccount').value;

		if (beneficiaryName == '') {
			document.getElementById('beneficiaryName').style.borderColor = 'red'
			canSubmit = false;

		}

		if (beneficiaryAccount == '') {
			document.getElementById('beneficiaryAccount').style.borderColor = 'red'
			canSubmit = false;

		}

		if (bankType == 'DifferentBank') {

			var beneficiaryBank = document.getElementById('beneficiaryBank').value;
			var beneficiaryIfsc = document.getElementById('beneficiaryIfsc').value;

			if (beneficiaryBank == '') {
				document.getElementById('beneficiaryBank').style.borderColor = 'red'
				canSubmit = false;

			}

			if (beneficiaryIfsc == '') {
				document.getElementById('beneficiaryIfsc').style.borderColor = 'red'
				canSubmit = false;

			}

		}

		return canSubmit;
	}

	function chequeDdValidation() {

		var chequeNo = document.getElementById('chequeNo').value;
		if (chequeNo == '' || chequeNo == null) {
			document.getElementById('chequeNo').style.borderColor = "red";
			return false;
		} else {
			document.getElementById('chequeNo').style.borderColor = "green";
		}
		var chequeDate = document.getElementById('chequeDate').value;
		if (chequeDate == '' || chequeDate == null) {
			document.getElementById('chequeDate').style.borderColor = "red";
			return false;
		} else {
			document.getElementById('chequeDate').style.borderColor = "green";
		}

		var chequeBank = document.getElementById('chequeBank').value;
		if (chequeBank == '' || chequeBank == null) {
			document.getElementById('chequeBank').style.borderColor = "red";
			return false;
		} else {
			document.getElementById('chequeBank').style.borderColor = "green";
		}
		var chequeBranch = document.getElementById('chequeBranch').value;
		if (chequeBranch == '' || chequeBank == null) {
			document.getElementById('chequeBranch').style.borderColor = "red";
			return false;
		} else {
			document.getElementById('chequeBranch').style.borderColor = "green";
		}
		
		return true;

	}

	function val() {
		debugger;
		if("${productConfiguration.isTopupAllowed}" != 1){
			document.getElementById('topupAmountError').innerHTML = 'Topup is not allowed for this deposit';
			return false;
		}
		
	 	if("${lockingPeriod}"=="true"){
			document.getElementById('topupAmountError').innerHTML = 'Sorry, Deposit is still within the locking period for Top-up.';
			return false;
		}
		 
		
		
		var fdAmount = parseFloat(document.getElementById('fdAmount').value);
		
		
		if (fdAmount == 0 || isNaN(fdAmount)) {
			document.getElementById('fdAmount').style.borderColor = "red";
			return false;
		} else {
			document.getElementById('fdAmount').style.borderColor = "green";
		}
		

		var paymentMode = document.getElementById('hdnPayment').value;

		if (paymentMode == 'DD' || paymentMode == 'Cheque') {
			if(chequeDdValidation()==false)
				return false;
		}
		else if (paymentMode == 'Card Payment'|| paymentMode == 'Credit Card'||paymentMode == 'Debit Card') {
			
			if(cardValidation()==false)
				return false;
		}
		else if (paymentMode == 'Net Banking') {
		
			if(netBankingValidation()==false)
				return false;

		}
		
		
		
		
		 document.getElementById('topupAmountError').innerHTML = '';
		 
 			 var minDepositAmount=parseFloat('${productConfiguration.minimumTopupAmount}');
				
				if(fdAmount<minDepositAmount){
					document.getElementById('fdAmount').style.borderColor = "red";
					 document.getElementById('topupAmountError').innerHTML = 'Entered amount should be more than '+minDepositAmount;
					return false;  
				}
				
              var maxDepositAmount=parseFloat('${productConfiguration.maximumTopupAmount}');
				
				if(fdAmount>maxDepositAmount){
					document.getElementById('fdAmount').style.borderColor = "red";
					 document.getElementById('topupAmountError').innerHTML = 'Entered amount should be less than '+maxDepositAmount;
					return false;  
				}
		}

	function onchangeBankType(value) {

		if (value == 'SameBank') {

			document.getElementById('benificiaryName').style.display = 'block';
			document.getElementById('accNumber').style.display = 'block';

			document.getElementById('bankNameTr').style.display = 'none';
			document.getElementById('ifscId').style.display = 'none';
			document.getElementById('transferOption').style.display = 'none';
 	}

		else if (value == 'DifferentBank') {
			document.getElementById('benificiaryName').style.display = 'block';
			document.getElementById('accNumber').style.display = 'block';
			document.getElementById('bankNameTr').style.display = 'block';
			document.getElementById('ifscId').style.display = 'block';
			document.getElementById('transferOption').style.display = 'block';
 		}

		else {

			document.getElementById('benificiaryName').style.display = 'none';
			document.getElementById('accNumber').style.display = 'none';
			document.getElementById('bankNameTr').style.display = 'none';
			document.getElementById('ifscId').style.display = 'none';
		}

	}

	function changeExpiryDate() {

		document.getElementById('expiryDate').value = document
				.getElementById('expiryMonth').value
				+ "-" + document.getElementById('expiryYear').value
	}
	
	function onChangeAmount(value){
		
		value=parseFloat(value);
		if(isNaN(value)){
			value=0;
		}
		var size= <c:out value="${depositHolderList.size()}"/>;
		
		for(var i=0;i<size;i++){
			var withdrawId= "withdrawAmount["+i+"]";
			var contributionId="contribution["+i+"]";
			var contribution =parseFloat(document.getElementById(contributionId).value)/100;
			var result=roundToTwo(parseFloat(value)* contribution)
			document.getElementById(withdrawId).value =result;
		}
		
	}
	
	function roundToTwo(num) {    
	    return +(Math.round(num + "e+2")  + "e-2");
	}
	
function searchDepost(){
	
		$("#fdForm").submit();
		}
		
function isNumberKey1(evt,obj)
{
	
	var minimumBalance1_ = document.getElementById(evt.target.id);

   var charCode = (evt.which) ? evt.which : evt.keyCode;
   if (charCode != 46 && charCode > 31 
     && (charCode < 48 || charCode > 57)){
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
   if (minimumBalance1_.value.length>10 && event.keyCode != 8){
	   	return false;
	   }
   
  }

</script>
<div class="right-container" id="right-container">
	<div class="container-fluid">


		<div class="Flexi_deposit">

			<div class="header_customer">
				<h3 id="pageTitle"></h3>
			</div>
			<div class="Success_msg">
				<div class="successMsg"
					style="text-align: center; color: green; font-size: 18px;">
					<h2>${success}</h2>
				</div>
				
				<div 
					style="text-align: center; color: red; font-size: 18px;">
					<h2>${error}</h2>
				</div>
			</div>
			
			<div class="flexi_table">
				<form:form action="fdPaymentDetailsConfirm" method="post"
					name="fixedDeposit" class="form-horizontal"
					commandName="fixedDepositForm">
					<form:hidden path="paymentMode" id="payMode" />
					<input type="hidden" name="menuId" value="${menuId}" />
					<input type="hidden" id="hdnPayment" value="${paymentMode }" />
					<form:hidden path="contactNum" value="" />
					<form:hidden path="email" />
					<form:hidden path="category" value="" />
					<form:hidden path="fdTenureDate" value="" />
					<form:hidden path="depositHolderId" />
					<form:hidden id="paymentMadeByHolderIds" path="paymentMadeByHolderIds" />
					<form:hidden path="depositId" />


					<form:hidden path="productConfigurationId" />


					<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.customerId" /></label>
						<div class="col-md-4">
							<form:input path="customerId" class="form-control"
								style="background: whitesmoke;" id="customerID" readonly="true" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.customerName" /></label>
						<div class="col-md-4">
							<form:input path="customerName" class="form-control"
								style="background: whitesmoke;" id="customerName"
								readonly="true" />
						</div>
					</div>

					<div class="form-group">
						<label class="col-md-4 control-label">Deposit Account
							Number</label>
						<div class="col-md-4">
							<form:input path="accountNo" class="form-control"
								style="background: whitesmoke;" readonly="true" />
								
						</div>
					</div>

					<div class="form-group">
						<label class="col-md-4 control-label">Enter Amount<span
							style="color: red">*</span></label>


						<div class="col-md-4">
							<form:input path="fdAmount" class="form-control"
								style="background: #fff; cursor:text;" id="fdAmount"
								onkeyup="onChangeAmount(this.value)"
								onkeypress="return isNumberKey1(event,$(this))" />
							<span id="fdAmountError" style="display: none; color: red;">
								<spring:message code="label.enterData" />
							</span>
						</div>
					</div>

					<!--.................  Cheque/DD PAYMENT....................-->


					<div id="chequeDdDiv" style="display:none;">
						<div class="form-group">
							<label class="col-md-4 control-label"> <span
								id="chequeDDNoLabel"></span><span style="color: red">*</span></label>
							<div class="col-md-4">
								<form:input path="chequeNo" class="form-control"
									style="background: #fff; cursor: text;" type="number"
									id="chequeNo" onkeypress="validationAccount(event)" />
								<span id="ddNoError" style="display: none; color: red;">
									<spring:message code="label.enterData" />
								</span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label"><span
								id="chequeDDDateLabel"><spring:message code="label.date" /></span><span
								style="color: red">*</span></label>
							<div class="col-md-4">
								<fmt:formatDate value="${date}" var="dateFormat"
									pattern="dd/MM/yyyy" />

								<form:input path="chequeDate"
									class="form-control datepicker-here"
									style="background: #fff; cursor: pointer;" id="chequeDate"
									value="${todaysDate}" readonly="true" />
								<span id="ddDateError" style="display: none; color: red;">
									<spring:message code="label.enterData" />
								</span>
							</div>
						</div>
						<div class="form-group" >
							<label class="col-md-4 control-label"><spring:message
									code="label.bank" /><span style="color: red">*</span></label>
							<div class="col-md-4">
								<form:input path="chequeBank" class="form-control"
									style="background: #fff; cursor: text;" id="chequeBank"
									pattern="[a-zA-Z ]+" onkeypress="validName(event)" />
								<span id="ddBankError" style="display: none; color: red;">
									<spring:message code="label.enterData" />
								</span>
							</div>
						</div>
						<div class="form-group" >
							<label class="col-md-4 control-label"><spring:message
									code="label.branch" /><span style="color: red">*</span></label>
							<div class="col-md-4">
								<form:input path="chequeBranch" class="form-control"
									style="background: #fff; cursor: text;" id="chequeBranch"
									pattern="[a-zA-Z ]+" onkeypress="validName(event)" />
							</div>
						</div>
					</div>




					<!-- ..................NET BANKING PAYMENT.................... -->

					<div id="netBankingDetailsTr" style="display:none;">

						<div class="form-group">
							<label class="col-md-4 control-label">Select Bank<span
								style="color: red">*</span></label>
							<div class="col-md-6">
								<form:select path="fdPayType" id="bankType"
									onchange="onchangeBankType(this.value)" class="form-control">
									<option value="">Select</option>
									<option id="savingBankId" value="SameBank">Same Bank</option>
									<option id="differentBankId" value="DifferentBank">
										Different Bank</option>

								</form:select>
							</div>
						</div>

						<div class="form-group" id="transferOption" style="display:none;">
							<label class="col-md-4 control-label">Transfer<span
								style="color: red">*</span></label>
							<div class="col-md-6">
								<form:select path="otherPayTransfer1"
									id="transferType" class="form-control">
									<option value="">Select</option>
									<option value="NEFT">NEFT</option>
									<option value="IMPS">IMPS</option>
									<option value="RTGS">RTGS</option>

								</form:select>
							</div>
						</div>

						<div class="form-group" id="benificiaryName" style="display:none;">
							<label class="control-label col-sm-4" for="">Beneficiary
								Name<span style="color: red">*</span>
							</label>

							<div class="col-sm-6">
								<form:input path="otherName1" class="form-control"
									id="beneficiaryName" />
							</div>

						</div>


						<div class="form-group" id="accNumber" style="display:none;">
							<label class="control-label col-sm-4" for="">Beneficiary
								Account Number<span style="color: red">*</span>
							</label>

							<div class="col-sm-6">
								<form:input path="otherAccount1"
									class="form-control" id="beneficiaryAccount" />
							</div>

						</div>

						<div class="form-group" id="bankNameTr" style="display:none;">
							<label class="control-label col-sm-4" for="">Bank Name<span
								style="color: red">*</span></label>

							<div class="col-sm-6">
								<input name="bank" class="form-control"
									id="beneficiaryBank" />
							</div>

						</div>

						<div class="form-group" id="ifscId" style="display:none;">
							<label class="control-label col-sm-4" for="">IFSC <span
								style="color: red">*</span></label>

							<div class="col-sm-6">
								<form:input path="otherIFSC1" class="form-control"
									id="beneficiaryIfsc" />
							</div>

						</div>



						<span id="error" style="color: red"></span>

					</div>


					<!-- .....................CARD PAYMENT........................... -->
					<div id="cardDetailsTr" style="display:none;">
						<div class="form-group">
							<label class="col-md-4 control-label">Select Card</label>
							<div class="col-md-6">
								<form:select path="cardType" placeholder="12/20"
									id="cardType" class="form-control">
									<form:option value="Select Card Type">
										<spring:message code="label.selectCard" />
									</form:option>
									<form:option value="Debit Card">
										<spring:message code="label.debit" />
									</form:option>
									<form:option value="Credit Card">
										<spring:message code="label.credit" />
									</form:option>

								</form:select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">Enter Card No</label>
							<div class="col-md-6">
								<form:input path="cardNo"
									placeholder="xxxx-xxxx-xxxx-xxxx" id="cardNo"
									class="form-control d" filtertype="CCNo" />
							</div>
						</div>

						<div style="color: red; margin-left: 315px;">
							<span id="cardNoError"></span>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">Expiry Date</label>
							<div class="col-md-2">
								<select class="form-control" id="expiryMonth"
									onchange="changeExpiryDate()">

									<option value="">MONTH</option>
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
								<select id="expiryYear" class="form-control"
									onchange="changeExpiryDate()">

									<option value="">YEAR</option>
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
							<form:hidden path="expiryDate" id="expiryDate" />
							<div class="col-md-2">
								<form:input type="password" path="cvv"
									filtertype="Number" placeholder="CVV" id="cvv"
									class="form-control" style="width:50%" />
							</div>
						</div>
						<span id="cardDetailsError" style="display: none; color: red">Please
							enter all the card details</span>

					</div>









					<table class="table table-striped table-bordered"
						style="margin-top: 49px; width: 95%; margin-left: 25px;">
						<tr>
							<td><b>Deposit holder</b></td>
							<td><b><spring:message code="label.customerName" /></b></td>
							<td><b>Customer id</b></td>
							<td><b>Contribution(%)</b></td>
							<td><b>Distributed amount</b></td>

						</tr>

						<c:forEach items="${depositHolderList}" var="depositHolder"
							varStatus="status">

							<tr>
								<td><b>${status.index+1} </b></td>
								<td><b> <c:out value="${depositHolder.customerName}"></c:out></b></td>
								<td><b> <c:out value="${depositHolder.customerId}"></c:out></b></td>
								<td><b> <input style="border: none"
										value="${depositHolder.contribution}"
										id="contribution[${status.index}]" readonly /></b></td>
								<td><b> <input style="border: none"
										id="withdrawAmount[${status.index}]" readonly /></b></td>

							</tr>
						</c:forEach>
					</table>

					<div class="form-group">
						<span id="topupAmountError"
							style="color: red; margin-left: 398px;"></span>
						<div class="col-md-offset-4 col-md-8">

																
									<input type=button class="btn btn-success"
								onclick="searchDepost()" value="Back">
									 <input type="submit"  onclick="return val();" class="btn btn-info"
								value="<spring:message code="label.confirm"/>">
						</div>
					</div>

				</form:form>
				
				<form:form action="depositPayment" method="post" class="form-horizontal"
					commandName="fixedDepositForm" id="fdForm">
				<form:hidden path="accountNo" value="${fixedDepositForm.accountNo}"/>
				</form:form>
			</div>

		</div>
		<style>
.myform-control {
	background: #999A95;
	color: #000;
	cursor: no-drop;
}
</style>