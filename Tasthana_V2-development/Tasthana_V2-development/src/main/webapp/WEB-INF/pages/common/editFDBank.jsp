<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<!-- To fetch the request url -->
<c:set var="req"
	value="${requestScope['javax.servlet.forward.request_uri']}" />
<c:set var="baseURL" value="${fn:split(req,'/')}" />
<script>


function blockSpecialChar(e){
    var k;
   
    document.all ? k = e.keyCode : k = e.which;
    return ((k > 64 && k < 91) || (k > 96 && k < 123) || k == 8 || k == 32 || (k >= 48 && k <= 57));
    }



function validIFSC(event){
	var valiName = document.getElementById(event.target.id);

	
	var regex = new RegExp("^[A-Za-z]{4}[a-zA-Z0-9]{7}$");
    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
 
   var a =  blockSpecialChar(event);
   if(a==false){
	   event.preventDefault();
	   return false;
	   }
    if (valiName.value.length >10 && event.keyCode != 8){
    	 var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
    	    if (!regex.test(key)) {
    	    	alert("invalid ifsc code")
    	        event.preventDefault();
    	        return false;
    	 }
    }
   
    

	
	
}



function validName(event){
	var valiName = document.getElementById(event.target.id);
	var regex = new RegExp("^[a-zA-Z ]+$");
	 var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
	 if(valiName.value.length==0 && event.keyCode ==32)
		{
		  event.preventDefault();
		return false;
		}
	 if (!regex.test(key)) {
	    	//alert("false")
	        event.preventDefault();
	        return false;
	    }
	
	
	
}




function validationAccount(event){
	
	var minimumBalance1_ = document.getElementById(event.target.id);

		 var keycode = event.which;
	    if (!(event.shiftKey == false && (keycode == 8 || keycode == 37 ||  (keycode >= 48 && keycode <= 57)))) {
	        event.preventDefault();
	        return false;
	    } 
	   
	    
	    if (minimumBalance1_.value.length >14 && event.keyCode != 8){
	    	 event.preventDefault();
	    	return false;
	    }
	
	
	 
	}


function showTenureDetails(value){
	var tenure = value;
	
	
	if(tenure=="Day"){
		document.getElementById('dayId').style.display = 'block';
		document.getElementById('monthId').style.display = 'none';
		document.getElementById('yearId').style.display = 'none';
		document.getElementById('dayValue').style.display = 'none';
		document.getElementById('daysValue').value = "";
		document.getElementById('newFDTenure').value = "";
		document.getElementById('tenureDiv').style.display = 'block';
	}
	if(tenure=="Month"){
		document.getElementById('dayId').style.display = 'none';
		document.getElementById('monthId').style.display = 'block';
		document.getElementById('yearId').style.display = 'none';
		document.getElementById('dayValue').style.display = 'none';
		document.getElementById('daysValue').value = "";
		document.getElementById('newFDTenure').value = "";
		document.getElementById('tenureDiv').style.display = 'block';
	}
	if(tenure=="Year"){
		document.getElementById('dayId').style.display = 'none';
		document.getElementById('monthId').style.display = 'none';
		document.getElementById('yearId').style.display = 'block';
		document.getElementById('dayValue').style.display = 'block';
		document.getElementById('newFDTenure').value = "";
		document.getElementById('tenureDiv').style.display = 'block';
	}
	
}


function getAlertBeforeConversion(callback){
	
	var depositId = '${depositHolderForm.deposit.id}';
	var conversionType = '${depositConversion}';

	var result = false;

    var dataString = 'depositId='+ depositId;
	  var amount = 0;
	  $.ajax({  
		async: false,
	    type: "GET",  
	    url: "<%=request.getContextPath()%>/common/convertDeposit", 
	    contentType: "application/json",
	    dataType: "json",
	    data: dataString,
    
	    success: function(data){  
	    	if(data==""){
        		callback(false);
        	}
        	else{
        		
    			var loseAmount = 0.0;
    			var newDepositAmount = 0.0;
    			var currentDepositInterestRate = 0.0;
    			var amountTransferrredToSaving = 0.0;

        		$.each(data, function (key, value) {
        			if(key == 'Lose Amount')
        				loseAmount = value;
        			
        			if(key == 'New Deposit Amount')
        				newDepositAmount = value;
        			
        			if(key == 'Transfer Amount To Saving Account')
        				amountTransferrredToSaving = value;
        			
        			if(key == 'Interest Rate')
        				currentDepositInterestRate = value;
        			
//                     alert('key ' + key + '    value ' +  value);                    
                 })
                 
                 
                //Shows dialog
            	var modal = document.getElementById('convertDepositAlertModal');
            	 modal.style.display = "block";
            	 
            	
            	document.getElementById('newInterestRate').innerHTML=currentDepositInterestRate;
             	document.getElementById('interestNeedsToAdjust').innerHTML=loseAmount
             	document.getElementById('newDepositAmount').innerHTML=newDepositAmount;
             	document.getElementById('transferAmountToSavingAccount').innerHTML=amountTransferrredToSaving;
            	
            		var proceed = document.getElementById('proceedForConversion');
            		var cancel = document.getElementById('cancelConversion');
            		proceed.onclick = function() {
            			modal.style.display = "none";
            				  callback(true);
            		}
            		cancel.onclick = function() {
            		    modal.style.display = "none";
            			 callback(false);
            		}
        		
        	}

		    // result = true;	    
	    },  
	    error: function(e){  
	    	console.log("Error from conversion");
	    	// result = false;
	    }  
	  });  
		// return result;
	}  

