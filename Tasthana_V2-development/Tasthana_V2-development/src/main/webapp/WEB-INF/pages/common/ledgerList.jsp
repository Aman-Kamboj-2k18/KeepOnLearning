<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="right-container" id="right-container">
	<div class="container-fluid">

		<div class="Success_msg">
			<div class="successMsg"
				style="text-align: center; color: red; font-size: 18px;">
				<h4>${error}</h4>

			</div>
		</div>


		<div class="Flexi_deposit">

			<!-- <div class="header_customer" style="margin-bottom: 30px;">
				<h3>
					Ledger List
				</h3>
			</div> -->
			
			<div class="Success_msg"></div>
			<div class="flexi_table">
				<form:form class="form-horizontal" action="journalList"  id="jounalForm" method="GET"
					name="ledgerReport" commandName="ledgerReportForm">
		
		 <form:hidden path="fromDate"/>
         <form:hidden path="toDate"/>

		
	        <div class="header_customer">	
				<h3><spring:message code="label.ledgerList"/></h3>
				
			</div>
			
			
	         <div class="successMsg"
				style="text-align: center; color: red; font-size: 18px;">
				<h4>${ledgerListError}</h4>

			</div>
	
		
		  <c:if test="${!empty ledgerList}">
		  
		 
			<div class="fd-list-table">	
			
			<span class="counter pull-right"></span>
			
			<table class="table data jqtable example" id="my-table">
				<thead>
				<tr>
					<th colspan="3" style="text-align: -webkit-center; border-top: 1px solid #ccc;">Debit</th>
					<th style="border-top: 1px solid #ccc; border-right: 1px solid #ccc;"></th>
					<th colspan="3" style="text-align: -webkit-center; border-top: 1px solid #ccc;">Credit</th>
				</tr>
				<tr>
					
					<th width="13%"><spring:message code="label.ledgerDateDebit" /></th>
					<th width="20%"><spring:message code="label.particularsDebit" /></th>
					<th width="15%"><spring:message code="label.debitAmount"/></th>
					<th width="4%" style="border-right: 1px solid #ccc;"></th>
					<th width="13%"><spring:message code="label.ledgerDateCredit" /></th>
					<th width="20%"><spring:message code="label.particularsCredit" /></th>
				    <th width="15%"><spring:message code="label.creditAmount" /></th>
					
				</tr>
			</thead>
				<tbody>
				<c:if test="${! empty ledgerList}">
					<c:forEach items="${ledgerList}" var="ledger">
						<tr>
					    	<td><fmt:formatDate pattern="dd/MM/yyyy"
									value="${ledger.ledgerDateDebit}" />
							</td>
							<td><c:out value="${ledger.particularsDebit}"></c:out></td>
							<fmt:formatNumber value="${ledger.debitAmount}" pattern="#.##" var="debitAmount"/>
							<c:choose>
							<c:when test="${! empty debitAmount}">
								<td class="commaSeparated">${debitAmount}</td>
							</c:when>
							<c:otherwise><td></td></c:otherwise>
							</c:choose>
                            <td style="border-right: 1px solid #ccc;"></td>
							<td><fmt:formatDate pattern="dd/MM/yyyy"
									value="${ledger.ledgerDateCredit}" />
							</td>
							<td><c:out value="${ledger.particularsCredit}"></c:out></td>
							<fmt:formatNumber value="${ledger.creditAmount}" pattern="#.##" var="creditAmount"/>
							<c:choose>
							<c:when test="${! empty creditAmount}">
								<td class="commaSeparated">${creditAmount}</td>
							</c:when>
							<c:otherwise><td></td></c:otherwise>
							</c:choose>
							
							
						</tr>
					</c:forEach>
				</c:if>	
			</div>
               
		</tbody>
		</table> 
		</div>

     </c:if> 



							 <div class="col-md-12" style="padding-bottom: 25px;text-align: -webkit-center;"> <a href="searchJournal" class="btn btn-info"><spring:message
									code="label.back" /></a></div>
						</div>
					</div>
			
				</form:form>
			</div>

		</div>
		
		<script>
		
		function viewLedger(){
			$("#jounalForm").attr("action", "viewLedger");
			$("#jounalForm").submit();
		}
		
		function searchJournal(){
			
			var fdAccountNo = document.getElementById('fdAccountNo').value;
			var fromDate = document.getElementById('fromDate').value;
			var toDate = document.getElementById('toDate').value;
			
		}
		</script>
	