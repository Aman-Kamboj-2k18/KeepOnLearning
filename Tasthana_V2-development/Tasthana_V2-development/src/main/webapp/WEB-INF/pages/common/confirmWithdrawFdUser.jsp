<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<div class="right-container" id="right-container">
	<div class="container-fluid">

		<script>

$(document).ready(function() {
	    
	   var modeOfPayment = document.getElementById('modeOfPayment').value;
	 
	   document.getElementById('netbankingDiv').style.display = 'none';	
	   document.getElementById('chequeDdDiv').style.display = 'none';
	   
		 if(modeOfPayment=='Cheque'){
				document.getElementById('chequeDDLabel').innerHTML='Cheque Number';
				document.getElementById('chequeDdDiv').style.display = 'block';
				document.getElementById('netbankingDiv').style.display = 'none';
				}
			else if(modeOfPayment=='DD'){
				document.getElementById('chequeDDLabel').innerHTML='DD Number';
				document.getElementById('chequeDdDiv').style.display = 'block';
				document.getElementById('netbankingDiv').style.display = 'none';
			
			}
			
			else if(modeOfPayment=='Fund Transfer'){
				document.getElementById('netbankingDiv').style.display = 'none';
				document.getElementById('chequeDdDiv').style.display = 'none';
				
				
			}
		
		 var bankType= document.getElementById('bankType').value;
		
		 onchangeBankType(bankType);
	

	
	distributeAmount();
		
});
		
     function submitForm(){
    	 
    	 var accNo = '${accNo}';
    	 $("#confirmForm").attr("action", "saveWithdrAmmount?accountNum="+accNo);
 		$("#confirmForm").submit(); 
		
     }
     
	function distributeAmount(){
		var fdAmount= parseFloat(document.getElementById('withdrawAmount').value);
		
		var size= <c:out value="${depositHolderList.size()}"/>;
		
		for(var i=0;i<size;i++){
			var withdrawId= "withdrawAmount["+i+"]";
			var contributionId="contribution["+i+"]";
			var contribution =parseFloat(document.getElementById(contributionId).value)/100;
			var result=roundToTwo(parseFloat(fdAmount)* contribution)
			document.getElementById(withdrawId).value =result;
		}
		
	}

function roundToTwo(num) {    
    return +(Math.round(num + "e+2")  + "e-2");
}
	
function onchangeBankType(value) {

	if (value == 'SameBank') {
       document.getElementById('bankNameTr').style.display = 'none';
		document.getElementById('ifscId').style.display = 'none';
	}

	else if (value == 'DifferentBank') {
		document.getElementById('bankNameTr').style.display = 'block';
		document.getElementById('ifscId').style.display = 'block';

	}


}




		</script>

		<div class="Flexi_deposit">

			<div class="header_customer">
				<h3>
					<spring:message code="label.withdraw" />
					Confirmation
				</h3>
			</div>
			<div class="Success_msg">
				<div class="successMsg"
					style="text-align: center; color: green; font-size: 18px;">
					<h2>${success}</h2>
				</div>
			</div>
			<div class="flexi_table">
				<form:form action="saveWithdrAmmount" method="post"
					name="fixedDeposit" commandName="withdrawForm"
					onsubmit="return val();" id="confirmForm">
					<form:hidden path="depositId" />
					<form:hidden path="depositHolderId" />
					<form:hidden path="compoundFixedAmt" />
					<form:hidden path="customerName" />
					<form:hidden path="email" />
					<form:hidden id="paymentMadeByHolderIds" path="paymentMadeByHolderIds" />
					<div style="margin: 0px auto;" width="600">

						<div class="form-group">
							<label class="col-md-4 control-label" style="margin-top: 15px; text-align: right;">Mode of Payment</label>
							<div class="col-md-6">
								<form:input path="modeOfPayment" id="modeOfPayment"
									class="myform-control" readonly="true" />
							</div>

						</div>
						<c:if test="${modeOfPayment=='Fund Transfer'}">		
					 <div class="form-group">
					 
						<c:if test="${withdrawForm.accountNo!=''}"> 
						
								<div class="form-group">
									<label class="control-label col-sm-4" for="" style="margin-top: 15px; text-align: right;"><spring:message
											code="label.accountNo" /></label>
									<div class="col-sm-6">
										<form:input path="accountNo"  value="${withdrawForm.linkedAccountNo}"
										 class="myform-control"
											id="accountNo" readonly="true" />
									</div>
								</div>
								
								<div class="form-group">
									<label class="control-label col-sm-4" for="" style="margin-top: 15px; text-align: right;"><spring:message
											code="label.accountType" /></label>
									<div class="col-sm-6">
										<form:input path="accountType" class="myform-control"
											id="accountType" readonly="true" />
									</div>
								</div>
								
							</c:if>
							</div> 
