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
					Customer Citizen Conversion
					<small><spring:message code="label.searchCustomer" /></small>
				</h3>
			</div>
			<div class="Success_msg"></div>
			<div class="flexi_table">
				<form:form class="form-horizontal" id="fdForm" 
					name="fixedDeposit" commandName="fixedDepositForm" autocomplete="false">

					<form:input type="hidden" path="id" id="id"/>
					
					<input type="hidden" name = "customerDetails" id="customerDetails"/> 
					
						
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
					

<!-- 						<tr> -->
<!-- 							<td><br></td> -->
<!-- 						</tr> -->

<%-- 						<c:if test="${! empty customerList}"> --%>
							<table align="center" id="tableCustomerList"  class="table table-striped table-bordered" style="width: 95%; margin-left: 20px;">
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
		var name ="";
		 var NRO=0;
		var PRP=0;
		var FCNR=0;
		var NRE=0;
		var RFC=0;
			 $(document).ready(function() {
				 debugger;
				 typeCustomer = getUrlVars()["type"];
				 productId = getUrlVars()["productId"];
				 productType = getUrlVars()["productType"];
				 name = getUrlVars()["name"];
				
				
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
				debugger;
			
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
					    url: "<%=request.getContextPath()%>/common/findCustomer", 
					    contentType: "application/json",
					    dataType: "json",
					    data: dataString,
				     async: false,
					    success: function(response){
					    	

					    	var trHTML = '';
					    	for(i=0; i< response.length; i++){
						    	var data = response[i];
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
					    	$('#tableCustomerList').append(trHTML);
							
							$('#tableCustomerList').addClass("jqtable example1");
					    },  
					    error: function(e){  
					    	
					    	 $('#error').html("Error occured!!")
					    	 
					    }  
					  });  
					
					}  


			}
			
			
			
			function addCustomer(customer) {
				var customerId = customer.id;
				 $("#fdForm").attr("action", "customerCitizenConversionByCustomerId?customerId="+customerId);
		          $("#fdForm").submit();
			}
			
			
			
		</script>
		<style>
		.single{visibility:hidden;}
		</style>
