<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="right-container" id="right-container">
	<div class="container-fluid">

		<div class="fd-list">
			<div class="header_customer">
				<h3 align="center">TDS List for deposit id:
					${tdsList[0].depositId}</h3>
			</div>

			<div class="col-md-6">

				<div id="linkedAccountDiv">

					<div class="form-group" id="accountTypeDiv">
						<label class="col-md-4 control-label"><spring:message
								code="label.name" />:</label>
						<div class="col-md-6">

							<input type="text" id="customerName" value="${customerName}"
								class="form-control" style="background: #fff; border: none;"
								readonly="true" />

						</div>

					</div>
					<div style="clear: both; height: 9px;"></div>
					<div class="form-group" id="accountTypeDiv">
						<label class="col-md-4 control-label"><spring:message
								code="label.fdAccountNum" />:</label>
						<div class="col-md-6">

							<input type="text" value="${depositAccountNumber}"
								id="depositAccountNumber"
								style="background: #fff; border: none;" class="form-control"
								readonly="true" />

						</div>

					</div>

					<div style="clear: both; height: 9px;"></div>
					<div class="form-group" id="accountBalanceDiv">
						<label class="col-md-4 control-label"  style="margin-top:2px;">Total TDS:</label>
                        <fmt:formatNumber value="${tDSAmountToDeduct}" pattern="#.##" var="tDSAmount" />
						<div class="col-md-6">
							<input type="text" id="accountBalance"  class="commaSeparated"
								value="${tDSAmount}" class="form-control"
								style="background: #fff; border: none;" readonly="true" />
						</div>

					</div>
				</div>
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
							<th>TDS Calculated Amount</th>
							<th>TDS Amount to Deduct</th>
<!-- 							<th>Rate %</th> -->
							<th>Calculation Date</th>
							<th>Report Submitted</th>
							<!-- 					<th>Report Submitted</th> -->
						</tr>
					</thead>
					<tbody>
						<c:if test="${! empty tdsList}">
							<c:forEach items="${tdsList}" var="tds">
								<tr>
									
									<fmt:formatNumber value="${tds.calculatedTDSAmount}"
										pattern="#.##" var="calculatedTDSAmount" />
									<td class="commaSeparated"><c:out value="${calculatedTDSAmount}"></c:out></td>
									
									<fmt:formatNumber value="${tds.tdsAmount}" pattern="#.##"
										var="tdsAmount" />
									<td class="commaSeparated"><c:out value="${tdsAmount}"></c:out></td>

<%-- 									<td><c:out value="${tds.tdsRate}"></c:out></td> --%>
									<%-- 							<td><c:out value="${tds.tdsCalcDate}"></c:out></td> --%>
									<td><fmt:formatDate pattern="dd/MM/yyyy"
											value="${tds.tdsCalcDate}" /></td>

									<td><c:if test="${tds.tdsDeducted==1}">
											<c:out value="Yes"></c:out>
										</c:if> <c:if test="${!(tds.tdsDeducted==1)}">
											<c:out value="No"></c:out>
										</c:if></td>
									<%-- 							<td><c:out value="${tds.tdsReportSubmitted}"></c:out></td> --%>

								</tr>
							</c:forEach>
						</c:if>

					</tbody>
				</table>
			</div>

		</div>