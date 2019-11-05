<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div class="right-container" id="right-container">
	<div class="container-fluid">

		<div class="fd-list">
			<div class="header_customer">
				<h3>
					<spring:message code="label.distributionList" />
				</h3>
			</div>

			<div class="col-md-6">

				<div id="linkedAccountDiv">

					<div class="form-group" id="accountTypeDiv">
						<label class="col-md-4 control-label"><spring:message
								code="label.name" />:</label>
						<div class="col-md-6">
                      	
							<input type="text" id="customerName" value="${customerName}" class="form-control"  style="background: #fff; border: none;" readonly="true" />

						</div>

					</div>
					<div style="clear: both; height: 9px;"></div>
					<div class="form-group" id="accountTypeDiv">
						<label class="col-md-4 control-label"><spring:message
								code="label.fdAccountNum" />:</label>
						<div class="col-md-6">

							<input type="text"  value = "${accountNumber}" id="accountNumber"  style="background: #fff; border: none;" class="form-control" readonly="true" />

						</div>

					</div>
					
					<div style="clear: both; height: 9px;"></div>
					<div class="form-group" id="accountBalanceDiv">
						<label class="col-md-4 control-label"><spring:message
								code="label.accountBalance" />:</label>


						<div class="col-md-6">
						 <fmt:formatNumber value="${totalBalance}" pattern="#.##" var="totalBal" />
					
							<input type="text" id="accountBalance" value="${totalBal}" class="form-control"  style="background: #fff; border: none;" readonly="true" />
						</div>

					</div>			
				</div>
			</div>
			<div class="col-md-6">
				<div id="linkedAccountDiv">
					<div class="form-group" id="accountTypeDiv">
						<label class="col-md-4 control-label"><spring:message
								code="label.variableBalance" />:</label>
						<div class="col-md-6">
<fmt:formatNumber value="${variableBalance}" pattern="#.##" var="variableBal" />
					
							<input type="text" id="variableBalance"  value="${variableBal}" class="form-control"  style="background: #fff; border: none;" readonly="true" />

						</div>

					</div>
					
					<div style="clear: both; height: 9px;"></div>

					<div class="form-group" id="accountBalanceDiv">
						<label class="col-md-4 control-label"><spring:message
								code="label.fixedBalance" />:</label>


						<div class="col-md-6">
						<fmt:formatNumber value="${fixedBalance}" pattern="#.##" var="fixedBal" />
							<input type="text" id="fixedBalance" value="${fixedBal}" class="form-control"  style="background: #fff; border: none;" readonly="true" />
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

							<th><spring:message code="label.distributionDate" /></th>
							<th><spring:message code="label.action" /></th>
							<th><spring:message code="label.depositedAmt" /></th>
							<th><spring:message code="label.compoundFixedAmt" /></th>
							<th><spring:message code="label.compoundVariableAmt" /></th>
							<th><spring:message code="label.fixedInterest" /></th>
							<th><spring:message code="label.variableInterest" /></th>
							<th><spring:message code="label.balanceFixedInterest" /></th>
							<th><spring:message code="label.balanceVariableInterest" /></th>
							<th><spring:message code="label.totalBalance" /></th>
							<th><spring:message code="label.payOffAmt" /></th>

						</tr>
					</thead>
					<tbody>
						<c:if test="${! empty distributionList}">
						
							<c:forEach items="${distributionList}" var="distribution">
								<tr>

									<td><fmt:formatDate pattern="dd/MM/yyyy"
											value="${distribution.distributionDate}" /></td>
									<td><c:out value="${distribution.action}"></c:out></td>
									
									<fmt:formatNumber value="${distribution.depositedAmt}" pattern="#.##" var="depositAmount"/>
									<td class="commaSeparated"><c:out value="${depositAmount}"></c:out></td>
									
									<fmt:formatNumber value="${distribution.compoundFixedAmt}" pattern="#.##" var="compoundFixedAmt"/>
									<td  class="commaSeparated"> <c:out value="${compoundFixedAmt}"></c:out></td>
									
									<fmt:formatNumber value="${distribution.compoundVariableAmt}" pattern="#.##" var="compoundVariableAmt"/>
									<td  class="commaSeparated"><c:out value="${compoundVariableAmt}"></c:out></td>
									
									<fmt:formatNumber value="${distribution.fixedInterest}" pattern="#.##" var="fixedInterest"/>
									
									<td  class="commaSeparated">
									<c:choose>
										<c:when test="${fn:contains(distribution.action, 'Interest')}">			
<%-- 										<c:when test="${distribution.action=='Interest'}"> --%>
                                        <c:out value="${fixedInterest}"></c:out>
                                        </c:when>
										<c:otherwise>
											<c:out value="0.0"></c:out>
										</c:otherwise>
									</c:choose>
									</td>
<%-- 									<fmt:formatNumber value="${distribution.fixedInterest}" pattern="#.##" var="fixedInterest"/> --%>
<%-- 									<td><c:out value="${fixedInterest}"></c:out></td> --%>
									<td class="commaSeparated"> 
									<fmt:formatNumber value="${distribution.variableInterest}" pattern="#.##" var="variableInterest"/>
									<c:choose>	
									<c:when test="${fn:contains(distribution.action, 'Interest')}">											
                                        <c:out value="${variableInterest}"></c:out>
                                        </c:when>
										<c:otherwise>
											<c:out value="0.0"></c:out>
										</c:otherwise>
									</c:choose>
									</td>
									
									<fmt:formatNumber value="${distribution.balanceFixedInterest}" pattern="#.##" var="fixedIntBalance"/>
									<td class="commaSeparated"><c:out value="${fixedIntBalance}"></c:out></td>
									
									<fmt:formatNumber value="${distribution.balanceVariableInterest}" pattern="#.##" var="variableIntBalance"/>
									<td class="commaSeparated"><c:out value="${variableIntBalance}"></c:out></td>
									
									<fmt:formatNumber value="${distribution.totalBalance}" pattern="#.##" var="totalBalance"/>
									<td class="commaSeparated"><c:out value="${totalBalance}"></c:out></td>
									
									<fmt:formatNumber value="${distribution.payOffAmt}" pattern="#.##" var="payOffAmt"/>
									<td class="commaSeparated"><c:out value="${payOffAmt}"></c:out></td> 



								</tr>
							</c:forEach>
						</c:if>
						<table align="center" class="f_deposit_btn">
						<tr>
							<td><a href="depositLists" class="btn btn-success"><spring:message
										code="label.back" /></a></td>
						</tr>

					</table>
					</tbody>
				</table>
			</div>

		</div>