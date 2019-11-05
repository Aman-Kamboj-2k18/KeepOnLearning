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

		<c:if test="${!empty ledgerSavingsAccountError ||  !empty ledgerSavingsAccountError}">
	        <div class="header_customer">	
				<h3><spring:message code="label.ledgerSavingAccountsList"/></h3>
				
			</div>
		
		   <div class="successMsg"
				style="text-align: center; color: red; font-size: 18px;">
				<h4>${ledgerSavingsAccountError}</h4>

			</div>
			<div class="fd-list-table">	
			
			<span class="counter pull-right"></span>
			
			<table class="table data jqtable example" id="my-table">
				<thead>
				<tr>
					<th colspan="4" style="text-align: -webkit-center; border-top: 1px solid #ccc;">Debit</th>
					<th style="border-top: 1px solid #ccc; border-right: 1px solid #ccc;"></th>
					<th colspan="4" style="text-align: -webkit-center; border-top: 1px solid #ccc;">Credit</th>
				</tr>
				<tr>
					
					<th width="11%"><spring:message code="label.ledgerDateDebit" /></th>
					<th width="19%"><spring:message code="label.particularsDebit" /></th>
					<th width="13%"><spring:message code="label.debitAmount"/></th>
					<th width="4%" style="border-right: 1px solid #ccc;"></th>
					<th width="11%"><spring:message code="label.ledgerDateCredit" /></th>
					<th width="19%"><spring:message code="label.particularsCredit" /></th>
				    <th width="13%"><spring:message code="label.creditAmount" /></th>
					<th width="20%">Deposit Account Number</th>
				   
					
				</tr>
			</thead>
				<tbody>
				<c:if test="${! empty ledgerSavingsAccount}">
					<c:forEach items="${ledgerSavingsAccount}" var="ledgerSaving">
						<tr>
					    	<td><fmt:formatDate pattern="dd/MM/yyyy"
									value="${ledgerSaving.ledgerDateDebit}" />
							</td>
							<td><c:out value="${ledgerSaving.particularsDebit}"></c:out></td>
							<fmt:formatNumber value="${ledgerSaving.debitAmount}" pattern="#.##" var="debitAmount"/>
							<td class="commaSeparated">${debitAmount}</td>
                            <td style="border-right: 1px solid #ccc;"></td>
							<td><fmt:formatDate pattern="dd/MM/yyyy"
									value="${ledgerSaving.ledgerDateCredit}" />
							</td>
							<td><c:out value="${ledgerSaving.particularsCredit}"></c:out></td>
							<fmt:formatNumber value="${ledgerSaving.creditAmount}" pattern="#.##" var="creditAmount"/>
							<td class="commaSeparated">${creditAmount}</td>
							<td><b style="color:#358cce"><a href="viewDeposit?id=${ledgerSaving.depositId}">${ledgerSaving.accountNumber}</a></b></td>
						</tr>
					</c:forEach>
				</c:if>

				

			
		</div>
               
			</tbody>
			</table> 
			</div>

     </c:if> 

