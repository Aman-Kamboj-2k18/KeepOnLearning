<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!-- To fetch the request url -->
<c:set var="req"
	value="${requestScope['javax.servlet.forward.request_uri']}" />
<c:set var="baseURL" value="${fn:split(req,'/')}" />



<div class="right-container" id="right-container">
	<div class="container-fluid">


		<div class="Flexi_deposit">

			<div class="header_customer">
				<h3>
 					<spring:message code="label.glConfiguration" />

				</h3>
			</div>
			<div class="Success_msg">
				<div class="successMsg"
					style="text-align: center; color: green; font-size: 18px;">
					<h2>${success}</h2>
				</div>
			</div>
			<div class="Success_msg">
				<div class="successMsg"
					style="text-align: center; color:red; font-size: 18px;">
					<h2>${error}</h2>
				</div>
			</div>
			


			<div class="flexi_table">
				<form:form method="post" class="form-horizontal" name="glConfigurationForm"
					commandName="glConfigurationForm" id="glConfigurationFm">
					
					<input type="hidden" id="glCodeList" name="glCodeList"> 
					<input type="hidden" id="glMethodList" name="glMethodList"> 
						<input type="hidden" id="glNumberList" name = "glNumberList"> 
<!-- 					<div class="col-md-6"> -->

<!-- 						<div class="form-group"> -->
<%-- 							<label class="col-md-4 control-label">Current/<spring:message --%>
<%-- 									code="label.savingAccountNo" /></label> --%>
<!-- 							<div class="col-md-6"> -->
								
<!-- ARPITA -->
<!-- 							</div> -->

<!-- 						</div> -->
<!-- 					</div> -->
				</form:form>
				
				<table class="table data jqtable example" id="my-table" style="width: 98%; margin: auto 7px;">
						<thead>
							<tr>
<!-- 							<th>Id</th> -->
								<th>GL Account</th>
								<th>GL Code</th>
<!-- 							<th>GL No.</th> -->
								<c:if test = "${glConfigurationCountById > 0 }">
								<th>Action</th>
								</c:if>
							</tr>
						</thead>
						
						<c:if test="${! empty glConfigurationList}">
							<c:forEach items="${glConfigurationList}" var="glConfiguration"
								varStatus="dl">
								<tr>
								
<%-- 									<td class="Id" aa="${glConfiguration.id}"><c:out value="${glConfiguration.id}"></c:out></td> --%>
									<td><c:out value="${glConfiguration.glAccount}"></c:out></td>
									<td> 
										<input name="glCode" 
												class="input form-control" 
												id="glCode_[${glConfiguration.glAccount}]" value ="${glConfiguration.glCode}" />

									</td>
<!-- 									<td>  -->
<!-- 										<input name="glNumber"  -->
<!-- 												class="input form-control"  -->
<%-- 												id="glNumber_[${glConfiguration.id}]" value ="${glConfiguration.glNumber}"/> --%>

<!-- 									</td> -->
									
									 <c:if test="${glConfigurationCountById > 0}"> 
									<td><button type="button" class="btn btn-info"  data-toggle="modal" data-target="#myModal" onclick="setValue('${glConfiguration.id}','${glConfiguration.glAccount}','${glConfiguration.glCode}','${glConfiguration.glNumber}')">Edit</button></td>
								 	 </c:if> 
									
	
	
								</tr>
							</c:forEach>
						</c:if>
					</table>
			
				<!-- <input style="margin-left: 400px; margin-bottom: 25px;" type="button" onclick="addRow()"
					id="add" class="btn btn-success" value="Add" />  -->
					<c:if test="${glConfigurationCountById > 0}">
				
					<button type="button" style="margin-left: 519px; margin-bottom: 25px;" class="btn btn-success" data-toggle="modal" data-target="#myModal" onclick="clearValue()">Add</button>
					</c:if>
					<c:if test="${glConfigurationCountById == 0}">
					<input style="margin-left: 522px; margin-bottom: 25px;" type="button" onclick="submit()"
					id="submit" class="btn btn-success" value="Save" /> 
					</c:if>
			</div>
		</div>
	</div>

</div>
<div class="container">
				<!-- Modal -->
				<div class="modal fade" id="myModal" role="dialog">
					<div class="modal-dialog">

						<!-- Modal content-->
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h4 class="header_customer">GL Configuration Edit/Update</h4>
							</div>
							<div class="modal-body">

								<form:form method="post" class="form-horizontal"
									commandName="glConfigurationForm" name="glConfigurationFormEdit"
									id="glConfigurationFormEdit" autocomplete="false">
								<form:input path="id" id="glId" type="hidden"
											class="input form-control" />
									<div class="">
										<label>Payment Method</label>

										<form:input path="glAccount" id="glMethod"
											class="input form-control" readonly="true" requried = "true"/>
										<br>
									</div>
									<div class="">
										<label>GL Code</lable> <form:input path="glCode" id="glCode"
												class="input form-control" requried = "true"/> <br>
									</div>
<!-- 									<div class=""> -->
<!-- 										<label>GL Number </label> -->

