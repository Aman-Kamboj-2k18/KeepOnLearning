<%@page import="annona.domain.Distribution"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
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
				<h3>
					Holder Wise
					<spring:message code="label.reports" />
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
<!-- 						<div class="form-group" style="margin-top: 35px; margin-bottom: 72px;"> -->
<!-- 							<label class="col-md-6 control-label">Deposit Id:</label> -->
<!-- 							<div class="col-md-6"> -->
<%-- 								<input value="${distributionList[0].depositId}" --%>
<!-- 									class="form-control" readonly /> -->
<!-- 							</div> -->
<!-- 						</div> -->

					</div>
					<div class="col-sm-6 col-md-6 col-lg-6">
<!-- 						<div class="form-group"> -->
<!-- 							<label class="col-md-6 control-label">Customer Id:</label> -->
<!-- 							<div class="col-md-6"> -->
<%-- 								<input value="${distributionList[0].customerId}" --%>
<!-- 									class="form-control" readonly /> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 						<div class="form-group" style="margin-top: 35px;margin-bottom: 72px;"> -->
<!-- 							<label class="col-md-6 control-label">Contribution(%):</label> -->
<!-- 							<div class="col-md-6"> -->
<%-- 								<input value="${distributionList[0].contribution}" --%>
<!-- 									class="form-control" readonly /> -->
<!-- 							</div> -->
<!-- 						</div> -->

					</div>


					<div class="pull-right download-color"></div>

					<table class="table table-hover table-bordered data jqtable example" style="margin-top: 54px;">
						<thead>
							<tr>
								<th><spring:message code="label.date" /></th>

								<th>Action</th>
								<th>Amount</th>
								<th>Current Balance</th>

							</tr>
						</thead>
						<tbody>
							<c:if test="${! empty distributionList}">
								<c:forEach items="${distributionList}" var="distribution">
								<c:set  var="date_1" value="${distribution[3]}" />
								
									<tr>
<fmt:formatDate pattern="dd-MM-yyyy" value="${distribution[3]}" var="date_String"/>


										 <td>${date_String}</td> 
										<td><c:out value="${distribution[4]}"></c:out></td>
<%-- 										<fmt:formatnumber value="${distribution[5]}" pattern="#.##" var="actionamount"/> --%>
										<fmt:formatNumber value="${distribution[5]}" pattern="#.##" var="amount"/>									
										<td class="commaseparated"> ${amount}</td>
  										<fmt:formatNumber value="${distribution[6]}" pattern="#.##" var="currentbalance"/>
                                         <td class="commaseparated">${currentbalance}</td>


									</tr>
								</c:forEach>
							</c:if>

						</tbody>
					</table>
				</div>
				<div class="col-sm-12 col-md-12 col-lg-12">
				<%-- 	<c:if test="${baseURL[1] == 'bnkEmp'}">
						<c:set var="back" value="bankEmp" />
					</c:if>
					<c:if test="${baseURL[1] == 'users'}">
						<c:set var="back" value="user" />
					</c:if> --%>
					<table align="center" class="f_deposit_btn">
						<tr>
							<td><a href="fdListforHolderWiseResort" class="btn btn-success"><spring:message
										code="label.back" /></a></td>
						</tr>

					</table>
				</div>

			</div>
		</div>
	</div>
</div>
