<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<script>

</script>
<div class="right-container" id="right-container">
        <div class="container-fluid">
			<div class="add-ornament">
			
			<form:form  class="form-horizontal" id="ornamentsMaster"
				name="ornament" action="editOrnament" autocomplete="off"  method="post"
				commandName="ornamentsMaster" >
				<form:hidden path="id"/>
				
          <div class="list-of-ornament">
			<div class="header_customer">	
				<h3>Edit Ornament</h3>
			</div>
			
		    	<div class="list-of-rates-table">	
				<span class="counter pull-right"></span>
				
				<div>
				
				
				<div class="form-group">
						<label class="col-md-4 control-label">Ornament<span style="color:red">*</span></label>
						<div class="col-md-4">
							<form:input path="ornament" id="ornament" class="myform-control"
								placeholder="" />
							<span id="ornamentError" style="display: none; color: red;"><spring:message
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

					<input type="submit" size="3" onclick="return addOrnament()"  value="Update" class="btn btn-primary">
					
				</div>
			</div>
</div>

</div>
</form:form>
</div>
</div>
</div>

  <script>
  
  
$(document).ready(function(){
	  
	  $('#ornament').bind('keypress', function (event) {
	        var regex = new RegExp("^[a-zA-Z0-9 ]*$");
	        var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
	        if (!regex.test(key)) {
	        	//alert("false")
	            event.preventDefault();
	            return false;
	        }
	       
	        
	    });
	  
	  
	});
  
 

	  
	  
	



  
    function addOrnament() {
 
    	
    	var submit=true;
    	var errorMsg="";
    	var validationError=document.getElementById('validationError');
		validationError.innerHTML="";
    	
    	
		  debugger;
			    var ornament = document.getElementById('ornament').value;
			    if(ornament==""){
			     document.getElementById('ornament').style.borderColor = "red";
			     errorMsg = "<br>Please Enter Ornament";
	    			validationError.innerHTML += errorMsg;
	    			submit=false;
			   
			    }
			   
			   
			    if(submit==true){
			    	 $("#fdForm").attr("action", "editOrnament");
					    $("#fdForm").submit();
			    }
			    else{
			    	return false;
			    }
			    
			    }
    
   
   	
    function editOrnament(id) {
    	
       	
	   	 window.location="ornamentsMasterById?id="+id; 
   }
   
  
   
   </script>