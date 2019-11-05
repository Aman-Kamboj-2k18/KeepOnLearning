<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="req"
	value="${requestScope['javax.servlet.forward.request_uri']}" />
<c:set var="baseURL" value="${fn:split(req,'/')}" />
<script src="<%=request.getContextPath()%>/resources/js/loginDate.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-1.11.1.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui-1.11.1.js"></script>
		
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui.multidatespicker.js"></script>
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/prettify.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/lang-css.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/mdp.css">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/prettify.css">



<div class="right-container" id="right-container">
	<div class="container-fluid">

		


		<div class="Flexi_deposit">

			<div class="header_customer" style="margin-bottom: 30px;">
				<h3>
					
					Issueing Overdraft
				</h3>
			</div>
			<div class="Success_msg">
				<div class="successMsg" style="text-align: center; color: red;">
					${error}</div>
					<div class="successMsg" style="text-align: center; color: green;">
					${sucess}</div>
			</div>
			<div class="flexi_table">
				<form:form class="form-horizontal"  action="overdraftIssue"  id="overdraftIssue" method="POST"
					name="overdraftIssue" onsubmit="return validate()" commandName="overdraftForm"  autocomplete="off">

<input type="hidden" name="menuId" value="${menuId}" id="menuId"/>
					<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.fdAccountNo" /><span style="color: red">*</span></label>
						<div class="col-md-4">
							<form:input path="accountNumber" class="myform-control" id="accountNumber" 
								placeholder="Enter Account Number"  required="true" autocomplete="off"/>

						</div>
					</div>
					
					<div class="col-sm-12 col-md-12 col-lg-12"
							style="margin-left: 402px;">
							<input type="button" class="btn btn-primary"
								 value="Search" onClick="myFunction()">

						</div>
					
					<div class="form-group">
						<label class="col-md-4 control-label">Current Balance<span style="color: red">*</span></label>
						<div class="col-md-4">
							<form:input path="currentBalance" class="myform-control" id="currentBalance"
								placeholder=" "  autocomplete="off" readonly="true" />

						</div><span id="currentBalanceNameError" style="display: none; color: red;"><spring:message code="label.validation" /></span>
					</div>
					<div class="form-group">
							<label class="col-md-4 control-label">Issue Date<span style="color: red">*</span></label>
							<div class="col-md-4">
								<form:input path="issueDate" value="${todaysDate}"
									placeholder="Select Date" class="form-control"
									style="background: whitesmoke; cursor: pointer;"
									id="datepicker" required="true" readonly="true" />

							</div>

						</div>
					
					<div class="form-group">
						<label class="col-md-4 control-label">Fixed Sanctioned Amount</label>
						<div class="col-md-4">
							<input type="radio" class="fixedAmount" value="0" checked name="fixedAmount" onchange="validateForm()" />No
							<input type="radio" class="fixedAmount" value="1" name="fixedAmount" onchange="validateForm()" />Yes
						</div>
					</div> 
					 <div class="form-group" id="sanctionPercentage">
						<label class="col-md-4 control-label">Overdraft Sanction Percentage %<span style="color: red">*</span></label>
						<div class="col-md-4">
							<form:input path="defaultOverdraftPercentage" id="defaultOverdraftPercentage" class="form-control"
								placeholder="" onkeyup="validatePercentage()"/>
							<span id="defaultOverdraftPercentageError" style="display: none; color: red;"><spring:message code="label.validation" /></span>
						</div>
					</div>
					
					
                    <div class="form-group" id="sanctionAmount">
						<label class="col-md-4 control-label">Overdraft Sanction Amount<span style="color: red">*</span></label>
						<div class="col-md-4">
							<form:input path="defaultOverdraftAmount" readonly="true" id="defaultOverdraftAmount" class="form-control" placeholder="" autocomplete="off" />
							<span id="defaultOverdraftAmountError" style="display: none; color: red;"><spring:message code="label.validation" /></span>
						</div>
					</div> 
					
					<div class="form-group">
						<label class="col-md-4 control-label">Rate Of Interest</label>
						<div class="col-md-4">
							
							<input value="" id="roi" class="form-control" readonly="true" />
						</div>
					</div> 
					<div class="form-group">
						<label class="col-md-4 control-label">Convert Withdraw Amount in Monthly EMI</label>
						<div class="col-md-4">
							<input type="checkbox" id="isEMI" onchange="updateEMI($(this))" />
							<input type="hidden" value="0" name="isEMI" id="hdnEMI"/>
						</div>
					</div> 
					
											<div class="form-group">
							<label class="col-md-4 control-label">Tenure Years<span style="color: red">*</span></label>
							<div class="col-md-6">
							<input name="totalTenureInDays" id="fdTenure" type="hidden">
							<form:hidden path="tenureInYears" id="tenureInYears"/>
							<!-- <input name="tenureInYears" id="tenureInYears" type="hidden"> -->
							<input name="tenureInMonths" id="tenureInMonths" type="hidden">
							<input name="tenureInDays" id="tenureInDays" type="hidden">
								<select class="input form-control"  id="fdTenureYears">
