<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<script>
	
</script>
<div class="right-container" id="right-container">
	<div class="container-fluid">
		<div class="ledger-mapping">

			<form:form class="form-horizontal" id="ledgerEventMapping"
				name="ledgerEventMapping" autocomplete="off" method="post"
				commandName="ledgerEventMapping">
				<input type="hidden" name="menuId" value="${menuId}" id="menuId"/>
				<div class="successMsg" style="text-align: center;">
					<b><font color="red" style="font-size: 25px;">${error}</font></b>
				</div>
				<div class="list-of-rates">
					<div class="header_customer">
						<h3>Ledger Mapping</h3>
					</div>

					<div class="list-of-rates-table">
						<span class="counter pull-right"></span>

						<div>

							<div class="form-group" id="events">
								<label class="col-md-4 control-label">Event<span
									style="color: red">*</span></label>
								<div class="col-md-4">
									<form:select id="event" onchange="getMappingByEvent(this.value)" path="event" class="form-control"
										required="true">
										<form:option value="select" selected="true">
											<spring:message code="label.select" />
										</form:option>

										<c:forEach items="${eventOperationsList}" var="list1">
											<option value="${list1.eventName}">${list1.eventName}</option>
										</c:forEach>

									</form:select>
<script>
													document.getElementById('event').value = "${event}";
												</script>
								</div>
							</div>




						</div>



						<table class="table table-bordered" align="center" id="my-table">
							<thead>
								<tr>
									<th>Mode Of Payment</th>
									<th>Debit Ledger</th>
									<th>Credit Ledger</th>
									<th class="deleteRow">Delete</th>
								</tr>
							</thead>
							<tbody>


								<c:forEach items="${ledgerEventMappings}"
									var="ledgerEventMapping" varStatus="count">

									<tr id="firstTR${count.index}">

										<td><form:select onchange="validatePayment($(this))" id="modeOfPayment${count.index}" path="modeOfPayment"
												class="form-control modeOfPayment" required="true">
												<form:option value="" selected="true">
													 <spring:message code="label.select" /> 
												</form:option>

												<c:forEach items="${modeOfPaymentList}" var="list1">
													<option value="${list1.paymentMode}">${list1.paymentMode}</option>
												</c:forEach>
												<script>
												document.getElementById('modeOfPayment${count.index}').value = "${ledgerEventMapping.modeOfPayment}";
												</script>

											</form:select></td>
										<td><form:select id="debitGLAccount${count.index}"
												path="debitGLAccount" class="form-control debit" onchange="checkCredit($(this),event)" required="true">
												<form:option value="" selected="true">
													 <spring:message code="label.select" />
												</form:option>

												<c:forEach items="${glConfigurationList}" var="list1">
													<option value="${list1.glCode}">${list1.glCode}-${list1.glAccount}</option>
												</c:forEach>
												<script>
													document.getElementById('debitGLAccount${count.index}').value = "${ledgerEventMapping.debitGLCode}";
												</script>

											</form:select></td>

										<td><form:select id="creditGLAccount${count.index}"
												path="creditGLAccount" class="form-control credit" onchange="checkDebit($(this),event)" required="true">
												<form:option value="" selected="true">
													<spring:message code="label.select" />
												</form:option>

												<c:forEach items="${glConfigurationList}" var="list1">
													<option value="${list1.glCode}">${list1.glCode}-${list1.glAccount}</option>
												</c:forEach>
												<script>
												document.getElementById('creditGLAccount${count.index}').value = "${ledgerEventMapping.creditGLCode}";
												</script>

											</form:select></td>

<td class="deleteRow"><a onclick="deleteRow($(this))">Delete</a></td>
									</tr>
								</c:forEach>
							</tbody>

						</table>
						<div style="display:none" id="firstTR">
						<table>
						<tbody>
						<tr>

										<td><form:select onchange="validatePayment($(this))" id="modeOfPayment" path="modeOfPayment"
												class="form-control modeOfPayment temp" required="true">
												<form:option value="" selected="true">
													 <spring:message code="label.select" /> 
												</form:option>

												<c:forEach items="${modeOfPaymentList}" var="list1">
													<option value="${list1.paymentMode}">${list1.paymentMode}</option>
												</c:forEach>
												<script>
												
												</script>

											</form:select></td>
										<td><form:select id="debitGLAccount"
												path="debitGLAccount" class="form-control debit temp" onchange="checkCredit($(this),event)" required="true">
												<form:option value="" selected="true">
													 <spring:message code="label.select" />
												</form:option>

												<c:forEach items="${glConfigurationList}" var="list1">
													<option value="${list1.glCode}">${list1.glCode}-${list1.glAccount}</option>
												</c:forEach>
												

											</form:select></td>

										<td><form:select id="creditGLAccount"
												path="creditGLAccount" class="form-control credit temp" onchange="checkDebit($(this),event)" required="true">
												<form:option value="" selected="true">
													<spring:message code="label.select" />
												</form:option>

												<c:forEach items="${glConfigurationList}" var="list1">
													<option value="${list1.glCode}">${list1.glCode}-${list1.glAccount}</option>
												</c:forEach>
												

											</form:select></td>
