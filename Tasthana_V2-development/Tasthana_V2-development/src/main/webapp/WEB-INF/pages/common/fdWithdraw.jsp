<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<div class="right-container" id="right-container">
	<div class="container-fluid">

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
		
		
		
		
		
		
		
		
$(document).ready(function() {

	var modeOfPayment = document.getElementById('modeOfPayment').value;
	 if(modeOfPayment=='Cheque'){
		document.getElementById('chequeDDLabel').innerHTML='Cheque Number';
		document.getElementById('chequeDdDiv').style.display = 'block';
	
	}
	else if(modeOfPayment=='DD'){
		document.getElementById('chequeDDLabel').innerHTML='DD Number';
		document.getElementById('chequeDdDiv').style.display = 'block';
	
		
	}
	
	else if(modeOfPayment=='Fund Transfer'){
		document.getElementById('chequeDdDiv').style.display = 'none';
		
	}
	 
	 var bankType ='${withdrawForm.otherBank1}';
	
	 onchangeBankType(bankType);
	 
	 var withdrawAmount = document.getElementById('withdrawAmount').value;
		onChangeAmount(withdrawAmount);
		
		
		
});

function onchangeBankType(value) {

	
	if (value == 'SameBank') {

		document.getElementById('benificiaryName').style.display = 'block';
		document.getElementById('accNumber').style.display = 'block';

		document.getElementById('bankNameTr').style.display = 'none';
		document.getElementById('ifscId').style.display = 'none';
		
		document.getElementById('beneficiaryBank').value="";
		document.getElementById('beneficiaryIfsc').value="";
 		document.getElementById('transferOption').style.display = 'none';
   }

	else if (value == 'DifferentBank') {
		document.getElementById('benificiaryName').style.display = 'block';
		document.getElementById('accNumber').style.display = 'block';
		document.getElementById('bankNameTr').style.display = 'block';
		document.getElementById('ifscId').style.display = 'block';
 		document.getElementById('transferOption').style.display = 'block';
 
	}


}

		
	function val() {
		
		 inp = document.querySelectorAll(".input");
		
		for(i=0;i<inp.length;i++){
			inp[i].style.borderColor = "#cccccc";
		}
		
		
		document.getElementById('validationError').innerHTML='';
		var withdrawAmount = parseFloat(document.getElementById('withdrawAmount').value);
		
		if (withdrawAmount == '' || isNaN(withdrawAmount)) {
			document.getElementById('withdrawAmount').style.borderColor = "red";
			document.getElementById('validationError').innerHTML='Please enter amount';
			return false;
		} 
		
		var compountVarAmt = parseFloat(document
				.getElementById('compoundVariableAmt').value);
		if (compountVarAmt <= withdrawAmount || withdrawAmount <= 0) {
			
			document.getElementById('validationError').innerHTML='Insufficient balance';
			document.getElementById('withdrawAmount').style.borderColor = "red";
			return false;

				}
		var modeOfPayment = document.getElementById('modeOfPayment').value;
		
		if(modeOfPayment=='Fund Transfer'){
			
			if (withdrawAmount == '' || isNaN(withdrawAmount)) {
				document.getElementById('withdrawAmount').style.borderColor = "red";
				document.getElementById('validationError').innerHTML='Please enter amount';
				return false;
				
				
			}
	
	     
		
	
		}
		
		else{
			var temp='DD';
			 if(modeOfPayment=='Cheque'){
				 temp='cheque';
				}
				
			 
		var chequeNo = document.getElementById('chequeDDNumber').value;
		if (chequeNo == '') {
			document.getElementById('chequeDDNumber').style.borderColor = "red";
			document.getElementById('validationError').innerHTML='Please enter '+temp+' number';
			return false;
		} 
		var chequeDate = document.getElementById('chequeDDdate').value;
		if (chequeDate == '') {
			document.getElementById('chequeDDdate').style.borderColor = "red";
			document.getElementById('validationError').innerHTML='Please enter '+temp+' date';
			return false;
		}

		var chequeBank = document.getElementById('chequeDDBank').value;
		if (chequeBank == '') {
			
			document.getElementById('chequeDDBank').style.borderColor = "red";
			document.getElementById('validationError').innerHTML='Please enter bank name';
			
			return false;
		} 
		var chequeBranch = document.getElementById('branch').value;
		if (chequeBranch == '') {
			document.getElementById('branch').style.borderColor = "red";
			document.getElementById('validationError').innerHTML='Please enter branch';
			
			return false;
		} 
		}

		return getAmountToLose();
		
	
	}

	function getAmountToLose(){
		var depositId=document.getElementById('depositId').value;
		var withdrawAmt= document.getElementById('withdrawAmount').value;
		   var amount = 0;
		   var confirmation = true;
		  $.ajax({  
			 async: false,
		    type: "GET",  
		    url: "<%=request.getContextPath()%>/common/getLooseAmountForWithdraw", 
		    contentType: "application/json",
		    dataType: "json",
		    data: "depositId=" + depositId + "&withdrawAmt=" + withdrawAmt,
		    
		    success: function(response){  	  
 		    	amount = response;
 		    	var message = 0;
 		    	if(amount != 0)
 		    	{
	 		    	if(amount < 0)
	 		    		{
		 		    		amount = amount * -1;
		   					message = 'System will take out Rs.' + amount + ' from your account. Do you wish to continue?'
	 		    		}
	   				else
	   					{
	   						message = 'System will add Rs.' + amount + ' in your account. Do you wish to continue?'
	   					}		
   						confirmation = window.confirm(message);
 		    	}
 		    	
		    },  
		    error: function(e){  
		    	 $('#error').html("Error occured!!")
		    	 alert("error");
		    	 confirmation = false;
		    	//return false;
		    }  
		  });  
		return confirmation;
		}  
	

	var clickOffConfirmed = false;
	function onclickConfirm(){
		 // escape here if the confirm is false;
    	//if (clickOffConfirmed) return false;
		
    	clickOffConfirmed = true;
		var modeOfPayment = document.getElementById('modeOfPayment').value;
		 if(modeOfPayment=='Fund Transfer'){
		var accArray= $('#accountNo').val().split(',');
		document.getElementById('linkedAccountNo').value=accArray[0];
		$("#withdrawForm").attr("action", "confirmWithdrawFdUser?accountNum="+accArray[0]);
		$("#withdrawForm").submit(); 
		}
		else{
			
			$("#withdrawForm").attr("action", "confirmWithdrawFdUser?accountNum="+null);
			$("#withdrawFsorm").submit(); 
		}
		 return true;
	}
	
	
	function popConfirmationBox(amount){
		
	    var confirmation = true;
	  //  var amount = this.amountToLose;
	  
				var message = 'System will take out Rs.' + amount + ' from your account. Do you wish to continue?'
				confirmation = window.confirm(message);
			    return confirmation;

	   
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
			
			
			
		</script>

		<div class="Flexi_deposit">

			<div class="header_customer">
				<h3>
					<spring:message code="label.withdraw" /> Deposit
				</h3>
			</div>
			<div class="erroeMsg"
				style="text-align: center; color: green; font-size: 18px;">
				<h2>${error}</h2>
			</div>
			<div class="Success_msg">
				<div class="successMsg"
					style="text-align: center; color: green; font-size: 18px;">
					<h2>${success}</h2>
				</div>
			</div>
			<div class="flexi_table">
				<form:form  method="post" id="withdrawForm"
					name="fixedDeposit" commandName="withdrawForm" onsubmit="return val();">
					<form:hidden path="depositId" id="depositId" />
					<form:hidden path="depositHolderId" />
					<form:hidden path="paymentMadeByHolderIds" />
					<form:hidden path="compoundFixedAmt" />
					<form:hidden path="customerName" />
					<form:hidden path="email" />
					<form:hidden path="linkedAccountNo" id="linkedAccountNo"/>
								
					
					<div style="margin: 0px auto;" width="80%">
					
						<div class="form-group">
									<label class="col-md-4 control-label" style="margin-top: 15px; text-align: right;">Mode of Payment</label>
									<div class="col-md-6">
										<form:input path="modeOfPayment" id="modeOfPayment" class="myform-control" style="background: whitesmoke;" readonly="true"/>
						</div>
								
						</div>
						
						
						
					<c:if test="${withdrawForm.modeOfPayment=='Fund Transfer'}">	
					<div id="linkedAccountDiv">
								<div class="form-group">
									<label class="col-md-4 control-label" style="text-align: right; margin-top: 20px;">Current/<spring:message
											code="label.savingAccountNo" /> </label>
									<div class="col-md-6" style="padding-top: 0;">
										<form:select path="accountNo" class="myform-control"
											id="accountNo" onchange="onChangeAccountNo(this.value)">
											
											<form:option value="">Select</form:option>
											<c:forEach items="${withdrawForm.accountList}"
												var="account">
												<fmt:formatNumber value="${account.accountBalance}"
													pattern="#.##" var="accBal1" />
												<option
													value="${account.accountNo},${account.accountType},${accBal1}"
													<c:if test="${account.accountType =='Savings'}" >selected="true"</c:if>>${account.accountNo}
												</option>
											</c:forEach>
										</form:select>

									</div>

								</div>
								<script>
				function onChangeAccountNo(account){
					
					if(account!=""){
					var account = account.split(",");
					document.getElementById('accountType').value=account[1];
 					/* document.getElementById('accountBalance').value = parseFloat(account[2]).toLocaleString("en-US");
 					document.getElementById('accountBalance1').value = account[2];
 					 */
					
					/* document.getElementById('accountTypeDiv').style.display='block';
					document.getElementById('accountBalanceDiv').style.display='block'; */
					/* document.getElementById('fundTransferLinkedAccount').style.display='block'; */
					var accArray= $('#accountNo').val().split(',');
					document.getElementById('linkedAccountNo').value=accArray[0];
					
					/* if(document.getElementById('modeOfPayment').value=='Fund Transfer'){
						var accArray= $('#accountNo').val().split(',');
						document.getElementById('linkedAccount').value=accArray[0];
						
						document.getElementById('linkedAccountBalance1').value = $('#accountBalance1').val();
						document.getElementById('linkedAccountBalance').value = parseFloat($('#accountBalance1').val()).toLocaleString("en-US");
						
					}
					 */
					
					
					}
				/* 	else{
						document.getElementById('accountTypeDiv').style.display='none';
						document.getElementById('accountBalanceDiv').style.display='none';
						
						if(document.getElementById('paymentMode').value=='Fund Transfer'){
							document.getElementById('paymentMode').value="Select";
						}
						document.getElementById('linkedAccountBalanceTr').style.display = 'none';
						document.getElementById('linkedAccountTr').style.display = 'none';
						
						document.getElementById('fundTransferLinkedAccount').style.display='none';
						
					} */
				}
				
				
			</script>


								<div class="form-group" id="accountTypeDiv">
									<label class="col-md-4 control-label" style="text-align: right; margin-top: 20px;"><spring:message
											code="label.accountType" /></label>
									<div class="col-md-6" style="margin-top: 0;">

										<form:input path="accountType" class="myform-control" style="background: whitesmoke;"
											value="Savings" id="accountType" readonly="true" />

									</div>

								</div>


							</div>	
						
						</c:if>
			  		<div class="form-group">
									<label class="col-md-4 control-label" style="margin-top: 15px; text-align: right;"><spring:message code="label.fdAccountNum" /></label>
									<div class="col-md-6">
										<form:input path="accountNumber" class="myform-control" style="background: whitesmoke;" readonly="true"/>
						</div>
								
						</div>
							
							
						<div class="form-group">
						<div class="col-md-4 control-label" style="margin-top: 15px; text-align: right;"><label ><spring:message code="label.customerId" /></label></div>
							<div class="col-md-6"><form:input path="customerId" class="myform-control"
									style="background: whitesmoke;" id="customerID" readonly="true" /></div>
						</div>

						<div class="form-group">

							<div><label class="col-md-4 control-label" style="margin-top: 15px; text-align: right;"><spring:message code="label.withDrawBal" /></label></div>

							<fmt:formatNumber value="${withdrawForm.compoundVariableAmt}"
								pattern="#.##" var="compoundVariableAmt" />
							<div class="col-md-6"><form:input path="compoundVariableAmt"
									class="myform-control" style="background: whitesmoke;"
									id="compoundVariableAmt" value="${compoundVariableAmt}"
									readonly="true" /></div>
						</div>

						<div class="form-group">

							<div><label class="col-md-4 control-label" style="margin-top: 15px; text-align: right;"><spring:message code="label.withDrawAmt" /><span
									style="color: red">*</span></label></div>
							<div class="col-md-6"><form:input path="withdrawAmount" class="myform-control input"
									 id="withdrawAmount"
									type="number" step="0.1" onkeyup="onChangeAmount(this.value)" onkeypress="validationAccount(event)" /></div>
						</div>
	           
	         <c:if test="${withdrawForm.modeOfPayment=='DD' || withdrawForm.modeOfPayment == 'Cheque' }">
	            <div id="chequeDdDiv">
                          
							<div class="form-group">
								<div class="col-md-4 control-label" style="margin-top: 15px; text-align: right;"><label id="chequeDDLabel"></label><span
										style="color: red">*</span></div>
								<div  class="col-md-6"><form:input path="chequeDDNumber"
										class="myform-control input" style="background: #fff; cursor: text;"
										id="chequeDDNumber" type="number" onkeypress="validationAccount(event)" /></div>
							</div>
							
							<div class="form-group">
								<div class="col-md-4 control-label" style="margin-top: 15px; text-align: right;"><label><spring:message code="label.date" /><span
										style="color: red">*</span></label></div>
								<div  class="col-md-6"><form:input path="chequeDDdate"
										class="myform-control datepicker-here input" value="${todaysDate}"
										style="background: #fff; cursor: pointer;" id="chequeDDdate"
										readonly="true" /></div>
							</div>
						
							<div class="form-group"> 
								<div class="col-md-4 control-label" style="margin-top: 15px; text-align: right;"><label><spring:message code="label.bank" /><span
										style="color: red">*</span></label></div>
								<div  class="col-md-6"><form:input path="chequeDDBank" class="myform-control input"
										style="background: #fff; cursor: text;" id="chequeDDBank" onkeypress="validName(event)"
										 /></div> <!--pattern="[a-zA-Z ]+"  -->
							</div>
							<
							
							<div class="form-group">
								<div class="col-md-4 control-label" style="margin-top: 25px; text-align: right;"><label><spring:message code="label.branch" /><span
										style="color: red">*</span></label></div>
								<div  class="col-md-6"><form:input path="branch" class="myform-control input"
										style="background: #fff; cursor: text;" id="branch" onkeypress="validName(event)"
										/></div>   <!--pattern="[a-zA-Z ]+"   -->
							</div>

				</div>

				</c:if>
					</div> 
					
           <span id="validationError" class="col-md-12" style="color:red;margin-left: 318px;margin-top: 36px;"></span>
                  
                    <div class="col-md-12 header_customer"
						style="padding-top: 1px; padding-bottom: 1px; margin-top: 43px; width: 97%"">
						<h5 align="center">
							<b>Holder Contribution</b>
						</h5>
					</div>

					<table class="table table-striped table-bordered"
						style="margin-top: 49px; width: 97%">
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
								<td><b> <input style="border: none" value="${depositHolder.contribution}"
										id="contribution[${status.index}]" readonly /></b></td>
								<td><b> <input style="border: none"
										id="withdrawAmount[${status.index}]" readonly /></b></td>

							</tr>
						</c:forEach>
					</table>
					<div class="col-sm-12 col-md-12 col-lg-12">

						<table align="center" class="f_deposit_btn">
							<tr>
								
								<td><a href="searchDepositForWithdraw?menuId=${menuId}" class="btn btn-danger"><spring:message
											code="label.back" /></a> <input type="submit" class="btn btn-info" onclick ="onclickConfirm()" value="<spring:message code="label.confirm"/>"></td>
							</tr>

						</table>
					</div>

				</form:form>
			</div>

		</div>
		<style>
.myform-control {
	background: #fff;
	color: #000;
}
.flexi_table{
margin-left: 23px;
}
</style>