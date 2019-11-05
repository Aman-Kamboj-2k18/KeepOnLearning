<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<script>

</script>
<div class="right-container" id="right-container">
        <div class="container-fluid">
			<div class="add-branch">
			
			<form:form  class="form-horizontal" id="addBranchForm"
				name="fixedDeposit" action="addedRoundOff" autocomplete="off"  method="post"
				commandName="roundOff" >
          <div class="list-of-rates">
          <div class="Flexi_deposit">	
			<div class="header_customer">	
				<h3>Round OFF On Maturity</h3>
			</div>
			
		    	<div class="col-md-12">	
				<span class="counter pull-right"></span>
				
				<div class="form-group col-md-9" id="decimalPlaces">
								<label class="col-md-6 control-label" for="" style="margin-top: 5px;">Decimal Places<span style="color: red">*</span>
								</label>
				<div class="col-md-3" style="margin-left: -15px; min-width: 100px;">
							<form:select id="depositClassification"
								path="decimalPlaces" class="myform-control"
								required="true">
								<form:option value="${roundOff.decimalPlaces}" selected="true">
									select
								</form:option>
								<form:option value="0">
									0
								</form:option>
								<form:option value="1">
									1
								</form:option>
								<form:option value="2">
									2
								</form:option>
								<form:option value="3">
									3
								</form:option>
								<form:option value="4">
									4
								</form:option>

							</form:select>
						</div>
								</div>
				
				
				<div class="form-group col-md-12" id="ripDiv" style="text-align: -webkit-left; margin-top: 5px;">
							<label class="col-md-4 control-label" style="padding-top: 6px; margin-left:37px; text-align: -webkit-right;">Nearest Highest Lowest<span style="color: red">*</span></label>
							<div class="col-md-7" style="margin: 2px 0 0 -15px;">

								<label for="radio"> <form:radiobutton path="nearestHighestLowest"
										id="highest" name="nearestHighestLowest" value="Nearest Highest" onclick="citizenFun()" checked="true"></form:radiobutton></label>
								<spring:message code="label.highest" />
								<label for="radio"> <form:radiobutton path="nearestHighestLowest"
										id="lowest" name="nearestHighestLowest" value="Nearest Lowest"
										onclick="citizenFun()"></form:radiobutton></label>
								<spring:message code="label.lowest" />
							</div>
			    <div class="col-md-offset-4 col-md-8"><span id="errorMsg" style="display: none; color: red;"><spring:message
									code="label.validation" /></span></div>
									<div class="col-sm-12 col-md-12 col-lg-12">
					<div class="errorMsg">
						<font color="red">${error}</font>
					</div>
				</div>
				
				 <div class="col-md-10" style="padding-bottom: 22px; margin-top: 20px; text-align: -webkit-center; margin-left: -17px;"><input type="submit" size="3" onclick="submit()"  value="<spring:message code="label.add"/>" class="btn btn-primary">
					
				</div>
				
				
			</div>

</div>
</div></div>
</form:form>
</div>
</div>
</div>

  <script>
  
  

  
function citizenFun(){
	
	var RIRadioChecked = document.getElementById("highest").checked;
	var NRIRadioChecked = document.getElementById("lowest").checked;
	if(RIRadioChecked==true){
		document.getElementById('accountTypeDIV').style.display = 'none';
		document.getElementById('radioError').style.display = 'none';
		document.getElementById('nriAccountType').value="";
		$('#setCategory option').each(function(){
			if($(this).attr('nearestHighestLowest')=="Nearest Highest"){
				$(this).show();
			}
			if($(this).attr('nearestHighestLowest')=="Nearest Lowest"){
				$(this).hide();
			}
		});
	}
	if(NRIRadioChecked== true){
		document.getElementById('accountTypeDIV').style.display = 'block';
		document.getElementById('radioError').style.display = 'none';
		$('#setCategory option').each(function(){
			if($(this).attr('nearestHighestLowest')=="Nearest Highest"){
				$(this).hide();
			}
			if($(this).attr('nearestHighestLowest')=="Nearest Lowest"){
				$(this).show();
			}
		});
	}
	$('#setCategory').val('select').change();
}
</script>tElementById('radioError').style.display = 'none';
	}
	
}
   </script>