function ajaxFun(callback){

	if(window.changed==0){
		onChangeTenure()
	}

	 var deposit=[];
	 var editDepositForm=[];
	 deposit.id = '${depositHolderForm.deposit.id}';
	 deposit.createdDate =$('#createdDate').val();
	 deposit.modifiedDate = $('#modifiedDate').val();
	 deposit.modifiedInterestRate = '${depositHolderForm.deposit.modifiedInterestRate}';
	 deposit.depositAmount = '${depositHolderForm.deposit.depositAmount}';
	 deposit.dueDate = $('#fdDueDate').val();
	 deposit.paymentType =  '${depositHolderForm.deposit.paymentType}';
	 deposit.depositCategory= '${depositHolderForm.deposit.depositCategory}';
	 var holderId= '${depositHolderForm.depositHolder.id}';

	 editDepositForm.maturityDateNew = $('#newMaturityDate').val();
	 var category= '${depositHolderForm.depositHolder.depositHolderCategory}';
	 

    $.ajax({
		
		
        url : '<%=request.getContextPath()%>/common/compareMaturity',
        type : "POST", 
        cache: false,
        async: false,
        data: "deposit.id="+deposit.id+"&deposit.createdDate="+deposit.createdDate+"&deposit.modifiedDate="+deposit.modifiedDate+
        "&deposit.interestRate="+deposit.modifiedInterestRate+"&deposit.depositAmount="+deposit.depositAmount+
        "&deposit.dueDate="+deposit.dueDate+"&deposit.paymentType="+deposit.paymentType+"&deposit.depositCategory="+deposit.depositCategory+
        "&editDepositForm.maturityDateNew="+editDepositForm.maturityDateNew+"&depositHolder.depositHolderCategory="+category+"&depositHolderList[0].id="+holderId,
        success : function(data) {
       
        	if(data==""){
        		callback(false);
        	}
        	else{
    debugger;
            	var oldInterestAmount= '${depositHolderForm.deposit.newMaturityAmount}';
            	
            	var adjustAmt= data.split(",")[0];
            	var newInterestRate= data.split(",")[1];
            	var newInterestAmount= data.split(",")[2];
            	
            	   //Shows dialog
            	var modal = document.getElementById('myModal_edit');
            	 modal.style.display = "block";
//             	 document.getElementById('oldMaturityAmount').innerHTML=oldInterestAmount;
//              	document.getElementById('newMaturityAmount').innerHTML=newInterestAmount
             	document.getElementById('oldMaturityDate').innerHTML=document.getElementById('maturityDate').value;
             	document.getElementById('newMaturityDate2').innerHTML=document.getElementById('newMaturityDate').value;
            	document.getElementById('newRate').innerHTML=newInterestRate;
             	document.getElementById('oldRate').innerHTML=deposit.modifiedInterestRate;
             	document.getElementById('adjustAmt').innerHTML=adjustAmt * -1;
             	if('${depositHolderForm.deposit.depositCategory}'=='REVERSE-EMI'){
             		document.getElementById('maturityAmount').style.display = 'none';
            	}
             	
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
        error: function(e){  
        	console.log("error");
	    	} 
    }); 

}

function tenChange(){
	var depositClassification = '${depositClassification}';
	
	var addTenureRadio_ = document.getElementById('addTenureRadio').checked = false;
	 var reduceTenureRadio_ = document.getElementById('reduceTenureRadio').checked = false;
	 var addRadio_ = document.getElementById('addRadio').checked = false;
	 var reduceRadio_ = document.getElementById('reduceRadio').checked = false;
	if(depositClassification=='Recurring Deposit'){
		
          document.getElementById("regularDiv").style.display = 'block';

          document.getElementById("oneTime").disabled=true;
		  document.getElementById("monthly").disabled=false;
       	  document.getElementById("quarterly").disabled=false;
          document.getElementById("halfYearly").disabled=false;
          document.getElementById("annually").disabled=false;
          document.getElementById("regular").checked = true;
          
        document.getElementById("oneTimeNew").disabled=false;
		document.getElementById("monthlyNew").disabled=true;
      	document.getElementById("quarterlyNew").disabled=true;
      	document.getElementById("halfYearlyNew").disabled=true;
      	document.getElementById("annuallyNew").disabled=true;
      	 document.getElementById("deductionDay").style.display = 'none';
      	 document.getElementById("typeOfTenureDiv").style.display = 'block';
      	
	}
	 if(depositClassification=='Regular Deposit'){
		 document.getElementById("recurringDiv").style.display = 'block';
		 document.getElementById("oneTimeNew").disabled=true;
		 document.getElementById("recurring").checked = true;
		 
		document.getElementById("oneTime").disabled=false;
		document.getElementById("monthly").disabled=true;
      	document.getElementById("quarterly").disabled=true;
      	document.getElementById("halfYearly").disabled=true;
      	document.getElementById("annually").disabled=true;
      	
      	document.getElementById("oneTimeNew").disabled=true;
		document.getElementById("monthlyNew").disabled=false;
    	document.getElementById("quarterlyNew").disabled=false;
    	document.getElementById("halfYearlyNew").disabled=false;
    	document.getElementById("annuallyNew").disabled=false;
    	 document.getElementById("deductionDay").style.display = 'block';
    	 document.getElementById("typeOfTenureDiv").style.display = 'block';
	}
	 if(depositClassification=='Tax Saving Deposit'){
		
		 document.getElementById('convertDepositDIV').style.display = 'none';
		document.getElementById('recurring').style.display = 'none';
		document.getElementById('regular').style.display = 'none';
		
	}
	 var tenureCheck = document.getElementById('tenureChange').checked;
	 var amountChange = document.getElementById('amountChange').checked;
	 var convertDeposit = document.getElementById('convertDeposit').checked;
	 
	 if(tenureCheck == true){
		 document.getElementById("tenureDiv").style.display = 'none';
		 document.getElementById("addReduceAmountDIV").style.display = 'none';
		 document.getElementById("convertDepositDIV").style.display = 'none';
		 document.getElementById("addReduceTenureDIV").style.display = 'block';
		 document.getElementById("addReduceDiv").style.display = 'none';
		 document.getElementById("deductionDay").style.display='none';
		 document.getElementById("typeOfTenureDiv").style.display='none';
		 document.getElementById("newFDTenure").value = '';
		 document.getElementById("daysValue").value = '';
		 document.getElementById("newPaymentTypeDiv").style.display = 'none';
		 document.getElementById("recurring").checked = false;
		 document.getElementById("regular").checked = false;
		 document.getElementById("changePaymentType").disabled=false;
		// document.getElementById("changePaymentType").value = '';

	 }
	 
	 else if(amountChange == true){
		 document.getElementById("tenureDiv").style.display = 'none';
		 document.getElementById("typeOfTenureDiv").style.display='none';
		 document.getElementById("addReduceTenureDIV").style.display = 'none';
		 document.getElementById("convertDepositDIV").style.display = 'none';
		 document.getElementById("addReduceAmountDIV").style.display = 'block';
		 document.getElementById("addAmount").value = '';
		 document.getElementById("newFDTenureType").value = '';
		 document.getElementById("newFDTenure").value = '';
		 document.getElementById("addReduceTenureDiv").style.display = 'none';
		 document.getElementById("newMaturityDateDiv").style.display = 'none';
		 document.getElementById("tenureError").style.display = 'none'; 
		 document.getElementById("deductionDay").style.display='none';
		 document.getElementById("newFDTenure").value = '';
		 document.getElementById("daysValue").value = '';
		 document.getElementById("newPaymentTypeDiv").style.display = 'none';
		 document.getElementById("recurring").checked = false;
		 document.getElementById("regular").checked = false;
		 document.getElementById("changePaymentType").disabled=false;
		// document.getElementById("changePaymentType").value = '';
	 }
	 
	 else{
		 
		 document.getElementById("addReduceTenureDIV").style.display = 'none';
		 document.getElementById("addReduceAmountDIV").style.display = 'none';
		 document.getElementById("convertDepositDIV").style.display = 'block';
		 document.getElementById("addAmount").value = '';
		 document.getElementById("addReduceDiv").style.display = 'none';
		 document.getElementById("newFDTenureType").value = '';
		 document.getElementById("newFDTenure").value = '';
		 document.getElementById("addReduceTenureDiv").style.display = 'none';
		 document.getElementById("newMaturityDateDiv").style.display = 'none';
		 document.getElementById("tenureError").style.display = 'none'; 
		 document.getElementById("newPaymentTypeDiv").style.display = 'block';
		 document.getElementById("changePaymentType").disabled=true;
		 //document.getElementById("changePaymentType").value = 'Monthly';
		 
		 
	 }
	 
		/*$("#editForm").attr("action", "editFD?depositChange="+depositChange);
		$("#editForm").submit(); */
	 

	 
	 
	 
}

function scientificToDecimal(num) {
    //if the number is in scientific notation remove it
    if(/\d+\.?\d*e[\+\-]*\d+/i.test(num)) {
        var zero = '0',
            parts = String(num).toLowerCase().split('e'), //split into coeff and exponent
            e = parts.pop(),//store the exponential part
            l = Math.abs(e), //get the number of zeros
            sign = e/l,
            coeff_array = parts[0].split('.');
        if(sign === -1) {
            coeff_array[0] = Math.abs(coeff_array[0]);
            num = '-'+zero + '.' + new Array(l).join(zero) + coeff_array.join('');
        }
        else {
            var dec = coeff_array[1];
            if(dec) l = l - dec.length;
            num = coeff_array.join('') + new Array(l+1).join(zero);
        }
    }
    
    return num;
};





$( document ).ready(function() {
	
	
	
   /* 
	
	var newIntRate = '${newIntRate}';
	var depositAmt = '${depositAmt}';
	var loseAmt = '${loseAmt}';
	var transferAmt = '${transferAmt}';
   document.getElementById('newInterestRate').innerHTML=newIntRate;
   document.getElementById('newDepositAmount').innerHTML=depositAmt;
   document.getElementById('loseAmount').innerHTML=loseAmt;
   document.getElementById('transferAmountToSavingAccount').innerHTML=transferAmt; */
	
	onchangePayOffType();
	
	var paymentMode=document.getElementById('fdPay').value; 
	onChangePaymentMode(paymentMode);
   
    /********** for add/reduce amount *********/
    var reduceRadio = document.getElementById('reduceRadio').checked;
	var addRadio = document.getElementById('addRadio').checked;
			
		
			if(addRadio==true || reduceRadio==true){
				document.getElementById("addReduceDiv").style.display = 'block';
			}
			else{
				document.getElementById("addReduceDiv").style.display = 'none';
			}
			
			
			
		    /********** for add/reduce tenure *********/
			var reduceTenureRadio = document.getElementById('reduceTenureRadio').checked;
			var addTenureRadio = document.getElementById('addTenureRadio').checked;
			
			if(addTenureRadio==true || reduceTenureRadio==true){
				document.getElementById("addReduceTenureDiv").style.display = 'block';
				document.getElementById("newMaturityDateDiv").style.display = 'block';
			}
			else{
				document.getElementById("addReduceTenureDiv").style.display = 'none';
				document.getElementById("newMaturityDateDiv").style.display = 'none';
			}
			
			
			
		if ("${depositHolderForm.deposit.stopPayment}"==1){
				document.getElementById('stopPayment').checked=true;
				
		}
		else{
			document.getElementById('stopPayment').checked=false;
		}			
    
		
		var size= <c:out value="${depositHolderList.size()}"/>
			for(var i=0;i<size;i++){
				var fdPayId='fdPay['+i+']';
				var fdPay = document.getElementById(fdPayId).value;
				displayDiv(fdPay,i);
				
				var payOffAccountType= document.getElementById('depositHolderList'+i+'.payOffAccountType').value; 
				onChangeAccountType(payOffAccountType, i,false);
				
			}
		
});

				
				
				
function addedAmt() {
    var depositAmt = document.getElementById('fdFixed').value;
    var addAmt = document.getElementById('addAmount').value;
    var reduceAmt = document.getElementById('reduceRadio').value;
	var addRadio= document.getElementById("addRadio").checked;
	var reduceRadio= document.getElementById("reduceRadio").checked;
	
	
    if(addRadio == true)
    {
    	var sumofDepositAmtAndaddedAmt = parseFloat(depositAmt) + parseFloat(addAmt);
    	document.getElementById('totalAmount').value=sumofDepositAmtAndaddedAmt;
     
    }

    if(reduceRadio == true)
    {
    		var deductAmtFromdepositAmt = parseFloat(depositAmt) - parseFloat(addAmt);
    		document.getElementById('totalAmount').value=deductAmtFromdepositAmt;
    	
    }
    
   
}
	
	function onchangePayOffType() {

		var payOffType = document.getElementById('payOffType').value;

		if (payOffType == '') {
			document.getElementById('payOffDetails').style.display = 'none';
		} else {
			document.getElementById('payOffDetails').style.display = 'block';
		}

	}
	
	function displayDiv(value, index) {
	
		if(value=='PART'){
			document.getElementById('firstPartInterestAmtTr['+index+']').style.display = 'block';
			document.getElementById('interestPercentTr['+index+']').style.display = 'none';
			document.getElementById('interestPercent['+index+']').value="";
		}
		
		else if(value=='PERCENT'){
			document.getElementById('firstPartInterestAmtTr['+index+']').style.display = 'none';
			document.getElementById('interestPercentTr['+index+']').style.display = 'block';
			document.getElementById('interestPayAmount['+index+']').value="";
			}
		
		else{
			
			/* setting all value to empty */
			document.getElementById('firstPartInterestAmtTr['+index+']').style.display = 'none';
			document.getElementById('interestPercentTr['+index+']').style.display = 'none';
			document.getElementById('interestPayAmount['+index+']').value="";
			document.getElementById('interestPercent['+index+']').value="";
		
		}
		
	}

	function onChangeAccountType(value, index,check){
	
		var otherBankDetails = "otherBankDetails["+index+"]";
		var savingBankDetails = "beneficiaryDetails["+index+"]";
		var bankNameId = 'depositHolderList'+index+'.bankName';
		var ifscCodeId = 'depositHolderList'+index+'.ifscCode';
		var nameOnBankAccountId = 'depositHolderList'+index+'.nameOnBankAccount';
		var accountNumberId = 'depositHolderList'+index+'.accountNumber';
	
		  if(check==true){
			  document.getElementById(bankNameId).value="";
				document.getElementById(ifscCodeId).value="";
				document.getElementById(nameOnBankAccountId).value="";
				document.getElementById(accountNumberId).value="";
	        }
		  
		if(value=='Saving Account'){
			document.getElementById(otherBankDetails).style.display = 'none';
			document.getElementById(savingBankDetails).style.display = 'block';
			
		
			
			
			}
		
		else if(value=='Other'){
			document.getElementById(otherBankDetails).style.display = 'block';
			document.getElementById(savingBankDetails).style.display = 'block';
		}
		
		else{
			document.getElementById(savingBankDetails).style.display = 'none';
			document.getElementById(otherBankDetails).style.display = 'none';
			document.getElementById(bankNameId).value="";
			document.getElementById(ifscCodeId).value="";
			document.getElementById(nameOnBankAccountId).value="";
			document.getElementById(accountNumberId).value="";
		}
	
		
	}


			
	var form_clean;
	var form_dirty;
	var changed=0;

	// serialize clean form
	$(function() {
		window.form_clean = $("form").serialize();
	});

	function recurringDeposit(){
		
		var recurring= document.getElementById("recurring").checked;
		
		
		if(recurring==true){
			document.getElementById("typeOfTenureDiv").style.display='block';
			document.getElementById("changePaymentType").value="";
			document.getElementById("changePaymentType").disabled=false;
			document.getElementById("deductionDay").style.display='block';
			document.getElementById("oneTime").disabled=true;
			document.getElementById("monthly").disabled=false;
        	document.getElementById("quarterly").disabled=false;
        	document.getElementById("halfYearly").disabled=false;
        	document.getElementById("annually").disabled=false;
		}
		
	}

	function regularDeposit(){
		var regular= document.getElementById("regular").checked;
        if(regular==true){
        	document.getElementById("typeOfTenureDiv").style.display='block';
        	document.getElementById("monthly").disabled=true;
        	document.getElementById("quarterly").disabled=true;
        	document.getElementById("halfYearly").disabled=true;
        	document.getElementById("annually").disabled=true;
        	document.getElementById("oneTime").disabled=false;
    		document.getElementById("deductionDay").style.display='none';
    		document.getElementById("deductionDay").value="";
    		document.getElementById("changePaymentType").value="";
		}
		

		
	}

	function isNumberKey(evt)
    {
		
       var charCode = (evt.which) ? evt.which : evt.keyCode;
       if (charCode != 46 && charCode > 31 
         && (charCode < 48 || charCode > 57)){
          return false;
       }else{
     	  var splitValue = $("#"+evt.target.id).val();
	          var res = splitValue.split(".");
	          if(splitValue.includes(".")){
	          var spVal = res[1];
	          if(spVal.length > 1){
	        	  evt.preventDefault();
	        	  return false;
	          }
	          return true;
	          }else{
	      
	          if(splitValue.length > 3){
	        	  evt.preventDefault();
	        	  return false;
	          }
     	  return true;
	          }
       }
      
       

       
    }
	
	function checkPercentage(evt,obj){
		
		if(obj.val()>100){
			var value=obj.val().substring(0, 2);
			obj.val(value);
			 evt.preventDefault();
			return false;
		}
	}
	
	function checkAmount(evt,obj){
		
		if(parseInt(obj.val())>parseInt($('#depositAmounttxt').attr('value'))){
			var length=obj.val().length;
			var value=obj.val().substring(0, length-1);
			obj.val(value);
			 evt.preventDefault();
			alert('Part Payment can not be greater than Deposit Amount');
			
       	  return false;
		}
		
		
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
	      
	          if(splitValue.length > 3){
	        	  evt.preventDefault();
	        	  return false;
	          }
     	  return true;
	          }
       }
      
       

       
    }
	function isNumberKey2(evt,obj)
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
	function showAddReduceBox(id){
		 var depositClasification = '${depositHolderForm.deposit.depositClassification}'; 
		 var taxSavingDeposit = '${depositHolderForm.deposit.taxSavingDeposit}'; 
         if(depositClasification == 'Tax Saving Deposit' && taxSavingDeposit == '1'){
        	document.getElementById('taxSavingDepositErrorForAmount').style.display = 'block';
        	document.getElementById('taxSavingDepositError').style.display = 'none';
			return;
	
        }
		    var depositAmt = document.getElementById('fdFixed').value;
		    var addAmt = document.getElementById('addAmount').value;
		    var reduceAmt = document.getElementById('reduceRadio').value;
			var addRadio= document.getElementById("addRadio").checked;
			var reduceRadio= document.getElementById("reduceRadio").checked;
		    if(addRadio == true)
		    {
		    	var sumofDepositAmtAndaddedAmt = parseFloat(depositAmt) + parseFloat(addAmt);
		    	document.getElementById('totalAmount').value=sumofDepositAmtAndaddedAmt;
		     
		    }

		    if(reduceRadio == true)
		    {
		    		var deductAmtFromdepositAmt = parseFloat(depositAmt) - parseFloat(addAmt);
		    		document.getElementById('totalAmount').value=deductAmtFromdepositAmt;
		    	
		    }
		document.getElementById(id).style.display = 'block';
	}
	
	function showAddReduceTenure(id){
		 var depositClasification = '${depositHolderForm.deposit.depositClassification}'; 
		 var taxSavingDeposit = '${depositHolderForm.deposit.taxSavingDeposit}'; 
         if(depositClasification == 'Tax Saving Deposit' && taxSavingDeposit == '1'){
        	document.getElementById('taxSavingDepositError').style.display = 'block';
        	document.getElementById('taxSavingDepositErrorForAmount').style.display = 'none';
			return;
         }
		document.getElementById(id).style.display = 'block';
		//onChangeTenure();
	}
	
	function hideDiv(value){

		if(value=='tenureType'){
			document.getElementById('reduceTenureRadio').checked=false;
			document.getElementById('addTenureRadio').checked=false;
			document.getElementById("addReduceTenureDiv").style.display = 'none';
			document.getElementById("newMaturityDateDiv").style.display = 'none';
			window.changed=0;
			
		}
		else{
			document.getElementById('reduceRadio').checked=false;
			document.getElementById('addRadio').checked=false;
			document.getElementById("addReduceDiv").style.display = 'none';
	
					
		}
	}   
	var today = null;
	function getTodaysDate()
	{
	
		
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
	function onChangeTenure(){
	
debugger;
		var newFDTenure=parseFloat(document.getElementById('newFDTenure').value);
		var tenureConcat = (document.getElementById('tenureConcat').value);
		var newTenure=$('#fdTenure1').val();
		var newTenureType=$('#fdTenureType1').val();

		
		if(newTenure=="" || newTenureType=="" ){
			document.getElementById('newMaturityDateDiv').style.display='none';
			return;
		}
		
		newTenure=parseInt(newTenure);
		//var newTenureType=$('#fdTenureType').val();
		
		var maturityDateArray=$('#maturityDate').val().split("/");
		var maturityDate = new Date(maturityDateArray[2], maturityDateArray[1]-1, maturityDateArray[0]);
		
		var maturityDateCust =document.getElementById('maturityDate').value;
		
		var reduceTenureRadio =document.getElementById('reduceTenureRadio').checked;
		var addTenureRadio =document.getElementById('addTenureRadio').checked;
		
		var today = getTodaysDate();
		
		
		/* if(reduceTenureRadio){
			
			if(newTenureType=='Day'){
				maturityDate.setDate(maturityDate.getDate() - newTenure);
			
			   
			}
			if(newTenureType=='Month'){
				
				maturityDate.setMonth(maturityDate.getMonth() - newTenure);
				
			}
			if(newTenureType=='Year'){
				maturityDate.setFullYear(maturityDate.getFullYear() - newTenure);
			}
			
			
		}
		
        if(addTenureRadio){
        	
        	if(newTenureType=='Day'){
    			maturityDate.setDate(maturityDate.getDate() + newTenure);
    		}
    		if(newTenureType=='Month'){
    			maturityDate.setMonth(maturityDate.getMonth() + newTenure);
    		}
    		if(newTenureType=='Year'){
    			
    			maturityDate.setFullYear(maturityDate.getFullYear() + newTenure);
    		}
    			
		} */
		/* if(maturityDate<today){
			document.getElementById('fdTenure').value='';
			document.getElementById('fdTenureType').value='';
			hideDiv('tenureType');
			document.getElementById('tenureError').innerHTML='Please enter valid tenure';
			return;
		} */
		
		var addTenureRadioButton = document.getElementById('addTenureRadio').checked;
		var reduceTenureRadioButton = document.getElementById('reduceTenureRadio').checked;
		var fdTenureVal = $("#fdTenureType1").val();
		var fdTenure1_Val = $("#fdTenure1").val();
		if(addTenureRadioButton == true){
			
			if(fdTenureVal == 'Day'){
				maturityDate.setDate(maturityDate.getDate() + parseInt(fdTenure1_Val));
			}else if(fdTenureVal == 'Month'){
				maturityDate.setMonth(maturityDate.getMonth() + parseInt(fdTenure1_Val));
			}else{
				maturityDate.setFullYear(maturityDate.getFullYear() + parseInt(fdTenure1_Val));
			}
			
			
			
		}
		
		
		
			if(reduceTenureRadioButton == true){
			
			if(fdTenureVal == 'Day'){
				maturityDate.setDate(maturityDate.getDate() - parseInt(fdTenure1_Val));
			}else if(fdTenureVal == 'Month'){
				maturityDate.setMonth(maturityDate.getMonth() - parseInt(fdTenure1_Val));
			}else{
				maturityDate.setFullYear(maturityDate.getFullYear() - parseInt(fdTenure1_Val));
			}
			
			
			
		}
	
			var maturityDat_ = maturityDate;
			debugger;
			var day = maturityDate.getDate();
			var month = parseInt(maturityDate.getMonth()+1);
			if(month<10){ month="0"+month;}
			var year = maturityDate.getFullYear();
			document.getElementById('newMaturityDateDiv').style.display='none';
			if(maturityDate < today){
				document.getElementById('newMaturityDateDiv').style.display='block';
				var diffDays  = parseInt((maturityDate - today) / (1000 * 60 * 60 * 24)); 
				//alert("Maturity Date date is less Login Date You are not eligible : ");
				document.getElementById('newMaturityDate').value = day+"/"+month+"/"+year;
				document.getElementById('validationError').innerHTML=' New maturity date is less than the current date, make sure that maturity date should be greater than current date.<br>';
					//val();
				
				
			}else{
				document.getElementById('validationError').innerHTML="";
			maturityDate = day+"/"+month+"/"+year;
			document.getElementById('newMaturityDateDiv').style.display='block';
			document.getElementById('newMaturityDate').value = maturityDate;
			}
	
		window.changed=1;
	
	}
	
	
	
	function onChangePaymentMode(value){
		
	
		if(value=="Fund Transfer"){
		var size= <c:out value="${accountList.size()}"/>
			if(size==0){
				document.getElementById('validationError').innerHTML='No current/saving account available<br>'
					
			}
			document.getElementById("linkedAccountDetails").style.display='block';
			}
		else{
		document.getElementById("linkedAccountDetails").style.display='none';
		}
		}
	
		var date_diff_indays = function(date1, date2) {
		dt1 = new Date(date1);
		dt2 = new Date(date2);
		return Math.floor((Date.UTC(dt2.getFullYear(), dt2.getMonth(), dt2.getDate()) - Date.UTC(dt1.getFullYear(), dt1.getMonth(), dt1.getDate()) ) /(1000 * 60 * 60 * 24));
		}
	function val(){
		
		 var tenureCheck = document.getElementById('tenureChange').checked;
		 var amountChange = document.getElementById('amountChange').checked;
		 var convertDeposit = document.getElementById('convertDeposit').checked;
		 var fdTenureType1_ = document.getElementById('fdTenureType1').value;
		 var fdTenure11_ = document.getElementById('fdTenure1').value;
		 if(tenureCheck==true){if(fdTenureType1_=="" ){
				$("#validationError").html("Please select tenure type");
				return;
			}
		 if( fdTenure11_=="" ){
				$("#validationError").html("Please select tenure");
				return;
			}
		 }
		 
		 var maturityDate_ = document.getElementById('maturityDate').value;
		 
		// var maturityDate_val  = new Date(maturityDate_);
		 //alert(maturityDate_val);
		 
		
		document.getElementById('validationError').innerHTML='';
		
		var inp = document.querySelectorAll('.input:not([type="hidden"])');
		
		for(i=0;i<inp.length;i++){
			inp[i].style.borderColor = "#cccccc";
		}
		
	
		/****** no data changed validation start********/
		
		window.form_dirty = $("form").serialize();

		/* if (window.form_clean == window.form_dirty ) {
			
				document.getElementById('validationError').innerHTML='No changes made';
				return false
				
		} */
		/******no data changed validation  end ********/
		
        /****** add/reduce tenure validation start********/
        
        debugger
		var submit = true;
		var addTenureRadio = document.getElementById('addTenureRadio').checked;
		var reduceTenureRadio = document.getElementById('reduceTenureRadio').checked;
		var newFDTenureType=document.getElementById('newFDTenureType').value;
		var newFDTenure=parseFloat(document.getElementById('newFDTenure').value); 
		var tenureConcat_ = (document.getElementById('tenureConcat').value);
		
		var tenureDayValue = parseInt(tenureConcat_.trim());
		
		var maturityDateArray=$('#maturityDate').val().split("/");
		var maturityDate = new Date(maturityDateArray[2], maturityDateArray[1]-1, maturityDateArray[0]);
		var day = maturityDate.getDate();
		var month=parseInt(maturityDate.getMonth()+1);
		if(month<10){ month="0"+month;}
		var year = maturityDate.getFullYear();	
		
		var createdDateArray = this.getTodaysDate();
		var remainingDays = date_diff_indays(new Date(createdDateArray),maturityDate);
		
		var year  = today.getFullYear();
		var month = today.getMonth();
		var day = today.getDate();
		var fdTenureType1_  = $("#fdTenureType1").val();
		var fdTenu = $("#fdTenure1").val();
		
		var fdTenure = 0;
		if(fdTenureType1_ == "Year"){
			var newDate = new Date(year+parseInt(fdTenu),month,day);
			var _days = (newDate - today)/(1000*60*60*24);

			fdTenure+= parseInt(_days);
		}
		if(fdTenureType1_ == "Month"){
			var newMonthDate = new Date(year,month+parseInt(fdTenu),day);
			fdTenure+= parseInt((newMonthDate - today)/(1000*60*60*24));
		}
		if(fdTenureType1_ == "Day"){
			fdTenure+= parseInt(fdTenu);
		}
		
		var minimumDays;
		var miniumMonths;
		var minimumYears;
		var totalMinimumTenure = 0;
		var productConfMiniumTenure = "${productConfiguration.minimumTenure}";
		var splitMinTenure = productConfMiniumTenure.split(",");
		for(var i = 0; i < splitMinTenure.length; i++) {
			var daysOrMonthOrYearVal = splitMinTenure[i];
			if(daysOrMonthOrYearVal.includes("Day")){
				var  splitDaysVal = daysOrMonthOrYearVal.split(" ");
					minimumDays = splitDaysVal[0];
			}else if(daysOrMonthOrYearVal.includes("Month")){
				var  splitMonthVal = daysOrMonthOrYearVal.split(" ");
				miniumMonths = splitMonthVal[0];
			}else if(daysOrMonthOrYearVal.includes("Year")){
				var  splitYearVal = daysOrMonthOrYearVal.split(" ");
				minimumYears = splitYearVal[0];
			}
			
		}
		 if(minimumDays != undefined){
			 totalMinimumTenure += parseInt(minimumDays);
			 }
		 if(miniumMonths != undefined){ 
			 var newMonthDateMinTenure = new Date(year,month+parseInt(miniumMonths),day);
			 totalMinimumTenure += parseInt((newMonthDateMinTenure - today)/(1000*60*60*24));
		 
		 }
		 if(minimumYears != undefined){ 
			 var newDateForMinTenure = new Date(year+parseInt(minimumYears),month,day);
				var _daysMinTenure = (newDateForMinTenure - today)/(1000*60*60*24);
			 totalMinimumTenure += parseInt(_daysMinTenure);
			 }
		
		 productConfMiniumTenure.replace(/,/g, ',');
		
		
		 if (parseInt(fdTenure) < parseInt(totalMinimumTenure && reduceTenureRadio)) {
			document.getElementById('fdTenure1').style.borderColor = "red";
			document.getElementById('validationError').innerHTML+= "<br>Minimum tenure for this deposit is "+ (productConfMiniumTenure.replace(/,/g, ' '))+".";
			validationError.innerHTML += errorMsg;
			submit = false;
		} 
		
		 var maximumDays;
			var maximumMonths;
			var maximumYears;
			var totalMaximumTenure = 0;
			var productConfMaximumTenure = "${productConfiguration.maximumTenure}";
			var splitMaxTenure = productConfMaximumTenure.split(",");
			for(var i = 0; i < splitMaxTenure.length; i++) {
				var daysOrMonthOrYearValMax = splitMaxTenure[i];
				if(daysOrMonthOrYearValMax.includes("Day")){
					var  splitDaysValMax = daysOrMonthOrYearValMax.split(" ");
					maximumDays = splitDaysValMax[0];
				}else if(daysOrMonthOrYearValMax.includes("Month")){
					var  splitMonthValMax = daysOrMonthOrYearValMax.split(" ");
					maximumMonths = splitMonthValMax[0];
				}else if(daysOrMonthOrYearValMax.includes("Year")){
					var  splitYearValMax = daysOrMonthOrYearValMax.split(" ");
					maximumYears = splitYearValMax[0];
				}
				
			}
			 if(maximumDays != undefined){
				 totalMaximumTenure += parseInt(maximumDays);
				 }
			 
			 if(maximumMonths != undefined){
				 var newMonthDateMaxTenure = new Date(year,month+parseInt(maximumMonths),day);
				 totalMaximumTenure += parseInt((newMonthDateMaxTenure - today)/(1000*60*60*24));
			 }
			 if(maximumYears != undefined) {
				 var newDateForMaxTenure = new Date(year+parseInt(maximumYears),month,day);
					var _daysMaxTenure = (newDateForMaxTenure - today)/(1000*60*60*24);
				 totalMaximumTenure += parseInt(_daysMaxTenure);
			 }
			
			 productConfMaximumTenure.replace(/,/g, ',');
			if (parseInt(fdTenure) > parseInt(totalMaximumTenure) && addTenureRadio) {
				document.getElementById('fdTenure1').style.borderColor = "red";
				document.getElementById('validationError').innerHTML+= "<br>Maximum tenure for this deposit is "+ (productConfMaximumTenure.replace(/,/g, ' '))+".";
				validationError.innerHTML += errorMsg;
				submit = false;
			}
			
			
			
		 
		
		debugger;
		var fdTenure1_ = document.getElementById('fdTenure1').value;
		if(fdTenureType1_ == "Day"){
			
			var reduceTenureRadio_ = document.getElementById('reduceTenureRadio').checked;
			if(reduceTenureRadio_ == true){
				if(fdTenure1_>=tenureDayValue){
					document.getElementById('fdTenure1').style.borderColor = "red";
					document.getElementById('validationError').innerHTML+='You can not reduce tenure more than total tenure';
					submit = false;
					return;
				}
			if(parseInt(fdTenure1_) > (parseInt(((remainingDays-1))))){	
			
			document.getElementById('fdTenure1').style.borderColor = "red";
			document.getElementById('validationError').innerHTML+='Your total tenure is '+tenureDayValue +' Days.'+ ((tenureDayValue - remainingDays))  +' Days has already crossed. So you can reduce the tenure maximum by '+ (remainingDays) + ' Days<br>'
			submit = false;
			
			
			}
			}
		
		
		}
		
		if(fdTenureType1_=="Year"){
			debugger;
			 	var totalDays = fdTenure1_ * 365;
			 	var reduceTenureRadio_ = document.getElementById('reduceTenureRadio').checked;
				if(reduceTenureRadio_ == true){
			 	if(totalDays>=tenureDayValue){
					document.getElementById('fdTenure1').style.borderColor = "red";
					document.getElementById('validationError').innerHTML+='You can not reduce tenure more than total tenure';
					submit = false;
					return;
				}
				}
			 	var trYear = (tenureDayValue/365);
			 	var trMonths = (tenureDayValue/30.4375);
			 	var trDay = tenureDayValue
			 	var  trRemaningMonth = (remainingDays/30.4375);
			 	
			 	var  trRemaningYear = (remainingDays/365);
			 
			 	
			 	(((tenureDayValue - remainingDays)/365).toFixed(2))
			 	
			 	var  trMaxMinYear = ((tenureDayValue - remainingDays)/365);
			 	
			 	var  trMaxMinMonth = ((tenureDayValue - remainingDays)/30.4375);
			 	var trRemMaxVal = "";
			 	var trMaxVal= "";
			 	
			 	
			 	var trRemVal;
			 
			 	var trval ;
			 	var tenureVal ;
			 	var tenureRemaningVal ;
			 	
			 	if(Math.trunc(trYear) > 0){
			 		tenureVal = Math.round((tenureDayValue/365));
			 		trval = 'Year';
			 	}else if(Math.trunc(trMonths)  > 0){
			 		tenureVal = Math.round((tenureDayValue/30.4375));
			 		 trval = 'Month';
			 	}else{
			 		tenureVal = tenureDayValue;
			 		 trval = 'Days';
			 	}
			 	
			 	if(Math.trunc(trRemaningYear) > 0){
			 		tenureRemaningVal = Math.round(trRemaningYear);
			 		trRemVal = 'Year';
			 	}else if(Math.trunc(trRemaningMonth)  > 0){
			 		tenureRemaningVal = Math.round(trRemaningMonth);
			 		trRemVal = 'Month';
			 	}else{
			 		tenureRemaningVal = remainingDays;
			 		trRemVal = 'Days';
			 	}
			 	
			 	
			 	if(Math.trunc(trMaxMinYear) > 0){
			 		trMaxVal = Math.round(trMaxMinYear);
			 		trRemMaxVal = 'Year';
			 	}else if(Math.trunc(trMaxMinMonth)  > 0){
			 		trMaxVal = Math.round(trMaxMinMonth);
			 		trRemMaxVal = 'Month';
			 	}else{
			 		tenureRemaningVal = tenureDayValue - remainingDays;
			 		trRemMaxVal = 'Days';
			 	}
			 	
			 	
			 	
			 	
		 		if(parseInt(totalDays) > parseInt((remainingDays))-1){
		 			document.getElementById('fdTenure1').style.borderColor = "red";
					document.getElementById('validationError').innerHTML+='Your total tenure is '+tenureVal+' ' +trval +' '+(tenureDayValue - (365*tenureVal))  +' Days .  '+  ((tenureDayValue - remainingDays))  +' Days has already crossed. So you can reduce the tenure maximum by '+ trMaxVal +' '+trRemMaxVal+' '+(remainingDays)+' Days<br>'
					submit = false;
					
				
				
			} 
			
			}
		
		
		debugger
			if(fdTenureType1_=="Month"){
			 	var monthToDays = fdTenure1_ * 30.4375;
			 	var reduceTenureRadio_ = document.getElementById('reduceTenureRadio').checked;
				if(reduceTenureRadio_ == true){
					if(monthToDays>=tenureDayValue){
						document.getElementById('fdTenure1').style.borderColor = "red";
						document.getElementById('validationError').innerHTML+='You can not reduce tenure more than total tenure';
						submit = false;
						return;
					}
				}
			 	
			 	var dynamicMonthVal = "Days";
			 	var dynamicMonthOrDays = (remainingDays)/30.4375;
			 	var month_1 = Math.trunc(dynamicMonthOrDays);
			 	var days_1 = tenureDayValue - month_1*30.4375;
			 	var totalMonthTenure = Math.trunc((tenureDayValue/30.4375));
			 	var totalDaysTenure = tenureDayValue - totalMonthTenure*30.4375;
			 	
			 	
			 	
		 		if(parseInt(monthToDays) > parseInt(remainingDays)-1){
		 			document.getElementById('fdTenure1').style.borderColor = "red";
					document.getElementById('validationError').innerHTML+='Your total tenure is '+ (totalMonthTenure) +' Months '+ Math.round(totalDaysTenure) +" Days . "+ ((tenureDayValue - remainingDays))  +' Days has already crossed. So you can reduce the tenure maximum by '+ Math.round(month_1)+" "+"Months "+ Math.round(days_1-1) +'  Days<br>'
					submit = false;
					
				
				
			} 
			
			
			}
		
		
		
		
		
		/* if(tenureCheck==true){yearsToYearsMonthsDays
		if(reduceTenureRadio==true || addTenureRadio==true){
		
		var fdTenure=parseFloat(document.getElementById('newFDTenure').value);
		var newFDTenureType=document.getElementById('fdTenureType').value;
	
		
		if(isNaN(newFDTenure)){
			document.getElementById('newFDTenure').style.borderColor = "red";
			document.getElementById('validationError').innerHTML+='Please enter valid tenure<br>'
				submit = false;
		}
	
		}
		} */
		
		if(convertDeposit==true){
			if(newFDTenureType==''){
				document.getElementById('newFDTenureType').style.borderColor = "red";
				document.getElementById('validationError').innerHTML+='Please select tenure type<br>'
					submit = false;
			}
			
			
			
			
			
			
			 if(submit==false){
					return false;	
					}
			
			
			var newPaymentTypeId_ = $("#newPaymentTypeId").val();
			
			var newFDTenureType_ = $("#newFDTenureType").val();
			if(newPaymentTypeId_=='Monthly'){
				if(newFDTenureType_ == 'Day'){
				if(parseInt(newFDTenure) < 30.4375){
				document.getElementById('newFDTenure').style.borderColor = "red";
				document.getElementById('validationError').innerHTML+='Minimum tenure should be 30 days for Monthly recurring deposit.<br>'
					submit = false;
				}
			}
			}
			
			if(newPaymentTypeId_=='Quarterly'){
				if(newFDTenureType_ == 'Day'){
				if(parseInt(newFDTenure) < 90){
				document.getElementById('newFDTenure').style.borderColor = "red";
				document.getElementById('validationError').innerHTML+='Minimum tenure should be 90 days for Quarterly recurring deposit.<br>'
					submit = false;
				}
			}
			}
			if(newPaymentTypeId_=='Half Yearly'){
				if(newFDTenureType_ == 'Day'){
				if(parseInt(newFDTenure) < 180){
				document.getElementById('newFDTenure').style.borderColor = "red";
				document.getElementById('validationError').innerHTML+='Minimum tenure should be 180 days for Half Yearly recurring deposit.<br>'
					submit = false;
				}
			}
			}
			
			
			if(newPaymentTypeId_=='Annually'){
				if(newFDTenureType_ == 'Day'){
				if(parseInt(newFDTenure) < 365){
				document.getElementById('newFDTenure').style.borderColor = "red";
				document.getElementById('validationError').innerHTML+='Minimum tenure should be 365 days for Annually recurring deposit.<br>'
					submit = false;
				}
			}
			}
			
			
			
			
			
			
			if(isNaN(newFDTenure)){
				document.getElementById('newFDTenure').style.borderColor = "red";
				document.getElementById('validationError').innerHTML+='Please enter valid tenure<br>'
					submit = false;
			}
			}
		/******add/reduce tenure end ********/
		
		
		/****** add/reduce amount validation start********/
		var fdAmount = parseFloat(document.getElementById('fdFixed').value);
		var addRadio = document.getElementById('addRadio').checked;
		var reduceRadio = document.getElementById('reduceRadio').checked;
		var addAmount=parseFloat(document.getElementById('addAmount').value);
	
		if(amountChange==true){
		if(reduceRadio==true || addRadio==true){
		
		if(isNaN(addAmount)){
			document.getElementById('addAmount').style.borderColor = "red";
			document.getElementById('validationError').innerHTML+='Please enter the amount to add/reduce<br>'
				submit = false;
		}
		else{
		if(addRadio==true){
			fdAmount=fdAmount+addAmount; 
		}
		if(reduceRadio==true){
			fdAmount=fdAmount-addAmount; 
		}
		
		}
		}
		}
		
		if(addRadio==true && (fdAmount < parseInt('${productConfiguration.minimumDepositAmount}'))){
			
			document.getElementById('addAmount').style.borderColor = "red";
			document.getElementById('validationError').innerHTML+='Minimum deposit amount is '+"${productConfiguration.minimumDepositAmount}"+'<br>'
			submit = false;
			
		}
		if(addRadio==true && (fdAmount > parseInt('${productConfiguration.maximumDepositAmount}'))){
			document.getElementById('addAmount').style.borderColor = "red";
			document.getElementById('validationError').innerHTML+='Maximum deposit amount is  '+"${productConfiguration.maximumDepositAmount}"+'<br>'
			submit = false;
			
		}
		
		 if(reduceRadio==true && (addAmount > parseFloat(document.getElementById('fdFixed').value))){
			document.getElementById('addAmount').style.borderColor = "red";
			document.getElementById('validationError').innerHTML+='Maximun Reduce Amount for this deposit is '+ parseFloat(document.getElementById('fdFixed').value) +'<br>'
				submit = false;
			
		}
		
		/******add/reduce amount validation end ********/
		
		/****** minimum deposit validation start********/
		/* var minDepositAmount= parseInt('${productConfiguration.minimumDepositAmount}');
		var maxDepositAmount = parseInt('${productConfiguration.maximumDepositAmount}');
		debugger
		if(parseFloat(fdAmount) < parseFloat(minDepositAmount)){
		document.getElementById('addAmount').style.borderColor = "red";
		document.getElementById('validationError').innerHTML+='Minimum amount for deposit is: '+minDepositAmount+'.<br>'
		submit = false;
		} */
		
		/******  minimum deposit validation end ********/
		/* if(parseFloat(fdAmount) > parseFloat(maxDepositAmount)){
			document.getElementById('addAmount').style.borderColor = "red";
			document.getElementById('validationError').innerHTML+='Maximum amount for deposit is: '+maxDepositAmount+'.<br>'
			submit = false;
			} */
		
		/****** Payment Type validation start********/
		
		var changePaymentType = document.getElementById('changePaymentType').value;
		if(changePaymentType==""){
			document.getElementById('changePaymentType').style.borderColor = "red";
			document.getElementById('validationError').innerHTML+='Please select payment type<br>'
			
				submit = false;
		}
		var recurring= document.getElementById("recurring").checked;
		
		if(recurring ==true){
			var deductionDay = document.getElementById('deductionDayVal').value;
			if(deductionDay==""){
			document.getElementById('deductionDayVal').style.borderColor = "red";
			document.getElementById('validationError').innerHTML+='Please select deduction day.<br>'
			
				submit = false;
			}
		}
		
		var fdPay = document.getElementById('fdPay').value;
		if(fdPay=="Fund Transfer"){
			var linkedAccountNumber = document.getElementById('linkedAccountNumber').value;
			if(linkedAccountNumber==""){
				document.getElementById('linkedAccountNumber').style.borderColor = "red";
				document.getElementById('validationError').innerHTML+='No linked account selected<br>'
				
					submit = false;
			}
			
		}
		
		/****** Payment Type validation  end ********/
		
		/****** Payoff validation start********/
		var payOffType = document.getElementById('payOffType').value;
		
		if(payOffType==""){
		}
		else{
			var size= <c:out value="${depositHolderList.size()}"/>
			for(var i=0;i<size;i++){
				var fdPayId='fdPay['+i+']';
				var fdPay = document.getElementById(fdPayId).value;
				if(fdPay==""){
					document.getElementById('validationError').innerHTML+='Please select part/percent payoff<br>';
					document.getElementById(fdPayId).style.borderColor = "red";	
					submit = false;
				}
				else{
					
					if(fdPay=="PART"){
						var interestPayAmountId= 'interestPayAmount['+i+']';
						var interestPayAmount =parseFloat(document.getElementById(interestPayAmountId).value);
						if(isNaN(interestPayAmount)){
							document.getElementById(interestPayAmountId).style.borderColor = "red";
							document.getElementById('validationError').innerHTML+='Please enter payoff amount<br>'
								submit = false;
						}
					}
					else if(fdPay=="PERCENT"){
						var interestPercentId= 'interestPercent['+i+']';
						var interestPercent =parseFloat(document.getElementById(interestPercentId).value);
						if(isNaN(interestPercent)){
							document.getElementById(interestPercentId).style.borderColor = "red";
							document.getElementById('validationError').innerHTML+='Please enter percent payoff amount<br>'
								submit = false;
						}	
					
					}
					
				}
				
					
					
					var payOffAccountTypeId = 'depositHolderList'+i+'.payOffAccountType';
					var payOffAccountType = document.getElementById(payOffAccountTypeId).value;
					
					if(payOffAccountType==""){
						document.getElementById(payOffAccountTypeId).style.borderColor = "red";
						document.getElementById('validationError').innerHTML+='Please select payoff account type<br>'
							submit = false;
					}
					
					else{
						var nameOnBankAccountId = 'depositHolderList'+i+'.nameOnBankAccount';
						var nameOnBankAccount = document.getElementById(nameOnBankAccountId).value;
						var accountNumberId = 'depositHolderList'+i+'.accountNumber';
						var accountNumber = document.getElementById(accountNumberId).value;
						
						if(nameOnBankAccount==""){
							document.getElementById(nameOnBankAccountId).style.borderColor = "red";
							document.getElementById('validationError').innerHTML+='Please enter beneficiary name<br>'
								submit = false;
						}
						
						
						if(accountNumber==""){
							document.getElementById(accountNumberId).style.borderColor = "red";
							document.getElementById('validationError').innerHTML+='Please enter beneficiary account number<br>'
								submit = false;
						}
						
						if(payOffAccountType=="Other"){
							
							var neftId= 'neft['+i+']';
							var impsId= 'imps['+i+']';
							var rtgsId= 'rtgs['+i+']';
							
							document.getElementById(neftId).style.boxShadow = "none";
						    document.getElementById(impsId).style.boxShadow = "none";
						    document.getElementById(rtgsId).style.boxShadow = "none";
							
						    var imps= document.getElementById(impsId).checked;
						    var neft= document.getElementById(neftId).checked;
						    var rtgs= document.getElementById(rtgsId).checked;
						    
							if(imps ==false && rtgs ==false && neft ==false){
								document.getElementById(neftId).style.boxShadow = "0 0 5px 0px red inset";
							    document.getElementById(impsId).style.boxShadow = "0 0 5px 0px red inset";
							    document.getElementById(rtgsId).style.boxShadow = "0 0 5px 0px red inset";
								
							    document.getElementById('validationError').innerHTML+='Please choose transfer type<br>'
							    	submit = false;
							
							}
							
							var bankNameId = 'depositHolderList'+i+'.bankName';
							var bankName = document.getElementById(bankNameId).value;
							
							if(bankName==""){
								document.getElementById(bankNameId).style.borderColor = "red";
								document.getElementById('validationError').innerHTML+='Please enter beneficiary bank name<br>'
									submit = false;
							}
							
						
							var ifscCodeId = 'depositHolderList'+i+'.ifscCode';
							var ifscCode = document.getElementById(ifscCodeId).value;
							
							if(ifscCode==""){
								document.getElementById(ifscCodeId).style.borderColor = "red";
								document.getElementById('validationError').innerHTML+='Please enter beneficiary bank ifsc<br>'
									submit = false;
							}
							
							
							
							
							
						}
						
					}
					
					
				
			
					
			}
			
		}
		
		if(submit==false){return false;}
		
		var addTenureRadio = document.getElementById('addTenureRadio').checked;
		var reduceTenureRadio = document.getElementById('reduceTenureRadio').checked;
		var convertDeposit = document.getElementById('convertDeposit').checked;
		
		var uiscreen = "confirmEditFdBank?holderId=${holderId}";

// 		if("${baseURL[1]}" == 'bnkEmp')
		//var strURL = "${baseURL[1]}";
		var _urlForCustomerAndEmp = window.location.pathname;
		var strURL = _urlForCustomerAndEmp;
		if( strURL.includes("common") || strURL.includes("Bank") ||  strURL.includes("bank"))	
			{
			uiscreen = "confirmEditFdBank?holderId=${holderId}";
			}
		else
			{
			uiscreen = "confirmEditFd?holderId=${holderId}";
			}
		
// 		var convertDepositProceed = true;
 		if(convertDeposit==true)
 		{

 			/* getAlertBeforeConversion(function(d) { */
				
			 	//if(d==true){
				$("#editForm").attr("action", uiscreen);
				$("#editForm").submit();
				//}
			 	//else{
			 		
			 	//	document.getElementById('error').innerHTML='Operation stopped.'
			 //}
			 	
			//	/* /* } */); */
 		}

 		else
 		{
 			
			if(reduceTenureRadio==false && addTenureRadio==false){
				$("#editForm").attr("action",uiscreen);
				$("#editForm").submit();
			}
			else{

				ajaxFun(function(d) {
				 	if(d==true){
					$("#editForm").attr("action", uiscreen);
					$("#editForm").submit();
					}
				 	else{
				 		
				 		document.getElementById('error').innerHTML='Operation stopped.'
				 	}
				 	
					})
			}

 		}	 
	
	}
</script>
<div class="right-container" id="right-container">
	<div class="container-fluid">

		<div class="Flexi_deposit">

			<div class="header_customer">
				<h3>
					<spring:message code="label.fixedDeposit" />
				</h3>
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
				<form:form action="confirmEditFdBank" class="form-horizontal"
					method="post" name="fixedDeposit" commandName="depositHolderForm"
					id="editForm">


					<%-- <form:hidden path="depositHolderId" /> --%>
					<%-- 	<form:hidden path="flexiRate" /> --%>

					<%-- <form:hidden path="depositHolder.customerId" /> --%>
					<form:hidden path="deposit.interestRate" />
					<form:hidden path="deposit.tenureType" />
					<form:hidden path="deposit.tenure" />
					<form:hidden path="depositHolder.depositHolderCategory" />
					<form:hidden path="deposit.modifiedDate" id="modifiedDate" />



					<div class="col-md-6">


						<div class="form-group">

							<label class="control-label col-md-4" for=""
								style="margin-top: -5px;"><spring:message
									code="label.changeDeposit" /></label> <label
								style="margin-left: 15px;"> <form:radiobutton
									path="depositChange" value="tenureChange" id="tenureChange"
									onclick="tenChange()"></form:radiobutton></label>
							<spring:message code="label.fdTenure" />
							<label> <form:radiobutton path="depositChange"
									value="amountChange" id="amountChange" onclick="tenChange()"></form:radiobutton></label>
							<spring:message code="label.depositAmount" />

							<label style="margin-left: 1px;"> <form:radiobutton
									path="depositChange" value="convertDeposit" id="convertDeposit"
									onclick="tenChange()"></form:radiobutton></label>
							<spring:message code="label.convertDeposit" />

						</div>


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
									code="label.depositClassification" /></label>
							<div class="col-md-6">
								<form:input path="deposit.depositClassification"
									value="${depositClassification}" class="form-control" id="fdID"
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
							<label class="col-md-4 control-label"><spring:message
									code="label.selectDate" /></label>
							<div class="col-md-6">
								<form:input path="deposit.dueDate" class="form-control"
									id="fdDueDate" readonly="true" />
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
							<label class="col-md-4 control-label">Tenure In Days</label>
							<div class="col-md-6">
								<input class="form-control" name ="totalTenureInDays" id= "tenureConcat"
									value="${depositHolderForm.deposit.totalTenureInDays}"
									 readonly />
							</div>
						</div>


						<div class="form-group" id="addReduceTenureDIV"
							style="display: none;">
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
									class="form-control input" id="fdTenureType1"
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
							<div class="col-md-2" id="fdTenureDIV">
								<form:input path="deposit.tenureType" type="number" min="1"
									class="form-control input" id="fdTenure1"
									onBlur="onChangeTenure()" onkeypress="validationAccount(event)" />
							</div>

							<div class="col-md-1">
								<a href="#" onclick="hideDiv('tenureType')">Cancel</a>
							</div>


						</div>
						<span style="color: red; margin-left: 178px;" id="tenureError"></span>
						<div id="newMaturityDateDiv" style="margin-top: 20px;">
							<label class="col-md-4 control-label" style="margin-top: -5px;">New
								maturity date</label>
							<div class="col-md-6">
								<form:input path="editDepositForm.maturityDateNew"
									class="form-control" id="newMaturityDate" readonly="true" />
							</div>
						</div>

						<div style="clear: both; height: 10px;"></div>
						<div class="form-group">
							<label class="col-md-4 control-label">Stop Payment</label>

							<div class="col-md-2">
								<form:checkbox path="deposit.stopPayment" value="1"
									class="form-control" id="stopPayment" style=" width: 16px;" />
							</div>
						</div>

						<div style="clear: both; height: 15px;"></div>

					</div>


					<div class="col-md-6">

						<%-- <div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.typeOfDeposit" /></label>
							<div class="col-md-6">
								<form:input path="deposit.depositType" class="form-control"
									id="contactNum" readonly="true" />
							</div>
						</div> --%>

						<div class="form-group">
							<label class="col-md-4 control-label">Created Date</label>
							<div class="col-md-6">
								<form:input path="deposit.createdDate" class="form-control"
									id="createdDate" readonly="true" />
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-md-4 control-label">Modified Date</label>
							<div class="col-md-6">
								<form:input path="deposit.modifiedDate" class="form-control"
									id="modifiedDate" readonly="true" />
							</div>
						</div>
						


						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.modeOfPay" /></label>
							<div class="col-md-6">
								<form:select path="deposit.paymentMode"
									class="form-control input" id="fdPay"
									onchange="onChangePaymentMode(this.value)">
									<option value="Fund Transfer"
										<c:if test="${depositHolderForm.deposit.paymentMode == 'Fund Transfer'}"> selected </c:if>>Debit
										from Savings account</option>
									<option value="DD"
										<c:if test="${depositHolderForm.deposit.paymentMode == 'DD'}"> selected </c:if>>DD</option>
									<option value="Cheque"
										<c:if test="${depositHolderForm.deposit.paymentMode == 'Cheque'}"> selected </c:if>>Cheque</option>
									<option value="Cash"
										<c:if test="${depositHolderForm.deposit.paymentMode == 'Cash'}"> selected </c:if>>Cash</option>
									<option value="Card Payment"
										<c:if test="${depositHolderForm.deposit.paymentMode == 'Card Payment'}"> selected </c:if>>Card
										Payment</option>
									<option value="Net Banking"
										<c:if test="${depositHolderForm.deposit.paymentMode == 'Net Banking'}"> selected </c:if>>Net
										Banking</option>
								</form:select>
							</div>
						</div>

						<div class="form-group" id="linkedAccountDetails">
							<label class="col-md-4 control-label">Account Details</label>
							<div class="col-md-6">
								<form:select path="deposit.linkedAccountNumber"
									class="form-control input" id="linkedAccountNumber">
									<c:forEach items="${accountList}" var="account">
										<option value="${account.accountNo}"
											<c:if test="${depositHolderForm.deposit.linkedAccountNumber ==account.accountNo}" >selected="true"</c:if>>${account.accountNo}
										</option>
									</c:forEach>
								</form:select>
							</div>
						</div>


						<div class="form-group">
							<label class="col-md-4 control-label">Deposit Amount</label>
							<div class="col-md-6">
								<fmt:formatNumber
									value="${depositHolderForm.deposit.depositAmount}"
									pattern="#.##" var="depositamount" />
								<form:hidden path="deposit.depositAmount" id="fdFixed" />
								<input type="text" id="depositAmounttxt" class="form-control commaSeparated"
									value="${depositamount}" readonly />
								<%-- 	<span id="fdAmountError2" style="display: none; color: red">Minimum
									amount for deposit is: ${bankConfiguration.minDepositAmount}</span> --%>

							</div>
						</div>

						<div class="form-group" style="margin-top: -5px;">
							<label class="col-md-4 control-label"><spring:message
									code="label.selectFormat" /></label>
							<div class="col-md-6">
								<form:select path="deposit.paymentType"
									class="form-control input" id="changePaymentType">
									<option value="">
										<spring:message code="label.selectValue" />
									</option>
									<option value="One-Time"
										<c:if test="${depositHolderForm.deposit.paymentType == 'One-Time'}">selected</c:if>
										id="oneTime">
										<spring:message code="label.oneTimePayment" />
									</option>
									<option value="Monthly"
										<c:if test="${depositHolderForm.deposit.paymentType == 'Monthly'}">selected</c:if>
										id="monthly">
										<spring:message code="label.monthly" />
									</option>
									<option value="Quarterly"
										<c:if test="${depositHolderForm.deposit.paymentType == 'Quarterly'}"> selected</c:if>
										id="quarterly">
										<spring:message code="label.quarterly" />
									</option>
									<option value="Half Yearly"
										<c:if test="${depositHolderForm.deposit.paymentType == 'Half Yearly'}"> selected</c:if>
										id="halfYearly">
										<spring:message code="label.halfYearly" />
									</option>
									<option value="Annually"
										<c:if test="${depositHolderForm.deposit.paymentType == 'Annually'}"> selected</c:if>
										id="annually">
										<spring:message code="label.annually" />
									</option>
								</form:select>
							</div>
						</div>





						<!-- changes.............. -->
						<div class="form-group" id="addReduceAmountDIV"
							style="display: none;">
							<label class="control-label col-md-4" for=""
								style="margin-top: -5px;">Add/Reduce Amount </label> <label
								for="radio" style="margin-left: 15px;"> <form:radiobutton
									path="editDepositForm.status" value="add" id="addRadio"
									name="addRadio" onclick="showAddReduceBox('addReduceDiv')"></form:radiobutton></label>
							Add <label for="radio"> <form:radiobutton
									path="editDepositForm.status" value="reduce" id="reduceRadio"
									name="reduceRadio" onclick="showAddReduceBox('addReduceDiv')"></form:radiobutton></label>
							Reduce


						</div>



						<span id="taxSavingDepositErrorForAmount" class="error"
							style="display: none;"><font color="red"><spring:message
									code="label.taxSavingDepositErrorForAmount" /></font></span>



						<div class="form-group" id="addReduceDiv" style="display: none;">
							<label class="control-label col-md-4" for="">Enter Amount
							</label>
							<div class="col-md-3">
								<fmt:formatNumber
									value="${depositHolderForm.editDepositForm.addAmount}"
									pattern="#.##" var="addAmount" />
								<form:input path="editDepositForm.addAmount"
									class="form-control input" type="number" value="${addAmount}"
									id="addAmount" onkeyup="addedAmt()"
									onkeypress="validationAccount(event)"  min="0"/>

							</div>
							<div class="col-md-1">
								<a href="#" onclick="hideDiv('amount')">Cancel</a>
							</div>
							<div style="clear: both; height: 8px;"></div>
							<label class="control-label col-md-4" for=""
								style="margin-bottom: -5px;">Final Deposit Amount </label>
							<div class="col-md-6" style="margin-bottom: -5px;">

								<form:input path="editDepositForm.addAmount"
									class="form-control" type="number" value="${totalAmount}"
									id="totalAmount" readonly="true" />

							</div>



						</div>

						<div style="clear: both; margin-top: 15px;"></div>

						<div class="form-group" style="display: none;"
							id="convertDepositDIV">
							<div class="form-group"
								style="width: 455px; border-bottom: 1px dotted #cfcfcf; text-align: -webkit-right;">
								<div class="col-md-10">
									<span style="display: block; margin-left: 25px;">
										<h4>New Deposit / Convert Deposit</h4>
									</span>
								</div>
							</div>

							<div class="form-group"
								style="margin-top: -10px; margin-bottom: -5px;">

								<div class="col-md-4"
									style="text-align: right; margin-top: -10px;">

									<span id="convertTo"><h5>
											Convert to<span style="color: red">*</span>
										</h5></span>
								</div>

								<div class="col-md-7" style="display: none;" id="recurringDiv">
									<label for="radio"> <form:radiobutton
											path="depositConversion" value="Recurring Deposit"
											id="recurring" name="addRadio" onclick="recurringDeposit()"></form:radiobutton></label>
									<spring:message code="label.recurringDeposit" />
									<br>
								</div>
								<div class="col-md-7" style="display: none;" id="regularDiv">
									<label for="radio"> <form:radiobutton
											path="depositConversion" value="Regular Deposit" id="regular"
											name="reduceRadio" onclick="regularDeposit()"></form:radiobutton></label>
									<spring:message code="label.regularDeposit" />


								</div>
							</div>


							<div class="form-group"
								style="display: none; clear: both; margin-top: 15px;"
								id="typeOfTenureDiv">
								<label class="col-md-4 control-label"><spring:message
										code="label.typeOfTenure" /><span style="color: red">*</span></label>
								<div class="col-md-6">
									<form:select path="newDepositTenureType"
										class="input form-control" id="newFDTenureType"
										onchange="showTenureDetails(this.value)">
										<form:option value="">
											<spring:message code="label.selectValue" />
										</form:option>
										<form:option value="Day">
											<spring:message code="label.days" />
										</form:option>
										<form:option value="Month">
											<spring:message code="label.month" />
										</form:option>
										<form:option value="Year">
											<spring:message code="label.Year" />
										</form:option>
									</form:select>
								</div>

							</div>
							<div class="form-group" style="display: none;" id="tenureDiv">
								<label class="col-md-4 control-label"> <span id="dayId"
									style="display: none"><spring:message code="label.days" /><span
										style="color: red">*</span></span><span id="monthId"
									style="display: none"><spring:message code="label.month" /><span
										style="color: red">*</span></span> <span id="yearId"
									style="display: none"><spring:message code="label.Year" /><span
										style="color: red">*</span></span></label>


								<div class="col-md-2">
									<form:input path="newDepositTenure" type="number" min="0"
										class="input form-control" id="newFDTenure"
										onkeypress="validationAccount(event)" />
									<span id="fdTenureError" style="display: none; color: red"><spring:message
											code="label.tenureError" /></span> <span id="fdTenureMax9YearError"
										style="display: none; color: red">Please enter valid </span>

								</div>

								<div id="dayValue" style="margin-left: 15px;">
									<label class="col-md-2 control-label"
										style="text-align: right; padding-left: 45px;"><spring:message
											code="label.days" /></label>
									<div class="col-md-2">
										<form:input path="newDepositDaysValue" type="number"  min="0"
											class="input form-control" id="daysValue" onkeypress="validationAccount(event)"/>
										<span id="dayValueError" style="display: none; color: red" ><spring:message
												code="label.daysError" /></span>

									</div>

								</div>


							</div>


							<div class="form-group" style="display: none"
								id="newPaymentTypeDiv">
								<label class="col-md-4 control-label"><spring:message
										code="label.selectFormat" /></label>
								<div class="col-md-6">
									<form:select path="newPaymentType" class="form-control input"
										id="newPaymentTypeId">
										<option value="">
											<spring:message code="label.selectValue" />
										</option>
										<option value="One-Time" id="oneTimeNew">
											<spring:message code="label.oneTimePayment" />
										</option>
										<option value="Monthly" id="monthlyNew">
											<spring:message code="label.monthly" />
										</option>
										<option value="Quarterly" id="quarterlyNew">
											<spring:message code="label.quarterly" />
										</option>
										<option value="Half Yearly" id="halfYearlyNew">
											<spring:message code="label.halfYearly" />
										</option>
										<option value="Annually" id="annuallyNew">
											<spring:message code="label.annually" />
										</option>
									</form:select>
								</div>
							</div>




							<div class="form-group" style="display: none" id="deductionDay">
								<label class="col-md-4 control-label" style="margin-top: -7px;">Amount
									Deducting Day<span style="color: red">*</span>
								</label>
								<div class="col-md-2">
									<form:select path="newDepositDeductionDay" class="form-control"
										id="deductionDayVal">
										<option value="">Day</option>
										<%
											for (int i = 1; i <= 30; i++) {
										%>
										<option value="<%=i%>"><%=i%></option>
										<%
											}
										%>

									</form:select>
								</div>
							</div>
							<c:if test="${!empty penaltyAmount}">
								<div class="form-group">
									<label class="col-md-4 control-label">(Penalty+GST)
										Amount</label>
									<div class="col-md-6">
										<form:hidden path="editDepositForm.penalty"
											value="${penaltyAmount}" />
										<form:input path="editDepositForm.totalPenalty"
											class="form-control" value="${totalpenaltyAmount}"
											readonly="true" />
									</div>
								</div>
							</c:if>


						</div>
					</div>



					<div class="header_me col-md-12">
						<h3>Interest PayOff Details (Optional)</h3>
					</div>


					<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.payOffPeriod" /></label>
						<div class="col-md-6">
							<form:select path="deposit.payOffInterestType"
								class="form-control input" id="payOffType"
								onchange="onchangePayOffType()">
								<option value="">
									<spring:message code="label.select" />
								</option>
								<%-- <option value="Monthly" id="days"
									<c:if test="${depositHolderForm.deposit.payOffInterestType == 'Monthly'}"> selected</c:if>>
									<spring:message code="label.fdPayOffType1" />
								</option> --%>
								<option value="Quarterly"
									<c:if test="${depositHolderForm.deposit.payOffInterestType == 'Quarterly'}"> selected</c:if>>
									<spring:message code="label.fdPayOffType3" />
								</option>
								<option value="Semiannual"
									<c:if test="${depositHolderForm.deposit.payOffInterestType == 'Semiannual'}"> selected</c:if>>
									<spring:message code="label.fdPayOffType4" />
								</option>
								<option value="Annual"
									<c:if test="${depositHolderForm.deposit.payOffInterestType == 'Annual'}"> selected</c:if>>
									<spring:message code="label.fdPayOffType5" />
								</option>
								<option value="End of Tenure"
									<c:if test="${depositHolderForm.deposit.payOffInterestType == 'End of Tenure'}"> selected</c:if>>
									<spring:message code="label.fdPayOffType2" />
								</option>
							</form:select>
						</div>
					</div>

					<div id="payOffDetails">

						<form:hidden path="depositHolderList[0].customerId"
							value="${depositHolderList[0].depositHolder.customerId}" />


						<c:forEach items="${depositHolderList}" varStatus="status"
							var="dHolder">
							<form:hidden path="depositHolderList[${status.index}].id"
								value="${dHolder.depositHolder.id}" />

							<div class="header_me col-md-12">
								<h5>Deposit holder: ${status.index+1}</h5>
							</div>


							<div class="form-group" style="margin-top: 35px;">
								<label class="col-md-4 control-label">Holder name</label>
								<div class="col-md-6">
									<form:input path="customerName"
										value="${depositHolderList[status.index].customerName}"
										class="form-control" readonly="true" />
								</div>
							</div>

							<div class="form-group">
								<label class="col-md-4 control-label">Holder type</label>
								<div class="col-md-6">
									<form:input
										path="depositHolderList[${status.index}].depositHolderStatus"
										value="${depositHolderList[status.index].depositHolder.depositHolderStatus}"
										class="form-control" readonly="true" />
								</div>
							</div>





							<div class="form-group" id="everyMonthDiv[${status.index}]">
								<label class="col-md-4 control-label"><spring:message
										code="label.interestType" /></label>
								<div class="col-md-6">
									<form:select
										path="depositHolderList[${status.index}].interestType"
										class="input form-control" id="fdPay[${status.index}]"
										onchange="displayDiv(this.value,${status.index})">
										<option value="">Select</option>
										<option value="PART"
											<c:if test="${dHolder.depositHolder.interestType == 'PART'}"> selected </c:if>>PART</option>
										<option value="PERCENT"
											<c:if test="${dHolder.depositHolder.interestType == 'PERCENT'}"> selected </c:if>>PERCENT</option>

									</form:select>
								</div>
							</div>

							<div id="interestPercentTr[${status.index}]"
								style="display: ${(dHolder.depositHolder.interestType=='PERCENT') ? 'block': 'none'};">
								<div class="form-group">
									<label class="col-md-4 control-label">Percentage</label>
									<div class="col-md-6">
										<form:input
											path="depositHolderList[${status.index}].interestPercent"
											class="form-control input" placeholder="Enter Percentage"
											value="${dHolder.depositHolder.interestPercent}"
											id="interestPercent[${status.index}]" type="number" min="0"
											max="99" onkeypress="return isNumberKey1(event,$(this))" onkeyup="return checkPercentage(event,$(this))" />
									</div>
								</div>
							</div>




							<div id="firstPartInterestAmtTr[${status.index}]"
								style="display: ${(dHolder.depositHolder.interestType=='PART') ? 'block': 'none'};">
								<div class="form-group">
									<label class="col-md-4 control-label"><spring:message
											code="label.amount" /></label>
									<div class="col-md-6">
										<form:input
											path="depositHolderList[${status.index}].interestAmt"
											class="form-control input"
											value="${dHolder.depositHolder.interestAmt}"
											id="interestPayAmount[${status.index}]"
											onkeypress="return isNumberKey2(event,$(this))" onkeyup="return checkAmount(event,$(this))"/>
									</div>
								</div>
							</div>

							<div class="form-group" id="firstPartInterest[${status.index}]">
								<label class="col-md-4 control-label"><spring:message
										code="label.payOffAccount1" /></label>
								<div class="col-md-6">
									<form:select
										path="depositHolderList[${status.index}].payOffAccountType"
										class="form-control input"
										onchange="onChangeAccountType(this.value,${status.index},true)">
										<option value="">Select</option>

										<option value="Saving Account"
											<c:if test="${dHolder.depositHolder.payOffAccountType == 'Saving Account'}"> selected </c:if>>Same
											Bank</option>
										<option value="Other"
											<c:if test="${dHolder.depositHolder.payOffAccountType == 'Other'}"> selected </c:if>>Different
											Bank</option>

									</form:select>
								</div>
							</div>

							<div id="beneficiaryDetails[${status.index}]"
								style="display: ${(empty dHolder.depositHolder.nameOnBankAccount) ? 'none': 'block'};">
								<div class="form-group">
									<label class="col-md-4 control-label"><spring:message
											code="label.name" /><span style="color: red">*</span></label>
									<div class="col-md-6">
										<form:input
											path="depositHolderList[${status.index}].nameOnBankAccount"
											value="${dHolder.depositHolder.nameOnBankAccount}"
											id="depositHolderList${status.index}.nameOnBankAccount"
											placeholder="Enter Name" class="form-control input"
											onkeypress="validName(event)" />
									</div>
								</div>

								<div class="form-group">
									<label class="col-md-4 control-label"><spring:message
											code="label.accountNo" /><span style="color: red">*</span></label>
									<div class="col-md-6">
										<form:input
											path="depositHolderList[${status.index}].accountNumber"
											value="${dHolder.depositHolder.accountNumber}"
											id="depositHolderList${status.index}.accountNumber"
											placeholder="Enter AccountNo" class="form-control input"
											onkeypress="validationAccount(event)"  min="0"/>
									</div>
								</div>
							</div>

							<div id="otherBankDetails[${status.index}]"
								style="display: ${(empty dHolder.depositHolder.bankName) ? 'none': 'block'};">

								<div class="form-group">
									<label class="col-md-4 control-label"><spring:message
											code="label.transfer" /><span style="color: red">*</span></label>
									<div class="col-md-6">
										<label for="radio"> <form:radiobutton
												path="depositHolderList[${status.index}].transferType"
												value="NEFT" id="neft[${status.index}]"
												name="transferType[${status.index}]"></form:radiobutton></label>
										<spring:message code="label.transfer1" />
										<label for="radio"> <form:radiobutton
												path="depositHolderList[${status.index}].transferType"
												value="IMPS" id="imps[${status.index}]"
												name="transferType[${status.index}]"></form:radiobutton></label>
										<spring:message code="label.transfer2" />
										<label for="radio"><form:radiobutton
												path="depositHolderList[${status.index}].transferType"
												value="RTGS" id="rtgs[${status.index}]"
												name="transferType[${status.index}]"></form:radiobutton></label>
										<spring:message code="label.transfer3" />
									</div>
								</div>

								<script type="text/javascript">

				if('${dHolder.depositHolder.transferType}'!=''){
					if('${dHolder.depositHolder.transferType}'=='RTGS')
					 document.getElementById('rtgs['+${status.index}+']').checked = true;
					if('${dHolder.depositHolder.transferType}'=='IMPS')
						 document.getElementById('imps['+${status.index}+']').checked = true;
					if('${dHolder.depositHolder.transferType}'=='NEFT')
						 document.getElementById('neft['+${status.index}+']').checked = true;
				}
			</script>


								<div class="form-group">
									<label class="col-md-4 control-label"><spring:message
											code="label.bank" /><span style="color: red">*</span></label>
									<div class="col-md-6">
										<form:input path="depositHolderList[${status.index}].bankName"
											placeholder="Enter Bank Name" class="form-control input"
											value="${dHolder.depositHolder.bankName}"
											id="depositHolderList${status.index}.bankName"
											onkeypress="validName(event)" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label"><spring:message
											code="label.ifsc" /><span style="color: red">*</span></label>
									<div class="col-md-6">
										<form:input path="depositHolderList[${status.index}].ifscCode"
											placeholder="Enter IFSC Code" class="form-control input"
											value="${dHolder.depositHolder.ifscCode}"
											id="depositHolderList${status.index}.ifscCode"
											onkeypress="validIFSC(event)" />
									</div>
								</div>

							</div>



						</c:forEach>
					</div>

					<c:if test="">
						<div class="form-group" id="confirmConvert"
							style="display: none; color: red">



							<%-- <table align="center" class="f_deposit_btn">
							<tr>
								<td><span id='validationError' style="color: red"></span></td>
							</tr>
							<tr>
								<td><input type="button" onclick="val()"
									class="btn btn-info"
									value="<spring:message code="label.confirm"/>">gtgtdhtrhr</td>


							</tr>

						</table> --%>


						</div>
					</c:if>
				</form:form>
			</div>

			<div class="form-group">



				<table align="center" class="f_deposit_btn">
					<tr>
						<td><span id='validationError' style="color: red"></span></td>
					</tr>
					<tr>
						<td style="text-align:center;">
<%-- 						<a href="fdList" class="btn btn-success"><spring:message --%>
<%-- 									code="label.back" /></a>  --%>
									<input type="button" onclick="val()" style="text-align:center;"
							class="btn btn-info"
							value="<spring:message code="label.confirm"/>"></td>


					</tr>

				</table>


			</div>
		</div>
	</div>
</div>






<div id="myModal_edit" class="modal">

	<!-- Modal content -->
	<div class="modal-content" style="height: 345px !important;">
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
				
<!-- 				<tr id="maturityAmount">
					<td><b>Maturity amount:</b></td>
 					<td id="oldMaturityAmount"></td> 
 					<td id="newMaturityAmount"></td> 
				</tr> -->
				
				<tr>
					<td><b>Maturity date:</b></td>
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

		<div style="margin-left: 4px; margin-top: -13px;">
			<b>Adjustment amount:</b> <span id="adjustAmt"></span>
		</div>
		<div class="col-md-8" style="margin-top: 19px;">
			<button class="btn btn-success" id="proceed">Proceed</button>
			<button class="btn btn-danger" id="cancel">Cancel</button>
		</div>
	</div>

</div>

<div id="convertDepositAlertModal" class="modal">

	<!-- Modal content -->
	<div class="modal-content">
		<h4>
			<u>Deposit Conversion Details</u>
		</h4>


		<table class="table table-bordered" align="center" id="my-table">
			<thead>
				<tr class="success">
					<th>Parameters</th>
					<th>Value</th>

				</tr>
			</thead>
			<tbody>
				<tr>
					<td><b>Rate of Current Deposit</b></td>
					<td id="newInterestRate"></td>
				</tr>
				<tr>
					<td><b>Lose Amount</b></td>
					<td id="interestNeedsToAdjust"></td>
				</tr>
				<tr>
					<td><b>New Deposit Amount</b></td>
					<td id="newDepositAmount"></td>
				</tr>
				<tr>
					<td><b>Transfer Amount to Saving Account</b></td>
					<td id="transferAmountToSavingAccount"></td>
				</tr>
			</tbody>
		</table>

		<div class="col-md-12" style="margin-top: 19px;">
			<button class="btn btn-success" id="proceedForConversion">Proceed</button>
			<button class="btn btn-danger" id="cancelConversion">Cancel</button>
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
	height: 345px;
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