<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
	<div class="left-sidebar" id="show-nav">
			<ul id="side" class="side-nav">
			<c:forEach items="${menus}" var="menu">
					<c:if test="${menu.childMenuItems.size() > 0}">
					<li class="panel"><div><a id="${menu.id}" permissions="${menu.permissions}" href="#"
					data-toggle="collapse" data-target="#panelDeveloper${menu.id}"><i
						class="fa fa-pause"></i> ${menu.name}<i
						class="fa fa-chevron-left pull-right" id="arow4"></i> </a></div>
						<ul class="collapse nav" id="panelDeveloper${menu.id}">
						<c:forEach items="${menu.childMenuItems}" var="childMenu">
						
						<%--  <c:forEach items="${menu.permission}" var = "permission">
						<h6 style="color: white;">Permission : ${permission.action}</h6>
						</c:forEach>  --%>
						
						<li><a id="menu_${childMenu.id}" permissions="${childMenu.permissions}" href="${childMenu.urlPattern}?menuId=${childMenu.id}">${childMenu.name}</a></li>
						
						</c:forEach>
						</ul></li>
					</c:if>
					<c:if test="${menu.childMenuItems.size() == 0 && menu.menu  ==  null}">
					<li class="panel"><a id="menu_${menu.id}" permissions="${menu.permissions}" href="${menu.urlPattern}?menuId=${menu.id}"
					data-toggle="collapse" data-target="#panelDeveloper${menu.id}"><i
						class="fa fa-pause"></i> ${menu.name}</a>
						<%--<c:forEach items="${menu.permission}" var = "permission">
						 <h6 style="color: white;">Permission  : ${permission.action}</h6>
						</c:forEach> --%>
					</c:if>
			
			
			
			</c:forEach>
			
		

			</ul>
		</div>