<%-- 	<c:if test="${zeroIndex=='ledgerInterestAccount' || firstIndex =='ledgerInterestAccount' || secondIndex =='ledgerInterestAccount'|| thirdIndex =='ledgerInterestAccount' || fourthIndex == 'ledgerInterestAccount' || fifthIndex == 'ledgerInterestAccount' || sixthIndex == 'ledgerInterestAccount' || seventhIndex == 'ledgerInterestAccount'}"> --%>

		<c:if test="${! empty ledgerInterestAccount || ! empty ledgerInterestAccountError}">
			<div class="header_customer">	
				<h3><spring:message code="label.ledgerInterestAccountsList"/></h3>
			</div>
			<div class="fd-list-table">	
			<div class="successMsg"
				style="text-align: center; color: red; font-size: 18px;">
				<h4>${ledgerInterestAccountError}</h4>

			</div>
			<span class="counter pull-right"></span>
			
			<table class="table data jqtable example" id="my-table">
				<thead>
				<tr>
					<th colspan="3" style="text-align: -webkit-center; border-top: 1px solid #ccc;">Debit</th>
					<th style="border-top: 1px solid #ccc; border-right: 1px solid #ccc;"></th>
					<th colspan="3" style="text-align: -webkit-center; border-top: 1px solid #ccc;">Credit</th>
				</tr>
				<tr>
					<th width="11%"><spring:message code="label.ledgerDateDebit" /></th>
					<th width="19%"><spring:message code="label.particularsDebit" /></th>
					<th width="13%"><spring:message code="label.debitAmount"/></th>
					<th width="4%" style="border-right: 1px solid #ccc;"></th>
					<th width="11%"><spring:message code="label.ledgerDateCredit" /></th>
					<th width="19%"><spring:message code="label.particularsCredit" /></th>
				    <th width="13%"><spring:message code="label.creditAmount" /></th>
					<th width="20%">Deposit Account Number</th>
				</tr>
			</thead>
				<tbody>
				<c:if test="${! empty ledgerInterestAccount}">
					<c:forEach items="${ledgerInterestAccount}" var="ledgerInterest">
						<tr>
					    	<td><fmt:formatDate pattern="dd/MM/yyyy"
									value="${ledgerInterest.ledgerDateDebit}" />
							</td>
							<td><c:out value="${ledgerInterest.particularsDebit}"></c:out></td>
							<fmt:formatNumber value="${ledgerInterest.debitAmount}" pattern="#.##" var="debitAmount"/>
							<td class="commaSeparated">${debitAmount}</td>
							<td style="border-right: 1px solid #ccc;"></td>
							<td><fmt:formatDate pattern="dd/MM/yyyy"
									value="${ledgerInterest.ledgerDateCredit}" />
							</td>
							<td><c:out value="${ledgerInterest.particularsCredit}"></c:out></td>
							<fmt:formatNumber value="${ledgerInterest.creditAmount}" pattern="#.##" var="creditAmount"/>
							<td class="commaSeparated">${creditAmount}</td>
							<td><b style="color:#358cce"><a href="viewDeposit?id=${ledgerInterest.depositId}">${ledgerInterest.accountNumber}</a></b></td>
						</tr>
					</c:forEach>
				</c:if>

			</tbody>
			</table> 
			</div>

     </c:if>

<%-- 		<c:if test="${zeroIndex=='ledgerdepositAccount' || firstIndex =='ledgerdepositAccount' || secondIndex =='ledgerdepositAccount'|| thirdIndex =='ledgerdepositAccount' || fourthIndex == 'ledgerdepositAccount' || fifthIndex == 'ledgerdepositAccount' || sixthIndex == 'ledgerdepositAccount' || seventhIndex == 'ledgerdepositAccount'}"> --%>

		<c:if test="${! empty ledgerDepositAccount  || ! empty ledgerDepositAccountError}">
			<div class="header_customer">	
				<h3><spring:message code="label.ledgerDepositAccountsList"/></h3>
			</div>
			<div class="fd-list-table">	
			<div class="successMsg"
				style="text-align: center; color: red; font-size: 18px;">
				<h4>${ledgerDepositAccountError}</h4>

			</div>
			<span class="counter pull-right"></span>
				
			<table class="table data jqtable example" id="my-table">
				<thead>
				<tr>
					<th colspan="3" style="text-align: -webkit-center; border-top: 1px solid #ccc;">Debit</th>
					<th style="border-top: 1px solid #ccc; border-right: 1px solid #ccc;"></th>
					<th colspan="3" style="text-align: -webkit-center; border-top: 1px solid #ccc;">Credit</th>
				</tr>
				<tr>
					<th width="11%"><spring:message code="label.ledgerDateDebit" /></th>
					<th width="19%"><spring:message code="label.particularsDebit" /></th>
					<th width="13%"><spring:message code="label.debitAmount"/></th>
					<th width="4%" style="border-right: 1px solid #ccc;"></th>
					<th width="11%"><spring:message code="label.ledgerDateCredit" /></th>
					<th width="19%"><spring:message code="label.particularsCredit" /></th>
				    <th width="13%"><spring:message code="label.creditAmount" /></th>
					<th width="20%">Deposit Account Number</th>
				</tr>
			</thead>
				<tbody>
				<c:if test="${! empty LedgerReportForm}">
					<c:forEach items="${LedgerReportForm}" var="ledgerDeposit">
						<tr>
					    	<td><fmt:formatDate pattern="dd/MM/yyyy"
									value="${ledgerDeposit.ledgerDateDebit}" />
							</td>
							<td><c:out value="${ledgerDeposit.particularsDebit}"></c:out></td>
							<fmt:formatNumber value="${ledgerDeposit.debitAmount}" pattern="#.##" var="debitAmount"/>
							<td class="commaSeparated">${debitAmount}</td>
							<td style="border-right: 1px solid #ccc;"></td>
							<td><fmt:formatDate pattern="dd/MM/yyyy"
									value="${ledgerDeposit.ledgerDateCredit}" />
							</td>
							<td><c:out value="${ledgerDeposit.particularsCredit}"></c:out></td>
							<fmt:formatNumber value="${ledgerDeposit.creditAmount}" pattern="#.##" var="creditAmount"/>
							<td class="commaSeparated">${creditAmount}</td>
							
							<td><b style="color:#358cce"><a href="viewDeposit?id=${ledgerDeposit.depositId}">${ledgerDeposit.accountNumber}</a></b></td>
							
						</tr>
					</c:forEach>
				</c:if>

			</tbody>
			</table> 
			</div>

      </c:if>
	
