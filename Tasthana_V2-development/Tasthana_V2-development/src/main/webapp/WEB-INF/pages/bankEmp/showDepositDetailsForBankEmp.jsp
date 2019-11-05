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

		<div class="header_customer">
			<h3>Deposit Details</h3>
		</div>



		<div class="Flexi_deposit">

			<div class="Success_msg">
				<div class="successMsg" style="text-align: center; color: red;">
					${error}</div>
			</div>
			<div class="flexi_table"
				style="margin-top: 7px; padding: auto 7px !important;">
				<form:form  id="fdForm"
					 class="form-horizontal" autocomplete="off"
					commandName="fixedDepositForm" name="fixedDeposit">

					<div style="clear: both;"></div>
					<div class="header_customer col-sm-12 col-md-12 col-lg-12"
						data-toggle="collapse" data-target="#depositDetails"
						style="border-bottom: 1px dotted #05007e; margin-bottom: 10px; padding: 7px !important;">

						<h3>
							<b><spring:message code="label.depositDetails" /></b> <i
								class="fa fa-chevron-circle-down pull-right" aria-hidden="true"
								" style="padding-right: 5px;"></i>
						</h3>
						<span id="depositOk"
							style="display: none; color: #14c514; margin-right: 25px; margin-top: -12px; float: right;"
							class="glyphicon glyphicon-ok"></span><span id="depositCross"
							style="display: none; color: red; margin-right: 25px; margin-top: -12px; float: right;"
							class="glyphicon glyphicon-remove"></span>
					</div>

					<div id="depositDetails" class="collapse">

						<div class="col-md-6">



							<div id="linkedAccountDiv">


								<div class="form-group" id="accountTypeDiv">
									<label class="col-md-4 control-label">Deposit Id: </label>
									<div class="col-md-6">


										<input type="text" value="${id}" class="form-control"
											style="background: #fff; border: none;" readonly="true" />

									</div>

								</div>


								<div class="form-group" id="accountTypeDiv">
									<label class="col-md-4 control-label">Account Number: </label>
									<div class="col-md-6">

										<input type="text" value="${accNumber}" class="form-control"
											style="background: #fff; border: none;" readonly="true" />

									</div>

								</div>

								<div class="form-group" id="accountTypeDiv">
									<label class="col-md-4 control-label">Deposit Amount:</label>
									<div class="col-md-6">

										<input type="text" value="${depositAmount}"
											class="form-control" style="background: #fff; border: none;"
											readonly="true" />

									</div>

								</div>

								<div class="form-group" id="accountTypeDiv">
									<label class="col-md-4 control-label">Created Date:</label>
									<div class="col-md-6">

										<input type="text"
											value="<fmt:formatDate value="${createdDate}" pattern="MM-dd-yyyy" />"
											class="form-control" style="background: #fff; border: none;"
											readonly="true" />

									</div>

								</div>




							</div>


						</div>

						<div class="col-md-6">


							<div class="form-group" id="accountTypeDiv">
								<label class="col-md-4 control-label">Current Balance:</label>
								<div class="col-md-6">
									<fmt:formatNumber value="${currentBal}" pattern="#.##"
										var="bal" />
									<td style="width: 15%;" class="commaSeparated">${bal}</td>
									<%-- 									<input type="text"  value="${currentBal}" class="form-control"  style="background: #fff; border: none;" readonly="true" />
 --%>
								</div>

							</div>

							<div class="form-group" id="accountTypeDiv">
								<label class="col-md-4 control-label">Expected Maturity:</label>
								<div class="col-md-6">
									<fmt:formatNumber value="${expectedMaturity}" pattern="#.##"
										var="maturity" />
									<td style="width: 15%;" class="commaSeparated">${maturity}</td>
									<%-- 									<input type="text"  value="${expectedMaturity}" class="form-control"  style="background: #fff; border: none;" readonly="true" /> 
 --%>
								</div>

							</div>

							<div class="form-group" id="accountTypeDiv">
								<label class="col-md-4 control-label">Deposit Category:</label>
								<div class="col-md-6">

									<input type="text" value="${depositCategory}"
										class="form-control" style="background: #fff; border: none;"
										readonly="true" />

								</div>

							</div>

							<div class="form-group" id="accountTypeDiv">
								<label class="col-md-4 control-label">Maturity Date:</label>
								<div class="col-md-6">

									<input type="text"
										value="<fmt:formatDate value="${maturityDate}" pattern="MM-dd-yyyy" />"
										class="form-control" style="background: #fff; border: none;"
										readonly="true" />

								</div>

							</div>
							<c:if test="${depositCategory == 'REVERSE-EMI'}">
							<div class="form-group" id="accountTypeDiv">
								<label class="col-md-4 control-label">Gestation Period:</label>
								<div class="col-md-6">

									<input type="text" value="${gestationPeriod}"
										class="form-control" style="background: #fff; border: none;"
										readonly="true" />

								</div>

							</div>
                          </c:if>
                           
                           <c:if test="${depositCategory == 'REVERSE-EMI'}">
							<div class="form-group" id="accountTypeDiv">
								<label class="col-md-4 control-label">Gestation End Date:</label>
								<div class="col-md-6">
                                   <input type="text"
										value="<fmt:formatDate value="${gestationEndDate}" pattern="MM-dd-yyyy" />"
										class="form-control" style="background: #fff; border: none;"
										readonly="true" />
									

								</div>

							</div>
                           </c:if>
                           
						</div>



					</div>
					<div style="clear: both;"></div>
					<div class="header_customer col-sm-12 col-md-12 col-lg-12"
						data-toggle="collapse" data-target="#depositHolderDetails"
						style="border-bottom: 1px dotted #05007e; margin-bottom: 10px; padding-top: 7px; padding-bottom: 7px;">

						<h3>
							<b><spring:message code="label.depositHolderDetails" /></b> <i
								class="fa fa-chevron-circle-down pull-right" aria-hidden="true"></i>
							<span id="depositHolderOk"
								style="display: none; color: #14c514; margin-right: 25px; margin-top: -12px; float: right;"
								class="glyphicon glyphicon-ok"></span><span
								id="depositHolderCross"
								style="display: none; color: red; margin-right: 25px; margin-top: -12px; float: right;"
								class="glyphicon glyphicon-remove"></span>
					</div>

					<div id="depositHolderDetails" class="collapse">
						<div class="col-md-12">

							<table class="table table-striped table-bordered">
								<tr>
									<td><b>Deposit Holder Id</b></td>
									<td><b><spring:message code="label.customerID" /></b></td>
									<td><b><spring:message code="label.customerName" /></b></td>
									<td><b><spring:message code="label.dateOfBirth" /></b></td>
									<td><b><spring:message code="label.email" /></b></td>
									<td><b><spring:message code="label.contactNumber" /></b></td>
									<td><b><spring:message code="label.address" /></b></td>
								</tr>

								<c:forEach items="${depositList}" var="d">

									<tr>
										<td><b> <c:out value="${d.depositHolderId}"></c:out></b></td>
										<td><b> <c:out value="${d.customerId}"></c:out></b></td>
										<td><b> <c:out value="${d.customerName}"></c:out></b></td>
										<td><b> <c:out value="${d.dateOfBirth}"></c:out></b></td>
										<td><b> <c:out value="${d.email}"></c:out></b></td>
										<td><b> <c:out value="${d.contactNum}"></c:out></b></td>
										<td><b> <c:out value="${d.address}"></c:out></b></td>

									</tr>

								</c:forEach>

							</table>

						</div>


					</div>

					<div style="clear: both;"></div>
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
						<div class="col-md-12">
							
							<table
								class="table data jqtable example table-bordered table-striped table-hover "
								id="my-table">
								<c:if test="${! empty nomineeList}">
								<thead>
									<tr>

										<th>Deposit Holder ID</th>
										<th>Nominee Name</th>
										<th>Nominee Age</th>
										<th>Nominee Address</th>
										<th>Nominee Relationship</th>
										<th>Nominee Pan</th>
										<th>Nominee Aadhar</th>

									</tr>
								</thead>
								</c:if>
								<tbody>
									<c:choose>
										<c:when test="${! empty nomineeList}">
                                            <c:forEach items="${nomineeList}" var="nominee">
											   <tr>

												<td><c:out value="${nominee.depositHolderId}"></c:out></td>
												<td><c:out value="${nominee.nomineeName}"></c:out></td>
												<td><c:out value="${nominee.nomineeAge}"></c:out></td>
												<td><c:out value="${nominee.nomineeAddress}"></c:out></td>
												<td><c:out value="${nominee.nomineeRelationship}"></c:out></td>
												<td><c:out value="${nominee.nomineePan}"></c:out></td>
												<td><c:out value="${nominee.nomineeAadhar}"></c:out></td>
										     </c:forEach>

                                         </c:when>
										<c:otherwise>
										<div class="successMsg">
						                      <b><font color="red">${nomineeError}</font></b>
				                     	</div>
											
										</c:otherwise>
									</c:choose>

