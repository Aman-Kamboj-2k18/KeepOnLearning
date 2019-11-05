
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script src="<%=request.getContextPath()%>/resources/js/validation.js"></script>
 
<script>

$(document).ready(function(){
	
	
	
	
	$('#singleDD').change(function(){
		debugger;
		$('.holders').prop("checked",false);
		$('#chequeAmount').val('');
		$('#chequeName').val('');
		if($('#singleDD').prop("checked")==true){
			$('#chequeDdDiv').show();
			$('#multiplechequeDdDiv').hide();
			$('#multipleHolders').html('');
			
		}else{
			$('#chequeDdDiv').hide();
			$('#multiplechequeDdDiv').show();
		}
		
	});
	showDetails();
});


	function searchDepost() {
		
		debugger;
		
		
		var accno = document.getElementById('fdID').value;
		
		if(accno == ''){
			document.getElementById('accError').style.display = 'block';
			return false;
		}
		
		
		$("#wait").show();
		disableScreen();
		

		if (accno != '') {
			
			setTimeout(function(){
				$("body").attr("style", "opacity: 0.5");
				$("#wait").hide();
				
				
			}, 8000);
			document.getElementById('accError').style.display = 'none';
			$("#fdForm").attr("action", "findClosedDeposit");
			$("#fdForm").submit();
			
		} else {

			document.getElementById('accError').style.display = 'block';
			return false;
		}
	}


function getTodaysDate()
{
	var today = null;
	$.ajax({  
	    type: "GET",  
	    async: false,
	    url: "<%=request.getContextPath()%>/common/loginDateForJsp",
			contentType : "application/json",
			dataType : "json",

			success : function(response) {
				//	window.loginDateForFront = new Date(parseInt(response));
				today = new Date(parseInt(response))
			},
			error : function(e) {
				$('#error').html("Error occured!!")
				// window.loginDateForFront = getTodaysDate();
			}
		});
		return today;
	}
	function onclickRadio(customerName, depositHolderId, depositId, appStatus,
			newMaturityDate, email) {
		debugger
		var canSubmit = true;
		var today = getTodaysDate();
		var dd = today.getDate();
		var mm = today.getMonth() + 1; //January is 0!
		var yyyy = today.getFullYear();
		var payAndWithdrawTenure = parseFloat('${payAndWithdrawTenure}');
		if (dd < 10) {
			dd = '0' + dd
		}

		if (mm < 10) {
			mm = '0' + mm
		}

		today = yyyy + '-' + mm + '-' + dd;

		var splitMaturityDate = newMaturityDate.split(" ");
		var newSplittedMaturity = splitMaturityDate[0];
		var diff = Math.floor((Date.parse(newSplittedMaturity) - Date
				.parse(today)) / 86400000);
		debugger
		if (diff < payAndWithdrawTenure) {

			document.getElementById('dayError').innerHTML = 'Your deposit will be matured by next '
					+ diff + ' days. So you can not make payment.';
			document.getElementById('fdPay').disabled = true;
			return false;

		}
		/* else{
			document.getElementById('dayError').innerHTML = '';
			document.getElementById('fdPay').disabled=false;
			
		} */
		document.getElementById('appStatusError').style.display = 'none';

		document.getElementById('customerName').value = customerName;
		document.getElementById('depositHolderId').value = depositHolderId;
		document.getElementById('depositId').value = depositId;

		if (appStatus == 'Pending') {
			document.getElementById('appStatusError').style.display = 'block';
			document.getElementById("goBtn").disabled = true;
			document.getElementById("goBtn").title = "Status is pending";
		} else {
			var paymentMode = document.getElementById('fdPay').value;

			if (paymentMode != "Select") {
				document.getElementById("goBtn").disabled = false;
				document.getElementById("goBtn").title = "";
			} else {
				document.getElementById("goBtn").disabled = true;
				document.getElementById("goBtn").title = "Please select mode of payment";
			}

		}
	}

	function search() {

		$("#fdForm").attr("action", "fdPaymentDetails");
		$("#fdForm").submit();

	}

	function showDetails() {
debugger;
		if ($("#fdPay").val() == 'DD') {
			$(".chequeDDNoLabel").text("DD Number");
			$(".chequeAndDD_DIV").show();
			$(".chequeDDNameLabel").text("DD Name");
			$(".chequeAndDDName_DIV").show();
		} else if ($("#fdPay").val() == 'Cheque') {
			$(".chequeDDNoLabel").text("Cheque Number");
			$(".chequeAndDD_DIV").show();
			$(".chequeDDNameLabel").text("Cheque Name");
			$(".chequeAndDDName_DIV").show();
		} else {
			$(".chequeAndDD_DIV").hide();$(".chequeAndDDName_DIV").hide();
		}

		/* var paymentMode = document.getElementById('fdPay').value;
		var selectRadio = false;
		var inp = document.querySelectorAll(".selectRadio");
		
		for(i=0;i<inp.length;i++){
			if(inp[i].checked==true){
				selectRadio=true;
				break;
				
			}
				
		}
		
		
		

		if (selectRadio == true && paymentMode != 'Select') {
			document.getElementById("goBtn").disabled = false;
			document.getElementById("goBtn").title = "";
		} else {

			document.getElementById("goBtn").disabled = true;
			if (paymentMode == 'Select' && selectRadio == true)
				document.getElementById("goBtn").title = "Please select mode of payment";
			else if (selectRadio == false && paymentMode != 'Select')
				document.getElementById("goBtn").title = "Please select deposit first";
			else
				document.getElementById("goBtn").title = "Please select mode of payment and deposit first";
		} */

	}
