 <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<div class="right-container" id="right-container">
        <div class="container-fluid">
			
<div class="content">
			<div class="accounts">
				<h3><spring:message code="label.calender"/></h3>
			</div>
			<div class="savings">
				<h3><spring:message code="label.sI"/></h3>
			</div>
			<div class="New_calender user-calender">
				 <div id="demo" class="claendar-user">
					<div id="ca"></div>
				</div>
			</div>
			<div class="Savings_table">
				<div class="Accounts_table">
					
					<table border="0" style="text-align: center; margin: 0 auto; width: 90%;">
						<tr>
							<td width="70%" height="50" align="left" valign="middle"><span class="font1"><strong><spring:message code="label.accountBalance"/></strong></span></td>
							<td width="20%" align="left" valign="middle"><span class="font1">${model.customer.accountBalance}</span></td> 
						</tr>
					</table>
					
				</div>
			</div>
		</div>
		</div>
		<script>
    $('#ca').calendar({
        // view: 'month',
        width: 320,
        height: 320,
        // startWeek: 0,
        // selectedRang: [new Date(), null],
    
        onSelected: function (view, date, data) {
            console.log('view:' + view)
            console.log('date:' + date)
            console.log('data:' + (data || 'None'));
        }
    });

</script>