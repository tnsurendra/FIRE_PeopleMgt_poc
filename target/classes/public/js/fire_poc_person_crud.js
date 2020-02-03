$( document ).ready(function() {


var page = 1;
var current_page = 1;
var total_page = 0;
var is_ajax_fire = 0;


manageData();


/* manage data list */
function manageData() {
    $.ajax({
        dataType: 'json',
        url: url+'allPersons',
        data: {page:page}
    }).done(function(data){
    	total_page = Math.ceil(data.total/10);
    	current_page = page;


    	$('#pagination').twbsPagination({
	        totalPages: 10,
	        visiblePages: current_page,
	        onPageClick: function (event, pageL) {
	        	page = pageL;
				
                if(is_ajax_fire != 0){
					console.log(is_ajax_fire);
	        	  getPageData();
                }
	        }
	    });


    	manageRow(data.person);
        is_ajax_fire = 1;


    });


}


/* Get Page Data*/
function getPageData() {
	$.ajax({
    	dataType: 'json',
    	url: url+'allPersons',
    	data: {page:page}
	}).done(function(data){
		console.log(data);
		console.log(data.person);
		manageRow(data.person);
	});
}


/* Add new Item table row */
function manageRow(data) {
	var	rows = '';
	$.each( data, function( key, value ) {
	  	rows = rows + '<tr>';
			rows = rows + '<td hidden="true">'+value.id+'</td>';
	  	rows = rows + '<td>'+value.first_name+'</td>';	
rows = rows + '<td>'+value.last_name+'</td>';	
rows = rows + '<td>'+value.age+'</td>';	
rows = rows + '<td>'+value.favourite_colour+'</td>';
rows = rows + '<td>'+value.hobby+'</td>';	
rows = rows + '<td data-id="'+value.id+'">';
  rows = rows + '<button data-toggle="modal" data-target="#edit-item" class="btn btn-primary edit-item">Update</button> ';
        rows = rows + '<button class="btn btn-danger remove-item">Delete</button>';
        rows = rows + '</td>';
	  	rows = rows + '</tr>';
	});


	$("tbody").html(rows);
}


/* Create new Item */
$(".crud-submit").click(function(e){
    e.preventDefault();
    var form_action = $("#create-item").find("form").attr("action");
    var first_name = $("#create-item").find("input[name='first_name']").val();
    var hobby = $("#create-item").find("textarea[name='hobby']").val();
	var last_name = $("#create-item").find("input[name='last_name']").val();
	var age = $("#create-item").find("input[name='age']").val();
	var favourite_colour = $("#create-item").find("input[name='favourite_colour']").val();
	hobby=[hobby];
   if(first_name != '' && last_name != ''){
        $.ajax({
            dataType: 'json',
            type:'POST',
			contentType: "application/json",
            url: url + form_action,
            data:JSON.stringify({'first_name':first_name, 'last_name':last_name,'age':age,'favourite_colour':favourite_colour,'hobby':hobby})
        }).done(function(data){
    
            getPageData();
            $(".modal").modal('hide');
 toastr.success('Person Added Successfully.', 'Success Alert', {timeOut: 5000});
             
        });
   }else{
        alert('You are missing FirstName or LastName.')
    }


});


/* Remove Item */
$("body").on("click",".remove-item",function(){
    var id = $(this).parent("td").data('id');
    var c_obj = $(this).parents("tr");


    $.ajax({
        type:'DELETE',
        url: url +'delete/'+id,
            }).done(function(data){
        c_obj.remove();
        toastr.success('Person Deleted Successfully.', 'Success Alert', {timeOut: 5000});
        getPageData();
    });


});


/* Edit Item */
$("body").on("click",".edit-item",function(){


    var id = $(this).parent("td").data('id');	
              var  pid= $(this).parent("td").prev("td").prev("td").prev("td").prev("td").prev("td").prev("td").text();
       var  first_name= $(this).parent("td").prev("td").prev("td").prev("td").prev("td").prev("td").text();
	    var last_name= $(this).parent("td").prev("td").prev("td").prev("td").prev("td").text();
        var       age= $(this).parent("td").prev("td").prev("td").prev("td").text();
var favourite_colour = $(this).parent("td").prev("td").prev("td").text();
            var hobby= $(this).parent("td").prev("td").text();

	
     $("#edit-item").find("input[name='pid']").val(pid);
    $("#edit-item").find("input[name='first_name']").val(first_name);
    $("#edit-item").find("input[name='last_name']").val(last_name);
    $("#edit-item").find("input[name='age']").val(age);
    $("#edit-item").find("textarea[name='hobby']").val(hobby);
	$("#edit-item").find("input[name='favourite_colour']").val(favourite_colour);
	$("#edit-item").find(".edit-id").val(id);
	
    

});


/* Updated new Item */
$(".crud-submit-edit").click(function(e){


    e.preventDefault();
    var form_action = $("#edit-item").find("form").attr("action");
    var pid = $("#edit-item").find("input[name='pid']").val();
	var first_name = $("#edit-item").find("input[name='first_name']").val();
	var last_name = $("#edit-item").find("input[name='last_name']").val();
	var age = $("#edit-item").find("input[name='age']").val();
	var hobby = $("#edit-item").find("textarea[name='hobby']").val();
	var favourite_colour = $("#edit-item").find("input[name='favourite_colour']").val();
hobby=[hobby];
   	console.log(hobby);
    var id = $("#edit-item").find(".edit-id").val();


    if(first_name != '' && last_name != ''){
        $.ajax({
            dataType: 'json',
            type:'PUT',
			contentType: "application/json",
            url: url + form_action+'/'+pid,
            data:JSON.stringify({'id':pid,'first_name':first_name,'last_name':last_name,'age':age,'hobby':hobby,'favourite_colour':favourite_colour})
        }).done(function(data){
			console.log(data);
            getPageData();
            $(".modal").modal('hide');
            toastr.success('Person Data Updated Successfully.', 'Success Alert', {timeOut: 5000});
        });
    }else{
        alert('You are missing FirstName or LastName.')
    }


});
});

