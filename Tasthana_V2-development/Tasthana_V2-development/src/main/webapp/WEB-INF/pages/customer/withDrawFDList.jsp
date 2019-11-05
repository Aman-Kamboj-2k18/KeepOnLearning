<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="right-container" id="right-container">
        <div class="container-fluid">
			
<div class="fd-list">
			<div class="header_customer">	
				<h3 align="center"><spring:message code="label.withDrawFDList"/></h3>
			</div>
			
			<div class="fd-list-table">	
			<div class="col-sm-12 col-md-12 col-lg-12">
		   <div class="errorMsg"><font color="red">${Success}</font></div>
	        </div>
			<span class="counter pull-right"></span>
				<div class="col-sm-12 col-md-12 col-lg-12">
					<input type="text" id="kwd_search" value="" placeholder="Search Here..."  style="float:right;"/>
				</div>
			<table class="table data jqtable example" id="my-table">
				<thead>
					<tr>
						<th><spring:message code="label.id"/></th>
						<th><spring:message code="label.date"></spring:message>
						<th><spring:message code="label.customerName"/></th>
						<th><spring:message code="label.customerId"/></th>
						<%-- <th><spring:message code="label.accountBalance"/></th> --%>
						<th><spring:message code="label.fdID"/></th>
						<th><spring:message code="label.fdAmount"/></th>
						<%-- <th><spring:message code="label.status"/></th> --%>
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
								<%-- <td><c:out value="${fixedDeposit.accountBalance}"></c:out></td> --%>
								<td><c:out value="${fixedDeposit.fdID}"></c:out></td>
								<td><c:out value="${fixedDeposit.fdAmount}"></c:out></td>
								<%-- <td><c:out value="${fixedDeposit.status}"></c:out></td> --%>

								<td><a href="withDrawFD?id=${fixedDeposit.id}"
									class="btn btn-primary" ><spring:message code="label.edit"/></a></td>
							</tr>
						</c:forEach>
					</c:if>

				</tbody>
			</table> 
			</div>

</div>