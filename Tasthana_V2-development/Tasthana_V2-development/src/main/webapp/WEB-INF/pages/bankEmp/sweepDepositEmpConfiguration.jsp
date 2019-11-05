<%@include file="taglib_includes.jsp"%>
<script src="<%=request.getContextPath()%>/resources/js/validation.js"></script>

<div class="right-container" id="right-container">
	<div class="container-fluid">

		<div class="set-rate">
			<div class="header_customer" style="margin-bottom: 10px;">
				<h3>Avail Sweep-In Facility</h3>
			</div>
			<span style="color: red; text-align: center">${error}</span>


			<form:form method="post" id="sweepConfigurationForm"
				class="form-horizontal" commandName="sweepInFacilityForCustomer"
				action="saveSweepConfiguration">

				<%--<input type="hidden" id = "isSweepDepositRequiredValue" path="isSweepDepositRequired" value="0"/>
			 <form:input type="hidden" path="tenure" value="${sweepConfigurationForm.tenure}"/>
				<form:input type="hidden" path="interestRate" value="${sweepConfigurationForm.interestRate}"/>
				<form:input type="hidden" path="tenureType" value="${sweepConfigurationForm.tenureType}"/> --%>
				<div class="successMsg">
					<b><font color="red">${success}</font></b>
				</div>
				<div class="errorMsg">
					<b><font color="red">${error}</font></b>
				</div>

				<form:hidden path="customerId" />
				<div class="col-md-1"></div>
				<div class="col-md-11" style="margin-left: 20px;">If you want
					your savings bank account to garner the interest rate payable on a
					regular deposit, you can configure your account to sweep-in
					facility. The surplus fund above the threshold limit from the
					savings bank account is transferred to regular deposit in multiples
					of 1,000.</div>
				<div style="clear: both; height: 20px;"></div>
				<div class="col-md-1"></div>
				<%-- <div class="form-group col-md-8" style="margin-left: -28px;">
						<label class="col-md-4 control-label">Want to get Sweep Facility? </label>
							<div class="col-md-6">
								<form:checkbox path="isSweepDepositRequired"  value = "${sweepConfigurationForm.isSweepDepositRequired}"
										class="form-control" id="sweepDeposit" 
										style=" width: 16px;" />
										<span id="errorIsCheckedValue" style= "color:red"> </span>
							</div></div> --%>
				<div class="form-group col-md-8">

					<div id="tenureConfiguration">
						<%-- <div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.typeOfTenure" /><span style="color: red">*</span></label>
							<div class="col-md-5">
								<form:select path="tenureType" class="input form-control"
									id="tenureType" onchange="showTenureDetails(this.value)">
									<form:option value="Select">
										<spring:message code="label.selectValue" />
									</form:option>
									<form:option value="Day">
										<spring:message code="label.days" />
									</form:option>
									<form:option value="Month">
										<spring:message code="label.month" />
									</form:option>
									<form:option value="Year">
										<spring:message code="label.Year" />
									</form:option>
								</form:select>
							</div>

						</div> --%>
						<div class="form-group">
							<%-- <label class="col-md-4 control-label"> 
								<span id="dayId" style="display: none"><spring:message code="label.days" />
								<span style="color: red">*</span></span>
								<span id="monthId" style="display: none"><spring:message code="label.month" /><span
									style="color: red">*</span></span> 
								<span id="yearId" style="display: none"><spring:message code="label.Year" /><span
									style="color: red">*</span></span>
								</label> --%>


							<%-- <div class="col-md-2">
								<form:input path="tenure" type="number" min="0"
									class="input form-control" id="tenure" value="${sweepConfigurationForm.tenure}" onkeypress="validationAccount(event)"/>
								<span id="fdTenureError" style="display: none; color: red"><spring:message
										code="label.tenureError" /></span> <span id="fdTenureMax9YearError"
									style="display: none; color: red">Please enter valid
									tenure</span>

							</div> --%>

							<div>
								<label class="col-md-4 control-label">Want to get Sweep
									Facility? </label>
								<div class="col-md-6">
									<form:checkbox path="isSweepInConfigureedByCustomer"
										value="${sweepInFacilityForCustomer.isSweepInConfigureedByCustomer}"
										class="form-control" id="sweepDeposit" style=" width: 16px;"
										onchange="showMinAmountAndSaving(this.value)" />
									<span id="errorIsCheckedValue" style="color: red"> </span>
								</div>
							</div>
							
							<div>
								<label class="col-md-4 control-label">SweepIn Restricted</label>
								<div class="col-md-6">
									<form:checkbox path="isSweepInRestrictedByBank"
										value = "${sweepInFacilityForCustomer.isSweepInRestrictedByBank}"
										class="form-control" id="isSweepInRestrictedByBank" style=" width: 16px;"/>

								</div>
							</div>

							<%-- <div class="col-md-4" id="dayValue" style="margin-left: 27px;">
								<label class="col-md-2 control-label"
									style="text-align: right; padding-left: 5px;"><spring:message
										code="label.days" /></label>
								<div class="col-md-6" id = "tenureDaysDiv">
									<form:input path="tenureDays" type="number" min="0"
										class="input form-control" id="tenureDays" onkeypress="validationAccount(event)" />
									<span id="dayValueError" style="display: none; color: red"><spring:message
											code="label.daysError" /></span>

								</div>

							</div> --%>


							<div id="minimumAmountAndSavingDIV">
								<div class="form-group" style="clear: both; margin-top: 40px;">
									<label class="col-md-4 control-label"
										style="padding-left: 54px; margin-top: -10px">Minimum
										Amount Required For Sweep In</label>
									<div class="col-md-5" style="margin-left: 5px;">
										<form:input path="minimumAmountRequiredForSweepIn"
										value = "${minAmount}"
											type="number" min="0" class="myform-control"
											id="minimumAmountRequiredForSweepIn" onkeypress="numberValidation(event)"/>
									</div>

								</div>

								<div class="form-group" style="clear: both; margin-top: 40px;">
									<label class="col-md-4 control-label"
										style="padding-left: 54px; margin-top: -40px">Minimum
										Saving Balance for Sweep In</label>
									<div class="col-md-5" style="margin-left: 5px;">
										<form:input path="minimumSavingBalanceForSweepIn"
										value = "${minSavingBal}"
											type="number" min="0" class="myform-control"
											id="minimumSavingBalanceForSweepIn" style="margin-top:-28px" onkeypress="numberValidation(event)"/>
									</div>

								</div>
							</div>

							<div class="form-group"
								style="margin-bottom: -20px; margin-top: -30px;" id="tenureDIV">
								<label class="col-md-4"
									style="padding-top: 23px; margin-left: 31px;"> Tenure<span
									style="color: red">*</span>
								</label>
								<div class="col-md-2"
									style="width: 17.5%; margin-left: -17px; padding: 6px;">
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
								<div class="col-md-2"
									style="margin-left: -10px; width: 19%; padding: 6px;">
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

								<div class="col-md-2"
									style="margin-left: -10px; width: 16%; padding: 6px;">
									<select id="tenureDay" class="myform-control" name="tenure">
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

				</div>

				<div style="clear: both; text-align: center; margin-bottom: 21px;">
					<input id="submit" type="submit"
						onclick="return saveSweepInFactilityForCustomer();"
						class="btn btn-info" style="margin-left: 2px;" value="Submit"
						disabled>
				</div>
			</form:form>
		</div>
	</div>
