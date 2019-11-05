<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script>

$(document).ready(function() {
debugger
var paymentMod = document.getElementById('hdnPayment').value;
	
	if (paymentMod == 'DD') {
		
		document.getElementById('pageTitle').innerHTML='DD Payment Confirmation';
		document.getElementById('chequeDdDiv').style.display = 'block';
		document.getElementById('chequeDDDateLabel').innerHTML='DD Date';
		document.getElementById('chequeDDNoLabel').innerHTML='DD Number';
		document.getElementById('netBankingDetailsTr').style.display = 'none';
		document.getElementById('cardDetailsTr').style.display = 'none';
	
	}
	else if (paymentMod == 'Cheque') {
		document.getElementById('pageTitle').innerHTML='Cheque Payment Confirmation';
		document.getElementById('chequeDdDiv').style.display = 'block';
		document.getElementById('chequeDDDateLabel').innerHTML='Cheque Date';
		document.getElementById('chequeDDNoLabel').innerHTML='Cheque Number';
		document.getElementById('netBankingDetailsTr').style.display = 'none';
		document.getElementById('cardDetailsTr').style.display = 'none';
		
	}
	else if (paymentMod == 'Cash') {
		document.getElementById('pageTitle').innerHTML='Cash Payment Confirmation';
		document.getElementById('chequeDdDiv').style.display = 'none';
		document.getElementById('netBankingDetailsTr').style.display = 'none';
		document.getElementById('cardDetailsTr').style.display = 'none';
	
	}
	
	else if (paymentMod == 'Card Payment' || paymentMod == 'Credit Card' || paymentMod == 'Debit Card') {
		document.getElementById('pageTitle').innerHTML='Card Payment Confirmation';
		document.getElementById('cardDetailsTr').style.display = 'block';
		document.getElementById('netBankingDetailsTr').style.display = 'none';
		document.getElementById('chequeDdDiv').style.display = 'none';
	}

	else if (paymentMod == 'Net Banking') {
		document.getElementById('pageTitle').innerHTML='Net Banking Confirmation';
		
		document.getElementById('netBankingDetailsTr').style.display = 'block';
		document.getElementById('cardDetailsTr').style.display = 'none';
		document.getElementById('chequeDdDiv').style.display = 'none';
		
		var bankType= document.getElementById('bankType').value;
		
		if (bankType == 'SameBank') {

			document.getElementById('bankNameTr').style.display = 'none';
			document.getElementById('ifscId').style.display = 'none';
		}

		else if (bankType == 'DifferentBank') {
			
			document.getElementById('bankNameTr').style.display = 'block';
			document.getElementById('ifscId').style.display = 'block';

		}
} 


	distributeAmount();
});


