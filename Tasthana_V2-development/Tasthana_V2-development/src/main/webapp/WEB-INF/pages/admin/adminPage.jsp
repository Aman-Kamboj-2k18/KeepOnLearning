<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="right-container" id="right-container">
	<div class="container-fluid">

		<div class="Flexi_deposit">

			<!-- <div class="header_customer">
				<h3>
		         	Approval Manager List
				</h3>
			</div>
			 -->
			<div class="flexi_table">
				
					<div class="col-sm-12">
						<div class="space-45"></div>
					</div>
					<%-- <c:if test="${! empty approverList}">
						<table class="table table-striped table-bordered table-hover data jqtable example">
							<thead>
								<tr class="success">
									<th><b>Serial No.</b></th>
									<th><b><spring:message code="label.userName" /></b></th>
									<th><b><spring:message code="label.email" /></b></th>
									<th><b><spring:message code="label.contactNumber" /></b></th>
									<th><b>Account Start Date</b></th>
									<th><b>Account Expiry Date</b></th>
									
									
									
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${approverList}" var="appr" varStatus="status">

									<tr>
									     <td>${status.index+1}</td>
										<td> <c:out value="${appr.userName}"></c:out></td>
										<td> <c:out value="${appr.email}"></c:out></td>
										<td> <c:out value="${appr.contactNo}"></c:out></td>
								       <td><fmt:formatDate	pattern="dd/MM/yyyy" value="${appr.startDate}" /></td>
										<td><fmt:formatDate	pattern="dd/MM/yyyy" value="${appr.accExpiryDate}" /></td>
										
						
										
									</tr>
									</c:forEach>
							</tbody>
							
						</table>

					</c:if> --%>
					<%-- <c:if test="${empty approverList}">&nbsp;&nbsp;No Record Found for Approver Manager</c:if> --%>
			
			
		
			<div class="header_customer">
				<h3>
		         	Employee List
				</h3>
			</div>
			
					<div class="col-sm-12">
						<div class="space-45"></div>
					</div>
					<c:if test="${! empty empList}">
						<table class="table table-striped table-bordered table-hover data jqtable example">
							<thead>
								<tr class="success">
									<th><b>Serial No.</b></th>
									<th><b><spring:message code="label.userName" /></b></th>
									<th><b><spring:message code="label.email" /></b></th>
									<th><b><spring:message code="label.contactNumber" /></b></th>
									<th><b>Account Start Date</b></th>
									<th><b>Account Expiry Date</b></th>
									
									
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${empList}" var="emp" varStatus="status">

									<tr>
									     <td>${status.index+1}</td>
										<td> <c:out value="${emp.userName}"></c:out></td>
									
										<td> <c:out value="${emp.email}"></c:out></td>
										<td> <c:out value="${emp.contactNo}"></c:out></td>
										<td><fmt:formatDate	pattern="dd/MM/yyyy" value="${emp.startDate}" /></td>
										<td><fmt:formatDate	pattern="dd/MM/yyyy" value="${emp.accExpiryDate}" /></td>
										
									
									
									</tr>
									</c:forEach>
							</tbody>
							
						</table>

					</c:if>
					<c:if test="${empty empList}">&nbsp;&nbsp;No Record Found for Employer</c:if>
			</div>
			</div>
		
		
		</div></div>

		<style>
.flexi_table {
	margin-bottom: 210px;
}

.space-45 {
	height: 27px;
}

input#fdID {
	margin-top: 0px;
}
</style>