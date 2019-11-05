
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="<%=request.getContextPath()%>/resources/js/highcharts.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/highcharts-3d.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/exporting.js"></script>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>


  <script> 
  var data = ${model.values};
 
  $(function () {
    // Set up the chart
    var chart = new Highcharts.Chart({
        chart: {
            renderTo: 'container',            
            type: 'column',
            options3d: {
                enabled: true,
                alpha: 15,
                beta: 15,
                depth: 50,
                viewDistance: 25
            }
        },
        title: {
            text: 'Deposit Graph'
        },      
        
        xAxis: {
            categories: ${categories},
            
            
            labels: {
                rotation: -45,
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        },
        yAxis: {
            min: 0,
            title: {
                text: 'Amount'
            }
        },
        tooltip: {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                '<td style="padding:0"><b>{point.y:.1f} </b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
            	dataLabels: {
                    enabled: true,
                    rotation: -90,
                    color: '#FFFFFF',
                    align: 'right',
                    format: '{point.y:.1f}', // one decimal
                    y: 10, // 10 pixels down from the top
                    style: {
                        fontSize: '10px',
                        fontFamily: 'Verdana, sans-serif'
                    }
                },
                borderWidth: 0,
                depth: 25
            }
        },
        series:data,
       
    });      
    
    function showValues() {
        $('#alpha-value').html(chart.options.chart.options3d.alpha);
        $('#beta-value').html(chart.options.chart.options3d.beta);
        $('#depth-value').html(chart.options.chart.options3d.depth);
    }

    // Activate the sliders
    $('#sliders input').on('input change', function () {
        chart.options.chart.options3d[this.id] = this.value;
        showValues();
        chart.redraw(false);
    });

    showValues();
});
  </script>
  <script type="text/javascript">
// Load google charts
google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(drawChart);

// Draw the chart and set the chart values
function drawChart() {
	
	//depositWiseTDS
	var depositWiseTDSList ="${depositWiseTDSList}";
	var size= "${depositWiseTDSList.size()}";
	
	
	var dataDeposit = google.visualization.arrayToDataTable([
       	['Deposit No', 'Current Balance'],
       	<c:forEach items="${depositList}" var="deposit">
       		["${deposit.accountNumber}", parseInt("${deposit.currentBalance}")],
    </c:forEach>
       		]);
	
 	var data = google.visualization.arrayToDataTable([
 	['Deposit No', 'TDS Amount'],
 	<c:forEach items="${depositWiseTDSList}" var="depositWiseTDS">
 		["${depositWiseTDS.depositNo}", parseInt("${depositWiseTDS.tdsAmount}")],
	</c:forEach>
 
	]);
// 	dataArr.push(["DepositNo", "TDS Amount"]);   
// 	<c:forEach items="${depositWiseTDSList}" var="depositWiseTDS">
// 		dataArr.push(["${depositWiseTDS.depositNo}", parseInt("${depositWiseTDS.tdsAmount}")] + ",");
// 	</c:forEach>
	
// 	 var data = google.visualization.arrayToDataTable([dataArr]);
	  
// 	for(var i=0;i<size; i++)
// 		{
// 		var a = depositWiseTDSList[i];
// 		alert("1: " + a[0]);
// 		alert("2: " + a[0][0]);
// 		//alert("DepositNo: "+ depositWiseTDSList[i]);
// 		//data.push([depositWiseTDSList[i].depositNo, depositWiseTDSList[i].tdsAmount]);
		
// 		}
	 
// 	 deposit.depositNo = '${depositWiseTDS.depositNo}';
// 	 deposit.tdsAmount = '${depositWiseTDS.tdsAmount}';
	
//   var data = google.visualization.arrayToDataTable([
//   ['Task', 'Hours per Day'],
//   ['Work', 8],
//   ['Friends', 2],
//   ['Eat', 2],
//   ['TV', 3],
//   ['Gym', 2],
//   ['Sleep', 7]
// ]);

  // Optional; add a title and set the width and height of the chart
//   var options = {'title':'Deposit Balance', 'width':800, 'height':300};
//   var options1 = {'title':'Last Year TDS', 'width':800, 'height':300};
  
  var options = {'title':'', 'width':800, 'height':300};
  var options1 = {'title':'', 'width':800, 'height':300};

  // Display the chart inside the <div> element with id="piechart"
  var chart = new google.visualization.PieChart(document.getElementById('piechart'));
  chart.draw(dataDeposit, options);
  // Display the chart inside the <div> element with id="piechart"
  var chart1 = new google.visualization.PieChart(document.getElementById('piechart1'));
  chart1.draw(data, options1);
}
</script>
<div class="right-container" id="right-container">
        <div class="container-fluid">

 <div class="fd-list">
 <div class="header_customer"> 
    <h3><spring:message code="label.fixedDepositList"/></h3>
   </div>
