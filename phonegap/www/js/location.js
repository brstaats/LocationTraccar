var id = parseInt(Math.random() *1000000000)   //needs to be a random uuid
console.log(id);


function position() {
        navigator.geolocation.getCurrentPosition(onSuccess, onError, {enableHighAccuracy:false});
};
var datetime = 0
var deviceId = 0
function onSuccess(position) {
        var latitude = position.coords.latitude
        var longitude = position.coords.longitude
        var altitude = position.coords.altitude
        var accuracy = position.coords.accuracy
        var altitudeAccuracy = position.coords.altitudeAccuracy
        var heading = position.coords.heading
        var speed = position.coords.speed
        var timestamp = position.timestamp
        var latlon = String(latitude)+" "+String(longitude)
        alert(latlon);
        var d = new Date()
        datetime = String(d.getFullYear()) + '-' +String(d.getMonth()+1) + '-' + String(d.getDate()) + '_' + String(d.getHours()) + ':' + String(d.getMinutes()) + ':' + String(d.getSeconds());
        var interval = 5000;

        id += 1                     // only for the id mag weg later
        if (id === 155) {
            timeInterval = 10000
        };
        var deviceId = 10   //uuid device

        insertLocation(id,deviceId,datetime,longitude,latitude,altitude,accuracy,speed, altitudeAccuracy, timeInterval);
        console.log(latitude);
        calcInterval(longitude,latitude,altitude);
        setTimeout(function(){myTimeFunction()}, 500);


};

function onError(error) {
        var errorCode = error.code;
        var errorMessage = error.message;
};

function insertLocation(id,deviceID,datetime,longitude,latitude,altitude,accuracy,speed, altitudeAccuracy, interval) {
    var url = ip + "/InsertLocation?id="+id+"&devid="+deviceID+"&timestamp="+datetime+"&lng="+longitude+"&lat=" + latitude + "&alt=" + 13.2 + "&acc=" + 100.1 + "&accalt=" + 100.3 +"&speed="+15.5+"&interval="+interval;
    //console.log(url)
    $.get(url, function (data) {

        data = JSON.parse(JSON.stringify(data));
        console.log("ResponseMessage: " + data.responseMessage + "<br>ResponseCode: " + data.responseCode);

        if (data.responseCode == "200") {
            console.log("succesfull upload");
        } else {
        //console.log("I failed")
        };
    });
}