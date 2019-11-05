<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="right-container" id="right-container">
	<div class="container-fluid">

		<div class="Flexi_deposit">

			<div class="header_customer">
				<h3>
				${title}
				</h3>
			</div>
			
			<div class="flexi_table">
				
					<div class="col-sm-12">
						<div class="space-45"></div>
					</div>
					<c:if test="${! empty list}">
						<table class="table table-striped table-bordered table-hover data jqtable example">
							<thead>
								<tr class="success">
									<th><b>Serial No.</b></th>
									<th><b>Display Name</b></th>
									<th><b><spring:message code="label.userName" /></b></th>
									<th><b><spring:message code="label.email" /></b></th>
									<th><b><spring:message code="label.contactNumber" /></b></th>	
									<%-- <th><b><spring:message code="label.currentRole" /></b></th>	 --%>									
									
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${list}" var="listVar" varStatus="status">

									<tr>
									     <td>${status.index+1}</td>
									   	<td> <c:out value="${listVar.displayName}"></c:out></td>
									ad	<td> <c:out value="${listVar.userName}"></c:out></td>
										<td> <c:out value="${listVar.email}"></c:out></td>
										<td> <c:out value="${listVar.contactNo}"></c:out></td>
										<%-- <td> <c:out value="${listVar.currentRole}"></c:out></td> --%>
									</tr>
									</c:forEach>
							</tbody>
							
						</table>

					</c:if>
			
			</div>

		</div>

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