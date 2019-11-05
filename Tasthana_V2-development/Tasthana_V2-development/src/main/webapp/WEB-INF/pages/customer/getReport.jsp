<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- To fetch the request url -->
<c:set var="req" value="${requestScope['javax.servlet.forward.request_uri']}"/>      
<c:set var="baseURL" value="${fn:split(req,'/')}" />

<div class="fd-list">
			<div class="header_customer">	
				<h3 align="center"><spring:message code="label.reports"/></h3>
			</div>
			
			<div class="fd-list-table">	
			<div class="col-sm-12 col-md-12 col-lg-12">
		   <div class="pull-right download-color">			 
			<%-- <a href="#"	onClick="$('#my-table').tableExport({type:'excel',escape:'false'});"><spring:message code="label.download"/></a>			 
			<a href="#"	onClick="$('#my-table').tableExport({type:'pdf',pdfFontSize:7,escape:'false'});"><spring:message code="label.print"/></a> --%>
		</div>
			<!-- <span class="counter pull-right"></span>
				<div class="col-sm-12 col-md-12 col-lg-12">
					<input type="text" id="kwd_search" value="" placeholder="Search Here..."  style="float:right;"/>
				</div> -->
			
			<table class="table data jqtable">
				<thead>
					<tr>
						<th><spring:message code="label.date"/></th>
						<th><spring:message code="label.typeOfDeposit"/></th>
						<th><spring:message code="label.accountBalance"/></th>
						<th><spring:message code="label.fdDeductDate"/></th>
						<th><spring:message code="label.fdAmount"/></th>
						<th>Fixed Amount</th>
						<th>Variable Amount</th>
						<th><spring:message code="label.fdTenureDa"/></th>
						<th><spring:message code="label.payOffTenure"/></th>
						<th><spring:message code="label.debit"/></th>
						<th><spring:message code="label.credit"/></th>
						<th>Rate Of Interest(%)</th>
						<th><spring:message code="label.fdStatus"/></th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${! empty transaction}">
						<c:forEach items="${transaction}" var="transaction">
							<tr>
								<fmt:formatDate var='date' value="${transaction.accountDate}"  pattern="dd/MM/yyyy"/>
								<td><c:out value="${date}"></c:out>
								<td><c:out value="${transaction.depositType}" ></c:out></td>
								<td><c:out value="${transaction.accountBalance}"></c:out></td>
								<fmt:formatDate var='date1' value="${transaction.fdDeductDate}"  pattern="dd/MM/yyyy"/>
								<td><c:out value="${date1}"></c:out>
								<td><c:out value="${transaction.fdAmount}"></c:out>
								<td><c:out value="${transaction.fdFixed}"></c:out></td>
								<td><c:out value="${transaction.fdChangeable}"></c:out></td>
								<fmt:formatDate var='date2' value="${transaction.fdTenureDate}"  pattern="dd/MM/yyyy"/>
								<td><c:out value="${date2}"></c:out>
								<td><c:out value="${transaction.fdInterest}"></c:out></td>
								<td><c:out value="${transaction.debited}"></c:out></td>
								<td><c:out value="${transaction.credited}"></c:out></td>
								<td><c:out value="${transaction.rateOfInterest}"></c:out></td>
								<td><c:out value="${transaction.transactionStatus}"></c:out></td>
								
							</tr>
						</c:forEach>
					</c:if>

				</tbody>
			</table> 
			</div>
			<div class="col-sm-12 col-md-12 col-lg-12">
			<c:if test="${baseURL[1] == 'bnkEmp'}"><c:set var="back" value="bankEmp"/></c:if>
					<c:if test="${baseURL[1] == 'users'}"><c:set var="back" value="user"/></c:if>
			<table align="center" class="f_deposit_btn">
				<tr>
					<td><a href="${back}" class="btn btn-success"><spring:message code="label.back"/></a></td>
				</tr>

			</table>
		</div>

</div>
</div>