<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="right-container" id="right-container">
 <div class="container-fluid">
     <div class="row">
<div class="body_fixed"><div class="list-of-rates">
			
		<div class="col-sm-12 col-md-12 col-lg-12 header_customer">
			<h3><spring:message code="label.deleteDownloadDoc"/></h3>
		</div>
		<%-- <div class="col-sm-12 col-md-12 col-lg-12 text-center">
		<c:if test="${empty caseList}">
		<img src="<%=request.getContextPath()%>/resources/images/NoData.png" align="middle"/>
		</c:if>
		</div>
		 --%>
		 <div class="list-of-rates-table">
		<div class="col-sm-12 col-md-12 col-lg-12">
			 <div  class="successMsg"><b><font color="green">${success}</font></b></div>
	 	</div>
		<c:if test="${! empty cust}">
		<div class="col-sm-12 col-md-12 col-lg-12">
		
			<div class="col-sm-12 col-md-12 col-lg-12">
					<input type="text" id="kwd_search" value="" placeholder="Search Here..."  style="float:right;"/>
			</div>
			<table class="table table-responsive data jqtable example" id="my-table">
				<thead>
					<tr>
					    <th><spring:message code="label.id"/></th>
						<th><spring:message code="label.customerId"/></th>
						<th><spring:message code="label.customerName"/></th>
						<th><spring:message code="label.accno"/></th>
					    <th><spring:message code="label.downloadDocuments"/></th>
					    		            
					</tr>
				</thead>
					<tbody>
						

							<c:forEach items="${cust}" var="custDetails">
							<tr>
							    <td><c:out value="${custDetails.id}"></c:out></td>
							    <td><c:out value="${custDetails.customerId}"></c:out></td>
								<td><c:out value="${custDetails.customerName}"></c:out>
							    <td><c:out value="${custDetails.depositAccountNumber}"></c:out>
								<td style="width:40%"><a href="documentsList?customerId=${custDetails.customerId}&accountNumber=${custDetails.depositAccountNumber}" class="btn btn-primary"><spring:message code="label.deleteDownloadDoc"/></a></td>
						</tr>
						</c:forEach>
					

					</tbody>
			</table> 
		</div>
		</c:if>
		           <div class="col-sm-12 col-md-12 col-lg-12">
					<table align="center">
						<tr>
							<td><a href="searchForDeathCustomer?menuId=${menuId}" class="btn btn-success"><spring:message code="label.back"/></a></td>
						</tr>
					</table>
		        </div>
</div></div></div>
</div></div>
</div>