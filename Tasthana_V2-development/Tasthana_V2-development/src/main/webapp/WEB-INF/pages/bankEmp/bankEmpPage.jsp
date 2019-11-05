
<link href="<%=request.getContextPath()%>/resources/css/calendar.css"
	rel="stylesheet">
<script src="<%=request.getContextPath()%>/resources/js/jquery1.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/calendar.js"></script>


<style>
/* div#ca { */
/* 	background-color: #FFFFFF; */
/* 	border-radius: 6px; */
/* 	box-shadow: 0 1px 2px #C3C3C3; */
/* 	-webkit-box-shadow: 0px 0px 21px 2px rgba(0, 0, 0, 0.18); */
/* 	-moz-box-shadow: 0px 0px 21px 2px rgba(0, 0, 0, 0.18); */
/* 	box-shadow: 0px 0px 21px 2px rgba(0, 0, 0, 0.18); */
/* } */
</style>


<div class="right-container" id="right-container">
	<div class="container-fluid">
<div>
<div class="list-of-rates">
<!-- 	<div class="col-md-12" style="top:-150px"> -->
<%-- 					<jsp:include page="/WEB-INF/pages/bankEmp/viewRate.jsp" /> --%>
<!-- 				</div> -->
		<div class="content">
			<div class="row">
			<!-- 	<div id="demo" class="col-md-4" style="top:10px" >
					<div id="ca"></div>
				</div> -->
			
			</div>

		</div>
</div>
		<script>
			$('#ca').calendar({
				// view: 'month',
				width : 320,
				height : 320,
			
				// startWeek: 0,
				// selectedRang: [new Date(), null],

				onSelected : function(view, date, data) {
					console.log('view:' + view)
					console.log('date:' + date)
					console.log('data:' + (data || 'None'));
				}
			});
		</script>