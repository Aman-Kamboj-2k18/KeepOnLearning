<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>



<!-- To fetch the request url -->
<c:set var="req"
	value="${requestScope['javax.servlet.forward.request_uri']}" />
<c:set var="baseURL" value="${fn:split(req,'/')}" />
<script>


var form_clean;
var form_dirty;
var changed = 0;
var n=<c:out value="${depositHolderForm.benificiaryList.size()}"/>;


// serialize clean form
$(function() {
	window.form_clean = $("form").serialize();

});




function removeBeneficiary(id,index){

		 document.getElementById('benificiary.isActive'+index).value=0;
		 document.getElementById(id).style.display='none';
	
	 window.n=parseInt(window.n)-1;
	
	
	  
}


		
 
$(document)
.ready(
		function() {

			$("body").on(
					"click",
					".remove-me",
					function(e) {
					
						$(this).parent("div").remove();
								
					 window.n=parseInt(window.n)-1;
					 
					
					var oldBeneficiarySize= <c:out value="${depositHolderForm.benificiaryList.size()}"/>;
				
					if(window.n==oldBeneficiarySize)
					 document.getElementById("newBeneficiaryHeading").innerHTML="";
					 
					});
			
			
			
			/********** for add/reduce tenure *********/
			var reduceTenureRadio = document
					.getElementById('reduceTenureRadio').checked;
			var addTenureRadio = document
					.getElementById('addTenureRadio').checked;

			if (addTenureRadio == true || reduceTenureRadio == true) {
				document.getElementById("addReduceTenureDiv").style.display = 'block';
				document.getElementById("newMaturityDateDiv").style.display = 'block';
			} else {
				document.getElementById("addReduceTenureDiv").style.display = 'none';
				document.getElementById("newMaturityDateDiv").style.display = 'none';
			}

		});

	function ajaxFun(callback) {
		
		if (window.changed == 0) {
			onChangeTenure()
		}

		var deposit = [];
		var editDepositForm = [];
		deposit.id = '${depositHolderForm.deposit.id}';
		deposit.createdDate = $('#createdDate').val();
		deposit.modifiedDate = $('#modifiedDate').val();
		deposit.modifiedInterestRate = '${depositHolderForm.deposit.modifiedInterestRate}';
		deposit.depositAmount = '${depositHolderForm.deposit.depositAmount}';
		deposit.dueDate = $('#fdDueDate').val();
		deposit.paymentType = '${depositHolderForm.deposit.paymentType}';
		deposit.depositCategory = '${depositHolderForm.deposit.depositCategory}';
		var holderId = '${depositHolderForm.depositHolder.id}';

		editDepositForm.maturityDateNew = $('#newMaturityDate').val();
		var category = '${depositHolderForm.depositHolder.depositHolderCategory}';

		var data = " deposit.id=" + deposit.id + "&deposit.createdDate="
				+ deposit.createdDate + "&deposit.modifiedDate="
				+ deposit.modifiedDate + "&deposit.interestRate="
				+ deposit.modifiedInterestRate + "&deposit.depositAmount="
				+ deposit.depositAmount + "&deposit.dueDate=" + deposit.dueDate
				+ "&deposit.paymentType=" + deposit.paymentType
				+ "&deposit.depositCategory=" + deposit.depositCategory
				+ "&editDepositForm.maturityDateNew="
				+ editDepositForm.maturityDateNew + "&category=" + category
				+ "&depositHolderList[0].id=" + holderId;

		
		var urlArray= "${req}".split("/");
		var url="/"+urlArray[1]+"/"+urlArray[2]+"/compareMaturity";
			$.ajax({
				url : url,
				type : "POST",
				cache : false,
				data : "deposit.id=" + deposit.id + "&deposit.createdDate="
						+ deposit.createdDate + "&deposit.modifiedDate="
						+ deposit.modifiedDate + "&deposit.interestRate="
						+ deposit.modifiedInterestRate
						+ "&deposit.depositAmount=" + deposit.depositAmount
						+ "&deposit.dueDate=" + deposit.dueDate
						+ "&deposit.paymentType=" + deposit.paymentType
						+ "&editDepositForm.maturityDateNew="
						+ editDepositForm.maturityDateNew + "&depositHolder.depositHolderCategory="
						+ category + "&depositHolderList[0].id=" + holderId,
				success : function(data) {

						if (data == "") {
							callback(false);
						} else {

							var adjustAmt = data.split(",")[0];
							var newInterestRate = data.split(",")[1];
							var newInterestAmount = data.split(",")[2];

							//Shows dialog
							var modal = document.getElementById('myModal');
							modal.style.display = "block";
							document.getElementById('oldMaturityDate').innerHTML = document
									.getElementById('maturityDate').value;
							document.getElementById('newMaturityDate2').innerHTML = document
									.getElementById('newMaturityDate').value;
							document.getElementById('newRate').innerHTML = newInterestRate;
							document.getElementById('oldRate').innerHTML = deposit.modifiedInterestRate;
							document.getElementById('adjustAmt').innerHTML = adjustAmt;

							var proceed = document.getElementById('proceed');
							var cancel = document.getElementById('cancel');
							proceed.onclick = function() {
								modal.style.display = "none";
								callback(true);
							}
							cancel.onclick = function() {
								modal.style.display = "none";
								callback(false);
							}

						}

					},
					error : function(e) {
						console.log("error");
					}
				});

	}




	function showAddReduceTenure(id) {
		 var depositClasification = '${depositHolderForm.deposit.depositClassification}'; 
		 var taxSavingDeposit = '${depositHolderForm.deposit.taxSavingDeposit}'; 
         if(depositClasification == 'Tax Saving Deposit' && taxSavingDeposit == '1'){
        	document.getElementById('taxSavingDepositError').style.display = 'block';
			return;
	
        }

		document.getElementById(id).style.display = 'block';
		onChangeTenure();
	}

	function hideDiv(value) {

		if (value == 'tenureType') {
			document.getElementById('reduceTenureRadio').checked = false;
			document.getElementById('addTenureRadio').checked = false;
			document.getElementById("addReduceTenureDiv").style.display = 'none';
			document.getElementById("newMaturityDateDiv").style.display = 'none';
			window.changed = 0;
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
	function onChangeTenure() {

		var newTenure = $('#fdTenure').val();
		var newTenureType = $('#fdTenureType').val();

		if (newTenure == "" || newTenureType == "") {
			document.getElementById('newMaturityDateDiv').style.display = 'none';
			return;
		}

		newTenure = parseInt(newTenure);
		var newTenureType = $('#fdTenureType').val();

		var maturityDateArray = $('#maturityDate').val().split("/");
		var maturityDate = new Date(maturityDateArray[2],
				maturityDateArray[1] - 1, maturityDateArray[0]);

		var reduceTenureRadio = document.getElementById('reduceTenureRadio').checked;
		var addTenureRadio = document.getElementById('addTenureRadio').checked;

		if (reduceTenureRadio) {

			if (newTenureType == 'Day') {
				maturityDate.setDate(maturityDate.getDate() - newTenure);
			}
			if (newTenureType == 'Month') {
				maturityDate.setMonth(maturityDate.getMonth() - newTenure);
			}
			if (newTenureType == 'Year') {
				maturityDate
						.setFullYear(maturityDate.getFullYear() - newTenure);
			}

		}
		var today = getTodaysDate();
		if (addTenureRadio) {
			if (newTenureType == 'Day') {
				maturityDate.setDate(maturityDate.getDate() + newTenure);
			}
			if (newTenureType == 'Month') {
				maturityDate.setMonth(maturityDate.getMonth() + newTenure);
			}
			if (newTenureType == 'Year') {
				maturityDate
						.setFullYear(maturityDate.getFullYear() + newTenure);
			}

		}

		document.getElementById('tenureError').innerHTML = '';
		if (maturityDate < today) {
			document.getElementById('fdTenure').value = '';
			document.getElementById('fdTenureType').value = '';
			hideDiv('tenureType');
			document.getElementById('tenureError').innerHTML = 'Please enter valid tenure';
			return;
		}
		var day = maturityDate.getDate();
		var month = parseInt(maturityDate.getMonth() + 1);
		if (month < 10) {
			month = "0" + month;
		}
		var year = maturityDate.getFullYear();

		maturityDate = day + "/" + month + "/" + year;
		document.getElementById('newMaturityDateDiv').style.display = 'block';
		document.getElementById('newMaturityDate').value = maturityDate;

		window.changed = 1;
	}

	function val() {
		
		document.getElementById('validationError').innerHTML = '';

		var inp = document.querySelectorAll('.input:not([type="hidden"])');

		for (i = 0; i < inp.length; i++) {
			inp[i].style.borderColor = "#cccccc";
		}

		/****** no data changed validation start********/
		window.form_dirty = $("form").serialize();
		if (window.form_clean == window.form_dirty) {
			document.getElementById('validationError').innerHTML = 'No changes made';
			return false
		}
		/******no data changed validation  end ********/

		/****** add/reduce tenure validation start********/

		var submit = true;

		/******add/reduce tenure end ********/

		var addTenureRadio = document.getElementById('addTenureRadio').checked;
		var reduceTenureRadio = document.getElementById('reduceTenureRadio').checked;
		
		if (reduceTenureRadio == true || addTenureRadio == true) {

			var fdTenure = parseFloat(document.getElementById('fdTenure').value);
			var fdTenureType = document.getElementById('fdTenureType').value;

			if (fdTenureType == '') {
				document.getElementById('fdTenureType').style.borderColor = "red";
				document.getElementById('validationError').innerHTML += 'Please select tenure type<br>'
				submit = false;
			}

			if (isNaN(fdTenure)) {
				document.getElementById('fdTenure').style.borderColor = "red";
				document.getElementById('validationError').innerHTML += 'Please enter valid tenure<br>'
				submit = false;
			}

		}
		
	
		     
		if (submit == false) {
			return;
		}
		
		/******add/reduce tenure end ********/

		/****** Beneficiary validation start********/

		
	var beneficiaryCount= parseInt(window.n);
	
		if(beneficiaryCount==0){
			validationError.innerHTML += "<br>Please enter atleast one beneficiary";
		    submit = false;
		}
		else{
			   var totAmount = 0;
			for(var i=0;i<beneficiaryCount;i++){
				
				var benificiaryName=document.getElementById('benificiaryName'+i).value;
				var accountNo=document.getElementById('accountNo'+i).value;
				var ifscCode=document.getElementById('ifscCode'+i).value;
				var amountToTransfer=document.getElementById('amountToTransfer'+i).value;
				var remark=document.getElementById('remark'+i).value;
		
				if(benificiaryName ==""){
					document.getElementById("benificiaryName"+i).style.borderColor = "red";
					submit = false;
				}
				
				if(accountNo ==""){
					document.getElementById("accountNo"+i).style.borderColor = "red";
						submit = false;
				}
				if(ifscCode ==""){
					document.getElementById("ifscCode"+i).style.borderColor = "red";
					submit = false;
				}
				if(amountToTransfer =="" || isNaN(amountToTransfer)){
					document.getElementById("amountToTransfer"+i).style.borderColor = "red";
						submit = false;
				}
				else{
					totAmount = parseFloat(totAmount) + parseFloat(amountToTransfer);
				}
				
				if(remark ==""){
					document.getElementById("remark"+i).style.borderColor = "red";
					submit = false;
				}
				
				
			}
			
			if (submit == false) {
				validationError.innerHTML = "<br>Please enter all the beneficiary details";
				return;
			}
			   
			   
			   var emiAmount = document.getElementById('emiAmt').value;
			     if(parseFloat(totAmount) != parseFloat(emiAmount))
			     {
			          validationError.innerHTML = "<br>EMI amount should be the sum of all beneficiaries transfer amount.";
					return;
			     }
			     
			
		}
	

	
		if (reduceTenureRadio == false && addTenureRadio == false) {
			$("#editForm").attr("action", "confirmEditReverseEMI?holderId=${holderId}");
			$("#editForm").submit();
		} else {

			ajaxFun(function(d) {

				if (d == true) {
					$("#editForm").attr("action", "confirmEditReverseEMI?holderId=${holderId}");
					$("#editForm").submit();
				} else {
					document.getElementById('error').innerHTML = 'Cannot be modified now. Please try later.'
				}
			})

		}

	}
	/* 	Payoff validation end  */
</script>
<div class="right-container" id="right-container">
	<div class="container-fluid">

		<div class="Flexi_deposit">

			<div class="header_customer">
				<h3>Reverse Annuity Modification</h3>
			</div>
			<div class="Success_msg">
				<div class="successMsg"
					style="text-align: center; color: green; font-size: 18px;">
					<h2>${success}</h2>
				</div>
				<div style="text-align: center; font-size: 18px;">
					<h5 style="color: red;" id="error">${error}</h5>
				</div>
			</div>



			<div class="flexi_table">
				<form:form action="confirmEditFd" class="form-horizontal"
					method="post" name="fixedDeposit" commandName="depositHolderForm"
					id="editForm">


					<form:hidden path="deposit.interestRate" />
					<form:hidden path="deposit.tenureType" />
					<form:hidden path="deposit.tenure" />
					<form:hidden path="depositHolder.id" />
					<form:hidden path="depositHolder.customerId" />
					<form:hidden path="deposit.modifiedDate" id="modifiedDate" />
					<form:hidden path="deposit.dueDate" id="fdDueDate" />
                     <form:hidden path="deposit.depositCategory"  />
                     <form:hidden path="depositHolder.depositHolderCategory" />



					<div class="col-md-6">
						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.fdID" /></label>
							<div class="col-md-6">
								<form:input path="deposit.id" class="form-control" id="fdID"
									readonly="true" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.accountNo" /></label>
							<div class="col-md-6">
								<form:input path="deposit.accountNumber" class="form-control"
									id="customerName" readonly="true" />
							</div>
						</div>




						<div class="form-group">
							<label class="col-md-4 control-label">Maturity Date</label>
							<div class="col-md-6">
								<form:input path="deposit.maturityDate" id="maturityDate"
									class="form-control" readonly="true" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-4 control-label">Tenure</label>
							<div class="col-md-6">
								<input class="form-control"
									value="${depositHolderForm.deposit.tenure} ${depositHolderForm.deposit.tenureType}"
									id="tenureConcat" readonly />
							</div>
						</div>


						<div class="form-group">
							<label class="control-label col-md-4" for=""
								style="margin-top: -5px;">Add/Reduce Tenure </label> <label
								for="radio" style="margin-left: 15px;"> <form:radiobutton
									path="editDepositForm.statusTenure" value="add"
									id="addTenureRadio" name="addTenureRadio"
									onclick="showAddReduceTenure('addReduceTenureDiv')"></form:radiobutton></label>
							Add <label for="radio"> <form:radiobutton
									path="editDepositForm.statusTenure" value="reduce"
									id="reduceTenureRadio" name="reduceTenureRadio"
									onclick="showAddReduceTenure('addReduceTenureDiv')"></form:radiobutton></label>
							Reduce
						</div>
						<span id="taxSavingDepositError" class="error"
										style="display: none;"><font color="red"><spring:message
												code="label.taxSavingDepositError" /></font></span>
						<div id="addReduceTenureDiv">

							<label class="col-md-4 control-label">Enter Tenure</label>
							<div class="col-md-3">
								<form:select path="deposit.tenureType"
									class="form-control input" id="fdTenureType"
									onchange="onChangeTenure()">
									<option value="">Select</option>

									<option value="Day" id="days">
										<spring:message code="label.Day" />
									</option>
									<option value="Month">
										<spring:message code="label.Month" />
									</option>
									<option value="Year">
										<spring:message code="label.Year" />
								</form:select>
							</div>
							<div class="col-md-2">
								<form:input path="deposit.tenureType" type="number" min="1"
									class="form-control input" id="fdTenure"
									onBlur="onChangeTenure()" />
							</div>
							<div class="col-md-1">
								<a href="#" onclick="hideDiv('tenureType')">Cancel</a>
							</div>


						</div>
						<span style="color: red; margin-left: 178px;" id="tenureError"></span>
						<div id="newMaturityDateDiv" style="margin-top: 53px;">
							<label class="col-md-4 control-label">New maturity date</label>
							<div class="col-md-6">
								<form:input path="editDepositForm.maturityDateNew"
									class="form-control" id="newMaturityDate" readonly="true" />
							</div>
						</div>

						<div style="clear: both; height: 10px;"></div>

						<div style="clear: both; height: 15px;"></div>

					</div>


					<div class="col-md-6">

						<div class="form-group">
							<label class="col-md-4 control-label">Reverse EMI
								Category </label>
							<div class="col-md-6">
								<form:input path="deposit.reverseEmiCategory"
									value="${depositHolderForm.deposit.reverseEmiCategory}"
									class="form-control" id="contactNum" readonly="true" />
							</div>
						</div>


						<div class="form-group">
							<label class="col-md-4 control-label">Created Date</label>
							<div class="col-md-6">
								<form:input path="deposit.createdDate" class="form-control"
									id="createdDate" readonly="true" />
							</div>
						</div>



						<div class="form-group">
							<label class="col-md-4 control-label">Deposit Amount</label>
							<div class="col-md-6">
								<fmt:formatNumber
									value="${depositHolderForm.deposit.depositAmount}"
									pattern="#.##" var="depositamount" />
								<form:hidden path="deposit.depositAmount" id="fdFixed" />
								<input type="text" class="form-control commaSeparated"
									value="${depositamount}" readonly />

							</div>
						</div>

		               <div class="form-group">
							<label class="col-md-4 control-label">EMI Amount</label>
							<div class="col-md-6">
								<fmt:formatNumber
									value="${depositHolderForm.depositHolder.emiAmount}"
									pattern="#.##" var="emiAmount" />
								<form:hidden path="depositHolder.emiAmount" id="emiAmt" />
								<input type="text" class="form-control commaSeparated"
									value="${emiAmount}" readonly />

							</div>
						</div>
						
						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.gestationPeriod" /></label>
							<div class="col-md-6">
								<form:input path="deposit.gestationPeriod" class="form-control"
									id="createdDate" readonly="true" />
							</div>
						</div>
						
					</div>



					<div class="header_me col-md-12">
						<h3>Beneficiary Details</h3>
					</div>

					<div class="col-sm-12">
						<div class="col-md-12" style="padding-top: 1px;">
							<div id="addBeneficiaryError" style="color: red"></div>
							<div class="add-btn">
								<input type="button" id="add" onclick="addNewBeneficiary()"
									class="btn btn-success" value="Add New Beneficiary">
							</div>

						</div>
					</div>
<script>

 function addNewBeneficiary(){
	
	if (window.n <= 7) {
		document.getElementById("addBeneficiaryError").innerHTML="";
		document.getElementById("newBeneficiaryHeading").innerHTML="New Beneficiary Added"
			
		var idx= window.n+1;
		var div="";
		
		 if(window.n==0){
			 div='<div id="addedBeneficiary0" class="col-sm-6"><table class="theader" width="400" style="position: relative;left: 2.6%;"><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.benificiaryName"/></b><span style="color:red">*</span></td><td><form:hidden path="benificiary.benificiaryId"/><form:hidden path="benificiary.isActive"/><form:input path="benificiary.benificiaryName" placeholder="Enter Benificiary Name"  id="benificiaryName0" class="myform-control input" required="true"></form:input></td></tr><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.accountType"/></b><span style="color:red">*</span></td><td><form:select path="benificiary.bankAccountType"  id="bankAccountType0"  class="myform-control" required="true"><form:option value="Savings"><spring:message code="label.Saving"/></form:option><form:option value="Current"><spring:message code="label.Current"/></form:option></form:select> </td></tr><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.accountNo"/></b><span style="color:red">*</span></td><td><form:input path="benificiary.benificiaryAccountNumber" placeholder="Enter account number" id="accountNo0"  class="myform-control input" type="number" required="true"></form:input></td></tr><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.ifsc"/></b><span style="color:red">*</span></td><td><form:input path="benificiary.ifscCode" placeholder="Enter IFSC code"  id="ifscCode0"  class="myform-control input" required="true"></form:input></td></tr><tr><td></td><td></td></tr> <tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.amount"/></b><span style="color:red">*</span></td><td><form:input path="benificiary.amountToTransfer" placeholder="Amount"    id="amountToTransfer0"  class="myform-control input" type="number" required="true"></form:input></td></tr> <tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.remarks"/></b><span style="color:red">*</span></td><td><form:input path="benificiary.remarks" placeholder="Remarks" id="remark0"   class="myform-control input" required="true"></form:input></td></tr></table><button id="b1" class="btn btn-warning remove-me"  type="button">Delete</button></div>';	
		}
		 else if(window.n==1){
			 div='<div id="addedBeneficiary1" class="col-sm-6"><table class="theader" width="400" style="position: relative;left: 2.6%;"><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.benificiaryName"/></b><span style="color:red">*</span></td><td><form:hidden path="benificiary.benificiaryId"/><form:hidden path="benificiary.isActive"/><form:input path="benificiary.benificiaryName" placeholder="Enter Benificiary Name"  id="benificiaryName1" class="myform-control input" required="true"></form:input></td></tr><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.accountType"/></b><span style="color:red">*</span></td><td><form:select path="benificiary.bankAccountType"  id="bankAccountType1"  class="myform-control" required="true"><form:option value="Savings"><spring:message code="label.Saving"/></form:option><form:option value="Current"><spring:message code="label.Current"/></form:option></form:select> </td></tr><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.accountNo"/></b><span style="color:red">*</span></td><td><form:input path="benificiary.benificiaryAccountNumber" placeholder="Enter account number" id="accountNo1"  class="myform-control input" type="number" required="true"></form:input></td></tr><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.ifsc"/></b><span style="color:red">*</span></td><td><form:input path="benificiary.ifscCode" placeholder="Enter IFSC code"  id="ifscCode1"  class="myform-control input" required="true"></form:input></td></tr><tr><td></td><td></td></tr> <tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.amount"/></b><span style="color:red">*</span></td><td><form:input path="benificiary.amountToTransfer" placeholder="Amount"    id="amountToTransfer1"  class="myform-control input" type="number" required="true"></form:input></td></tr> <tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.remarks"/></b><span style="color:red">*</span></td><td><form:input path="benificiary.remarks" placeholder="Remarks" id="remark1"   class="myform-control input" required="true"></form:input></td></tr></table><button id="b1" class="btn btn-warning remove-me"  type="button">Delete</button></div>';
		}
		else if(window.n==2){
			 div='<div id="addedBeneficiary2" class="col-sm-6"><table class="theader" width="400" style="position: relative;left: 2.6%;"><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.benificiaryName"/></b><span style="color:red">*</span></td><td><form:hidden path="benificiary.benificiaryId"/><form:hidden path="benificiary.isActive"/><form:input path="benificiary.benificiaryName" placeholder="Enter Benificiary Name"  id="benificiaryName2" class="myform-control input" required="true"></form:input></td></tr><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.accountType"/></b><span style="color:red">*</span></td><td><form:select path="benificiary.bankAccountType"  id="bankAccountType2"  class="myform-control" required="true"><form:option value="Savings"><spring:message code="label.Saving"/></form:option><form:option value="Current"><spring:message code="label.Current"/></form:option></form:select> </td></tr><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.accountNo"/></b><span style="color:red">*</span></td><td><form:input path="benificiary.benificiaryAccountNumber" placeholder="Enter account number" id="accountNo2"  class="myform-control input" type="number" required="true"></form:input></td></tr><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.ifsc"/></b><span style="color:red">*</span></td><td><form:input path="benificiary.ifscCode" placeholder="Enter IFSC code"  id="ifscCode2"  class="myform-control input" required="true"></form:input></td></tr><tr><td></td><td></td></tr> <tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.amount"/></b><span style="color:red">*</span></td><td><form:input path="benificiary.amountToTransfer" placeholder="Amount"    id="amountToTransfer2"  class="myform-control input" type="number" required="true"></form:input></td></tr> <tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.remarks"/></b><span style="color:red">*</span></td><td><form:input path="benificiary.remarks" placeholder="Remarks" id="remark2"   class="myform-control input" required="true"></form:input></td></tr></table><button id="b1" class="btn btn-warning remove-me"  type="button">Delete</button></div>';
		}
		else if(window.n==3){
			 div='<div id="addedBeneficiary3" class="col-sm-6"><table class="theader" width="400" style="position: relative;left: 2.6%;"><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.benificiaryName"/></b><span style="color:red">*</span></td><td><form:hidden path="benificiary.benificiaryId"/><form:hidden path="benificiary.isActive"/><form:input path="benificiary.benificiaryName" placeholder="Enter Benificiary Name"  id="benificiaryName3" class="myform-control input" required="true"></form:input></td></tr><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.accountType"/></b><span style="color:red">*</span></td><td><form:select path="benificiary.bankAccountType"  id="bankAccountType3"  class="myform-control" required="true"><form:option value="Savings"><spring:message code="label.Saving"/></form:option><form:option value="Current"><spring:message code="label.Current"/></form:option></form:select> </td></tr><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.accountNo"/></b><span style="color:red">*</span></td><td><form:input path="benificiary.benificiaryAccountNumber" placeholder="Enter account number" id="accountNo3"  class="myform-control input" type="number" required="true"></form:input></td></tr><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.ifsc"/></b><span style="color:red">*</span></td><td><form:input path="benificiary.ifscCode" placeholder="Enter IFSC code"  id="ifscCode3"  class="myform-control input" required="true"></form:input></td></tr><tr><td></td><td></td></tr> <tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.amount"/></b><span style="color:red">*</span></td><td><form:input path="benificiary.amountToTransfer" placeholder="Amount"    id="amountToTransfer3"  class="myform-control input" type="number" required="true"></form:input></td></tr> <tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.remarks"/></b><span style="color:red">*</span></td><td><form:input path="benificiary.remarks" placeholder="Remarks" id="remark3"   class="myform-control input" required="true"></form:input></td></tr></table><button id="b1" class="btn btn-warning remove-me"  type="button">Delete</button></div>';
				
		}
		else if(window.n==4){
			 div='<div id="addedBeneficiary4" class="col-sm-6"><table class="theader" width="400" style="position: relative;left: 2.6%;"><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.benificiaryName"/></b><span style="color:red">*</span></td><td><form:hidden path="benificiary.benificiaryId"/><form:hidden path="benificiary.isActive"/><form:input path="benificiary.benificiaryName" placeholder="Enter Benificiary Name"  id="benificiaryName4" class="myform-control input" required="true"></form:input></td></tr><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.accountType"/></b><span style="color:red">*</span></td><td><form:select path="benificiary.bankAccountType"  id="bankAccountType4"  class="myform-control" required="true"><form:option value="Savings"><spring:message code="label.Saving"/></form:option><form:option value="Current"><spring:message code="label.Current"/></form:option></form:select> </td></tr><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.accountNo"/></b><span style="color:red">*</span></td><td><form:input path="benificiary.benificiaryAccountNumber" placeholder="Enter account number" id="accountNo4"  class="myform-control input" type="number" required="true"></form:input></td></tr><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.ifsc"/></b><span style="color:red">*</span></td><td><form:input path="benificiary.ifscCode" placeholder="Enter IFSC code"  id="ifscCode4"  class="myform-control input" required="true"></form:input></td></tr><tr><td></td><td></td></tr> <tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.amount"/></b><span style="color:red">*</span></td><td><form:input path="benificiary.amountToTransfer" placeholder="Amount"    id="amountToTransfer4"  class="myform-control input" type="number" required="true"></form:input></td></tr> <tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.remarks"/></b><span style="color:red">*</span></td><td><form:input path="benificiary.remarks" placeholder="Remarks" id="remark3"   class="myform-control input" required="true"></form:input></td></tr></table><button id="b1" class="btn btn-warning remove-me"  type="button">Delete</button></div>';
				
		}
		else if(window.n==5){
			 div='<div id="addedBeneficiary5" class="col-sm-6"><table class="theader" width="400" style="position: relative;left: 2.6%;"><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.benificiaryName"/></b><span style="color:red">*</span></td><td><form:hidden path="benificiary.benificiaryId"/><form:hidden path="benificiary.isActive"/><form:input path="benificiary.benificiaryName" placeholder="Enter Benificiary Name"  id="benificiaryName5" class="myform-control input" required="true"></form:input></td></tr><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.accountType"/></b><span style="color:red">*</span></td><td><form:select path="benificiary.bankAccountType"  id="bankAccountType5"  class="myform-control" required="true"><form:option value="Savings"><spring:message code="label.Saving"/></form:option><form:option value="Current"><spring:message code="label.Current"/></form:option></form:select> </td></tr><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.accountNo"/></b><span style="color:red">*</span></td><td><form:input path="benificiary.benificiaryAccountNumber" placeholder="Enter account number" id="accountNo5"  class="myform-control input" type="number" required="true"></form:input></td></tr><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.ifsc"/></b><span style="color:red">*</span></td><td><form:input path="benificiary.ifscCode" placeholder="Enter IFSC code"  id="ifscCode5"  class="myform-control input" required="true"></form:input></td></tr><tr><td></td><td></td></tr> <tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.amount"/></b><span style="color:red">*</span></td><td><form:input path="benificiary.amountToTransfer" placeholder="Amount"    id="amountToTransfer5"  class="myform-control input" type="number" required="true"></form:input></td></tr> <tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.remarks"/></b><span style="color:red">*</span></td><td><form:input path="benificiary.remarks" placeholder="Remarks" id="remark3"   class="myform-control input" required="true"></form:input></td></tr></table><button id="b1" class="btn btn-warning remove-me"  type="button">Delete</button></div>';
				
		}
		else if(window.n==6){
			 div='<div id="addedBeneficiary6" class="col-sm-6"><table class="theader" width="400" style="position: relative;left: 2.6%;"><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.benificiaryName"/></b><span style="color:red">*</span></td><td><form:hidden path="benificiary.benificiaryId"/><form:hidden path="benificiary.isActive"/><form:input path="benificiary.benificiaryName" placeholder="Enter Benificiary Name"  id="benificiaryName6" class="myform-control input" required="true"></form:input></td></tr><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.accountType"/></b><span style="color:red">*</span></td><td><form:select path="benificiary.bankAccountType"  id="bankAccountType6"  class="myform-control" required="true"><form:option value="Savings"><spring:message code="label.Saving"/></form:option><form:option value="Current"><spring:message code="label.Current"/></form:option></form:select> </td></tr><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.accountNo"/></b><span style="color:red">*</span></td><td><form:input path="benificiary.benificiaryAccountNumber" placeholder="Enter account number" id="accountNo6"  class="myform-control input" type="number" required="true"></form:input></td></tr><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.ifsc"/></b><span style="color:red">*</span></td><td><form:input path="benificiary.ifscCode" placeholder="Enter IFSC code"  id="ifscCode6"  class="myform-control" required="true"></form:input></td></tr><tr><td></td><td></td></tr> <tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.amount"/></b><span style="color:red">*</span></td><td><form:input path="benificiary.amountToTransfer" placeholder="Amount"    id="amountToTransfer6"  class="myform-control" type="number" required="true"></form:input></td></tr> <tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.remarks"/></b><span style="color:red">*</span></td><td><form:input path="benificiary.remarks" placeholder="Remarks" id="remark3"   class="myform-control" required="true"></form:input></td></tr></table><button id="b1" class="btn btn-warning remove-me"  type="button">Delete</button></div>';
				
		}
		else if(window.n==7){
			 div='<div id="addedBeneficiary7" class="col-sm-6"><table class="theader" width="400" style="position: relative;left: 2.6%;"><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.benificiaryName"/></b><span style="color:red">*</span></td><td><form:hidden path="benificiary.benificiaryId"/><form:hidden path="benificiary.isActive"/><form:input path="benificiary.benificiaryName" placeholder="Enter Benificiary Name"  id="benificiaryName7" class="myform-control input" required="true"></form:input></td></tr><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.accountType"/></b><span style="color:red">*</span></td><td><form:select path="benificiary.bankAccountType"  id="bankAccountType7"  class="myform-control" required="true"><form:option value="Savings"><spring:message code="label.Saving"/></form:option><form:option value="Current"><spring:message code="label.Current"/></form:option></form:select> </td></tr><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.accountNo"/></b><span style="color:red">*</span></td><td><form:input path="benificiary.benificiaryAccountNumber" placeholder="Enter account number" id="accountNo7"  class="myform-control input" type="number" required="true"></form:input></td></tr><tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.ifsc"/></b><span style="color:red">*</span></td><td><form:input path="benificiary.ifscCode" placeholder="Enter IFSC code"  id="ifscCode7"  class="myform-control" required="true"></form:input></td></tr><tr><td></td><td></td></tr> <tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.amount"/></b><span style="color:red">*</span></td><td><form:input path="benificiary.amountToTransfer" placeholder="Amount"    id="amountToTransfer7"  class="myform-control" type="number" required="true"></form:input></td></tr> <tr><td align="right" style="padding-right: 15px;"><b style="font-size: 1em;"><spring:message code="label.remarks"/></b><span style="color:red">*</span></td><td><form:input path="benificiary.remarks" placeholder="Remarks" id="remark3"   class="myform-control" required="true"></form:input></td></tr></table><button id="b1" class="btn btn-warning remove-me"  type="button">Delete</button></div>';
				
		}
		
		
		$("#newBeneficiary").append(div);
		 window.n=parseInt(window.n)+1;
	}
	
		
		else {
			document.getElementById("addBeneficiaryError").innerHTML="Maximum 8 Beneficiaries are allowed!"
		
	}
	
}

</script>

					<c:forEach items="${depositHolderForm.benificiaryList}"
						var="beneficiaryTemp" varStatus="status">
						<div id="beneficiaryDiv${status.index}" class="col-sm-6">
							<form:hidden path="benificiary.isActive" value="${beneficiaryTemp.isActive}" id="benificiary.isActive${status.index}" />
								<form:hidden path="benificiary.benificiaryId" value="${beneficiaryTemp.id}" />
							<div class="form-1 col-md-6 col-xs-12">
								
								<table class="theader" width="400"
									>
									<tr>
										<td align="right" style="padding-right: 15px;"><b
											style="font-size: 1em;"><spring:message
													code="label.benificiaryName" /></b><span style="color: red">*</span></td>
										<td><form:input path="benificiary.benificiaryName"
												value="${beneficiaryTemp.benificiaryName}"
												placeholder="Enter Benificiary Name" id="benificiaryName${status.index}"
												class="myform-control  input"></form:input></td>
									</tr>
									<tr>
										<td align="right" style="padding-right: 15px;"><b
											style="font-size: 1em;"><spring:message
													code="label.accountType" /></b><span style="color: red">*</span></td>
										<td><form:select path="benificiary.bankAccountType"
												id="bankAccountType${status.index}" class="myform-control">
												<option value="Savings"
													<c:if test="${beneficiaryTemp.bankAccountType == 'Savings'}"> selected </c:if>>
													<spring:message code="label.Saving" />
												</option>
												<option value="Current"
													<c:if test="${beneficiaryTemp.bankAccountType == 'Current'}"> selected </c:if>>
													<spring:message code="label.Current" />
												</option>
											</form:select></td>
									</tr>
									<tr>
										<td align="right" style="padding-right: 15px;"><b
											style="font-size: 1em;"><spring:message
													code="label.accountNo" /></b><span style="color: red">*</span></td>
										<td><form:input
												path="benificiary.benificiaryAccountNumber"
												value="${beneficiaryTemp.benificiaryAccountNumber}"
												placeholder="Enter account number" id="accountNo${status.index}"
												class="myform-control input" type="number"></form:input></td>
									</tr>
									<tr>
										<td align="right" style="padding-right: 15px;"><b
											style="font-size: 1em;"><spring:message code="label.ifsc" /></b><span
											style="color: red">*</span></td>
										<td><form:input path="benificiary.ifscCode"
												placeholder="Enter IFSC code" id="ifscCode${status.index}"
												value="${beneficiaryTemp.ifscCode}" class="myform-control input"></form:input></td>
									</tr>
								
									<tr>
										<td align="right" style="padding-right: 15px;"><b
											style="font-size: 1em;"><spring:message
													code="label.amount" /></b><span style="color: red">*</span></td>
										<td><form:input path="benificiary.amountToTransfer"
												value="${beneficiaryTemp.amountToTransfer}" placeholder="Amount"
												id="amountToTransfer${status.index}" class="myform-control input" type="number"></form:input></td>
									</tr>
									<tr>
										<td align="right" style="padding-right: 15px;"><b
											style="font-size: 1em;"><spring:message
													code="label.remarks" /></b><span style="color: red">*</span></td>
										<td><form:input path="benificiary.remarks"
												placeholder="Remarks" value="${beneficiaryTemp.remarks}"
												id="remark${status.index}" class="myform-control input"></form:input></td>
									</tr>
								</table>
								<button id="b1" class="btn btn-warning"
									onclick="removeBeneficiary('beneficiaryDiv${status.index}',${status.index})"
									type="button">Delete</button>
							</div>

						</div>
					</c:forEach>
					

<div class="col-md-12">

<h4 class="header_me" id="newBeneficiaryHeading"></h4>
<div id="newBeneficiary" ></div>

</div>

					<div class="col-md-12">
						<div class="form-group">

							<table align="center" class="f_deposit_btn">
								<tr>
									<td><span id='validationError' style="color: red"></span></td>
								</tr>
								<tr>
								<c:if test="${baseURL[1] == 'bnkEmp'}">
							<c:set var="back" value="fdListBank" />
						</c:if>
						<c:if test="${baseURL[1] == 'users'}">
							<c:set var="back" value="fdList" />
						</c:if>
								
								
									<td><a href="${back}" class="btn btn-success"><spring:message
												code="label.back" /></a> <input type="button" onclick="val()"
										class="btn btn-info"
										value="<spring:message code="label.confirm"/>"></td>


								</tr>

							</table>

						</div>
					</div>
				</form:form>
			</div>

		</div>
	</div>
</div>




<div id="myModal" class="modal">

	<!-- Modal content -->
	<div class="modal-content">
		<h4>
			<u>Confirm change in maturity amount and maturity date</u>
		</h4>


		<table class="table table-bordered" align="center" id="my-table">
			<thead>
				<tr class="success">
					<th>Parameters</th>
					<th>Old value</th>
					<th>New value</th>

				</tr>
			</thead>
			<tbody>

				<tr>
					<td><b>Maturity Date:</b></td>
					<td id="oldMaturityDate"></td>
					<td id="newMaturityDate2"></td>
				</tr>
				<tr>
					<td><b>Rate:</b></td>
					<td id="oldRate"></td>
					<td id="newRate"></td>
				</tr>

			</tbody>
		</table>
		<div style="margin-left: 4px;; margin-top: -13px;">
			<b>Adjustment amount:</b> <span id="adjustAmt"></span>
		</div>
		<div class="col-md-8" style="margin-top: 19px;">
			<button class="btn btn-success" id="proceed">Proceed</button>
			<button class="btn btn-danger" id="cancel">Cancel</button>
		</div>
	</div>

</div>

<style>
body {
	font-family: Arial, Helvetica, sans-serif;
}

/* The Modal (background) */
.modal {
	display: none;
	position: fixed;
	z-index: 1;
	padding-top: 100px;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	overflow: auto;
	background-color: rgb(0, 0, 0);
	background-color: rgba(0, 0, 0, 0.4);
}

/* Modal Content */
.modal-content {
	padding: 20px;
	margin-top: 150px;
	margin-left: 283px;
	margin-top: :91px;
	border: 1px solid #888;
	width: 64%;
	height: 295px;
}

/* The Close Button */
.close {
	color: #aaaaaa;
	float: right;
	font-size: 28px;
	font-weight: bold;
}

.close:hover, .close:focus {
	color: #000;
	text-decoration: none;
	cursor: pointer;
}
</style>