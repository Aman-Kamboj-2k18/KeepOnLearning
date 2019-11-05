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

			<div class="header_customer" style="margin-bottom: 30px;">
				<h3>
					<spring:message code="label.suspendBankEmp" />
					<small><spring:message code="label.searchBankEmp" /></small>
				</h3>
			</div>
			<div class="Success_msg"></div>
			<div class="flexi_table">
				<form:form class="form-horizontal" id="fdForm" method="post"
					name="fixedDeposit" commandName="endUserForm">

                    <div class="form-group">
						<label class="col-md-4 control-label">Employee ID</label>
						<div class="col-md-6">
							<form:input path="bankId" class="form-control" id="bankId"
								placeholder="Enter ID" />

						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.empName" /></label>
						<div class="col-md-6">
							<form:input path="userName" class="form-control" id="userName"
								placeholder="Enter userName" />

						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.contactNumber" /></label>
						<div class="col-md-6">
							<form:input path="contactNo" id="contactNo" class="form-control"
								placeholder="Enter Contact Number" />

						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.email" /></label>
						<div class="col-md-6">
							<form:input path="email" id="email" class="form-control"
								placeholder="Enter Email" />

						</div>
					</div>

					<div class="form-group">
						<div class="col-md-offset-4 col-md-8">
							<input type="submit" onclick="searchCustomer()"
								class="btn btn-info"
								value="<spring:message code="label.search"/>">
						</div>
					</div>

					<table align="center">

						<tr>
							<td><br></td>
						</tr>

						<c:if test="${! empty endUserList}">
							<table class="table table-striped table-bordered">
								<tr>
									<td><b><spring:message code="label.customerName" /></b></td>
									<td><b><spring:message code="label.email" /></b></td>
									<td><b><spring:message code="label.contactNumber" /></b></td>


									<td><b><spring:message code="label.select" /></b></td>
								</tr>
								<c:forEach items="${endUserList}" var="user">

									<tr>
										<td><b> <c:out value="${user.userName}"></c:out></b></td>
										<td><b> <c:out value="${user.email}"></c:out></b></td>
										<td><b> <c:out value="${user.contactNo}"></c:out></b></td>
										<td><b><a href="suspendCustomer?id=${user.id}">Select</a></b></td>

										<%-- <td><b><form:radiobutton path="id" name="cusId"
													value="${cus.id}" onclick="onclickRadio()" /></b></td>
										<td style="display: none"><form:radiobutton
												style="display:none" path="id" value="${cus.id}" /></td> --%>
									</tr>
								</c:forEach>
							</table>

						</c:if>


					</table>
					<div class="form-group">
						<div class="col-md-offset-4 col-md-8">
							<a href="apprMng" class="btn btn-info"><spring:message
									code="label.back" /></a>
						</div>
					</div>

				</form:form>
			</div>

		</div>
		<script>
			script > $(document).ready(function() {
				$('[data-toggle="tooltip"]').tooltip();
				
				document.getElementById('userName').value = "";
				document.getElementById('contactNo').value = "";
				document.getElementById('email').value = "";
				
			});
		</script>
		<script>
			function searchCustomer() {
				$("#fdForm").attr("action", "searchBankEmp");
				$("#fdForm").submit();

			}

			
			
		</script>