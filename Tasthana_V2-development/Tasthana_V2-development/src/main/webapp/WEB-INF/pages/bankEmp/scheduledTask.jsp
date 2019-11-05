<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<
<script type="text/javascript">
	function showPanel() {
		document.getElementById('dailyPanel').style.display = 'none';
		document.getElementById('monthlyPanel').style.display = 'none';
		document.getElementById('weeklyPanel').style.display = 'none';

		var schedulerDuration = document.getElementById('schedulerDuration').value;
		if (schedulerDuration == "Annually") {
			document.getElementById('monthlyPanel').style.display = 'block';
			document.getElementById('dailyPanel').style.display = 'block';

		} else if (schedulerDuration == "Monthly") {
			document.getElementById('dailyPanel').style.display = 'block';

		} else if (schedulerDuration == "Quarterly") {
			document.getElementById('dailyPanel').style.display = 'block';

		} else if (schedulerDuration == "Weekly") {
			document.getElementById('weeklyPanel').style.display = 'block';

		}

	}
	$(document).ready(function() {

		showPanel();
	});

	function onchangeJob(value) {

		$("#scheduleTaskForm").attr("action",
				"schedulerConfiguration?jobName=" + value);
		$("#scheduleTaskForm").submit();

	}

	var lastday = function(y, m) {
		return new Date(y, m + 1, 0).getDate();
	}
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

	function val() {
		
		document.getElementById('dayOfMonth').style.borderColor = "black";
		
		var dailyPanel = document.getElementById('dailyPanel').style.display;
		var monthlyPanel = document.getElementById('monthlyPanel').style.display;
		if (dailyPanel == 'block' && monthlyPanel == 'none') {
			if(isNaN(parseInt($("#dayOfMonth").val())))
				return false;
			
			var date = getTodaysDate();
			var maxDay = lastday(date.getFullYear(), date.getMonth());
			
			
			if (parseInt($("#dayOfMonth").val()) > maxDay || parseInt($("#dayOfMonth").val())<=0) {
				document.getElementById('dayOfMonth').style.borderColor = "red";
				return false;
			}

		} else if (dailyPanel == 'block' && monthlyPanel == 'block') {

			if(isNaN(parseInt($("#dayOfMonth").val()))){
				document.getElementById('dayOfMonth').style.borderColor = "red";
				return false;
				
			}
				
			if(isNaN(parseInt($("#monthOfYear").val()))){
				document.getElementById('monthOfYear').style.borderColor = "red";
				return false;
					
			}
				
			monthOfYear = document.getElementById("monthOfYear").value;
			var date = getTodaysDate();
			var maxDay=lastday(date.getFullYear(), parseInt(monthOfYear) - 1);

			if (parseInt($("#dayOfMonth").val()) > maxDay) {
				document.getElementById('dayOfMonth').style.borderColor = "red";
				return false;
			}

		}

	}
