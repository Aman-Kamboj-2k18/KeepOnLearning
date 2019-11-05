<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- To fetch the request url -->
<c:set var="req"
	value="${requestScope['javax.servlet.forward.request_uri']}" />
<c:set var="baseURL" value="${fn:split(req,'/')}" />
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-1.11.1.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui-1.11.1.js"></script>
		
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-ui.multidatespicker.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/states.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/prettify.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/lang-css.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/mdp.css">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/prettify.css">
		<script type="text/javascript">
		
		var dateFormat = function() {
		        var token = /d{1,4}|m{1,4}|yy(?:yy)?|([HhMsTt])\1?|[LloSZ]|"[^"]*"|'[^']*'/g,
		                timezone = /\b(?:[PMCEA][SDP]T|(?:Pacific|Mountain|Central|Eastern|Atlantic) (?:Standard|Daylight|Prevailing) Time|(?:GMT|UTC)(?:[-+]\d{4})?)\b/g,
		                timezoneClip = /[^-+\dA-Z]/g,
		                pad = function(val, len) {
		                        val = String(val);
		                        len = len || 2;
		                        while (val.length < len) val = "0" + val;
		                        return val;
		                };

		        // Regexes and supporting functions are cached through closure
		        return function(date, mask, utc) {
		                var dF = dateFormat;

		                // You can't provide utc if you skip other args (use the "UTC:" mask prefix)
		                if (arguments.length == 1 && Object.prototype.toString.call(date) == "[object String]" && !/\d/.test(date)) {
		                        mask = date;
		                        date = undefined;
		                }

		                // Passing date through Date applies Date.parse, if necessary
		                date = date ? new Date(date) : new Date;
		                if (isNaN(date)) throw SyntaxError("invalid date");

		                mask = String(dF.masks[mask] || mask || dF.masks["default"]);

		                // Allow setting the utc argument via the mask
		                if (mask.slice(0, 4) == "UTC:") {
		                        mask = mask.slice(4);
		                        utc = true;
		                }

		                var _ = utc ? "getUTC" : "get",
		                        d = date[_ + "Date"](),
		                        D = date[_ + "Day"](),
		                        m = date[_ + "Month"](),
		                        y = date[_ + "FullYear"](),
		                        H = date[_ + "Hours"](),
		                        M = date[_ + "Minutes"](),
		                        s = date[_ + "Seconds"](),
		                        L = date[_ + "Milliseconds"](),
		                        o = utc ? 0 : date.getTimezoneOffset(),
		                        flags = {
		                                d: d,
		                                dd: pad(d),
		                                ddd: dF.i18n.dayNames[D],
		                                dddd: dF.i18n.dayNames[D + 7],
		                                m: m + 1,
		                                mm: pad(m + 1),
		                                mmm: dF.i18n.monthNames[m],
		                                mmmm: dF.i18n.monthNames[m + 12],
		                                yy: String(y).slice(2),
		                                yyyy: y,
		                                h: H % 12 || 12,
		                                hh: pad(H % 12 || 12),
		                                H: H,
		                                HH: pad(H),
		                                M: M,
		                                MM: pad(M),
		                                s: s,
		                                ss: pad(s),
		                                l: pad(L, 3),
		                                L: pad(L > 99 ? Math.round(L / 10) : L),
		                                t: H < 12 ? "a" : "p",
		                                tt: H < 12 ? "am" : "pm",
		                                T: H < 12 ? "A" : "P",
		                                TT: H < 12 ? "AM" : "PM",
		                                Z: utc ? "UTC" : (String(date).match(timezone) || [""]).pop().replace(timezoneClip, ""),
		                                o: (o > 0 ? "-" : "+") + pad(Math.floor(Math.abs(o) / 60) * 100 + Math.abs(o) % 60, 4),
		                                S: ["th", "st", "nd", "rd"][d % 10 > 3 ? 0 : (d % 100 - d % 10 != 10) * d % 10]
		                        };

		                return mask.replace(token, function($0) {
		                        return $0 in flags ? flags[$0] : $0.slice(1, $0.length - 1);
		                });
		        };
		}();

		// Some common format strings
		dateFormat.masks = {
		        "default": "ddd mmm dd yyyy HH:MM:ss",
		        shortDate: "m/d/yy",
		        mediumDate: "mmm d, yyyy",
		        longDate: "mmmm d, yyyy",
		        fullDate: "dddd, mmmm d, yyyy",
		        shortTime: "h:MM TT",
		        mediumTime: "h:MM:ss TT",
		        longTime: "h:MM:ss TT Z",
		        isoDate: "yyyy-mm-dd",
		        isoTime: "HH:MM:ss",
		        isoDateTime: "yyyy-mm-dd'T'HH:MM:ss",
		        isoUtcDateTime: "UTC:yyyy-mm-dd'T'HH:MM:ss'Z'"
		};

		// Internationalization strings
		dateFormat.i18n = {
		        dayNames: [
		                "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat",
		                "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
		        ],
		        monthNames: [
		                "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec",
		                "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
		        ]
		};

		// For convenience...
		Date.prototype.format = function(mask, utc) {
		        return dateFormat(this, mask, utc);
		};
