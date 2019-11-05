<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="right-container" id="right-container">
        <div class="container-fluid">
   
<div class="col-sm-12 col-md-9 col-lg-9 addvassi">
<div class="body_fixed" style="margin-top: 24px;">
	<div class="col-sm-12 col-md-12 col-lg-12 header_customer">
	<h3 >
			<spring:message code="label.depositRateImport"/>
	</h3>
	</div>
	
	<div class="Success_msg" style="margin-top: 30px">
				<div class="successMsg" style="text-align: center; color: red;">
					${error}</div>
			</div>
			
			
<div class="col-sm-12 col-md-12 col-lg-12" style="margin:0px auto;">

		<div id=""  style="margin-top: 68px; text-align: center;"><a href="<%=request.getContextPath()%>/resources/Download/DepositRate.xlsx" class="btn btn-success" download ><span class="glyphicon glyphicon-download-alt"></span>  Download Format</a>
		<a href="excelUpload" class="btn btn-info"> <span class="glyphicon glyphicon-upload"></span>  <spring:message code="label.importDepositRate"/></a></div><br>
		
	</div>
</div></div>
</div></div>




