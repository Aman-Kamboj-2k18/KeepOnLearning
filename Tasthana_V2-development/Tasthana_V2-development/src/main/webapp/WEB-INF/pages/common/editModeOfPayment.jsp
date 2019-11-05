<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<script>

</script>
<div class="right-container" id="right-container">
        <div class="container-fluid">
			<div class="add-modeOfPayment">
			
			<form:form  class="form-horizontal" id="modeOfPayment"
				name="modeOfPayment" action="editModeOfPayment" autocomplete="off"  method="post"
				commandName="modeOfPayment" >
				<form:hidden path="id"/>
				
          <div class="list-of-modeOfPayment">
			<div class="header_customer">	
				<h3>Mode Of Payment</h3>
			</div>
			
		    	<div class="list-of-rates-table">	
				<span class="counter pull-right"></span>
				
				<div>
				
				
				<div class="form-group">
						<label class="col-md-4 control-label">Payment Mode<span style="color:red">*</span></label>
						<div class="col-md-4">
							<form:input path="paymentMode" id="paymentMode" class="myform-control"
								placeholder="" />
							<span id="paymentModeError" style="display: none; color: red;"><spring:message
									code="label.validation" /></span>
						</div>
						
				</div>
				
				
				
				<div class="form-group" style="margin-bottom: 2px;">
													<label class="col-md-4"
														style="padding-top: 16px; text-align: -webkit-right;">Is
														Visible In Customer Side<span style="color: red">*</span>
													</label>
												
													
													<div class="col-md-4">
														<form:select path="isVisibleInCustomerSide" class="myform-control">
															<form:option value="1">Yes</form:option>
															<form:option value="0">No</form:option>
														</form:select>
                                                    </div>

												</div>
												
												
												
							<div class="form-group" style="margin-bottom: 2px;">
													<label class="col-md-4"
														style="padding-top: 16px; text-align: -webkit-right;">Is
														Visible In Bank Side<span style="color: red">*</span>
													</label>
												
													
													<div class="col-md-4">
														<form:select path="isVisibleInBankSide" class="myform-control">
															<form:option value="1">Yes</form:option>
															<form:option value="0">No</form:option>
														</form:select>
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
					<input type="submit" size="3" onclick="return addModeOfPayment()"  value="Update" class="btn btn-primary">
					
				</div>
				 <%-- <table class="table table-bordered"  align="center" id="my-table">
				 <c:if test="${! empty allList}">
					<thead>
						<tr>
					    	<th><spring:message code="label.id"/></th>
							<th>Payment Mode</th>
							<th>Is Visible In Customer Side</th>
							<th>Is Visible In Bank Side</th>
							<th><spring:message code="label.action"/></th>
<!-- 							<th>Edit</th> -->
						</tr>
					</thead>
					<tbody>
					 
						
							<c:forEach items="${allList}" var="list">
							   
								<tr>
									
									<td><c:out value="${list.id}"></c:out>
									<td><c:out value="${list.paymentMode}"></c:out></td>
									<td><c:out value="${list.isVisibleInCustomerSide}"></c:out></td>
									<td><c:out value="${list.isVisibleInBankSide}"></c:out></td>
									<td><a onclick="editModeOfPayment('${list.id}')" href="#"
									class="btn btn-primary"><spring:message code="label.edit"/></a></td>
									<td><a href="editCustomerCategory?id=${list.id}"
											class="btn btn-primary"><spring:message
													code="label.edit" /></a></td>
								</tr>
							</c:forEach>
						
	
					</tbody>
					</c:if>
				</table> 
			
 --%>				
				
			</div>
</div>

</div>
</form:form>
</div>
</div>
</div>

  <script>
  
  
$(document).ready(function(){
	  
	  $('#paymentMode').bind('keypress', function (event) {
	        var regex = new RegExp("^[a-zA-Z0-9 ]*$");
	        var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
	        if (!regex.test(key)) {
	        	//alert("false")
	            event.preventDefault();
	            return false;
	        }
	       
	        
	    });
	  
	  
	});
  
 

	  
	  
	



  
    function addModeOfPayment() {
 
    	
    	var submit=true;
    	var errorMsg="";
    	var validationError=document.getElementById('validationError');
		validationError.innerHTML="";
    	
    	
		  debugger;
			    var branchName = document.getElementById('paymentMode').value;
			    if(branchName==""){
			     document.getElementById('paymentMode').style.borderColor = "red";
			     errorMsg = "<br>Please Enter Payment Mode";
	    			validationError.innerHTML += errorMsg;
	    			submit=false;
			   
			    }
			   
			   
			    if(submit==true){
			    	 $("#fdForm").attr("action", "editModeOfPayment");
					    $("#fdForm").submit();
			    }
			    else{
			    	return false;
			    }
			    
			    }
    
   
   	
    function editModeOfPayment(id) {
    	
       	
	   	 window.location="modeOfPaymentById?id="+id; 
   }
   
  
   
   </script>