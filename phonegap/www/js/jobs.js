var getJobs = getJobsFunc()         // automatic start at the beginning of the app

function createJobObject (lat, lon, height, innerregio, outerregion){
    this.lat = lat
    this.lon = lon
    this.height = height
    this.innerregion = innerregio
    this.outerregion = outerregion
};

var jobsmap = new Map(); //Catched values of the requeust
var jobsmapkeys = []
var positionjob = 0



function getJobsFunc() {
    var url = ip+"/GetJobs?tracker_uuid=" + 10 + "&client_uuid=" + 1;
    $.get(url, function (data) {

        data = JSON.parse(JSON.stringify(data));

        //console.log("ResponseMessage: " + data.responseMessage + "<br>ResponseCode: " + data.responseCode);
        if (data.responseCode == "200") {
            // available data:enddate, innerregion_meter, outerregion_meter, priority, interval.uuid
            console.log(data);
            if (data.jobs.length == 0) {
                console.log("there is no job for you");
                jobsmapkeys = []
                return;
            } else {
                jobsmapkeys = []
                for (value in data.jobs){
                    var y = data.jobs[value]        //making of a map v
                    var datalist = [y.interval.uuid, y.enddate, parseFloat(y.innerregion_meter), parseFloat(y.outerregion_meter), parseFloat(y.longitude), parseFloat(y.latitude), parseFloat(y.altitude), y.name, y.finished]
                    jobsmap.set(parseInt(data.jobs[value].priority), datalist);     //add a key value pair
                    jobsmapkeys.push(parseInt(data.jobs[value].priority));          //add the key values to a list
                };
                mostimportantjob();

            }
        }else{
            console.log("it has not worked..." )
        }
    console.log(positionjob)
    //calcInterval(4.6584330, 52.1340470, 5, 10);

    });
 };

function mostimportantjob() {
    var highestpriority = Math.min.apply(Math,jobsmapkeys);         // Calculate and copy gets the highest priority job
    var x = jobsmap.get(highestpriority)
    positionjob = new createJobObject(x[4], x[5],x[6],x[2],x[3]);
    console.log("jobs string")
    console.log(positionjob.lat);
    console.log(jobsmapkeys);
};