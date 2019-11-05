<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div class="right-container" id="right-container" >
	<div class="container-fluid" style="min-height:330px">



	<c:if test="${not empty customerAccountTypeNotFound}">
		<p style= "color:red">${customerAccountTypeNotFound}</p>
	</c:if>
	
	<div class="col-sm-12">
		
	</div>
	</div>

