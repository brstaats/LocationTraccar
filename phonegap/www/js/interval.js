// interval

var timeInterval = 3000  // startwaarde moet vervangen worden voor server logic

var timeGeoloc      //timer geoposition needed to stop the timer

function myTimeFunction() {
    console.log("starting again" + timeInterval)
    timeGeoloc = setTimeout(position, timeInterval);
}

function myStopFunction() {
    clearTimeout(timeGeoloc);
    console.log("Timing has stopped");
}

function toRad(Value) {
    /** Converts numeric degrees to radians */
    return Value * Math.PI / 180;
}

function calcInterval (longitude1,latitude1,altitude1){
   //console.log("this should be the jobs " + jobsmap[10]);     positionjob.lat
   var R = 6371000; // metres
   var φ1 = toRad(latitude1);
   var φ2 = toRad((positionjob.lat));
   var Δφ = toRad(((positionjob.lat)-latitude1));
   var Δλ = toRad(((positionjob.lon)-longitude1));

   var a = Math.sin(Δφ/2) * Math.sin(Δφ/2) +
             Math.cos(φ1) * Math.cos(φ2) *
             Math.sin(Δλ/2) * Math.sin(Δλ/2);
   var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

   var d = R * c;
   console.log("distance = " + d)

   if (jobsmapkeys.length == 0) {
       timeInterval = 5000
   } else {
       timeInterval = 6000
   }

}