function distributeAmount(){
	var fdAmount= parseFloat(document.getElementById('fdAmount').value);
	
	var size= <c:out value="${depositHolderList.size()}"/>
	
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
function goBack(){
	
	 $("#fdForm").submit();
}
</script>
<div class="right-container" id="right-container">
	<div class="container-fluid">


		<div class="Flexi_deposit">

			<div class="header_customer">
				<h3 id="pageTitle">
					
				</h3>
			</div>
			<div class="Success_msg">
				<div class="successMsg"
					style="text-align: center; color: green; font-size: 18px;">
					<h2>${success}</h2>
				</div>
			</div>
			<div class="flexi_table">
			
			
				<form:form action="savePayment" method="post" name="fixedDeposit"
					class="form-horizontal" commandName="fixedDepositForm"
					onsubmit="return val();">
					<form:hidden id="paymode" path="paymentMode" />
					<input type="hidden" id="hdnPayment" value="${paymentMode }" />
					<form:hidden path="contactNum" value="" />
					<form:hidden path="category" value="" />
					<%-- <form:hidden path="fdId" value="" /> --%>
					<form:hidden path="fdTenureDate" value="" />
					<form:hidden path="depositHolderId" />
					<form:hidden id="paymentMadeByHolderIds" path="paymentMadeByHolderIds" />
					<form:hidden path="depositId" />
                     <form:hidden path="depositForm.topUp" id="topUp" />
                     <form:hidden path="email" />
					<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.customerId" /></label>
						<div class="col-md-4">
							<form:input path="customerId" class="form-control"
								id="customerID" readonly="true" style="background: whitesmoke;" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.customerName" /></label>
						<div class="col-md-4">
							<form:input path="customerName" class="form-control"
								id="customerName" readonly="true"
								style="background: whitesmoke;" />
						</div>
					</div>
			<div class="form-group">
				<label class="col-md-4 control-label">Account Number</label>
				<div class="col-md-4">
					<form:input path="accountNo" class="form-control" style="background: whitesmoke;"  readonly="true"/>
				</div>
			</div>
			
					<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.fdAmount" /><span style="color: red">*</span></label>

						<fmt:formatNumber value="${fixedDepositForm.fdAmount}"
							pattern="#.##" var="fdAmount" />
						<div class="col-md-4">
							<form:input path="fdAmount" class="form-control"
								value="${fdAmount}" style="background: whitesmoke; cursor:text;"
								id="fdAmount" type="number" readonly="true" />
							
						</div>
					</div>
					
					
					<!-- .....................CHEQUE/DD PAYMENT......................... -->
					<div id="chequeDdDiv" style="display:none">

						<div class="form-group">
							<label class="col-md-4 control-label"><span
								id="chequeDDNoLabel" ><span
									style="color: red">*</span></span></label>
							<div class="col-md-4">
								<form:input path="chequeNo" class="form-control"
									style="background: whitesmoke; cursor: text;" type="number"
									id="chequeNo" readonly="true" />
								
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-4 control-label"><span
								id="chequeDDDateLabel" ><span
									style="color: red">*</span></span>
							</label>
							<div class="col-md-4">
								<fmt:formatDate value="${date}" var="dateFormat"
									pattern="dd/MM/yyyy" />

								<form:input path="chequeDate" class="form-control"
									style="background: whitesmoke; cursor: pointer;"
									id="chequeDate" value="${todaysDate}" readonly="true" />
								
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.bank" /><span style="color: red">*</span></label>
							<div class="col-md-4">
								<form:input path="chequeBank" class="form-control"
									style="background: whitesmoke; cursor: text;" id="chequeBank"
									pattern="[a-zA-Z ]+" readonly="true" />
								
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.branch" /><span style="color: red">*</span></label>
							<div class="col-md-4">
								<form:input path="chequeBranch" class="form-control"
									style="background: whitesmoke; cursor: text;" id="chequeBranch"
									pattern="[a-zA-Z ]+" readonly="true" />
							</div>
						</div>

				</div>
					
					
					
					
				<!-- ..................NET BANKING PAYMENT.................... -->

					<div id="netBankingDetailsTr" style="display:none">

						<div class="form-group">
							<label class="col-md-4 control-label">Bank type<span
								style="color: red">*</span></label>
							<div class="col-md-6">
								<form:input id="bankType" path="fdPayType" class="form-control" readonly="true"/>
									
							</div>
						</div>
                     <c:if test="${fixedDepositForm.fdPayType!='SameBank'}">
						<div class="form-group">
							<label class="col-md-4 control-label">Transfer type<span
								style="color: red">*</span></label>
							<div class="col-md-6">
								<form:input path="otherPayTransfer1" id="transferType" class="form-control" readonly="true"/>
									
							</div>
						</div>
                     </c:if>
						<div class="form-group" id="benificiaryName">
							<label class="control-label col-sm-4" for="">Beneficiary
								Name
							</label>

							<div class="col-sm-6">
								<form:input path="otherName1" class="form-control"
									style="background: whitesmoke; text: pointer;"
									id="beneficiaryName" readonly="true" />
							</div>

						</div>


						<div class="form-group" id="accNumber">
							<label class="control-label col-sm-4" for="">Beneficiary Account
								Number
							</label>

							<div class="col-sm-6">
								<form:input path="otherAccount1"
									class="form-control"
									style="background: whitesmoke; text: pointer;"
									id="beneficiaryAccount" readonly="true" />
							</div>

						</div>

						<div class="form-group" id="bankNameTr">
							<label class="control-label col-sm-4" for="">Bank Name</label>

							<div class="col-sm-6">
								<form:input path="bank" class="form-control"
									style="background: whitesmoke; text: pointer;"
									id="beneficiaryBank" readonly="true" />
							</div>

						</div>

						<div class="form-group" id="ifscId">
							<label class="control-label col-sm-4" for="">IFSC </label>

							<div class="col-sm-6">
								<form:input path="otherIFSC1" class="form-control"
									style="background: whitesmoke; text: pointer;"
									id="beneficiaryIfsc" readonly="true" />
							</div>

						</div>


					</div>


					<!-- .....................CARD PAYMENT........................... -->
					<div id="cardDetailsTr" style="display:none">
						<div class="form-group">
							<label class="col-md-4 control-label">Card Type</label>
							<div class="col-md-4">
								<form:input path="cardType"
									id="cardType" class="form-control" readonly="true"/>
									
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">Card No</label>
							<div class="col-md-4">
								<form:input path="cardNo" class="form-control d" filtertype="CCNo" readonly="true" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">Expiry Date</label>
							<div class="col-md-2">
							<form:input class="form-control" path="expiryDate" id="expiryDate" readonly="true"/>
							
							</div>
							
						<label class="col-md-2" style="margin-left:28px;">CVV</label>
							
							<div class="col-md-2" style="margin-left:-145px;">
								<form:input type="password" path="cvv"								
									class="form-control" style="width:50%" readonly="true" />
							</div>
						</div>
						

					</div>




              <table class="table table-bordered" style="margin-top: 49px;">
									<tr>
										<td><b>Serial No.</b></td>
										<td><b><spring:message code="label.customerName" /></b></td>
										
										<td><b>Contribution(%)</b></td>
										<td><b>Distributed amount</b></td>
										
									</tr>

									<c:forEach items="${depositHolderList}" var="depositHolder" varStatus="status">
										
										<tr>
										<td><b>${status.index+1} </b></td>
											<td><b> <c:out value="${depositHolder.customerName}"></c:out></b></td>
											<td><b> <input style="border: none" value="${depositHolder.contribution}" id="contribution[${status.index}]" readonly /></b></td>
	                                      <td><b> <input style="border: none" id="withdrawAmount[${status.index}]" readonly/></b></td>
	                                     
	                                      </tr>
									</c:forEach>
								</table>

			
						
						
					<div class="form-group">

						<div class="col-md-offset-4 col-md-8">
							    <input type="button" class="btn btn-success"
								onclick="goBack();" value="Back">
								
								<input type="submit" class="btn btn-info"
								onclick="showDialog(); return false;"
								value="<spring:message code="label.save"/>"> 
						</div>
					</div>

				</form:form>
				<form:form action="fdPaymentDetails" method="post" class="form-horizontal"
					commandName="fixedDepositForm" id="fdForm">
				<form:hidden path="accountNo" value="${fixedDepositForm.accountNo}"/>
				<form:hidden path="customerId" value="${fixedDepositForm.customerId}"/>
				<form:hidden path="customerName" value="${fixedDepositForm.customerName}"/>
				<form:hidden path="depositId" value="${fixedDepositForm.depositId}"/>
				<form:hidden path="productConfigurationId" value="${fixedDepositForm.productConfigurationId}"/>
				
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