<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<script>
	
</script>
<div class="right-container" id="right-container">
	<div class="container-fluid">
		<div class="ledger-mapping">

			<form:form class="form-horizontal" id="ledgerEventMapping"
				name="ledgerEventMapping" autocomplete="off" method="post"
				commandName="ledgerEventMapping">
				<div class="successMsg" style="text-align: center;">
					<b><font color="red" style="font-size: 25px;">${error}</font></b>
				</div>
				<div class="list-of-rates">
					<div class="header_customer">
						<h3>Ledger Mapping</h3>
					</div>

					<div class="list-of-rates-table">
						<span class="counter pull-right"></span>

						<div>

							<div class="form-group" id="events">
								<label class="col-md-4 control-label">Event<span
									style="color: red">*</span></label>
								<div class="col-md-4">
									<form:select id="event" path="event" class="form-control"
										required="true">
										<form:option value="select" selected="true">
											<spring:message code="label.select" />
										</form:option>

										<c:forEach items="${eventOperationsList}" var="list1">
											<option>${list1.eventName}</option>
										</c:forEach>

									</form:select>

								</div>
							</div>




						</div>









						<table class="table table-bordered" align="center" id="my-table">
							<thead>
								<tr>
									<th>Mode Of Payment</th>
									<th>Debit Ledger</th>
									<th>Credit Ledger</th>
									
									<!-- 							<th>Edit</th> -->
								</tr>
							</thead>
							<tbody>


								
								<tr id="firstTR">
								<c:forEach items="${ledgerEventMappings}" var="ledgerEventMapping">
								
											<td>${ledgerEventMapping.id}</td>
									<td><form:select id="modeOfPayment" path="modeOfPayment"
											class="form-control" required="true">
											<form:option value="select" selected="true">
												<spring:message code="label.select" />
											</form:option>

											<c:forEach items="${modeOfPaymentList}" var="list1">
												<option>${list1.paymentMode}</option>
											</c:forEach>

										</form:select></td>
									<td><form:select id="debitGLAccount" path="debitGLAccount"
											class="form-control" required="true">
											<form:option value="select" selected="true">
												<spring:message code="label.select" />
											</form:option>

											<c:forEach items="${glConfigurationList}" var="list1">
												<option>${list1.glCode}-${list1.glAccount}</option>
											</c:forEach>

										</form:select></td>

									<td><form:select id="creditGLAccount"
											path="creditGLAccount" class="form-control" required="true">
											<form:option value="select" selected="true">
												<spring:message code="label.select" />
											</form:option>

											<c:forEach items="${glConfigurationList}" var="list1">
												<option>${list1.glCode}-${list1.glAccount}</option>
											</c:forEach>

										</form:select></td>
</c:forEach>
								</tr>
								

							</tbody>

						</table>

						<div class="errorMsg">
							<font color="red"
								style="text-align: center; width: 100%; float: left;"
								id="validationError">${error}</font>
						</div>

						<div class="col-md-offset-4 col-md-8"
							style="padding-bottom: 22px;">
							<input type="button" size="3" onclick="addRow()" value="addRow"
								class="btn btn-primary">

						</div>

						<div class="col-md-offset-4 col-md-8"
							style="padding-bottom: 22px;">
							<input type="submit" size="3" onclick="return addLedgerMapping()"
								value="save" class="btn btn-primary">

						</div>

					</div>


				</div>
			</form:form>
		</div>
	</div>
</div>
<script>
	$(document).ready(function() {

	});

	function addRow() {
		var html = $('#firstTR').clone();
		$("#my-table tbody").append(html);
	}

	function addLedgerMapping() {
		debugger;
		var events = document.getElementById('event').value;
		var submit = true;
		var errorMsg = "";
		var validationError = document.getElementById('validationError');
		validationError.innerHTML = "";

		if (events == "select" || events == "") {
			document.getElementById('event').style.borderColor = "red";
			errorMsg = "<br>Please Select Event";
			validationError.innerHTML += errorMsg;
			submit = false;
		} else {
			document.getElementById('event').style.borderColor = "black";

		}

		if (submit == true) {
			$("#ledgerEventMapping").attr("action", "saveLedgerMapping");
			$("#ledgerEventMapping").submit();
		} else {
			return false;
		}

	}
</script>
