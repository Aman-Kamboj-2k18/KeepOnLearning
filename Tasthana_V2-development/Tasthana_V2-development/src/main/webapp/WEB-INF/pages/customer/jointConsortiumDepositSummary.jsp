<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- To fetch the request url -->
<c:set var="req"
	value="${requestScope['javax.servlet.forward.request_uri']}" />
<c:set var="baseURL" value="${fn:split(req,'/')}" />
<style>
.form-horizontal .control-label {
	padding-top: 7px;
	margin-bottom: 0;
}
</style>
	<div class="right-container" id="right-container">
	<div class="container-fluid">

		<div class="Flexi_deposit">
			<div class="header_customer" style="margin-bottom: 30px;">
				<h3>Deposit Summary</h3>
			</div>
			<form:form class="form-horizontal" name="fixedDeposit"
				id="summaryForm" action="savePostJointConsortium"
				commandName="fixedDepositForm" >
				<!-- onsubmit="return val()" -->
				<div class="col-sm-8">
				<input type="hidden" name="customerDetails" value='${customerDetails}'/>

					<div class="form-group">
						<label class="control-label col-md-6"><spring:message
								code="label.fdAmount" /></label>
						<fmt:formatNumber value="${fixedDepositForm.fdAmount}"
							pattern="#.##" var="depositAmount" />
						<div class="col-md-6">
							<form:input class="form-control" path="fdAmount"
								value="${depositAmount}" readonly="true" />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-6">Rate Of Interest(%)</label>
						<div class="col-md-6">
							<form:input path="fdCreditAmount" class="form-control"
								readonly="true" />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-6"><spring:message
								code="label.estimateInterest" /></label>
						<fmt:formatNumber value="${fixedDepositForm.estimateInterest}"
							pattern="#.##" var="estimatedInterest" />
						<div class="col-md-6">
							<form:input path="estimateInterest" class="form-control"
								value="${estimatedInterest}" readonly="true" />
						</div>
					</div> 
					<div class="form-group">
						<label class="control-label col-md-6"><spring:message
								code="label.estimatedMaturityAmount" /></label>
						<fmt:formatNumber value="${fixedDepositForm.estimatePayOffAmount}"
							pattern="#.##" var="estimatePayOffAmount" />
						<div class="col-md-6">
							<form:input path="estimatePayOffAmount" class="form-control"
								value="${estimatePayOffAmount}" readonly="true" />
						</div>
					</div> 
					
					<c:if test="${! empty fixedDepositForm.payOffInterestType}">
						<div class="form-group">
							<label class="control-label col-md-6">Payoff Date</label>
						
							 <div class="col-md-6">
								<form:input path="payoffDate" class="form-control" readonly="true" />
							</div> 
						</div>
                	</c:if>
                	<c:if test="${baseURL[0] == 'common' || baseURL[1]== 'common'}">
                		 <div class="form-group" id = "branchDIV">
								<label class="control-label col-md-6" for=""><spring:message
										code="label.branchCode"/></label>
								<div class="col-sm-6">
									<form:input path="branchCode" class="form-control"
										id="branchCode" readonly="true"/>
								</div>
						</div>
						</c:if>

									<form:hidden path="accountAccessType"/>
								
				</div>
				<div class="col-sm-4">
              
				</div>


				<!--hidden path  -->
				<form:hidden path="isLinkedAccount" />
				<form:hidden path="id" />
				<form:hidden path="customer.id" />
				<form:hidden path="category" />
				<form:hidden path="customerName" />
				<form:hidden path="depositType" />
				<form:hidden path="accountNo" />
				

				<form:hidden path="accountType" />
				<form:hidden path="accountBalance" />
				<form:hidden path="paymentType" />
				<form:hidden path="fdAmount" />
				<form:hidden path="currency" />
				<form:hidden path="fdDeductDate" />
				<form:hidden path="totalTenureInDays" />
					<form:hidden path="tenureInYears" />
					<form:hidden path="tenureInMonths" />
					<form:hidden path="tenureInDays" />
				<form:hidden path="fdTenure" />
				<form:hidden path="userContribution" />
						
				
				<form:hidden path="nomineeName" />
				<form:hidden path="nomineeAge" />
				<form:hidden path="nomineeAddress" />
				<form:hidden path="nomineeRelationShip" />
				<form:hidden path="nomineePan" />
				<form:hidden path="nomineeAadhar" />
				
				<form:hidden path="guardianName" />
				<form:hidden path="guardianAge" />
				<form:hidden path="guardianAddress" />
				<form:hidden path="guardianRelationShip" />
				<form:hidden path="gaurdianAadhar" />
				<form:hidden path="gaurdianPan" />
				
				<form:hidden path="payOffInterestType" />
				<form:hidden path="interstPayType" />
				<form:hidden path="interestPercent" />
				<form:hidden path="interestPayAmount" />
				<form:hidden path="fdPayOffAccount" />
				<form:hidden path="otherPayTransfer" />
				<form:hidden path="otherName" />
				<form:hidden path="otherAccount" />
				<form:hidden path="otherBank" />
				<form:hidden path="otherIFSC" />
				<form:hidden path="depositForm.paymentMode" />
				<form:hidden path="depositForm.fdPay" />
				<form:hidden path="depositForm.linkedAccountNo" />
				<form:hidden path="depositForm.accountBalance" />
				<form:hidden path="depositForm.chequeNo" />
				<form:hidden path="depositForm.chequeDate" />
				<form:hidden path="depositForm.chequeBank" />
				<form:hidden path="depositForm.chequeBranch" />
				<form:hidden path="depositForm.cardType" />
				<form:hidden path="depositForm.cardNo" />
				<form:hidden path="depositForm.expiryDate" />
				<form:hidden path="depositForm.cvv" />
				<form:hidden path="fdPayType" />
				<form:hidden path="depositArea" />
				<form:hidden name="productId" path="" value="${fixedDepositForm.productConfigurationId }" />
				<form:hidden path="otherPayTransfer1" />
				<form:hidden path="otherName1" />
				<form:hidden path="otherAccount1" />
				<form:hidden path="otherBank1" />
				<form:hidden path="otherIFSC1" />
				<form:hidden path="maturityDate" />
				<form:hidden path="deductionDay" />	
				<form:hidden path="depositAccountType" />	
				<form:hidden path="maturityInstruction" />	
				<form:hidden path="daysValue" />	
                <form:hidden path="taxSavingDeposit" />
                <form:hidden path="productConfigurationId" />
                <form:hidden path="depositClassification" />  
                <form:hidden path="citizen" />  
                <form:hidden path="nriAccountType" />  
                
                <form:hidden path="isMaturityDisbrsmntInLinkedAccount" />
					<form:hidden path="isMaturityDisbrsmntInSameBank" />
					<form:hidden path="maturityDisbrsmntAccHolderName" />
					<form:hidden path="maturityDisbrsmntAccNumber" />
					<form:hidden path="maturityDisbrsmntTransferType" />
					<form:hidden path="maturityDisbrsmntBankName" />
					<form:hidden path="maturityDisbrsmntBankIFSCCode" />
                
                
				<c:forEach items="${fixedDepositForm.jointAccounts}" var="jointAcc"
					varStatus="status">
					<form:hidden path="jointAccounts[${status.index}].id" />
					<form:hidden path="jointAccounts[${status.index}].name" />
					<form:hidden path="jointAccounts[${status.index}].gender" />
					<form:hidden path="jointAccounts[${status.index}].age" />
					<form:hidden path="jointAccounts[${status.index}].contactNo" />
					<form:hidden path="jointAccounts[${status.index}].email" />
					<form:hidden path="jointAccounts[${status.index}].address" />
					<form:hidden path="jointAccounts[${status.index}].city" />
					<form:hidden path="jointAccounts[${status.index}].state" />
					<form:hidden path="jointAccounts[${status.index}].country" />
					<form:hidden path="jointAccounts[${status.index}].pincode" />
					<form:hidden path="jointAccounts[${status.index}].depositType" />
					<form:hidden path="jointAccounts[${status.index}].relationship" />
					
					
					
					
					
					<form:hidden path="jointAccounts[${status.index}].isMaturityDisbrsmntInLinkedAccount" />
					<form:hidden path="jointAccounts[${status.index}].isMaturityDisbrsmntInSameBank" />
					<form:hidden path="jointAccounts[${status.index}].maturityDisbrsmntAccHolderName" />
					<form:hidden path="jointAccounts[${status.index}].maturityDisbrsmntAccNumber" />
					<form:hidden path="jointAccounts[${status.index}].maturityDisbrsmntTransferType" />
					<form:hidden path="jointAccounts[${status.index}].maturityDisbrsmntBankName" />
					<form:hidden path="jointAccounts[${status.index}].maturityDisbrsmntBankIFSCCode" />
					
					
					
					 <form:hidden path="jointAccounts[${status.index}].nominee.name" />
					<form:hidden path="jointAccounts[${status.index}].nominee.age" />
					 <form:hidden path="jointAccounts[${status.index}].nominee.address" />
					<form:hidden path="jointAccounts[${status.index}].nominee.relationship" />
					 <form:hidden path="jointAccounts[${status.index}].nominee.panNo" />
					<form:hidden path="jointAccounts[${status.index}].nominee.aadharNo" />
					
					<form:hidden path="jointAccounts[${status.index}].nominee.gaurdianName" />
					<form:hidden path="jointAccounts[${status.index}].nominee.gaurdianAge" /> 
					<form:hidden path="jointAccounts[${status.index}].nominee.gaurdianAddress" />
					<form:hidden path="jointAccounts[${status.index}].nominee.gaurdianRelation" /> 
					<form:hidden path="jointAccounts[${status.index}].nominee.gaurdianPanNo" />
					<form:hidden path="jointAccounts[${status.index}].nominee.gaurdianAadharNo" /> 
					
					
					<form:hidden
						path="jointAccounts[${status.index}].contributionPercent" />


					<form:hidden
						path="jointAccounts[${status.index}].contributions.paymentType" />
					<form:hidden
						path="jointAccounts[${status.index}].contributions.interestPercentage" />
					<form:hidden
						path="jointAccounts[${status.index}].contributions.interestAmtPart" />
					<form:hidden
						path="jointAccounts[${status.index}].contributions.payOffAccPartOne" />
					<form:hidden
						path="jointAccounts[${status.index}].contributions.transferModeOne" />
					<form:hidden
						path="jointAccounts[${status.index}].contributions.beneficiaryOne" />
					<form:hidden
						path="jointAccounts[${status.index}].contributions.beneficiaryAccOne" />
					<form:hidden
						path="jointAccounts[${status.index}].contributions.beneficiaryBankOne" />
					<form:hidden
						path="jointAccounts[${status.index}].contributions.beneficiaryIFSCOne" />
				


				</c:forEach>

