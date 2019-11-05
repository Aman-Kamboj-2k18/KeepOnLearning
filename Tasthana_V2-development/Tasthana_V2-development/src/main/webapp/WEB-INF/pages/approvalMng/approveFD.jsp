<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<div class="Flexi_deposit">
	
	<div class="header_customer">
		<h3 align="center">
			<spring:message code="label.fdApproval" />
		</h3>
	</div>

<div class="flexi_table">
	<form:form action="approveFDConfirm" method="post" name="fixedDeposit" commandName="fixedDepositForm"
		onsubmit="return val();">
		<table align="center" width="600">
		<form:hidden path="id" />
		
	    
	        <tr>
		
				<td><b><spring:message
							code="label.customerName" /></b></td>
				<td><form:input path="customerName"  class="myform-control"
						id="customerName" readonly="true"/></td>
			</tr>
		  <tr>
		
				<td><b><spring:message
							code="label.contactNumber" /></b></td>
				<td><form:input path="contactNum"  class="myform-control"
						id="contactNum" readonly="true"/></td>
			</tr>
		<tr>
		
				<td><b><spring:message
							code="label.email" /></b></td>
				<td><form:input path="email"  class="myform-control"
						id="email" readonly="true"/></td>
			</tr>
		    <tr>
		
				<td><b><spring:message
							code="label.customerId" /></b></td>
				<td><form:input path="customerID"  class="myform-control"
						id="customerID" readonly="true"/></td>
			</tr>
			
			<tr>
				<td><b><spring:message
							code="label.fdID" /></b></td>
				<td><form:input path="fdID" class="myform-control"
						id="fdID" readonly="true"/></td>
			</tr>			
			<tr>
				<td><b><spring:message
							code="label.accountBalance" /></b></td>
				<td><form:input path="accountBalance" class="myform-control"
						id="accountBalance" readonly="true"/></td>
			</tr>
			<tr>
				<td><b><spring:message
							code="label.fdAmount" /></b></td>
				<td><form:input path="fdAmount"  class="myform-control"
						id="fdAmount" readonly="true"/>
					
						</td>
			</tr>
			<tr>
				<td><b><spring:message
							code="label.selectFormat" /></b></td>
				<td><form:input path="paymentType"  class="myform-control"
						id="paymentType" readonly="true"/>
					
						</td>
			</tr>
			<tr>
				<td><b><spring:message
							code="label.typeOfTenure" /></b><span style="color: red">*</span></td>
				<td>
					<form:input path="fdTenureType"  class="myform-control"
						id="fdTenureType" readonly="true"/>
					
						</td>

			</tr>
			<tr>
				<td><b><spring:message
							code="label.fdTenure" /></b></td>
				<td><form:input path="fdTenure"  class="myform-control"
						id="fdTenure" readonly="true"/>
						
						</td>
			</tr>
			
		                             <tr>
										<td><b><spring:message code="label.status"/></b><span style="color:red">*</span></td>
										
										<td><form:select path="status" id="status" class="myform-control" style="background: whitesmoke; cursor: pointer;">
										    <form:option value=""><spring:message code="label.selectValue"/></form:option> 
										    <form:option value="Approved"><spring:message code="label.approve"/></form:option>
											<form:option value="Closed"><spring:message code="label.close"/></form:option>
											</form:select>
										</td>
									</tr>
									<tr>
										<td></td>
										
									   <td id="statusError" class="error" style="display:none;"><font color="red"><spring:message code="label.selectValue"/></font></td>
									  <td id="statusError" class="error" style="display:none"><spring:message code="label.selectValue"/></td>
									</tr>
									
									<tr>
										<td><b><spring:message code="label.comment"/></b><span style="color:red">*</span></td>
										<td><form:textarea path="comment" maxlength="255" id="comment" placeholder="Enter Comment" style="height:120px;background: whitesmoke; cursor: text;" class="myform-control"></form:textarea></td>
										
											
									</tr>
									<tr>
										<td></td>
										<td id="commentError" class="error" style="display:none;"><font color="red"><spring:message code="label.commentValidation"/></font></td>
										<td id="commentError" class="error" style="display:none"><spring:message code="label.commentValidation"/></td>
									</tr>
									</table>
		<div class="space-10"></div>
		<div class="col-sm-12 col-md-12 col-lg-12">
			<table align="center">
				<tr>
					<td><input type="submit" class="btn btn-primary"
						value="<spring:message code="label.confirm"/>"></td>
						<td>&nbsp;</td>
					<td><a href="fdList" class="btn btn-success"><spring:message
								code="label.back" /></a></td>
				</tr>

			</table>
		</div>
	</form:form>
	</div>
	<script>
		
				function val(){
					
				
					if (document.getElementById('comment').value == ''){
						document.getElementById('comment').style.borderColor="red";
						return false;
					}
					else{
						document.getElementById('comment').style.borderColor="green";
					}
					if (document.getElementById('status').value == ''){
						document.getElementById('status').style.borderColor="red";
						return false;
					}
					else{
						document.getElementById('status').style.borderColor="green";
					}

				}
			
				
		</script>

	</div>
	<style>
.myform-control{
		background: #AFAFAF;
    color: black;
    cursor: no-drop;
	}
</style>