function toasterMsg(vid) {
  var x = document.getElementById(id);
  x.className = "show";
  setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
}

/* Sort table columns */
function sortTable(n) {
  var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
  table = document.getElementById("myTable");
  switching = true;
  //Set the sorting direction to ascending:
  dir = "asc"; 
  /*Make a loop that will continue until
  no switching has been done:*/
  while (switching) {
    //start by saying: no switching is done:
    switching = false;
    rows = table.rows;
    /*Loop through all table rows (except the
    first, which contains table headers):*/
    for (i = 1; i < (rows.length - 1); i++) {
      //start by saying there should be no switching:
      shouldSwitch = false;
      /*Get the two elements you want to compare,
      one from current row and one from the next:*/
      x = rows[i].getElementsByTagName("TD")[n];
      y = rows[i + 1].getElementsByTagName("TD")[n];
      /*check if the two rows should switch place,
      based on the direction, asc or desc:*/
      if (dir == "asc") {
        if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
          //if so, mark as a switch and break the loop:
          shouldSwitch= true;
          break;
        }
      } else if (dir == "desc") {
        if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
          //if so, mark as a switch and break the loop:
          shouldSwitch = true;
          break;
        }
      }
    }
    if (shouldSwitch) {
      /*If a switch has been marked, make the switch
      and mark that a switch has been done:*/
      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
      switching = true;
      //Each time a switch is done, increase this count by 1:
      switchcount ++;      
    } else {
      /*If no switching has been done AND the direction is "asc",
      set the direction to "desc" and run the while loop again.*/
      if (switchcount == 0 && dir == "asc") {
        dir = "desc";
        switching = true;
      }
    }
  }
}


