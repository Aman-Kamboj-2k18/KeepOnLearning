
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<div class="wrapper" id="wrapper">
	<div class="left-container" id="left-container">
		<!-- begin SIDE NAV USER PANEL -->
		<div class="left-sidebar" id="show-nav">
			<ul id="side" class="side-nav">
	            <li class="panel"><a id="" href="adminPage"> <i
						class="fa fa-home" aria-hidden="true"></i> Home
				</a></li>
				
				
				<li class="panel"><a id="" href="createRole"> <i
						class="fa fa-user-plus" aria-hidden="true"></i> Create Role
				</a></li>
				
				
				<li class="panel"><a id="" href="createUser"> <i
						class="fa fa-user-plus" aria-hidden="true"></i> <spring:message
							code="label.createUser" />
				</a></li>
				
				<li class="panel"><a id="" href="addBranchFromAdmin"> <i
						class="fa fa-user-plus" aria-hidden="true"></i> Add Branch
				</a></li>

				
				<li class="panel"><a href="bankEmpActiveList"><i
						class="fa fa-user-plus" aria-hidden="true"></i> <spring:message
									code="label.employeeActiveList" /></a></li>
				
					

				<!-- <li class="panel"> --><%-- <a id="panel2" href="#3"
					data-toggle="collapse" data-target="#rates"> <i
						class="fa fa-bell"></i> <spring:message code="label.notif" />
						<i class="fa fa-chevron-left pull-right" id="arow2"></i>
				</a> --%>
				<!-- 	<ul class="collapse nav" id="rates"> -->
						<%-- <li><a href="bankEmpList"><i
								class="fa fa-angle-double-right"></i> <spring:message
									code="label.be" /></a></li> --%>
						<%-- <li><a href="appMngList"><i
								class="fa fa-angle-double-right"></i> <spring:message
									code="label.am" /></a></li> --%>
									
									<!-- <li><a href="VPList"><i
								class="fa fa-angle-double-right"></i>Vice President</a></li>
 -->
				<!-- 	</ul>  -->
					
					<li class="panel"><a id="panel4" href="#2"
					data-toggle="collapse" data-target="#panelActiveList"><i
						class="fa fa-user"></i> Active
						List<i class="fa fa-chevron-left pull-right" id="arow4"></i>
				</a>
					<ul class="collapse nav" id="panelActiveList">

						<li><a href="bankEmpActiveList"><spring:message
									code="label.bankEmpSuspendList" /></a></li>
						<%-- <li><a href="approverActiveList"><spring:message
									code="label.approverManagerSuspendList" /></a></li> --%>
									<!-- <li><a href="VPActiveList">Vice President</a></li> -->
					</ul></li>

				<li class="panel"><a id="panel4" href="#2"
					data-toggle="collapse" data-target="#rates3"> <i
						class="fa fa-pause"></i> <spring:message
							code="label.suspend" /><i class="fa fa-chevron-left pull-right"
						id="arow4"></i> </a>
					<ul class="collapse nav" id="rates3">
						<li><a href="customerSuspendFromAdmin"><spring:message
									code="label.suspendCustlists" /></a></li>
						<li><a href="bankEmpSuspendFromAdmin"><spring:message
									code="label.bankEmpSuspendList" /></a></li>
<%-- 						<li><a href="approverManagerSuspend"><spring:message --%>
<%-- 									code="label.approverManagerSuspendList" /></a></li> --%>
					</ul></li>

				<li class="panel"><a id="panel4" href="#2"
					data-toggle="collapse" data-target="#rates5"><i
						class="fa fa-pause"></i> <spring:message
							code="label.suspendList" /><i
						class="fa fa-chevron-left pull-right" id="arow4"></i> </a>
					<ul class="collapse nav" id="rates5">
						<li><a href="customerSuspendList"><spring:message
									code="label.suspendCustlists" /></a></li>
						<li><a href="bankEmpSuspendList"><spring:message
									code="label.bankEmpSuspendList" /></a></li>
<%-- 						<li><a href="approverManagerSuspendList"><spring:message --%>
<%-- 									code="label.approverManagerSuspendList" /></a></li> --%>
					</ul></li>



				<li class="panel"><a id="panel4" href="#2"
					data-toggle="collapse" data-target="#panelPendingList"><i
						class="fa fa-clock-o"></i> Approval Pending
						List<i class="fa fa-chevron-left pull-right" id="arow4"></i>
				</a>
					<ul class="collapse nav" id="panelPendingList">

						<li><a href="bankEmpPendingList"><spring:message
									code="label.bankEmpSuspendList" /></a></li>
						<%-- <li><a href="approverPendingList"><spring:message
									code="label.approverManagerSuspendList" /></a></li> --%>
					</ul>
				</li>
					<li class="panel"><a id="" href="manageRole"> <i
						class="fa fa-user-plus" aria-hidden="true"></i> Manage Role
				</a></li>
				<li class="panel"><a id="" href="manageMenuPreference"> <i
						class="fa fa-user-plus" aria-hidden="true"></i> Manage Menu Preference
				</a></li>
					
					<li class="panel"><a id="panel4" href="#2"
					data-toggle="collapse" data-target="#panelDeveloper"><i
						class="fa fa-pause"></i> Developer Menu<i
						class="fa fa-chevron-left pull-right" id="arow4"></i> </a>
					<ul class="collapse nav" id="panelDeveloper">
						<li><a href="leftNavbarMenu">Add Menu</a></li>
						<li><a href="leftNavbarSubMenu">Add Sub Menu</a></li>
						<li><a href="findAllLeftNavbarMenu">Menu List</a></li>
						
						
					  
					</ul></li>
				


<!-- 				</li> -->

			</ul>
		</div>
		<!-- END SIDE NAV USER PANEL -->
	</div>