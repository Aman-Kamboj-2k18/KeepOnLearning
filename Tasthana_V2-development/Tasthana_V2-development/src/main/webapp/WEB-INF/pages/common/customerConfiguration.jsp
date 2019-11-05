<%@include file="taglib_includes.jsp"%>


<div class="right-container" id="right-container">
	<div class="container-fluid">



		<div class="set-rate">
			<div class="header_customer">
				<h3>
					<spring:message code="label.customerConfiguration" />
				</h3>
			</div>
			<form:form class="form-horizontal"
				action="confirmcustomerConfiguration" method="post"
				commandName="ratesForm" id="bankConfigurationForm">
 <input type="hidden" name="menuId" id="menuId" value="${menuId}" />

				<%-- 			<div  class="successMsg" style="margin-top: -15px;"><b><font color="red">${success}</font></b></div>
 --%>
				<div class="successMsg" style="margin-top: -25px;">
					<b><font color="red">${error}</font></b>
				</div>
				<div class="set-rate-table">
					<div style="clear: both; margin-top: -15px;">

						<div class="form-group" id="ripDiv">
							<label class="col-md-4 control-label"><spring:message
									code="label.citizen" /><span style="color: red">*</span></label>
							<div class="col-md-8">

								<label for="radio"> <form:radiobutton path="citizen"
										id="RIRadio" name="citizen" value="RI" onclick="citizenFun()"
										checked="true"></form:radiobutton></label>
								<spring:message code="label.ri" />
								<label for="radio"> <form:radiobutton path="citizen"
										id="NRIRadio" name="citizen" value="NRI"
										onclick="citizenFun()"></form:radiobutton></label>
								<spring:message code="label.nri" />
							</div>
						</div>

					
						<div class="form-group">
							<label class="col-md-4 control-label" style="padding-top: 14px;"><spring:message
									code="label.customerCategory" /><span style="color: red">*</span></label>

							<c:choose>
								<c:when test="${setCategory!=null}">
									<div class="col-md-4">



										<form:select id="setCategory" path="type"
											class="myform-control" onchange="changeCategory($(this))">">
											<option value="">
												<spring:message code="label.select" />
											</option>
											<c:forEach var="item" items="${setCategory}">
												<option nriAccountType="${item.nriAccountType}" citizen="${item.citizen}" value="${item.customerCategory}"
													<c:if test="${item.customerCategory eq selectedCategory}"><spring:message code="label.selectedSmall"/></c:if>>${item.customerCategory}</option>
											</c:forEach>
										</form:select>


									</div>
								</c:when>
								<c:otherwise>
									<form:select id="setCategory" path="type"
										class="myform-control">
										<form:option value="select"></form:option>

									</form:select>

								</c:otherwise>
							</c:choose>


						</div>

	<div class="form-group" style="clear:both;display:none"
							 id="accountTypeDIV">
							<label class="col-md-4 control-label" style="padding-top: 14px;"><spring:message
									code="label.accountType" /><span style="color: red">*</span></label>


							<div class="col-md-4">
								<form:select id="nriAccountType" path="nriAccountType"
									class="myform-control" onchange="nriAccType(this.value)">
									<form:option value="">
										<spring:message code="label.select" />
									</form:option>
									<%-- <form:option value="NRE">
										<spring:message code="label.nre" />
									</form:option>
									<form:option value="NRO">
										<spring:message code="label.nro" />
									</form:option>
									<form:option value="FCNR">
										<spring:message code="label.fcnr" />
									</form:option>
									<form:option value="RFC">
										<spring:message code="label.rfc" />
									</form:option>
									<form:option value="PRP">
										<spring:message code="label.prp" />
									</form:option> --%>
								</form:select>
							</div>
						</div>


						<div class="form-group" id='tdsDiv'>
							<label class="col-md-4 control-label"><spring:message
									code="label.tds" /> %<span style="color: red">*</span></label>
							<div class="col-md-4">
								<form:input path="tds" id="tds" onkeypress="return isNumberKey1(event,$(this))" class="myform-control"
									type="text" min="0" max="99"></form:input>
							</div>

						</div>
						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.depositFixedPercent" /><span style="color: red">*</span></label>
							<div class="col-md-4">
								<form:input path="fdFixedPercent" id="deposit" onkeypress="return isNumberKey1(event,$(this))"
									class="myform-control" type="text" min="0" max="99"
									onchange="onChangeFixPercent('deposit','fdVariablePercent')"></form:input>
							</div>

						</div>

						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.depositVariablePercent" /></label>

							<div class="col-md-4">
								<input type="text" id="fdVariablePercent" class="myform-control"
									readonly />
							</div>

						</div>
						
						

						<div class="form-group" id="minIntAmtForTDSDeductionDiv">
							<label class="col-md-4 control-label" style="padding-top: 7px;"><spring:message
									code="label.minInterestForTDS" /><span style="color: red">*</span></label>
							<div class="col-md-4">
								<form:input id="minIntAmtForTDSDeduction" onkeypress="return isNumberKey1(event,$(this))" 
									path="minIntAmtForTDSDeduction" class="myform-control"
									type="text" step=".01"></form:input>
							</div>
						</div>
						
						<div class="form-group">

							<label class="col-md-4 control-label"></label>
							<div class="col-md-4">
								<span id="validaionError" style="display: none; color: red"><spring:message
										code="label.radioError" /> </span> <span id="radioError"
									style="display: none; color: red"><spring:message
										code="label.radioError" /> </span> <%-- <a href="bankEmp"
									class="btn btn-success"><spring:message code="label.back" /></a> --%>
								<input type="submit" size="3" onclick="return validation()"
									value="<spring:message code="label.confirm"/>"
									class="btn btn-primary">

							</div>

						</div>

					</div>

					<table
						class="table data jqtable example table-bordered table-striped table-hover"
						id="my-table" style="width: 95%; margin-left: 25px;">


						<thead>
							<tr>
