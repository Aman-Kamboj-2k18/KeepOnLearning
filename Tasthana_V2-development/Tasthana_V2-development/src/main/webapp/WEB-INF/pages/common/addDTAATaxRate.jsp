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
<script
	src="<%=request.getContextPath()%>/resources/js/ccvalidate-tapan.min.js"></script>
	<link href="<%=request.getContextPath()%>/resources/css/datepicker.min.css"
	rel="stylesheet">

<div class="right-container" id="right-container">
	<div class="container-fluid">
		<div class="Flexi_deposit">

			<div class="header_customer">
				<h3>
					<spring:message code="label.addRate" />

				</h3>
			</div>
			<div class="Id">
				<div class="successMsg" style="text-align: center; color: red;">
					${Id}</div>
			</div>

			<div class="Success_msg">
				<div class="successMsg" style="text-align: center; color: red;">
					${error}</div>
			</div>
			<form:form class="form-horizontal" autocomplete="off"
					commandName="addCountryWiseTaxRateDTAAForm" action="addCountryWiseTaxRateDTAAPost" id="addCountryWiseTaxRateDTAAForm" 
					name="addCountryWiseTaxRateDTAAForm" >
			<div class="flexi_table" style="margin-top: 7px;">
							<input type="hidden" name="menuId" value="${menuId}" id="menuId"/>
				<!-- action="addRatePost" -->
				<div class="col-md-2"></div>
				<div class="col-md-6">
					<div class="form-group">
						<label class="col-md-6 control-label" style="padding-top: 16px;"><spring:message
								code="label.effectiveDate" /><span style="color: red">*</span></label>
						<div class="col-md-6" style="min-width: 185px !important; margin-left: -15px;">
							<form:input path="effectiveDate" id="effectiveDate"
								readonly="true" placeholder="Select Date"
								class="myform-control datepicker-here" />
						</div>
					</div>
					
					<div class="form-group">
					<label class="col-md-6 control-label" style="padding-top: 14px;"><spring:message
								code="label.country" /><span style="color: red">*</span></label>
					<div class="col-md-6" style="margin-left: -15px;">

						<form:select id="dtaaCountryId"
								path="dtaaCountryId" class="myform-control"
								required="true">
								<option value="">Select</option>
								<c:forEach var="item" items="${countryList}">
									<option value="${item.id}">${item.country}</option>	
<%-- 										<c:if test="${item eq selectedCountry}">selected</c:if>>${item}</option>													 --%>
								</c:forEach>

							</form:select>
					</div>
					</div>
					<div class="form-group">
						<label class="col-md-6 control-label" style="padding-top: 16px;"><spring:message
								code="label.rate" /><span style="color: red">*</span></label>
						<div class="col-md-6" style="min-width: 185px !important; margin-left: -15px;">
							<form:input path="taxRate" id="taxRate" onkeypress="return isNumberKey1(event,$(this))" type="text" class="myform-control" />
						</div>
					</div>
				
					
							<div style="text-align: center; margin-left: 125px;"><a href="viewDTAATaxRates?menuId=${menuId}" class="btn btn-success"><spring:message code="label.back" /></a> 
						<input type="submit" size="3"
							value="<spring:message code="label.save"/>"
							class="btn btn-primary"></div>
			</div></div>
			
			

		</form:form>

		</div>
	</div>

</div>

<script>
function isNumberKey1(evt,obj)
{
	debugger;
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
accountNo1_.onkeydown = function(event) {
	
	var taxRateValue = $("#taxRate").val();	 
		var keycode = event.which;
	    if (!(event.shiftKey == false && (keycode == 46 || keycode == 8 || keycode == 37 || (keycode >= 48 && keycode <= 57)
	    		|| (keycode >= 96 && keycode <= 105)))) {
	        event.preventDefault();
	    }

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
<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>


<script type="text/javascript">

$(document)
.ready(
		function() {
					
		}); 
          
     
function enterRate(id,index) {
	
	var rate = document.getElementById("rateList[" + id
			+ "]").value;
	var arrayId = [];
	
	var taskArray = new Array();
	$("input[name=rateList]").each(function() {
		taskArray.push($(this).val());
	});
	
	
}


</script>





<style>

.errmsg
{
color: red;
}
</style>
