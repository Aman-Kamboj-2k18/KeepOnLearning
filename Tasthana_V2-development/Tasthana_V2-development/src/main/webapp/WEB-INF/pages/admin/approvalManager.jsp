<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<script type="text/javascript">

function getTodaysDate()
{
	var today = null;
	$.ajax({  
	    type: "GET",  
	    async: false,
	    url: "<%=request.getContextPath()%>/bnkEmp/loginDateForJsp", 
	    contentType: "application/json",
	    dataType: "json",

	    success: function(response){  
	    //	window.loginDateForFront = new Date(parseInt(response));
	    	today = new Date(parseInt(response))
	    },  
	    error: function(e){  
	    	 $('#error').html("Error occured!!")
	    	 // window.loginDateForFront = getTodaysDate();
	    }  
	  });  
	return today;
}
function validateForm()
{
var d = getTodaysDate().getTime();
var uuid = 'xxxxxx'.replace(/[xy]/g, function(c) {
    var r = (d + Math.random()*16)%16 | 0;
    d = Math.floor(d/16);
    return (c=='x' ? r : (r&0x3|0x8)).toString(16);
});
document.role.transactionId.value=uuid;
}

function goBack() {
    window.history.back();
}
</script>
<div class="right-container" id="right-container">
        <div class="container-fluid">
			
<div class="role-page">

	<div class="col-sm-12 col-md-12 col-lg-12 header_customer">
		<h3>
			<spring:message code="label.confirmationScreen" />
		</h3>
	</div>
	<div class="col-sm-12 col-md-12 col-lg-12">
		<div class="successMsg"
			style="text-align: center; color: green; font-size: 18px;">${success}</div>
	</div>


	<form:form name="role" action="insertDetails" commandName="endUserForm"
		onsubmit="return validateForm()">
		<form:input type="hidden" id="rolesId" path="rolesId"/>
		<div class="col-sm-12 col-md-12 col-lg-12">
			<table  width="600">
               <tr>
					<td><b><spring:message code="label.name" /></b></td>
					<td><form:input path="displayName" class="myform-control"
							id="displayName" value=""
							readonly="true" />
				</tr>
				
				<tr>
					<td><b><spring:message code="label.bankId" /></b></td>
					<td><form:input path="bankId" class="myform-control"
							id="bankId" value=""
							readonly="true" />
				</tr>
				
				<tr>
					<td><b><spring:message code="label.designation" /></b></td>
					<td><form:input path="designation" class="myform-control"
							id="designation" value=""
							readonly="true" />
				</tr>

				<tr>
					<td><b><spring:message code="label.role" /></b></td>
					<td><form:input path="currentRole" class="myform-control"
							id="currentRole" value="${model.endUserForm.currentRole}"
							readonly="true" />
				</tr>


				<tr>
					<td><b><spring:message
								code="label.contactNumber" /></b></td>
					<td><form:input path="contactNo" class="myform-control"
							id="contactNo" value="${model.endUserForm.contactNo}"
							readonly="true" />
				</tr>
				<tr>
					<td><b><spring:message code="label.email" /></b></td>
					<td><form:input path="email" class="myform-control"
							value="${model.endUserForm.email}" readonly="true" /></td>
				</tr>

				<%-- <tr>
					<td><b><spring:message
								code="label.approvalManagerLimit" /></b></td>
					<td><form:input path="approvallimit" class="myform-control"
							value="${model.endUserForm.approvallimit}" readonly="true" /></td>
				</tr> --%>
				<tr>
					<td><b><spring:message
								code="label.userName" /></b></td>
					<td><form:input path="userName" class="myform-control"
							value="${model.endUserForm.userName}" readonly="true" /></td>
					<form:hidden path="transactionId" value="" />
					<form:hidden path="role" value="3" />
					<form:hidden path="password" />

				</tr>
				
				<tr>
							<fmt:formatDate value="${endUserForm.startDate}"
								var="startDateFormat" pattern="dd/MM/yyyy" />
							<td ><b>Account Start Date</b></td>
							<td ><form:input
									path="startDate" class="myform-control" readonly="true" value="${startDateFormat}"  /></td>

						</tr>

						<tr>
							<fmt:formatDate value="${endUserForm.accExpiryDate}"
								var="expiryDateFormat" pattern="dd/MM/yyyy" />
							<td><b>Account Expiry Date</b></td>
							<td><form:input
									path="accExpiryDate" class="myform-control" readonly="true" value="${expiryDateFormat}"
									  /> 
								<%-- <form:input path="accExpiryDate" class="myform-control"
									readonly="true"></form:input>	 --%>  
									  
									  </td>

						</tr>

				
			<%-- 	<form:hidden path="startDate"/>
				<form:hidden path="accExpiryDate"/>
				 --%>
					<%-- 	<tr>
							<td><b>Start Date :</b></td>
							<td><form:input path="startDate" class="myform-control"
									value="${model.endUserForm.startDate}" readonly="true" /></td>
							<form:hidden path="transactionId" value="" />
						</tr>
						<tr>
							<td><b>End Date :</b></td>
							<td><form:input path="accExpiryDate" class="myform-control"
									value="${model.endUserForm.accExpiryDate}" readonly="true" /></td>
							<form:hidden path="transactionId" value="" />
						</tr>
				 --%>

			</table>
			
			<div class="space-10"></div>
			<div class="col-sm-offset-2">
				<table  style="position: relative;left: 9%;">
					<tr>
					<td>
					<a href="createRole" class="btn btn-success"><spring:message
									code="label.back" /></a></td>
						<td>
						<%-- <button onclick="goBack()" class="btn btn-success"><spring:message
								code="label.back" /></button> --%>
					</td>
					<td class="col-sm-8"><input type="submit"
							value="<spring:message code="label.save"/>"
							class="btn btn-primary"></td>


					</tr>
				</table>
				</div>
		</div>

	</form:form>
</div></div>
<style>
.myform-control{
		background: #AFAFAF;
    color: black;
    cursor: no-drop;
	}
	
</style>