</div>


<script>
	$(document)
			.ready(
					function() {
						var topUp = "${sweepInFacilityForCustomer.tenure}";
						var topUArr = topUp.split(",");
						for (var i = 0; i < topUArr.length; i++) {
							if (topUArr[i] != "") {
								if (topUArr[i].includes("Month")) {
									$("#tenureMonth").val(topUArr[i]).change();

								}
								if (topUArr[i].includes("Year")) {
									$("#tenureYear").val(topUArr[i]).change();

								}
								if (topUArr[i].includes("Day")) {
									$("#tenureDay").val(topUArr[i]).change();

								}

							}
						}

						var dbValueSweep = "${sweepInFacilityForCustomer.isSweepInConfigureedByCustomer}";
						if (dbValueSweep == 1) {
							$("#sweepDeposit").val(1);
							$("#sweepDeposit").prop("checked", true);
							//$("#minimumAmountAndSavingDIV").show();
							
							$("#submit").prop("disabled", true);

						} else {
							$("#sweepDeposit").prop("checked", false);
							
							$("#sweepDeposit").val("0");
							//$("#minimumAmountAndSavingDIV").hide();

							$("#submit").prop("disabled", false);
						}
						
						
						var dbValueSweepInOfBankRestrict = "${sweepInFacilityForCustomer.isSweepInRestrictedByBank}";
						if (dbValueSweepInOfBankRestrict == 1) {
							$("#isSweepInRestrictedByBank").val(1);
							$("#isSweepInRestrictedByBank").prop("checked", true);
							
							
						} else {
							$("#isSweepInRestrictedByBank").prop("checked", false);
							$("#isSweepInRestrictedByBank").val("0");
						}
						
						
						var isTenureShowAndHide = "${isTenure}";
						
						if(isTenureShowAndHide == "true"){
							$("#tenureDIV").show();
							
						}else{
							$("#tenureDIV").hide();
						}
						
						var id = "${sweepInFacilityForCustomer.id}";
						if(id != ""){
							
							$("#minimumSavingBalanceForSweepIn").prop("readonly",true);
							$("#minimumAmountRequiredForSweepIn").prop("readonly",true);
							$("#tenureDay").prop("disabled","disabled");
							
							$("#tenureMonth").prop("disabled","disabled");
							
							$("#tenureYear").prop("disabled","disabled");
							
						}

					});

	function saveSweepInFactilityForCustomer() {
		var minimumSavingBalanceForSweepIn = $(
				"#minimumSavingBalanceForSweepIn").val(${minSavingBal});
		var minimumAmountRequiredForSweepIn = $(
				"#minimumAmountRequiredForSweepIn").val(${minAmount});
		var sweepDeposit = $("#sweepDeposit").val();
		
		var id = "${sweepInFacilityForCustomer.id}";
		if(id != ""){
		
		}else{
			if (sweepDeposit == 1) {
				if (minimumSavingBalanceForSweepIn == "" || minimumSavingBalanceForSweepIn == 0 && minimumAmountRequiredForSweepIn == ""
						|| minimumAmountRequiredForSweepIn == 0) {
					alert("Please enter minimum Saving Balance and Amount Required Sweep In");
					return false;
				}
			}else{
				alert("Please select  Sweep Facility ");
				return false;
			}
			
			var isTenureShowAndHide = "${isTenure}";
			
			if(isTenureShowAndHide == "true"){
				var days = $("#tenureDay").val();
				var months = $("#tenureMonth").val();
				var years = $("#tenureYear").val();
				
				if(days == "" && months == "" && years == ""){
					alert("Please select Tenure ! ");
					return false;
				}
				
			}else{
				$("#tenureDIV").hide();
			}
			
		}
		$("#tenureDay").prop("disabled",false);
		
		$("#tenureMonth").prop("disabled",false);
		;
		$("#tenureYear").prop("disabled",false);
		
		$("#sweepConfigurationForm").submit();
	}

	function saveSweepConfiguration() {
		var tenureType = document.getElementById('tenureType').value;
		var tenure = document.getElementById('tenure').value;

		if (tenureType == "" || tenureType == 'Select' || tenure == "") {
			//document.getElementById('errorMsg').style.display='block';
			//document.getElementById('errorMsg').value = "Please Insert the tenure.";

			if ($("#sweepDeposit").val() == 0) {
				document.getElementById("errorIsCheckedValue").innerHTML = "Please select Check box";
				return false;
			}
			if (tenureType == "Select" || tenureType == "") {
				document.getElementById("tenureType").style.borderColor = "red";
			} else {
				document.getElementById("tenureType").style.borderColor = "black";
			}
			if (tenure == "")
				document.getElementById("tenure").style.borderColor = "red";
			else
				document.getElementById("tenure").style.borderColor = "black";

			return false;
			///event.preventDefault();
		} else {

			$("#tenureConfiguration :input").prop("disabled", false);
			$("#sweepConfigurationForm").attr("action",
					"saveSweepConfiguration");
			$("#sweepConfigurationForm").submit();
			return true;

			/* 
				$("#sweepConfigurationForm").attr("action", "saveSweepConfiguration");
				$("#sweepConfigurationForm").submit(); */
		}

	}

	function showTenureDetails(value) {
		var tenure = value;
		if (tenure == "Day") {
			document.getElementById('dayId').style.display = 'block';
			document.getElementById('monthId').style.display = 'none';
			document.getElementById('yearId').style.display = 'none';
			document.getElementById('dayValue').style.display = 'none';
			//document.getElementById('daysValue').value = "";
			document.getElementById('tenure').value = "";
			document.getElementById('tenureDays').value = "";
		}
		if (tenure == "Month") {
			document.getElementById('dayId').style.display = 'none';
			document.getElementById('monthId').style.display = 'block';
			document.getElementById('yearId').style.display = 'none';
			document.getElementById('dayValue').style.display = 'none';
			//document.getElementById('daysValue').value = "";
			document.getElementById('tenure').value = "";
			document.getElementById('tenureDays').value = "";
		}
		if (tenure == "Year") {
			document.getElementById('dayId').style.display = 'none';
			document.getElementById('monthId').style.display = 'none';
			document.getElementById('yearId').style.display = 'block';
			document.getElementById('dayValue').style.display = 'block';
			document.getElementById('tenure').value = "";
			document.getElementById('tenureDays').value = "";
		}

	}

	function validationAccount(event) {

		var minimumBalance1_ = document.getElementById(event.target.id);

		var keycode = event.which;
		if (!(event.shiftKey == false && (keycode == 8 || keycode == 37 || (keycode >= 48 && keycode <= 57)))) {
			event.preventDefault();
			return false;
		}

		if (minimumBalance1_.value.length > 10 && event.keyCode != 8) {
			event.preventDefault();
			return false;
		}

	}

	$(function() {
		$("#sweepDeposit").click(function() {
			if ($(this).is(":checked")) {
				$("#errorIsCheckedValue").hide();
				$("#sweepDeposit").val(1);
				//  $("#isSweepDepositRequiredValue").val("1");
				$("#submit").prop("disabled", false);

			} else {
				$("#errorIsCheckedValue").show();
				$("#sweepDeposit").val(0);
				//  $("#isSweepDepositRequiredValue").val("0");
				$("#submit").prop("disabled", false);
			}
		});
		
		
		$("#isSweepInRestrictedByBank").click(function() {
			if ($(this).is(":checked")) {
				$("#errorIsCheckedValue").hide();
				$("#isSweepInRestrictedByBank").val(1);
				$("#submit").prop("disabled", false);
				

			} else {
				$("#errorIsCheckedValue").show();
				$("#isSweepInRestrictedByBank").val(0);
				$("#submit").prop("disabled", false);
				
			}
		});
	});

	function showMinAmountAndSaving(value) {
		if (value == 1) {
			//$("#minimumAmountAndSavingDIV").show();
			$("#sweepDeposit").val(1);
			//$("#minimumSavingBalanceForSweepIn").val("");
			//$("#minimumAmountRequiredForSweepIn").val("");

		} else {
			$("#sweepDeposit").val(0);
			//$("#minimumAmountAndSavingDIV").hide();
			//$("#minimumSavingBalanceForSweepIn").val("");
			//$("#minimumAmountRequiredForSweepIn").val("");

		}

	}
</script>