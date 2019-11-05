<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script>
function searchDepost(){
	

	var accno=document.getElementById('fdID').value;

	if(accno!=''){
		document.getElementById('accError').style.display='none';
	
       $("#fdForm").attr("action", "withdrawPaymentList");
       $("#fdForm").submit();
	}
	else{
		
			document.getElementById('accError').style.display='block';
	}
 }
</script>
<script>
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

function getDeathCertificate(depositId){
	if(depositId==undefined){
		alert("Deposit Id can not be null");
	return false;
	}
	var result="";
    $.ajax({  
		  async: false,
	      type: "GET",  
	      url: "<%=request.getContextPath()%>/common/getDeathCertificate", 
	      contentType: "application/json",
	      dataType: "json",
	      data: "depositId="+depositId,
	      success: function(response){     
	      result = response;
	      },  
	      error: function(e){  
	       alert("Error occured!!");
	      }  
	    }); 
    return result;
}


function onclickRadio(depositId,accountNumber,depositCategory,newMaturityDate,depositClasssification, accountAccessType,isSubmitted,depositHolderType){
	document.getElementById("goBtn").disabled = true;
	if(isSubmitted=="1"  || isSubmitted=="Yes")
	{
	alert('Account holder can not access account');
	return false;
	}
if(accountAccessType=="EitherOrSurvivor"){
	
}
if(accountAccessType=="AnyoneOrSurvivor"){
	
}
if(accountAccessType=="FormerOrSurvivor"){
if(depositHolderType=="PRIMARY"){}
else{
	var result=getDeathCertificate(depositId);
	if(result=="0"){
		alert("Secondary Holders are not allowed to operate this account");
		return false;
	}
	
	}
}
if(accountAccessType=="LatterOrSurvivor"){
if(depositHolderType=="SECONDARY"){}
else{
	var result=getDeathCertificate(depositId);
if(result=="0"){
alert("Primary Holders are not allowed to operate this account");
return false;
	}
	
	}
}
if(accountAccessType=="Jointly"){
alert("You are not allowed to operate this account individually");
return false;
}
if(accountAccessType=="JointlyOrSurvivor"){
var result=getDeathCertificate(depositId);
if(result=="0"){
	alert("You are not allowed to operate this account individually");
	return false;
}

}

	
	var canSubmit = true;
	
	if(depositClasssification=='Tax Saving Deposit'){
		document.getElementById('taxSavingError').style.display ='block';
		 document.getElementById("goBtn").disabled = true;
		return false;
	}
	
	document.getElementById('taxSavingError').style.display ='none';
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
	var diff =  Math.floor(( Date.parse(newSplittedMaturity) - Date.parse(today) ) / 86400000); 
	 document.getElementById('dayError').innerHTML ='';
    if(diff<payAndWithdrawTenure){
    	 document.getElementById("goBtn").disabled = true;
		 document.getElementById('dayError').innerHTML ='Your deposit will be matured by next '+diff+' days. So you can not withdraw.';
		 return false;
	
       }
	
	
	if(depositCategory=='AUTO'){
		document.getElementById('autoError').style.display ='block';
		
		 document.getElementById("goBtn").disabled = true;
		return false;
		
	}
	
	
	
	document.getElementById('autoError').style.display ='none';
	document.getElementById('depositId').value=depositId;
	document.getElementById('accountNumber').value=accountNumber;

	  document.getElementById("goBtn").disabled = false;
	 document.getElementById("goBtn").title = "Click here to transfer amount";
	 }

function onChangeModeOfPay(value){
	
	
	
		if(value=="fundTransfer")
	{
			
	  var fundTransfer = document.getElementById('fundTransfer').value; 
	  document.getElementById('fundTransferError').style.display = 'none';
	  if(fundTransfer==null || fundTransfer=='')
	  {
		     document.getElementById('fundTransferError').style.display = 'block';
	  }
	  
	}
	
	
}



function search(){
	
	 document.getElementById('fundTransferError').style.display = 'none';
    var size= <c:out value="${accList.size()}"/>
     if(size>0){
     $("#fdForm").attr("action", "withdrawDeposit");
     $("#fdForm").submit();
     }
     else
     {
    	  document.getElementById('fundTransferError').style.display = 'block';  
     }
}





