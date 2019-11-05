<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script type="text/javascript">


function getLoginDate()
{
	var today = null;
	$.ajax({  
	    type: "GET",  
	    async: false,
	    url: "<%=request.getContextPath()%>/bnkEmp/loginDateForJsp", 
	    contentType: "application/json",
	    dataType: "json",

	    success: function(response){  
	    //	window.loginDateForFront = new Date(parseInt(response));
	    	today = new Date(parseInt(response))
	    },  
	    error: function(e){  
	    	 $('#error').html("Error occured!!")
	    	 // window.loginDateForFront = getTodaysDate();
	    }  
	  });  
	return today;
}
	function validateForm() {
		var d = getLoginDate().getTime();
		var uuid = 'xxxxxx'.replace(/[xy]/g, function(c) {
			var r = (d + Math.random() * 16) % 16 | 0;
			d = Math.floor(d / 16);
			return (c == 'x' ? r : (r & 0x3 | 0x8)).toString(16);
		});
		document.customerHeadSave.transactionId.value = uuid;
	}

	function goBack() {
		window.history.back();
	}

	$(document).ready(function() {

		var age = "${customerForm.nomineeAge}";

		if (age < 18) {
			$("#age1").show();
			$("#age2").show();
			$("#age3").show();
			$("#age4").show();
		} else {
			$("#age1").hide();
			$("#age2").hide();
			$("#age3").hide();
			$("#age4").hide();
		}

	});
