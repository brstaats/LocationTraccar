var timeInterval = 3000  // starting value - is replaced later by the server logic

var timeGeoloc      //var is needed to stop the timer geoposition

function myTimeFunction() {
    console.log("starting again" + timeInterval)
    timeGeoloc = setTimeout(position, timeInterval);
};

function myStopFunction() {
    clearTimeout(timeGeoloc);
    console.log("Timing has stopped");
};

function toRad(Value) {
    /** Converts numeric degrees to radians */
    return Value * Math.PI / 180;
};

function calcInterval (longitude1,latitude1,altitude1,speed){
    var R = 6371; // km      positionjob.lat
    var φ1 = toRad(latitude1);
    var φ2 = toRad((positionjob.lat));
    var Δφ = toRad(((positionjob.lat)-latitude1));
    var Δλ = toRad(((positionjob.lon)-longitude1));

    var a = Math.sin(Δφ/2) * Math.sin(Δφ/2) +
        Math.cos(φ1) * Math.cos(φ2) *
        Math.sin(Δλ/2) * Math.sin(Δλ/2);
    var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

    var distance = R * c;
    distance = distance * 1.25;                                         // afstand + 25 % see distance check

    if (distance < positionjob.innerregion) {
        timeInterval = 2000;            //add0

    } else if (distance < positionjob.outerregion) {                    // 2 minuten moet 5 worden
        if ((distance - (speed * 120)) < positionjob.innerregion) {     // Toevoegen verhouding afgelegde weg en de straal van de inner cirkel.
            timeInterval = 6000;        //add0
        } else if ((distance - (speed * 120)) > positionjob.innerregion) {
            timeInterval = 12000;       //add0
        } else {
            console.log("komt ie toch niet... maar voor later")
            timeInterval = 22000;       //add0
        }
    } else {
        if ((distance - (speed * 240)) < positionjob.outerregion) {     // Toevoegen verhouding afgelegde weg en de straal van de outer cirkel.
            timeInterval = 12000;       //add0
        } else if ((distance - (speed * 240)) > positionjob.outerregion) {
            timeInterval = 24000;       //add0
        } else {
            console.log("komt ie toch niet... maar voor later");
            timeInterval = 44000;       //add0
        }
    }
};