</script>
<div class="right-container" id="right-container">
	<div class="container-fluid">

		<div class="Flexi_deposit">

			<div class="header_customer" style="margin-bottom: 25px;">
				<h3>Withdraw from deposit</h3>
			</div>
			<div class="col-md-12" style="text-align: center;">
				<span style="color: red;">${error}</span>
			</div>
			<!-- action="withdrawDeposit" -->
			<div class="flexi_table">
				<form:form class="form-horizontal" id="fdForm" method="post"
					name="deposit" commandName="withdrawForm">

					<form:hidden path="depositId" id="depositId" />
					<form:hidden path="accountNumber" d="accountNumber" />
					<form:hidden path="depositHolderId"
						value="${depositList[0].depositHolderId}" />

					<div class="col-sm-12">
						<div class="space-45"></div>
					</div>
					<c:if test="${! empty depositList}">
						<h4>
							<span style="color: red; margin-left: 31px;">*</span><b>Select
								the deposit</b>
						</h4>
						<span id="taxSavingError" class="error" style="display: none;"><font
							color="red"><spring:message code="label.taxSavingError" /></font></span>

						<span id="autoError" class="error" style="display: none;"><font
							color="red">Sorry you can't not withdraw amount from
								Auto/Sweep deposit.</font></span>
						<table class="table table-striped table-bordered"
							style="margin-left: 15px; width: 1050px">
							<tr>
								<td><b>Serial No.</b></td>
								<td><b>Account Number</b></td>
								<td><b>Deposit Id</b></td>
								<td><b>Deposit Type</b></td>
								<td><b>Contribution</b></td>
								<td><b>Created Date</b></td>

								<!-- <td><b>Deposit Category</b></td> -->

								<td><b><spring:message code="label.select" /></b></td>
							</tr>

							<c:forEach items="${depositList}" var="d" varStatus="status">

								<tr>
									<td><b> <c:out value="${status.index+1}"></c:out></b></td>
									<td><b> <c:out value="${d.accountNumber}"></c:out></b></td>
									<td><b> <c:out value="${d.depositId}"></c:out></b></td>
									<td><b> <c:out value="${d.depositType}"></c:out></b></td>
									<td><b> <c:out value="${d.contribution}"></c:out></b></td>
									<td><b> <fmt:formatDate pattern="dd/MM/yyyy"
												value="${d.createdDate}" /></b></td>
									<%-- <td><b> <c:out value="${d.depositCategory}"></c:out></b></td> --%>
									<td><b><input type="radio" name="radioBtn"
											onclick="onclickRadio('${d.depositId}','${d.accountNumber}','${d.depositCategory}','${d.newMaturityDate}','${d.depositClassification}','${d.accountAccessType}','${d.deathCertificateSubmitted}','${d.depositHolderStatus}')"
											id="radioBtm" class="radioBtm" value="${d.depositId}" /> <form:radiobutton
												style="display:none" path="customerId" checked="false" /> </b></td>


								</tr>

							</c:forEach>

						</table>

					</c:if>



					<span id="fundTransferError" style="display: none; color: red;">No
						linked Account for Fund Transfer</span>

					<span id="dayError" style="color: red; margin-left: 398px;"></span>


					<div class="form-group">
						<label class="col-md-4 control-label"></label>
						<div class="col-md-6">

							<input type="button" id="goBtn" disabled="true"
								class="btn btn-info" onclick="search()"
								title="Please select the account number first"
								value="<spring:message code="label.proceed"/>">

						</div>
					</div>

				</form:form>
			</div>

		</div>
	</div>
</div>
<script>
$(document).ready(function(){

	var inp = document.querySelectorAll(".radioBtm");
	
	for(i=0;i<inp.length;i++){
		inp[i].checked=false;
		}
		

	
	
});
</script>
<script>
		function validation(){
			if (document.getElementById('fdPay').value == 'Select') {
				document.getElementById('fdPay').style.borderColor = "red";
				return false;
			} 
			
			if (document.getElementById('fundTransfer').value == '') {
				document.getElementById('fundTransfer').style.borderColor = "red";
				return false;
			} 
		}
		
		
		
	function val() {

		var accountNo = document.getElementById('fdID').value;
		var account = accountNo.toString();

		if (account == 'select') {
			document.getElementById('fdID').style.borderColor = "red";
			return false;
		} else {
			document.getElementById('fdID').style.borderColor = "green";
		}
		
		if (document.getElementById('fdPay').value == 'select') {
			document.getElementById('fdPay').style.borderColor = "red";
			return false;
		} else {
			document.getElementById('fdPay').style.borderColor = "green";
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