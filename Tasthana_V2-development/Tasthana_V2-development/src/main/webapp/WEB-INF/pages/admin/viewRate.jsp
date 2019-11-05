<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%-- <script src="<%=request.getContextPath()%>/resources/js/loginDate.js"></script> --%>
<script>
function citizenType(value){
	if(value=='RI')
	  document.getElementById('nriAccountTypeDiv').style.display = 'none';
	else
	  document.getElementById('nriAccountTypeDiv').style.display = 'block';
}

$(document)
.ready(
		function() {
			var currency = '${currency}';
			var depositClassification = '${depositClassification}';
			var depositType = '${depositType}';
			var citizenType = '${citizenType}';
			var nriAccoType = '${nriAccountType}';
			document.getElementById('nriAccountType').value = nriAccoType;
			document.getElementById('currency').value = currency;
			document.getElementById('depositClassification').value= depositClassification;
			document.getElementById('citizen').value = citizenType;
		});




function populateRate(){
    	var customerCategory=document.getElementById('setCategory').value;
    	var currency= document.getElementById('currency').value;
    	var depositClassification = document.getElementById('depositClassification').value;
    	var amountSlabFrom = document.getElementById('amountSlabFrom').value;
    	var amountSlabTo = document.getElementById('amountSlabTo').value;
    	var citizen  = document.getElementById('citizen').value;
    	var nriAccountType  = document.getElementById('nriAccountType').value;
    debugger;
    if(amountSlabFrom=="" || amountSlabTo ==""){
    	$("#lblMsg").html("Please select or enter amount slabs.")-show();
    	return false;
    }else{
    	$("#lblMsg").hide();
    }
    	//document.getElementById('amountSlabTo').value == "";
    	 $('#amountSlabTo').empty();
    	
            $("#ratesForm").attr("action", "getRateListByCategory_Currency?customerCategory="+customerCategory+"&currency="+currency+"&depositClassification="+depositClassification+"&amountSlabFrom="+amountSlabFrom+"&amountSlabTo="+amountSlabTo+"&citizen="+citizen+"&nriAccountType="+nriAccountType); 
            $("#ratesForm").submit(); 
}

function populateFromSlab()
{

	var citizen = document.getElementById('citizen').value;
	
	if(citizen==""){
		$("#lblMsg").html("Please select citizen *").show();
		return false;
	}
	var customerCategory=document.getElementById('setCategory').value;
	if(customerCategory==""){
		$("#lblMsg").html("Please select customer category *").show();
		return false;
	}
	var currency= document.getElementById('currency').value;
	if(currency==""){
		$("#lblMsg").html("Please select currency *").show();
		return false;
	}
	var depositClassification = document.getElementById('depositClassification').value;
	if(depositClassification==""){
		$("#lblMsg").html("Please select deposit type *").show();
		return false;
	}
	var nriAccountType  = document.getElementById('nriAccountType').value;
	if(nriAccountType=="" && citizen=="NRI"){
		$("#lblMsg").html("Please select NRI Account Type *").show();
		return false;
	}
	$.ajax({  
		async: false,
		type: "GET",  
	    url: "<%=request.getContextPath()%>/bnkEmp/getAmountFromSlab", 
	    contentType: "application/json",
	    dataType: "json",
	    data: "customerCategory="+customerCategory+"&currency="+currency+"&depositClassification="+depositClassification+"&citizen="+citizen+"&nriAccountType="+nriAccountType,
   
	    success: function(response){  
	    	$("#lblMsg").hide();
	    	$('#amountSlabFrom').html('');
	  		document.getElementById('amountSlabTo').value =null;
    		
        // Check result isnt empt
        if(response != '')
        {
        	var len = response.length;

        	 
            $('#amountSlabFrom').empty();
            $('#amountSlabFrom').append("<option value='Select'>Select</option>");
        	 for( var i = 0; i<len; i++){
                 var val = response[i];
            
                 $('#amountSlabFrom').append("<option value='"+val+"'>"+val+"</option>");
                

             }
        }
	        
    },
    error: function(e){  
    	 $('#error').html("Error occured!!")
    	 $("#lblMsg").html("error").show();
    	 confirmation = false;
    	//return false;
    }  
  });  
}

