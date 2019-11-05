<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

	
<div class="right-container" id="right-container">
	<div class="container-fluid">


			<div class="list-of-rates">
			<div class="header_customer">
				<h3>Day End Process</h3>
			</div>

			<form:form action="saveDayEndProcess" method="post"
				class="form-horizontal" commandName="reportForm">
<input type="hidden" name="menuId" value="${menuId}" id="menuId"/>
						<div class="form-group col-md-offset-12 col-md-12" style="margin-top: 25px;" >
							
			<label id="lblDate" class="col-md-offset-6 col-md-6"></label>		

			<div style="clear: both; height: 25px;"></div>
			<div class="col-md-offset-6 col-md-6">

				 
				 <div class="successMsg" style="color: #191970;">${succMsg}
					</div>
					
				<input type="submit" size="3"
									value="Proceed"
									class="btn btn-primary">
			
			</div></div>

</form:form>
		</div>
	</div>
	<style>
.search_filter {
	display: flow-root;
	margin-bottom: 15px;
}
</style>
	<!-- <style>
		.table-bordered {
    border: 1px solid #BFBABA;
}
	  .table-bordered>tbody>tr>td, .table-bordered>tbody>tr>th, .table-bordered>tfoot>tr>td, .table-bordered>tfoot>tr>th, .table-bordered>thead>tr>td, .table-bordered>thead>tr>th{ border: 1px solid #C7C7C7;}
</style> -->

<script>

			
	$(document)
			.ready(
					function() {

					
						var today = getTodaysDate();
						var dd = today.getDate();
						var mm = today.getMonth()+1; //January is 0!
						var yyyy = today.getFullYear();

						if(dd<10) {
						    dd = '0'+dd
						} 

						if(mm<10) {
						    mm = '0'+mm
						} 

						today = dd + '/' + mm + '/' + yyyy;
						document.getElementById('lblDate').innerHTML = today;
					});
	
	function getTodaysDate()
	{
		var today = null;
		$.ajax({  
		    type: "GET",  
		    async: false,
		    url: "<%=request.getContextPath()%>/common/loginDateForJsp", 
		    contentType: "application/json",
		    dataType: "json",

		    success: function(response){  
		    //	window.loginDateForFront = new Date(parseInt(response));
		    	today = new Date(parseInt(response))
		    },  
		    error: function(e){  
		    	 $('#error').html("Error occured!!")
		    	 // window.loginDateForFront = getTodaysDate();
		    }  
		  });  
		return today;
	}

			
		</script>