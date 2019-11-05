<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<div class="approval-fd-confirm">
	<div class="errorMsg">${error}</div>
	<div class="col-sm-12 col-md-12 col-lg-12 header_customer">
		<h3 align="center">
			<spring:message code="label.fdApproval" />
		</h3>
	</div>


	<form:form action="updateFD" method="post" name="fixedDeposit" commandName="fixedDepositForm"
		onsubmit="return val();">
		<table style="margin:0px auto;" width="600">
		<form:hidden path="id" />
	        <tr>
		
				<td><b><spring:message code="label.customerName" /></b></td>
				<td><form:input path="customerName"  class="myform-control" id="customerName" readonly="true"/></td>
			</tr>
		  <tr>
		
				<td><b><spring:message code="label.contactNumber" /></b></td>
				<td><form:input path="contactNum"  class="myform-control" id="contactNum" readonly="true"/></td>
		  </tr>
		  <tr>
		
				<td><b><spring:message code="label.email" /></b></td>
				<td><form:input path="email"  class="myform-control" id="email" readonly="true"/></td>
		  </tr>
		    <tr>
		
				<td><b><spring:message code="label.customerId" /></b></td>
				<td><form:input path="customerID"  class="myform-control"
						id="customerID" readonly="true"/></td>
			</tr>
			
			<tr>
				<td><b><spring:message code="label.fdID" /></b></td>
				<td><form:input path="fdID" class="myform-control" id="fdID" readonly="true"/></td>
			</tr>			
			<tr>
				<td><b><spring:message code="label.accountBalance" /></b></td>
				<td><form:input path="accountBalance" class="myform-control" id="accountBalance" readonly="true"/></td>
			</tr>
			<tr>
				<td><b><spring:message code="label.fdAmount" /></b></td>
				<td><form:input path="fdAmount"  class="myform-control" id="fdAmount" readonly="true"/></td>
			</tr>
			<tr>
				<td><b><spring:message code="label.selectFormat" /></b></td>
				<td><form:input path="paymentType"  class="myform-control" id="paymentType" readonly="true"/></td>
			</tr>
			<tr>
				<td><b><spring:message code="label.typeOfTenure" /></b><span style="color: red">*</span></td>
				<td><form:input path="fdTenureType"  class="myform-control" id="fdTenureType" readonly="true"/> </td>

			</tr>
			<tr>
				<td><b><spring:message code="label.fdTenure" /></b></td>
				<td><form:input path="fdTenure"  class="myform-control" id="fdTenure" readonly="true"/> </td>
			</tr>
              <tr>
				<td><b><spring:message code="label.status"/></b><span style="color:red">*</span></td>
				
				<td> <form:input path="status"  class="myform-control" id="status" readonly="true"/></td>
			 </tr>
								
									
			<tr>
				<td><b><spring:message code="label.comment"/></b><span style="color:red">*</span></td>
				<td><form:textarea path="comment" id="comment" class="myform-control" placeholder="Enter Comment" style="height:120px;" readonly="true"></form:textarea></td>
			</tr>
									</table>
		<div class="space-10"></div>
		<div class="col-sm-12 col-md-12 col-lg-12">
			<table align="center">
				<tr>
					<td><input type="submit" class="btn btn-primary"
						value="<spring:message code="label.update"/>"></td>
						<td>&nbsp;</td>
					<td><a href="approveFD?id=${fixedDepositForm.id}" class="btn btn-success"><spring:message
								code="label.back" /></a></td>
				</tr>

			</table>
		</div>
	</form:form>
	

	</div>
	<style>
	.myform-control{
		background: #AFAFAF;
    color: black;
    cursor: no-drop;
	}
</style>