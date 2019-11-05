<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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

				/* 	if (parseInt('${depositHolderForm.deposit.tenure}') > 1) {
						oldTenureType = tenureArray[0] + 's';
					} */
					document.getElementById('fdTenureType').value = '${depositHolderForm.deposit.tenure}'
							+ ' ' + oldTenureType;

					if ('${depositHolderForm.editDepositForm.statusTenure}' != '') {

						var newTenureType = tenureArray[1];
						/* if (parseInt(tenureArray[2]) > 1)
							 newTenureType = newTenureType +'s'; */

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

				});

</script>


<div class="right-container" id="right-container">
	<div class="container-fluid">

		<div class="Flexi_deposit">

			<div class="header_customer">
				<h3>Confirm Reverse Annuity Modification</h3>
			</div>



			<div class="flexi_table">
				<form:form action="editReverseEMIPost" class="form-horizontal" method="post"
	name="fixedDeposit" commandName="depositHolderForm" id="editForm">

	<form:hidden path="deposit.interestRate" />
	<form:hidden path="deposit.tenureType" />
	<form:hidden path="deposit.tenure" />
    <form:hidden path="deposit.modifiedDate" id="modifiedDate" />
	<form:hidden path="deposit.dueDate" id="fdDueDate" />
	
	<form:hidden path="deposit.depositCategory"  />
	<form:hidden path="depositHolder.id" />
	<form:hidden path="depositHolder.customerId" />
		<form:hidden path="depositHolder.depositHolderCategory" />
	<form:hidden path="benificiary.benificiaryId" />
	<form:hidden path="benificiary.benificiaryName" />
	<form:hidden path="benificiary.bankAccountType"  />
	<form:hidden path="benificiary.amountToTransfer"  />
	<form:hidden path="benificiary.benificiaryAccountNumber" />
	<form:hidden path="benificiary.ifscCode" />
	<form:hidden path="benificiary.remarks" />
	<form:hidden path="benificiary.isActive" />
	<form:hidden path="editDepositForm.statusTenure" />

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






		<div style="clear: both; height: 10px;"></div>

		<div style="clear: both; height: 15px;"></div>

	</div>


	<div class="col-md-6">

		<div class="form-group">
			<label class="col-md-4 control-label">Reverse EMI
				Category </label>
			<div class="col-md-6">
				<form:input path="deposit.reverseEmiCategory"
					value="${depositHolderForm.deposit.reverseEmiCategory}"
					class="form-control" id="contactNum" readonly="true" />
			</div>
		</div>


		<div class="form-group">
			<label class="col-md-4 control-label">Created Date</label>
			<div class="col-md-6">
				<form:input path="deposit.createdDate" class="form-control"
					id="createdDate" readonly="true" />
			</div>
		</div>



		<div class="form-group">
			<label class="col-md-4 control-label">Deposit Amount</label>
			<div class="col-md-6">
				<fmt:formatNumber
					value="${depositHolderForm.deposit.depositAmount}"
					pattern="#.##" var="depositamount" />
				<form:hidden path="deposit.depositAmount" id="fdFixed" />
				<input type="text" class="form-control commaSeparated"
					value="${depositamount}" readonly />

			</div>
		</div>


	</div>



	<div class="header_me col-md-12">
		<h3>Beneficiary Details</h3>
	</div>

	<c:set var="benificiaryName"
		value="${depositHolderForm.benificiary.benificiaryName}" />
	<c:set var="bankAccountType"
		value="${depositHolderForm.benificiary.bankAccountType}" />
	<c:set var="benificiaryAccountNumber"
		value="${depositHolderForm.benificiary.benificiaryAccountNumber}" />
	<c:set var="amountToTransfer"
		value="${depositHolderForm.benificiary.amountToTransfer}" />
	<c:set var="ifsc"
		value="${depositHolderForm.benificiary.ifscCode}" />
	<c:set var="remark"
		value="${depositHolderForm.benificiary.remarks}" />
      <c:set var="isActive"
		value="${depositHolderForm.benificiary.isActive}" />

	<c:set var="benificiaryNameList"
		value="${fn:split(benificiaryName, ',')}" />
	<c:set var="bankAccountTypeList"
		value="${fn:split(bankAccountType, ',')}" />
	<c:set var="benificiaryAccountNumberList"
		value="${fn:split(benificiaryAccountNumber, ',')}" />
	<c:set var="amountToTransferList"
		value="${fn:split(amountToTransfer, ',')}" />
	<c:set var="remarkList"
		value="${fn:split(remark, ',')}" />
	<c:set var="ifscList"
		value="${fn:split(ifsc, ',')}" />
		<c:set var="isActiveList"
		value="${fn:split(isActive, ',')}" />
	



	<c:forEach items="${bankAccountTypeList}" var="accType"
		varStatus="status">

<c:if test="${isActiveList[status.index]!=0}">

		<div class="col-sm-6">
	
			<div class="form-1 col-md-6 col-xs-12">
			<h4>Beneficiary:${status.index+1}</h4>
				<table class="theader" width="400">
					<tr>
						<td align="right" style="padding-right: 15px;"><b
							style="font-size: 1em;"><spring:message
									code="label.benificiaryName" /></b><span style="color: red">*</span></td>
						<td><input value="${benificiaryNameList[status.index]}"
								class="myform-control" readonly /></td>
					</tr>
					<tr>
						<td align="right" style="padding-right: 15px;"><b
							style="font-size: 1em;"><spring:message
									code="label.accountType" /></b><span style="color: red">*</span></td>
						<td>
								<input value="${bankAccountTypeList[status.index]}"
								class="myform-control" readonly />
							</td>
					</tr>
					<tr>
						<td align="right" style="padding-right: 15px;"><b
							style="font-size: 1em;"><spring:message
									code="label.accountNo" /></b><span style="color: red">*</span></td>
						<td><input value="${benificiaryAccountNumberList[status.index]}"
								class="myform-control" readonly /></td>
					</tr>
					<tr>
						<td align="right" style="padding-right: 15px;"><b
							style="font-size: 1em;"><spring:message code="label.ifsc" /></b><span
							style="color: red">*</span></td>
						<td><input value="${ifscList[status.index]}"
								class="myform-control" readonly /></td>
					</tr>
				
					<tr>
						<td align="right" style="padding-right: 15px;"><b
							style="font-size: 1em;"><spring:message
									code="label.amount" /></b><span style="color: red">*</span></td>
						<td><input value="${amountToTransferList[status.index]}"
								class="myform-control" readonly /></td>
					</tr>
					<tr>
						<td align="right" style="padding-right: 15px;"><b
							style="font-size: 1em;"><spring:message
									code="label.remarks" /></b><span style="color: red">*</span></td>
						<td><input value="${remarkList[status.index]}"
								class="myform-control" readonly /></td>
								
								
					</tr>
				</table>
				
			</div>

		</div>
		<hr>
		</c:if>
	</c:forEach>




	<div class="col-md-12">
		<div class="form-group">

			<table align="center" class="f_deposit_btn">
			
				<tr>
				<c:if test="${baseURL[1] == 'bnkEmp'}">
							<c:set var="back" value="fdListBank" />
						</c:if>
						<c:if test="${baseURL[1] == 'users'}">
							<c:set var="back" value="fdList" />
						</c:if>
						
					<td><a href="${back}" class="btn btn-success"><spring:message
								code="label.back" /></a> <input type="submit"
						class="btn btn-info"
						value="<spring:message code="label.confirm"/>"></td>


				</tr>

			</table>

		</div>
	</div>
</form:form>
			</div>

		</div>
	</div>
</div>


