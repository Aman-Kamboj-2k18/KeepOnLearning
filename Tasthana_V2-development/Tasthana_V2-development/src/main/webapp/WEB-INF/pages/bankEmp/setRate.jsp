<%@include file="taglib_includes.jsp"%>

<div class="right-container" id="right-container">
	<div class="container-fluid">

		<div class="set-rate">
			<div class="header_customer">
				<h3>
					<spring:message code="label.setConfiguration" />
				</h3>
			</div>
			<form:form action="confirmRates" method="post"
				class="form-horizontal" autocomplete="off" commandName="ratesForm"
				onsubmit="return validateForm()">

				<div class="set-rate-table">

					<form:hidden path="transactionId" />
					<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.category" /></b><span style="color: red">*</span></label>
						<div class="col-md-6">
							<form:input path="type" class="myform-control" readonly="true"></form:input>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.tds" /> %</label>
						<div class="col-md-6">
							<form:input path="tds" id="tds" class="myform-control"
								type="number"  min="0" max="99"></form:input>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label">Deposit Fixed
							Percentage(%)</label>
						<div class="col-md-6">
							<form:input path="fdFixedPercent" id="rate" class="myform-control"
								type="number" min="0" max="99" onchange="onChangeFixPercent('rate','fdVariablePercent')"></form:input>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label">Deposit Variable
							Percentage(%)</label>
						<div class="col-md-6">
							<input type="text" id="fdVariablePercent" class="form-control"
								readonly />
						</div>

					</div>

					<div class="form-group">
						<div class="col-md-offset-4 col-md-8">
							<a href="updateRates"
								class="btn btn-success"><spring:message code="label.back" /></a>
								<input type="submit" size="3"
								value="<spring:message code="label.confirm"/>"
								class="btn btn-primary">
						</div>
					</div>

				</div>


			</form:form>
		</div>
	</div>
	<script>
		$(document).ready(function() {
			/* document.getElementById('rate').value = 0;
			document.getElementById('tds').value = 0; */
			
			var fixPercent=parseFloat(document.getElementById('rate').value) ;
			var variablePercent=100-fixPercent;
			document.getElementById('fdVariablePercent').value=variablePercent ;
			
			
		});
		
		function onChangeFixPercent(fixPercentId,variablePercentId){
			
			var fixPercent=parseFloat(document.getElementById(fixPercentId).value) ;
			if(fixPercent>100 || fixPercent<0){
				document.getElementById(fixPercentId).value=0;
				fixPercent=0;
				}
			
			var variablePercent=100-fixPercent;
			document.getElementById(variablePercentId).value=variablePercent ;
				
			}
		
		

		function validateForm() {

			if (document.getElementById('rate').value == ''
					|| document.getElementById('rate').value == null) {
				document.getElementById('rate').style.borderColor = "red";
				return false;
			} else {
				document.getElementById('rate').style.borderColor = "green";
			}

			if (document.getElementById('tds').value == ''
					|| document.getElementById('tds').value == null) {

				document.getElementById('tds').style.borderColor = "red";
				return false;
			} else {
				document.getElementById('tds').style.borderColor = "green";
			}

			if (canSubmit == false) {
				return false;
			}

		}
	</script>
	<script>
		function validateForm() {

			var canSubmit = true;

			if (document.getElementById('type').value == 'select') {
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