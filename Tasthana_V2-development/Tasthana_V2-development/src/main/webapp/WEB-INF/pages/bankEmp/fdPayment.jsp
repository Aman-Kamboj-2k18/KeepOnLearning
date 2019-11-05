
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script>




	function searchDepost() {
		
		debugger;
		
		
		var accno = document.getElementById('fdID').value;
		$("#wait").show();
		disableScreen();
		

		if (accno != '') {
			
			setTimeout(function(){
				$("body").attr("style", "opacity: 0.5");
				$("#wait").hide();
				
				
			}, 8000);
			document.getElementById('accError').style.display = 'none';
			$("#fdForm").attr("action", "depositPayment");
			$("#fdForm").submit();
			
		} else {

			document.getElementById('accError').style.display = 'block';
		}
	}


function getTodaysDate()
{
	var today = null;
	$.ajax({  
	    type: "GET",  
	    async: false,
	    url: "<%=request.getContextPath()%>/bnkEmp/loginDateForJsp", 
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
	function onclickRadio(customerName, depositHolderId, depositId, appStatus ,newMaturityDate,email) {
		debugger
		        var canSubmit = true;
				var today = getTodaysDate();
				var dd = today.getDate();
				var mm = today.getMonth()+1; //January is 0!
				var yyyy = today.getFullYear();
			    var payAndWithdrawTenure = parseFloat('${payAndWithdrawTenure}');
				if(dd<10) {
				    dd = '0'+dd
				} 

				if(mm<10) {
				    mm = '0'+mm
				} 

				today = yyyy + '-' + mm + '-' + dd;
				
				var splitMaturityDate = newMaturityDate.split(" ");	
				var newSplittedMaturity = splitMaturityDate[0];
				var diff =  Math.floor((Date.parse(newSplittedMaturity) - Date.parse(today)) / 86400000); 
				debugger
			    if(diff<payAndWithdrawTenure){

					 document.getElementById('dayError').innerHTML = 'Your deposit will be matured by next '+ diff +' days. So you can not make payment.';
					 document.getElementById('fdPay').disabled=true;
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
		 if ($("input:checkbox:checked").length == 0){
			document.getElementById("goBtn").disabled = true;
		} 
	}


	function search() {
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
			 $("#fdForm").attr("action", "fdPaymentDetails");
			 $("#fdForm").submit(); 
				

	}


	function showDetails(obj) {
		
		var paymentMode = $('option:selected', obj).attr('mode');
		var selectRadio = false;
		if ($("input:checkbox:checked").length > 0)
		{
			selectRadio = true;
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
		}

		
	
		
	}
	
</script>
<div class="right-container" id="right-container" >


	<div class="container-fluid">

		<div class="Flexi_deposit">


			<div class="header_customer" style="margin-bottom: 25px;">
				<h3>
					<spring:message code="label.payments" />
				</h3>
			</div>
			<div class="col-md-12" style="margin: 10px auto; text-align: center;">
				<span style="color: red;">${error}</span>
			</div>
			<div class="col-md-12" style="padding: 10px auto; text-align: center;">
				<span style="color: red;">${taxError}</span>
			</div>
			<div class="flexi_table">
				<form:form class="form-horizontal" id="fdForm" method="post"
					name="depositList" commandName="fixedDepositForm">
					<form:hidden id="depositId" path="depositId" />
					<form:hidden id="depositHolderId" path="depositHolderId" />
					<form:hidden id="paymentMadeByHolderIds" path="paymentMadeByHolderIds" />
					<form:hidden id="customerName" path="customerName" />
					<form:hidden id="email" path="email" />
					<input type="hidden" name="productConfigurationId" value="${productConfigurationId}" />
<%-- 					<form:hidden id="productConfigurationId" path="productConfigurationId" /> --%>

					<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.fdAccountNo" /><span style="color: red">*</span></label>
						<div class="col-md-4">
							<form:input path="accountNo"
								placeholder="Enter Account Number" id="fdID"
								class="myform-control" required="true" />
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
						
						<div id="wait" style="display:none;width:55px;height:60px;position:absolute;top:50%;left:50%;padding:2px;"><img src='https://www.skillstat.com/wp-content/themes/anvil/img/bx_loader.gif' width="150" height="150" /><br></div>

					</div>
					<div class="col-sm-12">
						<div class="space-45"></div>



					</div>

					<c:if
						test="${!(empty depositList) && depositList[0].approvalStatus!='Pending'}">
						<table class="table table-striped table-bordered" style="width: 95%; margin-left: 23px;">
							<tr>
								<td><b><spring:message code="label.customerID" /></b></td>
								<td><b><spring:message code="label.customerName" /></b></td>
								<td><b><spring:message code="label.dateOfBirth" /></b></td>
								<td><b><spring:message code="label.email" /></b></td>
								<td><b><spring:message code="label.contactNumber" /></b></td>
								<td><b><spring:message code="label.address" /></b></td>
								<td><b><spring:message code="label.select" /></b></td>
							</tr>

							<c:forEach items="${depositList}" var="d">

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
												onclick="onclickRadio('${d.customerName}','${d.depositHolderId}','${d.depositId}','${d.approvalStatus}','${d.newMaturityDate}','${d.email}')"
												path="customerId" customerName="${d.customerName}" depositHolderStatus="${d.depositHolderStatus }" deathCertificateSubmitted="${d.deathCertificateSubmitted}" holderId="${d.depositHolderId}" value="${d.customerId}" /></b></td>


								</tr>

							</c:forEach>

						</table>


						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.modeOfPay" /><span style="color: red">*</span></label>
							<div class="col-md-4">
								<form:select id="fdPay" placeholder="Payment Mode"
									path="paymentMode" onchange="showDetails(this)"
									class="myform-control">
									<form:option value="Select">
										<spring:message code="label.select" />
									</form:option>
									<c:forEach items="${paymentMode}" var="mode">
												<option mode="${mode.paymentMode}" value="${mode.id}">${mode.paymentMode}</option>
									</c:forEach>
								</form:select>
								<span id="fdPayError" style="display: none; color: red;"><spring:message
										code="label.validation" /></span> <span id="accError"
									style="display: none; color: red;">Please select Mode of
									Payment</span>
							</div>
						</div>

						<div class="form-group" id="typeOfPayment"
							style="margin-top: 25px;">
							<div class="col-sm-3"></div>
							
							

<!-- 							<div class="col-sm-3" -->
<!-- 								style="text-align: right; padding-right: 115px !important;"> -->
<!-- 								<label for="radio">  -->
<%-- 								<c:if test="${depositCategory != 'REVERSE-EMI'}"> --%>
<%-- 								<form:radiobutton path="topUp" --%>
<%-- 										name="regularRadio" id="regularRadio" value="0" /> Recurring --%>
<%-- 											</c:if> --%>
<!-- 								</label> -->

<!-- 							</div> -->
						
<!-- 							<div class="col-sm-3" style="margin-left: -115px !important;"> -->
<%-- 								<label for="radio"> <form:radiobutton path="topUp" --%>
<%-- 										name="topUpRadio" id="topUpRadio" value="1" checked="checked" /> --%>
<!-- 									Top up -->
<!-- 								</label> -->

<!-- 							</div> -->
						</div>


<div style="color: red; margin-left: 271px;"><span id="dayError"></span></div>


<div class="form-group">
    <div class="col-md-4"></div>		 
							<div class="col-md-8">

								<a href="bankEmp" class="btn btn-info">Back</a> <input type="button" id="goBtn" class="btn btn-info" onclick="search()" data-toggle="tooltip" value="Go" disabled="" title="Please select mode of payment and deposit">


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

								debugger;
								if(document.getElementsByClassName('selectRadio')!=null){
									if ($("input:checkbox:checked").length > 0){
										var selectRadio=true;
									}else{
										var selectRadio=false;
									}
								
								var paymentMode = document
										.getElementById('fdPay').value;

								if (selectRadio == true
										&& paymentMode != 'Select') {
									document.getElementById("goBtn").disabled = false;
									document.getElementById("goBtn").title = "";
								} else {

									document.getElementById("goBtn").disabled = true;

									document.getElementById("goBtn").title = "Please select mode of payment and deposit";

								}
								}

							});
		
			function val() {
				
				if (document.getElementById('fdPay').value == 'Select') {
					document.getElementById('fdPay').style.borderColor = "red";
					return false;
				}

			}
			
			
			
			function disableScreen() {
			    // creates <div class="overlay"></div> and 
			    // adds it to the DOM
			    var div= document.createElement("div");
			    div.className += "overlay";
			    document.body.appendChild(div);
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
    background-color:#EFEFEF;
    position: fixed;
    width: 100%;
    height: 100%;
    z-index: 1000;
    top: 0px;
    left: 0px;
    opacity: .5; /* in FireFox */ 
    filter: alpha(opacity=50); /* in IE */
}

</style>