</script>
<div class="right-container" id="right-container">


	<div class="container-fluid">

		<div class="Flexi_deposit">


			<div class="header_customer" style="margin-bottom: 25px;">
				<h3>Bank Payment on Maturity</h3>
			</div>
			<div class="col-md-12" style="margin: 10px auto; text-align: center;">
				<span style="color: red;">${error}</span>
			</div>
			<div class="col-md-12"
				style="padding: 10px auto; text-align: center;">
				<span style="color: red;">${taxError}</span>
			</div>
			<div class="flexi_table">
				<form:form class="form-horizontal" id="fdForm" method="post"
					name="depositList" commandName="fixedDepositForm">
					<form:hidden id="depositId" path="depositId" value="${deposit.id}"/>
					<input type="hidden" name="menuId" "value="${menuId}"/>
					<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.fdAccountNo" /><span style="color: red">*</span></label>
						<div class="col-md-4">
							<form:input path="accountNo" placeholder="Enter Account Number"
								id="fdID" class="myform-control" required="true" />
							<span id='accError' style="display: none; color: red;"></span> <span
								id="appStatusError" style="display: none; color: red;">This
								deposit is not Approved</span>
							<c:if test="${depositList[0].approvalStatus=='Pending'}">
								<span style="color: red">Deposit is pending</span>
							</c:if>

						</div>



						<div class="col-md-2">
							<input type="submit" class="btn btn-primary"
								onclick="searchDepost()" value="Search">

						</div>

						<div id="wait"
							style="display: none; width: 55px; height: 60px; position: absolute; top: 50%; left: 50%; padding: 2px;">
							<img
								src='https://www.skillstat.com/wp-content/themes/anvil/img/bx_loader.gif'
								width="150" height="150" /><br>
						</div>

					</div>
					<div class="col-sm-12">
						<div class="space-45"></div>



					</div>

					<c:if test="${not empty deposit}">
						<table class="table table-striped table-bordered"
							style="width: 95%; margin-left: 23px;">
							<tr>
								<td><b>Deposit Id</b></td>
								<td><b>Created Date</b></td>
								<td><b>Maturity Date</b></td>
								<td><b>Rate of Interest</b></td>
								<td><b>Deposit Classification</b></td>
								<td><b>Product Code</b></td>
								<td><b>Amount</b></td>
							</tr>


							<tr>

								<td><b> <c:out value="${deposit.id}"></c:out></b></td>
								<td><b> <c:out value="${deposit.createdDate}"></c:out></b></td>
								<td><b> <c:out value="${deposit.maturityDate}"></c:out></b></td>
								<td><b> <c:out value="${deposit.interestRate}"></c:out></b></td>
								<td><b> <c:out value="${deposit.depositClassification}"></c:out></b></td>
								<td><b> <c:out value="${productCode}"></c:out></b></td>

								<td><b><c:if test = "${deposit.maturityAmountOnClosing != null}"><c:out value="${deposit.maturityAmountOnClosing}"></c:out></c:if>
								</b></td>
							</tr>



						</table>

