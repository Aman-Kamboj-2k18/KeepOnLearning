<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="right-container" id="right-container">
	<div class="container-fluid">

		<div class="fd-list">
			<div class="header_customer">
				<h3>
					Confirm Deposit Clearance
				</h3>
			</div>

			<div class="fd-list-table">

				<span class="counter pull-right"></span>
				<div class="col-sm-12 col-md-12 col-lg-12">
					<input type="text" id="kwd_search" value=""
						placeholder="Search Here..." style="float: right;" />
				</div>
			<form:form id="clearanceForm" commandName="depositsList">
			
		       
						<table class="table data jqtable example" id="my-table">
						<thead>
							<tr>
								<th><spring:message code="label.fdID" /></th>
								<th><spring:message code="label.accno" /></th>
								<th><spring:message code="label.currentBalance" /></th>

								<th><spring:message code="label.fdAmount" /></th>
								<th><spring:message code="label.maturityDate" /></th>
								<th><spring:message code="label.createdDate" /></th>
								<th><spring:message code="label.status" /></th>
								

							</tr>
						</thead>
					<c:if test="${! empty depositsList.dList}">
							<c:forEach items="${depositsList.dList}" var="deposit" varStatus="status">
								<tr>
									<td> <form:input path="dList[${status.index}].id"  readonly="true" style="border:0;width: 63px;"/> 
									<td><form:input path="dList[${status.index}].accountNumber" readonly="true" style="border:0; width:190px;"/></td>

									<fmt:formatNumber value="${deposit.currentBalance}"
										pattern="#.##" var="currentBalance" />
									<td><form:input path="dList[${status.index}].currentBalance" value="${currentBalance}" readonly="true" style="border:0;width: 70px;"/></td>

									<fmt:formatNumber value="${deposit.depositAmount}"
										pattern="#.##" var="depositamount" />
									<td><form:input path="dList[${status.index}].depositAmount" value="${depositamount}" readonly="true" style="border:0;width: 70px;"/></td>

									<td><fmt:formatDate pattern="dd/MM/yyyy"
											value="${deposit.newMaturityDate}" /></td>
									<td><fmt:formatDate pattern="dd/MM/yyyy"
											value="${deposit.createdDate}" /></td>
									<td><form:input path="dList[${status.index}].status" readonly="true" style="border:0;width: 63px;"/> </td>
									
								</tr>
								
										<form:hidden path="dList[${status.index}].linkedAccountNumber"/>
										<form:hidden path="dList[${status.index}].paymentType"/>
										<form:hidden path="dList[${status.index}].tenureType"/>
										<form:hidden path="dList[${status.index}].tenure"/>
										<form:hidden path="dList[${status.index}].paymentMode"/>
										<form:hidden path="dList[${status.index}].dueDate"/>
										<form:hidden path="dList[${status.index}].interestRate"/>
										<form:hidden path="dList[${status.index}].flexiRate"/>
										<form:hidden path="dList[${status.index}].maturityAmount"/>
										<form:hidden path="dList[${status.index}].maturityDate"/>
										<form:hidden path="dList[${status.index}].payOffInterestType"/>
										<form:hidden path="dList[${status.index}].payOffDueDate"/>
										<form:hidden path="dList[${status.index}].depositType"/>
										<form:hidden path="dList[${status.index}].createdDate"/>
										<form:hidden path="dList[${status.index}].modifiedDate"/>
										<form:hidden path="dList[${status.index}].maturityAmountOnClosing"/>
								    	<form:hidden path="dList[${status.index}].closingDate"/>
										<form:hidden path="dList[${status.index}].prematurePanaltyAmount"/>
										<form:hidden path="dList[${status.index}].modifyPanalityAmount"/>
										<form:hidden path="dList[${status.index}].depositCategory"/>
										<form:hidden path="dList[${status.index}].approvalStatus"/>
										<form:hidden path="dList[${status.index}].comment"/>
										<form:hidden path="dList[${status.index}].currentBalance"/>
										<form:hidden path="dList[${status.index}].depositCurrency"/>
										<form:hidden path="dList[${status.index}].modifiedInterestRate"/>
										<form:hidden path="dList[${status.index}].transactionId"/>
										<form:hidden path="dList[${status.index}].createdBy"/>
										<form:hidden path="dList[${status.index}].stopPayment"/>
										<form:hidden path="dList[${status.index}].newMaturityDate"/>
										<form:hidden path="dList[${status.index}].clearanceStatus"/>
										
								
							</c:forEach>
						</c:if>
						</tbody>
					</table>
					</form:form>
			+
			<div class="form-group" style="margin-bottom: 35px;">
						<div class="col-md-offset-4 col-md-8">
						
					<a href="clearanceList?menuId=${menuId}" class="btn btn-warning"><spring:message
									code="label.back" /></a>
				 <input type="button" onclick="submit()" class="btn btn-success"
					value="Confirm" />
				</div>
					</div>
			</div>

		</div>
	</div>
</div>
<script>

function submit(){

     $("#clearanceForm").attr("action", "confirmDepositClearance?ids=");
	$("#clearanceForm").submit();

}
</script>