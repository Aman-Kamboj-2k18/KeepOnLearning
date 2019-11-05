<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="right-container" id="right-container">
	<div class="container-fluid">

		


		<div class="Flexi_deposit">

			<div class="header_customer" style="margin-bottom: 30px;">
				<h3>
					
				View Ledger</h3>
			</div>
			<div class="successMsg"
				style="text-align: center; color: red; font-size: 18px;">
				<h4>${error}</h4>

			</div>
		
			
			<div class="flexi_table">
				<form:form class="form-horizontal"  id="jounalForm" method="POST"
					name="ledgerReport" commandName="ledgerReportForm">
                   <form:hidden path="fdAccountNo"/>
                     <form:hidden path="fromDate"/>
                       <form:hidden path="toDate"/>
                       <div class="col-md-4"  style="margin-left: -40px;">
                       	<div class="form-group">
 						<label  id="lblSelectAll" class="col-md-8 control-label" style="margin-top: -4px;">Select All</label>
						<div class="col-md-4">
							<input type="checkbox"  id="selectAll" name="type" onclick="checkUncheckAll();" value= "Check All" > 							
						</div>
					</div>
					
                       </div>
                       
                       
                       <div class="col-md-4">
                       
                       <div class="form-group">
						<label class="col-md-8 control-label"  style="margin-top: -4px;"><spring:message
								code="label.ledgerSavingAccounts" /></label>
						<div class="col-md-4">
							<input type="checkbox"  id="ledgerSavingAccount" name="type" value="ledgerSavingAccount"> 
								
						</div>
					</div>
					
						<div class="form-group">
						<label class="col-md-8 control-label" style="margin-top: -4px;"><spring:message
								code="label.ledgerInterestAccount" /></label>
						<div class="col-md-4">
							<input type="checkbox"  id="ledgerInterestAccount" name="type" value="ledgerInterestAccount"> 
								
						</div>
					</div>
					
						<div class="form-group">
						<label class="col-md-8 control-label" style="margin-top: -4px;"><spring:message
								code="label.ledgerdepositAccount" /></label>
						<div class="col-md-4">
							<input type="checkbox" id="ledgerDepositAccount" name="type" value="ledgerDepositAccount"> 
								
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-8 control-label" style="margin-top: -4px;"><spring:message
								code="label.ledgerDDAccount" /></label>
						<div class="col-md-4">
							<input type="checkbox" id="ledgerDDAccount" name="type" value="ledgerDDAccount"> 
								
						</div>
					</div>
					</div>
					
					<div class="col-md-4">
						
						<div class="form-group">
						<label class="col-md-8 control-label" style="margin-top: -4px;"><spring:message
								code="label.ledgerCurrentAccount" /></label>
						<div class="col-md-4">
							<input type="checkbox" id="ledgerCurrentAccount" name="type" value="ledgerCurrentAccount"> 
								
						</div>
					</div>
					
						<div class="form-group">
						<label class="col-md-8 control-label" style="margin-top: -4px;"><spring:message
								code="label.ledgerChequeAccount" /></label>
						<div class="col-md-4">
							<input type="checkbox" id="ledgerChequeAccount" name="type" value="ledgerChequeAccount"> 
								
						</div>
					</div>
					
						<div class="form-group">
						<label class="col-md-8 control-label" style="margin-top: -4px;"><spring:message
								code="label.ledgerChargesAccount" /></label>
						<div class="col-md-4">
							<input type="checkbox" id="ledgerChargesAccount" name="type" value="ledgerChargesAccount"> 
								
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-md-8 control-label" style="margin-top: -4px;"><spring:message
								code="label.ledgerCashAccount" /></label>
						<div class="col-md-4">
							<input type="checkbox" id="ledgerCashAccount" name="type" value="ledgerCashAccount"> 
								
						</div>
					</div>
					<span id="checkBoxError" class="error" style="display: none;"><font
								color="red"><spring:message code="label.checkBoxError" /></font></span>
					
					

					<div class="form-group" style="margin-left: 50px;">
						<div class="col-md-8"> <a href="searchJournal" class="btn btn-info"><spring:message
									code="label.back" /></a>
									<input type="button" class="btn btn-info add-row" value="View List"
							onclick="ledgerList()">
							
						</div>
					</div>
			</div>
					
			
				</form:form>
			</div>

		</div>
		
		<script>
		
		function ledgerList(){
			 
			
			
			
			var myArray = new Array;
			
			$("input:checkbox[name=type]:checked").each(function(){
				myArray.push($(this).val());
			});
		
			var length = myArray.length;
			if(length == 0){
				
				document.getElementById('checkBoxError').style.display = 'block';
				return false;
			}
			else{
				document.getElementById('checkBoxError').style.display = 'none';
			}
			
			$("#jounalForm").attr("action", "ledgerList?myArray="+myArray);
			$("#jounalForm").submit();
	
		}
		


		 function checkUncheckAll() 
		 {

			 if(document.getElementById('selectAll').checked ==  1)
			 {
				 selectAll();
				 document.getElementById('lblSelectAll').innerHTML = 'Unselect all';
			 }
			 else
			 {
				 UnSelectAll();
				 document.getElementById('lblSelectAll').innerHTML = 'Select all';
			 }
			
		 }
		    
		 function selectAll()
		 {
		        var items = document.getElementsByName('type');
		        for (var i = 0; i < items.length; i++) 
		        {
		            if (items[i].type == 'checkbox')
		                items[i].checked = true;
		        }
		  }
	
	    function UnSelectAll()
	    {
	        var items = document.getElementsByName('type');
	        for (var i = 0; i < items.length; i++) 
	        {
	            if (items[i].type == 'checkbox')
	                items[i].checked = false;
	        }
	    }	
	    
</script>
	