<%-- 								<th style="background: darkcyan; color: #ffffff;"><spring:message --%>
<%-- 										code="label.id" /></th> --%>
								<th style="background: darkcyan; color: #ffffff;"><spring:message
										code="label.citizen" /></th>
								<th style="background: darkcyan; color: #ffffff;"><spring:message
										code="label.accountType" /></th>
								<th style="background: darkcyan; color: #ffffff;"><spring:message
										code="label.customerCategory" /></th>
								<th style="background: darkcyan; color: #ffffff;"><spring:message
										code="label.tds" />%</th>
								<th style="background: darkcyan; color: #ffffff;"><spring:message
										code="label.depositFixedPercent" /></th>
								<th style="background: darkcyan; color: #ffffff;"><spring:message
										code="label.minInterestForTDS" /></th>
								<th style="background: darkcyan; color: #ffffff;"><spring:message
										code="label.action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${! empty rates}">

								<c:forEach items="${rates}" var="rate">

									<tr>
<%-- 										<td><c:out value="${rate.id}"></c:out></td> --%>
										<td><c:out value="${rate.citizen}"></c:out></td>
										<td><c:out value="${rate.nriAccountType}"></c:out></td>
										<td><c:out value="${rate.type}"></c:out></td>
										<td><c:out value="${rate.tds}"></c:out></td>
										<td><c:out value="${rate.fdFixedPercent}"></c:out></td>
										<td><c:out value="${rate.minIntAmtForTDSDeduction}"></c:out></td>
										<td><a
											href="editCustomerConfiguration?nriAccountType=${rate.nriAccountType}&citizen=${rate.citizen}&type=${rate.type}&menuId=${menuId}"
											class="btn btn-success"><spring:message code="label.edit" /></a></td>


									</tr>

								</c:forEach>
							</c:if>

						</tbody>

					</table>






				</div>


			</form:form>
		</div>
	</div>
	<style>
