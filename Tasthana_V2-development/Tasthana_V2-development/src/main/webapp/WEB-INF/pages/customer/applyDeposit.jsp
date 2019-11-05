<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="right-container" id="right-container" style="display:none;">
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
					Single/Joint/Consortium Deposit
					<small><spring:message code="label.searchCustomer" /></small>
				</h3>
			</div>
			<div class="Success_msg"></div>
			<div class="flexi_table">
				<form:form class="form-horizontal" id="fdForm" 
					name="fixedDeposit" commandName="fixedDepositForm" autocomplete="false">

					<form:input type="hidden" path="id" id="id"/>
					<form:input type="hidden" path="productConfigurationId" id="productConfigurationId"/>
					<input type="hidden" name = "customerDetails" id="customerDetails"/> 
					<input type="hidden" name = "productId" id="productId"/> 
						<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.depositAccountType" /></label>
								<div class="col-md-6">
								<div id="depositss">
								<label for="radio"><form:radiobutton path="depositAccountType"
											id="single" value="SINGLE" onmousedown="noPermission(this.value,event)"/>Single</label>
								<label for="radio"><form:radiobutton path="depositAccountType" checked="checked" id="joint" value="JOINT"  onmousedown = "noPermission(this.value,event)"/>Joint</label>
											<label for="radio"><form:radiobutton path="depositAccountType"
											id="consortium" value="CONSORTIUM" onmousedown= "noPermission(this.value,event)"/>Consortium</label>
											</div>
											<div id="fixedAnnuity" style="display:none;">
											<label for="radio"><input type="radio" id="fAmount"  onmousedown = "noPermission(this.value,event)" value="Amount"/>Fixed Amount EMI</label>
											<label for="radio"><input type="radio" id="fTenure"  onmousedown = "noPermission(this.value,event)" value="Tenure" />Fixed Tenure EMI</label>
											</div>
								</div>
						</div>
						<div class="annuity">
                    <div class="form-group">
						<label class="col-md-4 control-label">Customer Id</label>
						<div class="col-md-6">
							<form:input path="customerID" id="customerId" class="form-control"
								placeholder="" />
							<span id="customerNameError" style="display: none; color: red;"><spring:message code="label.validation" /></span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.customerName" /></label>
						<div class="col-md-6">
							<form:input path="customerName" id="customerName" class="form-control"
								placeholder="" />
							<span id="customerNameError" style="display: none; color: red;"><spring:message code="label.validation" /></span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.contactNumber" /></label>
						<div class="col-md-6">
							<form:input path="contactNum" id="contactNumber" class="form-control" placeholder="" />
							<span id="contactNumberError" style="display: none; color: red;"><spring:message
									code="label.validation" /></span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.email" /></label>
						<div class="col-md-6">
							<form:input path="email" id="email" class="form-control" placeholder="" />
							<span id="emailError" style="display: none; color: red;"><spring:message
									code="label.validation" /></span>
						</div>
					</div>
					
					<div class="form-group">
					<span id="errorMsg" style="display:none;color:red; margin-left: 385px;margin-bottom: 10px;">Please fill at least one field to search the customer. </span>
						<div class="col-md-offset-4 col-md-8">
							<input type="button" onclick="searchCustomer()"
								class="btn btn-info"
								value="<spring:message code="label.search"/>">
								
						</div>
					</div>
				</form:form>
				
				<div style="clear: both; margin-top: 35px; margin-bottom: -5px;" class="col-md-12">
								<h4>Customers</h4>
							</div>
							<div style="clear: both; width: 625px; border-top: 1px dotted #cfcfcf; margin-bottom: 15px; margin-left: 15px;"></div>
					<table align="center">

<!-- 						<tr> -->
<!-- 							<td><br></td> -->
<!-- 						</tr> -->

