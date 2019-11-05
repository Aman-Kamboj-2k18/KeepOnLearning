<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="right-container" id="right-container">
	<div class="container-fluid">


		<div class="add-customer">
			<form:form class="form-horizontal" id="bankPaymentForm"
				name="nomineeDetails" autocomplete="off" method="post"
				commandName="bankPaymentForm">
				<!-- action="customerConfirm" -->
				<!-- onsubmit="return val()" -->
				<div class="newcustomer_border">
					<div class="header_customer">
						<h3>
							<spring:message code="label.nomineeDetails" />
						</h3>
					</div>
					<div class="successMsg">
						<b><font color="green">${success}</font></b>
					</div>
					<form:hidden path="customerName" />
					<form:hidden path="customerId" />
					<form:hidden path="depositId" />
					<form:hidden path="depositHolderId" />
					<form:hidden path="bankPaymentId" />
					<form:hidden path="bankPaymnetDetailsId" />
					<div class="add-customer-table" style="padding-top: 20px;">
						<div class="col-md-6">
							
							<div class="form-group">
								<label class="col-md-4 control-label" style="margin-top: -7px;"><spring:message
										code="label.nomineeName" /><span style="color: red"></span></label>
								<div class="col-md-6">
									<form:input path="nomineeName" id="nomineeName"
									 class="form-control" readonly="true"></form:input>
									
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-md-4 control-label" style="margin-top: -16px;"><spring:message
										code="label.nomineeRelationShip" /></b></label>
								<div class="col-md-6">
									<form:input path="nomineeRelationship"
										class="form-control" readonly="true"></form:input>
									
								</div>
							</div>
							
							<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.amt" /></label>
							<div class="col-md-4">
								<form:input path="amount" value="${Amount}"
									class="myform-control" id="amount" readonly="true"></form:input>
							</div>
						</div>
							
						
						</div>
						
						<div class="col-md-6">
							
							<div class="form-group">
								<label class="col-md-4 control-label" style="margin-top: -7px;"><spring:message
										code="label.nomineeAge" /><span style="color: red"></span></label>
								<div class="col-md-6">
									<form:input path="nomineeAge"
										class="form-control" readonly="true"></form:input>
									
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label" style="margin-top: -7px;"><spring:message
										code="label.adress" /></b></label>
								<div class="col-md-6">
									<form:input path="nomineeAddress"
										class="form-control" readonly="true"></form:input>
									
								</div>
							</div>
							
						</div>
						

				</div>
					<div style="clear: both;"></div>
					
					<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.modeOfPay" /><span style="color: red">*</span></label>
						<div class="col-md-4">
							<form:select id="fdPay" path="paymentMode" class="myform-control"
								onchange="onChangeModeOfPay()">
                                 <form:option value="Select">
									<spring:message code="label.select" />
								</form:option>
<%-- 								<form:option value="Cash"> --%>
<%-- 									<spring:message code="label.cashPayment" /> --%>
<%-- 								</form:option> --%>
								<form:option value="DD">
									<spring:message code="label.ddPayment" />
								</form:option>
								<form:option value="Cheque">
									<spring:message code="label.chequePayment" />
								</form:option>
							</form:select>
							<span id="fdPayError" style="display: none; color: red;">
								<spring:message code="label.validation" />
							</span>
						</div>
					</div>
					<div id="ddChequeDiv">
						<div class="form-group">
							<label class="col-md-4 control-label"><span
								id="chequeNoLabel" style="display: none">Cheque Number<span style="color: red">*</span></span><span id="ddNoLabel" style="display: none">DD Number<span style="color: red">*</span></span>
								</label>
							<div class="col-md-4">
								<form:input path="chequeNo" 
									class="myform-control" type="number" id="chequeNo" onkeypress="validationAccount(event)"></form:input>
								<span id="ddNoError" style="display: none; color: red;"><spring:message
										code="label.enterData" /></span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">
							<span
								id="chequeDateLabel" style="display: none">Cheque Date<span
									style="color: red">*</span></span><span id="ddDateLabel"
								style="display: none">DD Date<span style="color: red">*</span></span>
							</label>
							<div class="col-md-4">
								<form:input path="chequeDate" readonly="true" 
									class="myform-control datepicker-here" id="chequeDate"></form:input>
							</div>
						</div>
						
						
						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.bank" /><span style="color: red">*</span></label>
							<div class="col-md-4">
								<form:input path="chequeBank" placeholder="Enter Bank"
									class="myform-control" id="chequeBank" onkeypress="validName(event)"></form:input>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.branch" /><span style="color: red">*</span></label>
							<div class="col-md-4">
								<form:input path="chequeBranch" placeholder="Enter Branch"
									class="myform-control" id="chequeBranch" onkeypress="validName(event)"></form:input>
							</div>
						</div>
						
						
					</div>
				<span id="errorMsg" style="display:none;color:red; margin-left: 369px;margin-bottom: 10px;">Please select mode of payment. </span>
					
					<div class="form-group">
						
						<div class="col-md-offset-4 col-md-8">
							<input type="button" onclick="goSubmit()" id="goBtn"
								 class="btn btn-info" data-toggle="tooltip"
								title="Please first select the customer to click on search"
								value="<spring:message code="label.go"/>">
						</div>
					</div>

			</form:form>
		</div>
	</div>