<%-- 


									<c:if test="${! empty nomineeList}">
										<c:forEach items="${nomineeList}" var="nominee">
											<tr>

												<td><c:out value="${nominee.depositHolderId}"></c:out></td>
												<td><c:out value="${nominee.nomineeName}"></c:out></td>
												<td><c:out value="${nominee.nomineeAge}"></c:out></td>
												<td><c:out value="${nominee.nomineeAddress}"></c:out></td>
												<td><c:out value="${nominee.nomineeRelationship}"></c:out></td>
												<td><c:out value="${nominee.nomineePan}"></c:out></td>
												<td><c:out value="${nominee.nomineeAadhar}"></c:out></td>
										</c:forEach>

									</c:if> --%>

								</tbody>
							</table>
						</div>

					</div>





					<div class="header_customer col-sm-12 col-md-12 col-lg-12"
						data-toggle="collapse" data-target="#interestDetails"
						style="border-bottom: 1px dotted #05007e; margin-bottom: 10px; padding-top: 7px; padding-bottom: 7px;">
						<h3>
							<b></span> <spring:message code="label.interestDetails" /> </b> <i
								class="fa fa-chevron-circle-down pull-right" aria-hidden="true"></i>
						</h3>
						<span id="interestOk"
							style="display: none; color: #14c514; margin-right: 25px; margin-top: -12px; float: right;"
							class="glyphicon glyphicon-ok"></span><span id="interestCross"
							style="display: none; color: red; margin-right: 25px; margin-top: -12px; float: right;"
							class="glyphicon glyphicon-remove"></span>
					</div>

					<div id="interestDetails" class="collapse">
						<div class="form-group" id="everyMonthInt">

							<div class="col-sm-12">
								<div class="form-group" id="accountTypeDiv">
									<label class="col-md-4 control-label">Total Interest:</label>
									<div class="col-md-6">
										<fmt:formatNumber value="${totInterestAmount}" pattern="#.##"
											var="intAmt" />
										<td style="width: 15%;" class="commaSeparated">${intAmt}</td>
									</div>
								</div>

								<div class="form-group" id="accountTypeDiv">
									<label class="col-md-4 control-label">Interest Accrued:</label>
									<div class="col-md-6">
                                       <fmt:formatNumber value="${interestAccrued}" pattern="#.##"
											var="interestAccrued" />
											<td style="width: 15%;" class="commaSeparated">${interestAccrued}</td>
										

									</div>

								</div>
								<div class="form-group" id="accountTypeDiv">
									<label class="col-md-4 control-label">Rate Of Interest:</label>
									<div class="col-md-6">

										<input type="text" value="${interestRate}"
											class="form-control" style="background: #fff; border: none;"
											readonly="true" />

									</div>

								</div>


								<div class="col-md-10">

									<table
										class="table data jqtable example table-bordered table-striped table-hover "
										id="my-table">
										<c:if test="${! empty totalInterestList}">
										<thead>
											<tr>

												<th>Deposit Holder ID</th>
												 <th>Customer Name</th>
												<th>Interest Given</th>


											</tr>
										</thead>
										</c:if>
										<tbody>
											<c:if test="${! empty totalInterestList}">
												<c:forEach items="${totalInterestList}" var="interest">
													<tr>

														<td><c:out value="${interest.depositHolderId}"></c:out></td>
														<td><c:out value="${interest.customerName}"></c:out></td>
														<td><c:out value="${interest.totalInterest}"></c:out></td>
												</c:forEach>

											</c:if>

										</tbody>
									</table>
								</div>

							</div>





						</div>
					</div>

            
					<div style="clear: both;"></div>
              <%-- <c:if test="${depositCategory != 'AUTO'} "> --%>
               <c:if test="${depositCategory == 'REGULAR'}">
					<div class="header_customer col-sm-12 col-md-12 col-lg-12"
						data-toggle="collapse" data-target="#payOffDetails"
						style="border-bottom: 1px dotted #05007e; margin-bottom: 10px; padding-top: 7px; padding-bottom: 7px;">
						<h3>
							<b></span> <spring:message code="label.payOffDetails" /></b> <i
								class="fa fa-chevron-circle-down pull-right" aria-hidden="true"></i>
						</h3>
						<span id="payoffOk"
							style="display: none; color: #14c514; margin-right: 25px; margin-top: -12px; float: right;"
							class="glyphicon glyphicon-ok"></span><span id="payoffCross"
							style="display: none; color: red; margin-right: 25px; margin-top: -12px; float: right;"
							class="glyphicon glyphicon-remove"></span>
					</div>

					<div id="payOffDetails" class="collapse">
						<div class="form-group" id="everyMonthInt">
							<div style="clear: both;"></div>
							<div class="col-sm-12">
							<c:choose>
                                 <c:when test="${! empty payOff}">
							
                                
								<div class="form-group" id="accountTypeDiv">
									<div class="form-group" id="accountTypeDiv">
										<label class="col-md-4 control-label">Last PayOff
											Distribution Date:</label>
										<div class="col-md-6">
											<input type="text"
												value="<fmt:formatDate value="${lastPayoffDistributionDate}" pattern="MM-dd-yyyy" />"
												class="form-control" style="background: #fff; border: none;"
												readonly="true" />
										</div>
									</div>

									<div class="form-group" id="accountTypeDiv">
										<label class="col-md-4 control-label">Last PayOff
											Actual Amount:</label>

										<div class="col-md-6">
											<fmt:formatNumber value="${lastPayOffActualAmount}"
												pattern="#.##" var="actualAmt" />
											<td style="width: 15%;" class="commaSeparated">${actualAmt}</td>
											<%-- 												<input type="text"   value="${lastPayOffActualAmount}" pattern="#.##" class="form-control"  style="background: #fff; border: none;" readonly="true" /> 
 --%>
										</div>
									</div>

									<div class="form-group" id="accountTypeDiv">
										<label class="col-md-4 control-label">Last PayOff
											Type:</label>
										<div class="col-md-6">

											<input type="text" value="${lastpayOffType}"
												class="form-control" style="background: #fff; border: none;"
												readonly="true" />

										</div>
									</div>

									<div class="form-group" id="accountTypeDiv">
										<label class="col-md-4 control-label">Last PayOff
											Interest Percent:</label>
										<div class="col-md-6">

											<input type="text" value="${lastPayOffInterestPercent}"
												class="form-control" style="background: #fff; border: none;"
												readonly="true" />

										</div>
									</div>

									<div class="form-group" id="accountTypeDiv">
										<label class="col-md-4 control-label">Next PayOff Due
											Date:</label>
										<div class="col-md-6">

											<input type="text"
												value="<fmt:formatDate value="${nextPayOffDate}" pattern="MM-dd-yyyy" />"
												class="form-control" style="background: #fff; border: none;"
												readonly="true" />

										</div>
									</div>

									<div class="form-group" id="accountTypeDiv">
										<label class="col-md-4 control-label">Total PayOff
											Actual Amount:</label>
										<div class="col-md-6">
											<fmt:formatNumber value="${sumOffPayoffactualamount}"
												pattern="#.##" var="sumamt" />
											<td style="width: 15%;" class="commaSeparated">${sumamt}</td>
											<%-- 											<input type="text"  value="${sumOffPayoffactualamount}" class="form-control"  style="background: #fff; border: none;" readonly="true" /> 
 --%>
										</div>

										<%-- <table
														class="table data jqtable example table-bordered table-striped table-hover "
														id="my-table">
														<thead>
															<tr>

																<th>Deposit Holder ID</th>
																<th>PayOff Actual Amount</th> 
																<th>PayOff Distribution Date</th>
																<th>PayOff Interest Percent</th> 
																<th>PayOff Interest Type</th>
															    <th>PayOff Type</th>
																


															</tr>
														</thead>
														<tbody>
															<c:if test="${! empty payOffDistributionDetails}">
																<c:forEach items="${payOffDistributionDetails}" var="payOff">
																	<tr>

																		<td><c:out value="${payOff.depositHolderId}"></c:out></td>
																		<fmt:formatNumber value="${payOff.payOffActualAmount}" pattern="#.##" var="actualAmount"/>
						                                            	<td style="width: 15%;" class="commaSeparated">${actualAmount}</td>
																		<td align="left" style="width: 10%; padding-right: 20px; padding-left: 1px;"><fmt:formatDate pattern="dd/MM/yyyy"
									                                     value="${payOff.payOffDistributionDate}" />
																		<td><c:out value="${payOff.payOffInterestPercent}"></c:out></td>
																	    <td><c:out value="${payOff.payOffInterestType}"></c:out></td>
																		<td><c:out value="${payOff.payOffType}"></c:out></td>
																</c:forEach>

															</c:if>

														</tbody>
													</table> --%>

									</div>

								</div>
                               </c:when>
                                <c:otherwise>
                                     <div class="successMsg">
						                      <b><font color="red">${payOfferror}</font></b>
				                     	</div>
                                </c:otherwise>
                            </c:choose>

								<%-- <table
									class="table data jqtable example table-bordered table-striped table-hover "
									id="my-table">
									<thead>

										<tr>
											<th><spring:message code="label.lastDateOfPayOff" /></th>
											<th><spring:message code="label.nextDateOfPayOff" /></th>
											<th><spring:message code="label.payOffType" /></th>
											<th><spring:message code="label.payOffInterest" /></th>
											
										</tr>
									</thead>
									<tbody>
										<tr>

											<td>12/09/2019</td>
											<td>12/09/2019</td>
											<td>PART</td>
											<td>50000</td>
											
										</tr>

									</tbody>


								</table> --%>
							</div>
						</div>
