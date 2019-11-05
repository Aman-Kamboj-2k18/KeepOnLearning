<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!-- To fetch the request url -->
<c:set var="req"
	value="${requestScope['javax.servlet.forward.request_uri']}" />
<c:set var="baseURL" value="${fn:split(req,'/')}" />
<c:if test="${baseURL[1] == 'bnkEmp'}">
<c:set var="back" value="bankEmp"/>
</c:if>
<c:if test="${baseURL[1] == 'users'}">
<c:set var="back" value="users"/>
</c:if>




<script type="text/javascript">


function proceed(){
	
	var modal = document.getElementById('myModal');
	modal.style.display = "none";
}


	$(document)
			.ready(
					function() {
						
						
// 						var newIntRate = '${newIntRate}';
// 						var depositAmt = '${depositAmt}';
// 						var loseAmt = '${loseAmt}';
						
// 						var transferAmt = '${transferAmt}';
// 					   //document.getElementById('newInterestRate').innerHTML=newIntRate;
// 					  // document.getElementById('newDepositAmount').innerHTML=depositAmt;
// 					   document.getElementById('loseAmount').innerHTML=loseAmt;
// 					   document.getElementById('transferAmountToSavingAccount').innerHTML=transferAmt;
// 						//alert(newInterestRate + " : " + newIntRate + "\n" + depositAmount + " : " +  depositAmt +"\n " +loseAmount  +  " : "  +loseAmt + "\n" + transferAmount + " : " + transferAmt);
						var tenureType = '${depositHolderForm.deposit.tenureType}';
						tenureArray = tenureType.split(',');
						var oldTenureType = tenureArray[0];

						
						if (parseInt('${depositHolderForm.deposit.tenure}') > 1) {
							oldTenureType = tenureArray[0] + 's';
						}
						
					

// 						if ('${depositHolderForm.editDepositForm.statusTenure}' == '') 
// 						{
// 							document.getElementById('newMaturityDate').style.display = 'none';
// 							document.getElementById('newMaturityDate').style.display = 'block';
							
// 						}
// 						else
// 						{
// 							document.getElementById('fdTenureType').value = '${depositHolderForm.deposit.tenure}'
// 																			    + ' ' + oldTenureType;
// 						}
			
// 						if ('${depositHolderForm.editDepositForm.statusTenure}' != '') 
// 						{

// 							var newTenureType = tenureArray[1];
// 							if (parseInt(tenureArray[2]) > 1)
// 								 newTenureType =newTenureType +'s';

// 							var addFlag = true;
// 							if ('${depositHolderForm.editDepositForm.statusTenure}' == 'add') {
							
// // 								document.getElementById('tenureAddedDiv').style.display = 'block';
// // 								document.getElementById('tenureReducedDiv').style.display = 'none';

// 								document.getElementById('tenureAdded').value = tenureArray[2]
// 										+ ' ' + newTenureType;

// 							}
// 							if ('${depositHolderForm.editDepositForm.statusTenure}' == 'reduce') {
// // 								document.getElementById('tenureReducedDiv').style.display = 'block';
// // 								document.getElementById('tenureAddedDiv').style.display = 'none';
// 								document.getElementById('tenureReduced').value = tenureArray[2]
// 										+ ' ' + newTenureType;
// 								addFlag = false;
// 							}

// 							document.getElementById('newMaturityDateDiv').style.display = 'block';
// 						} else {
// // 							document.getElementById('tenureAddedDiv').style.display = 'none';
// // 							document.getElementById('tenureReducedDiv').style.display = 'none';
// //							document.getElementById('newMaturityDateDiv').style.display = 'none';
// 						}

						if ('${depositHolderForm.deposit.stopPayment}'==1){
								document.getElementById('stopPayment').value='Yes';
								
						}
						else{
							document.getElementById('stopPayment').value='No';
						}
						
						var customerName = '${depositHolderForm.customerName}'.split(",");
					
						for(var i=0;i<customerName.length;i++){
							
							document.getElementById('customerName['+i+']').value=customerName[i];
						}
						

                  var payOffType='${depositHolderForm.deposit.payOffInterestType}';
						if(payOffType== 'End of Tenure' || payOffType == ''){
							document.getElementById('payOffSection').style.display = 'none';
							
							var inp = document.querySelectorAll('.payoffInterestType');
							
							for(i=0;i<inp.length;i++){
								inp[i].value = "";
							}
							
						}	
						else{
							document.getElementById('payOffSection').style.display = 'block';
						}
						
						
					});

