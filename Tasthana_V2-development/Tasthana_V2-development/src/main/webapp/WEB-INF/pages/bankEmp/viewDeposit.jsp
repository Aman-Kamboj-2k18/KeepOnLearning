<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<!-- To fetch the request url -->
<c:set var="req"
	value="${requestScope['javax.servlet.forward.request_uri']}" />
<c:set var="baseURL" value="${fn:split(req,'/')}" />

<script>
	$(document)
		.ready(
				function() {
					var paymentType = "${depositHolderForm.deposit.paymentType}";
					if(paymentType=="One-Time")
						document.getElementById("dueDate").value = null;
					else
						{
							var nextDueDate = "${depositHolderForm.deposit.dueDate}";// .withoutTime()
							var dueDate1 = new Date(nextDueDate);
							document.getElementById("dueDate").value = dueDate1.getDate()+ "/" + dueDate1.getMonth() + "/" + dueDate1.getFullYear();
						}
				});
				

</script>
<div class="right-container" id="right-container">
	<div class="container-fluid">

		<div class="Flexi_deposit">

			<div class="header_customer">
				<h3>
					<spring:message code="label.viewDeposit" />
				</h3>
			</div>
			<div class="Success_msg">
				<div class="successMsg"
					style="text-align: center; color: green; font-size: 18px;">
					<h2>${success}</h2>
				</div>
				<div style="text-align: center; font-size: 18px;">
					<h5 style="color: red;" id="error">${error}</h5>
				</div>
			</div>



			<div class="flexi_table">
				<form:form action="confirmEditFdBank" class="form-horizontal"
					method="post" name="fixedDeposit" commandName="depositHolderForm"
					id="editForm">


					<%-- <form:hidden path="depositHolderId" /> --%>
					<%-- 	<form:hidden path="flexiRate" /> --%>

					<%-- <form:hidden path="depositHolder.customerId" /> --%>
					<%-- 					<form:hidden path="deposit.interestRate" /> --%>
					<%-- 					<form:hidden path="deposit.tenureType" /> --%>
					<%-- 					<form:hidden path="deposit.tenure" /> --%>
					<%-- 					<form:hidden path="depositHolder.depositHolderCategory" /> --%>
					<%-- 					<form:hidden path="deposit.modifiedDate" id="modifiedDate" /> --%>



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
							<label class="col-md-4 control-label" style="margin-top: -7px;"><spring:message
									code="label.depositClassification" /></label>
							<div class="col-md-6">
								<form:input path="deposit.depositClassification"
									value="${depositClassification}" class="form-control" id="fdID"
									readonly="true" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-4 control-label">Deposit Amount</label>
							<div class="col-md-6">
								<fmt:formatNumber
									value="${depositHolderForm.deposit.depositAmount}"
									pattern="#.##" var="depositamount" />
								<form:hidden path="deposit.depositAmount" id="fdFixed" />
								<input type="text" id="depositAmounttxt"
									class="form-control commaSeparated" value="${depositamount}"
									readonly />

							</div>
						</div>


						<div class="form-group">
							<label class="col-md-4 control-label">Payment Type</label>
							<div class="col-md-6">
								<form:input path="deposit.paymentType" class="form-control"
									readonly="true" />
							</div>
						</div>


					</div>


					<div class="col-md-6">

						<div class="form-group">
							<label class="col-md-4 control-label">Created Date</label>
							<div class="col-md-6">
								<form:input path="deposit.createdDate" class="form-control"
									id="createdDate" readonly="true" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-4 control-label">Tenure</label>
							<div class="col-md-6">
								<input class="form-control"
									value="${depositHolderForm.deposit.tenure} ${depositHolderForm.deposit.tenureType}"
									id="tenureConcat" readonly />
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-4 control-label">Maturity Date</label>
							<div class="col-md-6">
								<form:input path="deposit.maturityDate" id="maturityDate"
									class="form-control" readonly="true" />
							</div>
						</div>


						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.modeOfPay" /></label>
							<div class="col-md-6">
								<form:input path="deposit.paymentMode" class="form-control"
									id="fdID" readonly="true" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-4 control-label">Account Details</label>
							<div class="col-md-6">
								<form:input path="deposit.linkedAccountNumber"
									class="form-control" readonly="true" />
							</div>
						</div>



						<!-- 						<div class="form-group"> -->
						<!-- 							<label class="col-md-4 control-label">Next Payment Due Date</label> -->
						<!-- 							<div class="col-md-6"> -->
						<%-- 								<form:input path="deposit.dueDate" class="form-control" --%>
						<%-- 									id="fdDueDate" readonly="true" /> --%>
						<!-- 							</div> -->
						<!-- 						</div> -->

						<div class="form-group">
							<label class="col-md-4 control-label">Next Due Date</label>
							<div class="col-md-6">
								<input id="dueDate" class="form-control" readonly="true" />
							</div>
						</div>





						<!-- changes.............. -->
						<div class="form-group" id="addReduceAmountDIV"
							style="display: none;">
							<label class="control-label col-md-4" for=""
								style="margin-top: -5px;">Add/Reduce Amount </label> <label
								for="radio" style="margin-left: 15px;"> <form:radiobutton
									path="editDepositForm.status" value="add" id="addRadio"
									name="addRadio" onclick="showAddReduceBox('addReduceDiv')"></form:radiobutton></label>
							Add <label for="radio"> <form:radiobutton
									path="editDepositForm.status" value="reduce" id="reduceRadio"
									name="reduceRadio" onclick="showAddReduceBox('addReduceDiv')"></form:radiobutton></label>
							Reduce


						</div>



						<span id="taxSavingDepositErrorForAmount" class="error"
							style="display: none;"><font color="red"><spring:message
									code="label.taxSavingDepositErrorForAmount" /></font></span>



						<div class="form-group" id="addReduceDiv" style="display: none;">
							<label class="control-label col-md-4" for="">Enter Amount
							</label>
							<div class="col-md-3">
								<fmt:formatNumber
									value="${depositHolderForm.editDepositForm.addAmount}"
									pattern="#.##" var="addAmount" />
								<form:input path="editDepositForm.addAmount"
									class="form-control input" type="number" value="${addAmount}"
									id="addAmount" onkeyup="addedAmt()"
									onkeypress="validationAccount(event)" min="0" />

							</div>
							<div class="col-md-1">
								<a href="#" onclick="hideDiv('amount')">Cancel</a>
							</div>
							<div style="clear: both; height: 8px;"></div>
							<label class="control-label col-md-4" for=""
								style="margin-bottom: -5px;">Final Deposit Amount </label>
							<div class="col-md-6" style="margin-bottom: -5px;">

								<form:input path="editDepositForm.addAmount"
									class="form-control" type="number" value="${totalAmount}"
									id="totalAmount" readonly="true" />

							</div>



						</div>

						<div style="clear: both; margin-top: 15px;"></div>

						<div class="form-group" style="display: none;"
							id="convertDepositDIV">
							<div class="form-group"
								style="width: 455px; border-bottom: 1px dotted #cfcfcf; text-align: -webkit-right;">
								<div class="col-md-10">
									<span style="display: block; margin-left: 25px;">
										<h4>New Deposit / Convert Deposit</h4>
									</span>
								</div>
							</div>

							<div class="form-group"
								style="margin-top: -10px; margin-bottom: -5px;">

								<div class="col-md-4"
									style="text-align: right; margin-top: -10px;">

									<span id="convertTo"><h5>
											Convert to<span style="color: red">*</span>
										</h5></span>
								</div>

								<div class="col-md-7" style="display: none;" id="recurringDiv">
									<label for="radio"> <form:radiobutton
											path="depositConversion" value="Recurring Deposit"
											id="recurring" name="addRadio" onclick="recurringDeposit()"></form:radiobutton></label>
									<spring:message code="label.recurringDeposit" />
									<br>
								</div>
								<div class="col-md-7" style="display: none;" id="regularDiv">
									<label for="radio"> <form:radiobutton
											path="depositConversion" value="Regular Deposit" id="regular"
											name="reduceRadio" onclick="regularDeposit()"></form:radiobutton></label>
									<spring:message code="label.regularDeposit" />


								</div>
							</div>


							<div class="form-group"
								style="display: none; clear: both; margin-top: 15px;"
								id="typeOfTenureDiv">
								<label class="col-md-4 control-label"><spring:message
										code="label.typeOfTenure" /><span style="color: red">*</span></label>
								<div class="col-md-6">
									<form:select path="newDepositTenureType"
										class="input form-control" id="newFDTenureType"
										onchange="showTenureDetails(this.value)">
										<form:option value="">
											<spring:message code="label.selectValue" />
										</form:option>
										<form:option value="Day">
											<spring:message code="label.days" />
										</form:option>
										<form:option value="Month">
											<spring:message code="label.month" />
										</form:option>
										<form:option value="Year">
											<spring:message code="label.Year" />
										</form:option>
									</form:select>
								</div>

							</div>
							<div class="form-group" style="display: none;" id="tenureDiv">
								<label class="col-md-4 control-label"> <span id="dayId"
									style="display: none"><spring:message code="label.days" /><span
										style="color: red">*</span></span><span id="monthId"
									style="display: none"><spring:message code="label.month" /><span
										style="color: red">*</span></span> <span id="yearId"
									style="display: none"><spring:message code="label.Year" /><span
										style="color: red">*</span></span></label>


								<div class="col-md-2">
									<form:input path="newDepositTenure" type="number" min="0"
										class="input form-control" id="newFDTenure"
										onkeypress="validationAccount(event)" />
									<span id="fdTenureError" style="display: none; color: red"><spring:message
											code="label.tenureError" /></span> <span id="fdTenureMax9YearError"
										style="display: none; color: red">Please enter valid </span>

								</div>

								<div id="dayValue" style="margin-left: 15px;">
									<label class="col-md-2 control-label"
										style="text-align: right; padding-left: 45px;"><spring:message
											code="label.days" /></label>
									<div class="col-md-2">
										<form:input path="newDepositDaysValue" type="number" min="0"
											class="input form-control" id="daysValue"
											onkeypress="validationAccount(event)" />
										<span id="dayValueError" style="display: none; color: red"><spring:message
												code="label.daysError" /></span>

									</div>

								</div>


							</div>


							<div class="form-group" style="display: none"
								id="newPaymentTypeDiv">
								<label class="col-md-4 control-label"><spring:message
										code="label.selectFormat" /></label>
								<div class="col-md-6">
									<form:select path="newPaymentType" class="form-control input"
										id="newPaymentTypeId">
										<option value="">
											<spring:message code="label.selectValue" />
										</option>
										<option value="One-Time" id="oneTimeNew">
											<spring:message code="label.oneTimePayment" />
										</option>
										<option value="Monthly" id="monthlyNew">
											<spring:message code="label.monthly" />
										</option>
										<option value="Quarterly" id="quarterlyNew">
											<spring:message code="label.quarterly" />
										</option>
										<option value="Half Yearly" id="halfYearlyNew">
											<spring:message code="label.halfYearly" />
										</option>
										<option value="Annually" id="annuallyNew">
											<spring:message code="label.annually" />
										</option>
									</form:select>
								</div>
							</div>




							<div class="form-group" style="display: none" id="deductionDay">
								<label class="col-md-4 control-label" style="margin-top: -7px;">Amount
									Deducting Day<span style="color: red">*</span>
								</label>
								<div class="col-md-2">
									<form:select path="newDepositDeductionDay" class="form-control"
										id="deductionDayVal">
										<option value="">Day</option>
										<%
											for (int i = 1; i <= 30; i++) {
										%>
										<option value="<%=i%>"><%=i%></option>
										<%
											}
										%>

									</form:select>
								</div>
							</div>
							<c:if test="${!empty penaltyAmount}">
								<div class="form-group">
									<label class="col-md-4 control-label">(Penalty+GST)
										Amount</label>
									<div class="col-md-6">
										<form:hidden path="editDepositForm.penalty"
											value="${penaltyAmount}" />
										<form:input path="editDepositForm.totalPenalty"
											class="form-control" value="${totalpenaltyAmount}"
											readonly="true" />
									</div>
								</div>
							</c:if>


						</div>
					</div>



					<div class="header_me col-md-12">
						<h3>Interest PayOff Details</h3>
					</div>


					<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.payOffPeriod" /></label>
						<div class="col-md-6">
							<form:select path="deposit.payOffInterestType"
								class="form-control input" id="payOffType"
								onchange="onchangePayOffType()" readonly="true">
								<option value="">
									<spring:message code="label.select" />
								</option>
								<option value="Monthly" id="days"
									<c:if test="${depositHolderForm.deposit.payOffInterestType == 'Monthly'}"> selected</c:if>>
									<spring:message code="label.fdPayOffType1" />
								</option>
								<option value="Quarterly"
									<c:if test="${depositHolderForm.deposit.payOffInterestType == 'Quarterly'}"> selected</c:if>>
									<spring:message code="label.fdPayOffType3" />
								</option>
								<option value="Semiannual"
									<c:if test="${depositHolderForm.deposit.payOffInterestType == 'Semiannual'}"> selected</c:if>>
									<spring:message code="label.fdPayOffType4" />
								</option>
								<option value="Annual"
									<c:if test="${depositHolderForm.deposit.payOffInterestType == 'Annual'}"> selected</c:if>>
									<spring:message code="label.fdPayOffType5" />
								</option>
								<option value="End of Tenure"
									<c:if test="${depositHolderForm.deposit.payOffInterestType == 'End of Tenure'}"> selected</c:if>>
									<spring:message code="label.fdPayOffType2" />
								</option>
							</form:select>
						</div>
					</div>

					<div id="payOffDetails">

						<form:hidden path="depositHolderList[0].customerId"
							value="${depositHolderList[0].depositHolder.customerId}" />


						<c:forEach items="${depositHolderList}" varStatus="status"
							var="dHolder">
							<form:hidden path="depositHolderList[${status.index}].id"
								value="${dHolder.depositHolder.id}" />

							<div class="header_me col-md-12">
								<h5>Deposit holder: ${status.index+1}</h5>
							</div>


							<div class="form-group" style="margin-top: 35px;">
								<label class="col-md-4 control-label">Holder name</label>
								<div class="col-md-6">
									<form:input path="customerName"
										value="${depositHolderList[status.index].customerName}"
										class="form-control" readonly="true" />
								</div>
							</div>

							<div class="form-group">
								<label class="col-md-4 control-label">Holder type</label>
								<div class="col-md-6">
									<form:input
										path="depositHolderList[${status.index}].depositHolderStatus"
										value="${depositHolderList[status.index].depositHolder.depositHolderStatus}"
										class="form-control" readonly="true" />
								</div>
							</div>





							<div class="form-group" id="everyMonthDiv[${status.index}]">
								<label class="col-md-4 control-label"><spring:message
										code="label.interestType" /></label>
								<div class="col-md-6">
									<form:select
										path="depositHolderList[${status.index}].interestType"
										class="input form-control" id="fdPay[${status.index}]"
										onchange="displayDiv(this.value,${status.index})" readonly="true">
										<option value="">Select</option>
										<option value="PART"
											<c:if test="${dHolder.depositHolder.interestType == 'PART'}"> selected </c:if>>PART</option>
										<option value="PERCENT"
											<c:if test="${dHolder.depositHolder.interestType == 'PERCENT'}"> selected </c:if>>PERCENT</option>

									</form:select>
								</div>
							</div>

							<div id="interestPercentTr[${status.index}]"
								style="display: ${(dHolder.depositHolder.interestType=='PERCENT') ? 'block': 'none'};">
								<div class="form-group">
									<label class="col-md-4 control-label">Percentage</label>
									<div class="col-md-6">
										<form:input
											path="depositHolderList[${status.index}].interestPercent"
											class="form-control input" placeholder="Enter Percentage"
											value="${dHolder.depositHolder.interestPercent}"
											id="interestPercent[${status.index}]" type="number" min="0"
											max="99" onkeypress="return isNumberKey1(event,$(this))"
											onkeyup="return checkPercentage(event,$(this))" />
									</div>
								</div>
							</div>




							<div id="firstPartInterestAmtTr[${status.index}]"
								style="display: ${(dHolder.depositHolder.interestType=='PART') ? 'block': 'none'};">
								<div class="form-group">
									<label class="col-md-4 control-label"><spring:message
											code="label.amount" /></label>
									<div class="col-md-6">
										<form:input
											path="depositHolderList[${status.index}].interestAmt"
											class="form-control input"
											value="${dHolder.depositHolder.interestAmt}"
											id="interestPayAmount[${status.index}]"
											onkeypress="return isNumberKey2(event,$(this))"
											onkeyup="return checkAmount(event,$(this))" />
									</div>
								</div>
							</div>

							<div class="form-group" id="firstPartInterest[${status.index}]">
								<label class="col-md-4 control-label"><spring:message
										code="label.payOffAccount1" /></label>
								<div class="col-md-6">
									<form:select
										path="depositHolderList[${status.index}].payOffAccountType"
										class="form-control input"
										onchange="onChangeAccountType(this.value,${status.index},true)" readonly="true">
										<option value="">Select</option>

										<option value="Saving Account"
											<c:if test="${dHolder.depositHolder.payOffAccountType == 'Saving Account'}"> selected </c:if>>Same
											Bank</option>
										<option value="Other"
											<c:if test="${dHolder.depositHolder.payOffAccountType == 'Other'}"> selected </c:if>>Different
											Bank</option>

									</form:select>
								</div>
							</div>

							<div id="beneficiaryDetails[${status.index}]"
								style="display: ${(empty dHolder.depositHolder.nameOnBankAccount) ? 'none': 'block'};">
								<div class="form-group">
									<label class="col-md-4 control-label"><spring:message
											code="label.name" /><span style="color: red">*</span></label>
									<div class="col-md-6">
										<form:input
											path="depositHolderList[${status.index}].nameOnBankAccount"
											value="${dHolder.depositHolder.nameOnBankAccount}"
											id="depositHolderList${status.index}.nameOnBankAccount"
											placeholder="Enter Name" class="form-control input"
											onkeypress="validName(event)" />
									</div>
								</div>

								<div class="form-group">
									<label class="col-md-4 control-label"><spring:message
											code="label.accountNo" /><span style="color: red">*</span></label>
									<div class="col-md-6">
										<form:input
											path="depositHolderList[${status.index}].accountNumber"
											value="${dHolder.depositHolder.accountNumber}"
											id="depositHolderList${status.index}.accountNumber"
											placeholder="Enter AccountNo" class="form-control input"
											onkeypress="validationAccount(event)" min="0" />
									</div>
								</div>
							</div>

							<div id="otherBankDetails[${status.index}]"
								style="display: ${(empty dHolder.depositHolder.bankName) ? 'none': 'block'};">

								<div class="form-group">
									<label class="col-md-4 control-label"><spring:message
											code="label.transfer" /><span style="color: red">*</span></label>
									<div class="col-md-6">
										<label for="radio"> <form:radiobutton
												path="depositHolderList[${status.index}].transferType"
												value="NEFT" id="neft[${status.index}]"
												name="transferType[${status.index}]"></form:radiobutton></label>
										<spring:message code="label.transfer1" />
										<label for="radio"> <form:radiobutton
												path="depositHolderList[${status.index}].transferType"
												value="IMPS" id="imps[${status.index}]"
												name="transferType[${status.index}]"></form:radiobutton></label>
										<spring:message code="label.transfer2" />
										<label for="radio"><form:radiobutton
												path="depositHolderList[${status.index}].transferType"
												value="RTGS" id="rtgs[${status.index}]"
												name="transferType[${status.index}]"></form:radiobutton></label>
										<spring:message code="label.transfer3" />
									</div>
								</div>




								<div class="form-group">
									<label class="col-md-4 control-label"><spring:message
											code="label.bank" /><span style="color: red">*</span></label>
									<div class="col-md-6">
										<form:input path="depositHolderList[${status.index}].bankName"
											placeholder="Enter Bank Name" class="form-control input"
											value="${dHolder.depositHolder.bankName}"
											id="depositHolderList${status.index}.bankName"
											onkeypress="validName(event)" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label"><spring:message
											code="label.ifsc" /><span style="color: red">*</span></label>
									<div class="col-md-6">
										<form:input path="depositHolderList[${status.index}].ifscCode"
											placeholder="Enter IFSC Code" class="form-control input"
											value="${dHolder.depositHolder.ifscCode}"
											id="depositHolderList${status.index}.ifscCode"
											onkeypress="validIFSC(event)" />
									</div>
								</div>

							</div>



						</c:forEach>
					</div>

					<c:if test="">
						<div class="form-group" id="confirmConvert"
							style="display: none; color: red">



							<%-- <table align="center" class="f_deposit_btn">
							<tr>
								<td><span id='validationError' style="color: red"></span></td>
							</tr>
							<tr>
								<td><input type="button" onclick="val()"
									class="btn btn-info"
									value="<spring:message code="label.confirm"/>">gtgtdhtrhr</td>


							</tr>

						</table> --%>


						</div>
					</c:if>
				</form:form>
			</div>

			<div class="form-group">



				<table align="center" class="f_deposit_btn">
					<tr>
						<td><span id='validationError' style="color: red"></span></td>
					</tr>
					

				</table>


			</div>
		</div>
	</div>
