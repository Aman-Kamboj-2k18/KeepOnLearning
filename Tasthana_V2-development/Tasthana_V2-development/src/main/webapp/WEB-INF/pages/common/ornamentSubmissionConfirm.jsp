<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="right-container" id="right-container">


	<div class="ornament-submission-confirm">
		<div class="header_customer" style="margin-bottom: 20px;">
			<h3>Ornament Submission</h3>
		</div>
		<form:form id="ornamentSubmission" 
			class="form-horizontal" method="post" commandName="ornamentsForm">
			<div class="ornament-submission-confirm-table"></div>

			<div class="form-group">
				<label class="col-md-4 control-label"><spring:message
						code="label.customerId" /></label>
				<div class="col-md-4">
					<form:input path="customerId" class="myform-control"
						value="${customer.id}" readonly="true"></form:input>
				</div>
			</div>

			<div class="form-group">
				<label class="col-md-4 control-label"><spring:message
						code="label.customerName" /></label>
				<div class="col-md-4">
					<form:input path="" class="myform-control"
						value="${customer.customerName}" readonly="true"></form:input>
				</div>
			</div>

			<div class="form-group">
				<label class="col-md-4 control-label">Date<span
					style="color: red">*</span></label>
				<div class="col-md-4">
					<form:input path="submissionDate" value="${todaysDate}"
						placeholder="Select Date" class="form-control"
						style="background: whitesmoke; cursor: pointer;" id="datepicker"
						required="true" />

				</div>

			</div>
			
            <div class="form-group" >
								<label class="col-md-4 control-label">Ornament<span
					style="color: red">*</span></label>
			<div class="col-md-4">
				<form:select id="ornament" path="ornament" class="form-control"
					onchange="setOrnament(this.value)">
					<form:option value="select" selected="true">
						<spring:message code="label.select" />
					</form:option>

					<c:forEach items="${ornaments}" var="list1">
						<option>${list1.ornament}</option>
					</c:forEach>
				</form:select>


			</div>
			</div>

			<div class="form-group">
				<label class="col-md-4 control-label">Weight(in gm)<span
					style="color: red">*</span></label>
				<div class="col-md-4">
					<form:input path="weight" class="myform-control" id="weight"
					onkeyup="getAmount()"	placeholder=" " autocomplete="off"  />
				</div>
			</div>


			<div class="form-group">
				<label class="col-md-4 control-label">Purity<span
					style="color: red">*</span></label>
				<div class="col-md-4">
					<form:input path="purity" class="myform-control" id="purity"
						placeholder=" " value="91.67" />
				</div>
			</div>

			<div class="form-group">
				<label class="col-md-4 control-label">Carat<span
					style="color: red">*</span></label>
				<div class="col-md-4">
					<form:input path="carat" class="myform-control" id="carat"
						placeholder=" " value="22" />
				</div>
			</div>

			<div class="form-group"> 
				<label class="col-md-4 control-label">Todays Gold Rate(/gm)<span
					style="color: red">*</span></label>
				<div class="col-md-4">
					<form:input path="goldRate" class="myform-control" id="goldRate"
						placeholder=" " autocomplete="off" />
				</div>
			</div>
			

			<div class="form-group">
				<label class="col-md-4 control-label">Amount<span
					style="color: red">*</span></label>
				<div class="col-md-4">
					<form:input path="price" class="myform-control" id="price"
						placeholder=" " autocomplete="off"/>
				</div>
			</div>

			<div class="form-group">
				<label class="col-md-4 control-label">Comment(Optional)</label>
				<div class="col-md-4">
					<form:input path="comment" class="myform-control"></form:input>
				</div>
			</div>

			<div class="form-group">
				<div class="col-md-offset-4 col-md-8">
					<input type="button" id="button" value="<spring:message code="label.add"/>"
						class="btn btn-primary" onclick="add()" />
				</div>
			</div>
       
       <div class="form-group">
				<label class="col-md-4 control-label">Total Amount<span
					style="color: red">*</span></label>
				<div class="col-md-4">
					<form:input path="totalPrice" class="myform-control" id="totalPrice"
						placeholder=" " autocomplete="off" />
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-md-4 control-label">Total Weight(in gm)<span
					style="color: red">*</span></label>
				<div class="col-md-4">
					<form:input path="totalWeight" class="myform-control" id="totalWeight"
						placeholder=" " autocomplete="off" />
				</div>
			</div>
			
			<div class="form-group col-md-12">


				<div style="clear: both; height: 5px;"></div>
			 <table class="table table-bordered"  align="center" id="my-table" >
			 <thead>
						<tr>
							<th>Ornament</th>
							<th>Price</th>
							<th>Weight</th>
						</tr>
					</thead>
					
			 </table>
			</div>
						</div>	
          <div class="form-group">
				<div class="col-md-offset-4 col-md-8">
					<input type="submit" value="save"
						class="btn btn-primary" onclick="return submitForm()" />
				</div>
			</div>

		</form:form>
	</div>
</div>
<style type="text/css">
table, th, td {
	border: 1px solid #cdcdcd;
}

table th, table td {
	padding: 10px;
	text-align: left;
}
</style>
<script>

/* var weight = document.getElementById('weight');
var button = document.getElementById('button');
button.onclick = add() {
	weight.value = '';
} */

function getAmount(){
	debugger;
		var weight= document.getElementById('weight').value;
		var goldRate = document.getElementById('goldRate').value; 
		var vall= weight*goldRate;;
		   vall=vall.toFixed(2);
		document.getElementById('price').value =vall;
		
}



function add() {
	debugger;
    var totalPrice = 0;
    var totalWeight= 0;
    if( document.getElementById('totalPrice').value!= "")
 		totalPrice = parseFloat(document.getElementById('totalPrice').value);

    var price = document.getElementById('price').value;
    totalPrice = totalPrice + parseFloat(document.getElementById('price').value);
    document.getElementById('totalPrice').value = totalPrice;
    
    if( document.getElementById('totalWeight').value!= "")
    	totalWeight = parseFloat(document.getElementById('totalWeight').value);
    
    var weight = document.getElementById('weight').value;
    totalWeight = totalWeight + parseFloat(document.getElementById('weight').value);
    document.getElementById('totalWeight').value = totalWeight;
    var ornament =  document.getElementById('ornament').value;
    var trHTML = '';
    trHTML = '<tr><td>' +ornament  + '</td><td>' + price
	   + '</td><td>' +weight + '</td></tr>'; 
   $('#my-table').append(trHTML);
   
   document.getElementById("weight").value = "";
   document.getElementById("price").value = "";
   $("#reset").on("click", function () {
	    $("#ornament").val('Select');
	});
   
}

function setOrnament(value) {

}

function submitForm(){
	debugger;
	 var myArray = new Array;
       var grid = document.getElementById("my-table");
       for (i = 1; i < grid.rows.length; i++) {

           // GET THE CELLS COLLECTION OF THE CURRENT ROW.
           var objCells = grid.rows.item(i).cells;

           // LOOP THROUGH EACH CELL OF THE CURENT ROW TO READ CELL VALUES.
           for (var j = 0; j < objCells.length; j++) {
             var  row =  objCells.item(j).innerHTML;
      
           }
           
            myArray.push(row);
       }
  
	$("#ornamentSubmission").attr("action", "ornamentSubmissionSave?myArray="+myArray);
    $("#ornamentSubmission").submit();
}


</script>