</script>
<div class="right-container" id="right-container">
	<div class="container-fluid">

		<div class="add-customer">
			<form:form action="savedDetails" class="form-horizontal"
				name="customerHeadSave" method="post" commandName="customerForm"
				onsubmit="return validateForm();">
				<form:hidden path="transactionId" value="" />
				<div class="header_customer" style="margin-bottom: 30px;">
					<h3>
						<spring:message code="label.confirmationScreen" />
					</h3>
				</div>
				<div class="successMsg">
					<b><font color="green">${success}</font></b>
				</div>

				<div class="add-customer-table">



					<div class="col-md-6">
					
					
					<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.citizen" /></label>
							<div class="col-md-8">
								<form:input path="citizen" class="myform-control"
									readonly="true"></form:input>
							</div>
						</div>
					
					<c:if test="${customerForm.nriAccountType != null}">
					<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.nriAccountType" /></label>
							<div class="col-md-8">
								<form:input path="nriAccountType" class="myform-control"
									readonly="true"></form:input>
							</div>
						</div>
					</c:if>
					
					
					
					
					
					
					
						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.category" /></label>
							<div class="col-md-8">
								<form:input path="category" class="myform-control"
									readonly="true"></form:input>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.customerName" /></label>
							<div class="col-md-8">
								<form:input path="customerName" class="myform-control"
									readonly="true"></form:input>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.customerId" /></label>
							<div class="col-md-8">
								<form:input path="customerID" class="myform-control"
									readonly="true"></form:input>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.userName" /></label>
							<div class="col-md-8">
								<form:input path="userName" class="myform-control"
									readonly="true"></form:input>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.gender" /></label>
							<div class="col-md-8">
								<form:input path="gender" class="myform-control" readonly="true"></form:input>
							</div>
						</div>
						<div class="form-group">

							<label class="col-md-4 control-label">Date of Birth</label>
							<div class="col-md-8">
								<form:input path="dateOfBirth" class="myform-control"
									readonly="true"></form:input>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.contactNumber" /></label>
							<div class="col-md-8">
								<form:input path="contactNum" class="myform-control"
									readonly="true" id="contactno"></form:input>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.altContactNo" /></label>
							<div class="col-md-8">
								<form:input path="altContactNum" class="myform-control"
									readonly="true" id="txtAlternateContactNumber"></form:input>
							</div>
						</div>

					</div>
					<div class="col-md-6">
						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.email" /></label>
							<div class="col-md-8">
								<form:input path="email" class="myform-control" readonly="true"
									id="email"></form:input>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.altEmail" /></label>
							<div class="col-md-8">
								<form:input path="altEmail" class="myform-control"
									readonly="true" id="txtAlternateEmail"></form:input>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.address" /></label>
							<div class="col-md-8">
								<form:input path="address" class="myform-control"
									readonly="true" id="address"></form:input>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.pincode" /></label>
							<div class="col-md-8">
								<form:input path="pincode" class="myform-control"
									readonly="true" id="pincode"></form:input>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.country" /></label>
							<div class="col-md-8">
								<form:input path="country" class="myform-control"
									readonly="true" id="country"></form:input>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.state" /></label>
							<div class="col-md-8">
								<form:input path="state" class="myform-control" readonly="true"
									id="state"></form:input>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.city" /></label>
							<div class="col-md-8">
								<form:input path="city" class="myform-control" readonly="true"
									id="city"></form:input>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.pan" /></label>
							<div class="col-md-8">
								<form:input path="pan" class="myform-control" style="text-transform:uppercase" readonly="true"
									id="pan"></form:input>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.aadhar" /></label>
							<div class="col-md-8">
								<form:input path="aadhar" class="myform-control" readonly="true"
									id="aadhar"></form:input>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-4 control-label"></label>
							<div class="col-md-8">
								<form:hidden path="accountNo" class="myform-control"
									readonly="true" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label"></label>
							<div class="col-md-8">
								<form:hidden path="accountType" class="myform-control"
									readonly="true" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label"></label>
							<div class="col-md-8">
								<form:hidden path="accountBalance" class="myform-control"
									readonly="true" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label"></label>
							<div class="col-md-8">
								<form:hidden path="minimumBalance" class="myform-control"
									readonly="true" />
							</div>
						</div>
					</div>
					<div class="col-sm-12">
						<hr>
					</div>

					<c:if test="${! empty customerForm.guardianName}">
						<div style="margin-bottom: 10px; margin-left: 30px;">
							<b><h3>Guardian Information</h3></b>
						</div>
						<div class="col-sm-12">
							<hr>
						</div>

						<c:set var="guardianName"  value="${customerForm.guardianName}" />
						<form:hidden path="guardianName"/>
						<c:set var="guardianAge" value="${customerForm.guardianAge}" />
						<form:hidden path="guardianAge"/>
						
						<c:set var="guardianAddress"
							value="${customerForm.guardianAddress}" />
                         <form:hidden path="guardianAddress"/>
						<c:set var="guardianRelationShip"
							value="${customerForm.guardianRelationShip}" />
							 <form:hidden path="guardianRelationShip"/>
						<c:set var="guardianAadhar" value="${customerForm.guardianAadhar}" />
						 <form:hidden path="guardianAadhar"/>
						<c:set var="guardianPan" value="${customerForm.guardianPan}" />
	                     <form:hidden path="guardianPan"/>
	 					<div class="col-sm-6 col-xs-6">

							<div class="form-group">
								<label class="col-md-4 control-label">Guardian Name</label>
								<div class="col-md-8">
									<input value="${guardianName}" class="myform-control" readonly />

								</div>
							</div>

							<div class="form-group">
								<label class="col-md-4 control-label">Guardian Age</label>
								<div class="col-md-8">
									<input value="${guardianAge}" class="myform-control" readonly />

								</div>
							</div>

							<div class="form-group">
								<label class="col-md-4 control-label">Guardian Address</label>
								<div class="col-md-8">
									<input value="${guardianAddress}" class="myform-control"
										readonly />

								</div>
							</div>

							<div class="form-group">
								<label class="col-md-4 control-label">Guardian
									RelationShip</label>
								<div class="col-md-8">
									<input value="${guardianRelationShip}" class="myform-control"
										readonly />

								</div>
							</div>

							<div class="form-group">
								<label class="col-md-4 control-label">Guardian Aadhar</label>
								<div class="col-md-8">
									<input value="${guardianAadhar}" class="myform-control"
										readonly />

								</div>
							</div>

							<div class="form-group">
								<label class="col-md-4 control-label">Guardian Pan</label>
								<div class="col-md-8">
									<input value="${guardianPan}" class="myform-control" style="text-transform:uppercase" readonly />

								</div>
							</div>
						</div>
						<div class="col-sm-12">
							<hr>
						</div>
					</c:if>

					<c:if test="${! empty customerForm.accountType}">
						<div style="clear:both;"></div>
						<div style="margin-bottom: 10px; margin-left: 30px;">
							<b><h3>Account Information</h3></b>
						</div>
						<div class="col-sm-12">
							<hr>
						</div>
						<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
						<c:set var="accountType" value="${customerForm.accountType}" />
						<c:set var="accountNo" value="${customerForm.accountNo}" />
						<c:set var="accountBalance" value="${customerForm.accountBalance}" />
						<c:set var="minimumBalance" value="${customerForm.minimumBalance}" />


						<c:set var="accountTypeList" value="${fn:split(accountType, ',')}" />
						<c:set var="accountNoList" value="${fn:split(accountNo, ',')}" />
						<c:set var="accountBalanceList"
							value="${fn:split(accountBalance, ',')}" />
						<c:set var="minimumBalanceList"
							value="${fn:split(minimumBalance, ',')}" />
						

						<c:forEach items="${accountTypeList}" var="accType"
							varStatus="status">
							<div class="col-sm-6 col-xs-6">

								<div class="form-group">
									<label class="col-md-4 control-label"><spring:message
											code="label.accountType" /></label>
									<div class="col-md-8">
										<input value="${accountTypeList[status.index]}"
											class="myform-control" readonly />

									</div>
								</div>

								<div class="form-group">
									<label class="col-md-4 control-label"><spring:message
											code="label.accountNo" /></label>
									<div class="col-md-8">
										<input value="${accountNoList[status.index]}"
											class="myform-control" readonly />

									</div>
								</div>

								<div class="form-group">
									<label class="col-md-4 control-label"><spring:message
											code="label.accountBalance" /></label>
									<div class="col-md-8">
										<input value="${accountBalanceList[status.index]}"
											class="myform-control" readonly />

									</div>
								</div>
								<script>	
						
						</script>
								<div class="form-group">
									<label class="col-md-4 control-label"><spring:message
											code="label.accountBalance" /></label>
									<div class="col-md-8">
										<input value="${minimumBalanceList[status.index]}"
											class="myform-control" readonly />

									</div>
								</div>
							</div>
							<div class="col-sm-12">
								<hr>
							</div>
						</c:forEach>
					</c:if>

					<div class="col-sm-12 col-md-12 col-lg-12">
						<p align="center" style="padding-top: 10px;">
							<a href="addCustomer" class="btn btn-success"><spring:message
									code="label.back" /></a> <input type="submit"
								class="btn btn-primary"
								value="<spring:message code="label.save"/>">

						</p>
					</div>

				</div>
			</form:form>

		</div>
		<style>
.myform-control {
	cursor: no-drop;
	background: rgba(175, 175, 172, 0.94);
	color: #E8E8E8;
}
</style>