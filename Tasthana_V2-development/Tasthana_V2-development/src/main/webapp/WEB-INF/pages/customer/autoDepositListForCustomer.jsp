<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="right-container" id="right-container">
        <div class="container-fluid">
  
<div class="fd-list">
			<div class="header_customer">	
				<h3><spring:message code="label.autoDepositList"/></h3>
			</div>
			
			<div class="fd-list-table">	
			
			<span class="counter pull-right"></span>
				<div class="col-sm-12 col-md-12 col-lg-12">
					<input type="text" id="kwd_search" value="" placeholder="Search Here..."  style="float:right;"/>
				</div>
			<table class="table data jqtable example" id="my-table">
				<thead>
				<tr>
					<th><spring:message code="label.customerID" /></th>
					<th><spring:message code="label.customerName" /></th>
					<th><spring:message code="label.fdID" /></th>
					<th><spring:message code="label.accountNo" /></th>
					<th><spring:message code="label.fdAmount" /></th>
					<th><spring:message code="label.currentBalance" /></th>		
					<th><spring:message code="label.interestRate" /></th>		
				</tr>
			</thead>
				<tbody>
				<c:if test="${! empty autoDeposit}">
					<c:forEach items="${autoDeposit}" var="deposit">
						<tr>
							<td><c:out value="${deposit.customerId}"></c:out></td>
							<td><c:out value="${customerName}"></c:out></td>
							
							<td><c:out value="${deposit.id}"></c:out></td>
							<td><c:out value="${deposit.accountNumber}"></c:out></td>
							
							<fmt:formatNumber value="${deposit.depositAmount}" pattern="#.##" var="depositAmount"/>				
							<td class="commaSeparated"><c:out value="${depositAmount}"></c:out></td>
							
							<fmt:formatNumber value="${deposit.currentBalance}" pattern="#.##" var="currentBalance"/>	
							<td class="commaSeparated"><c:out value="${currentBalance}"></c:out></td>

							<td><c:out value="${deposit.interestRate}"></c:out></td>
							
							
						</tr>
					</c:forEach>
				</c:if>

			</tbody>
			</table> 
			</div>

</div>

