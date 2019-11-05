
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
<script src="<%=request.getContextPath()%>/resources/js/validation.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/js/ccvalidate-tapan.min.js"></script>
<link
	href="<%=request.getContextPath()%>/resources/css/datepicker.min.css"
	rel="stylesheet">

<div class="right-container" id="right-container" id="topDIV">
	<div class="container-fluid">



		<div class="Flexi_deposit">

			<div class="header_customer">
				<h3>
					<spring:message code="label.addPenaltyForWithdraw" />

				</h3>
			</div>
			<div class="Success_msg">
				<div class="successMsg" style="text-align: center; color: red;">
					${error}</div>
			</div>
			<div class="flexi_table">
				<form:form class="form-horizontal" autocomplete="off"
					id="wthdrawPenaltyForm" commandName="wthdrawPenaltyForm">
					
						<input type="hidden" name="menuId" value = "${menuId}"/>


					<div class="fd-list-table">


						<div class="form-group col-md-12">
							<label class="col-md-2 control-label"
								style="padding-top: 16px; text-align: -webkit-left;">Products
								<span style="color: red">*</span>
							</label>
							<div class="col-md-3" style="margin-left: -40px;">

								<form:select path="productConfigurationId"
									class="myform-control" id="productInfo"
									onchange="return produtNameShowAndHide(this.value)">
									<form:option value="">Select</form:option>
									<c:forEach var="productConfiguration"
										items="${productConfigurations}">
										<form:option value="${productConfiguration.id}">${productConfiguration.productCode}-${productConfiguration.productName}</form:option>
									</c:forEach>
								</form:select>


							</div>
						</div>
						
						<div class="col-md-4" style="margin-top: 12px;">
									<input type="radio" name="isPrematureWithdraw" id="isWithdrawPart" value = "0" checked="true"/>  Part Withdraw   
									<input type="radio" name="isPrematureWithdraw" id="isPrematureWithdraw" value = "1"/>  Premature Withdraw
								
								</div>

						<div class="form-group col-md-9" style="margin-left: -16px;">
							<label class="col-md-3 control-label"
								style="padding-top: 16px; text-align: -webkit-left;"><spring:message
									code="label.effectiveDate" /><span style="color: red">*</span></label>
							<div class="col-md-4" style="margin-left: -40px;">
								<form:input path="effectiveDate" id="effectiveDate"
									readonly="true" placeholder="Select Date"
									class="myform-control datepicker-here" dateFormat="dd-mm-yyy"
									required="true" />
								<input type="hidden" name="withdrawPenaltyFormList"
									id="withdrawPenaltyFormList">

							</div>
						</div>

						<div style="clear: both;"></div>
						<div id="tenureDIV">
							<div class="form-group">
								<div style="clear: both; margin-top: 35px; margin-bottom: 20px;">
									<h4>Tenure Based & Amount Based Penalty</h4>
									<div
										style="clear: both; width: 325px; border-top: 1px dotted #cfcfcf;"></div>
								</div>
								<div class="row">

									<div class="col-md-3">
										<div class="form-group">
											<label class="control-label">Amount<span
												style="color: red">*</span></label>
											<form:select path="amountSign" class="myform-control"
												id="amountSign" onchange="setSignValue(this.value)">
												<form:option value="">Sign</form:option>
												<form:option value="<">&lt;</form:option>
												<form:option value=">">&gt;</form:option>
												<form:option value="=">&#61;</form:option>
												<form:option value=">=">&gt;&#61;</form:option>
												<form:option value="<=">&lt;&#61;</form:option>
											</form:select>
										</div>
									</div>
									<div class="col-md-3">
										<div class="form-group">
											<label class="control-label">&nbsp; </label> <input
												id="amount" class="myform-control" placeholder="Amount">
										</div>
									</div>

								</div>


								<label class="control-label">Tenure<span
									style="color: red">*</span></label>

								<div class="row">
									<div class="col-md-3">
										<div class="form-group">
											<!-- <label class="control-label">Sign<span
												style="color: red">*</span></label> -->
											<form:select path="tenureSign" class="myform-control"
												id="tenureSign" onchange="setSignValue(this.value)">
												<form:option value="">Sign</form:option>
												<form:option value="<">&lt;</form:option>
												<form:option value=">">&gt;</form:option>
												<form:option value="=">&#61;</form:option>
												<form:option value=">=">&gt;&#61;</form:option>
												<form:option value="<=">&lt;&#61;</form:option>
											</form:select>
										</div>
									</div>
									<div class="col-md-3">
										<div class="form-group">
											<select id="tenureYear" class="myform-control" name="tenure">
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
									</div>
									<div class="col-md-3">
										<div class="form-group">
											<select id="tenureMonth" class="myform-control" name="tenure">
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
									</div>

									<div class="col-md-3">
										<div class="form-group">
											<select id="tenureDay" class="myform-control" name=tenure>
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


							</div>

							<div class="form-group">

								<div style="clear: both; margin-top: 35px; margin-bottom: 20px;">
									<h4>Penalty</h4>
									<div
										style="clear: both; width: 325px; border-top: 1px dotted #cfcfcf;"></div>
								</div>

								<div class="form-group col-md-11">
									<label class="col-md-6 control-label"
										style="padding-top: 16px; text-align: -webkit-left;">Rate
										%<span style="color: red">*</span>
									</label> <label class="col-md-6 control-label"
										style="padding-top: 16px; text-align: -webkit-left;">Flat
										Amount<span style="color: red">*</span>
									</label>

								</div>

								<div class="form-group col-md-12">
									<div class="col-md-4">
										<form:input path="penaltyRate" id="penaltyRateSign"
											placeholder="Penalty Rate" class="myform-control"
											onkeypress="amountValidation(event)" />
									</div>
									<div class="col-md-4">
										<form:input path="penaltyFlatAmount"
											id="penaltyFlatAmountSign" placeholder="Penalty Flat Amount"
											class="myform-control" onkeypress="numberValidation(event)" />
									</div>
									<div class="col-md-4">
										<input type="button" class="btn btn-info add-row"
											value="Add Rate" style="margin-top: 7px;"
											onclick="tenureBasedTableAddTrValue()">
									</div>
								</div>


							</div>



							<div class="form-group col-md-12">


								<div style="clear: both; height: 5px;"></div>


								<table id="penaltySignTable">

									<tbody>
										<tr>
											<th>Effective Date</th>
											<th>Product Code</th>
											<th>Amount Sign</th>
											<th>Amount</th>
											<th>Tenure Sign</th>
											<th>Tenure</th>
											<th><spring:message code="label.penaltyRate" /></th>
											<th><spring:message code="label.penaltyFlatAmount" /></th>
											<th style="display: none">ProductId</th>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
						<div id="amountDIV" style="display: none;">
							<div style="clear: both;"></div>

							<div class="form-group col-md-5">
								<div style="clear: both; margin-top: 35px margin-bottom: -5px;"
									class="col-md-12">
									<h4>Amount Range</h4>
								</div>
								<div
									style="clear: both; width: 325px; border-top: 1px dotted #cfcfcf;"></div>

								<div class="form-group col-md-11">
									<label class="col-md-6 control-label"
										style="padding-top: 16px; text-align: -webkit-left;">From<span
										style="color: red">*</span></label> <label
										class="col-md-6 control-label"
										style="padding-top: 16px; text-align: -webkit-left;">To<span
										style="color: red">*</span></label>

								</div>



								<div class="form-group col-md-11">

									<div class="col-md-6">
										<form:input path="amountFrom" type="number" id="amountFrom"
											placeholder="Amount From" class="myform-control"
											onkeypress="numberValidation(event)" />
									</div>
									<div class="col-md-6" style="margin-left: -15px;">
										<form:input path="amountTo" type="number" id="amountTo"
											placeholder="Amount To" class="myform-control"
											onkeypress="numberValidation(event)" />
									</div>
								</div>
							</div>

							<div class="form-group col-md-5">
								<div style="clear: both; text-align: -webkit-left;"
									class="col-md-12">
									<h4>Penalty</h4>
								</div>
								<div
									style="clear: both; width: 325px; border-top: 1px dotted #cfcfcf;"></div>
								<div class="form-group col-md-11">
									<label class="col-md-6 control-label"
										style="padding-top: 16px; text-align: -webkit-left;">Rate
										%<span style="color: red">*</span>
									</label> <label class="col-md-6 control-label"
										style="padding-top: 16px; text-align: -webkit-left;">Flat
										Amount<span style="color: red">*</span>
									</label>

								</div>

								<div class="form-group col-md-12">
									<div class="col-md-6">
										<form:input path="penaltyRate" type="number" id="penaltyRate"
											placeholder="Penalty Rate" class="myform-control"
											onkeypress="amountValidation(event)" />
									</div>
									<div class="col-md-6" style="margin-left: -15px;">
										<form:input path="penaltyFlatAmount" type="number"
											id="penaltyFlatAmount" placeholder="Penalty Flat Amount"
											class="myform-control" onkeypress="numberValidation(event)" />
									</div>
								</div>


							</div>
							<div class="form-group col-md-2"
								style="margin-top: 95px; margin-left: 15px;">
								<input type="button" class="btn btn-info add-row"
									value="Add Rate" onclick="addPenaltyRates()">
							</div>


							<div class="form-group col-md-12">


								<div style="clear: both; height: 5px;"></div>


								<table id="penaltyRateTable">

									<tbody>
										<tr>
											<th>Effective Date</th>
											<th>Product Code</th>
											<th><spring:message code="label.amountTo" /></th>
											<th><spring:message code="label.amountFrom" /></th>
											<th><spring:message code="label.penaltyRate" /></th>
											<th><spring:message code="label.penaltyFlatAmount" /></th>
											<th style="display: none">ProductId</th>
										</tr>
									</tbody>
								</table>

							</div>

						</div>
					</div>
					<div class="form-group">
						<div class="col-md-6" style="text-align: center;">
							<span id="errorMsg" style="display: none; color: red"><spring:message
									code="label.categoryMsg" /></span> <span id="sucessMsg"
								style="display: none; color: green"><spring:message
									code="label.categoryMsg" /></span> <span id="radioError"
								style="display: none; color: red"><spring:message
									code="label.radioError" /></span> <span id="validationMsg"
								style="display: none; color: red"><spring:message
									code="label.startDayEndDayError" /> </span> <span id="graterMsg"
								style="display: none; color: red"><spring:message
									code="label.withdrawPenaltyError" /> </span> <span id="negativeError"
								style="display: none; color: red"><spring:message
									code="label.negativeError" /> </span> <span id="duplicateError"
								style="display: none; color: red"><spring:message
									code="label.withdrawPenaltyduplicateError" /> </span> <span
								id="nriAccountTypeError" style="display: none; color: red"><spring:message
									code="label.nriAccountTypeError" /> </span>
						</div>
						<div class="col-md-6">
							<input type="button" id="goBtn" class="btn btn-info"
								data-toggle="tooltip" onclick="submitData()" value="Save"
								title="Please select mode of transfer and deposit id">
						</div>
					</div>
				</form:form>
			</div>

		</div>

	</div>
