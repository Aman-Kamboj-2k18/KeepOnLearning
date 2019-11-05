<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div class="right-container" id="right-container">
	<div class="container-fluid">


		<div class="add-customer">
			<form:form action="closedTransactionsList" class="form-horizontal"
				name="fixedDeposit" autocomplete="off" method="get"
				commandName="depositForm" onsubmit="return val();">

<input type="hidden" name="menuId" value="${menuId}"/>
				<div class="newcustomer_border">
					<div class="header_customer">
						<h3>
							<spring:message code="label.searchClosedTransaction" />
						</h3>
					</div>
					<div class="successMsg">
						<b><font color="green">${success}</font></b>
					</div>
					
					<div class="add-customer-table">

						<div class="form-group">
							<label class="col-md-4 control-label">From Date</label>
							<div class="col-md-4">
								<form:input path="closingDate" id="fromDate" readonly="true"
									placeholder="Select Date"
									class="myform-control datepicker-here" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">To Date</label>
							<div class="col-md-4">
								<form:input path="chequeDate" id="toDate" readonly="true"
									placeholder="Select Date"
									class="myform-control datepicker-here" />
							</div>
						</div>

					</div>
					
					<div class="successMsg">
						<b><font color="error">${error}</font></b>
					</div>
					<div class="col-md-offset-4 col-md-8">
						<input type="submit" id="goBtn" class="btn btn-info"
							value="<spring:message code="label.go"/>">
					</div>

					
				</div>
			</form:form>
			<div id="items" class="col-sm-12"></div>
		</div>
	</div>
</div>