<script>


function validName(event){
	var valiName = document.getElementById(event.target.id);
	var regex = new RegExp("^[a-zA-Z ]+$");
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



$(document).ready(function() {
	document.getElementById('ddChequeDiv').style.display = "none";

});


function onChangeModeOfPay() {

	var value = document.getElementById('fdPay').value;
	
	var ddChecqueDiv = document.getElementById('ddChequeDiv');
	var chequeNoLabel = document.getElementById('chequeNoLabel');
	var ddNoLabel = document.getElementById('ddNoLabel');
	var chequeDateLabel = document.getElementById('chequeDateLabel');
	var ddDateLabel = document.getElementById('ddDateLabel');
	
	
	if (value == 'Cash') {
		ddChecqueDiv.style.display = "none";

	}
	if (value == 'DD') {
		ddChecqueDiv.style.display = "block";
		
		ddNoLabel.style.display = "block";
		ddDateLabel.style.display = "block";
		chequeDateLabel.style.display = "none";
		chequeNoLabel.style.display = "none";
		
		
		document.getElementsByName('chequeNo')[0].placeholder='Enter DD Number';
		document.getElementsByName('chequeDate')[0].placeholder='Enter DD Date';
	
		
	}
	if (value == 'Cheque') {
		ddChecqueDiv.style.display = "block";
		
		chequeNoLabel.style.display = "block";
		chequeDateLabel.style.display = "block";
		ddDateLabel.style.display = "none";
		ddNoLabel.style.display = "none";
		
		document.getElementsByName('chequeNo')[0].placeholder='Enter Cheque Number';
		document.getElementsByName('chequeDate')[0].placeholder='Enter Cheque Date';
	}


}

function val() {

	var canSubmit = true;

	var value = document.getElementById('fdPay').value;
	if (value == 'Select') {
		document.getElementById('errorMsg').style.display = 'block';
		document.getElementById('errorMsg').value = "Please select mode of payment.";
		canSubmit = false;
	}

	if (value == 'Cash') {
	}
	if (value == 'DD' || value == 'Cheque') {

		var chequeNo = document.getElementById('chequeNo').value;
		if (chequeNo == '' || chequeNo == null) {
			document.getElementById('chequeNo').style.borderColor = "red";
			canSubmit = false;
		} else {
			document.getElementById('chequeNo').style.borderColor = "green";
		}
		var chequeDate = document.getElementById('chequeDate').value;
		if (chequeDate == '' || chequeDate == null) {
			document.getElementById('chequeDate').style.borderColor = "red";
			canSubmit = false;
		} else {
			document.getElementById('chequeDate').style.borderColor = "green";
		}

		var chequeBank = document.getElementById('chequeBank').value;
		
		if (chequeBank == '' || chequeBank == null) {
			document.getElementById('chequeBank').style.borderColor = "red";
			canSubmit = false;
		} else {
			document.getElementById('chequeBank').style.borderColor = "green";
		}
		var chequeBranch = document.getElementById('chequeBranch').value;
		if (chequeBranch == '' || chequeBank == null) {
			document.getElementById('chequeBranch').style.borderColor = "red";
			canSubmit = false;
		} else {
			document.getElementById('chequeBranch').style.borderColor = "green";
		}
	}
	return canSubmit;
}


function goSubmit() {
	var canSubmit = val();
	if (canSubmit == true) {
	
		$("#bankPaymentForm").attr("action", "payAmountToHolder");
		$("#bankPaymentForm").submit();
	} else {
		event.preventDefault();
	}
}
</script>

	<style>
body {
	font-family: Arial, Helvetica, sans-serif;
}

/* The Modal (background) */
.modal {
	display: none;
	position: fixed;
	z-index: 1;
	padding-top: 100px;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	overflow: auto;
	background-color: rgb(0, 0, 0);
	background-color: rgba(0, 0, 0, 0.4);
}

/* Modal Content */
.modal-content {
	background-color: #ebeef5;
	padding: 20px;
	margin-top: 150px;
	margin-left: 283px;
	margin-top: :91px;
	border: 1px solid #888;
	width: 64%;
	height: 419px;
}

/* The Close Button */
.close {
	color: #aaaaaa;
	float: right;
	font-size: 28px;
	font-weight: bold;
}

.close:hover, .close:focus {
	color: #000;
	text-decoration: none;
	cursor: pointer;
}

.form-horizontal .control-label {
	padding-top: 12px;
}
</style>