<option value="">Select</option><option value="1">1</option>
<option value="2">2</option>
<option value="3">3</option>
<option value="4">4</option>
<option value="5">5</option>
<option value="6">6</option>
<option value="7">7</option>
<option value="8">8</option>
<option value="9">9</option>
<option value="10">10</option>

</select>
							</div>

						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">Tenure Months<span style="color: red">*</span></label>

<div class="col-md-6">
<select class="input form-control" id="fdTenureMonths">
<option value="">Select</option>
<option value="1">1</option>
<option value="2">2</option>
<option value="3">3</option>
<option value="4">4</option>
<option value="5">5</option>
<option value="6">6</option>
<option value="7">7</option>
<option value="8">8</option>
<option value="9">9</option>
<option value="10">10</option>
<option value="11">11</option>
<option value="12">12</option>
</select>
								
							</div>

							


						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">Tenure Days<span style="color: red">*</span></label>

<div class="col-md-6">
								<select class="input form-control" id="fdTenureDays">
								<option value="">Select</option>
<option value="1">1</option>
<option value="2">2</option>
<option value="3">3</option>
<option value="4">4</option>
<option value="5">5</option>
<option value="6">6</option>
<option value="7">7</option>
<option value="8">8</option>
<option value="9">9</option>
<option value="10">10</option>
<option value="11">11</option>
<option value="12">12</option>
<option value="13">13</option>
<option value="14">14</option>
<option value="15">15</option>
<option value="16">16</option>
<option value="17">17</option>
<option value="18">18</option>
<option value="19">19</option>
<option value="20">20</option>
<option value="21">21</option>
<option value="22">22</option>
<option value="23">23</option>
<option value="24">24</option>
<option value="25">25</option>
<option value="26">26</option>
<option value="27">27</option>
<option value="28">28</option>
<option value="29">29</option>
<option value="30">30</option>
<option value="31">31</option>
</select>
							</div>
</div>
					

			 	
				     <div class="form-group">
						<div class="col-md-offset-4 col-md-8">
							<input type="submit" class="btn btn-info" id="goBtn" 
							
								value="Save" > 
						</div>
					</div> 

				</form:form>
			</div>

		</div>
		</div>
		</div>
		
<script>
	var currentBalance;var maxPerc;var minPerc;	
