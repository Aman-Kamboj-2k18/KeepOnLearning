$(document).ready(function(){
	// Write on keyup event of keyword input element
	$("#kwd_search").keyup(function(){
		
		// When value of the input is not blank
        var term=$(this).val()
       
		if( term != "")
		{
			// Show only matching TR, hide rest of them
			$("#my-table tbody>tr").hide();
            $("#my-table td").filter(function(){
                   return $(this).text().indexOf(term ) > -1
            }).parent("tr").show();
            $('.jqtable').next().hide();
		}
		else
		{
			$("#my-table tbody>tr").hide();
			// When there is no input or clean again, show everything back
			for(i=1;i<=10;i++){//10 denotes number of table size
				$("#my-table tbody>tr:nth-child("+i+")").show();
			}
			var rowCount=$("#my-table tbody>tr").length;
			if(rowCount>10){
				$('.jqtable').next().show();
			}
			
			//ApplyPagination();
		}
		
	});
	
	
	function ApplyPagination(){
$.fn.simplePagination = function(options) {
			
			var defaults = {
				perPage: 10,
				containerClass: '',
				previousButtonClass: '',
				nextButtonClass: '',
				previousButtonText: 'Previous',
				nextButtonText: 'Next',
				currentPage: 1
			};

			var settings = $.extend({}, defaults, options);

			return this.each(function() {
				var $rows = $('tbody tr', this);
				var pages = Math.ceil($rows.length/settings.perPage);

				var container = document.createElement('div');
				var bPrevious = document.createElement('input');
				bPrevious.setAttribute('type', 'button');
				bPrevious.setAttribute('value', 'Previous');
				
				var bNext = document.createElement('input');
				bNext.setAttribute('type', 'button');
				bNext.setAttribute('value', 'Next');
				
				
				var of = document.createElement('span');

//				bPrevious.innerHTML = settings.previousButtonText;
//				bNext.innerHTML = settings.nextButtonText;
				
//				 var t = document.createTextNode("Previous");
//				 bPrevious.appendChild(t);
//				  document.body.appendChild(bPrevious);

				container.className = settings.containerClass;
				bPrevious.className = settings.previousButtonClass;
				bNext.className = settings.nextButtonClass;

				bPrevious.style.marginRight = '8px';
				bNext.style.marginLeft = '8px';
				container.style.textAlign = "center";
				container.style.marginBottom = '20px';

				container.appendChild(bPrevious);
				container.appendChild(of);
				container.appendChild(bNext);

				$(this).after(container);

				update();

				$(bNext).click(function() {
					if (settings.currentPage + 1 > pages) {
						settings.currentPage = pages;
					} else {
						settings.currentPage++;
					}

					update();
				});

				$(bPrevious).click(function() {
					if (settings.currentPage - 1 < 1) {
						settings.currentPage = 1;
					} else {
						settings.currentPage--;
					}

					update();
				});

				function update() {
					var from = ((settings.currentPage - 1) * settings.perPage) + 1;
					var to = from + settings.perPage - 1;

					if (to > $rows.length) {
						to = $rows.length;
					}

					$rows.hide();
					$rows.slice((from-1), to).show();

					of.innerHTML = from + ' to ' + to + ' of ' + $rows.length + ' entries';

					if ($rows.length <= settings.perPage) {
						$(container).hide();
					} else {
						$(container).show();
					}
				}
			});

		}
	}
	
	// Pagination for table
	(function($) {
ApplyPagination();
		

	}(jQuery));
	$(".example").simplePagination({
		previousButtonClass: "btn btn-primary",
		nextButtonClass: "btn btn-primary"
	});

	


	var inp = document.querySelectorAll(".commaSeparated");

    for (i = 0; i < inp.length; i++) {
     var input;
     if (inp[i].type == 'text') {
      var input = inp[i].value;

     } else {
      var input = inp[i].innerHTML;

     }
     var inpArr = input.toString().split(".");
     var inputDecimal = 0;
     if (inpArr.length > 0) {
      input = inpArr[0];

     }

     input = input.replace(/[\D\s\._\-]+/g, "");
     input = input ? parseInt(input, 10) : 0;

     if (inp[i].type == 'text') {
      if (inpArr[1] == undefined) {
       inp[i].value = input.toLocaleString("en-US");
      } else {
       inp[i].value = input.toLocaleString("en-US")
         + "." + inpArr[1];
      }

     } else {
      if (inpArr[1] == undefined) {
       inp[i].innerHTML = input
         .toLocaleString("en-US");
      } else {
       inp[i].innerHTML = input
         .toLocaleString("en-US")
         + "." + inpArr[1];
      }

     }

    }
		
});   