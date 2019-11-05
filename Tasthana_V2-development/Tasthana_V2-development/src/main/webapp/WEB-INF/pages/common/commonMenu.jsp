
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<div class="wrapper" id="wrapper">
	<div class="left-container" id="left-container">
		<!-- begin SIDE NAV USER PANEL -->
		
		<!-- END SIDE NAV USER PANEL -->
		<div class="right-content">
		
		<h1>Welcome to Common DashBoard ! --   Request Method Type : ${method}</h1>
		
		<form:form onclick="submitForm()" id = "formSubmit">
		
		<button>Post </button>
		
		</form:form>
		
		
		</div>
	</div>
</div>
<script>
function submitForm(){
	alert("called submit");
	$("#formSubmit").attr("action",'${urlPattern}');
	$("#formSubmit").submit();

	
}



</script>





<style>
.right-content {
	width:100%;
	padding-left : 250px;
	margin-top: 160px
}

.left-container{
width: 100%;
}
</style>