</div>

 </c:if> 
 	<div style="clear: both;"></div>
               <c:if test="${depositCategory == 'REVERSE-EMI'}">
					<div class="header_customer col-sm-12 col-md-12 col-lg-12"
						data-toggle="collapse" data-target="#reverseEMIDetails"
						style="border-bottom: 1px dotted #05007e; margin-bottom: 10px; padding-top: 7px; padding-bottom: 7px;">
						<h3>
							<b></span> <spring:message code="label.reverseEmiDetails" /></b> <i
								class="fa fa-chevron-circle-down pull-right" aria-hidden="true"></i>
						</h3>
						<span id="reverseEmiOk"
							style="display: none; color: #14c514; margin-right: 25px; margin-top: -12px; float: right;"
							class="glyphicon glyphicon-ok"></span><span id="reverseEmiCross"
							style="display: none; color: red; margin-right: 25px; margin-top: -12px; float: right;"
							class="glyphicon glyphicon-remove"></span>
					</div>

					<div id="reverseEMIDetails" class="collapse">
						<div class="form-group" id="everyMonthInt">
							<div style="clear: both;"></div>
							<div class="col-sm-12">
							
								<table
										class="table data jqtable example table-bordered table-striped table-hover "
										id="my-table">
										<c:if test="${! empty reverseDepositDetails}">
										<thead>
											<tr>

												<th>Deposit ID</th>
												<th>Customer ID</th>
												<th>Deposit Type</th>
												<th>EMI Amount</th>
												<th>EMI Due Date</th>
												<th>Created By</th>
												<th>Created Date</th>
												<th>Account Number</th>
												<th>Deposit Holder Status</th>


											</tr>
										</thead>
										</c:if>
										<tbody>
											<c:if test="${! empty reverseDepositDetails}">
												<c:forEach items="${reverseDepositDetails}" var="emi">
													<tr>

														<td><c:out value="${emi.depositId}"></c:out></td>
														<td><c:out value="${emi.customerId}"></c:out></td>
														<td><c:out value="${emi.depositType}"></c:out></td>
														<td><c:out value="${emi.emiAmount}"></c:out></td>
														<td><c:out value="${emi.dueDate}"></c:out></td>
														<td><c:out value="${emi.createdBy}"></c:out></td>
														<td><c:out value="${emi.createdDate}"></c:out></td>
														<td><c:out value="${emi.accountNumber}"></c:out></td>
														<td><c:out value="${emi.depositHolderStatus}"></c:out></td>
														
														
												</c:forEach>

											</c:if>

										</tbody>
									</table>
									
										<table
										class="table data jqtable example table-bordered table-striped table-hover "
										id="my-table">
										<c:if test="${! empty reverseDepositDetails}">
										<thead>
											<tr>

												<th>Deposit ID</th>
												<th>Deposit Holder ID</th>
												<th>Beneficiary Name</th>
												<th>Beneficiary Account Number</th>
												<th>Amount To Transfer</th>
												
											</tr>
										</thead>
										</c:if>
										<tbody>
											<c:if test="${! empty benificiaryDetails}">
												<c:forEach items="${benificiaryDetails}" var="benificiary">
													<tr>

														<td><c:out value="${benificiary.depositId}"></c:out></td>
														<td><c:out value="${benificiary.depositHolderId}"></c:out></td>
														<td><c:out value="${benificiary.beneficiaryName}"></c:out></td>
														<td><c:out value="${benificiary.beneficiaryAccountNumber}"></c:out></td>
														<td><c:out value="${benificiary.amountToTransfer}"></c:out></td>
														
												</c:forEach>

											</c:if>

										</tbody>
									</table>
                             
						</div>
