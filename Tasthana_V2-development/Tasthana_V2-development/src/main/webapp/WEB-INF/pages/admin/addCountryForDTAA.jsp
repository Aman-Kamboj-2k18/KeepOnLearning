<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<div class="right-container" id="right-container">

	<div class="container-fluid">
	<div class="fd-list">
		<div class="col-sm-12 col-md-12 col-lg-12 header_customer">
				<h3 class="header_customer">
					<spring:message code="label.addCountryForDTAA" />
				</h3>
			</div>
			<div class="Success_msg">
				<div class="successMsg" style="text-align: center; color: red;">
					${error}</div>
			</div>
		<div class="fd-list-table">

			
			<form:form name="countryForDTAAForm" action="saveCountryForDTAA" method="post"
				commandName="addCountryForDTAAForm">


				<div class="form-group col-md-6" style="margin-top: 45px; margin-left: 145px;">
						<label class="col-md-4 control-label" style="text-align: -webkit-right; padding-top: 14px;"><spring:message
								code="label.country" /><span style="color: red">*</span></label>
						<div class="col-md-8">
							<form:input path="country" id="country" onkeypress="validName(event)"
								placeholder="Enter Country" class="myform-control" ></form:input>
						</div>
						
				
				</div>	
				<div style="clear:both;"></div>		
				<div class="space-10"></div>
				<table class="col-sm-offset-2" style="position: relative; left: 15%;">
					<tr>
						<td class="col-sm-8"><a href="countryForDTAA" class="btn btn-success"><spring:message
									code="label.back" /></a> <input type="submit" size="3"
							value="<spring:message code="label.save"/>"
							class="btn btn-primary"></td>

					</tr>
				</table>

			</form:form>


		</div>
	</div>

</div></div>
<script>
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
</script>

	