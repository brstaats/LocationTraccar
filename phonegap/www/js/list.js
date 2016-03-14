function createList(data, name, track) {

    var container = document.getElementById("login");

    // Clear previous contents of the container
    while (container.hasChildNodes()) {
        container.removeChild(container.lastChild);
    }

    setListName(name);

    for (var i = 0; i < data.jobs.length; i++) {
        createJobListElement(data.jobs[i], track, data.tracker_uuid);
    }
}



function clearList() {
    var container = document.getElementById("list");

    // Clear previous contents of the container
    while (container.hasChildNodes()) {
        container.removeChild(container.lastChild);

    }
    container.innerHTML = "";
}



function setListName(name) {

    document.getElementById("listname").innerHTML = name;

}


function createJobListElement(data, track, uuid) {

    var list = document.getElementById("list");

    var ul = document.createElement("ul");
    ul.setAttribute("class", "list");
    list.appendChild(ul);

    var li = document.createElement("li");
    li.setAttribute("class", "list");
    if(track == "track"){
         li.setAttribute("onclick", "startTracking('" + uuid + "')");
    } else{
         li.setAttribute("onclick", "showTrackers('" + data.job_uuid + "','" + data.name + "','" + track + "')");
    }
    ul.appendChild(li);

    var h3 = document.createElement("h3");
    h3.setAttribute("class", "list");
    var t = document.createTextNode(data.name);
    h3.appendChild(t);
    li.appendChild(h3);

    var p = document.createElement("p");
    p.setAttribute("class", "list");
    var t = document.createTextNode("Startdate: " + data.startdate + " - Endddate: " + data.enddate);
    p.appendChild(t);
    li.appendChild(p);

}

function createTrackerListElement(data) {

    var list = document.getElementById("list");

    var ul = document.createElement("ul");
    ul.setAttribute("class", "list");
    list.appendChild(ul);

    var li = document.createElement("li");
    li.setAttribute("class", "list");
    li.setAttribute("onclick", "showMap('" + data.location.lat + ","+data.location.lng+"')");
    
    ul.appendChild(li);

    var h3 = document.createElement("h3");
    h3.setAttribute("class", "list");
    var t = document.createTextNode(data.name);
    h3.appendChild(t);
    li.appendChild(h3);

    var p = document.createElement("p");
    p.setAttribute("class", "list");
    var t = document.createTextNode("Active: " + data.active + " - Location: " + data.location.lat + ", " + data.location.lng + ", " + data.location.alt);
    p.appendChild(t);
    li.appendChild(p);

}