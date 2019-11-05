<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- To fetch the request url -->
<c:set var="req" value="${requestScope['javax.servlet.forward.request_uri']}" />
<c:set var="baseURL" value="${fn:split(req,'/')}" />
<link
	href="<%=request.getContextPath()%>/resources/css/datepicker.min.css"
	rel="stylesheet">
<script src="<%=request.getContextPath()%>/resources/js/validation.js"></script>

<div class="right-container" id="right-container">

<div class="container-fluid">

			<div class="Flexi_deposit">
	<form:form action="editProductWiseConfiguration"
		id="productWiseConfigurationForm" commandName="productConfiguration"
		method="post" class="form-horizontal" autocomplete="off">
		<form:hidden path="id"/>
		<div class="successMsg" style="text-align: center;">
					<b><font color="red" style="font-size: 25px;">${error}</font></b>
			</div>
		<div class="container" style="width: 100%; padding: 5px 7px;">
			<div class="row">
				<div class="col-12">
					<div class="tabs">
						<ul class="nav nav-tabs" style="width: 95%; margin-left: 12px;">
							<li class="nav-item active"><a class="nav-link"
								href="#gernaltab" data-toggle="tab"><i class="fa fa-star"></i>General
									Tab</a></li>
							<li class="nav-item"><a class="nav-link" href="#topup"
								data-toggle="tab"><i class="fa fa-star"></i>Top-up</a></li>
							<li class="nav-item"><a class="nav-link" href="#withdraw"
								data-toggle="tab"><i class="fa fa-star"></i>Withdraw</a></li>
							<li class="nav-item"><a class="nav-link" href="#penalty"
								data-toggle="tab"><i class="fa fa-star"></i>Penalty</a></li>
								<li class="nav-item"><a class="nav-link" href="#overdraft"
										data-toggle="tab"><i class="fa fa-star"></i>Overdraft</a></li>
						</ul>

						<div class="tab-content">

							<div id="gernaltab" class="tab-pane active">

								<div class="col-md-12">

									<div class="col-md-6">
										<div class="form-group" style="margin-bottom: 2px;">
											<label class="col-md-4" style="padding-top: 16px; margin-left: -5px;">Product Code<span
												style="color: red">*</span></label>
											<div class="col-md-6" style="margin-left: -24px;">
												<form:input id="productCode" placeholder=""
													path="productCode" class="myform-control"
													onkeypress="alphaNumaricValidation(event)" style="width: 105%;" />
											</div>
										</div>
										<div class="form-group" style="margin-bottom: 2px;">
											<label class="col-md-4" style="padding-top: 16px; margin-left: -5px;">Product
												Name<span style="color: red">*</span>
											</label>
											<div class="col-md-6" style="margin-left: -24px;">

												<form:input id="productName" placeholder=""
													path="productName" class="myform-control"
													onkeypress="nameValidation(event)" style="width: 105%;" />


											</div>
										</div>

										<div class="form-group" style="margin-bottom: 2px;">
											<label class="col-md-4" style="padding-top: 16px; margin-left: -5px;">Product Type<span
												style="color: red">*</span></label>
											<div class="col-md-6" style="margin-left: -24px;">
												<form:select id="productType" path="productType"
													class="myform-control" style="width: 104%;" onchange = "hideGracePeriod(this.value)">
													<form:option value="">Select</form:option>
													<form:option value="Regular Deposit">Regular Deposit</form:option>
													<form:option value="Recurring Deposit">Recurring Deposit</form:option>
													<form:option value="Annuity Deposit">Annuity Deposit</form:option>
													<form:option value="Tax Saving Deposit">Tax Saving Deposit</form:option>
													<form:option value="Gold Deposit">Gold Deposit</form:option>
												</form:select>
											</div>
										</div>

										<div class="form-group" style="margin-bottom: 2px;">
											<label class="col-md-4" style="padding-top: 16px; margin-left: -5px;">Citizen<span
												style="color: red">*</span></label>


											<div class="col-md-6" style="margin-left: -24px;">
												<form:select id="citizen" path="citizen"
													class="myform-control"
													onchange="citizenChange(this.value,this.id)" style="width: 104%;">
													<form:option value="">Select</form:option>
													<form:option value="RI">RI</form:option>
													<form:option value="NRI">NRI</form:option>
												</form:select>


											</div>
										</div>
										<div class="form-group" style="margin-bottom: 0;">
											<label class="col-md-4" style="padding-top: 16px; margin-left: -5px;">Account Type<span
												style="color: red">*</span></label>
											<div class="col-md-6" style="margin-left: -24px;">
												<form:select id="nriAccountType" path="nriAccountType"
													class="myform-control"
													onchange="accountTypeVal(this.value)" style="width: 104%;">
													<form:option value="">Select</form:option>
													<form:option value="NRE">
														<spring:message code="label.nre" />
													</form:option>
													<form:option value="NRO">
														<spring:message code="label.nro" />
													</form:option>
													<form:option value="FCNR">
														<spring:message code="label.fcnr" />
													</form:option>
													<form:option value="RFC">
														<spring:message code="label.rfc" />
													</form:option>
													<form:option value="PRP">
														<spring:message code="label.prp" />
													</form:option>
												</form:select>


											</div>
										</div>
										<div class="form-group" style="margin-bottom: -20px; margin-top: -30px;">
											<label class="col-md-4" style="padding-top: 16px; margin-left: -5px;">Min Tenure<span
												style="color: red">*</span></label>
													<div class="col-md-2" style="width: 17.5%; margin-left: -17px; padding: 6px;">
												<select id="minTenureYear" class="myform-control"
													
													name="minimumTenure">
													<option value="">Year</option>
													<option value="1 Year">1</option>
													<option value="2 Year">2</option>
													<option value="3 Year">3</option>
													<option value="4 Year">4</option>
													<option value="5 Year">5</option>
													<option value="6 Year">6</option>
													<option value="7 Year">7</option>
													<option value="8 Year">8</option>
													<option value="9 Year">9</option>
													<option value="10 Year">10</option>
													<option value="11 Year">11</option>
													<option value="12 Year">12</option>
													<option value="13 Year">13</option>
													<option value="14 Year">14</option>
													<option value="15 Year">15</option>
												</select>
											</div>
											 <div class="col-md-2" style="margin-left: -10px; width: 19%; padding: 6px;">
												<select id="minTenureMonth" class="myform-control"
													
													name="minimumTenure">
													<option value="">Month</option>
													<option value="1 Month">1</option>
													<option value="2 Month">2</option>
													<option value="3 Month">3</option>
													<option value="4 Month">4</option>
													<option value="5 Month">5</option>
													<option value="6 Month">6</option>
													<option value="7 Month">7</option>
													<option value="8 Month">8</option>
													<option value="9 Month">9</option>
													<option value="10 Month">10</option>
													<option value="11 Month">11</option>
													<option value="12 Month">12</option>
												</select>
											</div>
											
											<div class="col-md-2" style="margin-left: -10px; width: 16%; padding: 6px;">
												<select id="minTenureDay" class="myform-control"
													
													name="minimumTenure">
													<option value="">Day</option>
													<option value="1 Day">1</option>
													<option value="2 Day">2</option>
													<option value="3 Day">3</option>
													<option value="4 Day">4</option>
													<option value="5 Day">5</option>
													<option value="6 Day">6</option>
													<option value="7 Day">7</option>
													<option value="8 Day">8</option>
													<option value="9 Day">9</option>
													<option value="10 Day">10</option>
													<option value="11 Day">11</option>
													<option value="12 Day">12</option>
													<option value="13 Day">13</option>
													<option value="14 Day">14</option>
													<option value="15 Day">15</option>
													<option value="16 Day">16</option>
													<option value="17 Day">17</option>
													<option value="18 Day">18</option>
													<option value="19 Day">19</option>
													<option value="20 Day">20</option>
													<option value="21 Day">21</option>
													<option value="22 Day">22</option>
													<option value="23 Day">23</option>
													<option value="24 Day">24</option>
													<option value="25 Day">25</option>
													<option value="26 Day">26</option>
													<option value="27 Day">27</option>
													<option value="28 Day">28</option>
													<option value="29 Day">29</option>
													<option value="30 Day">30</option>
													<option value="31 Day">31</option>
												</select>

											</div>
										</div>
										<div class="form-group" style="margin-bottom: -20px; margin-top: -30px;">
											<label class="col-md-4" style="padding-top: 16px; margin-left: -5px;">Max Tenure<span
												style="color: red">*</span></label>
													<div class="col-md-2" style="width: 17.5%; margin-left: -17px; padding: 6px;">
												<select  class="myform-control"
													id="maxTenureYear"
													name="maximumTenure">
													<option value="">Year</option>
													<option value="1 Year">1</option>
													<option value="2 Year">2</option>
													<option value="3 Year">3</option>
													<option value="4 Year">4</option>
													<option value="5 Year">5</option>
													<option value="6 Year">6</option>
													<option value="7 Year">7</option>
													<option value="8 Year">8</option>
													<option value="9 Year">9</option>
													<option value="10 Year">10</option>
													<option value="11 Year">11</option>
													<option value="12 Year">12</option>
													<option value="13 Year">13</option>
													<option value="14 Year">14</option>
													<option value="15 Year">15</option>
												</select>
											</div>
											 <div class="col-md-2" style="margin-left: -10px; width: 19%; padding: 6px;">
												<select  class="myform-control"
													id="maxTenureMonth"
													name="maximumTenure">
													<option value="">Month</option>
													<option value="1 Month">1</option>
													<option value="2 Month">2</option>
													<option value="3 Month">3</option>
													<option value="4 Month">4</option>
													<option value="5 Month">5</option>
													<option value="6 Month">6</option>
													<option value="7 Month">7</option>
													<option value="8 Month">8</option>
													<option value="9 Month">9</option>
													<option value="10 Month">10</option>
													<option value="11 Month">11</option>
													<option value="12 Month">12</option>
												</select>
											</div>
											
											<div class="col-md-2" style="margin-left: -10px; width: 16%; padding: 6px;">
												<select id="maxTenureDay" class="myform-control"
													name="maximumTenure">
													<option value="">Day</option>
													<option value="1 Day">1</option>
													<option value="2 Day">2</option>
													<option value="3 Day">3</option>
													<option value="4 Day">4</option>
													<option value="5 Day">5</option>
													<option value="6 Day">6</option>
													<option value="7 Day">7</option>
													<option value="8 Day">8</option>
													<option value="9 Day">9</option>
													<option value="10 Day">10</option>
													<option value="11 Day">11</option>
													<option value="12 Day">12</option>
													<option value="13 Day">13</option>
													<option value="14 Day">14</option>
													<option value="15 Day">15</option>
													<option value="16 Day">16</option>
													<option value="17 Day">17</option>
													<option value="18 Day">18</option>
													<option value="19 Day">19</option>
													<option value="20 Day">20</option>
													<option value="21 Day">21</option>
													<option value="22 Day">22</option>
													<option value="23 Day">23</option>
													<option value="24 Day">24</option>
													<option value="25 Day">25</option>
													<option value="26 Day">26</option>
													<option value="27 Day">27</option>
													<option value="28 Day">28</option>
													<option value="29 Day">29</option>
													<option value="30 Day">30</option>
													<option value="31 Day">31</option>
												</select>

											</div>


											
										</div>
										
										<div class="form-group" style="margin-bottom: 2px;">
											<label class="col-md-4" style="padding-top: 16px; margin-left: -10px;">Product Start Date<span
												style="color: red">*</span></label>
											<div class="col-md-6" style="margin-left: -22px;">
												<form:input id="productStartDate" placeholder=""
													path="productStartDate" readonly="true"
													class="myform-control datepicker-here" style="width: 106%;" />


											</div>
										</div>
										
										<div class="form-group" style="margin-bottom: 2px;">
											<label class="col-md-4" style="padding-top: 16px; margin-left: -10px;">Product End Date<span
												style="color: red">*</span></label>
											<div class="col-md-6" style="margin-left: -22px;">
												<form:input id="productEndDate" placeholder=""
													path="productEndDate"
													class="myform-control datepicker-here" readonly="true" style="width: 106%;" />



											</div>
										</div>
										
										
										<div class="form-group" style="margin-bottom: 2px;">
											<label class="col-md-4" style="padding-top: 16px;  margin-left: -5px;">Interest
												Calculation Basis<span style="color: red">*</span>
											</label>
											<div class="col-md-6" style="margin-left: -24px;">

												<form:select id="interestCalculationBasis" style="width: 106%; margin-left:-2px"
													path="interestCalculationBasis" class="myform-control">
													<form:option value="">Select</form:option>
													<form:option value="Monthly">Monthly</form:option>
													<form:option value="Quarterly">Quarterly</form:option>
													<form:option value="HALF YEARLY">Half Yearly</form:option>
													<form:option value="Annually">Annually</form:option>
												</form:select>
												</div>
										</div>
										
										 <div class="form-group" style="margin-bottom: 2px;">
													<label class="col-md-5"
														style="padding-top: 16px; text-align: -webkit-right; margin-left: -44px;">
														Maximim Online Deposit Limit<span style="color: red">*</span>
													</label>
													<div class="col-md-6" style="margin-left: -15px;">
														<form:input id="maximumOnlineDepositLimit" placeholder=""
															path="maximumOnlineDepositLimit" class="myform-control"
															onkeypress="numberValidation(event)" />


													</div>
												</div>
										<%-- <div class="form-group" style="margin-bottom: 2px;">
											<label class="col-md-4" style="padding-top: 16px;  margin-left: -5px;">Minimum Amount Required For SweepDeposit<span
												style="color: red">*</span></label>
											<div class="col-md-6" style="margin-left: -24px;width: 53%;">
												<form:input id="minimumAmountRequiredForSweepDeposit" placeholder=""
													path="minimumAmountRequiredForSweepDeposit" class="myform-control"
													onkeypress="numberValidation(event)" />


											</div>
										</div> --%>
										
										

										
									</div>




									<div class="col-md-6" style="margin-left: -24px;">
										
										
										<div class="form-group" style="margin-bottom: 2px;">
											<label class="col-md-5" style="padding-top: 16px; text-align: -webkit-right; margin-left: -44px;">Min Deposit Amount<span
												style="color: red">*</span></label>
											<div class="col-md-6" style="margin-left: -15px;">
												<form:input id="minimumDepositAmount" placeholder=""
													path="minimumDepositAmount" class="myform-control"
													onkeypress="numberValidation(event)" />


											</div>
										</div>
										<div class="form-group" style="margin-bottom: 2px;">
											<label class="col-md-5" style="padding-top: 16px; text-align: -webkit-right; margin-left: -44px;">Max Deposit Amount<span
												style="color: red">*</span></label>
											<div class="col-md-6" style="margin-left: -15px;">
												<form:input id="maximumDepositAmount" placeholder=""
													path="maximumDepositAmount" class="myform-control"
													onkeypress="numberValidation(event)" />


											</div>
										</div>

										<div class="form-group" style="margin-bottom: 2px;">
											<label class="col-md-4" style="padding-top: 16px; text-align: -webkit-right;">Default
												Currency<span style="color: red">*</span>
											</label>
											<div class="col-md-6" style="margin-left: -15px;">
												<form:select path="defaultCurrency" id="currency"
													class="myform-control">

													<form:option value="">Select</form:option>

												</form:select>

												<script>
													if ('${defaultCurrency}' != "") {
														if ('${nriAccType}' == 'FCNR'
																|| '${nriAccType}' == 'PRP')
															populateCurrencyEditFCNR(
																	"currency",
																	"${defaultCurrency}");
														else if ('${nriAccType}' == 'RFC')
															populateCurrencyEditRFC(
																	"currency",
																	"${defaultCurrency}");
														else
															populateCurrencyEdit(
																	"currency",
																	"${defaultCurrency}");
													} else {
														if ('${nriAccType}' == 'FCNR'
																|| '${nriAccType}' == 'PRP')
															populateCurrencyFCNR("currency");
														else if ('${nriAccType}' == 'RFC')
															populateCurrencyRFC("currency");
														else
															populateCurrency("currency");
													}
												</script>


											</div>
										</div>

										<div class="form-group" style="margin-bottom: 2px;" id = "gracePeriodForRecurringPaymentDIV">
											<label class="col-md-6" style="padding-top: 16px; text-align: -webkit-right; margin-left: -86px;">Grace Period for
												Recurring<span style="color: red">*</span>
											</label>
											<div class="col-md-6" style="margin-left: -15px;">
 
												<form:select path="gracePeriodForRecurringPayment"  id="gracePeriodForRecurringPayment"
													class="myform-control">
													<form:option value="">Day</form:option>
													<form:option value="1">1</form:option>
													<form:option value="2">2</form:option>
													<form:option value="3">3</form:option>
													<form:option value="4">4</form:option>
													<form:option value="5">5</form:option>
													<form:option value="6">6</form:option>
													<form:option value="7">7</form:option>
													<form:option value="8">8</form:option>
													<form:option value="9">9</form:option>
													<form:option value="10">10</form:option>
													<form:option value="11">11</form:option>
													<form:option value="12">12</form:option>
													<form:option value="13">13</form:option>
													<form:option value="14">14</form:option>
													<form:option value="15">15</form:option>
													<form:option value="16">16</form:option>
													<form:option value="17">17</form:option>
													<form:option value="18">18</form:option>
													<form:option value="19">19</form:option>
													<form:option value="20">20</form:option>
													<form:option value="21">21</form:option>
													<form:option value="22">22</form:option>
													<form:option value="23">23</form:option>
													<form:option value="24">24</form:option>
													<form:option value="25">25</form:option>
													<form:option value="26">26</form:option>
													<form:option value="27">27</form:option>
													<form:option value="28">28</form:option>
													<form:option value="29">29</form:option>
													<form:option value="30">30</form:option>
													<form:option value="31">31</form:option>
												</form:select>
											</div>
										</div>

										<div class="form-group" style="margin-bottom: 2px;">
											<label class="col-md-6" style="padding-top: 16px; text-align: -webkit-right; margin-left: -83px;">Interest
												Compounding Basis<span style="color: red">*</span>
											</label>
											<div class="col-md-6" style="margin-left: -15px;">

												<form:select id="interestCompoundingBasis"
													path="interestCompoundingBasis" class="myform-control">
													<form:option value="">Select</form:option>
													<form:option value="Monthly">Monthly</form:option>
													<form:option value="Quarterly">Quarterly</form:option>
													<form:option value="HALF YEARLY">Half Yearly</form:option>
													<form:option value="Annually">Annually</form:option>
												</form:select>
						</div>
										</div>
										<div class="form-group" style="margin-bottom: 2px;">
											<label class="col-md-6" style="padding-top: 16px; text-align: -webkit-right; margin-left: -83px;">TDS Calculation
												Basis<span style="color: red">*</span>
											</label>
											<div class="col-md-6" style="margin-left: -15px;">


												<form:select id="tdsCalculationOnBasis"
													path="tdsCalculationOnBasis" class="myform-control">
													<form:option value="">Select</form:option>
													<form:option value="On Interest Compounding">On Interest Compounding</form:option>
													<form:option value="Payout and Withdraw">Payout and Withdraw</form:option>
													<form:option value="on Both">on Both</form:option>

												</form:select>


											</div>
										</div>
										<div class="form-group" style="margin-bottom: 2px;">
											<label class="col-md-6" style="padding-top: 16px; text-align: -webkit-right; margin-left: -83px;">TDS Rate for No PAN<span
												style="color: red">*</span></label>
											<div class="col-md-6" style="margin-left: -15px;">
												<form:input id="tdsPercentForNoPAN" placeholder=""
													path="tdsPercentForNoPAN" class="myform-control"
													onkeypress="amountValidation(event)" />


											</div>
										</div>

										<div class="form-group" style="margin-bottom: 2px;">
											<label class="col-md-6" style="padding-top: 6px; text-align: -webkit-right; margin-left: -83px;">Contribution for
												Account<br>Holders Required<span style="color: red">*</span>
											</label>
											<div class="col-md-6" style="margin-left: -15px;">


												<form:select path="contributionRequiredForJointAccHolders"
													class="myform-control">
													<form:option value="0">No</form:option>
													<form:option value="1">Yes</form:option>

												</form:select>


											</div>
										</div>
										<div class="form-group" style="margin-bottom: 2px;">
											<label class="col-md-6" style="padding-top: 6px; text-align: -webkit-right; margin-left: -84px;">Nominee Required
												For Each Account Holder<span style="color: red">*</span>
											</label>
											<div class="col-md-6" style="margin-left: -15px;">

												<form:select path="isNomineeMandatory"
													class="myform-control">
												 <form:option value="0">No</form:option>
													<form:option value="1">Yes</form:option>

												</form:select>


											</div>
										</div>
										
										
								<div class="form-group" style="margin-bottom: 2px;">
													<label class="col-md-5"
														style="padding-top: 16px; text-align: -webkit-right; margin-left: -44px;">Payment Mode for Maturity/Payout<span style="color: red">*</span>
													</label>
													<div class="col-md-6" style="margin-left: -15px;">
														<select id="paymentModeOnMaturity" 
															name="paymentModeOnMaturity" class="myform-control">
															<option value="Fund Transfer">Fund Transfer</option>
															<option value="DD">DD</option>
															<option value="Cheque">Cheque</option>
															


														</select>


													</div>
												</div>
										
										
										<%-- <div class="form-group" style="margin-bottom: 2px;">
											<label class="col-md-5" style="padding-top: 16px; text-align: -webkit-right; margin-left: -44px;">Minimum Saving Balance For Sweep Deposit<span
												style="color: red">*</span></label>
											<div class="col-md-6" style="margin-left: -15px;">
												<form:input id="minimumSavingBalanceForSweepDeposit" placeholder=""
													path="minimumSavingBalanceForSweepDeposit" class="myform-control"
													onkeypress="numberValidation(event)" />


											</div>
										</div>  --%>
										

									</div>



								</div>

							</div>
							<div id="overdraft" class="tab-pane">
									 <div class="col-md-12">

												<div class="form-group" style="margin-bottom: 2px;">
													<label class="col-md-4"
														style="padding-top: 16px; text-align: -webkit-right;">Is
														Overdraft Facility Allowed<span style="color: red">*</span>
													</label>
												
													
													<div class="col-md-6">
														<form:select path="isOverdraftFacilityAvailable" class="myform-control">
															<form:option value="1">Yes</form:option>
															<form:option value="0">No</form:option>
														</form:select>
                                                    </div>

												</div>
												
												<h3 align="center"> Overdraft Limit In Bank </h3>
												
												
												<div class="form-group" style="margin-bottom: 2px;">
													<label class="col-md-4"
														style="padding-top: 6px; text-align: -webkit-right;">Overdraft<span style="color: red"></span>
													</label>
													<div class="col-md-6">

														<input id="overdraftPercentage" type="radio" name="online"
															value="overdraftPercentage"> In Percentage (%)<br> <input
															id="overdraftAmount" type="radio" name="online" value="overdraftAmount"
															checked="true"> In Amount


													</div>
												</div>
												
												<div id="overdraftPercent">
												<div class="form-group"  id ="minimumOverdraftPercentageDiv" style="margin-bottom: 2px;">
													<label class="col-md-5"
														style="padding-top: 16px; text-align: -webkit-right; margin-left: -44px;">Min
													Limit	Overdraft Percentage<span style="color: red">*</span>
													</label>
													<div class="col-md-6">
														<form:input  id="minimumOverdraftPercentage" path="minimumOverdraftPercentage"
															class="myform-control"
															onkeypress="amountValidation(event)" />



													
													</div>
													</div>
													<div class="form-group" id="maximumOverdraftPercentageDiv" style="margin-bottom: 2px;">
													<label class="col-md-4"
														style="padding-top: 6px; text-align: -webkit-right;">Maximum
														Limit of Overdraft Percentage<span style="color: red">*</span>
													</label>
													<div class="col-md-6">
														<form:input id="maximumOverdraftPercentage" placeholder=""
															path="maximumOverdraftPercentage" class="myform-control"
															onkeypress="amountValidation(event)" />


													</div>
												</div>
												<div class="form-group" id="defaultOverdraftPercentageDiv" style="margin-bottom: 2px;">
													<label class="col-md-4"
														style="padding-top: 6px; text-align: -webkit-right;">Default
														Overdraft Percentage<span style="color: red"></span>
													</label>
													<div class="col-md-6">
														<form:input id="defaultOverdraftPercentage" placeholder=""
															path="defaultOverdraftPercentage" class="myform-control"
															onkeypress="amountValidation(event)" />


													</div>
												</div>
												</div>
												<div id="overdraftAmount1" style="display:none">
												<div class="form-group"  id="minimumOverdraftAmountDiv" style="margin-bottom: 2px;">
													<label class="col-md-5"
														style="padding-top: 16px; text-align: -webkit-right; margin-left: -44px;">Minimun
													Limit	Overdraft Amount<span style="color: red">*</span>
													</label>
													<div class="col-md-6">
														<form:input id="minimumOverdraftAmount" path="minimumOverdraftAmount"
															class="myform-control"
															onkeypress="amountValidation(event)" />



													
													</div>
													</div>
													<div class="form-group" id="maximumOverdraftAmountDiv" style="margin-bottom: 2px;">
													<label class="col-md-4"
														style="padding-top: 6px; text-align: -webkit-right;">Maximum
														Limit of Overdraft Amount<span style="color: red">*</span>
													</label>
													<div class="col-md-6">
														<form:input id="maximumOverdraftAmount" placeholder=""
															path="maximumOverdraftAmount" class="myform-control"
															onkeypress="amountValidation(event)" />


													</div>
												</div>
												<div class="form-group" id="defaultOverdraftAmountDiv" style="margin-bottom: 2px;">
													<label class="col-md-4"
														style="padding-top: 6px; text-align: -webkit-right;">Default
														Overdraft Amount<span style="color: red"></span>
													</label>
													<div class="col-md-6">
														<form:input id="defaultOverdraftAmount" placeholder=""
															path="defaultOverdraftAmount" class="myform-control"
															onkeypress="amountValidation(event)" />


													</div>
												</div>
												</div>
												<h3 align="center">Online Overdraft Limit </h3>
												
												<div class="form-group" style="margin-bottom: 2px;">
													<label class="col-md-4"
														style="padding-top: 6px; text-align: -webkit-right;">Overdraft<span style="color: red"></span>
													</label>
													<div class="col-md-6">

														<input id="onlineOverdraftPercentage" type="radio" name="bank"
															value="onlineOverdraftPercentage"> In Percentage (%)<br> <input
															id="onlineOverdraftAmount" type="radio" name="bank" value="onlineOverdraftAmount"
															checked="true"> In Amount


													</div>
												</div>
												
												
												<div id="onlineDraftPercent">
												<div id="onlineMinimumOverdraftPercentageDiv" class="form-group" style="margin-bottom: 2px;">
													<label class="col-md-5"
														style="padding-top: 16px; text-align: -webkit-right; margin-left: -44px;">Online Min
													Limit	Overdraft Percentage<span style="color: red">*</span>
													</label>
													<div class="col-md-6">
														<form:input id="onlineMinimumOverdraftPercentage" path="onlineMinimumOverdraftPercentage"
															class="myform-control"
															onkeypress="amountValidation(event)" />



													
													</div>
													</div>
													<div id="onlineMaximumOverdraftPercentageDiv" class="form-group" style="margin-bottom: 2px;">
													<label class="col-md-4"
														style="padding-top: 6px; text-align: -webkit-right;">Online Maximum
														Limit of Overdraft Percentage<span style="color: red">*</span>
													</label>
													<div class="col-md-6">
														<form:input id="onlineMaximumOverdraftPercentage" placeholder=""
															path="onlineMaximumOverdraftPercentage" class="myform-control"
															onkeypress="amountValidation(event)" />


													</div>
												</div>
												<div id="onlineDefaultOverdraftPercentageDiv" class="form-group" style="margin-bottom: 2px;">
													<label class="col-md-4"
														style="padding-top: 6px; text-align: -webkit-right;">Online Default
														Overdraft Percentage <span style="color: red"></span>
													</label>
													<div class="col-md-6">
														<form:input id="onlineDefaultOverdraftPercentage" placeholder=""
															path="onlineDefaultOverdraftPercentage" class="myform-control"
															onkeypress="amountValidation(event)" />


													</div>
												</div>
												</div>
												<div style="display:none;" id="onlineDraftAmount">
												<div id="onlineMinimumOverdraftAmountDiv" class="form-group" style="margin-bottom: 2px;">
													<label class="col-md-5"
														style="padding-top: 16px; text-align: -webkit-right; margin-left: -44px;">Online Minimun
													Limit	Overdraft Amount<span style="color: red">*</span>
													</label>
													<div class="col-md-6">
														<form:input  id="onlineMinimumOverdraftAmount"  path="onlineMinimumOverdraftAmount"
															class="myform-control"
															onkeypress="amountValidation(event)" />



													
													</div>
													</div>
													<div id="onlineMaximumOverdraftAmountDiv" class="form-group" style="margin-bottom: 2px;">
													<label class="col-md-4"
														style="padding-top: 6px; text-align: -webkit-right;">Online Maximum
														Limit of Overdraft Amount<span style="color: red">*</span>
													</label>
													<div class="col-md-6">
														<form:input id="onlineMaximumOverdraftAmount" placeholder=""
															path="onlineMaximumOverdraftAmount" class="myform-control"
															onkeypress="amountValidation(event)" />


													</div>
												</div>
												<div id="onlineDefaultOverdraftAmountDiv" class="form-group" style="margin-bottom: 2px;">
													<label class="col-md-4"
														style="padding-top: 6px; text-align: -webkit-right;">Online Default
														Overdraft Amount<span style="color: red"></span>
													</label>
													<div class="col-md-6">
														<form:input id="onlineDefaultOverdraftAmount" placeholder=""
															path="onlineDefaultOverdraftAmount" class="myform-control"
															onkeypress="amountValidation(event)" />


													</div>
												</div>
												</div>
												
												<div class="form-group" style="margin-bottom: 2px;">
													<label class="col-md-4"
														style="padding-top: 6px; text-align: -webkit-right;">Interest Rate higher than the Deposit Rate in %<span style="color: red">*</span>
													</label>
													<div class="col-md-6">
														<form:input id="higherInterestRate" placeholder=""
															path="higherInterestRate" class="myform-control"
															onkeypress="amountValidation(event)" />


													</div>
												</div>
												</div> 
											
									</div>
							
							<div id="topup" class="tab-pane">


								<div class="col-md-12">

									<div class="col-md-6">
										<div class="form-group" style="margin-bottom: 2px;">
											<label class="col-md-4" style="padding-top: 16px; text-align: -webkit-right;">Is Top Allowed<span
												style="color: red">*</span></label>
											<div class="col-md-6">
												<form:select path="isTopupAllowed" class="myform-control" onchange="disableRemaningFields(event)">
													<form:option value="1">Yes</form:option>
													<form:option value="0">No</form:option>


												</form:select>


											</div>
										</div>
										<div class="form-group" style="margin-bottom: 2px;">
											<label class="col-md-5" style="padding-top: 16px; text-align: -webkit-right; margin-left: -44px;">Min Top-up Amount<span
												style="color: red"></span></label>
											<div class="col-md-6">
												<form:input path="minimumTopupAmount" class="myform-control"
													onkeypress="numberValidation(event)" />



											</div>
										</div>

										<div class="form-group" style="margin-bottom: 2px;">
											<label class="col-md-4" style="padding-top: 6px; text-align: -webkit-right;">Locking Period For
												Top up<span style="color: red"></span>
											</label>
											<div class="col-md-3" style="width: 21%;">
												<form:select id="LPYearSelect" class="myform-control"
													path="lockingPeriodForTopup">
													
													<form:option value="">Year</form:option>
													<form:option value="1 Year">1</form:option>
													<form:option value="2 Year">2</form:option>
													<form:option value="3 Year">3</form:option>
													<form:option value="4 Year">4</form:option>
													<form:option value="5 Year">5</form:option>
													<form:option value="6 Year">6</form:option>
													<form:option value="7 Year">7</form:option>
													<form:option value="8 Year">8</form:option>
													<form:option value="9 Year">9</form:option>
													<form:option value="10 Year">10</form:option>
													<form:option value="11 Year">11</form:option>
													<form:option value="12 Year">12</form:option>
													<form:option value="13 Year">13</form:option>
													<form:option value="14 Year">14</form:option>
													<form:option value="15 Year">15</form:option>
												</form:select>
											</div>
											<div class="col-md-3" style="width: 22.5%; margin-left: -25px;">
												<form:select id="LPMonthSelect" class="myform-control"
													path="lockingPeriodForTopup">
													<form:option value="">Month</form:option>
													<form:option value="1 Month">1</form:option>
													<form:option value="2 Month">2</form:option>
													<form:option value="3 Month">3</form:option>
													<form:option value="4 Month">4</form:option>
													<form:option value="5 Month">5</form:option>
													<form:option value="6 Month">6</form:option>
													<form:option value="7 Month">7</form:option>
													<form:option value="8 Month">8</form:option>
													<form:option value="9 Month">9</form:option>
													<form:option value="10 Month">10</form:option>
													<form:option value="11 Month">11</form:option>
													<form:option value="12 Month">12</form:option>
												</form:select>
											</div>
											<div class="col-md-3" style="width: 20%; margin-left: -25px;">
												<form:select id="LPDaySelect" class="myform-control"
													path="lockingPeriodForTopup">
													<form:option value="">Day</form:option>
													<form:option value="1 Day">1</form:option>
													<form:option value="2 Day">2</form:option>
													<form:option value="3 Day">3</form:option>
													<form:option value="4 Day">4</form:option>
													<form:option value="5 Day">5</form:option>
													<form:option value="6 Day">6</form:option>
													<form:option value="7 Day">7</form:option>
													<form:option value="8 Day">8</form:option>
													<form:option value="9 Day">9</form:option>
													<form:option value="10 Day">10</form:option>
													<form:option value="11 Day">11</form:option>
													<form:option value="12 Day">12</form:option>
													<form:option value="13 Day">13</form:option>
													<form:option value="14 Day">14</form:option>
													<form:option value="15 Day">15</form:option>
													<form:option value="16 Day">16</form:option>
													<form:option value="17 Day">17</form:option>
													<form:option value="18 Day">18</form:option>
													<form:option value="19 Day">19</form:option>
													<form:option value="20 Day">20</form:option>
													<form:option value="21 Day">21</form:option>
													<form:option value="22 Day">22</form:option>
													<form:option value="23 Day">23</form:option>
													<form:option value="24 Day">24</form:option>
													<form:option value="25 Day">25</form:option>
													<form:option value="26 Day">26</form:option>
													<form:option value="27 Day">27</form:option>
													<form:option value="28 Day">28</form:option>
													<form:option value="29 Day">29</form:option>
													<form:option value="30 Day">30</form:option>
													<form:option value="31 Day">31</form:option>
												</form:select>

											</div>


										</div>



									</div>
									<div class="col-md-6">
										<div class="form-group" style="margin-bottom: 2px;">
											<label class="col-md-5" style="padding-top: 16px; text-align: -webkit-right;">Max Top-up Amount<span
												style="color: red"></span></label>
											<div class="col-md-6">
												<form:input path="maximumTopupAmount" class="myform-control"
													onkeypress="numberValidation(event)" />
											</div>
										</div>
										<%-- <div class="form-group" style="margin-bottom: 2px;">
											<label class="col-md-5" style="padding-top: 16px; text-align: -webkit-right;">Is Interest Adj
												Required<span style="color: red"></span>
											</label>
											<div class="col-md-6">
												<form:select path="isInterestAdjustmentRequiredForTopup"
													class="myform-control">
													<form:option value="0">No</form:option>
													<form:option value="1">Yes</form:option>

												</form:select>

											</div>
										</div> --%>

										<div class="form-group" style="margin-bottom: 2px;">
											<label class="col-md-5" style="padding-top: 16px; text-align: -webkit-right;">Prevention of Top-up Before Maturity<span style="color: red"></span>
											</label>
											<div class="col-md-6">
												<select id = "preventionOfTopUpBeforeMaturity" name="preventionOfTopUpBeforeMaturity"
													class="myform-control">
													<option value="">Day</option>
													<option value="1">1</option>
													<option value="2">2</option>
													<option value="3">3</option>
													<option value="4">4</option>
													<option value="5">5</option>
													<option value="6">6</option>
													<option value="7">7</option>
													<option value="8">8</option>
													<option value="9">9</option>
													<option value="10">10</option>
													<option value="11">11</option>
													<option value="12">12</option>
													<option value="13">13</option>
													<option value="14">14</option>
													<option value="15">15</option>
													<option value="16">16</option>
													<option value="17">17</option>
													<option value="18">18</option>
													<option value="19">19</option>
													<option value="20">20</option>
													<option value="21">21</option>
													<option value="22">22</option>
													<option value="23">23</option>
													<option value="24">24</option>
													<option value="25">25</option>
													<option value="26">26</option>
													<option value="27">27</option>
													<option value="28">28</option>
													<option value="29">29</option>
													<option value="30">30</option>
													<option value="31">31</option>
												</select>

											</div>
										</div>
										  <div class="form-group" style="margin-bottom: 2px;">
													<label class="col-md-5"
														style="padding-top: 16px; text-align: -webkit-right; margin-left: -44px;">
														Incremental Payment<span style="color: red">*</span>
													</label>
													<div class="col-md-6" style="margin-left: -15px;">
														<form:input id="incrementalPayment" placeholder=""
															path="incrementalPayment" class="myform-control"
															onkeypress="numberValidation(event)" />


													</div>
												</div>
									</div>


								</div>

							</div>
							<div id="penalty" class="tab-pane">

								<div class="col-md-12">

									<div class="col-md-6">
										<%-- <div class="form-group" style="margin-bottom: 2px;">
											<label class="col-md-4" style="padding-top: 16px; text-align: -webkit-right;">Premature Closing<span
												style="color: red">*</span></label>
											<div class="col-md-6">
												<form:input id="penaltyforPrematureClosing" placeholder=""
													path="penaltyforPrematureClosing" class="myform-control"
													onkeypress="amountValidation(event)" />


											</div>
										</div> --%>
										
										<div class="form-group" style="margin-bottom: 2px;">
											<label class="col-md-4" style="padding-top: 6px; text-align: -webkit-right;">Penalty<span style="color: red"></span>
											</label>
											<div class="col-md-6">
											
												<input id="percentage" type="radio" name="same" value="percentage"> Percent (%) <br>
												<input id = "amount" type="radio" name="same" value="amount"> Penalty Amount


											</div>
										</div>
										<div id = "penaltyAmountDIV" class="form-group" style="margin-bottom: 2px;">
											<label class="col-md-4" style="padding-top: 6px; text-align: -webkit-right;">Penalty Amount for
												Top-up<span style="color: red">*</span>
											</label>
											<div class="col-md-6">
												<form:input id="penaltyAmountForTopup" placeholder=""
													path="penaltyAmountForTopup" class="myform-control"
													onkeypress="amountValidation(event)" />


											</div>
										</div>
										<div id = "penaltyPercentDIV" class="form-group" style="margin-bottom: 2px;">
											<label class="col-md-4" style="padding-top: 6px; text-align: -webkit-right;">Penalty Percent for
												Top-up<span style="color: red"></span>
											</label>
											<div class="col-md-6">
												<form:input id="penaltyPercentForTopup" placeholder=""
													path="penaltyPercentForTopup" class="myform-control"
													onkeypress="amountValidation(event)" />


											</div>
										</div>
										<div class="form-group" style="margin-bottom: 2px;">
											<label class="col-md-4" style="padding-top: 6px; text-align: -webkit-right;">Non Penalty Period<span style="color: red"></span>
											</label>
											<div class="col-md-2" style="width: 21%; margin-left: -15px;">
												<select id ="nonPenaltyPeriodForTopupYear" class="myform-control" style="margin-left: 15px"
													name="nonPenaltyPeriodForTopup">
													<option value="">Year</option>
													<option value="1 Year">1</option>
													<option value="2 Year">2</option>
													<option value="3 Year">3</option>
													<option value="4 Year">4</option>
													<option value="5 Year">5</option>
													<option value="6 Year">6</option>
													<option value="7 Year">7</option>
													<option value="8 Year">8</option>
													<option value="9 Year">9</option>
													<option value="10 Year">10</option>
													<option value="11 Year">11</option>
													<option value="12 Year">12</option>
													<option value="13 Year">13</option>
													<option value="14 Year">14</option>
													<option value="15 Year">15</option>
												</select>
											</div>
											 <div class="col-md-2" style="width: 22.5%; margin-left: -25px;">
												<select  id ="nonPenaltyPeriodForTopupMonth"  class="myform-control"
													name="nonPenaltyPeriodForTopup" style="margin-left: 15px">
													<option value="">Month</option>
													<option value="1 Month">1</option>
													<option value="2 Month">2</option>
													<option value="3 Month">3</option>
													<option value="4 Month">4</option>
													<option value="5 Month">5</option>
													<option value="6 Month">6</option>
													<option value="7 Month">7</option>
													<option value="8 Month">8</option>
													<option value="9 Month">9</option>
													<option value="10 Month">10</option>
													<option value="11 Month">11</option>
													<option value="12 Month">12</option>
												</select>
											</div>
											
											<div class="col-md-2" style="width: 21%; margin-left: -25px;">
												<select  id ="nonPenaltyPeriodForTopupDay"   class="myform-control"
													name="nonPenaltyPeriodForTopup" style="margin-left: 15px">
													<option value="">Day</option>
													<option value="1 Day">1</option>
													<option value="2 Day">2</option>
													<option value="3 Day">3</option>
													<option value="4 Day">4</option>
													<option value="5 Day">5</option>
													<option value="6 Day">6</option>
													<option value="7 Day">7</option>
													<option value="8 Day">8</option>
													<option value="9 Day">9</option>
													<option value="10 Day">10</option>
													<option value="11 Day">11</option>
													<option value="12 Day">12</option>
													<option value="13 Day">13</option>
													<option value="14 Day">14</option>
													<option value="15 Day">15</option>
													<option value="16 Day">16</option>
													<option value="17 Day">17</option>
													<option value="18 Day">18</option>
													<option value="19 Day">19</option>
													<option value="20 Day">20</option>
													<option value="21 Day">21</option>
													<option value="22 Day">22</option>
													<option value="23 Day">23</option>
													<option value="24 Day">24</option>
													<option value="25 Day">25</option>
													<option value="26 Day">26</option>
													<option value="27 Day">27</option>
													<option value="28 Day">28</option>
													<option value="29 Day">29</option>
													<option value="30 Day">30</option>
													<option value="31 Day">31</option>
												</select>

											</div>
											
										</div>
										<!-- <div class="form-group" style="margin-bottom: 2px;">
											<label class="col-md-4" style="padding-top: 6px; text-align: -webkit-right;">Penalty for Non Slab Based Withdraw<span style="color: red"></span>
											</label>
											<div class="col-md-6">
												<input id = "percentageWithdraw" type="radio" name="withdraw" value="percentageWithdraw"> In Percent (%) <br>
												<input id = "amountWithdraw" type="radio" name="withdraw" value="amountWithdraw"  checked="true"> In Amount
											</div>
										</div> -->

										<%-- <div id  = "nonSlabPercentWithdrawDIV" class="form-group" style="margin-bottom: 2px;">
											<label class="col-md-4" style="padding-top: 6px; text-align: -webkit-right;">
												Penalty in %<span style="color: red">*</span>
											</label>
											<div class="col-md-6">
												<form:input id="penaltyPercentForNonSlabBasedWithdraw"
													placeholder="" path="penaltyPercentForNonSlabBasedWithdraw"
													class="myform-control" onkeypress="amountValidation(event)" />


											</div>
										</div> --%>

										<%-- <div id  = "nonSlabAmountWithdrawDIV" class="form-group" style="margin-bottom: 2px;">
											<label class="col-md-4" style="padding-top: 6px; text-align: -webkit-right;">
												Penalty in Amount<span style="color: red">*</span>
											</label>
											<div class="col-md-6">
												<form:input id="penaltyAmountForNonSlabBasedWithdraw"
													placeholder="" path="penaltyAmountForNonSlabBasedWithdraw"
													class="myform-control" />


											</div>
										</div> --%>
										
										<!-- <div class="form-group" style="margin-bottom: 2px;">
											<label class="col-md-4" style="padding-top: 6px; text-align: -webkit-right;">Non Penalty Period<span style="color: red"></span>
											</label>
											<div class="col-md-2" style="width: 21%; margin-left: -15px;">
												<select id = "nonPenaltyPeriodForWithdrawYear" class="myform-control" style="margin-left: 15px"
													name="nonPenaltyPeriodForWithdraw">
													<option value="">Year</option>
													<option value="1 Year">1</option>
													<option value="2 Year">2</option>
													<option value="3 Year">3</option>
													<option value="4 Year">4</option>
													<option value="5 Year">5</option>
													<option value="6 Year">6</option>
													<option value="7 Year">7</option>
													<option value="8 Year">8</option>
													<option value="9 Year">9</option>
													<option value="10 Year">10</option>
													<option value="11 Year">11</option>
													<option value="12 Year">12</option>
													<option value="13 Year">13</option>
													<option value="14 Year">14</option>
													<option value="15 Year">15</option>
												</select>
											</div>
											 <div class="col-md-2" style="width: 22.5%; margin-left: -25px;">
												<select id = "nonPenaltyPeriodForWithdrawMonth"  class="myform-control"
													name="nonPenaltyPeriodForWithdraw" style="margin-left: 15px">
													<option value="">Month</option>
													<option value="1 Month">1</option>
													<option value="2 Month">2</option>
													<option value="3 Month">3</option>
													<option value="4 Month">4</option>
													<option value="5 Month">5</option>
													<option value="6 Month">6</option>
													<option value="7 Month">7</option>
													<option value="8 Month">8</option>
													<option value="9 Month">9</option>
													<option value="10 Month">10</option>
													<option value="11 Month">11</option>
													<option value="12 Month">12</option>
												</select>
											</div>
											
											<div class="col-md-2" style="width: 21%; margin-left: -25px;">
												<select id = "nonPenaltyPeriodForWithdrawDay" class="myform-control"
													name="nonPenaltyPeriodForWithdraw" style="margin-left: 15px">
													<option value="">Day</option>
													<option value="1 Day">1</option>
													<option value="2 Day">2</option>
													<option value="3 Day">3</option>
													<option value="4 Day">4</option>
													<option value="5 Day">5</option>
													<option value="6 Day">6</option>
													<option value="7 Day">7</option>
													<option value="8 Day">8</option>
													<option value="9 Day">9</option>
													<option value="10 Day">10</option>
													<option value="11 Day">11</option>
													<option value="12 Day">12</option>
													<option value="13 Day">13</option>
													<option value="14 Day">14</option>
													<option value="15 Day">15</option>
													<option value="16 Day">16</option>
													<option value="17 Day">17</option>
													<option value="18 Day">18</option>
													<option value="19 Day">19</option>
													<option value="20 Day">20</option>
													<option value="21 Day">21</option>
													<option value="22 Day">22</option>
													<option value="23 Day">23</option>
													<option value="24 Day">24</option>
													<option value="25 Day">25</option>
													<option value="26 Day">26</option>
													<option value="27 Day">27</option>
													<option value="28 Day">28</option>
													<option value="29 Day">29</option>
													<option value="30 Day">30</option>
													<option value="31 Day">31</option>
												</select>

											</div>
											
										</div> -->

									</div>
									<div class="col-md-6">

										<div class="form-group" style="margin-bottom: 2px;">
											<label class="col-md-4" style="padding-top: 16px; text-align: -webkit-right;">Modification Basis<span
												style="color: red">*</span></label>
											<div class="col-md-6">



												<form:select id="modificationBasis" path="modificationBasis"
													class="myform-control">
													<form:option value="">Select</form:option>

													<form:option value="Monthly">Monthly</form:option>

													<form:option value="Quarterly">Quarterly</form:option>

													<form:option value="Half Yearly">Half Yearly</form:option>

													<form:option value="Yearly">Yearly</form:option>

													<form:option value="Full Tenure">Full Tenure</form:option>

												</form:select>


											</div>
										</div>
										<div class="form-group" style="margin-bottom: 2px;">
											<label class="col-md-4" style="padding-top: 6px; text-align: -webkit-right;">Maximum Limit of
												Modification<span style="color: red">*</span>
											</label>
											<div class="col-md-6">
												<form:input id="maximumLimitOfModification" placeholder=""
													path="maximumLimitOfModification" class="myform-control"
													onkeypress="numberValidation(event)" />


											</div>
										</div>


										<div class="form-group" style="margin-bottom: 2px;">
											<label class="col-md-4" style="padding-top: 6px; text-align: -webkit-right;">Modification Penalty<span style="color: red"></span>
											</label>
											<div class="col-md-6">
												<input id = "percentageModification" type="radio" name="modification" value="percentModification"> In Percent (%) <br>
												<input id = "amountModification" type="radio" name="modification" value="amountModification"  checked="true"> In Amount
											</div>
										</div>

										<div id = "percentModificationDIV" class="form-group" style="margin-bottom: 2px;display: none;">
											<label class="col-md-4" style="padding-top: 6px; text-align: -webkit-right;">
												Penalty in %<span style="color: red">*</span>
											</label>
											<div class="col-md-6">
												<form:input id="modificationPenaltyPercent" placeholder=""
													path="modificationPenaltyPercent" class="myform-control"
													onkeypress="amountValidation(event)" />


											</div>
										</div>

										<div id = "amountModificationDIV" class="form-group" style="margin-bottom: 2px;">
											<label class="col-md-4" style="padding-top: 6px; text-align: -webkit-right;">Penalty in
												Amount<span style="color: red">*</span>
											</label>
											<div class="col-md-6">
												<form:input id="modificationPenaltyFlatAmount"
													placeholder="" path="modificationPenaltyFlatAmount"
													class="myform-control" onkeypress="amountValidation(event)" />


											</div>
										</div>
										
										<div class="form-group" style="margin-bottom: 2px;">
											<label class="col-md-4" style="padding-top: 6px; text-align: -webkit-right;">Non Penalty Period<span style="color: red"></span>
											</label>
											<div class="col-md-2" style="width: 21%; margin-left: -15px;">
												<select id= "nonPenaltyPeriodForModificationYear"  class="myform-control" style="margin-left: 15px"
													name="nonPenaltyPeriodForModification">
													<option value="">Year</option>
													<option value="1 Year">1</option>
													<option value="2 Year">2</option>
													<option value="3 Year">3</option>
													<option value="4 Year">4</option>
													<option value="5 Year">5</option>
													<option value="6 Year">6</option>
													<option value="7 Year">7</option>
													<option value="8 Year">8</option>
													<option value="9 Year">9</option>
													<option value="10 Year">10</option>
													<option value="11 Year">11</option>
													<option value="12 Year">12</option>
													<option value="13 Year">13</option>
													<option value="14 Year">14</option>
													<option value="15 Year">15</option>
												</select>
											</div>
											 <div class="col-md-2" style="width: 22.5%; margin-left: -25px;">
												<select id= "nonPenaltyPeriodForModificationMonth"  class="myform-control"
													name="nonPenaltyPeriodForModification" style="margin-left: 15px">
													<option value="">Month</option>
													<option value="1 Month">1</option>
													<option value="2 Month">2</option>
													<option value="3 Month">3</option>
													<option value="4 Month">4</option>
													<option value="5 Month">5</option>
													<option value="6 Month">6</option>
													<option value="7 Month">7</option>
													<option value="8 Month">8</option>
													<option value="9 Month">9</option>
													<option value="10 Month">10</option>
													<option value="11 Month">11</option>
													<option value="12 Month">12</option>
												</select>
											</div>
											
											<div class="col-md-2" style="width: 21%; margin-left: -25px;">
												<select id= "nonPenaltyPeriodForModificationDay" class="myform-control"
													name="nonPenaltyPeriodForModification" style="margin-left: 15px">
													<option value="">Day</option>
													<option value="1 Day">1</option>
													<option value="2 Day">2</option>
													<option value="3 Day">3</option>
													<option value="4 Day">4</option>
													<option value="5 Day">5</option>
													<option value="6 Day">6</option>
													<option value="7 Day">7</option>
													<option value="8 Day">8</option>
													<option value="9 Day">9</option>
													<option value="10 Day">10</option>
													<option value="11 Day">11</option>
													<option value="12 Day">12</option>
													<option value="13 Day">13</option>
													<option value="14 Day">14</option>
													<option value="15 Day">15</option>
													<option value="16 Day">16</option>
													<option value="17 Day">17</option>
													<option value="18 Day">18</option>
													<option value="19 Day">19</option>
													<option value="20 Day">20</option>
													<option value="21 Day">21</option>
													<option value="22 Day">22</option>
													<option value="23 Day">23</option>
													<option value="24 Day">24</option>
													<option value="25 Day">25</option>
													<option value="26 Day">26</option>
													<option value="27 Day">27</option>
													<option value="28 Day">28</option>
													<option value="29 Day">29</option>
													<option value="30 Day">30</option>
													<option value="31 Day">31</option>
												</select>

											</div>
											
										</div>

										<div class="form-group" style="margin-bottom: 2px;">
											<label class="col-md-4" style="padding-top: 6px; text-align: -webkit-right;">Penalty Percent For
												Deposit Conversion<span style="color: red"></span>
											</label>
											<div class="col-md-6">
												<form:input id="penaltyPercntForDepositConversion"
													placeholder="" path="penaltyPercntForDepositConversion"
													class="myform-control" onkeypress="amountValidation(event)" />


											</div>
										</div>




									</div>


								</div>

							</div>
							
							
							<div id="withdraw" class="tab-pane">

								<div class="col-md-12">

									<div class="col-md-6">
									
									<div class="form-group" style="margin-bottom: 2px;">
											<label class="col-md-5" style="padding-top: 16px; text-align: -webkit-right;">Is Premature Closing Allowed<span
												style="color: red">*</span></label>
											<div class="col-md-6" style="margin-left: -15px;">
												<select name="isPrematureClosingioAllowedForWithdraw" class="myform-control" onchange="disableRemaningFields(event)">
													<option value="0">No</option>
													<option value="1">Yes</option>


												</select> 


											</div>
										</div>
									
										<div class="form-group" style="margin-bottom: 2px;">
											<label class="col-md-5" style="padding-top: 16px; text-align: -webkit-right;">Is Withdraw Allowed<span
												style="color: red">*</span></label>
											<div class="col-md-6" style="margin-left: -15px;">
												<form:select path="isWithdrawAllowed" class="myform-control" onchange="disableRemaningFields(event)">
													<form:option value="0">No</form:option>
													<form:option value="1">Yes</form:option>

												</form:select>


											</div>
										</div>
										<div class="form-group" style="margin-bottom: 2px;">
											<label class="col-md-5" style="padding-top: 16px; text-align: -webkit-right;">Prevention of Withdraw Before Maturity<span style="color: red"></span>
											</label>
											<div class="col-md-6"style="margin-left: -15px;">
												<select id="preventionOfWithdrawBeforeMaturity" name="preventionOfWithdrawBeforeMaturity"
													class="myform-control">
													<option value="">Day</option>
													<option value="1">1</option>
													<option value="2">2</option>
													<option value="3">3</option>
													<option value="4">4</option>
													<option value="5">5</option>
													<option value="6">6</option>
													<option value="7">7</option>
													<option value="8">8</option>
													<option value="9">9</option>
													<option value="10">10</option>
													<option value="11">11</option>
													<option value="12">12</option>
													<option value="13">13</option>
													<option value="14">14</option>
													<option value="15">15</option>
													<option value="16">16</option>
													<option value="17">17</option>
													<option value="18">18</option>
													<option value="19">19</option>
													<option value="20">20</option>
													<option value="21">21</option>
													<option value="22">22</option>
													<option value="23">23</option>
													<option value="24">24</option>
													<option value="25">25</option>
													<option value="26">26</option>
													<option value="27">27</option>
													<option value="28">28</option>
													<option value="29">29</option>
													<option value="30">30</option>
													<option value="31">31</option>
												</select>

											</div>
										</div>
										<div class="form-group" style="margin-bottom: 2px;">
											<label class="col-md-5" style="padding-top: 16px; text-align: -webkit-right;">Locking Period For
												Withdraw<span style="color: red"></span>
											</label>
											<div class="col-md-2" style="width: 21%; margin-left: -15px;">
												<select id="LTFTYear" class="myform-control"
													name="lockingPeriodForWithdraw">
													<option value="">Year</option>
													<option value="1 Year">1</option>
													<option value="2 Year">2</option>
													<option value="3 Year">3</option>
													<option value="4 Year">4</option>
													<option value="5 Year">5</option>
													<option value="6 Year">6</option>
													<option value="7 Year">7</option>
													<option value="8 Year">8</option>
													<option value="9 Year">9</option>
													<option value="10 Year">10</option>
													<option value="11 Year">11</option>
													<option value="12 Year">12</option>
													<option value="13 Year">13</option>
													<option value="14 Year">14</option>
													<option value="15 Year">15</option>
												</select>
											</div>
											 <div class="col-md-2" style="width: 22.5%; margin-left: -25px;">
												<select id="LTFTMonth" class="myform-control"
													name="lockingPeriodForWithdraw">
													<option value="">Month</option>
													<option value="1 Month">1</option>
													<option value="2 Month">2</option>
													<option value="3 Month">3</option>
													<option value="4 Month">4</option>
													<option value="5 Month">5</option>
													<option value="6 Month">6</option>
													<option value="7 Month">7</option>
													<option value="8 Month">8</option>
													<option value="9 Month">9</option>
													<option value="10 Month">10</option>
													<option value="11 Month">11</option>
													<option value="12 Month">12</option>
												</select>
											</div>
											
											<div class="col-md-2" style="width: 21%; margin-left: -25px;">
												<select id="LTFTDay" class="myform-control"
													name="lockingPeriodForWithdraw">
													<option value="">Day</option>
													<option value="1 Day">1</option>
													<option value="2 Day">2</option>
													<option value="3 Day">3</option>
													<option value="4 Day">4</option>
													<option value="5 Day">5</option>
													<option value="6 Day">6</option>
													<option value="7 Day">7</option>
													<option value="8 Day">8</option>
													<option value="9 Day">9</option>
													<option value="10 Day">10</option>
													<option value="11 Day">11</option>
													<option value="12 Day">12</option>
													<option value="13 Day">13</option>
													<option value="14 Day">14</option>
													<option value="15 Day">15</option>
													<option value="16 Day">16</option>
													<option value="17 Day">17</option>
													<option value="18 Day">18</option>
													<option value="19 Day">19</option>
													<option value="20 Day">20</option>
													<option value="21 Day">21</option>
													<option value="22 Day">22</option>
													<option value="23 Day">23</option>
													<option value="24 Day">24</option>
													<option value="25 Day">25</option>
													<option value="26 Day">26</option>
													<option value="27 Day">27</option>
													<option value="28 Day">28</option>
													<option value="29 Day">29</option>
													<option value="30 Day">30</option>
													<option value="31 Day">31</option>
												</select>

											</div>
											
										</div>
										
										
									</div>
									
									<div class="col-md-6">
										<div class="form-group" style="margin-bottom: 2px;">
											<label class="col-md-6" style="padding-top: 6px; text-align: -webkit-right;">Is Slab Based
												Penalty Required For Withdraw<span style="color: red"></span>
											</label>
											<div class="col-md-6">
												<form:select path="isSlabBasedPenaltyRequiredForWithdraw"
													class="myform-control">
													<form:option value="0">No</form:option>
													<form:option value="1">Yes</form:option>

												</form:select>


											</div>
										</div>
										
										<div class="form-group" style="margin-bottom: 2px;">
													<label class="col-md-5"
														style="padding-top: 16px; text-align: -webkit-right; margin-left: -44px;">
														Incremental Withdraw<span style="color: red">*</span>
													</label>
													<div class="col-md-6" style="margin-left: -15px;">
														<form:input id="incrementalWithdraw" placeholder=""
															path="incrementalWithdraw" class="myform-control"
															onkeypress="numberValidation(event)" />


													</div>
												</div>
										<%-- <div class="form-group" style="margin-bottom: 2px;">
											<label class="col-md-6" style="padding-top: 6px; text-align: -webkit-right;">Is Interest
												Adjustment Required For Withdraw<span style="color: red"></span>
											</label>
											<div class="col-md-6">
												<form:select path="isInterestAdjustmentRequiredForWithdraw"
													class="myform-control">
													<form:option value="0">No</form:option>
													<form:option value="1">Yes</form:option>
												</form:select>
											</div>
										</div> --%>

									</div>
								</div>
							</div>
						</div>

					</div>
				</div>
			</div>
			<div style="text-align: center; margin-top:15px;">
				<input type="button" value="Submit" class="btn btn-primary"
					onclick="return submitForm()" />
			</div>
		</div>
	</form:form>
	<div class="alert alert-danger fade">
		<button type="button" class="close" data-dismiss="alert">×</button>
		<strong>Alert!</strong> <span id="alertMessages" style="color: red;font-size: 15px;"></span>
	</div>
