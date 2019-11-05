<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>



<div class="right-container" id="right-container">
	<div class="container-fluid">
		
		<div class="Flexi_deposit">

			<div class="header_customer" style="margin-bottom: 25px;">
				<h3>
					<spring:message code="label.withdrawFromSaving" />
				</h3>
			</div>
			<div class="col-md-12" style="padding: 10px; text-align: center;">
				<span style="color: red;">${error}</span>
			</div>
			<div class="flexi_table">
				<form:form action="withdrawFromSavingPost" class="form-horizontal"
					onsubmit="validate()"  name="withdrawForm"
					commandName="withdrawForm">
					<form:hidden path="customerId" />
					<form:hidden path="accountBalance" />
					<form:hidden path="depositId" />
					<form:hidden path="depositHolderId" />
				


                 <div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.savingAccountNo" /></label>
						<div class="col-md-4">
							<form:input path="accountNumber" id="accountNumber" 
								 class="myform-control" readonly="true"/>
								
						</div>

					</div>
					<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.withDrawAmount" /><span style="color: red">*</span></label>
						<div class="col-md-4">
							<form:input path="withdrawAmount" type="number" placeholder="Enter Amount"
								 class="myform-control" required="true" readonly="true"/>

						</div>

					</div>
					<div class="form-group" style="margin-left: 25px">
						<label class="col-md-1 control-label"><spring:message
								code="label.totalBalance" /></label>
						<div class="col-md-2">
						<fmt:formatNumber value="${withdrawForm.compoundFixedAmt}" pattern="#.##" var="totalBalance"/>       
						
							<form:input path="compoundFixedAmt" value="${totalBalance}" class="myform-control"  readonly="true"  />
						</div>		
						
						
					
						<label class="col-md-2 control-label">Auto Deposit Balance</label>

						<div class="col-md-2">
						<fmt:formatNumber value="${withdrawForm.compoundVariableAmt}" pattern="#.##" var="autoDepositBal"/>       
						
							<form:input path="compoundVariableAmt" value="${autoDepositBal}" class="myform-control"
								readonly="true" />

						</div>
							 <label class="col-md-2 control-label">Saving Amount Balance</label>
						<div class="col-md-2">
						<fmt:formatNumber value="${totalBalance-autoDepositBal}" pattern="#.##" var="savingBal"/>       
							
							<input value="${savingBal}" class="myform-control"
								readonly/>
						</div> 
					</div>





					<div class="form-group">
						<label class="col-md-4 control-label"></label>
						<div class="col-md-6">
							
						<a href="withdrawFromSaving" class="btn btn-success"><spring:message code="label.back"/></a>
						<input type="submit" id="withdrawBtn" class="btn btn-info"
								value="Confirm">

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