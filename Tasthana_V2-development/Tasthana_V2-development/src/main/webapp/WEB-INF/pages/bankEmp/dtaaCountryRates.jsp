<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="right-container" id="right-container">
        <div class="container-fluid">
   
<div class="fd-list">
	<div class="header_customer">
		<h3>
			DTAA Country wise Rates
		</h3>
	</div>

	<div class="fd-list-table">
	
	
		<span class="counter pull-right"></span>
		<div class="col-sm-12 col-md-12 col-lg-12">
			<input type="text" id="kwd_search" value=""
				placeholder="Search Country..." style="float: right;" />
		</div>
<!-- 		<table class="table data jqtable example" id="my-table"> -->
<%-- 		<div class="col-sm-6"><div style="float: left;margin-right: 80px;z-index: 100000 !important;"><a href="addDTAATaxRate" class="btn btn-success"><spring:message --%>
<%-- 									code="label.addCountryRates" /></a> </div></div> --%>
		<table class="table example" id="my-table" style="width: 65%; margin-left: 75px;">
			<thead>
				<tr>
					<th>Country</th>
					<th>Interest Rate %</th>
					<th>Effective Date</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${! empty dtaaCountryRateList}">
					<c:forEach items="${dtaaCountryRateList}" var="dtaaCountryRate">
						<tr country="${dtaaCountryRate.country}">
							<td><c:out value="${dtaaCountryRate.country}"></c:out></td>
							
							<fmt:formatNumber value="${dtaaCountryRate.taxRate}" pattern="#.##" var="taxRate"/>
							<td class="commaSeparated"><c:out value="${taxRate}"></c:out></td>
							<td><fmt:formatDate pattern="dd/MM/yyyy"
									value="${dtaaCountryRate.effectiveDate}" /></td>
							</td>
							
						</tr>
					</c:forEach>
				</c:if>
				

			</tbody>
		</table>
	</div>

</div>
