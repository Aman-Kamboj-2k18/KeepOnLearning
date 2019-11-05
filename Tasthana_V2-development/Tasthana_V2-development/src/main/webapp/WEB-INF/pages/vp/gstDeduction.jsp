<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- To fetch the request url -->
<c:set var="req"
	value="${requestScope['javax.servlet.forward.request_uri']}" />
<c:set var="baseURL" value="${fn:split(req,'/')}" />
<link href="<%=request.getContextPath()%>/resources/css/Validation.css"
	rel="stylesheet">
<script src="<%=request.getContextPath()%>/resources/js/validation.js"></script>



<div class="right-container" id="right-container">
	<div class="container-fluid" style="padding-top: 25px;">
		<div class="Flexi_deposit">

			<div class="header_customer">
				<h3>GST</h3>
			</div>
			<div class="Success_msg">
				<div class="successMsg" style="text-align: center; color: red;">
					${error}</div>
			</div>

			<div class="col-md-6">
				<form:form action="gstDeduction" commandName="gstDeduction" id="gstDeduction"
					method="post">
			<form:hidden path="id"/>
					<div class="form-group">
						<label class="col-md-6 control-label">CGST %<span
							style="color: red"></span></label>
						<div class="col-md-6">

							<form:input id="cgst" class="myform-control" path="cgst"
								onkeypress="amountValidation(event)" />
						</div>

					</div>


					<div class="form-group">
						<label class="col-md-6 control-label">SGST %<span
							style="color: red"></span></label>
						<div class="col-md-6">
							<form:input id="sgst" class="myform-control" path="sgst"
								onkeypress="amountValidation(event)" />
						</div>

					</div>


					<div class="form-group">
						<label class="col-md-6 control-label">IGST %<span
							style="color: red"></span></label>
						<div class="col-md-6">

							<form:input id="igst" class="myform-control" path="igst"
								onkeypress="amountValidation(event)" />
						</div>

					</div>

					<div class="form-group button-submit">
						<input type="button" value="Submit" class="btn btn-primary"
							style="margin-top: 21px; margin-right: 15px;"
							onclick="return submitOnButton()"/>
					</div>




				</form:form>
			</div>
		</div>
	</div>
</div>
<style>
<!--
-->
.button-submit {
	margin-top: 21px;
	float: right;
	margin-bottom: 18px;
}
</style>

<script>
	function submitOnButton() {
		debugger;
		var gst  = $("#cgst").val();
		var sgst = $("#sgst").val();
		var igst = $("#igst").val();
		if(gst == "" && sgst == "" && igst == ""){
			alert("Please select atleast one !")
			return false;
		}

		$("#gstDeduction").submit();
	}
</script>