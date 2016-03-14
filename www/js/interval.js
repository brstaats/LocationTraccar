// interval
var getJobs = getJobsFunc()
var timeInterval = 3000  // startwaarde moet vervangen worden voor server logic

function createJobObject (lat, lon, height, innerregio, outerregion){
    this.lat = lat
    this.lon = lon
    this.height = height
    this.innerregion = innerregio
    this.outerregion = outerregion
};


var timeGeoloc      //timer geoposition needed to stop the timer

 function myTimeFunction() {
     console.log("starting again" + timeInterval)
     timeGeoloc = setTimeout(position, timeInterval);
 }

 function myStopFunction() {
     clearTimeout(timeGeoloc);
     console.log("Timing has stopped");
 }

// jobs
var jobsmap = new Map(); //Catched values of the requeust
var jobsmapkeys = []

function getJobsFunc() {
    var url = ip+"/GetJobs?tracker_uuid=" + 10 + "&client_uuid=" + 1;
    $.get(url, function (data) {

        data = JSON.parse(JSON.stringify(data));

        //console.log("ResponseMessage: " + data.responseMessage + "<br>ResponseCode: " + data.responseCode);
        if (data.responseCode == "200") {
            // available data:enddate, innerregion_meter, outerregion_meter, priority, interval.uuid
            if (data.jobs.length == 0) {
                console.log("there are no jobs for you");
                jobsmapkeys = []
                return;
            } else {
                for (value in data.jobs){
                    var str = (data.jobs[value].geomtask).split(" ");       //calculate lat,lon,height from string "Point Z (2.44 4.323 5)"
                    var lon = parseFloat((str[2]).substring(1));
                    var lat = parseFloat(str[3]);
                    //console.log("string length" + str.length);
                    //console.log(str)
                    var lengthz = (str[4]).length;
                    var height = parseFloat((str[4]).substring(0, (lengthz - 1)));

                    var y = data.jobs[value]

                    var datalist = [y.interval.uuid, y.enddate, parseFloat(y.innerregion_meter), parseFloat(y.outerregion_meter), lon, lat,height]
                    jobsmap.set(parseInt(data.jobs[value].priority), datalist);     //add a key value pair
                    jobsmapkeys.push(parseInt(data.jobs[value].priority));          //add the key values to a list

                };
                var highestpriority = Math.min.apply(Math,jobsmapkeys);         //gets the highest priority job
                var x = jobsmap.get(highestpriority)
                var positionjob = new createJobObject(x[4], x[5],x[6],x[2],x[3]);
                //console.log("jobs string")
                //console.log(positionjob);
            }
        }else{
            coneole.log("it has not worked..." )
        }
    });
 }
 console.log(getJobs)

 function calcInterval (longitude,latitude,altitude){
    if (jobsmapkeys.length == 0) {
        timeInterval = 5000
    } else {
        timeInterval = 6000
    }

 }