<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<div class="right-container" id="right-container">
	<div class="container-fluid">

		<div class="Flexi_deposit">

			<div class="header_customer" style="margin-bottom: 30px;">
				<h3>
					<spring:message code="label.formSubmission" />
				</h3>
			</div>
			<div class="col-md-12" style="padding: 30px; text-align: center;">
				<span style="color: red;">${error}</span>
			</div>
			<div class="flexi_table">
				<form:form action="savedFormSubmission" class="form-horizontal"
					id="fdForm" method="post" name="deposit" commandName="depositForm">
					<form:hidden  path="customerId"/>
					<form:hidden  path="category"/>
					<div class="col-sm-12">
						<div class="space-45"></div>
					</div>
					<c:if test="${! empty cust}">
						

						<div class="form-group">
							<c:if test="${cust.category=='Senior Citizen'}">
								<label class="col-md-4 control-label"><spring:message
										code="label.formForSenior" /></label>
							</c:if>
							<c:if test="${!(cust.category=='Senior Citizen')}">
								<label class="col-md-4 control-label"><spring:message
										code="label.formForJunior" /></label>
							</c:if>
							<div class="col-md-4">
								<label><spring:message code="label.yes" /></label>
								<form:radiobutton path="documentSubmitted" value="Yes"
									id="deathRadio" onclick="onClickRadioBtn(this.value)"></form:radiobutton>
								<label style="padding-left: 10px;"><spring:message
										code="label.no" /></label>
								<form:radiobutton path="documentSubmitted" value="No"
									id="deathRadio" onclick="onClickRadioBtn(this.value)"
									checked="true"></form:radiobutton>
								<span id="fdPayError" style="display: none; color: red;"><spring:message
										code="label.validation" /></span>
							</div>
						</div>


						<div class="form-group">
							<div class="col-md-offset-4 col-md-8">
								<input type="submit" class="btn btn-info" id="goBtn"
									value="<spring:message code="label.go"/>"> <a
									href="customerSearchForFormSubmission" class="btn btn-info"><spring:message
										code="label.back" /></a>
							</div>
						</div>
					</c:if>

				</form:form>
			</div>
		</div>




		<style>
.flexi_table {
	margin-bottom: 210px;
}

.space-45 {
	height: 27px;
}

input#fdID {
	margin-top: 0px;
}
</style>