</div>

<style type="text/css">
form {
	margin: 20px 0;
}

form input, button {
	padding: 5px;
}

table {
	width: 100%;
	margin-bottom: 20px;
	border-collapse: collapse;
}

table, th, td {
	border: 1px solid #cdcdcd;
}

table th, table td {
	padding: 10px;
	text-align: left;
}

input[type=checkbox], input[type=radio] {
	margin: 4px 1px 0px;
	margin-top: 1px\9;
	line-height: normal;
	zoom: 1.0;
}

.form-horizontal .control-label {
	padding-top: 0;
}

.form-horizontal .control-label {
	padding-top: 0;
	font-size: 12px;
	font-weight: 100;
}

.form-horizontal .form-group {
	margin-left: 0;
	margin-right: 0;
}
</style>
<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<script type="text/javascript">
	// global variable:
	var mapValidater = new Map();
	var duplicate = 0;
	var myArray = new Array();

	// Function called when the form is submitted.
	// Function adds a task to the global array.
	var maxNumber = 0;

	function addPenaltyRates() {
		var submit = false;
		// Get the task:
		var amountFrom = document.getElementById('amountFrom').value;
		var amountTo = document.getElementById('amountTo').value;
		var penaltyRate = document.getElementById('penaltyRate').value;
		var penaltyFlatAmount = document.getElementById('penaltyFlatAmount').value;
		document.getElementById('errorMsg').style.display = 'none';

		//var currency = document.getElementById('currency').value;
		if (amountFrom == '') {
			document.getElementById('amountFrom').style.borderColor = "red";
			return submit;
		}

		else {
			document.getElementById('amountFrom').style.borderColor = "black";
		}

		if (amountTo == '') {
			document.getElementById('amountTo').style.borderColor = "red";
			return submit;
		}

		else {
			document.getElementById('amountTo').style.borderColor = "black";
		}

		if (Number(amountFrom) > Number(amountTo)) {
			document.getElementById('graterMsg').style.display = 'block';

			return submit;
		} else {

			document.getElementById('graterMsg').style.display = 'none';
			document.getElementById('errorMsg').style.display = 'none';
		}

		if (penaltyRate == "" && penaltyFlatAmount == "") {
			document.getElementById('penaltyRate').style.borderColor = "red";
			document.getElementById('penaltyFlatAmount').style.borderColor = "red";
			document.getElementById('negativeError').style.display = 'block';
			document.getElementById('negativeError').textContent = 'At least one penalty rate needs to be entered';
			return submit;
		} else {
			document.getElementById('penaltyRate').style.borderColor = "black";
			document.getElementById('penaltyFlatAmount').style.borderColor = "black";
			document.getElementById('negativeError').style.display = 'none';
		}

		if (Number(amountFrom) < 0 || Number(amountTo) < 0) {
			document.getElementById('amountFrom').style.borderColor = "red";
			document.getElementById('amountTo').style.borderColor = "red";
			document.getElementById('negativeError').style.display = 'block';
			return submit;
		} else {
			document.getElementById('amountFrom').style.borderColor = "black";
			document.getElementById('amountTo').style.borderColor = "black";
			document.getElementById('negativeError').style.display = 'none';
		}

		duplicate = 0;
		if (amountFrom && amountTo) {

			if (amountFrom <= maxNumber) {
				document.getElementById('duplicateError').style.display = 'block';
				return false;
			}

			// Add the item to the array:
			if (myArray.length > 0) {

				for (var i = 0, count = myArray.length; i < count; i++) {
					if (Number(amountFrom) > Number(myArray[i].amountFrom)) {
						if (Number(amountFrom) <= Number(myArray[i].amountTo)) {
							duplicate = 1;
							break;
						}
					}
					if (Number(amountTo) > Number(myArray[i].amountFrom)) {
						if (Number(amountTo) <= Number(myArray[i].amountTo)) {
							duplicate = 1;
							break;
						}
					}

				}
				if (duplicate == 1) {
					document.getElementById('duplicateError').style.display = 'block';
				} else {
					document.getElementById('duplicateError').style.display = 'none';

				}

			}
			if (duplicate == 1)
				return false;
		}

		var message = '';
		var trHTML = '';

		var mapSize = mapValidater.size;
		if (mapSize == 0) {
			mapValidater.set(amountFrom, amountTo);
			document.getElementById('errorMsg').style.display = 'none';

		} else {

			if (mapValidater.has(amountFrom)) {

				if (mapValidater.get(amountFrom) == amountTo) {
					document.getElementById('amountFrom').style.borderColor = "red";
					document.getElementById('amountTo').style.borderColor = "red";
					document.getElementById('effectiveDate').style.borderColor = "black";
					document.getElementById('errorMsg').textContent = "Amount From and Amount To can not be Duplicate!";
					document.getElementById('errorMsg').style.display = 'block';
					return false;

				} else {
					mapValidater.set(amountFrom, amountTo);
					document.getElementById('errorMsg').style.display = 'none';
				}
			}

			else {
				mapValidater.set(amountFrom, amountTo);
				document.getElementById('errorMsg').style.display = 'none';
			}
		}

		maxNumber = amountTo;
		var dateEffective = document.getElementById('effectiveDate').value;
		var prodSlab = $("#productInfo option:selected").html();
		trHTML = '<tr><td>' + dateEffective + '</td><td>' + prodSlab
				+ '</td><td>' + amountFrom + '</td><td>' + amountTo
				+ '</td><td>' + penaltyRate + '</td><td>' + penaltyFlatAmount
				+ '</td><td style="display:none">'
				+ $("#productInfo option:selected").val() + '</td></tr>';

		$('#penaltyRateTable').append(trHTML);

		if (amountFrom && amountTo) {
			// 			// Check for overlapping range

			// Add the item to the array:					
			myArray.push({
				amountFrom : amountFrom,
				amountTo : amountTo,

			})

			// Update the page:
			message = '<h2>Persons Entered</h2><ol>';
			for (var i = 0, count = myArray.length; i < count; i++) {
				message += '<li><span>' + myArray[i].amountFrom
						+ '</span><span> ' + myArray[i].amountTo;
			}
			message += '</ol>';
			document.getElementById('errorMsg').innerHTML = message;

		} // End of IF
		// Return false to prevent submission:
		//document.getElementById('effectiveDate').value = "";
		document.getElementById('amountFrom').value = "";
		document.getElementById('amountTo').value = "";
		document.getElementById('penaltyRate').value = "";
		document.getElementById('penaltyFlatAmount').value = "";
		//$("#productInfo").val("");

		return false;

	}

	function tenureBasedTableAddTrValue() {
		var tenureYear = $("#tenureYear").val();
		var tenureDay = $("#tenureDay").val();
		var tenureMonth = $("#tenureMonth").val();
		var effectiveDate = document.getElementById('effectiveDate').value;
		var tenureSign = $("#tenureSign").val();
		var amountSign = $("#amountSign").val();
		var amount = $("#amount").val();
		var productInfo = $("#productInfo").val();
		var monthDayYear = "";

		if (tenureYear != "") {
			monthDayYear += tenureYear + ",";
		}
		if (tenureMonth != "") {
			monthDayYear += tenureMonth + ",";

		}
		if (tenureDay != "") {
			monthDayYear += tenureDay + ",";
		}
		monthDayYear = monthDayYear.substring(0, monthDayYear.lastIndexOf(","))
		var penaltyRateSign = document.getElementById('penaltyRateSign').value;
		var penaltyFlatAmountSign = document
				.getElementById('penaltyFlatAmountSign').value;

		if (productInfo == "") {
			document.getElementById('productInfo').style.borderColor = "red";
			document.getElementById('errorMsg').textContent = "Please select Product !";
			document.getElementById('errorMsg').style.display = 'block';
			return false;
		} else {
			document.getElementById('productInfo').style.borderColor = "#ccc";
			document.getElementById('errorMsg').textContent = "";
			document.getElementById('errorMsg').style.display = 'none';

		}
		if (effectiveDate == "") {
			document.getElementById('effectiveDate').style.borderColor = "red";
			document.getElementById('errorMsg').textContent = "Please select Date !";
			document.getElementById('errorMsg').style.display = 'block';
			return false;
		} else {
			document.getElementById('effectiveDate').style.borderColor = "#ccc";
			document.getElementById('errorMsg').textContent = "";
			document.getElementById('errorMsg').style.display = 'none';

		}

		if (tenureYear == "" && tenureDay == "" && tenureMonth == "") {
			document.getElementById('tenureYear').style.borderColor = "red";
			document.getElementById('tenureDay').style.borderColor = "red";
			document.getElementById('tenureMonth').style.borderColor = "red";
			document.getElementById('errorMsg').textContent = "Please select atleast one period !";
			document.getElementById('errorMsg').style.display = 'block';
			return false;
		} else {
			document.getElementById('tenureYear').style.borderColor = "#ccc";
			document.getElementById('tenureDay').style.borderColor = "#ccc";
			document.getElementById('tenureMonth').style.borderColor = "#ccc";
			document.getElementById('errorMsg').textContent = "";
			document.getElementById('errorMsg').style.display = 'none';

		}

		if (tenureSign == "") {
			document.getElementById('tenureSign').style.borderColor = "red";
			document.getElementById('errorMsg').textContent = "Please select Tenure Sign !";
			document.getElementById('errorMsg').style.display = 'block';
			return false;
		} else {
			document.getElementById('tenureSign').style.borderColor = "#ccc";
			document.getElementById('errorMsg').textContent = "";
			document.getElementById('errorMsg').style.display = 'none';

		}

		if (amountSign == "") {
			document.getElementById('amountSign').style.borderColor = "red";
			document.getElementById('errorMsg').textContent = "Please enter Amount Sign !";
			document.getElementById('errorMsg').style.display = 'block';
			return false;
		} else {
			document.getElementById('amountSign').style.borderColor = "#ccc";
			document.getElementById('errorMsg').textContent = "";
			document.getElementById('errorMsg').style.display = 'none';

		}

		if (amount == "") {
			document.getElementById('amount').style.borderColor = "red";
			document.getElementById('errorMsg').textContent = "Please enter Sign !";
			document.getElementById('errorMsg').style.display = 'block';
			return false;
		} else {
			document.getElementById('amount').style.borderColor = "#ccc";
			document.getElementById('errorMsg').textContent = "";
			document.getElementById('errorMsg').style.display = 'none';

		}

		if (penaltyRateSign == "" && penaltyFlatAmountSign == "") {
			document.getElementById('penaltyRate').style.borderColor = "red";
			document.getElementById('penaltyFlatAmount').style.borderColor = "red";
			document.getElementById('errorMsg').textContent = "Please enter atleast one Penalty Rate or Flat Amount !";
			document.getElementById('errorMsg').style.display = 'block';
			return false;
		} else {
			document.getElementById('penaltyRate').style.borderColor = "#ccc";
			document.getElementById('penaltyFlatAmount').style.borderColor = "#ccc";
			document.getElementById('errorMsg').textContent = "";
			document.getElementById('errorMsg').style.display = 'none';

		}

		if (penaltyRateSign != "" && penaltyFlatAmountSign != "") {
			document.getElementById('penaltyRate').style.borderColor = "red";
			document.getElementById('penaltyFlatAmount').style.borderColor = "red";
			document.getElementById('errorMsg').textContent = "Please enter only one Penalty Rate or Flat Amount !";
			document.getElementById('errorMsg').style.display = 'block';
			return false;
		} else {
			document.getElementById('penaltyRate').style.borderColor = "#ccc";
			document.getElementById('penaltyFlatAmount').style.borderColor = "#ccc";
			document.getElementById('errorMsg').textContent = "";
			document.getElementById('errorMsg').style.display = 'none';

		}
		var tableData = document.getElementById('penaltySignTable');
		var numberOfRows = tableData.rows.length;
		var row1 = false;
		var row2 = false;

		for (var i = 1; i < numberOfRows; i += 1) {
			var row = tableData.rows[i];
			var effictiveDateRow = row.cells[0].innerText;
			var productCodeName = row.cells[1].innerText;
			var amountSignRow = row.cells[2].innerText;
			//var amount = row.cells[3].innerText;
			var tenureSignRow = row.cells[3].innerText;
			var tenure = row.cells[3].innerText;
			var penaltySignRate = row.cells[4].innerText;
			var penaltySignFlatAmount = row.cells[5].innerText;
			

		}
		var prod = $("#productInfo option:selected").html();
		var isPrematureWithdraw=0;
		if($("#isWithdrawPart").prop('checked')){
			isPrematureWithdraw = 0;
		}else{
			isPrematureWithdraw = 1;
		}
		var signTableTd = '<tr><td>' + effectiveDate + '</td><td>' + prod
				+ '</td><td>' + amountSign + '</td><td>' + amount + '</td><td>'
				+ tenureSign + '</td><td>' + monthDayYear + '</td><td>'
				+ penaltyRateSign + '</td><td>' + penaltyFlatAmountSign
				+ '</td><td style="display:none">'
				+ $("#productInfo option:selected").val() + '</td><td style="display:none">'
				+ isPrematureWithdraw + '</td></tr>';

		$('#penaltySignTable').append(signTableTd);

		$("#tenureYear").val("");
		$("#tenureDay").val("");
		$("#tenureMonth").val("");
		//$("#amount").val("");
		//document.getElementById('effectiveDate').value = "";

		//$("#amountSign").val("").change();
		$("#tenureSign").val("").change();
		//$("#productInfo").val("");

		document.getElementById('penaltyRateSign').value = "";
		document.getElementById('penaltyFlatAmountSign').value = "";

	}

	function submitData() {
		var myArray = new Array;
		var effectiveDate = document.getElementById('effectiveDate').value;
		var productId = document.getElementById('productInfo').value;
		var penaltySignTableData = document.getElementById('penaltySignTable');
		var penaltySignTableNumberOfRows = penaltySignTableData.rows.length;
		var isPrematureWithdraw=0;
		if($("#isWithdrawPart").prop('checked')){
			isPrematureWithdraw = 0;
		}else{
			isPrematureWithdraw = 1;
		}
		for (var i = 1; i < penaltySignTableNumberOfRows; i += 1) {
			var penaltySignTableRow = penaltySignTableData.rows[i];
			var selectedDate = penaltySignTableRow.cells[0].innerText;
			var selectedAmountSign = penaltySignTableRow.cells[2].innerText;
			var selectedAmount = penaltySignTableRow.cells[3].innerText;
			var selectedTenureSign = penaltySignTableRow.cells[4].innerText;
			var selectedTenure = penaltySignTableRow.cells[5].innerText;
			var penaltySignTablePenaltyRate = penaltySignTableRow.cells[6].innerText;
			var penaltySignTablePenaltyFlatAmount = penaltySignTableRow.cells[7].innerText;
			var selectedProductId = penaltySignTableRow.cells[8].innerText;
			var isPremature = penaltySignTableRow.cells[9].innerText;
			myArray.push({
				effectiveDate : selectedDate,
				tenureSign : selectedTenureSign,
				amountSign : selectedAmountSign,
				amount : selectedAmount,
				tenure : selectedTenure,
				penaltyRate : penaltySignTablePenaltyRate,
				penaltyFlatAmount : penaltySignTablePenaltyFlatAmount,
				productConfigurationId : selectedProductId,
				isPrematureWithdraw : isPremature,
			})

		}
		var withdrawPenaltyFormList = JSON.stringify(myArray);
		withdrawPenaltyFormList = '{"withdrawPenaltyFormList":'
				+ withdrawPenaltyFormList + '}';
		$("#withdrawPenaltyFormList").val(withdrawPenaltyFormList);
		var validation = val();
		if (validation) {
			$("#wthdrawPenaltyForm").attr("action", "saveWithdrawPenalty");
			$("#wthdrawPenaltyForm").submit();
		}

	}

	$(document).ready(function() {
		$(".add-row").click(function() {

		});

		// Find and remove selected table rows
		$(".delete-row").click(function() {
			$("table tbody").find('input[name="record"]').each(function() {
				if ($(this).is(":checked")) {
					$(this).parents("tr").remove();
				}
			});
		});
	});

	function val() {

		var submit = true;
		var productInfo = document.getElementById('productInfo').value;
		var effectiveDate = document.getElementById('effectiveDate').value;

		var amountFrom = document.getElementById('amountFrom').value;
		var penaltyFlatAmount = document.getElementById('penaltyFlatAmount').value;
		var amountTo = document.getElementById('amountTo').value;
		var penaltyRate = document.getElementById('penaltyRate').value;
		var tableId = "";
		if ($("#amountBased").prop('checked')) {
			tableId = "penaltyRateTable";

		} else {
			tableId = "penaltySignTable";
		}
		var tableData = document.getElementById(tableId);
		var numberOfRows = tableData.rows.length;
		if (numberOfRows == 1) {

			if (productInfo == "") {
				document.getElementById('productInfo').style.borderColor = "red";
				document.getElementById('errorMsg').textContent = "Product can not be empty!";
				document.getElementById('errorMsg').style.display = 'block';
				submit = false;
				return submit;

			} else {
				document.getElementById('productInfo').style.borderColor = "#ccc";
				document.getElementById('errorMsg').style.display = 'none';

			}

			if (effectiveDate == "") {
				document.getElementById('effectiveDate').style.borderColor = "red";
				document.getElementById('errorMsg').textContent = "Effective Date can not be empty!";
				document.getElementById('errorMsg').style.display = 'block';
				submit = false;
				return submit;

			} else {
				document.getElementById('effectiveDate').style.borderColor = "#ccc";
				document.getElementById('errorMsg').style.display = 'none';

			}
			if (amountFrom == '' || amountTo == '') {
				document.getElementById('amountFrom').style.borderColor = "red";
				document.getElementById('amountTo').style.borderColor = "red";
				document.getElementById('effectiveDate').style.borderColor = "black";
				document.getElementById('errorMsg').textContent = "Amount From and Amount To can not be empty!";
				document.getElementById('errorMsg').style.display = 'block';
				submit = false;
				return submit;

			} else {
				document.getElementById('amountFrom').style.borderColor = "#ccc";
				document.getElementById('amountTo').style.borderColor = "#ccc";
				document.getElementById('errorMsg').style.display = 'none';

			}
			if (penaltyRate == "" && penaltyFlatAmount == "") {
				document.getElementById('penaltyRate').style.borderColor = "red";
				document.getElementById('penaltyFlatAmount').style.borderColor = "red";
				document.getElementById('errorMsg').textContent = "Penalty Rate and Penalty Flat Amount can not be empty both of them  atleast one is mandtory!";
				document.getElementById('errorMsg').style.display = 'block';
				submit = false;
				return submit;

			} else {
				document.getElementById('penaltyRate').style.borderColor = "#ccc";
				document.getElementById('penaltyFlatAmount').style.borderColor = "#ccc";
				document.getElementById('errorMsg').style.display = 'none';

			}
			if (numberOfRows == 1) {
				document.getElementById('errorMsg').textContent = "Please add Rate!";
				document.getElementById('errorMsg').style.display = 'block';
				submit = false;
				return submit;
			} else {
				document.getElementById('amountFrom').style.borderColor = "black";
				document.getElementById('amountTo').style.borderColor = "black";
				document.getElementById('validationMsg').style.display = 'none';

			}
		}

		if (numberOfRows > 1) {

		}

		return submit;
	}

	function tenureAndAmountBased(event) {
		var amountAndTenureBased = event.target.value;
		/* if (amountAndTenureBased == 'Amount Based') {
			$("#tenureDIV").hide();
			$("#amountDIV").show();
		} else {
			$("#tenureDIV").show();
			$("#amountDIV").hide();
		} */

	}

	function setSignValue(value) {
		//$("#sign").val(value);

	}

	function produtNameShowAndHide(value) {
		//$("#productNameDIV").html('<lable class = "col-md-2 control-lable">Product Name</lable><input type="text" class="myform-control" readonly value = "'+value+'"/>')

	}
</script>