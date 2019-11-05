<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<div class="right-container" id="right-container">
        <div class="container-fluid">
			

<div class="list-of-rates">
			<div class="header_customer">	
				<h3>Select Deposit</h3>
			</div>
			<div class="list-of-rates-table">	
				<span class="counter pull-right"></span>
					<input type="text" id="kwd_search" value="" class="form-control" placeholder="Search Here..."  style="float:right;margin-bottom: 10px; width: 20%;"/>
				
				<div>
				
			<!-- <div class="list-of-rates-table">	
			<div class="search_filter">
				
					<div class="space-10"></div>
					<div class="col-sm-12" id="dividerLine">
					<hr>
					</div> -->
					<!-- select dh.depositHolderStatus,"
		    + "dh.contribution,dh.depositId,d.maturityDate,d.status,d.createdDate,d.depositAmount,d.currentBalance,d.accountNumber from Deposit as d "
		    + "inner join d.depositHolder as dh where dh.customerId=:userId and d.status='OPEN' and d.depositCategory IS NULL");
					 -->
					 
			
					 
				<c:if test="${! empty objList}">
				
				<table class="table data jqtable example"  id="my-table">
					<thead >
						<tr class="success">
					    <th>Serial No</th>
						<th>Deposit Id</th>
						<th>Deposit Holder Status</th>
						<th>Contribution</th>
						<th>Created Date</th>
						<th>Maturity Date</th>
						
						<th>Select</th>
						
						</tr>
					</thead>
					<tbody>
				
							<c:forEach items="${objList}" var="obj" varStatus="status">
								<tr>
								
								<td><c:out value="${status.index+1}"/></td>
									<td><c:out value="${obj[2]}" /></td>
									<td><c:out value="${obj[0]}" /></td>
									<td><c:out value="${obj[1]}" /></td>
									<td><fmt:formatDate pattern="dd/MM/yyyy" value="${obj[5]}" /></td>
									<td><fmt:formatDate pattern="dd/MM/yyyy" value="${obj[3]}" /></td>
									<td><a href="showModificationList?depositId=${obj[2]}">Show Report</a></td>
									
															</tr>
							</c:forEach>
					
	
					</tbody>
					
					<table align="center" class="f_deposit_btn">
							<tr>
								
								 <td><a href="searchCustForModificationReport" class="btn btn-danger"><spring:message
											code="label.back" /></a></td> 
								
							</tr>

						</table>
				</table> 
					</c:if>
					
			</div>
			
			</div>
</div>
</div>
<style>
.search_filter {
    display: flow-root;
    margin-bottom: 15px;
}
</style>
<!-- <style>
		.table-bordered {
    border: 1px solid #BFBABA;
}
	  .table-bordered>tbody>tr>td, .table-bordered>tbody>tr>th, .table-bordered>tfoot>tr>td, .table-bordered>tfoot>tr>th, .table-bordered>thead>tr>td, .table-bordered>thead>tr>th{ border: 1px solid #C7C7C7;}
</style> -->