<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link href="<%=request.getContextPath()%>/resources/css/Validation.css"
	rel="stylesheet">
<script
	src="<%=request.getContextPath()%>/resources/js/ccvalidate-tapan.min.js"></script>



<script>
$(document).ready(function() {
	
		document.getElementById("goBack").addEventListener("click", function(event){

		window.history.back();
		});
	
	//alert("${depositType}");
	// Initiate CC Validation;
	 InitiateValidation();
		});
	
	function onChangeAmount(value){
		
	    value=parseFloat(value);
		if(isNaN(value)){
			value=0;
		}
		
		var size= <c:out value="${depositHolderList.size()}"/>
		
		for(var i=0;i<size;i++){
			var withdrawId= "withdrawAmount["+i+"]";
			var contributionId="contribution["+i+"]";
			var contribution =parseFloat(document.getElementById(contributionId).value)/100;
			var result=roundToTwo(parseFloat(value)* contribution)
			document.getElementById(withdrawId).value =result.toLocaleString("en-US");
		}
		
	}

	function roundToTwo(num) {    
	    return +(Math.round(num + "e+2")  + "e-2");
	}
	
</script>





<div class="right-container" id="right-container">
	<div class="container-fluid">

		<div class="Flexi_deposit">

			<div class="header_customer" style="margin-bottom: 25px;">
				<h3>Card Payment</h3>
			</div>

			<div class="flexi_table">
				<input type="password" style="opacity: 0; position: absolute;"></input>
				<input style="opacity: 0; position: absolute;"></input>
				<form:form action="makePayment" id="fdForm" method="post"
					class="form-horizontal" name="deposit" commandName="fixedDepositForm"
					onsubmit="return val();" autocomplete="off">
					
					<form:hidden path="paymentMode" />
					<input style="opacity: 0; position: absolute;">
					<input type="password" style="opacity: 0; position: absolute;">

					<form:hidden path="depositId" id="depositId" />
					<form:hidden path="expiryDate" id="expiryDate" />

					<div class="form-group">
						<label class="col-md-4 control-label">Deposit Id</label>
						<div class="col-md-6">
							<form:input path="depositId" class="form-control" readonly="true" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label">Account Number</label>
						<div class="col-md-6">
							<form:input path="accountNo" class="form-control"
								readonly="true" />
						</div>
					</div>

					<div class="form-group">
						<label class="col-md-4 control-label">Enter Amount<span
							style="color: red">*</span></label>
						<div class="col-md-6">
							<form:input type="number" onkeypress="validationAccount(event)"
								path="fdAmount" class="form-control" id="fdAmount"
								onkeyup="onChangeAmount(this.value)" autocomplete="off"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label">Select Card<span
							style="color: red">*</span></label>
						<div class="col-md-6">
							<form:select path="cardType" placeholder="12/20" id="cardType"
								class="form-control">
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
						<label class="col-md-4 control-label">Enter Card No<span
							style="color: red">*</span></label>
						<div class="col-md-6">

							<%-- <form:input path="cardNo" placeholder="xxxx-xxxx-xxxx-xxxx"
								autocomplete="off" id="1" class="form-control d"
								filtertype="CCNo" maxlength="19" type="text" readonly="true"
								onfocus="this.removeAttribute('readonly')" /> --%>
								
								<form:input path="cardNo" placeholder="xxxx-xxxx-xxxx-xxxx"
								autocomplete="off" id="1" class="form-control d"
								filtertype="CCNo" maxlength="19" type="text" />
						</div>
					</div>

					<div style="color: red; margin-left: 315px;">
						<span id="cardNoError"></span>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label">Expiry Date<span
							style="color: red">*</span></label>
						<div class="col-md-2">
							<select class="form-control" id="expiryMonth"
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
							<select id="expiryYear" class="form-control"
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
						<input type="password" style="opacity: 0; position: absolute;"></input>
						<input style="opacity: 0; position: absolute;"></input>
						<div class="col-md-4">
							<%--  <label class="col-sm-12 control-label pull-right"><spring:message code="label.cvv" /></label> --%>

							<form:input path="cvv" type="number" placeholder="CVV"
								style="width:25%" id="cvv" filtertype="Number"
								autocomplete="new-password" class="form-control secure-font" />

						</div>

