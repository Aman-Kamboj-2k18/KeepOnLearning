<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- To fetch the request url -->
<c:set var="req" value="${requestScope['javax.servlet.forward.request_uri']}"/>      
<c:set var="baseURL" value="${fn:split(req,'/')}" />

<div class="Flexi_deposit">
	
	<div class="header_customer">
		<h3 align="center">
			<spring:message code="label.deathCerticate" />
		</h3>
	</div>

<div class="flexi_table">
	<form:form action="updateDeathDetails" method="post" name="fixedDeposit" commandName="fixedDepositForm"
		onsubmit="return val();">
		<table align="center">
		<form:hidden path="id" />
	    
	        <tr>
		
				<td class="col-sm-4"><label><spring:message
							code="label.customerName" /></label></td>
				<td class="col-sm-8"><form:input path="customerName"  class="myform-control"
						id="customerName" readonly="true"/></td>
			</tr>
		  <tr>
		
				<td class="col-sm-4"><label><spring:message
							code="label.contactNumber" /></label></td>
				<td class="col-sm-8"><form:input path="contactNum"  class="myform-control"
						id="contactNum" readonly="true"/></td>
			</tr>
		<tr>
		
				<td class="col-sm-4"><label><spring:message
							code="label.email" /></label></td>
				<td class="col-sm-8"><form:input path="email"  class="myform-control"
						id="email" readonly="true"/></td>
			</tr>
		    <tr>
		
				<td class="col-sm-4"><label><spring:message
							code="label.customerId" /></label></td>
				<td class="col-sm-8"><form:input path="customerID"  class="myform-control"
						id="customerID" readonly="true"/></td>
			</tr>
			
			<tr>
				<td class="col-sm-4"><label><spring:message
							code="label.fdID" /></label></td>
				<td class="col-sm-8"><form:input path="fdID" class="myform-control"
						id="fdID" readonly="true"/></td>
			</tr>			
			<tr>
				<td class="col-sm-4"><label><spring:message
							code="label.accountBalance" /></label></td>
				<td class="col-sm-8"><form:input path="accountBalance" class="myform-control"
						id="accountBalance" readonly="true"/></td>
			</tr>
			<tr>
				<td class="col-sm-4"><label><spring:message
							code="label.selectFormat" /></label></td>
				<td class="col-sm-8"><form:input path="paymentType" class="myform-control"
						id="paymentType" readonly="true"/></td>
			</tr>
			<tr>
				<td class="col-sm-4"><label><spring:message
							code="label.selectDate" /></label></td>
				<td class="col-sm-8"><form:input path="fdDueDate" class="myform-control"
						id="fdDueDate" readonly="true"/></td>
			</tr>
			<tr>
				<td class="col-sm-4"><label><spring:message
							code="label.fdAmount" /></label></td>
				<td class="col-sm-8"><form:input path="fdAmount"  class="myform-control"
						id="fdAmount" readonly="true"/>
					
						</td>
			</tr>
			<tr>
				<td class="col-sm-4"><label><spring:message
							code="label.typeOfTenure" /></label><span style="color: red">*</span></td>
				<td class="col-sm-8">
					<form:input path="fdTenureType"  class="myform-control"
						id="fdTenureType" readonly="true"/>
					
						</td>

			</tr>
			<tr>
				<td class="col-sm-4"><label><spring:message
							code="label.fdTenure" /></label></td>
				<td class="col-sm-8"><form:input path="fdTenure"  class="myform-control"
						id="fdTenure" readonly="true"/>
						
						</td>
			</tr>
			
			<tr>
				<td class="col-sm-4"><label><spring:message
							code="label.deathCer" /></label></td>
				<td class="col-sm-8"><form:select id="deathCertificate" path="deathCertificate" class="form-control" >
											<form:option value="select"></form:option>
													<form:option value="Submitted"></form:option>
													
											</form:select>
						
						</td>
			</tr>
			
			
		
		</table>
		<div class="space-10">&nbsp;</div>
		<div class="col-sm-12 col-md-12 col-lg-12">
		
			<div class="save-back">
								<p align="center" style="padding-top: 10px;">
								<input type="submit"  class="btn btn-primary" value="<spring:message code="label.update"/>">
					
								<a href="deathIssue" class="btn btn-success"><spring:message code="label.back"/></a>
								</p>
			</div>
		</div>
	</form:form>
	</div>
	
	</div>
	<style>
	.myform-control{
		    background: #999A95;
    		color: #000;
   			 cursor: no-drop;
	}
	</style>