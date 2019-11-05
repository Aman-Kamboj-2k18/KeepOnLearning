<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div class="right-container" id="right-container">
	<div class="container-fluid">


		<div class="approval-customer-confirm">
			<div class="header_customer" style="margin-bottom: 20px;">
				<h3>
					<spring:message code="label.approval" />
				</h3>
			</div>
			<form:form action="approveCustomerConfirm" class="form-horizontal"
				commandName="customerForm" onsubmit="return val();">
				<form:hidden path="id" />
				<div class="approval-customer-confirm-table">
                     <form:hidden path="transactionId"/>
					<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.customerName" /></label>
						<div class="col-md-5">
							<form:input path="customerName" class="myform-control"
								readonly="true"></form:input>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.customerId" /></label>
						<div class="col-md-5">
							<form:input path="customerID" class="myform-control"
								readonly="true"></form:input>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.userName" /></label>
						<div class="col-md-5">
							<form:input path="userName" class="myform-control"
								readonly="true"></form:input>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.address" /></label>
						<div class="col-md-5">
							<form:input path="address" class="myform-control" readonly="true"
								id="companyName"></form:input>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.status" /></b><span style="color: red">*</span></label>
						<div class="col-md-5">
							<form:select path="status" class="myform-control" id="status"
								style="cursor: pointer;background: whitesmoke;">
								<form:option value="">
									<spring:message code="label.selectValue" />
								</form:option>
								<form:option value="Approved">
									<spring:message code="label.approve" />
								</form:option>
								<form:option value="Closed">
									<spring:message code="label.close" />
								</form:option>
<%-- 								<form:option value="Suspended"> --%>
<%-- 									<spring:message code="label.suspend" /> --%>
<%-- 								</form:option> --%>
							</form:select>
							<span id="statusError" class="error" style="display: none;"><font
								color="red"><spring:message code="label.selectValue" /></font></span> <span
								id="statusError" class="error" style="display: none"><spring:message
									code="label.selectValue" /></span>
						</div>
					</div>
					<form:hidden path="contactNum" />
					<form:hidden path="email" />
					<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.comment" /></b><span style="color: red">*</span></label>
						<div class="col-md-5">
							<form:textarea class="myform-control" path="comment"
								maxlength="255" id="comment" placeholder="Enter Comment"
								style="height:120px;cursor: text;
   											 background: whitesmoke;"></form:textarea>
							<span id="commentError" class="error" style="display: none;"><font
								color="red"><spring:message
										code="label.commentValidation" /></font></span> <span id="commentError"
								class="error" style="display: none"><spring:message
									code="label.commentValidation" /></span>
						</div>
					</div>

					<div class="form-group">
						<div class="col-md-offset-4 col-md-8">
							<a href="customerApprovalList"
								class="btn btn-success"><spring:message code="label.back" /></a> <input type="submit"
								value="<spring:message code="label.confirm"/>"
								class="btn btn-primary"> 
						</div>
					</div>



				</div>



			</form:form>
			<script>
				function val() {

					if (document.getElementById('comment').value == '') {
						document.getElementById('comment').style.borderColor = "red";
						return false;
					} else {
						document.getElementById('comment').style.borderColor = "green";
					}
					if (document.getElementById('status').value == '') {
						document.getElementById('status').style.borderColor = "red";
						return false;
					} else {
						document.getElementById('status').style.borderColor = "green";
					}
				}
			</script>
		</div>
		<style>
.myform-control {
	background: #AFAFAF;
	color: black;
	cursor: no-drop;
}
</style>