</div>
</div></div>
<script>
$(document).ready(function() {
	
	
	
	if ("${productConfiguration.modificationPenaltyFlatAmount}" != '') {
		$("#amountModificationDIV").show();
		$("#percentModificationDIV").hide();
		$("#amountModification").attr("checked",true);
		
	} 
	
	if ("${productConfiguration.modificationPenaltyPercent}" != '') {
		$("#percentModificationDIV").show();
		$("#amountModificationDIV").hide();
		$("#percentageModification").attr("checked",true);
	} 
	
	/* if ("${productConfiguration.penaltyAmountForNonSlabBasedWithdraw}" != '') {
		$("#nonSlabAmountWithdrawDIV").show();
		$("#nonSlabPercentWithdrawDIV").hide();
		$("#amountWithdraw").attr("checked",true);
		
	} 
	
	if ("${productConfiguration.penaltyPercentForNonSlabBasedWithdraw}" != '') {
		$("#nonSlabPercentWithdrawDIV").show();
		$("#nonSlabAmountWithdrawDIV").hide();
		$("#percentageWithdraw").attr("checked",true);
	}  */
	
	
	if ("${productConfiguration.penaltyAmountForTopup}" != '') {
		$("#penaltyAmountDIV").show();
		$("#penaltyPercentDIV").hide();
		$("#amount").attr("checked",true);
		
	} 
	
	if ("${productConfiguration.penaltyPercentForTopup}" != '') {
		$("#penaltyPercentDIV").show();
		$("#penaltyAmountDIV").hide();
		$("#percentage").attr("checked",true);
	} 
	
	
	if ("${productConfiguration.citizen}" == 'RI') {
		document.getElementById('nriAccountType').disabled = true;
		$("#currency").val('${productConfiguration.defaultCurrency}').change();
	} else {
		$("#currency").val('${productConfiguration.defaultCurrency}').change();
		document.getElementById('nriAccountType').disabled = false;
	}  
	
	
	var topUp = "${productConfiguration.lockingPeriodForTopup}";
	var withdraw = "${productConfiguration.lockingPeriodForWithdraw}";
			var topUArr = topUp.split(",");
			for(var i = 0; i < topUArr.length ; i++){
				if(topUArr[i] != ""){
					if(topUArr[i].includes("Month")){
						$("#LPMonthSelect").val(topUArr[i]).change();
						
					}
					if(topUArr[i].includes("Year")){
						$("#LPYearSelect").val(topUArr[i]).change();
						
					}
					if(topUArr[i].includes("Day")){
						$("#LPDaySelect").val(topUArr[i]).change();
						
					}
					
				}
			}
			
			
			
			var withdrawArr = withdraw.split(",");
			for(var i = 0; i < withdrawArr.length ; i++){
				if(withdrawArr[i] != ""){
					if(withdrawArr[i].includes("Month")){
						$("#LTFTMonth").val(withdrawArr[i]).change();
						
					}
					if(withdrawArr[i].includes("Year")){
						$("#LTFTYear").val(withdrawArr[i]).change();
						
					}
					if(withdrawArr[i].includes("Day")){
						$("#LTFTDay").val(topUArr[i]).change();
						
					}
					
				}
			}
			
			var minTenure = "${productConfiguration.minimumTenure}";
			var maxTenure = "${productConfiguration.maximumTenure}";
			var minTenureArr = minTenure.split(",");
			for(var i = 0; i < minTenureArr.length ; i++){
				if(minTenureArr[i] != ""){
					if(minTenureArr[i].includes("Month")){
						$("#minTenureMonth").val(minTenureArr[i]).change();
						
					}
					if(minTenureArr[i].includes("Year")){
						$("#minTenureYear").val(minTenureArr[i]).change();
						
					}
					if(minTenureArr[i].includes("Day")){
						$("#minTenureDay").val(minTenureArr[i]).change();
						
					}
					
				}
			}
			
			var paymentModeChange = "${productConfiguration.paymentModeOnMaturity}";
			if(paymentModeChange != ''){
				$("#paymentModeOnMaturity").val(paymentModeChange).change();
			}
			
			var maxTenureArr = maxTenure.split(",");
			for(var i = 0; i < maxTenureArr.length ; i++){
				if(maxTenureArr[i] != ""){
					if(maxTenureArr[i].includes("Month")){
						$("#maxTenureMonth").val(maxTenureArr[i]).change();
						
					}
					if(maxTenureArr[i].includes("Year")){
						$("#maxTenureYear").val(maxTenureArr[i]).change();
						
					}
					if(maxTenureArr[i].includes("Day")){
						$("#maxTenureDay").val(maxTenureArr[i]).change();
						
					}
					
				}
			}
			
			var pOWBM = "${productConfiguration.preventionOfWithdrawBeforeMaturity}";
			var pOYUBM = "${productConfiguration.preventionOfTopUpBeforeMaturity}";
			if(pOWBM !="")
				$("#preventionOfWithdrawBeforeMaturity").val(pOWBM).change();
			if(pOYUBM !="")
				$("#preventionOfTopUpBeforeMaturity").val(pOYUBM).change();
				
			
			
			var proType = "${productConfiguration.productType}";
			if(proType  ==  'Recurring Deposit'){
				$("#gracePeriodForRecurringPaymentDIV").show();
			
				$("#gracePeriodForRecurringPayment").val("");
				
			}else{
				$("#gracePeriodForRecurringPaymentDIV").hide();
			}
			
			
			if("${productConfiguration.gracePeriodForRecurringPayment}" != ""){
				$("#gracePeriodForRecurringPayment").val("${productConfiguration.gracePeriodForRecurringPayment}").change();
				
			}
			
			var nonPenaltyPeriodForTopup = "${productConfiguration.nonPenaltyPeriodForTopup}";
			var nonPenaltyPeriodForTopupTopUArr = nonPenaltyPeriodForTopup.split(",");
			for(var i = 0; i < nonPenaltyPeriodForTopupTopUArr.length ; i++){
				if(nonPenaltyPeriodForTopupTopUArr[i] != ""){
					if(nonPenaltyPeriodForTopupTopUArr[i].includes("Month")){
						$("#nonPenaltyPeriodForTopupMonth").val(nonPenaltyPeriodForTopupTopUArr[i]).change();
						
					}
					if(nonPenaltyPeriodForTopupTopUArr[i].includes("Year")){
						$("#nonPenaltyPeriodForTopupYear").val(nonPenaltyPeriodForTopupTopUArr[i]).change();
						
					}
					if(nonPenaltyPeriodForTopupTopUArr[i].includes("Day")){
						$("#nonPenaltyPeriodForTopupDay").val(nonPenaltyPeriodForTopupTopUArr[i]).change();
						
					}
					
				}
			}
			
			
			
			/* var nonPenaltyPeriodForWithdraw = "${productConfiguration.nonPenaltyPeriodForWithdraw}";
			var nonPenaltyPeriodForWithdrawTopUArr = nonPenaltyPeriodForWithdraw.split(",");
			for(var i = 0; i < nonPenaltyPeriodForWithdrawTopUArr.length ; i++){
				if(nonPenaltyPeriodForWithdrawTopUArr[i] != ""){
					if(nonPenaltyPeriodForWithdrawTopUArr[i].includes("Month")){
						$("#nonPenaltyPeriodForWithdrawMonth").val(nonPenaltyPeriodForWithdrawTopUArr[i]).change();
						
					}
					if(nonPenaltyPeriodForWithdrawTopUArr[i].includes("Year")){
						$("#nonPenaltyPeriodForWithdrawYear").val(nonPenaltyPeriodForWithdrawTopUArr[i]).change();
						
					}
					if(nonPenaltyPeriodForWithdrawTopUArr[i].includes("Day")){
						$("#nonPenaltyPeriodForWithdrawDay").val(nonPenaltyPeriodForWithdrawTopUArr[i]).change();
						
					}
					
				}
			} */
			
			
			
			var nonPenaltyPeriodForModification = "${productConfiguration.nonPenaltyPeriodForModification}";
			var nonPenaltyPeriodForModificationTopUArr = nonPenaltyPeriodForModification.split(",");
			for(var i = 0; i < nonPenaltyPeriodForModificationTopUArr.length ; i++){
				if(nonPenaltyPeriodForModificationTopUArr[i] != ""){
					if(nonPenaltyPeriodForModificationTopUArr[i].includes("Month")){
						$("#nonPenaltyPeriodForModificationMonth").val(nonPenaltyPeriodForModificationTopUArr[i]).change();
						
					}
					if(nonPenaltyPeriodForModificationTopUArr[i].includes("Year")){
						$("#nonPenaltyPeriodForModificationYear").val(nonPenaltyPeriodForModificationTopUArr[i]).change();
						
					}
					if(nonPenaltyPeriodForModificationTopUArr[i].includes("Day")){
						$("#nonPenaltyPeriodForModificationDay").val(nonPenaltyPeriodForModificationTopUArr[i]).change();
						
					}
					
				}
			}
			
			
			
			
			
			
			
			
				
	});
	
