<%@include file="taglib_includes.jsp"%>
<div class="right-container" id="right-container">
        <div class="container-fluid">
   
<div class="set-rate">
			<div class="header_customer">
				<h3><%-- <spring:message code="label.confirmAddedRate"/> --%>Confirm Customer Configuration </h3>
			</div>
	<form:form action="savecustomerConfiguration" name="rate" method="post"
		commandName="ratesForm" >
			
				<div class="set-rate-table">
			<div class="successMsg"><b><font color="red">${success}</font></b></div>
			
			<div style="clear:both; padding: 25px 15px 15px;"></div>
			
			<div class="form-group" style="clear:both; padding-bottom: 25px;">
				<label class="col-md-4 control-label" style="text-align: right;"><spring:message code="label.citizen"/><span style="color:red">*</span></label>
				<div class="col-md-6">
					<form:input path="citizen" id="citizen" class="form-control" required="true" readonly="true"></form:input>
				</div>
				
			</div>
			
			<c:if test="${ratesForm.citizen!='RI'}">
			<div class="form-group" style="clear:both; padding-bottom: 25px;">
				<label class="col-md-4 control-label" style="text-align: right;"><spring:message code="label.nriAccountType"/> %<span style="color:red">*</span></label>
				<div class="col-md-6">
					<form:input path="nriAccountType" id="nriAccountType" class="form-control" required="true" readonly="true"></form:input>
				</div>
				
			</div>
			</c:if>	
			<div class="form-group" style="clear:both; padding-bottom: 25px;">
				<label class="col-md-4 control-label" style="text-align: right;"><spring:message code="label.customerCategory"/> %<span style="color:red">*</span></label>
				<div class="col-md-6">
					<form:input path="type" id="setCategory" class="form-control" required="true" readonly="true"></form:input>
				</div>
				
			</div>
			
			
			<c:if test="${ratesForm.nriAccountType ne 'NRE' && ratesForm.nriAccountType ne 'FCNR' && ratesForm.nriAccountType ne 'PRP'}">
			<div class="form-group" style="clear:both; padding-bottom: 25px;">
				<label class="col-md-4 control-label" style="text-align: right;"><spring:message code="label.tds"/> %<span style="color:red">*</span></label>
				<div class="col-md-6">
					<form:input path="tds" id="tds" class="form-control" required="true" type="number" min="0" max="99" readonly="true"></form:input>
				</div>
				
			</div>
			</c:if>
			<div class="form-group" style="clear:both; padding-bottom: 25px;">
				<label class="col-md-4 control-label" style="text-align: right;">Deposit Fixed Percentage(%)<span style="color:red">*</span></label>
				<div class="col-md-6">
					<form:input path="fdFixedPercent" id="deposit" class="form-control" type="number" min="0" max="99" required="true" onchange="onChangeFixPercent('deposit','fdVariablePercent')" readonly="true"></form:input>
				</div>
				
			</div>
			
			<c:if test="${ratesForm.nriAccountType ne 'NRE' && ratesForm.nriAccountType ne 'FCNR' && ratesForm.nriAccountType ne 'PRP'}">
			<div class="form-group" style="clear:both; padding-bottom: 25px;">
							<label class="col-md-4 control-label" style="text-align: right; padding-top: 7px;"><spring:message
									code="label.minInterestForTDS" /><span style="color: red">*</span></label>
							<div class="col-md-6">
								<form:input id="minIntAmtForTDSDeduction"
									path="minIntAmtForTDSDeduction" class="form-control"
									required="true" type="number" step=".01" readonly="true"></form:input>
							</div>
			</div>
		  </c:if>	
						
			<div class="form-group" style="clear:both; padding-bottom: 45px;">
				<label class="col-md-4 control-label" style="text-align: right;">Deposit Variable Percentage(%)</label>
				<div class="col-md-6">
					<input type="text" id="fdVariablePercent" required="true" class="form-control" readonly />
				</div>  
				
			</div>
			<div class="form-group" style="clear:both; padding-bottom: 45px;">
				
				<label class="col-md-4 control-label"></label>
				<div class="col-md-6">
					<a href="customerConfiguration?menuId=${menuId}" class="btn btn-success"><spring:message code="label.back"/></a>
					<input type="submit" size="3"  value="<spring:message code="label.confirm"/>" class="btn btn-primary">
					
				</div>
				
			</div>
				

		</div>


	</form:form>
</div>

<script>

	$(document).ready(function() {
		
		var fixPercent=parseFloat(document.getElementById('deposit').value) ;
		var variablePercent=100-fixPercent;
		document.getElementById('fdVariablePercent').value = variablePercent ;
		
	});
	

	
</script>