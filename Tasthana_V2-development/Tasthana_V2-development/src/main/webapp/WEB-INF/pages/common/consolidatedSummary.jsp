<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<script>
	
</script>
<div class="right-container" id="right-container">
	<div class="container-fluid">
		<div class="add-modeOfPayment">

			<form:form class="form-horizontal" id="consolidatedSummary"
				name="consolidatedSummary" action="consolidatedSummary"
				autocomplete="off" method="post" commandName="consolidatedSummary">
				<input type="hidden" name="menuId" value="${menuId}" id="menuId" />
				<div class="list-of-modeOfPayment">
					<div class="header_customer">
						<h3>Consolidated Summary</h3>
					</div>

					<div class="list-of-rates-table">
						<span class="counter pull-right"></span>

						<div>

							<div class="row">

								<label class="col-md-2">Branch Wise<span
									style="color: red">*</span>
								</label>
								<div class="col-md-2">
									<select class="myform-control" id = "branch">
										<option value="">Select</option>
										<c:forEach items="${branches}" var="branch">
											<option value="${branch.id }">${branch.branchName}</option>
										</c:forEach>
									</select>

								</div>

							</div>
							<div class="row">
							
							<label class="col-sm-2">To Date<span style="color: red">*</span>
								</label>
								<div class="col-sm-2">
									<input name="toDate" id="toDate" readonly
										placeholder="Select Date"
										class="myform-control datepicker-here" />
								</div>
							
								<label class="col-sm-2 from-group">From Date<span
									style="color: red">*</span>
								</label>
								<div class="col-sm-2">
									<input name="fromDate" id="fromDate" readonly
										placeholder="Select Date"
										class="myform-control datepicker-here" />
								</div>
								
								<div class="col-md-offset-4 col-md-8"
									style="padding-bottom: 22px;">
									<input type="button" size="3"
										onclick="return findByFromDateAndToDateTotalDeposits()" value="Go"
										class="btn btn-primary">

								</div>
							</div>

						</div>
						
						<div>
						<div class ="col-md-12 form-group">
						<label class ="col-md-6">Total No. Deposit Opened</label>
						<input readonly="readonly" value="0" id="openDepositCount" class="col-md-6 myform-control" style="width: 22%;">
						</div>
						<div  class ="col-md-12 form-group">
						<label class ="col-md-6">Total Payment Received</label>
						<input readonly="readonly" value="0" class="col-md-6 myform-control" style="width: 22%;" id="totalPaymentReceived">
						</div>
						<div  class ="col-md-12 form-group">
						<label class ="col-md-6">Total Payment withdraw</label>
						<input readonly="readonly" value="0" class="col-md-6 myform-control" style="width: 22%;" id="totalWithdrawAmount">
						</div>
						
						<div  class ="col-md-12 form-group">
						<label class ="col-md-6">Total Amount paid by bank</label>
						<input readonly="readonly" value="0" class="col-md-6 myform-control" style="width: 22%;">
						</div>
						
						<div  class ="col-md-12 form-group">
						<label class ="col-md-6">Total No. of deposit matured</label>
						<input readonly="readonly" value="0" class="col-md-6 myform-control" style="width: 22%;" id="maturedDepositCount">
						</div>
						
						<div class ="col-md-12 form-group">
						<label class ="col-md-6">Total No. of deposit premature closed</label>
						<input readonly="readonly" value="0" id="closedDepositCount" class="col-md-6 myform-control" style="width: 22%;">
						</div>
						
						<div  class ="col-md-12 form-group">
						<label class ="col-md-6">Total interest  given</label>
						<input readonly="readonly" value="0" class="col-md-6 myform-control" style="width: 22%;" id="totalIntrest">
						</div>
						</div>
					</div>

				</div>
			</form:form>
		</div>
	</div>
	
</div>

<script>
	$(document).ready(function() {

	});

function findByFromDateAndToDateTotalDeposits(){

	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var branchOptional = $("#branch").val();

	if(fromDate == "" || toDate == ""){
	alert("Please select Dates !");
		
		return false;
		}
	$.ajax({  
	    type: "GET",  
	    async: false,
	    url: "<%=request.getContextPath()%>/common/findByFromDateAndToDateTotalDeposits"
		    +"?branch="+branchOptional+"&fromDate="+fromDate+"&toDate="+toDate, 
	    contentType: "application/json",
	    dataType: "json",
	    success: function(response){  
	    	$("#openDepositCount").val(response.openDepositCount);
	    	$("#closedDepositCount").val(response.closedDepositCount);
	    	$("#totalPaymentReceived").val(response.totalPaymentReceived);
	    	$("#totalWithdrawAmount").val(response.totalWithdrawAmount);
	    	$("#maturedDepositCount").val(response.maturedDepositCount);
	    	$("#totalIntrest").val(response.totalIntrest);
	    	$("#totalAmountPaidByBank").val(response.totalAmountPaidByBank);
	    	

		  	
	    },  
	    error: function(e){  
	    	 $('#error').html("Error occured!!")
	    	 // window.loginDateForFront = getTodaysDate();
	    }  
	  });  

}

	
</script>