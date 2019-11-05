<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- To fetch the request url -->
<c:set var="req" value="${requestScope['javax.servlet.forward.request_uri']}"/>      
<c:set var="baseURL" value="${fn:split(req,'/')}" />

<script>
$(function () {
    $("input[name='fdPayOffAccount']").click(function () {
    	if ($("#fdPayOffAccount6").is(":Checked")) {
			$("#dvPassport").show();
			$("#sameBankDetails").hide();
		} else if ($("#fdPayOffAccount22").is(":Checked")) {
			$("#sameBankDetails").show();
			$("#dvPassport").hide();
		} else {
			$("#dvPassport").hide();
			$("#sameBankDetails").hide();
		}
    });
    $("input[name='fdPayOffAccount1']").click(function() {
		if ($("#fdPayOffAccount3").is(":Checked")) {
			$("#dvPassport1").show();
			$("#part1Details").hide();
		}else if($("#fdPayOffAccount24").is(":Checked")) {
			
			$("#part1Details").show();
			$("#dvPassport1").hide();
		} 
		else {
			$("#dvPassport1").hide();
			$("#part1Details").hide();
		}
	});
	$("input[name='fdPayOffAccount2']").click(function() {
		if ($("#fdPayOffAccount9").is(":Checked")) {
			$("#dvPassport2").show();
			$("#part2Details").hide();
		}else if($("#fdPayOffAccount23").is(":Checked")) {
			
			$("#part2Details").show();
			$("#dvPassport2").hide();
		} 
		else {
			$("#dvPassport2").hide();
			$("#part2Details").hide();
		}
	});
    
    $("input[name='fdPayOffType']").click(function () {
        if ($("#fdPayOffType2").is(":Checked")) {
        	$("#divInterest").hide();
    		 $("#divInterest1").hide();
    		 $("#dvPassport2").hide();
    		 $("#dvPassport1").hide();
    		 $("#wholeInt").hide();
    		 $("#partInt").hide();
    		 $("#percentage").hide();
        } else {
        	
    		 $("#wholeInt").show();
    		 $("#partInt").show();
    		 $("#percentage").show();
        }
    });
    
    
    $("input[name='interstPayType']").click(function () {
    	
        if ($("#interstPayType1").is(":Checked") ) {
        	var canSubmit = true;
        	if(document.fixedDeposit.fdPayOffType.value == 'EndOfTenure')
        		{
        		document.getElementById('interstPayType3Error').style.display = 'block';
        		canSubmit = false;
        		 $("#divInterest").hide();
        		 $("#divInterest1").hide();
        		 $("#dvPassport2").hide();
        		 
        		}
        	else
        		{
        		$("#divInterest").show();
        		$("#divInterest1").show();
        		$("#interestPercent").hide();
        		
        		document.getElementById('interstPayType3Error').style.display = 'none';
        		}
        	if (canSubmit == false) {
    			return false;
    		}
        }else if($("#interstPayType2").is(":Checked")){
        	var canSubmit = true;
        	if(document.fixedDeposit.fdPayOffType.value == 'EndOfTenure')
        		{
        		document.getElementById('interstPayType3Error').style.display = 'block';
        		canSubmit = false;
        		 $("#divInterest").hide();
        		 $("#divInterest1").hide();
        		 $("#dvPassport2").hide();
        		 
        		}
        	else
        		{
        		$("#divInterest").show();
        		$("#divInterest1").show();
        		$("#interestPercent").show();
        		
        		document.getElementById('interstPayType3Error').style.display = 'none';
        		}
        	if (canSubmit == false) {
    			return false;
    		}
        }else {
            $("#divInterest").hide();
            $("#divInterest1").hide();
            $("#dvPassport2").hide();
            document.getElementById('interstPayType3Error').style.display = 'none';
        }
    });
});


	function val() {
		
		
	
		var canSubmit = true;
		/* if (document.fixedDeposit.fdPayOffType.value == '') {
			document.getElementById('fdPayOffTypeError').style.display = 'block';
			canSubmit = false;
		} else {
			document.getElementById('fdPayOffTypeError').style.display = 'none';
		} */
		if (document.fixedDeposit.fdTenureType.value  == 'Day') {
			if (document.fixedDeposit.fdTenure.value  > 60) {
				if (document.fixedDeposit.fdPayOffType.value == '') {
					document.getElementById('fdPayOffTypeError').style.display = 'block';
					
					canSubmit = false;
				} else {
					document.getElementById('fdPayOffTypeError').style.display = 'none';
					
				}
				
			} 
			
		} else if (document.fixedDeposit.fdTenureType.value  == 'Month') {
			if (document.fixedDeposit.fdTenure.value  >= 2) {
				if (document.fixedDeposit.fdPayOffType.value == '') {
					document.getElementById('fdPayOffTypeError').style.display = 'block';
					
					canSubmit = false;
				} else {
					document.getElementById('fdPayOffTypeError').style.display = 'none';
					
				}
			} 
			
			
		} else {
			if (document.fixedDeposit.interstPayType.value == '') {
				document.getElementById('interstPayTypeError').style.display = 'block';
				
				canSubmit = false;
			} else {
				document.getElementById('interstPayTypeError').style.display = 'none';
				
			}
			
			if (document.fixedDeposit.fdPayOffType.value == '') {
				document.getElementById('fdPayOffTypeError').style.display = 'block';
				
				canSubmit = false;
			} else {
				document.getElementById('fdPayOffTypeError').style.display = 'none';
				
			}
		}
		if (document.fixedDeposit.fdPayOffAccount.value == '') {
			document.getElementById('fdPayOffAccountError').style.display = 'block';
			canSubmit = false;
		} else {
			document.getElementById('fdPayOffAccountError').style.display = 'none';
		}
		
		/* if (document.fixedDeposit.interstPayType.value == '') {
			document.getElementById('interstPayTypeError').style.display = 'block';
			document.getElementById('interstPayType3Error').style.display = 'none';
			canSubmit = false;
		} else {
			document.getElementById('interstPayTypeError').style.display = 'none';
			document.getElementById('interstPayType3Error').style.display = 'none';
		} */
				
		if (document.fixedDeposit.interstPayType.value == 'PART' || document.fixedDeposit.interstPayType.value == 'PERCENT') {
			
			 if(document.fixedDeposit.interestPayAmount.value == '')
				{
			document.getElementById('interstPayType1Error').style.display = 'block';
			document.getElementById('interstPayType2Error').style.display = 'none';
			document.getElementById('fdPayOffAccount1Error').style.display = 'none';
			canSubmit = false;
				}
			else if(parseFloat(document.fixedDeposit.interestPayAmount.value)+parseFloat(document.fixedDeposit.interestPayAmount1.value)> parseFloat(document.fixedDeposit.estimateInterest.value))
				{
				document.getElementById('interstPayType2Error').style.display = 'block';
				document.getElementById('interstPayType1Error').style.display = 'none';
				document.getElementById('fdPayOffAccount1Error').style.display = 'none';
				canSubmit = false;
				}
			
			else if (document.fixedDeposit.fdPayOffAccount1.value == '') {
					document.getElementById('fdPayOffAccount1Error').style.display = 'block';
					document.getElementById('interstPayType2Error').style.display = 'none';	
					document.getElementById('interstPayType1Error').style.display = 'none';
					canSubmit = false;
				} 
			 else
		      {
				document.getElementById('interstPayType2Error').style.display = 'none';	
				document.getElementById('interstPayType1Error').style.display = 'none';
				document.getElementById('fdPayOffAccount1Error').style.display = 'none';
		      }
			
		} else {
			document.getElementById('interstPayTypeError').style.display = 'none';
			document.getElementById('interstPayType2Error').style.display = 'none';	
			document.getElementById('interstPayType1Error').style.display = 'none';
		}

		if (document.fixedDeposit.fdPayOffAccount.value == 'Other') {
			if (document.fixedDeposit.otherPayTransfer.value == '') {
				document.getElementById('otherPayTransferError').style.display = 'block';
				canSubmit = false;
			} else {
				document.getElementById('otherPayTransferError').style.display = 'none';
			}
			if (document.fixedDeposit.otherAccount.value == '') {
				document.getElementById('otherAccountError').style.display = 'block';
				canSubmit = false;
			} else {
				document.getElementById('otherAccountError').style.display = 'none';
			}
			if (document.fixedDeposit.otherIFSC.value == '') {
				document.getElementById('otherIFSCError').style.display = 'block';
				canSubmit = false;
			} else {
				document.getElementById('otherIFSCError').style.display = 'none';
			}
		}

		if (canSubmit == false) {
			return false;
		}
		
if (document.fixedDeposit.period.value  > 2) {
			
			if (confirm("You will get penalty for making changes more than 2 times. Do you want to continue!") == true) {
		        return true;
		    } else {
		       return false;
		    }
		}
		

	}
	
	
	
	$( document ).ready(function(){
		
		
	var fdTenureType = "${fixedDepositForm.fdTenureType}";
		var fdTenure = "${fixedDepositForm.fdTenure}";
		if (fdTenureType == 'Day') {
			if (fdTenure > 60) {
				$("#everyMonthInt").show();
				$("#endOfTenureInt").show();
				
			} else {
				$("#everyMonthInt").hide();
				$("#endOfTenureInt").hide();
			}
		} else if (fdTenureType == 'Month') {
			if (fdTenure >= 2) {
				$("#everyMonthInt").show();
				$("#endOfTenureInt").show();
			} else {
				$("#everyMonthInt").hide();
				$("#endOfTenureInt").hide();
			}
		} else {
			$("#everyMonthInt").show();
			$("#endOfTenureInt").show();
		}

	});