.successMsg {
	text-align: center;
	padding: 30px;
}
</style>

	<script>
	function isNumberKey1(evt,obj)
	{
		
	   var charCode = (evt.which) ? evt.which : evt.keyCode;
	   if (charCode != 46 && charCode > 31 
	     && (charCode < 48 || charCode > 57)){
	      return false;
	   }else{
	 	  var splitValue = obj.val();
	          var res = splitValue.split(".");
	          if(splitValue.includes(".")){
	          var spVal = res[1];
	          if(spVal.length > 1){
	        	  evt.preventDefault();
	        	  return false;
	          }
	          return true;
	          }else{
	      
	         
	 	  return true;
	          }
	   }
	  }
		function nriAccType(value) {
			if (value == 'FCNR' || value == 'NRE' || value == 'PRP') {
				document.getElementById("minIntAmtForTDSDeductionDiv").style.display = 'none';
				document.getElementById("minIntAmtForTDSDeduction").value = "";
				document.getElementById("tdsDiv").style.display = 'none';
				document.getElementById("tds").value = 0.0;
			} else {
				document.getElementById("minIntAmtForTDSDeductionDiv").style.display = 'block';
				document.getElementById("tdsDiv").style.display = 'block';
			}

			/* 	if(value == 'FCNR' || value == 'NRI' ||  value == 'NRE' || value == 'PRP'){
			 document.getElementById("tdsDiv").style.display = 'none';
			 document.getElementById("tds").value = 0.0;
			 }
			 else{
			 document.getElementById("tdsDiv").style.display = 'block';
			 } */
		}

		function validation() {

			var submit = true;

			var RIRadioChecked = document.getElementById("RIRadio").checked;
			var NRIRadioChecked = document.getElementById("NRIRadio").checked;
			var nriAccountType = document.getElementById('nriAccountType').value;
			var setCategory = document.getElementById("setCategory").value;
			var tds = document.getElementById("tds").value;
			var deposit = document.getElementById("deposit").value;
			var minIntAmtForTDSDeduction = document
					.getElementById("minIntAmtForTDSDeduction").value;
			var fdVariablePercent = document
					.getElementById("fdVariablePercent").value;

			if (RIRadioChecked == false && NRIRadioChecked == false) {
				document.getElementById('radioError').style.display = 'block';

				return false;
			}
			if (NRIRadioChecked == true) {

				if (nriAccountType == "") {
					document.getElementById('nriAccountType').style.borderColor = 'red';

					return false;
				} else {
					document.getElementById('nriAccountType').style.borderColor = 'blue';

				}

				if (setCategory == "") {
					document.getElementById('setCategory').style.borderColor = 'red';

					return false;
				} else {
					document.getElementById('setCategory').style.borderColor = 'blue';

				}

				if (tds == 0 && nriAccountType != 'NRE'
						&& nriAccountType != 'FCNR' && nriAccountType != 'PRP') {
					document.getElementById('tds').style.borderColor = 'red';

					return false;
				} else {
					document.getElementById('tds').style.borderColor = 'blue';

				}

				if (deposit == 0) {
					document.getElementById('deposit').style.borderColor = 'red';

					return false;
				} else {
					document.getElementById('deposit').style.borderColor = 'blue';

				}

				if (minIntAmtForTDSDeduction == "" && nriAccountType != 'NRE'
						&& nriAccountType != 'FCNR' && nriAccountType != 'PRP') {
					document.getElementById('minIntAmtForTDSDeduction').style.borderColor = 'red';

					return false;
				} else {
					document.getElementById('minIntAmtForTDSDeduction').style.borderColor = 'blue';

				}

			}

			if (RIRadioChecked == true) {
				document.getElementById('nriAccountType').value = "";

				if (setCategory == "") {
					document.getElementById('setCategory').style.borderColor = 'red';

					return false;
				} else {
					document.getElementById('setCategory').style.borderColor = 'blue';

				}
				if (tds == 0) {
					document.getElementById('tds').style.borderColor = 'red';

					return false;
				} else {
					document.getElementById('tds').style.borderColor = 'blue';

				}

				if (deposit == 0) {
					document.getElementById('deposit').style.borderColor = 'red';

					return false;
				} else {
					document.getElementById('deposit').style.borderColor = 'blue';

				}

				if (minIntAmtForTDSDeduction == "") {
					document.getElementById('minIntAmtForTDSDeduction').style.borderColor = 'red';

					return false;
				} else {
					document.getElementById('minIntAmtForTDSDeduction').style.borderColor = 'blue';

				}
			}
			var menuId = '${menuId}';

			$("#bankConfigurationForm").attr("action",
					"customerConfiguration?RIRadio=RI?&menuId="+menuId);
			$("#bankConfigurationForm").submit();

			return submit;
		}

		$(document)
				.ready(
						function() {
							debugger

							/* var minIntAmtForTDSDeduction = document
									.getElementById('minIntAmtForTDSDeduction');

							minIntAmtForTDSDeduction.onkeydown = function(e) {
								if (!((e.keyCode > 95 && e.keyCode < 106)
										|| (e.keyCode > 47 && e.keyCode < 58) || e.keyCode == 8)) {
									return false;
								}
								if (minIntAmtForTDSDeduction.value.length > 15
										&& e.keyCode != 8) {
									return false;
								}
							} */

							/* var deposit = document.getElementById('deposit');

							deposit.onkeydown = function(e) {
								if (!((e.keyCode > 95 && e.keyCode < 106)
										|| (e.keyCode > 47 && e.keyCode < 58) || e.keyCode == 8)) {
									return false;
								}
								if (deposit.value.length > 15 && e.keyCode != 8) {
									return false;
								}
							}
							*/
							var tds = document.getElementById('tds');

							tds.onkeydown = function(e) {

								if (tds.value.length > 15 && e.keyCode != 8) {
									return false;
								}
							}

							var NRIRadioChecked = document
									.getElementById("NRIRadio").checked;
							var RIRadioChecked = document
									.getElementById("RIRadio").checked;
							if (RIRadioChecked == true)
								document.getElementById("accountTypeDIV").style.display = 'none';

							var fixPercent = parseFloat(document
									.getElementById('deposit').value);
							var variablePercent = 100 - fixPercent;
							var citizenType = '${citizenType}';
							var selectedCategory = '${selectedCategory}';
							document.getElementById('fdVariablePercent').value = variablePercent;
							var nriAccountType = document
									.getElementById("nriAccountType").value;

						});

		function onChangeFixPercent(fixPercentId, variablePercentId) {

			var fixPercent = parseFloat(document.getElementById(fixPercentId).value);
			if (fixPercent > 100 || fixPercent < 0) {
				document.getElementById(fixPercentId).value = 0;
				fixPercent = 0;
			}

			var variablePercent = 100 - fixPercent;
			document.getElementById(variablePercentId).value = variablePercent;

		}
		
		function changeCategory(obj){
			debugger;
			var html="<option value=''>Select</option>";
			var NRIRadioChecked = document.getElementById("NRIRadio").checked;
			var nriAccounts=$('option:selected', obj).attr('nriaccounttype');
			if(NRIRadioChecked== true && nriAccounts!=undefined){
				//var nriAccounts=obj.attr('nriaccounttype');
				//var nriAccounts=$('option:selected', obj).attr('nriaccounttype');
				var array = nriAccounts.split(',');
				$.each(array, function( index, value ) {
					html +="<option value='"+value+"'>"+value+"</option>";
					});
			}
			
			$('#nriAccountType').html(html).val('').change();
		}
		
		
		function citizenFun(){
			
			var RIRadioChecked = document.getElementById("RIRadio").checked;
			var NRIRadioChecked = document.getElementById("NRIRadio").checked;
			if(RIRadioChecked==true){
				document.getElementById('accountTypeDIV').style.display = 'none';
				
				document.getElementById('nriAccountType').value="";
				$('#setCategory option').each(function(){
					if($(this).attr('citizen')=="RI"){
						$(this).show();
					}
					if($(this).attr('citizen')=="NRI"){
						$(this).hide();
					}
				});
			}
			if(NRIRadioChecked== true){
				document.getElementById('accountTypeDIV').style.display = 'block';
				
				$('#setCategory option').each(function(){
					if($(this).attr('citizen')=="RI"){
						$(this).hide();
					}
					if($(this).attr('citizen')=="NRI"){
						$(this).show();
					}
				});
			}
			$('#setCategory').val('').change();
		}
		$(document).ready(function(){
			 citizenFun();
		});
		
		
		

		function citizenFun() {

			var RIRadioChecked = document.getElementById("RIRadio").checked;
			var NRIRadioChecked = document.getElementById("NRIRadio").checked;
			if (RIRadioChecked == true) {
				document.getElementById('validaionError').style.display = 'none';
				document.getElementById('accountTypeDIV').style.display = 'none';
				document.getElementById('nriAccountType').value = "";

			}

			document.getElementById("nriAccountType").value = "";
			document.getElementById("tds").value = 0.0;
			document.getElementById("minIntAmtForTDSDeduction").value = "";
			document.getElementById("deposit").value = 0.0;

			if (NRIRadioChecked == true) {
				document.getElementById('radioError').style.display = 'none';
				document.getElementById('validaionError').style.display = 'none';
				document.getElementById('accountTypeDIV').style.display = 'block';
				$('#setCategory option').each(function(){
					if($(this).attr('citizen')=="RI"){
						$(this).hide();
					}
					if($(this).attr('citizen')=="NRI"){
						$(this).show();
					}
				});

			}
			if (RIRadioChecked == true) {
				document.getElementById('radioError').style.display = 'none';
				document.getElementById("nriAccountType").value = "";
				$('#setCategory option').each(function(){
					if($(this).attr('citizen')=="RI"){
						$(this).show();
					}
					if($(this).attr('citizen')=="NRI"){
						$(this).hide();
					}
				});
			}
			$('#setCategory').val('').change();
		}
		
		function isNumberKey1(evt,obj)
		{
			
		   var charCode = (evt.which) ? evt.which : evt.keyCode;
		   if (charCode != 46 && charCode > 31 
		     && (charCode < 48 || charCode > 57)){
		      return false;
		   }else{
		 	  var splitValue = obj.val();
		          var res = splitValue.split(".");
		          if(splitValue.includes(".")){
		          var spVal = res[1];
		          if(spVal.length > 1){
		        	  evt.preventDefault();
		        	  return false;
		          }
		          return true;
		          }else{
		      
		         
		 	  return true;
		          }
		   }
		  }

	</script>