</script>
		<script type="text/javascript">

			var dates = [];
			function getWeek(date) {
				var days = [ 'Sunday', 'Monday', 'Tuesday', 'Wednesday',
						'Thursday', 'Friday', 'Saturday' ], prefixes = [ '1',
						'2', '3', '4', '5' ];
				return prefixes[Math.floor(date.getDate() / 7)];
			}

			$(document)
					.ready(
							function() {
								
								var currentDate = new Date();
								var year = currentDate.getFullYear();
						
								/* var lastYear=year;
								var month = currentDate.getMonth();
								var day = currentDate.getDate();
								var  yearAddDate = new Date(year , 11, 31)
								var diffDays = parseInt((yearAddDate - currentDate) / (1000 * 60 * 60 * 24)); 
								
								var i = 1;
								while (i < diffDays) {
									currentDate
											.setDate(currentDate.getDate() + 1);
									var week = getWeek(currentDate);

									if ((currentDate.getDay() == 6 && (week == "2" || week == "4"))
											|| currentDate.getDay() == 0) {
										var month = currentDate.getMonth() + 1;

										var day = currentDate.getDate();

										var year = currentDate.getFullYear();

										var date_ = month + "/" + day + "/"
												+ year;
										dates.push(date_);
									}
									i++;
								} */
								
								
								$('#mdp-demo').multiDatesPicker({
									// preselect the 14th and 19th of the current month
									minDate:new Date(),
									//changeYear: true,
									//addDates : dates,
									//removeDates : dates,
									onSelect: function(date){

										dates.push(date);
									},
					                	/* onChangeMonthYear: function(year, month, datepicker) {
					                	debugger;
					                	if(year!=lastYear){
					                		debugger;
					                		lastYear=year;
					                	$('#mdp-demo').multiDatesPicker('removeDates', dates);
					                	}
					                	var currentDate = new Date();
										var currentyear = currentDate.getFullYear();
										var month = currentDate.getMonth();
										var day = currentDate.getDate();
										var  yearAddDate = new Date(year + 5, month, day)
										if(year!=currentyear){
											currentDate=new Date(year,0,1);
											yearAddDate=new Date(year,11,31);
										}
										
										var diffDays = parseInt((yearAddDate - currentDate) / (1000 * 60 * 60 * 24)); 
					                	
					                	 dates = [];
										var i = 1;
										while (i < diffDays) {
											currentDate
													.setDate(currentDate.getDate() + 1);
											var week = getWeek(currentDate);

											if ((currentDate.getDay() == 6 && (week == "2" || week == "4"))
													|| currentDate.getDay() == 0) {
												var month = currentDate.getMonth() + 1;

												var day = currentDate.getDate();

												var year = currentDate.getFullYear();

												var date_ = month + "/" + day + "/"
														+ year;
												dates.push(date_);//1
											}
											i++;
										}
					                	$('#mdp-demo').multiDatesPicker('addDates', dates);//2
					                } */
								});
								
							});

			
			
			function toTimestamp(strDate) {
				var todayTime = strDate;
				var month = todayTime.getMonth() + 1;
				var day = todayTime.getDate();
				var year = todayTime.getFullYear();
				var date_ = month + "/" + day + "/" + year;
				return date_;
			}
		
		$(function() {
			prettyPrint();
		});
		
		
	
		function getDates(year,state,branchCode){


			$("#mdp-demo").datepicker(
                    "change",
                    { maxDate:new Date(year,11,31) }
            );
			
			$("#mdp-demo").datepicker(
                    "change",
                    { minDate:new Date(year,0,1) }
            );

			var selectedYear=year;
			$('#mdp-demo').multiDatesPicker('removeDates', dates);
			 $.ajax({  
				    type: "GET",  
				    url: '<%=request.getContextPath()%>/common/branchHolidayConfiguration?year='+parseInt(year)+'&state='+state+'&branchCode='+branchCode, 
				    contentType: "application/json",
				    dataType: "json",
				    async:true,
				    success: function(response){ 

				    	var dateArray = response.date;
				    	 if(dateArray != undefined){
				    		 dates = [];
				    	for(var i = 0; i< dateArray.length ;i++){
				    		var date=new Date(dateArray[i]);
				    		var month = date.getMonth() + 1;

							var day = date.getDate();

							var year_ = date.getFullYear();

							var date_ = month + "/" + day + "/"
									+ year_;
							dates.push(date_);
				    		//dates.push(date);
				    	}
				    	//dates.push(dateArray);
				    	$('#mdp-demo').multiDatesPicker('addDates', dates);
				    	
				    	}else{
				    		dates=[];

				    		var currentDate = new Date();
							//var year = currentDate.getFullYear();
							var lastYear=selectedYear;
							var currentDate = new Date();
							var currentyear = currentDate.getFullYear();
							var month = currentDate.getMonth();
							var day = currentDate.getDate();
							currentDate=new Date(currentyear, month, day)
							var  yearAddDate = new Date(currentyear, 11, 31)
							if(selectedYear!=currentyear){
								currentDate=new Date(selectedYear,0,1);
								yearAddDate=new Date(selectedYear,11,31);
							}
							
							var diffDays = parseInt((yearAddDate - currentDate) / (1000 * 60 * 60 * 24)); 
							
							var i = 0;
							while (i < diffDays) {
								currentDate
										.setDate(currentDate.getDate() + 1);
								var week = getWeek(currentDate);

								if ((currentDate.getDay() == 6 && (week == "2" || week == "4"))
										|| currentDate.getDay() == 0) {
									var month = currentDate.getMonth() + 1;

									var day = currentDate.getDate();

									var year = currentDate.getFullYear();

									var date_ = month + "/" + day + "/"
											+ year;
									dates.push(date_);
								}
								i++;
							}
							$('#mdp-demo').multiDatesPicker('addDates', dates);
				    	}
				    
				    },  
				    error: function(e){  
				    	debugger
				    	
				    }  
				  });  
			 
		}
		
		
		
		
		
		function getDatess(){
			debugger;
			var year = $('#year').val();
			var state = $('#state').val();
			if(state=="Select"){
				alert('Please select state');
				return false;
			}
			var branchCode = $('.myCheck:checked').val();
			if(branchCode==undefined){
				alert('Please select branch');
				return false;
			}
			getDates(year,state,branchCode);
			
		}
		</script>
		
		<div class="right-container" id="right-container">
	<div class="container-fluid">



		<div class="Flexi_deposit">

			<div class="header_customer">
				<h3>
					Holiday Configuration</h3>
			</div>
			<div class="Success_msg">
				<div class="successMsg" style="text-align: center; color: red;">
					${error}</div>
					<div class="successMsg" style="text-align: center; color: green;">
					${sucess}</div>
			</div>
			<div class="flexi_table" style="margin-top: 7px;">
			<form:form  commandName="holidayConfiguration" method="post" id="holidayConfiguration" class="form-horizontal" autocomplete="off">
					<input type="hidden" name="menuId" value="${menuId}" id="menuId"/>
					<div class="col-md-12">
					<div class="form-group col-md-4">
						<label class="col-md-3 control-label" style="padding-top: 16px;">Year<span
								style="color: red">*</span></label>
								<div class="col-md-9">
								
								<select onchange="getDatess();" id="year" name="year"  class="myform-control">
							<%-- 	<form:option value="select">Select</form:option> --%>
								<option value="2019">2019</option><option value="2020">2020</option>
								<option value="2021">2021</option><option value="2022">2022</option>
								<option value="2023">2023</option><option value="2024">2024</option>
								<option value="2025">2025</option><option value="2026">2026</option>
								<option value="2027">2027</option><option value="2028">2028</option>
								<option value="2029">2029</option><option value="2030">2030</option>
								<option value="2031">2031</option><option value="2032">2032</option>
								<option value="2033">2033</option><option value="2034">2034</option>
								<option value="2035">2035</option><option value="2036">2036</option>
								<option value="2037">2037</option><option value="2038">2038</option>
								<option value="2039">2039</option><option value="2040">2040</option>
								<option value="2041">2041</option><option value="2042">2042</option>
								<option value="2043">2043</option><option value="2044">2044</option>
								<option value="2045">2045</option><option value="2046">2046</option>
								<option value="2047">2047</option><option value="2048">2048</option>
								<option value="2049">2049</option><option value="2050">2050</option>
								</select>
								</div>
								</div>
					<div class="form-group col-md-6" id="states">
							<label class="col-md-3 control-label" style="padding-top: 16px;">State<span style="color: red">*</span></label>
							
							<div class="col-md-6">
							<form:select id="state" path="state"
								class="input myform-control"  onChange="myFunction()">
								<form:option value="Select"></form:option>
							</form:select>

								<script>

								populateStates("state");
								</script>
							

							
								 
				</div>
				</div>
							<div style="clear: both; height: 10px;"></div>
				
				<table class="table table-bordered"   align="center" id="my-table">
				
				
				 
					<thead>
						<tr>
					    	<th></th>
							<th><spring:message code="label.branchName"/></th>
							<th><spring:message code="label.branchCode"/></th>
						     <th>Area</th>
						     <th>City/Town</th>
							<th>Urban/Rural</th>
							
							
