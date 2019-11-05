<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="right-container" id="right-container">
	<div class="container-fluid">
		<span style="color: red; margin-left: 398px;font-size: 19px">${error}</span>
		<span style="color: #552cf1; margin-left: 398px;font-size: 19px">${information}</span>
		<div class="card_title">Assign Roles</div>
		<div class="card_content">
		<c:forEach items="${roles}" var="role">	
		<a href="selectLoggedInRole?role=${role.id}" class="card border-primary">
		  <div class="card-header">${role.roleDisplayName}</div>
		  <div class="card-body text-primary">
		    <h5 class="card-title"> Click here to login as ${role.roleDisplayName}</h5>
		  </div>
		</a>	
		
		</c:forEach>
		</div>
	</div>
</div>

<style>
	.card_content .card {
    float: left;
    width: 32%;
    border: 1px solid #358cce;
    display: inline-block;
    margin-right: 1.3%;
    padding: 0px 20px 15px;
    border-radius: 5px;
    background-color: #fff;
}
.card_content .card:hover{text-decoration: none;}
.card_content .card:last-child {
    margin: 0;
}
.card-header {
    border-bottom: 1px solid #000;
    margin: 0 -20px;
    padding: 12px 20px 10px;
    color: #fff;background-color: #358cce;
}

.card_content p {
    margin: 0;
}

	.card_title {
    padding: 10px 0;
    background: white;
    text-align: center;
    margin-bottom: 20px;
    font-size: 18px;
    font-weight: 600;
    border-radius: 5px;
    width: 98.5%;
    color: #358cce;
    border: 1px solid #358cce;
}
</style>
