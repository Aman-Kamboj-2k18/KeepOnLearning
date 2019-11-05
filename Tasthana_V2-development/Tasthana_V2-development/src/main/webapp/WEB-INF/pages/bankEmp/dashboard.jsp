<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%-- <script src="<%=request.getContextPath()%>/resources/js/loginDate.js"></script> --%>

<div class="right-container" id="right-container">
<div class="container-fluid">
   
<div class="set-rate">
			<div class="header_customer">
				<h3><%-- <spring:message code="label.confirmAddedRate"/> --%>Tasthana means as flexible as the stalk of a lotus</h3>
			</div>
	<div class="flexi_table" style="padding:25px;">
		<div class="col-md-12 form-group">
			<p>Tasthana from the house of Annona is an innovative deposit product today. This product encompasses all types of deposits under one roof. This is an innovation over the existing current deposit.</p>
<!-- 			 This means if you have a regular deposit where you open -->
<!-- 			it onetime and don't touch it, it will go on till maturity. If the customer now wants to make it to recurring, he/she can do it. -->
<!-- 			Similarly, it can be converted back to regular deposit. Also tenure can be modified and top-up can be done. -->
			
<!-- 			<p>Withdrawals can be done for regular deposits and recurring deposits.</p> -->
			
<!-- 			<p>Annuity products have enriched our product. There are also sweep deposits which get combined if the interest rate is same. -->
<!-- 			Thus, reconciliations become very easy. Annuity products have multiple beneficiaries and varying amounts.</p> -->
			
<!-- 			<p>TDS and Interest are calculated automatically. It also takes multiple deposits and computes TDS.</p> -->
			
<!-- 				<p>Our deposit wise configuration comes under these heads.  -->
<!--                 	<ul class="ul_01" style="margin-top: -15px;"> -->
<!--                 		<li><span class="ti-check-box1">&#8212;</span> Amount slab - Minimum and Maximum</li> -->
<!--                 		<li><span class="ti-check-box1">&#8212;</span> Deposit type - Regular, Recurring, Annuity and Tax Saving</li> -->
<!--                 	</ul> -->
<!--                 </p> -->
				
</div>
<div class="col-md-12 form-group" style="margin-top: -25px;">
<h3>Some Of The Salient Features Of Tasthana</h3></div>
   <div class="col-md-6 form-group">
   	<ol class="ul_01">
   		<li><span class="ti-check-box">&#x2611;</span> You can add or top-up to the principal amount and withdraw from it any number of time</li>
   		<li><span class="ti-check-box">&#x2611;</span> You can change the tenure any number of time</li>
   		<li><span class="ti-check-box">&#x2611;</span> You can break-up the interest received say each month and credit it to other accounts both within and outside the bank</li>
   		<li><span class="ti-check-box">&#x2611;</span> You can convert a Deposit Account to Recurring and vice-versa</li>
   		<li><span class="ti-check-box">&#x2611;</span> All Auto Debit Sweep Accounts are combined to one account. This is a huge help in reconciliation of accounts</li>
   		<li><span class="ti-check-box">&#x2611;</span> You can have a Consortium of Depositors (3 and more)</li>	
   		<li><span class="ti-check-box">&#x2611;</span> You will have a Corpus amount at the end of tenure</li>
   		<li><span class="ti-check-box">&#x2611;</span> You can withdraw from a Recurring Deposit</li>
   		<li><span class="ti-check-box">&#x2611;</span> Tasthana has Annuity Products with Gestation Period, with multiple beneficiaries and varying amounts credited</li>
   		<li><span class="ti-check-box">&#x2611;</span> Tasthana calculates Interest and TDS automatically each month/quarter/semi-annually/annually</li>
   		<li><span class="ti-check-box">&#x2611;</span> It posts accounting entries automatically</li>
   		<li><span class="ti-check-box">&#x2611;</span> Reports are generated and can be customised</li>	
   	</ol>
   </div>
   <div class="col-md-6 form-group">
   	
<div class="col-md-4 form-group">
<a href="calculator">Want to Earn<br>1 Crore</a>		
   </div>

<div class="col-md-4 form-group" style="border-left: 1px dotted #ccc; border-right: 1px dotted #ccc;">
<a href="<%=request.getContextPath()%>/resources/pdf/Deposits_AGameofNumbers.pdf" target="_blank">Deposits - A Game of Numbers</a></div>

<div class="col-md-4 form-group">
<a href="<%=request.getContextPath()%>/resources/pdf/Patent201641021201.pdf" target="_blank">About<br>The Patents</a></div>
<div style="clear:both; margin-bottom: 10px;"></div>
<iframe width="90%" height="250" src="https://www.youtube.com/embed/91T-nKDnL1A" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>

</div>
</div>
</div></div></div></div>

<style>

/* The Modal (background) */
.modal {
	display: none;
	position: fixed;
	z-index: 1;
	padding-top: 100px;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	overflow: auto;
	background-color: rgb(0, 0, 0);
	background-color: rgba(0, 0, 0, 0.4);
}

/* Modal Content */
.modal-content {
	padding: 20px;
	margin-top: 150px;
	margin-left: 283px;
	margin-top: :91px;
	border: 1px solid #888;
	width: 64%;
	height: 345px;
}

/* The Close Button */
.close {
	color: #aaaaaa;
	float: right;
	font-size: 28px;
	font-weight: bold;
}

.close:hover, .close:focus {
	color: #000;
	text-decoration: none;
	cursor: pointer;
}

.ti-check-box {
	color: #002664;
	font-size: 1.5em;
	font-weight: 900;
}

.ti-check-box1 {
	color: #f78b00;
	font-size: 1.5em;
	font-weight: 900;
	padding-top: 0;
}

.ul_01 {
	display: block;
    margin-block-start: 1em;
    margin-block-end: 1em;
    margin-inline-start: 0px;
    margin-inline-end: 0px;
    padding-inline-start: 40px;
    margin-top: -5px;
}
</style>
