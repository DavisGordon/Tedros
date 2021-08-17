/* Toggle between adding and removing the "responsive" class to topnav when the user clicks on the icon */
function shm() {
  var x = document.getElementById("tMenu");
  if (x.className === "topnav") {
    x.className += " responsive";
  } else {
    x.className = "topnav";
  }
}

function hm(){
	var x = document.getElementById("tMenu");
	if (!(x.className === "topnav")) 
		x.className = "topnav";
}