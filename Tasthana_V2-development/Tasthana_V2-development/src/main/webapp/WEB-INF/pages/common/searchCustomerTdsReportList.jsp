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
					Reverse Annuity
					<small><spring:message code="label.searchCustomer" /></small>
				</h3>
			</div>
			<div class="Success_msg"></div>
			<div class="flexi_table">
				<form:form class="form-horizontal" id="fdForm" method="post"
					name="fixedDeposit" commandName="fixedDepositForm" autocomplete="off">

					<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.customerName" /></label>
						<div class="col-md-6">
							<form:input path="customerName" id="customerName" class="form-control"
								placeholder="" autocomplete="off"/>

						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.contactNumber" /></label>
						<div class="col-md-6">
							<form:input path="contactNum" id="contactNumber" class="form-control" placeholder="" />
							

						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.email" /></label>
						<div class="col-md-6">
							<form:input path="email" id="email" class="form-control" placeholder="" />
							

						</div>
					</div>

					<div class="form-group">
						<div class="col-md-offset-4 col-md-8">
							<input type="button" onclick="searchCustomer()"
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
									<td><b>Customer ID</b></td>


									<td><b><spring:message code="label.select" /></b></td>
								</tr>
								<c:forEach items="${endUserList}" var="user">

									<tr>
										<td><b> <c:out value="${user.customerName}"></c:out></b></td>
										<td><b> <c:out value="${user.email}"></c:out></b></td>
										<td><b> <c:out value="${user.contactNum}"></c:out></b></td>
										<td><b> <c:out value="${user.id}"></c:out></b></td>
										<%-- <td><b><a href="reverseEmiDefault?id=${user.id}">Select</a></b></td> --%>
										<td><b><form:radiobutton path="id" name="cusId"
													value="${user.id}" onclick="onclickRadio(${user.id})" /></b></td>
										<td style="display: none"><form:radiobutton
												style="display:none"  path="id" value="${user.id}" /></td> 
									</tr>
								</c:forEach>
							</table>

						</c:if>


					</table>
					<input type="hidden" id="customerId"/>
					<div class="form-group">
						<div class="col-md-offset-4 col-md-8">
						
							<a href="bankEmp" class="btn btn-warning"><spring:message
									code="label.back" /></a>
									
							<input type="button" class="btn btn-info" data-toggle="tooltip"
								title="Please first select the customer to click on search"
								id="goBtn" onclick="submitForm()" disabled="true"
								value="<spring:message code="label.go"/>">
								
						
						</div>
					</div>

				</form:form>
			</div>

		</div>
		<script>
		function submitForm() {
			var customerId= document.getElementById('customerId').value;
			customerId=parseInt(customerId);
			$("#fdForm").attr("action", "formSubmission?customerId="+customerId);
			$("#fdForm").submit();

		}
		
		$(document).ready(function() {
				$('[data-toggle="tooltip"]').tooltip();
				
				document.getElementById('userName').value = "";
				document.getElementById('contactNo').value = "";
				document.getElementById('email').value = "";
				
			});
		</script>
		<script>
			function searchCustomer() {
				
				    var custName = document.getElementById('customerName').value;
				    var conNum = document.getElementById('contactNumber').value;
				    var emailCust = document.getElementById('email').value;
				    
				    if(custName=="" && emailCust=="" && conNum == ""){
				   	 document.getElementById('errorMsg').style.display='block';
				     document.getElementById('errorMsg').value="Please Insert at least one field.";
				     event.preventDefault();
				    }
				    else
				    {   
				    $("#fdForm").attr("action", "customerListForFormSubmission");
				    $("#fdForm").submit();

				    }
			

			}

			function onclickRadio(customerId) {
				document.getElementById("goBtn").disabled = false;
			  document.getElementById('customerId').value=customerId;
				
			}

			
		</script>