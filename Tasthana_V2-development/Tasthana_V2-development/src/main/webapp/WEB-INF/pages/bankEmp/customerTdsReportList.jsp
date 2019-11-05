<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="right-container" id="right-container">
        <div class="container-fluid">
 
<div class="fd-list">
			<div class="header_customer">	
				<h3><spring:message code="label.custTdsReport" /></h3>
			</div>
			
			<div class="fd-list-table">	
			
			
			<div class="col-md-6">

				<div id="linkedAccountDiv">

					<div class="form-group" id="accountTypeDiv">
						<label class="col-md-4 control-label"><spring:message
								code="label.name" />:</label>
						<div class="col-md-6">

							<input type="text" id="customerName" value="${cust}" class="form-control"  style="background: #fff; border: none;" readonly="true" />

						</div>

					</div>
					<div style="clear: both; height: 9px;"></div>
					<div class="form-group" id="accountTypeDiv">
						<label class="col-md-4 control-label"><spring:message
								code="label.totalTDSAmount" />:</label>
						<div class="col-md-6">

							<input type="text"  value = "${totalTDS}" id="totalTdsAmount"  style="background: #fff; border: none;" class="form-control" readonly="true" />

						</div>

					</div>
					
							
				</div>
			</div>
			
			
			
			
			
			
			
			<span class="counter pull-right"></span>
				<div class="col-sm-12 col-md-12 col-lg-12">
					<input type="text" id="kwd_search" value="" placeholder="Search Here..."  style="float:right;"/>
				</div>
			<table class="table data jqtable example" id="my-table">
				<thead>
					<tr>
					<th><spring:message code="label.fdID" /></th>
					<th><spring:message code="label.accountNo" /></th>				
					<th><spring:message code="label.calTDSAmt" /></th>
					<th><spring:message code="label.tdsAmt" /></th>
					<th><spring:message code="label.tdsDate" /></th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${! empty customerTdsList}">
						<c:forEach items="${customerTdsList}" var="custTds">
							<tr>
							<td><c:out value="${custTds.depositId}"></c:out></td>
							<td><c:out value="${custTds.depositAccountNo}"></c:out></td>
							<fmt:formatNumber value="${custTds.calculatedTDSAmount}" pattern="#.##" var="calculatedTDSAmount"/>
							<td class="commaSeparated"><c:out value="${calculatedTDSAmount}"></c:out></td>
							<fmt:formatNumber value="${custTds.contributedTDSAmount}" pattern="#.##" var="contributedTDSAmount"/>
							<td class="commaSeparated"><c:out value="${contributedTDSAmount}"></c:out></td>
							<td><fmt:formatDate pattern="dd/MM/yyyy" value="${custTds.tdsDate}" /></td>
							
							</tr>
						</c:forEach>
					</c:if>

				</tbody>
			</table> 
			</div>

</div>