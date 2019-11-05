<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="right-container" id="right-container">
        <div class="container-fluid">
   
            
<div class="bank-emp-list">
	<div class="bank-emp-list-success">
		<div class="col-sm-12 col-md-12 col-lg-12">
			<div class="col-sm-12 col-md-12" style="margin-bottom: 3em;" align="center">
			       <h2  class="successMsg" style="color:#0fad00">Success</h2>
			        <img src="<%=request.getContextPath()%>/resources/images/check-true.jpg" alt="Success Image">
      
       		 </div>
		</div>
		<table align="center" width="400">


			
				<td><b><spring:message code="label.transactionType" /></b></td>
				<td><spring:message code="label.bankDetails" /></td>
			</tr>

			<tr>
				<td><b><spring:message code="label.status" /></b></td>
				<td><font color="green"><spring:message
							code="label.updateSuccessfully" /></font></td>
			</tr>

		</table>


	</div>
</div>

<style>
	.bank-emp-list-success {
    float: left;
    width: 100%;
    margin-top: 30px;
}
</style>