</script>
<div class="right-container" id="right-container">
	<div class="container-fluid">


		<div class="Flexi_deposit">

			<div class="header_customer">
				<h3>
					<spring:message code="label.fixedDeposit" />
				</h3>
			</div>
			<div class="Success_msg">
				<div class="successMsg"
					style="text-align: center; color: green; font-size: 18px;">
					<h2>${success}</h2>
				</div>
			</div>



			<div class="flexi_table">
				<form:form action="editFdPost" method="post" id="depositHolderForm" class="form-horizontal"
					name="fixedDeposit" commandName="depositHolderForm"
					>  <!-- onsubmit="return val();" -->
					<%-- 	<form:hidden path="depositHolderId" />
					<form:hidden path="flexiRate" /> --%>
					<form:hidden path="deposit.interestRate" />
					<%-- 	<form:hidden path="customerId" /> --%>
					<form:hidden path="deposit.createdDate" />
					<form:hidden path="deposit.interestRate" />
					<form:hidden path="editDepositForm.status" />
					<form:hidden path="editDepositForm.statusTenure" />
					<form:hidden path="deposit.tenure" />
					<form:hidden path="deposit.tenureType" />
					<form:hidden path="deposit.modifiedDate" />
					<form:hidden path="deposit.linkedAccountNumber" />
					<form:hidden path="depositChange" />
					<form:hidden path="newDepositDaysValue" />
					<form:hidden path="depositConversion" />
					<form:hidden path="newDepositDeductionDay" />
					<form:hidden path="newDepositTenureType" />
					<form:hidden path="newDepositTenure" />
					<form:hidden path="newMaturityDate" value= "${newMaturityDate}"/>
					
					

					<div class="col-md-6">
						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.fdID" /></label>
							<div class="col-md-6">
								<form:input path="deposit.id" class="form-control" id="fdID"
									readonly="true" />
							</div>
						</div>
						
						
						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.accountNo" /></label>
							<div class="col-md-6">
								<form:input path="deposit.accountNumber" class="form-control"
									id="customerName" readonly="true" />
							</div>
						</div>
						<%-- <div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.typeOfDeposit" /></label>
							<div class="col-md-6">
								<form:input path="deposit.depositType" class="form-control"
									id="contactNum" readonly="true" />
							</div>
						</div> --%>
						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.selectDate" /></label>
							<div class="col-md-6">
								<form:input path="deposit.dueDate" class="form-control"
									id="fdDueDate" readonly="true" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.modeOfPay" /></label>
							<div class="col-md-6">

								<form:input path="deposit.paymentMode" class="form-control"
									id="fdPay" readonly="true" />

							</div>
						</div>
						<c:if test="${depositHolderForm.deposit.paymentMode=='Fund Transfer'}">
						<div class="form-group">
							<label class="col-md-4 control-label">Linked Account</label>
							<div class="col-md-6">

								<input type="text" value="${depositHolderForm.deposit.linkedAccountNumber}"  class="form-control" readonly />

							</div>
						</div>
						</c:if>
						
						<div class="form-group">
							<label class="col-md-4 control-label">Maturity date</label>
							<div class="col-md-6">
								<form:input path="deposit.maturityDate" class="form-control"
									id="maturityDate" readonly="true" />


							</div>
						</div>
						
						<c:if test="${depositHolderForm.depositChange=='tenureChange'}">
						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.newMaturityDate" /></label> 
							<div class="col-md-6">
								<input class="form-control" id="newMaturityDate"
								value="${newMaturityDate}" readonly="true" />
							</div>
						</div>
						</c:if>
						
						
<%-- 						<c:if test="${depositHolderForm.depositChange=='tenureChange'}"> --%>
						
<!-- 						<div class="form-group" id="tenureAddedDiv"> -->
<!-- 							<label class="col-md-4 control-label">Added tenure</label> -->
<!-- 							<div class="col-md-6"> -->
<!-- 								<input class="form-control" id="tenureAdded" readonly="true" /> -->


