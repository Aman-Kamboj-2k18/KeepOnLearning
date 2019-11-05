<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="right-container" id="right-container">
	<div class="container-fluid">


		<div class="customer-approval-list">
			<div class="header_customer">
				<h3>
					<spring:message code="label.customerList" />
				</h3>
			</div>
			<div class="col-sm-12 col-md-12 col-lg-12">
				<div class="successMsg">
					<b><font color="green">${success}</font></b>
				</div>
			</div>

			<div class="col-sm-12 col-md-12 col-lg-12">


				<div class="customer-app-list-table">
					<span class="counter pull-right"></span> <input type="text"
						id="kwd_search" class="form-control" value=""
						placeholder="Search Here..."
						style="float: right; width: 20%; margin-bottom: 20px;" />

					<div class="space-10"></div>

					<b style="font-size: 1.3em; color: blue">Pending List</b>
					<table class="table data jqtable example" id="my-table">
						<thead>
							<tr>
								<th><spring:message code="label.id" /></th>
								<th><spring:message code="label.customerName" /></th>
								<th><spring:message code="label.email" /></th>
								<th><spring:message code="label.address" /></th>
								<th><spring:message code="label.status" /></th>
								<th><spring:message code="label.action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${! empty customerList}">
								<c:forEach items="${customerList}" var="customer">
									<c:if test="${customer.status=='Pending'}">
									<tr>
										<td><c:out value="${customer.id}"></c:out></td>
										<td><c:out value="${customer.customerName}"></c:out>
										<td><c:out value="${customer.email}"></c:out>
										<td><c:out value="${customer.address}"></c:out></td>
										<td><c:out value="${customer.status}"></c:out></td>



										<td><a href="approveCustomer?id=${customer.id}"
											class="btn btn-primary"><spring:message
													code="label.select" /></a></td>
									</tr>
									</c:if>
								</c:forEach>
							</c:if>

						</tbody>
					</table>

					<div class="space-10"></div>

					<b style="font-size: 1.3em; color: blue">Suspended List</b>

					<table class="table data jqtable example" id="my-table">
						<thead>
							<tr>
								<th><spring:message code="label.id" /></th>
								<th><spring:message code="label.customerName" /></th>
								<th><spring:message code="label.email" /></th>
								<th><spring:message code="label.address" /></th>
								<th><spring:message code="label.status" /></th>
								<th><spring:message code="label.action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${! empty customerList}">
								<c:forEach items="${customerList}" var="customer">
									<c:if test="${customer.status=='Suspended'}">
									<tr>
										<td><c:out value="${customer.id}"></c:out></td>
										<td><c:out value="${customer.customerName}"></c:out>
										<td><c:out value="${customer.email}"></c:out>
										<td><c:out value="${customer.address}"></c:out></td>
										<td><c:out value="${customer.status}"></c:out></td>



										<td><a href="approveCustomer?id=${customer.id}"
											class="btn btn-primary"><spring:message
													code="label.select" /></a></td>
									</tr>
									</c:if>
								</c:forEach>
							</c:if>

						</tbody>
					</table>


					<div class="space-10"></div>

					<b style="font-size: 1.3em; color: blue">Closed List</b>

					<table class="table data jqtable example" id="my-table">
						<thead>
							<tr>
								<th><spring:message code="label.id" /></th>
								<th><spring:message code="label.customerName" /></th>
								<th><spring:message code="label.email" /></th>
								<th><spring:message code="label.address" /></th>
								<th><spring:message code="label.status" /></th>
								<th><spring:message code="label.action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${! empty customerList}">
								<c:forEach items="${customerList}" var="customer">
								<c:if test="${customer.status=='Closed'}">
									<tr>
										<td><c:out value="${customer.id}"></c:out></td>
										<td><c:out value="${customer.customerName}"></c:out>
										<td><c:out value="${customer.email}"></c:out>
										<td><c:out value="${customer.address}"></c:out></td>
										<td><c:out value="${customer.status}"></c:out></td>



										<td><a href="approveCustomer?id=${customer.id}"
											class="btn btn-primary"><spring:message
													code="label.select" /></a></td>
									</tr>
									</c:if>
								</c:forEach>
							</c:if>

						</tbody>
					</table>


				</div>
			</div>
		</div>
		<style>
.table>tbody>tr>td, .table>tbody>tr>th, .table>tfoot>tr>td, .table>tfoot>tr>th,
	.table>thead>tr>td, .table>thead>tr>th {
	border: 1px solid #B1B1B1;
}

.table>caption+thead>tr:first-child>td, .table>caption+thead>tr:first-child>th,
	.table>colgroup+thead>tr:first-child>td, .table>colgroup+thead>tr:first-child>th,
	.table>thead:first-child>tr:first-child>td, .table>thead:first-child>tr:first-child>th
	{
	border-top: 0;
	border: 2px solid #585858;
}
</style>