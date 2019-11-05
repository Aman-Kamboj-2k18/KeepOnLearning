<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<script>

</script>
<div class="right-container" id="right-container">
	<div class="container-fluid">



		<div class="Flexi_deposit">


			<div class="header_customer">	
				<h3><spring:message code="label.customerList"/></h3>
			</div>
			
			<div class="fd-list-table">	
				<span class="counter pull-right"></span>
					<input type="text" id="kwd_search" value="" class="form-control" placeholder="Search Here..."  style="float:right;margin-bottom: 10px; width: 20%;"/>
				
				<div>
				
			
				<table class="table table-bordered jqtable example" align="center" id="my-table">
					<thead>
						<tr>
						
							<th><spring:message code="label.genCategory"/></th>
							<th><spring:message code="label.customerName"/></th>
							<th><spring:message code="label.customerId"/></th>
							<th><spring:message code="label.gender"/></th>
							<%-- <th><spring:message code="label.age"/></th> --%>
							<th><spring:message code="label.dateOfBirth"/></th>
							<th><spring:message code="label.status"/></th>
							<th>Select</th>
							
						</tr>
					</thead>
					<tbody>
					 
						<c:if test="${! empty cusList}">
							<c:forEach items="${cusList}" var="cus">
							   
								<tr>
									
									<td><c:out value="${cus.category}"></c:out>
									<td><c:out value="${cus.customerName}"></c:out></td>
									<td><c:out value="${cus.customerID}"></c:out></td>
									<td><c:out value="${cus.gender}"></c:out>
									<%-- <td><c:out value="${cus.age}"></c:out></td> --%>
									<td><fmt:formatDate pattern="dd/MM/yyyy"
									value="${cus.dateOfBirth}" />
						        	</td>
									<td><c:out value="${cus.status}"></c:out></td>
									<td><a href="showSavingAccount?id=${cus.id}&menuId=${menuId}">Show</a></td>
										</tr>
							</c:forEach>
						</c:if>
	
					</tbody>
				</table> .
			</div>

</div></div></div></div>
<!-- <style>
		.table-bordered {
    border: 1px solid #BFBABA;
}
	  .table-bordered>tbody>tr>td, .table-bordered>tbody>tr>th, .table-bordered>tfoot>tr>td, .table-bordered>tfoot>tr>th, .table-bordered>thead>tr>td, .table-bordered>thead>tr>th{ border: 1px solid #C7C7C7;}
</style> -->