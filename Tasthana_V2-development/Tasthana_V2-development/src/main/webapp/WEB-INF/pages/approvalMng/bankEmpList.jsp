<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="right-container" id="right-container">
	<div class="container-fluid">
	
		<div class="bank-emp-list">
			<div class="header_customer">
				<h3>
					<spring:message code="label.bankEmployeeApprovalRequests" />
				</h3>
			</div>
			<div class="bank-emp-list-success">
				<div class="successMsg">
					<b><font color="green">${success}</font></b>
				</div>
			</div>
			<div class="search-emp">
				<input type="text" id="kwd_search" value=""
					placeholder="Search Here..." style="float: right;" />
			</div>
			<div class="space-10"></div>
			<div class="bank-emp-list-table">
			 <b style="font-size: 1.3em; color:blue">Pending List</b> 
			
			
				<table class="table data jqtable example" id="my-table">
				<%--  <caption><b>Pending List</b></caption> --%>
					<thead>
						<tr>
						
							<th><spring:message code="label.id" /></th>
							<th><spring:message code="label.userName" /></th>
							<th><spring:message code="label.contactNumber" /></th>
							<th><spring:message code="label.currentRole" /></th>
							<th><spring:message code="label.email" /></th>
							<th><spring:message code="label.status" /></th>
							<th><spring:message code="label.action" /></th>

						</tr>
					</thead>
					<tbody>
						<c:if test="${! empty userbankList}">
					

							<c:forEach items="${userbankList}" var="user">
							<c:if test="${user.status=='Pending'}">
						
								<tr>
								    <td><c:out value="${user.id}"></c:out></td>
									<td><c:out value="${user.userName}"></c:out></td>
									<td><c:out value="${user.contactNo}"></c:out></td>
									<td><c:out value="${user.currentRole}"></c:out></td>
									<td><c:out value="${user.email}"></c:out></td>
									<td><c:out value="${user.status}"></c:out></td>


									<td>&nbsp;<a href="approveBankEmp?id=${user.id}"
										class="btn btn-primary"><spring:message
												code="label.select" /></a>
									</td>
								</tr>
								</c:if>
							</c:forEach>
						</c:if>

					</tbody>
				</table>
			</div>
			
			
				<div class="space-10"></div>
			<div class="bank-emp-list-table">
			 <b style="font-size: 1.3em;color:blue"">Suspended List</b>
				<table class="table data jqtable example" id="my-table">
				
					<thead>
						<tr>
						
							<th><spring:message code="label.id" /></th>
							<th><spring:message code="label.userName" /></th>
							<th><spring:message code="label.contactNumber" /></th>
							<th><spring:message code="label.currentRole" /></th>
							<th><spring:message code="label.email" /></th>
							<th><spring:message code="label.status" /></th>
							<th><spring:message code="label.action" /></th>

						</tr>
					</thead>
					<tbody>
						<c:if test="${! empty userbankList}">
					

							<c:forEach items="${userbankList}" var="user">
							<c:if test="${user.status=='Suspended'}">
						
								<tr>
								    <td><c:out value="${user.id}"></c:out></td>
									<td><c:out value="${user.userName}"></c:out></td>
									<td><c:out value="${user.contactNo}"></c:out></td>
									<td><c:out value="${user.currentRole}"></c:out></td>
									<td><c:out value="${user.email}"></c:out></td>
									<td><c:out value="${user.status}"></c:out></td>


									<td>&nbsp;<a href="approveBankEmp?id=${user.id}"
										class="btn btn-primary"><spring:message
												code="label.select" /></a>
									</td>
								</tr>
								</c:if>
							</c:forEach>
						</c:if>

					</tbody>
				</table>
			</div>
			
			
			
			
				<div class="space-10"></div>
			<div class="bank-emp-list-table">
			<b style="font-size: 1.3em; color:blue"">Closed List</b>
				<table class="table data jqtable example" id="my-table">
				
					<thead>
						<tr>
						
							<th><spring:message code="label.id" /></th>
							<th><spring:message code="label.userName" /></th>
							<th><spring:message code="label.contactNumber" /></th>
							<th><spring:message code="label.currentRole" /></th>
							<th><spring:message code="label.email" /></th>
							<th><spring:message code="label.status" /></th>
							<th><spring:message code="label.action" /></th>

						</tr>
					</thead>
					<tbody>
						<c:if test="${! empty userbankList}">
					

							<c:forEach items="${userbankList}" var="user">
							<c:if test="${user.status=='Closed'}">
						
								<tr>
								    <td><c:out value="${user.id}"></c:out></td>
									<td><c:out value="${user.userName}"></c:out></td>
									<td><c:out value="${user.contactNo}"></c:out></td>
									<td><c:out value="${user.currentRole}"></c:out></td>
									<td><c:out value="${user.email}"></c:out></td>
									<td><c:out value="${user.status}"></c:out></td>


									<td>&nbsp;<a href="approveBankEmp?id=${user.id}"
										class="btn btn-primary"><spring:message
												code="label.select" /></a>
									</td>
								</tr>
								</c:if>
							</c:forEach>
						</c:if>

					</tbody>
				</table>
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

h3 {
	text-align: center;
}
</style>