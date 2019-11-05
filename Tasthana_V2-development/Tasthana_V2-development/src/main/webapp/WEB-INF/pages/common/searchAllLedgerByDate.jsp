<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="right-container" id="right-container">
	<div class="container-fluid">




		<div class="Flexi_deposit">

			<div class="header_customer" style="margin-bottom: 30px;">
				<h3>Search All Journal</h3>
			</div>
			<div class="Success_msg">
				<div class="successMsg"
					style="text-align: center; color: red; font-size: 18px;">
					<h4 id= "errorMsgForDate">${error}</h4>

				</div>
			</div>
			<div class="flexi_table">
				<form:form class="form-horizontal" action="journalAllList"
					id="jounalForm" method="GET" name="ledgerReport"
					commandName="ledgerReportForm">
					<input type="hidden" name="menuId" value="${menuId}"/>


					<div class="add-customer-table">

						<div class="form-group">
							<label class="col-md-4 control-label">From Date</label>
							<div class="col-md-4">
								<form:input path="fromDate" id="fromDate" readonly="true"
									placeholder="Select Date"
									class="myform-control datepicker-here" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">To Date</label>
							<div class="col-md-4">
								<form:input path="toDate" id="toDate" readonly="true"
									placeholder="Select Date"
									class="myform-control datepicker-here" />
							</div>
						</div>

					</div>

					<div class="form-group">
						<div class="col-md-offset-4 col-md-8">
							<input type="submit" class="btn btn-info"
								value="<spring:message code="label.viewJournalList"/>">
						</div>
					</div>

					<table align="center">

						<tr>
							<td><br></td>
						</tr>

						<c:if test="${! empty journalList}">
							<div class="col-sm-12 col-md-12 col-lg-12">
								<input type="text" id="kwd_search" value=""
									placeholder="Search Here..." style="float: right;" />
							</div>
							<table
								class="table table-striped table-bordered table data jqtable example"
								style="width: 95% !important; margin-left: 25px;">
								<thead style="color: #358cce;">
								<tr>
									<th><b><spring:message code="label.fdID" /></b></th>
									<th><b><spring:message code="label.journalDate" /></b></th>
									<th><b><spring:message code="label.particulars" /></b></th>
									<th><b><spring:message code="label.debitAmount" /></b></th>
									<th><b><spring:message code="label.creditAmount" /></b></th>
									 <th><b>Deposit Account Number</b></th> 
								</tr>
								</thead>
								<tbody>
								<c:forEach items="${journalList}" var="journal">

									<tr>
										<td><b> <c:out value="${journal.depositId}"></c:out></b></td>
										<td><b> <c:out value="${journal.journalDate}"></c:out></b></td>
										<td><b> <c:out value="${journal.particulars}"></c:out></b></td>
										<td><b> <c:out value="${journal.debitAmount}"></c:out></b></td>
										<td><b> <c:out value="${journal.creditAmount}"></c:out></b></td>
										<td><b style="color:#358cce"> <c:out value="${journal.depositAccountNumber}"></c:out></b></td>
									</tr>
								</c:forEach>
								</tbody>
							</table>

						</c:if>


					</table>

					 <div class="form-group">
						<div class="col-md-offset-4 col-md-8">
							<input type="submit" class="btn btn-info" id="goBtn"
								onclick="viewLedger()"
								value="<spring:message code="label.viewLedger"/>">
						</div>
					</div> 

				</form:form>
			</div>

		</div>

		<script>
		
		function viewLedger(){
			var fDate = $("#fromDate").val();
			var tDate = $("#toDate").val();
			if(fDate == "" || tDate == "" ){
				$("#errorMsgForDate").text("Please select Date !");
				return false;
				
			}
			
			$("#jounalForm").attr("action", "ledgerAllList");
			$("#jounalForm").submit();
		}

		</script>