<%-- 						<c:if test="${! empty customerList}"> --%>
							<table id="tableCustomerList"  class="table table-striped table-bordered" style="width: 95%; margin-left: 20px;">
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
					<div class="jointt">
					<div style="clear: both; margin-top: 35px; margin-bottom: -5px;" class="col-md-12">
								<h4>Deposit Account Holders</h4>
							</div>
					<div style="clear: both; width: 625px; border-top: 1px dotted #cfcfcf; margin-bottom: 15px; margin-left: 15px;"></div>
							
					<table id="tableAccountHolderList" class="table table-bordered table-striped table-hover jqtable example"
								align="center" id="my-table" style="width: 95%; margin-left: 20px;">
								<thead>
									<tr class="success" style="font-size: 0.85em;">
										<th width="3%" style="background: #efefef; color: #333; vertical-align: text-top;">Serial</th>
										<th width="13%" style="background: #efefef; color: #333; vertical-align: text-top;">Name</th>
										<th width="15%" style="background: #efefef; color: #333; vertical-align: text-top;">Contact</th>
										<th width="5%" style="background: #efefef; color: #333; vertical-align: text-top;">Gender</th>
										<th width="22%" style="background: #efefef; color: #333; vertical-align: text-top;">Email</th>
										<th width="20%" style="background: #efefef; color: #333; vertical-align: text-top;">Address</th>
										<th width="6%" style="background: #efefef; color: #333; vertical-align: text-top;">Relationship</th>
										<th width="13%" style="background: #efefef; color: #333; vertical-align: text-top;">Primary Acc Holder</th>
										<th width="3%" style="background: #efefef; color: #333; vertical-align: text-top;">Remove</th>

									</tr>
								</thead>
						</table>
					
					

						<div class="col-md-offset-4 col-md-8" style="margin-bottom: 25px;;">

									<input type="button" class="btn btn-info" data-toggle="tooltip"
								title="Please first select the customer to click on search"
								id="goBtn" onclick="validateForm()" disabled="true"
 								value="<spring:message code="label.go"/>"> 
 						</div> 

</div>
			</div>
			</div>

		</div></div></div>
		
	<%
     Cookie[] cookies= request.getCookies();
     //check null because there are chances that there are no cookies
     if(cookies !=null)
     {
	       for(int i=0 ;i<cookies.length;i++ )
	       {
	         	Cookie cookie = cookies[i];
	         	cookie.setMaxAge(0);
	            response.addCookie(cookie);

	       }
      }
    %>
		
		
		<script>
		var typeCustomer ="";
		var productId ="";
		var productType ="";
		 var NRO=0;
		var PRP=0;
		var FCNR=0;
		var NRE=0;
		var RFC=0; 
		var name ="";
			 $(document).ready(function() {
				 typeCustomer = getUrlVars()["type"];
				 productId = getUrlVars()["productId"];
				 productType = getUrlVars()["productType"];
				 
 				name = getUrlVars()["name"];
				 
				 
				
				 if(name.toLowerCase().indexOf("single")>=0){
						
					 $("#single").prop("checked", true);
				 }
				 if(name.toLowerCase().indexOf("joint")>=0){
					 $("#joint").prop("checked", true);
				 }
				 if(name.toLowerCase().indexOf("consortium")>=0){
					 $("#consortium").prop("checked", true);
				 }
				 if(name.toLowerCase().indexOf("amount")>=0){
						 window.location.href="<%=request.getContextPath()%>/users/reverseEmiFixedAmount?productId="+productId;
			         
				 }
				 if(name.toLowerCase().indexOf("tenure")>=0){
					 window.location.href="<%=request.getContextPath()%>/users/reverseEmi?productId="+productId;
					
				 	}
				 setTimeout(function(){
					 $('#right-container').show();
				 },500);
				
				 if(productType=="Annuity%20Deposit"){
					 $('.header_customer h3').html("Annuity Deposit");
					 $('#fixedAnnuity').show();
					 $('.annuity').hide();
					 $('#depositss').hide();
				 }
				 if(productType=="Tax%20Saving%20Deposit"){
					 $('.header_customer h3').html("Tax Saving Deposit");
					
				 }
				 debugger;
				 addCustomerById("${customerInfo.id}");
		     });
			 
			 function getUrlVars()
			 {
			     var vars = [], hash;
			     var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
			     for(var i = 0; i < hashes.length; i++)
			     {
			         hash = hashes[i].split('=');
			         vars.push(hash[0]);
			         vars[hash[0]] = hash[1];
			     }
			     return vars;
			 }
			 
			function searchCustomer() {
				
			
					var customerId = document.getElementById('customerId').value;
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
								   var jointFlag = $("#joint").is(":checked");
									
									var consortiumFlag = $("#consortium").is(":checked");
								   if(jointFlag==true || consortiumFlag==true){
var date=new Date(data.dateOfBirth);
									   
									   var year = date.getFullYear();

									   var month = (1 + date.getMonth()).toString();
									   month = month.length > 1 ? month : '0' + month;

									   var day = date.getDate().toString();
									   day = day.length > 1 ? day : '0' + day;
									   
									   date= day + '-' + month + '-' + year;
									   
									   
									   trHTML += '<tr><td>' + data.customerName + '</td><td>' + data.email  + '</td><td>' + data.contactNum
									   + '</td><td>' + date + '</td><td>'
									   + '<input type="button" customer="'+data.customerName +'" value="Select Customer" id="'+ data.id +'" onclick="addCustomer(this)"/>'
									   + '</td></tr>'; 
								   }
								   else{
									   if(typeCustomer==data.citizen){
								   
									   var date=new Date(data.dateOfBirth);
									   
									   var year = date.getFullYear();

									   var month = (1 + date.getMonth()).toString();
									   month = month.length > 1 ? month : '0' + month;

									   var day = date.getDate().toString();
									   day = day.length > 1 ? day : '0' + day;
									   
									   date= day + '-' + month + '-' + year;
									   
									   
									   trHTML += '<tr><td>' + data.customerName + '</td><td>' + data.email  + '</td><td>' + data.contactNum
									   + '</td><td>' + date + '</td><td>'
									   + '<input type="button" customer="'+data.customerName +'" value="Select Customer" id="'+ data.id +'" onclick="addCustomer(this)"/>'
									   + '</td></tr>'; 
								   }
								   }
								   
								   
								}