/**
 *   populateToSlab 
 */
 
function populateToSlab()
{
	var customerCategory=document.getElementById('setCategory').value;
	var currency= document.getElementById('currency').value;
	var depositClassification = document.getElementById('depositClassification').value;
	 var amountSlabFrom = document.getElementById('amountSlabFrom').value;
	
	$.ajax({  
		async: false,
		type: "GET",  
	    url: "<%=request.getContextPath()%>/bnkEmp/getAmountToSlab", 
	    contentType: "application/json",
	    dataType: "json",
	    data: "customerCategory="+customerCategory+"&currency="+currency+"&depositClassification="+depositClassification+"&amountSlabFrom="+amountSlabFrom,
   
	    success: function(response){  	  
	    	document.getElementById('amountSlabTo').value = response;
	        
    },
    error: function(e){  
    	 $('#error').html("Error occured!!")
    	 alert("error");
    	 confirmation = false;
    	//return false;
    }  
  });  

}

function accountTypeVal(value){
	

	var FCNR=["USD","GBP","EUR","JPY","CAD","AUD","SGD","HKD"];
	var RFC=["USD","GBP","EUR","JPY","CAD","AUD"];
	
	   document.getElementById('amountSlabTo').value == "";
  	 $('#amountSlabTo').empty();
	 	 $("#ratesForm").attr("action", "viewRate?nriAccType=" + value);
	
	     //$("#ratesForm").submit();
	      $('#currency option').hide();
	     $('#currency option').each(function(){
	    	 
	    	 if(value=="FCNR"  || value=="PRP"){
	    		var length=$.inArray( $(this).val(), FCNR );
	    		if(length>-1)$(this).show();
	    	 }else if(value=="RFC"){
	    		 var length=$.inArray( $(this).val(), RFC );
	    		 if(length>-1)$(this).show();
	    	 }
	    	 else{
	    		 $('#currency option').show();
	    	 }
	    	
	     }); 
	}
</script>

<div class="right-container" id="right-container">
	<div class="container-fluid">

		<div class="Flexi_deposit">
		<div class="list-of-rates" style="border: none; box-shadow: none;">
			<div class="header_customer">
				<h3>
					<spring:message code="label.listOfRates" /> 
				</h3>
			</div>

			<div class="list-of-rates-table" style="box-shadow: none;">
				<div class="search_filter">
					<span class="counter pull-right"></span>

					<div>
					<div class="col-md-4">
						<form:form commandName="ratesForm" id="ratesForm" name="viewForm" action="getRateByCategory">
						
							
							<label class="col-md-3 control-label"><spring:message
									code="label.citizen" /><span style="color: red">*</span></label>
<!--  onchange="citizenType(this.value)" -->

							<div class="col-md-9" style="margin-top:-15px;">
								<form:select id="citizen" path="citizen"  onchange="citizenType(this.value)"
									class="myform-control">
									
									<form:option value="">
										<spring:message code="label.select" />
									</form:option>
									<form:option value="RI">
										<spring:message code="label.ri" />
									</form:option>
									<form:option value="NRI">
										<spring:message code="label.nri" />
									</form:option>
									
								</form:select>
							</div>
						
							</div>
							<div class="col-md-4">
						
								<label class="col-md-5 control-label" style="margin-left: 0px; margin-top: -12px; text-align: left;"><spring:message
								code="label.customerCategory" /><span style="color: red">*</span></label>
								<c:choose>
									<c:when test="${setCategory!=null}">
										<div class="col-md-7" style="margin-left: -12px; margin-top: -22px; ">

											<form:select id="setCategory" path="type"
												class="myform-control" required="true">