</script>
<div class="right-container" id="right-container">
	<div class="container-fluid">


		<div class="add-customer">
			<form:form class="form-horizontal" id="scheduleTaskForm"
				autocomplete="off" action="saveScheduledTask"
				commandName="scheduleTaskForm" onsubmit="return val()">

				<div class="newcustomer_border">
					<div class="header_customer">
						<h3>
							<spring:message code="label.scheduletask" />
						</h3>
					</div>
					<div class="successMsg">
						<b><font color="green">${success}</font></b>
					</div>
			
					<div class="add-customer-table">
						<div class="col-md-6">

							<div class="form-group">
								<label class="col-md-4 control-label">Task Name<span
									style="color: red">*</span></label>
								<div class="col-md-8">
									<form:select path="jobName" id="jobName" class="myform-control"
										onchange="onchangeJob(this.value)">

										<form:option value="Interest Calculation">
											<spring:message code="label.INTERESTCALCULATION" />
										</form:option>
										<form:option value="Payoff Calculation">
											<spring:message code="label.PAYOFFCALCULATION" />
										</form:option>
										<form:option value="Auto Deduction">
											<spring:message code="label.AUTODEDUCTION" />
										</form:option>
										<form:option value="Auto Deposit Creation">
											<spring:message code="label.AUTODEPOSITCREATION" />
										</form:option>
										<form:option value="Reverse EMI Deduction">
											<spring:message code="label.REVERSEEMIDEDUCTION" />
										</form:option>
										<form:option value="Modification Penality Calculation">
											<spring:message code="label.MODIFICATIONPENALITY" />
										</form:option>
										<form:option value="Transfer Money on Maturity">
											<spring:message code="label.TRANSFERMONEYONMATURITY" />
										</form:option>
									</form:select>

								</div>
							</div>


							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.schedule" /><span style="color: red">*</span></label>
								<div class="col-md-8">
									<form:select path="schedulerDuration" id="schedulerDuration"
										class="myform-control" onChange="showPanel()">
										<form:option value="Daily">
											<spring:message code="label.once" />
										</form:option>
										<form:option value="Weekly">
											<spring:message code="label.weekly" />
										</form:option>
										<form:option value="Fortnightly">
											<spring:message code="label.fortnightly" />
										</form:option>
										<form:option value="Monthly">
											<spring:message code="label.monthly" />
										</form:option>
										<form:option value="Quarterly">
											<spring:message code="label.quarterly" />
										</form:option>
										<form:option value="Annually">
											<spring:message code="label.annually" />
										</form:option>
									</form:select>

								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.startdate" /></label>

								<div class="col-md-8">
									<form:input path="startdate" id="startdate" readonly="true"
										placeholder="Select Date"
										class="myform-control datepicker-here" />


								</div>
							</div>

							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.enddate" /></label>
								<div class="col-md-8">
									<form:input path="enddate" id="enddate" readonly="true"
										placeholder="Select Date"
										class="myform-control datepicker-here" />


								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.starttime" /><span style="color: red">*</span></label>
								<div class="col-md-8">
									<form:select path="starttime" id="starttime"
										style="width:44px;">
										<c:forEach var="i" begin="0" end="23">
											<form:option value="${i}">
											${i}
									</form:option>
										</c:forEach>
									</form:select>


								</div>
							</div>









						</div>
						<div class="col-md-6">

							<div style="margin-top: 61px;">

								<div id="monthlyPanel">
									<label class="col-md-2 control-label">Month<span
										style="color: red">*</span></label>
									<div class="col-md-4">
										<form:select path="monthOfYear" id="monthOfYear">
											<form:option selected="selected" value="1">January</form:option>
											<form:option value="2">February</form:option>
											<form:option value="3">March</form:option>
											<form:option value="4">April</form:option>
											<form:option value="5">May</form:option>
											<form:option value="6">June</form:option>
											<form:option value="7">July</form:option>
											<form:option value="8">August</form:option>
											<form:option value="9">September</form:option>
											<form:option value="10">October</form:option>
											<form:option value="11">November</form:option>
											<form:option value="12">December</form:option>
										</form:select>
									</div>
								</div>

								<div id="dailyPanel">
									<label class="col-md-2 control-label">Day<span
										style="color: red">*</span></label>
									<div class="col-md-2">
										<form:input type="number" id="dayOfMonth" path="dayOfMonth"
											style="width:34px;" />
									</div>
								</div>

								<div id="weeklyPanel">
									<label class="col-md-2 control-label">Week<span
										style="color: red">*</span></label>
									<div class="col-md-4">
										<form:select path="week" id="week">
											<form:option value="SUN" selected="selected">Sunday</form:option>
											<form:option value="MON">Monday</form:option>
											<form:option value="TUE">Tuesday</form:option>
											<form:option value="WED">Wednesday</form:option>
											<form:option value="THU">Thursday</form:option>
											<form:option value="FRI">Friday</form:option>
											<form:option value="SAT">Saturday</form:option>
										</form:select>
									</div>
								</div>
							</div>

						</div>

					</div>

				</div>
				<div class="col-sm-12">

					<div class="col-sm-3"></div>
					<div class="col-sm-3">
						<input type="submit" class="btn btn-primary" value="Submit">
					</div>
					<div class="col-sm-1"></div>
					<div class="col-sm-3">
						<input type="button" class="btn btn-warning" value="Cancel">
					</div>

				</div>
			</form:form>
		</div>



		<div style="clear: both; height: 72px;"></div>

		<div class="col-sm-12">
			<table class="table table-bordered" align="center" id="my-table">
				<thead>
					<tr>

						<th><spring:message code="label.task" /> Name</th>
						<th><spring:message code="label.startdate" /></th>
						<th><spring:message code="label.enddate" /></th>
						<th>Task Schedule Details</th>
						<th>Edit</th>

					</tr>
				</thead>
				<tbody>

					<c:if test="${! empty jobList}">
						<c:forEach items="${jobList}" var="job">

							<tr>

								<td><c:out value="${job.jobName}"></c:out></td>
								<td><fmt:formatDate value="${job.startDate}"
										pattern="dd/MM/yyyy" /></td>
								<td><fmt:formatDate value="${job.endDate}"
										pattern="dd/MM/yyyy" /></td>
								<td><c:out value="${job.cronMeaning}"></c:out></td>
								<td><a href="schedulerConfiguration?jobName=${job.jobName}">Edit</a></td>
							</tr>
						</c:forEach>
					</c:if>

				</tbody>
			</table>
			.
		</div>



	</div>

</div>

	<style>
body {
	font-family: Arial, Helvetica, sans-serif;
}

/* The Modal (background) */
.modal {
	display: none;
	position: fixed;
	z-index: 1;
	padding-top: 100px;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	overflow: auto;
	background-color: rgb(0, 0, 0);
	background-color: rgba(0, 0, 0, 0.4);
}

/* Modal Content */
.modal-content {
	background-color: #ebeef5;
	padding: 20px;
	margin-top: 150px;
	margin-left: 283px;
	margin-top: :91px;
	border: 1px solid #888;
	width: 64%;
	height: 419px;
}

/* The Close Button */
.close {
	color: #aaaaaa;
	float: right;
	font-size: 28px;
	font-weight: bold;
}

.close:hover, .close:focus {
	color: #000;
	text-decoration: none;
	cursor: pointer;
}

.form-horizontal .control-label {
	padding-top: 12px;
}
</style>