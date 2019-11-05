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
		
		<script	src="<%=request.getContextPath()%>/resources/js/ccvalidate-tapan.min.js"></script>

<div class="right-container" id="right-container">
	<div class="container-fluid">

		


		<div class="Flexi_deposit">

			<div class="header_customer" style="margin-bottom: 30px;">
				<h3>
					
					Overdraft Payment
				</h3>
			</div>
			
			<div class="flexi_table">
				<form:form class="form-horizontal" action="savePaymentOverdraft"  onsubmit="return validate();" id="withdrawOverdraft" method="post"
					  commandName="depositForm"  autocomplete="off">


					<div class="form-group">
						<label class="col-md-4 control-label">Overdraft Number<span style="color: red">*</span></label>
						<div class="col-md-4">
							<form:input path="overdraftNumber" class="myform-control" id="overdraftNumber" 
								placeholder="Enter Overdraft Number"  required="true" autocomplete="off"/>

						</div>
					</div>
					
					<div class="col-sm-12 col-md-12 col-lg-12"
							style="margin-left: 402px;">
							<input type="button" class="btn btn-primary"
								 value="Search" onClick="getOverdraftDetails()">

						</div>
						
						<!-- <div class="form-group">
							<label class="col-md-4 control-label">Overdraft Number<span style="color: red">*</span></label>
							<div class="col-md-4">
								