<!-- 							<th>Edit</th> -->
						</tr>
					</thead>
					</table> 
					
					<%-- <tbody>
					 
						
							<c:forEach items="${branch}" var="list">
							   
								<tr>
									
									<td><input type="checkbox" name="type" />  </td>
									<td><c:out value="${list.branchName}"></c:out></td>
									<td><c:out value="${list.branchCode}"></c:out></td>
								
									<td><c:out value="${list.urbanOrRular}"></c:out></td>
									<td><c:out value="${list.cityOrTown}"></c:out></td>
									<td><c:out value="${list.area}"></c:out></td>
									<td><a href="editCustomerCategory?id=${list.id}"
											class="btn btn-primary"><spring:message
													code="label.edit" /></a></td>
								</tr>
							</c:forEach>
						
	
					</tbody>
					</c:if> --%> 
					
				
			
				
				<div style="clear: both; height: 10px;"></div>
				<div class="form-group">
							<label class="col-md-3 control-label" style="padding-top: 16px;">Date<span
								style="color: red">*</span></label>
							<div class="col-md-6">


								<%-- <form:input id="calDate" readonly="true"
									placeholder="Select Date" path="dates"
									class="myform-control" /> --%>
									<input id="mdp-demo" readonly="true"
									placeholder="Select Date" name="dates"
									class="myform-control" />
									

							</div>
							<div style="clear: both; height: 25px;"></div>
							<div class="col-md-11" style="text-align: -webkit-center;">
								<input type="submit" value="Confirm" onclick="check()" class="btn btn-primary" />
							</div>
						</div>

					</div>

				</form:form>

					</div>
			
			
			</div>
			
			
				
				
			</div>
