<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="right-container" id="right-container">
        <div class="container-fluid">
   
<div class="fd-list">
			<div class="header_customer">	
				<h3>Interest Report</h3>
			</div>
			
			<div class="fd-list-table">	
			
			<span class="counter pull-right"></span>
				<div class="col-sm-12 col-md-12 col-lg-12">
					<input type="text" id="kwd_search" value="" placeholder="Search Here..."  style="float:right; margin-bottom: 15px;"/>
				</div>
			<table class="table data jqtable example table-bordered table-striped table-hover " id="my-table">
				<thead>
					<tr>
					<th><spring:message code="label.fdID" /></th>
					<th><spring:message code="label.accountNo" /></th>
					<th>Contribution(%)</th>
					<th><spring:message code="label.maturityDate"/></th>
					<th><spring:message code="label.createdDate" /></th>
					<th>Holder <spring:message code="label.status" /></th>
					<th>Deposit Category</th>
					<th>Action</th>
<%-- 					<th><spring:message code="label.tds"/></th>
 --%>					</tr>
				</thead>
				<tbody>
					<c:if test="${! empty depositLists}">
						<c:forEach items="${depositLists}" var="deposit">
							<tr>
							<td><c:out value="${deposit.depositId}"></c:out></td>
							<td><c:out value="${deposit.accountNumber}"></c:out></td>
							<td><c:out value="${deposit.contribution}"></c:out></td>
							<td><fmt:formatDate pattern="dd/MM/yyyy" value="${deposit.newMaturityDate}" /></td>
							<td><fmt:formatDate pattern="dd/MM/yyyy"
									value="${deposit.createdDate}" />
						        	</td>
							<td><c:out value="${deposit.depositHolderStatus}"></c:out></td>
							<td><c:if test="${empty deposit.category}">Regular</c:if><c:out value="${deposit.category}"></c:out></td>
						
								<td><a href="showInterestRecords?id=${deposit.depositId}&menuId=${menuId}"
									class="btn btn-primary"><spring:message code="label.showDetails"/></a></td>
							<%-- <td><a href="showTdsRecords?id=${deposit.depositId}"
									class="btn btn-primary"><spring:message code="label.showTds"/></a></td>
							 --%></tr>
						</c:forEach>
					</c:if>

				</tbody>
			</table> 
			</div>

</div>