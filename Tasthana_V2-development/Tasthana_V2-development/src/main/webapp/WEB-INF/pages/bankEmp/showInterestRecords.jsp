<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="right-container" id="right-container">
        <div class="container-fluid">
   
<div class="fd-list">
	<div class="header_customer">
		<h3>
			Interest List for deposit id: ${interestList[0].depositId}
		</h3>
	</div>

	<div class="fd-list-table">
	
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

							<input type="text"  value = "${depositAccountNumber}" id="depositAccountNumber"  style="background: #fff; border: none;" class="form-control" readonly="true" />

						</div>

					</div>
				
					<div style="clear: both; height: 9px;"></div>
					<div class="form-group" id="accountBalanceDiv">
						<label class="col-md-4 control-label"><spring:message
								code="label.totalInterest" />:</label>


						<div class="col-md-6">
							<input type="text" id="accountBalance" value="${interestAmount}" class="form-control commaSeparated"  style="background: #fff; border: none;" readonly="true" />
						</div>

					</div>			
				</div>
			</div>
			
	
	
	
	

		<span class="counter pull-right"></span>
		<div class="col-sm-12 col-md-12 col-lg-12">
			<input type="text" id="kwd_search" value=""
				placeholder="Search Here..." style="float: right;" />
		</div>
<!-- 		<table class="table data jqtable example" id="my-table"> -->
		<table class="table example" id="my-table">
			<thead>
				<tr>
					<th>Interest Rate %</th>
					<th>Interest Amount</th>
					<th>Interest Calculation Date</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${! empty interestList}">
					<c:forEach items="${interestList}" var="interest">
						<tr>
							<td><c:out value="${interest.interestRate}"></c:out></td>
							
							<fmt:formatNumber value="${interest.interestAmount}" pattern="#.##" var="interestAmount"/>
							<td class="commaSeparated"><c:out value="${interestAmount}"></c:out></td>
							<td><fmt:formatDate pattern="dd/MM/yyyy"
									value="${interest.interestDate}" /></td>
<%-- 							<td><c:if test="${interest.interestDeducted==1}"><c:out value="Yes"></c:out></c:if > --%>
<%-- 							<c:if test="${!(interest.interestDeducted==1)}"><c:out value="No"></c:out></c:if > --%>
							</td>
							
						</tr>
					</c:forEach>
				</c:if>
				
				<table align="center" class="f_deposit_btn">
						<tr>
							<td><a href="interestReport" class="btn btn-success"><spring:message
										code="label.back" /></a></td>
						</tr>

					</table>
			</tbody>
		</table>
	</div>

</div>