<!-- 						<div class="col-sm-3"></div> -->
<!-- 						<div class="form-group col-sm-5" style="margin-top: 20px;"> -->
<!-- 							<label class="control-label col-sm-3" for=""></label> -->
							
<!-- 								<div class="col-sm-3"> -->
<%-- 								<c:if test="${depositType != 'REVERSE-EMI'}"> --%>
<%-- 									<form:radiobutton path="topUp" id="regularRadio" value="0"></form:radiobutton> --%>
<!-- 									Recurring -->
<%-- 									</c:if> --%>
<!-- 								</div> -->
							

<!-- 							<label class="control-label col-sm-1" for=""></label> -->

<!-- 							<div class="col-sm-3"> -->
<%-- 								<form:radiobutton path="topUp" id="topupRadio" value="1" --%>
<%-- 									checked="checked"></form:radiobutton> --%>
<!-- 								Top Up -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</div> -->

					<div class="header_customer"
						style="padding-top: 1px; padding-bottom: 1px; margin-top: 33px; margin-left: 15px; width:97.25%">
						<h5 align="center">
							<b>Holder Contribution</b>
						</h5>
					</div>

					<table class="table table-bordered"
						style="margin-top: 49px; width: 95%; margin-left: 30px;">
						<tr>
							<td><b>Serial</b></td>
							<td><b><spring:message code="label.customerName" /></b></td>
							<td><b>Holder Status</b></td>
							<td><b>Contribution(%)</b></td>
							<td><b>Distributed Amount</b></td>

						</tr>

						<c:forEach items="${depositHolderList}" var="depositHolder"
							varStatus="status">

							<tr>
								<td><b>${status.index+1} </b></td>
								<td><b> <c:out value="${depositHolder.customerName}"></c:out></b></td>
								<td><b> <c:out
											value="${depositHolder.depositHolderStatus}"></c:out></b></td>
								<td><b> <input style="border: none"
										value="${depositHolder.contribution}"
										id="contribution[${status.index}]" readonly /></b></td>
								<td><b> <input style="border: none"
										id="withdrawAmount[${status.index}]" readonly /></b></td>

							</tr>
						</c:forEach>
					</table>
					<div style="color: red; margin-left: 315px;">
						<span id="dayError"></span>
					</div>

					<div style="color: red; margin-left: 315px;">
						<span id="cardNoError"></span>
					</div>

					<div class="form-group">
						<label class="col-md-4 control-label"> <span
							id="topupAmountError" style="color: red; "></span></label>
						<div class="col-md-6">
							<div class="col-md-6">
								<div class="successMsg">
									<b><font color="error">${error}</font></b>
								</div>
								<button id="goBack" class="btn btn-info"><spring:message
										code="label.back" /></button> <input type="submit"
									class="btn btn-info"
									value="<spring:message code="label.payNow"/>">
							</div>
						</div>
				</form:form>
			</div>

		</div>
	</div>
</div>
<script>



function changeExpiryDate() {

	document.getElementById('expiryDate').value = document
			.getElementById('expiryMonth').value
			+ "-" + document.getElementById('expiryYear').value
}

