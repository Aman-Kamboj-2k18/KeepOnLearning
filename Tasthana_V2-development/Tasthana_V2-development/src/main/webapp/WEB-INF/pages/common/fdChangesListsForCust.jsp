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
					<th><spring:message code="label.accountNo" /></th>
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
					<c:if test="${! empty depositLists}">
						<c:forEach items="${depositLists}" var="depositHolder">
							<tr>
							<td><c:out value="${depositHolder.depositId}"></c:out></td>
							<td><c:out value="${depositHolder.accountNumber}"></c:out></td>
							<fmt:formatNumber value="${depositHolder.currentBalance}" pattern="#.##" var="currentBalance"/>
							<td class="commaSeparated"><c:out value="${currentBalance}"></c:out></td>
							
							<fmt:formatNumber value="${depositHolder.depositAmount}" pattern="#.##" var="depositamount"/>
							<td class="commaSeparated"><c:out value="${depositamount}"></c:out></td>
							<td><fmt:formatDate pattern="dd/MM/yyyy" value="${depositHolder.newMaturityDate}" /></td>
							<td><fmt:formatDate pattern="dd/MM/yyyy" value="${depositHolder.createdDate}" /></td>
							
							<td><c:out value="${depositHolder.status}"></c:out></td>
							<td><c:if test="${empty depositHolder.category}">Regular</c:if><c:out value="${depositHolder.category}"></c:out></td>
							<td><a href="showDistributionRecordsCust?id=${depositHolder.depositId}&menuId=${menuId}"
									class="btn btn-primary"><spring:message code="label.showDetails"/></a></td>
							</tr>
						</c:forEach>
					</c:if>

				</tbody>
			</table> 
			</div>

</div>