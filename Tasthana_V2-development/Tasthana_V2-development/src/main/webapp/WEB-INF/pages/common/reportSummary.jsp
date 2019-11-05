<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<script>

function getTodaysDate()
{
	var today = null;
	$.ajax({  
	    type: "GET",  
	    async: false,
	    url: "<%=request.getContextPath()%>/common/loginDateForJsp", 
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
	function validate() {
		var canSubmit = true;
		document.getElementById("tableDiv").style.display = 'none';

		var toDate = document.getElementById('toDate').value;
		var fromDate = document.getElementById('fromDate').value;

		if (toDate == '' && fromDate == '') {
			document.getElementById("error").innerHTML = "Please select From Date and To Date";
			document.getElementById("fromDate").style.borderColor = "red";
			document.getElementById("toDate").style.borderColor = "red";

			canSubmit = false;
		} else if (toDate == '') {
			document.getElementById("error").innerHTML = "Please select To Date";
			document.getElementById("fromDate").style.borderColor = "#cccccc";
			document.getElementById("toDate").style.borderColor = "red";
			canSubmit = false;
		} else if (fromDate == '') {
			document.getElementById("error").innerHTML = "Please select From Date";
			document.getElementById("toDate").style.borderColor = "#cccccc";
			document.getElementById("fromDate").style.borderColor = "red";
			canSubmit = false;
		}

		var parts = toDate.split('/');
		var toDate = new Date(parts[2], parts[1] - 1, parts[0]);

		var parts2 = fromDate.split('/');
		var fromDate = new Date(parts2[2], parts2[1] - 1, parts2[0]);

		var today = getTodaysDate();

		if (toDate > today) {
			document.getElementById("error").innerHTML = "Future Date selected";
			document.getElementById("fromDate").style.borderColor = "#cccccc";
			document.getElementById("toDate").style.borderColor = "red";
			canSubmit = false;
		}

		if (fromDate > today) {
			document.getElementById("error").innerHTML = "Future Date selected";
			document.getElementById("fromDate").style.borderColor = "red";
			document.getElementById("toDate").style.borderColor = "#cccccc";
			canSubmit = false;
		}
		
		if (fromDate > toDate) {
			document.getElementById("error").innerHTML = "Invalid date selection";
			document.getElementById("fromDate").style.borderColor = "red";
			document.getElementById("toDate").style.borderColor = "red";
			canSubmit = false;
			
		}

		return canSubmit;
	}
</script>

<div class="right-container" id="right-container">
	<div class="container-fluid">


		<div class="Flexi_deposit">
			<div class="header_customer">
				<h3>Report Summary</h3>
			</div>

			<div class="fd-list-table">
				<div class="search_filter">

					<div class="pull-left">
						<form:form commandName="reportForm" id="ratesForm" name="viewForm"
							action="getSummary" onsubmit="return validate()">
							<div class="form-group">
								<label class="col-md-4 control-label" style="padding-top: 16px;">From Date<span
									style="color: red">*</span></label>
								<div class="col-md-8">
									<form:input path="fromDate" value="${fromDate}" id="fromDate"
										readonly="true" placeholder="Select Date"
										class="myform-control datepicker-here" />

								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label" style="padding-top: 16px;">To Date<span
									style="color: red">*</span></label>
								<div class="col-md-8">
									<form:input path="toDate" value="${toDate}" id="toDate"
										readonly="true" placeholder="Select Date"
										class="myform-control datepicker-here" />

								</div>
							</div>

							<div class="save-back" style="margin-top: 5%;">
								<span id="error" style="color: red"></span>
								<p align="center" style="padding-top: 10px;">

									<input type="submit" class="btn btn-primary"
										value="<spring:message code="label.showDetails"/>">
								</p>
							</div>

						</form:form>


					</div>

					<div class="space-10"></div>
					<div class="col-sm-12" id="dividerLine">
						<hr>
					</div>
					<div id="tableDiv">
						<c:if test="${! empty reportList}">

							<table
								class="table table-bordered table-striped table-hover data jqtable example "
								align="center" id="my-table">
								<thead>
									<tr class="success">
										<th>Date</th>
										<th>Count (New Deposit)</th>
										<th>New Deposits Fixed Amount</th>
										<th>New Deposits Variable Amount</th>
										<th>Existing Deposits Fixed Amount</th>
										<th>Existing Deposits Variable Amount</th>
										<th>Withdraw Amount</th>
										<th>Interest Amount</th>
										<th>TDS Amount</th>
										<th>GST Amount</th>
										<th>Penalty Amount</th>

									</tr>
								</thead>
								<tbody>

									<c:forEach items="${reportList}" var="report">
										<tr>
											<td style="font: 34px"><b><fmt:formatDate
														pattern="dd/MM/yyyy" value="${report.fromDate}" /></b></td>

											<td><c:out value="${report.newCount}"></c:out></td>


											<td><fmt:formatNumber minFractionDigits="2"
													value="${report.newFixedAmount}" /></td>
											<td><fmt:formatNumber minFractionDigits="2"
													value="${report.newVariableAmount}" /></td>
											<td><fmt:formatNumber minFractionDigits="2"
													value="${report.existingFixedAmount}" /></td>
											<td><fmt:formatNumber minFractionDigits="2"
													value="${report.existingVariableAmount}" /></td>
											<td><fmt:formatNumber minFractionDigits="2"
													value="${report.withdrawAmount}" /></td>
											<td><fmt:formatNumber minFractionDigits="2"
													value="${report.interestAmount}" /></td>
											<td><fmt:formatNumber minFractionDigits="2"
													value="${report.tdsAmount}" /></td>
											<td><fmt:formatNumber minFractionDigits="2"
													value="${report.gstAmount}" /></td>
											<td><fmt:formatNumber minFractionDigits="2"
													value="${report.penalityAmount}" /></td>


										</tr>
									</c:forEach>


								</tbody>
							</table>
						</c:if>
					</div>
				</div>

			</div>
		</div>
	</div>
	<style>
.search_filter {
	display: flow-root;
	margin-bottom: 15px;
}
</style>
	<!-- <style>
		.table-bordered {
    border: 1px solid #BFBABA;
}
	  .table-bordered>tbody>tr>td, .table-bordered>tbody>tr>th, .table-bordered>tfoot>tr>td, .table-bordered>tfoot>tr>th, .table-bordered>thead>tr>td, .table-bordered>thead>tr>th{ border: 1px solid #C7C7C7;}
</style> -->