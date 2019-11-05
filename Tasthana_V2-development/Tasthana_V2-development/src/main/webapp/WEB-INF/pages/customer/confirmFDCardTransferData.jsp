<link href="<%=request.getContextPath()%>/resources/css/Validation.css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/resources/js/ccvalidate-tapan.min.js"></script>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

     <div style="margin-top: 50px;" class="container">
        <div class="row">
        <form:form action="saveFDCardTransferData" method="post" name="fixedDeposit" commandName="cardPaymentForm"
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
                               <form:input path="cardType" placeholder="12/20" id="cardType" class="form-control" readonly="true"/>
								
                            </div>
                        </div>
                    </div>
                    
                    <div class="row">
                        <div class="col-sm-12">
                            <label class="col-sm-12 control-label"><spring:message code="label.cardNo"/></label>
                            <div class="col-sm-12">
                                <form:input placeholder="4587-7834-6735-1930" id="1" class="form-control d" path="cardNo" filtertype="CCNo" readonly="true"/>
                            </div>
                        </div>
                    </div>
                    
                    <div class="row">
                        <div class="col-sm-8">
                            <label class="col-sm-12 control-label"><spring:message code="label.expDate"/></label>
						<div class="col-sm-6">
							<form:input path="expiryMonth" placeholder="12/20" id="expiryMonth" class="form-control" readonly="true"/>
						
						</div>
						
					    
						<div class="col-sm-6">
								<form:input path="expiryYear" placeholder="12/20" id="expiryYear" class="form-control" readonly="true"/>
                            </div>
                        </div>
                       
                             
					   
                        <div class="col-sm-4">
                            <label class="col-sm-12 control-label pull-right"><spring:message code="label.cvv"/></label>
                            <div class="col-sm-12">
                                <form:input placeholder="454" id="cvvNo" class="form-control" path="cvvNo" filtertype="Number" readonly="true"/>
								
                            </div>
                        </div>
                      
					    
                    </div>
					<br>
					<div class="row">
                        <div class="col-sm-12">
                            <label class="col-sm-12 control-label"><spring:message code="label.fdAmount"/></label>
                            <div class="col-sm-12">
                                <form:input id="fdAmount" class="form-control d" path="fdAmount" readonly="true"/>
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
					<td><input type="submit" class="btn btn-info " onclick="showDialog(); return false;" value="<spring:message code="label.payNow"/>"></td>
					<td><a href="getFDCardTransferData" class="btn btn-info"><spring:message code="label.back" /></a></td>
				</tr>

			</table>
                        </div>
                    </div>
                </div>
            </div>
            </form:form>
        </div>
    </div>