<td class="deleteRow"><a onclick="deleteRow($(this))">Delete</a></td>

									</tr>
								</tbody></table>
						</div>

						<div class="errorMsg">
							<font color="red"
								style="text-align: center; width: 100%; float: left;"
								id="validationError">${error}</font>
						</div>

						<div class="col-md-offset-4 col-md-8"
							style="padding-bottom: 22px;">
							
							<input type="button" size="3" onclick="addRow()" value="addRow"
								class="btn btn-primary">

						</div>

						<div class="col-md-offset-4 col-md-8"
							style="padding-bottom: 22px;">
							<input id="btnSave" type="button" size="3" onclick="return addLedgerMapping()"
								value="save" class="btn btn-primary">

						</div>

					</div>


				</div>
			</form:form>
		</div>
	</div>
</div>
<script>
function checkCredit(obj,e){
	debugger;
	if(obj.val()==obj.closest('tr').find('.credit').val()){
		alert('Ledger value for credit and debit can not be same');
		obj.val('');
		e.preventDefault();
	}
	
}

function checkDebit(obj,e){
	debugger;
	if(obj.val()==obj.closest('tr').find('.debit').val()){
		alert('Ledger value for credit and debit can not be same');
		obj.val('');
		e.preventDefault();
	}
	
}
	function addRow() {

		var html = $('#firstTR table tbody').html();
		html=html.replace(/ temp/g, " ");
		$("#my-table tbody").append(html);
	}
	
	function getMappingByEvent(value) {
		debugger;
		$("#event").val(value);
		
		var events = document.getElementById('event').value;
		var submit = true;
		var errorMsg = "";
		var validationError = document.getElementById('validationError');
		validationError.innerHTML = "";

		if (events == "select" || events == "") {
			document.getElementById('event').style.borderColor = "red";
			errorMsg = "<br>Please Select Event";
			validationError.innerHTML += errorMsg;
			submit = false;
		} else {
			document.getElementById('event').style.borderColor = "black";

		}

		if (submit == true) {
			var location=window.location.href;
			if(location.indexOf('event')>0){
				var index=location.indexOf('&');
				location=location.substring(0, index);
			}
			
			location=location +"&event="+events;
			window.location.href=location;
			
		} else {
			return false;
		}
		
		
	}

	function addLedgerMapping() {
		debugger;
		var events = document.getElementById('event').value;
		var submit = true;
		var errorMsg = "";
		var validationError = document.getElementById('validationError');
		validationError.innerHTML = "";

		if (events == "select" || events == "") {
			document.getElementById('event').style.borderColor = "red";
			errorMsg = "<br>Please Select Event";
			validationError.innerHTML += errorMsg;
			submit = false;
		} else {
			document.getElementById('event').style.borderColor = "black";

		}
		debugger;
		$('.modeOfPayment').each(function(){
			if($(this).hasClass('temp')){
				return false;
			}
			if($(this).val()==''){
				errorMsg = "<br>Please select mode of payment";
				validationError.innerHTML += errorMsg;
				submit = false;
				
			}
		});
		
		$('.debit').each(function(){
			if($(this).hasClass('temp')){
				return false;
			}
			if($(this).val()==''){
				errorMsg = "<br>Please select debit mode";
				validationError.innerHTML += errorMsg;
				submit = false;
				
			}
		});
		
		$('.credit').each(function(){
			if($(this).hasClass('temp')){
				return false;
			}
			if($(this).val()==''){
				errorMsg = "<br>Please select credit mode";
				validationError.innerHTML += errorMsg;
				submit = false;
				
			}
		});
		
		var rows=$('#my-table').find('tr').length;
		if(rows==1){
			errorMsg = "<br>Please add atleast one ledger";
			validationError.innerHTML += errorMsg;
			submit = false;
		}
		

		if (submit == true) {
			$("#ledgerEventMapping").attr("action", "saveLedgerMapping");
			$("#ledgerEventMapping").submit();
		} else {
			return false;
		}

	}
	
	function validatePayment(obj){
		debugger;
		var modeOfPayment=obj.val();
		$('.modeOfPayment').each(function(){
			if($('select').index($(this))==$('select').index(obj)){
				//ignore
			}
			else{
				if($(this).val()==modeOfPayment){
					obj.val('');
					alert('Mode of payment can not be same for multiple ledgers');
				}
			}
		});
	}
	function deleteRow(obj){
		obj.closest('tr').remove();s
	}
	
	var vars = [], hash;
	function getUrlVars()
	{
	   
	    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
	    for(var i = 0; i < hashes.length; i++)
	    {
	        hash = hashes[i].split('=');
	        vars.push(hash[0]);
	        vars[hash[0]] = hash[1];
	    }
	    return vars;
	}


	$(document).ready(function(){
		debugger;
		var menuId=getUrlVars()["menuId"];
		var permissions=$("#menu_"+menuId).attr("permissions");
		//alert(permissions);
		if(permissions.toLowerCase().indexOf("write")<0){
			$('.btn-primary').hide();
			$('.deleteRow').hide();
			
		}
	});
</script>
