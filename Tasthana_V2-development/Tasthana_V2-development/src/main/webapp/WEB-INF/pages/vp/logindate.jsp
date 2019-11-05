<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- To fetch the request url -->
<c:set var="req"
	value="${requestScope['javax.servlet.forward.request_uri']}" />
<c:set var="baseURL" value="${fn:split(req,'/')}" />
<link href="<%=request.getContextPath()%>/resources/css/Validation.css"
	rel="stylesheet">
	<link href="<%=request.getContextPath()%>/resources/css/datepicker.min.css"
	rel="stylesheet">
<script src="<%=request.getContextPath()%>/resources/js/ccvalidate-tapan.min.js"></script>


<div class="right-container" id="right-container">
	<div class="container-fluid">



		<div class="Flexi_deposit">

			<div class="header_customer">
				<h3>
					<spring:message code="label.logindate" />

				</h3>
			</div>
			<div class="Success_msg">
				<div class="successMsg" style="text-align: center; color: red;">
					${error}</div>
			</div>
			<div class="flexi_table" style="margin-top: 7px;">
				<form:form action="logindateSave" class="form-horizontal" autocomplete="off"
					commandName="loginForm"  name="login" onsubmit="return val()">
				 
					<div class="col-md-6">
						<div class="form-group">
							<label class="col-md-6 control-label" style="padding-top: 16px;"><spring:message
									code="label.effectiveDate" /><span style="color: red">*</span></label>
							<div class="col-md-6">
								<form:input path="loginDate" id="loginDate"
									readonly="true" placeholder="Select Date"
									class="myform-control datepicker-here" />
							</div>
						</div>

					</div>
					<div class="col-md-12"  style="text-align: left;margin-left: 272px;">
					
				    <tr>
								<td><span id='validationError' style="color: red"></span></td>
					</tr>
					<tr>
					
						<td><input type="submit" size="3" style="margin-bottom: 17px;margin-left: 42px;"    
						value="<spring:message code="label.confirm"/>"
							class="btn btn-primary"></td>
					</tr>
			</div>
				</form:form>
				
				
			</div>
</div>

<script>
function val(){
	    var validationError=document.getElementById('validationError');
		validationError.innerHTML="";
		var errorMsg = "";
	   var submit = true;
	   var date = document.getElementById('loginDate').value;
	   if(date==null || date==''){
		document.getElementById('loginDate').style.borderColor = "red";
		errorMsg = "<br>Please insert the date.";
		validationError.innerHTML += errorMsg;
		submit = false;
	}
	   
	    var parts = date.split('/');
	    var mydate = new Date(parts[2], parts[1] - 1, parts[0]);
	    var userDate = mydate.getFullYear()+'/'+(mydate.getMonth()+1)+'/'+mydate.getDate(); 
	    
	  //  var today = getTodaysDate();
	    var today =  getLoginDate();
	   
	   // var date = today.getFullYear()+'/'+(today.getMonth()+1)+'/'+today.getDate();  
	    if (userDate<today) {
	    	document.getElementById('loginDate').style.borderColor = "red";
			errorMsg = "<br>Past Date Selected.";
			validationError.innerHTML += errorMsg;
			submit = false;
	    }
	return submit;
}


function getLoginDate()
{
	var today = null;

	$.ajax({  
	    type: "GET",  
	    async: false,
	    url: "<%=request.getContextPath()%>/vp/loginDateForJsp", 
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

</script>

<style type="text/css">
form {
	margin: 20px 0;
}

form input, button {
	padding: 5px;
}

table {
	width: 100%;
	margin-bottom: 20px;
	border-collapse: collapse;
}

table, th, td {
	border: 1px solid #cdcdcd;
}

table th, table td {
	padding: 10px;
	text-align: left;
}

input[type=checkbox], input[type=radio] {
	margin: 4px 1px 0px;
	margin-top: 1px\9;
	line-height: normal;
	zoom: 1.0;
}

.form-horizontal .control-label {
	padding-top: 0;
}
</style>
<!-- <script src="https://code.jquery.com/jquery-1.12.4.min.js"></script> -->