</div>






<div id="myModal" class="modal">

	<!-- Modal content -->
	<div class="modal-content" style="height: 345px !important;">
		<h4>
			<u>Confirm change in maturity amount and maturity date</u>
		</h4>


		<table class="table table-bordered" align="center" id="my-table">
			<thead>
				<tr class="success">
					<th>Parameters</th>
					<th>Old value</th>
					<th>New value</th>

				</tr>
			</thead>
			<tbody>

				<!-- 				<tr id="maturityAmount">
					<td><b>Maturity amount:</b></td>
 					<td id="oldMaturityAmount"></td> 
 					<td id="newMaturityAmount"></td> 
				</tr> -->

				<tr>
					<td><b>Maturity date:</b></td>
					<td id="oldMaturityDate"></td>
					<td id="newMaturityDate2"></td>
				</tr>
				<tr>
					<td><b>Rate:</b></td>
					<td id="oldRate"></td>
					<td id="newRate"></td>
				</tr>


			</tbody>
		</table>

		<div style="margin-left: 4px; margin-top: -13px;">
			<b>Adjustment amount:</b> <span id="adjustAmt"></span>
		</div>
		<div class="col-md-8" style="margin-top: 19px;">
			<button class="btn btn-success" id="proceed">Proceed</button>
			<button class="btn btn-danger" id="cancel">Cancel</button>
		</div>
	</div>

