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
					<th>Account Number</th>
					<th><spring:message code="label.currentBalance" /></th>
					<th><spring:message code="label.fdAmount" /></th>
					<th><spring:message code="label.maturityDate"/></th>
					<th><spring:message code="label.createdDate" /></th>
					<th><spring:message code="label.status" /></th>
					<th>Deposit Category</th>
					<th><spring:message code="label.action"/></th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${! empty depositsList}">
						<c:forEach items="${depositsList}" var="deposit">
							<tr>
							<td><c:out value="${deposit.id}"></c:out></td>
							<td><c:out value="${deposit.accountNumber}"></c:out></td>
							<fmt:formatNumber value="${deposit.currentBalance}" pattern="#.##" var="currentBalance"/>
							<td class="commaSeparated"><c:out value="${currentBalance}"></c:out></td>
							<fmt:formatNumber value="${deposit.depositAmount}" pattern="#.##" var="depositamount"/>
							<td class="commaSeparated"><c:out value="${depositamount}"></c:out></td>
							<td><fmt:formatDate pattern="dd/MM/yyyy" value="${deposit.maturityDate}" /></td>
							<td><fmt:formatDate pattern="dd/MM/yyyy" value="${deposit.createdDate}"/></td>
							<td><c:out value="${deposit.status}"></c:out></td>
							<td><c:if test="${empty deposit.depositCategory}">Regular</c:if><c:out value="${deposit.depositCategory}"></c:out></td>
							<td><a href="holderWiseReport?depositId=${deposit.id}&depositAccountNo=${deposit.accountNumber}"
									class="btn btn-primary">Show Report</a></td>
							</tr>
						</c:forEach>
					</c:if>

				</tbody>
			</table> 
			</div>

</div>