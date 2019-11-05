<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%-- <script src="<%=request.getContextPath()%>/resources/js/loginDate.js"></script> --%>
<script>

function populateRate(){
        
    	var customerCategory=document.getElementById('setCategory').value;
    	var currency= document.getElementById('currency').value;
    	var depositClassification = document.getElementById('depositClassification').value;
    	var amountSlabFrom = document.getElementById('amountSlabFrom').value;
    	var amountSlabTo = document.getElementById('amountSlabTo').value;
    	document.getElementById('amountSlabTo').value == "";
    	 $('#amountSlabTo').empty();
            $("#ratesForm").attr("action", "getRateListByCategory_Currency?customerCategory="+customerCategory+"&currency="+currency+"&depositClassification="+depositClassification+"&amountSlabFrom="+amountSlabFrom+"&amountSlabTo="+amountSlabTo); 
            $("#ratesForm").submit(); 
}

function populateFromSlab()
{

	var customerCategory=document.getElementById('setCategory').value;
	var currency= document.getElementById('currency').value;
	var depositClassification = document.getElementById('depositClassification').value;
	$.ajax({  
		async: false,
		type: "GET",  
	    url: "<%=request.getContextPath()%>/bnkEmp/getAmountFromSlab", 
	    contentType: "application/json",
	    dataType: "json",
	    data: "customerCategory="+customerCategory+"&currency="+currency+"&depositClassification="+depositClassification,
   
	    success: function(response){  	
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
    	 alert("error");
    	 confirmation = false;
    	//return false;
    }  
  });  
}

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

					<div class="pull-left">
					<div class="col-md-3">
						<form:form commandName="ratesForm" id="ratesForm" name="viewForm" action="getRateByCategory">
							
							<label class="col-md-8 control-label" style="margin-left: -30px; text-align: left;"><spring:message
								code="label.customerCategory" /><span style="color: red">*</span></label>
								<c:choose>
									<c:when test="${setCategory!=null}">
										<div class="col-md-8" style="margin-left: 72px; margin-top: -50px; text-align: left; width: 170px;">

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
					<div class="form-group col-md-3">
						<label class="col-md-4 control-label" style="margin-left: -15px;"><spring:message
							code="label.currency" /><span style="color: red">*</span></label>

						<div class="col-md-8" style="margin-left: 15px; margin-top: -10px; min-width: 125px;">
							<form:select id="currency" path="currency"
								class="input myform-control">
								<form:option value="Select"></form:option>
							</form:select>

							<script>
									if('${currency}'!=""){
									//populateCurrencyEdit("currency","${bankConfiguration.currency}");
									populateCurrencyEdit("currency","${currency}");
									}
									else{
										populateCurrency("currency");
									}
									</script>
							<span id="currencyError" style="display: none; color: red"><spring:message
									code="label.selectCurrency" /></span>

						</div>
					
					
					</div>
					<div class="col-md-3">
					<label class="col-md-6 control-label" style="margin-left: -35px;"><spring:message
								code="label.depositType" /><span style="color: red">*</span></label>


						<div class="col-md-6" style="margin-left: 35px; margin-top: -50px; min-width: 215px;">
							<form:select id="depositClassification"
								path="depositClassification" class="myform-control"
								required="true">
								
								<c:forEach var="item" items="${depositClassificationList}">

													<option value="${item}"
														<c:if test="${item eq selectedDepositClassification}">selected</c:if>>${item}</option>
														
								</c:forEach>
	
<%-- 								<form:option value="Regular Deposit" selected="true"> --%>
<%-- 									<spring:message code="label.fixedDeposit" /> --%>
<%-- 								</form:option> --%>
<%-- 								<form:option value="Recurring Deposit"> --%>
<%-- 									<spring:message code="label.recurringDeposit" /> --%>
<%-- 								</form:option> --%>
<%-- 								<form:option value="Tax Saving Deposit"> --%>
<%-- 									<spring:message code="label.taxSavingDeposit" /> --%>
<%-- 								</form:option> --%>
<%-- 								<form:option value="Annuity Deposit"> --%>
<%-- 									<spring:message code="label.annuityDeposit" /> --%>
<%-- 								</form:option> --%>

							</form:select>
						</div></div>
						
						<div class="col-md-3" style="margin: -5px 0 auto -7px;">
					<input type="button" class="btn btn-warning" onclick="populateFromSlab()"
								value="Get Slabs" style="width: 100px;"> 
					
					</div>
					</div>
					<div style="clear: both; margin-left: 75px; margin-bottom: -5px;" class="col-md-12">
					<h4>Amount Slab</h4></div>
					
					<div style="clear: both; width: 455px; border-top: 1px dotted #cfcfcf; margin-left:125px;"></div>
					
						
						<div class="form-group" style="margin-left: 125px;">
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
							<form:input path="amountSlabTo" type="number" id="amountSlabTo"
								placeholder="Enter Amount" class="myform-control"
								required="true" />

						</div></div>

						<div class="col-md-2" style="margin-left: -65px; margin-top: -15px;">
						<input type="button" class="btn btn-warning" onclick="populateRate()"
								value="GO" style="width: 100px;"> 
					    </div>
					
					</div>
							

							<div class="col-md-12">
								<div class="form-group col-md-8"
									style="margin-left: -42px; margin-top: 20px; color: #d70000; font-size: 1.15em; margin-bottom: -25px;">
									<label class="col-md-4 control-label"><spring:message
											code="label.logindate" />:</label>
									<div class="col-md-4" style="text-align: left; margin-left: -100px;">
										<fmt:formatDate
												pattern="dd/MM/yyyy" value="${loginDate}" />


									</div>
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