<div class="fd-list-table">	
			
			<span class="counter pull-right"></span>
				<div class="col-sm-12 col-md-12 col-lg-12">
					<input type="text" id="kwd_search" value="" placeholder="Search Here..."  style="float:right;"/>
				</div>
				
				
						<div class="col-md-6">
						<div class="form-group" style="margin-left: -24px; color: #d70000; font-size: 1.15em;">
							<label class="col-md-4 control-label"><spring:message
									code="label.logindate" /></label>
							<div class="col-md-4" style="text-align:left; margin-left: -65px;">
							<td style="width: 10%;"><fmt:formatDate pattern="dd/MM/yyyy" value="${loginDate}"/></td>
							
								
							</div>
						</div>

					</div>
					
				
			<table class="table data jqtable" id="my-table">
				<thead>
				<tr>
					<th style="width: 5%;"><spring:message code="label.fdID" /></th>
					<th style="width: 20%;"><spring:message code="label.accno" /></th>
					<th style="width: 15%;"><spring:message code="label.currentBalance" /></th>
					
					<th style="width: 15%;"><spring:message code="label.fdAmount" /></th>
					<th style="width: 10%;"><spring:message code="label.maturityDate"/></th>
					<th style="width: 10%; padding-right: 3px; padding-left: 20px;"><spring:message code="label.depositHolderStatus"/></th>
					<th style="width: 10%;"><spring:message code="label.contribution"/></th>
					<th style="width: 10%;"><spring:message code="label.createdDate" /></th>
					<th style="width: 10%;"><spring:message code="label.status" /></th>
						<th style="width: 5%; padding-right: 20px; padding-left: 1px;">Deposit Category</th>
				
				</tr>
			</thead>
			<tbody>
				<tr>
					<td colspan="10">
					<div style="max-width:120%; width:100%; height:140px;overflow: scroll; overflow-x: auto; overflow-y: scroll;">
					<table class="table data jqtable example" id="my-table">
					<c:if test="${! empty depositHolderList}">
					<c:forEach items="${depositHolderList}" var="depositHolder">
						<tbody>
						<tr>
							<td style="width: 5%;"><c:out value="${depositHolder.deposit.id}"></c:out></td>
							<td style="width: 20%;"><c:out value="${depositHolder.deposit.accountNumber}"></c:out></td>
							
							<fmt:formatNumber value="${depositHolder.deposit.currentBalance}" pattern="#.##" var="currentBalance"/>
							<td style="width: 15%;" class="commaSeparated">${currentBalance}</td>
							
							<fmt:formatNumber value="${depositHolder.deposit.depositAmount}" pattern="#.##" var="depositamount"/>
							<td style="width: 15%;" class="commaSeparated">${depositamount}</td>
							
							<td align="left" style="width: 10%; padding-right: 20px; padding-left: 1px;"><fmt:formatDate pattern="dd/MM/yyyy"
									value="${depositHolder.deposit.newMaturityDate}" />
							</td>	
							<td align="left" style="width: 10%; padding-right: 20px; padding-left: 1px;"><c:out value="${depositHolder.depositHolder.depositHolderStatus}"></c:out></td>
							<td style="width: 10%;"><c:out value="${depositHolder.depositHolder.contribution}"></c:out></td>
							<td style="width: 10%;"><fmt:formatDate pattern="dd/MM/yyyy" value="${depositHolder.deposit.createdDate}"/></td>
							<td style="width: 10%;"><c:out value="${depositHolder.deposit.status}"></c:out></td>
							<td style="width: 5%;"><c:if test="${empty depositHolder.deposit.depositCategory}">Regular</c:if><c:out value="${depositHolder.deposit.depositCategory}"></c:out></td>
						</tr>
					</c:forEach>
				</c:if>
					</td>
					
				</tr>	

				</tbody>
			</table>
			</div></td>
					
				</tr>
			</tbody>
			</table> 
			</div>

<div id="container"></div>
<div id="sliders" align="center">
    <table style="margin:0px auto;">
        <tr>
        	<td>Alpha Angle</td>
        	<td style="width: 7px;"></td>
        	<td style="padding: 7px;"><input id="alpha" type="range" min="0" max="45" value="15"/></td>
        	<td><span id="alpha-value" class="value"></span></td>
        </tr>
        <tr>
        	<td>Beta Angle</td>
        	<td style="width: 7px;"></td>
        	<td style="padding: 7px;"><input id="beta" type="range" min="0" max="45" value="15"/></td>
        	<td><span id="beta-value" class="value"></span></td>
        </tr>
        <tr>
        	<td>Depth</td>
        	<td style="width: 7px;"></td>
        	<td style="padding: 7px;"><input id="depth" type="range" min="20" max="100" value="50"/></td>
        	<td><span id="depth-value" class="value"></span></td>
        </tr>
    </table>
    
    <div style="clear: both; border-top: 4px dotted #ccc; margin: 35px 0 15px;"></div>
    <div class="col-md-12"><h3>Current Balance</h3>
    <div id="piechart"></div></div>
	<div style="clear: both; border-top: 4px dotted #ccc; margin: 25px 0;"></div>
    <div class="col-md-12"><h3>Last Year TDS</h3>
    <div id="piechart1"></div></div>
    
    <div style="clear: both; margin: 35px 0 0;"></div>
</div>

</div>


 </div></div>


<style>
#container, #sliders {
    min-width: 310px; 
    max-width: 800px;
    margin: 0 auto;
}
#container {
    height: 400px; 
}


</style>
	</body>
	