//  								+'<input type=radio id=custId value=' + data.id + 'onclick=onclickRadio()'  +
								$('#tableCustomerList').append(trHTML);
								
								$('#tableCustomerList').addClass("jqtable example1");
								
								//ApplyPaging();
						        
						    },

						    error: function(e){  
						    	console.log(e)
						    	
						    	 $('#error').html("Error occured!!")

						    }  
						  });  

				    }
				}
			 
			
			var typeOfDeposit = 0;
			var allCustomer = [];
			function addCustomer(customer) {
				
				var customerN=$(customer).attr("customer");
				var fixedTenure = $("#fTenure").is(":checked");
				var fixedAmount = $("#fAmount").is(":checked");
				if(productType=="Annuity%20Deposit" && fixedTenure==false && fixedAmount==false){
					alert("Please Select Deposit type");
					return false;
				}
				if(fixedTenure==true){
					 window.location.href="<%=request.getContextPath()%>/bnkEmp/reverseEmiForBankEmp?userName="+customerN+"&productId="+productId;
					 return false;
				}
                if(fixedAmount==true){
                	 window.location.href="<%=request.getContextPath()%>/bnkEmp/reverseEmiFixedAmountForBankEmp?userName="+customerN+"&productId="+productId;
                	 return false;
				}

				var customerId = customer.id;
				var dataString = 'customerId='+ customerId;
				if(allCustomer.includes(customerId)){
					alert("Customer Id already selected. Please select another customer");
					return false;
				}
			

				
				var single = $("#single").val();
				
				var singleFlag = $("#single").is(":checked");
				var joint = $("#joint").val();			
				var jointFlag = $("#joint").is(":checked");
				var consortium = $("#consortium").val();
				var consortiumFlag = $("#consortium").is(":checked");

				if(typeOfDeposit >=1 && single == "SINGLE" && singleFlag == true){
					
					alert("Maximum 1 account holder can be added in Single Deposit. If you want to add more account holders, please change it to Joint / Consotium Deposit.");                            
					return false;
				}
				
				
				if(typeOfDeposit >=2 && joint == "JOINT" && jointFlag == true){
			
					alert("Maximum 2 account holders can be added in Joint Deposit. If you want to add more account holders, please change it to Consotium Deposit.");                            
					return false;
				}
				
				
				

				if(typeOfDeposit>=10 && consortium == "CONSORTIUM" && consortiumFlag == true){
					alert("In Consortium Deposit, maximum 10 deposit holders can be added.");
					return false;
				}
				
				
			
				  $.ajax({  
					    type: "GET",  
					    url: "<%=request.getContextPath()%>/bnkEmp/findCustomerById", 
					    contentType: "application/json",
					    dataType: "json",
					    data: dataString,  
				        async: false,
					    success: function(response){  
											    	
					    	var trHTML = '';

							   var data = response;
							   var nriAccountType=data.nriAccountType;
							  
							   /* if(NRO==1 && nriAccountType!="NRO"){
							   	alert("Please select NRO customers only");
							   	return false;
							   }
							   if(NRE==1 && nriAccountType!="NRE"){
							   	alert("Please select NRE customers only");
							   	return false;
							   }
							   if(PRP==1 && nriAccountType!="PRP"){
							   	alert("Please select PRP customers only");
							   	return false;
							   }
							   if(FCNR==1 && nriAccountType!="FCNR"){
							   	alert("Please select FCNR customers only");
							   	return false;
							   }
							   if(RFC==1 && nriAccountType!="RFC"){
							   	alert("Please select RFC customers only");
							   	return false;
							   } */

									trHTML = '<tr><td>' + data.id + '</td><td>' + data.customerName  + '</td><td>' + data.contactNum
									   + '</td><td>' + data.gender + '</td><td>' + data.email + '</td><td>' + data.address + '</td><td>'
									   + '<input class="a" type="text" id="relationShip'+ data.id +'"/>'  + '</td><td>'
									   + '<input class="a b" type="radio" disabled abc="'+data.id+'" class = "prim" name="prim" id="prim'+ data.id+'" onchange = "depositAcccountHolder(this.value,'+ data.id +')" value="false"/>'+ '</td><td>'
									   + '<input type="button" id = "'+data.id+'" value="Remove" onclick="removeCustomer(this)"/>'
									   + '</td></tr>';
								  
									   $('#tableAccountHolderList').append(trHTML);
									   typeOfDeposit++;
									   allCustomer.push(customerId);
							 if(singleFlag==true){
								 $('td input.a').addClass('single');
								 primaryCustomerId = data.id;
								 validateFormForSingle();
							 }
							 else{
								 $('td input.a').removeClass('single');
							 }
							
					
					        
					    },

					    error: function(e){  
					    	console.log(e)
					    	
					    	 $('#error').html("Error occured!!")

					    }  
					  });  

						
				document.getElementById("goBtn").disabled = false;
				
				
			}

			
			
