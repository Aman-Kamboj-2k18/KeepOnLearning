
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<div class="right-container" id="right-container">
	<div class="container-fluid">
		
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
								value="fixedTenureRadio" > Fixed Tenure EMI</label>
						
						


					</div>
				</div>



				<div class="col-sm-12 col-md-12 col-lg-12" style="margin-top: 46px;margin-left: -36px;margin-bottom: 34px;">




					<table align="center">
						<tr>
						<td><a href="user"
									class="btn btn-warning"> <spring:message code="label.back" /></a></td>
									<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
							<td><a href="#" onclick="selectEmi()"><input style="width:58px;"  id="goBtn" type="button"
									class="btn btn-primary" value="Go"></a></td>

						</tr>

					</table>
				</div>

			</div>

		</div>
	</div>
</div>
<script>

function selectEmi(){
	var fixedAmtRadio = document.getElementById("fixedAmountRadio").value;
	var fixedTenure = document.getElementById("fixedTenureRadio").value;
	
	if(document.getElementById("fixedAmountRadio").checked==true){
		 window.location.href= "reverseEmiFixedAmount";
	}
	else if(document.getElementById("fixedTenureRadio").checked==true){
		 window.location.href="reverseEmi";
	}
	else{
		 window.location.href="#";
	}
  
   
}

</script>