<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div class="fd-rates-data">
			<div class="header_customer">	
				<h3 align="center"><spring:message code="label.fixedDeposit"/></h3>
			</div>
			
			<div class="fd-rates-data-table">	
		
			<table class="table data jqtable example" id="my-table">
				<thead>
					<tr>
						<%-- <th><spring:message code="label.id"/></th> --%>
						<th><spring:message code="label.customerId"/></th>
						<th><spring:message code="label.fdAmount"/></th>
						<th><spring:message code="label.selectFormat"/></th>
						<th><spring:message code="label.fdDeductDate"/></th>
						
						<%-- <th><spring:message code="label.fdDueDate"/></th> --%>
							<th><spring:message code="label.interestAmt"/></th>
						<th><spring:message code="label.fdCurrentAmt"/></th>
						<th><spring:message code="label.fdTenureDate"/></th>
					
						
						
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${! empty fdRates}">
						<c:forEach items="${fdRates}" var="fdRates">
							<tr>
								<%-- <td><c:out value="${fdRates.id}"></c:out></td> --%>
								<td><c:out value="${fdRates.customerID}"></c:out>
								<td><c:out value="${fdRates.fdAmount}"></c:out></td>
								<td><c:out value="${fdRates.paymentType}"></c:out></td>
								 <fmt:formatDate var='date1' value='${fdRates.fdDeductDate}' pattern="dd/MM/yyyy" />   
								<td><c:out value="${date1}"></c:out></td>
								
								<%--  <fmt:formatDate type="date" var='date2' value='${fdRates.fdDueDate}' />  
								<td><c:out value="${date2}"></c:out></td> --%>
								<td><c:out value="${fdRates.interestAmount}"></c:out></td>
								<td><c:out value="${fdRates.fdCurrentAmount}"></c:out></td>
								<fmt:formatDate var='date3' value='${fdRates.fdTenureDate}' pattern="dd/MM/yyyy" />
								<td><c:out value="${date3}"></c:out></td>
								
														</tr>
						</c:forEach>
					</c:if>

				</tbody>
			</table> 
			</div>

</div>
<style>
	.fd-rates-data {
    margin-bottom: 200px;
}
</style>