function addCustomerById(Id) {
				
			
				allCustomer.push(Id);
				var customerId = Id;
				var dataString = 'customerId='+ customerId;
				
			

				
				var single = $("#single").val();			
				var singleFlag = $("#single").is(":checked");
				var joint = $("#joint").val();			
				var jointFlag = $("#joint").is(":checked");
				var consortium = $("#consortium").val();
				var consortiumFlag = $("#consortium").is(":checked");
				
				typeOfDeposit++;
			
				  $.ajax({  
					    type: "GET",  
					    url: "<%=request.getContextPath()%>/bnkEmp/findCustomerById", 
					    contentType: "application/json",
					    dataType: "json",
					    data: dataString,  
				        async: false,
					    success: function(response){  
									debugger;		    	
					    	var trHTML = '';

							   var data = response;
							  

									trHTML = '<tr><td>' + data.id + '</td><td>' + data.customerName  + '</td><td>' + data.contactNum
									   + '</td><td>' + data.gender + '</td><td>' + data.email + '</td><td>' + data.address + '</td><td>'
									   + '<input class="a" type="text" id="relationShip'+ data.id +'"/>'  + '</td><td>'
									   + '<input class="a b" type="radio" checked="checked" abc="'+data.id+'" class = "prim" name="prim" id="prim'+ data.id+'" onchange = "depositAcccountHolder(this.value,'+ data.id +')" value="true"/>'+ '</td><td>'
									   + '</td></tr>';
								 // $('.jointt').show();
									   $('#tableAccountHolderList').append(trHTML);
							 if(singleFlag==true){
								 $('td input.a').addClass('single');
								 primaryCustomerId = data.id;
								 validateFormForSingle();
							 }
							 else{
								 $('td input.a').removeClass('single');
							 }
							
					
					        
					    },

					    error: function(e){  
					    	console.log(e)
					    	
					    	 $('#error').html("Error occured!!")

					    }  
					  });  

						
				document.getElementById("goBtn").disabled = false;
				
				
			}


			function noPermission(value,e){
				/* var nriAccountType="${customer.nriAccountType}";
				  if(name.toLowerCase().indexOf("rfc")>=0){
				
				 RFC=1;
			 }
			 if(name.toLowerCase().indexOf("fcnr")>=0){
					
				 FCNR=1;
			 }
			 if(name.toLowerCase().indexOf("prp")>=0){
				 PRP=1;	
				
			 }
			 if(name.toLowerCase().indexOf("nro")>=0){
					
				 NRO=1;
			 }
			 if(name.toLowerCase().indexOf("nre")>=0){
					
				 NRE=1;
			 }
			 if(NRO==1 && nriAccountType!="NRO"){
					alert("You are not validated to open NRO deposit.");
					return false;
				}
				if(NRE==1 && nriAccountType!="NRE"){
					alert("You are not validated to open NRE deposit.");
					return false;
				}
				if(PRP==1 && nriAccountType!="PRP"){
					alert("You are not validated to open PRP deposit.");
					return false;
				}
				if(FCNR==1 && nriAccountType!="FCNR"){
					alert("You are not validated to open FCNR deposit.");
					return false;
				}
				if(RFC==1 && nriAccountType!="RFC"){
					alert("You are not validated to open RFC deposit.");
					return false;
				}  */
				if(value=="Amount"){
					 window.location.href="<%=request.getContextPath()%>/users/reverseEmiFixedAmount?productId="+productId;
		            	
				 }
				if(value=="Tenure"){

					 window.location.href="<%=request.getContextPath()%>/users/reverseEmi?productId="+productId;
					
				 }
				if(value=="SINGLE"){
					setTimeout(function(){addCustomerById("${customerInfo.id}")},200);
				 }
				else{
					$('.jointt').show();
				}
				if(typeOfDeposit>=3 && value == "JOINT"){
					alert("Can Not Change, More than 2 accounts are selected");
					e.preventDefault();
// 					$("#joint").v;
 					return false;
 					
				}
				if(typeOfDeposit>=2 && value == "SINGLE"){
					/* alert("Can Not Change, More than 1 account is selected");
					e.preventDefault();
// 					$("#joint").v;
 					return false;
 					e.preventDefault(); */
				}
				if(value=="SINGLE"){
					 $('td input.a').addClass('single');
					 var id=$('.b').attr('abc');
					 primaryCustomerId = id;
				 }
				 else{
					 $('td input.a').removeClass('single');
				 }
			}
			
			
			
			
			
			
			var depositHonderAccountValue = false;
			var depositHonderAccountValueId = "";
			var primaryCustomerId = "";
			function depositAcccountHolder(value,id){
				
				depositHonderAccountValueId = "prim"+id;	
				
				/*  var tableData_ = document.getElementById('tableAccountHolderList');
		            var numberOfRows_ = tableData_.rows.length;
		            
		            for (var i = 1; i < numberOfRows_; i += 1)
		            {
		            	  var row = tableData_.rows[i];
		            
		            } */
				$(".prim").val(false);
				
				$("#prim"+id).val(true);
				depositHonderAccountValue = $("#prim"+id).val();
				primaryCustomerId = id;
			}
			
			
			function removeCustomer(customer) {
				typeOfDeposit--;
				var id  = $(customer).attr("id");
				var remove = allCustomer.indexOf(id);
				allCustomer.splice(remove,1);
				  var i = customer.parentNode.parentNode.rowIndex;
				  
				  document.getElementById("tableAccountHolderList").deleteRow(i);
			}
			function onclickRadio() {
				alert(1);
				document.getElementById("goBtn").disabled = false;
			}
			function validateFormForSingle() {
				debugger;
				 var myMap = new Map();
				 var flagIsPrimary = false;
				 var flagNonRelation = false;
				 
				 var tableData = document.getElementById('tableAccountHolderList');
		            var numberOfRows = tableData.rows.length;
		            var bodyStart = '{"data":[';
		            for (var i = 1; i < 2; i += 1)
		            {
		                var row = tableData.rows[i];
		                var id = row.cells[0].innerText;
		                var primaryAccount =  true;
		               	var relationship  =  $("#relationShip"+id).val();
		               	if(flagIsPrimary  ==  false && primaryAccount == "true"){
		               		flagIsPrimary = true;
		               	}
		               	
		               	if(primaryAccount == "false" && relationship == ""){
		               		flagNonRelation = true;
		               		
		               	}
		               	
		               	bodyStart += '{"id":"'+id+'",';
		               	bodyStart += '"isPrimaryHolder":"'+primaryAccount+'",';
		               	
		            	bodyStart += '"relationship":"'+relationship+'"},';
		               	
		             	myMap.set(id, relationship);

	                	var primHolder = document.getElementById('prim'+id).value;
		            }
		            var comaIndex = bodyStart.indexOf(",");
		            bodyStart = bodyStart.substring(0,bodyStart.length - 1);
		         	bodyStart += ']}';


		          document.getElementById("id").value = primaryCustomerId;
		          document.getElementById("customerDetails").value = bodyStart;
		          document.getElementById("productId").value = productId;
		         
		        	  $("#fdForm").attr("action", "createSingleDeposit");
			          $("#fdForm").submit();
		         
		          
			}
			

			function validateForm() {
				 var myMap = new Map();
				 var flagIsPrimary = false;
				 var flagNonRelation = false;
				 
				 var tableData = document.getElementById('tableAccountHolderList');
		            var numberOfRows = tableData.rows.length;
		            var isJointEnable  = $("#joint").prop("checked");
		            if(numberOfRows < 3 && isJointEnable){
		            	alert("Please select secondary customer !");
		            		return false;
		            }
		            
		            var bodyStart = '{"data":[';
		            for (var i = 1; i < numberOfRows; i += 1)
		            {
		                var row = tableData.rows[i];
		                var id = row.cells[0].innerText;
		                var primaryAccount =  $("#prim"+id).val();
		               	var relationship  =  $("#relationShip"+id).val();
		               	if(flagIsPrimary  ==  false && primaryAccount == "true"){
		               		flagIsPrimary = true;
		               	}
		               	
		               	if(primaryAccount == "false" && relationship == ""){
		               		flagNonRelation = true;
		               		
		               	}
		               	
		               	bodyStart += '{"id":"'+id+'",';
		               	bodyStart += '"isPrimaryHolder":"'+primaryAccount+'",';
		               	
		            	bodyStart += '"relationship":"'+relationship+'"},';
		               	
		             	myMap.set(id, relationship);

	                	var primHolder = document.getElementById('prim'+id).value;
		            }
		            
		            
		           /*  if(!flagIsPrimary){
		            	alert("Please select atleast one primary account");
		            	return false;
		            	
		            } */
		            if(flagNonRelation){
		            	alert("Relationship for non primary accounts can not be empty");
		            	return false;
		            	
		            }
		            var comaIndex = bodyStart.indexOf(",");
		            bodyStart = bodyStart.substring(0,bodyStart.length - 1);
		            
		           // bodyStart.replaceAt(comaIndex, " ");
		          
		        	bodyStart += ']}';


		          document.getElementById("id").value = primaryCustomerId;
		          
		         // document.getElementById("customerDetails").value = myMap;
		          
		          document.getElementById("customerDetails").value = bodyStart;
		          document.getElementById("productId").value = productId;
		          
		          var single = $("#single").val();			
				  var singleFlag = $("#single").is(":checked");
					

				  if(single == "SINGLE" && singleFlag == true)
		          {
		        	  $("#fdForm").attr("action", "createSingleDeposit");
			          $("#fdForm").submit();
		          }
		          else
		          {
			          $("#fdForm").attr("action", "jointConsortiumDeposit");
			          $("#fdForm").submit();
		          }
			}
			
			String.prototype.replaceAt=function(index, replacement) {
			
			    return this.substr(0, index) + replacement+ this.substr(index + replacement.length);
			}
			$(document).ready(function() {
				//$('#cusId').checked=false;
				//document.getElementById('cusId').checked = false;
			});
		</script>
		<style>
		.single{visibility:hidden;}
		</style>