<%-- 			<c:if test="${zeroIndex=='ledgerDDAccount' || firstIndex =='ledgerDDAccount' || secondIndex =='ledgerDDAccount'|| thirdIndex =='ledgerDDAccount' || fourthIndex == 'ledgerDDAccount' || fifthIndex == 'ledgerDDAccount' || sixthIndex == 'ledgerDDAccount' || seventhIndex == 'ledgerDDAccount'}"> --%>
		<c:if test="${! empty ledgerDDAccount  || ! empty ledgerDDAccountError}">
			
			<div class="header_customer">	
				<h3><spring:message code="label.ledgerDDAccountsList"/></h3>
			</div>
			<div class="fd-list-table">	
			<div class="successMsg"
				style="text-align: center; color: red; font-size: 18px;">
				<h4>${ledgerDDAccountError}</h4>

			</div>
			<span class="counter pull-right"></span>
			
			<table class="table data jqtable example" id="my-table">
				<thead>
				<tr>
					<th colspan="3" style="text-align: -webkit-center; border-top: 1px solid #ccc;">Debit</th>
					<th style="border-top: 1px solid #ccc; border-right: 1px solid #ccc;"></th>
					<th colspan="3" style="text-align: -webkit-center; border-top: 1px solid #ccc;">Credit</th>
				</tr>
				
				<tr>
					<th width="11%"><spring:message code="label.ledgerDateDebit" /></th>
					<th width="19%"><spring:message code="label.particularsDebit" /></th>
					<th width="13%"><spring:message code="label.debitAmount"/></th>
					<th width="4%" style="border-right: 1px solid #ccc;"></th>
					<th width="11%"><spring:message code="label.ledgerDateCredit" /></th>
					<th width="19%"><spring:message code="label.particularsCredit" /></th>
				    <th width="13%"><spring:message code="label.creditAmount" /></th>
					<th width="20%">Deposit Account Number</th>
					
				</tr>
			</thead>
				<tbody>
				<c:if test="${! empty ledgerDDAccount}">
					<c:forEach items="${ledgerDDAccount}" var="ledgerDD">
						<tr>
					    	<td><fmt:formatDate pattern="dd/MM/yyyy"
									value="${ledgerDD.ledgerDateDebit}" />
							</td>
							<td><c:out value="${ledgerDD.particularsDebit}"></c:out></td>
							<fmt:formatNumber value="${ledgerDD.debitAmount}" pattern="#.##" var="debitAmount"/>
							<td class="commaSeparated">${debitAmount}</td>
							<td style="border-right: 1px solid #ccc;"></td>
							<td><fmt:formatDate pattern="dd/MM/yyyy"
									value="${ledgerDD.ledgerDateCredit}" />
							</td>
							<td><c:out value="${ledgerDD.particularsCredit}"></c:out></td>
							<fmt:formatNumber value="${ledgerDD.creditAmount}" pattern="#.##" var="creditAmount"/>
							<td class="commaSeparated">${creditAmount}</td>
							<td><b style="color:#358cce"><a href="viewDeposit?id=${ledgerDD.depositId}">${ledgerDD.accountNumber}</a></b></td>
							<%--  <c:out value="${ledgerDD.accountNumber}"></c:out> --%>
						</tr>
					</c:forEach>
				</c:if>

			</tbody>
			</table> 
			</div>

        </c:if>

