<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div class="right-container" id="right-container">
	<div class="container-fluid">

		<div class="fd-list">
			<div class="header_customer">
				<h3>
					<spring:message code="label.closingFixedDepositList" />
				</h3>
			</div>

			<div class="fd-list-table">
				<div class="col-sm-12 col-md-12 col-lg-12">
					<div class="errorMsg">
						<font color="red">${Success}</font>
					</div>
				</div>
				<span class="counter pull-right"></span>
				<div class="col-sm-12 col-md-12 col-lg-12">
					<input type="text" id="kwd_search" value=""
						placeholder="Search Here..." style="float: right;" />
				</div>
				<table class="table data jqtable example" id="my-table">
					<thead>
						<tr>

							<th><spring:message code="label.id" /></th>
							<th><spring:message code="label.accno" /></th>
							<th><spring:message code="label.currentBalance" /></th>

							<th><spring:message code="label.depositAmt" /></th>
							<th><spring:message code="label.createdDate" /></th>

							<th><spring:message code="label.deposittype" /></th>
							<th><spring:message code="label.status" /></th>
								<th>Deposit Category</th>
							<th><spring:message code="label.action" /></th>

						</tr>
					</thead>
					<tbody>
						<c:if test="${! empty fixedDeposits}">
							<c:forEach items="${fixedDeposits}" var="fixedDeposit"
								varStatus="status">
							 
								<tr>
								<c:if test="${fixedDeposit.depositClassification ne 'Tax Saving Deposit'}"> 
									<td><c:out value="${fixedDeposit.id}"></c:out></td>
									<td><c:out value="${fixedDeposit.accountNumber}"></c:out>
									
									<fmt:formatNumber value="${fixedDeposit.currentBalance}" pattern="#.##" var="currentBalance"/>       
									<td class="commaSeparated"><c:out value="${currentBalance}"></c:out>
									
									<fmt:formatNumber value="${fixedDeposit.depositAmount}" pattern="#.##" var="depositAmount"/>       
									<td class="commaSeparated"><c:out value="${depositAmount}"></c:out></td>
									<td><fmt:formatDate pattern="dd/MM/yyyy"
											value="${fixedDeposit.createdDate}" /></td>
									<td><c:out value="${fixedDeposit.depositType}"></c:out></td>
									<td><c:out value="${fixedDeposit.status}"></c:out></td>
								    <td><c:if test="${empty fixedDeposit.depositCategory}">Regular</c:if><c:out value="${fixedDeposit.depositCategory}"></c:out></td>
					
									<td><a href="closeFD?id=${fixedDeposit.id}"
										id="delete[${status.index}]"
										onclick="onclickClose(${status.index})"
										class="btn btn-primary delete"
										data-confirm="Are you sure to close the deposit?"><spring:message
												code="label.close" /></a></td>
</c:if>
								</tr>
								
								<script>
							
							function onclickClose(index){
								var deleteId='delete['+index+']';
								
								var element= document.getElementById(deleteId);
								  var choice = confirm(element.getAttribute('data-confirm'));
								  
								  if (choice) {
								    window.location.href = element.getAttribute('href');
								  }
							}
								
							
							
								var deleteLinks = document.querySelectorAll('.delete');

								for (var i = 0; i < deleteLinks.length; i++) {
								  deleteLinks[i].addEventListener('click', function(event) {
									  event.preventDefault();

								  });
								}
								</script>
								
							</c:forEach>
						</c:if>

					</tbody>
				</table>
			</div>

		</div>