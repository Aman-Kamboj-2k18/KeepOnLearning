<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script>
	function searchDepost() {

		$("#fdForm").attr("action", "withdrawPaymentList");
		$("#fdForm").submit();

	}

	function getTodaysDate()
	{
		var today = null;
		$.ajax({  
		    type: "GET",  
		    async: false,
		    url: "<%=request.getContextPath()%>/common/loginDateForJsp", 
		    contentType: "application/json",
		    dataType: "json",

		    success: function(response){  
		    //	window.loginDateForFront = new Date(parseInt(response));
		    	today = new Date(parseInt(response))
		    },  
		    error: function(e){  
		    	 $('#error').html("Error occured!!")
		    	 // window.loginDateForFront = getTodaysDate();
		    }  
		  });  
		return today;
	}
	
	function onclickRadio(customerName,depositHolderId, appStatus, newMaturityDate,depositId,email) {      //mandeep

	
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
		if (diff < payAndWithdrawTenure) {

			document.getElementById('dayError').innerHTML = 'Your deposit will be matured by next '
					+ diff + ' days. So you can not withdraw.';
			return false;

		}
		
		
		document.getElementById('appStatusError').style.display = 'none';

		document.getElementById('customerName').value = customerName;
		
		
		document.getElementById('depositHolderId').value = depositHolderId;
		document.getElementById('depositId').value = depositId;
debugger;
		if (appStatus == 'Pending') {
			document.getElementById('appStatusError').style.display = 'block';
			document.getElementById("goBtn").disabled = true;
			document.getElementById("goBtn").title = "Status is pending";
		} else {
		var fdPay = document.getElementById('fdPay').value;

		if (fdPay != 'Select' && $("input:checkbox:checked").length>0) {
			
			document.getElementById("goBtn").disabled = false;
			document.getElementById("goBtn").title = "";
			
		} else {
			document.getElementById("goBtn").disabled = true;
			document.getElementById("goBtn").title = "Please select mode of transfer";

		}
		}

		/* document.getElementById('appStatusError').style.display = 'none';
		if (appStatus == 'Pending') {
			document.getElementById('appStatusError').style.display = 'block';
			document.getElementById("goBtn").disabled = true;
		} else {
			document.getElementById("goBtn").disabled = false;
		} */
		
		/* if ($("input:checkbox:checked").length == 0){
			document.getElementById("goBtn").disabled = true;
		} */
	}

	function onchangeTransferMode(value) {
		if (value != 'Select' && $("input:checkbox:checked").length>0) {
			
				document.getElementById("goBtn").disabled = false;
				document.getElementById("goBtn").title = "";
			
		}

	}

	function validate() {
		
		 var depositHolderIds="";
		  var totalCheckBoxes=document.getElementsByClassName('selectRadio').length;
		  var checkedCheckBoxes=$("input:checkbox:checked").length;
		 // var customerDead=false;
		 
		  var isSubmit1=true;
		 if(totalCheckBoxes==1){
			 $("input:checkbox:checked").each(function(){
				 var death=$(this).attr("deathCertificateSubmitted");
				 if (typeof death !== typeof undefined && death !== false) {
						var customer =$(this).attr("customerName");
						
						alert("Payment is not possible,"+ customer  +"  is expired");
						
						isSubmit1=false;
						return false;
					}
			 });
		 }
		 if(isSubmit1==false){return false;}
		 var isSubmit=true;
		  $("input:checkbox:checked").each(function(){
			  var death=$(this).attr("deathCertificateSubmitted");
				if (typeof death !== typeof undefined && death !== false) {
					var customer =$(this).attr("customerName");
					
					if(checkedCheckBoxes==1){
					alert(customer+" has expired so select Another Account holder.");
					}else if(checkedCheckBoxes>1)
						{
						alert(customer+" has expired, he is not eligible for payment, please remove the selection.");
						}
					
					
					isSubmit=false;
					return false;
				}
		  });
		  if(isSubmit==false){return false;}
		  
         $("input:checkbox:checked").each(function(){
        	 depositHolderIds+=$(this).attr("holderid")+",";
		     });
         depositHolderIds=depositHolderIds.substring(0,depositHolderIds.length-1);
         var accountAccessType=$("#accountAccessType").val();
        
		if(accountAccessType=="EitherOrSurvivor"){
			//dont do anything
		}
	if(accountAccessType=="AnyoneOrSurvivor"){
			//dont do anything
		}
	if(accountAccessType=="FormerOrSurvivor"){
		var primary=false;
		var secondary=false;
		var isDeathCertificateSubmitted=false;
		$("input:checkbox:checked").each(function(){
			if($(this).attr("depositHolderStatus")=="PRIMARY"){
				primary=true;
			}
			if($(this).attr("depositHolderStatus")=="SECONDARY"){
				secondary=true;
			}
		});
		
		
		if(secondary==true && primary==true){
			alert("Secondary Holders are not allowed to operate this account");
			return false;
		}
		if(primary==true){
			
		}
		else{
			
			$("input:checkbox").each(function(){
				if($(this).attr("depositHolderStatus")=="PRIMARY"){
					var death=$(this).attr("deathCertificateSubmitted");
					if (typeof death !== typeof undefined && death !== false) {
						isDeathCertificateSubmitted=true;
					}
				}
				
				
			});
			
			
			
			
			if(isDeathCertificateSubmitted==false){
				alert("Secondary Holders are not allowed to operate this account");
				return false;
			}
			
			}
	}
	if(accountAccessType=="LatterOrSurvivor"){
		debugger;
		var secondary=false;
		var primary=false;
		var isDeathCertificateSubmitted=false;
		$("input:checkbox:checked").each(function(){
			if($(this).attr("depositHolderStatus")=="SECONDARY"){
				secondary=true;
			}
			if($(this).attr("depositHolderStatus")=="PRIMARY"){
				primary=true;
			}
		});
		if(secondary==true && primary==true){
			alert("Primary Holders are not allowed to operate this account");
			return false;
		}
		if(secondary==true){}
		else{
			debugger;

			
			$("input:checkbox").each(function(){
				if($(this).attr("depositHolderStatus")=="SECONDARY"  ){
					   
					
					var death=$(this).attr("deathCertificateSubmitted");
					if (typeof death !== typeof undefined && death !== false) {
						isDeathCertificateSubmitted=true;
					}
					
				}
				
				
			});
			
			
			
			
			
			
			if(isDeathCertificateSubmitted==false){
				alert("Primary Holders are not allowed to operate this account");
				return false;
			}
			
			}
	}
	if(accountAccessType=="Jointly"){
		 if(totalCheckBoxes!=checkedCheckBoxes){
		alert("You are not allowed to operate this account individually");
		return false;
		 }
	}
	if(accountAccessType=="JointlyOrSurvivor"){
		 if(totalCheckBoxes!=checkedCheckBoxes){
		var isDeathCertificateSubmitted=false;
		
		$("input:checkbox").each(function(){
			
				var death=$(this).attr("deathCertificateSubmitted");
				if (typeof death !== typeof undefined && death !== false) {
					isDeathCertificateSubmitted=true;
				}
			
		});
		
		if(isDeathCertificateSubmitted==false){
			alert("You are not allowed to operate this account individually");
			return false;
		}
		 }
	}

	document.getElementById('paymentMadeByHolderIds').value = depositHolderIds;
		
	}

	function search() {
		
		

		$("#fdForm").attr("action", "depositPayment");
		$("#fdForm").submit();

	}
