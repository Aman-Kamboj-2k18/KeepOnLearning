<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="right-container" id="right-container">
	<div class="container-fluid">

		<div class="fd-list">
			<div class="header_customer">
				<h3 align="center">
					<spring:message code="label.closedDepositList" />
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
							<th><spring:message code="label.fdID" /></th>
							<th>Primary Holder Name</th>
							<th><spring:message code="label.depositCategory" /></th>
							<th><spring:message code="label.accountNo" /></th>
							<th><spring:message code="label.fdAmount" /></th>
							<th><spring:message code="label.maturityDate" /></th>
							<th><spring:message code="label.createdDate" /></th>
							<th><spring:message code="label.status" /></th>
							<th><spring:message code="label.action" /></th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${! empty depositLists}">
							<c:forEach items="${depositLists}" var="deposit">
								<tr>
									<td><c:out value="${deposit.depositId}"></c:out></td>
									<td><c:out value="${deposit.customerName}"></c:out></td>
									<td><c:if test="${deposit.depositCategory=='AUTO'}">Auto</c:if>
										<c:if test="${deposit.depositCategory!='AUTO'}">Regular</c:if></td>
									<td><c:out value="${deposit.accountNumber}"></c:out></td>
									<td class="commaSeparated"><c:out value="${deposit.depositAmount}"></c:out></td>
									<td><fmt:formatDate pattern="dd/MM/yyyy"
											value="${deposit.newMaturityDate}" /></td>
									<td><fmt:formatDate pattern="dd/MM/yyyy"
											value="${deposit.createdDate}" /></td>
									<td><c:out value="${deposit.depositType}"></c:out></td>
									<td><a
										href="showDistributionRecords?id=${deposit.depositId}&menuId=${menuId}"
										class="btn btn-primary"><spring:message
												code="label.showDetails" /></a></td>
								</tr>
							</c:forEach>
						</c:if>

					</tbody>

				</table>
				<div class="save-back" style="margin-top: -2%;">
					<p align="center" style="padding-top: 10px;">
						<a href="closedDeposits?menuId=${menuId}" class="btn btn-success"><spring:message
								code="label.back" /></a>
					</p>
				</div>
			</div>

		</div>