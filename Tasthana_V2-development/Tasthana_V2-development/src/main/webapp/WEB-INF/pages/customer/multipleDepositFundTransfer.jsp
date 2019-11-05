<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!-- To fetch the request url -->
<c:set var="req"
	value="${requestScope['javax.servlet.forward.request_uri']}" />
<c:set var="baseURL" value="${fn:split(req,'/')}" />


<script type="text/javascript">
var minTopupDays="";
	$(document).ready(function() {
		
		//minTopupDays=parseInt("${config.payAndWithdrawTenure}");
		window.arrayId = [];
		document.getElementById('totalTransferAmt').style.display = 'none';
		var account=document.getElementById('accountNo').value;
		document.getElementById('blankFormError').innerHTML='';
		document.getElementById('succMsg').innerHTML='';

		// deselect all check boxes
		//UnSelectAll();
		
		if(account!=""){
			var account = account.split(",");
			
			document.getElementById('accountType').value=account[1];
		
			document.getElementById('accountBalance').value = parseFloat(account[2]).toLocaleString("en-US");
			document.getElementById('accountBalance1').value = account[2];
			
			document.getElementById('accountTypeDiv').style.display='block';
			document.getElementById('accountBalanceDiv').style.display='block';
			//document.getElementById('fundTransferLinkedAccount').style.display='block';
			
			}
			else{
				document.getElementById('accountTypeDiv').style.display='none';
				document.getElementById('accountBalanceDiv').style.display='none';
				//document.getElementById('fundTransferLinkedAccount').style.display='none';
				
			}
	});

	var globalVariable;
	


	function enterAmount(id,index) {
		
		var amount = document.getElementById("multipleTransferAmount[" + id
				+ "]").value;
		var arrayId = [];		
		var sum = 0;
		var taskArray = new Array();	
		$("input[name=multipleTransferAmount]").each(function() {
			taskArray.push($(this).val());
		});

		for (var i = 0; i <= taskArray.length - 1; i++) {
			if (!isNaN(taskArray[i])) {
				if (taskArray[i] != "")
					sum += parseFloat(taskArray[i]);
				
			}
		}

		globalVariable = sum.toFixed(2);
		multipleIds = id;
		if (globalVariable != null)
			fun2(globalVariable);
		document.getElementById('totalTransferAmt').style.display = 'block';
		document.getElementById('totalTransferAmount').value = globalVariable;
	
	}

	function fun2() {
		var local = globalVariable;

	}
	
	function isNumber(evt, obj){
		 var charCode = (evt.which) ? evt.which : evt.keyCode;
		   if (charCode != 46 && charCode > 31 
		     && (charCode < 48 || charCode > 57)){
		    event.preventDefault();
			   return false;
		   }
	}
	
	function isNumber11(evt, obj){
		debugger;
		var depositHolderType=obj.attr("depositHolderStatus");
		var accountAccessType=obj.attr("accountAccessType");
		var isSubmitted=obj.attr("deathCertificateSubmitted");
		var depositId=obj.attr("did");
		
		if(isSubmitted=="1" || isSubmitted==1 || isSubmitted=="Yes")
		{
		alert('Account holder can not access account');
		 event.preventDefault();
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
			 event.preventDefault();
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
	 event.preventDefault();
	return false;
		}
		
		}
}
if(accountAccessType=="Jointly"){
	alert("You are not allowed to operate this account individually");
	 event.preventDefault();
	return false;
}
if(accountAccessType=="JointlyOrSurvivor"){
	var result=getDeathCertificate(depositId);
	if(result=="0"){
		alert("You are not allowed to operate this account individually");
		 event.preventDefault();
		return false;
	}
	
}

	
	
		 var charCode = (evt.which) ? evt.which : evt.keyCode;
		   if (charCode != 46 && charCode > 31 
		     && (charCode < 48 || charCode > 57)){
		    event.preventDefault();
			   return false;
		   }
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
		      url: "<%=request.getContextPath()%>/bnkEmp/getDeathCertificate", 
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
	
	function submit(e)
	{
		debugger;
		var topupAmount=parseInt($('#totalTransferAmount').val());
		var balanceAmount=parseInt($('#accountBalance1').val());
		
		if(topupAmount>balanceAmount){
			document.getElementById('succMsg').innerHTML='';
			document.getElementById('blankFormError').innerHTML ="Insufficient balance in account.<br/>";
			return false;
		}
		var d = getTodaysDate();
		var succ = false;
		var date=new Date(d);
		var taskArray = new Array();
		var submit=true;isMin=true;
		//var minTopup=parseInt("${minTopup}");
		//alert(minTopup);
		
		
		
		
		
		$("input[name=multipleTransferAmount]").css('border','1px solid #ccc');
		document.getElementById('blankFormError').innerHTML="";
		$("input[name=multipleTransferAmount]").each(function() {
			
			var maturityDate=new Date($(this).attr('maturityDate'));
			var depositDate=new Date($(this).attr('depositDate'));
			var isTopup=$(this).attr("isTopup");
			var minTopup=$(this).attr("minTopup");
			var maxTopup=$(this).attr("maxTopup");
			var lockingArray=$(this).attr("lockingPeriodForWithdraw").split(',');
			var lockingDays=0;
			if(lockingArray[0]!=""){lockingDays +=parseInt(lockingArray[0])*365;}
			if(lockingArray[1]!=""){lockingDays +=parseInt(lockingArray[1])*30;}
			if(lockingArray[2]!=""){lockingDays +=parseInt(lockingArray[2]);}
			var diff  = new Date(maturityDate - date);
			   var days  =parseInt(diff/1000/60/60/24);
			   
			   var diff1  = new Date(date - depositDate);
			   var days1  =parseInt(diff1/1000/60/60/24);
			   minTopupDays=parseInt($(this).attr("minTopupDays"));
			  debugger;
				   if($(this).val()!="")
					{
					  
					   if(isTopup==0|| isTopup=="0")
						{
							isMin=false;
							document.getElementById('blankFormError').innerHTML +='Top up not allowed for Deposit '+$(this).attr("did")+'. <br/>';
							$(this).css('border','1px solid red');
						}else if(days1 < lockingDays){
						    isMin=false;
						    document.getElementById('succMsg').innerHTML='';
							document.getElementById('blankFormError').innerHTML +='Deposit '+$(this).attr("did")+' is in locking period. So you can not make topup. <br/>';
							$(this).css('border','1px solid red');
						} 
						else if(days < minTopupDays){
						    isMin=false;
						    document.getElementById('succMsg').innerHTML='';
							document.getElementById('blankFormError').innerHTML +='Deposit '+$(this).attr("did")+' will be matured by next '+days+' days. So you can not make topup. <br/>';
							$(this).css('border','1px solid red');
						} else if(parseInt($(this).val())<parseInt(minTopup)){
							isMin=false;
							document.getElementById('blankFormError').innerHTML +='Minimum deposit amount for topup must be greater than '+minTopup+'. <br/>';
							$(this).css('border','1px solid red');
						}
						else if(parseInt($(this).val())>parseInt(maxTopup)){
							isMin=false;
							document.getElementById('blankFormError').innerHTML +='Maximum deposit amount for topup must be lesser than '+minTopup+'. <br/>';
							$(this).css('border','1px solid red');
						}
						
					   else{
						   
								var rowVal = $(this).attr("id") + "|" + $(this).val() + "#";
								taskArray.push(rowVal);
							
					   }
					}
					
			  });
		
		if(taskArray.length == 0 && isMin==true)
			{
			    document.getElementById('succMsg').innerHTML='';
				document.getElementById('blankFormError').innerHTML='Please enter amount for atleast one deposit.<br>';
				return false;
			}
		
		if(taskArray.length == 0){
			return false;
		}
	var length=taskArray.length;	
	$.ajax({
		  url: "<%=request.getContextPath()%>/users/multipleDepositFundTransferPost",
		  data: "transferArrList=" + taskArray,
		  type: 'post',
		  async: true,
		  success: function(data) {
			  succ = true;
			  //document.getElementById('blankFormError').innerHTML='';
			  document.getElementById('succMsg').innerHTML='Transferred amount successfully for '+length+' deposits.<br>';
			  $("input[name=multipleTransferAmount]").each(function() {
					if($(this).val()!="")
						{
						
						 var id = $(this).attr("id");		
						document.getElementById(id).value= "";
						document.getElementById('totalTransferAmount').value = "";
						globalVariable=0;
						}
				});
		}});
	

	}

	
