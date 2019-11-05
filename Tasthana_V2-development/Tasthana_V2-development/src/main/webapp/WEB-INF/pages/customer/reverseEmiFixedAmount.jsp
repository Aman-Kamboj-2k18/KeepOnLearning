
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- To fetch the request url -->
<c:set var="req"
	value="${requestScope['javax.servlet.forward.request_uri']}" />
<c:set var="baseURL" value="${fn:split(req,'/')}" />
<link href="<%=request.getContextPath()%>/resources/css/Validation.css"
	rel="stylesheet">
<script
	src="<%=request.getContextPath()%>/resources/js/ccvalidate-tapan.min.js"></script>

<div class="right-container" id="right-container">
	<div class="container-fluid">
		<div class="Flexi_deposit">
			<div class="header_customer">
				<h3>Reverse EMI</h3>
			</div>




			<div class="header_customer" data-toggle="collapse"
				data-target="#depositDetails"
				style="border-bottom: 1px dotted #05007e; border-top: 1px dotted #05007e; padding-top: 7px; padding-bottom: 7px;">
				<h3>
					<b>Deposit Details</b> <i
						class="fa fa-chevron-circle-down pull-right" aria-hidden="true"></i>
				</h3>
				<span id="depositOk"
					style="display: none; color: #14c514; margin-right: 25px; margin-top: -12px; float: right;"
					class="glyphicon glyphicon-ok"></span> <span id="depositCross"
					style="display: none; color: red; margin-right: 25px; margin-top: -12px; float: right;"
					class="glyphicon glyphicon-remove"></span>
			</div>
			<div class="Success_msg">
				<div class="successMsg" style="text-align: center; color: red;">
					${error}</div>
			</div>
			<div class="flexi_table" style="margin-top: 7px;">
				<form:form action="confirmReverseEmiFixedAmount"
					class="form-horizontal" autocomplete="off"
					commandName="fixedDepositForm" name="fixedDeposit">

					<form:hidden path="id" />
					<form:hidden path="fdCreditAmount" />

					<form:hidden path="customerName"
						value="${model.customer.customerName}" />

					<form:hidden path="productConfigurationId"
						value="${fixedDepositForm.productConfigurationId}" />
					<form:hidden path="contactNum" value="${model.customer.contactNum}" />
					<form:hidden path="email" value="${model.customer.email}" />
					<form:hidden path="category" value="${model.customer.category}" />
					<form:hidden path="interstPayType" value="PART" />
					<form:hidden path="status" value="Reverse EMI" />
					<form:hidden path="paymentType" value="Once" />
					<form:hidden path="fixedAmountEmi" />
					<form:hidden path="customerId" id="customerId" />

					<form:hidden path="depositId" id="depositId" />
					<form:hidden path="depositHolderId" id="depositHolderId" />

					<div id="depositDetails" class="collapse in">
						<div class="col-md-6" style="padding-top: 7px;">

							<div id="linkedAccountDiv">
								<div class="form-group">
									<label class="col-md-4 control-label">Current/<spring:message
											code="label.savingAccountNo" /></label>
									<div class="col-md-6">
										<form:select path="accountNo" class="form-control"
											id="accountNo" onchange="onChangeAccountNo(this.value)">
											<form:option value="">Select</form:option>
											<c:forEach items="${fixedDepositForm.accountList}"
												var="account">
												<fmt:formatNumber value="${account.accountBalance}"
													pattern="#.##" var="accBal1" />
												<option
													value="${account.accountNo},${account.accountType},${accBal1}"
													<%-- <c:if test="${account.accountType =='Savings'}" > --%>selected="selected"<%-- </c:if> --%>>${account.accountNo}
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
					document.getElementById('accountBalance').value = parseFloat(account[2]).toLocaleString("en-US");
 					document.getElementById('accountBalance1').value = account[2];
 					
					
					document.getElementById('accountTypeDiv').style.display='block';
					document.getElementById('accountBalanceDiv').style.display='block';
					document.getElementById('fundTransferLinkedAccount').style.display='block';
					
					if(document.getElementById('paymentMode').value=='Fund Transfer'){
						var accArray= $('#accountNo').val().split(',');
						document.getElementById('linkedAccount').value=accArray[0];
						
						document.getElementById('linkedAccountBalance1').value = $('#accountBalance1').val();
						document.getElementById('linkedAccountBalance').value = parseFloat($('#accountBalance1').val()).toLocaleString("en-US");
						
						
					}
					
					
					
					}
					else{
						document.getElementById('accountTypeDiv').style.display='none';
						document.getElementById('accountBalanceDiv').style.display='none';
						
						if(document.getElementById('paymentMode').value=='Fund Transfer'){
							document.getElementById('paymentMode').value="Select";
						}
						document.getElementById('linkedAccountBalanceTr').style.display = 'none';
						document.getElementById('linkedAccountTr').style.display = 'none';
						
						document.getElementById('fundTransferLinkedAccount').style.display='none';
						
					}
				}
				
				
			</script>
								<c:if test="${! empty fixedDepositForm.accountList}">

									<div class="form-group" id="accountTypeDiv">
										<label class="col-md-4 control-label"><spring:message
												code="label.accountType" /></label>
										<div class="col-md-6">

											<form:input path="accountType" class="form-control"
												value="Savings" id="accountType" readonly="true" />

										</div>

									</div>
									<div class="form-group" id="accountBalanceDiv">
										<label class="col-md-4 control-label"><spring:message
												code="label.accountBalance" /></label>


										<div class="col-md-6">

											<form:hidden path="accountBalance" id="accountBalance1" />
											<input class="form-control" id="accountBalance" readonly />
										</div>

									</div>
								</c:if>

							</div>



							<c:if test="${fixedDepositForm.citizen=='NRI'}">

								<div class="form-group" id="everyMonthInt">
									<label class="col-md-4 control-label" for=""
										style="padding-top: 4px;"><spring:message
											code="label.nriAccountType" /><span style="color: red">*</span></label>
									<div class="col-md-8">

										<span id="label7"><label for="radio"> <form:radiobutton
													path="nriAccountType" id="nre" name="NRE" value="NRE"
													onclick="nriAccType(this.value)" checked="checked"></form:radiobutton></label>
											<spring:message code="label.nre" /></span> <span id="label6"><label
											for="radio"> <form:radiobutton path="nriAccountType"
													id="nro" name="nro" value="NRO"
													onclick="nriAccType(this.value)"></form:radiobutton></label> <spring:message
												code="label.nro" /></span> <span id="label8"><label
											for="radio"> <form:radiobutton path="nriAccountType"
													id="fcnr" name="fcnr" value="FCNR"
													onclick="nriAccType(this.value)"></form:radiobutton></label> <spring:message
												code="label.fcnr" /></span> <span id="label9"><label
											for="radio"> <form:radiobutton path="nriAccountType"
													id="rfc" name="rfc" value="RFC"
													onclick="nriAccType(this.value)"></form:radiobutton></label> <spring:message
												code="label.rfc" /></span> <span id="label9"><label
											for="radio"> <form:radiobutton path="nriAccountType"
													id="prp" name="prp" value="PRP"
													onclick="nriAccType(this.value)"></form:radiobutton></label> <spring:message
												code="label.prp" /></span>

									</div>

								</div>
							</c:if>
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.currency" /><span style="color: red">*</span></label>

								<div class="col-md-6">
									<form:select id="currency" path="currency"
										class="input form-control">
										<form:option value="Select"></form:option>
									</form:select>

									<script>

									if('${currency}'!=""){
										if('${fixedDepositForm.nriAccountType}' == 'FCNR' || '${fixedDepositForm.nriAccountType}' == 'PRP')
											populateCurrencyEditFCNR("currency","${currency}");
										else if('${fixedDepositForm.nriAccountType}' == 'RFC')
											populateCurrencyEditRFC("currency","${currency}");
										else
											populateCurrencyEdit("currency","${currency}");
									}
									else{
										if('${fixedDepositForm.nriAccountType}' == 'FCNR' || '${fixedDepositForm.nriAccountType}' == 'PRP')
											populateCurrencyFCNR("currency");
										else if('${fixedDepositForm.nriAccountType}' == 'RFC')
											populateCurrencyRFC("currency");
										else
											populateCurrency("currency");
									}
									</script>
									<span id="currencyError" style="display: none; color: red"><spring:message
											code="label.selectCurrency" /></span>

								</div>
							</div>


							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.fdAmount" /><span style="color: red">*</span></label>
								<div class="col-md-6">
									<fmt:formatNumber value="${fixedDepositForm.fdAmount}"
										pattern="#.##" var="fdAmount" />
									<form:input path="fdAmount" step="any" type="text"
										class="form-control"
										onkeypress="return isNumberKey1(event,$(this))" id="fdAmount"
										value="${fdAmount}"
										onkeyup="onchangeDepositAmount(this.value)" />



								</div>

							</div>

							<div class="form-group">
								<label class="col-md-4 control-label">Area<span
									style="color: red">*</span></label>
								<div class="col-md-6">

									<form:input path="depositArea" type="text"
										class="input form-control" id="depositArea" required="true"
										placeholder="Enter Area" />


								</div>
							</div>

						</div>
						<div class="col-md-6" style="padding-top: 7px;">

							<div class="form-group">
								<label class="col-md-6 control-label"
									style="margin-left: -90px;"><spring:message
										code="label.gestationPeriod" /> (In Year)</label>
								<div class="col-md-6">
									<form:input path="gestationPeriod" type="number"
										class="input form-control" id="gestationPeriod"
										onkeypress="validationAccount(event)" />

								</div>

							</div>

							<div class="form-group">
								<label class="col-md-4 control-label">EMI/month<span
									style="color: red">*</span></label>
								<div class="col-md-6">
									<%-- <fmt:formatNumber value="${fixedDepositForm.interestPayAmount}"
										pattern="#.##" var="fdAmount" /> --%>
									<form:input path="emiAmount" type="number" step="any"
										class="form-control"
										onkeyup="emiAmt();chechDepositeAmountAndEMIAmount(event,this.value)"
										id="interestPayAmount" onkeypress="validationAccount(event)" />
									<%--  value="${interestPayAmount}"  --%>

								</div>

							</div>


							<div class="form-group col-md-10"
								style="text-align: center; margin-top: 3px; margin-left: 50px;">
								<input type="button" onclick="getEMITenure()"
									class="btn btn-info" value="Click to check tenure">
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label">Tenure<span
									style="color: red">*</span></label>
								<div class="col-md-3">
									<form:input path="emiTenure" class="input form-control"
										id="emiTenure" readonly="true" />


								</div>

							</div>

							<div class="form-group" id="everyMonthInt">
								<label class="col-md-4 control-label" for=""
									style="margin-top: 10px; margin-left: -85px;"><spring:message
										code="label.payOffTenure" /></label>
								<div class="col-sm-8"
									style="margin-left: -15px; margin-right: -152px; padding-right: 0;">
									<span id="label0"><label for="radio"> <form:radiobutton
												path="payOffInterestType" id="fdPayOffType1"
												name="fdPayOffType" value="monthly" checked="true"></form:radiobutton></label>
										<spring:message code="label.fdPayOffType1" /></span> <span
										id="label0"><label for="radio"> <form:radiobutton
												path="payOffInterestType" id="fdPayOffType1"
												name="fdPayOffType" value="quarterly"></form:radiobutton></label> <spring:message
											code="label.fdPayOffType3" /></span> <span id="label0"><label
										for="radio"> <form:radiobutton
												path="payOffInterestType" id="fdPayOffType1"
												name="fdPayOffType" value="halfYearly"></form:radiobutton></label> <spring:message
											code="label.fdPayOffType4" /></span> <span id="label0"><label
										for="radio"> <form:radiobutton
												path="payOffInterestType" id="fdPayOffType1"
												name="fdPayOffType" value="annually"></form:radiobutton></label> <spring:message
											code="label.fdPayOffType5" /></span>

								</div>

							</div>

						</div>

						<input type="button" onclick="resetBasicDetails()"
							class="btn btn-warning pull-right" value="Reset"
							style="margin-top: -5px; margin-right: 9px; margin-botton: 10px;" />
					</div>


					<div class="header_customer col-sm-12 col-md-12 col-lg-12"
						data-toggle="collapse" data-target="#nomineeDetails"
						style="border-bottom: 1px dotted #05007e; margin-bottom: 10px; padding-top: 7px; padding-bottom: 7px;">
						<h3>
							<b><spring:message code="label.nominee" /></b> <i
								class="fa fa-chevron-circle-down pull-right" aria-hidden="true"></i>
						</h3>
						<span id="nomineeOk"
							style="display: none; color: #14c514; margin-right: 25px; margin-top: -12px; float: right;"
							class="glyphicon glyphicon-ok"></span><span id="nomineeCross"
							style="display: none; color: red; margin-right: 25px; margin-top: -12px; float: right;"
							class="glyphicon glyphicon-remove"></span>
					</div>

					<div id="nomineeDetails" class="collapse">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tbody>
								<tr>
									<td align="left" valign="top" width="50%">
										<div class="col-md-12" style="padding-top: 1px;">
											<div class="form-group">
												<label class="control-label col-sm-4" for=""><spring:message
														code="label.nomineeName" /><span style="color: red">*</span></label>
												<div class="col-sm-6">
													<form:input path="nomineeName"
														placeholder="Enter Nominee Name" id="nomineeName"
														class="form-control"
														onkeypress="validAlphabetsAndNumbers(event)"></form:input>
												</div>
											</div>
											<div class="form-group">
												<label class="control-label col-sm-4" for=""><spring:message
														code="label.nomineeAge" /><span style="color: red">*</span></label>
												<div class="col-sm-6">
													<form:input path="nomineeAge" type="number"
														placeholder="Enter Nominee Age"
														onkeypress="validationAccount(event)" id="nomineeAge"
														min="1" max="100" class="form-control"
														onkeyup="showGuardian(this.value,'gaurdianDiv','major','minor')"></form:input>
												</div>
											</div>
											<div class="form-group">
												<label class="control-label col-sm-4" for=""><spring:message
														code="label.nomineeAddress" /><span style="color: red">*</span></label>
												<div class="col-sm-6">
													<form:input path="nomineeAddress"
														placeholder="Enter Nominee Address" id="nomineeAddress"
														class="form-control"></form:input>
												</div>
											</div>
											<div class="form-group">
												<label class="control-label col-sm-4" for=""><spring:message
														code="label.nomineeRelationShip" /><span
													style="color: red">*</span></label>
												<div class="col-sm-6">
													<form:input path="nomineeRelationShip"
														placeholder="Enter Nominee Relationship"
														id="nomineeRelationShip" onkeypress="validName(event)"
														class="form-control" pattern="[a-zA-Z ]+"></form:input>
												</div>
											</div>

											<div class="form-group" id="nomineDiv">
												<label class="col-md-4 control-label">Nominee Pan</span>
												</label>
												<div class="col-sm-6">
													<form:input path="nomineePan" placeholder="Enter PAN"
														id="nomineePan" class="input form-control"
														style="text-transform:uppercase"></form:input>
													<span id="contactNum2Error" class="error"
														style="display: none;"><font color="red"><spring:message
																code="label.incorrectFormat" /></font></span>
												</div>
											</div>
											<div class="successMsg">
												<b><font color="error">${errorPan}</font></b>
											</div>
											<div class="form-group">
												<label class="col-md-4 control-label">Nominee Aadhar</label>
												<div class="col-md-6">
													<form:input path="nomineeAadhar" placeholder="Enter Aadhar"
														type="number" id="nomineeAadhar"
														class="input form-control"></form:input>
													<span id="contactNum2Error" class="error"
														style="display: none;"><font color="red"><spring:message
																code="label.incorrectFormat" /></font></span>
												</div>
											</div>

										</div>
									</td>
									<td align="left" valign="bottom" width="50%"><div
											class="col-md-12" style="margin-top: -14px;">

											<div id="gaurdianDiv">

												<div class="form-group">
													<label class="col-md-4 control-label"></label>
													<div class="col-md-6"></div>
												</div>
												<div class="form-group">
													<label class="col-md-4 control-label"><spring:message
															code="label.guardianName" /><span style="color: red">*</span></label>
													<div class="col-md-6">
														<form:input path="guardianName" class="form-control"
															id="guardianName" onkeypress="validName(event)" />
													</div>
												</div>
												<div class="form-group">
													<label class="col-md-4 control-label"><spring:message
															code="label.guardianAge" /><span style="color: red">*</span></label>
													<div class="col-md-6">
														<form:input path="guardianAge" type="number"
															class="form-control" id="guardianAge"
															onkeypress="validationAccount(event)" />

													</div>
												</div>
												<div class="form-group">
													<label class="col-md-4 control-label"><spring:message
															code="label.guardianAddress" /><span style="color: red">*</span></label>
													<div class="col-md-6">
														<form:input path="guardianAddress" class="form-control"
															id="guardianAddress" />
													</div>
												</div>
												<div class="form-group">
													<label class="col-md-4 control-label"><spring:message
															code="label.guardianRelationShip" /><span
														style="color: red">*</span></label>
													<div class="col-md-6">
														<form:input path="guardianRelationShip"
															class="form-control" id="guardianRelationShip"
															onkeypress="validName(event)" />

													</div>
												</div>

												<div class="form-group">
													<label class="col-md-4 control-label">Guardian Pan</label>
													<div class="col-md-6">
														<form:input path="gaurdianPan" placeholder="Enter PAN"
															id="gaurdianPan" class="input form-control"
															style="text-transform:uppercase"></form:input>
														<span id="contactNum2Error" class="error"
															style="display: none;"><font color="red"><spring:message
																	code="label.incorrectFormat" /></font></span>
													</div>
												</div>

												<div class="form-group">
													<label class="col-md-4 control-label">Guardian
														Aadhar
													</label>
													<div class="col-md-6">
														<form:input path="gaurdianAadhar"
															placeholder="Enter Aadhar" type="number"
															id="gaurdianAadhar" class="input form-control"></form:input>
														<span id="contactNum2Error" class="error"
															style="display: none;"><font color="red"><spring:message
																	code="label.incorrectFormat" /></font></span>
													</div>
												</div>
												<span id="guardianEmptyError"
													style="display: none; color: red">Please fill all
													guardian details </span> <span id="guardianAgeError"
													style="display: none; color: red">Guardian age
													should be between 18 and 100 </span> <span
													id="guardianRelationShipError"
													style="display: none; color: red">Special characters
													and numbers are not allowed </span>

											</div>
										</div>
										<div style="clear: both;"></div> <input type="button"
										style="margin-top: -5px; margin-right: 9px; margin-bottom: 15px"
										onclick="resetNomineeDetails()"
										class="btn btn-warning pull-right" value="Reset" /></td>
								</tr>
							</tbody>
						</table>


					</div>



					<div class="header_customer col-sm-12 col-md-12 col-lg-12"
						data-toggle="collapse" data-target="#beneficiaryDetails"
						style="border-bottom: 1px dotted #05007e; margin-bottom: 10px; padding-top: 7px; padding-bottom: 7px;">
						<h3>
							<b>Beneficiary Details</b> <i
								class="fa fa-chevron-circle-down pull-right" aria-hidden="true"></i>
						</h3>
						<span id="beneficiaryOk"
							style="display: none; color: #14c514; margin-right: 25px; margin-top: -12px; float: right;"
							class="glyphicon glyphicon-ok"></span><span id="beneficiaryCross"
							style="display: none; color: red; margin-right: 25px; margin-top: -12px; float: right;"
							class="glyphicon glyphicon-remove"></span>
					</div>
					<span id="beneficiaryAmountError" style="display: none; color: red">EMI
						Amount is less than beneficiary amount </span>
					<div id="beneficiaryDetails" class="collapse">

						<div id="payOffDetails">

							<div class="form-group" id="everyMonthInt"></div>

						</div>

						<div id="items" class="col-sm-12">
							<div class="col-md-12" style="padding-top: 1px;">


								<div class="successMsg">
									<b><font color="error">${error}</font></b>
								</div>
								<div class="add-btn">
									<input type="button" id="add" class="btn btn-success"
										value="Add Beneficiary">
								</div>

							</div>
						</div>

						<input type="button" onclick="resetBeneficiary()"
							style="margin-bottom: 9px; margin-right: 9px;"
							class="btn btn-warning pull-right" value="Reset" />
					</div>


					<div class="header_customer col-sm-12 col-md-12 col-lg-12"
						data-toggle="collapse" data-target="#paymentDetails"
						style="border-bottom: 1px dotted #05007e; margin-bottom: 10px; padding-top: 7px; padding-bottom: 7px;">
						<h3>
							<b><spring:message code="label.payments" /></b> <i
								class="fa fa-chevron-circle-down pull-right" aria-hidden="true"></i>
						</h3>
						<span id="paymentOk"
							style="display: none; color: #14c514; margin-right: 25px; margin-top: -12px; float: right;"
							class="glyphicon glyphicon-ok"></span><span id="paymentCross"
							style="display: none; color: red; margin-right: 25px; margin-top: -12px; float: right;"
							class="glyphicon glyphicon-remove"></span>
					</div>
					<div id="paymentDetails" class="collapse">
						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.modeOfPay" /> </label>
							<div class="col-md-6">
								<form:select id="paymentMode" path="depositForm.paymentMode"
									class="form-control" onchange="showDetails(this.value)">
									<form:option value="Select">Select</form:option>

									<form:option value="Fund Transfer"
										id="fundTransferLinkedAccount">
										Fund Transfer From Linked Account
								</form:option>

									<c:if test="${cashPayment eq 1}">
										<form:option value="Cash">
											<spring:message code="label.cashPayment" />
										</form:option>
									</c:if>
									<c:if test="${ddPayment eq 1}">
										<form:option value="DD">
											<spring:message code="label.ddPayment" />
										</form:option>
									</c:if>
									<c:if test="${chequePayment eq 1}">
										<form:option value="Cheque">
											<spring:message code="label.chequePayment" />
										</form:option>
									</c:if>
									<form:option value="Card Payment">
							          	Card Payment
							        </form:option>
									<c:if test="${netBanking eq 1}">
										<form:option value="Net Banking">
								Net Banking
							</form:option>
									</c:if>

								</form:select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.fdAmount" /><span style="color: red">*</span></label>
							<fmt:formatNumber value="${depositAmount}" pattern="#.##"
								var="depositAmount" />
							<div class="col-md-6">
								<form:input path="depositForm.fdPay" class="form-control"
									id="paymentAmt" readonly="true" />
							</div>
						</div>
						<div class="form-group" id="linkedAccountTr">
							<label class="col-md-4 control-label">Account Number<span
								style="color: red">*</span></label>
							<div class="col-md-6">
								<form:input path="depositForm.linkedAccountNo"
									class="form-control" id="linkedAccount" readonly="true" />
							</div>
						</div>
						<div class="form-group" id="linkedAccountBalanceTr">
							<label class="col-md-4 control-label">Account Balance<span
								style="color: red">*</span></label>
							<fmt:formatNumber
								value="${fixedDepositForm.depositForm.accountBalance}"
								pattern="#.##" var="accBal" />
							<div class="col-md-6">
								<form:hidden path="depositForm.accountBalance"
									id="linkedAccountBalance1" />
								<input class="form-control" id="linkedAccountBalance" readonly />
							</div>
						</div>


						<div style="display: none" id="bankDetailsTr">
							<div class="form-group" id="chequeNoTr">
								<label class="col-md-4 control-label"> <span
									id="chequeNoLabel" style="display: none">Cheque Number<span
										style="color: red">*</span></span><span id="ddNoLabel"
									style="display: none">DD Number<span style="color: red">*</span></span></label>
								<div class="col-md-6">
									<form:input path="depositForm.chequeNo" class="form-control"
										id="chequeNo" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label"> <span
									id="chequeDateLabel" style="display: none">Cheque Date<span
										style="color: red">*</span></span><span id="ddDateLabel"
									style="display: none">DD Date<span style="color: red">*</span></span></label>
								<div class="col-md-6">
									<form:input path="depositForm.chequeDate" value="${todaysDate}"
										readonly="true" class="form-control datepicker-here"
										style="background: whitesmoke; cursor: pointer;"
										id="chequeDate" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.bank" /><span style="color: red">*</span></label>
								<div class="col-md-6">
									<form:input path="depositForm.chequeBank" class="form-control"
										id="chequeBank" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.branch" /><span style="color: red">*</span></label>
								<div class="col-md-6">
									<form:input path="depositForm.chequeBranch"
										class="form-control" id="chequeBranch" />
								</div>
							</div>
							<span id="bankDetailsError" style="display: none; color: red">Please
								enter all the bank details</span>


						</div>
						<div style="display: none" id="cardDetailsTr">
							<div class="form-group">
								<label class="col-md-4 control-label">Select Card</label>
								<div class="col-md-6">
									<form:select path="depositForm.cardType" placeholder="12/20"
										id="cardType" class="form-control">
										<form:option value="">
											<spring:message code="label.selectCard" />
										</form:option>
										<form:option value="Debit Card">
											<spring:message code="label.debit" />
										</form:option>
										<form:option value="Credit Card">
											<spring:message code="label.credit" />
										</form:option>
										<form:option value="Other">
											<spring:message code="label.other" />
										</form:option>
									</form:select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label">Enter Card No</label>
								<div class="col-md-6">
									<form:input path="depositForm.cardNo"
										placeholder="xxxx-xxxx-xxxx-xxxx" id="cardNo"
										class="form-control" filtertype="CCNo" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label">Expiry Date</label>
								<div class="col-md-2">
									<select class="form-control" id="expiryMonth"
										onchange="changeExpiryDate()">

										<option value="">MONTH</option>
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

										<option value="">YEAR</option>
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
								<form:hidden path="depositForm.expiryDate" id="expiryDate" />
								<div class="col-md-2">
									<form:input path="depositForm.cvv" filtertype="Number"
										placeholder="CVV" id="cvv" type="password"
										class="form-control" style="width:50%" />
								</div>
							</div>
							<span id="cardDetailsError" style="display: none; color: red">Please
								enter all the card details</span>
						</div>

						<div style="display: none" id="netBankingDetailsTr">


							<div class="form-group">
								<label class="col-md-4 control-label">Select Bank<span
									style="color: red">*</span></label>
								<div class="col-md-6">
									<form:select path="fdPayType" id="bankType"
										onchange="onchangeBankType()" class="form-control">
										<form:option value="">
										Select
									</form:option>
										<form:option id="savingBankId" value="SameBank">
										Same Bank
									</form:option>
										<form:option id="differentBankId" value="DifferentBank">
										Different Bank
									</form:option>

									</form:select>
								</div>
							</div>

							<div class="form-group" id="transferOption">
								<label class="col-md-4 control-label">Transfer<span
									style="color: red">*</span></label>
								<div class="col-md-6">
									<form:select path="otherPayTransfer1" id="transferType"
										class="form-control">
										<form:option value="">
										Select
									</form:option>
										<form:option value="NEFT">
										NEFT
									</form:option>
										<form:option value="IMPS">
										IMPS
									</form:option>
										<form:option value="RTGS">
										RTGS
									</form:option>

									</form:select>
								</div>
							</div>

							<div class="form-group" id="paymentBeneficiaryNameDiv">
								<label class="control-label col-sm-4" for="">Beneficiary
									Name<span style="color: red">*</span>
								</label>

								<div class="col-sm-6">
									<form:input path="otherName1" class="form-control"
										id="paymentBeneficiaryName" onkeypress="validName(event)" />
								</div>

							</div>


							<div class="form-group" id="paymentBeneficiaryAccountDiv">
								<label class="control-label col-sm-4" for="">Account
									Number<span style="color: red">*</span>
								</label>

								<div class="col-sm-6">
									<form:input path="otherAccount1" class="form-control"
										id="paymentBeneficiaryAccount"
										onkeypress="validationAccount(event)" />
								</div>

							</div>

							<div class="form-group" id="paymentBankNameTr">
								<label class="control-label col-sm-4" for="">Bank Name<span
									style="color: red">*</span></label>

								<div class="col-sm-6">
									<form:input path="otherBank1" class="form-control"
										id="paymentBeneficiaryBank" onkeypress="validName(event)" />
								</div>

							</div>

							<div class="form-group" id="paymentBeneficiaryIfscDiv">
								<label class="control-label col-sm-4" for="">IFSC <span
									style="color: red">*</span></label>

								<div class="col-sm-6">
									<form:input path="otherIFSC1" class="form-control"
										id="paymentBeneficiaryIfsc" onkeypress="validIFSC(event)" />
								</div>

							</div>

						</div>
						<input type="button" onclick="resetPaymentDetails()"
							class="btn btn-warning pull-right" value="Reset"
							style="margin-right: 9px; margin-bottom: 33px;" />
					</div>

					<div class="col-sm-12 col-md-12 col-lg-12">

						<script>
						
						function addNewBeneficiary(addNew){
							
							if (window.n <= 7) {
							
								
								if(window.n==0){
									
								$("#items")
										.append(
												'<div class="form-1 col-md-6 col-xs-12"><div class="add-personal-details field"><h4><spring:message code="label.accountDeatils"/></h4></div><table class="theader" width="400" style="position: relative;left: 2.6%;"><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.benificiaryName"/></b><span style="color:red">*</span></td><td><form:input path="benificiaryName" placeholder="Enter Benificiary Name" id="benificiaryName0" onkeypress="validAlphabetsAndNumbers(event)" class="myform-control"></form:input><span id="accountNum2Error" class="error" style="display:none;"><font color="red"><spring:message code="label.incorrectFormat"/></font></span></td></tr><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.accountType"/></b><span style="color:red">*</span></td><td><form:select path="bankAccountType"  id="bankAccountType0" class="myform-control" ><form:option value="Savings"><spring:message code="label.Saving"/></form:option><form:option value="Current"><spring:message code="label.Current"/></form:option></form:select> </td></tr><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.accountNo"/></b><span style="color:red">*</span></td><td><form:input path="benificiaryAccountNumber" placeholder="Enter account number" id="accountNo0"  onkeypress="validationBenificiary(event)" class="myform-control" type="number"></form:input><span id="accountNum2Error" class="error" style="display:none;"><font color="red"><spring:message code="label.incorrectFormat"/></font></span></td></tr><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.ifsc"/></b><span style="color:red">*</span></td><td><form:input path="ifscCode" placeholder="Enter IFSC code" id="ifscCode0"  onkeypress="validIFSC(event)" class="myform-control"></form:input><span id="accountNum2Error" class="error" style="display:none;"><font color="red"><spring:message code="label.incorrectFormat"/></font></span></td></tr><tr><td></td><td><span id="accountBalanceError0" class="error" style="display:none;color:red;"><spring:message code="label.incorrectFormat"/></span></td></tr> <tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.amount"/></b><span style="color:red">*</span></td><td><form:input path="amountToTransfer" placeholder="Amount"  id="amountToTransfer0" onkeypress="return isNumberKey1(event,$(this))" class="myform-control" type="text"></form:input></td></tr> <tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.remarks"/></b><span style="color:red">*</span></td><td><form:input path="remarks" placeholder="Remarks" id="remark0" onkeypress="validName(event)"  class="myform-control"></form:input><span id="accountNum2Error" class="error" style="display:none;"><font color="red"><spring:message code="label.incorrectFormat"/></font></span></td></tr></table><button id="b1" class="btn btn-warning remove-me" type="button">Delete</button></div><br>');
							}
								else if(window.n==1){ 
								
									$("#items")
											.append(
													'<div class="form-1 col-md-6 col-xs-12"><div class="add-personal-details field"><h4><spring:message code="label.accountDeatils"/></h4></div><table class="theader" width="400" style="position: relative;left: 2.6%;"><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.benificiaryName"/></b><span style="color:red">*</span></td><td><form:input path="benificiaryName" placeholder="Enter Benificiary Name" id="benificiaryName1" onkeypress="validAlphabetsAndNumbers(event)" class="myform-control"></form:input><span id="accountNum2Error" class="error" style="display:none;"><font color="red"><spring:message code="label.incorrectFormat"/></font></span></td></tr><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.accountType"/></b><span style="color:red">*</span></td><td><form:select path="bankAccountType"  id="bankAccountType1" class="myform-control"><form:option value="Savings"><spring:message code="label.Saving"/></form:option><form:option value="Current"><spring:message code="label.Current"/></form:option></form:select> </td></tr><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.accountNo"/></b><span style="color:red">*</span></td><td><form:input path="benificiaryAccountNumber" placeholder="Enter account number" id="accountNo1" onkeypress="validationBenificiary(event)" class="myform-control" type="number"></form:input><span id="accountNum2Error" class="error" style="display:none;"><font color="red"><spring:message code="label.incorrectFormat"/></font></span></td></tr><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.ifsc"/></b><span style="color:red">*</span></td><td><form:input path="ifscCode" placeholder="Enter IFSC code" id="ifscCode1" onkeypress="validIFSC(event)" class="myform-control"></form:input><span id="accountNum2Error" class="error" style="display:none;"><font color="red"><spring:message code="label.incorrectFormat"/></font></span></td></tr><tr><td></td><td><span id="accountBalanceError1" class="error" style="display:none;color:red;"><spring:message code="label.incorrectFormat"/></span></td></tr> <tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.amount"/></b><span style="color:red">*</span></td><td><form:input path="amountToTransfer" placeholder="Amount"  id="amountToTransfer1" onkeypress="return isNumberKey1(event,$(this))" class="myform-control" type="text"></form:input></td></tr> <tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.remarks"/></b><span style="color:red">*</span></td><td><form:input path="remarks" placeholder="Remarks" id="remark1" onkeypress="validName(event)"  class="myform-control"></form:input><span id="accountNum2Error" class="error" style="display:none;"><font color="red"><spring:message code="label.incorrectFormat"/></font></span></td></tr></table><button id="b2" class="btn btn-warning remove-me" type="button">Delete</button></div><br>');
								}
								else if(window.n==2){
								
									$("#items")
											.append(
													'<div class="form-1 col-md-6 col-xs-12"><div class="add-personal-details field"><h4><spring:message code="label.accountDeatils"/></h4></div><table class="theader" width="400" style="position: relative;left: 2.6%;"><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.benificiaryName"/></b><span style="color:red">*</span></td><td><form:input path="benificiaryName" placeholder="Enter Benificiary Name" id="benificiaryName2" onkeypress="validAlphabetsAndNumbers(event)"   class="myform-control"></form:input><span id="accountNum2Error" class="error" style="display:none;"><font color="red"><spring:message code="label.incorrectFormat"/></font></span></td></tr><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.accountType"/></b><span style="color:red">*</span></td><td><form:select path="bankAccountType"  id="bankAccountType2" class="myform-control"><form:option value="Savings"><spring:message code="label.Saving"/></form:option><form:option value="Current"><spring:message code="label.Current"/></form:option></form:select> </td></tr><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.accountNo"/></b><span style="color:red">*</span></td><td><form:input path="benificiaryAccountNumber" placeholder="Enter account number" id="accountNo2" onkeypress="validationBenificiary(event)" class="myform-control" type="number"></form:input><span id="accountNum2Error" class="error" style="display:none;"><font color="red"><spring:message code="label.incorrectFormat"/></font></span></td></tr><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.ifsc"/></b><span style="color:red">*</span></td><td><form:input path="ifscCode" placeholder="Enter IFSC code" id="ifscCode2" onkeypress="validIFSC(event)"  class="myform-control"></form:input><span id="accountNum2Error" class="error" style="display:none;"><font color="red"><spring:message code="label.incorrectFormat"/></font></span></td></tr><tr><td></td><td><span id="accountBalanceError2" class="error" style="display:none;color:red;"><spring:message code="label.incorrectFormat"/></span></td></tr> <tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.amount"/></b><span style="color:red">*</span></td><td><form:input path="amountToTransfer" placeholder="Amount"  id="amountToTransfer2" onkeypress="return isNumberKey1(event,$(this))" class="myform-control" type="text"></form:input></td></tr> <tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.remarks"/></b><span style="color:red">*</span></td><td><form:input path="remarks" placeholder="Remarks" id="remark2" onkeypress="validName(event)" class="myform-control"></form:input><span id="accountNum2Error" class="error" style="display:none;"><font color="red"><spring:message code="label.incorrectFormat"/></font></span></td></tr></table><button id="b3" class="btn btn-warning remove-me" type="button">Delete</button></div><br>');
								}
								
								else if(window.n==3){
									
									$("#items")
											.append(
													'<div class="form-1 col-md-6 col-xs-12"><div class="add-personal-details field"><h4><spring:message code="label.accountDeatils"/></h4></div><table class="theader" width="400" style="position: relative;left: 2.6%;"><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.benificiaryName"/></b><span style="color:red">*</span></td><td><form:input path="benificiaryName" placeholder="Enter Benificiary Name" id="benificiaryName3" onkeypress="validAlphabetsAndNumbers(event)"  class="myform-control"></form:input><span id="accountNum2Error" class="error" style="display:none;"><font color="red"><spring:message code="label.incorrectFormat"/></font></span></td></tr><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.accountType"/></b><span style="color:red">*</span></td><td><form:select path="bankAccountType"  id="bankAccountType3" class="myform-control"><form:option value="Savings"><spring:message code="label.Saving"/></form:option><form:option value="Current"><spring:message code="label.Current"/></form:option></form:select> </td></tr><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.accountNo"/></b><span style="color:red">*</span></td><td><form:input path="benificiaryAccountNumber" placeholder="Enter account number" id="accountNo3" onkeypress="validationBenificiary(event)"  class="myform-control" type="number"></form:input><span id="accountNum2Error" class="error" style="display:none;"><font color="red"><spring:message code="label.incorrectFormat"/></font></span></td></tr><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.ifsc"/></b><span style="color:red">*</span></td><td><form:input path="ifscCode" placeholder="Enter IFSC code" id="ifscCode3" onkeypress="validIFSC(event)" class="myform-control"></form:input><span id="accountNum2Error" class="error" style="display:none;"><font color="red"><spring:message code="label.incorrectFormat"/></font></span></td></tr><tr><td></td><td><span id="accountBalanceError3" class="error" style="display:none;color:red;"><spring:message code="label.incorrectFormat"/></span></td></tr> <tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.amount"/></b><span style="color:red">*</span></td><td><form:input path="amountToTransfer" placeholder="Amount"  id="amountToTransfer3" onkeypress="return isNumberKey1(event,$(this))" class="myform-control"  type="text"></form:input></td></tr> <tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.remarks"/></b><span style="color:red">*</span></td><td><form:input path="remarks" placeholder="Remarks" id="remark3" onkeypress="validName(event)" class="myform-control"></form:input><span id="accountNum2Error" class="error" style="display:none;"><font color="red"><spring:message code="label.incorrectFormat"/></font></span></td></tr></table><button id="b4" class="btn btn-warning remove-me" type="button">Delete</button></div><br>');
								}
								else if(window.n==4){
									
									$("#items")
											.append(
													'<div class="form-1 col-md-6 col-xs-12"><div class="add-personal-details field"><h4><spring:message code="label.accountDeatils"/></h4></div><table class="theader" width="400" style="position: relative;left: 2.6%;"><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.benificiaryName"/></b><span style="color:red">*</span></td><td><form:input path="benificiaryName" placeholder="Enter Benificiary Name" id="benificiaryName4" onkeypress="validAlphabetsAndNumbers(event)" class="myform-control"></form:input><span id="accountNum2Error" class="error" style="display:none;"><font color="red"><spring:message code="label.incorrectFormat"/></font></span></td></tr><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.accountType"/></b><span style="color:red">*</span></td><td><form:select path="bankAccountType"  id="bankAccountType4" class="myform-control"><form:option value="Savings"><spring:message code="label.Saving"/></form:option><form:option value="Current"><spring:message code="label.Current"/></form:option></form:select> </td></tr><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.accountNo"/></b><span style="color:red">*</span></td><td><form:input path="benificiaryAccountNumber" placeholder="Enter account number" id="accountNo4" onkeypress="validationBenificiary(event)" class="myform-control" type="number"></form:input><span id="accountNum2Error" class="error" style="display:none;"><font color="red"><spring:message code="label.incorrectFormat"/></font></span></td></tr><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.ifsc"/></b><span style="color:red">*</span></td><td><form:input path="ifscCode" placeholder="Enter IFSC code" id="ifscCode4" onkeypress="validIFSC(event)" class="myform-control"></form:input><span id="accountNum2Error" class="error" style="display:none;"><font color="red"><spring:message code="label.incorrectFormat"/></font></span></td></tr><tr><td></td><td><span id="accountBalanceError4" class="error" style="display:none;color:red;"><spring:message code="label.incorrectFormat"/></span></td></tr> <tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.amount"/></b><span style="color:red">*</span></td><td><form:input path="amountToTransfer" placeholder="Amount"  id="amountToTransfer4" onkeypress="return isNumberKey1(event,$(this))" class="myform-control" type="text"></form:input></td></tr> <tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.remarks"/></b><span style="color:red">*</span></td><td><form:input path="remarks" placeholder="Remarks" id="remark4" onkeypress="validName(event)" class="myform-control"></form:input><span id="accountNum2Error" class="error" style="display:none;"><font color="red"><spring:message code="label.incorrectFormat"/></font></span></td></tr></table><button id="b5" class="btn btn-warning remove-me" type="button">Delete</button></div><br>');
								}
								else if(window.n==5){
									
									$("#items")
											.append(
													'<div class="form-1 col-md-6 col-xs-12"><div class="add-personal-details field"><h4><spring:message code="label.accountDeatils"/></h4></div><table class="theader" width="400" style="position: relative;left: 2.6%;"><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.benificiaryName"/></b><span style="color:red">*</span></td><td><form:input path="benificiaryName" placeholder="Enter Benificiary Name" id="benificiaryName5" onkeypress="validAlphabetsAndNumbers(event)" class="myform-control"></form:input><span id="accountNum2Error" class="error" style="display:none;"><font color="red"><spring:message code="label.incorrectFormat"/></font></span></td></tr><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.accountType"/></b><span style="color:red">*</span></td><td><form:select path="bankAccountType"  id="bankAccountType5" class="myform-control"><form:option value="Savings"><spring:message code="label.Saving"/></form:option><form:option value="Current"><spring:message code="label.Current"/></form:option></form:select> </td></tr><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.accountNo"/></b><span style="color:red">*</span></td><td><form:input path="benificiaryAccountNumber" placeholder="Enter account number" id="accountNo5" onkeypress="validationBenificiary(event)" class="myform-control" type="number"></form:input><span id="accountNum2Error" class="error" style="display:none;"><font color="red"><spring:message code="label.incorrectFormat"/></font></span></td></tr><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.ifsc"/></b><span style="color:red">*</span></td><td><form:input path="ifscCode" placeholder="Enter IFSC code" id="ifscCode5" onkeypress="validIFSC(event)"  class="myform-control"></form:input><span id="accountNum2Error" class="error" style="display:none;"><font color="red"><spring:message code="label.incorrectFormat"/></font></span></td></tr><tr><td></td><td><span id="accountBalanceError5" class="error" style="display:none;color:red;"><spring:message code="label.incorrectFormat"/></span></td></tr> <tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.amount"/></b><span style="color:red">*</span></td><td><form:input path="amountToTransfer" placeholder="Amount"  id="amountToTransfer5" onkeypress="return isNumberKey1(event,$(this))" class="myform-control" type="text"></form:input></td></tr> <tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.remarks"/></b><span style="color:red">*</span></td><td><form:input path="remarks" placeholder="Remarks" id="remark5" onkeypress="validName(event)" class="myform-control"></form:input><span id="accountNum2Error" class="error" style="display:none;"><font color="red"><spring:message code="label.incorrectFormat"/></font></span></td></tr></table><button id="b6" class="btn btn-warning remove-me" type="button">Delete</button></div><br>');
								}
								else if(window.n==6){
									
									$("#items")
											.append(
													'<div class="form-1 col-md-6 col-xs-12"><div class="add-personal-details field"><h4><spring:message code="label.accountDeatils"/></h4></div><table class="theader" width="400" style="position: relative;left: 2.6%;"><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.benificiaryName"/></b><span style="color:red">*</span></td><td><form:input path="benificiaryName" placeholder="Enter Benificiary Name" id="benificiaryName6" onkeypress="validAlphabetsAndNumbers(event)" class="myform-control"></form:input><span id="accountNum2Error" class="error" style="display:none;"><font color="red"><spring:message code="label.incorrectFormat"/></font></span></td></tr><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.accountType"/></b><span style="color:red">*</span></td><td><form:select path="bankAccountType"  id="bankAccountType6" class="myform-control"><form:option value="Savings"><spring:message code="label.Saving"/></form:option><form:option value="Current"><spring:message code="label.Current"/></form:option></form:select> </td></tr><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.accountNo"/></b><span style="color:red">*</span></td><td><form:input path="benificiaryAccountNumber" placeholder="Enter account number" id="accountNo6" onkeypress="validationBenificiary(event)"  class="myform-control" type="number"></form:input><span id="accountNum2Error" class="error" style="display:none;"><font color="red"><spring:message code="label.incorrectFormat"/></font></span></td></tr><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.ifsc"/></b><span style="color:red">*</span></td><td><form:input path="ifscCode" placeholder="Enter IFSC code" id="ifscCode6" onkeypress="validIFSC(event)" class="myform-control"></form:input><span id="accountNum2Error" class="error" style="display:none;"><font color="red"><spring:message code="label.incorrectFormat"/></font></span></td></tr><tr><td></td><td><span id="accountBalanceError6" class="error" style="display:none;color:red;"><spring:message code="label.incorrectFormat"/></span></td></tr> <tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.amount"/></b><span style="color:red">*</span></td><td><form:input path="amountToTransfer" placeholder="Amount"  id="amountToTransfer6" onkeypress="return isNumberKey1(event,$(this))" class="myform-control" type="text"></form:input></td></tr> <tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.remarks"/></b><span style="color:red">*</span></td><td><form:input path="remarks" placeholder="Remarks" id="remark6" onkeypress="validName(event)" class="myform-control"></form:input><span id="accountNum2Error" class="error" style="display:none;"><font color="red"><spring:message code="label.incorrectFormat"/></font></span></td></tr></table><button id="b7" class="btn btn-warning remove-me" type="button">Delete</button></div><br>');
								}
								else if(window.n==7){
									
									$("#items")
											.append(
													'<div class="form-1 col-md-6 col-xs-12"><div class="add-personal-details field"><h4><spring:message code="label.accountDeatils"/></h4></div><table class="theader" width="400" style="position: relative;left: 2.6%;"><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.benificiaryName"/></b><span style="color:red">*</span></td><td><form:input path="benificiaryName" placeholder="Enter Benificiary Name" id="benificiaryName7" onkeypress="validAlphabetsAndNumbers(event)" class="myform-control"></form:input><span id="accountNum2Error" class="error" style="display:none;"><font color="red"><spring:message code="label.incorrectFormat"/></font></span></td></tr><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.accountType"/></b><span style="color:red">*</span></td><td><form:select path="bankAccountType"  id="bankAccountType7" class="myform-control"><form:option value="Savings"><spring:message code="label.Saving"/></form:option><form:option value="Current"><spring:message code="label.Current"/></form:option></form:select> </td></tr><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.accountNo"/></b><span style="color:red">*</span></td><td><form:input path="benificiaryAccountNumber" placeholder="Enter account number" id="accountNo7" onkeypress="validationBenificiary(event)" class="myform-control" type="number"></form:input><span id="accountNum2Error" class="error" style="display:none;"><font color="red"><spring:message code="label.incorrectFormat"/></font></span></td></tr><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.ifsc"/></b><span style="color:red">*</span></td><td><form:input path="ifscCode" placeholder="Enter IFSC code" id="ifscCode7" onkeypress="validIFSC(event)"  class="myform-control"></form:input><span id="accountNum2Error" class="error" style="display:none;"><font color="red"><spring:message code="label.incorrectFormat"/></font></span></td></tr><tr><td></td><td><span id="accountBalanceError7" class="error" style="display:none;color:red;"><spring:message code="label.incorrectFormat"/></span></td></tr> <tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.amount"/></b><span style="color:red">*</span></td><td><form:input path="amountToTransfer" placeholder="Amount"  id="amountToTransfer7" onkeypress="return isNumberKey1(event,$(this))" class="myform-control" type="text"></form:input></td></tr> <tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.remarks"/></b><span style="color:red">*</span></td><td><form:input path="remarks" placeholder="Remarks" id="remark7" onkeypress="validName(event)" class="myform-control"></form:input><span id="accountNum2Error" class="error" style="display:none;"><font color="red"><spring:message code="label.incorrectFormat"/></font></span></td></tr></table><button id="b8" class="btn btn-warning remove-me" type="button">Delete</button></div><br>');
								}
								

								if(addNew==true){
									
								
									
									/* document.getElementById('benificiaryName0').value="";
									document.getElementById('accountNo0').value="";
									document.getElementById('ifscCode0').value="";
									document.getElementById('amountToTransfer0').value="";
									document.getElementById('remark0').value=""; */
									
									
								}
								
								 window.n=parseInt(window.n)+1;
							}
								else {
								alert("Maximum 8 Beneficiaries are allowed!")
							}
							
						}
					$(document)
							.ready(
									function() {
										
										$('#nomineePan').keyup(function(e){
											
											var panVal = $(this).val();
											var alphanumeric = "^[a-zA-Z0-9]+$";
											var regpan = /^([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}?$/;
									if(panVal.length > 9){
									
											if(regpan.test(panVal)){
												if(e.keyCode != 144){
											  
												}
											} else {
												if( e.keyCode != 144){
												alert("invalid pan number");
												}
											}
									}
											
										});
										$('#nomineePan').bind('keypress', function (event) {
									        var regex = new RegExp("^[a-zA-Z0-9]+$");
									        var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
									        if (!regex.test(key)) {
									        	//alert("false")
									            event.preventDefault();
									            return false;
									        }
									    });
										
										
									$('#gaurdianPan').keyup(function(e){
											
											var panVal = $(this).val();
											var alphanumeric = "^[a-zA-Z0-9]+$";
											var regpan = /^([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}?$/;
									if(panVal.length > 9){
										
											if(regpan.test(panVal)){
												if(e.keyCode != 144){
											  
												}
											} else {
												if( e.keyCode != 144){
												alert("invalid pan number");
												}
											}
									}
											
										});
										$('#gaurdianPan').bind('keypress', function (event) {
									        var regex = new RegExp("^[a-zA-Z0-9]+$");
									        var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
									        if (!regex.test(key)) {
									        	//alert("false")
									            event.preventDefault();
									            return false;
									        }
									    });
										var gaurdianAadhar = document.getElementById('gaurdianAadhar');
										var nomineeAadhar = document.getElementById('nomineeAadhar');
										gaurdianAadhar.onkeydown = function(e) {
										    if(!((e.keyCode > 95 && e.keyCode < 106)
										      || (e.keyCode > 47 && e.keyCode < 58) 
										      || e.keyCode == 8)) {
										        return false;
										    }
										    if (gaurdianAadhar.value.length>11 && e.keyCode != 8){
										    	return false;
										    }
										}
										nomineeAadhar.onkeydown = function(e) {
										    if(!((e.keyCode > 95 && e.keyCode < 106)
										      || (e.keyCode > 47 && e.keyCode < 58) 
										      || e.keyCode == 8)) {
										        return false;
										    }
										    if (nomineeAadhar.value.length>11 && e.keyCode != 8){
										    	return false;
										    }
										}
									
										//window.n = $("#items .form-1").length;
									
										$("#add").click(
												
														function(e) {
															addNewBeneficiary(true);
												
														});

										$("body").on(
												"click",
												".remove-me",
												function(e) {
													$(this).parent("div")
															.remove();
												 window.n=parseInt(window.n)-1;
												});
										
										
									
										var radioValueAccountType = "${customerNriAccountType}";
										
										if(radioValueAccountType == "NRE"){
											
											$("#nre").prop("checked", true);
											
										}else if(radioValueAccountType == "NRO"){
											$("#nro").prop("checked", true);
											
										}else if(radioValueAccountType == "FCNR"){
											$("#fcnr").prop("checked", true);
											
										}else if(radioValueAccountType == "RFC"){
											$("#rfc").prop("checked", true);
											
											
											
										}else if(radioValueAccountType == "PRP"){
											$("#prp").prop("checked", true);
											
										}else{
											$("#nre").prop("checked", false);
											$("#nro").prop("checked", false);
											$("#fcnr").prop("checked", false);
											$("#rfc").prop("checked", false);
											$("#prp").prop("checked", false);
											
										}
										
										
										var FCNR=["USD","GBP","EUR","JPY","CAD","AUD","SGD","HKD"];
										var RFC=["USD","GBP","EUR","JPY","CAD","AUD"];
										  $('#currency option').hide();
										  $('#currency').val('Select').show().change();
									     $('#currency option').each(function(){
									    	
									    	 if(radioValueAccountType=="FCNR"  || radioValueAccountType == "PRP"){
									    		var length=$.inArray( $(this).val(), FCNR );
									    		if(length>-1)$(this).show();
									    	 }else if(radioValueAccountType == "RFC"){
									    		 var length=$.inArray( $(this).val(), RFC );
									    		 if(length>-1)$(this).show();
									    	 }else{
									    		 $('#currency option').show();
									    	 }
									    	 
									    	
									     }); 
									     
										
									     
											if(radioValueAccountType == ""){
												$("#currency").val("INR").change();
											}else if(radioValueAccountType == "NRE" || radioValueAccountType == 'NRO'){
												$("#currency").val("INR").change();
											}else if(radioValueAccountType == 'FCNR' || radioValueAccountType == 'RFC'){
												$("#currency").val("EUR").change();
											}
										
									});
				</script>



						<table align="center" class="f_deposit_btn">
							<tr>
								<td><span id='validationError' style="color: red"></span></td>
							</tr>
							<tr>
								<td><a href="user" class="btn btn-warning"> <spring:message
											code="label.back" /></a> <input type="submit"
									class="btn btn-info" onclick="return val();" value="Submit"></td>


							</tr>

						</table>
					</div>

				</form:form>
			</div>

		</div>

		<script>
		function isNumberKey1(evt,obj)
		{
			
		   var charCode = (evt.which) ? evt.which : evt.keyCode;
		   if (charCode != 46 && charCode > 31 
		     && (charCode<48||charCode>57)){
		      return false;
		   }else{
		 	  var splitValue = obj.val();
		          var res = splitValue.split(".");
		          if(splitValue.includes(".")){
		          var spVal = res[1];
		          if(spVal.length > 1){
		        	  evt.preventDefault();
		        	  return false;
		          }
		          return true;
		          }else{
		      
		         
		 	  return true;
		          }
		   }
		  }
$(document).ready(function(){
	
	var arrayLength = <c:out value="${fixedDepositForm.accountList.size()}"/>;
	
	if(arrayLength==0){
		document.getElementById('linkedAccountDiv').style.display = 'none';
	/* 	document.getElementById('savingAccRadio').style.display = 'none'; */
		
		
	}
	
	var account=document.getElementById('accountNo').value;
	
	if(account!=""){
		var account = account.split(",");
		document.getElementById('accountType').value=account[1];
		document.getElementById('accountBalance').value = parseFloat(account[2]).toLocaleString("en-US");
		document.getElementById('accountBalance1').value = account[2];
		
		document.getElementById('accountTypeDiv').style.display='block';
		document.getElementById('accountBalanceDiv').style.display='block';
		document.getElementById('fundTransferLinkedAccount').style.display='block';
		
		}
		else{
			document.getElementById('accountTypeDiv').style.display='none';
			document.getElementById('accountBalanceDiv').style.display='none';
			document.getElementById('fundTransferLinkedAccount').style.display='none';
			
		}
	
		/* nominee.ready function */
		$("#gaurdianDiv").hide();
		if(parseFloat(document.getElementById("nomineeAge").value)<18){
			$("#gaurdianDiv").show();
			
		}
		
	/*..beneficiary..ready function......*/

	
	var beneficiaryArray= '${fixedDepositForm.benificiaryName}'.split(",")
	var bankAccountTypeArray= '${fixedDepositForm.bankAccountType}'.split(",")
	var benificiaryAccountNumberArray= '${fixedDepositForm.benificiaryAccountNumber}'.split(",")
	var ifscCodeArray= '${fixedDepositForm.ifscCode}'.split(",")
	var amountToTransferArray= '${fixedDepositForm.amountToTransfer}'.split(",")
	var remarksArray= '${fixedDepositForm.remarks}'.split(",")
	
	window.n=0;
	for(var i=0;i<beneficiaryArray.length;i++){
	
		addNewBeneficiary(false);
		if(beneficiaryArray != ""){
			document.getElementById('benificiaryName'+i).value=beneficiaryArray[i];
			document.getElementById('bankAccountType'+i).value=bankAccountTypeArray[i];
			document.getElementById('accountNo'+i).value=benificiaryAccountNumberArray[i];
			document.getElementById('ifscCode'+i).value=ifscCodeArray[i];
			document.getElementById('amountToTransfer'+i).value=amountToTransferArray[i];
			document.getElementById('remark'+i).value=remarksArray[i];
		}
		
	
	
	}
	
	
	/************payment.ready function  **********.......*/

	
			// Initiate CC Validation
							InitiateValidation();
							var paymentMode = document.getElementById('paymentMode').value;
						/* 	document.getElementById('transferModeTr').style.display = 'none'; */
							showDetails(paymentMode); 
							if(paymentMode=='Card Payment'){
								var expiryDate= document.getElementById('expiryDate').value;
								var arrayStr=expiryDate.split("-");
								document.getElementById('expiryMonth').value=arrayStr[0];
								document.getElementById('expiryYear').value=arrayStr[1];
								
							}

							var linkedAccountNo = document
									.getElementById('linkedAccount').value;

							if (linkedAccountNo == "") {
								document.getElementById('linkedAccountBalanceTr').style.display = 'none';
								document.getElementById('linkedAccountTr').style.display = 'none';

							} else {
								document
										.getElementById('linkedAccountBalanceTr').style.display = 'block';
								document.getElementById('linkedAccountTr').style.display = 'block';
								
							}
							
							onchangeBankType();
							
							
							
							
							
							
							
});


function onchangeDepositAmount(value){
	
	document.getElementById('paymentAmt').value=value;
}


function nriAccType(value){

	var val;
	
	var nreCheck = document.getElementById('nre').checked;
	var nroCheck = document.getElementById('nro').checked;
	var rfcCheck = document.getElementById('rfc').checked;
	var fcnrCheck = document.getElementById('fcnr').checked;
	  
	if(nreCheck == true ){
		val = document.getElementById('nre').value;
	}
	else if(nroCheck == true){
		val = document.getElementById('nro').value;
	}
	else if(rfcCheck == true){
		val = document.getElementById('rfc').value;
	}
	else if(fcnrCheck == true){
		val = document.getElementById('fcnr').value;
	}
	else{
		val = document.getElementById('prp').value;
	}
	
	
	
	var FCNR=["USD","GBP","EUR","JPY","CAD","AUD","SGD","HKD"];
	var RFC=["USD","GBP","EUR","JPY","CAD","AUD"];
	  $('#currency option').hide();
	  $('#currency').val('Select').show().change();
     $('#currency option').each(function(){
    	
    	 if(val=="FCNR"  || val == "PRP"){
    		var length=$.inArray( $(this).val(), FCNR );
    		if(length>-1)$(this).show();
    	 }else if(val == "RFC"){
    		 var length=$.inArray( $(this).val(), RFC );
    		 if(length>-1)$(this).show();
    	 }else{
    		 $('#currency option').show();
    	 }
    	 
    	
     }); 
	
     
     
     var radioValueAccountType = value;
		if(radioValueAccountType == ""){
			$("#currency").val("INR").change();
		}else if(radioValueAccountType == "NRE" || radioValueAccountType == 'NRO'){
			$("#currency").val("INR").change();
		}else if(radioValueAccountType == 'FCNR' || radioValueAccountType == 'RFC'){
			$("#currency").val("EUR").change();
		}
			
	
	
	//$("#fixedDepositForm").attr("action", "reverseEmiFixedAmount?val="+val);
	//$("#fixedDepositForm").submit();

}

	function val() {
		
		var submit = true;
	    //var	maxFdAmount = 100000000000;
	    var emiTenure=document.getElementById('emiTenure').value;
        var citizen = '${fixedDepositForm.citizen}';
       
		var fdAmount= document.getElementById('fdAmount').value;
	
		document.getElementById("paymentOk").style.display='none';
		document.getElementById("paymentCross").style.display='none';
		
		document.getElementById("nomineeOk").style.display='none';
		document.getElementById("nomineeCross").style.display='none';
		document.getElementById("depositOk").style.display='none';
		document.getElementById("depositCross").style.display='none';
		if(document.getElementById('nre')!=null){
		var nreCheck = document.getElementById('nre').checked;
		var nroCheck = document.getElementById('nro').checked;
		var rfcCheck = document.getElementById('rfc').checked;
		var fcnrCheck = document.getElementById('fcnr').checked;
		var prpCheck = document.getElementById('prp').checked;
		
		}
		
		var validationError=document.getElementById('validationError');
		validationError.innerHTML="";
		var errorMsg = "";
		
		var inp = document.querySelectorAll("#depositDetails .form-control");
		for(i=0;i<inp.length;i++){
			inp[i].style.borderColor = "#cccccc";
		}
		
		
		
		currency = document.getElementById('currency').value;
		if(currency == 'Select'){
			document.getElementById('currency').style.borderColor = "red";
			errorMsg = "<br>Please select the currency";
			validationError.innerHTML += errorMsg;
			document.getElementById('depositCross').style.display='block';
			submit = false;
		}
		if(fdAmount==""){
			document.getElementById('fdAmount').style.borderColor = "red";
			errorMsg = "<br>Please enter deposit amount";
			validationError.innerHTML += errorMsg;
			document.getElementById('depositCross').style.display='block';
			submit = false;
			
		}
		else{
			 fdAmount=parseFloat(fdAmount);
			 var minDepositAmount= parseInt('${productConfiguration.minimumDepositAmount}');
			   var maxFdAmount= parseInt('${productConfiguration.maximumDepositAmount}');
		    
		if(minDepositAmount>fdAmount){
			document.getElementById('fdAmount').style.borderColor = "red";
			errorMsg = "<br>Minimum Deposit amount is "+minDepositAmount+".";
			validationError.innerHTML += errorMsg;
			document.getElementById('depositCross').style.display='block';
			submit = false;
		}
		if(fdAmount>maxFdAmount){
			document.getElementById('fdAmount').style.borderColor = "red";
			errorMsg = "<br>Maximum Deposit amount is "+maxFdAmount+".";
			validationError.innerHTML += errorMsg;
			
			submit = false;
		}
		
		
		currency = document.getElementById('currency').value;
		if(currency == 'Select'){
			document.getElementById('currency').style.borderColor = "red";
			errorMsg = "<br>Please select the currency";
			validationError.innerHTML += errorMsg;
			document.getElementById('depositCross').style.display='block';
			submit = false;
		}
		 
		if(citizen == 'NRI'){
		if(nreCheck == false && nroCheck == false && rfcCheck == false && fcnrCheck == false && prpCheck == false ){
			errorMsg = "<br>Please select NRI account type ";
			validationError.innerHTML += errorMsg;
			document.getElementById('depositCross').style.display='block';
			submit = false;
			
		}
		}
		
		
		
		}
		
		if($("#depositArea").val()==""){
			
			document.getElementById("depositArea").style.borderColor = "red"
			errorMsg = "<br>Area can not be empty";
			validationError.innerHTML += errorMsg;
			
			submit = false;
		}
if(emiTenure==""){
			
			document.getElementById("emiTenure").style.borderColor = "red"
			errorMsg = "<br>EMI Tenure can not be empty";
			validationError.innerHTML += errorMsg;
			
			submit = false;
		}
		var interestPayAmount= document.getElementById('interestPayAmount').value;
		
		if(interestPayAmount==""){
			document.getElementById('interestPayAmount').style.borderColor = "red";
			errorMsg = "<br>Please enter EMI amount";
			validationError.innerHTML += errorMsg;
			document.getElementById('depositCross').style.display='block';
			submit = false;
			
		}
		var gestationPeriod=$('#gestationPeriod').val();
if(gestationPeriod=="" ){
			
			document.getElementById('gestationPeriod').style.borderColor = "red";
			errorMsg = "<br>Please enter Gestation Period  ";
			validationError.innerHTML += errorMsg;
			document.getElementById('depositCross').style.display='block';
			submit = false;
		    } else {
				document.getElementById('gestationPeriod').style.borderColor = "black";

			} 
	
		
		if(submit==false){
			return false;
		}
		document.getElementById('depositOk').style.display='block';
		
		
		
		
		/************** ....................nominee validation starts ........... ************** */
		
		
		 inp = document.querySelectorAll("#nomineeDetails .form-control");
		 var nomineeCross=document.getElementById("nomineeCross");
		for(i=0;i<inp.length;i++){
			inp[i].style.borderColor = "#cccccc";
		}
		
		var regExp1=new RegExp("[^a-z|^A-Z]");
		
		
		var nomineeName = document.getElementById("nomineeName").value; 
		var nomineeAddress= document.getElementById("nomineeAddress").value;
		var nomineeRelationShip= document.getElementById("nomineeRelationShip").value;
		var nomineeAge = document.getElementById("nomineeAge").value; 
		var nomineePan= document.getElementById("nomineePan").value;
		var nomineeAadhar = document.getElementById("nomineeAadhar").value; 
		var nomineeAadharLen = document.getElementById("nomineeAadhar").value.length;

		if(nomineeName==""){
			
			document.getElementById("nomineeName").style.borderColor = "red"
			 errorMsg ="<br>Please enter nominee name";
			 validationError.innerHTML += errorMsg;
			nomineeCross.style.display='block';
			submit= false;
		}
		if (nomineeName.length<3 || nomineeName>100) {

			document.getElementById("nomineeName").style.borderColor = "red"
			errorMsg = "<br>Nominee name must be between 3 and 100 characters.";
			validationError.innerHTML += errorMsg;
			nomineeCross.style.display = 'block';
			submit = false;
		}
		if(nomineeAge=="" || isNaN(nomineeAge)){
			
			document.getElementById("nomineeAge").style.borderColor = "red"
				nomineeCross.style.display='block';
			 errorMsg ="<br>Please enter nominee age";
			 validationError.innerHTML += errorMsg;
			 submit= false;
		}
		if(parseInt(nomineeAge)<2 || parseInt(nomineeAge)>100){

			document.getElementById("nomineeAge").style.borderColor = "red"
			nomineeCross.style.display = 'block';
			errorMsg = "<br>Nominee Age must be between 2 to 100 years.";
			validationError.innerHTML += errorMsg;
			submit = false;
		}

		if(nomineeAddress==""){
			document.getElementById("nomineeAddress").style.borderColor = "red";
			 errorMsg ="<br>Please enter nominee address";
			 validationError.innerHTML += errorMsg;
			 nomineeCross.style.display='block';
			 submit= false;
		}
		if (nomineeAddress.length<3 || nomineeAddress>200) {

			document.getElementById("nomineeAddress").style.borderColor = "red"
			errorMsg = "<br>Nominee address must be between 3 and 200 characters.";
			validationError.innerHTML += errorMsg;
			
			submit = false;
		}
		if(nomineeRelationShip==""){
			document.getElementById("nomineeRelationShip").style.borderColor = "red";
			nomineeCross.style.display='block';
			 errorMsg ="<br>Please enter nominee relationship";
			 validationError.innerHTML += errorMsg;
			 submit= false;
		}
		if (nomineeRelationShip.length<3 || nomineeRelationShip>100) {

			document.getElementById("nomineeRelationShip").style.borderColor = "red"
			errorMsg = "<br>Nominee relationship must be between 3 and 100 characters.";
			validationError.innerHTML += errorMsg;
			submit = false;
		}
		if(regExp1.test(nomineeRelationShip)){
		
			document.getElementById('nomineeRelationShip').style.borderColor = "red";
			 errorMsg ="<br>Special characters and numbers are not allowed";
			 validationError.innerHTML += errorMsg;
			nomineeCross.style.display='block';
			submit= false;
		}
		
		if(regExp1.test(nomineeRelationShip)){
			
			document.getElementById('nomineeRelationShip').style.borderColor = "red";
			 errorMsg ="<br>Special characters and numbers are not allowed";
			 validationError.innerHTML += errorMsg;
			nomineeCross.style.display='block';
			submit= false;
		}
		
		
		/* if(nomineePan==""){
			document.getElementById("nomineePan").style.borderColor = "red";
			 errorMsg ="<br>Please enter nominee PAN";
			 validationError.innerHTML += errorMsg;
			 nomineeCross.style.display='block';
			 submit= false;
		} */
		/* if(nomineeAadhar==""){
			document.getElementById("nomineeAadhar").style.borderColor = "red";
			 errorMsg ="<br>Please enter nominee aadhar";
			 validationError.innerHTML += errorMsg;
			 nomineeCross.style.display='block';
			 submit= false;
		}
		 */
		
		
		if(nomineeAadharLen!=12 && nomineeAadharLen>0){
			document.getElementById("nomineeAadhar").style.borderColor = "red"
			 nomineeCross.style.display='block';
			 errorMsg ="<br>Aadhar card number is not valid. Please insert 12 digit number.";
			 validationError.innerHTML += errorMsg;
				 submit= false;
		}
		
		
		if(submit== false){return false;}
		
		 nomineeAge = parseInt(nomineeAge);
		
			/* if(nomineeAge>=18 && nomineePan=="")
			{
				document.getElementById("nomineePan").style.borderColor = "red"
				 errorMsg ="<br>Please enter nominee's PAN";
				 validationError.innerHTML += errorMsg;
				 nomineeCross.style.display='block';
				 submit= false;
			} */
			
			var panVal = $('#nomineePan').val();
			if(panVal!= "")
			{
		
					var regpan = /^([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}?$/;
					
					if(regpan.test(panVal)){
					
						document.getElementById("nomineePan").style.borderColor = "black"
					}else {
						
						document.getElementById('nomineePan').style.borderColor = "red";
						errorMsg ="<br>Please input correct pan card number.";
						nomineeCross.style.display='block';
						 validationError.innerHTML += errorMsg;
							 submit= false;
					}
			}
			
			if(submit== false){return false;}
			
			 nomineeAge = parseInt(nomineeAge);
		 
		if(nomineeAge<18){
			
			var guardianName=document.getElementById('guardianName').value;
			var guardianAge=parseInt(document.getElementById('guardianAge').value);
			var guardianAddress=document.getElementById('guardianAddress').value;
			var guardianRelationShip=document.getElementById('guardianRelationShip').value;
			var guardianPan=document.getElementById('gaurdianPan').value;
			var guardianAadhar=document.getElementById('gaurdianAadhar').value;
			var guardianAadharVal=document.getElementById('gaurdianAadhar').value;
			var gaurdianAadharLen = document.getElementById("gaurdianAadhar").value.length; 
			var nomineeAadhar= document.getElementById("nomineeAadhar").value;

			if(guardianName=="" || guardianAge<=0 || guardianAddress=="" || guardianRelationShip=="" ){

				if(guardianName=="" )
					document.getElementById("guardianName").style.borderColor ="red";
			
				
				if(guardianAge=="" || isNaN(guardianAge) )
					document.getElementById("guardianAge").style.borderColor ="red";
				if(guardianAddress=="" )
					document.getElementById("guardianAddress").style.borderColor ="red";
				if(guardianRelationShip=="" )
					document.getElementById("guardianRelationShip").style.borderColor ="red";
								/* if(guardianPan=="" )
					document.getElementById("gaurdianPan").style.borderColor ="red";
				if(guardianAadhar=="" )
					document.getElementById("gaurdianAadhar").style.borderColor ="red";
				 */
				 errorMsg ="<br>Please fill all the guardian details";
				 validationError.innerHTML += errorMsg;
				 nomineeCross.style.display='block';
				 
				submit = false;
			}
			if (guardianName.length<3 || guardianName>100) {

				document.getElementById("guardianName").style.borderColor = "red"
				errorMsg = "<br>Guardian name must be between 3 and 100 characters.";
				validationError.innerHTML += errorMsg;
				submit = false;
			}
			if (guardianAddress.length<3 || guardianAddress>200) {

				document.getElementById("guardianAddress").style.borderColor = "red"
				errorMsg = "<br>Guardian address must be between 3 and 200 characters.";
				validationError.innerHTML += errorMsg;
				submit = false;
			}

			if (guardianRelationShip.length<3 || guardianRelationShip>100) {

				document.getElementById("guardianRelationShip").style.borderColor = "red"
				errorMsg = "<br>Guardian relationship must be between 3 and 100 characters.";
				validationError.innerHTML += errorMsg;
				submit = false;
			}

			if(guardianAge< 18 || guardianAge >100){
				document.getElementById('guardianAge').style.borderColor = "red";
				 errorMsg ="<br>Guardian age should be between 18 and 100 years.";
				 validationError.innerHTML += errorMsg;
				 nomineeCross.style.display='block';
				
				submit = false;
			}
			
			
			
			if(regExp1.test(guardianRelationShip)){
				document.getElementById('guardianRelationShip').style.borderColor = "red";
				 errorMsg ="<br>Special characters and numbers are not allowed";
				 validationError.innerHTML += errorMsg;
				 nomineeCross.style.display='block';
				submit = false;
			}
			
if(gaurdianAadharLen!=12 && gaurdianAadharLen>0){
				
				document.getElementById('gaurdianAadhar').style.borderColor = "red";
				errorMsg ="<br>Aadhar card number is not valid. Please insert 12 digit number.";
				 validationError.innerHTML += errorMsg;
				 nomineeCross.style.display='block';
				submit = false;
			} 
           var panVal = $('#gaurdianPan').val();
			
			
			var regpan = /^([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}?$/;
			
			if(regpan.test(panVal)){
			
				document.getElementById("gaurdianPan").style.borderColor = "black"
			}else if(panVal!="") {
				
				document.getElementById('gaurdianPan').style.borderColor = "red";
				errorMsg ="<br>Please input correct pan card number.";
				nomineeCross.style.display='block';
				validationError.innerHTML += errorMsg;
				submit= false;
			}
			
			if(guardianAadharVal==nomineeAadhar && nomineeAadhar!=""){
				
				document.getElementById('nomineeAadhar').style.borderColor = "red";
				document.getElementById('gaurdianAadhar').style.borderColor = "red";
				errorMsg ="<br>Nominee Aadhar and Guardian Aadhar should not be same.";
				 validationError.innerHTML += errorMsg;
				 nomineeCross.style.display='block';
				submit = false;
				
			}
				
				
		}
		if(submit==false){return false}
		document.getElementById("nomineeOk").style.display='block';
		
		
		
		
		/********* ...............payoff validation starts.......... ****************/
		 
		  
		  
		  /* var beneficiaryName= document.getElementById("beneficiaryName")!=null? document.getElementById("beneficiaryName").value :"";
		  var beneficiaryAccount= document.getElementById("beneficiaryAccount")!=null? document.getElementById("beneficiaryAccount").value :"";
		  
		    

		  if(beneficiaryName ==""){
		   document.getElementById("beneficiaryName").style.borderColor = "red";
		     validationError.innerHTML += "<br>Please enter beneficiary name";
		   document.getElementById("payoffCross").style.display='block';
		   submit = false;
		   
		  }
		  if(beneficiaryAccount ==""){
		   document.getElementById("beneficiaryAccount").style.borderColor = "red";
		     validationError.innerHTML += "<br>Please enter beneficiary account";
		   document.getElementById("payoffCross").style.display='block';
		   submit = false;
		  }
		  
		  
		  if(differentBankRadio==true){
		   
		    
		      var imps= document.getElementById("imps").checked;
		   var rtgs= document.getElementById("rtgs").checked;
		   var neft= document.getElementById("neft").checked;
		   
		   if(imps ==false && rtgs ==false && neft ==false){
		    document.getElementById("imps").style.boxShadow = "0 0 5px 0px red inset";
		       document.getElementById('rtgs').style.boxShadow = "0 0 5px 0px red inset";
		       document.getElementById('neft').style.boxShadow = "0 0 5px 0px red inset";
		       validationError.innerHTML += "<br>Please choose transfer type";
		       document.getElementById("payoffCross").style.display='block';
		      submit = false;
		   }
		   
		   
		   var beneficiaryBank= document.getElementById("beneficiaryBank")!=null? document.getElementById("beneficiaryBank").value :"";
		   var beneficiaryIfsc= document.getElementById("beneficiaryIfsc")!=null? document.getElementById("beneficiaryIfsc").value :"";
		   
		    if(beneficiaryBank ==""){
		     document.getElementById("beneficiaryBank").style.borderColor = "red";
		       validationError.innerHTML += "<br>Please enter beneficiary bank";
		       document.getElementById("payoffCross").style.display='block';
		       submit = false;
		    }
		    if(beneficiaryIfsc ==""){
		     document.getElementById("beneficiaryIfsc").style.borderColor = "red";
		       validationError.innerHTML += "<br>Please enter beneficiary ifsc";
		       document.getElementById("payoffCross").style.display='block';
		       submit = false;
		    }
		  
		  
		  }
		  
		 
		  if(submit == false){return false;}
		  
		  document.getElementById("payoffOk").style.display='block'; */
		
		/************** ....................beneficiary validation starts ........... ************** */
	
	 inp = document.querySelectorAll("#beneficiaryDetails .myform-control");
		for(i=0;i<inp.length;i++){
			inp[i].style.borderColor = "#cccccc";
		}
		
		var beneficiaryCount= parseInt(window.n);
		
		if(beneficiaryCount==0){
			validationError.innerHTML += "<br>Please enter atleast one beneficiary";
		    submit = false;
		}
		else{
			
			for(var i=0;i<beneficiaryCount;i++){
				
				var benificiaryName=document.getElementById('benificiaryName'+i).value;
				var accountNo=document.getElementById('accountNo'+i).value;
				var ifscCode=document.getElementById('ifscCode'+i).value;
				var amountToTransfer=document.getElementById('amountToTransfer'+i).value;
				var remark=document.getElementById('remark'+i).value;
		
				if(benificiaryName ==""){
					document.getElementById("benificiaryName"+i).style.borderColor = "red";
					validationError.innerHTML = "<br>Please enter all the beneficiary details";
				  	submit = false;
				}
				if(accountNo ==""){
					document.getElementById("accountNo"+i).style.borderColor = "red";
					validationError.innerHTML = "<br>Please enter all the beneficiary details";
				  	submit = false;
				}
				if(ifscCode ==""){
					document.getElementById("ifscCode"+i).style.borderColor = "red";
					validationError.innerHTML = "<br>Please enter all the beneficiary details";
				  	submit = false;
				}
				if(amountToTransfer ==""){
					document.getElementById("amountToTransfer"+i).style.borderColor = "red";
					validationError.innerHTML = "<br>Please enter all the beneficiary details";
				  	submit = false;
				}
				
				if(remark ==""){
					document.getElementById("remark"+i).style.borderColor = "red";
					validationError.innerHTML = "<br>Please enter all the beneficiary details";
				  	submit = false;
				}
				
				
			}
			
		}
		
		if(submit == false){
	
		document.getElementById("beneficiaryCross").style.display='block';
		return false;}
		
		 document.getElementById("beneficiaryOk").style.display='block';
			
		
		/********........payment validation starts..........***********/
		

	 inp = document.querySelectorAll("#paymentDetails .form-control");
			for(i=0;i<inp.length;i++){
				inp[i].style.borderColor = "#cccccc";
			}
			
			
			var paymentMode = $('#paymentMode').val();

		if (paymentMode == 'Select') {
		
			document.getElementById('paymentMode').style.borderColor = "red";
			validationError.innerHTML += "<br>Please select mode of payment";
			document.getElementById("paymentCross").style.display='block';
			submit = false;
		}

		
		else if (paymentMode == 'Cheque' || paymentMode == 'DD') {

			var chequeNo = document.getElementById('chequeNo').value;
			var chequeBank = document.getElementById('chequeBank').value;
			var chequeBranch = document.getElementById('chequeBranch').value;

			if (chequeNo == "" || chequeBank == "" || chequeBranch == "") {
				
				
				if (chequeNo == "")
					document.getElementById('chequeNo').style.borderColor = "red";
				if (chequeBank == "")
					document.getElementById('chequeBank').style.borderColor = "red";
				if (chequeBranch == "")		
					document.getElementById('chequeBranch').style.borderColor = "red";
				
				validationError.innerHTML += "<br>Please enter all the payment details";
				document.getElementById("paymentCross").style.display='block';
				submit = false;
			}

		}

		else if (paymentMode == 'Card Payment') {

			var cardType = document.getElementById('cardType').value;
			var cardNo = document.getElementById('cardNo').value;
			var expiryMonth = document.getElementById('expiryMonth').value;
			var expiryYear = document.getElementById('expiryYear').value;
			var cvv = document.getElementById('cvv').value;

			if (cardType == "" || cardNo == "" || expiryMonth == ""
					|| expiryYear == "" || cvv == "") {
				
				if (cardType == "")
					document.getElementById('cardType').style.borderColor = "red";
				if (cardNo == "")
					document.getElementById('cardNo').style.borderColor = "red";
				if (expiryMonth == "")
					document.getElementById('expiryMonth').style.borderColor = "red";
				if (expiryYear == "")
					document.getElementById('expiryYear').style.borderColor = "red";
				if (cvv == "")
					document.getElementById('cvv').style.borderColor = "red";
				
				validationError.innerHTML += "<br>Please enter all the card details";
				document.getElementById("paymentCross").style.display='block';
				submit = false;
				
			}

		}

		else if (paymentMode == 'Fund Transfer') {
			var fdAmount = parseFloat(document.getElementById('fdAmount').value);
			var linkedAccountBalance = parseFloat(document.getElementById('linkedAccountBalance1').value.replace(/\,/g,""));
			
			if (linkedAccountBalance < fdAmount) {
				
				validationError.innerHTML += "<br>Insufficient balance in linked account";
				document.getElementById("paymentCross").style.display='block';
				submit = false;
			}
		}
		
		
		else if (paymentMode == 'Net Banking') {
			  
			

			    var bankType = document.getElementById('bankType').value;
			    
			     
			    if(bankType==""){
			    	document.getElementById('bankType').style.borderColor = "red";
					validationError.innerHTML += "<br>Please select Bank Type";
					document.getElementById("paymentCross").style.display='block';
					return false;
			    }
			    
			    var transferType = document.getElementById('transferType').value;
			    
			    if(document.getElementById('bankType').value!='SameBank' && transferType==""){
			    	document.getElementById('transferType').style.borderColor = "red";
					validationError.innerHTML += "<br>Please select transfer type";
					document.getElementById("paymentCross").style.display='block';
					submit = false;
			    }
			    
			    
			     var paymentBeneficiaryName = document.getElementById('paymentBeneficiaryName').value;
			     
			     if(paymentBeneficiaryName==""){
				    	document.getElementById('paymentBeneficiaryName').style.borderColor = "red";
						validationError.innerHTML += "<br>Please enter beneficiary name";
						document.getElementById("paymentCross").style.display='block';
						submit = false;
				    }
			     
			     var paymentBeneficiaryAccount = document.getElementById('paymentBeneficiaryAccount').value;
			     if(paymentBeneficiaryAccount==""){
				    	document.getElementById('paymentBeneficiaryAccount').style.borderColor = "red";
						validationError.innerHTML += "<br>Please enter beneficiary account number";
						document.getElementById("paymentCross").style.display='block';
						submit = false;
				    }
			     
			     
			     if(bankType=="DifferentBank"){
			      var paymentBeneficiaryBank = document.getElementById('paymentBeneficiaryBank').value;
			      var paymentBeneficiaryIfsc = document.getElementById('paymentBeneficiaryIfsc').value;
			      
			      if(paymentBeneficiaryBank==""){
				    	document.getElementById('paymentBeneficiaryBank').style.borderColor = "red";
						validationError.innerHTML += "<br>Please enter beneficiary bank name";
						document.getElementById("paymentCross").style.display='block';
						submit = false;
				    }
			     
			     if(paymentBeneficiaryIfsc==""){
				    	document.getElementById('paymentBeneficiaryIfsc').style.borderColor = "red";
						validationError.innerHTML += "<br>Please enter ifsc code";
						document.getElementById("paymentCross").style.display='block';
						submit = false;
				    }
			      
			      
			     }	     
			    
			
			     
			    }
		 if(window.n>0){
			    var totAmount = 0;
			
			   for(var i=0;i<window.n;i++)
			   { 
			    if (isNaN(document.getElementById('amountToTransfer'+i).value)) {
			     
			     document.getElementById('amountToTransfer'+i).style.borderColor = "red";
			     document.getElementById('amountToTransfer'+i).style.display = 'block';
			     errorMsg = "<br>Please enter transfer amount for the beneficiary.";
			     submit = false;
			    } else {
			     document.getElementById('amountToTransfer'+i).style.borderColor = "black";
			     //document.getElementById('amountToTransfer'+i).style.display = 'none';
			     totAmount = parseFloat(totAmount) + parseFloat(document.getElementById('amountToTransfer'+i).value);
			    }
			   }
			   if(submit)
			    {
			     var emiAmount = document.getElementById('interestPayAmount').value;
			     if(parseFloat(totAmount) != parseFloat(emiAmount))
			     {
			     
			      // document.getElementById('amountToTransfer0').style.borderColor = "red";
			       errorMsg = "<br>EMI amount should be the sum of all beneficiaries transfer amount.";
			          validationError.innerHTML += errorMsg;
			          submit = false;
			     }
			    }
			  }		  
	

	return submit;
	
	}

	
	
	
/* 	
		
		function showDetailsSameBank(){
			
			document.getElementById('beneficiaryNameTr').style.display='block';
			document.getElementById('accountNoTr').style.display='block';
			document.getElementById('bankNameTr').style.display='none';
			document.getElementById('ifscTr').style.display='none';
			
			document.getElementById("beneficiaryBank").value="";
			document.getElementById("beneficiaryIfsc").value="";
			document.getElementById('transferModeTr').style.display='none';
		}
		 */
	/* 	function showDetailsDiffBank(){
			
			document.getElementById('beneficiaryNameTr').style.display='block';
			document.getElementById('accountNoTr').style.display='block';
			document.getElementById('bankNameTr').style.display='block';
			document.getElementById('ifscTr').style.display='block';
			document.getElementById('transferModeTr').style.display='block';

			
		} */
	
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
		
       function hidePartPercentDiv(id){
			
			document.getElementById(id).style.display='none';
			
		}
       function displayHidePartPercent(id,id1){
    	    document.getElementById(id).style.display='block';
			document.getElementById(id1).style.display='none';
       }
       
       function showGuardian(value,id,majorId,minorId){
    	   debugger
    		
   		if(parseInt(value)<18){
   	
   			document.getElementById(id).style.display='block';
   			document.getElementById("nomineePan").value="";
   			document.getElementById("nomineDiv").style.display='none';
   			document.getElementById("guardianName").value="";
   			document.getElementById("guardianAge").value="";
   			document.getElementById("guardianAddress").value="";
   			document.getElementById("guardianRelationShip").value="";
   			
   			
   		}
   		else{
   		document.getElementById(id).style.display='none';
   		document.getElementById("nomineDiv").style.display='block';
   		
   		}
   	} 
       
       

	function changeExpiryDate() {

		document.getElementById('expiryDate').value = document
				.getElementById('expiryMonth').value
				+ "-" + document.getElementById('expiryYear').value
	}

	function showDetails(value) {

		var paymentMode = value;
		
		 if(paymentMode=='Select'){
			 document.getElementById('bankDetailsTr').style.display = 'none';
				document.getElementById('cardDetailsTr').style.display = 'none';
				document.getElementById('netBankingDetailsTr').style.display = 'none';
				document.getElementById('linkedAccountBalanceTr').style.display = 'none';
				document.getElementById('linkedAccountTr').style.display = 'none';
	        }
		var linkedAccountNo = document.getElementById('linkedAccount').value;
		
        if(value=='Net Banking'){
        	document.getElementById('paymentBeneficiaryName').style.display='block';
			document.getElementById('paymentBeneficiaryAccountDiv').style.display='block';
			
			if($('#bankType').val()=='DifferentBank'){
				document.getElementById('paymentBankNameTr').style.display='block';
				document.getElementById('paymentBeneficiaryIfscDiv').style.display='block';
				
			}
			else{
				document.getElementById('paymentBankNameTr').style.display='none';
				document.getElementById('paymentBeneficiaryIfscDiv').style.display='none';
				
			}
			
			
			
        }
		if (paymentMode == 'Fund Transfer') {
			document.getElementById('bankDetailsTr').style.display = 'none';
			document.getElementById('cardDetailsTr').style.display = 'none';
			document.getElementById('netBankingDetailsTr').style.display = 'none';

			document.getElementById('linkedAccountBalanceTr').style.display = 'block';
			document.getElementById('linkedAccountTr').style.display = 'block';
			var accArray= $('#accountNo').val().split(',');
			document.getElementById('linkedAccount').value=accArray[0];
			document.getElementById('linkedAccountBalance1').value=$('#accountBalance1').val();
			document.getElementById('linkedAccountBalance').value=parseFloat($('#accountBalance1').val()).toLocaleString("en-US");
			
			

		}

		if (paymentMode == 'Cash') {
			document.getElementById('bankDetailsTr').style.display = 'none';
			document.getElementById('cardDetailsTr').style.display = 'none';
			document.getElementById('netBankingDetailsTr').style.display = 'none';
			if (linkedAccountNo == "") {
			

			} else {
				document.getElementById('linkedAccountBalanceTr').style.display = 'none';
				document.getElementById('linkedAccountTr').style.display = 'none';

			}
		}
		if (paymentMode == 'DD') {

			document.getElementById('bankDetailsTr').style.display = 'block';
			document.getElementById('cardDetailsTr').style.display = 'none';
			document.getElementById('netBankingDetailsTr').style.display = 'none';

			/* for labels */
			document.getElementById('ddNoLabel').style.display = 'block';
			document.getElementById('ddDateLabel').style.display = 'block';
			document.getElementById('chequeNoLabel').style.display = 'none';
			document.getElementById('chequeDateLabel').style.display = 'none';

			if (linkedAccountNo == "") {
				

			} else {
				document.getElementById('linkedAccountBalanceTr').style.display = 'none';
				document.getElementById('linkedAccountTr').style.display = 'none';

			}

		}
		if (paymentMode == 'Cheque') {

			document.getElementById('bankDetailsTr').style.display = 'block';
			document.getElementById('cardDetailsTr').style.display = 'none';
			document.getElementById('netBankingDetailsTr').style.display = 'none';

			/* for labels */
			document.getElementById('ddNoLabel').style.display = 'none';
			document.getElementById('ddDateLabel').style.display = 'none';
			document.getElementById('chequeNoLabel').style.display = 'block';
			document.getElementById('chequeDateLabel').style.display = 'block';

			if (linkedAccountNo == "") {
				

			} else {
				document.getElementById('linkedAccountBalanceTr').style.display = 'none';
				document.getElementById('linkedAccountTr').style.display = 'none';

			}
		}
		if (paymentMode == 'Card Payment') {

			document.getElementById('bankDetailsTr').style.display = 'none';
			document.getElementById('netBankingDetailsTr').style.display = 'none';
			document.getElementById('cardDetailsTr').style.display = 'block';
			if (linkedAccountNo == "") {
				

			} else {
				document.getElementById('linkedAccountBalanceTr').style.display = 'none';
				document.getElementById('linkedAccountTr').style.display = 'none';

			}
		}

		if (paymentMode == 'Net Banking') {

			document.getElementById('bankDetailsTr').style.display = 'none';
			document.getElementById('cardDetailsTr').style.display = 'none';
			document.getElementById('netBankingDetailsTr').style.display = 'block';
			if (linkedAccountNo == "") {
				
			} else {
				document.getElementById('linkedAccountBalanceTr').style.display = 'none';
				document.getElementById('linkedAccountTr').style.display = 'none';

			}
		}
		
	

	}
	
	function onchangeBankType() {
		var value = document.getElementById('bankType').value;
		if(value=='')
		{
			document.getElementById('paymentBeneficiaryNameDiv').style.display='none';
			document.getElementById('paymentBeneficiaryAccountDiv').style.display='none';
			document.getElementById('paymentBankNameTr').style.display='none';
			document.getElementById('paymentBeneficiaryIfscDiv').style.display='none';
			document.getElementById('transferOption').style.display='none';
		}
		
		if(value=='SameBank')
		{
		document.getElementById('paymentBankNameTr').style.display='none';
		document.getElementById('paymentBeneficiaryIfscDiv').style.display='none';
		document.getElementById('paymentBeneficiaryNameDiv').style.display='block';
		document.getElementById('paymentBeneficiaryAccountDiv').style.display='block';
		document.getElementById('transferOption').style.display='none';
	
		}
		
		else if(value=='DifferentBank'){
			document.getElementById('paymentBeneficiaryNameDiv').style.display='block';
			document.getElementById('paymentBeneficiaryAccountDiv').style.display='block';
			document.getElementById('paymentBankNameTr').style.display='block';
			document.getElementById('paymentBeneficiaryIfscDiv').style.display='block';
			document.getElementById('transferOption').style.display='block';
		}
		
	}
	
		
	function resetBasicDetails(){
		
		document.getElementById('fdAmount').value='';
		document.getElementById('paymentAmt').value='';
		document.getElementById('currency').value='${productConfiguration.defaultCurrency}';
		document.getElementById('interestPayAmount').value='';
		
	}
	
	   function resetNomineeDetails(){
		   
		document.getElementById('nomineeName').value='';
		document.getElementById('nomineeAge').value='';
		document.getElementById('nomineeAddress').value='';
		document.getElementById('nomineeRelationShip').value='';
		document.getElementById('nomineePan').value='';
		document.getElementById('nomineeAadhar').value='';
		document.getElementById('gaurdianDiv').style.display = 'none';
	}
		
		
/* 	 function resetPayoffDetails(){
	
		 
		
		 document.getElementById('savingBankRadio').checked=false;
		 document.getElementById('differentBankRadio').checked=false;

		 document.getElementById('neft').checked=false;
		 document.getElementById('imps').checked=false;
		 document.getElementById('rtgs').checked=false;

	
		//	document.getElementById('interestPayAmount').value='';
		    document.getElementById('beneficiaryName').value='';
			document.getElementById('beneficiaryAccount').value='';
			document.getElementById('beneficiaryBank').value='';
			document.getElementById('beneficiaryIfsc').value='';
		 
	
			
			
			
	}
	 */
	 function resetBeneficiary(){
		 
			var beneficiaryCount= parseInt(window.n);
	
				for(var i=0;i<beneficiaryCount;i++){
					var j=i+1;
					document.getElementById('benificiaryName'+i).value="";
					document.getElementById('accountNo'+i).value="";
					document.getElementById('ifscCode'+i).value="";
					document.getElementById('amountToTransfer'+i).value="";
					document.getElementById('remark'+j).value="";
				}
		 
	 }
	 
	function resetPaymentDetails(){
		
		
		document.getElementById('cardDetailsTr').style.display='none';
		document.getElementById('netBankingDetailsTr').style.display='none';
		document.getElementById('bankNameTr').style.display='none';
		document.getElementById('paymentMode').value='Select';
		
		document.getElementById('bankDetailsTr').style.display = 'none';
	    document.getElementById('linkedAccountBalanceTr').style.display = 'none';
		document.getElementById('linkedAccountTr').style.display = 'none';			
		
		
		document.getElementById('cardType').value='';
	    document.getElementById('cardNo').value='';
		document.getElementById('expiryMonth').value='';
		document.getElementById('expiryYear').value='';
		document.getElementById('cvv').value='';
	 
		document.getElementById('bankType').value='';
	    document.getElementById('transferType').value='';
		document.getElementById('paymentBeneficiaryName').value='';
		document.getElementById('paymentBeneficiaryAccount').value='';
		document.getElementById('paymentBeneficiaryBank').value='';
		document.getElementById('paymentBeneficiaryIfsc').value='';
		
		
	}
	 
	 function getEMITenure(){
debugger;
		 	
		 var fdAmount= document.getElementById('fdAmount').value;
		 var emiAmt= document.getElementById('interestPayAmount').value;
		
		if($('#gestationPeriod').val()==""){
			document.getElementById('gestationPeriod').style.borderColor = "red";
			errorMsg = "<br>Please enter Gestation Period";
			validationError.innerHTML = errorMsg;
			document.getElementById('depositCross').style.display='block';
			submit = false;
			return submit;
		}else{
			document.getElementById('gestationPeriod').style.borderColor = "black";
			validationError.innerHTML = "";
		}
			if(fdAmount==""){
				document.getElementById('fdAmount').style.borderColor = "red";
				errorMsg = "<br>Please enter deposit amount";
				validationError.innerHTML += errorMsg;
				document.getElementById('depositCross').style.display='block';
				submit = false;
				return submit;
			}
			
			else{
				 fdAmount=parseFloat(fdAmount);
				 var minDepositAmount= parseInt('${productConfiguration.minimumDepositAmount}');
				   var maxFdAmount= parseInt('${productConfiguration.maximumDepositAmount}');
			   
			    
			if(minDepositAmount>fdAmount){
				document.getElementById('fdAmount').style.borderColor = "red";
				errorMsg = "<br>Minimum Deposit amount is "+minDepositAmount+".";
				validationError.innerHTML += errorMsg;
				document.getElementById('depositCross').style.display='block';
				submit = false;
				return submit;
			}
		
			if(fdAmount>maxFdAmount){
				document.getElementById('fdAmount').style.borderColor = "red";
				errorMsg = "<br>Maximum Deposit amount is "+maxFdAmount+".";
				validationError.innerHTML += errorMsg;
				
				submit = false;
				return submit;
			}
			} 
			
			if(emiAmt==""){
			//	document.getElementById('emiAmt').style.borderColor = "red";
				errorMsg = "<br>Please enter EMI amount";
				validationError.innerHTML += errorMsg;
				document.getElementById('depositCross').style.display='block';
				submit = false;
				return submit;
			}
			
			if(emiAmt>fdAmount){
			//	document.getElementById('emiAmt').style.borderColor = "red";
				errorMsg = "<br>EMI amount should be equal to deposit amount";
				validationError.innerHTML += errorMsg;
				document.getElementById('depositCross').style.display='block';
				submit = false;
				return submit;
			}
						
			    var dataString = 'fdAmount='+ fdAmount
	            + '&emiAmt=' + emiAmt;
		  
		 
		  var tenure = 0;
		  $.ajax({  
		    type: "GET", 
		    async: false,
		    url: "<%=request.getContextPath()%>/bnkEmp/getTenureForReverseEMI", 
		    contentType: "application/json",
		    dataType: "json",
		    data: dataString,
	    
		    success: function(response){  
		    	tenure = response;
			    	
			    	document.getElementById('emiTenure').value=tenure;

		    },  
		    error: function(e){  
		    	 $('#error').html("Error occured!!")
		    	document.getElementById('emiTenure').value=0;
		    }  
		  });  
			  
			
			return tenure;
			} 	
	 
	 function emiAmt(){
		var interestPayAmount =  document.getElementById('interestPayAmount').value; 
		document.getElementById('amountToTransfer0').value=interestPayAmount;
		
	 }
	 
	 
	 
	 function validationAccount(event){
				
				var minimumBalance1_ = document.getElementById(event.target.id);

					 var keycode = event.which;
				    if (!(event.shiftKey == false && (keycode == 8 || keycode == 37 ||  (keycode >= 48 && keycode <= 57)))) {
				        event.preventDefault();
				        return false;
				    } 
				   
				    
				    if (minimumBalance1_.value.length>14 && event.keyCode != 8){
				    	 event.preventDefault();
				    	return false;
				    }
				
				
				 
				}
	 
	 function validationBenificiary(event){
			
			var minimumBalance1_ = document.getElementById(event.target.id);

				 var keycode = event.which;
			    if (!(event.shiftKey == false && (keycode == 8 || keycode == 37 ||  (keycode >= 48 && keycode <= 57)))) {
			        event.preventDefault();
			        return false;
			    } 
			    if (minimumBalance1_.value.length>14 && event.keyCode != 8){
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
		
		
		
		
		function chechDepositeAmountAndEMIAmount(event,value){
			debugger
			var fdAmountValue = $("#fdAmount").val();
			var emiAmountValue = value;
			
			if(fdAmountValue==""){
				alert("Deposite amount should not be empty");
				document.getElementById("fdAmount").style.borderColor = "red";
				return false;
			}
			
			if(parseInt(emiAmountValue) > parseInt(fdAmountValue) && parseInt(fdAmountValue) > 0 && event.keyCode !=144){
				document.getElementById("fdAmount").style.borderColor = "grey";
				alert("EMI amount should not be more than Deposite Amount.");
				document.getElementById(event.target.id).style.borderColor = "red";
			}
			
			
		}
		
		
	
</script>


		<style>
input[type=checkbox], input[type=radio] {
	margin: 4px 1px 0px;
	margin-top: 1px\9;
	line-height: normal;
	zoom: 1.4;
}

.form-horizontal .control-label {
	padding-top: 0;
}
</style>