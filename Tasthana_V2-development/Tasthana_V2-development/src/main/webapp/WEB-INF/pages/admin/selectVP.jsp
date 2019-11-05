<%@include file="taglib_includes.jsp"%>


<link href="<%=request.getContextPath()%>/resources/css/HoldOn.css" rel="stylesheet" type="text/css">
<script src="<%=request.getContextPath()%>/resources/js/HoldOn.js"></script>

<div class="right-container" id="right-container">
        <div class="container-fluid">
		
<div class="bank-emp-list">

	<div class="header_customer">
		<h3 style="text-align: center;">
			VP Details
		</h3>
	</div>
	<div class="col-md-12 col-md-12 col-lg-12">

		<form:form action="selectUpdateVP" method="post"
			commandName="endUserForm">
			<table width="500">
				<tr>
					<td ><spring:message code="label.id" />
					
					<td ><form:input path="id" readonly="true" class="myform-control"></form:input></td>
				</tr>
				<tr>
					<td ><spring:message code="label.userName" /></td>
					<td ><form:input path="userName" class="myform-control"
							readonly="true"></form:input></td>
				</tr>
				<tr>
					<td ><spring:message
							code="label.contactNumber" /></td>
					<td ><form:input path="contactNo" class="myform-control"
							readonly="true"></form:input></td>
				</tr>
				<tr>
					<td ><spring:message code="label.currentRole" /></td>
					<td ><form:input path="currentRole" class="myform-control"
							readonly="true"></form:input></td>
				</tr>
				<tr>
					<td ><spring:message code="label.email" /></td>
					<td ><form:input path="email" readonly="true" class="myform-control"></form:input></td>
				</tr>

			</table>
			
			<div class="col-sm-12">&nbsp;</div>
				<table class="col-md-offset-2">
				<tr>
											<td id="loading"></td></tr>
					<tr>
						<td class="col-sm-8"><input type="submit" size="3" id="save" onclick="testHoldon('sk-rect');"
							value="<spring:message code="label.sendEmailNotification"/>"
							class="btn btn-primary"></td>
						<td class="col-sm-8"><a href="#" class="btn btn-success" 
							onclick="javascript:window.location='appMngList';"><spring:message
									code="label.back" /></a></td>

					</tr>
				</table>
			
		</form:form>

	</div>
</div></div>
<style>
	.myform-control{
		background: #AFAFAF;
    color: black;
    cursor: no-drop;
	}
</style>
<script>
                            
	function testHoldon(themeName){
		HoldOn.open({
			theme:themeName,
		
		});
		
		setTimeout(function(){
			HoldOn.close();
		},8000);
	}
</script>
<script>
    $(document).ready( function () {
        $('#bootstrap-table').bdt();
    });
</script>