<table class="table table-striped table-bordered"
							style="width: 95%; margin-left: 23px;">
							<tr>
								<td><b>Name</b></td>
								<td><b>Holder Id</b></td>
								<td><b>Contribution</b></td>
								<td><b>Amount</b></td>
								<td><b>Is Alive</b></td>
								<td><b>Nominee Name</b></td>
								<td><b>Select</b></td>
							</tr>
							<tbody>
<c:if test="${! empty depositHolders}">
					<c:forEach items="${depositHolders}" var="depositHolder">

							<tr>

								<td><b> <c:out value="${depositHolder.customerName}"></c:out></b></td>
								<td><b> <c:out value="${depositHolder.depositHolderId}"></c:out></b></td>
								<td><b> <c:out value="${depositHolder.contribution}"></c:out></b></td>
								<td><b> <c:out value="${depositHolder.distAmtOnMaturity}"></c:out></b></td>
								<td><b><c:if test = "${depositHolder.deathCertificateSubmitted == null}"> <c:out value="Yes"></c:out></c:if>
								<c:if test = "${depositHolder.deathCertificateSubmitted != null}"> <c:out value="No"></c:out></c:if>
								</b></td>
								<td><b> <c:out value="${depositHolder.nomineeName}"></c:out></b></td>

								<td><input value="${depositHolder.depositHolderId}" name="depositHolders" amount="${depositHolder.distAmtOnMaturity}" class="holders" contribution="${depositHolder.contribution}" cname="${depositHolder.customerName}" nominee="${depositHolder.nomineeName}" death="${depositHolder.deathCertificateSubmitted}" holderType="${depositHolder.depositHolderStatus}" type="checkbox" onchange="paymentValidation($(this));"/></td>
							</tr>


</c:forEach>
				</c:if>
				</tbody>
						</table>

