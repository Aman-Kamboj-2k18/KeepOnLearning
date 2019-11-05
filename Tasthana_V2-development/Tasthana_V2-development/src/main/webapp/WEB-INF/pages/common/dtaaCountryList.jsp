<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="right-container" id="right-container">
        <div class="container-fluid">
   
<div class="fd-list">
	<div class="header_customer">
		<h3>
			Foreign Countries for DTAA
		</h3>
	</div>

	<div class="fd-list-table">
	
	
		<span class="counter pull-right"></span>
		
					
		<div class="col-sm-6"><div style="float: left;margin-right: 80px;z-index: 100000 !important;">
			<a href="addingCountryForDTAA?menuId=${menuId}" class="btn btn-success"><spring:message
									code="label.addCountry" /></a> 
		</div></div>
		<div class="col-sm-6">
			<input type="text" id="kwd_search" value=""
				placeholder="Search Here..." style="float: right;" />
		</div>
<!-- 		<table class="table data jqtable example" id="my-table"> -->
		<table class="table example" id="my-table" style="width: 65%; margin-left: 65px;margin-top: -25px;">
			<thead>
				<tr>
					<th style="border-right: 1px solid #efefef;text-align: -webkit-center;">Id</th>
					<th style="text-align: -webkit-center;">Country</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${! empty dtaaCountryList}">
					<c:forEach items="${dtaaCountryList}" var="dtaaCountry">
						<tr>
							<td style="border-right: 1px solid #efefef;text-align: -webkit-center;"><c:out value="${dtaaCountry.id}"></c:out></td>
							<td style="text-align: -webkit-center;"><c:out value="${dtaaCountry.country}"></c:out></td>	
							
						</tr>
					</c:forEach>
				</c:if>
				
			</tbody>
		</table>
	</div>

</div>