<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="right-container" id="right-container">
	<div class="container-fluid">

		<div class="fd-list">
			<div class="header_customer">
				<h3>
					Uncleared <spring:message code="label.fixedDepositList" />
				</h3>
			</div>

			<div class="fd-list-table">

				<span class="counter pull-right"></span>
				<div class="col-sm-12 col-md-12 col-lg-12">
					<input type="text" id="kwd_search" value=""
						placeholder="Search Here..." style="float: right;" />
				</div>
			<form:form id="clearanceForm">
					<table class="table data jqtable example" id="my-table">
						<thead>
							<tr>
								<th><spring:message code="label.fdID" /></th>
								<th><spring:message code="label.accno" /></th>
								<th><spring:message code="label.currentBalance" /></th>

								<th><spring:message code="label.fdAmount" /></th>
								<th><spring:message code="label.maturityDate" /></th>
								<th><spring:message code="label.createdDate" /></th>
								<th><spring:message code="label.status" /></th>
								<th>Action</th>

							</tr>
						</thead>
						<c:if test="${! empty clearanceList}">
							<c:forEach items="${clearanceList}" var="deposit">
								<tr>
									<td><c:out value="${deposit.id}"></c:out></td>
									<td><c:out value="${deposit.accountNumber}"></c:out></td>

									<fmt:formatNumber value="${deposit.currentBalance}"
										pattern="#.##" var="currentBalance" />
									<td class="commaSeparated"><c:out value="${currentBalance}"></c:out></td>

									<fmt:formatNumber value="${deposit.depositAmount}"
										pattern="#.##" var="depositamount" />
									<td class="commaSeparated"><c:out value="${depositamount}"></c:out></td>

									<td><fmt:formatDate pattern="dd/MM/yyyy"
											value="${deposit.newMaturityDate}" /></td>
									<td><fmt:formatDate pattern="dd/MM/yyyy"
											value="${deposit.createdDate}" /></td>
									<td><c:out value="${deposit.status}"></c:out></td>
									<td><input type="checkbox"
										onclick="addRemove('${deposit.id}')" id="clearanceBox"
										 /></td>
								</tr>
							</c:forEach>
						</c:if>

						</tbody>
					</table>
			</form:form>
				<div style="margin-left: 368px;">
				<span id="errorSubmit" style="color: red"></span>
				</div>
					
				 <input style="margin-left: 368px;"
					type="button" onclick="submit()" class="btn btn-success"
					value="Deposit Clearance" />
					
			</div>

		</div>
	</div>
</div>
<script>

var arrayId=[];
$(document).ready(function(){
	window.arrayId=[];
	
});

function addRemove(depositId){
	
	var temp=0;
	for (var i=window.arrayId.length-1; i>=0; i--) {
		
	    if (window.arrayId[i] === depositId) {
	       	window.arrayId.splice(i, 1);
	    	temp=1;
	         break;     
	         }
	    
	    }
	if(temp==0){
		window.arrayId.push(depositId);
		
	}
}

function submit(){
	document.getElementById('errorSubmit').innerHTML="";
	
if(window.arrayId.length>0){
	$("#clearanceForm").attr("action", "depositClearance?ids="+arrayId);
	$("#clearanceForm").submit();

}
else{
	document.getElementById('errorSubmit').innerHTML="No deposit selected for clearance";
}

}
</script>