<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- To fetch the request url -->


<c:set var="req"
	value="${requestScope['javax.servlet.forward.request_uri']}" />
<c:set var="baseURL" value="${fn:split(req,'/')}" />

 <c:forEach items="${baseURL}" var="urlValue">
 	<c:if test = "${urlValue == 'common'}">
        <c:set var="urlvalue"  value = "${urlValue}"/>
      </c:if>
      
      <c:if test = "${urlValue == 'users'}">
        <c:set var="urlvalue"  value = "${urlValue}"/>
      </c:if>
</c:forEach>
 



<div class="right-container" id="right-container">
	<div class="container-fluid">

		<div class="Flexi_deposit">

			<div class="header_customer">
				<h3>Deposit Confirmation</h3>
			</div>
			<div class="col-sm-12 col-md-12 col-lg-12">
				<div class="errorMsg">${error}</div>
			</div>
			<div class="flexi_table">

				<form:form class="form-horizontal" action="saveDeposit"
					method="post" name="fixedDeposit" commandName="fixedDepositForm"
					id="confirmForm">
					 <form:hidden path="customerName" id="userName"/>
					<form:hidden path="contactNum" />
					<form:hidden path="branchCode" />
					<form:hidden path="email" />
					<form:hidden path="id" />
					<form:hidden path="category" />
					<form:hidden path="flexiInterest" />
					<form:hidden path="maturityDate" />
					<form:hidden path="interstPayType" />
					<form:hidden path="totalTenureInDays" />
					<form:hidden path="tenureInYears" />
					<form:hidden path="tenureInMonths" />
					<form:hidden path="tenureInDays" />
					<form:hidden path="productConfigurationId" />
					<input type="hidden">
					
					<form:hidden path="status" />
					<form:hidden path="deductionDay" />
					<form:hidden path="maturityInstruction" />
					 <form:hidden path="daysValue" />
					  <form:hidden path="taxSavingDeposit" />

  					<%-- <form:hidden path="nomineePan" /> --%>
					<form:hidden path="nomineeAadhar" />
					<form:hidden path="gaurdianAadhar" />
					 <form:hidden path="depositClassification" />  
					 <form:hidden path="citizen" />
					 <form:hidden path="nriAccountType" />  
					 <form:hidden name="productId" path="" value="${fixedDepositForm.productConfigurationId }" />
					 <input type="hidden" name="customerDetails" value='${customerDetails}'/>
					  
					<br>
					<div class="header_customer" data-toggle="collapse"
						data-target="#depositDetails"
						style="border-bottom: 1px dotted #05007e; padding-top: 7px; padding-bottom: 7px; margin-bottom: 10px;">
						<h3>
							<b>Summary</b> <i class="fa fa-chevron-circle-down pull-right"
								aria-hidden="true"></i>
						</h3>
					</div>


					<div id="depositDetails" class="collapse in">
						<div class="col-md-6">
							<c:if test="${fixedDepositForm.accountNo!=''}">
								<div class="form-group">
									<label class="control-label col-sm-4" for=""><spring:message
											code="label.accountNo" /></label>
									<div class="col-sm-6">
										<form:input path="accountNo" class="myform-control"
											id="accountNo" readonly="true" />
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-sm-4" for=""><spring:message
											code="label.accountType" /></label>
									<div class="col-sm-6">
										<form:input path="accountType" class="myform-control"
											id="accountType" readonly="true" />
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-sm-4" for=""><spring:message
											code="label.accountBalance" /></label>
									<fmt:formatNumber value="${fixedDepositForm.accountBalance}"
										pattern="#.##" var="accBal" />
									<div class="col-sm-6">
										<form:input path="accountBalance" class="myform-control"
											id="accountBalance" value="${accBal}" readonly="true" />
									</div>
								</div>
							</c:if>

							<div class="form-group">
								<label class="control-label col-sm-4" for=""><spring:message
										code="label.selectFormat" /></label>
								<div class="col-sm-6">
									<form:input path="paymentType" class="myform-control"
										id="paymentType" readonly="true" />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-4" for=""><spring:message
										code="label.fdAmount" /></label>
								<fmt:formatNumber value="${fixedDepositForm.fdAmount}"
									pattern="#.##" var="depositAmt" />
								<div class="col-sm-6">
									<form:input path="fdAmount" class="myform-control"
										id="fdAmount" value="${depositAmt}" readonly="true" />
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-sm-4" for=""><spring:message
										code="label.currency" /></label>
								<div class="col-sm-6">
									<form:input path="currency" class="myform-control"
										readonly="true" />
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-sm-4" for="">Deposit
									Fixed Amount</label>
								<fmt:formatNumber value="${fixedDepositForm.fdFixed}"
									pattern="#.##" var="depositFixedAmt" />
								<div class="col-sm-6">
									<form:input path="fdFixed" class="myform-control" id="fdFixed"
										value="${depositFixedAmt}" readonly="true" />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-4" for="">Deposit
									Variable Amount</label>
								<fmt:formatNumber value="${fixedDepositForm.fdChangeable}"
									pattern="#.##" var="depositVariableAmt" />
								<div class="col-sm-6">
									<form:input path="fdChangeable" class="myform-control"
										id="fdChangeable" value="${depositVariableAmt}"
										readonly="true" />
								</div>
							</div>

						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label class="control-label col-sm-4" for=""><spring:message
										code="label.selectDate" /></label>
								<div class="col-sm-6">
									<form:input path="fdDeductDate" class="myform-control"
										id="fdCreateDate" readonly="true" />
								</div>
							</div>


							<div class="form-group">
								<label class="control-label col-sm-4" for="">Total Tenure Days</label>
								<div class="col-sm-6">
									<c:set var="tenure"
										value="${fixedDepositForm.totalTenureInDays} Days" />
									<input value="${tenure}" class="myform-control" readonly />
								</div>
							</div>
							<c:if test="${fixedDepositForm.tenureInYears!=null}">
							<div class="form-group">
								<label class="control-label col-sm-4" for="">Tenure Years</label>
								<div class="col-sm-6">
									
									<input value="${fixedDepositForm.tenureInYears}" class="myform-control" readonly />
								</div>
							</div>
							
							</c:if>
							<c:if test="${fixedDepositForm.tenureInMonths!=null}">
							<div class="form-group">
								<label class="control-label col-sm-4" for="">Tenure Months</label>
								<div class="col-sm-6">
									
									<input value="${fixedDepositForm.tenureInMonths}" class="myform-control" readonly />
								</div>
							</div>
							
							</c:if>
							<c:if test="${fixedDepositForm.tenureInDays!=null}">
							<div class="form-group">
								<label class="control-label col-sm-4" for="">Tenure Days</label>
								<div class="col-sm-6">
									
									<input value="${fixedDepositForm.tenureInDays}" class="myform-control" readonly />
								</div>
							</div>
							
							</c:if>
						
							<c:if test="${fixedDepositForm.daysValue!=null}">
							<div class="form-group">
								<label class="control-label col-sm-4" for=""><spring:message
										code="label.days" /></label>
								<div class="col-sm-6">
									
									<input value="${fixedDepositForm.daysValue}" class="myform-control" readonly />
								</div>
							</div>
							
							</c:if>
							
							<div class="form-group">
								<label class="control-label col-sm-4" for="">Rate Of
									Interest(%)</label>
								<div class="col-sm-6">
									<form:input path="fdCreditAmount" class="myform-control" id=""
										readonly="true" />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-4" for=""><spring:message
										code="label.estimateInterest" /></label>
								<fmt:formatNumber value="${fixedDepositForm.estimateInterest}"
									pattern="#.##" var="estimatedInterest" />
								<div class="col-sm-6">
									<form:input path="estimateInterest" class="myform-control"
										id="estimateInterest" value="${estimatedInterest}"
										readonly="true" />
								</div>
							</div>
							<%-- <div class="form-group">
							<label class="control-label col-sm-4" for=""><spring:message
									code="label.estimateTDSDeduct" /></label>
							<fmt:formatNumber value="${fixedDepositForm.estimateTDSDeduct}"
								pattern="#.##" var="estimatedTDS" />
							<div class="col-sm-6">
								<form:input path="estimateTDSDeduct" class="myform-control"
									id="estimateTDSDeduct" value="${estimatedTDS}" readonly="true" />
							</div>
						</div>
 --%>
							<div class="form-group">
								<label class="control-label col-sm-4" for=""><spring:message
										code="label.estimatedMaturityAmount" /></label>
								<fmt:formatNumber
									value="${fixedDepositForm.estimatePayOffAmount}" pattern="#.##"
									var="estimatedMaturityAmt" />
								<div class="col-sm-6">
									<form:input path="estimatePayOffAmount" class="myform-control"
										id="estimatePayOffAmount" value="${estimatedMaturityAmt}"
										readonly="true" />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-4" for=""><spring:message
										code="label.maturityDate" /></label>
								<div class="col-sm-6">
									<form:input path="fdTenureDate" class="myform-control"
										id="fdTenureDate" readonly="true" />
								</div>
							</div>
							<c:if test="${baseURL[0] == 'common' || baseURL[1]== 'common'}">
							 <div class="form-group" id= "branchDIV">
								<label  class="control-label col-sm-4" for="" ><spring:message
										code="label.branchCode" /></label>
								<div class="col-sm-6">
									<form:input  path="branchCode" class="myform-control"
										id="branchCode" readonly="true" />
								</div>
							</div> 
							</c:if>
							<div class="form-group" id= "areaDIV">
								<label class="control-label col-sm-4" for="">Deposit Area</label>
								<div class="col-sm-6">
									<form:input path="depositArea" class="myform-control"
										id="depositArea" readonly="true" />
								</div>
							</div>
						</div>
					</div>


					<div class="header_customer col-sm-12 col-md-12 col-lg-12"
						data-toggle="collapse" data-target="#nomineeDetails"
						style="border-bottom: 1px dotted #05007e; padding-top: 7px; padding-bottom: 7px; margin-bottom: 10px;">
						<h3>
							<b> <spring:message code="label.nominee" /></b> <i
								class="fa fa-chevron-circle-down pull-right" aria-hidden="true"></i>
						</h3>
					</div>

					<div id="nomineeDetails" class="collapse in">
						<div class="col-md-6" style="padding-top: 0;">
							<div class="form-group">
								<label class="control-label col-sm-4" for=""><spring:message
										code="label.nomineeName" /></label>
								<div class="col-sm-6">
									<form:input path="nomineeName" readonly="true" id="nomineeName"
										class="myform-control"></form:input>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-4" for=""><spring:message
										code="label.nomineeAge" /></label>
								<div class="col-sm-6">
									<form:input path="nomineeAge" type="number" readonly="true"
										id="nomineeAge" class="myform-control"></form:input>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-4" for=""><spring:message
										code="label.nomineeAddress" /></label>
								<div class="col-sm-6">
									<form:input path="nomineeAddress" readonly="true"
										id="nomineeAddress" class="myform-control"></form:input>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-4" for=""><spring:message
										code="label.nomineeRelationShip" /></label>
								<div class="col-sm-6">
									<form:input path="nomineeRelationShip" readonly="true"
										id="nomineeRelationShip" class="myform-control"></form:input>
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-sm-4" for="">Nominee Pan</label>
								<div class="col-sm-6">
									<form:input path="nomineePan" readonly="true" id="nomineePan"
										class="myform-control" style="text-transform:uppercase"></form:input>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-4" for="">Nominee
									Aadhar</label>
								<div class="col-sm-6">
									<form:input path="nomineeAadhar" readonly="true"
										id="nomineeAadhar" class="myform-control"></form:input>
								</div>
							</div>
						</div>
						<div class="col-md-6" style="margin-top: -21px;" id="gaurdianDiv">

							<div class="form-group">
								<label class="col-md-4 control-label"></label>
								<div class="col-md-6"></div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.guardianName" /></label>
								<div class="col-md-6">
									<form:input path="guardianName" class="myform-control"
										readonly="true" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.guardianAge" /></label>
								<div class="col-md-6">
									<form:input path="guardianAge" type="number"
										class="myform-control" readonly="true" />
									<!--min="18" max="100" required="true"  -->
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.guardianAddress" /></label>
								<div class="col-md-6">
									<form:input path="guardianAddress" class="myform-control"
										readonly="true" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.guardianRelationShip" /></label>
								<div class="col-md-6">
									<form:input path="guardianRelationShip" class="myform-control"
										readonly="true" />

								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label">Gaurdian Pan</label>
								<div class="col-md-6">
									<form:input path="gaurdianPan" class="myform-control"
										readonly="true" style="text-transform:uppercase" />

								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label">Gaurdian Aadhar</label>
								<div class="col-md-6">
									<form:input path="gaurdianAadhar" class="myform-control"
										readonly="true" />

								</div>
							</div>

						</div>

					</div>
					<!-- PayOFF section started -->

					<c:if test="${! empty fixedDepositForm.payOffInterestType}">

						<div class="header_customer col-sm-12 col-md-12 col-lg-12"
							data-toggle="collapse" data-target="#payOffDetails"
							style="border-bottom: 1px dotted #05007e; padding-top: 7px; padding-bottom: 7px; margin-bottom: 10px;">

							<h3>
								<b> <spring:message code="label.payOffDetails" /></b> <i
									class="fa fa-chevron-circle-down pull-right" aria-hidden="true"></i>

							</h3>
						</div>


						<div id="payOffDetails" class="collapse in">
							<div class="col-md-6">

								<div class="form-group" id="everyMonthInt">
									<label class="control-label col-sm-4" for=""><spring:message
											code="label.payOffTenure" /></label>
									<div class="col-sm-6">

										<form:input path="payOffInterestType" class="myform-control"
											readonly="true" />

									</div>
								</div>
								<c:if test="${fixedDepositForm.payoffDate!=''}">
									<div class="form-group" id="payoffDate">
										<label class="control-label col-sm-4" for="">Payoff
											Date</label>
										<div class="col-sm-6">

											<form:input path="payoffDate" class="myform-control"
												readonly="true" />

										</div>
									</div>
								</c:if>





								<c:if test="${fixedDepositForm.interstPayType=='PERCENT'}">
									<div class="form-group" id="interestPercentTr">
										<label class="control-label col-sm-4" for="">Interest
											Percentage</label>
										<div class="col-sm-6">
											<form:input path="interestPercent" class="myform-control"
												readonly="true" id="interestPercent" />
										</div>


									</div>
								</c:if>
								<c:if test="${fixedDepositForm.interstPayType=='PART'}">
									<div class="form-group" id="interestAmtTr">
										<label class="control-label col-sm-4" for=""><spring:message
												code="label.amount" /></label>
										<div class="col-sm-6">
											<form:input path="interestPayAmount" readonly="true"
												class="myform-control" id="interestPayAmount" />
										</div>


									</div>
								</c:if>

								<div class="form-group">
									<label class="control-label col-sm-4" for=""><spring:message
											code="label.payOffAccount1" /></label>
									<div class="col-sm-6">

										<form:input path="fdPayOffAccount" class="myform-control"
											readonly="true" />


									</div>

								</div>


							</div>
							<div class="col-md-6">
								<c:if
									test="${fixedDepositForm.fdPayOffAccount!='Saving Account'}">
									<div class="form-group" id="transferModeTr">
										<label class="control-label col-sm-4" for=""><spring:message
												code="label.transfer" /></label>
										<div class="col-sm-6">

											<form:input path="otherPayTransfer" class="myform-control"
												readonly="true" />

										</div>
									</div>
								</c:if>
								<div class="form-group" id="beneficiaryNameTr">
									<label class="control-label col-sm-4" for="">Beneficiary
										Name </label>
									<div class="col-sm-6">
										<form:input path="otherName" class="myform-control"
											id="beneficiaryName" />
									</div>
								</div>
								<div class="form-group" id="accountNoTr">
									<label class="control-label col-sm-4" for="">Account
										Number </label>
									<div class="col-sm-6">
										<form:input path="otherAccount" class="myform-control"
											readonly="true" id="beneficiaryAccount" />
									</div>
								</div>

								<c:if test="${fixedDepositForm.fdPayOffAccount=='Other'}">

									<div class="form-group" id="bankNameTr">
										<label class="control-label col-sm-4" for="">Bank Name</label>
										<div class="col-sm-6">
											<form:input path="otherBank" class="myform-control"
												id="beneficiaryBank" readonly="true" />
										</div>
									</div>
									<div class="form-group" id="ifscTr">
										<label class="control-label col-sm-4" for="">IFSC</label>
										<div class="col-sm-6">
											<form:input path="otherIFSC" class="myform-control"
												readonly="true" id="beneficiaryIfsc" />
										</div>

									</div>
								</c:if>
							</div>
						</div>
					</c:if>
