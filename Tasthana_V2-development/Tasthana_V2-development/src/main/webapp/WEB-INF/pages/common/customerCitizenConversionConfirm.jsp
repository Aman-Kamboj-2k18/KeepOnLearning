<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="req"
	value="${requestScope['javax.servlet.forward.request_uri']}" />
<c:set var="baseURL" value="${fn:split(req,'/')}" />
<div class="right-container" id="right-container">
	

		<div class="customer-citizen-confirm">
			<div class="header_customer" style="margin-bottom: 20px;">
				<h3>
					Customer Citizen COnversion
				</h3>
			</div>
			<form:form id="customerCitizenConversionForm" action="customerCitizenConversionSave" class="form-horizontal" method="post"
				commandName="customer" >
				
				
				<div class="customer-citizen-conversion-confirm-table">
                     <form:hidden path="contactNum" />
                     <form:hidden path="customerID" />
					<form:hidden path="email" />
					<form:hidden path="category" />
					<form:hidden path="organisationId" />
					<form:hidden path="organisationName" />
					<form:hidden path="gender" />
					<form:hidden path="age" />
					<form:hidden path="nomineeName" />
					<form:hidden path="nomineeAge" />
					<form:hidden path="nomineeAddress" />
					<form:hidden path="nomineeRelationShip" />
					<form:hidden path="guardianName" />
					<form:hidden path="guardianAge" />
					<form:hidden path="guardianAddress" />
					<form:hidden path="guardianRelationShip" />
					<form:hidden path="guardianAadhar" />
					<form:hidden path="guardianPan" />
					<form:hidden path="address" />
					<form:hidden path="city" />
					<form:hidden path="pincode" />
					<form:hidden path="country" />
					<form:hidden path="state" />
					<form:hidden path="altContactNum" />
					<form:hidden path="altEmail" />
					<form:hidden path="uniqueKey" />
					<form:hidden path="comment" />
					<form:hidden path="status" />
					<form:hidden path="approveDate" />
					<form:hidden path="password" />
					<form:hidden path="transactionId" />
					<form:hidden path="notificationStatus" />
					<form:hidden path="userName" />
					<form:hidden path="pan" />
					<form:hidden path="aadhar" />
					<form:hidden path="createdOn" />
					<form:hidden path="createdBy" />

					<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.customerName" /></label>
						<div class="col-md-5">
							<form:input path="customerName" class="myform-control"
							value="${customer.customerName}"	readonly="true"></form:input>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.customerId" /></label>
						<div class="col-md-5">
							<form:input path="id" class="myform-control"
								value ="${customer.id}" readonly="true"></form:input>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label">Date Of Birth</label>
						<div class="col-md-5">
						<fmt:formatDate pattern="dd/MM/yyyy"
									value="${customer.dateOfBirth}"  var="theFormattedDate"/>
							<form:input path="dateOfBirth" class="myform-control"
							value="${theFormattedDate}"	readonly="true"></form:input>
						</div>
					</div>
					<div class="form-group" id="citizenDiv">
						<label class="col-md-4 control-label">Citizen</label>
						<div class="col-md-5">
							<form:input path="citizen" class="myform-control" readonly="true"
						value="${customer.citizen}"		id="citizen" ></form:input>
						</div>
					</div>
					
					<c:if test="${customer.citizen == 'NRI'}">
					<div class="form-group">
						<label class="col-md-4 control-label">NRI Account Type</label>
						<div class="col-md-5">
							<form:input path="nriAccountType" class="myform-control" readonly="true"
						value="${customer.nriAccountType}"	id="nriAccountType"></form:input>
						</div>
					</div>
					
					</c:if>
					
					<div class="form-group" id="ripDiv">
								<label class="col-md-4 control-label">Citizen Convert To<span style="color: red">*</span></label>
								<div class="col-md-8" style="margin-top:10px;">
              
              						<c:if test="${customer.citizen == 'NRI'}">             						
										<label for="radio" id="radioButtonRi"> <form:radiobutton
												path="citizen" id="RIRadio" name="citizen"
												onclick="citizenFun()" value="RI" checked="true"></form:radiobutton></label>
										<spring:message code="label.ri" />
									</c:if>
									<c:if test="${customer.citizen == 'RI'}">
										<label for="radio"> <form:radiobutton
												onclick="citizenFun()" path="citizen" id="NRIRadio"
												name="citizen" value="NRI"></form:radiobutton></label>
										<spring:message code="label.nri" />
									</c:if>
								</div>
							</div>


							<div class="form-group" style="display: none;" clear:    
							both;" id="accountTypeDIV">
								<label class="col-md-4 control-label"><spring:message
										code="label.nriAccountType" /><span style="color: red">*</span></label>

								<div class="col-md-5">
									<form:select id="nriAccountType" path="nriAccountType"
										class="form-control"
										onchange="accountTypeVal(this.value)">
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

					<span id="radioError" class="error" style="display: none;"><font
								color="red"><spring:message code="label.radioError" /></font></span>
					
					
					
					<div class="form-group">
						<div class="col-md-offset-4 col-md-8">
							<a href="customerCitizenConversion?menuId=96"
								class="btn btn-success"><spring:message code="label.back" /></a> <input type="button"
								value="<spring:message code="label.confirm"/>"
								class="btn btn-primary"  onclick="return submitForm()"/> 
						</div>
					</div>



				</div>



			</form:form>
			</div>
			<script>
			function citizenFun(){
				
				var NRIRadioChecked = document.getElementById("NRIRadio").checked;
				if(NRIRadioChecked== true){
					 document.getElementById('radioError').style.display = 'none';
					 document.getElementById('accountTypeDIV').style.display = 'block'; 
				}
				else{
					 document.getElementById('radioError').style.display = 'none';
					 document.getElementById('accountTypeDIV').style.display = 'none'; 
				}
			}
			
			function submitForm(){
				debugger;
				var customerId = '${customer.id}';
				var citizen = document.getElementById("citizen").value;
				if(citizen == "RI"){
					var depositForcefullyClosed = confirm("Are you sure to close the deposit?");
					
				}else{
					var depositForcefullyClosed = true;
					alert("closed deposit for this customer!");
					
				}
				if(depositForcefullyClosed == true)
					{
						$("#customerCitizenConversionForm").attr("action", "customerCitizenConversionSave?depositForcefullyClosed="+depositForcefullyClosed);
				          $("#customerCitizenConversionForm").submit();
					}
				 
			}
				
			</script>
		
		<style>
.myform-control {
	background: #AFAFAF;
	color: black;
	cursor: no-drop;
}
</style>