<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- To fetch the request url -->
<c:set var="req" value="${requestScope['javax.servlet.forward.request_uri']}"/>      
<c:set var="baseURL" value="${fn:split(req,'/')}" />
<script>
	/* function val() {
		if (confirm("Are you sure... \n you want to make change") == true) {
			return true;
		} else {
			return false;
		}

	} */
</script>
<div class="Flexi_deposit">	
	<div class="header_customer">
		<h3 align="center">
			<spring:message code="label.fixedDeposit" />
		</h3>
	</div>
	<div class="col-sm-12 col-md-12 col-lg-12">
		<div class="errorMsg">${error}</div>
	</div>
	<div class="flexi_table">

	<form:form action="changePayOffs" method="post" name="fixedDeposit" commandName="fixedDepositForm"
		onsubmit="return val();">
		<table align="center" width="500">
		<form:hidden path="id"/>
		<form:hidden path="period"/>
		<form:hidden path="category"/>
		      <tr>
		
				<td><label><spring:message
							code="label.customerName" /></label></td>
				<td><form:input path="customerName"  class="myform-control"
						id="customerName" readonly="true"/></td>
			</tr>
		  <tr>
		
				<td><label><spring:message
							code="label.contactNumber" /></label></td>
				<td><form:input path="contactNum"  class="myform-control"
						id="contactNum" readonly="true"/></td>
			</tr>
		<tr>
		
				<td><label><spring:message
							code="label.email" /></label></td>
				<td><form:input path="email"  class="myform-control"
						id="email" readonly="true"/></td>
			</tr>
		    <tr>
		
				<td><label><spring:message
							code="label.customerId" /></label></td>
				<td><form:input path="customerID"  class="myform-control"
						id="customerID" readonly="true"/></td>
			</tr>
			
			<tr>
				<td><label><spring:message
							code="label.fdID" /></label></td>
				<td><form:input path="fdID" class="myform-control"
						id="fdID" readonly="true"/></td>
			</tr>			
			<tr>
				<td><label><spring:message
							code="label.accountBalance" /></label></td>
				<td><form:input path="accountBalance" class="myform-control"
						id="accountBalance" readonly="true"/></td>
			</tr>
			<tr>
				<td><label><spring:message code="label.accountDetails" /><span style="color: red">*</span></label></td>
				<td><form:input path="fdPayType" class="myform-control"
						id="fdPayType" readonly="true"/></td>
			</tr>
			<tr>
				<td><label><spring:message
							code="label.modeOfPay" /></label></td>
				<td><form:input path="fdPay" class="myform-control"
						id="fdPay" readonly="true"/></td>
			</tr>
			<tr>
				<td><label><spring:message
							code="label.selectFormat" /></label></td>
				<td><form:input path="paymentType" class="myform-control"
						id="paymentType" readonly="true"/></td>
			</tr>
			
			<tr>
				<td><label><spring:message
							code="label.selectDate" /></label></td>
				<td><form:input path="fdDueDate" class="myform-control"
						id="fdDueDate" readonly="true"/></td>
			</tr>
			<tr>
				<td><label><spring:message
							code="label.fdAmount" /></label></td>
				<td><form:input path="fdAmount"  class="myform-control"
						id="fdAmount" readonly="true"/>
					
						</td>
			</tr>
			<tr>
				<td><label>Deposit Fixed Amount</label></td>
				<td><form:input path="fdFixed"  class="myform-control"
						id="fdFixed" readonly="true"/>
					
						</td>
			</tr>
			<tr>
				<td><label>Deposit Variable Amount</label></td>
				<td><form:input path="fdChangeable"  class="myform-control"
						id="fdChangeable" readonly="true"/>
					
						</td>
			</tr>
			<tr>
				<td><label><spring:message
							code="label.typeOfTenure" /></label></td>
				<td>
					<form:input path="fdTenureType"  class="myform-control"
						id="fdTenureType" readonly="true"/>
					
						</td>

			</tr>
			<tr>
				<td><label><spring:message
							code="label.fdTenure" /></label></td>
				<td><form:input path="fdTenure"  class="myform-control"
						id="fdTenure" readonly="true"/>
						
						</td>
			</tr>
			<tr>
				<td><label><spring:message
							code="label.interestAmt" /></label></td>
				<td>
					<form:input path="fdInterest"  class="myform-control"
						id="fdInterest" value="${model.fdInterestAmount }" readonly="true"/>
					
						</td>

			</tr>
			<tr>
				<td><label><spring:message
							code="label.fdCurrentAmt" /></label></td>
				<td><form:input path="fdCurrentAmount"  class="myform-control"
						id="fdCurrentAmount" value="${model.fdCurrentAmount }" readonly="true"/>
						
						</td>
			</tr>
			<tr>
				<td><label><spring:message
							code="label.estimateInterest" /></label></td>
				<td><form:input path="estimateInterest"  class="myform-control"
						id="estimateInterest" readonly="true"/>
						
						</td>
			</tr>
			<tr>
				<td><label><spring:message
							code="label.estimateTDSDeduct" /></label></td>
				<td><form:input path="estimateTDSDeduct"  class="myform-control"
						id="estimateTDSDeduct" readonly="true"/>
						
						</td>
			</tr>
			<%-- <tr>
				<td><label><spring:message
							code="label.estimateSESDeduct" /></label></td>
				<td><form:input path="estimateSESDeduct"  class="myform-control"
						id="estimateSESDeduct" readonly="true"/>
						
						</td>
			</tr>
			<tr>
				<td><label><spring:message
							code="label.estimateServiceTax" /></label></td>
				<td><form:input path="estimateServiceTax"  class="myform-control"
						id="estimateServiceTax" readonly="true"/>
						
						</td>
			</tr> --%>
			<tr>
				<td><label><spring:message
							code="label.estimatePayOffAmount" /></label></td>
				<td><form:input path="estimatePayOffAmount"  class="myform-control"
						id="estimatePayOffAmount" readonly="true"/>
						
						</td>
			</tr>
			<tr>
				<td><b><spring:message
							code="label.fdTenureDate" /></b></td>
				<td><form:input path="fdTenureDate"  class="myform-control"
						id="fdTenureDate" readonly="true"/>
				</td>
			</tr>
			
		</table>
		
		<div class="col-sm-12 col-md-12 col-lg-12">
		<c:if test="${baseURL[1] == 'bnkEmp'}"><c:set var="back" value="editFD?id=${model.fixedDepositForm.id}"/></c:if>
					<c:if test="${baseURL[1] == 'users'}"><c:set var="back" value="editFD?id=${model.fixedDepositForm.id}"/></c:if>
		
			<table align="center" class="f_deposit_btn">
				<tr>
					<td><input type="submit" class="btn btn-primary"
						value="<spring:message code="label.save"/>"></td>
					<td><a href="${back}" class="btn btn-success"><spring:message code="label.back"/></a></td>
				</tr>

			</table>
		</div>
	</form:form>
	
	</div>
	</div>
<style>
	.myform-control{
		    background: #B5B5B3;
    color: #1B1B1B;
    cursor: no-drop;
	}
</style>