window.loginDateForFront;
/*$(document).ready(function(){
	
	  $.ajax({  
	    type: "GET",  
	    async: false,
	    url: "<%=request.getContextPath()%>/bnkEmp/loginDateForJsp", 
	    contentType: "application/json",
	    dataType: "json",
	   
    
	    success: function(response){  
	    	window.loginDateForFront = new Date(parseInt(response));
	    	alert("LoginDate For Front: " + window.loginDateForFront);
		    	
	    },  
	    error: function(e){  
	    	 $('#error').html("Error occured!!")
	    	  window.loginDateForFront = getTodaysDate();
	    }  
	  });  
	  
	alert( "abc:  " + window.loginDateForFront);
     
	        
}); */  

function getLoginDate() 
{
	alert("<%=request.getContextPath()%>");
	$.ajax({  
	    type: "GET",  
	    async: false,
	    url: "<%=request.getContextPath()%>/bnkEmp/loginDateForJsp", 
	    contentType: "application/json",
	    dataType: "json",
	  
	    success: function(response){  
	    	window.loginDateForFront = new Date(parseInt(response));
		    	
	    },  
	    error: function(e){  
	    	 $('#error').html("Error occured!!")
	    	  window.loginDateForFront = getTodaysDate();
	    }  
	  });  
	  
}

function getTodaysDate() 
{
	if(window.loginDateForFront==null || window.loginDateForFront=="")
		getLoginDate();
    return window.loginDateForFront;
}

function setTodaysDate(DateTime) 
{
	if(dt!=null || dt!="")
		window.loginDateForFront = DateTime.getDate();
	
    return window.loginDateForFront;
}

function getNewDate() 
{
	//  new Date(1980, 6, 31) --> example
	if(window.loginDateForFront==null || window.loginDateForFront=="")
		getLoginDate();
	
//	birthday.getFullYear();      // 1980
//	birthday.getMonth();         // 6
//	birthday.getDate();          // 31
//	birthday.getDay(); 
	
    return new Date(window.loginDateForFront.birthday.getFullYear(), birthday.getFullYear(), birthday.getDate());
}