</c:if>
						<div class="form-group">
							<label class="col-md-4 control-label" style="margin-top: 15px; text-align: right;"><spring:message
									code="label.fdAccountNum" /><span style="color: red">*</span></label>
							<div class="col-md-6">
								<form:input path="accountNumber" class="myform-control" readonly="true" />
							</div>

						</div>

						<div>

							<div class="col-md-4 control-label" style="margin-top: 15px; text-align: right;">
								<label><spring:message code="label.customerId" /></label>
							</div>
							<div class="col-md-6">
								<form:input path="customerId" class="myform-control"
									id="customerID" readonly="true" />
							</div>
						</div>



						<div>

							<div class="col-md-4 control-label" style="margin-top: 15px; text-align: right;">
								<label><spring:message code="label.withDrawBal" /></label>
							</div>

							<fmt:formatNumber value="${withdrawForm.compoundVariableAmt}"
								pattern="#.##" var="compoundVariableAmt" />
							<div class="col-md-6">
								<form:input path="compoundVariableAmt" class="myform-control"
									id="compoundVariableAmt" value="${compoundVariableAmt}"
									readonly="true" />
							</div>
						</div>

						<div>

							<div class="col-md-4 control-label" style="margin-top: 15px; text-align: right;">
								<label><spring:message code="label.withDrawAmt" /></label>
							</div>
							<div class="col-md-6">
								<form:input path="withdrawAmount" class="myform-control"
									id="withdrawAmount" type="number"
									onkeyup="onChangeAmount(this.value)" readonly="true" />
							</div>
						</div>


					   <div id="chequeDdDiv">

							<div>
								<div class="col-md-4 control-label" style="margin-top: 15px; text-align: right;">
									<label id="chequeDDLabel"></label>
								</div>
								<div class="col-md-6">
									<form:input path="chequeDDNumber" class="myform-control"
										id="chequeDDNumber" type="number" readonly="true" />
								</div>
							</div>

							<div>
								<div class="col-md-4 control-label" style="margin-top: 15px; text-align: right;">
									<label><spring:message code="label.date" /></label>
								</div>
								<div class="col-md-6">
									<form:input path="chequeDDdate" class="myform-control"
										value="${todaysDate}" readonly="true" />
								</div>
							</div>

							<div>
								<div class="col-md-4 control-label" style="margin-top: 15px; text-align: right;">
									<label><spring:message code="label.bank" /></label>
								</div>
								<div class="col-md-6">
									<form:input path="chequeDDBank" class="myform-control" id="Bank"
										readonly="true" />
								</div>
							</div>

							<div>
								<div class="col-md-4 control-label" style="margin-top: 15px; text-align: right;">
									<label><spring:message code="label.branch" /></label>
								</div>
								<div class="col-md-6">
									<form:input path="branch" class="myform-control"
										readonly="true" id="branch" />
								</div>
							</div>


						</div>

						<!-- ..................NET BANKING PAYMENT.................... -->
						<div id="netbankingDiv">
							<div>
								<div class="col-md-4 control-label">
									<label>Bank type</label>
								</div>
								<div class="col-md-6">
									<form:input path="otherBank1" id="bankType"
										class="myform-control" readonly="true" />

								</div>
							</div>
                          <c:if test="${withdrawForm.otherBank1!='SameBank'}">
							<div>
								<div class="col-md-4 control-label">
									<label>Transfer</label>
								</div>
								<div class="col-md-6">
									<form:input path="otherPayTransfer1" class="myform-control"
										readonly="true" />
								</div>
							</div>
                           </c:if>
							<div id="benificiaryName">
								<div class="col-md-4 control-label">
									<label for="">Beneficiary Name </label>
								</div>
								<div class="col-md-6">
									<form:input path="otherName1" class="myform-control"
										id="beneficiaryName" readonly="true" />
								</div>

							</div>


							<div id="accNumber">
								<div class="col-md-4 control-label">
									<label for="">Beneficiary Account Number</label>
								</div>
								<div class="col-md-6">
									<form:input path="otherAccount1" class="myform-control"
										id="beneficiaryAccount" readonly="true" />
								</div>

							</div>

							<div id="bankNameTr">
								<div class="col-md-4 control-label">
									<label for="">Bank Name</label>
								</div>


								<div class="col-md-6">
									<form:input path="Bank" class="myform-control"
										id="beneficiaryBank" readonly="true" />
								</div>

							</div>

							<div id="ifscId">
								<div class="col-md-4 control-label">
									<label for="">IFSC</label>
								</div>

								<div class="col-md-6">
									<form:input path="otherIFSC1" class="myform-control"
										id="beneficiaryIfsc" readonly="true" />
								</div>
							</div>


						</div>
					</div>


					<div class="col-md-12 header_customer"
						style="padding-top: 1px; padding-bottom: 1px; margin-top: 43px;">
						<h5 align="center">
							<b>Holder Contribution</b>
						</h5>
					</div>
					<table class="table table-striped table-bordered">
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



					<div class="col-sm-12 col-md-12 col-lg-12">

						<table align="center" class="f_deposit_btn">
							<tr>
								<td><a href="searchDepositForWithdraw?menuId=${menuId}" class="btn btn-danger">Back</a>
								 <input type="submit" class="btn btn-info"
									onclick="submitForm()"
									value="<spring:message code="label.save"/>"></td>
								
							</tr>

						</table>
					</div>


				</form:form>
			</div>

		</div>
		<style>
.myform-control {
	background: whitesmoke;
	color: #000;
}

.flexi_table {
	margin-left: 23px;
}
</style>