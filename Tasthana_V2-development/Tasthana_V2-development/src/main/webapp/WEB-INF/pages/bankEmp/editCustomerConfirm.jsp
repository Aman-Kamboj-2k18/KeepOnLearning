<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>




<div class="add-customer">
<form:form action="editcustomerPost" name="fixedDeposit" method="post" commandName="customerForm" onsubmit="return val();">
	 <div  class="successMsg"><b><font color="green">${success}</font></b></div>
				<div class="header_customer">
					<h3 align="center"><spring:message code="label.editCustomer"/></h3>
				</div>
		<div class="newcustomer_border">
			<div class="add-personal-details">
				<h4><spring:message code="label.addPersonalDetails"/></h4>
			</div>
			<div class="add-customer-table">
			    <form:hidden path="customerID" />
			      <form:hidden path="id" />
			    <form:hidden path="accountNo" />
								<table class="theader" width="400">
								    <tr>
										<td><b><spring:message code="label.category"/></b><span style="color:red">*</span></td>
										<td><form:input readonly="true" path="category" class="myform-control"/>
											
											
									</td>												
									</tr>
									<tr>
									<td></td>
									<td>
									<span id="categoryError" class="error" style="display:none;"><font color="red"><spring:message code="label.selectValue"/></font></span>
										</td>
									</tr>
									<tr>
										<td><b><spring:message code="label.customerName"/></b><span style="color:red">*</span></td>
										<td><form:input readonly="true" path="customerName" class="myform-control"></form:input>
										
									</tr>
									<tr>
										<td></td>
										<td>
											<span id="nameError" class="error" style="display:none;"><font color="red"><spring:message code="label.validation"/>*</font></span>
									   		<span id="nameError" class="error" style="display:none"><spring:message code="label.validation"/></span>
									   	</td>
									
									</tr>
									
									<tr>
											<td><b><spring:message code="label.gender"/></b></td>
											<td><form:input  readonly="true" path="gender" class="myform-control"/>
											   
										    </td>															
									</tr>
									<tr>
										<td><b><spring:message code="label.age"/></b><span style="color:red">*</span></td>
										<td><form:input readonly="true" path="age"  id="age"  class="myform-control"></form:input>
											</td>
									
									</tr>
									
									<tr>
										<td><b><spring:message code="label.contactNumber"/></b><span style="color:red">*</span></td>
										<td><form:input readonly="true" path="contactNum"  id="contactNum"  class="myform-control"></form:input>
											</td>
									
									</tr>
									<tr>
										<td></td>
										<td>
											 <span id="contactNum1Error" class="error" style="display:none;"><font color="red"><spring:message code="label.incorrectFormat"/></font></span>
										 	 
									  	</td>
									
									</tr>
									<tr>
										<td><b><spring:message code="label.altContactNo"/></b></td>
										<td><form:input readonly="true" path="altContactNum"  id="altContactNum"  class="myform-control"></form:input>
									<span id="contactNum2Error" class="error" style="display:none;"><font color="red"><spring:message code="label.incorrectFormat"/></font></span>
										</td>
									</tr>
									<tr>
										<td><b><spring:message code="label.email"/></b><span style="color:red">*</span></td>
										<td><form:input readonly="true" path="email" id="email"  class="myform-control"></form:input>
										</td>
																									
									</tr>
									<tr>
										<td></td>
										<td>
												<span id="emailError" class="error" style="display:none;"><font color="red"><spring:message code="label.incorrectFormat"/></font></span>
										   
									  	</td>
									
									</tr>
						</table>
				</div>
				<div class="add-customer-table">
						<table width="400" class="theader">
									
									<tr>
										<td><b><spring:message code="label.altEmail"/></b></td>
										<td><form:input readonly="true" path="altEmail"  id="altEmail"  class="myform-control"></form:input>
									<span id="altemailError" class="error" style="display:none;"><font color="red"><spring:message code="label.incorrectFormat"/></font></span>
										
										</td>
									</tr>
									
							 
									<tr>	
											<td><b><spring:message code="label.address"/></b><span style="color:red">*</span></td>
											<td><form:input readonly="true" path="address"  id="address"  class="myform-control"></form:input>
											</td> 
																									
									</tr>
									<tr>
										<td></td>
										<td>
												<span id="addressError" class="error" style="display:none;"><font color="red"><spring:message code="label.validation"/></font></span>
											
									  	</td>
									
									</tr>	
									<tr>
											<td><b><spring:message code="label.pincode"/></b><span style="color:red">*</span></td>
											<td><form:input readonly="true" path="pincode"  id="pincode"  class="myform-control"></form:input>
											</td>
									</tr>
									<tr>
										<td></td>
										<td>
												<span id="pincodeError" class="error" style="display:none;"><font color="red"><spring:message code="label.incorrectFormat"/></font></span>
											
									  	</td>
									
									</tr>																			
	<tr>
	
		 <td><b><spring:message
					code="label.country" /></b><span style="color: red">*</span></td>
		
		
				
		<td><form:input readonly="true" path="country"  class="form-control" />
		    
		  
		</td>
		 
																			
	
								
	</tr> 


	<tr>
		<td><b><spring:message
					code="label.state" /></b><span style="color: red">*</span></td>
		<td><form:input readonly="true" path="state" id="state" class="form-control" autocomplete="off"/>
		
	
			
	</tr>

								
									<tr>
										<td><b><spring:message code="label.city"/></b><span style="color:red">*</span></td>
										<td><form:input readonly="true" path="city"  id="city"  class="myform-control"></form:input></td>
										
									</tr>
									<tr>
										<td></td>
										<td>
											<span id="cityError" class="error" style="display:none;"><font color="red"><spring:message code="label.validation"/>*</font></span>
										<span id="cityError" class="error" style="display:none"><spring:message code="label.validation"/></span>
									  	</td>
									
									</tr>
									
									<tr>
										<td><b><spring:message code="label.nomineeName"/></b><span style="color:red">*</span></td>
										<td><form:input readonly="true" path="nomineeName"  id="nomineeName"  class="myform-control"></form:input></td>
										
									</tr>
									<tr>
										<td><b><spring:message code="label.nomineeAge"/></b><span style="color:red">*</span></td>
										<td><form:input readonly="true" path="nomineeAge"  id="nomineeAge"  class="myform-control" onchange="Validation();"></form:input></td>
										
									</tr>
									<tr>
										<td><b><spring:message code="label.nomineeAddress"/></b><span style="color:red">*</span></td>
										<td><form:input path="nomineeAddress"  id="nomineeAddress"  class="myform-control"></form:input></td>
										
									</tr>
									<tr>
										<td><b><spring:message code="label.nomineeRelationShip"/></b><span style="color:red">*</span></td>
										<td><form:input readonly="true"  path="nomineeRelationShip" id="nomineeRelationShip"  class="myform-control"></form:input></td>
										
									</tr>
									<tr id="age1" style="display: none">
										<td><b><spring:message code="label.guardianName"/></b><span style="color:red">*</span></td>
										<td><form:input readonly="true" path="guardianName"  id="guardianName"  class="myform-control"></form:input></td>
										
									</tr>
									<tr id="age2" style="display: none">
										<td><b><spring:message code="label.guardianAge"/></b><span style="color:red">*</span></td>
										<td><form:input readonly="true" path="guardianAge" id="guardianAge"  class="myform-control"></form:input></td>
										
									</tr>
									<tr id="age3" style="display: none">
										<td><b><spring:message code="label.guardianAddress"/></b><span style="color:red">*</span></td>
										<td><form:input readonly="true" path="guardianAddress"  id="guardianAddress"  class="myform-control"></form:input></td>
										
									</tr>
									<tr id="age4" style="display: none">
										<td><b><spring:message code="label.guardianRelationShip"/></b><span style="color:red">*</span></td>
										<td><form:input readonly="true" path="guardianRelationShip"  id="guardianRelationShip"  class="myform-control"></form:input></td>
										
									</tr>								
									
									
									
								</table>
								
				</div>
		</div>
				<div class="add-customer-table2">
				<div class="add-personal-details">
					<h4><spring:message code="label.accountDeatils"/></h4>
				</div>
								<table class="theader" width="400" align="center">
									<%-- <tr>	
											<td><b><spring:message code="label.accountNo"/></b>&nbsp;&nbsp;<span style="color:red;">*</span></td>
											<td><form:input path="accountNo" placeholder="Enter account no" id="accountNo"  class="myform-control"></form:input>
											</td> 
																									
									</tr> 
									<tr>
										<td></td>
										<td>
											<span id="accountNoError" class="error" style="display:none;"><font color="red"><spring:message code="label.validation"/>*</font></span>
											<span id="accountNoError" class="error" style="display:none"><spring:message code="label.validation"/></span>
									  	</td>
									
									</tr>--%>
									<tr>
											<td><b><spring:message code="label.accountType"/></b></td>
											<td><form:input path="accountType"  class="myform-control" readonly="true"/>
											   
										    </td>															
									</tr>
									<tr>	
											<td><b><spring:message code="label.accountBalance"/></b><span style="color:red">*</span></td>
											<td><form:input readonly="true" path="accountBalance"  id="accountBalance"  class="myform-control"></form:input>
											</td> 
																									
									</tr>
									<tr>
										<td></td>
										<td>
											<span id="accountBalanceError" class="error" style="display:none;"><font color="red"><spring:message code="label.validation"/>*</font></span>
											<span id="accountBalanceError" class="error" style="display:none"><spring:message code="label.validation"/></span>
									  	</td>
									
									</tr>
								</table>
				</div>
				
			<div class="save-back">
								<p align="center" style="padding-top: 10px;">
								<input type="submit"  class="btn btn-primary" value="<spring:message code="label.confirm"/>">
					
								<a href="bankEmp" class="btn btn-success"><spring:message code="label.back"/></a>
								</p>
			</div>
				
		
		</form:form>
		</div>
	
	
		<script>
		
		/* function Validation()
		{
			var age = document.getElementById('nomineeAge').value;
			if(age<18)
				{
				$("#age1").show();
				$("#age2").show();
				$("#age3").show();
				$("#age4").show();
				}
			else
				{
				$("#age1").hide();
				$("#age2").hide();
				$("#age3").hide();
				$("#age4").hide();
				}
		}
		
		
		
			$(function() {
				$("#datepicker").datepicker({
					format : 'dd/mm/yyyy'
				});
			});
			$(document).ready(
					function() {
						$(':input', '#customerHeadForm').not(
								':button, :submit, :reset, :hidden').val('')
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
			
 */			
			
			

	/* 		function val() {
				
				
				var d = getTodaysDate().getTime();
				var uuid = 'xxxxxxxx'.replace(/[xy]/g, function(c) {
					var r = (d + Math.random() * 16) % 16 | 0;
					d = Math.floor(d / 16);
					return (c == 'x' ? r : (r & 0x3 | 0x8)).toString(16);
				});
				document.fixedDeposit.customerID.value = uuid;
				
			       var d1 = getTodaysDate().getTime();
			       var uuid1 ='AN'+'xxxxxxxxxxxxxx'.replace(/[xy]/g, function(c1) {
			              var r1 = (d1 + Math.random() * 16) % 16 | 0;
			              d1 = Math.floor(d / 16);
			              return (c1 == 'x' ? r1 : (r1 & 0x3 | 0x8)).toString(16);
			       });
			       document.fixedDeposit.accountNo.value = uuid1;

				
				
				var phoneNum = /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;
				var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
				var canSubmit = true;
			
			    if (document.getElementById('category').value == 'select') {

					document.getElementById('category').style.borderColor = "red";
					return false;
				} else {
					document.getElementById('category').style.borderColor = "green";

				}

				if (document.getElementById('customerName').value == '') {

					document.getElementById('customerName').style.borderColor = "red";
					return false;
				} else {
					document.getElementById('customerName').style.borderColor = "green";
				}
				if (document.getElementById('age').value == '') {

					document.getElementById('age').style.borderColor = "red";
					return false;
				} else {
					document.getElementById('age').style.borderColor = "green";

				}
				if (document.getElementById('contactNum').value == '') {

					document.getElementById('contactNum').style.borderColor = "red";
					return false;
				} else {
					document.getElementById('contactNum').style.borderColor = "green";
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
					document.getElementById('email').style.borderColor = "green";
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

				if (document.getElementById('pincode').value == '') {

					document.getElementById('pincode').style.borderColor = "red";
					return false;
				} else {
					document.getElementById('pincode').style.borderColor = "green";
				}
				if (isNaN(document.getElementById('pincode').value)) {
					document.getElementById('pincodeError').style.display = 'block';
					canSubmit = false;
				} else {
					document.getElementById('pincodeError').style.display = 'none';
				}

				if (document.getElementById('address').value == '') {

					document.getElementById('address').style.borderColor = "red";
					return false;
				} else {
					document.getElementById('address').style.borderColor = "green";
				}

				if (document.getElementById('city').value == '') {

					document.getElementById('city').style.borderColor = "red";
					return false;

				} else {
					document.getElementById('city').style.borderColor = "green";
				}

				if (document.getElementById('country').value == '-1') {

					document.getElementById('countryError').style.borderColor = "red";
					return false;
				} else {
					document.getElementById('countryError').style.borderColor = "green";
				}
				if (document.getElementById('state').value == '-1') {

					document.getElementById('stateError').style.borderColor = "red";
					return false;
				} else {
					document.getElementById('stateError').style.borderColor = "green";
				}
		
				
			 
			  
				if (document.getElementById('nomineeName').value == '') {

					document.getElementById('nomineeName').style.borderColor = "red";
					return false;
				} else {
					document.getElementById('nomineeName').style.borderColor = "green";

				}
				if (document.getElementById('nomineeAge').value == '') {

					document.getElementById('nomineeAge').style.borderColor = "red";
					return false;
				} else {
					document.getElementById('nomineeAge').style.borderColor = "green";

				}
				if (document.getElementById('nomineeAddress').value == '') {

					document.getElementById('nomineeAddress').style.borderColor = "red";
					return false;
				} else {
					document.getElementById('nomineeAddress').style.borderColor = "green";

				}
				if (document.getElementById('nomineeRelationship').value == '') {

					document.getElementById('nomineeRelationship').style.borderColor = "red";
					return false;
				} else {
					document.getElementById('nomineeRelationship').style.borderColor = "green";

				}
				
				 if (parseInt(document.getElementById('nomineeAge').value) < 18) {
						if (document.getElementById('guardianName').value == '') {

							document.getElementById('guardianName').style.borderColor = "red";
							return false;
						} else {
							document.getElementById('guardianName').style.borderColor = "green";

						}
						if (document.getElementById('guardianAge').value == '') {

							document.getElementById('guardianAge').style.borderColor = "red";
							return false;
						} else {
							document.getElementById('guardianAge').style.borderColor = "green";

						}
						if (document.getElementById('guardianAddress').value == '') {

							document.getElementById('guardianAddress').style.borderColor = "red";
							return false;
						} else {
							document.getElementById('guardianAddress').style.borderColor = "green";

						}
						if (document.getElementById('guardianRelatioship').value == '') {

							document.getElementById('guardianRelatioship').style.borderColor = "red";
							return false;
						} else {
							document.getElementById('guardianRelatioship').style.borderColor = "green";

						}
					}
				  

				if (document.getElementById('accountBalance').value == 0) {

					document.getElementById('accountBalance').style.borderColor = "red";
					return false;
				} else {
					document.getElementById('accountBalance').style.borderColor = "green";
				}
				

				if (canSubmit == false) {
					return false;
				}
			}
 */
			$(document)
					.ready(
							function() {
								$(".search")
										.keyup(
												function() {
													var searchTerm = $(
															".search").val();
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

													$(".results tbody tr")
															.not(
																	":containsi('"
																			+ searchSplit
																			+ "')")
															.each(
																	function(e) {
																		$(this)
																				.attr(
																						'visible',
																						'false');
																	});

													$(
															".results tbody tr:containsi('"
																	+ searchSplit
																	+ "')")
															.each(
																	function(e) {
																		$(this)
																				.attr(
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
