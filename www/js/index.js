var ip = "http://158.234.169.77:8080";

function showTrackers(uuid,name) {

    var url = ip+"/GetTrackers?job_uuid=" + uuid + "&client_uuid=";

    $.get(url, function (data) {
      
        data = JSON.parse(JSON.stringify(data));

        console.log("ResponseMessage: " + data.responseMessage + "<br>ResponseCode: " + data.responseCode);
        if (data.responseCode == "200") {

            if (data.trackers.length == 0) {
                 alert("No trackers for this job");
            } else {
                clearList();
                for (var i = 0; i < data.trackers.length; i++) {


                    setListName(document.getElementById("listname").innerHTML+ " <font color='#701112'>>></font> "+name);


                    for (var i = 0; i < data.trackers.length; i++) {
                        createTrackerListElement(data.trackers[i]);
                    }


                //    container.innerHTML = container.innerHTML + "<br><center><div class='DivResult' id='" + data.trackers[i].uuid + "'>Trackername: " + data.trackers[i].name + "<br>Uuid: " + data.trackers[i].uuid + "<br>Active: " + data.trackers[i].active + "<br>Location: " + data.trackers[i].location.lat + ", " + data.trackers[i].location.lng + ", " + data.trackers[i].location.alt + "<br>Accuracy: " + data.trackers[i].location.acc + "<br>Alt. Accuracy: " + data.trackers[i].location.accheight + "<br><a href=# onclick=editTracker('" + JSON.stringify(data.trackers[i]) + "')>Edit</a> || <a onclick=deleteTracker('" + uuid + "','" + data.trackers[i].uuid + "','" + del + "') href='#/'>" + delType + "</a>";
                }
            }
        }

    });
}



function test() {
    alert("test");
}