<c:if test="${ fixedDepositForm.isMaturityDisbrsmntInLinkedAccount=='0'}">
					

						<div class="header_customer col-sm-12 col-md-12 col-lg-12"
							data-toggle="collapse" data-target="maturityDetails"
							style="border-bottom: 1px dotted #05007e; padding-top: 7px; padding-bottom: 7px; margin-bottom: 10px;">
<h3>
								<b> Funds Transfer on Maturity<i
									class="fa fa-chevron-circle-down pull-right" aria-hidden="true"></i>

							</h3>
							
						</div>
						<div id="maturityDetails" class="collapse in col-md-6">
						<div>
								
								<form:hidden path="isMaturityDisbrsmntInSameBank"/>
								<form:hidden path="isMaturityDisbrsmntInLinkedAccount"/>
								<form:hidden path="maturityDisbrsmntTransferType"/>
								<div class="form-group" id="beneficiaryNameMaturity">
									<label class="control-label col-sm-4" for="">Beneficiary
										Name </label>
									<div class="col-sm-6">
										<form:input path="maturityDisbrsmntAccHolderName" class="myform-control"
											id="beneficiaryNameMaturity" />
									</div>
								</div>
								<div class="form-group" id="accountNoTr">
									<label class="control-label col-sm-4" for="">Account
										Number </label>
									<div class="col-sm-6">
										<form:input path="maturityDisbrsmntAccNumber" class="myform-control"
											readonly="true" id="beneficiaryAccountMr" />
									</div>
								</div>

								<c:if test="${fixedDepositForm.isMaturityDisbrsmntInSameBank=='0'}">

									<div class="form-group" id="bankNameTr">
										<label class="control-label col-sm-4" for="">Bank Name</label>
										<div class="col-sm-6">
											<form:input path="maturityDisbrsmntBankName" class="myform-control"
												id="beneficiaryBankMr" readonly="true" />
										</div>
									</div>
									<div class="form-group" id="ifscTr">
										<label class="control-label col-sm-4" for="">IFSC</label>
										<div class="col-sm-6">
											<form:input path="maturityDisbrsmntBankIFSCCode" class="myform-control"
												readonly="true" id="beneficiaryIfscMr" />
										</div>

									</div>
								</c:if>
							</div>
							</div>
						</c:if>


					<div class="header_customer col-sm-12 col-md-12 col-lg-12"
						data-toggle="collapse" data-target="#paymentDetails"
						style="border-bottom: 1px dotted #05007e; padding-top: 7px; padding-bottom: 7px; margin-bottom: 10px;">

						<h3>
							<b> <spring:message code="label.payments" /> Details
							</b> <i class="fa fa-chevron-circle-down pull-right"
								aria-hidden="true"></i>
						</h3>
					</div>

					<div id="paymentDetails" class="collapse in col-md-6">
						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.modeOfPay" /> </label>
							<div class="col-md-6">

								<form:hidden id="paymentMode" path="depositForm.paymentMode"
									class="myform-control" readonly="true" />
									
								 <input value="${paymentMode}"
									class="myform-control" readonly="true" /> 


							</div>
						</div>


						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.fdAmount" /></label>
							<fmt:formatNumber value="${depositAmount}" pattern="#.##"
								var="depositAmount" />
							<div class="col-md-6">
								<form:input path="depositForm.fdPay" class="myform-control"
									id="paymentAmt" readonly="true" />
							</div>
						</div>
							<span id='validationError' style="color: red"></span>
						<c:if
							test="${paymentMode=='Fund Transfer'}">

							<div class="form-group" id="linkedAccountTr">
								<label class="col-md-4 control-label">Account Number</label>
								<div class="col-md-6">
									<form:input path="depositForm.linkedAccountNo"
										class="myform-control" id="linkedAccount" readonly="true" />
								</div>
							</div>
							<div class="form-group" id="linkedAccountBalanceTr">
								<label class="col-md-4 control-label">Account Balance</label>
								<fmt:formatNumber
									value="${fixedDepositForm.depositForm.accountBalance}"
									pattern="#.##" var="accBal" />
								<div class="col-md-6">
									<form:input path="depositForm.accountBalance"
										class="myform-control" id="linkedAccountBalance"
										value="${accBal}" readonly="true" />
								</div>
							</div>
						</c:if>

						<c:if
							test="${paymentMode=='Cheque' || paymentMode=='DD'}">
							<div id="bankDetailsTr">
								<div class="form-group" id="chequeNoTr">
									<label class="col-md-4 control-label"> <c:if
											test="${paymentMode=='Cheque'}">Cheque Number</c:if>
										<c:if test="${paymentMode=='DD'}">DD Number</c:if></label>
									<div class="col-md-6">
										<form:input path="depositForm.chequeNo" class="myform-control"
											id="chequeNo" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label"> <c:if
											test="${paymentMode=='Cheque'}">Cheque Date</c:if>
										<c:if test="${paymentMode=='DD'}">DD Date</c:if></label>
									<div class="col-md-6">
										<form:input path="depositForm.chequeDate"
											value="${todaysDate}" readonly="readonly"
											class="myform-control"
											id="chequeDate" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label"><spring:message
											code="label.bank" /></label>
									<div class="col-md-6">
										<form:input path="depositForm.chequeBank"
											class="myform-control" id="chequeBank" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label"><spring:message
											code="label.branch" /></label>
									<div class="col-md-6">
										<form:input path="depositForm.chequeBranch"
											class="myform-control" id="chequeBranch" />
									</div>
								</div>

							</div>
						</c:if>
						<c:if test="${paymentMode=='Debit Card'}">
							<div id="cardDetailsTr">
								<%-- <div class="form-group">
									<label class="col-md-4 control-label">Select Card</label>
									<div class="col-md-6">

										<form:input path="depositForm.paymentMode" class="myform-control"
											readonly="true" />


									</div>
								</div> --%>
								<div class="form-group">
									<label class="col-md-4 control-label">Enter Card No</label>
									<div class="col-md-6">
										<form:input path="depositForm.cardNo"
											placeholder="xxxx-xxxx-xxxx-xxxx" id="cardNo"
											class="myform-control d" filtertype="CCNo" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">Expiry Date</label>
									<div class="col-md-2">
										<form:input path="depositForm.expiryDate"
											class="myform-control" readonly="true" style="width: 78px;" />




									</div>


									<div class="col-md-2">
										<form:hidden path="depositForm.cvv" />
									</div>
								</div>

							</div>
						</c:if>
						<c:if test="${paymentMode=='Credit Card'}">
							<div id="cardDetailsTr">
							<%-- <div class="form-group">
									<label class="col-md-4 control-label">Select Card</label>
									<div class="col-md-6">

										<form:input path="depositForm.paymentMode" class="myform-control"
											readonly="true" />


									</div>
								</div>  --%>
								<div class="form-group">
									<label class="col-md-4 control-label">Enter Card No</label>
									<div class="col-md-6">
										<form:input path="depositForm.cardNo"
											placeholder="xxxx-xxxx-xxxx-xxxx" id="cardNo"
											class="myform-control d" filtertype="CCNo" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">Expiry Date</label>
									<div class="col-md-2">
										<form:input path="depositForm.expiryDate"
											class="myform-control" readonly="true" style="width: 78px;" />




									</div>


									<div class="col-md-2">
										<form:hidden path="depositForm.cvv" />
									</div>
								</div>

							</div>
						</c:if>
						<c:if
							test="${paymentMode=='Net Banking'}">
							<div id="netBankingDetailsTr">


								<div class="form-group">
									<label class="col-md-4 control-label">Bank type</label>
									<div class="col-md-6">

										<form:input path="fdPayType" class="myform-control"
											readonly="true" />


									</div>
								</div>
								<c:if test="${fixedDepositForm.fdPayType!='SameBank'}">
									<div class="form-group">
										<label class="col-md-4 control-label">Transfer type</label>
										<div class="col-md-6">

											<form:input path="otherPayTransfer1" class="myform-control"
												readonly="true" />

										</div>
									</div>
								</c:if>

								<div class="form-group" id="benificiaryName">
									<label class="control-label col-sm-4" for="">Beneficiary
										Name </label>

									<div class="col-sm-6">
										<form:input path="otherName1" class="myform-control"
											readonly="true" id="beneficiaryName" />
									</div>

								</div>


								<div class="form-group" id="accNumber">
									<label class="control-label col-sm-4" for="">Account
										Number </label>

									<div class="col-sm-6">
										<form:input path="otherAccount1" class="myform-control"
											id="beneficiaryAccount" />
									</div>

								</div>
								<c:if test="${fixedDepositForm.fdPayType!='SameBank'}">
									<div class="form-group" id="bankNameTr">
										<label class="control-label col-sm-4" for="">Bank Name</label>

										<div class="col-sm-6">
											<form:input path="otherBank1" class="myform-control"
												id="beneficiaryBank" />
										</div>

									</div>

									<div class="form-group" id="ifscId">
										<label class="control-label col-sm-4" for="">IFSC</label>

										<div class="col-sm-6">
											<form:input path="otherIFSC1" class="myform-control"
												id="beneficiaryIfsc" />
										</div>

									</div>
								</c:if>

							</div>
						</c:if>
					</div>
					
					<div class="form-group">

						<c:if test="${urlvalue} == 'common'}">
							<c:set var="back" value="applyOnlineFD?depositType=single" />
						</c:if>
						<c:if test="${urlvalue} == 'users'}">
							<c:set var="back" value="newDeposit" />
						</c:if>
					
						<div class="col-sm-offset-2 col-sm-10"
							style="position: relative; left: 10px; margin-top: 43px;">
							<input type="button" class="btn btn-warning" onclick="goBack()"
								value="<spring:message code="label.back"/>"
								style="margin-left: 44px;"> <input type="submit"
								class="btn btn-primary" value="confirm"  onclick="return val()"
								style="margin-left: 2px;">


						</div>
					</div>
				</form:form>

			</div>
		</div>
	</div>


