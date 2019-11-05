

	<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
	<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
	<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<div class="right-container" id="right-container">
        <div class="container-fluid">
  
<section id="amsuccess">
	<div class="updated-am-success">
	
		
		<div class="col-sm-12 col-md-12 col-lg-12">
			<div class="col-sm-12 col-md-12" style="margin-bottom: 3em;" align="center">
			       <h2  class="successMsg" style="color:#0fad00">Success</h2>
			        <img src="<%=request.getContextPath()%>/resources/images/check-true.jpg" alt="Success Image">
      
       		 </div>
		</div>

		<table align="center" width="400">


			<tr>
				<td><b><spring:message code="label.transactionType" /></b></td>
				<td><spring:message code="label.approvalManagerDetails" /></td>
			</tr>

			<tr>
				<td><b>${transactionStatus}</b></td>
				<td><font color="green"><spring:message
							code="label.updateSuccessfully" /></font></td>
			</tr>

		</table>

	</div>
</section>