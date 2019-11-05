<%@include file="taglib_includes.jsp"%>
<script>

$(document).ready(function() {
	 var productType = getUrlVars()["productType"];
	if(productType=="Tax%20Saving%20Deposit"){
		$('#tblNRI').hide();
	}
	
});

function getUrlVars()
{
    var vars = [], hash;
    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
    for(var i = 0; i < hashes.length; i++)
    {
        hash = hashes[i].split('=');
        vars.push(hash[0]);
        vars[hash[0]] = hash[1];
    }
    return vars;
}

</script>
<div class="right-container" id="right-container">
	<div class="container-fluid">



		<div class="Flexi_deposit">
			<div class="header_customer">
				<h3><spring:message code="label.openNewDeposit"/></h3>
			</div>
			<div class="flexi_table" style="margin: 25px;">
				<div class="col-md-6">
					<table align="center">
	
						<table id="tblRI" class="table table-striped table-bordered" style="width: 85% !important; margin-left: 25px;">
							<th colspan="3">Products for RI</th>						
<!-- 							<tr> -->
<%-- 								<td><b><spring:message code="label.id" /></b></td> --%>
<%-- 								<td><b><spring:message code="label.productCode" /></b></td> --%>
<%-- 								<td><b><spring:message code="label.productName" /></b></td> --%>
<!-- 							</tr> -->
							<c:if test="${! empty productConfigurationListForRI}">
								<c:forEach items="${productConfigurationListForRI}" var="product">

									<tr>
										<td hidden><b> <c:out value="${product.id}"></c:out></b></td>
										<td hidden><b> <c:out value="${product.productCode}"></c:out></b></td>
										<td><b> <a href="createDeposit?productId=${product.id}&type=RI&productType=${productType}&name=${product.productName}"><c:out value="${product.productName}"></c:out></a></b></td>
									</tr>
								</c:forEach>
							</c:if>
						</table>
				</div>
				
				<div class="col-md-6">
						
						<table id="tblNRI" class="table table-striped table-bordered" style="width: 85% !important; margin-left: 25px;">
							<th colspan="3">Products for NRI</th>						
<!-- 							<tr> -->
<%-- 								<td><b><spring:message code="label.id" /></b></td> --%>
<%-- 								<td hidden><b><spring:message code="label.productCode" /></b></td> --%>
<%-- 								<td><b><spring:message code="label.productName" /></b></td> --%>
<!-- 							</tr> -->
							<c:if test="${! empty productConfigurationListForNRI}">
								<c:forEach items="${productConfigurationListForNRI}" var="product">

									<tr>
										<td hidden><b> <c:out value="${product.id}"></c:out></b></td>
										<td hidden><b> <c:out value="${product.productCode}"></c:out></b></td>
										<td><b> <a href="createDeposit?productId=${product.id}&type=NRI&productType=${productType}&name=${product.productName}"><c:out value="${product.productName}"></c:out></a></b></td>
<%-- 										<td><b> <a href="depositProducts?id=${endUserForm.id}"><c:out value="${product.productName}"></a></c:out></b></td> --%>
									</tr>
								</c:forEach>
							</c:if>
						</table>					


					</table>
				
				</div>
			
			</div>
		</div>
	</div>
</div>

<!-- <ul style="margin-left: 25px;"> -->
<%-- 				<li style="font-size: 1.25em; padding: 10px; border-bottom: 1px dotted #ccc;"><a href="regularDepositProducts"><spring:message code="label.fixedDeposit" /></a><br> --%>
<!-- 				<i style="font-size: 0.75em; color: #999;">- One time investment provides a higher rate of interest than a regular savings account</i></li> -->
				
<!-- 			</ul> -->