<%-- 										<form:input path="glNumber" id="glNumber" --%>
<%-- 											class="input form-control" requried = "true"/> --%>
<!-- 										<br> -->
<!-- 									</div> -->



									<input type="button" onclick="editSubmit()"class="btn btn-info add-row" value="Save" />
									<span id ="errorMsg" style="color: red"></span>
								</form:form>

							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-danger"
									data-dismiss="modal">Close</button>
							</div>
						</div>

					</div>
				</div>

			</div>











<script>
function clearValue() {
	debugger
	$('#glMethod').prop('readonly',false);
	$('#glMethod').css("border" ,"1px solid grey");
	$('#glCode').css("border" ,"1px solid grey");
	$('#glNumber').css("border" ,"1px solid grey");
	$("#errorMsg").text("");
	$('#glId').val("");
	$('#glMethod').val("");
	$('#glCode').val("");
	$('#glNumber').val("");
	
}
function setValue(id , glAccount , code , number) {
	$("#glMethod").attr("readonly", true);
	$("#errorMsg").text("");
	$('#glMethod').css("border" ,"1px solid grey");
	$('#glCode').css("border" ,"1px solid grey");
	$('#glNumber').css("border" ,"1px solid grey");
	$('#glId').val(id);
	$('#glMethod').val(glAccount);
	$('#glCode').val(code);
	$('#glNumber').val(number);
	
}
function submit()
{
	debugger;
	var succ = false;
	
	var glCodeArray = new Array();
	var glNumberArray = new Array();
	$("input[name=glCode]").each(function() {
		if($(this).val()!="")
			{
				var rowVal = $(this).attr("id") + "|" + $(this).val();
				
				glCodeArray.push(rowVal);
			}
	});
	$("input[name=glNumber]").each(function() {
		if($(this).val()!="")
			{
				var rowVal = $(this).attr("id") + "|" + $(this).val();
				glNumberArray.push(rowVal);
			}
	});
	

	//withdrawPenaltyList
	var glCodeList = JSON.stringify(glCodeArray);
	var glNumberList = JSON.stringify(glNumberArray);
	

	$("#glCodeList").val(glCodeList);
	$("#glNumberList").val(glNumberList);
	
	/* var method=$('glMethod_1').val();
	var glCode = $('glCode_1').val();
	var glNumber = $('glNumber_1').val();
	
	$("#glCodeList").val(glCode);
	$("#glNumberList").val(glNumber);
	$("#glNumberList").val(glMethod); */
	$("#glConfigurationFm").attr("action","saveGLConfiguration");
	$("#glConfigurationFm").submit();

// 	if(glCodeArray.length == 0)
// 		{
// 		 document.getElementById('succMsg').innerHTML='';
// 			document.getElementById('blankFormError').innerHTML='Please enter amount for atleast one deposit.<br>';
// 			return false;
// 		}
}
function editSubmit()
{
	debugger
	$("#errorMsg").text("");
	$('#glMethod').css("border" ,"1px solid grey");
	$('#glCode').css("border" ,"1px solid grey");
	$('#glNumber').css("border" ,"1px solid grey");
	debugger;
	var method=$('#glMethod').val();
	var glCode = $('#glCode').val();
	var glNumber = $('#glNumber').val();
	
	if(method == ""){
		$("#errorMsg").text("Please enter all fields *");
		$('#glMethod').css("border" ,"1px solid red");
	 	return false;
		
	}
	
	if(glCode == ""){
		$("#errorMsg").text("Please enter all fields *");
		$('#glCode').css("border" ,"1px solid red");
	 	return false;
	}
	
	if(glNumber == "" ){
		$("#errorMsg").text("Please enter all fields *");
		$('#glNumber').css("border" ,"1px solid red");
	 	return false;
		
	}
	$("#errorMsg").text("");
	$('#glMethod').css("border" ,"1px solid grey");
	$('#glCode').css("border" ,"1px solid grey");
	$('#glNumber').css("border" ,"1px solid grey");
	$("#glConfigurationFormEdit").attr("action","editSaveGLConfiguration");
	$("#glConfigurationFormEdit").submit();

}

$('input[name="glCode"]').on('keypress', function (event) {
	debugger
    var regex = new RegExp("^[ A-Za-z0-9_@./#&+-]*$");
    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
    if (!regex.test(key)) {
       event.preventDefault();
       return false;
    }
});

$('input[name="glNumber"]').on('keypress', function (event) {
	debugger
    var regex = new RegExp("^[ A-Za-z0-9_@./#&+-]*$");
    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
    if (!regex.test(key)) {
       event.preventDefault();
       return false;
    }
});

function addRow()
{
	$('#add').hide();
	$('#SaveGL').show();
	var value=parseInt($('.Id:last').attr('aa'))+1;
	var trHTML='<tr style=""><td>'+value+'</td><td><input style="border:1px solid blue;" name="" class="input form-control" id="glMethod_1" value=""></td><td><input style="border:1px solid blue;" name="glCode" class="input form-control" id="glCode_1"></td><td><input style="border:1px solid blue;" name="glNumber" class="input form-control" id="glNumber_1"></td></tr>';
	$('#my-table').append(trHTML);
}


/* $( document ).ready(function() {
  alert("${glConfigurationCountById}")
}); */











</script>

		<style>
.form-control {
	background: whitesmoke;
	color: #000;
	cursor: pointer;
}
</style>