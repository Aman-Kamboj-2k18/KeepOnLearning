<%@include file="taglib_includes.jsp"%>

<div class="right-container" id="right-container">
	<div class="container-fluid">

		<div class="set-rate">
			<div class="header_customer" style="margin-bottom: 10px;">
				<h3>Credit Balance</h3>
			</div>
			<div style="text-align:center;"><span style="color: red;">${error}</span>
			</div>

			<form:form action="addSavingBalance" method="post"
				id="savingAccountForm" class="form-horizontal"
				commandName="accountDetailsForm">
				<div class="successMsg">
					<b><font color="red">${success}</font></b>
				</div>
				<form:hidden path="customerID" value="${accDetails.customerID}" />
				<form:hidden path="id" value="${accDetails.id}"/>
				<input type="hidden" value="${menuId}" name="menuId"/>
				<div class="set-rate-table">
					
					<div class="form-group">
						<label class="col-md-4 control-label">Account Number</b></label>
						<div class="col-md-6">
							<form:input path="accountNo" value="${accDetails.accountNo}"
								id="accountNo" class="myform-control" readonly="true"></form:input>
						</div>
					</div>

					<div class="form-group">
						<label class="col-md-4 control-label">Account Balance</label>
						<div class="col-md-6">
						<fmt:formatNumber value="${accDetails.accountBalance}" pattern="#.##" var="accountBalance"/>
							<form:input path="accountBalance"
								value="${accountBalance}" id="accountBalance"
								class="myform-control" readonly="true"></form:input>
						</div>
					</div>

					<div class="form-group">
						<label class="col-md-4 control-label">Add Balance<span
							style="color: red">*</span></label>
						<div class="col-md-6">
							<form:input path="accountBalance2" id="addBalance"
								class="myform-control" required="true" onkeypress="isNumber(event,$(this))"></form:input>
						</div>
					</div>


					<div class="form-group">

						<div class="col-md-offset-4 col-md-8">
							<a href="viewCustomersSavingAccount?menuId=${menuId}"
								class="btn btn-success"><spring:message code="label.back" /></a> <input type="submit" size="3" value="Add Balance"
								class="btn btn-primary"> <input type="button"
								onclick="sweepDeposit()" size="3" value="Sweep Deposit"
								class="btn btn-primary"> 
						</div>
					</div>

				</div>


			</form:form>
		</div>
		<script>
		
		
		

		function isNumber(evt, obj){
			 var charCode = (evt.which) ? evt.which : evt.keyCode;
			   if (charCode != 46 && charCode > 31 
			     && (charCode < 48 || charCode > 57)){
			    event.preventDefault();
				   return false;
			   }
		}
		
			$(document).ready(function() {
				document.getElementById('addBalance').value = "";
				
				
				var addBalance = document.getElementById('addBalance');
				
				addBalance.onkeydown = function(e) {
				    if(!((e.keyCode > 95 && e.keyCode < 106)
				      || (e.keyCode > 47 && e.keyCode < 58) 
				      || e.keyCode == 8)) {
				        return false;
				    }
				    if (addBalance.value.length>12 && e.keyCode != 8){
				    	return false;
				    }
				}

			});

			function sweepDeposit() {

				$("#savingAccountForm").attr("action", "sweepDeposit");
				$("#savingAccountForm").submit();

			}
		</script>