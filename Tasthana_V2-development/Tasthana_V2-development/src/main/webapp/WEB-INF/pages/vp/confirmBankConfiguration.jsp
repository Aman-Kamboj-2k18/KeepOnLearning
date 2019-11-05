<%-- <%@include file="taglib_includes.jsp"%>


<div class="set-rate">
			<div class="header_customer">
				<h3 style="text-align: center"><spring:message code="label.bankconfiguration"/></h3>
			</div>
	<form:form action="saveBankConfiguration" method="post"
		commandName="bankConfigurationForm" onsubmit="return validateForm()">
			<div  class="successMsg"><b><font color="red">${success}</font></b></div>
				<form:hidden path="id"/>
				
			<div class="set-rate-table">

				<table align="center" width="400">
					
					
					<tr>
						<td><b><spring:message code="label.interestCalculationBasis"/></b></td>
						<td><form:input path="interestCalculationBasis"  class="myform-control" readonly="true"></form:input>
							</td>
					</tr>
					
					
				
					<tr>
					    <td><b><spring:message code="label.minbalforautodeposit"/></b><span style="color:red">*</span></td>
						<td><form:input id="minBalanceForAutodeposit" path="minBalanceForAutodeposit" readonly="true" class="myform-control" required></form:input></td>													
					</tr>
				   			 
				</table>
				<div class="space-10"></div>
				<table align="center" style="position: relative; left: 6.3%;">
					<tr>
						<td><input type="submit" size="3" value="<spring:message code="label.confirm"/>"
							class="btn btn-primary"></td>
							<td>&nbsp;&nbsp;</td>
						<td><a href="bankConfiguration" class="btn btn-success"><spring:message code="label.back"/></a></td>
					</tr>
				</table>

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
	
	$(function() {
		$("#datepicker").datepicker({
			format : 'dd/mm/yyyy'
		});
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
</script> --%>