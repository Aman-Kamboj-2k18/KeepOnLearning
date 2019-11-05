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
<link
	href="<%=request.getContextPath()%>/resources/css/datepicker.min.css"
	rel="stylesheet">
<script
	src="<%=request.getContextPath()%>/resources/js/ccvalidate-tapan.min.js"></script>


<div class="right-container" id="right-container">
	<div class="container-fluid">



		<div class="Flexi_deposit">

			<div class="header_customer">
				<h3>Currency Configuration</h3>

			</div>
			<div class="Success_msg">
				<div class="successMsg" style="text-align: center; color: red;">
					${error}</div>
			</div>

			<form:form action="saveCurrencyConfiguration"
				id="currencyConfiguration" commandName="currencyConfiguration"
				method="post">


				<div class="" style="width: 100%;">
					<form:hidden path="citizen" id="citizenHidden" />
					<form:hidden path="id" id="RIandNRIID" />
					<label class="checkbox-custom"> Citizen </label> <label
						class="checkbox-custom"><input type="radio"
						name="citizenRI" value="RI" checked="checked"
						onchange="onChangeCitizenType(this.value)"> RI</label> <label
						class="checkbox-custom"><input type="radio"
						name="citizenRI" value="NRI"
						onchange="onChangeCitizenType(this.value)"> NRI</label>
				</div>
				<div class="form-group" style="margin-bottom: 2px; width: 100%;"
					id="currencyDIV">
					<label class="col-md-2" style="padding-top: 21px;">
						Currency<span style="color: red">*</span>
					</label>
					<div class="col-md-4" style="margin-left: -105px;">
						<form:select path="currency" id="currency" class="myform-control"
							style="margin: 16px;">
							<form:option value="">Select</form:option>

						</form:select>

						<script>
							if ('${currency}' != "") {
								if ('${accountType}' == 'FCNR'
										|| '${accountType}' == 'PRP')
									populateCurrencyEditFCNR("currency",
											"${currency}");
								else if ('${accountType}' == 'RFC')
									populateCurrencyEditRFC("currency",
											"${currency}");
								else
									populateCurrencyEdit("currency",
											"${currency}");
							} else {
								if ('${accountType}' == 'FCNR'
										|| '${accountType}' == 'PRP')
									populateCurrencyFCNR("currency");
								else if ('${accountType}' == 'RFC')
									populateCurrencyRFC("currency");
								else
									populateCurrency("currency");
							}
						</script>


					</div>
				</div>

				<table class="table_" id="nriTable" style="display: none;">
					<thead>
						<tr>
							<!-- <th style="text-align: center;">Options</th> -->
							<th style="width: 3% !important; text-align: center;">Account
								Type</th>
							<th style="text-align: center; width: 3% !important;">Currency</th>
							<!-- 	<th colspan="5" style="text-align: center;">Account Type</th> -->
						</tr>
					</thead>
					<tbody>

				<c:if test="${configurationNRI.size() == 0}">
						<tr>
							<td style="text-align: center;">NRE</td>
							<td style="text-align: -webkit-center;" class="regular"><form:select
									path="currency" id="currencyNRE" class="myform-control newEnteredCurrency"
									style="width: 66%;">

									<form:option value="">Select</form:option>
									<script>
										populateCurrency("currencyNRE");
									</script>

								</form:select></td>
						</tr>
						<tr>
							<td style="text-align: center;">NRO</td>
							<td style="text-align: -webkit-center;" class="regular"><form:select
									path="currency" id="currencyNRO" class="myform-control newEnteredCurrency"
									style="width: 66%;">
									<form:option value="">Select</form:option>
								</form:select> <script>
									populateCurrency("currencyNRO");
								</script></td>
						</tr>
						<tr>
							<td style="text-align: center;">FCNR</td>
							<td style="text-align: -webkit-center;" class="regular"><form:select
									path="currency" id="currencyFCNR" class="myform-control  newEnteredCurrency"
									style="width: 66%;">
									<form:option value="">Select</form:option>
								</form:select> <script>
									populateCurrencyFCNR("currencyFCNR");
								</script></td>
						</tr>
						<tr>
							<td style="text-align: center;">RFC</td>
							<td style="text-align: -webkit-center;" class="regular"><form:select
									path="currency" id="currencyRFC" class="myform-control  newEnteredCurrency"
									style="width: 66%;">
									<form:option value="">Select</form:option>
								</form:select> <script>
									populateCurrencyRFC("currencyRFC");
								</script></td>
						</tr>
						<tr>
							<td style="text-align: center;">PRP</td>
							<td style="text-align: -webkit-center;" class="regular"><form:select
									path="currency" id="currencyPRP" class="myform-control  newEnteredCurrency"
									style="width:66%;">
									<form:option value="">Select</form:option>
								</form:select> <script>
									populateCurrencyFCNR("currencyPRP");
								</script></td>
						</tr>
						</c:if>
						<c:if test="${configurationNRI.size() > 0}">
							<c:forEach items="${configurationNRI}"
								var="currncyConfigurationNRI" varStatus="status">
								<tr>
									<td style="display: none;"><form:input path="ids" value="${currncyConfigurationNRI.id}"/></td>
									<td style="text-align: center;">${currncyConfigurationNRI.accountType}</td>
									<td style="text-align: -webkit-center;" class="regular"><form:select
											path="currency"
											id="AccountType_${currncyConfigurationNRI.id}"
											class="myform-control validinput" style="width:66%;">
											<form:option value="">Select</form:option>
										</form:select> <script>
											var accountype_ = "${currncyConfigurationNRI.accountType}";
											var accId = "${currncyConfigurationNRI.id}";
											var accuntID = "AccountType_"
													+ accId;
											if (accountype_ == 'NRE') {
												populateCurrency(accuntID);
												$("#" + accuntID)
														.val(
																"${currncyConfigurationNRI.currency}")
														.change();
											} else if (accountype_ == "NRO") {
												populateCurrency(accuntID);
												$("#" + accuntID)
														.val(
																"${currncyConfigurationNRI.currency}")
														.change();
											} else if (accountype_ == "FCNR") {
												populateCurrencyFCNR(accuntID);
												$("#" + accuntID)
														.val(
																"${currncyConfigurationNRI.currency}")
														.change();
											} else if (accountype_ == "PRP") {
												populateCurrencyFCNR(accuntID);
												$("#" + accuntID)
														.val(
																"${currncyConfigurationNRI.currency}")
														.change();
											} else if (accountype_ == "RFC") {
												populateCurrencyRFC(accuntID);
												$("#" + accuntID)
														.val(
																"${currncyConfigurationNRI.currency}")
														.change();
											} else {
												populateCurrency(accuntID);
											}
										</script></td>
								</tr>




							</c:forEach>
						</c:if>


					</tbody>
				</table>



				<div
					style="text-align: center; margin-top: 24px; margin-bottom: 27px; width: 100%; clear: both;">
					<input type="button" value="Submit" class="btn btn-primary"
						onclick="return submitOnButton();" />
				</div>

			</form:form>



		</div>
	</div>
