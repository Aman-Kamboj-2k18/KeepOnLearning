<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!-- To fetch the request url -->
<c:set var="req"
	value="${requestScope['javax.servlet.forward.request_uri']}" />
<c:set var="baseURL" value="${fn:split(req,'/')}" />


<script type="text/javascript">
	$(document)
			.ready(
					function() {

						var tenureType = '${depositHolderForm.deposit.tenureType}';
						tenureArray = tenureType.split(',');
						var oldTenureType = tenureArray[0];

						if (parseInt('${depositHolderForm.deposit.tenure}') > 1) {
							oldTenureType = tenureArray[0] + 's';
						}
						document.getElementById('fdTenureType').value = '${depositHolderForm.deposit.tenure}'
								+ ' ' + oldTenureType;

						if ('${depositHolderForm.editDepositForm.statusTenure}' != '') {

							var newTenureType = tenureArray[1];
							if (parseInt(tenureArray[2]) > 1)
								 newTenureType = newTenureType +'s';

							var addFlag = true;
							if ('${depositHolderForm.editDepositForm.statusTenure}' == 'add') {
								document.getElementById('tenureAddedDiv').style.display = 'block';
								document.getElementById('tenureReducedDiv').style.display = 'none';

								document.getElementById('tenureAdded').value = tenureArray[2]
										+ ' ' + newTenureType;

							}
							if ('${depositHolderForm.editDepositForm.statusTenure}' == 'reduce') {
								document.getElementById('tenureReducedDiv').style.display = 'block';
								document.getElementById('tenureAddedDiv').style.display = 'none';
								document.getElementById('tenureReduced').value = tenureArray[2]
										+ ' ' + newTenureType;
								addFlag = false;
							}

							document.getElementById('newMaturityDateDiv').style.display = 'block';
						} else {
							document.getElementById('tenureAddedDiv').style.display = 'none';
							document.getElementById('tenureReducedDiv').style.display = 'none';
							document.getElementById('newMaturityDateDiv').style.display = 'none';
						}

				
						if (${depositHolderForm.deposit.stopPayment==1}){
								document.getElementById('stopPayment').value='Yes';
								
						}
						else{
							document.getElementById('stopPayment').value='No';
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
				<form:form action="editFdPost" method="post" class="form-horizontal"
					name="fixedDeposit" commandName="depositHolderForm"
					onsubmit="return val();">
				
					<form:hidden path="deposit.interestRate" />
					<form:hidden path="deposit.createdDate" />
					<form:hidden path="deposit.interestRate" />
					<form:hidden path="editDepositForm.status" />
					<form:hidden path="editDepositForm.statusTenure" />
					<form:hidden path="deposit.tenure" />
					<form:hidden path="deposit.tenureType" />
					<form:hidden path="deposit.modifiedDate" />
					<form:hidden path="deposit.linkedAccountNumber" />
					<form:hidden path="depositChange" />
					<form:hidden path="depositConversion" />


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
						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.typeOfDeposit" /></label>
							<div class="col-md-6">
								<form:input path="deposit.depositType" class="form-control"
									id="contactNum" readonly="true" />
							</div>
						</div>
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
							<label class="col-md-4 control-label">Stop Payment</label>

							<div class="col-md-2">

								<input class="form-control" id="stopPayment" readonly /> 
							</div>
						</div>

						<form:hidden path="deposit.stopPayment" />
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
							<label class="col-md-4 control-label"><spring:message
									code="label.fdTenure" /></label>
							<div class="col-md-6">
								<input class="form-control" id="fdTenureType" readonly="true" />
							</div>
						</div>
						<div class="form-group" id="tenureAddedDiv">
							<label class="col-md-4 control-label">Added tenure</label>
							<div class="col-md-6">
								<input class="form-control" id="tenureAdded" readonly="true" />


							</div>
						</div>

						<div class="form-group" id="tenureReducedDiv">
							<label class="col-md-4 control-label">Reduced tenure</label>
							<div class="col-md-6">
								<input class="form-control" id="tenureReduced" readonly />


							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">Maturity date</label>
							<div class="col-md-6">
								<form:input path="deposit.maturityDate" class="form-control"
									id="maturityDate" readonly="true" />


							</div>
						</div>
						<div class="form-group" id="newMaturityDateDiv">
							<label class="col-md-4 control-label">New maturity date</label>
							<div class="col-md-6">
								<form:input path="editDepositForm.maturityDateNew"
									class="form-control" id="newMaturityDate" readonly="true" />

							</div>
						</div>
						
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
								<form:input path="deposit.paymentType" value ="${depositPaymentType}" class="form-control"
									id="changePaymentType" readonly="true" />


							</div>
						</div>
						</c:if>
						
						
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
						
						
						<c:if test="${!empty depositHolderForm.editDepositForm.deductionDay}">
						<div class="form-group">
							<label class="col-md-4 control-label">Amount Deduction Day</label>
							<div class="col-md-6">
							<input type="text" path="depositHolderForm.deductionDay" value="${depositHolderForm.editDepositForm.deductionDay}"  class="form-control" readonly />
							
								

							</div>
						</div>
						</c:if></c:if>
					</div>

					<div class="header_me col-md-12">
						<h3>Interest PayOff Details</h3>
					</div>
						<form:hidden path="depositHolderList[0].id" />
						
						
					     <form:hidden path="depositHolderList[0].customerId" />
					<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.payOffPeriod" /></label>
						<div class="col-md-6">
							<form:input path="deposit.payOffInterestType"
								class="form-control" id="payOffType" readonly="true" />

						</div>
					</div>

					<c:choose>
						<c:when
							test="${depositHolderForm.deposit.payOffInterestType == 'End of Tenure' || depositHolderForm.deposit.payOffInterestType == ''}">
							<div class="col-md-10">No Payoff selected</div>

						</c:when>
						<c:otherwise>
 
				<c:forEach items="${depositHolderForm.depositHolderList}"
								varStatus="status" var="dHolder">
							
				 	      


                    <div class="header_me col-md-12">
						<h5><b>Deposit holder: 1</b></h5>
					</div>					
					
						<div class="form-group" style="margin-top: 35px;">
									<label class="col-md-4 control-label">Holder name</label>
									<div class="col-md-6">
										<input id="customerName[0]" value="${depositHolderForm.customerName}" class="form-control" readonly="true" />
									</div>
								</div>
								
								<div class="form-group" >
									<label class="col-md-4 control-label">Holder type</label>
									<div class="col-md-6">
										<form:input path="depositHolderList[0].depositHolderStatus" class="form-control" readonly="true" />
									</div>
								</div>
										
                 <c:if test="${depositHolderForm.depositHolderList[status.index].interestType!=''}">
								
									<div class="form-group" id="everyMonthDiv[0]">
										<label class="col-md-4 control-label"><spring:message
												code="label.interestType" /></label>
										<div class="col-md-6">
										
												<form:input	path="depositHolderList[0].interestType"
													class="form-control" id="fdPay" readonly="true" />
						
										</div>
									</div>


								

									<div class="form-group" id="interestPercentTr[0]"
										style="display: ${(!empty dHolder.interestPercent) ? 'block': 'none'};">
										<label class="col-md-4 control-label">Percentage</label>
										<div class="col-md-6">
											<form:input
												path="depositHolderList[0].interestPercent"
												class="form-control" placeholder="Enter Percentage"
												id="interestPercent[0]" readonly="true" />
										</div>
									</div>
									<div class="form-group"
										id="firstPartInterestAmtTr[0]"
										style="display: ${(! empty dHolder.interestAmt) ? 'block': 'none'};">
										<label class="col-md-4 control-label"><spring:message
												code="label.amount" /><span style="color: red">*</span></label>
										<div class="col-md-6">
											<form:input
												path="depositHolderList[0].interestAmt"
												class="form-control" id="interestPayAmount[0]"
												readonly="true" />
										</div>
									</div>
									<div class="form-group" id="firstPartInterest[0]">
										<label class="col-md-4 control-label"><spring:message
												code="label.payOffAccount1" /></label>
										<div class="col-md-6">
											<form:input
												path="depositHolderList[0].payOffAccountType"
												class="form-control" readonly="true" />
										</div>
									</div>
								

								<div id="beneficiaryDetails[0]"
									style="display: ${(empty dHolder.nameOnBankAccount) ? 'none': 'block'};">
									<div class="form-group">
										<label class="col-md-4 control-label"><spring:message
												code="label.name" /><span style="color: red">*</span></label>
										<div class="col-md-6">
											<form:input
												path="depositHolderList[0].nameOnBankAccount"
												placeholder="Enter Name" class="form-control"
												readonly="true" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-4 control-label"><spring:message
												code="label.accountNo" /><span style="color: red">*</span></label>
										<div class="col-md-6">
											<form:input
												path="depositHolderList[0].accountNumber"
												placeholder="Enter AccountNo" class="form-control"
												readonly="true" />
										</div>
									</div>
								</div>
								<div id="otherBankDetails[0]"
									style="display: ${(empty dHolder.bankName) ? 'none': 'block'};">
									
								<div class="form-group" id="transferMode[0]">
									<label class="col-md-4 control-label"><spring:message
											code="label.transfer" /><span style="color: red">*</span></label>
									<div class="col-md-6">
										<form:input class="form-control"
											path="depositHolderList[0].transferType"
											readonly="true" />
									</div>
								</div>
								
									<div class="form-group">
										<label class="col-md-4 control-label"><spring:message
												code="label.bank" /><span style="color: red">*</span></label>
										<div class="col-md-6">
											<form:input
												path="depositHolderList[0].bankName"
												placeholder="Enter Bank Name" class="form-control"
												readonly="true" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-4 control-label"><spring:message
												code="label.ifsc" /><span style="color: red">*</span></label>
										<div class="col-md-6">
											<form:input
												path="depositHolderList[0].ifscCode"
												placeholder="Enter IFSC Code" class="form-control"
												readonly="true" />
										</div>
									</div>
									
								</div>
								</c:if>
						</c:forEach>
						</c:otherwise>
					</c:choose>

				
					<div class="form-group">
						<div class="col-md-offset-4 col-md-8">
							
							<a
								href="fdList" class="btn btn-success"><spring:message
									code="label.back" /></a>
									<input type="submit" class="btn btn-info"
								value="<spring:message code="label.save"/>"> 
						</div>
					</div>

				</form:form>
			</div>

		</div>
		<style>
.form-control {
	background: whitesmoke;
	color: #000;
	cursor: pointer;
}
</style>