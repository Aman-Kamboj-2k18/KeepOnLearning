<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="right-container" id="right-container">
	<div class="container-fluid">

		<div class="Success_msg">
			<div class="successMsg"
				style="text-align: center; color: red; font-size: 18px;">
				<h4>${error}</h4>

			</div>
		</div>


		<div class="Flexi_deposit">

			<div class="header_customer" style="margin-bottom: 30px;">
				<h3>
					Joint/Consortium
					<spring:message code="label.fixedDeposit" />
					<small><spring:message code="label.searchCustomer" /></small>
				</h3>
			</div>
			<div class="Success_msg"></div>
			<div class="flexi_table">
				<form:form class="form-horizontal" id="fdForm" name="fixedDeposit"
					commandName="fixedDepositForm">

					<form:input type="hidden" path="id" id="id" />
					<input type="hidden" name="customerDetails" id="customerDetails" />
					<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.typeOfDeposit" /></label>
						<div class="col-md-6">
							<label for="radio"><form:radiobutton path="depositType"
									id="joint" value="JOINT" checked="checked"
									onchange="noPermition(this.value)" />Joint</label>&nbsp;&nbsp; <label
								for="radio"><form:radiobutton path="depositType"
									id="consortium" value="CONSORTIUM"
									onchange="noPermition(this.value)" />Consortium</label>
						</div>
					</div>
<div class="form-group">
						<label class="col-md-4 control-label">Customer ID</label>
						<div class="col-md-6">
							<form:input path="customerID" id="customerId"
								class="form-control" placeholder="" />
							<span id="customerNameError" style="display: none; color: red;"><spring:message
									code="label.validation" /></span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.customerName" /></label>
						<div class="col-md-6">
							<form:input path="customerName" id="customerName"
								class="form-control" placeholder="" />
							<span id="customerNameError" style="display: none; color: red;"><spring:message
									code="label.validation" /></span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.contactNumber" /></label>
						<div class="col-md-6">
							<form:input path="contactNum" id="contactNumber"
								class="form-control" placeholder="" />
							<span id="contactNumberError" style="display: none; color: red;"><spring:message
									code="label.validation" /></span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.email" /></label>
						<div class="col-md-6">
							<form:input path="email" id="email" class="form-control"
								placeholder="" />
							<span id="emailError" style="display: none; color: red;"><spring:message
									code="label.validation" /></span>
						</div>
					</div>

					<div class="form-group">
						<span id="errorMsg"
							style="display: none; color: red; margin-left: 385px; margin-bottom: 10px;">Please
							fill at least one field to search the customer. </span>
						<div class="col-md-offset-4 col-md-8">
							<input type="button" onclick="searchCustomer()"
								class="btn btn-info"
								value="<spring:message code="label.search"/>">

						</div>
					</div>
				</form:form>

				<div style="clear: both; margin-top: 35px; margin-bottom: -5px;"
					class="col-md-12">
					<h4>Customers</h4>
				</div>
				<div
					style="clear: both; width: 625px; border-top: 1px dotted #cfcfcf; margin-bottom: 15px; margin-left: 15px;"></div>
				<table align="center">

					<!-- 						<tr> -->
					<!-- 							<td><br></td> -->
					<!-- 						</tr> -->

					<%-- 						<c:if test="${! empty customerList}"> --%>
					<table id="tableCustomerList"
						class="table table-striped table-bordered"
						style="width: 95%; margin-left: 20px;">
						<tr style="font-size: 0.85em;">
							<td><b><spring:message code="label.customerName" /></b></td>
							<%-- <td><b><spring:message code="label.customerId" /></b></td> --%>
							<td><b><spring:message code="label.email" /></b></td>
							<td><b><spring:message code="label.contactNumber" /></b></td>
							<td><b><spring:message code="label.dateOfBirth" /></b></td>

							<%-- <td><b><spring:message code="label.accountNo" /></b></td> --%>
							<%-- 									<td><b><spring:message code="label.select" /></b></td> --%>
							<td><b>Action</b></td>
						</tr>
					</table>



				</table>

				<div style="clear: both; margin-top: 35px; margin-bottom: -5px;"
					class="col-md-12">
					<h4>Deposit Account Holders</h4>
				</div>
				<div
					style="clear: both; width: 625px; border-top: 1px dotted #cfcfcf; margin-bottom: 15px; margin-left: 15px;"></div>

				<table id="tableAccountHolderList"
					class="table table-bordered table-striped table-hover jqtable example"
					align="center" id="my-table" style="width: 95%; margin-left: 20px;">
					<thead>
						<tr class="success" style="font-size: 0.85em;">
							<th width="3%"
								style="background: #efefef; color: #333; vertical-align: text-top;">Serial</th>
							<th width="13%"
								style="background: #efefef; color: #333; vertical-align: text-top;">Name</th>
							<th width="15%"
								style="background: #efefef; color: #333; vertical-align: text-top;">Contact</th>
							<th width="5%"
								style="background: #efefef; color: #333; vertical-align: text-top;">Gender</th>
							<th width="22%"
								style="background: #efefef; color: #333; vertical-align: text-top;">Email</th>
							<th width="20%"
								style="background: #efefef; color: #333; vertical-align: text-top;">Address</th>
							<th width="6%"
								style="background: #efefef; color: #333; vertical-align: text-top;">Relationship</th>
							<th width="13%"
								style="background: #efefef; color: #333; vertical-align: text-top;">Primary
								Acc Holder</th>
							<th width="3%"
								style="background: #efefef; color: #333; vertical-align: text-top;">Remove</th>

						</tr>
					</thead>
				</table>



				<div class="col-md-offset-4 col-md-8" style="margin-bottom: 25px;">

					<input type="submit" class="btn btn-info" data-toggle="tooltip"
						title="Please first select the customer to click on search"
						id="goBtn" onclick="validateForm()" disabled="true"
						value="<spring:message code="label.go"/>">
				</div>



			</div>

		</div>
	</div>