<select name="overdraftNumber" onChange="getOverdraftDetails($(this));" class="myform-control" id="overdraftNumber"><option value="Select">Select</option></select>
							</div>
	
						</div> -->
						
						<!-- <div class="form-group">
						<label class="col-md-4 control-label">Current Balance<span style="color: red">*</span></label>
						<div class="col-md-4">
							<input class="myform-control" id="currentBalance"
								 autocomplete="off" readonly="true" />

						</div>
					</div> -->
					<%-- <div class="form-group">
						<label class="col-md-4 control-label">Withdrawable Amount<span style="color: red">*</span></label>
						<div class="col-md-4">
							<form:input path="withdrawableAmount" class="myform-control" id="withdrawableAmount"
								placeholder=" "  autocomplete="off" readonly="true" />

						</div><span id="withdrwableAmountError" style="display: none; color: red;"><spring:message code="label.validation" /></span>
					</div> --%>
					
					  <div class="form-group">
						<label class="col-md-4 control-label">Amount to Return</label>
						<div class="col-md-4">
							<input id="totalAmountToReturn" class="form-control" 
								placeholder="" autocomplete="off"    readonly="true"/>
							<span id="totalSanctionedAmountError" style="display: none; color: red;"><spring:message code="label.validation" /></span>
						</div>
					</div> 
					
					<div class="form-group">
						<label class="col-md-4 control-label">Total Amount Paid</label>
						<div class="col-md-4">
							<input id="totalAmountPaid" class="form-control" 
								placeholder="" autocomplete="off"    readonly="true"/>
							<span id="totalSanctionedAmountError" style="display: none; color: red;"><spring:message code="label.validation" /></span>
						</div>
					</div> 
					<div class="form-group">
						<label class="col-md-4 control-label">Outstanding Payment</label>
						<div class="col-md-4">
							<input id="outstandingAmount" class="form-control"
								placeholder="" autocomplete="off"    readonly="true"/>
							<span id="totalSanctionedAmountError" style="display: none; color: red;"><spring:message code="label.validation" /></span>
						</div>
					</div> 
					<div class="form-group">
							<label class="col-md-4 control-label">Payment Date<span style="color: red">*</span></label>
							<div class="col-md-4">
								<form:input path="createdDate" value="${todaysDate}"
									placeholder="Select Date" class="form-control"
									style="background: whitesmoke; cursor: pointer;"
									id="datepicker" required="true" readonly="true" />

							</div>

						</div>
					
					
					 <div class="form-group">
						<label class="col-md-4 control-label">Amount to Pay<span style="color: red">*</span></label>
						<div class="col-md-4">
							<form:input  autocomplete="off" path="depositAmount" id="withdrawAmount" class="form-control"
								placeholder="" />
						
						</div>
					</div>
					
					<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.modeOfPay" /><span style="color: red">*</span></label>
							<div class="col-md-4">
								<form:select id="fdPay" placeholder="Payment Mode"
									path="paymentMode" onchange="showDetails(this)"
									class="myform-control">
									<form:option value="Select">
										<spring:message code="label.select" />
									</form:option>
									<c:forEach items="${paymentMode}" var="mode">
												<option mode="${mode.paymentMode}"	value="${mode.id}">${mode.paymentMode}</option>
									</c:forEach>
								</form:select>
								 <span id="fdPayError"
									style="display: none; color: red;">Please select Mode of
									Payment</span>
							</div>
						</div>
						<div class="form-group" id="linkedAccountTr" style="display:none">
							<label class="col-md-4 control-label">Account Number<span
								style="color: red">*</span></label>
							<div class="col-md-6">
								<form:input path="linkedAccountNo"
									class="form-control" id="linkedAccount" readonly="true" />
							</div>
						</div>
						<div class="form-group" id="linkedAccountBalanceTr" style="display:none">
							<label class="col-md-4 control-label">Account Balance<span
								style="color: red">*</span></label>

							<div class="col-md-6">
								<form:hidden path="accountBalance"
									id="linkedAccountBalance1" />
								<input class="form-control" id="linkedAccountBalance" readonly />
							</div>
						</div>
						
						<div style="display: none" id="bankDetailsTr">
							<div class="form-group" id="chequeNoTr">
								<label class="col-md-4 control-label"> <span
									id="chequeNoLabel" style="display: none">Cheque Number<span
										style="color: red">*</span></span><span id="ddNoLabel"
									style="display: none">DD Number<span style="color: red">*</span></span></label>
								<div class="col-md-6">
									<form:input path="chequeNo"
										class="input form-control" id="chequeNo" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label"> <span
									id="chequeDateLabel" style="display: none">Cheque Date<span
										style="color: red">*</span></span><span id="ddDateLabel"
									style="display: none">DD Date<span style="color: red">*</span></span></label>
								<div class="col-md-6">
									<form:input path="chequeDate" value="${todaysDate}"
										readonly="true" class="form-control datepicker-here"
										style="background: whitesmoke; cursor: pointer;"
										id="chequeDate" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.bank" /><span style="color: red">*</span></label>
								<div class="col-md-6">
									<form:input path="chequeBank"
										class="input form-control" id="chequeBank" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.branch" /><span style="color: red">*</span></label>
								<div class="col-md-6">
									<form:input path="chequeBranch"
										class="input form-control" id="chequeBranch" />
								</div>
							</div>
							<span id="bankDetailsError" style="display: none; color: red">Please
								enter all the bank details</span>
								</div>
						<div style="display: none" id="cardDetailsTr">
							<div class="form-group">
								<label class="col-md-4 control-label">Select Card</label>
								<div class="col-md-6">
									<form:select path="cardType" placeholder="12/20"
										id="cardType" class="input form-control">
										<form:option value="">
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
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label">Enter Card No</label>
								<div class="col-md-6">
									<form:input path="cardNo"
										placeholder="xxxx-xxxx-xxxx-xxxx" id="cardNo"
										class="input form-control cardnum" filtertype="CCNo" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label">Expiry Date</label>
								<div class="col-md-2">
									<select class="input form-control" id="expiryMonth"
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
									<select id="expiryYear" class="input form-control"
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
								<input style="opacity: 0; position: absolute;"> <input
									type="password" style="opacity: 0; position: absolute;">
								<div class="col-md-2">
									<form:input path="cvv" filtertype="text"
										placeholder="CVV" id="cvv" class="input form-control"
										style="width:50%" type="password"
										onkeypress="validationCVV(event)" />
								</div>
							</div>

							<span id="cardDetailsError" style="display: none; color: red">Please
								enter all the card details</span>
						</div>

						<div style="display: none" id="netBankingDetailsTr">


							<div class="form-group">
								<label class="col-md-4 control-label">Select Bank<span
									style="color: red">*</span></label>
								<div class="col-md-6">
									<form:select path="fdPayType" id="bankType"
										onchange="onchangeBankType()" class="input form-control">
										<form:option value="">
										Select
									</form:option>
										<form:option id="savingBankId" value="SameBank">
										Same Bank
									</form:option>
										<form:option id="differentBankId" value="DifferentBank">
										Different Bank
									</form:option>

									</form:select>
								</div>
							</div>
							<div class="form-group" id="paymentBeneficiaryNameDiv">
								<label class="control-label col-sm-4" for="">Beneficiary
									Name<span style="color: red">*</span>
								</label>

								<div class="col-sm-6">
									<form:input path="otherName1" class="input form-control"
										id="paymentBeneficiaryName" />
								</div>

							</div>


							<div class="form-group" id="paymentBeneficiaryAccountDiv">
								<label class="control-label col-sm-4" for="">Account
									Number<span style="color: red">*</span>
								</label>

								<div class="col-sm-6">
									<form:input path="otherAccount1" type="text"
										class="input form-control" id="paymentBeneficiaryAccount"
										onkeypress="validationAccount(event)" />
								</div>

							</div>

							<div class="form-group" id="transferOption">
								<label class="col-md-4 control-label">Transfer<span
									style="color: red">*</span></label>
								<div class="col-md-6">
									<form:select path="otherPayTransfer1" id="transferType"
										class="input form-control">
										<form:option value="">
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
							<div class="form-group" id="paymentBankNameTr">
								<label class="control-label col-sm-4" for="">Bank Name<span
									style="color: red">*</span></label>

								<div class="col-sm-6">
									<form:input path="otherBank1" class="input form-control"
										id="paymentBeneficiaryBank" onkeypress="validName(event)" />
								</div>

							</div>

							<div class="form-group" id="paymentBeneficiaryIfscDiv">
								<label class="control-label col-sm-4" for="">IFSC <span
									style="color: red">*</span></label>

								<div class="col-sm-6">
									<form:input path="otherIFSC1" class="input form-control"
										id="paymentBeneficiaryIfsc" onkeypress="validIFSC(event)" />
								</div>

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
		    	 
		    	}
		    	
		    	if(!drafts.includes(data.overdraftNumber))
		    	{
		    		drafts.push(data.overdraftNumber);
		    		overdrafts += '<option value="'+data.overdraftNumber+'">'+data.overdraftNumber+'</option>'; 
		    	}
	    	}
	    	$('#overdraftNumber').append(overdrafts);
	    	
	    	document.getElementById('currentBalance').value= response[0].currentBalance;
	    	 
	    },  
	    error: function(e){ 
	    	alert("overdraft details not exist")
	    	$('#error').html("Error occured !!")
	    	 
	    }  
	  });  
	return data;
	}  





