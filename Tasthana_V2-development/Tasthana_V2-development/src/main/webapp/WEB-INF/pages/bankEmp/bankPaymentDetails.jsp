<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="right-container" id="right-container">
        <div class="container-fluid">
 
<div class="fd-list">
			<div class="header_customer">	
				<h3><spring:message code="label.bankPaymentDetails"/></h3>
			</div>
			
			<div class="fd-list-table">	
			
			<span class="counter pull-right"></span>
				<div class="col-sm-12 col-md-12 col-lg-12">
					<input type="text" id="kwd_search" value="" placeholder="Search Here..."  style="float:right;"/>
				</div>
			<table class="table data jqtable example" id="my-table">
				<thead>
					<tr>
					<th><spring:message code="label.paymentDate" /></th>
					<th><spring:message code="label.depositHolderName" /></th>
					<th><spring:message code="label.amount" /></th>
					<th><spring:message code="label.paid"/></th>
					<th><spring:message code="label.nomineeName" /></th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${! empty bankPaymentDetails}">
						<c:forEach items="${bankPaymentDetails}" var="bankPayment">
							<tr>
							<td><fmt:formatDate pattern="dd/MM/yyyy" value="${bankPayment.amountPaidDate}" /></td>
							<td><c:out value="${bankPayment.customerName}"></c:out></td>
							<fmt:formatNumber value="${bankPayment.amount}" pattern="#.##" var="amount"/>
							<td class="commaSeparated"><c:out value="${amount}"></c:out></td>
							<c:choose>
							<c:when test="${bankPayment.nomineeId != null}">
							<td><c:out value="Nominee"></c:out></td>
							</c:when>
							<c:otherwise>
							<td><c:out value="Holder"></c:out></td>
							</c:otherwise>
							</c:choose>
							<c:choose>
							<c:when test="${bankPayment.nomineeId != null}">
							<td><c:out value="${bankPayment.nomineeName}"></c:out></td>
							</c:when>
							<c:otherwise>
							<td><c:out value=""></c:out></td>
							</c:otherwise>
							</c:choose>
							</tr>
						</c:forEach>
					</c:if>

				</tbody>
			</table> 
			</div>

</div>