</div>
<script>
	var ids = ""
	$(document).ready(function() {
		var currencyRIConf = "${configurationRI.currency}";
		if (currencyRIConf != "" && currencyRIConf != null) {
			$("#RIandNRIID").val("${configurationRI.id}");
			$("#currency").val(currencyRIConf);
		}

	});

	var citizenType = "RI";
	function onChangeCitizenType(value) {
		if (value == 'RI') {
			citizenType = value;

			$("#nriTable").hide();
			$("#currencyDIV").show();

		} else {
			citizenType = value;

			$("#nriTable").show();
			$("#currencyDIV").hide();
		}

	}

	function submitOnButton() {

		$("#citizenHidden").val(citizenType);
		if (citizenType == "RI") {
			$(".validinput").prop("disabled","disabled");
			var currRIConf = "${configurationRI.currency}";
			if (currRIConf != "" && currRIConf != null) {
				$("#RIandNRIID").val("${configurationRI.id}");
			} else {
				$("#RIandNRIID").val("");
				if($("#currency").val() == ""){
					alert("Please select currency !");
					$("#currency").css("border","solid 1px red");;
					return false;
				}else{
					$("#currency").css("border","solid 1px #ccc");
				}
			}
			$("#currencyNRE").prop('disabled', "disabled");
			$("#currencyNRO").prop('disabled', "disabled");
			$("#currencyFCNR").prop('disabled', "disabled");
			$("#currencyPRP").prop('disabled', "disabled");
			$("#currencyRFC").prop('disabled', "disabled");
		} else {
			var nriListSize = "${configurationNRI.size() > 0}";
			var sizeOfList = "<c:out value = '${configurationNRI.size()}'/>";
			$("#RIandNRIID").prop("disabled","disabled");

			if (String(true) == nriListSize) {
				var stop = false;
				$(".validinput").each(function(){
					if($(this).val() == ""){
						stop = true;
						alert("Please select all currencies !");
						$(this).css("border","solid 1px red");
						return false;
					}else{
						$(this).css("border","solid 1px #ccc");
					}
					
				});
				if(stop){
					stop = false;
					return false;
				}

			} else {
				var stopNew = false;
				$(".newEnteredCurrency").each(function(){
					if($(this).val() == ""){
						stopNew = true;
						alert("Please select all currencies !");
						$(this).css("border","solid 1px red");
						return false;
					}else{
						$(this).css("border","solid 1px #ccc");
					}
					
				});
				if(stopNew){
					stopNew = false;
					return false;
				}
				$("#RIandNRIID").val("");
			}
			if(stopNew){
				stopNew = false;
				return false;
			}
			$("#currency").prop('disabled', "disabled");
		}
		$("#currencyConfiguration").submit();

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
</script>

<style>
table, th, td {
	border: 1px solid skyblue;
	border-collapse: collapse;
}

.table_ {
	width: 90%;
	margin-bottom: 100px;
	margin: 15px;
	float: none;
	position: relative;
}

th, td {
	padding: 5px;
	text-align: left;
	font-family: inherit;
}

.checkbox-custom {
	font-size: 15px;
	margin: 11px;
	margin: 10px;
	padding: 8px;
	font-family: initial;
}

label.checkbox-custom input {
	margin-right: 4px;
}
</style>