</script>
<div class="right-container" id="right-container">
	<div class="container-fluid">


		<div class="Flexi_deposit">

			<div class="header_customer">
				<h3>
					<spring:message code="label.fundTransferForMultipleDeposit" />
				</h3>
			</div>
			<div class="Success_msg">
				<div class="successMsg"
					style="text-align: center; color: green; font-size: 18px;">
					<h2>${success}</h2>
				</div>
			</div>



			<div class="flexi_table">
				<form:form method="post" class="form-horizontal" name="fixedDeposit"
					commandName="fundTransferForm" id="fundTransfer">
					<div class="col-md-6">

						<div class="form-group">
							<label class="col-md-4 control-label">Current/<spring:message
									code="label.savingAccountNo" /></label>
							<div class="col-md-6">
								<form:select path="accountNo" class="form-control"
									id="accountNo" onchange="onChangeAccountNo(this.value)">
									<form:option value="">Select</form:option>
									<c:forEach items="${fundTransferForm.accountList}"
										var="account">
										<fmt:formatNumber value="${account.accountBalance}"
											pattern="#.##" var="accBal1" />
										<option
											value="${account.accountNo},${account.accountType},${accBal1}"
											<c:if test="${account.accountType =='Savings'}" >selected="true"</c:if>>${account.accountNo}
										</option>
									</c:forEach>
								</form:select>

							</div>

						</div>



						<script>
							function onChangeAccountNo(account) {

								if (account != "") {
									var account = account.split(",");
									document.getElementById('accountType').value = account[1];
									document.getElementById('accountBalance').value = parseFloat(
											account[2]).toLocaleString("en-US");
									document.getElementById('accountBalance1').value = account[2];

									document.getElementById('accountTypeDiv').style.display = 'block';
									document
											.getElementById('accountBalanceDiv').style.display = 'block';
									
									if (document.getElementById('paymentMode').value == 'Fund Transfer') {
										var accArray = $('#accountNo').val()
												.split(',');
										document
												.getElementById('linkedAccount').value = accArray[0];

										document
												.getElementById('linkedAccountBalance1').value = $(
												'#accountBalance1').val();
										document
												.getElementById('linkedAccountBalance').value = parseFloat(
												$('#accountBalance1').val())
												.toLocaleString("en-US");

									}

								} else {
									document.getElementById('accountTypeDiv').style.display = 'none';
									document
											.getElementById('accountBalanceDiv').style.display = 'none';

									if (document.getElementById('paymentMode').value == 'Fund Transfer') {
										document.getElementById('paymentMode').value = "Select";
									}
									document
											.getElementById('linkedAccountBalanceTr').style.display = 'none';
									document.getElementById('linkedAccountTr').style.display = 'none';

									
								}
							}
						</script>


						<div class="form-group" id="accountTypeDiv">
							<label class="col-md-4 control-label"><spring:message
									code="label.accountType" /></label>
							<div class="col-md-6">

								<form:input path="accountType" class="form-control"
									value="Savings" id="accountType" readonly="true" />

							</div>

						</div>
                        <div class="Success_msg">
					      <div class="successMsg" style="text-align: center; color: red;">
						     ${error}
						 </div>
			        	</div>
						<div class="form-group" id="accountBalanceDiv">
							<label class="col-md-4 control-label"><spring:message
									code="label.accountBalance" /></label>


							<div class="col-md-6">
								<form:hidden path="accountBalance" id="accountBalance1" />
								<input class="form-control" id="accountBalance" readonly />
							</div>

						</div>
                        

						<div class="form-group" id="totalTransferAmt">
							<label class="control-label col-sm-4" for="">Total <spring:message
									code="label.amount" /><span style="color: red">*</span></label>
							<div class="col-sm-6">
								<form:input path="totalTransferAmount" readonly="true" type="number"
									class="input form-control" id="totalTransferAmount" />
							</div>


						</div>

					</div>

				
					<table class="table data jqtable example" id="my-table" style="width: 98%; margin: auto 7px;">
						<thead>
							<tr>
								<th><spring:message code="label.fdID" /></th>
								<th><spring:message code="label.accno" /></th>
								<th><spring:message code="label.currentBalance" /></th>

								<th><spring:message code="label.fdAmount" /></th>
								<th><spring:message code="label.maturityDate" /></th>
								<th><spring:message code="label.depositHolderStatus" /></th>
								<th><spring:message code="label.contribution" /></th>
								<th><spring:message code="label.createdDate" /></th>
								<th><spring:message code="label.status" /></th>
								<th>Deposit Category</th>
