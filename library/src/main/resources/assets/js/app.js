$(document).ready(function(){
    var elements = $('td[title="status"]');
    for( i=0; i<elements.length; i++ ){
    	var statusIs = elements[i].innerHTML;
    	var onlyNumberId = elements[i].id.slice("6");
    	var realId = "#"+onlyNumberId;
    	if(statusIs == "lost")
    		{
    			$(realId).attr("disabled","disabled");
    		}
    	else{
    		$(realId).removeAttr("disabled");
    	}
    }
    
    var url="ws://54.215.210.214:61623"; 
    var login = "admin"; 
    var password = "password"; 
    var destination = "/topic/69169.book.*"; 
    var client = Stomp.client(url); 
    client.debug = function(str) { 
    	$("#debug").append(str + "\n"); 
    	}; 
    	client.connect(login,password, function(frame){  
    		client.debug("connected to Stomp"); 
    		client.subscribe(destination, function(message){ 
    			
    			window.location.reload(); 
    			}); 
    		});
          });
          

$(":button").click(function() {
	var isbn = this.id;
	var uri = "/library/v1/books/"+isbn+"?status=lost";
	var btnDisable = "#"+isbn;
	var statusS = "#status"+isbn;
	alert('About to report lost on ISBN ' + isbn);
	
	$.ajax({
		  url: uri,
		  type: 'PUT',
		  success: function(data) {
			  $(btnDisable).attr("disabled","disabled");
			    $(statusS).text("lost");
		  }
		});
});