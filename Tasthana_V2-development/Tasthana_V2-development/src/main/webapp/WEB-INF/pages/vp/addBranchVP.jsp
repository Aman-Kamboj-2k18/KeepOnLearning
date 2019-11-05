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
				name="fixedDeposit" action="addedBranch" autocomplete="off"  method="post"
				commandName="branch" >
				
          <div class="list-of-rates">
			<div class="header_customer">	
				<h3><spring:message code="label.addBranch"/></h3>
			</div>
			
		    	<div class="list-of-rates-table">	
				<span class="counter pull-right"></span>
				
				<div>
				
				<div class="form-group" id="states">
							<label class="col-md-4 control-label">State<span style="color: red">*</span></label>
							
							<div class="col-md-4">
								<form:select id="state" path="state"
								class="input myform-control"  onChange="myFunction()">
								<form:option value="Select"></form:option>
							</form:select>

								<script>

								populateStates("state");
								</script>
							
								 
				</div>
				
									
									</div>
									</div>
									
									<div class="form-group col-md-12" id="ripDiv" style="text-align: -webkit-left; margin-top: 5px;">
							<label class="col-md-4 control-label" style="padding-top: 6px; margin-left:37px; text-align: -webkit-right;">Urban Or Rular</label>
							<div class="col-md-7" style="margin: 2px 0 0 -15px;">

								<label for="radio"> <form:radiobutton path="urbanOrRular"
										id="urban" name="urbanOrRular" value="Urban" onclick="citizenFun()" checked="true"></form:radiobutton></label>
								Urban
								<label for="radio"> <form:radiobutton path="urbanOrRular"
										id="rular" name="urbanOrRular" value="Rular"
										onclick="citizenFun()"></form:radiobutton></label>
								Rular
							</div>
			    
				
				
				
			</div>
									
									<div class="form-group">
						<label class="col-md-4 control-label">City/Town</label>
						<div class="col-md-4">
							<form:input path="cityOrTown" id="cityOrTown" class="myform-control"
								placeholder="" />
							<span id="branchNameError" style="display: none; color: red;"><spring:message
									code="label.validation" /></span>
						</div>
						
				</div>
				
				
				<div class="form-group">
						<label class="col-md-4 control-label">Area</label>
						<div class="col-md-4">
							<form:input path="area" id="area" class="myform-control"
								placeholder="" />
							<span id="branchCodeError" style="display: none; color: red;"><spring:message
									code="label.validation" /></span>
						</div>
						
				</div>
				<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.branchName" /><span style="color:red">*</span></label>
						<div class="col-md-4">
							<form:input path="branchName" id="branchName" class="myform-control"
								placeholder="" />
							<span id="branchNameError" style="display: none; color: red;"><spring:message
									code="label.validation" /></span>
						</div>
						
				</div>
				
				
				<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.branchCode" /><span style="color:red">*</span></label>
						<div class="col-md-4">
							<form:input path="branchCode" id="branchCode" class="myform-control"
								placeholder="" />
							<span id="branchCodeError" style="display: none; color: red;"><spring:message
									code="label.validation" /></span>
						</div>
						
				</div>
				
			    <div class="col-md-offset-4 col-md-8"><span id="errorMsg" style="display: none; color: red;"><spring:message
									code="label.validation" /></span></div>
									<div class="col-sm-12 col-md-12 col-lg-12">
					<div class="errorMsg">
						<font color="red" style="text-align:center;width:100%;float:left;" id="validationError">${error}</font>
					</div>
				</div>
				
				 <div class="col-md-offset-4 col-md-8" style="padding-bottom: 22px;">
<%-- 				 <a href="apprMng" class="btn btn-success"><spring:message code="label.back"/></a> --%>
					<input type="submit" size="3" onclick="return addBranch()"  value="<spring:message code="label.add"/>" class="btn btn-primary">
					
				</div>
				 <table class="table table-bordered"  align="center" id="my-table">
				 <c:if test="${! empty allList}">
					<thead>
						<tr>
					    	<th><spring:message code="label.id"/></th>
							<th><spring:message code="label.branchName"/></th>
							<th><spring:message code="label.branchCode"/></th>
							<th>State</th>
							<th>Urban/Rular</th>
							<th>City/Town</th>
							<th>Area</th>
