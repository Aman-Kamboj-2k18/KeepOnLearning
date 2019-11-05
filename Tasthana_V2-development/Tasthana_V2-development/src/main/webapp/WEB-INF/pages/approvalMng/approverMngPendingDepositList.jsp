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
					<input type="text" id="kwd_search" value="" class="form-control" placeholder="Search Here..."  style="float:right;width:20%;margin-bottom:20px;"/>
			
			<table class="table data jqtable table-bordered example" id="my-table">
				<thead>
				<tr>
					<th><spring:message code="label.fdID" /></th>
					<th><spring:message code="label.accno" /></th>
				    <th><spring:message code="label.fdAmount" /></th>
					<th><spring:message code="label.currentBalance" /></th>
<%-- 					<th><spring:message code="label.genCategory" /></th>
 --%>					<th><spring:message code="label.maturityDate"/></th>
				    <th><spring:message code="label.selectDate"/></th>
					<th><spring:message code="label.createdDate" /></th>
					<th><spring:message code="label.status" /></th>
					<th><spring:message code="label.appStatus" /></th>
				</tr>
			</thead>
				<tbody>
				<c:if test="${! empty depositLists}">
					<c:forEach items="${depositLists}" var="depositList">
						<tr>
							<td><c:out value="${depositList.id}"></c:out></td>
							<td><c:out value="${depositList.accountNumber}"></c:out></td>
							
							<fmt:formatNumber value="${depositList.depositAmount}" pattern="#.##" var="depositAmount"/>
							<td><c:out value="${depositAmount}"></c:out></td>
							
							<fmt:formatNumber value="${depositList.currentBalance}" pattern="#.##" var="currentBalance"/>
							<td><c:out value="${currentBalance}"></c:out></td>
<%-- 							<td><c:out value="${depositList.depositCategory}"></c:out></td>
 --%>							<td><fmt:formatDate pattern="dd/MM/yyyy"
									value="${depositList.newMaturityDate}" />
							</td>
							<td><fmt:formatDate pattern="dd/MM/yyyy"
											value="${depositList.dueDate}" /></td>
							<td><fmt:formatDate pattern="dd/MM/yyyy"
											value="${depositList.createdDate}" /></td>
							<td><c:out value="${depositList.status}"></c:out></td>
							<td><c:out value="${depositList.approvalStatus}"></c:out></td>
							
						</tr>
					</c:forEach>
				</c:if>

			</tbody>
			</table> 
			</div>

</div>
<!-- <style>
	.header_customer {
    background: #03A9F4;
}
</style>
 -->