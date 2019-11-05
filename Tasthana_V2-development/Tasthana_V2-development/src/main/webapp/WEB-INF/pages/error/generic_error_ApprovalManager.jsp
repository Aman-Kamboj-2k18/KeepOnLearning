<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div class="right-container" id="right-container">
	<div class="container-fluid">

	<c:if test="${empty errCode}">
		<h1>Oops! Something went wrong!!</h1>
		<h3>${errorMessage}</h3>
	</c:if>

	<c:if test="${not empty errMsg}">
		<h2>${errMsg}</h2>
	</c:if>

	<div class="col-sm-12">
		<div align="center">
						<img src="<%=request.getContextPath()%>/resources/images/oops.png"  style="width: 30%; height: auto;">
		</div>
	</div>