<%-- 	<c:if test="${zeroIndex=='ledgerCurrentAccount' || firstIndex =='ledgerCurrentAccount' || secondIndex =='ledgerCurrentAccount'|| thirdIndex =='ledgerCurrentAccount' || fourthIndex == 'ledgerCurrentAccount' || fifthIndex == 'ledgerCurrentAccount' || sixthIndex == 'ledgerCurrentAccount' || seventhIndex == 'ledgerCurrentAccount'}"> --%>

		<c:if test="${! empty ledgerCurrentAccount   || ! empty ledgerCurrentAccountError}">			
			<div class="header_customer">	
				<h3><spring:message code="label.ledgerCurrentAccountsList"/></h3>
			</div>
			<div class="fd-list-table">	
			<div class="successMsg"
				style="text-align: center; color: red; font-size: 18px;">
				<h4>${ledgerCurrentAccountError}</h4>

			</div>
			<span class="counter pull-right"></span>
			
			<table class="table data jqtable example" id="my-table">
				<thead>
				<tr>
					<th colspan="3" style="text-align: -webkit-center; border-top: 1px solid #ccc;">Debit</th>
					<th style="border-top: 1px solid #ccc; border-right: 1px solid #ccc;"></th>
					<th colspan="3" style="text-align: -webkit-center; border-top: 1px solid #ccc;">Credit</th>
				</tr>
				<tr>
					<th width="11%"><spring:message code="label.ledgerDateDebit" /></th>
					<th width="19%"><spring:message code="label.particularsDebit" /></th>
					<th width="13%"><spring:message code="label.debitAmount"/></th>
					<th width="4%" style="border-right: 1px solid #ccc;"></th>
					<th width="11%"><spring:message code="label.ledgerDateCredit" /></th>
					<th width="19%"><spring:message code="label.particularsCredit" /></th>
				    <th width="13%"><spring:message code="label.creditAmount" /></th>
					<th width="20%">Deposit Account Number</th>
					
				</tr>
			</thead>
				<tbody>
				<c:if test="${! empty ledgerCurrentAccount}">
					<c:forEach items="${ledgerCurrentAccount}" var="ledgerCurrent">
						<tr>
					    	<td><fmt:formatDate pattern="dd/MM/yyyy"
									value="${ledgerCurrent.ledgerDateDebit}" />
							</td>
							<td><c:out value="${ledgerCurrent.particularsDebit}"></c:out></td>
							<fmt:formatNumber value="${ledgerCurrent.debitAmount}" pattern="#.##" var="debitAmount"/>
							<td class="commaSeparated">${debitAmount}</td>
							<td style="border-right: 1px solid #ccc;"></td>
							<td><fmt:formatDate pattern="dd/MM/yyyy"
									value="${ledgerCurrent.ledgerDateCredit}" />
							</td>
							<td><c:out value="${ledgerCurrent.particularsCredit}"></c:out></td>
							<fmt:formatNumber value="${ledgerCurrent.creditAmount}" pattern="#.##" var="creditAmount"/>
							<td class="commaSeparated">${creditAmount}</td>
							<td><b style="color:#358cce"><a href="viewDeposit?id=${ledgerCurrent.depositId}">${ledgerCurrent.accountNumber}</a></b></td>
						</tr>
					</c:forEach>
				</c:if>

			</tbody>
			</table>
				 
			</div>

     </c:if> 

