<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<script>

function validationAccount(event) {

	var minimumBalance1_ = document.getElementById(event.target.id);

	var keycode = event.which;
	if (!(event.shiftKey == false && (keycode == 8 || keycode == 37 || (keycode >= 48 && keycode <= 57)))) {
		event.preventDefault();
		return false;
	}

	if (minimumBalance1_.value.length > 10 && event.keyCode != 8) {
		event.preventDefault();
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
		document.getElementById(withdrawId).value =result;
	}
	
}

function roundToTwo(num) {    
    return +(Math.round(num + "e+2")  + "e-2");
}

	function val() {
		debugger;
		var compountVarAmt = parseFloat(document
				.getElementById('compoundVariableAmt').value);
		var withDrawAmt = parseFloat(document.getElementById('withdrawAmount').value);
		
		
		
		
		
		var submit = true;

		if (compountVarAmt <= withDrawAmt || withDrawAmt <= 0) {
			submit = false;
			document.getElementById('withdrawAmountError').style.display = 'block';
			return false;
		} 
		if (document.getElementById('withdrawAmount').value == '') {

			document.getElementById('withdrawAmount').style.borderColor = "red";
			submit = false;
		} else {
			document.getElementById('withdrawAmount').style.borderColor = "black";

		}
		
		submit = getAmountToLose();
		return submit;

	}
	
	function getAmountToLose(){
		
		var depositId=document.getElementById('depositId').value;
		var withdrawAmt= document.getElementById('withdrawAmount').value;
		var compoundVariableAmt= document.getElementById('compoundVariableAmt').value;
		/* if(withdrawAmt>compoundVariableAmt){
			alert("You can not withdraw amount more than your balance amount.");
			return false;
		} */
		   var amount = 0;
		   var confirmation = true;
		  $.ajax({  
			 async: false,
		    type: "GET",  
		    url: "<%=request.getContextPath()%>/users/getLooseAmountForWithdraw", 
		    contentType: "application/json",
		    dataType: "json",
		    data: "depositId=" + depositId + "&withdrawAmt=" + withdrawAmt,
		    
		    success: function(response){  	  
 		    	amount = response;
 		    	var message = 0;
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
</script>
<div class="right-container" id="right-container">
	<div class="container-fluid">

		<div class="Flexi_deposit">

			<div class="header_customer">
				<h3>
					<spring:message code="label.withdraw" /> from deposit
				</h3>
			</div>
			<div class="Success_msg">
				<div class="successMsg"
					style="text-align: center; color: green; font-size: 18px;">
					<h2>${success}</h2>
				</div>
			</div>
			<div class="flexi_table">
				<form:form class="form-horizontal" action="saveWithdrawAmmount"
					autocomplete="off" method="post" name="fixedDeposit"
					commandName="withdrawForm" onsubmit="return val();">
					
					<form:hidden path="depositHolderId" />
					<form:hidden path="compoundFixedAmt" />
					
					<form:hidden path="modeOfPayment" />

	             <div class="form-group" id="accNo">
						<label class="control-label col-md-4">Deposit Id</label>
						<div class="col-md-6">
							<form:input path="depositId" class="form-control" readonly="true"/>
							
						</div>
					</div>
					
					     <div class="form-group" id="accNo">
						<label class="control-label col-md-4">Account Number</label>
						<div class="col-md-6">
							<form:input path="accountNumber" class="form-control" readonly="true"/>
							
						</div>
					</div>
					

					<div class="form-group">
						<label class="control-label col-md-4"><spring:message
								code="label.withDrawBal" /></label>
						<div class="col-md-6">
							<fmt:formatNumber value="${withdrawForm.compoundVariableAmt}"
								pattern="#.##" var="compoundVariableAmt" />
							<form:input path="compoundVariableAmt" required="true"
								class="form-control" id="compoundVariableAmt"
								value="${compoundVariableAmt}" readonly="true" />
						</div>
					</div>


					
                  <div class="form-group" id="fundTransferDiv">
						<label class="col-md-4 control-label">Transfer To Account<span
							style="color: red">*</span></label>
						<div class="col-md-6">


							<form:select id="fundTransfer" path="linkedAccountNo"
								class="form-control">

								<c:forEach items="${accList}" var="acc">

									<form:option selected="selected" hidden ="hidden" readonly="true" value="${acc.accountType} - ${acc.accountNo}">
								</form:option>
								</c:forEach>
							</form:select>


							<span id="fundTransferError" style="display: none; color: red;">No linked Account for Fund Transfer</span>
						</div>
					</div> 



					<div class="form-group" id="accNo">
						<label class="control-label col-md-4">Enter Amount<span style="color: red">*</span></label>
						<div class="col-md-6">
							<form:input path="withdrawAmount" type="number" step="0.01"
								class="form-control" onkeypress="validationAccount(event)" id="withdrawAmount" onkeyup="onChangeAmount(this.value)"/>
							<span id="withdrawAmountError" style="display: none">Enter
								correct amount</span>
						</div>
					</div>
					
      
      	<div class="header_customer" style="padding-top: 1px;padding-bottom: 1px;margin-top: 33px;">
				<h5 align="center">
					<b>Holder Contribution</b>
				</h5>
			</div>
			             
       <table  class="table table-bordered" style="margin-top: 49px; margin-left: 25px; width: 95%;">
			<tr>
				<td><b>Serial</b></td>
				<td><b><spring:message code="label.customerName" /></b></td>
				<td><b>Contribution(%)</b></td>
				<td><b>Holder status</b></td>
				<td><b>Distributed amount</b></td>
				
			</tr>

			<c:forEach items="${depositHolderList}" var="depositHolder" varStatus="status">
		
				<tr>
				<td><b>${status.index+1} </b></td>
					<td><b> <c:out value="${depositHolder.customerName}"></c:out></b></td>
					<td><b> <input style="border: none" value="${depositHolder.contribution}" id="contribution[${status.index}]" readonly /></b></td>
                    <td><b> <c:out value="${depositHolder.depositHolderStatus}"></c:out></b></td>
							
                    <td><b> <input style="border: none" id="withdrawAmount[${status.index}]" readonly/></b></td>
                                
                </tr>
			</c:forEach>
		</table>
                   
                   
					<div class="form-group">
						<label class="control-label col-md-4"></label>
						<div class="col-md-6">
							<a href="withdrawPaymentList" class="btn btn-success"><spring:message
									code="label.back" /></a>
									<input type="submit" class="btn btn-info" 
								id="sub"  value="<spring:message code="label.confirm"/>">
							
						</div>
					</div>


				</form:form>
			</div>

		</div>