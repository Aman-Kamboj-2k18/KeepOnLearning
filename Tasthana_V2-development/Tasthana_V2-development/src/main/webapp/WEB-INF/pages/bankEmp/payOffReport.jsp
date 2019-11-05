<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div class="right-container" id="right-container">
	<div class="container-fluid">


		<div class="unSuccessfulPayOffDetails">
			<form:form action="unSuccessfulPayOffDetailsList" class="form-horizontal"
				name="unSuccessfulPayOffDetails" autocomplete="off" method="get"
				commandName="payOfForm" onsubmit="return val();">


				<div class="newcustomer_border">
					<div class="header_customer">
						<h3>
							<spring:message code="label.searchUnSuccessfulPayoffDetails" />
						</h3>
					</div>
					<div class="successMsg col-md-offset-4 col-md-8">
						<b><font color="green">${success}</font></b>
					</div>
					
					<div class="add-customer-table">

						<div class="form-group">
							<label class="col-md-4 control-label">From Date</label>
							<div class="col-md-4">
								<form:input path="unSuccessfulPayoffDetailsDateFrom" id="fromDate" readonly="true"
									placeholder="Select Date"
									class="myform-control datepicker-here" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">To Date</label>
							<div class="col-md-4">
								<form:input path="unSuccessfulPayoffDetailsDateTo" id="toDate" readonly="true"
									placeholder="Select Date"
									class="myform-control datepicker-here" />
							</div>
						</div>

					</div>
					
					<div class="successMsg col-md-offset-4 col-md-8">
						<b><font color="error">${error}</font></b>
					</div>
					<span id="futureDateError" style="display: none; color:red ">Future Date Selected</span>	
					<span id="dateError" style="display: none; color:red ">Future Date Selected</span>	
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


<script>

function getTodaysDate()
{
	var today = null;
	$.ajax({  
	    type: "GET",  
	    async: false,
	    url: "<%=request.getContextPath()%>/bnkEmp/loginDateForJsp", 
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
function val(){
			
			var canSubmit = true;
			var firstDate = document.getElementById('fromDate').value;
			var secDate = document.getElementById('toDate').value;
			var today = getTodaysDate();
			var firstDateArr= firstDate.split("/");
			var secDateArr= secDate.split("/");
			var fromDate =new Date(firstDateArr[2], firstDateArr[1]-1, firstDateArr[0]);
			var toDate =new Date(secDateArr[2], secDateArr[1]-1, secDateArr[0]);
			 
	      if (fromDate>today){ 
     	
                  $('#futureDateError').after('<p>You cannot enter a date in the future!.</p>');
                  return false;
                }
	  
	      else if(toDate>today){

                  $('#futureDateError').after('<p>You cannot enter a date in the future!.</p>');
                  return false;
	            }
	      
	      else if(fromDate>toDate){
	    	  $('#dateError').after('<p>From Date can not be grater than To Date!.</p>');
              return false;
	      }
	return true;
}

</script>



