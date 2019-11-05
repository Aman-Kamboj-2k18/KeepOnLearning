<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="right-container" id="right-container">
        <div class="container-fluid">
		
<div class="fd-list">
			<div class="header_customer">	
				<h3><spring:message code="label.fixedDepositList"/></h3>
			</div>
			
			<div class="fd-list-table">	
			
			<span class="counter pull-right"></span>
					<input type="text" id="kwd_search" class="form-control" value="" placeholder="Search Here..."  style="float:right;width:20%;"/>
					<span id="error" style="color:red; margin-left: 307px;"></span>
			<table class="table data jqtable example table-bordered table-striped table-hover" id="my-table">
				<thead>
					<tr>
					<th><spring:message code="label.fdID" /></th>
					<th>Primary Holder Name</th>
					<th><spring:message code="label.accno" /></th>
					<th><spring:message code="label.fdAmount" /></th>
					<th><spring:message code="label.currentBalance" /></th>
					<th><spring:message code="label.maturityDate"/></th>
					<th><spring:message code="label.createdDate" /></th>
					<th><spring:message code="label.approvalStatus" /></th>
					<th>Deposit Category</th>
					<th><spring:message code="label.action"/></th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${! empty depositList}">
			
						<c:forEach items="${depositList}" var="deposit">
							<c:if test="${deposit.approvalStatus=='Approved'}">
							<tr>
							<td><c:out value="${deposit.depositId}"></c:out></td>
							<td><c:out value="${deposit.customerName}"></c:out></td>
							<td><c:out value="${deposit.accountNumber}"></c:out></td>
								
							<fmt:formatNumber value="${deposit.depositAmount}" pattern="#.##" var="depositamount"/>
							<td class="commaSeparated">${depositamount}</td>
							
							<fmt:formatNumber value="${deposit.currentBalance}" pattern="#.##" var="currentBalance"/>
							<td class="commaSeparated">${currentBalance}</td>
							
							<td><fmt:formatDate pattern="dd/MM/yyyy" value="${deposit.newMaturityDate}" /></td>
						<td><fmt:formatDate pattern="dd/MM/yyyy" value="${deposit.createdDate}"/></td>
							<td><c:out value="${deposit.approvalStatus}"></c:out></td>
							<td><c:if test="${empty deposit.depositCategory}">Regular</c:if><c:out value="${deposit.depositCategory}"></c:out></td>
							
							<td><a onclick="return checkCategory('${deposit.depositCategory}',${deposit.depositId},'${deposit.category}',${deposit.depositHolderId},${deposit.customerId}); return false;" href="#"
									class="btn btn-primary"><spring:message code="label.edit"/></a></td>
							</tr>
							</c:if>
						</c:forEach>
						
					</c:if> 

				</tbody>
			</table> 
			</div>

</div>
<script>

function checkCategory(depositCategory,depositId,category,depositHolderId,customerId){
	
// 	if(depositCategory=='' || depositCategory=='REVERSE-EMI'){
	if(depositCategory==''){
		
		 window.location="editFDBank?id="+depositId+"&holderId="+depositHolderId+"&depositCategory="+depositCategory; 
	}
	else if(depositCategory=='REVERSE-EMI'){
		document.getElementById("error").innerHTML='Annuity Deposit cannot be modified.';
		return false;
	}
	else{
		document.getElementById("error").innerHTML='Cannot be modified for deposit category:'+depositCategory;
		return false;
	}
}
</script>