<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<style>
.add-customer-table2 {
	float: left;
	width: 100%;
	padding: 0px 30px;
}
</style>
<div class="right-container" id="right-container">
	<div class="container-fluid">

		<div class="add-customer">
			<form:form action="editcustomerPost" name="fixedDeposit"
				autocomplete="off" method="post" class="form-horizontal"
				commandName="customerForm" onsubmit="return val();">
				<div class="successMsg">
					<b><font color="green">${success}</font></b>
				</div>
				<div class="successMsg">
					<b><font color="error">${error}</font></b>
				</div>
				<div class="header_customer" style="margin-bottom: 20px;">
					<h3>
						<spring:message code="label.editCustomer" />
					</h3>
				</div>
				<div class="newcustomer_border">

					<div class="add-customer-table">
						<form:hidden path="id" />
						<form:hidden path="userName" />
						<div class="col-md-6">
						
							 
					<div>
						<div id="addRateCategory" class="collapse in">
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.customerCategory" /><span style="color: red">*</span></label>

								<c:choose>
									<c:when test="${setCategory!=null}">
										<div class="col-md-8">

											<form:select id="setCategory" path="category"
												class="form-control" required="true" onchange="getDepositClassification()">
												
												<c:forEach var="item" items="${setCategory}">

													<option value="${item.customerCategory}"
														<c:if test="${item.customerCategory eq selectedCategory}"><spring:message code="label.selected" /></c:if>>${item.customerCategory}</option>
												</c:forEach>
											</form:select>


										</div>
									</c:when>
									<c:otherwise>
										<div class="col-md-6">
											<form:select id="category" path="type" class="myform-control">
												<option value="select"><spring:message
														code="label.select" /></option>

											</form:select>
										</div>
									</c:otherwise>
								</c:choose>

							</div>

						</div>
					</div>
							
							
							
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.customerName" /><span style="color: red">*</span></label>
								<div class="col-md-8">
									<form:input path="customerName" placeholder="Enter Name"
										class="form-control" required="true"></form:input>
									<span id="nameError" class="error"
										style="display: none; color: red;"><font color="red"><spring:message
												code="label.validation" />*</font></span> <span id="nameError"
										class="error" style="display: none; color: red;"><spring:message
											code="label.validation" /></span>
								</div>
							</div>

                      <c:if test="${accDetails.citizen=='NRI'}">
							<div class="form-group"  style="clear: both;" id="accountTypeDIV">
							<label class="col-md-4 control-label"><spring:message
									code="label.nriAccountType" /><span style="color: red">*</span></label>

							<div class="col-md-6">
								<form:select id="nriAccountType" path="nriAccountType"
									class="input form-control" onchange="accountTypeVal(this.value)">
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
				</c:if>	
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.gender" /><span style="color: red">*</span></label>
								<div class="col-md-8">
									<form:select path="gender" class="form-control" required="true">
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
								<label class="col-md-4 control-label">Date of Birth<span
									style="color: red">*</span></label>
								<div class="col-md-8">
									<form:input path="dateOfBirth" placeholder="Select Date"
										class="form-control" id="datepicker" required="true"
										readonly="true" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.contactNumber" /><span style="color: red">*</span></label>
								<div class="col-md-8">
									<form:input path="contactNum"
										placeholder="Enter contact number" id="contactNum"
										class="form-control" required="true"></form:input>
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
										id="altContactNum" class="form-control"></form:input>
									<span id="contactNum2Error" class="error"
										style="display: none; color: red;"><font color="red"><spring:message
												code="label.incorrectFormat" /></font></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.email" /><span style="color: red">*</span></label>
								<div class="col-md-8">
									<form:input path="email" placeholder="Enter email id"
										id="email" class="form-control" required="true"></form:input>
									<span id="emailError" class="error"
										style="display: none; color: red;"><font color="red"><spring:message
												code="label.incorrectFormat" /></font></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.altEmail" /></label>
								<div class="col-md-8">
									<form:input path="altEmail"
										placeholder="Enter alternative email id" id="altEmail"
										class="form-control"></form:input>
									<span id="altemailError" class="error"
										style="display: none; color: red;"><font color="red"><spring:message
												code="label.incorrectFormat" /></font></span>
								</div>
							</div>
						</div>


						<div class="col-md-6">
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.address" /><span style="color: red">*</span></label>
								<div class="col-md-8">
									<form:input path="address" placeholder="Enter address"
										id="address" class="form-control" required="true"></form:input>
									<span id="addressError" class="error"
										style="display: none; color: red;"><font color="red"><spring:message
												code="label.validation" /></font></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.pincode" /><span style="color: red">*</span></label>
								<div class="col-md-8">
									<form:input path="pincode" placeholder="Enter pincode"
										id="pincode" class="form-control" required="true"></form:input>
									<span id="pincodeError" class="error"
										style="display: none; color: red;"><font color="red"><spring:message
												code="label.incorrectFormat" /></font></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.country" /><span style="color: red">*</span></label>
								<div class="col-md-8">
									<select name="country" id="country"
										onchange="populateStatesOnchange('country', 'state','${newclientForm.country}')"
										class="form-control" required="true">
									</select>
									<form:hidden path="country" id="ctr" />
									<span id="countryError" style="display: none; color: red;">
										<spring:message code="label.validation" />
									</span>

								</div>
							</div>
							<script>
							populateCountriesEdit("country","${customerForm.country}");
					    </script>

							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.state" /></b><span style="color: red">*</span></label>
								<div class="col-md-8">
									<select id="state" class="form-control" autocomplete="off"
										required="true" onchange="onchangeState(this.value)">
									</select>

									<form:hidden path="state" id="st" />
									<script>
					
										populateStateEdit("country", "state","${customerForm.state}","${customerForm.country}");
										
									</script>
									<span id="stateError" style="display: none; color: red;">
										<spring:message code="label.validation" />
									</span>
								</div>
							</div>

							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.city" /><span style="color: red">*</span></label>
								<div class="col-md-8">
									<form:input path="city" placeholder="Enter city" id="city"
										class="form-control" required="true"></form:input>
									<span id="cityError" class="error"
										style="display: none; color: red;"><font color="red"><spring:message
												code="label.validation" />*</font></span> <span id="cityError"
										class="error" style="display: none; color: red;"><spring:message
											code="label.validation" /></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.pan" /></label>
								<div class="col-md-8">
									<form:input path="pan" placeholder="Enter pan" id="pan"
										class="form-control" style="text-transform:uppercase"></form:input>
								</div>
							</div>
							<div class="successMsg">
								<b><font color="error">${errorPan}</font></b>
							</div>
							<span id="panCardError" class="error" style="display: none;"><font
								color="red"><spring:message code="label.panCardError" /></font></span>
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.aadhar" /></label>
								<div class="col-md-8">
									<form:input path="aadhar" placeholder="Enter aadhar"
										id="aadhar" class="form-control" />
								</div>
							</div>
							<div class="successMsg">
								<b><font color="error">${errorAadhar}</font></b>
							</div>
							<span id="aadharNum1Error" class="error" style="display: none;"><font
								color="red"><spring:message code="label.aadharError" /></font></span>

						</div>

					</div>

					<c:if test="${not empty accDetails.guardianName}">
						<div class="add-customer-table">
							<div style="clear: both;"></div>
							<div class="col-md-12">
								<div class="header_customer"
									style="margin-bottom: 20px; margin-top: 20px;">
									<h3>Edit Guardian</h3>
								</div>
							</div>
							<div class="col-md-6">

								<div class="form-group">
									<label class="col-md-4 control-label"><spring:message
											code="label.guardianName" /><span style="color: red">*</span></label>
									<div class="col-md-8">
										<form:input path="guardianName" placeholder="Enter Name"
											class="form-control" id="guardianName" required="true"></form:input>
										<span id="nameError" class="error"
											style="display: none; color: red;"><font color="red"><spring:message
													code="label.validation" />*</font></span> <span id="nameError"
											class="error" style="display: none; color: red;"><spring:message
												code="label.validation" /></span>
									</div>
								</div>


								<div class="form-group">
									<label class="col-md-4 control-label">Guardian Age<span
										style="color: red">*</span></label>
									<div class="col-md-8">
										<form:input path="guardianAge" placeholder="Enter Age"
											class="form-control" id="guardianAge" required="true" />
									</div>
								</div>

								<div class="form-group">
									<label class="col-md-4 control-label">Guardian Address<span
										style="color: red">*</span></label>
									<div class="col-md-8">
										<form:input path="guardianAddress" placeholder="Enter Address"
											class="form-control" id="guardianAddress" required="true" />
									</div>
								</div>

							</div>
							<div class="col-md-6">

								<div class="form-group">
									<label class="col-md-4 control-label">Guardian
										Relationship<span style="color: red">*</span>
									</label>
									<div class="col-md-8">
										<form:input path="guardianRelationShip"
											placeholder="Enter contact number" id="guardianRelationShip"
											class="form-control" required="true"></form:input>
										<span id="contactNum1Error" class="error"
											style="display: none;"><font color="red"><spring:message
													code="label.incorrectFormat" /></font></span>
									</div>
								</div>


								<div class="form-group">
									<label class="col-md-4 control-label">Guardian Pan<span
										style="color: red">*</span></label>
									<div class="col-md-8">
										<form:input path="guardianPan" placeholder="Enter email id"
											id="guardianPan" class="form-control"
											style="text-transform:uppercase" required="true"></form:input>
										<span id="emailError" class="error"
											style="display: none; color: red;"><font color="red"><spring:message
													code="label.incorrectFormat" /></font></span>
									</div>
								</div>

								<div class="form-group">
									<label class="col-md-4 control-label">Guardian Aadhar</label>
									<div class="col-md-8">
										<form:input path="guardianAadhar"
											placeholder="Enter alternative email id" id="guardianAadhar"
											class="form-control" required="true"></form:input>
										<span id="altemailError" class="error"
											style="display: none; color: red;"><font color="red"><spring:message
													code="label.incorrectFormat" /></font></span>
									</div>
								</div>


							</div>



						</div>
					</c:if>

					<div class="col-sm-12">
						<hr>
					</div>
					<c:if test="${accountDetail ne 0}">
						<div class="add-customer-table2">
							<div class="addmybtn">
								<div class="add-btn">
									<input type="button" id="add" class="btn btn-success"
										value="Add Account">
								</div>
							</div>

							<!--  Script for Add Account details -Ravikiran -->

							<script>
					var n ;
					
				$(document).ready(function(){
					
					var arrayLength = <c:out value="${customerForm.accountDetails.size()}"/>;
					var cat = '${accDetails.category}';
					document.getElementById('setCategory').value = cat ;
					
					window.n = arrayLength;
					
					$("#add").click(function (e) {
						//Append a new row of code to the "#items" div
						
						if (window.n <=2) {
							
							if(window.n==0){ 
							$("#items").append('<div class="form-1 col-sm-6 col-xs-12"><div class="add-personal-details field"><h4><spring:message code="label.addaccountDeatils"/></h4></div><table class="theader" width="400" style="position: relative;left: 2.6%;"><tr><td><b><spring:message code="label.accountType"/></b></td><td><form:select path="accountType" class="myform-control"   id="accountType0" onchange="check(this.id)"><form:option value="Savings"  ><spring:message code="label.Saving"/></form:option><form:option value="Current"><spring:message code="label.Current"/></form:option><form:option value="Deposit"></form:option></form:select> </td></tr><tr><td><b><spring:message code="label.accountBalance"/></b><span style="color:red">*</span></td><td><form:input path="accountBalance" placeholder="Account Balance"  onkeypress="validationAccount(event)" id="accountBalance0"  class="myform-control" required="true"></form:input></td></tr><tr><td><b><spring:message code="label.accountNo"/></b></td><td><form:input path="accountNo" placeholder="Enter account number" onkeypress = "validationAccountNumberType(event)" id="accountNo0"   class="myform-control" required="true"></form:input><span id="accountNum0Error" class="error" style="display:none;"><font color="red"><spring:message code="label.incorrectFormat"/></font></span></td></tr><tr><td></td><td><span id="accountBalanceError0" class="error" style="display:none;color:red;"><font color="red"><spring:message code="label.incorrectFormat"/>*</font></span></td></tr><tr><td><b><spring:message code="label.minimumBalance"/></b><span style="color:red">*</span></td><td><form:input path="minimumBalance" placeholder="Minimum Balance" id="minimumBalance0" onkeypress="validationAccount(event)"  class="myform-control" required="true"></form:input></td></tr></table><button id="b1" class="btn btn-danger removebtn" type="button">Delete</button></div>');
							}
							if(window.n==1){
								$("#items").append('<div class="form-1 col-sm-6 col-xs-12"><div class="add-personal-details field"><h4><spring:message code="label.addaccountDeatils"/></h4></div><table class="theader" width="400" style="position: relative;left: 2.6%;"><tr><td><b><spring:message code="label.accountType"/></b></td><td><form:select path="accountType" class="myform-control" id="accountType1" onchange="check1(this.id)"><form:option value="Savings" ><spring:message code="label.Saving"/></form:option><form:option value="Current"><spring:message code="label.Current"/></form:option><form:option value="Deposit"></form:option></form:select> </td></tr><tr><td><b><spring:message code="label.accountBalance"/></b><span style="color:red">*</span></td><td><form:input path="accountBalance" placeholder="Account Balance" onkeypress="validationAccount(event)" id="accountBalance1"  class="myform-control" required="true"></form:input></td></tr><tr><td><b><spring:message code="label.accountNo"/></b></td><td><form:input path="accountNo" placeholder="Enter account number" onkeypress = "validationAccountNumberType1(event)" id="accountNo1"  class="myform-control" required="true"></form:input><span id="accountNum1Error" class="error" style="display:none;"><font color="red"><spring:message code="label.incorrectFormat"/></font></span></td></tr><tr><td></td><td><span id="accountBalanceError1" class="error" style="display:none;color:red;"><font color="red"><spring:message code="label.incorrectFormat"/>*</font></span></td></tr><tr><td><b><spring:message code="label.minimumBalance"/></b><span style="color:red">*</span></td><td><form:input path="minimumBalance" placeholder="Minimum Balance" id="minimumBalance1" onkeypress="validationAccount(event)"    class="myform-control" required="true"></form:input></td></tr></table><button id="b1" class="btn btn-danger removebtn" type="button">Delete</button></div>');
								}
							if(window.n==2){
								$("#items").append('<div class="form-1 col-sm-6 col-xs-12"><div class="add-personal-details field"><h4><spring:message code="label.addaccountDeatils"/></h4></div><table class="theader" width="400" style="position: relative;left: 2.6%;"><tr><td><b><spring:message code="label.accountType"/></b></td><td><form:select path="accountType" class="myform-control" id="accountType2" onchange="check2(this.id)"><form:option value="Savings"  ><spring:message code="label.Saving"/></form:option><form:option value="Current"><spring:message code="label.Current"/></form:option><form:option value="Deposit"></form:option></form:select> </td></tr><tr><td><b><spring:message code="label.accountBalance"/></b><span style="color:red">*</span></td><td><form:input path="accountBalance" placeholder="Account Balance" onkeypress="validationAccount(event)" id="accountBalance2"  class="myform-control" required="true"></form:input></td></tr><tr><td><b><spring:message code="label.accountNo"/></b></td><td><form:input path="accountNo" placeholder="Enter account number" onkeypress = "validationAccountNumberType2(event)" id="accountNo2"  class="myform-control" required="true"></form:input><span id="accountNum2Error" class="error" style="display:none;"><font color="red"><spring:message code="label.incorrectFormat"/></font></span></td></tr><tr><td></td><td><span id="accountBalanceError2" class="error" style="display:none;color:red;"><font color="red"><spring:message code="label.incorrectFormat"/>*</font></span></td></tr><tr><td><b><spring:message code="label.minimumBalance"/></b><span style="color:red">*</span></td><td><form:input path="minimumBalance" placeholder="Minimum Balance" id="minimumBalance2" onkeypress="validationAccount(event)"  class="myform-control" required="true"></form:input></td></tr></table><button id="b1" class="btn btn-danger removebtn" type="button">Delete</button></div>');
								}
							window.n=parseInt(window.n)+1;
						   
						}
						else{
							alert("Only three Accounts are allowed!")
						}
					});
			
				
					$('#items').on('click','.removebtn',function() {
					    $(this).parent().remove();
					    window.n=parseInt(window.n)-1;
					});
				});
		</script>
							<c:forEach items="${customerForm.accountDetails}" var="account"
								varStatus="status">
								<div class="col-sm-6 col-xs-12">
									<form:hidden path="accountId" value="${account.id}" />


									<table class="theader" width="400">

										<tr>
											<td><b><spring:message code="label.accountType" /></b></td>

											<td><form:input path="accountType"
													id="accountType${status.index}"
													value="${account.accountType}" readonly="true"
													placeholder="Account Type" class="form-control"></form:input></td>

										</tr>
										<tr>
											<td><b><spring:message code="label.accountBalance" /></b><span
												style="color: red">*</span></td>

											<%-- 									<td><form:input path="accountBalance" --%>
											<%-- 											value="${account.accountBalance}" readonly="true" placeholder="Displays" --%>
											<%-- 											id="accountBalance${status.index}" class="form-control" ></form:input></td> --%>
											<fmt:formatNumber value="${account.accountBalance}"
												pattern="#.##" var="accBal" />
											<td><form:input path="accountBalance" value="${accBal}"
													readonly="true" placeholder="Displays"
													id="accountBalance${status.index}"   class="form-control"></form:input></td>
											<span id="accountBalanceError${status.index}" class="error"
												style="display: none; color: red;"><font color="red"><spring:message
														code="label.incorrectFormat" />*</font></span>

										</tr>

										<tr>
											<td><b><spring:message code="label.accountNo" /></b></td>
											<td><form:input path="accountNo"
													placeholder="Enter account number"
													value="${account.accountNo}" id="accountNo${status.index}"
													class="form-control" required="true" onkeypress="validationAccount(event)"></form:input> <span
												id="accountNum2Error" class="error" style="display: none;"><font
													color="red"><spring:message
															code="label.incorrectFormat" /></font></span></td>
										</tr>

										<tr>
											<td><b><spring:message code="label.minimumBalance" /></b><span
												style="color: red">*</span></td>

											<fmt:formatNumber value="${account.minimumBalance}"
												pattern="#.##" var="minimumBal" />
											<td><form:input path="minimumBalance" value="${minimumBal}"
													required="true" placeholder="Displays"
													id="minimumBalance${status.index}" onkeypress="validationAccount(event)" class="form-control"></form:input></td>
											<span id="minimumBalanceError${status.index}" class="error"
												style="display: none; color: red;"><font color="red"><spring:message
 														code="label.incorrectFormat" />*</font></span> 

										</tr>

									</table>

									<a
										data-confirm="Are you sure to delete the account permanently?"
										href="deleteAccount?id=${account.id}&customerId=${customerForm.id}"
										class="btn btn-danger delete"><spring:message
											code="label.delete" /></a>
									<script>
								
								var deleteLinks = document.querySelectorAll('.delete');

								for (var i = 0; i < deleteLinks.length; i++) {
								  deleteLinks[i].addEventListener('click', function(event) {
									  event.preventDefault();

									  var choice = confirm(this.getAttribute('data-confirm'));

									  if (choice) {
									    window.location.href = this.getAttribute('href');
									  }
								  });
								}
								</script>
								</div>


							</c:forEach>
						</div>
					</c:if>
					<div class="col-sm-12">

						<hr>
					</div>
					<div id="items" class="col-sm-12"></div>
				</div>
				<span id="futureDateOfBirthError" style="display: none; color: red">Future
					Date Selected</span>
				<div class="save-back">
				<p align="center" style="padding-top: 10px;">
					<label id="lblMessageAccounts" style="color:red;display:none;">Multiple same type of accounts can not be saved.</label></p>
					<%-- <span style="color:red">${error}</span> --%>
					<p align="center" style="padding-top: 10px;">

						<a href="customerList" class="btn btn-success"><spring:message
								code="label.back" /></a> <input type="submit"
							class="btn btn-primary"
							value="<spring:message code="label.confirm"/>">
					</p>
				</div>


			</form:form>
		</div>

		<script>
		
		
		
		
		$(document).ready(
				function() {
					
					
					
					
					$('#accountNo1').keypress(function(){
					alert('ok');
						
						 
						});
					
					
					
					
					
					/* var accountNo0_ = document.getElementById('accountNo0');
					var accountNo1_ = document.getElementById('accountNo1');
					var accountNo2_ = document.getElementById('accountNo2');
					
					accountNo0_.keydown = function(event) {
						
				
						var accountType0Value = $("#accountType0").val();
						if(accountType0Value == 'Deposit'){
							var keycode = event.which;
						    if (!(event.shiftKey == false && (keycode == 8 || keycode == 37 || (keycode >= 65 && keycode <= 90) ||(keycode >= 48 && keycode <= 57) || (keycode > 96 && keycode < 123) || (keycode > 64 && keycode < 91) || !keycode ==  32 ))) {
						        event.preventDefault();
						    }
							
						}else{
							 
							var keycode = event.which;
						    if (!(event.shiftKey == false && (keycode == 8 || keycode == 37 || (keycode >= 48 && keycode <= 57)))) {
						        event.preventDefault();
						    }
							
						}
						
						
					    
					} */
					
					
					
					
				/*  accountNo1_.onkeydown = function(event) {
						
						var accountType0Value = $("#accountType1").val();
						if(accountType0Value == 'Deposit'){
							var keycode = event.which;
						    if (!(event.shiftKey == false && (keycode == 46 || keycode == 8 || keycode == 37 ||keycode == 106|| (keycode >= 65 && keycode <= 90) ||(keycode >= 48 && keycode <= 57) || (keycode > 96 && keycode < 123) || (keycode > 64 && keycode < 91) ))) {
						        event.preventDefault();
						    }
							
						}else{
							 
							var keycode = event.which;
						    if (!(event.shiftKey == false && (keycode == 46 || keycode == 8 || keycode == 37 || (keycode >= 48 && keycode <= 57)))) {
						        event.preventDefault();
						    }
							
						}
						
						
					    
					} */
					
					/* accountNo2_.onkeydown = function(event) {
						
						
						var accountType20Value = $("#accountType2").val();
						if(accountType20Value == 'Deposit'){
						
							var keycode = event.which;
						    if (!(event.shiftKey == false && (keycode == 8 || keycode == 37 (keycode >= 65 && keycode <= 90) ||(keycode >= 48 && keycode <= 57) || (keycode > 96 && keycode < 123) || (keycode > 64 && keycode < 91) ))) {
						        event.preventDefault();
						    }
							
						}else{
							 
							var keycode = event.which;
						    if (!(event.shiftKey == false && (keycode == 46 || keycode == 8 || keycode == 37 || (keycode >= 48 && keycode <= 57)))) {
						        event.preventDefault();
						    }
							
						}
						
						
					    
					} */
					 
					
			
				
				});
		
		
		
		
		



