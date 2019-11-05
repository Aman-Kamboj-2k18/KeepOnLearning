<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="right-container" id="right-container">
	<div class="container-fluid">

		<div class="role-page"
			style="border: none; padding: 0; box-shadow: none; background: none;">
			
			<div class="Flexi_deposit" id = "serachInputBoxHide" style="display:none; ">
			
			<div class="header_customer" style="margin-bottom: 10px;">
				<h3>
					Overdraft Summary
				</h3>
				
			</div>
			
			<div class="exit" style="font-size: 19px;">

				<h3 style="color: green">${sucess}</h3>
			</div>

			<div class="list-of-rates-table" style="box-shadow: none !important;">
					<form:form id = "fromSubmit"  commandName = "overdraftForm">
				
					<div class="form-group" id="totalTransferAmt">
							<label class="control-label col-sm-2" for="">Overdraft No.<span style="color: red">*</span></label>
							<div class="col-sm-4">
								<form:input path="overdraftNumber"  type="text"
									class="myform-control" id="overdraftNumber" value="${overdraftNumber}"/>
							</div>
						<div class="col-sm-4">
						<input value="Search" type ="button" class="btn btn-primary" onclick = "return overdraftSummarySearchByOverdraftNumber()"/>
						</div>
						
						</div>
					</form:form>
			</div>
			<div style="text-align: center;"><span id = "errorMsg" style="color: red;font-family:monospace;font-size: x-large;">${error }</span></div>
			
			</div>
			
			</div>
			<div class="Flexi_deposit" style="margin-top: 15px !important;">
			<div class="header_customer">
				<h3>
					Overdraft Number Summary
				</h3>
				
				
			</div>


			<div class="employee_details">
				<h4 style="color: blue; text-align: center;">${information}</h4>
				<div class="space-10"></div>
				 <table class="table with-pager data jqtable example" id="my-table" style="width: 98%; margin-left: 15px;">
					<thead>
						<tr>
						
							
							<th>Action</th>
							
							<th>Overdraft <spring:message code="label.id" /></th>
							<th>Overdraft No.</th>
							<th>Issued Date</th>
							<th>Issued Amount</th>
							<th>Withdraw Amount</th>
							<th>Paid Amount</th>
							
						</tr>
					</thead>
					<tbody>

						<c:choose>
							<c:when test="${role ne 'ROLE_USER'}">
								<tr>
								<td><input type="radio" onclick = "setOverdraftId(${overdraftIssue.id},'${overdraftIssue.overdraftNumber}')" name = "setOverdraftId"/></td>
									<td><c:out value="${overdraftIssue.id}"></c:out></td>
									<td><c:out value="${overdraftIssue.overdraftNumber}"></c:out></td>
									<td><fmt:formatDate value="${overdraftIssue.issueDate}"
											pattern="yyyy-MM-dd" /></td>
									<td><c:out value="${overdraftIssue.sanctionedAmount}"></c:out></td>
									<td><c:out value="${overdraftIssue.withdrawableAmount}"></c:out></td>
									<td><c:out value="${overdraftIssue.totalAmountPaid}"></c:out></td>
									
								</tr>
							</c:when>
							<c:otherwise>
								<c:forEach items="${overdraftIssueList}" var="overdraftIssue">
									<tr>
										<td><input type="radio" onclick = "setOverdraftId(${overdraftIssue.id},'${overdraftIssue.overdraftNumber}')" name = "setOverdraftIds"/></td>
										<td><c:out value="${overdraftIssue.id}"></c:out></td>
										<td><c:out value="${overdraftIssue.overdraftNumber}"></c:out></td>
										<td><fmt:formatDate value="${overdraftIssue.issueDate}"
												pattern="yyyy-MM-dd" /></td>
										<td><c:out value="${overdraftIssue.sanctionedAmount}"></c:out></td>
										<td><c:out value="${overdraftIssue.withdrawableAmount}"></c:out></td>
										<td><c:out value="${overdraftIssue.totalAmountPaid}"></c:out></td>
									
										
									</tr>

								</c:forEach>
							</c:otherwise>
						</c:choose>
					</tbody>
					</table>
					<div style="margin-left: 24%;margin-bottom: 12px;"><button onclick="detailedWithdrawList()" disabled="disabled" id  = "detailedWithdrawListId" class="btn btn-primary">Detailed Withdraw List</button>
									<button class="btn btn-primary" style="margin-left: 268px;" onclick="detailedPaymentList()" disabled="disabled" id = "detailedPaymentListId">Detailed Payment List</button></div>
							 
			</div>
			

			</div>
		</div></div>
		
		<script>
		$( document ).ready(function() {
		  if('${role}' != 'ROLE_USER'){
			  $("#serachInputBoxHide").show();
		  }
		});
		
		
		
		function overdraftSummarySearchByOverdraftNumber() {
			var overdraftNumber = $("#overdraftNumber").val();
			if(overdraftNumber != ""){
				$("#errorMsg").text("");
				$("#fromSubmit").attr("action","overdraftSummarySearchByOverdraftNumber?menuId=${menuId}");
				$("#fromSubmit").submit();
				return true;
				
			}else{
				$("#errorMsg").text("Please enter overdraft number *");
				return false;
			}
		}
			
			var overdraftId  = 0;
			var overdraftNumber = "";
			
			function setOverdraftId(id,overdraftNo) {
				
				overdraftId = id;
				overdraftNumber = overdraftNo;
				$("#detailedWithdrawListId").attr("disabled",false);
				$("#detailedPaymentListId").attr("disabled",false);
				
			}
			
			function detailedPaymentList() {
				location.href = "overdraftSummaryPaymentDetailList?menuId=${menuId}&id="+overdraftId+"&overdraftNumber="+overdraftNumber;
				
			}
			
			
			function detailedWithdrawList() {
				location.href = "overdraftSummaryWithdrawDetailList?menuId=${menuId}&id="+overdraftId+"&overdraftNumber="+overdraftNumber;
				
			}
			
		
		
		</script>
	</div>