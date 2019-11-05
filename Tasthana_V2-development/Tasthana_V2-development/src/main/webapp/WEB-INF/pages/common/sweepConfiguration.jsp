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
<div class="right-container" id="right-container">
	<div class="container-fluid">
		<div class="Flexi_deposit">

			<div class="header_customer">
				<h3>Sweep Configuration</h3>
			</div>
			<div class="Success_msg">
				<div class="successMsg" style="text-align: center; color: red;">
					${error}</div>
			</div>

			<div class="col-md-12">
				<form:form action="saveSweepConfiguration"
					commandName="sweepConfiguration" id="sweepConfiguration"
					method="post">
					<input type="hidden" name="menuId" value="${menuId}" id="menuId"/>
					<form:hidden path="id"/>
					<div class="form-group col-md-6">
						<label class="col-md-4 control-label" style="padding-top:7px;">Sweep In Type<span
							style="color: red"></span></label>
						<div class="col-md-8">

							<form:select path="sweepInType" class="myform-control"
								id="sweepInType">
								<form:option value="">Select</form:option>
								<form:option value="Fixed Interest & Fixed Tenure">Fixed Interest & Fixed Tenure</form:option>
								<form:option value="Fixed Interest & Dynamic Tenure">Fixed Interest & Dynamic Tenure</form:option>
								<form:option value="Dynamic Interest & Fixed Tenure">Dynamic Interest & Fixed Tenure</form:option>
								<form:option value="Dynamic Interest & Dynamic Tenure">Dynamic Interest & Dynamic Tenure</form:option>
							</form:select>
						</div>

					</div>


					<div class="form-group col-md-6">
						<label class="col-md-6 control-label" style="padding-top:7px;">Minimum Saving
							Balance for Sweep In<span style="color: red"></span>
						</label>
						<div class="col-md-3">
							<form:input id="minimumSavingBalanceForSweepIn"
								class="myform-control" path="minimumSavingBalanceForSweepIn"
								onkeypress="numberValidation(event)"
								onfocus="focusInput(this.id)" />
						</div>

					</div>


					<div class="form-group col-md-6">
						<label class="col-md-4 control-label" style="padding-top:7px;">Minimum Amount
							Required For Sweep In<span style="color: red"></span>
						</label>
						<div class="col-md-8">
							<form:input id="minimumAmountRequiredForSweepIn"
								class="myform-control" path="minimumAmountRequiredForSweepIn"
								onkeypress="numberValidation(event)"
								onfocus="focusInput(this.id)" />
						</div>

					</div>

					<div class="form-group col-md-6">
						<label class="col-md-6 control-label" style="padding-top:7px;">Is Sweep Out Allowed<span
							style="color: red"></span></label>
						<div class="col-md-3">

							<form:select path="isSweepOutAllowed" class="myform-control"
								id="isSweepOutAllowed">
								<form:option value="">Select</form:option>
								<form:option value="0">No</form:option>
								<form:option value="1">Yes</form:option>
							</form:select>
						</div>

					</div>
					<div style="text-align: center;">
						<span id="errorMsg" style="color: red; display: none;"></span>
					</div>
					<div style="clear: both;"></div>
					<div class="form-group button-submit" style="text-align: center;">

						<input type="button" value="Submit" class="btn btn-primary"
							onclick="return submitFormData()" style="margin-top: 13px;" />

					</div>




				</form:form>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	$("select").change(function() {

		if ($(this).val() != "") {
			$(this).css("border", "1px solid #ccc");
			$("#errorMsg").hide();
		}
	}).change();

	function submitFormData() {

		var sweepInType = $("#sweepInType").val();
		var minimumSavingBalanceForSweepIn = $(
				"#minimumSavingBalanceForSweepIn").val();
		var minimumAmountRequiredForSweepIn = $(
				"#minimumAmountRequiredForSweepIn").val();
		var isSweepOutAllowed = $("#isSweepOutAllowed").val();

		if (sweepInType == "") {
			$("#errorMsg").text("Please select sweep  type !");
			$("#errorMsg").show();
			$("#sweepInType").css("border", "1px solid red");
			return false;
		}

		if (minimumAmountRequiredForSweepIn == "") {
			$("#errorMsg").text("Please enter min amount required !");
			$("#errorMsg").show();
			$("#minimumAmountRequiredForSweepIn")
					.css("border", "1px solid red");
			return false;
		}
		if (minimumSavingBalanceForSweepIn == "") {
			$("#errorMsg").text("Please enter min saving balance !");
			$("#errorMsg").show();
			$("#minimumSavingBalanceForSweepIn").css("border", "1px solid red");
			return false;
		}

		if (isSweepOutAllowed == "") {
			$("#errorMsg").text("Please select sweep out allowed !");
			$("#errorMsg").show();
			$("#isSweepOutAllowed").css("border", "1px solid red");
			return false;
		}
		
		$("#sweepConfiguration").submit();

	}

	function focusInput(id) {
		$("#" + id).css("border", "1px solid #ccc");
		$("#errorMsg").hide();

	}
</script>

