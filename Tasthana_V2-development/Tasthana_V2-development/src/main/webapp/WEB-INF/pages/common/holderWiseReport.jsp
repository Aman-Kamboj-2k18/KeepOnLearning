<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- To fetch the request url -->
<c:set var="req"
	value="${requestScope['javax.servlet.forward.request_uri']}" />
<c:set var="baseURL" value="${fn:split(req,'/')}" />

<div class="right-container" id="right-container">
	<div class="container-fluid">

		<div class="Flexi_deposit">

			<div class="header_customer">
				<h3 align="center">
					
					<spring:message code="label.amountDistributionReport" />
				</h3>
			</div>


			<div class="fd-list-table">
				<div class="col-sm-12 col-md-12 col-lg-12">

					<div class="col-sm-6 col-md-6 col-lg-6">
						<div class="form-group">
							<label class="col-md-6 control-label">Account
								Number:</label>
							<div class="col-md-6">
								<input value="${depositAccountNo}" class="form-control" readonly />
							</div>
						</div>
						<div class="form-group" style="margin-top: 35px; margin-bottom: 72px;">
							<label class="col-md-6 control-label">Deposit Id:</label>
							<div class="col-md-6">
								<input value="${distributionList[0][0]}"
									class="form-control" readonly />
							</div>
						</div>

					</div>
					<c:if test="${! empty depositHolderList}">
					<c:forEach items="${depositHolderList}" var="depositHolder" varStatus="status">
		<div class="col-sm-12 col-md-12 col-lg-12">					
			<div >
				<h4 align="center" class="header_customer">
					<b>Deposit holder: <c:out value="${status.index+1}"/></b>
					
				</h4>
			</div>
					<div class="col-sm-6 col-md-6 col-lg-6">
						<div class="form-group">
							<label class="col-md-6 control-label">Customer Id:</label>
							<div class="col-md-6">
								<input value="${depositHolder.customerId}"
									class="form-control" readonly />
							</div>
							</div>
							<div class="form-group" style="margin-top: 35px;margin-bottom: 86px;">
							<label class="col-md-6 control-label">Customer Name:</label>
							<div class="col-md-6">
								<input value="${depositHolder.customerName}"
									class="form-control" readonly />
							</div>
							</div>
							</div>
						<div class="col-sm-6 col-md-6 col-lg-6">	
							<div class="form-group">
							<label class="col-md-6 control-label">Contribution(%):</label>
							<div class="col-md-6">
								<input value="${depositHolder.contribution}"
									class="form-control" style="width: 60px;" readonly />
							</div>
						</div>
					</div>
					
					
					<div class="pull-right download-color"></div>

					<table class="table table-hover table-bordered data jqtable example" style="margin-top: 54px;">
						<thead>
							<tr>
								<th><spring:message code="label.date" /></th>

								<th>Action</th>
								
								<th>Distributed Amount</th>
								<th>Current Balance</th>

							</tr>
						</thead>
						<tbody>
						<c:if test="${! empty distributionList}">
								<c:forEach items="${distributionList}" var="distribution">
								
									<tr>
										<td><fmt:formatDate
												value="${distribution[3]}"
												pattern="dd/MM/yyyy" /></td>
										<td><c:out value="${distribution[4]}"></c:out></td>
										<td><fmt:formatNumber type = "number"
												value="${distribution[5]}"
												pattern="#.##" /></td>
												
												<td><fmt:formatNumber type = "number"
												value="${distribution[6]}"
												pattern="#.##" /></td>
<%-- 										<fmt:formatNumber value="${distribution.actionAmount}" pattern="#.##" var="actionamount"/> --%>
<%-- 						             	<td class="commaSeparated"><c:out value="${actionamount}"></c:out></td> --%>
<%-- 										<fmt:formatNumber value="${distribution.distributedAmt}" pattern="#.##" var="distributedAmt"/> --%>
<%-- 						             	<td class="commaSeparated"><c:out value="${distributedAmt}"></c:out></td> --%>
<%-- 						             	<fmt:formatNumber value="${distribution.currentBalance}" pattern="#.##" var="currentBalance"/> --%>
<%-- 						             	<td class="commaSeparated"><c:out value="${currentBalance}"></c:out></td> --%>
                                       


									</tr>
									
								</c:forEach>
							</c:if>

						</tbody>
					</table>
					</div></c:forEach></c:if>
				</div>
				<div class="col-sm-12 col-md-12 col-lg-12">
			
					<table align="center" class="f_deposit_btn">
						<tr>
							<td><a href="distributionReportsCust?menuId=${menuId}" class="btn btn-success"><spring:message
										code="label.back" /></a></td>
						</tr>

					</table>
				</div>

			</div>
		</div>
	</div>
</div>
