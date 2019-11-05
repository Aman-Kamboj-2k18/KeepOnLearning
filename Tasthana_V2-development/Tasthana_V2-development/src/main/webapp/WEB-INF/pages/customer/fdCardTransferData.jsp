<link href="<%=request.getContextPath()%>/resources/css/Validation.css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/resources/js/ccvalidate-tapan.min.js"></script>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script>
        $(document).ready(function () {
            // Initiate CC Validation
            InitiateValidation();

            // For check valid invalid details
            $("#btnValidate").click(function () {

                // This method returns true if cc details are valid else false.
                ValidateForm();

                if (ValidateForm()) {
                    alert('You have entered valid details...');
                }
                else {
                    alert('You have entered invalid details/skipped some fields...');
                }
            });
        });
        
        function val() {
    		var canSubmit = true;
    		if (document.fixedDeposit.fdAmount.value == 0.0) {
    			document.getElementById('fdAmountError').style.display = 'block';
    			canSubmit = false;
    		} else {
    			document.getElementById('fdAmountError').style.display = 'none';
    		}
    		
    		if (document.fixedDeposit.cardType.value == 'Select Card Type') {
    			document.getElementById('cardTypeError').style.display = 'block';
    			canSubmit = false;
    		} else {
    			document.getElementById('cardTypeError').style.display = 'none';
    		}
    		
    		if (document.fixedDeposit.cardNo.value == '') {
    			document.getElementById('cardNoError').style.display = 'block';
    			canSubmit = false;
    		} else {
    			document.getElementById('cardNoError').style.display = 'none';
    		}
    		
    		if (document.fixedDeposit.expiryMonth.value == 'MM') {
    			document.getElementById('expiryMonthError').style.display = 'block';
    			canSubmit = false;
    		} else {
    			document.getElementById('expiryMonthError').style.display = 'none';
    		}
    		
    		if (document.fixedDeposit.expiryYear.value == 'YY') {
    			document.getElementById('expiryYearError').style.display = 'block';
    			canSubmit = false;
    		} else {
    			document.getElementById('expiryYearError').style.display = 'none';
    		}
    		
    		if (document.fixedDeposit.cvvNo.value == 0) {
    			document.getElementById('cvvNoError').style.display = 'block';
    			canSubmit = false;
    		} else {
    			document.getElementById('cvvNoError').style.display = 'none';
    		}
    		

    		if (canSubmit == false) {
    			return false;
    		}

    	}

    </script>
     <div style="margin-top: 50px;" class="container">
        <div class="row">
        <form:form action="confirmFDCardTransferData" method="post" name="fixedDeposit" commandName="cardPaymentForm"
		onsubmit="return val();">
		        <form:hidden path="customerName" />
				<form:hidden path="contactNum" />
				<form:hidden path="email" />
				<form:hidden path="category"/>				
				<form:hidden path="customerID"/>
				<form:hidden path="accountNo"/>
				<form:hidden path="accountType"/>
				<form:hidden path="accountBalance"/>
				<form:hidden path="fdTenureDate"/>
				<form:hidden path="fdId"/>
            <div class="col-sm-5 col-xs-offset-3">
                <div class="well">
					<div class="row">
                        <div class="col-sm-12">
                        
                            <label class="col-sm-12 control-label"><spring:message code="label.cardType"/></label>
                            <div class="col-sm-6">
                               <form:select path="cardType" placeholder="12/20" id="cardType" class="form-control">
									<form:option value="Select Card Type"><spring:message code="label.selectCard"/></form:option>
									<form:option value="Debit Card"><spring:message code="label.debit"/></form:option>
									<form:option value="Credit Card"><spring:message code="label.credit"/></form:option>
									<form:option value="Other"><spring:message code="label.other"/></form:option>
								</form:select>
                            </div>
                        </div>
                    </div>
                     <div class="row">  
                            <div class="col-sm-12">
                               <div id="cardTypeError" style="display: none; color: red;">
						<spring:message code="label.selectValue" />
					</div>
					</div>
					</div>
                    <div class="row">
                        <div class="col-sm-12">
                            <label class="col-sm-12 control-label"><spring:message code="label.cardNo"/></label>
                            <div class="col-sm-12">
                                <form:input placeholder="xxxx-xxxx-xxxx-xxxx" id="1" class="form-control d" path="cardNo" filtertype="CCNo"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">  
                            <div class="col-sm-12">
                               <div id="cardNoError" style="display: none; color: red;">
						<spring:message code="label.enterCardNo" />
					</div>
					</div></div>
                    <div class="row">
                        <div class="col-sm-8">
                            <label class="col-sm-12 control-label"><spring:message code="label.expDate"/></label>
						<div class="col-sm-6">
							<form:select path="expiryMonth" placeholder="12/20" id="expiryMonth" class="form-control">
								<form:option value="MM"><spring:message code="label.mm"/></form:option>
								<form:option value="01">01</form:option>
								<form:option value="02">02</form:option>
								<form:option value="03">03</form:option>
								<form:option value="04">04</form:option>
								<form:option value="05">05</form:option>
								<form:option value="06">06</form:option>
								<form:option value="07">07</form:option>
								<form:option value="08">08</form:option>
								<form:option value="09">09</form:option>
								<form:option value="10">10</form:option>
								<form:option value="11">11</form:option>
								<form:option value="12">12</form:option>
							</form:select>
						 <div id="expiryMonthError" style="display: none; color: red;">
						<spring:message code="label.selectValue" />
					    </div>
						</div>
						
					    
						<div class="col-sm-6">
								<form:select path="expiryYear" placeholder="12/20" id="expiryYear" class="form-control">
									<form:option value="YY"><spring:message code="label.yy"/></form:option>
									<form:option value="2016">2016</form:option>
									<form:option value="2017">2017</form:option>
									<form:option value="2018">2018</form:option>
									<form:option value="2019">2019</form:option>
									<form:option value="2020">2020</form:option>
									<form:option value="2021">2021</form:option>
									<form:option value="2022">2022</form:option>
									<form:option value="2023">2023</form:option>
									<form:option value="2024">2024</form:option>
									<form:option value="2025">2025</form:option>
									<form:option value="2026">2026</form:option>
									<form:option value="2027">2027</form:option>
									<form:option value="2028">2028</form:option>
									<form:option value="2029">2029</form:option>
									<form:option value="2030">2030</form:option>
								</form:select>
								  <div id="expiryYearError" style="display: none; color: red;">
						<spring:message code="label.selectValue" />
					    </div>
                            </div>
                        </div>
                       
                             
					   
                        <div class="col-sm-4">
                            <label class="col-sm-12 control-label pull-right"><spring:message code="label.cvv"/></label>
                            <div class="col-sm-12">
                                <form:input placeholder="454" id="cvvNo" class="form-control" path="cvvNo" filtertype="Number"/>
								
                               <div id="cvvNoError" style="display: none; color: red;">
						<spring:message code="label.enterData" />
					    </div>
                            </div>
                        </div>
                      
					    
                    </div>
					<br>
					<div class="row">
                        <div class="col-sm-12">
                            <label class="col-sm-12 control-label"><spring:message code="label.fdAmount"/></label>
                            <div class="col-sm-12">
                                <form:input id="fdAmount" class="form-control d" path="fdAmount"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">  
                            <div class="col-sm-12">
                               <div id="fdAmountError" style="display: none; color: red;">
						<spring:message code="label.enterData" />
					</div>
                            </div>
                        
                    </div>
                   
                   
                    <div class="row">
                        <div class="col-sm-12">
                            <table align="center" class="f_deposit_btn">
				<tr>
					<td><input type="submit" class="btn btn-info " onclick="showDialog(); return false;" value="<spring:message code="label.confirm"/>"></td>
					<td><a href="cardTransfer" class="btn btn-info"><spring:message code="label.back" /></a></td>
				</tr>

			</table>
                        </div>
                    </div>
                </div>
            </div>
            </form:form>
        </div>
    </div>