<style>
.myform-control {
	background: #B5B5B3;
	color: #1B1B1B;
	cursor: no-drop;
}

.form-horizontal .control-label {
	padding-top: 8px;
}
</style>
	<script>
		$(document).ready(function() {
			
			if('${role.role}' == 'ROLE_USER'){
				   $("#branchDIV").hide();
				   $("#areaDIV").hide();
				   
			   }
			
			
			
			
			document.getElementById("branchCode").className = 'hidden';
			
			if (parseInt($('#nomineeAge').val()) < 18) {
				document.getElementById('gaurdianDiv').style.display = 'block';
			} else {
				document.getElementById('gaurdianDiv').style.display = 'none';

			}

		});
		/* function val() {

			var canSubmit = true;
			//var panJointVal = '${getcustomerByPanCard}';
			//var aadharJointVal = '${getcustomerByAadharCard}';
			var validationError = document.getElementById('validationError');
			validationError.innerHTML = "";
			var errorMsg = "";

			if (panJointVal > 0) {
				errorMsg = " <br>PAN card is already register with another user name. Please check the PAN card number.";
				validationError.innerHTML += errorMsg;
				return false;

			}

			if (aadharJointVal > 0) {
				errorMsg = " <br>Aadhar card is already register with another user name. Please check the Aadhar card number.";
				validationError.innerHTML += errorMsg;
				return false;

			}

		} */
		function goBack(){

			/* if ('${urlvalue}' == 'bnkEmp') { */
				$("#confirmForm").attr("action", "createSingleDeposit");
				$("#confirmForm").submit();
			/* }
			if ('${urlvalue}' == 'users') {
				$("#confirmForm").attr("action", "newDeposit");
				$("#confirmForm").submit();
			} */

		}
	</script>
	