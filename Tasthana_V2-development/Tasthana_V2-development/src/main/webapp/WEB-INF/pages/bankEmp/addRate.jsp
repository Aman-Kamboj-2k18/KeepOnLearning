<%@include file="taglib_includes.jsp"%>

<div class="right-container" id="right-container">
        <div class="container-fluid">
			
<div class="set-rate">
			<div class="header_customer" style="margin-bottom:30px;">
				<h3><spring:message code="label.addRate"/></h3>
			</div>
	<form:form action="confirmAddRates" method="post" class="form-horizontal" commandName="ratesForm" onsubmit="return validateForm()">
			<div  class="successMsg"><b><font color="red">${success}</font></b></div>
				
				
			<div class="set-rate-table">
			
			<form:hidden path="transactionId"/>

			<div class="form-group">
				<label class="col-md-4 control-label">Type <span style="color:red">*</span></label>
				<div class="col-md-6">
					<form:select id="type" path="type" class="myform-control">
							<form:option value="Select"></form:option>
								<form:option value="General"><spring:message code="label.general"/></form:option>
								<form:option value="Disabled"><spring:message code="label.normal"/></form:option>
								<form:option value="Senior Citizen"><spring:message code="label.seniorCitizen"/></form:option>
								<form:option value="NGO"><spring:message code="label.ngo"/></form:option>
								<form:option value="Charitable Organization"><spring:message code="label.CharitableOrganization"/></form:option>
							</form:select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-4 control-label"><spring:message code="label.tds"/></b><span style="color:red">*</span></label>
				<div class="col-md-6">
					<form:input path="tds" id="rate" class="myform-control" ></form:input>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-4 control-label"><spring:message code="label.ses"/><span style="color:red">*</span></label>
				<div class="col-md-6">
					<form:input path="ses" id="tds" class="myform-control" ></form:input>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-4 control-label"><spring:message code="label.service"/><span style="color:red">*</span></label>
				<div class="col-md-6">
					<form:input path="service" id="ses" class="myform-control" ></form:input>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-4 control-label">Deposit Fixed Percentage(%)<span style="color:red">*</span></label>
				<div class="col-md-6">
					<form:input path="fdFixedPercent" id="service" class="myform-control" ></form:input>
				</div>
			</div>
			

			<div class="form-group">
			
				<div class="col-md-offset-4 col-md-8">
					<input type="submit" size="3" value="<spring:message code="label.confirm"/>" class="btn btn-primary">
					<a href="bankEmp" class="btn btn-success"><spring:message code="label.back"/></a>
				</div>
			</div>

		</div>


	</form:form>
</div>
<script>

	$(document).ready(function() {
		document.getElementById('type').value = "Select";
		document.getElementById('rate').value = 0;
		document.getElementById('tds').value = 0;
		document.getElementById('ses').value = 0;
		document.getElementById('service').value = 0;
	});
	

	function validateForm() {

		if (document.getElementById('type').value == '' || document.getElementById('type').value == 'Select') {
			document.getElementById('type').style.borderColor = 'red';
			return false;
		} else {
			document.getElementById('type').style.borderColor = 'green';
		}
		
		if (document.getElementById('rate').value == '' || document.getElementById('rate').value == null ) {
			document.getElementById('rate').style.borderColor = "red";
			return false;
		} else {
			document.getElementById('rate').style.borderColor = "green";
		}
		
		if (document.getElementById('tds').value == '' ||document.getElementById('tds').value == null) {

			document.getElementById('tds').style.borderColor = "red";
			return false;
		} else {
			document.getElementById('tds').style.borderColor = "green";
		}
		
		if (document.getElementById('ses').value == '' ||document.getElementById('ses').value == null) {

			document.getElementById('ses').style.borderColor = "red";
			return false;
		} else {
			document.getElementById('ses').style.borderColor = "green";
		}
		
		if (document.getElementById('service').value == '' ||document.getElementById('service').value == null) {

			document.getElementById('service').style.borderColor = "red";
			return false;
		} else {
			document.getElementById('service').style.borderColor = "green";
		}	
		
		if (canSubmit == false) {
			return false;
		}

	}
</script>