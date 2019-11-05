<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="right-container" id="right-container">
	<div class="container-fluid">

		<div class="fd-list">
			<div class="header_customer">
				<h3 align="center">
					UnSuccessful PayOff List
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

							<th>Deposit Id</th>
							<th>Deposit Holder Id</th>
							<th>Date</th>
							<th>Available Amount</th>
							<th>PayOff Amount</th>

						</tr>
					</thead>
					<tbody>
						<c:if test="${! empty unSuccessfulPayOffDetailsList}">
							<c:forEach items="${unSuccessfulPayOffDetailsList}"
								var="unSuccessfulPayOffDetailsL">
								<tr>
									<td><c:out value="${unSuccessfulPayOffDetailsL.depositid}"></c:out></td>
									<td><c:out
											value="${unSuccessfulPayOffDetailsL.depositHolderId}"></c:out></td>

									<td><fmt:formatDate pattern="dd/MM/yyyy"
											value="${unSuccessfulPayOffDetailsL.unSuccessfulPayoffDetailsDate}" /></td>

									<td><c:out
											value="${unSuccessfulPayOffDetailsL.availableVariableInterestAmountForPayOff}"></c:out></td>
									<td><c:out
											value="${unSuccessfulPayOffDetailsL.payOffAmount}"></c:out></td>

								</tr>
							</c:forEach>
						</c:if>

					</tbody>

				</table>
				<div class="save-back" style="margin-top: -2%;">
					<p align="center" style="padding-top: 10px;">
						<a href="bankEmp" class="btn btn-success"><spring:message
								code="label.back" /></a>
					</p>
				</div>
			</div>

		</div>