<div class="form-group">
<input type="checkbox" id="singleDD" checked /><label class="col-md-4 control-label">Issue a single DD/Cheque</label>
</div>
						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.modeOfPay" /><span style="color: red">*</span></label>
							<div class="col-md-4">
								<form:select id="fdPay" placeholder="Payment Mode"
									path="paymentMode" onchange="showDetails()"
									class="myform-control">
									<form:option value="Select">
										<spring:message code="label.select" />
									</form:option>
									<%-- <form:option value="Cash">
										<spring:message code="label.cashPayment" />
									</form:option> --%>
									<form:option value="DD">
										<spring:message code="label.ddPayment" />
									</form:option>
									<form:option value="Cheque">
										<spring:message code="label.chequePayment" />
									</form:option>

									<%-- <form:option value="Card Payment">
								       Card Payment
							</form:option> --%>
								</form:select>
								<span id="fdPayError" style="display: none; color: red;"><spring:message
										code="label.validation" /></span> <span id="accError"
									style="display: none; color: red;">Please select Mode of
									Payment</span>
							</div>
						</div>

						
						<!--.................  Cheque/DD PAYMENT....................-->


						<div id="chequeDdDiv">
							<div class="form-group chequeAndDD_DIV" style="display: none;"
								class="">
								<label class="col-md-4 control-label chequeDDNoLabel"> <span
									id=""></span><span style="color: red">*</span></label>
								<div class="col-md-4">
									<form:input path="chequeNo" class="myform-control"
										style="background: #fff; cursor: text;" type="number"
										id="chequeNo" onkeypress="numberValidation(event)" />
									<span id="ddNoError" style="display: none; color: red;">
										<spring:message code="label.enterData" />
									</span>
								</div>
							</div>
							<div class="form-group chequeAndDDName_DIV" style="display: none;"
								class="">
								<label class="col-md-4 control-label chequeDDNameLabel"> <span
									id=""></span><span style="color: red">*</span></label>
								<div class="col-md-4">
									<form:input path="chequeName" class="myform-control"
										style="background: #fff; cursor: text;" type="text"
										id="chequeName" readonly="true" />
									<span id="ddNoError" style="display: none; color: red;">
										<spring:message code="label.enterData" />
									</span>
								</div>
							</div>
							
							
							<div class="form-group">
								<label class="col-md-4 control-label"> <span
									id="">Amount</span><span style="color: red">*</span></label>
								<div class="col-md-4">
								<input type="hidden" name="deathCertificateSubmitted" id="deathCertificateSubmitted">
									<form:input path="chequeAmount" class="myform-control"
										style="background: #fff; cursor: text;" type="text"
										id="chequeAmount" readonly="true" />
									<span id="ddNoError" style="display: none; color: red;">
										<spring:message code="label.enterData" />
									</span>
								</div>
							</div>
							
							
							<div class="form-group">
								<label class="col-md-4 control-label"><span
									id="chequeDDDateLabel"><spring:message code="label.date" /></span><span
									style="color: red">*</span></label>
								<div class="col-md-4">
									<fmt:formatDate value="${deposit.createdDate}" var="dateFormat"
										pattern="dd/MM/yyyy" />

									<form:input path="chequeDate"
										class="myform-control datepicker-here"
										style="background: #fff; cursor: pointer;" id="chequeDate"
										 readonly="true" />
										 
									<span id="ddDateError" style="display: none; color: red;">
										<spring:message code="label.enterData" />
									</span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.bank" /><span style="color: red">*</span></label>
								<div class="col-md-4">
									<form:input path="chequeBank" class="myform-control"
										style="background: #fff; cursor: text;" id="chequeBank"
										 onkeypress="alphaNumaricValidation(event)" />
									<span id="ddBankError" style="display: none; color: red;">
										<spring:message code="label.enterData" />
									</span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.branch" /><span style="color: red">*</span></label>
								<div class="col-md-4">
									<form:input path="chequeBranch" class="myform-control"
										style="background: #fff; cursor: text;" id="chequeBranch"
										 onkeypress="alphaNumaricValidation(event)" />
								</div>
							</div>
							<div style="color: red; margin-left: 327px;padding-bottom: 13px;">
								<span id="dayError"></span>
							</div>


							<div class="form-group">
								<div class="col-md-4"></div>
								<div class="col-md-8">

								<a href="bankPaymentOnMaturity?menuId=${menuId}" class="btn btn-info">Back</a> <input
									type="submit" id="" class="btn btn-info"
									data-toggle="tooltip" value="Go" onclick="return validate()"
									title="Please select mode of payment and deposit">


								</div>
							</div>
						</div>
						
						
						
						
						
						
						
						
						

						<div id="multiplechequeDdDiv" style="display:none;">
						<div id="multipleHolders">
						
						</div>
							<div style="color: red; margin-left: 327px;padding-bottom: 13px;">
								<span id="dayError"></span>
							</div>


							<div class="form-group">
								<div class="col-md-4"></div>
								<div class="col-md-8">

								<a href="#" class="btn btn-info">Back</a> <input
									type="submit" id="" class="btn btn-info"
									data-toggle="tooltip" value="Go" onclick="return validate()"
									title="Please select mode of payment and deposit">


								</div>
							</div>
						</div>



						
					</c:if>
				</form:form>
			</div>

		</div>

		<script>
		
		function paymentValidation(obj){
			if($('#singleDD').prop("checked")==true){
			var prDeath='';var prHolderType='';var prContribution='0.0';
			prContribution=parseFloat(prContribution);
			$('#chequeName').val('');
			debugger;
			var amount='0.0';
			amount=parseFloat(amount);
			$('#chequeAmount').val('');
			$('.holders:checked').each(function(){
				if($(this).attr('death')==undefined){
					$('#deathCertificateSubmitted').val('0');
				}
				else{
					$('#deathCertificateSubmitted').val('1');
				}
				if(prDeath==''){
					prDeath=$(this).attr('death');
					if($(this).attr('holderType')=="PRIMARY")
					{
					prHolderType="PRIMARY";
					if(prDeath=="1"){
					$('#chequeName').val($(this).attr('nominee'));
					}else{
						$('#chequeName').val($(this).attr('cname'));
					}
					}
				if(prHolderType!="PRIMARY"){
					prHolderType="SECONDARY";
					if(parseFloat($(this).attr('contribution'))>prContribution){
					prContribution=$(this).attr('contribution');
					if(prDeath=="1"){
						$('#chequeName').val($(this).attr('nominee'));
						}else{
							$('#chequeName').val($(this).attr('cname'));
						}
					}
				}
				amount+=parseFloat(parseFloat($(this).attr('amount')));
				}
				else{
					if(prDeath==$(this).attr('death')){
						if($(this).attr('holderType')=="PRIMARY")
						{
						prHolderType="PRIMARY";
						if($(this).attr('death')=="1"){
							$('#chequeName').val($(this).attr('nominee'));
							}else{
								$('#chequeName').val($(this).attr('cname'));
							}
						}
					if(prHolderType!="PRIMARY"){
						prHolderType="SECONDARY";
						if(parseFloat($(this).attr('contribution'))>prContribution){
						prContribution=$(this).attr('contribution');
						
						
						if($(this).attr('death')=="1"){
							$('#chequeName').val($(this).attr('nominee'));
							}else{
								$('#chequeName').val($(this).attr('cname'));
							}
						//$('#chequeName').val($(this).attr('nominee'));
						}
					}
					amount+=parseFloat(parseFloat($(this).attr('amount')));
					}
					else{
						alert("Alive customers can not be payed with expired customers.");
						$('.holders').prop("checked", false);
						$('#deathCertificateSubmitted').val('');
						$('#chequeName').val('');
						amount='';
					}
				}
			});
			
			$('#chequeAmount').val(amount);
			}
			else{
				
				var html='';
				var i=0;
				$('.holders:checked').each(function(){
					debugger;
					var chequeName=$(this).attr('cname');
				var name=$(this).attr('cname');
				var nominee=$(this).attr('nominee');
				var amount=$(this).attr('amount');
				var contribution=$(this).attr('contribution');
				var death=$(this).attr('death');
				var holdertype=$(this).attr('holdertype');
				var holderId=$(this).attr('value');
				if(death=="1"){
					chequeName=nominee;
				}
				
					    html+='<div class="col-md-6" id="'+name+'"><div class="form-group chequeAndDD_DIV" class="">';
						html+='<label class="col-md-4 control-label chequeDDNoLabel"> <span id=""></span><span style="color: red">*</span></label>';
						html+='<div class="col-md-7"><input name="jointAccounts['+i+'].chequeNo" class="myform-control" style="background: #fff; cursor: text;" type="number" id="chequeNo" onkeypress="numberValidation(event)" />';
						html+='<span id="ddNoError" style="display: none; color: red;">Please enter date</span></div></div>';
						html+='<div class="form-group chequeAndDDName_DIV" class="">';
						html+='<label class="col-md-4 control-label chequeDDNameLabel"> <span></span><span style="color: red">*</span></label><div class="col-md-7"><input value="'+chequeName+'" name="jointAccounts['+i+'].chequeName" class="myform-control" style="background: #fff; cursor: text;" type="text" id="chequeName" readonly="true" /><span id="ddNoError" style="display: none; color: red;">Please enter data</span></div></div>';
						html+='<div class="form-group"><label class="col-md-4 control-label"> <span>Amount</span><span style="color: red">*</span></label><div class="col-md-7"><input type="hidden" name="jointAccounts['+i+'].deathCertificateSubmitted" id="deathCertificateSubmitted"><input type="hidden" name="jointAccounts['+i+'].id" value="'+holderId+'">';
						html+='<input name="jointAccounts['+i+'].chequeAmount" class="myform-control" style="background: #fff; cursor: text;" type="text" value="'+amount+'" id="chequeAmount" readonly="true" /><span id="ddNoError" style="display: none; color: red;">Please Enter data</span></div></div>';
						html+='<div class="form-group"><label class="col-md-4 control-label"><span id="chequeDDDateLabel">Date</span><span style="color: red">*</span></label><div class="col-md-7"><fmt:formatDate value="${deposit.createdDate}" var="dateFormat" pattern="dd/MM/yyyy" /><input id="chequeDate'+i+'" name="jointAccounts['+i+'].chequeDate" class="myform-control datepicker-here" style="background: #fff; cursor: pointer;" readonly="true" type="text"  /><span id="ddDateError" style="display: none; color: red;">Please enter data</span></div></div>';
						html+='<div class="form-group"><label class="col-md-4 control-label">Bank<span style="color: red">*</span></label><div class="col-md-7"><input name="jointAccounts['+i+'].chequeBank" class="myform-control" style="background: #fff; cursor: text;" id="chequeBank" onkeypress="alphaNumaricValidation(event)" /><span id="ddBankError" style="display: none; color: red;">Please enter data</span></div></div><div class="form-group"><label class="col-md-4 control-label">Branch<span style="color: red">*</span></label><div class="col-md-7"><input name="jointAccounts['+i+'].chequeBranch" class="myform-control" style="background: #fff; cursor: text;" id="chequeBranch" onkeypress="alphaNumaricValidation(event)" /></div></div></div>';
					i++;
				});
				$('#multipleHolders').html(html);
				showDetails();
				$('.datepicker-here').datepicker();			
				
			}
		}
		
		function load_js()
		   {
		      var head= document.getElementsByTagName('head')[0];
		      var script= document.createElement('script');
		      script.src= '/annona/resources/js/datepicker.js';
		      head.appendChild(script);
		   }
		
		function disableScreen() {
				// creates <div class="overlay"></div> and 
				// adds it to the DOM
				var div = document.createElement("div");
				div.className += "overlay";
				document.body.appendChild(div);
			}
			
			
			
			function validate() {
				
				// if($("#fdPay").val() == 'Select'){
				//	$("#dayError").text("Please select payment mode option");
				//	return false;
				//}
				
				//if($("#chequeNo").val() == ''){
				//	$("#dayError").text("Please enter DD/Cheque number !");
				//	return false;
				//}
				
				//if($("#chequeDate").val() == ''){
				//	$("#dayError").text("Please enter Date !");
				//	return false;
				//}
				//if($("#chequeBank").val() == ''){
				//	$("#dayError").text("Please enter Bank Name !");
				//	return false;
				//}
				
				//if($("#chequeBranch").val() == ''){
				//	$("#dayError").text("Please enter Bank Branch !");
				//	return false;
				//}
				//$("#dayError").text("");
				//$("#fdPay").attr("disabled",false); 
				if($('#singleDD').prop("checked")==true){
				$("#fdForm").attr("action","savePaymentMaturityDetails");
				}
				else{
					$("#fdForm").attr("action","saveMultiplePaymentMaturityDetails");
				}
				$("#fdForm").submit();
				
				
			}
		</script>
		<style>
.flexi_table {
	margin-bottom: 210px;
}

.space-45 {
	height: 27px;
}

input#fdID {
	margin-top: 0px;
}

.overlay {
	background-color: #EFEFEF;
	position: fixed;
	width: 100%;
	height: 100%;
	z-index: 1000;
	top: 0px;
	left: 0px;
	opacity: .5; /* in FireFox */
	filter: alpha(opacity = 50); /* in IE */
}
</style>
