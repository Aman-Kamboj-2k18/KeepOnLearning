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
							<form:input path="customerID" class="form-control" id="customerId"
								placeholder="Enter ID" />

						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.customerName" /></label>
						<div class="col-md-6">
							<form:input path="customerName" class="form-control" id="userName"
								placeholder="Enter name" />

						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.contactNumber" /></label>
						<div class="col-md-6">
							<form:input path="contactNum" id="contactNo" class="form-control"
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
			<span id="errorMsg" style="display:none;color:red; margin-left: 369px;margin-bottom: 10px;">Please fill at least one field to search the customer. </span>

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
										<td><b><a href="suspendCustomer?id=${user.id}">Select</a></b></td>

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
				var custId = document.getElementById('customerId').value;
				 var custName = document.getElementById('userName').value;
				
				    var conNum = document.getElementById('contactNo').value;
				   
				    var emailCust = document.getElementById('email').value;
				  
				    if(custId=="" && custName=="" && emailCust=="" && conNum == ""){
				    	
				     document.getElementById('errorMsg').style.display='block';
				    document.getElementById('errorMsg').value="Please Insert at least one field.";
				     event.preventDefault();
				    }
				    else
				    {   
				    	
				    	$("#fdForm").attr("action", "searchCustomer");
						$("#fdForm").submit();

				    }
				
			}

			function onclickRadio() {

				document.getElementById("goBtn").disabled = false;
			}

			
		</script>