<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="right-container" id="right-container">
	<div class="container-fluid">

		<div class="role-page"
			style="border: none; padding: 0; box-shadow: none; background: none;">
			
			<div class="Flexi_deposit">
			
			<div class="header_customer" style="margin-bottom: 10px;">
				<h3>
					Overdraft Summary
				</h3>
				
			</div>
			
			<div class="exit" style="font-size: 19px;">

				<h3 style="color: green">${sucess}</h3>
			</div>
			<div class="employee_details">
<input type="text" id="kwd_search" value=""
					placeholder="Search Here..." style="float: right; margin: 15px;" />
				<div class="space-10"></div>
				 <table class="table with-pager data jqtable example" id="my-table" style="width: 98%; margin-left: 15px;">
					<thead>
						<tr>
							<th>Overdraft Payment<spring:message code="label.id" /></th>
							<th>Overdraft Id</th>
							<th>Payment Date</th>
							<th>Payment Amount</th>
							<th>Payment Mode</th>
							<th>Payment Id</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${overdraftPayments}" var ="overdraftPayment">
								<tr>
									
									<td><c:out value="${overdraftPayment.id}"></c:out></td>
									<td><c:out value="${overdraftPayment.overdraftId}"></c:out></td>
									<td><c:out value="${overdraftPayment.paymentDate}"></c:out></td>
									<td><c:out value="${overdraftPayment.paymentAmount}"></c:out></td>
									<td><c:out value="${overdraftPayment.paymentMode}"></c:out></td>
									<td><c:out value="${overdraftPayment.paymentModeId}"></c:out></td>
									</tr>
									</c:forEach>
					</tbody>
				</table>
			<c:choose>
			<c:when test="${role eq 'ROLE_USER'}">
			<div style="margin-left: 24%;margin-bottom: 12px;"><a href = "overdraftSummary?menuId=${menuId}" class="btn btn-success">Back</a>
			
			 </div>
			
			</c:when>
			<c:otherwise>
			<div style="margin-left: 24%;margin-bottom: 12px;"><a href = "overdraftSummaryBackPress?menuId=${menuId}&id=${overdraftPayments[0].overdraftId}" class="btn btn-success">Back</a>
			
			 </div>
			</c:otherwise>
			
			</c:choose>
			
			
			</div>
			
	
			
			</div>
			
			</div>
			
		</div></div>
		
