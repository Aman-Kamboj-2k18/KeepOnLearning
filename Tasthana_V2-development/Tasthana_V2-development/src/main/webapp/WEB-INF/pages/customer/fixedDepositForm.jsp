<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- To fetch the request url -->
<c:set var="req"
	value="${requestScope['javax.servlet.forward.request_uri']}" />
<c:set var="baseURL" value="${fn:split(req,'/')}" />

<link href="<%=request.getContextPath()%>/resources/css/Validation.css"
	rel="stylesheet">
<script
	src="<%=request.getContextPath()%>/resources/js/ccvalidate-tapan.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/loginDate.js"></script>
<script>
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
			$(document).ready(function() {
				
				if('${productConfiguration.productType}'=='Regular Deposit' ||'${productConfiguration.productType}'== 'Tax Saving Deposit'){
				$('#paymentType').val('One-Time').change();
				document.getElementById("paymentType").disabled = true;
				}
				//console.log(${baseURL[1]});
				if('${baseURL[1]}' == 'common'|| '${baseURL[0]}' == 'common')
					$('#branch').show();
				else
					$('#branch').remove();
					
								var paymentType2 = document.getElementById("paymentType").value;
								
								var payoffTypee='${productConfiguration.interestCalculationBasis}';
								if(payoffTypee=='Quarterly'){
									$('#fd1').hide();
								}
								if(payoffTypee=='HALF YEARLY'){
									$('#fd1').hide();
									$('#fd3').hide();
								}
								if(payoffTypee=='Annually'){
									$('#fd1').hide();
									$('#fd3').hide();
									$('#fd4').hide();
								}
								
								/* if ($('#citizen').val() == "NRI") {
									$("#taxSavingDepositId").hide();
								} */

								accountTypeVal("${nriAccountType}");
								$('#nomineePan').keyup(function(e) {

													var panVal = $(this).val();
													var alphanumeric = "^[a-zA-Z0-9]+$";
													var regpan = /^([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}?$/;
													if (panVal.length > 9) {
														
														if (regpan.test(panVal)) {
															if (e.keyCode != 144) {

															}
														} else {
															if (e.keyCode != 144) {
																alert("invalid pan number");
															}
														}
													}

												});
								$('#nomineePan')
										.bind(
												'keypress',
												function(event) {
													var regex = new RegExp(
															"^[a-zA-Z0-9]+$");
													var key = String
															.fromCharCode(!event.charCode ? event.which
																	: event.charCode);
													if (!regex.test(key)) {
														//alert("false")
														event.preventDefault();
														return false;
													}
												});

								$('#gaurdianPan')
										.keyup(
												function(e) {

													var panVal = $(this).val();
													var alphanumeric = "^[a-zA-Z0-9]+$";
													var regpan = /^([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}?$/;
													if (panVal.length > 9) {
													
														if (regpan.test(panVal)) {
															if (e.keyCode != 144) {

															}
														} else {
															if (e.keyCode != 144) {
																alert("invalid pan number");
															}
														}
													}

												});
								$('#gaurdianPan')
										.bind(
												'keypress',
												function(event) {
													var regex = new RegExp(
															"^[a-zA-Z0-9]+$");
													var key = String
															.fromCharCode(!event.charCode ? event.which
																	: event.charCode);
													if (!regex.test(key)) {
														//alert("false")
														event.preventDefault();
														return false;
													}
												});

								//document.getElementById('dayId').style.display = 'block';
								//document.getElementById('dayValue').style.display = 'none';
								document.getElementById('nomineePanCard').style.display = 'none';
								//document.getElementById('deductionDay').value = '${fixedDepositForm.deductionDay}';
								//document.getElementById('daysValue').value = '${fixedDepositForm.daysValue}';

								var fdAmount = document
										.getElementById('fdAmount');
								var nomineeAadhar = document
										.getElementById('nomineeAadhar');
								//var daysValue = document
										//.getElementById('daysValue');
								var fdTenure_ = document
										.getElementById('fdTenure');

								var interestPayAmount = document
										.getElementById('interestPayAmount');
								var interestPercent = document
										.getElementById('interestPercent');

								var gaurdianAadhar = document
										.getElementById('gaurdianAadhar');

								$('#nomineeName')
										.bind(
												'keypress',
												function(event) {
													var regex = new RegExp(
															"^[a-zA-Z0-9 ]+$");
													var key = String
															.fromCharCode(!event.charCode ? event.which
																	: event.charCode);
													if (this.value.length == 0
															&& event.keyCode == 32) {
														event.preventDefault();
														return false;
													}
													if (!regex.test(key)) {
														//alert("false")
														event.preventDefault();
														return false;
													}
												});

								$('#guardianRelationShip')
										.bind(
												'keypress',
												function(event) {
													var regex = new RegExp(
															"^[a-zA-Z ]+$");
													var key = String
															.fromCharCode(!event.charCode ? event.which
																	: event.charCode);
													if (this.value.length == 0
															&& event.keyCode == 32) {
														event.preventDefault();
														return false;
													}
													if (!regex.test(key)) {
														//alert("false")
														event.preventDefault();
														return false;
													}
												});

								$('#guardianName')
										.bind(
												'keypress',
												function(event) {
													var regex = new RegExp(
															"^[a-zA-Z0-9 ]+$");
													var key = String
															.fromCharCode(!event.charCode ? event.which
																	: event.charCode);
													if (this.value.length == 0
															&& event.keyCode == 32) {
														event.preventDefault();
														return false;
													}
													if (!regex.test(key)) {
														//alert("false")
														event.preventDefault();
														return false;
													}
												});

								$('#nomineeRelationShip')
										.bind(
												'keypress',
												function(event) {
													var regex = new RegExp(
															"^[a-zA-Z ]+$");
													var key = String
															.fromCharCode(!event.charCode ? event.which
																	: event.charCode);
													if (this.value.length == 0
															&& event.keyCode == 32) {
														event.preventDefault();
														return false;
													}
													if (!regex.test(key)) {
														event.preventDefault();
														return false;
													}
												});

								$('#paymentBeneficiaryName').bind('keypress',function(event) {
													var regex = new RegExp(
															"^[a-zA-Z ]+$");
													var key = String
															.fromCharCode(!event.charCode ? event.which
																	: event.charCode);
													if (this.value.length == 0
															&& event.keyCode == 32) {
														event.preventDefault();
														return false;
													}
													if (!regex.test(key)) {
														//alert("false")
														event.preventDefault();
														return false;
													}
												});

								$('#chequeBranch').bind('keypress',function(event) {
													var regex = new RegExp(
															"^[a-zA-Z]+$");
													var key = String
															.fromCharCode(!event.charCode ? event.which
																	: event.charCode);
													if (!regex.test(key)) {
														//alert("false")
														event.preventDefault();
														return false;
													}
												});

								$('#chequeBank')
										.bind(
												'keypress',
												function(event) {
													var regex = new RegExp(
															"^[a-zA-Z]+$");
													var key = String
															.fromCharCode(!event.charCode ? event.which
																	: event.charCode);
													if (!regex.test(key)) {
														//alert("false")
														event.preventDefault();
														return false;
													}
												});

								$('#chequeNo')
										.bind(
												'keypress',
												function(event) {
													var regex = new RegExp(
															"^[0-9]+$");
													var key = String
															.fromCharCode(!event.charCode ? event.which
																	: event.charCode);
													if (!regex.test(key)) {
														//alert("false")
														event.preventDefault();
														return false;
													}
												});

								$('#cardNo')
										.bind(
												'keypress',
												function(event) {
													var regex = new RegExp(
															"^[0-9]$");
													var key = String
															.fromCharCode(!event.charCode ? event.which
																	: event.charCode);
													var value = this.value;
													if (!regex.test(key)) {
														event.preventDefault();
														return false;
													}
												});

								$('#beneficiaryIfsc')
										.bind(
												'keypress',
												function(event) {
													var regex = new RegExp(
															"^[A-Za-z]{4}[a-zA-Z0-9]{7}$");
													var key = String
															.fromCharCode(!event.charCode ? event.which
																	: event.charCode);
													var value = this.value;
													if (!regex.test(key)
															&& value.length > 10) {
														alert("invalid ifsc code")
														event.preventDefault();
														return false;
													}
												});

								$('#beneficiaryIfsc')
										.bind(
												'keypress',
												function(event) {
													var regex = new RegExp(
															"^[a-zA-Z0-9]+$");
													var key = String
															.fromCharCode(!event.charCode ? event.which
																	: event.charCode);
													if (!regex.test(key)) {
														//alert("false")
														event.preventDefault();
														return false;
													}
												});
								$('#beneficiaryIfscMr')
								.bind(
										'keypress',
										function(event) {
											var regex = new RegExp(
													"^[a-zA-Z0-9]+$");
											var key = String
													.fromCharCode(!event.charCode ? event.which
															: event.charCode);
											if (!regex.test(key)) {
												//alert("false")
												event.preventDefault();
												return false;
											}
										});

								$('#beneficiaryName')
										.bind(
												'keypress',
												function(event) {
													var regex = new RegExp(
															"^[a-zA-Z ]+$");
													var key = String
															.fromCharCode(!event.charCode ? event.which
																	: event.charCode);

													if (this.value.length == 0
															&& event.keyCode == 32) {
														event.preventDefault();
														return false;
													}
													if (!regex.test(key)) {
														//alert("false")
														event.preventDefault();
														return false;
													}
												});
								$('#beneficiaryNameMr')
								.bind(
										'keypress',
										function(event) {
											var regex = new RegExp(
													"^[a-zA-Z ]+$");
											var key = String
													.fromCharCode(!event.charCode ? event.which
															: event.charCode);

											if (this.value.length == 0
													&& event.keyCode == 32) {
												event.preventDefault();
												return false;
											}
											if (!regex.test(key)) {
												//alert("false")
												event.preventDefault();
												return false;
											}
										});

								$('#beneficiaryBank')
										.bind(
												'keypress',
												function(event) {
													var regex = new RegExp(
															"^[a-zA-Z ]+$");
													var key = String
															.fromCharCode(!event.charCode ? event.which
																	: event.charCode);
													if (this.value.length == 0
															&& event.keyCode == 32) {
														event.preventDefault();
														return false;
													}
													if (!regex.test(key)) {
														//alert("false")
														event.preventDefault();
														return false;
													}
												});

								$('#beneficiaryBankMr')
								.bind(
										'keypress',
										function(event) {
											var regex = new RegExp(
													"^[a-zA-Z ]+$");
											var key = String
													.fromCharCode(!event.charCode ? event.which
															: event.charCode);
											if (this.value.length == 0
													&& event.keyCode == 32) {
												event.preventDefault();
												return false;
											}
											if (!regex.test(key)) {
												//alert("false")
												event.preventDefault();
												return false;
											}
										});

								var arrayLength = "${fixedDepositForm.accountList.size()}";

								if (arrayLength == 0) {
									document.getElementById('linkedAccountDiv').style.display = 'none';
								}

								var account = document.getElementById('accountNo').value;

								if (account != "") {
									var account = account.split(",");
                                     document.getElementById('accountType').value = account[1];

									document.getElementById('accountBalance').value = parseFloat(
											account[2]).toLocaleString("en-US");
									document.getElementById('accountBalance1').value = account[2];

									document.getElementById('accountTypeDiv').style.display = 'block';
									document.getElementById('accountBalanceDiv').style.display = 'block';
									//document.getElementById('fundTransferLinkedAccount').style.display = 'block';

								} else {
									document.getElementById('accountTypeDiv').style.display = 'none';
									document.getElementById('accountBalanceDiv').style.display = 'none';
									//document.getElementById('fundTransferLinkedAccount').style.display = 'none';

								}

								/*nominee screen....*/

								$("#interestPercentTr").hide();
								$("#interestAmtTr").hide();
								$("#gaurdianDiv").hide();

								
								var fdTenure = "${fixedDepositForm.fdTenure}";
								

								var fdPayOffType1 = document
										.getElementById("fdPayOffType1").checked;
								var fdPayOffType3 = document
										.getElementById("fdPayOffType3").checked;
								var fdPayOffType4 = document
										.getElementById("fdPayOffType4").checked;
								var fdPayOffType5 = document
										.getElementById("fdPayOffType5").checked;

								if (fdPayOffType1 == true || fdPayOffType3 == true || fdPayOffType4 == true || fdPayOffType5 == true) {

									$("#partPercentDiv").show();

									if (document.getElementById("percentInterest").checked) {

										$("#interestPercentTr").show();
										$("#interestAmtTr").hide();
										document
												.getElementById("interestPayAmount").value = "";

									}

									if (document.getElementById("partInterest").checked) {

										$("#interestPercentTr").hide();
										document
												.getElementById("interestPercent").value = "";
										$("#interestAmtTr").show();
									}

								} else {
									$("#partPercentDiv").hide();
								}

								if (document.getElementById("savingBankRadio").checked) {
									showDetailsSameBank();

								}
								if (document
										.getElementById("differentBankRadio").checked) {
									showDetailsDiffBank();
								}

								if (parseFloat(document
										.getElementById("nomineeAge").value) < 18) {
									$("#gaurdianDiv").show();

								}

								if (parseFloat(document
										.getElementById("nomineeAge").value) > 18) {
									$("#nomineePanCard").show();

								}

								/************payment.ready function  **********.......*/

								// Initiate CC Validation
								InitiateValidation();
								var paymentMode = document
										.getElementById('paymentMode').value;

								showDetails(document.getElementById('paymentMode'));
								if (paymentMode == 'Debit Card' || paymentMode == 'Credit Card') {
									var expiryDate = document
											.getElementById('expiryDate').value;
									var arrayStr = expiryDate.split("-");
									document.getElementById('expiryMonth').value = arrayStr[0];
									document.getElementById('expiryYear').value = arrayStr[1];

								}

								var linkedAccountNo = document
										.getElementById('linkedAccount').value;
								if (linkedAccountNo == "") {
									document
											.getElementById('linkedAccountBalanceTr').style.display = 'none';
									document.getElementById('linkedAccountTr').style.display = 'none';

								} else {
									document
											.getElementById('linkedAccountBalanceTr').style.display = 'block';
									document.getElementById('linkedAccountTr').style.display = 'block';

								}
							
								
								gaurdianAadhar.onkeydown = function(e) {
									if (!((e.keyCode > 95 && e.keyCode < 106)
											|| (e.keyCode > 47 && e.keyCode < 58) || e.keyCode == 8)) {
										return false;
									}
									if (gaurdianAadhar.value.length > 11
											&& e.keyCode != 8) {
										return false;
									}
								}

								var beneficiaryAccount = document
										.getElementById('beneficiaryAccount');

								beneficiaryAccount.onkeydown = function(e) {
									if (!((e.keyCode > 95 && e.keyCode < 106)
											|| (e.keyCode > 47 && e.keyCode < 58) || e.keyCode == 8)) {
										return false;
									}
									if (beneficiaryAccount.value.length > 15
											&& e.keyCode != 8) {
										return false;
									}
								}
								var beneficiaryAccountMr = document
								.getElementById('beneficiaryAccountMr');

						beneficiaryAccountMr.onkeydown = function(e) {
							if (!((e.keyCode > 95 && e.keyCode < 106)
									|| (e.keyCode > 47 && e.keyCode < 58) || e.keyCode == 8)) {
								return false;
							}
							if (beneficiaryAccountMr.value.length > 15
									&& e.keyCode != 8) {
								return false;
							}
						}
								interestPayAmount.onkeyup = function(e) {

									var depositeAmount = $("#fdAmount").val();
									if (!((e.keyCode > 95 && e.keyCode < 106)
											|| (e.keyCode > 47 && e.keyCode < 58) || e.keyCode == 8)) {
										return false;
									}
									/*  if (interestPayAmount.value.length>10 && e.keyCode != 8){
									 	return false;
									 } */
									if (parseInt(interestPayAmount.value) > parseInt(depositeAmount)
											&& e.keyCode != 8) {
										document
												.getElementById("interestPayAmount").style.borderColor = "red";
										alert("Payoff amount should not more than Deposit amount.");
										// event.preventDefault();
										//return false;
									}

								}

								interestPercent.onkeyup = function(e) {

									if (!((e.keyCode > 95 && e.keyCode < 106)
											|| (e.keyCode > 47 && e.keyCode < 58) || e.keyCode == 8)) {
										return false;
									}
									if (parseInt(interestPercent.value) > 100
											&& e.keyCode != 8) {
										document
												.getElementById("interestPercent").style.borderColor = "red";
										alert("Payoff should not more than 100%");
										}

								}

								
								
								$('#nomineeAge').keyup(function(){
									  if ($(this).val() > 100){
									   
									    $(this).val('100');
									  }
									});

								

							
								
								$('#guardianAge').keyup(function(){
									  if ($(this).val() > 100){
									   
									    $(this).val('100');
									  }
									});


								
								var nomineeAadhar = document
										.getElementById('nomineeAadhar');

								nomineeAadhar.onkeydown = function(e) {
									if (!((e.keyCode > 95 && e.keyCode < 106)
											|| (e.keyCode > 47 && e.keyCode < 58) || e.keyCode == 8)) {
										return false;
									}
									if (nomineeAadhar.value.length > 11
											&& e.keyCode != 8) {
										return false;
									}
								}

								fdTenure_.onkeydown = function(e) {
									if (!((e.keyCode > 95 && e.keyCode < 106)
											|| (e.keyCode > 47 && e.keyCode < 58) || e.keyCode == 8)) {
										return false;
									}
									if (fdTenure_.value.length > 5
											&& e.keyCode != 8) {
										return false;
									}
									
									
								}

								/* daysValue.onkeydown = function(e) {
									if (!((e.keyCode > 95 && e.keyCode < 106)
											|| (e.keyCode > 47 && e.keyCode < 58) || e.keyCode == 8)) {
										return false;
									}
									if (daysValue.value.length > 5
											&& e.keyCode != 8) {
										return false;
									}
								} */

								nomineeAadhar.onkeydown = function(e) {
									if (!((e.keyCode > 95 && e.keyCode < 106)
											|| (e.keyCode > 47 && e.keyCode < 58) || e.keyCode == 8)) {
										return false;
									}
									if (nomineeAadhar.value.length > 11
											&& e.keyCode != 8) {
										return false;
									}
								}

								// Listen for input event on numInput.
								fdAmount.onkeydown = function(e) {
									if (!((e.keyCode > 95 && e.keyCode < 106)
											|| (e.keyCode > 47 && e.keyCode < 58) || e.keyCode == 8)) {
										return false;
									}
									if (fdAmount.value.length > 10
											&& e.keyCode != 8) {
										return false;
									}
								}
								
					
							var account = "${nriAccountType}";
								$("#nriAccountType").val(account).change();
								
								accountTypeVal(account); 
								
 
								onchangeBankType();
								if('${productConfiguration.defaultCurrency}' != "")
								$('#currency').val('${productConfiguration.defaultCurrency}').change();
								else
									$('#currency').val('INR').change();
									
							});

			function onchangeDepositAmount(value) {

				document.getElementById('paymentAmt').value = value;
			}

			function usernameAndNameValidater(id) {
				var regex = /^[a-zA-Z ]{2,30}$/;
				var ctrl = document.getElemetnById(id);

				if (regex.test(ctrl.value)) {
					return true;
				} else {
					return false;
				}
			}

			function limit() {

				var r = document.getElementById('paymentType').value;
$('#paymentType1').val(r);
				if (r == 'One-Time') {
					$("#days").show();
					  //document.getElementById("deductionDay").value ="";
					    //document.getElementById("deductionDay").disabled=true;
				} else if (r == 'Monthly' || r == 'Quarterly'
						|| r == 'Half Yearly' || r == 'Annually') {
					  document.getElementById("deductionDay").value ="";
					document.getElementById("deductionDay").disabled = false;
				}

				else {
					$("#days").hide();
				}

			}
			
			
			function val() {
				debugger;
				var submit = true;
				var maxFdAmount = 100000000000;
				var fdAmount = document.getElementById('fdAmount').value;
				var nomineeDetails = document.getElementById('nomineeDetails').value;
				var nomineeReqForEachAcHolder = parseInt('${productConfiguration.nomineeRequiredForEachAccHolder}');
				var fdTenure=0;
				var fdTenureYears1 = document.getElementById('fdTenureYears').value;
				document.getElementById('tenureInYears').value = fdTenureYears1;
				
				var fdTenureMonths1 = document.getElementById('fdTenureMonths').value;
				document.getElementById('tenureInMonths').value = fdTenureMonths1;
				var fdTenureDays1 = document.getElementById('fdTenureDays').value;
				document.getElementById('tenureInDays').value=fdTenureDays1;
				today = getLoginDate();
				var year  = today.getFullYear();
				var month = today.getMonth();
				var day = today.getDate();
				if(fdTenureYears1!=""){
					var newDate = new Date(year+parseInt(fdTenureYears1),month,day);
					var _days = (newDate - today)/(1000*60*60*24);

					fdTenure+= parseInt(_days);
				}
				if(fdTenureMonths1!=""){
					var newMonthDate = new Date(year,month+parseInt(fdTenureMonths1),day);
					fdTenure+= parseInt((newMonthDate - today)/(1000*60*60*24));
				}
				if(fdTenureDays1!=""){
					fdTenure+= parseInt(fdTenureDays1);
				}
				
				
				
				document.getElementById('fdTenure').value=fdTenure;
				//var dayValue = parseInt(document.getElementById('daysValue').value);
				var paymentType = document.getElementById('paymentType').value;
				var deductionDay="";
				if('${productConfiguration.productType}'!='Regular Deposit' && '${productConfiguration.productType}'!= 'Tax Saving Deposit'){
				    deductionDay= document.getElementById('deductionDay').value;
					}
				var accountBalance_ = document.getElementById('accountBalance1').value;
				var maturityInstruction = document
						.getElementById('maturityInstructionId').value;
				var interestPayAmount_ = document
						.getElementById('interestPayAmount').value;

				var currency = document.getElementById('currency').value;
				document.getElementById("paymentOk").style.display = 'none';
				document.getElementById("paymentCross").style.display = 'none';
				document.getElementById("payoffOk").style.display = 'none';
				document.getElementById("payoffCross").style.display = 'none';
				document.getElementById("maturityOk").style.display = 'none';
				document.getElementById("maturityCross").style.display = 'none';
				document.getElementById("nomineeOk").style.display = 'none';
				document.getElementById("nomineeCross").style.display = 'none';
				document.getElementById("depositOk").style.display = 'none';
				document.getElementById("depositCross").style.display = 'none';

				var validationError = document.getElementById('validationError');
				validationError.innerHTML = "";
				var errorMsg = "";

				var inp = document.querySelectorAll("#depositDetails .input");
				for (i = 0; i < inp.length; i++) {
					inp[i].style.borderColor = "#cccccc";
				}
				if (paymentType == "") {
					document.getElementById('paymentType').style.borderColor = "red";
					errorMsg = "Please Enter payment type";
					validationError.innerHTML += errorMsg;
					submit = false;
				}

				if(fdTenureYears1=="" && fdTenureMonths1=="" && fdTenureDays1==""){
					errorMsg = "<br>Please Select atleast one tenure type";
					validationError.innerHTML += errorMsg;

					submit = false;
				}
				
				if (fdAmount == "") {
					document.getElementById('fdAmount').style.borderColor = "red";
					errorMsg = "<br>Please enter deposit amount";
					validationError.innerHTML += errorMsg;

					submit = false;

				} else {
					fdAmount = parseFloat(fdAmount);
					var minDepositAmount = parseInt('${productConfiguration.minimumDepositAmount}');
					var maxDepositAmount = parseInt('${productConfiguration.maximumDepositAmount}');
					if (minDepositAmount > fdAmount) {
						document.getElementById('fdAmount').style.borderColor = "red";
						errorMsg = "<br>Please enter deposit amount Minimum "
								+ minDepositAmount + ".";
						validationError.innerHTML += errorMsg;

						submit = false;
					}
                    
					if(submit==false){
						return false;	
						}
                    if( nomineeReqForEachAcHolder == 1 && nomineeDetails== "") {
						
						if(submit == false){
							document.getElementById("nomineeDetails").style.display='block';
							document.getElementById("nomineeCross").style.display='block';
							validationError.innerHTML = "<br>Please enter all the NOMINEE DETAILS";
							
							return false;}
					}
					
					if (maxDepositAmount < fdAmount) {
						document.getElementById('fdAmount').style.borderColor = "red";
						errorMsg = "<br>Maximum limit of deposit is less than"
								+ maxDepositAmount + ".";
						validationError.innerHTML += errorMsg;

						submit = false;
					}

					if('${baseURL[1]}' == 'common'|| '${baseURL[0]}' == 'common'){
						
						
						var branch = document
						.getElementById('branchCode').value;
						 if (branch == "") {
								document.getElementById('branchCode').style.borderColor = "red";
								errorMsg = "<br>Please Select Branch Code";
								validationError.innerHTML += errorMsg;
								document.getElementById('depositCross').style.display = 'block';
								submit = false;
							} else {
								document.getElementById('branchCode').style.borderColor = "black";

							} 
					}
					/* if (fdAmount > maxFdAmount) {
						document.getElementById('fdAmount').style.borderColor = "red";
						errorMsg = "<br>Maximum limit of deposit is less than "
								+ maxDepositAmount + ".";
						validationError.innerHTML += errorMsg;

						submit = false;
					} */

					if(submit==false){
						return false;	
						}
					var paymentMode_ = $("#paymentMode").val();
					
					if(paymentMode_ == "Net Banking" || paymentMode_ == "Debit Card" || paymentMode_ == "Debit Card" || paymentMode_ == "cash" || paymentMode_ ==  'Cheque' || paymentMode_ ==  'DD'){
					
				}else{
					if(paymentMode_ == 'Fund Transfer'){
						if (parseFloat(fdAmount) > parseFloat(accountBalance_)) {
							document.getElementById('fdAmount').style.borderColor = "red";
							errorMsg = "<br>Insufficent fund ";
							validationError.innerHTML += errorMsg;
							submit = false;
						}
					}
				}
					
					
					if (interestPayAmount_ != ""
							|| $("interestPercent").val() != "") {
						if ($("#partInterest").val() == 'PART') {
							if (interestPayAmount_ > parseInt(fdAmount)) {
								document.getElementById("interestPayAmount").style.borderColor = "red";
								errorMsg = "<br>Payoff amount should not more than Deposit amount";
								validationError.innerHTML += errorMsg;

								submit = false;

							}

						}
						if ($("#percentInterest").val() == 'PERCENT') {
							if ($("#interestPercent").val() > 100) {
								document.getElementById("interestPayAmount").style.borderColor = "red";
								errorMsg = "<br>Payoff percentage should not more than 100%";
								validationError.innerHTML += errorMsg;

								submit = false;

							}

						}
					} else {

						if ($("#partInterest").val() == 'PART') {
							document.getElementById("interestPayAmount").style.borderColor = "red";
							errorMsg = "<br>Payoff percentage should not empty";
							validationError.innerHTML += errorMsg;

							submit = false;
						} else if ($("#interestPercent").val() == 'PERCENT') {
							document.getElementById("interestPayAmount").style.borderColor = "red";
							errorMsg = "<br>Payoff percentage should not be empty";
							validationError.innerHTML += errorMsg;

							submit = false;
						} else {
							document.getElementById("interestPercentTr").style.borderColor = "red";
							errorMsg = "<br>Please select Payoff options  percentage or part ";
							validationError.innerHTML += errorMsg;

							submit = false;

						}

					}

				}

				if (maturityInstruction == "select") {
					document.getElementById('maturityInstructionId').style.borderColor = "red";
					errorMsg = "<br>Please Select Maturity Instruction";
					validationError.innerHTML += errorMsg;
					document.getElementById('depositCross').style.display = 'block';
					submit = false;
				} else {
					document.getElementById('maturityInstructionId').style.borderColor = "black";

				}

				 if (deductionDay == "" && paymentType != 'One-Time') {
					//document.getElementById("deductionDay").style.borderColor = "red";
					errorMsg = "<br>Please enter deduction day";
					validationError.innerHTML += errorMsg;
					document.getElementById('depositCross').style.display = 'block';
					submit = false;
				}
/* 
				if (fdTenure == 0 || isNaN(fdTenure)) {

					//document.getElementById('fdTenure').style.borderColor = "red";
					errorMsg = "<br>Please enter correct tenure";
					validationError.innerHTML += errorMsg;

					submit = false;
				} else {
					//document.getElementById('fdTenure').style.borderColor = "black";
				} */
				if (currency == 'Select') {
					document.getElementById('currency').style.borderColor = "red";
					errorMsg = "<br>Please enter currency";
					validationError.innerHTML += errorMsg;

					submit = false;
				} else {
					document.getElementById('currency').style.borderColor = "black";
				}
				/* if (dayValue < 0) {
					document.getElementById('dayValue').style.borderColor = "red";
					errorMsg = "<br>Entered  Days value can not be negative";
					validationError.innerHTML += errorMsg;

					submit = false;
				} */
				if(submit==false){
					return false;	
					}
				
				var minimumDays;
				var miniumMonths;
				var minimumYears;
				var totalMinimumTenure = 0;
				var productConfMiniumTenure = "${productConfiguration.minimumTenure}";
				var splitMinTenure = productConfMiniumTenure.split(",");
				for(var i = 0; i < splitMinTenure.length; i++) {
					var daysOrMonthOrYearVal = splitMinTenure[i];
					if(daysOrMonthOrYearVal.includes("Day")){
						var  splitDaysVal = daysOrMonthOrYearVal.split(" ");
							minimumDays = splitDaysVal[0];
					}else if(daysOrMonthOrYearVal.includes("Month")){
						var  splitMonthVal = daysOrMonthOrYearVal.split(" ");
						miniumMonths = splitMonthVal[0];
					}else if(daysOrMonthOrYearVal.includes("Year")){
						var  splitYearVal = daysOrMonthOrYearVal.split(" ");
						minimumYears = splitYearVal[0];
					}
					
				}
				 if(minimumDays != undefined){
					 totalMinimumTenure += parseInt(minimumDays);
					 }
				 if(miniumMonths != undefined){ 
					 var newMonthDateMinTenure = new Date(year,month+parseInt(miniumMonths),day);
					 totalMinimumTenure += parseInt((newMonthDateMinTenure - today)/(1000*60*60*24));
				 
				 }
				 if(minimumYears != undefined){ 
					 var newDateForMinTenure = new Date(year+parseInt(minimumYears),month,day);
						var _daysMinTenure = (newDateForMinTenure - today)/(1000*60*60*24);
					 totalMinimumTenure += parseInt(_daysMinTenure);
					 }
				
				 productConfMiniumTenure.replace(/,/g, ',');
				
				if (parseInt(fdTenure) < parseInt(totalMinimumTenure)) {
					document.getElementById('fdTenure').style.borderColor = "red";
					errorMsg = "<br>Minimum tenure for this deposit is "+ (productConfMiniumTenure.replace(/,/g, ' '))+".";
					validationError.innerHTML += errorMsg;
					submit = false;
				}
				
				
				 if(submit==false){
						return false;	
						}
				var maximumDays;
				var maximumMonths;
				var maximumYears;
				var totalMaximumTenure = 0;
				var productConfMaximumTenure = "${productConfiguration.maximumTenure}";
				var splitMaxTenure = productConfMaximumTenure.split(",");
				for(var i = 0; i < splitMaxTenure.length; i++) {
					var daysOrMonthOrYearValMax = splitMaxTenure[i];
					if(daysOrMonthOrYearValMax.includes("Day")){
						var  splitDaysValMax = daysOrMonthOrYearValMax.split(" ");
						maximumDays = splitDaysValMax[0];
					}else if(daysOrMonthOrYearValMax.includes("Month")){
						var  splitMonthValMax = daysOrMonthOrYearValMax.split(" ");
						maximumMonths = splitMonthValMax[0];
					}else if(daysOrMonthOrYearValMax.includes("Year")){
						var  splitYearValMax = daysOrMonthOrYearValMax.split(" ");
						maximumYears = splitYearValMax[0];
					}
					
				}
				 if(maximumDays != undefined){
					 totalMaximumTenure += parseInt(maximumDays);
					 }
				 
				 if(maximumMonths != undefined){
					 var newMonthDateMaxTenure = new Date(year,month+parseInt(maximumMonths),day);
					 totalMaximumTenure += parseInt((newMonthDateMaxTenure - today)/(1000*60*60*24));
				 }
				 if(maximumYears != undefined) {
					 var newDateForMaxTenure = new Date(year+parseInt(maximumYears),month,day);
						var _daysMaxTenure = (newDateForMaxTenure - today)/(1000*60*60*24);
					 totalMaximumTenure += parseInt(_daysMaxTenure);
				 }
				
				 productConfMaximumTenure.replace(/,/g, ',');
				if (parseInt(fdTenure) > parseInt(totalMaximumTenure)) {
					document.getElementById('fdTenure').style.borderColor = "red";
					errorMsg = "<br>Maximum tenure for this deposit is "+ (productConfMaximumTenure.replace(/,/g, ' '))+".";
					validationError.innerHTML += errorMsg;
					submit = false;
				}
					
						if(paymentType == 'Monthly'){
						if (parseInt(fdTenure) < 30) {
							document.getElementById('fdTenure').style.borderColor = "red";
							errorMsg = "<br>Minimum tenure should be 30 days for Monthly recurring deposit.";
							validationError.innerHTML += errorMsg;
							submit = false;
						
						}
						}
						 if(submit==false){
								return false;	
								}
						if(paymentType == 'Quarterly'){
							if (parseInt(fdTenure) < 90) {
								document.getElementById('fdTenure').style.borderColor = "red";
								errorMsg = "<br>Minimum tenure should be 90 days for Quarterly recurring deposit.";
								validationError.innerHTML += errorMsg;
								submit = false;
							
							}
						
						}
						if(paymentType == 'Half Yearly'){
							if (parseInt(fdTenure) < 180) {
								document.getElementById('fdTenure').style.borderColor = "red";
								errorMsg = "<br>Minimum tenure should be 180 days for Half Yearly recurring deposit.";
								validationError.innerHTML += errorMsg;
								submit = false;
							
							}
						
						}
						if(paymentType == 'Annually'){
							if (parseInt(fdTenure) < 365) {
								document.getElementById('fdTenure').style.borderColor = "red";
								errorMsg = "<br>Minimum tenure should be 365 days for Yearly recurring deposit.";
								validationError.innerHTML += errorMsg;
								submit = false;
							
							}
						
						}
				

				
				
				/* else {
					document.getElementById('fdTenureType').style.borderColor = "black";
				} */
				if (submit == false) {
					document.getElementById('depositCross').style.display = 'block';
					return false;
				}
				document.getElementById('depositOk').style.display = 'block';

				/************** ....................nominee validation starts ........... ************** */
		var isNomineeMandatory=$('#isNomineeMandatory').val();
				if(isNomineeMandatory==1 || isNomineeMandatory=="1"){
					inp = document.querySelectorAll("#nomineeDetails .input");
					var nomineeCross = document.getElementById("nomineeCross");
					for (i = 0; i < inp.length; i++) {
						inp[i].style.borderColor = "#cccccc";
					}

					var regExp1 = new RegExp("[^a-z|^A-Z]");

					var nomineeName = document.getElementById("nomineeName").value;
					var nomineeAddress = document.getElementById("nomineeAddress").value;
					var nomineeRelationShip = document
							.getElementById("nomineeRelationShip").value;
					var nomineeAge = document.getElementById("nomineeAge").value;

					var nomineePan = document.getElementById("nomineePan").value;
					var nomineeAadhar = document.getElementById("nomineeAadhar").value;
					var nomineeAadharLen = document.getElementById("nomineeAadhar").value.length;
					var gaurdianAadhar = document.getElementById("gaurdianAadhar").value;
					if (nomineeAadharLen != 12 && nomineeAadharLen>0) {
						document.getElementById("nomineeAadhar").style.borderColor = "red"
						nomineeCross.style.display = 'block';
						errorMsg = "<br>Aadhar card number is not valid. Please insert 12 digit number.";
						validationError.innerHTML += errorMsg;
						submit = false;
					}

					var panVal = $('#nomineePan').val();
					var regpan = /^([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}?$/;

					if (nomineeName == "") {

						document.getElementById("nomineeName").style.borderColor = "red"
						errorMsg = "<br>Please enter nominee name";
						validationError.innerHTML += errorMsg;
						nomineeCross.style.display = 'block';
						submit = false;
					}
					if (nomineeName.length<3 || nomineeName.length>100) {

						document.getElementById("nomineeName").style.borderColor = "red"
						errorMsg = "<br>Nominee name must be between 3 and 100 characters.";
						validationError.innerHTML += errorMsg;
						nomineeCross.style.display = 'block';
						submit = false;
					}
					
					if (nomineeAge == "" || isNaN(nomineeAge)) {

						document.getElementById("nomineeAge").style.borderColor = "red"
						nomineeCross.style.display = 'block';
						errorMsg = "<br>Please enter nominee age";
						validationError.innerHTML += errorMsg;
						submit = false;
					}
					if (nomineeAddress == "") {
						document.getElementById("nomineeAddress").style.borderColor = "red";
						errorMsg = "<br>Please enter nominee address";
						validationError.innerHTML += errorMsg;
						nomineeCross.style.display = 'block';
						submit = false;
					}
					if (nomineeAddress.length<3 || nomineeAddress.length>200) {

						document.getElementById("nomineeAddress").style.borderColor = "red"
						errorMsg = "<br>Nominee address must be between 3 and 200 characters.";
						validationError.innerHTML += errorMsg;
						
						submit = false;
					}
					if (nomineeRelationShip == "") {
						document.getElementById("nomineeRelationShip").style.borderColor = "red";
						nomineeCross.style.display = 'block';
						errorMsg = "<br>Please enter nominee relationship";
						validationError.innerHTML += errorMsg;
						submit = false;
					}
					if (nomineeRelationShip.length<3 || nomineeRelationShip.length>100) {

						document.getElementById("nomineeRelationShip").style.borderColor = "red"
						errorMsg = "<br>Nominee relationship must be between 3 and 100 characters.";
						validationError.innerHTML += errorMsg;
						submit = false;
					}
					if (regExp1.test(nomineeRelationShip)) {

						document.getElementById('nomineeRelationShip').style.borderColor = "red";
						errorMsg = "<br>Special characters and numbers are not allowed";
						validationError.innerHTML += errorMsg;
						nomineeCross.style.display = 'block';
						submit = false;
					}
					
					/* if (nomineeAadhar == "") {
						document.getElementById("nomineeAadhar").style.borderColor = "red";
						nomineeCross.style.display = 'block';
						errorMsg = "<br>Please enter nominee Aadhar card number";
						validationError.innerHTML += errorMsg;
						submit = false;
					} */

					if (submit == false) {
						return false;
					}
					
					
					
					nomineeAge = parseInt(nomineeAge);

					if (nomineeAge < 18) {

						var guardianName = document.getElementById('guardianName').value;
						var guardianAge = parseInt(document
								.getElementById('guardianAge').value);
						var guardianAddress = document
								.getElementById('guardianAddress').value;
						var guardianRelationShip = document
								.getElementById('guardianRelationShip').value;
						var gaurdianPan = document.getElementById("gaurdianPan").value;
						var gaurdianAadhar = document
								.getElementById("gaurdianAadhar").value;
						var gaurdianAadharLen = document
								.getElementById("gaurdianAadhar").value.length;

						if (guardianName == "" && guardianAge == ""
							&& guardianAddress == ""
							&& guardianRelationShip == ""
							) { 
							document.getElementById("guardianName").style.borderColor = "red";
							document.getElementById("guardianAge").style.borderColor = "red";
							document.getElementById("guardianAddress").style.borderColor = "red";
							document.getElementById("guardianRelationShip").style.borderColor = "red";
							//document.getElementById("gaurdianPan").style.borderColor = "red";
							//document.getElementById("gaurdianAadhar").style.borderColor = "red";
						errorMsg = "<br>Please fill all the guardian details";
						validationError.innerHTML += errorMsg;
						nomineeCross.style.display = 'block';
						submit = false;
						return submit;
					}

							if (guardianName == "")
								document.getElementById("guardianName").style.borderColor = "red";
							if (guardianName.length<3 || guardianName>100) {
								document.getElementById("guardianName").style.borderColor = "red"
								errorMsg = "<br>Guardian name must be between 3 and 100 characters";
								validationError.innerHTML += errorMsg;
								submit = false;
							}
							if (guardianAge == "" || isNaN(guardianAge))
								document.getElementById("guardianAge").style.borderColor = "red";
							if (guardianAddress == "")
								document.getElementById("guardianAddress").style.borderColor = "red";
							if (guardianAddress.length < 3 || guardianAddress > 200) {

								document.getElementById("guardianAddress").style.borderColor = "red"
								errorMsg = "<br>Guardian address must be between 3 and 200 characters";
								validationError.innerHTML += errorMsg;
								submit = false;
							}
							if (guardianRelationShip == "")
								document.getElementById("guardianRelationShip").style.borderColor = "red";
							if (guardianRelationShip.length<3 || guardianRelationShip>100) {

								document.getElementById("guardianRelationShip").style.borderColor = "red"
								errorMsg = "<br>Guardian relationship must be between 3 and 100 characters";
								validationError.innerHTML += errorMsg;
								submit = false;
							}
							/* if (gaurdianPan == "")
								document.getElementById("gaurdianPan").style.borderColor = "red"; */
							/* if (gaurdianAadhar == "" || isNaN(guardianAge))
								document.getElementById("gaurdianAadhar").style.borderColor = "red"; */
							
							
						
						

						if (guardianAge< 18 || guardianAge >100) {
							document.getElementById('guardianAge').style.borderColor = "red";
							errorMsg = "<br>Guardian age should be between 18 and 100 years";
							validationError.innerHTML += errorMsg;
							nomineeCross.style.display = 'block';

							submit = false;
						}

						if (regExp1.test(guardianRelationShip)) {
							document.getElementById('guardianRelationShip').style.borderColor = "red";
							errorMsg = "<br>Special characters and numbers are not allowed";
							validationError.innerHTML += errorMsg;
							nomineeCross.style.display = 'block';
							submit = false;
						}

						if (gaurdianAadharLen != 12 && gaurdianAadharLen>0) {

							document.getElementById('gaurdianAadhar').style.borderColor = "red";
							errorMsg = "<br>Aadhar card number is not valid. Please insert 12 digit number.";
							validationError.innerHTML += errorMsg;
							nomineeCross.style.display = 'block';
							submit = false;
						}

						var panVal = $('#gaurdianPan').val();

						var regpan = /^([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}?$/;

						if (regpan.test(panVal)) {

							document.getElementById("gaurdianPan").style.borderColor = "black"
						} else if(panVal!="") {

							document.getElementById('gaurdianPan').style.borderColor = "red";
							errorMsg = "<br>Please input correct pan card number.";
							nomineeCross.style.display = 'block';
							validationError.innerHTML += errorMsg;
							submit = false;
						}

						if (gaurdianAadhar == nomineeAadhar && nomineeAadhar!="") {

							document.getElementById('nomineeAadhar').style.borderColor = "red";
							document.getElementById('gaurdianAadhar').style.borderColor = "red";
							errorMsg = "<br>Nominee Aadhar and Guardian Aadhar should not be same.";
							validationError.innerHTML += errorMsg;
							nomineeCross.style.display = 'block';
							submit = false;

						}

						if (regpan.test(panVal)) {

							document.getElementById("nomineePan").style.borderColor = "black"
						} else if(panVal!="") {

							document.getElementById('nomineePan').style.borderColor = "red";
							errorMsg = "<br>Please input correct pan card number.";
							nomineeCross.style.display = 'block';
							validationError.innerHTML += errorMsg;
							submit = false;
						}

					} else {

						var nomineePan = document.getElementById('nomineePan').value;
						var panVal = $('#nomineePan').val();

						var regpan = /^([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}?$/;

						if (regpan.test(panVal)) {

							document.getElementById("nomineePan").style.borderColor = "black"
						} else if(panVal!="") {

							document.getElementById('nomineePan').style.borderColor = "red";
							errorMsg = "<br>Please input correct pan card number.";
							nomineeCross.style.display = 'block';
							validationError.innerHTML += errorMsg;
							submit = false;
						}
						/* if (nomineePan == "") {

							document.getElementById("nomineePan").style.borderColor = "red";
							errorMsg = "<br>Please enter nominee Pan card Number";
							validationError.innerHTML += errorMsg;
							nomineeCross.style.display = 'block';
							submit = false;
						} */
					}

				}
				

				

				
				if($("#branchCode").length>0){
					var branchCode=document.getElementById("branchCode").value;
					if (branchCode.length<3 || branchCode>100) {

						document.getElementById("branchCode").style.borderColor = "red"
						errorMsg = "<br>Branch Code must be between 3 and 100";
						validationError.innerHTML += errorMsg;
						nomineeCross.style.display = 'block';
						submit = false;
					}
				}
				
				if($("#depositArea").val()==""){
					
						document.getElementById("depositArea").style.borderColor = "red"
						errorMsg = "<br>Area can not be empty";
						validationError.innerHTML += errorMsg;
						
						submit = false;
					}
				
				
				


				if (submit == false) {
					return false
				}
				document.getElementById("nomineeOk").style.display = 'block';

				/********* ...............payoff validation starts.......... ****************/

				inp = document.querySelectorAll("#payOffDetails .form-control");
				for (i = 0; i < inp.length; i++) {
					inp[i].style.borderColor = "#cccccc";
				}

				var fdPayOffOnce = document.getElementById("fdPayOffType0").checked;
				var fdPayOffMonth = document.getElementById("fdPayOffType1").checked;
				var fdPayOffQuarter = document.getElementById("fdPayOffType3").checked;
				var fdPayOffSemiAnnual = document
						.getElementById("fdPayOffType4").checked;
				var fdPayOffAnnual = document.getElementById("fdPayOffType5").checked;
				var fdPayOffEnd = document.getElementById("fdPayOffType6").checked;

				if (fdPayOffOnce == true || fdPayOffMonth == true
						|| fdPayOffQuarter == true
						|| fdPayOffSemiAnnual == true || fdPayOffAnnual == true) {
					if (fdPayOffOnce == true) {
						if (document.getElementById('oneTimePayOffDate').value == '') {

							document.getElementById('oneTimePayOffDate').style.borderColor = "red";

							submit = false;
						}

					}
					var partChecked = document.getElementById("partInterest").checked;
					var percentChecked = document
							.getElementById("percentInterest").checked;

					document.getElementById("partInterest").style.boxShadow = "none";
					document.getElementById('percentInterest').style.boxShadow = "none";

					if (partChecked == false && percentChecked == false) {

						document.getElementById("partInterest").style.boxShadow = "0 0 5px 0px red inset";
						document.getElementById('percentInterest').style.boxShadow = "0 0 5px 0px red inset";

						document.getElementById("payoffCross").style.display = 'block';
						submit = false;

					}

					var interestPercent = document
							.getElementById("interestPercent").value;
					var interestPayAmount = document
							.getElementById("interestPayAmount").value;

					if (partChecked == true && interestPayAmount == "") {
						document.getElementById("interestPayAmount").style.borderColor = "red";

						document.getElementById("payoffCross").style.display = 'block';

						submit = false;

					}

					if (percentChecked == true && interestPercent == "") {
						document.getElementById("interestPayAmount").style.borderColor = "red";

						document.getElementById("payoffCross").style.display = 'block';
						submit = false;

					}

					var savingBankRadio = document
							.getElementById("savingBankRadio").checked;
					var differentBankRadio = document
							.getElementById("differentBankRadio").checked;

					document.getElementById("savingBankRadio").style.boxShadow = "none";
					document.getElementById('differentBankRadio').style.boxShadow = "none";
					
					if (savingBankRadio == false && differentBankRadio == false) {

						document.getElementById("savingBankRadio").style.boxShadow = "0 0 5px 0px red inset";
						document.getElementById('differentBankRadio').style.boxShadow = "0 0 5px 0px red inset";

						document.getElementById("payoffCross").style.display = 'block';
						submit = false;
					}
				

					var beneficiaryName = document
							.getElementById("beneficiaryName") != null ? document
							.getElementById("beneficiaryName").value
							: "";
					var beneficiaryAccount = document
							.getElementById("beneficiaryAccount") != null ? document
							.getElementById("beneficiaryAccount").value
							: "";

					if (beneficiaryName == "") {
						document.getElementById("beneficiaryName").style.borderColor = "red";

						submit = false;

					}
					if (beneficiaryAccount == "") {
						document.getElementById("beneficiaryAccount").style.borderColor = "red";

						submit = false;
					}

					if (differentBankRadio == true) {

						var imps = document.getElementById("imps").checked;
						var rtgs = document.getElementById("rtgs").checked;
						var neft = document.getElementById("neft").checked;

						document.getElementById("imps").style.boxShadow = "none";
						document.getElementById('rtgs').style.boxShadow = "none";
						document.getElementById('neft').style.boxShadow = "none";

						if (imps == false && rtgs == false && neft == false) {
							document.getElementById("imps").style.boxShadow = "0 0 5px 0px red inset";
							document.getElementById('rtgs').style.boxShadow = "0 0 5px 0px red inset";
							document.getElementById('neft').style.boxShadow = "0 0 5px 0px red inset";

							submit = false;
						}

						var beneficiaryBank = document
								.getElementById("beneficiaryBank").value;
						var beneficiaryIfsc = document
								.getElementById("beneficiaryIfsc").value;

						if (beneficiaryBank == "") {
							document.getElementById("beneficiaryBank").style.borderColor = "red";

							submit = false;
						}
						if (beneficiaryIfsc == "") {
							document.getElementById("beneficiaryIfsc").style.borderColor = "red";

							submit = false;
						}

					}
					
				}

				if (submit == false) {
					validationError.innerHTML = "<br>Please enter all the payoff details";
					document.getElementById("payoffCross").style.display = 'block';
					return false;
				}

				document.getElementById("payoffOk").style.display = 'block';

				
				
				
				
				/********* ...............maturity validation starts.......... ****************/
var paymentModeOnMaturity=$('#paymentModeOnMaturity').val();
			if(paymentModeOnMaturity=="Fund Transfer"){
				inp = document.querySelectorAll("#maturityDetails .form-control");
				for (i = 0; i < inp.length; i++) {
					inp[i].style.borderColor = "#cccccc";
				}

				var inLinked= document
				.getElementById("inLinked").checked;
				var notInLinked= document
				.getElementById("notInLinked").checked;
				document.getElementById("notInLinked").style.boxShadow = "none";
				document.getElementById('inLinked').style.boxShadow = "none";
				
				if (inLinked == false && notInLinked == false) {

					document.getElementById("inLinked").style.boxShadow = "0 0 5px 0px red inset";
					document.getElementById('notInLinked').style.boxShadow = "0 0 5px 0px red inset";

					document.getElementById("maturityCross").style.display = 'block';
					submit = false;
				}
				if(notInLinked==true){
					var savingBankRadioMr = document
					.getElementById("savingBankRadioMr").checked;
			var differentBankRadioMr = document
					.getElementById("differentBankRadioMr").checked;

			document.getElementById("savingBankRadioMr").style.boxShadow = "none";
			document.getElementById('differentBankRadioMr').style.boxShadow = "none";

					
					if (savingBankRadioMr == false && differentBankRadioMr == false) {

						document.getElementById("savingBankRadioMr").style.boxShadow = "0 0 5px 0px red inset";
						document.getElementById('differentBankRadioMr').style.boxShadow = "0 0 5px 0px red inset";

						document.getElementById("maturityCross").style.display = 'block';
						submit = false;
					}

					var beneficiaryNameMr = document
							.getElementById("beneficiaryNameMr") != null ? document
							.getElementById("beneficiaryNameMr").value
							: "";
					var beneficiaryAccountMr = document
							.getElementById("beneficiaryAccountMr") != null ? document
							.getElementById("beneficiaryAccountMr").value
							: "";

					if (beneficiaryNameMr == "") {
						document.getElementById("beneficiaryNameMr").style.borderColor = "red";

						submit = false;

					}
					if (beneficiaryAccountMr == "") {
						document.getElementById("beneficiaryAccountMr").style.borderColor = "red";

						submit = false;
					}

					
					
					
					if (differentBankRadioMr == true) {

						var impsMr = document.getElementById("impsMr").checked;
						var rtgsMr = document.getElementById("rtgsMr").checked;
						var neftMr = document.getElementById("neftMr").checked;

						document.getElementById("impsMr").style.boxShadow = "none";
						document.getElementById('rtgsMr').style.boxShadow = "none";
						document.getElementById('neftMr').style.boxShadow = "none";

						if (impsMr == false && rtgsMr == false && neftMr == false) {
							document.getElementById("impsMr").style.boxShadow = "0 0 5px 0px red inset";
							document.getElementById('rtgsMr').style.boxShadow = "0 0 5px 0px red inset";
							document.getElementById('neftMr').style.boxShadow = "0 0 5px 0px red inset";

							submit = false;
						}

						var beneficiaryBankMr = document
								.getElementById("beneficiaryBankMr").value;
						var beneficiaryIfscMr = document
								.getElementById("beneficiaryIfscMr").value;

						if (beneficiaryBankMr == "") {
							document.getElementById("beneficiaryBankMr").style.borderColor = "red";

							submit = false;
						}
						if (beneficiaryIfscMr == "") {
							document.getElementById("beneficiaryIfscMr").style.borderColor = "red";

							submit = false;
						}

					}
				}

				if (submit == false) {
					validationError.innerHTML = "<br>Please enter all the maturity details";
					document.getElementById("maturityCross").style.display = 'block';
					return false;
				}

				document.getElementById("maturityOk").style.display = 'block';


			}
				
				
				
				
				
				/********........payment validation starts..........***********/

				inp = document.querySelectorAll("#paymentDetails .input");
				for (i = 0; i < inp.length; i++) {
					inp[i].style.borderColor = "#cccccc";
				}

				var paymentMode = $('#paymentMode').val();

				if (paymentMode == 'Select') {

					document.getElementById('paymentMode').style.borderColor = "red";
					validationError.innerHTML += "<br>Please select mode of payment";
					document.getElementById("paymentCross").style.display = 'block';
					submit = false;
				}

				else if (paymentMode == 'Cheque' || paymentMode == 'DD') {

					var chequeNo = document.getElementById('chequeNo').value;
					var chequeBank = document.getElementById('chequeBank').value;
					var chequeBranch = document.getElementById('chequeBranch').value;

					if (chequeNo == "" || chequeBank == ""
							|| chequeBranch == "") {

						if (chequeNo == "")
							document.getElementById('chequeNo').style.borderColor = "red";
						if (chequeBank == "")
							document.getElementById('chequeBank').style.borderColor = "red";
						if (chequeBranch == "")
							document.getElementById('chequeBranch').style.borderColor = "red";

						validationError.innerHTML += "<br>Please enter all the payment details";
						document.getElementById("paymentCross").style.display = 'block';
						submit = false;
					}

				}

				else if (paymentMode == 'Debit Card' || paymentMode == 'Credit Card') {

					//var cardType = document.getElementById('cardType').value;
					var cardNo = document.getElementById('cardNo').value;
					var expiryMonth = document.getElementById('expiryMonth').value;
					var expiryYear = document.getElementById('expiryYear').value;
					var cvv = document.getElementById('cvv').value;

					if (cardNo == "" || expiryMonth == ""
							|| expiryYear == "" || cvv == "") {

						/* if (cardType == "")
							document.getElementById('cardType').style.borderColor = "red"; */
						if (cardNo == "")
							document.getElementById('cardNo').style.borderColor = "red";
						if (expiryMonth == "")
							document.getElementById('expiryMonth').style.borderColor = "red";
						if (expiryYear == "")
							document.getElementById('expiryYear').style.borderColor = "red";
						if (cvv == "")
							document.getElementById('cvv').style.borderColor = "red";

						validationError.innerHTML += "<br>Please enter all the card details";
						document.getElementById("paymentCross").style.display = 'block';
						submit = false;

					}

				}

				else if (paymentMode == 'Fund Transfer') {
					var fdAmount = parseFloat(document
							.getElementById('fdAmount').value);
					var linkedAccountBalance = parseFloat(document
							.getElementById('linkedAccountBalance1').value);

					if (linkedAccountBalance < fdAmount) {

						validationError.innerHTML += "<br>Insufficient balance in linked account";
						document.getElementById("paymentCross").style.display = 'block';
						submit = false;
					}
				}

				else if (paymentMode == 'Net Banking') {

					var transferType = document.getElementById('transferType').value;
					if (document.getElementById('bankType').value != 'SameBank'
							&& transferType == "") {
						document.getElementById('transferType').style.borderColor = "red";
						validationError.innerHTML += "<br>Please select transfer type";
						document.getElementById("paymentCross").style.display = 'block';
						submit = false;
					}

					var bankType = document.getElementById('bankType').value;

					if (bankType == "") {
						document.getElementById('bankType').style.borderColor = "red";
						validationError.innerHTML += "<br>Please select Bank Type";
						document.getElementById("paymentCross").style.display = 'block';
						return false;
					}

					var paymentBeneficiaryName = document
							.getElementById('paymentBeneficiaryName').value;

					if (paymentBeneficiaryName == "") {
						document.getElementById('paymentBeneficiaryName').style.borderColor = "red";
						validationError.innerHTML += "<br>Please enter beneficiary name";
						document.getElementById("paymentCross").style.display = 'block';
						submit = false;
					}

					var paymentBeneficiaryAccount = document
							.getElementById('paymentBeneficiaryAccount').value;
					if (paymentBeneficiaryAccount == "") {
						document.getElementById('paymentBeneficiaryAccount').style.borderColor = "red";
						validationError.innerHTML += "<br>Please enter beneficiary account number";
						document.getElementById("paymentCross").style.display = 'block';
						submit = false;
					}

					if (bankType == "DifferentBank") {
						var paymentBeneficiaryBank = document
								.getElementById('paymentBeneficiaryBank').value;
						var paymentBeneficiaryIfsc = document
								.getElementById('paymentBeneficiaryIfsc').value;

						if (paymentBeneficiaryBank == "") {
							document.getElementById('paymentBeneficiaryBank').style.borderColor = "red";
							validationError.innerHTML += "<br>Please enter beneficiary bank name";
							document.getElementById("paymentCross").style.display = 'block';
							submit = false;
						}

						if (paymentBeneficiaryIfsc == "") {
							document.getElementById('paymentBeneficiaryIfsc').style.borderColor = "red";
							validationError.innerHTML += "<br>Please enter ifsc code";
							document.getElementById("paymentCross").style.display = 'block';
							submit = false;
						}
					}
				}
				if(submit==false){return false;}
				var result = checkPanAndAdhaar();
				if (result > 0) {submit = false;}
				return submit;
			}

			function hideAll() {

				document.getElementById('transferModeTr').style.display = 'none';
				document.getElementById('beneficiaryNameTr').style.display = 'none';
				document.getElementById('accountNoTr').style.display = 'none';
				document.getElementById('bankNameTr').style.display = 'none';
				document.getElementById('ifscTr').style.display = 'none';

				document.getElementById("beneficiaryName").value = "";
				document.getElementById("beneficiaryAccount").value = "";
				document.getElementById("beneficiaryBank").value = "";
				document.getElementById("beneficiaryIfsc").value = "";

			}

			function showDetailsSameBank() {

				document.getElementById('transferModeTr').style.display = 'none';
				document.getElementById('beneficiaryNameTr').style.display = 'block';
				document.getElementById('accountNoTr').style.display = 'block';
				document.getElementById('bankNameTr').style.display = 'none';
				document.getElementById('ifscTr').style.display = 'none';

				document.getElementById("beneficiaryBank").value = "";
				document.getElementById("beneficiaryIfsc").value = "";

			}
			
			function showDetailsSameBankM() {

				document.getElementById('transferModeMr').style.display = 'none';
				document.getElementById('beneficiaryDivMr').style.display = 'block';
				document.getElementById('accountNoMr').style.display = 'block';
				document.getElementById('bankNameMr').style.display = 'none';
				document.getElementById('ifscMr').style.display = 'none';
				document.getElementById('neftMr').checked = false;
				document.getElementById('impsMr').checked = false;
				document.getElementById('rtgsMr').checked = false;

				document.getElementById("beneficiaryBankMr").value = "";
				document.getElementById("beneficiaryIfscMr").value = "";

			}

			function showDetailsDiffBank() {

				document.getElementById('transferModeTr').style.display = 'block';
				document.getElementById('beneficiaryNameTr').style.display = 'block';
				document.getElementById('accountNoTr').style.display = 'block';
				document.getElementById('bankNameTr').style.display = 'block';
				document.getElementById('ifscTr').style.display = 'block';

			}
			function showDetailsDiffBankM() {

				document.getElementById('transferModeMr').style.display = 'block';
				document.getElementById('beneficiaryDivMr').style.display = 'block';
				document.getElementById('accountNoMr').style.display = 'block';
				document.getElementById('bankNameMr').style.display = 'block';
				document.getElementById('ifscMr').style.display = 'block';

			}
			function displayPartPercentDiv(id) {
				var fdPayOffType0 = document.getElementById('fdPayOffType0').checked;
				if (fdPayOffType0 == true) {
					document.getElementById('oneTimeDatePayoff').style.display = 'block';

				} else {
					document.getElementById('oneTimeDatePayoff').style.display = 'none';
				}
				document.getElementById(id).style.display = 'block';
				document.getElementById('payoffAccountType').style.display = 'block';

			}

			function displayHidePartPercent(id, id1) {
				document.getElementById(id).style.display = 'block';
				document.getElementById(id1).style.display = 'none';
				$("#interestPayAmount").val("");

			}

			function showGuardian(value, id, majorId, minorId) {

				if (parseInt(value) < 18) {
					document.getElementById('nomineePanCard').style.display = 'none';
					document.getElementById(id).style.display = 'block';

					document.getElementById("guardianName").value = "";
					document.getElementById("guardianAge").value = "";
					document.getElementById("guardianAddress").value = "";
					document.getElementById("guardianRelationShip").value = "";
					document.getElementById("gaurdianPan").value = "";
					document.getElementById("gaurdianAadhar").value = "";

				} else {
					document.getElementById(id).style.display = 'none';
					document.getElementById('nomineePanCard').style.display = 'block';
					document.getElementById("nomineePan").value = "";
				}
			}

			function changeExpiryDate() {

				document.getElementById('expiryDate').value = document
						.getElementById('expiryMonth').value
						+ "-" + document.getElementById('expiryYear').value
			}

			function showDetails(obj) {

				var paymentMode = $('option:selected', obj).attr('mode');

				if (paymentMode == 'Select') {
					document.getElementById('bankDetailsTr').style.display = 'none';
					document.getElementById('cardDetailsTr').style.display = 'none';
					document.getElementById('netBankingDetailsTr').style.display = 'none';
					document.getElementById('linkedAccountBalanceTr').style.display = 'none';
					document.getElementById('linkedAccountTr').style.display = 'none';
				}
				var linkedAccountNo = document.getElementById('linkedAccount').value;

				if (paymentMode == 'Net Banking') {
					document.getElementById('paymentBeneficiaryNameDiv').style.display = 'block';
					document.getElementById('paymentBeneficiaryAccountDiv').style.display = 'block';

					if ($('#bankType').val() == 'DifferentBank') {
						document.getElementById('transferOption').style.display = 'block';
						document.getElementById('paymentBankNameTr').style.display = 'block';
						document.getElementById('paymentBeneficiaryIfscDiv').style.display = 'block';

					} else {
						document.getElementById('transferOption').style.display = 'none';
						document.getElementById('paymentBankNameTr').style.display = 'none';
						document.getElementById('paymentBeneficiaryIfscDiv').style.display = 'none';

					}

				}
				if (paymentMode == 'Fund Transfer') {
					document.getElementById('bankDetailsTr').style.display = 'none';
					document.getElementById('cardDetailsTr').style.display = 'none';
					document.getElementById('netBankingDetailsTr').style.display = 'none';

					document.getElementById('linkedAccountBalanceTr').style.display = 'block';
					document.getElementById('linkedAccountTr').style.display = 'block';
					var accArray = $('#accountNo').val().split(',');
					document.getElementById('linkedAccount').value = accArray[0];
					document.getElementById('linkedAccountBalance1').value = $(
							'#accountBalance1').val();
					document.getElementById('linkedAccountBalance').value = parseFloat(
							$('#accountBalance1').val())
							.toLocaleString("en-US");

				}

				if (paymentMode == 'Cash') {
					document.getElementById('bankDetailsTr').style.display = 'none';
					document.getElementById('cardDetailsTr').style.display = 'none';
					document.getElementById('netBankingDetailsTr').style.display = 'none';
					if (linkedAccountNo == "") {

					} else {
						document.getElementById('linkedAccountBalanceTr').style.display = 'none';
						document.getElementById('linkedAccountTr').style.display = 'none';

					}
				}
				if (paymentMode == 'DD') {

					document.getElementById('bankDetailsTr').style.display = 'block';
					document.getElementById('cardDetailsTr').style.display = 'none';
					document.getElementById('netBankingDetailsTr').style.display = 'none';

					/* for labels */
					document.getElementById('ddNoLabel').style.display = 'block';
					document.getElementById('ddDateLabel').style.display = 'block';
					document.getElementById('chequeNoLabel').style.display = 'none';
					document.getElementById('chequeDateLabel').style.display = 'none';

					if (linkedAccountNo == "") {

					} else {
						document.getElementById('linkedAccountBalanceTr').style.display = 'none';
						document.getElementById('linkedAccountTr').style.display = 'none';

					}

				}
				if (paymentMode == 'Cheque') {

					document.getElementById('bankDetailsTr').style.display = 'block';
					document.getElementById('cardDetailsTr').style.display = 'none';
					document.getElementById('netBankingDetailsTr').style.display = 'none';

					/* for labels */
					document.getElementById('ddNoLabel').style.display = 'none';
					document.getElementById('ddDateLabel').style.display = 'none';
					document.getElementById('chequeNoLabel').style.display = 'block';
					document.getElementById('chequeDateLabel').style.display = 'block';

					if (linkedAccountNo == "") {

					} else {
						document.getElementById('linkedAccountBalanceTr').style.display = 'none';
						document.getElementById('linkedAccountTr').style.display = 'none';

					}
				}
				if (paymentMode == 'Credit Card'|| paymentMode == 'Debit Card')
				{
					document.getElementById('bankDetailsTr').style.display = 'none';
					document.getElementById('netBankingDetailsTr').style.display = 'none';
					document.getElementById('cardDetailsTr').style.display = 'block';
					if (linkedAccountNo == "") {

					} else {
						document.getElementById('linkedAccountBalanceTr').style.display = 'none';
						document.getElementById('linkedAccountTr').style.display = 'none';

					}
				}

				if (paymentMode == 'Net Banking') {

					document.getElementById('bankDetailsTr').style.display = 'none';
					document.getElementById('cardDetailsTr').style.display = 'none';
					document.getElementById('netBankingDetailsTr').style.display = 'block';
					if (linkedAccountNo == "") {

					} else {
						document.getElementById('linkedAccountBalanceTr').style.display = 'none';
						document.getElementById('linkedAccountTr').style.display = 'none';

					}
				}

			}

			function onchangeBankType() {
				var value = document.getElementById('bankType').value;

				if (value == '') {
					document.getElementById('paymentBeneficiaryNameDiv').style.display = 'none';
					document.getElementById('paymentBeneficiaryAccountDiv').style.display = 'none';
					document.getElementById('paymentBankNameTr').style.display = 'none';
					document.getElementById('paymentBeneficiaryIfscDiv').style.display = 'none';
				}

				if (value == 'SameBank') {
					document.getElementById('paymentBankNameTr').style.display = 'none';
					document.getElementById('paymentBeneficiaryIfscDiv').style.display = 'none';
					document.getElementById('paymentBeneficiaryNameDiv').style.display = 'block';
					document.getElementById('paymentBeneficiaryAccountDiv').style.display = 'block';
					document.getElementById('transferOption').style.display = 'none';

				}

				else if (value == 'DifferentBank') {
					document.getElementById('paymentBeneficiaryNameDiv').style.display = 'block';
					document.getElementById('paymentBeneficiaryAccountDiv').style.display = 'block';
					document.getElementById('paymentBankNameTr').style.display = 'block';
					document.getElementById('paymentBeneficiaryIfscDiv').style.display = 'block';
					document.getElementById('transferOption').style.display = 'block';

				}

			}

			function resetBasicDetails() {

if('${productConfiguration.productType}'=='Regular Deposit' ||'${productConfiguration.productType}'== 'Tax Saving Deposit'){
                $('#paymentType').val('One-Time').change();
				document.getElementById("paymentType").disabled = true;
}
				document.getElementById('fdAmount').value = '';
				//document.getElementById('fdTenureType').value = '';
				document.getElementById('fdTenure').value = 0;
				document.getElementById('paymentAmt').value = '';
				
				document.getElementById('currency').value = '${productConfiguration.defaultCurrency}';

			}

			function resetNomineeDetails() {

				document.getElementById('nomineeName').value = '';
				document.getElementById('nomineeAge').value = '';
				document.getElementById('nomineeAddress').value = '';
				document.getElementById('nomineeRelationShip').value = '';
				document.getElementById('gaurdianDiv').style.display = 'none';
			}

			function resetPayoffDetails() {
				document.getElementById('fdPayOffType0').checked = false;
				document.getElementById('fdPayOffType1').checked = false;
				document.getElementById('fdPayOffType3').checked = false;
				document.getElementById('fdPayOffType4').checked = false;
				document.getElementById('fdPayOffType5').checked = false;
				document.getElementById('fdPayOffType6').checked = false;

				document.getElementById('percentInterest').checked = false;
				document.getElementById('partInterest').checked = false;

				/* document.getElementById('depositAccountRadio').checked=false; */
				document.getElementById('savingBankRadio').checked = false;
				document.getElementById('differentBankRadio').checked = false;

				document.getElementById('neft').checked = false;
				document.getElementById('imps').checked = false;
				document.getElementById('rtgs').checked = false;

				document.getElementById('interestPercent').value = '';
				document.getElementById('interestPayAmount').value = '';
				document.getElementById('beneficiaryName').value = '';
				document.getElementById('beneficiaryAccount').value = '';
				document.getElementById('beneficiaryBank').value = '';
				document.getElementById('beneficiaryIfsc').value = '';

				document.getElementById('partPercentDiv').style.display = 'none';
				document.getElementById('transferModeTr').style.display = 'none';
				document.getElementById('payoffAccountType').style.display = 'none';
				document.getElementById('beneficiaryNameTr').style.display = 'none';
				document.getElementById('interestPercentTr').style.display = 'none';
				document.getElementById('interestAmtTr').style.display = 'none';
				document.getElementById('accountNoTr').style.display = 'none';
				document.getElementById('bankNameTr').style.display = 'none';
				document.getElementById('ifscTr').style.display = 'none';
			}
			
			
			
			function resetMaturityDetails() {

				

				document.getElementById('inLinked').checked = false;
				document.getElementById('notInLinked').checked = false;

				/* document.getElementById('depositAccountRadio').checked=false; */
				document.getElementById('savingBankRadioMr').checked = false;
				document.getElementById('differentBankRadioMr').checked = false;

				document.getElementById('neftMr').checked = false;
				document.getElementById('impsMr').checked = false;
				document.getElementById('rtgsMr').checked = false;

				document.getElementById('beneficiaryNameMr').value = '';
				document.getElementById('beneficiaryAccountMr').value = '';
				document.getElementById('beneficiaryBankMr').value = '';
				document.getElementById('beneficiaryIfscMr').value = '';

				
				document.getElementById('transferModeMr').style.display = 'none';
				document.getElementById('payoffAccountTypeMr').style.display = 'none';
				document.getElementById('beneficiaryDivMr').style.display = 'none';
				
				document.getElementById('accountNoMr').style.display = 'none';
				document.getElementById('bankNameMr').style.display = 'none';
				document.getElementById('ifscMr').style.display = 'none';
			
			}

               function inLinkedAccount() {

				

				

				/* document.getElementById('depositAccountRadio').checked=false; */
				document.getElementById('savingBankRadioMr').checked = false;
				document.getElementById('differentBankRadioMr').checked = false;

				document.getElementById('neftMr').checked = false;
				document.getElementById('impsMr').checked = false;
				document.getElementById('rtgsMr').checked = false;

				document.getElementById('beneficiaryNameMr').value = '';
				document.getElementById('beneficiaryAccountMr').value = '';
				document.getElementById('beneficiaryBankMr').value = '';
				document.getElementById('beneficiaryIfscMr').value = '';

				
				document.getElementById('transferModeMr').style.display = 'none';
				document.getElementById('payoffAccountTypeMr').style.display = 'none';
				document.getElementById('beneficiaryDivMr').style.display = 'none';
				
				document.getElementById('accountNoMr').style.display = 'none';
				document.getElementById('bankNameMr').style.display = 'none';
				document.getElementById('ifscMr').style.display = 'none';
				document.getElementById('inLinked').checked = true;
				document.getElementById('notInLinked').checked = false;
			}

               function notInLinkedAccount() {

   				
   				/* document.getElementById('depositAccountRadio').checked=false; */
   				document.getElementById('savingBankRadioMr').checked = false;
   				document.getElementById('differentBankRadioMr').checked = false;

   				document.getElementById('neftMr').checked = false;
   				document.getElementById('impsMr').checked = false;
   				document.getElementById('rtgsMr').checked = false;

   				document.getElementById('beneficiaryNameMr').value = '';
   				document.getElementById('beneficiaryAccountMr').value = '';
   				document.getElementById('beneficiaryBankMr').value = '';
   				document.getElementById('beneficiaryIfscMr').value = '';

   				
   				document.getElementById('transferModeMr').style.display = 'none';
   				document.getElementById('payoffAccountTypeMr').style.display = 'block';
   				document.getElementById('beneficiaryDivMr').style.display = 'none';
   				
   				document.getElementById('accountNoMr').style.display = 'none';
   				document.getElementById('bankNameMr').style.display = 'none';
   				document.getElementById('ifscMr').style.display = 'none';
   				document.getElementById('notInLinked').checked = true;
   				document.getElementById('inLinked').checked = false;
   				
   			}


			function resetPaymentDetails() {

				document.getElementById('cardDetailsTr').style.display = 'none';
				document.getElementById('netBankingDetailsTr').style.display = 'none';
				document.getElementById('bankNameTr').style.display = 'none';
				document.getElementById('paymentMode').value = 'Select';

				document.getElementById('bankDetailsTr').style.display = 'none';
				document.getElementById('linkedAccountBalanceTr').style.display = 'none';
				document.getElementById('linkedAccountTr').style.display = 'none';

				document.getElementById('cardType').value = '';
				document.getElementById('cardNo').value = '';
				document.getElementById('expiryMonth').value = '';
				document.getElementById('expiryYear').value = '';
				document.getElementById('cvv').value = '';

				document.getElementById('bankType').value = '';
				document.getElementById('transferType').value = '';
				document.getElementById('paymentBeneficiaryName').value = '';
				document.getElementById('paymentBeneficiaryAccount').value = '';
				document.getElementById('paymentBeneficiaryBank').value = '';
				document.getElementById('paymentBeneficiaryIfsc').value = '';

			}

			/* function showTenureDetails(value) {
				var tenure = value;
				if (tenure == "Day") {
					document.getElementById('dayId').style.display = 'block';
					document.getElementById('monthId').style.display = 'none';
					document.getElementById('yearId').style.display = 'none';
					document.getElementById('dayValue').style.display = 'none';
					document.getElementById('daysValue').value = "";
					document.getElementById('fdTenure').value = "";
				}
				if (tenure == "Month") {
					document.getElementById('dayId').style.display = 'none';
					document.getElementById('monthId').style.display = 'block';
					document.getElementById('yearId').style.display = 'none';
					document.getElementById('dayValue').style.display = 'none';
					document.getElementById('daysValue').value = "";
					document.getElementById('fdTenure').value = "";
				}
				if (tenure == "Year") {
					document.getElementById('dayId').style.display = 'none';
					document.getElementById('monthId').style.display = 'none';
					document.getElementById('yearId').style.display = 'block';
					document.getElementById('dayValue').style.display = 'block';
					document.getElementById('fdTenure').value = "";
				}

			} */

			function checkPanAndAdhaar() {

				var submit = true;
				var userName = document.getElementById("userName").value;
				var nomineePan = document.getElementById("nomineePan").value;
				var nomineeAadhar = document.getElementById("nomineeAadhar").value;
				var gaurdianAadhar = document.getElementById("gaurdianAadhar").value;
				var gaurdianPan = document.getElementById("gaurdianPan").value;
				var validationError = document
						.getElementById('validationError');

				validationError.innerHTML = "";
				var errorMsg = "";
				var result = 0;
				$
						.ajax({
							async : false,
							type : "GET",
							url : "/common/panCardValidation",
							contentType : "application/json",
							dataType : "json",

							data : "nomineePan=" + nomineePan
									+ "&nomineeAadhar=" + nomineeAadhar
									+ "&gaurdianAadhar=" + gaurdianAadhar
									+ "&gaurdianPan=" + gaurdianPan
									+ "&customerName=" + userName,

							success : function(response) {
								result = response;
								if (result == 1) {
									document.getElementById("nomineePan").style.borderColor = "red"
									errorMsg = "<br>Nominee Pan card is already exist with some other user name. Please input correct pan card number.";
									validationError.innerHTML += errorMsg;
									nomineeCross.style.display = 'block';
									submit = false;
								}

								if (result == 2) {

									document.getElementById("nomineeAadhar").style.borderColor = "red"
									errorMsg = "<br>Nominee Aadhar card is already exist with some other user name. Please input correct Aadhar card number.";
									validationError.innerHTML += errorMsg;
									nomineeCross.style.display = 'block';
									submit = false;

								}

								if (result == 3) {

									document.getElementById("gaurdianPan").style.borderColor = "red"
									errorMsg = "<br>Gaurdian Pan card is already exist with some other user name. Please input correct Pan card number.";
									validationError.innerHTML += errorMsg;
									nomineeCross.style.display = 'block';
									submit = false;
								}

								if (result == 4) {
									document.getElementById("gaurdianAadhar").style.borderColor = "red"
									errorMsg = "<br>Gaurdian Aadhar card is already exist with some other user name. Please input correct Aadhar card number.";
									validationError.innerHTML += errorMsg;
									nomineeCross.style.display = 'block';
									submit = false;
								}

								if (result > 0) {

									submit = false;
								}

							},
							error : function(e) {
								$('#error').html("Error occured!!")

								submit = false;
								//return false;
							}
						});
				return result;
			}

			function isNumberKey1(evt,obj)
			{
				
			   var charCode = (evt.which) ? evt.which : evt.keyCode;
			   if (charCode != 46 && charCode > 31 
			     && (charCode < 48 || charCode > 57)){
			      return false;
			   }else{
			 	  var splitValue = obj.val();
			          var res = splitValue.split(".");
			          if(splitValue.includes(".")){
			          var spVal = res[1];
			          if(spVal.length > 1){
			        	  evt.preventDefault();
			        	  return false;
			          }
			          return true;
			          }else{
			      
			         
			 	  return true;
			          }
			   }
			  }
			
			function checkFunction() {
				
				var paymentType = document.getElementById('paymentType').value;
				var taxSavingDepositChecked = document.getElementById("taxSavingDeposit").checked;
				var taxSavingDepositValue = document.getElementById("taxSavingDeposit").value;
				
				if (taxSavingDepositChecked == true) {
					if('${productConfiguration.productType}'=='Regular Deposit' ||'${productConfiguration.productType}'== 'Tax Saving Deposit'){
					$('#paymentType').val('One-Time').change();
					document.getElementById("paymentType").disabled = true;
					}
					//document.getElementById("deductionDay").disabled = true;
					//document.getElementById('fdTenureType').value = 'Year';
					
					if(taxSavingDepositValue=="Days"){
						document.getElementById('dayId').style.display= 'none';
						document.getElementById('monthId').style.display = 'none';
						document.getElementById('yearId').style.display = 'block';
						
					}else if(taxSavingDepositValue=="Months"){
						document.getElementById('dayId').style.display= 'none';
						document.getElementById('monthId').style.display = 'none';
						document.getElementById('yearId').style.display = 'block';
					}else{
						document.getElementById('dayId').style.display= 'none';
						document.getElementById('monthId').style.display = 'none';
						document.getElementById('yearId').style.display = 'block';
					}
					
					
					//document.getElementById("fdTenureType").disabled = true;
					//document.getElementById('fdTenure').value = '5';
					//document.getElementById("fdTenure").disabled = true;
					//document.getElementById('dayValue').style.display = 'none';
				}

				if (taxSavingDepositChecked == false) {
					//document.getElementById('paymentType').value = '';
					//document.getElementById("paymentType").disabled = false;
					//document.getElementById("deductionDay").disabled = false;
					//document.getElementById('fdTenureType').value = '';
					//document.getElementById("fdTenureType").disabled = false;
					document.getElementById('fdTenure').value = '';
					document.getElementById("fdTenure").disabled = false;
					//document.getElementById('dayValue').style.display = 'block';

				}

			}

			function accountTypeVal(value) {
				
				//$("#fixedDepositForm").attr("action", "getFixedDeposit?val="+value);
				/* if (value != "") { */
					/* $("#taxSavingDepositId").css({
						display : "none"
					}); */
				    //document.getElementById('paymentType').value = '';
					//document.getElementById("paymentType").disabled = false;
					//document.getElementById("deductionDay").disabled = false;
					//document.getElementById('fdTenureType').value = '';
					//document.getElementById("fdTenureType").disabled = false;
					document.getElementById('fdTenure').value = '';
					document.getElementById("fdTenure").disabled = false;
					//$("#taxSavingDeposit").prop('checked', false);
				/* } else {
					$("#taxSavingDepositId").css({
						display : "block"
					});

				} */
				var FCNR = [ "USD", "GBP", "EUR", "JPY", "CAD", "AUD", "SGD",
						"HKD" ];
				var RFC = [ "USD", "GBP", "EUR", "JPY", "CAD", "AUD" ];
				$('#currency option').hide();
				$('#currency').val('Select').show().change();
				$('#currency option').each(function() {

					if (value == "FCNR" || value == "PRP") {
						var length = $.inArray($(this).val(), FCNR);
						if (length > -1)
							$(this).show();
					} else if (value == "RFC") {
						var length = $.inArray($(this).val(), RFC);
						if (length > -1)
							$(this).show();
					} else {
						$('#currency option').show();
					}

				});
				
				 if(value  == 'NRO' || value  == 'NRE' || value == ""){
					$("#currency").val("INR").change();
					
				}else if(value  == 'FCNR' || value  == 'RFC'){
					$("#currency").val("EUR").change();
					
				}  

			}

			function validationAccount(event) {

				var minimumBalance1_ = document.getElementById(event.target.id);

				var keycode = event.which;
				if (!(event.shiftKey == false && (keycode == 8 || keycode == 37 || (keycode >= 48 && keycode <= 57)))) {
					event.preventDefault();
					return false;
				}

				if (minimumBalance1_.value.length > 10 && event.keyCode != 8) {
					event.preventDefault();
					return false;
				}

			}
			
			
			
			function validationCVV(event) {

				var minimumBalance1_ = document.getElementById(event.target.id);

				var keycode = event.which;
				if (!(event.shiftKey == false && (keycode == 8 || keycode == 37 || (keycode >= 48 && keycode <= 57)))) {
					event.preventDefault();
					return false;
				}

				if (minimumBalance1_.value.length > 2 && event.keyCode != 8) {
					event.preventDefault();
					return false;
				}

			}

			function validName(event) {

				var valiName = document.getElementById(event.target.id);
				var regex = new RegExp("^[a-zA-Z ]+$");
				var key = String.fromCharCode(!event.charCode ? event.which
						: event.charCode);

				if (valiName.value.length == 0 && event.keyCode == 32) {
					event.preventDefault();
					return false;
				}
				if (!regex.test(key)) {
					//alert("false")
					event.preventDefault();
					return false;
				}
			}

			function blockSpecialChar(e) {
				var k;
			
				document.all ? k = e.keyCode : k = e.which;
				return ((k > 64 && k < 91) || (k > 96 && k < 123) || k == 8
						|| k == 32 || (k >= 48 && k <= 57));
			}

			function validIFSC(event) {
				var valiName = document.getElementById(event.target.id);

				var regex = new RegExp("^[A-Za-z]{4}[a-zA-Z0-9]{7}$");
				var key = String.fromCharCode(!event.charCode ? event.which
						: event.charCode);
				
				var a = blockSpecialChar(event);
				if (a == false) {
					event.preventDefault();
					return false;
				}
				if (valiName.value.length > 10 && event.keyCode != 8) {
					var key = String.fromCharCode(!event.charCode ? event.which
							: event.charCode);
					if (!regex.test(key)) {
						alert("invalid ifsc code")
						event.preventDefault();
						return false;
					}
				}

			}
			
			
			
		</script>


<div class="right-container" id="right-container">
	<div class="container-fluid">
		<div class="Flexi_deposit">
			<div class="header_customer">
				<h3>
					<spring:message code="label.singleDeposit" />
				</h3>
			</div>





			<div class="header_customer" data-toggle="collapse"
				data-target="#depositDetails"
				style="border-bottom: 1px dotted #05007e; border-top: 1px dotted #05007e; padding-top: 7px; padding-bottom: 7px;">
				<h3>
					<b><spring:message code="label.depositDetails" /></b> <i
						class="fa fa-chevron-circle-down pull-right" aria-hidden="true"></i>
				</h3>
				<span id="depositOk"
					style="display: none; color: #14c514; margin-right: 25px; margin-top: -12px; float: right;"
					class="glyphicon glyphicon-ok"></span> <span id="depositCross"
					style="display: none; color: red; margin-right: 25px; margin-top: -12px; float: right;"
					class="glyphicon glyphicon-remove"></span>
			</div>
			<div class="Success_msg">
				<div class="successMsg" style="text-align: center; color: red;">
					${error}</div>
			</div>
			<div class="flexi_table" style="margin-top: 7px;">
				<form:form action="confirmFixedDeposit" id="fixedDepositForm"
					class="form-horizontal" autocomplete="off"
					commandName="fixedDepositForm" name="fixedDeposit">

					<form:hidden path="id" />
					<form:hidden path="" name="customerDetails" value="{customerDetails}" />
					<form:hidden path="customerName"
						value="${model.customer.customerName}" id="userName" />
						<form:hidden path="productConfigurationId"
						value="${model.productConfiguration.id}" id="productConfigurationId" />

<input type="hidden" value="${model.productConfiguration.isNomineeMandatory}" id="isNomineeMandatory" />
<input type="hidden" value="${model.productConfiguration.paymentModeOnMaturity}" id="paymentModeOnMaturity" />
					<form:hidden path="contactNum" value="${model.customer.contactNum}" />
					<form:hidden path="email" value="${model.customer.email}" />
					<form:hidden path="category" value="${model.customer.category}" />
					<form:hidden path="citizen" value="${model.customer.citizen}" />
					<%-- <form:hidden path="nriAccountType" value="${model.customer.nriAccountType}"/> --%>




					<div id="depositDetails" class="collapse in">
						<div class="col-md-6" style="padding-top: 7px;">

							<div id="linkedAccountDiv">
								<div class="form-group">
									<label class="col-md-4 control-label">Current/<spring:message
											code="label.savingAccountNo" /></label>
									<div class="col-md-6">
										<form:select path="accountNo" class="form-control"
											id="accountNo" onchange="onChangeAccountNo(this.value)">

											<form:option value="">Select</form:option>
											<c:forEach items="${fixedDepositForm.accountList}"
												var="account">
												<fmt:formatNumber value="${account.accountBalance}"
													pattern="#.##" var="accBal1" />
												<option
													value="${account.accountNo},${account.accountType},${accBal1}"
													<%-- <c:if test="${account.accountType =='Savings'}" > --%>selected="true"<%-- </c:if> --%>>${account.accountNo}
												</option>
											</c:forEach>
										</form:select>

									</div>

								</div>
								<script>
									function onChangeAccountNo(account) {

										if (account != "") {
											var account = account.split(",");
											document
													.getElementById('accountType').value = account[1];
											document
													.getElementById('accountBalance').value = parseFloat(
													account[2]).toLocaleString(
													"en-US");
											document
													.getElementById('accountBalance1').value = account[2];

											document
													.getElementById('accountTypeDiv').style.display = 'block';
											document
													.getElementById('accountBalanceDiv').style.display = 'block';
											//document.getElementById('fundTransferLinkedAccount').style.display = 'block';

											if (document
													.getElementById('paymentMode').value == 'Fund Transfer') {
												var accArray = $('#accountNo')
														.val().split(',');
												document
														.getElementById('linkedAccount').value = accArray[0];

												document
														.getElementById('linkedAccountBalance1').value = $(
														'#accountBalance1')
														.val();
												document
														.getElementById('linkedAccountBalance').value = parseFloat(
														$('#accountBalance1')
																.val())
														.toLocaleString("en-US");

											}

										} else {
											document
													.getElementById('accountTypeDiv').style.display = 'none';
											document
													.getElementById('accountBalanceDiv').style.display = 'none';

											if (document
													.getElementById('paymentMode').value == 'Fund Transfer') {
												document
														.getElementById('paymentMode').value = "Select";
											}
											document
													.getElementById('linkedAccountBalanceTr').style.display = 'none';
											document
													.getElementById('linkedAccountTr').style.display = 'none';

											//documentetElementById('fundTransferLinkedAccount').style.display = 'none';

										}
									}
								</script>


								<div class="form-group" id="accountTypeDiv">
									<label class="col-md-4 control-label"><spring:message
											code="label.accountType" /></label>
									<div class="col-md-6">

										<form:input path="accountType" class="form-control"
											value="Savings" id="accountType" readonly="true" />

									</div>

								</div>
								<div class="form-group" id="accountBalanceDiv">
									<label class="col-md-4 control-label"><spring:message
											code="label.accountBalance" /></label>


									<div class="col-md-6">
										<form:hidden path="accountBalance" id="accountBalance1" />
										<input class="form-control" id="accountBalance" readonly />
									</div>

								</div>


							</div>

							<c:if test="${fixedDepositForm.citizen == 'NRI'}">
								<div class="form-group" style="clear: both;" id="accountTypeDIV">
									<label class="col-md-4 control-label"><spring:message
											code="label.nriAccountType" /><span style="color: red">*</span></label>


									<div class="col-md-6">
										<form:select id="nriAccountType" path="nriAccountType"
											class="input form-control"
											onchange="accountTypeVal(this.value)">
											<!-- onchange="accountTypeVal(this.value)" -->
											<%-- <c:if test="${model.customer.nriAccountType == 'NRE'}">
											
											 document.getElementById("nriAccountType").value ="${model.customer.nriAccountType}";
											
											</c:if>  --%>



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
										code="label.selectFormat" /><span style="color: red">*</span></label>
								<div class="col-md-6">
								<input type="hidden" name="paymentType" id="paymentType1" />
									<form:select path="" class="input form-control"
										id="paymentType" onchange="limit()">
										<form:option value="">
											Select
										</form:option>
										<c:if test="${productConfiguration.productType == 'Regular Deposit' ||productConfiguration.productType== 'Tax Saving Deposit'}">
										<form:option value="One-Time">
											<spring:message code="label.oneTimePayment" />
										</form:option>
										</c:if>
										<c:if test="${productConfiguration.productType == 'Recurring Deposit'}">
										
										<form:option value="Monthly">
											<spring:message code="label.monthly" />
										</form:option>
										<form:option value="Quarterly">
											<spring:message code="label.quarterly" />
										</form:option>
										<form:option value="Half Yearly">
											<spring:message code="label.halfYearly" />
										</form:option>
										<form:option value="Annually">Yearly
								        </form:option>
										</c:if>
									</form:select>
								</div>

							</div>
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.fdAmount" /><span style="color: red">*</span></label>
								<div class="col-md-6">
									<fmt:formatNumber value="${fixedDepositForm.fdAmount}"
										pattern="#.##" var="fdAmount" />
									<form:input path="fdAmount" type="text"
										class="input form-control" id="fdAmount" value="${fdAmount}"
										onkeyup="onchangeDepositAmount(this.value)"
										placeholder="deposite Amount" />



								</div>
							</div>


							<div class="form-group" id="maturityIns">
								<label class="col-md-6 control-label" for=""
									style="margin-left: -90px;"> <spring:message
										code="label.maturityInstruction" /><span style="color: red">*</span>
								</label>


								<div class="col-md-6">
									<form:select id="maturityInstructionId"
										path="maturityInstruction" class="form-control"
										required="true">
										<form:option value="select">
											<spring:message code="label.select" />
										</form:option>

										<form:option selected="true" value="autoRenew">
											<spring:message code="label.autoRenew" />
										</form:option>
										<form:option value="repay">
											<spring:message code="label.repayPricipalAndInterest" />
										</form:option>

										<form:option value="autoRenewPrincipalAndRepayInterest">
											<spring:message
												code="label.autoRenewPrincipalAndRepayInterest" />
										</form:option>

										<form:option value="autoRenewInterestAndRepayPrincipal">
											<spring:message
												code="label.autoRenewInterestAndRepayPrincipal" />
										</form:option>

									</form:select>
								</div>

							</div>
							
							
							
							<div class="form-group" id="branch">
								<label class="col-md-6 control-label" for=""
									style="margin-left: -90px;"> <spring:message
										code="label.branchCode" /><span style="color: red">*</span>
								</label>


								<div class="col-md-6">
									<form:input type="text" id="branchCode"
										path="branchCode" class="form-control"
										required="true" readonly="true" value="${branchCode} - ${branchName}"/>
										
										
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-md-4 control-label">Area<span style="color: red">*</span></label>
								<div class="col-md-6">
									
									<form:input path="depositArea" type="text"
										class="input form-control" id="depositArea" required="true" 
										placeholder="Enter Deposit Area" />


								</div>
							</div>
							

						</div>

					</div>
					<div class="col-md-6" style="padding-top: 7px;">
						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.fdDate" /><span style="color: red">*</span></label>
							<div class="col-md-6">
								<form:input path="fdDeductDate" value="${todaysDate}"
									placeholder="Select Date" class="form-control"
									style="background: whitesmoke; cursor: pointer;"
									id="datepicker" required="true" readonly="true" />

							</div>

						</div>
<c:if test="${productConfiguration.productType == 'Recurring Deposit'}">
							<div class="form-group">
								<label class="col-md-4 control-label">Amount Deducting
									Day<span style="color: red">*</span>
								</label>
								<div class="col-md-3">
									<form:select path="deductionDay" class="form-control"
										id="deductionDay">
										<option value="">Day</option>
										<%
											for (int i = 1; i <= 30; i++) {
										%>
										<option value="<%=i%>"><%=i%></option>
										<%
											}
										%>
</form:select>
								</div>
							</div>
</c:if>

						<div class="form-group">
							<label class="col-md-4 control-label">Tenure Years<span style="color: red">*</span></label>
							<div class="col-md-6">
							<input name="totalTenureInDays" id="fdTenure" type="hidden">
							<form:hidden path="tenureInYears" id="tenureInYears"/>
							<!-- <input name="tenureInYears" id="tenureInYears" type="hidden"> -->
							<input name="tenureInMonths" id="tenureInMonths" type="hidden">
							<input name="tenureInDays" id="tenureInDays" type="hidden">
								<select class="input form-control" id="fdTenureYears">
<option value="">Select</option><option value="1">1</option>
<option value="2">2</option>
<option value="3">3</option>
<option value="4">4</option>
<option value="5">5</option>
<option value="6">6</option>
<option value="7">7</option>
<option value="8">8</option>
<option value="9">9</option>
<option value="10">10</option>

</select>
							</div>

						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">Tenure Months<span style="color: red">*</span></label>

<div class="col-md-6">
<select class="input form-control" id="fdTenureMonths">
<option value="">Select</option>
<option value="1">1</option>
<option value="2">2</option>
<option value="3">3</option>
<option value="4">4</option>
<option value="5">5</option>
<option value="6">6</option>
<option value="7">7</option>
<option value="8">8</option>
<option value="9">9</option>
<option value="10">10</option>
<option value="11">11</option>
<option value="12">12</option>
</select>
								
							</div>

							


						</div>
						<div class="form-group">
							<label class="col-md-4 control-label">Tenure Days<span style="color: red">*</span></label>

<div class="col-md-6">
								<select class="input form-control" id="fdTenureDays">
								<option value="">Select</option>
<option value="1">1</option>
<option value="2">2</option>
<option value="3">3</option>
<option value="4">4</option>
<option value="5">5</option>
<option value="6">6</option>
<option value="7">7</option>
<option value="8">8</option>
<option value="9">9</option>
<option value="10">10</option>
<option value="11">11</option>
<option value="12">12</option>
<option value="13">13</option>
<option value="14">14</option>
<option value="15">15</option>
<option value="16">16</option>
<option value="17">17</option>
<option value="18">18</option>
<option value="19">19</option>
<option value="20">20</option>
<option value="21">21</option>
<option value="22">22</option>
<option value="23">23</option>
<option value="24">24</option>
<option value="25">25</option>
<option value="26">26</option>
<option value="27">27</option>
<option value="28">28</option>
<option value="29">29</option>
<option value="30">30</option>
<option value="31">31</option>
</select>
							</div>
</div>
<div class="form-group" style="clear: both;">
							<label class="col-md-4 control-label"><spring:message
									code="label.currency" /><span style="color: red">*</span></label>

							<div class="col-md-6">
								<form:select id="currency" path="currency"
									class="input form-control">
									<form:option value="Select"></form:option>
								</form:select>

								<script>
									if ('${currency}' != "") {
										if ('${val}' == 'FCNR'
												|| '${val}' == 'PRP')
											populateCurrencyEditFCNR(
													"currency", "${currency}");
										else if ('${val}' == 'RFC')
											populateCurrencyEditRFC("currency",
													"${currency}");
										else
											populateCurrencyEdit("currency",
													"${currency}");
									} else {
										if ('${val}' == 'FCNR'
												|| '${val}' == 'PRP')
											populateCurrencyFCNR("currency");
										else if ('${val}' == 'RFC')
											populateCurrencyRFC("currency");
										else
											populateCurrency("currency");
									}
								</script>
								<span id="currencyError" style="display: none; color: red"><spring:message
										code="label.selectCurrency" /></span>

							</div>
						</div>

						<div class="form-group" id="taxSavingDepositId"
							style="display: none">
							<label class="col-md-4 control-label" style="margin-top: 7px;"><spring:message
									code="label.taxSavingDeposit" /></label>
<c:if test="${productConfiguration.productType != 'Tax Saving Deposit'}">
							<div class="col-md-2">
								<form:checkbox path="taxSavingDeposit" value="1"
									class="form-control" id="taxSavingDeposit"
									style=" width: 16px;" onclick="checkFunction()" />
							</div>
							</c:if>
							<c:if test="${productConfiguration.productType == 'Tax Saving Deposit'}">
							<div class="col-md-2">
								<form:checkbox path="taxSavingDeposit" value="1"
									class="form-control" id="taxSavingDeposit"
									style=" width: 16px;" checked="checked" onclick="checkFunction()" />
							</div>
							</c:if>
						</div>
						<input type="button" onclick="resetBasicDetails()"
							class="btn btn-warning pull-right" value="Reset"
							style="margin-top: -5px; margin-right: 9px;" />
					</div>


					<div style="clear: both;"></div>
					<div class="header_customer col-sm-12 col-md-12 col-lg-12"
						data-toggle="collapse" data-target="#nomineeDetails"
						style="border-bottom: 1px dotted #05007e; margin-bottom: 10px; padding-top: 7px; padding-bottom: 7px;">
						<h3>
							<b><spring:message code="label.nominee" /></b> <i
								class="fa fa-chevron-circle-downS pull-right" aria-hidden="true"></i>
						</h3>
						<span id="nomineeOk"
							style="display: none; color: #14c514; margin-right: 25px; margin-top: -12px; float: right;"
							class="glyphicon glyphicon-ok"></span><span id="nomineeCross"
							style="display: none; color: red; margin-right: 25px; margin-top: -12px; float: right;"
							class="glyphicon glyphicon-remove"></span>
					</div>

					<div id="nomineeDetails" class="collapse">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tbody>
								<tr>
									<td align="left" valign="top" width="50%">
										<div class="col-md-12" style="padding-top: 1px;">
											<div class="form-group">
												<label class="control-label col-sm-4" for=""><spring:message
														code="label.nomineeName" /><span style="color: red">*</span></label>
												<div class="col-sm-6">
													<form:input path="nomineeName"
														placeholder="Enter Nominee Name" id="nomineeName"
														class="input form-control"></form:input>
												</div>
											</div>
											<div class="form-group">
												<label class="control-label col-sm-4" for=""><spring:message
														code="label.nomineeAge" /><span style="color: red">*</span></label>
												<div class="col-sm-6">
													<form:input path="nomineeAge" type="text"
														placeholder="Enter Nominee Age" id="nomineeAge"
														 class="input form-control" onkeypress="validationAccount(event)"
														onkeyup="showGuardian(this.value,'gaurdianDiv','major','minor')"></form:input>
												</div>
											</div>
											<div class="form-group">
												<label class="control-label col-sm-4" for=""><spring:message
														code="label.nomineeAddress" /><span style="color: red">*</span></label>
												<div class="col-sm-6">
													<form:input path="nomineeAddress"
														placeholder="Enter Nominee Address" id="nomineeAddress"
														class="input form-control"></form:input>
												</div>
											</div>
											<div class="form-group">
												<label class="control-label col-sm-4" for=""><spring:message
														code="label.nomineeRelationShip" /><span
													style="color: red">*</span></label>
												<div class="col-sm-6">
													<form:input path="nomineeRelationShip"
														placeholder="Enter Nominee Relationship"
														id="nomineeRelationShip" class="input form-control"
														pattern="[a-zA-Z ]+"></form:input>
												</div>
											</div>

											<div class="form-group" id="nomineePanCard">
												<label class="col-md-4 control-label">Nominee Pan
												</label>
												<div class="col-sm-6">
													<form:input path="nomineePan" placeholder="Enter PAN"
														id="nomineePan" class="input form-control"
														style="text-transform:uppercase"></form:input>
													<span id="nomineePanError" class="error"></span> <span
														id="contactNum2Error" class="error" style="display: none;"><font
														color="red"><spring:message
																code="label.incorrectFormat" /></font></span>
												</div>
											</div>
											<div class="successMsg">
												<b><font color="error">${errorPan}</font></b>
											</div>
											<div class="form-group">
												<label class="col-md-4 control-label"><spring:message
														code="label.nomineeAadhar" /></label>
												<div class="col-md-6">
													<form:input path="nomineeAadhar" placeholder="Enter Aadhar"
														type="text" id="nomineeAadhar" class="input form-control"></form:input>
													<span id="contactNum2Error" class="error"
														style="display: none;"><font color="red"><spring:message
																code="label.incorrectFormat" /></font></span>
												</div>
											</div>
											<div class="successMsg">
												<b><font color="error">${errorAadhar}</font></b>
											</div>

										</div>
									</td>
									<td align="left" valign="bottom" width="50%"><div
											class="col-md-12" style="margin-top: -14px;">

											<div id="gaurdianDiv">

												<div class="form-group">
													<label class="col-md-4 control-label"></label>
													<div class="col-md-6"></div>
												</div>
												<div class="form-group">
													<label class="col-md-4 control-label"><spring:message
															code="label.guardianName" /><span style="color: red">*</span></label>
													<div class="col-md-6">
														<form:input path="guardianName" class="input form-control"
															id="guardianName" />
													</div>
												</div>
												<div class="form-group">
													<label class="col-md-4 control-label"><spring:message
															code="label.guardianAge" /><span style="color: red">*</span></label>
													<div class="col-md-6">
														<form:input path="guardianAge" type="text"
															class="input form-control" id="guardianAge" onkeypress="validationAccount(event)"/>

													</div>
												</div>
												<div class="form-group">
													<label class="col-md-4 control-label"><spring:message
															code="label.guardianAddress" /><span style="color: red">*</span></label>
													<div class="col-md-6">
														<form:input path="guardianAddress"
															class="input form-control" id="guardianAddress" />
													</div>
												</div>
												<div class="form-group">
													<label class="col-md-4 control-label"><spring:message
															code="label.guardianRelationShip" /><span
														style="color: red">*</span></label>
													<div class="col-md-6">
														<form:input path="guardianRelationShip"
															class="input form-control" id="guardianRelationShip" />

													</div>
												</div>

												<div class="form-group">
													<label class="col-md-4 control-label"><spring:message
															code="label.guardianPan" /></label>
													<div class="col-md-6">
														<form:input path="gaurdianPan" placeholder="Enter PAN"
															id="gaurdianPan" class="input form-control"
															style="text-transform:uppercase"></form:input>
														<span id="contactNum2Error" class="error"
															style="display: none;"><font color="red"><spring:message
																	code="label.incorrectFormat" /></font></span>
													</div>
												</div>

												<div class="form-group">
													<label class="col-md-4 control-label"><spring:message
															code="label.guardianAadhar" />
													</label>
													<div class="col-md-6">
														<form:input path="gaurdianAadhar"
															placeholder="Enter Aadhar" type="text"
															id="gaurdianAadhar" class="input form-control"></form:input>
														<span id="contactNum2Error" class="error"
															style="display: none;"><font color="red"><spring:message
																	code="label.incorrectFormat" /></font></span>
													</div>
												</div>

												<span id="guardianEmptyError"
													style="display: none; color: red">Please fill all
													guardian details </span> <span id="guardianAgeError"
													style="display: none; color: red"><spring:message
														code="label.guardianAge" /> should be between 18 and 100
												</span> <span id="guardianRelationShipError"
													style="display: none; color: red">Special characters
													and numbers are not allowed </span>

											</div>
											</div>
										<div style="clear: both;"></div> <input type="button"
										style="margin-top: -5px; margin-right: 9px; margin-bottom: 15px"
										onclick="resetNomineeDetails()"
										class="btn btn-warning pull-right" value="Reset" /></td>
								</tr>
							</tbody>
						</table>
						</div>
<div class="header_customer col-sm-12 col-md-12 col-lg-12"
						data-toggle="collapse" data-target="#payOffDetails"
						style="border-bottom: 1px dotted #05007e; margin-bottom: 10px; padding-top: 7px; padding-bottom: 7px;">
						<h3>
							<b></span> <spring:message code="label.payOffDetails" /> (Optional)</b>
							<i class="fa fa-chevron-circle-down pull-right"
								aria-hidden="true"></i>
						</h3>
						<span id="payoffOk"
							style="display: none; color: #14c514; margin-right: 25px; margin-top: -12px; float: right;"
							class="glyphicon glyphicon-ok"></span><span id="payoffCross"
							style="display: none; color: red; margin-right: 25px; margin-top: -12px; float: right;"
							class="glyphicon glyphicon-remove"></span>
					</div>

					<div id="payOffDetails" class="collapse">
						<div class="form-group" id="everyMonthInt">
							<label class="col-md-2 control-label" for="radio" style="margin-top: 10px;"><spring:message
									code="label.payOffTenure" /></label>
							<div class="col-sm-7">
								<label style="display: none;" id="fd0" for="radio"> <form:radiobutton
										path="payOffInterestType" style="display:none;"
										id="fdPayOffType0" name="fdPayOffType" value="One-Time"
										onclick="displayPartPercentDiv('partPercentDiv')"></form:radiobutton>One
									Time
								</label> <label id="fd1" for="radio"> <form:radiobutton
										path="payOffInterestType" id="fdPayOffType1"
										name="fdPayOffType" value="Monthly"
										onclick="displayPartPercentDiv('partPercentDiv')"></form:radiobutton>
									<spring:message code="label.fdPayOffType1" /></label> <label id="fd3"
									for="radio"> <form:radiobutton
										path="payOffInterestType" id="fdPayOffType3"
										name="fdPayOffType" value="Quarterly"
										onclick="displayPartPercentDiv('partPercentDiv')"></form:radiobutton>
									<spring:message code="label.fdPayOffType3" />
								</label> <label id="fd4" for="radio"> <form:radiobutton
										path="payOffInterestType" id="fdPayOffType4"
										name="fdPayOffType" value="Semiannual"
										onclick="displayPartPercentDiv('partPercentDiv')"></form:radiobutton>
									<spring:message code="label.fdPayOffType4" />
								</label> <label id="fd5" for="radio"> <form:radiobutton
										path="payOffInterestType" id="fdPayOffType5"
										name="fdPayOffType" value="Annual"
										onclick="displayPartPercentDiv('partPercentDiv')"></form:radiobutton>
									<spring:message code="label.fdPayOffType5" />
								</label> <label id="fd6" for="radio"> <form:radiobutton
										path="payOffInterestType" id="fdPayOffType6"
										name="fdPayOffType" value="End of Tenure"
										onclick="displayPartPercentDiv('partPercentDiv')"></form:radiobutton>
									<spring:message code="label.fdPayOffType2" />
								</label>
								</div>
						</div>
						<div class="form-group" id="oneTimeDatePayoff"
							style="display: none;">
							<label class="col-md-2 control-label"><spring:message
									code="label.oneTimePayoffInterestDate" /><span
								style="color: red">*</span> </label>
							<div class="col-md-7">
								<form:input path="payoffDate" id="oneTimePayOffDate"
									readonly="true" placeholder="Select Date"
									class="form-control datepicker-here" />

							</div>
						</div>
						<div class="form-group" id="partPercentDiv">
							<label class="control-label col-sm-2" for=""
								style="margin-top: 7px;"><spring:message
									code="label.interestType" /></label>
							<div class="col-sm-7">
								<label for="radio"><form:radiobutton
										path="interstPayType" value="PART" name="radioPartPercent"
										id="partInterest"
										onclick="displayHidePartPercent('interestAmtTr','interestPercentTr')"></form:radiobutton>
								</label>
								<spring:message code="label.interestType2" />
								<label for="radio"><form:radiobutton
										path="interstPayType" value="PERCENT" name="radioPartPercent"
										id="percentInterest"
										onclick="displayHidePartPercent('interestPercentTr','interestAmtTr')"></form:radiobutton>
								</label>Percentage
							</div>
							</div>
						<div class="form-group" id="interestPercentTr">
							<label class="control-label col-sm-2" for=""><spring:message
									code="label.enterPercentage" /><span style="color: red">*</span>
							</label>
							<div class="col-sm-7">
								<form:input path="interestPercent" type="text"
									class="input form-control" placeholder="Enter Percentage"
									id="interestPercent"
									onkeypress="return isNumberKey1(event,$(this))" />
							</div>
							<span id="interestPercentError" style="display: none; color: red">Please
								enter percent</span>

						</div>

						<div class="form-group" id="interestAmtTr">
							<label class="control-label col-sm-2" for="">Enter <spring:message
									code="label.amount" /><span style="color: red">*</span></label>
							<div class="col-sm-7">
								<form:input path="interestPayAmount" type="text"
									class="input form-control" id="interestPayAmount"
									onkeypress="return isNumberKey1(event,$(this))" />
							</div>
							</div>

						<div class="form-group" id="payoffAccountType"
							style="display: none">
							<label class="control-label col-sm-2" for=""
								style="margin-top: 10px;"><spring:message
									code="label.payOffAccount1" /></label>
							<div class="col-sm-7">
								<%-- <form:radiobutton path="fdPayOffAccount" onclick="hideAll()"
									id="depositAccountRadio" value="FD Account"></form:radiobutton>
								<spring:message code="label.fdAccount" /> --%>
								<form:radiobutton path="fdPayOffAccount"
									onclick="showDetailsSameBank()" id="savingBankRadio"
									value="Saving Account"></form:radiobutton>
								<spring:message code="label.savingAccount" />
								<form:radiobutton path="fdPayOffAccount"
									onclick="showDetailsDiffBank()" id="differentBankRadio"
									value="Other"></form:radiobutton>
								<spring:message code="label.otherBan" />

							</div>
						</div>
						<div class="form-group" id="beneficiaryNameTr"
							style="display: none;">
							<label class="control-label col-sm-2" for=""><spring:message
									code="label.benificiaryName" /> <span style="color: red">*</span>
							</label>
							<div class="col-sm-7">
								<form:input path="otherName" value="" class="input form-control"
									style="text: pointer;" id="beneficiaryName" />
							</div>
						</div>
						<div class="form-group" id="accountNoTr" style="display: none;">
							<label class="control-label col-sm-2" for=""><spring:message
									code="label.accno" /> <span style="color: red">*</span> </label>
							<div class="col-sm-7">
								<form:input path="otherAccount" type="text"
									class="input form-control" id="beneficiaryAccount" />
							</div>
						</div>

						<div class="form-group" id="transferModeTr" style="display: none;">
							<label class="control-label col-sm-2" for=""
								style="margin-top: 7px;"><spring:message
									code="label.transfer" /><span style="color: red">*</span></label>
							<div class="col-sm-7">
								<label for="radio"><form:radiobutton
										path="otherPayTransfer" id="neft" value="NEFT"></form:radiobutton></label>
								<spring:message code="label.transfer1" />
								<label for="radio"><form:radiobutton
										path="otherPayTransfer" id="imps" value="IMPS"></form:radiobutton></label>
								<spring:message code="label.transfer2" />
								<label for="radio"><form:radiobutton
										path="otherPayTransfer" id="rtgs" value="RTGS"></form:radiobutton></label>
								<spring:message code="label.transfer3" />
							</div>
						</div>
						<div class="form-group" id="bankNameTr" style="display: none;">
							<label class="control-label col-sm-2" for=""><spring:message
									code="label.bankName" /><span style="color: red">*</span></label>
							<div class="col-sm-6">
								<form:input path="otherBank" class="input form-control"
									id="beneficiaryBank" />
							</div>
						</div>
						<div class="form-group" id="ifscTr" style="display: none;">
							<label class="control-label col-sm-2" for=""><spring:message
									code="label.ifs" /><span style="color: red">*</span></label>
							<div class="col-sm-7">
								<form:input path="otherIFSC" class="input form-control"
									id="beneficiaryIfsc" />
							</div>

						</div>
						<input type="button" onclick="resetPayoffDetails()"
							style="margin-bottom: 9px; margin-right: 9px;"
							class="btn btn-warning pull-right" value="Reset" />
					</div>
					
					
					<div class="header_customer col-sm-12 col-md-12 col-lg-12"
						data-toggle="collapse" data-target="#maturityDetails"
						style="border-bottom: 1px dotted #05007e; margin-bottom: 10px; padding-top: 7px; padding-bottom: 7px;">
						<h3>
							<b></span> Funds Transfer on Deposit Maturity</b>
							<i class="fa fa-chevron-circle-down pull-right"
								aria-hidden="true"></i>
						</h3>
						<span id="maturityOk"
							style="display: none; color: #14c514; margin-right: 25px; margin-top: -12px; float: right;"
							class="glyphicon glyphicon-ok"></span><span id="maturityCross"
							style="display: none; color: red; margin-right: 25px; margin-top: -12px; float: right;"
							class="glyphicon glyphicon-remove"></span>
					</div>

					<div id="maturityDetails" class="collapse">
						<div class="form-group" id="everyMonthInt">
							<label class="col-md-2 control-label" for="radio"
								style="margin-top: 10px;">Transfer To Linked Account</label>
							<div class="col-sm-7" style="margin-top: 6px;">
							<c:if test="${! empty fixedDepositForm.accountList}">
								 <label id="fd1" for="radio"> <form:radiobutton checked="checked" 
										path="isMaturityDisbrsmntInLinkedAccount" id="inLinked"
										name="isMaturityDisbrsmntInLinkedAccount" value="1"
										onclick="inLinkedAccount()"></form:radiobutton>
									Yes</label>
									<label id="fd3" for="radio"> <form:radiobutton
										path="isMaturityDisbrsmntInLinkedAccount" id="notInLinked"
										name="isMaturityDisbrsmntInLinkedAccounts" onclick="notInLinkedAccount()" value="0"
										></form:radiobutton>
									No	</label>
									</c:if>
								<c:if test="${ empty fixedDepositForm.accountList}">
								 
									<label id="fd3" for="radio"> <form:radiobutton
										path="isMaturityDisbrsmntInLinkedAccount" id="notInLinked"
										name="isMaturityDisbrsmntInLinkedAccounts" onclick="notInLinkedAccount()" value="0"
										></form:radiobutton>
									No	</label>
									</c:if>
									</div>
						</div>
						
						

						

						<div class="form-group" id="payoffAccountTypeMr" style="display:none;">
							<label class="control-label col-sm-2" for=""
								style="margin-top: 10px;"><spring:message
									code="label.payOffAccount1" /></label>
							<div class="col-sm-7">
								
								<form:radiobutton path="isMaturityDisbrsmntInSameBank"
									onclick="showDetailsSameBankM()" id="savingBankRadioMr"
									value="1"></form:radiobutton>
								<spring:message code="label.savingAccount" />
								<form:radiobutton path="isMaturityDisbrsmntInSameBank"
									onclick="showDetailsDiffBankM()" id="differentBankRadioMr"
									value="0"></form:radiobutton>
								<spring:message code="label.otherBan" />

							</div>
						</div>
						<div class="form-group" id="beneficiaryDivMr"
							style="display: none;">
							<label class="control-label col-sm-2" for=""><spring:message
									code="label.benificiaryName" /> <span style="color: red">*</span>
							</label>
							<div class="col-sm-7">
								<form:input path="maturityDisbrsmntAccHolderName" value="" class="input form-control"
									style="text: pointer;" id="beneficiaryNameMr" />
							</div>
						</div>
						<div class="form-group" id="accountNoMr" style="display: none;">
							<label class="control-label col-sm-2" for=""><spring:message
									code="label.accno" /> <span style="color: red">*</span> </label>
							<div class="col-sm-7">
								<form:input path="maturityDisbrsmntAccNumber" type="text"
									class="input form-control" id="beneficiaryAccountMr" />
							</div>
						</div>

						<div class="form-group" id="transferModeMr" style="display: none;">
							<label class="control-label col-sm-2" for=""
								style="margin-top: 7px;"><spring:message
									code="label.transfer" /><span style="color: red">*</span></label>
							<div class="col-sm-7">
								<label for="radio"><form:radiobutton
										path="maturityDisbrsmntTransferType" id="neftMr" value="NEFT"></form:radiobutton></label>
								<spring:message code="label.transfer1" />
								<label for="radio"><form:radiobutton
										path="maturityDisbrsmntTransferType" id="impsMr" value="IMPS"></form:radiobutton></label>
								<spring:message code="label.transfer2" />
								<label for="radio"><form:radiobutton
										path="maturityDisbrsmntTransferType" id="rtgsMr" value="RTGS"></form:radiobutton></label>
								<spring:message code="label.transfer3" />
							</div>
						</div>
						<div class="form-group" id="bankNameMr" style="display: none;">
							<label class="control-label col-sm-2" for=""><spring:message
									code="label.bankName" /><span style="color: red">*</span></label>
							<div class="col-sm-6">
								<form:input path="maturityDisbrsmntBankName" class="input form-control"
									id="beneficiaryBankMr" />
							</div>
						</div>
						<div class="form-group" id="ifscMr" style="display: none;">
							<label class="control-label col-sm-2" for=""><spring:message
									code="label.ifs" /><span style="color: red">*</span></label>
							<div class="col-sm-7">
								<form:input path="maturityDisbrsmntBankIFSCCode" class="input form-control"
									id="beneficiaryIfscMr" />
							</div>

						</div>
						<input type="button" onclick="resetMaturityDetails()"
							style="margin-bottom: 9px; margin-right: 9px;"
							class="btn btn-warning pull-right" value="Reset" />
					</div>
					
					
					
					<div class="header_customer col-sm-12 col-md-12 col-lg-12"
						data-toggle="collapse" data-target="#paymentDetails"
						style="border-bottom: 1px dotted #05007e; margin-bottom: 10px; padding-top: 7px; padding-bottom: 7px;">
						<h3>
							<b><spring:message code="label.payments" /></b> <i
								class="fa fa-chevron-circle-down pull-right" aria-hidden="true"></i>
						</h3>
						<span id="paymentOk"
							style="display: none; color: #14c514; margin-right: 25px; margin-top: -12px; float: right;"
							class="glyphicon glyphicon-ok"></span><span id="paymentCross"
							style="display: none; color: red; margin-right: 25px; margin-top: -12px; float: right;"
							class="glyphicon glyphicon-remove"></span>
					</div>
					<div id="paymentDetails" class="collapse" style="float:left;width:100%;">
						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.modeOfPay" /> </label>
							<div class="col-md-6">
								<form:select id="paymentMode" path="depositForm.paymentMode"
									class="input form-control" onchange="showDetails(this)">
									<form:option value="Select">Select</form:option>
									<c:forEach items="${paymentMode}" var="mode">
												<option mode="${mode.paymentMode}"	value="${mode.id}">${mode.paymentMode}</option>
									</c:forEach>
									
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-4 control-label"><spring:message
									code="label.fdAmount" /><span style="color: red">*</span></label>
							<fmt:formatNumber value="${depositAmount}" pattern="#.##"
								var="depositAmount" />
							<div class="col-md-6">
								<form:input path="depositForm.fdPay" class="form-control"
									id="paymentAmt" readonly="true" />
							</div>
						</div>
						<div class="form-group" id="linkedAccountTr">
							<label class="col-md-4 control-label">Account Number<span
								style="color: red">*</span></label>
							<div class="col-md-6">
								<form:input path="depositForm.linkedAccountNo"
									class="form-control" id="linkedAccount" readonly="true" />
							</div>
						</div>
						<div class="form-group" id="linkedAccountBalanceTr">
							<label class="col-md-4 control-label">Account Balance<span
								style="color: red">*</span></label>

							<div class="col-md-6">
								<form:hidden path="depositForm.accountBalance"
									id="linkedAccountBalance1" />
								<input class="form-control" id="linkedAccountBalance" readonly />
							</div>
						</div>

						<div style="display: none" id="bankDetailsTr">
							<div class="form-group" id="chequeNoTr">
								<label class="col-md-4 control-label"> <span
									id="chequeNoLabel" style="display: none">Cheque Number<span
										style="color: red">*</span></span><span id="ddNoLabel"
									style="display: none">DD Number<span style="color: red">*</span></span></label>
								<div class="col-md-6">
									<form:input path="depositForm.chequeNo"
										class="input form-control" id="chequeNo" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label"> <span
									id="chequeDateLabel" style="display: none">Cheque Date<span
										style="color: red">*</span></span><span id="ddDateLabel"
									style="display: none">DD Date<span style="color: red">*</span></span></label>
								<div class="col-md-6">
									<form:input path="depositForm.chequeDate" value="${todaysDate}"
										readonly="true" class="form-control datepicker-here"
										style="background: whitesmoke; cursor: pointer;"
										id="chequeDate" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.bank" /><span style="color: red">*</span></label>
								<div class="col-md-6">
									<form:input path="depositForm.chequeBank"
										class="input form-control" id="chequeBank" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label"><spring:message
										code="label.branch" /><span style="color: red">*</span></label>
								<div class="col-md-6">
									<form:input path="depositForm.chequeBranch"
										class="input form-control" id="chequeBranch" />
								</div>
							</div>
							<span id="bankDetailsError" style="display: none; color: red">Please
								enter all the bank details</span>
								</div>
						<div style="display: none" id="cardDetailsTr">
							 <div class="form-group">
								<label class="col-md-4 control-label">Select Card</label>
								<div class="col-md-6">
									<form:select path="depositForm.cardType" placeholder="12/20" id = "cardType_"
										id="cardType" class="input form-control">
										<form:option value="">
											<spring:message code="label.selectCard" />
										</form:option>
										<form:option value="Debit Card">
											<spring:message code="label.debit" />
										</form:option>
										<form:option value="Credit Card">
											<spring:message code="label.credit" />
										</form:option>
										<form:option value="Other">
											<spring:message code="label.other" />
										</form:option>
									</form:select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label">Enter Card No</label>
								<div class="col-md-6">
									<form:input path="depositForm.cardNo"
										placeholder="xxxx-xxxx-xxxx-xxxx" id="cardNo"
										class="input form-control" filtertype="CCNo" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label">Expiry Date</label>
								<div class="col-md-2">
									<select class="input form-control" id="expiryMonth"
										onchange="changeExpiryDate()">

										<option value="">MONTH</option>
										<option value="01">01</option>
										<option value="02">02</option>
										<option value="03">03</option>
										<option value="04">04</option>
										<option value="05">05</option>
										<option value="06">06</option>
										<option value="07">07</option>
										<option value="08">08</option>
										<option value="09">09</option>
										<option value="10">10</option>
										<option value="11">11</option>
										<option value="12">12</option>

									</select>
								</div>
								<div class="col-md-2">
									<select id="expiryYear" class="input form-control"
										onchange="changeExpiryDate()">

										<option value="">YEAR</option>
										<option value="2017">2017</option>
										<option value="2018">2018</option>
										<option value="2019">2019</option>
										<option value="2020">2020</option>
										<option value="2021">2021</option>
										<option value="2022">2022</option>
										<option value="2023">2023</option>
										<option value="2024">2024</option>
										<option value="2025">2025</option>
										<option value="2026">2026</option>
										<option value="2027">2027</option>
										<option value="2028">2028</option>
										<option value="2029">2029</option>
										<option value="2030">2030</option>
										<option value="2031">2031</option>
										<option value="2032">2032</option>
										<option value="2033">2033</option>
										<option value="2034">2034</option>
										<option value="2035">2035</option>

									</select>
								</div>
								<form:hidden path="depositForm.expiryDate" id="expiryDate" />
								<input style="opacity: 0; position: absolute;"> <input
									type="password" style="opacity: 0; position: absolute;">
								<div class="col-md-2">
									<form:input path="depositForm.cvv" filtertype="text"
										placeholder="CVV" id="cvv" class="input form-control"
										style="width:50%" type="password"
										onkeypress="validationCVV(event)" />
								</div>
							</div>

							<span id="cardDetailsError" style="display: none; color: red">Please
								enter all the card details</span>
						</div>

						<div style="display: none" id="netBankingDetailsTr">


							<div class="form-group">
								<label class="col-md-4 control-label">Select Bank<span
									style="color: red">*</span></label>
								<div class="col-md-6">
									<form:select path="fdPayType" id="bankType"
										onchange="onchangeBankType()" class="input form-control">
										<form:option value="">
										Select
									</form:option>
										<form:option id="savingBankId" value="SameBank">
										Same Bank
									</form:option>
										<form:option id="differentBankId" value="DifferentBank">
										Different Bank
									</form:option>

									</form:select>
								</div>
							</div>
							<div class="form-group" id="paymentBeneficiaryNameDiv">
								<label class="control-label col-sm-4" for="">Beneficiary
									Name<span style="color: red">*</span>
								</label>

								<div class="col-sm-6">
									<form:input path="otherName1" class="input form-control"
										id="paymentBeneficiaryName" />
								</div>

							</div>


							<div class="form-group" id="paymentBeneficiaryAccountDiv">
								<label class="control-label col-sm-4" for="">Account
									Number<span style="color: red">*</span>
								</label>

								<div class="col-sm-6">
									<form:input path="otherAccount1" type="text"
										class="input form-control" id="paymentBeneficiaryAccount"
										onkeypress="validationAccount(event)" />
								</div>

							</div>

							<div class="form-group" id="transferOption">
								<label class="col-md-4 control-label">Transfer<span
									style="color: red">*</span></label>
								<div class="col-md-6">
									<form:select path="otherPayTransfer1" id="transferType"
										class="input form-control">
										<form:option value="">
										Select
									</form:option>
										<form:option value="NEFT">
										NEFT
									</form:option>
										<form:option value="IMPS">
										IMPS
									</form:option>
										<form:option value="RTGS">
										RTGS
									</form:option>

									</form:select>
								</div>
							</div>
							<div class="form-group" id="paymentBankNameTr">
								<label class="control-label col-sm-4" for="">Bank Name<span
									style="color: red">*</span></label>

								<div class="col-sm-6">
									<form:input path="otherBank1" class="input form-control"
										id="paymentBeneficiaryBank" onkeypress="validName(event)" />
								</div>

							</div>

							<div class="form-group" id="paymentBeneficiaryIfscDiv">
								<label class="control-label col-sm-4" for="">IFSC <span
									style="color: red">*</span></label>

								<div class="col-sm-6">
									<form:input path="otherIFSC1" class="input form-control"
										id="paymentBeneficiaryIfsc" onkeypress="validIFSC(event)" />
								</div>

							</div>

						</div>
						<input type="button" onclick="resetPaymentDetails()"
							class="btn btn-warning pull-right" value="Reset"
							style="margin-right: 9px; margin-bottom: 33px;" />
					</div>
					<div class="col-sm-12 col-md-12 col-lg-12">
						<c:if test="${baseURL[1] == 'common'}">
							<c:set var="back" value="applyOnlineFD?depositType=single" />
						</c:if>
						<c:if test="${baseURL[1] == 'users'}">
							<c:set var="back" value="user" />
						</c:if>
<table align="center" class="f_deposit_btn">
							<tr>
								<td><span id='validationError' style="color: red"></span></td>
							</tr>
							<tr>
								<td>
									<%-- <a href="${back}" style="margin-left: -88px;"
									class="btn btn-warning"> <spring:message code="label.back" /></a> --%>
									<input type="submit" onclick="return val();"
									class="btn btn-info" style="margin-left: 2px;" value="Submit">
								</td>
</tr>
</table>
					</div>
</form:form>
			</div>

		</div>
<style>
input[type=checkbox], input[type=radio] {
	margin: 4px 1px 0px;
	margin-top: 1px\9;
	line-height: normal;
	zoom: 1.0;
}
.form-horizontal .control-label {
	padding-top: 0;}
</style>