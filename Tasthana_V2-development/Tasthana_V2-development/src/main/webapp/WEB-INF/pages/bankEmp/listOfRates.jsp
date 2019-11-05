<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="right-container" id="right-container">
        <div class="container-fluid">
			

<div class="list-of-rates">
			<div class="header_customer">	
				<h3><spring:message code="label.listOfCustomerConfiguration"/></h3>
			</div>
			
			<div class="list-of-rates-table">	
				<span class="counter pull-right"></span>
					<input type="text" id="kwd_search" value="" class="form-control" placeholder="Search Here..."  style="float:right;width:20%;"/>
				<div class="space-10"></div>
				
				<table class="table table-bordered" align="center" id="my-table">
					<thead>
						<tr>
							<th><spring:message code="label.id"/></th>
							<th><spring:message code="label.category"/></th>
							<th><spring:message code="label.tds"/> %</th>
							<th>Deposit Fixed Percentage(%)</th>
							<th>Deposit Variable Percentage(%)</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${! empty rates}">
							<c:forEach items="${rates}" var="rates">
								<tr>
									<td><c:out value="${rates.id}"></c:out></td>
									<td><c:out value="${rates.type}"></c:out>
									<td><c:out value="${rates.tds}"></c:out></td>
									<td><c:out value="${rates.fdFixedPercent}"></c:out></td>
									<td><c:out value="${100-rates.fdFixedPercent}"></c:out></td>
									<td><a href="setRate?id=${rates.id}"
										class="btn btn-primary"><spring:message code="label.update"/></a>
																</tr>
							</c:forEach>
						</c:if>
	
					</tbody>
				</table> 
			</div>

</div>
<!-- <style>
		.table-bordered {
    border: 1px solid #BFBABA;
}
	  .table-bordered>tbody>tr>td, .table-bordered>tbody>tr>th, .table-bordered>tfoot>tr>td, .table-bordered>tfoot>tr>th, .table-bordered>thead>tr>td, .table-bordered>thead>tr>th{ border: 1px solid #C7C7C7;}
</style> -->