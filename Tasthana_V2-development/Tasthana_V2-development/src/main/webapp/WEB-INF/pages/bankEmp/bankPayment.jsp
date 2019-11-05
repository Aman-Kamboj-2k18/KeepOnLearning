<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script>
	function val() {

		var canSubmit = true;

		var value = document.getElementById('fdPay').value;
		if (value == 'Select') {
			document.getElementById('errorMsg').style.display = 'block';
			document.getElementById('errorMsg').value = "Please select mode of payment.";
			canSubmit = false;
		}

		if (value == 'Cash') {
		}
		if (value == 'DD' || value == 'Cheque') {
			document.getElementById('errorMsg').style.display = 'none';
			var chequeNo = document.getElementById('chequeNo').value;
			if (chequeNo == '' || chequeNo == null) {
				document.getElementById('chequeNo').style.borderColor = "red";
				canSubmit = false;
			} else {
				document.getElementById('chequeNo').style.borderColor = "green";
			}
			var chequeDate = document.getElementById('chequeDate').value;
			if (chequeDate == '' || chequeDate == null) {
				document.getElementById('chequeDate').style.borderColor = "red";
				canSubmit = false;
			} else {
				document.getElementById('chequeDate').style.borderColor = "green";
			}

			var chequeBank = document.getElementById('chequeBank').value;
			if (chequeBank == '' || chequeBank == null) {
				document.getElementById('chequeBank').style.borderColor = "red";
				canSubmit = false;
			} else {
				document.getElementById('chequeBank').style.borderColor = "green";
			}
			var chequeBranch = document.getElementById('chequeBranch').value;
			if (chequeBranch == '' || chequeBank == null) {
				document.getElementById('chequeBranch').style.borderColor = "red";
				canSubmit = false;
			} else {
				document.getElementById('chequeBranch').style.borderColor = "green";
			}
		}
		return canSubmit;
	}
</script>

<script>
	$(document).ready(function() {
		document.getElementById('paymentMode').style.display = 'none';
		document.getElementById('ddChequeDiv').style.display = "none";

	});

	function searchDepost() {

		$("#fdForm").attr("action", "customerList");
		$("#fdForm").submit();

	}

	function search() {

		$("#fdForm").attr("action", "depositPayment");
		$("#fdForm").submit();

	}
	var depositHolderId_ = 0;
	var deathCertificateSubmitted_ = '';
	function goSubmit() {
		$("#fdForm").attr(
				"action",
				"depositeHolderNomineeDetails?depositHolderId="
						+ depositHolderId_);
		$("#fdForm").submit();
		
		
		/* var canSubmit = val();

		if (canSubmit == true) {
			$("#fdForm").attr("action", "payAmountToNominee");
			$("#fdForm").submit();
		} else {
			event.preventDefault();
		} */
	}

	function onChangeModeOfPay() {

		var value = document.getElementById('fdPay').value;

		var ddChecqueDiv = document.getElementById('ddChequeDiv');
		var chequeNoLabel = document.getElementById('chequeNoLabel');
		var ddNoLabel = document.getElementById('ddNoLabel');
		var chequeDateLabel = document.getElementById('chequeDateLabel');
		var ddDateLabel = document.getElementById('ddDateLabel');

		if (value == 'Cash') {
			ddChecqueDiv.style.display = "none";

		}
		if (value == 'DD') {
			ddChecqueDiv.style.display = "block";

			ddNoLabel.style.display = "block";
			ddDateLabel.style.display = "block";
			chequeDateLabel.style.display = "none";
			chequeNoLabel.style.display = "none";

			document.getElementsByName('chequeNo')[0].placeholder = 'Enter DD Number';
			document.getElementsByName('chequeDate')[0].placeholder = 'Enter DD Date';

		}
		if (value == 'Cheque') {
			ddChecqueDiv.style.display = "block";

			chequeNoLabel.style.display = "block";
			chequeDateLabel.style.display = "block";
			ddDateLabel.style.display = "none";
			ddNoLabel.style.display = "none";

			document.getElementsByName('chequeNo')[0].placeholder = 'Enter Cheque Number';
			document.getElementsByName('chequeDate')[0].placeholder = 'Enter Cheque Date';
		}

	}

	function onClickRadioBtn(valueSelected) {

		if (valueSelected == "On Death") {
			document.getElementById("deathCertificateTd").style.display = "block";
			document.getElementById("deathCertificateTh").style.display = "block";

		}
		if (valueSelected == "On Closing") {
			document.getElementById("deathCertificateTd").style.display = "none";
			document.getElementById("deathCertificateTh").style.display = "none";

		}
	}

	function onclickRadio(depositHolderId, deathCertificateSubmitted) {
		depositHolderId_ = depositHolderId;
		deathCertificateSubmitted_ = deathCertificateSubmitted;
		if (deathCertificateSubmitted == 'Yes') {
			
			/* $("#fdForm").attr(
					"action",
					"depositeHolderNomineeDetails?depositHolderId="
							+ depositHolderId);
			$("#fdForm").submit(); */
		} else {
			
			//document.getElementById('paymentMode').style.display = 'block';
		}
		document.getElementById("goBtn").disabled = false;
	}