<!-- 							<th>Edit</th> -->
						</tr>
					</thead>
					<tbody>
					 
						
							<c:forEach items="${allList}" var="list">
							   
								<tr>
									
									<td><c:out value="${list.id}"></c:out>
									<td><c:out value="${list.branchName}"></c:out></td>
									<td><c:out value="${list.branchCode}"></c:out></td>
									<td><c:out value="${list.state}"></c:out></td>
									<td><c:out value="${list.urbanOrRular}"></c:out></td>
									<td><c:out value="${list.cityOrTown}"></c:out></td>
									<td><c:out value="${list.area}"></c:out></td>
<%-- 									<td><a href="editCustomerCategory?id=${list.id}" --%>
<%-- 											class="btn btn-primary"><spring:message --%>
<%-- 													code="label.edit" /></a></td> --%>
								</tr>
							</c:forEach>
						
	
					</tbody>
					</c:if>
				</table> 
			
				
				
			</div>


</div>
</form:form>
</div>
</div>
</div>

  <script>
  
  
$(document).ready(function(){
	  
	  $('#branchName').bind('keypress', function (event) {
	        var regex = new RegExp("^[a-zA-Z0-9 ]*$");
	        var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
	        if (!regex.test(key)) {
	        	//alert("false")
	            event.preventDefault();
	            return false;
	        }
	       
	        
	    });
	  
	  
	});
  
 
$(document).ready(function(){
	  
	  $('#branchCode').bind('keypress', function (event) {
	        var regex = new RegExp("^[a-zA-Z0-9 ]*$");
	        var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
	        if (!regex.test(key)) {
	        	//alert("false")
	            event.preventDefault();
	            return false;
	        }
	       
	        
	    });
	  
	  
	});



  
    function addBranch() {
 
    	 var states=document.getElementById('state').value;
    	var submit=true;
    	var errorMsg="";
    	var validationError=document.getElementById('validationError');
		validationError.innerHTML="";
    	if ( states == "Select" || states =="") {
    			document.getElementById('state').style.borderColor = "red";
    			errorMsg = "<br>Please Select State";
    			validationError.innerHTML += errorMsg;
    			//document.getElementById('stateError').style.display = 'block';
    			submit = false;
    		} else {
    			document.getElementById('state').style.borderColor = "black";

    		} 
    	
		  debugger;
			    var branchName = document.getElementById('branchName').value;
			    if(branchName==""){
			     document.getElementById('branchName').style.borderColor = "red";
			     errorMsg = "<br>Please Enter Branch Name";
	    			validationError.innerHTML += errorMsg;
	    			submit=false;
			    // document.getElementById('errorMsg').style.display='block';
			     /* event.preventDefault(); */
			    }
			   /*  else
			    {   
			    $("#fdForm").attr("action", "addedBranch");
			    $("#fdForm").submit();

			    } */
			    var branchCode = document.getElementById('branchCode').value;
			    if(branchCode==""){
			     document.getElementById('branchCode').style.borderColor = "red";
			     /* document.getElementById('errorMsg').style.display='block';
			     event.preventDefault(); */
			     errorMsg = "<br>Please Enter Branch Code";
	    			validationError.innerHTML += errorMsg;
	    			submit=false;
			    }
			   /*  else
			    {   
			    $("#fdForm").attr("action", "addedBranch");
			    $("#fdForm").submit();

			    } */
			    if(submit==true){
			    	 $("#fdForm").attr("action", "addedBranch");
					    $("#fdForm").submit();
			    }
			    else{
			    	return false;
			    }
			    }
    
    
    function citizenFun(){
    	
    	var RIRadioChecked = document.getElementById("urban").checked;
    	var NRIRadioChecked = document.getElementById("rular").checked;
    	if(RIRadioChecked==true){
    		document.getElementById('accountTypeDIV').style.display = 'none';
    		document.getElementById('radioError').style.display = 'none';
    		document.getElementById('nriAccountType').value="";
    		$('#setCategory option').each(function(){
    			if($(this).attr('urbanOrRular')=="Urban"){
    				$(this).show();
    			}
    			if($(this).attr('urbanOrRular')=="Rular"){
    				$(this).hide();
    			}
    		});
    	}
    	if(NRIRadioChecked== true){
    		document.getElementById('accountTypeDIV').style.display = 'block';
    		document.getElementById('radioError').style.display = 'none';
    		$('#setCategory option').each(function(){
    			if($(this).attr('urbanOrRular')=="Urban"){
    				$(this).hide();
    			}
    			if($(this).attr('urbanOrRular')=="Rular"){
    				$(this).show();
    			}
    		});
    	}
    	$('#setCategory').val('select').change();
    }
    
   
   </script>