<%-- 								<th><spring:message code="label.select" /></th> --%>
								<th>Enter Amount</th>

							</tr>
						</thead>
						
						<c:if test="${! empty depositHolderList}">
							<c:forEach items="${depositHolderList}" var="depositHolder"
								varStatus="dl">
									<c:if test="${depositHolder.deposit.depositCategory!='AUTO'}">
								
								<tr> 
								<c:if test="${depositHolder.deposit.depositClassification!='Tax Saving Deposit'}">
									<td><c:out value="${depositHolder.deposit.id}"></c:out></td>
									<td><c:out value="${depositHolder.deposit.accountNumber}"></c:out></td>

									<fmt:formatNumber
										value="${depositHolder.deposit.currentBalance}" pattern="#.##"
										var="currentBalance" />
									<td class="commaSeparated">${currentBalance}</td>

									<fmt:formatNumber
										value="${depositHolder.deposit.depositAmount}" pattern="#.##"
										var="depositamount" />
									<td class="commaSeparated">${depositamount}</td>

									<td><fmt:formatDate pattern="dd/MM/yyyy"
											value="${depositHolder.deposit.newMaturityDate}" /></td>
									<td><c:out
											value="${depositHolder.depositHolder.depositHolderStatus}"></c:out></td>
									<td><c:out
											value="${depositHolder.depositHolder.contribution}"></c:out></td>
									<td><fmt:formatDate pattern="dd/MM/yyyy"
											value="${depositHolder.deposit.createdDate}" /></td>
									<td><c:out value="${depositHolder.deposit.status}"></c:out></td>
									<td><c:if
											test="${empty depositHolder.deposit.depositCategory}">Regular</c:if>
										<c:out value="${depositHolder.deposit.depositCategory}"></c:out></td>
									