<!-- 												onchange="changeCategory(this.value);"> -->
												<!-- <option value="select">Select</option> -->
												<c:forEach var="item" items="${setCategory}">

													<option value="${item.customerCategory}"
														<c:if test="${item.customerCategory eq selectedCategory}">selected</c:if>>${item.customerCategory}</option>
												</c:forEach>
											</form:select>


										</div>
									</c:when>
									<c:otherwise>
										<div class="col-md-4" style="margin-left: -65px; text-align: left;">
											<form:select id="setCategory" path="type" class="myform-control">
												<option value="select"><spring:message code="label.select" /></option>

											</form:select>
										</div>
									</c:otherwise>
								</c:choose>
							</div>	
							
							
							
							<div class="col-md-4" id="nriAccountTypeDiv">
						
								<label class="col-md-5 control-label" style="margin-left: 0px; text-align: left; margin-top: -12px;"><spring:message
								code="label.accountType" /><span style="color: red">*</span></label>
								<div class="col-md-7" style="margin-left: -12px; margin-top: -22px; ">
								<form:select id="nriAccountType" path="nriAccountType"
									class="myform-control" onchange="accountTypeVal(this.value)"> 
								    <form:option value="">
										<spring:message code="label.select" />
									</form:option>
									<form:option value="NRE">
										<spring:message code="label.nre" />
									</form:option>
									<form:option value="NRO">
										<spring:message code="label.nro" />
									</form:option>
									<form:option value="FCNR">
										<spring:message code="label.fcnr" />
									</form:option>
									<form:option value="RFC">
										<spring:message code="label.rfc" />
									</form:option>
									<form:option value="PRP">
										<spring:message code="label.prp" />
									</form:option>
								</form:select>
								</div>
							</div>	
					
					<div style="clear:both; height: 20px;"></div>
					
					<div class="form-group col-md-4">
						<label class="col-md-3 control-label" style="margin-left: -15px;"><spring:message
							code="label.currency" /><span style="color: red">*</span></label>

						<div class="col-md-9" style="margin-top:-10px; margin-left: 15px;">
							<form:select id="currency" path="currency"
								class="input myform-control">
								<form:option value="Select"></form:option>
							</form:select>

							<script>
							
							//window.alert("Hello");
// 									if('${currency}'!=""){
// 									//populateCurrencyEdit("currency","${bankConfiguration.currency}");
// 									populateCurrencyEdit("currency","${currency}");
// 									}
// 									else{
// 										populateCurrency("currency");
// 									}

								
									if ('${currency}' != "") {
										if ('${nriAccountType}' == 'FCNR'
												|| '${nriAccountType}' == 'PRP') {

											populateCurrencyEditFCNR(
													"currency", "${currency}");
										} else if ('${nriAccountType}' == 'RFC')
											populateCurrencyEditRFC("currency",
													"${currency}");
										else
											populateCurrencyEdit("currency",
													"${currency}");
									} else {
										if ('${nriAccountType}' == 'FCNR'
												|| '${nriAccountType}' == 'PRP')
											populateCurrencyFCNR("currency");
										else if ('${nriAccountType}' == 'RFC')
											populateCurrencyRFC("currency");
										else
											populateCurrency("currency");
									}

								
							</script>
							<span id="currencyError" style="display: none; color: red"><spring:message
									code="label.selectCurrency" /></span>

						</div>
					
					
					</div>
					
					
					
					<div class="col-md-4">
					<label class="col-md-3 control-label"><spring:message
								code="label.depositType" /><span style="color: red">*</span></label>


						<div class="col-md-8" style="margin-left: 15px;">
							<form:select id="depositClassification"
								path="depositClassification" class="myform-control"
								required="true">
								
								<c:forEach var="item" items="${depositClassificationList}">

													<option value="${item}"
														<c:if test="${item eq selectedDepositClassification}">selected</c:if>>${item}</option>
														
								</c:forEach>
	


							</form:select>
						</div></div>
						
						<div class="col-md-4" style="margin: 7px 0 auto -7px;">
					<input type="button" class="btn btn-warning" onclick="populateFromSlab()"
								value="Get Slabs" style="width: 100px;"> 
					
					</div>
					</div>
					<div style="clear: both; margin-left: -36px; margin-bottom: -5px;" class="col-md-12">
					<h4>Amount Slab</h4></div>
					
					<div style="clear: both; width: 455px; border-top: 1px dotted #cfcfcf; margin-left:25px;"></div>
					
						
						<div class="form-group" style="margin-left: 0;">
						<div class="form-group col-md-4">
						<label class="col-md-6 control-label" style="padding-top: 16px;"><spring:message
								code="label.from" /><span style="color: red">*</span></label>
						<div class="col-md-6" style="min-width: 150px !important; margin-left: -75px;"">
