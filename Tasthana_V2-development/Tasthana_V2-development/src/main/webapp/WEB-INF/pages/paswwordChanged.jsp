<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<div class="container1">
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
		<div class="success_msg">
			<div  class="successMsg" style="text-align:center;color:green;font-size: 18px;">${success}</div>
		</div>
    	<div class="row-fluid apps">
					
			
					<table class="table-bordered" align="center" width="500">
					
					
					<tr>  <td class="heading_text"><b>Transaction Id:</b></td><td>${model.endUserForm.transactionId}</td></tr>
					
				    
									<tr>
										<td><b>Transaction Type:</b></td>
										<td>
										Forgot password
										</td>																
									</tr>	
									
									<tr>
										<td><b>Status:</b></td>
										<td>
										<font color="green">Changed successfully.</font>
											
										</td>																
									</tr>	
									
					</table>
					<div class="container">
					<div class="col-md-12" style="margin-top: 95px;">
						<a href="<%=request.getContextPath()%>/">Go back to login page</a>
					</div>
					</div>
		</div>
		<style>
		td {
    padding: 10px;
}
		h3 {
    text-align: center;
    color: #797979;
    font-family: sans-serif;
}
.row-fluid.apps {
    margin-top: 45px;
        float: left;
    width: 100%;
}
	img.img-responsive.tastana_header {
	    width: 327px;
	}

.footer1 {
	    margin-top: 380px !important;
}
.row-fluid.apps a{
    background: tomato;
    padding: 6px;
    border-radius: 5px;
    color: white;
    }
	</style>			