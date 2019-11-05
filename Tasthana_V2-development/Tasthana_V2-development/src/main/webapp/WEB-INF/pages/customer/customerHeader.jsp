<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="author" content="banking">
<title>Tasthana - Customer</title>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<!-- Themes -->
<link href="<%=request.getContextPath()%>/resources/css/themeBlue.css"
	rel="stylesheet">
<link href="<%=request.getContextPath()%>/resources/css/themeOrange.css"
	rel="stylesheet">
<link href="<%=request.getContextPath()%>/resources/css/themeGreen.css"
	rel="stylesheet">
<link href="<%=request.getContextPath()%>/resources/css/themeRed.css"
	rel="stylesheet">
<link href="css/font-awesome.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/resources/css/admin.css"
	rel="stylesheet">
<link href="<%=request.getContextPath()%>/resources/css/style.css"
	rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Raleway"
	rel="stylesheet">
<script src="https://use.fontawesome.com/07b0ce5d10.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/search.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/countries.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/currencies.js"></script>

<!-- For calendar date -->
<link
	href="<%=request.getContextPath()%>/resources/css/datepicker.min.css"
	rel="stylesheet">
<script
	src="<%=request.getContextPath()%>/resources/js/datepicker.en.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/datepicker.js"></script>
</head>

<body>
	<!-- Header -->
	<div class="header_new">
		<div class="tasthana_header">
			<div class="container">
				<div class="col-md-6 col-sm-6 col-xs-6">
					<img
						src="<%=request.getContextPath()%>/resources/images/logo_header.png"
						style="width: 299px;" class="img-responsive logo_header">
				</div>
				<div class="col-md-6 col-sm-6 col-xs-6" align="center">
					<img
						src="<%=request.getContextPath()%>/resources/images/tasthana_header.PNG"
						class="img-responsive tastana_header">
				</div>
			</div>
		</div>
	</div>
	<!--=============================  NAVIGATION  =============================-->
	<!--top nav start=======-->
	<nav class="navbar navbar-inverse top-bar navbar-fixed-top">
		<div class="container-fluid ab_bg">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#myNavbar">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#"> <!-- <img src="" width="90%"> -->TASTHANA
				</a> <span class="menu-icon" id="menu-icon"><i class="fa fa-bars"
					aria-hidden="true" id="chang-menu-icon"></i></span>
			</div>
			<div class="collapse navbar-collapse navbar-right" id="myNavbar">
				<ul class="nav navbar-nav">
					<%-- <li class="dropdown"><a class="dropdown-toggle"
						data-toggle="dropdown" href="#"><i class="fa fa-paint-brush"
							aria-hidden="true"></i> <spring:message code="label.menuThemes" />
							<span class="caret"></span></a>
						<ul class="dropdown-menu cust-dropdown">
							<li><a href="themeChange?theme=themeRed"><spring:message
										code="label.red" /></a></li>
							<li><a href="themeChange?theme=themeBlue"><spring:message
										code="label.blue" /></a></li>
							<li><a href="themeChange?theme=themeGreen"><spring:message
										code="label.green" /></a></li>
							<li><a href="themeChange?theme=themeOrange"><spring:message
										code="label.orange" /></a></li>
						</ul></li> --%>
					<li class="dropdown"><a class="dropdown-toggle"
						data-toggle="dropdown" href="#"><i class="fa fa-globe"
							aria-hidden="true"></i> Languages <span class="caret"></span></a>
						<ul class="dropdown-menu cust-dropdown"
							style="height: 213px; overflow-y: scroll;">
							<li><a href="getLocaleLang?lang=en"
								<c:if test="${requestCurrentUser.prefferedLanguage == 'en'}"> selected="selected"</c:if>><i
									class="flags flag6"></i>English</a></li>
							<li><a href="getLocaleLang?lang=sp"
								<c:if test="${requestCurrentUser.prefferedLanguage == 'sp'}"> selected="selected"</c:if>><i
									class="flags flag2"></i>Spanish</a></li>
							<li><a href="getLocaleLang?lang=de"
								<c:if test="${requestCurrentUser.prefferedLanguage == 'de'}"> selected="selected"</c:if>><i
									class="flags flag3"></i>German</a></li>
							<li><a href="getLocaleLang?lang=fr"
								<c:if test="${requestCurrentUser.prefferedLanguage == 'fr'}"> selected="selected"</c:if>><i
									class="flags flag4"></i>French</a></li>
							<li><a href="getLocaleLang?lang=it"
								<c:if test="${requestCurrentUser.prefferedLanguage == 'it'}"> selected="selected"</c:if>><i
									class="flags flag5"></i>Italian</a></li>
							<li><a href="getLocaleLang?lang=ar"
								<c:if test="${requestCurrentUser.prefferedLanguage == 'ar'}"> selected="selected"</c:if>><i
									class="flags flag7"></i>Arabic</a></li>
							<li><a href="getLocaleLang?lang=pr"
								<c:if test="${requestCurrentUser.prefferedLanguage == 'pr'}"> selected="selected"</c:if>><i
									class="flags flag8"></i>Portugese</a></li>
							<li><a href="getLocaleLang?lang=ch"
								<c:if test="${requestCurrentUser.prefferedLanguage == 'ch'}"> selected="selected"</c:if>><i
									class="flags flag9"></i>Chinese</a></li>
							<li><a href="getLocaleLang?lang=jp"
								<c:if test="${requestCurrentUser.prefferedLanguage == 'jp'}"> selected="selected"</c:if>><i
									class="flags flag10"></i>Japnese</a></li>
							<li><a href="getLocaleLang?lang=rs"
								<c:if test="${requestCurrentUser.prefferedLanguage == 'rs'}"> selected="selected"</c:if>><i
									class="flags flag11"></i>Russian</a></li>
							<li><a href="getLocaleLang?lang=hn"
								<c:if test="${requestCurrentUser.prefferedLanguage == 'hn'}"> selected="selected"</c:if>><i
									class="flags flag12"></i>Hindi</a></li>
							<li><a href="getLocaleLang?lang=bu"
								<c:if test="${requestCurrentUser.prefferedLanguage == 'bu'}"> selected="selected"</c:if>><i
									class="flags flag13"></i>Bulgerian</a></li>
							<li><a href="getLocaleLang?lang=hu"
								<c:if test="${requestCurrentUser.prefferedLanguage == 'hu'}"> selected="selected"</c:if>><i
									class="flags flag14"></i>Hungerian</a></li>
							<li><a href="getLocaleLang?lang=cz"
								<c:if test="${requestCurrentUser.prefferedLanguage == 'cz'}"> selected="selected"</c:if>><i
									class="flags flag15"></i>Czhec</a></li>
							<li><a href="getLocaleLang?lang=uk"
								<c:if test="${requestCurrentUser.prefferedLanguage == 'uk'}"> selected="selected"</c:if>><i
									class="flags flag16"></i>Ukreninan</a></li>
							<li><a href="getLocaleLang?lang=cr"
								<c:if test="${requestCurrentUser.prefferedLanguage == 'cr'}"> selected="selected"</c:if>><i
									class="flags flag17"></i>Croatian</a></li>
						</ul></li>
					<li class="dropdown"><a class="dropdown-toggle"
						data-toggle="dropdown" href="#"><i class="fa fa-user"
							aria-hidden="true"></i> <spring:message code="label.welcome" />
							${requestCurrentUser.userName} <span class="caret"></span></a>
						<ul class="dropdown-menu cust-dropdown">
							<li><a
								href="editCustomerProfile?id=${requestCurrentUser.id}"><spring:message
										code="label.myProfile" /></a></li>

							<li><a href="<c:url value="/j_spring_security_logout"/>"><i
									class="fa fa-power-off"></i> Logout</a></li>
						</ul></li>
				</ul>
			</div>
		</div>
	</nav>
	<div class="wrapper" id="wrapper">
		<div class="left-container" id="left-container">
			<!-- begin SIDE NAV USER PANEL -->
			<div class="left-sidebar" id="show-nav">

				<ul id="side" class="side-nav">
					<li class="panel"><a href="user"><spring:message
								code="label.home" /></a></li>
					<li class="panel"><a href="getRateByCategory"><spring:message
								code="label.rateList" /></a></li>
								
					<li class="panel"><a href="openNewDeposit"><spring:message
								code="label.newDeposit" /></a>
					</li>
					<li class="panel"><a href="fdCustomerList"><spring:message
										code="label.listDeposit" /></a></li>
		
					<li class="panel"><a id="panel2" href="#3"
						data-toggle="collapse" data-target="#rates1"><spring:message
								code="label.fdChanges" /><i
							class="fa fa-chevron-left pull-right" id="arow2"></i></a>
						<ul class="collapse nav" id="rates1">
							<li><a href="fdList"><spring:message
										code="label.fixedDepositList" /></a></li>
							<li><a href="closeFDList"><spring:message
										code="label.closeFD" /></a></li>
							<li><a href="modifiedDeposits">Modification Report</a></li>

						</ul></li>

					<li class="panel"><a href="sweepConfiguration">Sweep Configuration</a></li>
					<li class="panel"><a href="fdListForFundTransfer">Payment</a></li>
					<li class="panel"><a href="fundTransfer">Fund Transfer</a></li>
					<li class="panel"><a href="#" data-toggle="collapse"
						data-target="#withdrawList">Withdraw<i
							class="fa fa-chevron-left pull-right" id="arow221"></i></a>
						<ul class="collapse nav" id="withdrawList">
							<li class="panel"><a href="withdrawPaymentList">Withdraw
									from Deposit</a></li>
							<li class="panel"><a href="withdrawFromSaving"><spring:message
										code="label.withdrawFromSaving" /></a></li>
							<li class="panel"><a href="fdCustomerListForWithdraw"><spring:message
										code="label.wihdrawList" /></a></li>
						</ul></li>

					<li class="panel"><a href="downloadDocument"><spring:message
								code="label.downloadDocuments" /></a></li>

					<li class="panel"><a href="autoDepositList"><spring:message
								code="label.autoDepositList" /></a></li>
					<li class="panel"><a id="panel2" href="#3"
						data-toggle="collapse" data-target="#reports1"><spring:message
								code="label.reports" /><i class="fa fa-chevron-left pull-right"
							id="arow2"></i></a>
						<ul class="collapse nav" id="reports1">
							<li class="panel"><a href="fdListforHolderWiseResort">Holder
									wise report</a></li>

							<li class="panel"><a href="depositLists">Transaction <spring:message
										code="label.reports" /></a></li>
							<li class="panel"><a href="interestReport">Interest <spring:message
										code="label.reports" /></a></li>
							<li class="panel"><a href="reportSummary"><spring:message
										code="label.reports" /> Summary</a></li>

						</ul>
						</li>

					<li class="panel"><a href="withdrawOverdraft">Withdraw From Overdraft</a></li>
                   
                    <li class="panel"><a href="paymentOverdraft">Overdraft Payment</a></li>
				</ul>

			</div>
		</div>
		<script type="text/javascript">
			$(document).ready(function() {
				$("#panel1").click(function() {
					$("#arow1").toggleClass("fa-chevron-left");
					$("#arow1").toggleClass("fa-chevron-down");
				});

				$("#panel2").click(function() {
					$("#arow2").toggleClass("fa-chevron-left");
					$("#arow2").toggleClass("fa-chevron-down");
				});

				$("#panel3").click(function() {
					$("#arow3").toggleClass("fa-chevron-left");
					$("#arow3").toggleClass("fa-chevron-down");
				});

				$("#panel4").click(function() {
					$("#arow4").toggleClass("fa-chevron-left");
					$("#arow4").toggleClass("fa-chevron-down");
				});

				$("#panel5").click(function() {
					$("#arow5").toggleClass("fa-chevron-left");
					$("#arow5").toggleClass("fa-chevron-down");
				});

				$("#panel6").click(function() {
					$("#arow6").toggleClass("fa-chevron-left");
					$("#arow6").toggleClass("fa-chevron-down");
				});

				$("#panel7").click(function() {
					$("#arow7").toggleClass("fa-chevron-left");
					$("#arow7").toggleClass("fa-chevron-down");
				});

				$("#panel8").click(function() {
					$("#arow8").toggleClass("fa-chevron-left");
					$("#arow8").toggleClass("fa-chevron-down");
				});

				$("#panel9").click(function() {
					$("#arow9").toggleClass("fa-chevron-left");
					$("#arow9").toggleClass("fa-chevron-down");
				});

				$("#panel10").click(function() {
					$("#arow10").toggleClass("fa-chevron-left");
					$("#arow10").toggleClass("fa-chevron-down");
				});

				$("#panel11").click(function() {
					$("#arow11").toggleClass("fa-chevron-left");
					$("#arow11").toggleClass("fa-chevron-down");
				});

				$("#menu-icon").click(function() {
					$("#chang-menu-icon").toggleClass("fa-bars");
					$("#chang-menu-icon").toggleClass("fa-times");
					$("#show-nav").toggleClass("hide-sidebar");
					$("#show-nav").toggleClass("left-sidebar");

					$("#left-container").toggleClass("less-width");
					$("#right-container").toggleClass("full-width");
				});

			});
		</script>
		<!-- Dropdown hover effect -->
		<script>
			$(document).ready(
					function() {
						$(".dropdown").hover(
								function() {
									$('.dropdown-menu', this).not(
											'.in .dropdown-menu').stop(true,
											true).slideDown("fast");
									$(this).toggleClass('open');
								},
								function() {
									$('.dropdown-menu', this).not(
											'.in .dropdown-menu').stop(true,
											true).slideUp("fast");
									$(this).toggleClass('open');
								});
					});
		</script>