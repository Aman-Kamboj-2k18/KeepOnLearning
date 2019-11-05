<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="right-container" id="right-container">
        <div class="container-fluid">
		<!-- 	<div class="row">
				<div class="col-md-12">
				<ul class="breadcrumb">
					<li><i class="fa fa-home"></i><a href="#"> Home</a></li>
					<li>Customer List</li>
				</ul>
				</div>
            </div> -->
<div class="edit-admin-profile">

	<div class="success-msg">
	
		<div class="successMsg"
			style="text-align: center; color: green; font-size: 18px;">${success}</div>

	</div>

	<table align="center" width="400">


		<%-- <tr>
			<td class="heading_text"><b><spring:message
						code="label.transactionId" />:</b></td>
			<td>${model.endUserForm.transactionId}</td>
		<tr>
			<td><b><spring:message code="label.transactionType" />:</b></td>
			<td><spring:message code="label.adminDetails" /></td>
		</tr> --%>
		<tr>
			<td><b><spring:message code="label.status" /></b></td>
			<td><font color="green"><spring:message
						code="label.saveSuccessfully" /></font></td>
		</tr>

	</table>


</div></div>
<style>
	.edit-admin-profile{
		margin-top:30px;
		margin-bottom:270px;
	}
</style>