<!-- 							</div> -->
<!-- 						</div> -->
	               
<!-- 						<div class="form-group" id="tenureReducedDiv"> -->
<!-- 							<label class="col-md-4 control-label">Reduced tenure</label> -->
<!-- 							<div class="col-md-6"> -->
<!-- 								<input class="form-control" id="tenureReduced" readonly /> -->


<!-- 							</div> -->
<!-- 						</div> -->
						
<%-- 						</c:if> --%>
						
						
					</div>
					<div class="col-md-6">
						<div class="form-group">
							<label class="col-md-4 control-label">Deposit Amount</label>

							<fmt:formatNumber
								value="${depositHolderForm.deposit.depositAmount}"
								pattern="#.##" var="depositamount" />
							<div class="col-md-6">
								<form:input path="deposit.depositAmount" class="form-control"
									value="${depositamount}" id="fdFixed" readonly="true" />
							</div>
						</div>

						<!-- changes -->

						<c:if
							test="${!(empty depositHolderForm.editDepositForm.status) && ''+depositHolderForm.editDepositForm.addAmount!=''}">

							<div class="form-group">
								<c:if test="${depositHolderForm.editDepositForm.status=='add'}">
									<label class="control-label col-md-4">Added Amount</label>
								</c:if>
								<c:if
									test="${depositHolderForm.editDepositForm.status=='reduce'}">
									<label class="control-label col-md-4">Reduced Amount</label>
								</c:if>
								<div class="col-md-6">
									<fmt:formatNumber
										value="${depositHolderForm.editDepositForm.addAmount}"
										pattern="#.##" var="addAmount" />

									<form:input path="editDepositForm.addAmount"
										class="form-control" value="${addAmount}" readonly="true" />
								</div>
							</div>
						</c:if>


						<div class="form-group">
							<label class="col-md-4 control-label">Stop Payment</label>

							<div class="col-md-2">

								<input class="form-control" id="stopPayment" readonly />
							</div>
						</div>

						<form:hidden path="deposit.stopPayment" />

                       <c:if test="${depositHolderForm.depositChange!='convertDeposit'}">
						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.selectFormat" /></label>
							<div class="col-md-6">
								<form:input path="deposit.paymentType"  class="form-control"
									id="changePaymentType" readonly="true" />


							</div>
						</div>
						
						</c:if>
						
						 <c:if test="${depositHolderForm.depositChange=='convertDeposit'}">
						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.selectFormat" /></label>
							<div class="col-md-6">
								<form:input path="deposit.paymentType" value="${depositPaymentType}" class="form-control"
									id="changePaymentType" readonly="true" />


							</div>
						</div>
						
						</c:if>
						<%-- <c:if test="${depositHolderForm.depositChange=='tenureChange'}"> --%>
						<%-- 
						<c:if test="${!empty depositHolderForm.editDepositForm.deductionDay}">
						<div class="form-group">
							<label class="col-md-4 control-label">Deduction Day</label>
							<div class="col-md-6">
							<input type="text" path="depositHolderForm.deductionDay" value="${depositHolderForm.editDepositForm.deductionDay}"  class="form-control" readonly />
							
								

							</div>
						</div>
						</c:if> --%>
						
						<c:if test="${!empty depositHolderForm.editDepositForm.penalty}">
							<div class="form-group">
								<label class="col-md-4 control-label">Penalty Amount</label>
								<div class="col-md-6">
									<form:hidden path="editDepositForm.penalty" />
									<form:input path="editDepositForm.totalPenalty"
										class="form-control" readonly="true" />
								</div>
							</div>
						</c:if>
						<c:if test="${depositHolderForm.depositChange=='convertDeposit'}">
						<div style="clear: both; margin-top: 15px;"></div>
						<div class="form-group" style="width: 455px; border-bottom: 1px dotted #cfcfcf; text-align: -webkit-right;">
						<div class="col-md-8"><span id="rgId" style="display: block; margin-left:25px;">
									<h4>New Deposit / Converted Deposit</h4></span></div>
						</div>
						<c:if test="${depositHolderForm.depositChange=='tenureChange'}">
						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.fdTenure" /></label>
							<div class="col-md-6">
								<input class="form-control" id="fdTenureType" readonly="true" />
							</div>
						</div>
						</c:if>
					
					
					
						<c:if test="${depositHolderForm.depositChange=='convertDeposit'}">
						<div class="form-group">
							<label class="col-md-4 control-label">New <spring:message
									code="label.selectFormat" /></label>
							<div class="col-md-6">
								<form:input path="newPaymentType" class="form-control"
									id="changePaymentType" readonly="true" />


							</div>
						</div>
						</c:if>
						<c:if test="${depositHolderForm.depositChange=='convertDeposit'}">
						<div class="form-group">
							<label class="col-md-4 control-label">Converted To</label>
							<div class="col-md-6">
							<input type="text" path="depositHolderForm.depositConversion" value="${depositHolderForm.depositConversion}"  class="form-control" readonly />
							
							</div>
						</div>
                        </c:if>
						<!-- <div class="form-group" id="tenureReducedDiv">
							<label class="col-md-4 control-label">Reduced tenure</label>
							<div class="col-md-6">
								<input class="form-control" id="tenureReduced" readonly />


							</div>
						</div> -->
						<%-- <div class="form-group">
							<label class="col-md-4 control-label">Maturity date</label>
							<div class="col-md-6">
								<form:input path="deposit.maturityDate" class="form-control"
									id="maturityDate" readonly="true" />


							</div>
						</div>
						 --%>
						
						
