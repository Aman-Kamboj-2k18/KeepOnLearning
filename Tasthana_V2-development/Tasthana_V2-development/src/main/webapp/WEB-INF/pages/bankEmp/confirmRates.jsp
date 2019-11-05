<%@include file="taglib_includes.jsp"%>
<div class="right-container" id="right-container">
        <div class="container-fluid">
			
<div class="set-rate">
			<div class="header_customer">
				<h3><spring:message code="label.setRate"/></h3>
			</div>
	<form:form action="saveRate" name="rate" method="post" class="form-horizontal" commandName="ratesForm" onsubmit="return validateForm()">
			
				<div class="set-rate-table">

					<div class="form-group">
						<label class="col-md-4 control-label"><spring:message code="label.category"/></label>
						<div class="col-md-6">
							<form:input path="type"  class="myform-control" readonly="true"></form:input>
						</div>
					</div>
					
					<div class="form-group">
							<label class="col-md-4 control-label"><spring:message code="label.tds"/></label>
							<div class="col-md-6">
								<form:input path="tds" id="rate" class="myform-control" readonly="true"></form:input>
							</div>
						</div>					
					
					<div class="form-group">
						<label class="col-md-4 control-label">Deposit Fixed Percentage(%)</label>
						<div class="col-md-6">
							<form:input path="fdFixedPercent"  class="myform-control" readonly="true"></form:input>
						</div>
					</div>
					<form:hidden path="transactionId"/>
					<div class="form-group">
						
						<div class="col-md-offset-4 col-md-8">
							<a href="updateRates" class="btn btn-success"><spring:message code="label.back"/></a>
							<input type="submit" size="3" value="<spring:message code="label.save"/>" class="btn btn-primary">
							
						</div>
					</div>
				
				</div>


	</form:form>
</div>