function disableRemaningFields(event){    
	
	if(event.target.id == 'isTopupAllowed'){
		var isTop = $("#isTopupAllowed").val();
		if(isTop == "0"){
		$("#minimumTopupAmount").val("");
		$("#maximumTopupAmount").val("");
		$("#isInterestAdjustmentRequiredForTopup").val("0");
		$("#preventionOfTopUpBeforeMaturity").val("");
		$("#LPYearSelect").val("");
		$("#LPMonthSelect").val("");
		$("#LPDaySelect").val("");
		$('#minimumTopupAmount').attr('readonly', true);
		$('#maximumTopupAmount').attr('readonly', true);
		$('#isInterestAdjustmentRequiredForTopup').prop('disabled', true);
		$('#preventionOfTopUpBeforeMaturity').prop('disabled', true);
		$('#LPYearSelect').prop('disabled', true);
		$('#LPMonthSelect').prop('disabled', true);
		$('#LPDaySelect').prop('disabled', true);
		}else{
			$("#minimumTopupAmount").val("");
			$("#maximumTopupAmount").val("");
			$("#isInterestAdjustmentRequiredForTopup").val("0");
			$("#preventionOfTopUpBeforeMaturity").val("");
			$("#LPYearSelect").val("");
			$("#LPMonthSelect").val("");
			$("#LPDaySelect").val("");
			$('#minimumTopupAmount').attr('readonly', false);
			$('#maximumTopupAmount').attr('readonly', false);
			$('#isInterestAdjustmentRequiredForTopup').prop('disabled', false);
			$('#preventionOfTopUpBeforeMaturity').prop('disabled', false);
			$('#LPYearSelect').prop('disabled', false);
			$('#LPMonthSelect').prop('disabled', false);
			$('#LPDaySelect').prop('disabled', false);
		}
		
	}		

	if (event.target.id == 'isWithdrawAllowed') {
		var isWith = $("#isWithdrawAllowed").val();
		if(isWith == "0"){
			$("#isSlabBasedPenaltyRequiredForWithdraw").val("0");
			$("#isInterestAdjustmentRequiredForTopup").val("0");
			$("#preventionOfWithdrawBeforeMaturity").val("");
		$("#LTFTYear").val("");
		$("#LTFTMonth").val("");
		$("#LTFTDay").val("");
		$('#isSlabBasedPenaltyRequiredForWithdraw').prop('disabled', true);
		$('#preventionOfWithdrawBeforeMaturity').prop('disabled', true);
		$('#isInterestAdjustmentRequiredForWithdraw').prop('disabled', true);
		$('#LTFTYear').prop('disabled', true);
		$('#LTFTMonth').prop('disabled', true);
		$('#LTFTDay').prop('disabled', true);
		}else{
			$("#isSlabBasedPenaltyRequiredForWithdraw").val("0");
			$("#isInterestAdjustmentRequiredForWithdraw").val("0");
			$("#preventionOfWithdrawBeforeMaturity").val("");
			$("#LPYearSelect").val("");
			$("#LPMonthSelect").val("");
			$("#LPDaySelect").val("");
			$('#isSlabBasedPenaltyRequiredForWithdraw').prop('disabled', false);
			$('#preventionOfWithdrawBeforeMaturity').prop('disabled', false);
			$('#isInterestAdjustmentRequiredForWithdraw').prop('disabled', false);
			$('#LTFTYear').prop('disabled', false);
			$('#LTFTMonth').prop('disabled', false);
			$('#LTFTDay').prop('disabled', false);
		}

	}

}
	
		function accountTypeVal(value) {
		var FCNR = [ "USD", "GBP", "EUR", "JPY", "CAD", "AUD", "SGD", "HKD" ];
		var RFC = [ "USD", "GBP", "EUR", "JPY", "CAD", "AUD" ];
		$('#currency option').hide();
		$('#currency').val('').show().change();
		$('#currency option').each(function() {

			if (value == "FCNR" || value == "PRP") {
				var length = $.inArray($(this).val(), FCNR);
				if (length > -1)
					$(this).show();
			} else if (value == "RFC") {
				var length = $.inArray($(this).val(), RFC);
				if (length > -1)
					$(this).show();
			} else {
				$('#currency option').show();
			}

		});

	}

		function submitForm() {
			var flag = true;
			
			if ($("#productCode").val() == "") {
				$(".alert").removeClass("in").show();
				$(".alert").delay(2500).addClass("in").fadeOut(1600);
				$("#alertMessages").text("Product code can not be empty !").show();
				flag = false;
				return flag;
			}
			if ($("#productName").val() == "") {
				$(".alert").removeClass("in").show();
				$(".alert").delay(2500).addClass("in").fadeOut(1600);
				$("#alertMessages").text("Product name can not be empty !").show();
				flag = false;
				return flag;
			}
			if ($("#productType").val() == ""
					|| $("#productType").val() == "select") {
				$(".alert").removeClass("in").show();
				$(".alert").delay(2500).addClass("in").fadeOut(1600);
				$("#alertMessages").text("Product type can not be empty !").show();
				flag = false;
				return flag;
			}
			if ($("#citizen").val() == "" || $("#citizen").val() == "select") {

				$(".alert").removeClass("in").show();
				$(".alert").delay(2500).addClass("in").fadeOut(1600);
				$("#alertMessages").text("Please select citizen !").show();
				flag = false;
				return flag;
			}

			if ($("#citizen").val() == 'NRI'
					&& ($("#nriAccountType").val() == "" || $("#nriAccountType")
							.val() == "select")) {
				$(".alert").removeClass("in").show();
				$(".alert").delay(2500).addClass("in").fadeOut(1600);
				$("#alertMessages").text("Please select Account Type !").show();
				flag = false;
				return flag;
			}

			
			var minTenureDay = $("#minTenureDay").val();
			var minTenureMonth = $("#minTenureMonth").val();
			var minTenureYear = $("#minTenureYear").val();
			if(minTenureDay == "" && minTenureMonth == "" && minTenureYear == ""){
			$(".alert").removeClass("in").show();
			$(".alert").delay(2500).addClass("in").fadeOut(1600);
			$("#alertMessages").text("Please select atlest one Minimum Tenure !").show();
			flag = false;
			return flag;
			}
			var maxTenureDay = $("#maxTenureDay").val();
			var maxTenureMonth = $("#maxTenureMonth").val();
			var maxTenureYear = $("#maxTenureYear").val();
			if(maxTenureDay == "" && maxTenureMonth == "" && maxTenureYear == ""){
			$(".alert").removeClass("in").show();
			$(".alert").delay(2500).addClass("in").fadeOut(1600);
			$("#alertMessages").text("Please select atlest one Maximum Tenure !").show();
			flag = false;
			return flag;
			}
			
			

			if ($("#productStartDate").val() == "") {
				$(".alert").removeClass("in").show();
				$(".alert").delay(2500).addClass("in").fadeOut(1600);
				$("#alertMessages").text("Product Start Date can not be empty!")
						.show();
				flag = false;
				return flag;
			}
			if ($("#productEndDate").val() == "") {
				$(".alert").removeClass("in").show();
				$(".alert").delay(2500).addClass("in").fadeOut(1600);
				$("#alertMessages").text("Product End Date can not be empty!")
						.show();
				flag = false;
				return flag;
			}
			var dateStart = $("#productStartDate").val();
			var dateEnd = $("#productEndDate").val();
			var startDateFormat = dateStart.split("/");
			var startEndFormat = dateEnd.split("/");
			var start = new Date(startDateFormat[1] + "/" + startDateFormat[0]
					+ "/" + startDateFormat[2]);
			var end = new Date(startEndFormat[1] + "/" + startEndFormat[0] + "/"
					+ startEndFormat[2]);
			if (start > end) {
				$(".alert").removeClass("in").show();
				$(".alert").delay(2500).addClass("in").fadeOut(1600);
				$("#alertMessages").text(
						"Product Start Date  should be less than to End Date !")
						.show();
				flag = false;
				return flag;
			}
			
			
			
			if ($("#interestCalculationBasis").val() == ""
				|| $("#interestCalculationBasis").val() == "select") {
			$(".alert").removeClass("in").show();
			$(".alert").delay(2500).addClass("in").fadeOut(1600);
			$("#alertMessages").text("Interest Calculation Basis can not be empty !").show();
			flag = false;
			return flag;
		}
			
			
			
			/* if ($("#minimumAmountRequiredForSweepDeposit").val() == "") {
				$(".alert").removeClass("in").show();
				$(".alert").delay(2500).addClass("in").fadeOut(1600);
				$("#alertMessages")
						.text("Minimum Amount Required For Sweep Deposit can not be empty!").show();
				flag = false;
				return flag;
			}
			 */

			if ($("#minimumDepositAmount").val() == "") {
				$(".alert").removeClass("in").show();
				$(".alert").delay(2500).addClass("in").fadeOut(1600);
				$("#alertMessages")
						.text("Minimum Deposit Amount can not be empty!").show();
				flag = false;
				return flag;
			}
			if ($("#maximumDepositAmount").val() == "") {
				$(".alert").removeClass("in").show();
				$(".alert").delay(2500).addClass("in").fadeOut(1600);
				$("#alertMessages")
						.text("Maximum Deposit Amount can not be empty!").show();
				flag = false;
				return flag;
			}

			if (parseInt($("#minimumDepositAmount").val()) > parseInt($(
					"#maximumDepositAmount").val())) {
				$(".alert").removeClass("in").show();
				$(".alert").delay(2500).addClass("in").fadeOut(1600);
				$("#alertMessages")
						.text(
								"Minimum deposit amount  should be less than to max deposit amount!")
						.show();
				flag = false;
				return flag;
			}
			if ($("#currency").val() == "" || $("#currency").val() == "select") {

				$(".alert").removeClass("in").show();
				$(".alert").delay(2500).addClass("in").fadeOut(1600);
				$("#alertMessages").text("Please select currency !").show();
				flag = false;
				return flag;
			}
			
			if($("#productType").val() == 'Recurring Deposit'){
			if ($("#gracePeriodForRecurringPayment").val() == "") {
				$(".alert").removeClass("in").show();
				$(".alert").delay(2500).addClass("in").fadeOut(1600);
				$("#alertMessages").text(
						"Please select GracePeriod For RecurringPayment in Day!")
						.show();
				flag = false;
				return flag;
			}
			}
			if ($("#interestCompoundingBasis").val() == ""
					|| $("#interestCompoundingBasis").val() == "select") {
				$(".alert").removeClass("in").show();
				$(".alert").delay(2500).addClass("in").fadeOut(1600);
				$("#alertMessages").text(
						"Please select Interest Compounding Basis !").show();
				flag = false;
				return flag;
			}

			if ($("#tdsCalculationOnBasis").val() == "") {
				$(".alert").removeClass("in").show();
				$(".alert").delay(2500).addClass("in").fadeOut(1600);
				$("#alertMessages").text(
						"TDS Calculation On Basis can not be empty!").show();
				flag = false;
				return flag;
			}

			if ($("#tdsPercentForNoPAN").val() == "") {
				$(".alert").removeClass("in").show();
				$(".alert").delay(2500).addClass("in").fadeOut(1600);
				$("#alertMessages")
						.text("TDS Percent For No PAN can not be empty!").show();
				flag = false;
				return flag;
			}
			
			 if ($("#paymentModeOnMaturity").val() == "") {
				$(".alert").removeClass("in").show();
				$(".alert").delay(2500).addClass("in").fadeOut(1600);
				$("#alertMessages")
						.text("Please select payment mode option !").show();
				flag = false;
				return flag;
			} 
			
			/* if ($("#minimumSavingBalanceForSweepDeposit").val() == "") {
				$(".alert").removeClass("in").show();
				$(".alert").delay(2500).addClass("in").fadeOut(1600);
				$("#alertMessages")
				.text("Minimum Saving Balance For Sweep Deposit can not be empty!").show();
				flag = false;
				return flag;
			}
 */
			/* ----------------------------------------------------------------- */
			if($("#isTopupAllowed").val() == "1"){
				 if ($("#minimumTopupAmount").val() == "") {
				$(".alert").removeClass("in").show();
				$(".alert").delay(2500).addClass("in").fadeOut(1600);
				$("#alertMessages").text("Minimum Topup Amount can not be empty!")
						.show();
				flag = false;
				return flag;
			}

			if ($("#maximumTopupAmount").val() == "") {
				$(".alert").removeClass("in").show();
				$(".alert").delay(2500).addClass("in").fadeOut(1600);
				$("#alertMessages").text("Maximum Top up Amount can not be empty!")
						.show();
				flag = false;
				return flag;
			} 
			
			 var dayLP = $("#LPDaySelect").val();
			var monthLP = $("#LPMonthSelect").val();
			var yearLP = $("#LPYearSelect").val();

			/* if (dayLP == '' && monthLP == '' && yearLP == '') {
				$(".alert").removeClass("in").show();
				$(".alert").delay(2500).addClass("in").fadeOut(1600);
				$("#alertMessages").text(
						"Please select atleast one locking period Top Up !")
						.show();
				flag = false;
				return flag;
			}  */
			
			/*  if ($("#preventionOfTopUpBeforeMaturity").val() == "") {
				$(".alert").removeClass("in").show();
				$(".alert").delay(2500).addClass("in").fadeOut(1600);
				$("#alertMessages").text("Please select Prevention of Top-up Before Maturity !")
						.show();
				flag = false;
				return flag;
			} */
			}
			
			if($("#isWithdrawAllowed").val() == "1"){
			/* if ($("#preventionOfWithdrawBeforeMaturity").val() == "") {
				$(".alert").removeClass("in").show();
				$(".alert").delay(2500).addClass("in").fadeOut(1600);
				$("#alertMessages").text("Please select Prevention of Withdraw Before Maturity !")
						.show();
				flag = false;
				return flag;
			}
			  */
			
			
			 var LTFTDay = $("#LTFTDay").val();
			var LTFTMonth = $("#LTFTMonth").val();
			var LTFTYear = $("#LTFTYear").val();
			/* if(LTFTDay == "" && LTFTMonth == "" && LTFTYear == ""){
			$(".alert").removeClass("in").show();
			$(".alert").delay(2500).addClass("in").fadeOut(1600);
			$("#alertMessages").text("Please select atlest one Locking Period Withdraw !").show();
			flag = false;
			return flag;
			}  */

			}
			
			/* if ($("#penaltyforPrematureClosing").val() == "") {
				$(".alert").removeClass("in").show();
				$(".alert").delay(2500).addClass("in").fadeOut(1600);
				$("#alertMessages").text(
						"Penalty for Premature Closing  can not be empty!").show();
				flag = false;
				return flag;
			} */

			var isCheckedAmount = $('#amount').prop('checked');
			

			if (isCheckedAmount) {
					if ($("#penaltyAmountForTopup").val() == "") {
						$(".alert").removeClass("in").show();
						$(".alert").delay(2500).addClass("in").fadeOut(1600);
						$("#alertMessages").text(
								"Penalty Amount For Topup can not be empty!").show();
						flag = false;
						return flag;
					}

				}
			var isCheckedPercentage = $('#percentage').prop('checked');
			if(isCheckedPercentage){
				if ($("#penaltyPercentForTopup").val() == "") {
					$(".alert").removeClass("in").show();
					$(".alert").delay(2500).addClass("in").fadeOut(1600);
					$("#alertMessages").text(
							"Penalty Percent For Topup can not be empty!").show();
					flag = false;
					return flag;
				}
			}
			/* var isCheckedWithdrawAmount = $('#amountWithdraw').prop('checked'); 
			var isCheckedWithdrawPercent = $('#percentageWithdraw').prop('checked');
			if(isCheckedWithdrawPercent){
			if ($("#penaltyPercentForNonSlabBasedWithdraw").val() == "") {
				$(".alert").removeClass("in").show();
				$(".alert").delay(2500).addClass("in").fadeOut(1600);
				$("#alertMessages")
						.text(
								"penalty Percent For Non Slab Based Withdraw can not be empty!")
						.show();
				flag = false;
				return flag;
			}
			}
			if(isCheckedWithdrawAmount){
			if ($("#penaltyAmountForNonSlabBasedWithdraw").val() == "") {
				$(".alert").removeClass("in").show();
				$(".alert").delay(2500).addClass("in").fadeOut(1600);
				$("#alertMessages")
						.text(
								"Penalty Amount For Non Slab BasedWithdraw can not be empty!")
						.show();
				flag = false;
				return flag;
			}
			} */
			if ($("#modificationBasis").val() == "") {
				$(".alert").removeClass("in").show();
				$(".alert").delay(2500).addClass("in").fadeOut(1600);
				$("#alertMessages").text("Please select Modification on Basis !")
						.show();
				flag = false;
				return flag;
			}

			if ($("#maximumLimitOfModification").val() == "") {
				$(".alert").removeClass("in").show();
				$(".alert").delay(2500).addClass("in").fadeOut(1600);
				$("#alertMessages").text(
						"Maximum Limit Of Modification can not be empty!").show();
				flag = false;
				return flag;
			}
			var isCheckedModificationAmount = $('#amountModification').prop('checked'); 
			var isCheckedModificationPercent = $('#percentageModification').prop('checked');
			if(isCheckedModificationPercent){
			if ($("#modificationPenaltyPercent").val() == "") {
				$(".alert").removeClass("in").show();
				$(".alert").delay(2500).addClass("in").fadeOut(1600);
				$("#alertMessages").text(
						"Modification Penalty Percent can not be empty!").show();
				flag = false;
				return flag;
			}
			}
			if(isCheckedModificationAmount){
			if ($("#modificationPenaltyFlatAmount").val() == "") {
				$(".alert").removeClass("in").show();
				$(".alert").delay(2500).addClass("in").fadeOut(1600);
				$("#alertMessages").text(
						"modification Penalty flat Amount can not be empty!")
						.show();
				flag = false;
				return flag;
			}
			}
			/* if ($("#penaltyPercntForDepositConversion").val() == "") {
				$(".alert").removeClass("in").show();
				$(".alert").delay(2500).addClass("in").fadeOut(1600);
				$("#alertMessages").text(
						"Penalty Percnt For Deposit Conversion can not be empty!")
						.show();
				flag = false;
				return flag;
			} */
			
			if (flag) {
				$("form").submit();
			}
		}

	function citizenChange(value, id) {
		if (value == 'RI') {
			document.getElementById('nriAccountType').disabled = true;
			$("#currency").val('INR').change();
			$("#nriAccountType").val("").change();
		} else {
			document.getElementById('nriAccountType').disabled = false;
		}
	}

	function showHideInputBox(event) {
		debugger;
		var id = document.getElementById(event.target.id);
		if (id.value == 'Day') {

			$("#LPDay").show();
		}
		if (id.value == 'Month') {

			$("#LPMonth").show();

		}
		if (id.value == 'Year') {

			$("#LPYear").show();

		}
		if (id.value == '' && event.target.id == "LPDaySelect") {
			$("#LPDay").hide();
		}
		if (id.value == '' && event.target.id == "LPMonthSelect") {
			$("#LPMonth").hide();

		}
		if (id.value == '' && event.target.id == "LPYearSelect") {
			$("#LPYear").hide();

		}

	}
	
	
	
	$('input[type=radio]').change( function() {
		if (this.value == 'amount') {
			$("#penaltyAmountDIV").show();
			$("#penaltyPercentDIV").hide();
			$("#penaltyPercentForTopup").val("");

		} 
		if(this.value == 'percentage') {
			$("#penaltyPercentDIV").show();
			$("#penaltyAmountDIV").hide();
			$("#penaltyAmountForTopup").val("");
		}
		
		
		/* if (this.value == 'amountWithdraw') {
			$("#nonSlabAmountWithdrawDIV").show();
			$("#nonSlabPercentWithdrawDIV").hide();
			$("#penaltyPercentForNonSlabBasedWithdraw").val("");

		} 
		if(this.value == 'percentageWithdraw') {
			$("#nonSlabAmountWithdrawDIV").hide();
			$("#nonSlabPercentWithdrawDIV").show();
			$("#penaltyAmountForNonSlabBasedWithdraw").val("");
		} */
		if (this.value == 'amountModification') {
			$("#amountModificationDIV").show();
			$("#percentModificationDIV").hide();
			$("#modificationPenaltyPercent").val("");

		} if(this.value == 'percentModification') {
			$("#amountModificationDIV").hide();
			$("#percentModificationDIV").show();
			$("#modificationPenaltyFlatAmount").val("");
		}
		
		
		
		  
		});
	
	
	
	
	function hideGracePeriod(value) {
	
		if(value  ==  'Recurring Deposit'){
			$("#gracePeriodForRecurringPaymentDIV").show();
		
			$("#gracePeriodForRecurringPayment").val("");
			
		}else{
			$("#gracePeriodForRecurringPaymentDIV").hide();
		}
		
	}
</script>
<style>
label {
	float: left;
	width: 93px;
}

.alert-danger {
	position: fixed;
	color: #a94442;
	background-color: #f2dede;
	border-color: #ebccd1;
	top: 161px;
	left: 777px;
	right: 30px;
	width: 42%;
	padding: 10px;
	height: 40px;
	z-index: 99999;
	margin: auto;
}
</style>
