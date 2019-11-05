<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="right-container" id="right-container">
        <div class="container-fluid">
			
<div class="Flexi_deposit">
	
	<div class="header_customer" style="margin-bottom:25px;">
		<h3>
			Withdrawal List
		</h3>
	</div>
	<div class="col-md-12" style="padding:30px;text-align:center;">
			<span style="color:red;">${error}</span>
	</div>
<div class="flexi_table">


					<table class="table data jqtable example" id="my-table">
						<thead>
							 <tr>
								     <td><b>Deposit Id</b></td>
								    
								     <td><b>Withdrawn By</b></td>
								     <td><b>Withdrawal Date</b></td>
								     <td><b>Withdrawal Amount</b></td>
								     <td><b>Interest Given</b></td>
								     <td><b>Total Withdrawal</b></td>
								 
								     </tr>
						</thead>
						<tbody>
							<c:if test="${! empty withdrawList}">
								<c:forEach items="${withdrawList}" var="w">
									
									<tr>
									
										<td><c:out value="${w.depositId}"></c:out></td>
										<td><c:out value="${w.customerName}"></c:out>
										<td><c:out value="${w.withdrawDate}"></c:out>
										<td><c:out value="${w.withdrawAmount}"></c:out></td>
										<fmt:formatNumber value="${w.interestAmount}" pattern="#.##" var="interestAmount"/>       
								     	<td class="commaSeparated"><c:out value="${interestAmount}"></c:out>
									    <fmt:formatNumber value="${w.totalAmount}" pattern="#.##" var="totalAmount"/>       
								     	<td class="commaSeparated"><c:out value="${totalAmount}"></c:out>
									
									</tr>
									
								</c:forEach>
							</c:if>

						</tbody>
					</table>



	</div>

	</div>
</div>
</div>
<style>
	.flexi_table {
    margin-bottom: 210px;
}
.space-45 {
    height: 27px;
}
input#fdID {
    margin-top: 0px;
}
</style>