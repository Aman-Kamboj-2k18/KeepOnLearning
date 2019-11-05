<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="right-container" id="right-container">
        <div class="container-fluid">
		
<div class="fd-list">
			<div class="header_customer">	
				<h3>View Product Configuration</h3>
			</div>
			
			<div class="fd-list-table">	
			
			<span class="counter pull-right"></span>
					<input type="text" id="kwd_search" class="form-control" value="" placeholder="Search Here..."  style="float:right;width:20%;"/>
					<span id="error" style="color:red; margin-left: 307px;"></span>
			<table class="table data jqtable example table-bordered table-striped table-hover" id="my-table">
				<thead>
					<tr>
					<th>S.No</th>
					<th>Product Code</th>
					<th>Product Name</th>
					<th>Product Type</th>
					<th>Citizen</th>
					<th>Product Start Date</th>
					<th>Product End Date</th>
					<th><spring:message code="label.action"/></th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${! empty productConfigurations}">
			
						<c:forEach items="${productConfigurations}" var="productConfiguration">
							 <tr>
							<td><c:out value="${productConfiguration.id}"></c:out></td>
							<td><c:out value="${productConfiguration.productCode}"></c:out></td>
							<td><c:out value="${productConfiguration.productName}"></c:out></td>
							<td><c:out value="${productConfiguration.productType}"></c:out></td>
							<td><c:out value="${productConfiguration.citizen}"></c:out></td>
							<td><fmt:formatDate pattern="dd/MM/yyyy" value="${productConfiguration.productStartDate}" /></td>
							<td><fmt:formatDate pattern="dd/MM/yyyy" value="${productConfiguration.productEndDate}"/></td>
							<td><a onclick="editProductConfiguration('${productConfiguration.id}')" href="#"
									class="btn btn-primary"><spring:message code="label.edit"/></a></td>
							</tr>
							
						</c:forEach>
						
					</c:if> 

				</tbody>
			</table> 
			</div>

</div>
<script>

function editProductConfiguration(id) {
	
	 window.location="productConfiurationByAccountType?id="+id; 
	
}

</script>