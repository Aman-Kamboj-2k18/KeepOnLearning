/**
 * 
 */
function nameValidation(event){
		
		var valiName = document.getElementById(event.target.id);
		var regex = new RegExp("^[a-zA-Z ]+$");
		 var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
		
		if(valiName.value.length==0 && event.keyCode ==32)
			{
			  event.preventDefault();
			return false;
			}
		 if (!regex.test(key)) {
		        event.preventDefault();
		        return false;
		   }
		 }

function alphaNumaricValidation(event){
	
	var valiName = document.getElementById(event.target.id);
	var regex = new RegExp("^[a-zA-Z0-9 ]+$");
	 var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
	
	if(valiName.value.length==0 && event.keyCode ==32)
		{
		  event.preventDefault();
		return false;
		}
	 if (!regex.test(key)) {
	        event.preventDefault();
	        return false;
	   }
	 }

function amountValidation(event){
	
	var valiName = document.getElementById(event.target.id);
	var regex = new RegExp("^[0-9.]+$");
	 var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
	
	if(valiName.value.length==0 && event.keyCode ==32)
		{
		  event.preventDefault();
		return false;
		}
	 if (!regex.test(key)) {
	        event.preventDefault();
	        return false;
	   }
	 }


function numberValidation(event){
	
	var valiName = document.getElementById(event.target.id);
	var regex = new RegExp("^[0-9]+$");
	 var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
	
	if(valiName.value.length==0 && event.keyCode ==32)
		{
		  event.preventDefault();
		return false;
		}
	 if (!regex.test(key)) {
	        event.preventDefault();
	        return false;
	   }
	 }




