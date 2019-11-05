
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%-- <%@page import="javax.servlet.http.Cookie"%> --%>


<div class="right-container" id="right-container">
	<div class="container-fluid">

		<div class="role-page"
			style="border: none; padding: 0; box-shadow: none; background: none;">
			
			<div class="Flexi_deposit">
			
			<div class="header_customer" style="margin-bottom: 30px;">
				<h3 align="center">
					Nav Bar Menu List
				</h3>
			</div>
			<div class="employee_details">
				<input type="text" id="kwd_search" value=""
					placeholder="Search Here..." style="float: right;" />

				<div class="space-10"></div>
				 <table class="table with-pager data jqtable example" id="my-table">
					<thead>
						<tr>
							<th><spring:message code="label.id" /></th>
							<th>Menu Name</th>
							<th>URL Pattern</th>
							<th>Created By</th>
							<th>Modified By</th>
							<th>Create On</th>
							<th>Modified On</th>
							<th>Status</th>
							<!-- <th>Action</th> -->
						
						</tr>
					</thead>
					<tbody>
						<c:if test="${! empty listMenu}">
							<c:forEach items="${listMenu}" var="menu">
								<tr>
									<td><c:out value="${menu.id}"></c:out></td>
									<td><c:out value="${menu.name}"></c:out></td>
									<td><c:out value="${menu.urlPattern}"></c:out></td>
									<td><c:out value="${menu.createdBy}"></c:out></td>
									<td><c:out value="${menu.modifiedBy}"></c:out></td>
									<td><c:out value="${menu.createdOn}"></c:out></td>
									<td><c:out value="${menu.modifiedOn}"></c:out></td>
									<td><c:out value="${menu.isActive}"></c:out></td>
									
									<%-- <td><a href="editRole?id=${menu.id}"
										class="btn btn-primary"><spring:message code="label.edit" /></a></td> --%>
								</tr>
							</c:forEach>
						</c:if>

					</tbody>
				</table>

			</div>
			
			</div>
			
			
			
		</div></div>
	</div>
	<script>
	
	
	
	
	
		$(document).ready(
				function() {

				});

		$("input").tagsinput('items');
	</script>
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

.secure-font{
-webkit-text-security:disc;}

.exit h3 {
	color: tomato;
	font-family: serif;
	letter-spacing: 0.5px;
	font-size: 1.2em;
	text-align: center;
}
</style>