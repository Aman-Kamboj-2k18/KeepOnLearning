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
	function validateForm() {
		var d = getTodaysDate().getTime();
		var uuid = 'xxxxxx'.replace(/[xy]/g, function(c) {
			var r = (d + Math.random() * 16) % 16 | 0;
			d = Math.floor(d / 16);
			return (c == 'x' ? r : (r & 0x3 | 0x8)).toString(16);
		});
		document.bankempupdate.transactionId.value = uuid;
	}
</script>
<div class="right-container" id="right-container">
        <div class="container-fluid">
			

<div class="bank-emp-list">
	<div class="header_customer">
		<h3>
			<spring:message code="label.confirmationScreen" />
		</h3>
	</div>
	<form:form name="customerupdate" action="updateCustomerStatus" method="post"
		commandName="endUserForm" onsubmit="return validateForm();">
		<table style="margin:0px auto;" width="600">

			<tr>
				<td><b><spring:message code="label.id" /></b><span
					style="color: red">*</span></td>
				<td><form:input path="id" id="id" class="myform-control"
						value="${model.endUserForm.id}" readonly="true" /></td>
			</tr>
			<tr>
				<td><b><spring:message
							code="label.userName" /></b></td>
				<td><form:input path="userName" id="userName" class="myform-control"
						value="${model.endUserForm.userName}" readonly="true" />
			</tr>

			<%-- <tr>
							<td><b>Display Name :</b></td>
							<td><form:input path="displayName"
									value="${model.endUserForm.displayName}" readonly="true" /></td>
						</tr> --%>
			<tr>
				<td><b><spring:message code="label.email" /></b></td>
				<td><form:input path="email" class="myform-control"
						value="${model.endUserForm.email}" readonly="true" /></td>

			</tr>

			<tr>
				<td><b><spring:message
							code="label.contactNumber" /></b></td>
				<td><form:input path="contactNo" class="myform-control"
						value="${model.endUserForm.contactNo}" readonly="true" /></td>
			</tr>


			<tr>
				<td><b><spring:message code="label.status" /></b></td>
				<td><form:input path="status" class="myform-control"
						value="${model.endUserForm.status}" readonly="true" /></td>
				<form:hidden path="transactionId" value="" />
			</tr>
			<tr>
				<td><b><spring:message
							code="label.comment" /></b></td>
				<td><form:textarea path="comment" id="comment" class="myform-control"
						placeholder="Enter Comment" style="height:120px;" readonly="true"></form:textarea></td>
			</tr>


		</table>

		<div class="space-10"></div>
			<table align="center"  style="position: relative;right: 3.3%;">
				<tr>
					<td class="col-sm-8"><a
							href="suspendCustomerFromAdmin?id=${endUserForm.id}"
							class="btn btn-success"><spring:message code="label.back" /></a>
							<input type="submit"
						value="<spring:message code="label.save"/>"
						class="btn btn-primary"></td>
			</table>

	</form:form>
</div>

<style>
	.myform-control{
		background: #AFAFAF;
    color: black;
    cursor: no-drop;
	}
</style>