<%-- 							<form:input path="amountSlabFrom" type="number" --%>
<%-- 								id="amountSlabFrom" placeholder="Enter Amount" --%>
<%-- 								class="myform-control" required="true" /> --%>
										<form:select id="amountSlabFrom" path="amountSlabFrom"
														class="myform-control" required="true"  onchange="populateToSlab()">
														<option value="select">Select</option>
														<c:forEach var="item" items="${amountFromSlablist}">

															<option value="${item}">${item}</option>
															<c:if test="${item eq selectedFromSlab}">selected</c:if>>${item}</option>
														</c:forEach>
													</form:select>



						</div></div>

						<div class="form-group col-md-4">
						<label class="col-md-6 control-label" style="padding-top: 16px; margin-left:-85px;"><spring:message
								code="label.to" /></label>
						<div class="col-md-6" style="min-width: 150px !important; margin-left: -105px;">
							<form:input path="amountSlabTo" type="text" id="amountSlabTo"
								placeholder="Enter Amount" class="myform-control"
								required="true" />

						</div></div>

						<div class="col-md-2" style="margin-left: -9px;">
						<input type="button" class="btn btn-warning" onclick="populateRate()"
								value="GO" style="width: 100px;"> 
					    </div>
					
					</div>
							

							<div class="col-md-12">
								<div class="form-group col-md-6"
									style="margin-left: -42px; margin-top: 20px; color: #d70000; font-size: 1.15em; margin-bottom: -25px;">
									<label class="col-md-4 control-label"><spring:message
											code="label.logindate" />:</label>
									<div class="col-md-4" style="text-align: left; margin-left: -60px;">
										<fmt:formatDate
												pattern="dd/MM/yyyy" value="${loginDate}" />


									</div>
								</div>
<div class="form-group col-md-6"
									style="margin-left: -60px; margin-top: 20px; color: #d70000; font-size: 1.15em; margin-bottom: -25px;">
									<label id="lblMsg" style="color:red;font-weight:bold;display:none;" class="col-md-12 control-label">
Error Msg</label>
									
								</div>
							</div>

						</form:form>
					</div>
				</div>
				<table class="table table-bordered example" align="center" id="my-table" style="width: 94%;">
					<thead>
						<tr>
							<th><spring:message code="label.tenors" /></th>
							<th><spring:message code="label.genCategory" /></th>
							<th><spring:message code="label.fdRates" /> %</th>
							<th><spring:message code="label.effectiveDate" /></th>

						</tr>
					</thead>
					<tbody>

						<c:if test="${! empty rateList}">
							<c:forEach items="${rateList}" var="rates">

								<c:set var="toDay" value=""></c:set>
								<c:if test="${! empty rates.calMaturityPeriodFromInDays}">
									<c:set var="toD" value="${rates.calMaturityPeriodFromInDays }" />
									<c:if test="${toD ge 1}">
										<c:set var="toDay" value="${toD} days" />
									</c:if>
									<c:if test="${toD eq 1}">
										<c:set var="toDay" value="${toD} day" />
									</c:if>
								</c:if>


								<c:set var="toDays" value=""></c:set>
								<c:if test="${! empty rates.calMaturityPeriodToInDays}">
									<c:set var="toD" value="${rates.calMaturityPeriodToInDays }" />
									<c:if test="${toD ge 1}">
										<c:set var="toDays" value="${toD} days" />
									</c:if>
									<c:if test="${toD eq 1}">
										<c:set var="toDays" value="${toD} day" />
									</c:if>
								</c:if>

								<tr>
									<td><b><c:out value="${toDay} to ${toDays}"></c:out></b></td>

									<td><c:out value="${rates.category}"></c:out>
									<td><c:out value="${rates.interestRate}"></c:out></td>
									<td><fmt:formatDate pattern="dd/MM/yyyy"
											value="${rates.effectiveDate}" /></td>
								</tr>
							</c:forEach>
						</c:if>

					</tbody>
				</table>
			</div>
		</div>
	</div></div>
	<style>
.search_filter {
	display: flow-root;
	margin-bottom: 15px;
}
</style>