</div>
		
		<script>
		
		function myFunction() {
		
			var state = document.getElementById('state').value;
		
			 var dataString = 'state='+state;
         
	
	 var data ='';
	// delete all rows except the first row from the table
		$("#my-table").find("tr:gt(0)").remove();
	  $.ajax({  
	    type: "GET",  
	    url: "<%=request.getContextPath()%>/common/getState", 
	    contentType: "application/json",
	    dataType: "json",
	    data: dataString,
     async: false,
	    success: function(response){
	    	

	    	var trHTML = '';
	    	for(i=0; i< response.length; i++){
		    	var data = response[i];
		    	
		    	trHTML += '<tr><td><input type="radio" onchange="getDatess();" class="myCheck" value="' + data.branchCode
				   + '" name="type"  /> </td><td>' + data.branchName  + '</td><td>' + data.branchCode
				   + '</td><td>' +data.area + '</td><td>' +data.cityOrTown + '</td><td>' +data.urbanOrRural + '</td></tr>'; 
		    	 
	    	}
	    	$('#my-table').append(trHTML);
	    },  
	    error: function(e){  
	    	
	    	 $('#error').html("Error occured!!")
	    	 
	    }  
	  });  
	return data;
	}  



	    	
       
		function check(){
        		 debugger;
        		 
        		//Reference the Table.
        		 var myArray = new Array;
      	        var grid = document.getElementById("my-table");
      	 
      	        //Reference the CheckBoxes in Table.
      	        var checkBoxes = grid.getElementsByTagName("INPUT");
      	               	 
      	        //Loop through the CheckBoxes.
      	        for (var i = 0; i < checkBoxes.length; i++) {
      	            if (checkBoxes[i].checked) {
      	                var row = checkBoxes[i].parentNode.parentNode;
      	                myArray.push(row.cells[2].innerHTML);
      	                
      	            }
      	        }
      	 
      	        //Display selected Row data in Alert Box.
      	        
      	
    	           
    	            $("#holidayConfiguration").attr("action", "holidayConfiguration?myArray="+myArray);
    	            $("#holidayConfiguration").submit();
		}
		
		
		
		 function checkUncheckAll() 
		 {

			 if(document.getElementById('selectAll').checked ==  1)
			 {
				 selectAll();
				

			 }
			 else
			 {
				 UnSelectAll();
				
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
		


