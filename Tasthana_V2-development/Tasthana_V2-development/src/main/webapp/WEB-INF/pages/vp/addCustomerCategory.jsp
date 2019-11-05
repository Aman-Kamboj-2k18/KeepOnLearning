<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<script>

</script>
<div class="right-container" id="right-container">
        <div class="container-fluid">
			<div class="add-customer-category">
			
			<form:form  class="form-horizontal" id="addCustomerCategoryForm"
				name="fixedDeposit" action="addedCustomer" autocomplete="off"  method="post"
				commandName="customerCategoryForm" >
          <div class="list-of-rates">
          <div class="Flexi_deposit">
			<div class="header_customer">	
				<h3><spring:message code="label.addCustomerCategory"/></h3>
			</div>
			
		    	<div class="col-md-12">	
				<span class="counter pull-right"></span>
				
				<div>
				<div class="form-group">
						<label class="col-md-4 control-label"><spring:message
								code="label.addCustomerCategory" /><span style="color:red">*</span></label>
						<div class="col-md-4">
							<form:input path="customerCategory" id="customerCategory" class="myform-control"
								placeholder="" />
							<span id="customerCategoryError" style="display: none; color: red;"><spring:message
									code="label.validation" /></span>
						</div>
				</div>
			    <div class="col-md-offset-4 col-md-8"><span id="errorMsg" style="display: none; color: red;"><spring:message
									code="label.validation" /></span></div>
									<div class="col-sm-12 col-md-12 col-lg-12">
					<div class="errorMsg">
						<font color="red">${error}</font>
					</div>
				</div>
				
				 <div class="col-md-offset-4 col-md-8" style="padding-bottom: 22px;">
<%-- 				 <a href="apprMng" class="btn btn-success"><spring:message code="label.back"/></a> --%>
					<input type="submit" size="3" onclick="addCustomer()"  value="<spring:message code="label.add"/>" class="btn btn-primary">
					
				</div>
				 <table class="table table-bordered" align="center" id="my-table">
				 <c:if test="${! empty allList}">
					<thead>
						<tr>
					    	<th><spring:message code="label.id"/></th>
							<th><spring:message code="label.customerCategory"/></th>
							<th><spring:message code="label.createdBy"/></th>
							<th><spring:message code="label.createdDate"/></th>
							<th><spring:message code="label.isActive"/></th>
<!-- 							<th>Edit</th> -->
						</tr>
					</thead>
					<tbody>
					 
						
							<c:forEach items="${allList}" var="list">
							   
								<tr>
									
									<td><c:out value="${list.id}"></c:out>
									<td><c:out value="${list.customerCategory}"></c:out></td>
									<td><c:out value="${list.createdBy}"></c:out></td>
									<td><fmt:formatDate pattern="dd/MM/yyyy"
											value="${list.createdDate}" /></td>
									<td><c:out value="${list.isActive}"></c:out>
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
</div></div>
</form:form>
</div>
</div>
</div>

  <script>
  
  
  $(document).ready(function(){
	  
	  $('#customerCategory').bind('keypress', function (event) {
	        var regex = new RegExp("^[a-zA-Z ]*$");
	        var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
	        if (!regex.test(key)) {
	        	//alert("false")
	            event.preventDefault();
	            return false;
	        }
	    });
	  
	  
	});
  
  
    function addCustomer() {
			 
			    var customerCategory = document.getElementById('customerCategory').value;
			    if(customerCategory==""){
			     document.getElementById('customerCategory').style.borderColor = "red";
			     document.getElementById('errorMsg').style.display='block';
			     event.preventDefault();
			    }
			    else
			    {   
			    $("#fdForm").attr("action", "addedCustomer");
			    $("#fdForm").submit();

			    }
			    }
   </script>