function showDetails(obj) {

	var paymentMode = $('option:selected', obj).attr('mode');

	if (paymentMode == 'Select') {
		document.getElementById('bankDetailsTr').style.display = 'none';
		document.getElementById('cardDetailsTr').style.display = 'none';
		document.getElementById('netBankingDetailsTr').style.display = 'none';
		document.getElementById('linkedAccountBalanceTr').style.display = 'none';
		document.getElementById('linkedAccountTr').style.display = 'none';
	}
	var linkedAccountNo = document.getElementById('linkedAccount').value;

	if (paymentMode == 'Net Banking') {
		document.getElementById('paymentBeneficiaryNameDiv').style.display = 'block';
		document.getElementById('paymentBeneficiaryAccountDiv').style.display = 'block';

		if ($('#bankType').val() == 'DifferentBank') {
			document.getElementById('transferOption').style.display = 'block';
			document.getElementById('paymentBankNameTr').style.display = 'block';
			document.getElementById('paymentBeneficiaryIfscDiv').style.display = 'block';

		} else {
			document.getElementById('transferOption').style.display = 'none';
			document.getElementById('paymentBankNameTr').style.display = 'none';
			document.getElementById('paymentBeneficiaryIfscDiv').style.display = 'none';

		}

	}
	if (paymentMode == 'Fund Transfer') {
		document.getElementById('bankDetailsTr').style.display = 'none';
		document.getElementById('cardDetailsTr').style.display = 'none';
		document.getElementById('netBankingDetailsTr').style.display = 'none';

		document.getElementById('linkedAccountBalanceTr').style.display = 'block';
		document.getElementById('linkedAccountTr').style.display = 'block';
		var accArray = $('#accountNo').val().split(',');
		document.getElementById('linkedAccount').value = accArray[0];
		document.getElementById('linkedAccountBalance1').value = $(
				'#accountBalance1').val();
		document.getElementById('linkedAccountBalance').value = parseFloat(
				$('#accountBalance1').val())
				.toLocaleString("en-US");

	}

	if (paymentMode == 'Cash') {
		document.getElementById('bankDetailsTr').style.display = 'none';
		document.getElementById('cardDetailsTr').style.display = 'none';
		document.getElementById('netBankingDetailsTr').style.display = 'none';
		if (linkedAccountNo == "") {

		} else {
			document.getElementById('linkedAccountBalanceTr').style.display = 'none';
			document.getElementById('linkedAccountTr').style.display = 'none';

		}
	}
	if (paymentMode == 'DD') {

		document.getElementById('bankDetailsTr').style.display = 'block';
		document.getElementById('cardDetailsTr').style.display = 'none';
		document.getElementById('netBankingDetailsTr').style.display = 'none';

		/* for labels */
		document.getElementById('ddNoLabel').style.display = 'block';
		document.getElementById('ddDateLabel').style.display = 'block';
		document.getElementById('chequeNoLabel').style.display = 'none';
		document.getElementById('chequeDateLabel').style.display = 'none';

		if (linkedAccountNo == "") {

		} else {
			document.getElementById('linkedAccountBalanceTr').style.display = 'none';
			document.getElementById('linkedAccountTr').style.display = 'none';

		}

	}
	if (paymentMode == 'Cheque') {

		document.getElementById('bankDetailsTr').style.display = 'block';
		document.getElementById('cardDetailsTr').style.display = 'none';
		document.getElementById('netBankingDetailsTr').style.display = 'none';

		/* for labels */
		document.getElementById('ddNoLabel').style.display = 'none';
		document.getElementById('ddDateLabel').style.display = 'none';
		document.getElementById('chequeNoLabel').style.display = 'block';
		document.getElementById('chequeDateLabel').style.display = 'block';

		if (linkedAccountNo == "") {

		} else {
			document.getElementById('linkedAccountBalanceTr').style.display = 'none';
			document.getElementById('linkedAccountTr').style.display = 'none';

		}
	}
	if (paymentMode == 'Credit Card'|| paymentMode == 'Debit Card')
	{
		document.getElementById('bankDetailsTr').style.display = 'none';
		document.getElementById('netBankingDetailsTr').style.display = 'none';
		document.getElementById('cardDetailsTr').style.display = 'block';
		if (linkedAccountNo == "") {

		} else {
			document.getElementById('linkedAccountBalanceTr').style.display = 'none';
			document.getElementById('linkedAccountTr').style.display = 'none';

		}
	}

	if (paymentMode == 'Net Banking') {

		document.getElementById('bankDetailsTr').style.display = 'none';
		document.getElementById('cardDetailsTr').style.display = 'none';
		document.getElementById('netBankingDetailsTr').style.display = 'block';
		if (linkedAccountNo == "") {

		} else {
			document.getElementById('linkedAccountBalanceTr').style.display = 'none';
			document.getElementById('linkedAccountTr').style.display = 'none';

		}
	}

}



