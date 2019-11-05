<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<script>

</script>
<div class="right-container" id="right-container">
        <div class="container-fluid">
			<div class="add-ornaments">
			
			<form:form  class="form-horizontal" id="ornamentsMaster"
				name="ornamentsMaster" action="addOrnament" autocomplete="off"  method="post"
				commandName="ornamentsMaster" >
				<input type="hidden" name="menuId" value="${menuId}" id="menuId"/>
          <div class="list-of-ornaments">
			<div class="header_ornaments">	
				<h3>Ornaments</h3>
			</div>
			
		    	<div class="list-of-rates-table">	
				<span class="counter pull-right"></span>
				
				<div>
				
				
				<div class="form-group">
						<label class="col-md-4 control-label">Add Ornament<span style="color:red">*</span></label>
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
					<input type="submit" size="3" onclick="return addOrnament()"  value="<spring:message code="label.add"/>" class="btn btn-primary">
					
				</div>
				 <table class="table table-bordered"  align="center" id="my-table">
				 <c:if test="${! empty allList}">
					<thead>
						<tr>
					    	<th><spring:message code="label.id"/></th>
							<th>Ornament</th>
							<th><spring:message code="label.action"/></th>
<!-- 							<th>Edit</th> -->
						</tr>
					</thead>
					<tbody>
					 
						
							<c:forEach items="${allList}" var="list">
							   
								<tr>
									
									<td><c:out value="${list.id}"></c:out>
									<td><c:out value="${list.ornament}"></c:out></td>
									<td><a onclick="editOrnament('${list.id}')" href="#"
									class="btn btn-primary"><spring:message code="label.edit"/></a></td>
					</tr>
							</c:forEach>
						
	
					</tbody>
					</c:if>
				</table> 
			
				
				
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
			    	 $("#fdForm").attr("action", "addOrnament");
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