</script>

<div class="right-container" id="right-container">
	<div class="container-fluid">

		<div class="Flexi_deposit">

			<div class="header_customer">
				<h3>
					<spring:message code="label.bankPayments" />
				</h3>
			</div>
			<div class="col-md-12" style="padding: 30px; text-align: center;">
				<span style="color: red;">${error}</span>
			</div>
			<div class="flexi_table">
				<form:form id="fdForm" class="form-horizontal" name="deposit"
					commandName="bankPaymentForm">
					<form:hidden path="customerName" />
					<form:hidden path="customerId" />
					<form:hidden path="deathCertificateSubmitted" />
					<form:hidden path="distAmtOnMaturity" />
					<form:hidden path="nomineeName" />
					<form:hidden path="nomineeAge" />
					<form:hidden path="nomineeAddress" />
					<form:hidden path="nomineeRelationship" />
					<form:hidden path="depositId" />
					<form:hidden path="depositHolderId" />
					<form:hidden path="bankPaymentId" />
					<form:hidden path="bankPaymnetDetailsId" />

					<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.fdAccountNum" /><span style="color: red">*</span></label>
						<div class="col-md-4">
							<form:input path="accountNumber"
								placeholder="Enter Account Number" id="fdID"
								class="myform-control"></form:input>
						</div>
						<div class="col-md-2">
							<input type="button" class="btn btn-primary"
								onclick="searchDepost()" value="Search">
						</div>
					</div>



					<div class="col-sm-12">
						<div class="space-45"></div>
					</div>
					<c:if test="${! empty depositList}">
						<table class="table table-striped table-bordered">
							<thead>
								<tr>
									<td><b><spring:message code="label.customerID" /></b></td>
									<td><b><spring:message code="label.depositHolderId" /></b></td>
									<td><b><spring:message code="label.customerName" /></b></td>
									<td><b><spring:message code="label.dateOfBirth" /></b></td>
									<td><b><spring:message code="label.email" /></b></td>
									<td><b><spring:message code="label.contactNumber" /></b></td>
									<td><b><spring:message code="label.address" /></b></td>

									<td id="deathCertificateTh"><b><spring:message
												code="label.deathCertificateSubmitted" /></b></td>

									<td><b><spring:message code="label.select" /></b></td>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${depositList}" var="dp">

									<tr>
										<td><b> <c:out value="${dp.customerId}"></c:out></b></td>
										<td><b> <c:out value="${dp.depositHolderId}"></c:out></b></td>
										<td><b> <c:out value="${dp.customerName}"></c:out></b></td>
										<td><b> <fmt:formatDate pattern="DD/MM/YYYY"
													value="${dp.dateOfBirth}" /></b></td>
										<td><b> <c:out value="${dp.email}"></c:out></b></td>
										<td><b> <c:out value="${dp.contactNum}"></c:out></b></td>
										<td><b> <c:out value="${dp.address}"></c:out></b></td>

										<td id="deathCertificateTd" style="border: 0;"><b> <c:out
													value="${dp.deathCertificateSubmitted}"></c:out></b></td>
										<td><b><form:radiobutton path="customerId"
													onclick="onclickRadio('${dp.depositHolderId}','${dp.deathCertificateSubmitted}')"
													value="${dp.customerId}" /></b></td>
										<td style="display: none"><form:radiobutton
												path="customerId" value="${dp.customerId}" />
									</tr>
							</tbody>
							</c:forEach>
						</table>
                   
					</c:if>


					<div class="form-group" id="paymentMode">
						<label class="col-md-4 control-label"><spring:message
								code="label.modeOfPay" /><span style="color: red">*</span></label>
						<div class="col-md-4">
							<form:select id="fdPay" path="paymentMode" class="myform-control"
								onchange="onChangeModeOfPay()">
								<form:option value="Select">
									<spring:message code="label.select" />
								</form:option>
								
								<form:option value="DD">
									<spring:message code="label.ddPayment" />
								</form:option>
								<form:option value="Cheque">
									<spring:message code="label.chequePayment" />
								</form:option>
							</form:select>
							<span id="fdPayError" style="display: none; color: red;">
								<spring:message code="label.validation" />
							</span>
						</div>
					</div>
					<div id="ddChequeDiv">
						<div class="form-group">
							<label class="col-md-4 control-label"><span
								id="chequeNoLabel" style="display: none"><spring:message code="label.chequeNumber" /><span
									style="color: red">*</span></span><span id="ddNoLabel"
								style="display: none"><spring:message code="label.ddNumber" /><span style="color: red">*</span></span>
							</label>
							<div class="col-md-4">
								<form:input path="chequeNo" class="myform-control" id="chequeNo"></form:input>
								<span id="ddNoError" style="display: none; color: red;"><spring:message
										code="label.enterData" /></span>
							</div>
						</div>
						
						
						
						
						<div class="form-group">
							<label class="col-md-4 control-label"> <span
								id="chequeDateLabel" style="display: none"><spring:message
										code="label.chequeDate" /><span
									style="color: red">*</span></span><span id="ddDateLabel"
								style="display: none"><spring:message
										code="label.ddDate" /><span style="color: red">*</span></span>
							</label>
							<div class="col-md-4">
								<form:input path="chequeDate"
									class="myform-control datepicker-here" id="chequeDate" readonly="true"></form:input>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.bank" /><span style="color: red">*</span></label>
							<div class="col-md-4">
								<form:input path="chequeBank" placeholder="Enter Bank"
									class="myform-control" id="chequeBank"></form:input>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.branch" /><span style="color: red">*</span></label>
							<div class="col-md-4">
								<form:input path="chequeBranch" placeholder="Enter Branch"
									class="myform-control" id="chequeBranch"></form:input>
							</div>
						</div>
					</div>
					<span id="errorMsg"
						style="display: none; color: red; margin-left: 369px; margin-bottom: 10px;"><spring:message
									code="label.plsSelectModefPayment" /></span>

					<div class="form-group">

						<div class="col-md-offset-4 col-md-8">
							<input type="button" onclick="goSubmit()" id="goBtn"
								disabled="true" class="btn btn-info" data-toggle="tooltip"
								title="Please first select the customer to click on search"
								value="<spring:message code="label.go"/>">
						</div>
					</div>

				</form:form>
			</div>

		</div>

		<style>
.flexi_table {
	margin-bottom: 210px;
}

.space-45 {
	height: 27px;
}

input#fdID {
	margin-top: 0px;
}
</style>