function myFunction() {
	debugger;
	
	var accountNumber = document.getElementById('accountNumber').value;
	
	// data string
    var dataString = 'accountNumber=' + accountNumber;
	data='';
	 
	  $.ajax({  
	    type: "GET",  
	    url: "<%=request.getContextPath()%>/common/getOverdraftDetails", 
	    contentType: "application/json",
	    dataType: "json",
	    data: dataString,
        async: false,
	    success: function(response){  
		   
// 			for (i = 0; i < response.length; ++i) {
				debugger;
				   var data = response;
				   if(data.currentBalance==0){
					   alert("Issue Overdraft not available");
					   return false;
				   }
				   currentBalance=data.currentBalance;
				   currentBalance=currentBalance.toFixed(2)
				   minPerc=data.minimumOverdraftPercentage;
				   maxPerc=data.maximumOverdraftPercentage;
				   document.getElementById('currentBalance').value= currentBalance;
				   document.getElementById('roi').value= data.interestRate;
				   //if( data.defaultOverdraftPercentage == null){
		               $('#sanctionAmount').show();
		              // if( data.defaultOverdraftAmount != null)
		              // document.getElementById('defaultOverdraftAmount').value = data.defaultOverdraftAmount;
		               
				  // } else  {
					   
					   $('#sanctionPercentage').show();
				   document.getElementById('defaultOverdraftPercentage').value = minPerc;
				   var vall= (minPerc/100)*data.currentBalance;
				   vall=vall.toFixed(2);
				   document.getElementById('defaultOverdraftAmount').value = vall;
				  // }
// 			}
		    	
	    },  
	    error: function(e){  
	    	debugger;
	    	alert("eror:productConfigurationId not exist with this account Number");
// 	    	 $('#error').html("Error occured!!")
	    	
	    }  
	  });  
	return data;

}  

function validatePercentage(){
	debugger;
	var bal= document.getElementById('currentBalance').value;
	var sanctionPercentage = document.getElementById('defaultOverdraftPercentage').value; 
	var vall= (sanctionPercentage/100)*bal;;
	   vall=vall.toFixed(2);
	   //document.getElementById('defaultOverdraftAmount').value = vall;
	document.getElementById('defaultOverdraftAmount').value =vall;
	
}


  function validate() {
	  
	  var sanctionAmount= document.getElementById('defaultOverdraftAmount').value; 
	  if(sanctionAmount=="")
		  {
		  alert("Please enter sanction amount");
			document.getElementById('defaultOverdraftPercentage').value=minPerc;
			return false;
		  }
	  var value=$('.fixedAmount:checked').val();
		if(value=="0"){
	  
	  var sanctionPercentage = document.getElementById('defaultOverdraftPercentage').value; 
		if(sanctionPercentage<minPerc){
			alert("Percentage can not be less than "+minPerc);
			//document.getElementById('defaultOverdraftPercentage').value=minPerc;
			return false;
		}
			
		if(sanctionPercentage>maxPerc){
			alert("Percentage can not be more than "+maxPerc);
			//document.getElementById('defaultOverdraftPercentage').value=maxPerc;
			return false
		}
		}
	  var fdTenureYears1 = document.getElementById('fdTenureYears').value;
		document.getElementById('tenureInYears').value = fdTenureYears1;
		
		var fdTenureMonths1 = document.getElementById('fdTenureMonths').value;
		document.getElementById('tenureInMonths').value = fdTenureMonths1;
		var fdTenureDays1 = document.getElementById('fdTenureDays').value;
		document.getElementById('tenureInDays').value=fdTenureDays1;
	if(fdTenureYears1=="" && fdTenureMonths1=="" && fdTenureDays1==""){
		alert("Please select tenure");
		return false;
	}
	}
  var isEMI=0;
 function updateEMI(obj){
	 debugger;
	 if(isEMI==0){
		 isEMI=1;
		
		 }
	 else{
		 isEMI=0;
		 
		 }
	 $('#hdnEMI').val(isEMI);
	 //alert(obj.val());
 }

	
	
	function validateForm(){
		var value=$('.fixedAmount:checked').val();
		if(value=="0"){
			$('#sanctionPercentage').show();
		}
		else{
			$('#defaultOverdraftPercentage').val('');
			$('#sanctionPercentage').hide();
		}
	}
</script>
	