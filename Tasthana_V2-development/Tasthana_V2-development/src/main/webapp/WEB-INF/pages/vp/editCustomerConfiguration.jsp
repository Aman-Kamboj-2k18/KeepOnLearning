<%@include file="taglib_includes.jsp"%>


<div class="right-container" id="right-container">
	<div class="container-fluid">



		<div class="set-rate">
			<div class="header_customer">
				<h3>
					<spring:message code="label.editCustomerConfiguration" />
				</h3>
			</div>
			<form:form class="form-horizontal"
				action="editCustomerConfigurationPost" method="post"
				commandName="ratesForm" id="ratesForm">

					<div class="successMsg" style="margin-top: -25px;">
						<b><font color="red">${success}</font></b>
					</div>
				<div class="set-rate-table">
					

					<div class="form-group">
						<label class="col-md-4 control-label" style="padding-top: 14px;"><spring:message
								code="label.citizen" /><span style="color: red">*</span></label>

						<div class="col-md-6">
							<form:input path="citizen" id="citizen" class="myform-control"
								readonly="true"></form:input>
						</div>

					</div>

					<div class="form-group" style="display:none" id = "accountTypeDiv">
						<label class="col-md-4 control-label" style="padding-top: 14px;"><spring:message
								code="label.accountType" /><span style="color: red">*</span></label>

								<div class="col-md-6">
									<form:input path="nriAccountType" id="nriAccountType"
										class="myform-control" readonly="true"></form:input>
								</div>

					</div>


					<div class="form-group">
						<label class="col-md-4 control-label" style="padding-top: 14px;"><spring:message
								code="label.customerCategory" /><span style="color: red">*</span></label>

						<div class="col-md-6">
							<form:input path="type" id="type" class="myform-control"
								readonly="true"></form:input>
						</div>


					</div>



				</div>

				<div class="form-group">
					<label class="col-md-4 control-label"><spring:message
							code="label.tds" /> %<span style="color: red">*</span></label>
					<div class="col-md-6">
						<form:input path="tds" id="tds" class="myform-control"
							type="number" min="0" max="99" required="true"></form:input>
					</div>

				</div>
				<div class="form-group">
					<label class="col-md-4 control-label"><spring:message
							code="label.depositFixedPercent" /><span style="color: red">*</span></label>
					<div class="col-md-6">
						<form:input path="fdFixedPercent" id="deposit"
							class="myform-control" type="number" min="0" max="99"
							onchange="onChangeFixPercent('deposit','fdVariablePercent')" required="true"></form:input>
					</div>

				</div>

				<div class="form-group">
					<label class="col-md-4 control-label" style="padding-top: 7px;"><spring:message
							code="label.minInterestForTDS" /><span style="color: red">*</span></label>
					<div class="col-md-6">
						<form:input id="minIntAmtForTDSDeduction"
							path="minIntAmtForTDSDeduction" class="myform-control"
							type="number" step=".01" required="true"></form:input>
					</div>
				</div>

				<div class="form-group">

					<label class="col-md-4 control-label"></label>
					<div class="col-md-6">
						<span id="validaionError" style="display: none; color: red"><spring:message
								code="label.radioError" /> </span> <span id="radioError"
							style="display: none; color: red"><spring:message
								code="label.radioError" /> </span> <a href="customerConfigurationFromVP"
							class="btn btn-success"><spring:message code="label.back" /></a>
						<input type="submit" size="3" onclick="return validation()"
							value="<spring:message code="label.confirm"/>"
							class="btn btn-primary">

					</div>

				</div>
			</form:form>
		</div>



	</div>
</div>
<style>
.successMsg {
	text-align: center;
	padding: 30px;
}
</style>

<script>


$( document ).ready(function() {
	
	
	var citizenType= "${ratesForm.citizen}";
	
	if(citizenType == 'NRI'){
		document.getElementById('accountTypeDiv').style.display = 'block';
	}
	
	
	var minIntAmtForTDSDeduction = document.getElementById('minIntAmtForTDSDeduction');

	  minIntAmtForTDSDeduction.onkeydown = function(e) {
		    if(!((e.keyCode > 95 && e.keyCode < 106)
		      || (e.keyCode > 47 && e.keyCode < 58) 
		      || e.keyCode == 8)) {
		        return false;
		    }
		    if (minIntAmtForTDSDeduction.value.length>15 && e.keyCode != 8){
		    	return false;
		    }
		}
	  
		
		
	  

		var deposit = document.getElementById('deposit');

	  	deposit.onkeydown = function(e) {
		    if(!((e.keyCode > 95 && e.keyCode < 106)
		      || (e.keyCode > 47 && e.keyCode < 58) 
		      || e.keyCode == 8)) {
		        return false;
		    }
		    if (deposit.value.length>15 && e.keyCode != 8){
		    	return false;
		    }
		}
	  
	  
	
	var tds = document.getElementById('tds');

	tds.onkeydown = function(e) {
	    if(!((e.keyCode > 95 && e.keyCode < 106)
	      || (e.keyCode > 47 && e.keyCode < 58) 
	      || e.keyCode == 8)) {
	        return false;
	    }
	    if (tds.value.length>15 && e.keyCode != 8){
	    	return false;
	    }
	}

});







</script>