<%-- 		<c:if test="${zeroIndex=='ledgerChequeAccount' || firstIndex =='ledgerChequeAccount' || secondIndex =='ledgerChequeAccount'|| thirdIndex =='ledgerChequeAccount' || fourthIndex == 'ledgerChequeAccount' || fifthIndex == 'ledgerChequeAccount' || sixthIndex == 'ledgerChequeAccount' || seventhIndex == 'ledgerChequeAccount'}"> --%>

		<c:if test="${! empty ledgerChequeAccount  || ! empty ledgerChequeAccountError}">	
			<div class="header_customer">	
				<h3><spring:message code="label.ledgerChequeAccountsList"/></h3>
			</div>
			<div class="fd-list-table">	
			<div class="successMsg"
				style="text-align: center; color: red; font-size: 18px;">
				<h4>${ledgerChequeAccountError}</h4>

			</div>
			<span class="counter pull-right"></span>
			
			<table class="table data jqtable example" id="my-table">
				<thead>
				<tr>
					<th colspan="3" style="text-align: -webkit-center; border-top: 1px solid #ccc;">Debit</th>
					<th style="border-top: 1px solid #ccc; border-right: 1px solid #ccc;"></th>
					<th colspan="3" style="text-align: -webkit-center; border-top: 1px solid #ccc;">Credit</th>
				</tr>
				<tr>
					<th width="11%"><spring:message code="label.ledgerDateDebit" /></th>
					<th width="19%"><spring:message code="label.particularsDebit" /></th>
					<th width="13%"><spring:message code="label.debitAmount"/></th>
					<th width="4%" style="border-right: 1px solid #ccc;"></th>
					<th width="11%"><spring:message code="label.ledgerDateCredit" /></th>
					<th width="19%"><spring:message code="label.particularsCredit" /></th>
				    <th width="13%"><spring:message code="label.creditAmount" /></th>
					<th width="20%">Deposit Account Number</th>
					
				</tr>
			</thead>
				<tbody>
				<c:if test="${! empty ledgerChequeAccount}">
					<c:forEach items="${ledgerChequeAccount}" var="ledgerCheque">
						<tr>
					    	<td><fmt:formatDate pattern="dd/MM/yyyy"
									value="${ledgerCheque.ledgerDateDebit}" />
							</td>
							<td><c:out value="${ledgerCheque.particularsDebit}"></c:out></td>
							<fmt:formatNumber value="${ledgerCheque.debitAmount}" pattern="#.##" var="debitAmount"/>
							<td class="commaSeparated">${debitAmount}</td>
							<td style="border-right: 1px solid #ccc;"></td>
							<td><fmt:formatDate pattern="dd/MM/yyyy"
									value="${ledgerCheque.ledgerDateCredit}" />
							</td>
							<td><c:out value="${ledgerCheque.particularsCredit}"></c:out></td>
							<fmt:formatNumber value="${ledgerCheque.creditAmount}" pattern="#.##" var="creditAmount"/>
							<td class="commaSeparated">${creditAmount}</td>
							<td><b style="color:#358cce"><a href="viewDeposit?id=${ledgerCheque.depositId}">${ledgerCheque.accountNumber}</a></b></td>
						</tr>
					</c:forEach>
				</c:if>

			</tbody>
			</table> 
			</div>

      </c:if> 