</script>
<div class="right-container" id="right-container">
	<div class="container-fluid">

		<div class="Flexi_deposit">

			<div class="header_customer" style="margin-bottom: 25px;">
				<h3>
					<spring:message code="label.withdraw" />
					Deposit
				</h3>
			</div>
			<div class="col-md-12" style="padding: 30px; text-align: center;">
				<span style="color: red;">${error}</span>
			</div>
			<div class="flexi_table">
				<form:form action="withdrawFdUser" class="form-horizontal"
					onsubmit="return validate()" id="fdForm" method="post" name="deposit"
					commandName="depositForm">
					<input type="hidden" name="menuId" value="${menuId}"/>
					<form:hidden path="depositId" />
					<form:hidden id="depositHolderId" path="depositHolderId" />
					<form:hidden id="paymentMadeByHolderIds" path="paymentMadeByHolderIds" />
					<form:hidden path="customerName" />
					<form:hidden path="email" />
					<c:if test="${empty deposit}">

						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.fdAccountNum" /><span style="color: red">*</span></label>
							<div class="col-md-4">
								<form:input path="accountNumber"
									placeholder="Enter Account Number" id="fdID"
									class="myform-control" required="true" />


							</div>

						</div>
						<div class="col-sm-12 col-md-12 col-lg-12"
							style="margin-left: 402px;">
							<input type="submit" class="btn btn-primary"
								onclick="searchDepost()" value="Search">

						</div>
					</c:if>
					<span id="appStatusError" style="display: none; color: red;">This
						deposit is not yet approved</span>

					<c:if test="${! empty deposit}">

						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.fdAccountNum" /><span style="color: red">*</span></label>
							<div class="col-md-4">
								<form:input path="accountNumber" class="myform-control"
									style="background-color: #968c8c2b;margin-bottom: 10px;"
									readonly="true" />
							</div>

						</div>

						<table class="table table-striped table-bordered"
							style="margin-left: 15px; width: 949px;">
							<tr>
								<td><b><spring:message code="label.customerID" /></b></td>
								<td><b><spring:message code="label.customerName" /></b></td>
								<td><b><spring:message code="label.dateOfBirth" /></b></td>
								<td><b><spring:message code="label.email" /></b></td>
								<td><b><spring:message code="label.contactNumber" /></b></td>
								<td><b><spring:message code="label.address" /></b></td>
								<td><b><spring:message code="label.select" /></b></td>

							</tr>

							<c:forEach items="${deposit}" var="d">

								<tr>
                                    <input type="hidden" value="${d.accountAccessType }" id="accountAccessType">
									<td><b> <c:out value="${d.customerId}"></c:out></b></td>
									<td><b> <c:out value="${d.customerName}"></c:out></b></td>
									<td><b> <c:out value="${d.dateOfBirth}"></c:out></b></td>
									<td><b> <c:out value="${d.email}"></c:out></b></td>
									<td><b> <c:out value="${d.contactNum}"></c:out></b></td>
									<td><b> <c:out value="${d.address}"></c:out></b></td>
									<td><b><form:checkbox id="selectRadio"
												class="selectRadio"
												onclick="onclickRadio('${d.customerName}','${d.depositHolderId}','${d.approvalStatus}','${d.newMaturityDate}','${d.depositId}','${d.email}')"         
												path="customerId" customerName="${d.customerName}" depositHolderStatus="${d.depositHolderStatus }" deathCertificateSubmitted="${d.deathCertificateSubmitted}" holderId="${d.depositHolderId}" value="${d.customerId}" /></b></td>


								</tr>

							</c:forEach>

						</table>

						<div class="form-group">
							<label class="col-md-4 control-label">Mode of transfer<span
								style="color: red">*</span></label>
							<div class="col-md-4">
								<select id="fdPay" name="paymentMode" class="myform-control"
									onchange="onchangeTransferMode(this.value)">
									<option value="Select">Select</option>
									<c:forEach items="${paymentMode}" var="mode">
												<option mode="${mode.paymentMode}"	value="${mode.paymentMode}">${mode.paymentMode}</option>
									</c:forEach>
								</select>

							</div>
						</div>

						<div style="color: red; margin-left: 398px;">
							<span id="dayError"></span>
						</div>



						<div class="form-group">
							<div class="col-md-6"></div>
							<div class="col-md-6">
								<a href="searchDepositForWithdraw?menuId=${menuId}" class="btn btn-danger">Back</a> <input
									type="submit" id="goBtn" disabled="true" class="btn btn-info"
									data-toggle="tooltip" onclick="return valPay()" value="Proceed"
									title="Please select mode of transfer and deposit id">

							</div>
						</div>




					</c:if>





				</form:form>
			</div>

		</div>

		<script>
			$(document)
					.ready(
							function() {
								
								if(document.getElementsByClassName('selectRadio')!=null){
									if ($("input:checkbox:checked").length > 0){
										var selectRadio=true;
									}else{
										var selectRadio=false;
									}
								
								var paymentMode = document
										.getElementById('fdPay').value;

								if (selectRadio == true
										&& paymentMode != 'Select' && $("input:checkbox:checked").length>0) {
									
									document.getElementById("goBtn").disabled = false;
									document.getElementById("goBtn").title = "";
									
								} else {

									document.getElementById("goBtn").disabled = true;

								document.getElementById("goBtn").title = "Please select mode of transfer and deposit id"
						
								}
								}
								
								});
		
			function valPay() {

				if (document.getElementById('fdPay').value == 'Select') {
					document.getElementById('fdPay').style.borderColor = "red";
					return false;
				}

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
</style>