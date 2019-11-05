<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- To fetch the request url --> 
<c:set var="req" value="${requestScope['javax.servlet.forward.request_uri']}"/>      
<c:set var="baseURL" value="${fn:split(req,'/')}" />
<c:if test="${baseURL[1] == 'bnkEmp'}"><c:set var="back" value="bankEmp"/></c:if>
<c:if test="${baseURL[1] == 'users'}"><c:set var="back" value="users"/></c:if>
<div class="right-container" id="right-container">
        <div class="container-fluid">
      
			


<div class="Flexi_deposit">
		<div class="col-sm-12 col-md-12 col-lg-12">
			<div class="successMsg"
				style="text-align: center; color: green; font-size: 18px;">${success}</div>
		</div>

	<div class="fdsaved-table">
		<div class="notify successbox">
        <h1>Success!</h1>
        <span class="alerticon"><img src="<%=request.getContextPath()%>/resources/images/check_01.jpg" style="width: 12% !important;"></span>
   
		<table align="center" width="400">
				<td style="text-align:center"><b>status: Updated Successfully</b></td>
						
				
			</tr>

		</table>
		   </div>
	</div>
	</div></div>
	</div>
	<style>
	h1:before,
h1:after {
  content: "";
  position: relative;
  display: inline-block;
  width: 50%;
  height: 1px;
  vertical-align: middle;
  background: #f0f0f0;
}
h1:before {    
  left: -.5em;
  margin: 0 0 0 -50%;
}
h1:after {    
  left: .5em;
  margin: 0 -50% 0 0;
}
.notify {
    display: block;
    background: #fff;
    padding: 12px 18px;
    max-width: 600px;
    margin: 0 auto;
    cursor: pointer;
    -webkit-border-radius: 3px;
    -moz-border-radius: 3px;
    border-radius: 3px;
    margin-bottom: 20px;
    box-shadow: rgba(0, 0, 0, 0.3) 0px 1px 2px 0px;
}
h1 {
    font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;
    font-size: 2.5em;
    line-height: 1.5em;
    letter-spacing: -0.05em;
    margin-bottom: 20px;
    padding: .1em 0;
    color: #444;
    position: relative;
    overflow: hidden;
    white-space: nowrap;
    text-align: center;
}
h1 > span {
  display: inline-block;
  vertical-align: middle;
  white-space: normal;
}
	.successbox h1 {
	    color: #678361;
	    text-align: center;
	}

	.alerticon {
    display: block;
    text-align: center;
    margin-bottom: 10px;
}
		.Flexi_deposit {
    margin-bottom: 240px;
    padding:30px;
}
.fdsaved-table {
    margin-top: 52px;
}
	</style>