<%-- 						<c:if test="${!empty depositHolderForm.editDepositForm.deductionDay}"> --%>
<!-- 						<div class="form-group"> -->
<!-- 							<label class="col-md-4 control-label">Amount Deduction Day</label> -->
<!-- 							<div class="col-md-6"> -->
<%-- 							<input type="text" path="depositHolderForm.deductionDay" value="${depositHolderForm.editDepositForm.deductionDay}"  class="form-control" readonly /> --%>
							
								

<!-- 							</div> -->
<!-- 						</div> -->
<%-- 						</c:if> --%>
						
						<c:if test="${!empty depositHolderForm.newDepositDeductionDay}">
						<div class="form-group">
							<label class="col-md-4 control-label">Amount Deduction Day</label>
							<div class="col-md-6">
							<input type="text" path="depositHolderForm.newDepositDeductionDay" value="${depositHolderForm.newDepositDeductionDay}"  class="form-control" readonly />
							
								

							</div>
						</div>
						</c:if>
						
						</c:if>
					</div>


					<div class="header_me col-md-12">
						<h3>Interest PayOff Details</h3>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.payOffPeriod" /></label>
						<div class="col-md-6">
							<form:input path="deposit.payOffInterestType"
								class="form-control" id="payOffType" readonly="true" />

						</div>
					</div>
					<form:hidden path="depositHolderList[0].customerId" />


					<div id="payOffSection">
						<c:forEach items="${depositHolderForm.depositHolderList}"
							varStatus="status" var="dHolder">
							<form:hidden path="depositHolderList[${status.index}].id" />



							<div class="header_me col-md-12">
								<h5>
									<b>Deposit holder: ${status.index+1}</b>
								</h5>
							</div>

							<div class="form-group" style="margin-top: 35px;">
								<label class="col-md-4 control-label">Holder name</label>
								<div class="col-md-6">
									<input id="customerName[${status.index}]" class="form-control"
										readonly />
								</div>
							</div>

							<div class="form-group">
								<label class="col-md-4 control-label">Holder type</label>
								<div class="col-md-6">
									<form:input
										path="depositHolderList[${status.index}].depositHolderStatus"
										class="form-control" readonly="true" />
								</div>
							</div>



							<div class="form-group" id="everyMonthDiv[${status.index}]"
								style="display: ${(empty dHolder.interestType) ? 'none': 'block'};">
								<label class="col-md-4 control-label"><spring:message
										code="label.interestType" /></label>
								<div class="col-md-6">

									<form:input
										path="depositHolderList[${status.index}].interestType"
										class="form-control payoffInterestType"
										id="interestType[${status.index}]" readonly="true" />

								</div>
							</div>




							<div class="form-group" id="interestPercentTr[${status.index}]"
								style="display: ${(!empty dHolder.interestPercent) ? 'block': 'none'};">
								<label class="col-md-4 control-label">Percentage</label>
								<div class="col-md-6">
									<form:input
										path="depositHolderList[${status.index}].interestPercent"
										class="form-control" placeholder="Enter Percentage"
										id="interestPercent[${status.index}]" readonly="true" />
								</div>
							</div>
							<div class="form-group"
								id="firstPartInterestAmtTr[${status.index}]"
								style="display: ${(! empty dHolder.interestAmt) ? 'block': 'none'};">
								<label class="col-md-4 control-label"><spring:message
										code="label.amount" /><span style="color: red">*</span></label>
								<div class="col-md-6">
									<form:input
										path="depositHolderList[${status.index}].interestAmt"
										class="form-control" id="interestPayAmount[${status.index}]"
										readonly="true" />
								</div>
							</div>
							<div class="form-group" id="firstPartInterest[${status.index}]"
								style="display: ${(empty dHolder.payOffAccountType) ? 'none': 'block'};">
								<label class="col-md-4 control-label"><spring:message
										code="label.payOffAccount1" /></label>
								<div class="col-md-6">
									<form:input
										path="depositHolderList[${status.index}].payOffAccountType"
										class="form-control" readonly="true" />
								</div>
							</div>


							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.name" /><span style="color: red">*</span></label>
								<div class="col-md-6">
									<form:input
										path="depositHolderList[${status.index}].nameOnBankAccount"
										placeholder="Enter Name" class="form-control" readonly="true" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.accountNo" /><span style="color: red">*</span></label>
								<div class="col-md-6">
									<form:input
										path="depositHolderList[${status.index}].accountNumber"
										placeholder="Enter AccountNo" class="form-control"
										readonly="true" />
								</div>
							</div>

							<div id="otherBankDetails[${status.index}]"
								style="display: ${(empty dHolder.bankName) ? 'none': 'block'};">

								<div class="form-group">
									<label class="col-md-4 control-label"><spring:message
											code="label.transfer" /><span style="color: red">*</span></label>
									<div class="col-md-6">
										<form:input class="form-control"
											path="depositHolderList[${status.index}].transferType"
											readonly="true" />
									</div>
								</div>

								<div class="form-group">
									<label class="col-md-4 control-label"><spring:message
											code="label.bank" /><span style="color: red">*</span></label>
									<div class="col-md-6">
										<form:input path="depositHolderList[${status.index}].bankName"
											placeholder="Enter Bank Name" class="form-control"
											readonly="true" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label"><spring:message
											code="label.ifsc" /><span style="color: red">*</span></label>
									<div class="col-md-6">
					
										<form:input path="depositHolderList[${status.index}].ifscCode"
											placeholder="Enter IFSC Code" class="form-control"
											readonly="true" />
									</div>
								</div>

							</div>

						</c:forEach>
					</div>



					<div class="form-group">
						<div class="col-md-offset-4 col-md-8">
							<input type= "button" onclick = "return backToEditFD()" class="btn btn-success" value="<spring:message
									code="label.back"/>"> <input type="submit" class="btn btn-info" value ="<spring:message code="label.save"/>">
						</div>
					</div>

				</form:form>
			</div>

		</div>
		
		
		
<script type="text/javascript">

function backToEditFD(){
	debugger;
	var id  = $("#fdID").val();
	
	var replacePathName = window.location.pathname;
	//replacePathName = replacePathName.replace("users","bankEmp");
	//replacePathName = replacePathName.replace("confirmEditFd","editFDBank");
	var backVal = "${back}";
	if(replacePathName.includes("users"))
		window.location = "editFDBank?id="+id+"&holderId=${holderId}&depositCategory=${depositHolderForm.deposit.depositCategory}";
	if(replacePathName.includes("bnkEmp"))
		window.location = "editFDBank?id="+id+"&holderId=${holderId}&depositCategory=${depositHolderForm.deposit.depositCategory}";
}

</script>
		
		
<style>
.form-control {
	background: whitesmoke;
	color: #000;
	cursor: pointer;
}
</style>