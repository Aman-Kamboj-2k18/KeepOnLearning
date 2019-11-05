
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script>
$(document).ready(function(){
	if("${account}"=="null"||"${account}"==""){
		
	}
	else{
		depositSummary();
	}
});
	function depositSummary() {
		
		
		var accno = document.getElementById('fdID').value;

		if (accno != '') {
			document.getElementById('accError').style.display = 'none';
			$("#fdForm").attr("action", "showDepositDetails");
			$("#fdForm").submit();
		} else {

			document.getElementById('accError').style.display = 'block';
		}
	}
</script>

<div class="right-container" id="right-container">
	<div class="container-fluid">

		<div class="Flexi_deposit">


			<div class="header_customer" style="margin-bottom: 25px;">
				<h3>
					<spring:message code="label.depositSummary" />
				</h3>
			</div>
			<div class="col-md-12" style="padding: 30px; text-align: center;">
				<span style="color: red;">${error}</span>
			</div>
			<div class="flexi_table">
				<form:form class="form-horizontal" id="fdForm" method="post"
					name="depositList" commandName="depositForm">
					
					<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.fdAccountNo" /><span style="color: red">*</span></label>
						<div class="col-md-4">
							<form:input path="accountNumber"
								placeholder="Enter Account Number" id="fdID"
								class="myform-control" required="true" value="${account}" />
							<span id='accError' style="display: none; color: red;"></span> <span
								id="appStatusError" style="display: none; color: red;">This
								deposit is not Approved</span>
							<c:if test="${depositList[0].approvalStatus=='Pending'}">
								<span style="color: red">Deposit is pending</span>
							</c:if>
						</div>

						<div class="col-md-2">
							<input type="submit" class="btn btn-primary"
								onclick="depositSummary()" value="View">

						</div>

					</div>
					<div class="col-sm-12">
						<div class="space-45"></div>



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