</div>

<%
	Cookie[] cookies = request.getCookies();
	//check null because there are chances that there are no cookies
	if (cookies != null) {
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			cookie.setMaxAge(0);
			response.addCookie(cookie);

		}
	}
%>


<script>
			 $(document).ready(function() {
				 debugger;
				 addCustomer("${customerInfo.id}");
				 document.getElementById("goBtn").disabled = true;
				$('[data-toggle="tooltip"]').tooltip();
				
				
			});
			function searchCustomer() {
			
					var customerId =document.getElementById('customerId').value;
				    var customerName = document.getElementById('customerName').value;
				    var contactNumber = document.getElementById('contactNumber').value;
				    var emailId = document.getElementById('email').value;

				    if(customerId == "" && customerName=="" && emailId=="" && contactNumber == ""){
				     document.getElementById('errorMsg').style.display='block';
				     document.getElementById('errorMsg').value="Please Insert at least one field.";
				     event.preventDefault();
				    }
				    else
				    {   

				    	// data string
					    var dataString = 'customerId='+ customerId
					                    + '&customerName=' + customerName        
					                    + '&contactNumber=' + contactNumber
					                    + '&emailId=' + emailId;
						

						// delete all rows except the first row from the table
						$("#tableCustomerList").find("tr:gt(0)").remove();

						
						  $.ajax({  
						    type: "GET",  
						    url: "<%=request.getContextPath()%>/bnkEmp/findCustomer", 
						    contentType: "application/json",
						    dataType: "json",
						    data: dataString,  
					        async: false,
						    success: function(response){  
						    	var trHTML = '';
								for (i = 0; i < response.length; ++i) {
								   var data = response[i];
								   var date=new Date(data.dateOfBirth);
								   
								   var year = date.getFullYear();

								   var month = (1 + date.getMonth()).toString();
								   month = month.length > 1 ? month : '0' + month;

								   var day = date.getDate().toString();
								   day = day.length > 1 ? day : '0' + day;
								   
								   date= day + '-' + month + '-' + year;
								   
								   var customerInfoId = "${customerInfo.id}";
								   if(customerInfoId == data.id){
									   addCustomer(customerInfoId);
									  
								   }else{
								   trHTML += '<tr><td>' + data.customerName + '</td><td>' + data.email  + '</td><td>' + data.contactNum
								   + '</td><td>' + date + '</td><td>'
								   + '<input type="button" value="Add Customer" id="'+ data.id +'" onclick="addCustomer(this.id)"/>'
								   + '</td></tr>';
								   }
								   
								}
//  								+'<input type=radio id=custId value=' + data.id + 'onclick=onclickRadio()'  +
								$('#tableCustomerList').append(trHTML);
								
								$('#tableCustomerList').addClass("jqtable example1");
								
								ApplyPaging();
						        
						    },

						    error: function(e){  
						    	console.log(e)
						    	
						    	 $('#error').html("Error occured!!")

						    }  
						  });  

				    }
				}
			 
			
			var typeOfDeposit = 0;
			var customerA=[];
			function addCustomer(custId) {
				debugger;
				var customerId = custId;
				if("${customerInfo.id}" == customerId && customerA.length > 0){
					return false;
				}
				if(customerA.includes(customerId)){
					alert("Customer Already selected. Please select another customer.");
					return false;
				}else{
					customerA.push(customerId);
				}
				
				
				var dataString = 'customerId='+ customerId;

				var joint = $("#joint").val();
				
				var jointFlag = $("#joint").is(":checked");
				var consortium = $("#consortium").val();
				var consortiumFlag = $("#consortium").is(":checked");

				if(typeOfDeposit>=2 && joint == "JOINT" && jointFlag == true){
			
					alert("Maximum 2 account holders can be added in Joint Deposit. If you want to add more account holders, please change it to Consotium Deposit.");                            
					return false;
				}
				
				

				if(typeOfDeposit>=10 && consortium == "CONSORTIUM" && consortiumFlag == true){
					alert("In Consortium Deposit, maximum 10 deposit holders can be added.");
					return false;
				}
				
				typeOfDeposit++;
			
				  $.ajax({  
					    type: "GET",  
					    url: "<%=request.getContextPath()%>/bnkEmp/findCustomerById",
					    
					contentType : "application/json",
					dataType : "json",
					data : dataString,
					async : false,
					success : function(response) {
						var trHTML = '';
						var data = response;

						var customerId_ = "${customerInfo.id}";
						if(customerId_ == customerId){
							debugger;
							
							trHTML = '<tr><td>'
								+ data.id
								+ '</td><td>'
								+ data.customerName
								+ '</td><td>'
								+ data.contactNum
								+ '</td><td>'
								+ data.gender
								+ '</td><td>'
								+ data.email
								+ '</td><td>'
								+ data.address
								+ '</td><td>'
								+ '<input type="text" id="relationShip'+ data.id +'"/>'
								+ '</td><td>'
								+ '<input type="radio" value = "true"  checked class = "prim" name="prim" id="prim'
								+ data.id
								+ '" onchange = "depositAcccountHolder(this.value,'
								+ data.id
								+ ')" value="false"/>'
								+ '</td><td>'
								+ '<input type="button" disabled value="Remove" id="'+data.id+'" onclick="removeCustomer(this)"/>'
								+ '</td></tr>';
						
								
							
						}else{
						trHTML = '<tr><td>'
								+ data.id
								+ '</td><td>'
								+ data.customerName
								+ '</td><td>'
								+ data.contactNum
								+ '</td><td>'
								+ data.gender
								+ '</td><td>'
								+ data.email
								+ '</td><td>'
								+ data.address
								+ '</td><td>'
								+ '<input type="text" id="relationShip'+ data.id +'"/>'
								+ '</td><td>'
								+ '<input type="radio" disabled class = "prim" name="prim" id="prim'
								+ data.id
								+ '" onchange = "depositAcccountHolder(this.value,'
								+ data.id
								+ ')" value="false"/>'
								+ '</td><td>'
								+ '<input type="button" value="Remove" id="'+data.id+'" onclick="removeCustomer(this)"/>'
								+ '</td></tr>';
						}

						$('#tableAccountHolderList').append(trHTML)

					},

					error : function(e) {
						console.log(e)

						$('#error').html("Error occured!!")

					}
				});

		document.getElementById("goBtn").disabled = false;
	}

	function noPermition(value) {
		debugger
		if (typeOfDeposit >= 3 && value == "JOINT") {
			alert("Can Not Change, 10 CONSORTIUM");
			// 					$("#joint").v;
			// 					return false;
		}

	}

	var depositHonderAccountValue = false;
	var depositHonderAccountValueId = "";
	var primaryCustomerId = "";
	function depositAcccountHolder(value, id) {
		debugger
		depositHonderAccountValueId = "prim" + id;

		/*  var tableData_ = document.getElementById('tableAccountHolderList');
		    var numberOfRows_ = tableData_.rows.length;
		    
		    for (var i = 1; i < numberOfRows_; i += 1)
		    {
		    	  var row = tableData_.rows[i];
		    
		    } */
		$(".prim").val(false);

		$("#prim" + id).val(true);
		depositHonderAccountValue = $("#prim" + id).val();
		primaryCustomerId = id;
	}

	function removeCustomer(customer) {
		debugger;
		var obj=$(customer);
		
		var customerId = obj.attr('id');
		var index = customerA.indexOf(customerId);
		if (index > -1) {
			customerA.splice(index, 1);
		}
		typeOfDeposit--;
		var i = customer.parentNode.parentNode.rowIndex;
		document.getElementById("tableAccountHolderList").deleteRow(i);
		if(customerA.length == 1){
			document.getElementById("goBtn").disabled = true;
		}
		
		
	}
	function onclickRadio() {
		document.getElementById("goBtn").disabled = false;
	}

	function validateForm() {
		debugger;
		var myMap = new Map();

		// 				alert("Value  : "+depositHonderAccountValue+"  : "+depositHonderAccountValueId);
		var tableData = document.getElementById('tableAccountHolderList');
		var numberOfRows = tableData.rows.length;
		var bodyStart = '{"data":[';
		for (var i = 1; i < numberOfRows; i += 1) {
			var row = tableData.rows[i];
			var id = row.cells[0].innerText;
			var primaryAccount = $("#prim" + id).val();
			var relationship = $("#relationShip" + id).val();
			if("${customerInfo.id}" != id && relationship == ""){
				alert("Secondry holder account relationship can not be empty !")
				return false;
				 
			}
			

			bodyStart += '{"id":"' + id + '",';
			bodyStart += '"isPrimaryHolder":"' + primaryAccount + '",';
			bodyStart += '"relationship":"' + relationship + '"},';

			//bodyStart += '{"'+id+'":"'+relationship+'"},';

			myMap.set(id, relationship);

			//alert(real);
			var primHolder = document.getElementById('prim' + id).value;
		}

		var comaIndex = bodyStart.indexOf(",");
		bodyStart = bodyStart.substring(0, bodyStart.length - 1);

		// bodyStart.replaceAt(comaIndex, " ");

		bodyStart += ']}';

		document.getElementById("id").value = primaryCustomerId;

		// document.getElementById("customerDetails").value = myMap;

		document.getElementById("customerDetails").value = bodyStart;

		$("#fdForm").attr("action", "jointConsortiumDeposit");
		$("#fdForm").submit();

	}

	String.prototype.replaceAt = function(index, replacement) {

		return this.substr(0, index) + replacement
				+ this.substr(index + replacement.length);
	}
	$(document).ready(function() {
		//$('#cusId').checked=false;
		//document.getElementById('cusId').checked = false;
	});
</script>