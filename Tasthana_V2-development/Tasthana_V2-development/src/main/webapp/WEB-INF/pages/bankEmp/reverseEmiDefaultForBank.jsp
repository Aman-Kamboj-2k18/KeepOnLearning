
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div class="right-container" id="right-container">
	<div class="container-fluid">
       <form:form id="reverseEmiBank" class="form-horizontal"
				name="reverseEmi" autocomplete="off" 
				commandName="fixedDepositForm" >
		
		<div class="Flexi_deposit">	
		<div class="header_customer">
			<h3>Reverse EMI</h3>
		</div>

<div style="text-align: center; font-size: 18px;">
					<h5 style="color: red;" id="error">${error}</h5>
				</div>
			<div class="flexi_table" style="margin-top: 7px;">
			
				<div class="form-group" id="emiType"  style="margin-top: 59px;">
					<div class="col-sm-3"></div>
					
					<div class="col-sm-3">
						<label for="radio"> <input type="radio" name="emiRadio" id="fixedAmountRadio" 
								value="fixedAmountRadio" > Fixed Amount EMI</label>
					
					</div>
	                <div class="col-sm-3">
						<label for="radio"> <input type="radio" name="emiRadio" 
								 id="fixedTenureRadio"
								value="fixedTenureRadio"  > Fixed Tenure EMI</label>
				
					</div>
				</div>

				<div class="col-sm-12 col-md-12 col-lg-12" style="margin-top: 46px;margin-left: -36px;margin-bottom: 34px;">

					<table align="center">
						<tr>
						<%--  <td>
						 <a href="customerSearch"
									class="btn btn-warning"> <spring:message code="label.back" /></a></td>
									<td>&nbsp;&nbsp;&nbsp;&nbsp;</td> --%>
							<td> 
						
							<a id="go" href="#" onclick="selectEmi()" class="btn btn-primary">Go</a>
									
									</td>

						</tr>

					</table>
				</div>

			</div>

		</div>
		</form:form>
	</div>
</div>

<script>


function selectEmi(){

	var userName = '${fixedDepositForm.userName}';
	var fixedAmtRadio = document.getElementById("fixedAmountRadio").checked;
	var fixedTenure = document.getElementById("fixedTenureRadio").checked;
	
	if(fixedAmtRadio ==true)
		document.getElementById("go").href="reverseEmiFixedAmountForBankEmp?userName=${fixedDepositForm.userName}"; 
	else
		document.getElementById("go").href="reverseEmiForBankEmp?userName=${fixedDepositForm.userName}"; 

   return true;
	
}

</script>