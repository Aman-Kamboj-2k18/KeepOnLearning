<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="right-container" id="right-container">
        <div class="container-fluid">

<div class="fd-list">
			<div class="header_customer">	
				<h3><spring:message code="label.fixedDepositList"/></h3>
			</div>
			
			<div class="fd-list-table">	
			
			<span class="counter pull-right"></span>
				<div class="col-sm-12 col-md-12 col-lg-12">
					<input type="text" id="kwd_search" value="" placeholder="Search Here..."  style="float:right;"/>
				</div>
			<table class="table data jqtable example" id="my-table">
				<thead>
				<tr>
					<th><spring:message code="label.fdID" /></th>
					<th><spring:message code="label.accno" /></th>
					<th><spring:message code="label.currentBalance" /></th>
					
					<th><spring:message code="label.fdAmount" /></th>
					<th><spring:message code="label.maturityDate"/></th>
					<th><spring:message code="label.depositHolderStatus"/></th>
					<th><spring:message code="label.contribution"/></th>
					<th><spring:message code="label.createdDate" /></th>
					<th><spring:message code="label.status" /></th>
						<th>Deposit Category</th>
				
				</tr>
			</thead>
				<c:if test="${! empty depositHolderList}">
					<c:forEach items="${depositHolderList}" var="depositHolder">
						<tr>
							<td><c:out value="${depositHolder.deposit.id}"></c:out></td>
							<td><c:out value="${depositHolder.deposit.accountNumber}"></c:out></td>
							
							<fmt:formatNumber value="${depositHolder.deposit.currentBalance}" pattern="#.##" var="currentBalance"/>
							<td class="commaSeparated">${currentBalance}</td>
							
							<fmt:formatNumber value="${depositHolder.deposit.depositAmount}" pattern="#.##" var="depositamount"/>
							<td class="commaSeparated">${depositamount}</td>
							
							<td><fmt:formatDate pattern="dd/MM/yyyy"
									value="${depositHolder.deposit.newMaturityDate}" />
							</td>	
							<td><c:out value="${depositHolder.depositHolder.depositHolderStatus}"></c:out></td>
							<td><c:out value="${depositHolder.depositHolder.contribution}"></c:out></td>
							<td><fmt:formatDate pattern="dd/MM/yyyy" value="${depositHolder.deposit.createdDate}"/></td>
							<td><c:out value="${depositHolder.deposit.status}"></c:out></td>
							<td><c:if test="${empty depositHolder.deposit.depositCategory}">Regular</c:if><c:out value="${depositHolder.deposit.depositCategory}"></c:out></td>
						</tr>
					</c:forEach>
				</c:if>

			</tbody>
			</table> 
			</div>

</div>

</div></div>