
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
  <div class="wrapper" id="wrapper">
    <div class="left-container" id="left-container">
      <!-- begin SIDE NAV USER PANEL -->
		<div class="left-sidebar" id="show-nav">
			<ul id="side" class="side-nav">
				
				<li class="panel">
					<a id="" href="createRole"> <i class="fa fa-bar-chart" aria-hidden="true"></i> <spring:message code="label.createRole" /> </a>
				</li>
				<li class="panel">
					<a id="panel2" href="#3" data-toggle="collapse" data-target="#rates"> <i class="fa fa-line-chart"></i> <spring:message code="label.notif" />
					<i class="fa fa-chevron-left pull-right" id="arow2"></i> </a>
					<ul class="collapse nav" id="rates">
						<li> <a href="bankEmpList"><i class="fa fa-angle-double-right"></i> <spring:message code="label.be" /></a></li>
						<li> <a href="appMngList"><i class="fa fa-angle-double-right"></i> <spring:message code="label.am" /></a></li>
						
					</ul>
				</li>
		
				
				
			</ul>
		</div>
      <!-- END SIDE NAV USER PANEL -->
    </div>