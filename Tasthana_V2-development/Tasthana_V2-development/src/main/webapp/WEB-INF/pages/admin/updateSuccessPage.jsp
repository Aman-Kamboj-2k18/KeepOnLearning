<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="right-container" id="right-container">
        <div class="container-fluid">
			
<div class="role-page">

	<div class="col-sm-12 col-md-12 col-lg-12">
		<div class="col-sm-12 col-md-12 col-lg-12">
			<div class="col-sm-12 col-md-12" style="margin-bottom: 3em;" align="center">
			       <h2  class="successMsg" style="color:#0fad00">Success</h2>
			        <img src="<%=request.getContextPath()%>/resources/images/check-true.jpg" alt="Success Image">
      
       		 </div>
		</div>
		<br><br>
		<div class="sapce10"></div>
		<table align="center" width="400">



			<tr>
				<td style="padding-left: 70px;"><b><spring:message code="label.status" /></b></td>
				<td><font color="green"><spring:message
							code="label.updateSuccessfully" /></font></td>
			</tr>

		</table>
	</div>
	</div>
	</div>
<style>
	.role-page {
       margin: 0px auto;
    width: 100%;
    padding: 35px;
    margin-bottom: 100px;
}
.footer {
    display: inline-block;
    width: 100%;
    background: rgba(74, 74, 74, 0.89);
    margin-top: 13em;
}
</style>