function onchangeState(value){
	document.getElementById('st').value =value;
}

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

	$(document).ready(
			function() {
				$(':input', '#customerHeadForm').not(
						':button, :submit, :reset, :hidden').val('')
						
			document.getElementById('ctr').value ='${customerForm.country}';
			document.getElementById('st').value ='${customerForm.state}';
		
			
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
	
	
	
	
	
	

	function val() {
		var canSubmit = true;
		$('#lblMessageAccounts').hide();
		var saving=0;
	
	
	
		var phoneNum = /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;
		var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
		

	if (document.getElementById('setCategory').value == 'select') {

			document.getElementById('setCategory').style.borderColor = "red";
			canSubmit = false;
		} else {
			document.getElementById('setCategory').style.borderColor = "black";

		}
 
		if (document.getElementById('customerName').value == '') {

			document.getElementById('customerName').style.borderColor = "red";
			canSubmit = false;
		} else {
			document.getElementById('customerName').style.borderColor = "black";
		}
		
		
		if (document.getElementById('gender').value == '') {

			document.getElementById('gender').style.borderColor = "red";
			canSubmit = false;
		} else {
			document.getElementById('gender').style.borderColor = "black";

		}
		if (document.getElementById('datepicker').value == '') {

			document.getElementById('datepicker').style.borderColor = "red";
			canSubmit = false;
		} else {
			document.getElementById('datepicker').style.borderColor = "black";

		}
		if (document.getElementById('contactNum').value == '') {

			document.getElementById('contactNum').style.borderColor = "red";
			canSubmit = false;
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
			canSubmit = false;
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
			canSubmit = false;
		} else {
			document.getElementById('address').style.borderColor = "black";
		}
		if (document.getElementById('pincode').value == '') {

			document.getElementById('pincode').style.borderColor = "red";
			canSubmit = false;
		} else {
			document.getElementById('pincode').style.borderColor = "black";
		}
		if (isNaN(document.getElementById('pincode').value)) {
			document.getElementById('pincodeError').style.display = 'block';
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
			canSubmit = false;
		} else {
			document.getElementById('state').style.borderColor = "black";
		}

		if (document.getElementById('city').value == '') {

			document.getElementById('city').style.borderColor = "red";
			canSubmit = false;

		} else {
			document.getElementById('city').style.borderColor = "black";
		}
debugger;
		var accType=$('#accountType0').val();
      	var accType1=$('#accountType1').val();
      	var accType2=$('#accountType2').val();
      	if(accType==accType1){
      		$('#lblMessageAccounts').show();
      		document.getElementById('accountType0').style.borderColor = "red";
      		document.getElementById('accountType1').style.borderColor = "red";
			canSubmit = false;
      	}else if(accType1==accType2){
      		$('#lblMessageAccounts').show();
      		alert("Multiple same type of accounts can not be saved.");
      		document.getElementById('accountType1').style.borderColor = "red";
      		document.getElementById('accountType2').style.borderColor = "red";
			canSubmit = false;
      	}else if(accType==accType2){
      		$('#lblMessageAccounts').show();
      		alert("Multiple same type of accounts can not be saved.");
      		document.getElementById('accountType0').style.borderColor = "red";
      		document.getElementById('accountType2').style.borderColor = "red";
			canSubmit = false;
      	}
		if(window.n>0){
		
		for(var i=0;i<window.n;i++){
		
		
			if (isNaN(document.getElementById('accountBalance'+i).value)) {
				document.getElementById('accountBalance'+i).style.borderColor = "red";
				
				document.getElementById('accountBalanceError'+i).style.display = 'block';
				canSubmit = false;
			} else {
				
				document.getElementById('accountBalance'+i).style.borderColor = "green";
				document.getElementById('accountBalanceError'+i).style.display = 'none';
			}
			
		}
		
		  var panVal = $('#pan').val();
			
			var aadharLen = document.getElementById('aadhar').value.length
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
			if (aadharLen==12) {

				document.getElementById('aadharNum1Error').style.display = 'none';
			} else {

				document.getElementById('aadharNum1Error').style.display = 'block';
				canSubmit = false;
			}
			
		
		
		
		}
		
		
		return canSubmit;
	
	}
	
	
	$(document).ready(function(){
		
	});

	$(document).ready(function() {
						
		var number = document.getElementById('contactNum');
		var alternateNumber = document.getElementById('altContactNum');
		var aadhar = document.getElementById('aadhar');
		var pincode = document.getElementById('pincode');
		
		
		var userName = document.getElementById('userName');
		
		var customerName = document.getElementById('customerName');
						$('#pan').keyup(function(e){
							
							var panVal = $(this).val();
							var regpan = /^([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}?$/;
					if(panVal.length > 9){
						
							if(regpan.test(panVal)){
								if(e.keyCode != 144){
							  
								}
							} else {
								if( e.keyCode != 144){
								alert("invalid pan card number");
								}
							}
					}
						});
						
						
						$('#userName').bind('keypress', function (event) {
					        var regex = new RegExp("^[a-zA-Z0-9]+$");
					        var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
					        if (!regex.test(key)) {
					        	//alert("false")
					            event.preventDefault();
					            return false;
					        }
					    });
						
						$('#customerName').bind('keypress', function (event) {
					        var regex = new RegExp("^[a-zA-Z ]+$");
					        var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
					        if (!regex.test(key)) {
					        	//alert("false")
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
						
						
						
						
						
						
						
						
						
						
						$(".search").keyup(function(){
											var searchTerm = $(".search").val();
											var listItem = $('.results tbody')
													.children('tr');
											var searchSplit = searchTerm
													.replace(/ /g,
															"'):containsi('")

											$.extend($.expr[':'],
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
														$(this).attr('visible',
																'false');
													});

											$(
													".results tbody tr:containsi('"
															+ searchSplit
															+ "')").each(
													function(e) {
														$(this).attr('visible',
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
	

	function validationAccount(event){
		
		var minimumBalance1_ = document.getElementById(event.target.id);

			 var keycode = event.which;
		    if (!(event.shiftKey == false && (keycode == 8 || keycode == 37 ||  (keycode >= 48 && keycode <= 57)))) {
		        event.preventDefault();
		        return false;
		    } 
		   
		    
		    if (minimumBalance1_.value.length>10 && event.keyCode != 8){
		    	return false;
		    }
		
		
		 
		}
	
	
	
	
	
	
	function validationAccountNumberType(event){
		
		var minimumBalance1_ = document.getElementById(event.target.id);
		
		var accountType0Value = $("#accountType0").val();
		//alert(event);
		
		if(accountType0Value == 'Deposit'){
			var keycode = event.which;
			//alert(keycode)
		    if (!(event.shiftKey == false && (keycode == 8 || keycode == 37 || (keycode >= 65 && keycode <= 90) ||(keycode >= 48 && keycode <= 57) || (keycode > 96 && keycode < 123) || (keycode > 64 && keycode < 91) || !keycode ==  32 ))) {
		        event.preventDefault();
		    }
			
		}else{
			 
			var keycode = event.which;
		    if (!(event.shiftKey == false && (keycode == 8 || keycode == 37 || (keycode >= 48 && keycode <= 57)))) {
		        event.preventDefault();
		    }
			
		}
		
		 
		}
	
	
function validationAccountNumberType1(event){
		
		var minimumBalance1_ = document.getElementById(event.target.id);
		
		var accountType0Value = $("#accountType1").val();
		//alert(event);
		
		if(accountType0Value == 'Deposit'){
			var keycode = event.which;
			//alert(keycode)
		    if (!(event.shiftKey == false && (keycode == 8 || keycode == 37 || (keycode >= 65 && keycode <= 90) ||(keycode >= 48 && keycode <= 57) || (keycode > 96 && keycode < 123) || (keycode > 64 && keycode < 91) || !keycode ==  32 ))) {
		        event.preventDefault();
		    }
			
		}else{
			 
			var keycode = event.which;
		    if (!(event.shiftKey == false && (keycode == 8 || keycode == 37 || (keycode >= 48 && keycode <= 57)))) {
		        event.preventDefault();
		    }
			
		}
		
		 
		}
		
		
		
function validationAccountNumberType2(event){
	
	var minimumBalance1_ = document.getElementById(event.target.id);
	
	var accountType0Value = $("#accountType2").val();
	//alert(event);
	
	if(accountType0Value == 'Deposit'){
		var keycode = event.which;
		//alert(keycode)
	    if (!(event.shiftKey == false && (keycode == 8 || keycode == 37 || (keycode >= 65 && keycode <= 90) ||(keycode >= 48 && keycode <= 57) || (keycode > 96 && keycode < 123) || (keycode > 64 && keycode < 91) || !keycode ==  32 ))) {
	        event.preventDefault();
	    }
		
	}else{
		 
		var keycode = event.which;
	    if (!(event.shiftKey == false && (keycode == 8 || keycode == 37 || (keycode >= 48 && keycode <= 57)))) {
	        event.preventDefault();
	    }
		
	}
	
	 
	}
	

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
	
	
	
 function ValidateEmail(email) {
	 debugger;
    var expr = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
    return expr.test(email);
}


$("#email").focusout(function () {
	debugger;
    if (!ValidateEmail($("#email").val())) {
        alert("Invalid email address.");
    }
     
});
	
$("#altEmail").focusout(function () {
	debugger;
    if (!ValidateEmail($("#altEmail").val())) {
        alert("Invalid email address.");
    }
    
});
	
	
	
	
	
	
</script>