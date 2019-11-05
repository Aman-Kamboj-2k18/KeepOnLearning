<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script>
	
	function searchDepost() {

		$("#fdForm").attr("action", "bankpaymentDetails");
		$("#fdForm").submit();

	}

	function search() {

		$("#fdForm").attr("action", "depositPayment");
		$("#fdForm").submit();

	}


</script>

<div class="right-container" id="right-container">
	<div class="container-fluid">

		<div class="Flexi_deposit">

			<div class="header_customer">
				<h3>
					<spring:message code="label.searchBankPaymentDetails" />
				</h3>
			</div>
			<div class="col-md-12" style="padding: 30px; text-align: center;">
				<span style="color: red;">${error}</span>
			</div>
			<div class="flexi_table">
				<form:form id="fdForm" class="form-horizontal" name="bankPaymentDetails"
					commandName="bankPaymentForm">
				
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