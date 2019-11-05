<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div class="right-container" id="right-container">
        <div class="container-fluid">
			
<div class="bank-emp-list">
	<div class="header_customer">
		<h3>
			<spring:message code="label.listOfApprovalManager" />
		</h3>
	</div>
	<div class="col-sm-12 col-md-12 col-lg-12">
		<font color="red">${success} </font>
	</div>

	

		<input type="text" id="kwd_search" value=""
			placeholder="Search Here..." style="float: right;" />
	<div class="space-10"></div>
	<table class="table data jqtable example" id="my-table">


		<thead>
			<tr>
				<th><spring:message code="label.id" /></th>
				<th><spring:message code="label.userName" /></th>
				<th><spring:message code="label.contactNumber" /></th>
				<th><spring:message code="label.currentRole" /></th>
				<th><spring:message code="label.email" /></th>
				<th><spring:message code="label.notificationStatus" /></th>
				<th><spring:message code="label.action" /></th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${! empty userList}">
				<c:forEach items="${userList}" var="user">
					<tr>
						<td><c:out value="${user.id}"></c:out></td>
						<td><c:out value="${user.userName}"></c:out></td>
						<td><c:out value="${user.contactNo}"></c:out></td>
						<td><c:out value="${user.currentRole}"></c:out></td>
						<td><c:out value="${user.email}"></c:out></td>
						<td><c:out value="${user.notificationStatus}"></c:out></td>
						<td><a href="selectApproveManager?id=${user.id}"
							class="btn btn-primary"><spring:message
									code="label.sendEmailNotification" /></a></td>
					</tr>
				</c:forEach>
			</c:if>

		</tbody>
	</table>

</div></div>
<style>
	.table>tbody>tr>td, .table>tbody>tr>th, .table>tfoot>tr>td, .table>tfoot>tr>th, .table>thead>tr>td, .table>thead>tr>th {
    border: 1px solid #B1B1B1;
}
.table>caption+thead>tr:first-child>td, .table>caption+thead>tr:first-child>th, .table>colgroup+thead>tr:first-child>td, .table>colgroup+thead>tr:first-child>th, .table>thead:first-child>tr:first-child>td, .table>thead:first-child>tr:first-child>th {
    border-top: 0;
    border: 2px solid #585858;
}
</style>

<script>
	$(document).ready(function() {
		$('#bootstrap-table').bdt();
	});
</script>
