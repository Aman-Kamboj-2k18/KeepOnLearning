<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="right-container" id="right-container">
	<div class="container-fluid">

		<%-- <div class="Success_msg">
			<div class="successMsg"
				style="text-align: center; color: red; font-size: 18px;">
				<h4>${error}</h4>

			</div>
		</div> --%>


		<div class="Flexi_deposit">

			<div class="header_customer" style="margin-bottom: 30px;">
				<h3>
					<spring:message code="label.suspendCust" />
					<small><spring:message code="label.searchCustomer" /></small>
				</h3>
			</div>
			<div class="Success_msg"></div>
			<div class="flexi_table">
				<form:form class="form-horizontal" id="fdForm" method="post"
					name="fixedDeposit" commandName="fixedDepositForm">


					<div class="form-group">
						<label class="col-md-4 control-label">Customer Id</label>
						<div class="col-md-6">
							<form:input path="customerID" id="customerId" class="form-control"
								placeholder="" />
							<span id="customerNameError" style="display: none; color: red;"><spring:message code="label.validation" /></span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.customerName" /></label>
						<div class="col-md-6">
							<form:input path="customerName" id="customerName" class="form-control"
								placeholder="" />
							<span id="customerNameError" style="display: none; color: red;"><spring:message code="label.validation" /></span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.contactNumber" /></label>
						<div class="col-md-6">
							<form:input path="contactNum" id="contactNumber" class="form-control" placeholder="" />
							<span id="contactNumberError" style="display: none; color: red;"><spring:message
									code="label.validation" /></span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.email" /></label>
						<div class="col-md-6">
							<form:input path="email" id="email" class="form-control" placeholder="" />
							<span id="emailError" style="display: none; color: red;"><spring:message
									code="label.validation" /></span>
						</div>
					</div>
					<div class="Success_msg">
						<div class="successMsg"
							style="text-align: center; color: red; font-size: 18px; margin-left: -213px;">
							<h4 style="font-size: 14px;">${error}</h4>

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

						<c:if test="${! empty customerList}">
							<table class="table table-striped table-bordered">
								<tr>
									<td><b><spring:message code="label.customerName" /></b></td>
									<td><b><spring:message code="label.email" /></b></td>
									<td><b><spring:message code="label.contactNumber" /></b></td>


									<td><b><spring:message code="label.select" /></b></td>
								</tr>
								<c:forEach items="${customerList}" var="user">

									<tr>
										<td><b> <c:out value="${user.customerName}"></c:out></b></td>
										<td><b> <c:out value="${user.email}"></c:out></b></td>
										<td><b> <c:out value="${user.contactNum}"></c:out></b></td>
										<td><b><a
												href="suspendCustomerFromAdmin?id=${user.id}">Select</a></b></td>

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
							<a href="adminPage" class="btn btn-info"><spring:message
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
				$("#fdForm").attr("action", "searchCustomer");
				$("#fdForm").submit();

			}

			function onclickRadio() {

				document.getElementById("goBtn").disabled = false;
			}
		</script>