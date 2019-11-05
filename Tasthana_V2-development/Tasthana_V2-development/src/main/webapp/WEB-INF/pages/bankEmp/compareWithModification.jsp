<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<div class="right-container" id="right-container">
	<div class="container-fluid">


		<div class="list-of-rates">
			<div class="header_customer">
				<h3>Modification Summary</h3>

			</div>
			<div class="flexi_table">
				<div class="form-group">
					<label class="col-md-4 control-label">Deposit Id:</label>
					<div class="col-md-6">
						<input value="${depositHolderForm.depositId}" class="form-control"
							readonly />
					</div>
				</div>
				<br>
				<div class="form-group">
					<label class="col-md-4 control-label">Modification Date:</label>
					<div class="col-md-6">
						<fmt:formatDate pattern="dd/MM/yyyy"
							value="${depositHolderForm.createdDate}" var="modifiedDate" />
						<input value="${modifiedDate}" class="form-control" readonly />
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-4 control-label">Modification by:</label>
					<div class="col-md-6">

						<input value="${depositHolderForm.status}" class="form-control"
							readonly />
					</div>
				</div>



			</div>
			<div class="list-of-rates-table">
				<div class="search_filter">

					<div class="space-10"></div>
					<div class="col-sm-12" id="dividerLine">
						<hr>
					</div>

					<c:if test="${isChanged==1}">

						<table class="table table-bordered table-striped table-hover "
							align="center" id="my-table">
							<thead>
								<tr class="success">

									<th>Parameter Changed</th>
									<th>New Value</th>
									<th>Old Value</th>


								</tr>
							</thead>
							<tbody>
								<c:if test="${! empty depositHolderForm.maturityDate}">
									<tr>
										<td>Maturity Date</td>
										<td><fmt:formatDate pattern="dd/MM/yyyy"
												value="${depositHolderForm.maturityDate}" /></td>
										<td><fmt:formatDate pattern="dd/MM/yyyy"
												value="${depositHolderFormPrev.maturityDate}" /></td>
									</tr>
								</c:if>

								<c:if test="${! empty depositHolderForm.depositamount}">
									<tr>
										<td>Deposit Amount</td>
										<td class="commaSeparated"><c:out
												value="${depositHolderForm.depositamount}" /></td>
										<td class="commaSeparated"><c:out
												value="${depositHolderFormPrev.depositamount}" /></td>
									</tr>
								</c:if>


								<c:if test="${! empty depositHolderForm.paymentType}">
									<tr>
										<td>Payment Type</td>
										<td><c:out value="${depositHolderForm.paymentType}" /></td>
										<td><c:out value="${depositHolderFormPrev.paymentType}" /></td>
									</tr>
								</c:if>

								<c:if test="${! empty depositHolderForm.tenure}">
									<tr>
										<td>Tenure</td>
										<td><c:out
												value="${depositHolderForm.tenure} ${depositHolderForm.tenureType}" /></td>
										<td><c:out
												value="${depositHolderFormPrev.tenure} ${depositHolderFormPrev.tenureType}" /></td>
									</tr>
								</c:if>


								<c:if test="${! empty depositHolderForm.paymentMode}">
									<tr>
										<td>Payment Mode</td>
										<td><c:out value="${depositHolderForm.paymentMode}" /></td>
										<td><c:out value="${depositHolderFormPrev.paymentMode}" /></td>
									</tr>
								</c:if>

								<c:if test="${! empty depositHolderForm.interestRate}">
									<tr>
										<td>Interest Rate</td>
										<td><c:out value="${depositHolderForm.interestRate}" /></td>
										<td><c:out value="${depositHolderFormPrev.interestRate}" /></td>
									</tr>
								</c:if>

								<c:if test="${! empty depositHolderForm.payOffType}">
									<tr>
										<td>PayOff Type</td>
										<td><c:out value="${depositHolderForm.payOffType}" /></td>
										<td><c:out value="${depositHolderFormPrev.payOffType}" /></td>
									</tr>
								</c:if>



							</tbody>
						</table>
					</c:if>

					<c:if test="${! empty depositHolderForm.depositHolder}">

						<div class="header_customer">
							<h3>Payoff modification:</h3>
						</div>


						<table class="table table-bordered table-striped table-hover "
							align="center" id="my-table">
							<thead>
								<tr class="success">

									<th>Parameter Changed</th>
									<th>New Value</th>
									<th>Old Value</th>



								</tr>
							</thead>
							<tbody>


								<c:forEach items="${depositHolderForm.depositHolder}"
									var="depositHolder" varStatus="status">
									<tr class="w-50">
										<th><c:out value="Deposit holder id: ${depositHolder.id}" /></th>
									</tr>

									<c:if test="${! empty depositHolder.interestType}">
										<tr>
											<td>Interest Type</td>

											<td><c:out value="${depositHolder.interestType}" /></td>
											<td><c:out
													value="${depositHolderFormPrev.depositHolder[status.index].interestType}" /></td>
										</tr>
									</c:if>


									<c:if test="${! empty depositHolder.interestAmt}">
										<tr>
											<td>Interest Amount</td>
											<td class="commaSeparated"><c:out
													value="${depositHolder.interestAmt}" /></td>
											<td class="commaSeparated"><c:out
													value="${depositHolderFormPrev.depositHolder[status.index].interestAmt}" /></td>
										</tr>
									</c:if>


									<c:if test="${! empty depositHolder.interestPercent}">
										<tr>
											<td>Interest Percent</td>
											<td><c:out value="${depositHolder.interestPercent}" /></td>
											<td><c:out
													value="${depositHolderFormPrev.depositHolder[status.index].interestPercent}" /></td>
										</tr>
									</c:if>


									<c:if test="${! empty depositHolder.payOffAccountType}">
										<tr>
											<td>PayOff Account Type</td>
											<td><c:out value="${depositHolder.payOffAccountType}" /></td>
											<td><c:out
													value="${depositHolderFormPrev.depositHolder[status.index].payOffAccountType}" /></td>
										</tr>
									</c:if>


									<c:if test="${! empty depositHolder.transferType}">
										<tr>
											<td>Transfer Type</td>
											<td><c:out value="${depositHolder.transferType}" /></td>
											<td><c:out
													value="${depositHolderFormPrev.depositHolder[status.index].transferType}" /></td>
										</tr>
									</c:if>


									<c:if test="${! empty depositHolder.nameOnBankAccount}">
										<tr>
											<td>Name on Bank Account</td>
											<td><c:out value="${depositHolder.nameOnBankAccount}" /></td>
											<td><c:out
													value="${depositHolderFormPrev.depositHolder[status.index].nameOnBankAccount}" /></td>
										</tr>
									</c:if>


									<c:if test="${! empty depositHolder.accountNumber}">
										<tr>
											<td>Account Number</td>
											<td><c:out value="${depositHolder.accountNumber}" /></td>
											<td><c:out
													value="${depositHolderFormPrev.depositHolder[status.index].accountNumber}" /></td>
										</tr>
									</c:if>


									<c:if test="${! empty depositHolder.bankName}">
										<tr>
											<td>Bank Name</td>
											<td><c:out value="${depositHolder.bankName}" /></td>
											<td><c:out
													value="${depositHolderFormPrev.depositHolder[status.index].bankName}" /></td>
										</tr>
									</c:if>

									<c:if test="${! empty depositHolder.ifscCode}">
										<tr>
											<td>IFSC Code</td>
											<td><c:out value="${depositHolder.ifscCode}" /></td>
											<td><c:out
													value="${depositHolderFormPrev.depositHolder[status.index].ifscCode}" /></td>
										</tr>
									</c:if>


								</c:forEach>

							</tbody>

						</table>
					</c:if>
					<table align="center" class="f_deposit_btn">
						<tr>

							 <td><a href="searchCustForModificationReport"
								class="btn btn-danger"><spring:message code="label.back" /></a></td> 

						</tr>

					</table>



				</div>

			</div>
		</div>
	</div>
	<style>
.search_filter {
	display: flow-root;
	margin-bottom: 15px;
}
</style>
	<!-- <style>
		.table-bordered {
    border: 1px solid #BFBABA;
}
	  .table-bordered>tbody>tr>td, .table-bordered>tbody>tr>th, .table-bordered>tfoot>tr>td, .table-bordered>tfoot>tr>th, .table-bordered>thead>tr>td, .table-bordered>thead>tr>th{ border: 1px solid #C7C7C7;}
</style> -->