<%-- 	  <c:if test="${zeroIndex=='ledgerChargesAccount' || firstIndex =='ledgerChargesAccount' || secondIndex =='ledgerChargesAccount'|| thirdIndex =='ledgerChargesAccount' || fourthIndex == 'ledgerChargesAccount' || fifthIndex == 'ledgerChargesAccount' || sixthIndex == 'ledgerChargesAccount' || seventhIndex == 'ledgerChargesAccount'}"> --%>

		<c:if test="${! empty ledgerChargesAccount || ! empty ledgerChargesAccountError}">	
			<div class="header_customer">	
				<h3><spring:message code="label.ledgerChargesAccountsList"/></h3>
			</div>
			<div class="fd-list-table">	
				<div class="successMsg"
				style="text-align: center; color: red; font-size: 18px;">
				<h4>${ledgerChargesAccountError}</h4>

			</div>
			<span class="counter pull-right"></span>
				
			<table class="table data jqtable example" id="my-table">
				<thead>
				<tr>
					<th colspan="3" style="text-align: -webkit-center; border-top: 1px solid #ccc;">Debit</th>
					<th style="border-top: 1px solid #ccc; border-right: 1px solid #ccc;"></th>
					<th colspan="3" style="text-align: -webkit-center; border-top: 1px solid #ccc;">Credit</th>
				</tr>
				<tr>
					<th width="11%"><spring:message code="label.ledgerDateDebit" /></th>
					<th width="19%"><spring:message code="label.particularsDebit" /></th>
					<th width="13%"><spring:message code="label.debitAmount"/></th>
					<th width="4%" style="border-right: 1px solid #ccc;"></th>
					<th width="11%"><spring:message code="label.ledgerDateCredit" /></th>
					<th width="19%"><spring:message code="label.particularsCredit" /></th>
				    <th width="13%"><spring:message code="label.creditAmount" /></th>
					<th width="20%">Deposit Account Number</th>
					
				</tr>
			</thead>
				<tbody>
				<c:if test="${! empty ledgerChargesAccount}">
					<c:forEach items="${ledgerChargesAccount}" var="ledgerCharges">
						<tr>
					    	<td><fmt:formatDate pattern="dd/MM/yyyy"
									value="${ledgerCharges.ledgerDateDebit}" />
							</td>
							<td><c:out value="${ledgerCharges.particularsDebit}"></c:out></td>
							<fmt:formatNumber value="${ledgerCharges.debitAmount}" pattern="#.##" var="debitAmount"/>
							<td class="commaSeparated">${debitAmount}</td>
							<td style="border-right: 1px solid #ccc;"></td>
							<td><fmt:formatDate pattern="dd/MM/yyyy"
									value="${ledgerCharges.ledgerDateCredit}" />
							</td>
							<td><c:out value="${ledgerCharges.particularsCredit}"></c:out></td>
							<fmt:formatNumber value="${ledgerCharges.creditAmount}" pattern="#.##" var="creditAmount"/>
							<td class="commaSeparated">${creditAmount}</td>
							<td><b style="color:#358cce"><a href="viewDeposit?id=${ledgerCharges.depositId}">${ledgerCharges.accountNumber}</a></b></td>
						</tr>
					</c:forEach>
				</c:if>

			</tbody>
			</table> 
			</div>

       </c:if>

