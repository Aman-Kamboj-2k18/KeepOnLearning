<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


  <div class="user-confirm-page">

				<div class="col-sm-12 col-md-12 col-lg-12 header_customer">
					<h3 align="center"><spring:message code="label.profileDetails"/></h3>
				</div>
				<form:form name="customerDetails" action="updateCustomerDetails" commandName="endUserForm"
					onsubmit="return validateForm()">
					<div style="margin-top: 156px;">
					<table style="margin: 0px auto;" width="400">
						<form:hidden path="id" />
						

						<tr>
							<td><b><spring:message code="label.userName"/></b></td>
							<td><form:input path="userName" class="myform-control" id="userName" value=""
									readonly="true" />
						</tr>

						<tr>
							<td><b><spring:message code="label.displayName"/> </b></td>
							<td><form:input path="displayName" class="myform-control" id="displayName" value=""
									readonly="true" />
						</tr>


						<tr>
							<td><b><spring:message code="label.contactNumber"/></b></td>
							<td><form:input path="contactNo" class="myform-control" value="" readonly="true" /></td>

						</tr>

						<tr>
							<td><b><spring:message code="label.altContactNo"/></b></td>
							<td><form:input path="altContactNo" class="myform-control" value="" readonly="true" /></td>

						</tr>

						<tr>
							<td><b><spring:message code="label.email"/></b></td>
							<td><form:input path="email" value="" class="myform-control" readonly="true" /></td>
						</tr>
						<tr>
							<td><b><spring:message code="label.altEmail"/></b></td>
							<td><form:input path="altEmail" value="" class="myform-control" readonly="true" /></td>
						</tr>

						


						<form:hidden path="transactionId" value="" />
					</table>
					</div>
					<div class="space-10"></div>
					<div class="col-sm-12">
						<table align="center">
							<tr>
								<td class="col-sm-8"><a href="user" class="btn btn-success" ><spring:message code="label.back"/></a> <input type="submit"  value="<spring:message code="label.update"/>" class="btn btn-primary"></td>
								
							</tr>
						</table>
					</div>
<div style="clear:both;"></div>
				</form:form>
</div>
<style>
.myform-control{

    background: #A6A7A6;
    color: black;
    cursor: no-drop;
  }
  </style>