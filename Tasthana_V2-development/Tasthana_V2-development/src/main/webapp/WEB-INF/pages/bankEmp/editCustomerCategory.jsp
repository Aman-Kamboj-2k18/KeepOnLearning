<%@include file="taglib_includes.jsp"%>
<div class="right-container" id="right-container">
	<div class="container-fluid">

		<div class="edit-bank-password">
			<div class="header_customer" style="margin-bottom: 30px;">
				<h3>Edit Customer Category</h3>
			</div>
			<div class="col-sm-12 col-md-12 col-lg-12">
				<div class="p_content">
					<form:form action="updateCustomerCategory"
						name="editCustomerCategory" class="form-horizontal"
						autocomplete="off" onsubmit="return validateForm()"
						commandName="customerCategoryForm">

						<div class="form_page">
							<form:hidden path="id" />
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.customerCategory" /><span style="color: red">*</span></label>
								<div class="col-md-3">
									<form:input path="customerCategory" id="customerCategory"
										class="myform-control" placeholder="" />
									<span id="customerCategoryError"
										style="display: none; color: red;"><spring:message
											code="label.validation" /></span>
								</div>
							</div>
							<span id="customerCategoryError" class="error"
								style="display: none; color: red;"><font color="red"><spring:message
										code="label.inputCategory" /></font> </span>

							
							<div class="form-group">
								<label class="col-md-4 control-label"><b><spring:message code="label.isActive" /></b><span
									style="color: red">*</span></label>
								<div class="col-md-3">
									<form:select path="isActive" id="isActive"
										class="myform-control" width="29px;!important">
										

										<form:option value="Yes">
											<spring:message code="label.yes" />
										</form:option>
										<form:option value="No">
											<spring:message code="label.no" />
										</form:option>
									</form:select>
									<span id="customerCategoryError"
										style="display: none; color: red;"><spring:message
											code="label.validation" /></span>
								</div>
							</div>
							
							<span id="isActiveError" class="error"
								style="display: none; color: red;"><font color="red">Please
									input this field.</font> </span>



							<div class="form-group">
								<label class="col-md-4 control-label"></label>
								<div class="col-md-6"><a href="addCustomerCategory"
										class="btn btn-success"><spring:message code="label.back" /></a> 
									<button class="btn btn-md btn-primary btn-block" type="submit"
										style="width: 100px;">
										<spring:message code="label.update" />
									</button>
								</div>
							</div>

							<div class="form-group">

								<div class="col-md-offset-4 col-md-8">
									 
								</div>
							</div>

						</div>
					</form:form>
				</div>
			</div>
		</div>
		<style>
.p_content {
	float: left;
	width: 100%;
	margin-bottom: 100px;
}
</style>
		<script>
	
	function validateForm() {

		var customerCategory = document.getElementById('customerCategory');
		var isActive = document.getElementById('isActive');

	

		var canSubmit = true;

		if (document.getElementById('customerCategory').value == '') {
			document.getElementById('customerCategoryError').style.display = 'block';
			canSubmit = false;
		} else {
			document.getElementById('customerCategory').borderColor = "green";
		}

		if (document.getElementById('isActive').value == '') {
			document.getElementById('isActiveError').style.display = 'block';
			document.getElementById('isActive').style.borderColor = "red";
			return false;
		} else {
			document.getElementById('isActive').style.borderColor = "green";
		}

		
		if (canSubmit == false) {
			return false;
		}

	}
</script>