function validate() {
	debugger;
	var submit=true;
	validationError.innerHTML='';
	var withdrawAmount = document.getElementById('withdrawAmount').value;
	
	if($("#withdrawAmount").val()==""){
		
		document.getElementById("withdrawAmount").style.borderColor = "red"
		errorMsg = "<br>Amount can not be empty";
		validationError.innerHTML += errorMsg;
		
		submit = false;
	}else {
		withdrawAmount = parseFloat(withdrawAmount);
		var amountTopay= parseFloat($('#outstandingAmount').val());
		if(withdrawAmount>amountTopay){
			document.getElementById("withdrawAmount").style.borderColor = "red"
				errorMsg = "<br>Amount to be paid is "+amountTopay;
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
	$(".cardnum").bind("keypress", function(e) {
		debugger;
		if (!isCCNo(e, this)) {
			return false
		}
	});
	  $('#withdrawAmount').bind('keypress', function (event) {
	        var regex = new RegExp("^[0-9 ]*$");
	        var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
	        if (!regex.test(key)) {
	            event.preventDefault();
	            return false;
	        }
	       
	        
	    });
	  $('#chequeNo')
		.bind(
				'keypress',
				function(event) {
					var regex = new RegExp(
							"^[0-9]+$");
					var key = String
							.fromCharCode(!event.charCode ? event.which
									: event.charCode);
					if (!regex.test(key)) {
						//alert("false")
						event.preventDefault();
						return false;
					}
				});

$('#cardNo')
		.bind(
				'keypress',
				function(event) {
					var regex = new RegExp(
							"^[0-9]$");
					var key = String
							.fromCharCode(!event.charCode ? event.which
									: event.charCode);
					var value = this.value;
					if (!regex.test(key)) {
						event.preventDefault();
						return false;
					}
				});

$('#beneficiaryIfsc')
		.bind(
				'keypress',
				function(event) {
					var regex = new RegExp(
							"^[A-Za-z]{4}[a-zA-Z0-9]{7}$");
					var key = String
							.fromCharCode(!event.charCode ? event.which
									: event.charCode);
					var value = this.value;
					if (!regex.test(key)
							&& value.length > 10) {
						alert("invalid ifsc code")
						event.preventDefault();
						return false;
					}
				});

$('#beneficiaryIfsc')
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
$('#paymentBeneficiaryName').bind('keypress',function(event) {
	var regex = new RegExp(
			"^[a-zA-Z ]+$");
	var key = String
			.fromCharCode(!event.charCode ? event.which
					: event.charCode);
	if (this.value.length == 0
			&& event.keyCode == 32) {
		event.preventDefault();
		return false;
	}
	if (!regex.test(key)) {
		//alert("false")
		event.preventDefault();
		return false;
	}
});

$('#chequeBranch').bind('keypress',function(event) {
	var regex = new RegExp(
			"^[a-zA-Z]+$");
	var key = String
			.fromCharCode(!event.charCode ? event.which
					: event.charCode);
	if (!regex.test(key)) {
		//alert("false")
		event.preventDefault();
		return false;
	}
});

$('#chequeBank')
.bind(
'keypress',
function(event) {
	var regex = new RegExp(
			"^[a-zA-Z]+$");
	var key = String
			.fromCharCode(!event.charCode ? event.which
					: event.charCode);
	if (!regex.test(key)) {
		//alert("false")
		event.preventDefault();
		return false;
	}
});
$('#beneficiaryBank')
.bind(
		'keypress',
		function(event) {
			var regex = new RegExp(
					"^[a-zA-Z ]+$");
			var key = String
					.fromCharCode(!event.charCode ? event.which
							: event.charCode);
			if (this.value.length == 0
					&& event.keyCode == 32) {
				event.preventDefault();
				return false;
			}
			if (!regex.test(key)) {
				//alert("false")
				event.preventDefault();
				return false;
			}
		});

	  
	});
	
function validationCVV(event) {

	var minimumBalance1_ = document.getElementById(event.target.id);

	var keycode = event.which;
	if (!(event.shiftKey == false && (keycode == 8 || keycode == 37 || (keycode >= 48 && keycode <= 57)))) {
		event.preventDefault();
		return false;
	}

	if (minimumBalance1_.value.length > 2 && event.keyCode != 8) {
		event.preventDefault();
		return false;
	}

}

function getOverdraftDetails(obj){
	var draft=$('#overdraftNumber').val();
	//alert(draft);
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
		    	document.getElementById('totalAmountPaid').value= data.totalAmountPaid;
		    	var outstandingAmount =  data.amountToReturn-data.totalAmountPaid;
		    	document.getElementById('outstandingAmount').value= outstandingAmount.toFixed(2);
		    	document.getElementById('totalAmountToReturn').value= data.amountToReturn.toFixed(2);
		    	 
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
	