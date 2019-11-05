<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div class="fd-approval-list">
			<div class="col-sm-12 col-md-12 col-lg-12 header_customer">	
				<h3 align="center"><spring:message code="label.fixedDepositList"/></h3>
			</div>
			
				<div class="search-fd">	
			
					<span class="counter pull-right"></span>
					<div class="col-sm-12 col-md-12 col-lg-12">
							<input type="text" id="kwd_search" value="" placeholder="Search Here..."  style="float:right;"/>
						</div>
				</div>
			
			<div class="fd-approval-list-table">
			<div class="space-10"></div>
			<table class="table data jqtable example" id="my-table">
				<thead>
					<tr>
						<th><spring:message code="label.id"/></th>
						<th><spring:message code="label.date"></spring:message>
						<th><spring:message code="label.customerName"/></th>
						<th><spring:message code="label.customerId"/></th>
						<th><spring:message code="label.accountBalance"/></th>
						<th><spring:message code="label.fdID"/></th>
						<th><spring:message code="label.fdAmount"/></th>
						<th><spring:message code="label.status"/></th>
						<th><spring:message code="label.action"/></th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${! empty fixedDeposits}">
						<c:forEach items="${fixedDeposits}" var="fixedDeposit">
							<tr>
								<td><c:out value="${fixedDeposit.id}"></c:out></td>
								<fmt:formatDate var='date' value="${fixedDeposit.fdCreateDate}"  pattern="dd/MM/yyyy"/>
								<td><c:out value="${date}"></c:out>
								<td><c:out value="${fixedDeposit.customerName}"></c:out>
								<td><c:out value="${fixedDeposit.customerID}"></c:out></td>
								<td><c:out value="${fixedDeposit.accountBalance}"></c:out></td>
								<td><c:out value="${fixedDeposit.fdID}"></c:out></td>
								<td><c:out value="${fixedDeposit.fdAmount}"></c:out></td>
								<td><c:out value="${fixedDeposit.status}"></c:out></td>

								<td><a href="approveFD?id=${fixedDeposit.id}"
									class="btn btn-primary"><spring:message code="label.select"/></a></td>
							</tr>
						</c:forEach>
					</c:if>

				</tbody>
			</table> 
			</div>
</div>
<style>
	.table>tbody>tr>td, .table>tbody>tr>th, .table>tfoot>tr>td, .table>tfoot>tr>th, .table>thead>tr>td, .table>thead>tr>th {
    border: 1px solid #B1B1B1;
}
.table>caption+thead>tr:first-child>td, .table>caption+thead>tr:first-child>th, .table>colgroup+thead>tr:first-child>td, .table>colgroup+thead>tr:first-child>th, .table>thead:first-child>tr:first-child>td, .table>thead:first-child>tr:first-child>th {
    border-top: 0;
    border: 2px solid #585858;
}
.fd-approval-list {
    float: left;
    width: 100%;
    margin-bottom: 160px;
}
.search-fd {
    float: left;
    width: 100%;
}
</style>