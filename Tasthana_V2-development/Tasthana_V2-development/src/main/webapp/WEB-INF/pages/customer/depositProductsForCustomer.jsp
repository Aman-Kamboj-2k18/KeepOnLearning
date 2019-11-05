<%@include file="taglib_includes.jsp"%>

<div class="right-container" id="right-container">
	<div class="container-fluid">



		<div class="Flexi_deposit">
			<div class="header_customer">
				<h3><spring:message code="label.openNewDeposit"/></h3>
			</div>
			<div class="flexi_table" style="margin: 25px;">
				<div class="col-md-6">
					<table align="center">
	
						<table class="table table-striped table-bordered" style="width: 85% !important; margin-left: 25px;">
							<th colspan="3">Products</th>						
							<c:if test="${! empty productConfigurationList}">
								<c:forEach items="${productConfigurationList}" var="product">

									<tr>
										<td hidden><b> <c:out value="${product.id}"></c:out></b></td>
										<td hidden><b> <c:out value="${product.productCode}"></c:out></b></td>
										<td><b> <a href="createDeposit?productId=${product.id}&type=${product.citizen}&productType=${productType}&name=${product.productName}"><c:out value="${product.productName}"></c:out></a></b></td>
										<%-- <td><b> <a href="newDeposit?productId=${product.id}&depositType=${productType}"><c:out value="${product.productName}"></c:out></a></b></td> --%>
									</tr>
								</c:forEach>
							</c:if>
						</table>
					</div>
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