</script>

<div class="Flexi_deposit">
	
	<div class="header_customer">
		<h3 align="center">
			<spring:message code="label.payOffDetails" />
		</h3>
	</div>
	<div class="col-sm-12 col-md-12 col-lg-12">
		<div class="errorMsg">${error}</div>
	</div>
	<div class="flexi_table">

	<form:form action="makeChange" method="post" name="fixedDeposit" commandName="fixedDepositForm"
		onsubmit="return val();">
		<table align="center" width="500">
		 <tr>
				
				<form:hidden path="paymentType"/>
				<form:hidden path="fdAmount"/>
				<form:hidden path="fdDueDate"/>
				<form:hidden path="fdTenureType"/>
				<form:hidden path="fdTenure"/>
				<form:hidden path="fdTenureDate"/>
				<form:hidden path="fdID"/>
				<form:hidden path="category"/>
				<form:hidden path="fdPayType"/>
				<form:hidden path="fdPay"/>
				<form:hidden path="period"/>
				<form:hidden path="fdChangeable"/>
				<form:hidden path="fdFixed"/>
				
				
			</tr>
			<tr>
				<td style="color: #17A008;"><label><spring:message
							code="label.interestTotalAmt" /></label></td>
				<td ><form:input path="estimateInterest" value="" class="myform-control" 
						id="estimateInterest" readonly="true"/>
						
						</td>
			</tr>			
			<tr id="everyMonthInt">
				<td><label><spring:message code="label.payOffTenure" /><span style="color: red">*</span></label></td>
				<td style="font-weight: 700;color: #525252; font-family: monospace;width: 306px;">
				<label for="radio">
				<form:radiobutton path="fdPayOffType" id="fdPayOffType" value="EveryMonth" ></form:radiobutton>
				</label><spring:message code="label.fdPayOffType1" /></td>
				<td style="font-weight: 700;color: #525252; font-family: monospace;width: 306px;">
				<label for="radio">
				<form:radiobutton path="fdPayOffType" id="fdPayOffType3" value="Quarterly" ></form:radiobutton>
				</label><spring:message code="label.fdPayOffType3" /></td>
				<td style="font-weight: 700;color: #525252; font-family: monospace;width: 306px;">
				<label for="radio">
				<form:radiobutton path="fdPayOffType" id="fdPayOffType4" value="Semiannual" ></form:radiobutton>
				</label><spring:message code="label.fdPayOffType4" /></td>
				<td style="font-weight: 700;color: #525252; font-family: monospace;width: 306px;">
				<label for="radio">
				<form:radiobutton path="fdPayOffType" id="fdPayOffType5" value="Annual" ></form:radiobutton>
				</label><spring:message code="label.fdPayOffType5" /></td>
			</tr>
			<tr id="endOfTenureInt">
				<td>&nbsp;</td>
				<td><label for="radio"><form:radiobutton path="fdPayOffType" id="fdPayOffType2"  value="EndOfTenure"></form:radiobutton></label><spring:message code="label.fdPayOffType2" />
									
			</tr>
			<tr>
				<td></td>
					<td id="fdPayOffTypeError" style="display: none; color: red;">
						<spring:message code="label.selectValue" />
					</td>
			</tr>
			
			
			<%-- <tr id="wholeInt" style="display: none;">
				<td><label><spring:message code="label.interestType" /><span style="color: red">*</span></label></td>
				<td style="font-weight: 700;color: #525252; font-family: monospace;width: 306px;"><label for="radio"><form:radiobutton path="interstPayType" id="interstPayType" value="WHOLE" ></form:radiobutton></label><spring:message code="label.interestType1" /></td>
			</tr> --%>
			<tr id="partInt" style="display: none;">
				<td>&nbsp;</td>
				<td><label for="radio"><form:radiobutton path="interstPayType" id="interstPayType1"  value="PART"></form:radiobutton></label><spring:message code="label.interestType2" />
									
			</tr>
			<tr id="percentage" style="display: none;">
				<td>&nbsp;</td>
				<td><label for="radio"><form:radiobutton path="interstPayType" id="interstPayType2"  value="PERCENT"></form:radiobutton></label>Percentage
				<form:input path="interestPercent" value="" class="myform-control" placeholder="Enter Percentage"
						id="interestPercent" style="display: none;" onblur="populateBalance()"/>
						</td>					
			</tr>
			</table>
			<table align="center" width="500" id="divInterest" style="display: none;margin-left: 439px;">
			<tr> <!-- id="divInterest" style="display: none;"> -->
				<td><label><spring:message code="label.amount" /><span style="color: red">*</span></label></td>
				<td><form:input path="interestPayAmount" id="interestPayAmount"  value="" onblur="populateBalance()"/>
									
			</tr>
			<tr>
				<td></td>
				  <td id="interstPayTypeError" style="display: none; color: red;">
						<spring:message code="label.selectValue" />
					</td>
					 <td id="interstPayType3Error" style="display: none; color: red;">
						<spring:message code="label.cannotSelect" />
					</td>
					</tr>
					<tr>
				<td></td>
					<td id="interstPayType1Error" style="display: none; color: red;">
						<spring:message code="label.enterData" />
					</td>
					<td id="interstPayType2Error" style="display: none; color: red;">
						<spring:message code="label.interestLess" />
					</td>
			</tr>
			
			<tr>
					<td><label><spring:message code="label.payOffAccount1" /><span style="color: red">*</span></label></td>
					<td style="font-weight: 700;color: #525252; font-family: monospace;width: 306px;"><label for="radio"><form:radiobutton path="fdPayOffAccount1" id="fdPayOffAccount1" value="FD Account" ></form:radiobutton></label><spring:message code="label.fdAccount" /></td>
				</tr>
				<tr><td>&nbsp;</td>
					<td><label for="radio"><form:radiobutton path="fdPayOffAccount1" id="fdPayOffAccount24"  value="Saving Account"></form:radiobutton></label><spring:message code="label.savingAccount" /></td>
				</tr>
					
				<tr><td>&nbsp;</td>
					<td><label for="radio"><form:radiobutton path="fdPayOffAccount1" id="fdPayOffAccount3"  value="Other"></form:radiobutton></label><spring:message code="label.otherBan" /></td>
			
				</tr>
			<tr>
				<td></td>
					<td id="fdPayOffAccount1Error" style="display: none; color: red;">
						<spring:message code="label.selectValue" />
					</td>
			</tr>
			<table align="center" width="500" id="part1Details" style="display: none;margin-left: 439px;">
			<tr>
				<td style="color: #17A008;"><label><spring:message code="label.transfer" /><span style="color: red">*</span></label></td>
				<td style="font-weight: 700;color: #525252; font-family: monospace;width: 306px;"><label for="radio"><form:radiobutton path="" id="" value="NEFT" ></form:radiobutton></label><spring:message code="label.transfer1" />
					<label for="radio"><form:radiobutton path="" id=""  value="IMPS"></form:radiobutton></label><spring:message code="label.transfer2" />
					<label for="radio"><form:radiobutton path="" id=""  value="RTGS"></form:radiobutton></label><spring:message code="label.transfer3" />
				</td>						
			</tr>
			<tr>
			<td style="color: #17A008;"><label>Beneficiary<span style="color: red">*</span></label></td>
				<td ><form:input path="" value="" placeholder="Enter Name" class="myform-control" style="background: whitesmoke; text: pointer;"
						id="otherName"/>
						
						</td>
			</tr>
			<tr>
			<td style="color: #17A008;"><label>Account Number<span style="color: red">*</span></label></td>
				<td ><form:input path="" value="" placeholder="Enter Account No" class="myform-control" style="background: whitesmoke; text: pointer;"
						id="otherBank"/>
						
						</td>
			</tr>
			
			</table>
			</table>
			
			<table align="center" width="500" id="dvPassport1" style="display: none;margin-left: 439px;">
			<tr>
				<td style="color: #17A008;"><label><spring:message code="label.transfer" /><span style="color: red">*</span></label></td>
				<td style="font-weight: 700;color: #525252; font-family: monospace;width: 306px;"><label for="radio"><form:radiobutton path="otherPayTransfer1" id="otherPayTransfer1" value="NEFT" ></form:radiobutton></label><spring:message code="label.transfer1" />
					<label for="radio"><form:radiobutton path="otherPayTransfer1" id="otherPayTransfer1"  value="IMPS"></form:radiobutton></label><spring:message code="label.transfer2" />
					<label for="radio"><form:radiobutton path="otherPayTransfer1" id="otherPayTransfer1"  value="RTGS"></form:radiobutton></label><spring:message code="label.transfer3" />
				</td>						
			</tr>
			<tr>
			<td style="color: #17A008;"><label><spring:message
							code="label.name" /><span style="color: red">*</span></label></td>
				<td ><form:input path="otherName1" value="" placeholder="Enter Name" class="myform-control" style="background: whitesmoke; text: pointer;"
						id="otherName1"/>
						
						</td>
			</tr>
			<tr>
			<td style="color: #17A008;"><label><spring:message
							code="label.bank" /><span style="color: red">*</span></label></td>
				<td ><form:input path="otherBank1" value="" placeholder="Enter Bank Name" class="myform-control" style="background: whitesmoke; text: pointer;"
						id="otherBank1"/>
						
						</td>
			</tr>
			<tr>
			<td style="color: #17A008;"><label><spring:message
							code="label.branch" /><span style="color: red">*</span></label></td>
				<td ><form:input path="otherBranch1" value="" placeholder="Enter Branch Name" class="myform-control" style="background: whitesmoke; text: pointer;"
						id="otherBranch1"/>
						
						</td>
			</tr>
			<tr>
			<td style="color: #17A008;"><label><spring:message
							code="label.accountNo" /><span style="color: red">*</span></label></td>
				<td ><form:input path="otherAccount1" value="" placeholder="Enter AccountNo" class="myform-control" style="background: whitesmoke; text: pointer;"
						id="otherAccount1"/>
						
						</td>
			</tr>
			<tr>
			<td style="color: #17A008;"><label><spring:message
							code="label.ifsc" /><span style="color: red">*</span></label></td>
				<td ><form:input path="otherIFSC1" value="" placeholder="Enter IFSC Code" class="myform-control" style="background: whitesmoke; text: pointer;"
						id="otherIFS1C"/>
						
						</td>
			</tr>
			<tr>
				<td></td>
					<td id="otherPayTransfer1Error" style="display: none; color: red;">
						<spring:message code="label.selectValue" />
					</td>
			</tr>		
			<tr>
				<td></td>
					<td id="otherIFSC1Error" style="display: none; color: red;">
						<spring:message code="label.enterData" />
					</td>
			</tr>
			<tr>
				<td></td>
					<td id="otherAccount1Error" style="display: none; color: red;">
						<spring:message code="label.enterData" />
					</td>
			</tr>
		</table>
		
		
		
		
			<table align="center" width="500" id="divInterest1" style="display: none;margin-left: 439px;">
			<tr> <!-- id="divInterest" style="display: none"> -->
				<td><label><spring:message code="label.amount" /></label></td>
				<td><form:input path="interestPayAmount1" id="interestPayAmount1"  value="" />
									
			</tr>
			
			<tr>
					<td><label><spring:message code="label.payOffAccount1" /></label></td>
					<td style="font-weight: 700;color: #525252; font-family: monospace;width: 306px;"><label for="radio"><form:radiobutton path="fdPayOffAccount2" id="fdPayOffAccount1" value="FD Account" ></form:radiobutton></label><spring:message code="label.fdAccount" /></td>
				</tr>
				<tr><td>&nbsp;</td>
					<td><label for="radio"><form:radiobutton path="fdPayOffAccount2" id="fdPayOffAccount23"  value="Saving Account"></form:radiobutton></label><spring:message code="label.savingAccount" /></td>
				</tr>
					
				<tr><td>&nbsp;</td>
					<td><label for="radio"><form:radiobutton path="fdPayOffAccount2" id="fdPayOffAccount9"  value="Other"></form:radiobutton></label><spring:message code="label.otherBan" /></td>
			
				</tr>
			
			</table>
			<table align="center" width="500" id="part2Details" style="display: none;margin-left: 439px;">
			<tr>
				<td style="color: #17A008;"><label><spring:message code="label.transfer" /><span style="color: red">*</span></label></td>
				<td style="font-weight: 700;color: #525252; font-family: monospace;width: 306px;"><label for="radio"><form:radiobutton path="" id="" value="NEFT" ></form:radiobutton></label><spring:message code="label.transfer1" />
					<label for="radio"><form:radiobutton path="" id=""  value="IMPS"></form:radiobutton></label><spring:message code="label.transfer2" />
					<label for="radio"><form:radiobutton path="" id=""  value="RTGS"></form:radiobutton></label><spring:message code="label.transfer3" />
				</td>						
			</tr>
			<tr>
			<td style="color: #17A008;"><label>Beneficiary<span style="color: red">*</span></label></td>
				<td ><form:input path="" value="" placeholder="Enter Name" class="myform-control" style="background: whitesmoke; text: pointer;"
						id="otherName"/>
						
						</td>
			</tr>
			<tr>
			<td style="color: #17A008;"><label>Account Number<span style="color: red">*</span></label></td>
				<td ><form:input path="" value="" placeholder="Enter Account No" class="myform-control" style="background: whitesmoke; text: pointer;"
						id="otherBank"/>
						
						</td>
			</tr>
			
			</table>
			<table align="center" width="500" id="dvPassport2" style="display: none;margin-left: 439px;">
			<tr>
				<td style="color: #17A008;"><label><spring:message code="label.transfer" /></label></td>
				<td style="font-weight: 700;color: #525252; font-family: monospace;width: 306px;"><label for="radio"><form:radiobutton path="otherPayTransfer2" id="otherPayTransfer2" value="NEFT" ></form:radiobutton></label><spring:message code="label.transfer1" />
					<label for="radio"><form:radiobutton path="otherPayTransfer2" id="otherPayTransfer2"  value="IMPS"></form:radiobutton></label><spring:message code="label.transfer2" />
					<label for="radio"><form:radiobutton path="otherPayTransfer2" id="otherPayTransfer2"  value="RTGS"></form:radiobutton></label><spring:message code="label.transfer3" />
				</td>						
			</tr>
			<tr>
			<td style="color: #17A008;"><label><spring:message
							code="label.name" /><span style="color: red">*</span></label></td>
				<td ><form:input path="otherName2" value="" placeholder="Enter Name" class="myform-control" style="background: whitesmoke; text: pointer;"
						id="otherName2"/>
						
						</td>
			</tr>
			<tr>
			<td style="color: #17A008;"><label><spring:message
							code="label.bank" /><span style="color: red">*</span></label></td>
				<td ><form:input path="otherBank2" value="" placeholder="Enter Bank Name" class="myform-control" style="background: whitesmoke; text: pointer;"
						id="otherBank2"/>
						
						</td>
			</tr>
			<tr>
			<td style="color: #17A008;"><label><spring:message
							code="label.branch" /><span style="color: red">*</span></label></td>
				<td ><form:input path="otherBranch2" value="" placeholder="Enter Branch Name" class="myform-control" style="background: whitesmoke; text: pointer;"
						id="otherBranch2"/>
						
						</td>
			</tr>
			<tr>
			<td style="color: #17A008;"><label><spring:message
							code="label.accountNo" /><span style="color: red">*</span></label></td>
				<td ><form:input path="otherAccount2" value="" placeholder="Enter AccountNo" class="myform-control" style="background: whitesmoke; text: pointer;"
						id="otherAccount2"/>
						
						</td>
			</tr>
			<tr>
			<td style="color: #17A008;"><label><spring:message
							code="label.ifsc" /><span style="color: red">*</span></label></td>
				<td ><form:input path="otherIFSC2" value="" placeholder="Enter IFSC Code" class="myform-control" style="background: whitesmoke; text: pointer;"
						id="otherIFSC2"/>
						
						</td>
			</tr>
			<tr>
				<td></td>
					<td id="otherPayTransfer2Error" style="display: none; color: red;">
						<spring:message code="label.selectValue" />
					</td>
			</tr>		
			<tr>
				<td></td>
					<td id="otherIFSC2Error" style="display: none; color: red;">
						<spring:message code="label.enterData" />
					</td>
			</tr>
			<tr>
				<td></td>
					<td id="otherAccount2Error" style="display: none; color: red;">
						<spring:message code="label.enterData" />
					</td>
			</tr>
		</table>
		
			
			<table align="center" width="500">
           <tr><td>&nbsp;</td><td></td></tr>
				<tr>
					<td><label><spring:message code="label.payOffAccount" /><span style="color: red">*</span></label></td>
					<td style="font-weight: 700;color: #525252; font-family: monospace;width: 306px;"><label for="radio"><form:radiobutton path="fdPayOffAccount" id="fdPayOffAccount1" value="FD Account" ></form:radiobutton></label><spring:message code="label.fdAccount" /></td>
				</tr>
				<tr><td>&nbsp;</td>
					<td><label for="radio"><form:radiobutton path="fdPayOffAccount" id="fdPayOffAccount22"  value="Saving Account"></form:radiobutton></label>Same Bank</td>
				</tr>
					
				<tr><td>&nbsp;</td>
					<td><label for="radio"><form:radiobutton path="fdPayOffAccount" id="fdPayOffAccount6"  value="Other"></form:radiobutton></label>Different Bank</td>
			
				</tr>
			<tr>
				<td></td>
					<td id="fdPayOffAccountError" style="display: none; color: red;">
						<spring:message code="label.selectValue" />
					</td>
			</tr>
			</table>
			
			<table align="center" width="500" id="sameBankDetails" style="display: none">
			<tr>
				<td style="color: #17A008;"><label><spring:message code="label.transfer" /><span style="color: red">*</span></label></td>
				<td style="font-weight: 700;color: #525252; font-family: monospace;width: 306px;"><label for="radio"><form:radiobutton path="" id="" value="NEFT" ></form:radiobutton></label><spring:message code="label.transfer1" />
					<label for="radio"><form:radiobutton path="" id=""  value="IMPS"></form:radiobutton></label><spring:message code="label.transfer2" />
					<label for="radio"><form:radiobutton path="" id=""  value="RTGS"></form:radiobutton></label><spring:message code="label.transfer3" />
				</td>						
			</tr>
			<tr>
			<td style="color: #17A008;"><label>Beneficiary<span style="color: red">*</span></label></td>
				<td ><form:input path="" value="" placeholder="Enter Name" class="myform-control" style="background: whitesmoke; text: pointer;"
						id="otherName"/>
						
						</td>
			</tr>
			<tr>
			<td style="color: #17A008;"><label>Account Number<span style="color: red">*</span></label></td>
				<td ><form:input path="" value="" placeholder="Enter Account No" class="myform-control" style="background: whitesmoke; text: pointer;"
						id="otherBank"/>
						
						</td>
			</tr>
			</table>
			
			<table align="center" width="500" id="dvPassport" style="display: none">
			<tr>
				<td style="color: #17A008;"><label><spring:message code="label.transfer" /><span style="color: red">*</span></label></td>
				<td style="font-weight: 700;color: #525252; font-family: monospace;width: 306px;"><label for="radio"><form:radiobutton path="otherPayTransfer" id="otherPayTransfer" value="NEFT" ></form:radiobutton></label><spring:message code="label.transfer1" />
					<label for="radio"><form:radiobutton path="otherPayTransfer" id="otherPayTransfer"  value="IMPS"></form:radiobutton></label><spring:message code="label.transfer2" />
					<label for="radio"><form:radiobutton path="otherPayTransfer" id="otherPayTransfer"  value="RTGS"></form:radiobutton></label><spring:message code="label.transfer3" />
				</td>						
			</tr>
			<tr>
			<td style="color: #17A008;"><label><spring:message
							code="label.name" /><span style="color: red">*</span></label></td>
				<td ><form:input path="otherName" value="" placeholder="Enter Name" class="myform-control" style="background: whitesmoke; text: pointer;"
						id="otherName"/>
						
						</td>
			</tr>
			<tr>
			<td style="color: #17A008;"><label><spring:message
							code="label.bank" /><span style="color: red">*</span></label></td>
				<td ><form:input path="otherBank" value="" placeholder="Enter Bank Name" class="myform-control" style="background: whitesmoke; text: pointer;"
						id="otherBank"/>
						
						</td>
			</tr>
			<tr>
			<td style="color: #17A008;"><label><spring:message
							code="label.branch" /><span style="color: red">*</span></label></td>
				<td ><form:input path="otherBranch" value="" placeholder="Enter Branch Name" class="myform-control" style="background: whitesmoke; text: pointer;"
						id="otherBranch"/>
						
						</td>
			</tr>
			<tr>
			<td style="color: #17A008;"><label><spring:message
							code="label.accountNo" /><span style="color: red">*</span></label></td>
				<td ><form:input path="otherAccount" value="" placeholder="Enter AccountNo" class="myform-control" style="background: whitesmoke; text: pointer;"
						id="otherAccount"/>
						
						</td>
			</tr>
			<tr>
			<td style="color: #17A008;"><label><spring:message
							code="label.ifsc" /><span style="color: red">*</span></label></td>
				<td ><form:input path="otherIFSC" value="" placeholder="Enter IFSC Code" class="myform-control" style="background: whitesmoke; text: pointer;"
						id="otherIFSC"/>
						
						</td>
			</tr>
			<tr>
				<td></td>
					<td id="otherPayTransferError" style="display: none; color: red;">
						<spring:message code="label.selectValue" />
					</td>
			</tr>		
			<tr>
				<td></td>
					<td id="otherIFSCError" style="display: none; color: red;">
						<spring:message code="label.enterData" />
					</td>
			</tr>
			<tr>
				<td></td>
					<td id="otherAccountError" style="display: none; color: red;">
						<spring:message code="label.enterData" />
					</td>
			</tr>
		</table>

		<div class="col-sm-12 col-md-12 col-lg-12">
		<c:if test="${baseURL[1] == 'bnkEmp'}"><c:set var="back" value="fdList"/></c:if>
					<c:if test="${baseURL[1] == 'users'}"><c:set var="back" value="fdList"/></c:if>
		
		
			<table align="center" class="f_deposit_btn">
				<tr>
					<td><input type="submit" class="btn btn-primary"
						value="<spring:message code="label.continue"/>"></td>
					<td><a href="${back}" class="btn btn-success"><spring:message code="label.back"/></a></td>
				</tr>

			</table>
		</div>
	</form:form>
	
	</div>
	</div>
	
	<script>
		function populateBalance() {			
			var estimatedInterest = document.getElementById('estimateInterest').value;
			
			if (document.getElementById("interstPayType1").checked == true) {
				var interestAmtEntered = document.getElementById('interestPayAmount').value;
				if (interestAmtEntered != '') {
					var balanceInterest = Math
							.round((((estimatedInterest - interestAmtEntered) + 0.00001)) * 100) / 100;
					document.getElementById('interestPayAmount1').value = balanceInterest;
				}
			} else if (document.getElementById("interstPayType2").checked == true) {
				
				var interestPercent = document
						.getElementById('interestPercent').value;
				var interestAmount = Math
						.round(((((estimatedInterest * interestPercent) / 100) + 0.00001)) * 100) / 100;
				document.getElementById('interestPayAmount').value = interestAmount;
				var balanceInterest = Math
						.round((((estimatedInterest - interestAmount) + 0.00001)) * 100) / 100;
				document.getElementById('interestPayAmount1').value = balanceInterest;
			}
		}
	</script>
