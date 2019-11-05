<%@include file="taglib_includes.jsp"%>


			<div class="header_customer">
				<h3 style="text-align: center"><spring:message code="label.category"/></h3>
			</div>
			<div class="add-category">
				<form:form action="addCustomer" method="post"
					commandName="customerForm" onsubmit="return validateForm()">
						<div class="ac-category" style="text-align: center;">
							<font color="red">${success}</font>
						</div>
						
						
						<table align="center" width="400">
							<tr>
									<td><b><spring:message
												code="label.category" /> :</b></td>
									<td><form:select id="category" path="category" class="myform-control">
											<form:option value="select"></form:option>
													<form:option value="General"><spring:message code="label.general"/></form:option>
													<form:option value="Disabled"><spring:message code="label.normal"/></form:option>
													<form:option value="Senior Citizen"><spring:message code="label.seniorCitizen"/></form:option>
													<form:option value="NGO"><spring:message code="label.ngo"/></form:option>
													<form:option value="Charitable Organization"><spring:message code="label.CharitableOrganization"/></form:option>
											</form:select>
											
									</td>
							</tr>
							<tr>
									<td>&nbsp;</td>
									<td><div id="categoryError" style="display: none; color: red;"><spring:message code="label.validation"/></div></td>
						  </tr>
						</table>
							<div class="col-sm-12 col-md-12 col-lg-12">
						<table align="center" class="f_deposit_btn">
							<tr>
								<td><input type="submit" class="btn btn-info" onclick="showDialog(); return false;" value="<spring:message code="label.go"/>"></td>
								<td><a href="bankEmp" class="btn btn-info"><spring:message code="label.back" /></a></td>
							</tr>
			
						</table>
					</div>
			
				</form:form>
			</div>
<script>
	function validateForm() {

		var canSubmit = true;

		if (document.getElementById('category').value == 'select') {
			document.getElementById('categoryError').style.display = 'block';
			canSubmit = false;
		} else {
			document.getElementById('categoryError').style.display = 'none';
		}

		if (canSubmit == false) {
			return false;
		}

	}
</script>