<span id='validationError' style="color: red"></span>

				<div class="form-group">
					<div class="col-md-offset-5 col-md-7">

						<form:hidden path="fdID" id="fdID" />
					<%-- 	<input type="button" class="btn btn-warning" onclick="goBack()"
							value="<spring:message code="label.back"/>" style="margin-left: -10px;"> --%>
							<a href="javascript:void(0)" onclick="goBack();" type="button" class="btn btn-warning" value="<spring:message code="label.back"/>" style="margin-left: -10px;">Back</a>
							
						<input type="submit" class="btn btn-primary" value="Confirm"  style="margin-left:9px;"/> 
					</div>
				</div>
			</form:form>
		</div>
	</div>


	<script>
	$( document ).ready(function() {
		   if('${role.role}' == 'ROLE_USER'){
			   $("#branchDIV").hide();
			   $("#areaDIV").hide();
			   
		   }
		});
	
	
	
		function goBack() {
			$("#summaryForm").attr("action", "jointConsortiumDeposit");
			$("#summaryForm").submit();

		}
	</script>
	
	
	<style>
	
	.button11 {
    display: block;
    width: 115px;
    height: 25px;
    background: #204d74;
    padding: 10px;
    text-align: center;
    border-radius: 5px;
    color: white;
    font-weight: bold;
}
	
	</style>
	