<%-- 		  <c:if test="${zeroIndex=='ledgerCashAccount' || firstIndex =='ledgerCashAccount' || secondIndex =='ledgerCashAccount'|| thirdIndex =='ledgerCashAccount' || fourthIndex == 'ledgerCashAccount' || fifthIndex == 'ledgerCashAccount' || sixthIndex == 'ledgerCashAccount' || seventhIndex == 'ledgerCashAccount'}"> --%>

		<c:if test="${! empty ledgerCashAccount|| ! empty ledgerCashAccountError}">	
			<div class="header_customer">	
				<h3><spring:message code="label.ledgerCashAccountsList"/></h3>
			</div>
			
			<div class="fd-list-table">	
				<div class="successMsg"
				style="text-align: center; color: red; font-size: 18px;">
				<h4>${ledgerCashAccountError}</h4>

			</div>
			<span class="counter pull-right"></span>
				
			<table class="table data jqtable example" id="my-table">
				<thead>
				<tr>
					<th colspan="3" style="text-align: -webkit-center; border-top: 1px solid #ccc;">Debit</th>
					<th style="border-top: 1px solid #ccc; border-right: 1px solid #ccc;"></th>
					<th colspan="3" style="text-align: -webkit-center; border-top: 1px solid #ccc;">Credit</th>
				</tr>
				<tr>
					<th width="11%"><spring:message code="label.ledgerDateDebit" /></th>
					<th width="19%"><spring:message code="label.particularsDebit" /></th>
					<th width="13%"><spring:message code="label.debitAmount"/></th>
					<th width="4%" style="border-right: 1px solid #ccc;"></th>
					<th width="11%"><spring:message code="label.ledgerDateCredit" /></th>
					<th width="19%"><spring:message code="label.particularsCredit" /></th>
				    <th width="13%"><spring:message code="label.creditAmount" /></th>
					<th width="20%">Deposit Account Number</th>
				
					
				</tr>
			</thead>
				<tbody>
				<c:if test="${! empty ledgerCashAccount}">
					<c:forEach items="${ledgerCashAccount}" var="ledgerCash">
						<tr>
					    	<td><fmt:formatDate pattern="dd/MM/yyyy"
									value="${ledgerCash.ledgerDateDebit}" />
							</td>
							<td><c:out value="${ledgerCash.particularsDebit}"></c:out></td>
							<fmt:formatNumber value="${ledgerCash.debitAmount}" pattern="#.##" var="debitAmount"/>
							<td class="commaSeparated">${debitAmount}</td>
							<td style="border-right: 1px solid #ccc;"></td>
							<td><fmt:formatDate pattern="dd/MM/yyyy"
									value="${ledgerCash.ledgerDateCredit}" />
							</td>
							<td><c:out value="${ledgerCash.particularsCredit}"></c:out></td>
							<fmt:formatNumber value="${ledgerCash.creditAmount}" pattern="#.##" var="creditAmount"/>
							<td class="commaSeparated">${creditAmount}</td>
							<td><b style="color:#358cce"><a href="viewDeposit?id=${ledgerCash.depositId}">${ledgerCash.accountNumber}</a></b></td>
						</tr>
					</c:forEach>
				</c:if>

			</tbody>
			</table> 
			</div>

 </c:if> 
 
 	<c:if test="${! empty ledgerCommonAccount|| ! empty ledgerCommonAccountError}">	
			<div class="header_customer">	
				<h3>Ledger Common Account</h3>
			</div>
			
			<div class="fd-list-table">	
				<div class="successMsg"
				style="text-align: center; color: red; font-size: 18px;">
				<h4>${ledgerCommonAccountError}</h4>

			</div>
			<span class="counter pull-right"></span>
				
			<table class="table data jqtable example" id="my-table">
				<thead>
				<tr>
					<th colspan="3" style="text-align: -webkit-center; border-top: 1px solid #ccc;">Debit</th>
					<th style="border-top: 1px solid #ccc; border-right: 1px solid #ccc;"></th>
					<th colspan="5" style="text-align: -webkit-center; border-top: 1px solid #ccc;">Credit</th>
				</tr>
				<tr>
					<th width="11%"><spring:message code="label.ledgerDateDebit" /></th>
					<th width="19%"><spring:message code="label.particularsDebit" /></th>
					<th width="13%"><spring:message code="label.debitAmount"/></th>
					<th width="4%" style="border-right: 1px solid #ccc;"></th>
					<th width="11%"><spring:message code="label.ledgerDateCredit" /></th>
					<th width="19%"><spring:message code="label.particularsCredit" /></th>
				    <th width="13%"><spring:message code="label.creditAmount" /></th>
					<th width="20%">Deposit Account Number</th>
						<th width="20%">Create Deposit</th>
					
				</tr>
			</thead>
				<tbody>
				<c:if test="${! empty ledgerCommonAccount}">
					<c:forEach items="${ledgerCommonAccount}" var="ledgerCommon">
						<tr>
					    	<td><fmt:formatDate pattern="dd/MM/yyyy"
									value="${ledgerCommon.ledgerDateDebit}" />
							</td>
							<td><c:out value="${ledgerCommon.particularsDebit}"></c:out></td>
							<fmt:formatNumber value="${ledgerCommon.debitAmount}" pattern="#.##" var="debitAmount"/>
							<td class="commaSeparated">${debitAmount}</td>
							<td style="border-right: 1px solid #ccc;"></td>
							<td><fmt:formatDate pattern="dd/MM/yyyy"
									value="${ledgerCommon.ledgerDateCredit}" />
							</td>
							<td><c:out value="${ledgerCommon.particularsCredit}"></c:out></td>
							
							<fmt:formatNumber value="${ledgerCommon.creditAmount}" pattern="#.##" var="creditAmount"/>
							
							<td class="commaSeparated">${creditAmount}</td>
							
							<td><b style="color:#358cce"><a href="viewDeposit?id=${ledgerCommon.depositId}">${ledgerCommon.accountNumber}</a></b></td>

							<c:if test="${ledgerCommon.depositId == null}">
							 <td><b style="color:#358cce"><a href="createSingleDepositFromCommonLedger?customerId=${ledgerCommon.customerId}&productId=2768&debitAmount=${debitAmount}&creditAmount=${creditAmount}">Create Deposit</a></b></td>
							 </c:if>
						</tr>
					</c:forEach>
				</c:if>

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
	