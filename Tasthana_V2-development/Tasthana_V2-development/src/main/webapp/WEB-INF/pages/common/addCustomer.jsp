<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div class="right-container" id="right-container">
	<div class="container-fluid">


		<div class="add-customer">
			<form:form class="form-horizontal" id="addCustomerForm"
				name="fixedDeposit" autocomplete="false" method="post"
				commandName="customerForm">
				<!-- action="customerConfirm" -->
				<!-- onsubmit="return val()" -->
				<div class="newcustomer_border">
					<div class="header_customer">
						<h3>
							<spring:message code="label.addPersonalDetails" />
						</h3>
					</div>
					<div class="successMsg">
						<b><font color="green">${success}</font></b>
					</div>
<input type="hidden" name="menuId" value="${menuId}"/>
					<div class="add-customer-table">
						<form:hidden path="customerID" />
						<form:hidden path="age" id= "age_"/>
						<div class="col-md-6">

							<div class="form-group" id="ripDiv">
								<label class="col-md-4 control-label"><spring:message
										code="label.citizen" /><span style="color: red">*</span></label>
								<div class="col-md-8" style="margin-top:10px;">

									<label for="radio" id="radioButtonRi"> <form:radiobutton
											path="citizen" id="RIRadio" name="citizen"
											onclick="citizenFun()" value="RI" checked="true"></form:radiobutton></label>
									<spring:message code="label.ri" />
									<label for="radio"> <form:radiobutton
											onclick="citizenFun()" path="citizen" id="NRIRadio"
											name="citizen" value="NRI"></form:radiobutton></label>
									<spring:message code="label.nri" />
								</div>
							</div>


							<div class="form-group" style="display: none;" clear:
								both;" id="accountTypeDIV">
								<label class="col-md-4 control-label"><spring:message
										code="label.nriAccountType" /><span style="color: red">*</span></label>

								<div class="col-md-8">
									<form:select id="nriAccountType" path="nriAccountType"
										class="input myform-control"
										onchange="accountTypeVal(this.value)">
										<form:option value="">
											<spring:message code="label.select" />
										</form:option>
										<form:option value="NRE">
											<spring:message code="label.nre" />
										</form:option>
										<form:option value="NRO">
											<spring:message code="label.nro" />
										</form:option>
										<form:option value="FCNR">
											<spring:message code="label.fcnr" />
										</form:option>
										<form:option value="RFC">
											<spring:message code="label.rfc" />
										</form:option>
										<form:option value="PRP">
											<spring:message code="label.prp" />
										</form:option>
									</form:select>
								</div>
							</div>


							<span id="radioError" class="error" style="display: none;"><font
								color="red"><spring:message code="label.radioError" /></font></span>



							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.category" /></b><span style="color: red">*</span></label>
								<div class="col-md-8">
									<table>
										
											<c:if test="${setCategory!=null}">
												<div>

													<form:select id="category" path="category"
														class="myform-control" required="true">
														<option value="select">Select</option>
														<c:forEach var="item" items="${setCategory}">
															<option value="${item.customerCategory}"
																<c:if test="${item.customerCategory eq selectedCategory}">selected</c:if>>${item.customerCategory}</option>
														</c:forEach>
													</form:select>


												</div>
											</c:if>
											<c:if test = "${setCategory == null}">
												<div class="col-md-12">
													<form:select id="setCategory" path="category"
														class="myform-control">
														<option value="select"><spring:message
																code="label.select" /></option>

													</form:select>
												</div>
											</c:if>
										
									</table>
									<span id="categoryError" class="error" style="display: none;"><font
										color="red"><spring:message code="label.selectValue" /></font></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.customerName" /><span style="color: red">*</span></label>
								<div class="col-md-8">
									<form:input path="customerName" id="customerName"
										placeholder="Enter Name" class="myform-control"></form:input>
									<span id="nameError" class="error" style="display: none;"><font
										color="red"><spring:message code="label.validation" />*</font></span>
									<span id="nameError" class="error" style="display: none"><spring:message
											code="label.validation" /></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.userName" /><span style="color: red">*</span></label>
								<div class="col-md-8">
									<form:input path="userName" placeholder="Enter Username"
										class="myform-control"></form:input>
									<span id="nameError" class="error" style="display: none;"><font
										color="red"><spring:message code="label.validation" />*</font></span>
									<span id="nameError" class="error" style="display: none"><spring:message
											code="label.validation" /></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.gender" /><span style="color: red">*</span></label>
								<div class="col-md-8">
									<form:select path="gender" id="gender" class="myform-control">
										<form:option value="">
											<spring:message code="label.select" />
										</form:option>
										<form:option value="male">
											<spring:message code="label.male" />
										</form:option>
										<form:option value="female">
											<spring:message code="label.female" />
										</form:option>
										<form:option value="transgender">
											<spring:message code="label.transgender" />
										</form:option>
									</form:select>
								</div>
							</div>
							<div class="form-group">

								<label class="col-md-4 control-label"><spring:message
										code="label.dateOfBirth" /><span style="color: red">*</span></label>
								<div class="col-md-8">
									<form:input path="dateOfBirth" id="dateOfBirth" readonly="true"
										placeholder="Select Date"
										class="myform-control datepicker-here" />

									<span id="dateOfBirthError" class="error"
										style="display: none;"><font color="red"><spring:message
												code="label.selectValue" /></font></span> <span
										id="futureDateOfBirthError" style="display: none; color: red"><spring:message
											code="label.futureDateSelected" /></span>


								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.contactNumber" /></b><span style="color: red">*</span></label>
								<div class="col-md-8">
									<form:input path="contactNum"
										placeholder="Enter contact number" id="contactNum"
										class="myform-control"></form:input>
									<span id="contactNum1Error" class="error"
										style="display: none;"><font color="red"><spring:message
												code="label.incorrectFormat" /></font></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.altContactNo" /></label>
								<div class="col-md-8">
									<form:input path="altContactNum"
										placeholder="Enter alternative contact number"
										id="altContactNum" class="myform-control"></form:input>
									<span id="contactNum2Error" class="error"
										style="display: none;"><font color="red"><spring:message
												code="label.incorrectFormat" /></font></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.email" /><span style="color: red">*</span></label>
								<div class="col-md-8">
									<form:input path="email" placeholder="Enter email id"
										id="email" type="email" class="myform-control"></form:input>
									<span id="emailError" class="error" style="display: none;"><font
										color="red"><spring:message
												code="label.incorrectFormat" /></font></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.altEmail" /></label>
								<div class="col-md-8">
									<form:input path="altEmail"
										placeholder="Enter alternative email id" type="email"
										id="altEmail" class="myform-control"></form:input>
									<span id="altemailError" class="error" style="display: none;"><font
										color="red"><spring:message
												code="label.incorrectFormat" /></font></span>
								</div>
							</div>


						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.address" /><span style="color: red">*</span></label>
								<div class="col-md-8">
									<form:textarea path="address" placeholder="Enter address"
										id="address" class="myform-control" row="7" col="50"
										style="height: 70px;"></form:textarea>
									<span id="addressError" class="error" style="display: none;"><font
										color="red"><spring:message code="label.validation" /></font></span>
								</div>
							</div>

							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.pincode" /><span style="color: red">*</span></label>
								<div class="col-md-8">
									<form:input path="pincode" placeholder="Enter pincode"
										id="pincode" class="myform-control"></form:input>
									<span id="pincodeError" class="error" style="display: none;"><font
										color="red"><spring:message
												code="label.incorrectFormat" /></font></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.country" /><span style="color: red">*</span></label>
								<div class="col-md-8">
									<form:select path="country" id="country"
										placeholder="Select country" class="myform-control"></form:select>
									<span id="countryError" class="error" style="display: none;"><font
										color="red"><spring:message code="label.selectValue" /></font></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.state" /><span style="color: red">*</span></label>
								<div class="col-md-8">
									<form:select path="state" placeholder="Select state" id="state"
										class="myform-control"></form:select>
									<script>
										populateCountries("country", "state");
									</script>
									<span id="stateError" class="error" style="display: none;"><font
										color="red"><spring:message code="label.selectValue" /></font></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.city" /><span style="color: red">*</span></label>
								<div class="col-md-8">
									<form:input path="city" placeholder="Enter city" id="city"
										class="myform-control"></form:input>
									<span id="cityError" class="error" style="display: none;"><font
										color="red"><spring:message code="label.validation" />*</font></span>
									<span id="cityError" class="error" style="display: none"><spring:message
											code="label.validation" /></span>

								</div>
							</div>

							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.pan" /></label>
								<div class="col-md-8">
									<form:input path="pan" placeholder="Enter PAN" id="pan"
										class="myform-control" style="text-transform:uppercase"></form:input>
									<span id="contactNum2Error" class="error"
										style="display: none;"><font color="red"><spring:message
												code="label.incorrectFormat" /></font></span>
								</div>
							</div>

							<span id="panCardError" class="error" style="display: none;"><font
								color="red"><spring:message code="label.panCardError" /></font></span>
							<div class="successMsg">
								<b><font color="error">${errorPan}</font></b>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.aadhar" /><span style="color: red">
										</span></label>
								<div class="col-md-8">
									<form:input path="aadhar" placeholder="Enter Aadhar"
										 id="aadhar" class="myform-control"></form:input>
									<span id="contactNum2Error" class="error"
										style="display: none;"><font color="red"><spring:message
												code="label.incorrectFormat" /></font></span> <span id="aadharNum1Error"
										class="error" style="display: none;"><font color="red"><spring:message
												code="label.aadharError" /></font></span>
								</div>
							</div>
							<div class="successMsg">
								<b><font color="error">${errorAadhar}</font></b>
							</div>
						</div>

					</div>


				</div>
				<div class="col-sm-12">
					<hr>
				</div>
				<div class="col-sm-12 col-xs-12">
					<div class="successMsg">
						<b><font color="error">${error}</font></b>
					</div>
					<div class="add-btn">
						<input type="button" id="add" class="btn btn-success"
							value="Add Account">
					</div>
				</div>

				<div id="items" class="col-sm-12"></div>

				<!--  Script for Add Account details - -->

				<script>
				function panvalidation(e,id){
					var panVal = $('#'+id).val();
					
						var regex = new RegExp("^[a-zA-Z0-9]+$");
						 var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
						
						
						 if (!regex.test(key)) {
						    	  event.preventDefault();
						        return false;
						   }
					
						var regpan = /^([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}?$/;
					    if(panVal.length > 9){
						if(regpan.test(panVal)){
							
							if(e.keyCode != 144){
						  
							}
						} else {
							if(e.keyCode != 144){
							alert("invalid pan number");
							return false;
							}
						}
					}
					    
					    if(panVal.length > 9 &&  e.keyCode != 8 ){
					    	  e.preventDefault();
					    	  return false;
					    }
						
				
					
					
				}
				
					$(document)
							.ready(
									function() {
										var n ;
										window.n = $("#items .form-1").length;
										
										today = getLoginDate();
										
										//when the Add Filed button is clicked
										$("#add")
												.click(
														function(e) {
															//Append a new row of code to the "#items" div
															
															if (window.n <= 2) {
															
																if(window.n==0){
																	
																$("#items")
																		.append(
																				'<div class="form-1 col-md-6 col-xs-12"><div class="add-personal-details field"><h4><spring:message code="label.accountDeatils"/></h4></div><table class="theader" width="400" style="position: relative;left: 2.6%;"><tr><td><b><spring:message code="label.accountType"/></b></td><td><form:select onchange="check(this.id)" id="accountType" path="accountType"  class="myform-control"><form:option value="Savings"><spring:message code="label.Saving"/></form:option><form:option value="Current"><spring:message code="label.Current"/></form:option><form:option value="Deposit"></form:option></form:select> </td></tr><tr><td><b><spring:message code="label.accountBalance"/></b><span style="color:red">*</span></td><td><form:input path="accountBalance" placeholder="Account Balance" onkeypress="validationAccount(event)"  id="accountBalance0"  class="myform-control" required="true"></form:input></td></tr><tr><td><b><spring:message code="label.accountNo"/></b><span style="color:red">*</span></td><td><form:input path="accountNo" placeholder="Enter account number" id="accountNo0" required="true"  class="myform-control"></form:input><span id="accountNumError0" class="error" style="display:none;"><font color="red"><spring:message code="label.incorrectFormat"/></font></span></td></tr><tr><td></td><td><span id="accountBalanceError0" class="error" style="display:none;color:red;"><spring:message code="label.incorrectFormat"/></span></td></tr><tr><td><b><spring:message code="label.minimumBalance"/></b><span style="color:red">*</span></td><td><form:input path="minimumBalance" placeholder="Enter minimum balance" id="minimumBalance0" onkeypress="validationAccount(event)" required="true" class="myform-control"></form:input><span id="minimumBalanceError0" class="error" style="display:none;"><font color="red"><spring:message code="label.incorrectFormat"/></font></span></td></tr></table><button id="b1" class="btn btn-danger remove-me" type="button">Delete</button></div>');
																var accountNo0_ = document.getElementById('accountNo0');
																debugger;
																accountNo0_.onkeydown = function(event) {
																	
																	
																	var accountType0Value = $("#accountType").val();
																	if(accountType0Value == 'Deposit'){
																		var keycode = event.which;
																	    if (!(event.shiftKey == false && (keycode == 8 || keycode == 37 || (keycode >= 65 && keycode <= 90) ||(keycode >= 48 && keycode <= 57)||(keycode >= 96 && keycode <= 105) || (keycode > 64 && keycode < 91) || !keycode ==  32 ))) {
																	        event.preventDefault();
																	    }
																		
																	}else{
																		 
																		var keycode = event.which;
																	    if (!(event.shiftKey == false && (keycode == 8 || keycode == 37 || (keycode >= 48 && keycode <= 57)||(keycode >= 96 && keycode <= 105)))) {
																	        event.preventDefault();
																	    }
																		
																	}
																	
																	
																    
																}
															}
																if(window.n==1){
																
																	$("#items")
																			.append(
																					'<div class="form-1 col-md-6 col-xs-12"><div class="add-personal-details field"><h4><spring:message code="label.accountDeatils"/></h4></div><table class="theader" width="400" style="position: relative;left: 2.6%;"><tr><td><b><spring:message code="label.accountType"/></b></td><td><form:select path="accountType" onchange="check1(this.id)" id="accountType1" class="myform-control"><form:option value="Savings"><spring:message code="label.Saving"/></form:option><form:option value="Current"><spring:message code="label.Current"/></form:option><form:option value="Deposit"></form:option></form:select> </td></tr><tr><td><b><spring:message code="label.accountBalance"/></b><span style="color:red">*</span></td><td><form:input path="accountBalance" placeholder="Account Balance" onkeypress="validationAccount(event)" id="accountBalance1" class="myform-control" required="true"></form:input></td></tr><tr><td><b><spring:message code="label.accountNo"/></b><span style="color:red">*</span></td><td><form:input path="accountNo" placeholder="Enter account number" id="accountNo1" required="true"  class="myform-control"></form:input><span id="accountNumError1" class="error" style="display:none;"><font color="red"><spring:message code="label.incorrectFormat"/></font></span></td></tr><tr><td></td><td><span id="accountBalanceError1" class="error" style="display:none;color:red;"><spring:message code="label.incorrectFormat"/></span></td></tr><tr><td><b><spring:message code="label.minimumBalance"/></b><span style="color:red">*</span></td><td><form:input path="minimumBalance" placeholder="Enter minimum balance" id="minimumBalance1" onkeypress="validationAccount(event)" required="true" class="myform-control"></form:input><span id="minimumBalanceError1" class="error" style="display:none;"><font color="red"><spring:message code="label.incorrectFormat"/></font></span></td></tr></table><button id="b1" class="btn btn-danger remove-me" type="button">Delete</button></div>');
																	var accountNo1_ = document.getElementById('accountNo1');
																	accountNo1_.onkeydown = function(event) {
																		
																		var accountType0Value = $("#accountType1").val();
																		if(accountType0Value == 'Deposit'){
																			var keycode = event.which;
																		    if (!(event.shiftKey == false && (keycode == 46 || keycode == 8 || keycode == 37 ||keycode == 106|| (keycode >= 65 && keycode <= 90) ||(keycode >= 48 && keycode <= 57)||(keycode >= 96 && keycode <= 105) || (keycode > 96 && keycode < 123) || (keycode > 64 && keycode < 91) ))) {
																		        event.preventDefault();
																		    }
																			
																		}else{
																			 
																			var keycode = event.which;
																		    if (!(event.shiftKey == false && (keycode == 46 || keycode == 8 || keycode == 37 || (keycode >= 48 && keycode <= 57)||(keycode >= 96 && keycode <= 105)))) {
																		        event.preventDefault();
																		    }
																			
																		}
																		
																		
																	    
																	}
																}
																if(window.n==2){
																
																	$("#items")
																			.append(
																					'<div class="form-1 col-md-6 col-xs-12"><div class="add-personal-details field"><h4><spring:message code="label.accountDeatils"/></h4></div><table class="theader" width="400" style="position: relative;left: 2.6%;"><tr><td><b><spring:message code="label.accountType"/></b></td><td><form:select path="accountType" onchange="check2(this.id)" id="accountType2" class="myform-control"><form:option value="Savings"><spring:message code="label.Saving"/></form:option><form:option value="Current"><spring:message code="label.Current"/></form:option><form:option value="Deposit"></form:option></form:select> </td></tr><tr><td><b><spring:message code="label.accountBalance"/></b><span style="color:red">*</span></td><td><form:input path="accountBalance" placeholder="Account Balance"  onkeypress="validationAccount(event)" id="accountBalance2"  class="myform-control" required="true"></form:input></td></tr><tr><td><b><spring:message code="label.accountNo"/></b><span style="color:red">*</span></td><td><form:input path="accountNo" placeholder="Enter account number" id="accountNo2" required="true"  class="myform-control"></form:input><span id="accountNumError2" class="error" style="display:none;"><font color="red"><spring:message code="label.incorrectFormat"/></font></span></td></tr><tr><td></td><td><span id="accountBalanceError2" class="error" style="display:none;color:red;"><spring:message code="label.incorrectFormat"/></span></td></tr><tr><td><b><spring:message code="label.minimumBalance"/></b><span style="color:red">*</span></td><td><form:input path="minimumBalance" placeholder="Enter minimum balance" id="minimumBalance2" onkeypress="validationAccount(event)" required="true" class="myform-control"></form:input><span id="minimumBalanceError2" class="error" style="display:none;"><font color="red"><spring:message code="label.incorrectFormat"/></font></span></td></tr></table><button id="b1" class="btn btn-danger remove-me" type="button">Delete</button></div>');
																	var accountNo2_ = document.getElementById('accountNo2');
																	accountNo2_.onkeydown = function(event) {
																		
																		
																		var accountType20Value = $("#accountType2").val();
																		if(accountType20Value == 'Deposit'){
																		
																			var keycode = event.which;
																		    if (!(event.shiftKey == false && (keycode == 8 || keycode == 37 (keycode >= 65 && keycode <= 90) ||(keycode >= 48 && keycode <= 57)||(keycode >= 96 && keycode <= 105) || (keycode > 96 && keycode < 123) || (keycode > 64 && keycode < 91) ))) {
																		        event.preventDefault();
																		    }
																			
																		}else{
																			 
																			var keycode = event.which;
																		    if (!(event.shiftKey == false && (keycode == 46 || keycode == 8 || keycode == 37 || (keycode >= 48 && keycode <= 57)||(keycode >= 96 && keycode <= 105)))) {
																		        event.preventDefault();
																		    }
																			
																		}
																		
																		
																	    
																	}
																}
																 window.n=parseInt(window.n)+1;
															}
																else {
																alert("Only three Accounts are allowed!")
															}
															
										
										
										
														});
										$("body").on(
												"click",
												".remove-me",
												function(e) {
													$(this).parent("div")
															.remove();
												 window.n=parseInt(window.n)-1;
												});
									});
					
					
					
					function check(id){
						 var id = $("#"+id).val();
						 $("#accountNo0").val("");
						}
					
					function check1(id){
						 var id = $("#"+id).val();
						 $("#accountNo1").val("");
						}
					function check2(id){
						 var id = $("#"+id).val();
						 $("#accountNo2").val("");
						}
					
					
					
					
					
					
					
					
					
					
					
					
					function validationAccount(event){
						var minimumBalance0_ = document.getElementById(event.target.id);
				
							 var keycode = event.which;
						    if (!(event.shiftKey == false && (keycode == 8 || keycode == 37 ||  (keycode >= 48 && keycode <= 57)))) {
						        event.preventDefault();
						        return false;
						    } 
						   
						    
						    if (minimumBalance0_.value.length>10 && event.keyCode != 8){
						    	return false;
						    }
						
						
						 
						}
					
					
					
					
					
					
					
				</script>

				<div class="space-10"></div>

				<div class="save-back" style="margin-top: 5%;">
				<p align="center" style="padding-top: 10px;">
				<label id="lblErrMessage" style="color:red;"></label>
					<label id="lblMessageAccounts" style="color:red;display:none;">Multiple same type of accounts can not be saved.</label></p>
					<p align="center" style="padding-top: 10px;">
					
						<%-- <a href="bankEmp" class="btn btn-success"><spring:message
								code="label.back" /></a> --%> <input type="button"
							onclick="return isValid()" class="btn btn-primary" value="Confirm" />
					</p>
				</div>


				<div id="myModal" class="modal">

					<!-- Modal content -->
					<div class="modal-content">
						<h4 style="color: #031d06bd;">
							<span style="color: red">*</span>
							<spring:message code="label.customerAgeLessThanEighteenError" />
						</h4>
						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.guardianName" /><span style="color: red">*</span></label>
							<div class="col-md-6">
								<form:input path="guardianName"
									placeholder="Enter Guardian Name" class="input form-control"
									id="guardianName" required="true" onkeypress="validName(event)"/>
							</div>
						</div>


						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.guardianAge" /><span style="color: red">*</span></label>
							<div class="col-md-6">
								<form:input path="guardianAge" type="text"
									class="input form-control" placeholder="Enter Guardian Age"
									id="guardianAge" onkeypress="validationAge(event)"/>

							</div>
						</div>

						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.guardianAddress" /><span style="color: red">*</span></label>
							<div class="col-md-6">
								<form:input path="guardianAddress" class="input form-control"
									placeholder="Enter Guardian Address" id="guardianAddress" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.guardianRelationShip" /><span style="color: red">*</span></label>
							<div class="col-md-6">
								<form:input path="guardianRelationShip"
									class="input form-control"
									placeholder="Enter Guardian RelationShip"
									id="guardianRelationShip" onkeypress="validName(event)"/>

							</div>
						</div>

						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.guardianPan" /><span style="color: red"></span></label>
							<div class="col-md-6">
								<form:input path="guardianPan" placeholder="Enter PAN"
									id="guardianPan" onkeypress ="panvalidation(event,this.id)" class="input form-control"
									style="text-transform:uppercase" />
								<span id="contactNum2Error" class="error" style="display: none;"><font
									color="red"><spring:message code="label.incorrectFormat" /></font></span>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.guardianAadhar" /><span style="color: red">
									
								</span></label>
							<div class="col-md-6">
								<form:input path="guardianAadhar" placeholder="Enter Aadhar"
									type="text" id="guardianAadhar" class="input form-control" onkeypress="validationAccount(event)"></form:input>
								<span id="contactNum2Error" class="error" style="display: none;"><font
									color="red"><spring:message code="label.incorrectFormat" /></font></span>
							</div>

						</div>

						<%--  <span id="guardianAadharError" class="error" style="display: block;padding-left:  0;padding-right: 285px;"><font
										color="red"><spring:message
												code="label.aadharError" /></font></span> --%>


						<div style="text-align: center;">

							<span id="guardianError" class="error"
								style="display: block; padding-left: 0; padding-right: 285px;"><font
								color="red"><spring:message code="label.guardianError" /></font></span>
							<span id="guardianAgeError" class="error" style="display: none;"><font
								color="red"><spring:message code="label.guardianAgeError" /></font></span>

							<!-- onclick="val()"  -->
							<input type="button" id="proceed" class="btn btn-primary" onclick="return isValid();return nomineDetailValidate(this.id)"
								value="Confirm" /> <input type="button" class="btn btn-danger"
								id="cancel" onclick="cancelModel()" value="Cancel" />
						</div>
						<div></div>

					</div>

				</div>
			</form:form>
		</div>
	</div>
	<script>
	
	function nomineDetailValidate(confirmButtonId){
		alert(confirmButtonId)
		
		return true;
	}
	
	var today = null;
	function getLoginDate()
	{

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
	function validationAccount(event){
		
		var minimumBalance1_ = document.getElementById(event.target.id);

			 var keycode = event.which;
		    if (!(event.shiftKey == false && (keycode == 8 || keycode == 37 ||  (keycode >= 48 && keycode <= 57)))) {
		        event.preventDefault();
		        return false;
		    } 
		   
		    
		    if (minimumBalance1_.value.length>11 && event.keyCode != 8){
		    	 event.preventDefault();
		    	return false;
		    }
		
		
		 
		}
	
	
function validationAge(event){
		
		var minimumBalance1_ = document.getElementById(event.target.id);

			 var keycode = event.which;
		    if (!(event.shiftKey == false && (keycode == 8 || keycode == 37 ||  (keycode >= 48 && keycode <= 57)))) {
		        event.preventDefault();
		        return false;
		    } 
		   
		    
		    if (minimumBalance1_.value.length>2 && event.keyCode != 8){
		    	 event.preventDefault();
		    	return false;
		    }
		
		
		 
		}
function validName(event){
	
	var valiName = document.getElementById(event.target.id);
	var regex = new RegExp("^[a-zA-Z ]+$");
	 var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
	
	if(valiName.value.length==0 && event.keyCode ==32)
		{
		  event.preventDefault();
		return false;
		}
	 if (!regex.test(key)) {
	    	//alert("false")
	        event.preventDefault();
	        return false;
	   }
	 }
	
	
	
	function citizenFun(){
		
		var NRIRadioChecked = document.getElementById("NRIRadio").checked;
		if(NRIRadioChecked== true){
			 document.getElementById('radioError').style.display = 'none';
			 document.getElementById('accountTypeDIV').style.display = 'block'; 
		}
		else{
			 document.getElementById('radioError').style.display = 'none';
			 document.getElementById('accountTypeDIV').style.display = 'none'; 
		}
	}
	

		function checkDOB() {
		
        var dateString = document.getElementById('dateOfBirth').value;
       
        var myDate = new Date(dateString);
       
        var str = myDate.toString("MMMM yyyy");
       
     //   var today = getTodaysDate();
       
        if (myDate>today) { 
        	
            $('#dateOfBirthError').after('<p>You cannot enter a date in the future!.</p>');
            return false;
        }
     
        return true;
    }
	
	
		/* function usernameAndNameValidater(id)
		{
		    var regex = /^[a-zA-Z ]{2,30}$/;
		    var ctrl =  document.getElemetnById(id);

		    if (regex.test(ctrl.value)) {
		    	
		    	ctrl.style.borderColor = "black";
		    	
		        return true;
		    }
		    else {
		    	ctrl.style.borderColor = "red";
		        return false;
		    }
		}
		 */
		
		
		
		
		
		
		
	
		function Validation() {
			var age = document.getElementById('nomineeAge').value;
			if (age < 18) {
				$("#age1").show();
				$("#age2").show();
				$("#age3").show();
				$("#age4").show();
			} else {
				$("#age1").hide();
				$("#age2").hide();
				$("#age3").hide();
				$("#age4").hide();
			}
		}

		function pan(obj){
		
			var panVal = obj.val();
			var regpan = /^([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}?$/;
	if(panVal.length > 9){
			if(regpan.test(panVal)){
			  
			} else {
				alert("invalid");
			}
	}
			
			
			
		}
		$(document).ready(
				function() {
					$('#pan').keyup(function(e){
						
						var panVal = $(this).val();
						var regpan = /^([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}?$/;
				if(panVal.length > 9){
				
						if(regpan.test(panVal)){
							if(e.keyCode != 144){
						  
							}
						} else {
							if( e.keyCode != 144){
							alert("invalid");
							}
						}
				}
						
					});
					//document.getElementById('accountTypeDIV').style.display = 'none';
					$(':input', '#customerHeadForm').not(
							':button, :submit, :reset, :hidden').val('');
								 
						var number = document.getElementById('contactNum');
					var alternateNumber = document.getElementById('altContactNum');
					var aadhar = document.getElementById('aadhar');
					var pincode = document.getElementById('pincode');
					var accountBalance0 = document.getElementById('accountBalance0');
					var accountNo0 = document.getElementById('accountNo0');
					var minimumBalance0 = document.getElementById('minimumBalance0');
					
					var userName = document.getElementById('userName');
					
					var customerName = document.getElementById('customerName');
					
					$('#userName').bind('keypress', function (event) {
				         var regex = new RegExp("^[A-Za-z0-9_@./#&+-]*$");
				        var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
				        if (!regex.test(key)) {
				        	//alert("false")
				            event.preventDefault();
				            return false;
				        } 
				        
				    });
					
					$('#customerName').bind('keypress', function (event) {
						debugger;
				        var regex = new RegExp("^[a-zA-Z0-9 ]+$");
				        var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
				        if (!regex.test(key)) {
				        	//alert("false")
				            event.preventDefault();
				            return false;
				        }
				        var valueCustname = $("#customerName").val();
				        
				        if(valueCustname.trim().length == 0  && event.keyCode == 32){
				        	 event.preventDefault();
					            return false;
				        }  
				        
				       
				    });
					
					
					
					number.onkeydown = function(event) {
						var keycode = event.which;
						if (!(event.shiftKey == false && (keycode == 8 || keycode == 37  || (keycode >= 48 && keycode <= 57)|| (keycode >= 96 && keycode <= 105)))) {
					        event.preventDefault();
					    }
					    if (number.value.length>9 && event.keyCode != 8){
					    	return false;
					    }
					}

					alternateNumber.onkeydown = function(event) {
						var keycode = event.which;
						if (!(event.shiftKey == false && ( keycode == 8 || keycode == 37  || (keycode >= 48 && keycode <= 57)|| (keycode >= 96 && keycode <= 105)))) {
					        event.preventDefault();
					    }
					    if (alternateNumber.value.length>9 && event.keyCode != 8){
					    	return false;
					    }
					}
					aadhar.onkeydown = function(event) {
						var keycode = event.which;
						if (!(event.shiftKey == false && ( keycode == 8 || keycode == 37 || (keycode >= 48 && keycode <= 57)|| (keycode >= 96 && keycode <= 105)))) {
					        event.preventDefault();
					    }
					    if (aadhar.value.length>11 && event.keyCode != 8){
					    	return false;
					    }
					}
					pincode.onkeydown = function(event) {
						var keycode = event.which;
						if (!(event.shiftKey == false && ( keycode == 8 || keycode == 37  || (keycode >= 48 && keycode <= 57)|| (keycode >= 96 && keycode <= 105)))) {
					        event.preventDefault();
					    }
					    if (pincode.value.length>5 && event.keyCode != 8){
					    	return false;
					    }
					}
					
					
					




				});

		$('.accordion .item .heading').click(function() {

			var a = $(this).closest('.item');
			var b = $(a).hasClass('open');
			var c = $(a).closest('.accordion').find('.open');

			if (b != true) {
				$(c).find('.content').slideUp(200);
				$(c).removeClass('open');
			}

			$(a).toggleClass('open');
			$(a).find('.content').slideToggle(200);

		});

		function cancelModel(){
			var modal = document.getElementById('myModal');
   		    modal.style.display = "none";
   		
   			
   		}
		
		function isValid() {
var messages="";
			debugger;
// 			var d = getTodaysDate().getTime();
            $('#lblMessageAccounts').hide();
			var d = today.getTime()
			var uuid = 'xxxxxxxx'.replace(/[xy]/g, function(c) {
				var r = (d + Math.random() * 16) % 16 | 0;
				d = Math.floor(d / 16);
				return (c == 'x' ? r : (r & 0x3 | 0x8)).toString(16);
			});
			 document.fixedDeposit.customerID.value = uuid;
			 document.getElementById('futureDateOfBirthError').style.display = 'none';
			 
			var phoneNum = /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;
			var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
			var canSubmit = true;
			
			 var NRIRadioChecked = document.getElementById("NRIRadio").checked;
			 var RIRadioChecked = document.getElementById("RIRadio").checked;
			  if(NRIRadioChecked == false && RIRadioChecked == false ){
				
					document.getElementById('radioError').style.display = 'block';
					return false; 
			 } 
			/*  else
			 {
				 document.getElementById('radioError').style.display = 'none';
			 } */
			 
			/*  if(NRIRadioChecked == true){
					var nriAccountType = document.getElementById('nriAccountType').value;
					if(nriAccountType == ''){
						document.getElementById('nriAccountType').style.borderColor = "red";
						return false; 
					}
					else{
						document.getElementById('nriAccountType').style.borderColor = "black";
					}
				aadhar
			 } */
			if (document.getElementById('category').value == 'select') {

				document.getElementById('category').style.borderColor = "red";
				return false;
			} else {
				document.getElementById('category').style.borderColor = "black";

			}
			 
			/*  if (document.getElementById('aadhar').value == '') {

					document.getElementById('aadhar').style.borderColor = "red";
					return false;
				} else {
					document.getElementById('aadhar').style.borderColor = "black";
				} */


			if (document.getElementById('customerName').value == '') {

				document.getElementById('customerName').style.borderColor = "red";
				return false;
			} else {
				document.getElementById('customerName').style.borderColor = "black";
			}
			var custNameLengthCheck = document.getElementById('customerName').value;
			
			if (custNameLengthCheck.length < 3 || custNameLengthCheck.length > 100) {
				alert("Customer name should be greater than 3 and less than 100 !");
				document.getElementById('customerName').style.borderColor = "red";
				return false;
			} else {
				document.getElementById('customerName').style.borderColor = "black";
			}
			var userNameLengthCheck = document.getElementById('userName').value;

			if (document.getElementById('userName').value == '') {

				document.getElementById('userName').style.borderColor = "red";
				return false;
			} else {
				document.getElementById('userName').style.borderColor = "black";
			}
			
			
			if (userNameLengthCheck.length < 3 || userNameLengthCheck.length > 100) {
				alert("User name should be greater than 3 and less than 100 !");
				document.getElementById('userName').style.borderColor = "red";
				return false;
			} else {
				document.getElementById('userName').style.borderColor = "black";
			}
			
       if(document.getElementById('category').value!='NGO' && document.getElementById('category').value!='Charitable Organization'){
    	 
			if (document.getElementById('gender').value == '') {

				document.getElementById('gender').style.borderColor = "red";
				return false;
			} else {
				document.getElementById('gender').style.borderColor = "black";
			}
			
			
             }			
			if (document.getElementById('dateOfBirth').value == '') {

			    document.getElementById('dateOfBirth').style.borderColor = "red";
			    return false;
			   } 
			
			
			    var dateString = document.getElementById('dateOfBirth').value;
			    var parts = dateString.split('/');
			    var mydate = new Date(parts[2], parts[1] - 1, parts[0]);

			  //  var today = getTodaysDate();

			    if (mydate > today) {
			     document.getElementById('dateOfBirth').style.borderColor = "red";
			     document.getElementById('futureDateOfBirthError').style.display = 'block';
			     return false;
			    }
			      	var diff = (today-mydate)/1000/3600/24/365;
			      	var age_ = Math.floor(diff);
			      	$("#age_").val(age_);
			      	
			      	
			      	/* if(diff<18){
			      		
			      		
						var guardianName= document.getElementById('guardianName').value;
		       			var guardianAge= document.getElementById('guardianAge').value;
		       			
		       			var guardianAddress= document.getElementById('guardianAddress').value;
		       			var guardianRelationShip= document.getElementById('guardianRelationShip').value;
		       			
		       			var guardianAadhar = document.getElementById('guardianAadhar').value;
		       			var guardianPan= document.getElementById('guardianPan').value;
		       			
						
						
						
		       			
		       			
					} */
			      	
			      	
			      	debugger;
			      	
			      	var accBal0 = $("#accountBalance0").val();
			      	if(accBal0 == "" || parseInt(accBal0) < 0 ){
			      		document.getElementById('accountBalance0').style.borderColor = "red";
			      		
						canSubmit = false;
			      	}else if(accBal0!=undefined){
			      		document.getElementById('accountBalance0').style.borderColor = "black";
			      	}
			      	
			      	var accBal1  = $("#accountBalance1").val();
			      	if(accBal1 == "" || parseInt(accBal1) < 0 ){
			      		document.getElementById('accountBalance1').style.borderColor = "red";
						canSubmit = false;
			      	}else if(accBal1!=undefined){
			      		document.getElementById('accountBalance1').style.borderColor = "black";
			      	}
			      	
			      	var accBal2  = $("#accountBalance2").val();
			      	if(accBal2 == "" || parseInt(accBal2) < 0 ){
			      		document.getElementById('accountBalance2').style.borderColor = "red";
						canSubmit = false;
			      	}else if(accBal2!=undefined){
			      		document.getElementById('accountBalance2').style.borderColor = "black";
			      	}
			      	
			      	
			      	var acctNo0 = $("#accountNo0").val();
			      	if(acctNo0 == "" || parseInt(acctNo0) < 0 ){
			      		document.getElementById('accountNo0').style.borderColor = "red";
			      		
						canSubmit = false;
			      	}else if(acctNo0!=undefined){
			      		document.getElementById('accountNo0').style.borderColor = "black";
			      	}
			      	
			      	var acctNo1  = $("#accountNo1").val();
			      	if(acctNo1 == "" || parseInt(acctNo1) < 0 ){
			      		document.getElementById('accountNo1').style.borderColor = "red";
			      		
						canSubmit = false;
			      	}else if(acctNo1!=undefined){
			      		document.getElementById('accountNo1').style.borderColor = "black";
			      	}
			      	
			    	var accNo2  = $("#accountNo2").val();
			      	if(accNo2 == "" || parseInt(accNo2) < 0 ){
			      		document.getElementById('accountNo2').style.borderColor = "red";
			      		
						canSubmit = false;
			      	}else if(accNo2!=undefined){
			      		document.getElementById('accountNo2').style.borderColor = "black";
			      	}
			      	
			      	if(((acctNo0 == "" || parseInt(acctNo0) < 0)&&acctNo0!=undefined) || ((acctNo1 == "" || parseInt(acctNo1) < 0)&&acctNo1!=undefined) ||((accNo2 == "" || parseInt(accNo2) < 0)&&accNo2!=undefined) ){
			      		messages+="Account number field can not be empty.<br/>";
			      	}
			      	
			      	var accType=$('#accountType').val();
			      	var accType1=$('#accountType1').val();
			      	var accType2=$('#accountType2').val();
			      	if(accType!=undefined){
			      		document.getElementById('accountType').style.borderColor = "black";
			      	}
			      	if(accType1!=undefined){
		      		document.getElementById('accountType1').style.borderColor = "black";
			      	}
			      	if(accType2!=undefined){
		      		document.getElementById('accountType2').style.borderColor = "black";
			      	}
			      	if(accType==accType1 && accType != undefined){
			      		$('#lblMessageAccounts').show();
			      		document.getElementById('accountType').style.borderColor = "red";
			      		document.getElementById('accountType1').style.borderColor = "red";
						canSubmit = false;
			      	}else if(accType1==accType2 && accType1 != undefined){
			      		$('#lblMessageAccounts').show();
			      		
			      		document.getElementById('accountType1').style.borderColor = "red";
			      		document.getElementById('accountType2').style.borderColor = "red";
						canSubmit = false;
			      	}else if(accType==accType2 &&  accType != undefined){
			      		$('#lblMessageAccounts').show();
			      		alert("Multiple same type of accounts can not be saved.");
			      		document.getElementById('accountType').style.borderColor = "red";
			      		document.getElementById('accountType2').style.borderColor = "red";
						canSubmit = false;
			      	}
			      	
			      
			      	
			      	
			      	var minBal0 = $("#minimumBalance0").val();
			      	if(minBal0 == "" || parseInt(minBal0) < 0 ){
			      		document.getElementById('minimumBalance0').style.borderColor = "red";
			      		
						canSubmit = false;
			      	}else if(minBal0!=undefined){
			      		document.getElementById('minimumBalance0').style.borderColor = "black";
			      	}
			      	
			      	var minBal1  = $("#minimumBalance1").val();
			      	if(minBal1 == "" || parseInt(minBal1) < 0 ){
			      		document.getElementById('minimumBalance1').style.borderColor = "red";
			      		
						canSubmit = false;
			      	}else if(minBal1!=undefined){
			      		document.getElementById('minimumBalance1').style.borderColor = "black";
			      	}
			      	
			      	var minBal2  = $("#minimumBalance2").val();
			      	if(minBal2 == "" || parseInt(minBal2) < 0 ){
			      		document.getElementById('minimumBalance2').style.borderColor = "red";
			      		
						canSubmit = false;
			      	}else if(minBal2!=undefined){
			      		document.getElementById('minimumBalance2').style.borderColor = "black";
			      	}
			      	
			    	if(((minBal0 == "" || parseInt(minBal0) < 0)&& minBal0!=undefined) ||((minBal1 == "" || parseInt(minBal1) < 0)&& minBal1!=undefined) ||((minBal2 == "" || parseInt(minBal2) < 0)&& minBal2!=undefined) ){
			      		messages+="Minimum Balance must be greater than zero. <br/>";
			      	}
			      	 
		if(parseInt(document.getElementById('guardianAge').value)<18){
			 document.getElementById('guardianAgeError').style.display = 'block';
   			 return false;
			
		}
			 document.getElementById('dateOfBirth').style.borderColor = "black";

			
			if (document.getElementById('contactNum').value == '') {

				document.getElementById('contactNum').style.borderColor = "red";
				return false;
			} else {
				document.getElementById('contactNum').style.borderColor = "black";
			}
			if (contactNum.value
					.match(/^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/)
					&& !(document.getElementById('contactNum').value == '')) {

				document.getElementById('contactNum1Error').style.display = 'none';
			} else {

				document.getElementById('contactNum1Error').style.display = 'block';
				canSubmit = false;
			}
			if (altContactNum.value
					.match(/^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/)
					|| (document.getElementById('altContactNum').value == '')) {

				document.getElementById('contactNum2Error').style.display = 'none';
			} else {

				document.getElementById('contactNum2Error').style.display = 'block';
				canSubmit = false;
			}

			if (document.getElementById('email').value == '') {

				document.getElementById('email').style.borderColor = "red";
				return false;
			} else {
				document.getElementById('email').style.borderColor = "black";
			}
			if (email.value
					.match(/^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/)
					&& !(document.getElementById('email').value == '')) {

				document.getElementById('emailError').style.display = 'none';

			} else {
				document.getElementById('emailError').style.display = 'block';
				canSubmit = false;
			}

			if (altEmail.value
					.match(/^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/)
					|| (document.getElementById('altEmail').value == '')) {

				document.getElementById('altemailError').style.display = 'none';

			} else {
				document.getElementById('altemailError').style.display = 'block';
				canSubmit = false;
			}
			if (document.getElementById('address').value == '') {

				document.getElementById('address').style.borderColor = "red";
				return false;
			} else {
				document.getElementById('address').style.borderColor = "black";
			}
			
			var  addressLengthCheck = document.getElementById('address').value;
			
			if (addressLengthCheck.length < 8 || addressLengthCheck.length > 200) {
				alert("Address should be greater than 8 and less than 200 !");
				document.getElementById('address').style.borderColor = "red";
				return false;
			} else {
				document.getElementById('address').style.borderColor = "black";
			}
			
			
			if (document.getElementById('pincode').value == '') {

				document.getElementById('pincode').style.borderColor = "red";
				return false;
			} else {
				document.getElementById('pincode').style.borderColor = "black";
			}
			if (isNaN(document.getElementById('pincode').value)) {
				document.getElementById('pincodeError').style.display = 'block';
				document.getElementById('pincode').style.borderColor = "red";
				canSubmit = false;
			} else {
				var len = document.getElementById('pincode').value.length;
				
				if (len == 6) {
					document.getElementById('pincodeError').style.display = 'none';
				} else {
					document.getElementById('pincodeError').style.display = 'block';
					document.getElementById('pincode').style.borderColor = "red";
					canSubmit = false;
				}
			}

			if (document.getElementById('country').value == '-1') {

				document.getElementById('country').style.borderColor = "red";
				return false;
			} else {
				document.getElementById('country').style.borderColor = "black";
			}
			if (document.getElementById('state').value == '-1') {

				document.getElementById('state').style.borderColor = "red";
				return false;
			} else {
				document.getElementById('state').style.borderColor = "black";
			}

			if (document.getElementById('city').value == '') {

				document.getElementById('city').style.borderColor = "red";
				return false;

			} else {
				document.getElementById('city').style.borderColor = "black";
			}

			var  cityLengthCheck = document.getElementById('city').value;
			
			if (cityLengthCheck.length < 3 || cityLengthCheck.length > 100) {
				alert("City should be greater than 3 and less than 100 !");
				document.getElementById('city').style.borderColor = "red";
				return false;
			} else {
				document.getElementById('city').style.borderColor = "black";
			}
			
		
			if(window.n>0){
			for(var i=0;i<window.n;i++){
				
				if (isNaN(document.getElementById('accountBalance'+i).value)) {
					document.getElementById('accountBalance'+i).style.borderColor = "red";
					document.getElementById('accountBalanceError'+i).style.display = 'block';
					canSubmit = false;
				} else {
					//document.getElementById('accountBalance'+i).style.borderColor = "black";
					document.getElementById('accountBalanceError'+i).style.display = 'none';
				}
			}
			}
			
			if(window.n>0){
				for(var i=0;i<window.n;i++){
					
					if (isNaN(document.getElementById('accountNo'+i).value)) {
						document.getElementById('accountNo'+i).style.borderColor = "red";
						document.getElementById('accountNumError'+i).style.display = 'block';
						canSubmit = false;
					} else {
						//document.getElementById('accountNo'+i).style.borderColor = "black";
						document.getElementById('accountNumError'+i).style.display = 'none';
					}
				}
				}
			
			if(window.n>0){
				for(var i=0;i<window.n;i++){
					
					if (isNaN(document.getElementById('minimumBalance'+i).value)) {
						document.getElementById('minimumBalance'+i).style.borderColor = "red";
						document.getElementById('minimumBalanceError'+i).style.display = 'block';
						canSubmit = false;
					} else {
						//document.getElementById('minimumBalance'+i).style.borderColor = "black";
						document.getElementById('minimumBalanceError'+i).style.display = 'none';
					}
				}
				}
			
			
            var panVal = $('#pan').val();
			
			var aadharLen = document.getElementById('aadhar').value.length;
			
			var regpan = /^([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}?$/;
			if(panVal!==''){
			if(regpan.test(panVal)){
			
				document.getElementById('pan').style.borderColor = "black";
			}else {
				
				document.getElementById('pan').style.borderColor = "red";
				document.getElementById('panCardError').style.display = 'block';
				canSubmit = false;
			}
			}
			/* if (aadharLen==12) {

				document.getElementById('aadharNum1Error').style.display = 'none';
			} else {

				document.getElementById('aadharNum1Error').style.display = 'block';
				canSubmit = false;
				
				 return canSubmit;
			} */
			
			
			
			if (diff<18 && canSubmit){
	    		
	    			var modal = document.getElementById('myModal');
	           	 modal.style.display = "block";
	           	 
	         	var guardianName= document.getElementById('guardianName').value;
       			var guardianAge= parseInt(document.getElementById('guardianAge').value);
       			
       			var guardianAddress= document.getElementById('guardianAddress').value;
       			var guardianRelationShip= document.getElementById('guardianRelationShip').value;
       			
       			var guardianAadhar= document.getElementById('guardianAadhar').value;
       			var guardianPan= document.getElementById('guardianPan').value;
       			
       			
       			
       			
       			var flag = 0;
       			if(guardianName == ""){
       				
       				document.getElementById('guardianName').style.borderColor = "red";
       				canSubmit = false;
       				
       			}else{
       				document.getElementById('guardianName').style.borderColor = "black";
       				canSubmit = true;
       				flag++;
       			}
       			
				
				
				if(guardianAge == ""){
					document.getElementById('guardianAge').style.borderColor = "red";
					canSubmit = false;
					
       			}else{
       				document.getElementById('guardianAge').style.borderColor = "black";
       				canSubmit = true;
       				flag++;
       			}
				var adharLen = guardianAadhar;
       			
			/* 	if(adharLen.length < 12){
					
					document.getElementById('guardianAadhar').style.borderColor = "red";
					canSubmit = false;
					
       			}else{
       				document.getElementById('guardianAadhar').style.borderColor = "black";
       				canSubmit = true;
       				flag++;
       			}
				
				if(guardianAadhar == "" ){
					document.getElementById('guardianAadhar').style.borderColor = "red";
					canSubmit = false;
					
       			} */
				
				
				
				if(guardianRelationShip == ""){
       				document.getElementById('guardianRelationShip').style.borderColor = "red";
       				canSubmit = false;
       			}else{
       				document.getElementById('guardianRelationShip').style.borderColor = "black";
       				canSubmit = true;
       				flag++;
       			}
				
				if(guardianAddress == ""){
       				document.getElementById('guardianAddress').style.borderColor = "red";
       				canSubmit = false;
       			}else{
       				document.getElementById('guardianAddress').style.borderColor = "black";
       				canSubmit = true;
       				flag++;
       			}
				
			/*	if(guardianPan != ""){
					
					var regpan = /^([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}?$/;
					if(regpan.test(guardianPan)){
						document.getElementById('guardianPan').style.borderColor = "black";
						canSubmit = true;
						flag++;
					}else {
						document.getElementById('guardianPan').style.borderColor = "red";
						canSubmit = false;
					}
       			}else{
       				document.getElementById('guardianPan').style.borderColor = "red";
					canSubmit = false;
       			}*/
				if(parseInt(guardianAge) > 100){
					document.getElementById('guardianAge').style.borderColor = "red";
					canSubmit = false;
					
       			}
				
				if(flag < 4){
					canSubmit = false;
				
				}
				
				
				
       			
       			
       			
       		
       			if(guardianName!="" && !isNaN(guardianAge) && guardianAddress!="" && guardianRelationShip!=""){
       				
       				document.getElementById('guardianError').style.display ='none';
       			}
       			else{
       			 document.getElementById('guardianError').style.display = 'block';
       			 return false;
       			}
       			
	    	}
	      
			 debugger;
			if(canSubmit==true){
				   $("#addCustomerForm").attr("action", "customerConfirm");
				    $("#addCustomerForm").submit();

			}
			else{
				$('#lblErrMessage').html(messages);
			}
		

			
		}
		
		

		$(document)
				.ready(
						function() {
							$(".search")
									.keyup(
											function() {
												var searchTerm = $(".search")
														.val();
												var listItem = $(
														'.results tbody')
														.children('tr');
												var searchSplit = searchTerm
														.replace(/ /g,
																"'):containsi('")

												$
														.extend(
																$.expr[':'],
																{
																	'containsi' : function(
																			elem,
																			i,
																			match,
																			array) {
																		return (elem.textContent
																				|| elem.innerText || '')
																				.toLowerCase()
																				.indexOf(
																						(match[3] || "")
																								.toLowerCase()) >= 0;
																	}
																});

												$(".results tbody tr").not(
														":containsi('"
																+ searchSplit
																+ "')").each(
														function(e) {
															$(this).attr(
																	'visible',
																	'false');
														});

												$(
														".results tbody tr:containsi('"
																+ searchSplit
																+ "')").each(
														function(e) {
															$(this).attr(
																	'visible',
																	'true');
														});

												var jobCount = $('.results tbody tr[visible="true"]').length;
												$('.counter').text(
														jobCount + ' item');

												if (jobCount == '0') {
													$('.no-result').show();
												} else {
													$('.no-result').hide();
												}
											});
						});
	</script>




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