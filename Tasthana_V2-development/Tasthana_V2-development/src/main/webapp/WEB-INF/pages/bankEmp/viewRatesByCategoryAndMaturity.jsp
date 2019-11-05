<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<script>
function changeCategory(){
	
		  $("#ratesForm").submit();
	 
}
</script>
<div class="list-of-rates">
			<div class="header_customer">	
				<h3 align="center"><spring:message code="label.listOfRates"/></h3>
			</div>
			
			<div class="list-of-rates-table">	
				<span class="counter pull-right"></span>
				<div class="col-sm-12 col-md-12 col-lg-12">
					<input type="text" id="kwd_search" value="" placeholder="Search Here..."  style="float:right;"/>
				</div>
				<div>
				<form:form commandName="depositRates" name="viewForm" action="viewRatesByCategoryAndMaturityPost">
					<table >
							<tr>
								
						<td><b><spring:message code="label.genCategory" /> :</b></td>
					
					<td>
				<form:select id="category" class="myform-control" path="category" >
											<form:option value="select"></form:option>
													<form:option value="General"></form:option>
													<form:option value="Senior Citizen"></form:option>
													<form:option value="NGO"></form:option>
													</form:select>
									</td>
									</tr>
									<tr>
									<td><b><spring:message code="label.maturityPeriod" /> :</b> </td>
									<td><form:input path="calMaturityPeriodFromInDays"  placeholder="Enter maturity period" type="number"/></td>
									</tr>
									<tr>
									<td><input type="submit" value="View Rate"></td>
									</tr>
										
									</table>
				</form:form>
		<c:if test="${! empty rate}"> 
				<table class="table table-bordered" align="center" id="my-table">
					<thead>
						<tr>
						
							<th><spring:message code="label.genCategory"/></th>
							<th><spring:message code="label.fdRates"/></th>
							
							
						</tr>
					</thead>
					<tbody>
					 
								<tr>
								
									<td><c:out value="${depositRates.category}"></c:out>
									<td><c:out value="${rate}"></c:out></td>
								
										</tr>
						
					
	
					</tbody>
				</table> 
			 </c:if> 
			</div>

</div>
<!-- <style>
		.table-bordered {
    border: 1px solid #BFBABA;
}
	  .table-bordered>tbody>tr>td, .table-bordered>tbody>tr>th, .table-bordered>tfoot>tr>td, .table-bordered>tfoot>tr>th, .table-bordered>thead>tr>td, .table-bordered>thead>tr>th{ border: 1px solid #C7C7C7;}
</style> -->