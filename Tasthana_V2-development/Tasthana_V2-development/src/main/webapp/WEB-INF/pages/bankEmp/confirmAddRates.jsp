<%@include file="taglib_includes.jsp"%>

<div class="set-rate">
			<div class="header_customer">
				<h3 style="text-align: center"><%-- <spring:message code="label.confirmAddedRate"/> --%>Confirm Add Rate</h3>
			</div>
	<form:form action="saveAddRates" name="rate" method="post"
		commandName="ratesForm" >
			
				<div class="set-rate-table">

				<table align="center" width="400">
					<tr>
						<td><b><spring:message code="label.category"/></b></td>
						<td><form:input path="type"  class="myform-control" readonly="true"></form:input>
							</td>
					</tr>
				
					<tr>
					    <td><b><spring:message code="label.tds"/></b></td>
						<td><form:input path="tds"  class="myform-control" readonly="true"></form:input></td>													
					</tr>
					<tr>
					    <td><b><spring:message code="label.ses"/></b></td>
						<td><form:input path="ses"  class="myform-control" readonly="true"></form:input></td>													
					</tr>
					<tr>
					    <td><b><spring:message code="label.service"/></b></td>
						<td><form:input path="service"  class="myform-control" readonly="true"></form:input></td>													
					</tr>
					<tr>
					    <td><b>Deposit Fixed Percentage(%)</b></td>
						<td><form:input path="fdFixedPercent"  class="myform-control" readonly="true"></form:input></td>													
					</tr>
					
					<form:hidden path="transactionId"/>
      			 
				</table>
				<div class="space-10"></div>
				<table align="center" style="position: relative; left: 5.1%;">
					<tr>
						<td><input type="submit" size="3" value="<spring:message code="label.save"/>"
							class="btn btn-primary"></td>
							<td>&nbsp;&nbsp;</td>
						<td><a href="addRate" class="btn btn-success"><spring:message code="label.back"/></a></td>
					</tr>
				</table>

		</div>


	</form:form>
</div>