function val() {
	var canSubmit= true;
	document.getElementById('topupAmountError').innerHTML ='';
	if("${productConfiguration.isTopupAllowed}"!="1"){
		document.getElementById('topupAmountError').innerHTML = 'Topup is not allowed for this deposit';
		return false;
	}
	
 if("${lockingPeriod}"=="true"){
		document.getElementById('topupAmountError').innerHTML = 'Sorry, Deposit is still within the locking period for Top-up.';
		return false;
	} 
	  
	
	var minTopUpAmount=parseFloat('${productConfiguration.minimumTopupAmount}');
	var maxTopUpAmount=parseFloat('${productConfiguration.maximumTopupAmount}');
    var daysDiff = parseFloat('${daysDiff}');
	var payAndWithdrawTenure =parseFloat('${payAndWithdrawTenure}');
	var fdAmount = document.getElementById('fdAmount').value;
	var cardNo = document.getElementById('1').value;
	
	var cardNoSplit = cardNo.split("-");
	var cardNoZeroIndex = cardNoSplit[0];
	var cardNoFirstIndex = cardNoSplit[1];
	var cardNoSecondIndex = cardNoSplit[2];
	var cardNoThirdIndex = cardNoSplit[3];
	var cardNoAfterConcatination = cardNoZeroIndex + cardNoFirstIndex + cardNoSecondIndex + cardNoThirdIndex;
	var cardNoAfterConcatinationLen = cardNoAfterConcatination.length;
if (document.getElementById('fdAmount').value == '') {

	document.getElementById('fdAmount').style.borderColor = "red";
	canSubmit= false;
} else {
	document.getElementById('fdAmount').style.borderColor = "green";
}

if (document.getElementById('cardType').value == 'Select Card Type') {

	document.getElementById('cardType').style.borderColor = "red";
	canSubmit= false;
	return canSubmit;
} else {
	document.getElementById('cardType').style.borderColor = "green";
}


 
 if(isNaN(cardNoAfterConcatination)){
	document.getElementById('1').style.borderColor = "red";
	document.getElementById('cardNoError').innerHTML = '<br> Please input correct card number';
	canSubmit= false;
}
else{
	document.getElementById('1').style.borderColor = "green";
} 
 
 if(cardNoAfterConcatinationLen == 16) {
		canSubmit= true;
	} else {
		document.getElementById('1').style.borderColor = "red";
		 document.getElementById('cardNoError').innerHTML = 'Please provide a valid card number!';
		canSubmit= false;
	}

if (document.getElementById('1').value == '') {

	document.getElementById('1').style.borderColor = "red";
	canSubmit= false;
} else {
	document.getElementById('1').style.borderColor = "green";

}

if (document.getElementById('expiryMonth').value == 'MM' ) {

	document.getElementById('expiryMonth').style.borderColor = "red";
	canSubmit= false;
} else {
	document.getElementById('expiryMonth').style.borderColor = "green";
}
if (document.getElementById('expiryYear').value == 'YY') {

	document.getElementById('expiryYear').style.borderColor = "red";
	canSubmit= false;
} else {
	document.getElementById('expiryYear').style.borderColor = "green";
}
if (document.getElementById('cvv').value == '') {

	document.getElementById('cvv').style.borderColor = "red";
	canSubmit= false;
} else {
	document.getElementById('cvv').style.borderColor = "green";
}
	if(daysDiff<payAndWithdrawTenure){
		
		 document.getElementById('dayError').innerHTML = 'Your deposit will be matured by next '+daysDiff+' days. So you can not make payment.';
		canSubmit= false;
	}
	
if(fdAmount<minTopUpAmount){

	document.getElementById('fdAmount').style.borderColor = "red";
	 document.getElementById('dayError').innerHTML += '<br> Amount should not be less than ' + minTopUpAmount +'.'
	 canSubmit= false;
}
if(fdAmount>maxTopUpAmount){

	document.getElementById('fdAmount').style.borderColor = "red";
	 document.getElementById('dayError').innerHTML += '<br> Amount should not be more than ' + maxTopUpAmount +'.'
	 canSubmit= false;
}
	return canSubmit;
}



function changeExpiryDate(){
	
	document.getElementById('expiryDate').value= 
		document.getElementById('expiryMonth').value+"-"+document.getElementById('expiryYear').value
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
	 
	 function test(event){
		debugger;
		var keycode = event.which;
	
		 var value = $("#cvv").val();
		 if(value.length == 0){
			 $("#cvv").prop("type", "number");
		 }else if(value.length == 1 && value.length==1){
			 $("#cvv").prop("type", "number");
		 }else{
		
			 $("#cvv").prop("type", "password");
		 }
	 		
	 }
	 
	 function testonfocus(event){
		// debugger;
		/*  var value = $("#cvv").val();
		 if(value.length == 0){
			 $("#cvv").prop("type", "number");
		 }else{
			 $("#cvv").prop("type", "password");
		 } */
	 		
	 }
</script>
<style>
.flexi_table {
	margin-bottom: 210px;
}

.space-45 {
	height: 27px;
}

input#fdID {
	margin-top: 0px;
}

.secure-font {
	-webkit-text-security: disc;
}
</style>