</div>
</div>
 </c:if> 
 


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

							<div class="col-md-12">
								<div id="linkedAccountDiv">
									<div class="form-group" id="accountTypeDiv">
										<label class="col-md-4 control-label"> Amount Paid: </label>
										<div class="col-md-6">


											<input type="text" value="${lastAmtPaid}"
												class="form-control" style="background: #fff; border: none;"
												readonly="true" />

										</div>

									</div>


									<div class="form-group" id="accountTypeDiv">
										<label class="col-md-4 control-label">Last Payment
											Date: </label>
										<div class="col-md-6">

											<input type="text"
												value="<fmt:formatDate value="${lastPaymentDate}" pattern="MM-dd-yyyy" />"
												class="form-control" style="background: #fff; border: none;"
												readonly="true" />

										</div>

									</div>

									<div class="form-group" id="accountTypeDiv">
										<label class="col-md-4 control-label">Mode Of Payment:</label>
										<div class="col-md-6">

											<input type="text" value="${modeOfPay}" class="form-control"
												style="background: #fff; border: none;" readonly="true" />

										</div>

									</div>

									<div class="form-group" id="accountTypeDiv">
										<label class="col-md-4 control-label">Paid By:</label>
										<div class="col-md-6">

											<input type="text" value="${createdBy}" class="form-control"
												style="background: #fff; border: none;" readonly="true" />

										</div>

									</div>

									<div class="form-group" id="accountTypeDiv">
										<label class="col-md-4 control-label">Next Payment
											Date:</label>
										<div class="col-md-6">

											<input type="text"
												value="<fmt:formatDate value="${dueDate}" pattern="MM-dd-yyyy" />"
												class="form-control" style="background: #fff; border: none;"
												readonly="true" />

										</div>

									</div>



								</div>




							</div>
						</div>


					</div>
				</form:form>
			</div>



		</div>


	</div>

</div>





<script>
			function val() {
				
				document.getElementById("paymentOk").style.display = 'none';
				document.getElementById("paymentCross").style.display = 'none';
				document.getElementById("payoffOk").style.display = 'none';
				document.getElementById("reverseEmiOk").style.display = 'none';
				document.getElementById("reverseEmiCross").style.display = 'none';
				document.getElementById("payoffCross").style.display = 'none';
				document.getElementById("nomineeOk").style.display = 'none';
				document.getElementById("depositHolderOk").style.display = 'none';
				document.getElementById("depositOk").style.display = 'none';
				document.getElementById("interestOk").style.display = 'none';

				document.getElementById("interestCross").style.display = 'none';
				document.getElementById("nomineeCross").style.display = 'none';
				document.getElementById("depositHolderCross").style.display = 'none';
				document.getElementById("depositOk").style.display = 'none';
				document.getElementById("depositCross").style.display = 'none';
			}
		</script>


<style>
input[type=checkbox], input[type=radio] {
	margin: 4px 1px 0px;
	margin-top: 1px\9;
	line-height: normal;
	zoom: 1.0;
}

.form-horizontal .control-label {
	padding-top: 0;
}
</style>