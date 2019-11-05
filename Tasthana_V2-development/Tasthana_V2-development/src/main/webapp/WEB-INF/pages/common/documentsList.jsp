<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

			
		
		<c:if test="${! empty filesList}">

			<div class="right-container" id="right-container">
        <div class="container-fluid">
        	<div class="fd-list">
				<div class="header_customer">	
					<h3>Details</h3>
				</div>
		
		<div class="successMsg"
			style="text-align: center; color: green; font-size: 18px;">${success}
		</div>
		
		
			<div class="fd-list-table">	
					<input type="text" id="kwd_search" value="" placeholder="Search Here..."  style="float:right;"/>
				</div>
				
				
				<div style="clear: both;">
				<form:form commandName="uploadedFileForm" action="" method="post" id="fileForm">
				
				
			<table class="table data jqtable example table-bordered table-striped table-hover" id="my-table">
				<thead>
					<tr>
					<th><input type="checkbox"  id="checkAll"/></th>
					<th><spring:message code="label.id"/></th>
					<th><spring:message code="label.customerName"/></th>
					
<!-- 				    <th>Documents</th>
 -->				    <th>Date</th>		           	            
					</tr>
				</thead>
					<tbody>
						

							<c:forEach items="${filesList}" var="file">
							<tr>
								<td><form:checkbox path="idList" value="${file.id}" id="idList"/></td>
							    <td><c:out value="${file.id}"></c:out></td>
								<td><c:out value="${file.customerName}"></c:out>
<%-- 								<td><c:out value="${file.document}"></c:out>
 --%>								<td><c:out value="${file.uploadDate}"></c:out>
							</tr>
						</c:forEach>
					

				</tbody>
			</table>
			<button type="submit" id="download" class="btn btn-primary"><spring:message code="label.download"/></button>
			<button type="submit" id="delete" class="btn btn-primary"><spring:message code="label.delete"/></button>
		
			</form:form></div>
		</div>
			</c:if>
<div class="col-sm-12 col-md-12 col-lg-12"  style="margin-top: 42px;">
					<table align="center">
						<tr>
							<td><a href="searchDeathCustomer" class="btn btn-success"><spring:message code="label.back"/></a></td>
						</tr>
					</table>
		        </div>
</div>

<script>
var checkedAtLeastOne = false;

$("#download").on("click", function(e){
    e.preventDefault();
   if(checkboxValidation()) {
	   $('#fileForm').attr('action', "documentDownload").submit();   
   }   
});
$("#delete").on("click", function(e){
    e.preventDefault();
    if(checkboxValidation()) {
    	$('#fileForm').attr('action', "documentDelete").submit();
    }
});

$("#checkAll").change(function () {
    $("input:checkbox").prop('checked', $(this).prop("checked"));
});


function checkboxValidation (argument) {	
	if($('#idList:checked').length<1){
		alert('Please check atleast one value');					
		return false;	
	}else {
		return true;
	}
	
}
</script>
