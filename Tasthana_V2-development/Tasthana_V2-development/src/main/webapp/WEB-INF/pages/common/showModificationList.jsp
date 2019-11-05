<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<div class="right-container" id="right-container">
	<div class="container-fluid">


		<div class="list-of-rates">
			<div class="header_customer">
				<h3>Modification List for deposit: ${objList[0][0]}</h3>
			</div>

			<div class="list-of-rates-table">
				<div class="search_filter">

					<div class="space-10"></div>
					<div class="col-sm-12" id="dividerLine">
						<hr>
					</div>
					<c:if test="${! empty objList}">

						<table class="table table-bordered table-striped table-hover "
							align="center" id="my-table">
							<thead>
								<tr class="success">
									<th>Serial No</th>
									<th>Modified Date</th>
									<th>Modified By</th>
									<th>Modification No.</th>
								<!-- 	<th>Penalty(If Any)</th> -->
									<th>Select</th>
								</tr>
							</thead>
							<tbody>

								<c:forEach items="${objList}" var="obj" varStatus="status">
									<tr>

										<td><c:out value="${status.index+1}" /></td>
										<td><fmt:formatDate pattern="dd/MM/yyyy"
												value="${obj[1]}" /></td>
										<td><c:out value="${obj[2]}" /></td>
										<td><c:out value="${obj[3]}" /></td>
										<%-- <td><c:out value="${obj[4]}" /></td> --%>
										<c:if test="${status.index+1 < objList.size()}">
											<td><a
												href="compareWithModification?modificationNo=${obj[3]}&preModificationNo=${objList[status.index+1][3]}&menuId=${menuId}"><button>Show
														Modification Report</button></a></td>
										</c:if>

										<c:if test="${status.index+1 == objList.size()}">
											<td><a
												href="compareWithDeposit?modificationNo=${obj[3]}&depositId=${obj[0]}&menuId=${menuId}"><button>Show
														Modification Report</button> </a></td>
										</c:if>

										<%-- <td><a href="showModificationReport?depositId=${obj[0]}">Show Report</a></td>
									 --%>
									</tr>
								</c:forEach>


							</tbody>
							
						</table>
					</c:if>
                           <table align="center" class="f_deposit_btn">
							<tr>
								
								 <td><a href="searchCustForModificationReport?menuId=${menuId}" class="btn btn-danger"><spring:message
											code="label.back" /></a></td> 
								
							</tr>

						</table>
				</div>

			</div>
		</div>
	</div>
	<style>
.search_filter {
	display: flow-root;
	margin-bottom: 15px;
}
</style>
	<!-- <style>
		.table-bordered {
    border: 1px solid #BFBABA;
}
	  .table-bordered>tbody>tr>td, .table-bordered>tbody>tr>th, .table-bordered>tfoot>tr>td, .table-bordered>tfoot>tr>th, .table-bordered>thead>tr>td, .table-bordered>thead>tr>th{ border: 1px solid #C7C7C7;}
</style> -->