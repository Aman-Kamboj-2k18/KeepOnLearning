<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="right-container" id="right-container">
	<div class="container-fluid">

		<div class="Flexi_deposit">

			<div class="header_customer">
				<h3>
					<spring:message code="label.approverManagerSuspendList" />
				</h3>
			</div>
			<div class="col-md-12" style="padding: 30px; text-align: center;">
				<span style="color: red;">${error}</span>
			</div>
			<div class="flexi_table">
				
					<div class="col-sm-12">
						<div class="space-45"></div>
					</div>
					<c:if test="${! empty suspendedList}">
						<table class="table table-striped table-bordered">
							<thead>
								<tr>
									
									<td><b><spring:message code="label.userName" /></b></td>
									<td><b><spring:message code="label.email" /></b></td>
									<td><b><spring:message code="label.contactNumber" /></b></td>
									<td><b><spring:message code="label.status" /></b></td>
									
									<td><b><spring:message code="label.select" /></b></td>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${suspendedList}" var="suspend">

									<tr>
									
										<td><b> <c:out value="${suspend.userName}"></c:out></b></td>
									
										<td><b> <c:out value="${suspend.email}"></c:out></b></td>
										<td><b> <c:out value="${suspend.contactNo}"></c:out></b></td>
										<td><b> <c:out value="${suspend.status}"></c:out></b></td>
										<td><b><a href="revokeApproverManager?id=${suspend.id}">Revoke</a></b></td>
										
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