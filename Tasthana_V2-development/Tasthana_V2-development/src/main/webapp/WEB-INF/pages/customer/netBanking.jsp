<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- To fetch the request url -->
<c:set var="req"
	value="${requestScope['javax.servlet.forward.request_uri']}" />
<c:set var="baseURL" value="${fn:split(req,'/')}" />
<script>

function hasDecimalPlace(value, x) {
    var pointIndex = value.indexOf('.');
    return  pointIndex >= 0 && pointIndex < value.length - x;
}




	function validateForm() {
		debugger;
	    var daysDiff = parseFloat('${daysDiff}');
		var preventionOfWithdrawBeforeMaturity = parseFloat('${preventionOfWithdrawBeforeMaturity}');
		var minTopUpAmount=parseFloat('${minTopUpAmount}');
		var fdAmount = document.getElementById('fdAmount').value;
		var beneficiaryName= document.getElementById("beneficiaryName")!=null? document.getElementById("beneficiaryName").value :"";
		var beneficiaryAccount= document.getElementById("beneficiaryAccount")!=null? document.getElementById("beneficiaryAccount").value :"";
		var beneficiaryBank= document.getElementById("beneficiaryBank")!=null? document.getElementById("beneficiaryBank").value :"";
		var otherIFSC= document.getElementById("beneficiaryIfsc")!=null? document.getElementById("beneficiaryIfsc").value :"";
		var differentBankRadio= document.getElementById("differentBankRadio").checked;
		var regularPaymentRadio=false;
		if($('#regularRadio').length>0){
			regularPaymentRadio= document.getElementById("regularRadio").checked;
		}
		
		var topupPaymentRadio= document.getElementById("topupRadio").checked;
				var imps= document.getElementById("imps").checked;
				var rtgs= document.getElementById("rtgs").checked;
				var neft= document.getElementById("neft").checked; 
		if (document.getElementById('fdAmount').value == ''
			|| document.getElementById('fdAmount').value == null) {
		document.getElementById('fdAmount').style.borderColor = 'red';
		return false;
	} else {
		document.getElementById('fdAmount').style.borderColor = 'green';
	}
		
		 if(document.getElementById("savingBankRadio").checked==false && document.getElementById("differentBankRadio").checked==false){
			
			 document.getElementById('sameBankError').style.display = 'block';
			return false;
			
		}  
		 else{
			 document.getElementById('sameBankError').style.display = 'none';
		 }
		
			if(document.getElementById("savingBankRadio").checked==true){
			if(beneficiaryName =="" || (imps ==false && rtgs ==false && neft ==false)){
				document.getElementById('benificiaryNameError').style.display = 'block';
				return false;
			}
			else if(beneficiaryAccount =="" ||  (imps ==false && rtgs ==false && neft ==false)){
				document.getElementById('beneficiaryAccountError').style.display = 'block';
				return false;
			}
			else 
			{
				document.getElementById('beneficiaryBank').style.borderColor = 'green';
				document.getElementById('beneficiaryAccount').style.borderColor = 'green';
				document.getElementById('benificiaryNameError').style.display = 'none';
				document.getElementById('beneficiaryAccountError').style.display = 'none';

			} 
			
		}
		
		debugger;
			if(differentBankRadio==true){
				if(beneficiaryName =="" || beneficiaryAccount =="" || (imps ==false && rtgs ==false && neft ==false) || 
						beneficiaryBank =="" || otherIFSC ==""){
				document.getElementById('accountDetailError').style.display = 'block';
				
				return false;
			}
			}
			
			document.getElementById('dayError').innerHTML ='';
			if(fdAmount<minTopUpAmount){
			
				document.getElementById('fdAmount').style.borderColor = "red";
				 document.getElementById('dayError').innerHTML = 'Amount should not be less than ' + minTopUpAmount +".";
				return false;  
			}
			
		debugger;
			if(daysDiff<preventionOfWithdrawBeforeMaturity){
				 document.getElementById('dayError').innerHTML += '<br>Your deposit will be matured by next '+daysDiff+' days. So you can not make payment.';
				 return false;  
				 
			}
			
		
		
	}

	
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
	
	
	function isNumber(evt) {
	    evt = (evt) ? evt : window.event;
	    var charCode = (evt.which) ? evt.which : evt.keyCode;
	    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
	        return false;
	    }
	    return true;
	}
	$( document ).ready(function() {
	//	alert("${depositType}");
	});
	
	