</div>

<div id="convertDepositAlertModal" class="modal">

	<!-- Modal content -->
	<div class="modal-content">
		<h4>
			<u>Deposit Conversion Details</u>
		</h4>


		<table class="table table-bordered" align="center" id="my-table">
			<thead>
				<tr class="success">
					<th>Parameters</th>
					<th>Value</th>

				</tr>
			</thead>
			<tbody>
				<tr>
					<td><b>Rate of Current Deposit</b></td>
					<td id="newInterestRate"></td>
				</tr>
				<tr>
					<td><b>Lose Amount</b></td>
					<td id="interestNeedsToAdjust"></td>
				</tr>
				<tr>
					<td><b>New Deposit Amount</b></td>
					<td id="newDepositAmount"></td>
				</tr>
				<tr>
					<td><b>Transfer Amount to Saving Account</b></td>
					<td id="transferAmountToSavingAccount"></td>
				</tr>
			</tbody>
		</table>

		<div class="col-md-12" style="margin-top: 19px;">
			<button class="btn btn-success" id="proceedForConversion">Proceed</button>
			<button class="btn btn-danger" id="cancelConversion">Cancel</button>
		</div>
	</div>

</div>

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
	padding: 20px;
	margin-top: 150px;
	margin-left: 283px;
	margin-top: :91px;
	border: 1px solid #888;
	width: 64%;
	height: 345px;
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
</style>