<%-- 									<td><b><form:checkbox --%>
<%-- 												path="depositHolderObjList[${dl.index}].deposit.id" --%>
<%-- 												value="${depositHolder.deposit.id}" name="checkDeposit" --%>
<%-- 												id="check[${depositHolder.deposit.id}]" --%>
<%-- 												onclick="check('${depositHolder.deposit.id}','${dl.index}')" /></b></td> --%>
									<td><c:if test="${depositHolder.deposit.depositClassification!='Tax Saving Deposit'}"><b><form:input path="multipleTransferAmount" accountAccessType="${depositHolder.deposit.accountAccessType}" depositHolderStatus="${depositHolder.depositHolder.depositHolderStatus}" deathCertificateSubmitted="${depositHolder.depositHolder.deathCertificateSubmitted}" isTopup="${depositHolder.productConfiguration.isTopupAllowed}"
												name="multipleTransferAmount" type="number"	class="input form-control"  depositDate="${depositHolder.deposit.createdDate }" minTopupDays="${depositHolder.productConfiguration.preventionOfTopUpBeforeMaturity}" maturitydate="${depositHolder.deposit.newMaturityDate}" minTopup="${depositHolder.productConfiguration.minimumTopupAmount }" maxTopup="${depositHolder.productConfiguration.maximumTopupAmount }" lockingPeriodForWithdraw="${depositHolder.productConfiguration.lockingPeriodForTopup }" onkeypress="isNumber11(event,$(this))"
												id="multipleTransferAmount[${depositHolder.deposit.id}]" did="${depositHolder.deposit.id}"
												onkeyup="enterAmount('${depositHolder.deposit.id}','${depositHolder.deposit.accountNumber}','${dl.index}')" /></b></c:if></td>
</c:if>
								</tr>
								</c:if>
							</c:forEach>
						</c:if>

						</tbody>
					</table>
			

					<div class="form-group">
						<div class="col-md-offset-4 col-md-8"></div>
					</div>

				</form:form>
				<center>
				<span id="blankFormError" style="color: red;"></span> 
					
					<span id="succMsg" style="color: blue;"></span> 
					
					<span id="selectError" style="display: none; color: red;">Please select the check box.</span>
					<input style=" margin-bottom: 25px;" type="button" onclick="submit(event)"
					id="submit" class="btn btn-success" value="Submit" /> 
				
				</center>
				
					
					
			</div>

		</div></div></div>
		<style>
.form-control {
	background: whitesmoke;
	color: #000;
	cursor: pointer;
}
</style>