</script>
<div class="right-container" id="right-container">
	<div class="container-fluid">

		<div class="Flexi_deposit">

			<div class="header_customer">
				<h3>
					<spring:message code="label.netBankingPayment" />
				</h3>
			</div>
			<div class="col-sm-12 col-md-12 col-lg-12" style="text-align: center;">
				<div class="errorMsg"><font style ="color: red;" size="4">${error}</font></div>
			</div>
			<div class="flexi_table">
				<!-- action="depositAmount"    -->
<!-- 				//paymentThroughNetBanking -->
				
				<form:form class="form-horizontal" autocomplete="off"
					action="makePayment" onsubmit="return validateForm();"
					method="post" name="fixedDeposit" commandName="fixedDepositForm">

					<form:hidden path="paymentMode" />
					<form:hidden path="maturityDate" />
					<div class="form-group">
						<label class="col-md-4 control-label">Deposit Id</label>
						<div class="col-md-6">
							<form:input path="depositId" class="form-control" readonly="true" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label">Deposit Account
							Number</label>
						<div class="col-md-6">
							<form:input path="accountNo" class="form-control" readonly="true" />
						</div>
					</div>

					<div class="form-group">
						<label class="col-md-4 control-label">Enter Amount<span
							style="color: red">*</span></label>
						<div class="col-md-6">
							<form:input type="text" path="fdAmount"
								class="form-control" id="fdAmount"
								onkeyup="onChangeAmount(this.value)" onkeypress="validationAccount1(event)" autocomplete="off"/>
						</div>
					</div>


					<div class="header_customer"
						style="padding-top: 1px; padding-bottom: 1px; margin-top: 33px;">
						<h5 align="center">
							<b>Payment Information</b>
						</h5>
					</div>
					
					<div class="form-group" style="margin-top: 15px;">
						<label class="control-label col-sm-3" for=""></label>
					
						<div class="col-sm-3"
							style="text-align: left; margin-left: 20px; margin-top: 5px; padding-left: 40px;">
							<c:if test="${depositType != 'REVERSE-EMI'}">
							<form:radiobutton path="depositForm.topUp" id="regularRadio"
								value="1"></form:radiobutton>
								<spring:message code="label.recurring" />
								</c:if>
						</div>
						

						<div class="col-sm-3"
							style="margin-left: -120px; margin-top: 5px;">
							<form:radiobutton path="depositForm.topUp" id="topupRadio"
								value="0" checked="checked"></form:radiobutton>
							Top Up
						</div>
						<label class="control-label col-sm-3" for=""></label>
					</div>

					
					
					<div class="form-group">
						<span id="sameBankError"
							style="display: none; color: red; margin-left: 238px;">Please
							select the Bank</span> <label class="control-label col-sm-3" for=""></label>

						<div class="col-sm-3"
							style="text-align: left; margin-left: 20px; margin-top: 20px; padding-left: 40px;">
							<form:radiobutton path="fdPayOffAccount"
								onclick="showDetailsSameBank()" id="savingBankRadio"
								value="Saving Account"></form:radiobutton>
							<spring:message code="label.savingAccount" />
						</div>


						<div class="col-sm-3"
							style="margin-left: -120px; margin-top: 20px;">
							<form:radiobutton path="fdPayOffAccount"
								onclick="showDetailsDiffBank()" id="differentBankRadio"
								value="Other"></form:radiobutton>
							<spring:message code="label.otherBan" />

						</div>
						<label class="control-label col-sm-3" for=""></label>
					</div>

					<div class="form-group" id="transferModeTr" style="display: none;">
						<label class="control-label col-sm-4" for=""><spring:message
								code="label.transfer" /><span style="color: red">*</span></label>
						<div class="col-sm-6">


							<label for="radio"><form:radiobutton
									path="otherPayTransfer" id="neft" value="NEFT" checked="true"></form:radiobutton></label>
							<spring:message code="label.transfer1" />
							<label for="radio"><form:radiobutton
									path="otherPayTransfer" id="imps" value="IMPS"></form:radiobutton></label>
							<spring:message code="label.transfer2" />
							<label for="radio"><form:radiobutton
									path="otherPayTransfer" id="rtgs" value="RTGS"></form:radiobutton></label>
							<spring:message code="label.transfer3" />
						</div>
					</div>
					<div class="form-group" id="beneficiaryNameTr"
						style="display: none;">
						<span id="benificiaryNameError"
							style="display: none; color: red; margin-left: 238px;">Please
							insert beneficiary name</span> <label class="control-label col-sm-4"
							for="">Beneficiary Name<span style="color: red">*</span>
						</label>
						<div class="col-sm-6">
							<form:input path="otherName" value="" class="form-control"
								style="text: pointer;" id="beneficiaryName" onkeypress="validAlphabetsAndNumbers(event)"/>
						</div>
					</div>
					<div class="form-group" id="accountNoTr" style="display:">
						<span id="beneficiaryAccountError"
							style="display: none; color: red; margin-left: 238px;">Please
							insert Beneficiary Account number</span> <label
							class="control-label col-sm-4" for="">Beneficiary Account
							Number<span style="color: red">*</span>
						</label>
						<div class="col-sm-6">
							<form:input path="otherAccount" type="number" class="form-control"
								style="text: pointer;" id="beneficiaryAccount" onkeypress="validationAccount(event)"/>
						</div>
					</div>
					<div class="form-group" id="bankNameTr" style="display: none;">
						<label class="control-label col-sm-4" for="">Bank Name<span
							style="color: red">*</span></label>
						<div class="col-sm-6">
							<form:input path="otherBank" class="form-control"
								style="text: pointer;" id="beneficiaryBank" pattern="[a-zA-Z ]+" onkeypress="validName(event)"/>
						</div>

						<span id="bankNameError" style="display: none; color: red">Please
							insert Bank Name</span>


					</div>
					<div class="form-group" id="ifscTr" style="display: none;">
						<label class="control-label col-sm-4" for="">IFSC<span
							style="color: red">*</span></label>
						<div class="col-sm-6">
							<form:input path="otherIFSC" class="form-control"
								style="text: pointer;" id="beneficiaryIfsc" onkeypress="validIFSC(event)"/>
						</div>

						<span id="ifscError" style="display: none; color: red">Please
							insert Ifsc</span> <span id="beneficiaryIfscError"
							style="display: none; color: red">Please insert ifsc</span> <span
							id="accountDetailError" style="display: none; color: red">Please
							insert all account details</span>
					</div>



					<div class="header_customer"
						style="padding-top: 1px; padding-bottom: 1px; margin-top: 33px;">
						<h5 align="center">
							<b>Holder Contribution</b>
						</h5>
					</div>

					<table class="table table-bordered"
						style="margin-top: 49px; width: 95%; margin-left: 25px;">
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

			<div  style="color: red; margin-left: 398px;"><span id="dayError"></span></div>
					<div class="form-group">
						<label class="col-md-4 control-label"></label> 
					
						
							<div>
								<%-- <a href="fdListForFundTransfer" class="btn btn-success"><spring:message
										code="label.back" /></a> --%> <input type="submit"
									class="btn btn-primary"
									value="<spring:message code="label.continue"/>">
							</div>
							</div>
						
				</form:form>

			</div>
		</div>
	</div>


	<script>
		function populateBalance() {			
			var estimatedInterest = document.getElementById('estimateInterest').value;
			
			if (document.getElementById("interstPayType1").checked == true) {
				var interestAmtEntered = document.getElementById('interestPayAmount').value;
				if (interestAmtEntered != '') {
					var balanceInterest = Math
							.round((((estimatedInterest - interestAmtEntered) + 0.00001)) * 100) / 100;
					document.getElementById('interestPayAmount1').value = balanceInterest;
				}
			} else if (document.getElementById("interstPayType2").checked == true) {
				
				var interestPercent = document
						.getElementById('interestPercent').value;
				var interestAmount = Math
						.round(((((estimatedInterest * interestPercent) / 100) + 0.00001)) * 100) / 100;
				document.getElementById('interestPayAmount').value = interestAmount;
				var balanceInterest = Math
						.round((((estimatedInterest - interestAmount) + 0.00001)) * 100) / 100;
				document.getElementById('interestPayAmount1').value = balanceInterest;
			}
		}
		
		
		function hideAll(){
			
			document.getElementById('transferModeTr').style.display='none';
			document.getElementById('beneficiaryNameTr').style.display='none';
			document.getElementById('accountNoTr').style.display='none';
			document.getElementById('bankNameTr').style.display='none';
			document.getElementById('ifscTr').style.display='none';

		
		}
		
		function showDetailsSameBank(){
			
			document.getElementById('transferModeTr').style.display='block';
			document.getElementById('beneficiaryNameTr').style.display='block';
			document.getElementById('accountNoTr').style.display='block';
			document.getElementById('bankNameTr').style.display='none';
			document.getElementById('ifscTr').style.display='none';
			document.getElementById('transferModeTr').style.display='none';
		}
		
		function showDetailsDiffBank(){
			
			document.getElementById('transferModeTr').style.display='block';
			document.getElementById('beneficiaryNameTr').style.display='block';
			document.getElementById('accountNoTr').style.display='block';
			document.getElementById('bankNameTr').style.display='block';
			document.getElementById('ifscTr').style.display='block';
			document.getElementById('transferModeTr').style.display='block';
		}
		function displayPartPercentDiv(id){
			
			document.getElementById(id).style.display='block';
			
		}
       function hidePartPercentDiv(id){
			
			document.getElementById(id).style.display='none';
			
		}
       function displayHidePartPercent(id,id1){
    	    document.getElementById(id).style.display='block';
			document.getElementById(id1).style.display='none';
       }
       
       function showGuardian(value,id,majorId,minorId){
    		
   		if(parseInt(value)<18){
   	
   			document.getElementById(id).style.display='block';
   		}
   		else{
   		document.getElementById(id).style.display='none';
   		
   		}
   	} 
	
       
       
       
       
       
       
       
       
       
       function validationAccount(event){
    		
    		var minimumBalance1_ = document.getElementById(event.target.id);

    			 var keycode = event.which;
    		    if (!(event.shiftKey == false && (keycode == 8 || keycode == 37 ||  (keycode >= 48 && keycode <= 57)))) {
    		        event.preventDefault();
    		        return false;
    		    } 
    		   
    		    
    		    if (minimumBalance1_.value.length >14 && event.keyCode != 8){
    		    	 event.preventDefault();
    		    	return false;
    		    }
    		
    		
    		 
    		}
       
       function validationAccount1(event){
    	   var minimumBalance1_ = document.getElementById(event.target.id);
    	   var character = String.fromCharCode(event.keyCode)
    	    var newValue = minimumBalance1_.value + character;
    	    if (isNaN(newValue) || hasDecimalPlace(newValue, 3)) {
    	        event.preventDefault();
    	        return false;
    	    }
   		

   			 if (minimumBalance1_.value.length>10 && event.keyCode != 8){
   		    	 event.preventDefault();
   		    	return false;
   		    }
   		
   		
   		 
   		}
    		

    	function validationAadhar(event){
    		
    		var minimumBalance1_ = document.getElementById(event.target.id);

    			 var keycode = event.which;
    		    if (!(event.shiftKey == false && (keycode == 8 || keycode == 37 ||  (keycode >= 48 && keycode <= 57)))) {
    		        event.preventDefault();
    		        return false;
    		    } 
    		   
    		    
    		    if (minimumBalance1_.value.length>11 && event.keyCode != 8){
    		    	 event.preventDefault();
    		    	return false;
    		    }
    		
    		
    		 
    		}

    		
    		
    		
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
    		 
    		 function validAlphabetsAndNumbers(event){
    				
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
       
       
       
       
       
       
       
       
       
       
       
       
       
	</script>
	<style>
.flexi_table {
	margin-top: 30px;
}
</style>