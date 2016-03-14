function startTracking(id) {

    clearList();

    var elem = document.createElement("a");
    elem.setAttribute("href", "#");
    elem.setAttribute("class", "stylish");
    elem.setAttribute("onclick", "start('"+id+"')");

    var t = document.createTextNode("Track");
    elem.appendChild(t);
    
    
    var center = document.createElement("center");
    center.appendChild(elem);
     document.getElementById("list").appendChild(center);
  

}

function showMap(coords){
    
    
     window.open("https://www.google.nl/maps/@"+coords+",14z?");
    
    
}

function start(id){}