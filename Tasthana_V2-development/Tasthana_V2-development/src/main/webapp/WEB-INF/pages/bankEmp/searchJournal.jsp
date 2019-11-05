<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="right-container" id="right-container">
	<div class="container-fluid">

		


		<div class="Flexi_deposit">

			<div class="header_customer" style="margin-bottom: 30px;">
				<h3>
					
					Search Journal
				</h3>
			</div>
			<div class="Success_msg">
			<div class="successMsg"
				style="text-align: center; color: red; font-size: 18px;">
				<h4>${error}</h4>

			</div>
		</div>
			<div class="flexi_table">
				<form:form class="form-horizontal" action="journalList"  id="jounalForm" method="GET"
					name="ledgerReport" commandName="ledgerReportForm">


					<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.fdAccountNo" /></label>
						<div class="col-md-4">
							<form:input path="fdAccountNo" class="myform-control" id="fdAccountNo"
								placeholder="Enter Account Number" value="${account}" required="true"/>

						</div>
					</div>
					
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
							<input type="submit" onclick="searchJournal()"
								class="btn btn-info"
								value="<spring:message code="label.viewJournalList"/>">
						</div>
					</div>

			 	<table align="center">

						<tr>
							<td><br></td>
						</tr>

						<c:if test="${! empty journalList}">
							<table class="table table-striped table-bordered" style="width: 95% !important; margin-left: 25px;">
								<tr>
									<td><b><spring:message code="label.fdID" /></b></td>
									<td><b><spring:message code="label.journalDate" /></b></td>
									<td><b><spring:message code="label.particulars" /></b></td>
									<td><b><spring:message code="label.debitAmount" /></b></td>
									<td><b><spring:message code="label.creditAmount" /></b></td>
									<%-- <td><b><spring:message code="label.select" /></b></td> --%>
								</tr>
								<c:forEach items="${journalList}" var="journal">

									<tr>
										<td><b> <c:out value="${journal.depositId}"></c:out></b></td>
										<td><b> <c:out value="${journal.journalDate}"></c:out></b></td>
										<td><b> <c:out value="${journal.particulars}"></c:out></b></td>
										<td><b> <c:out value="${journal.debitAmount}"></c:out></b></td>
										<td><b> <c:out value="${journal.creditAmount}"></c:out></b></td>
									</tr>
								</c:forEach>
							</table>

						</c:if>


					</table>
				
				    <div class="form-group">
						<div class="col-md-offset-4 col-md-8">
							<input type="submit" class="btn btn-info" id="goBtn" onclick="viewLedger()"
								value="<spring:message code="label.viewLedger"/>"> 
						</div>
					</div>

				</form:form>
			</div>

		</div>
		
		<script>
		
		function viewLedger(){
			
			
			var fdAccountNo = document.getElementById('fdAccountNo').value;
			if(fdAccountNo==null || fdAccountNo ==''){
				document.getElementById('fdAccountNo').style.borderColor = "red";
				document.getElementById('fdAccountNoError').style.display = 'block';
				return false;
				
			}
			else{
				document.getElementById('fdAccountNo').style.borderColor = "blue";
			}
			
			
			$("#jounalForm").attr("action", "viewLedger?fdAccountNo="+fdAccountNo);
			$("#jounalForm").submit();
		}

		</script>
	