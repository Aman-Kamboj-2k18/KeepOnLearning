<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="right-container" id="right-container">
        <div class="container-fluid">
  
<div class="fd-list">
			<div class="header_customer">	
				<h3>List of Overdrafts</h3>
			</div>
			
			<div class="fd-list-table">	
			
			<span class="counter pull-right"></span>
				<div class="col-sm-12 col-md-12 col-lg-12">
					<input type="text" id="kwd_search" value="" placeholder="Search Here..."  style="float:right;"/>
				</div>
			<table class="table data jqtable example" id="my-table">
				<thead>
				<tr>
					<th>Overdraft Number</th>
					<th>Issue Date</th>
					<th>Sanctioned Amount</th>
					<th>Withdrawable Amount</th>
					<th>Amount Withdrawn</th>
									
				</tr>
			</thead>
				<tbody>
				<c:if test="${! empty overdraftIssueList}">
					<c:forEach items="${overdraftIssueList}" var="overdraft">
						<tr>
							<td><c:out value="${overdraft.overdraftNumber}"></c:out></td>
							<td><c:out value="${overdraft.issueDate}"></c:out></td>
							
							<fmt:formatNumber value="${overdraft.sanctionedAmount}" pattern="#.##" var="sanctionedAmount"/>	
							<td class="commaSeparated">${sanctionedAmount}</td>
							<fmt:formatNumber value="${overdraft.withdrawableAmount}" pattern="#.##" var="withdrawableAmount"/>	
							<td class="commaSeparated">${withdrawableAmount}</td>
							
							<fmt:formatNumber value="${overdraft.totalAmountWithdrawn}" pattern="#.##" var="amountWithdrawn"/>				
							<td class="commaSeparated">${amountWithdrawn}</td>
							
							
						</tr>
					</c:forEach>
				</c:if>

			</tbody>
			</table> 
			</div>

</div>

