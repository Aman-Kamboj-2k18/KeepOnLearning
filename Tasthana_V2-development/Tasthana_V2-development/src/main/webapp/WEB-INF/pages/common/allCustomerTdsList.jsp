<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="right-container" id="right-container">
	<div class="container-fluid">

		<div class="fd-list">
			<div class="header_customer">
				<h3>
					<spring:message code="label.customerTdsreports" />
				</h3>
			</div>

			<div class="fd-list-table">

				<span class="counter pull-right"></span>
				<div class="col-sm-12 col-md-12 col-lg-12">
					<input type="text" id="kwd_search" value=""
						placeholder="Search Here..." style="float: right;" />
				</div>
				<table class="table data jqtable example" id="my-table">
					<thead>
						<tr>
							<th><spring:message code="label.customerId" /></th>
							<th><spring:message code="label.customerName" /></th>
							<th><spring:message code="label.calculatedTDSAmount" /></th>
							<th><spring:message code="label.tdsAmount" /></th>
							
							
							<th><spring:message code="label.tdsDate" /></th>
							<th><spring:message code="label.financialYear" /></th> 
							
						</tr>
					</thead>
					<tbody>
						<c:if test="${! empty tdsLists}">
							<c:forEach items="${tdsLists}" var="tdsList">
								<tr>
									<td><c:out value="${tdsList.customerId}"></c:out></td>
									<td><c:out value="${tdsList.customerName}"></c:out></td>
									<td class="commaSeparated"><c:out value="${tdsList.calculatedTDSAmount}"></c:out></td>
									<td class="commaSeparated"><c:out value="${tdsList.tdsAmount}"></c:out></td>
									
									<td><fmt:formatDate pattern="dd/MM/yyyy"
											value="${tdsList.tdsCalcDate}" /></td>
									<td><c:out value="${tdsList.financialYear}"></c:out></td> 
									
								</tr>
							</c:forEach>
						</c:if>

					</tbody>

				</table>
				
			</div>

		</div>