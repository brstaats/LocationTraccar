package locationserver.response;

import locationserver.database.Connector;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by biezenj on 7-12-2015.
 */
public class JSONResponse {


    public static JSONObject getSetStateReply(Map<String, String[]> parameterMap) {

        Validators validators = new Validators("licenseCode", "userId", "active");
        ParameterValidator validator = new ParameterValidator(parameterMap, validators.getParams());

        return (validator.checkIfParameterMapHasValidParameters()) ? new Connector().setTrackerState(parameterMap.get("userId")[0], parameterMap.get("active")[0]) : validator.getJSONMessage();
    }

    public static JSONObject getTrackers(Map<String, String[]> parameterMap) {

        Validators validators = new Validators("job_uuid", "client_uuid");

        ParameterValidator validator = new ParameterValidator(parameterMap, validators.getParams());

        return (validator.checkIfParameterMapHasValidParameters()) ? new Connector().getTrackers(validators.getParams()) : validator.getJSONMessage();
    }

    public static JSONObject getInterval(Map<String, String[]> parameterMap) {

        Validators validators = new Validators("deviceId", "timestamp");
        ParameterValidator validator = new ParameterValidator(parameterMap, validators.getParams());

        return (validator.checkIfParameterMapHasValidParameters()) ? new Connector().getInterval(validators.getParams()) : validator.getJSONMessage();
    }

    public static JSONObject createJob(Map<String, String[]> parameterMap) {

        Validators validators = new Validators("name", "client_uuid", "startdate", "enddate", "starttime", "endtime", "far", "close", "far_still", "close_still");
        ParameterValidator validator = new ParameterValidator(parameterMap, validators.getParams());

        return (validator.checkIfParameterMapHasValidParameters()) ? new Connector().createJob(validators.getParams()) : validator.getJSONMessage();
    }

    public static JSONObject createClient(Map<String, String[]> parameterMap) {

        Validators validators = new Validators("name", "max_trackers");
        ParameterValidator validator = new ParameterValidator(parameterMap, validators.getParams());

        return (validator.checkIfParameterMapHasValidParameters()) ? new Connector().createClient(validators.getParams()) : validator.getJSONMessage();
    }

    public static JSONObject setInterval(Map<String, String[]> parameterMap) {

        Validators validators = new Validators("uuid","job_uuid", "far", "close", "far_still", "close_still");
        ParameterValidator validator = new ParameterValidator(parameterMap, validators.getParams());

        return (validator.checkIfParameterMapHasValidParameters()) ? new Connector().setInterval(validators.getParams()) : validator.getJSONMessage();
    }

    public static JSONObject setTracker(Map<String, String[]> parameterMap) {

        Validators validators = new Validators("name","uuid", "active");
        ParameterValidator validator = new ParameterValidator(parameterMap, validators.getParams());

        return (validator.checkIfParameterMapHasValidParameters()) ? new Connector().setTracker(validators.getParams()) : validator.getJSONMessage();
    }

    public static JSONObject setJob(Map<String, String[]> parameterMap) {

        Validators validators = new Validators("name","uuid", "startdate", "enddate", "starttime", "endtime");
        ParameterValidator validator = new ParameterValidator(parameterMap, validators.getParams());

        return (validator.checkIfParameterMapHasValidParameters()) ? new Connector().setJob(validators.getParams()) : validator.getJSONMessage();
    }

    public static JSONObject setLocation(Map<String, String[]> parameterMap) {

        Validators validators = new Validators("uuid","lng", "lat", "alt", "acc", "accalt");
        ParameterValidator validator = new ParameterValidator(parameterMap, validators.getParams());

        return (validator.checkIfParameterMapHasValidParameters()) ? new Connector().setLocation(validators.getParams()) : validator.getJSONMessage();
    }

    public static JSONObject insertLocation(Map<String, String[]> parameterMap) {

        Validators validators = new Validators("id","devid","timestamp","lng","lat","alt","acc","accalt","speed","interval");
        ParameterValidator validator = new ParameterValidator(parameterMap, validators.getParams());

        return (validator.checkIfParameterMapHasValidParameters()) ? new Connector().insertLocation(validators.getParams()) : validator.getJSONMessage();
    }

    public static JSONObject createTracker(Map<String, String[]> parameterMap) {

        Validators validators = new Validators("name","auth_code");
        ParameterValidator validator = new ParameterValidator(parameterMap, validators.getParams());

        return (validator.checkIfParameterMapHasValidParameters()) ? new Connector().createTracker(validators.getParams()) : validator.getJSONMessage();
    }

    public static JSONObject getJobs(Map<String, String[]> parameterMap) {

        Validators validators = new Validators("client_uuid", "tracker_uuid");
        ParameterValidator validator = new ParameterValidator(parameterMap, validators.getParams());

        return (validator.checkIfParameterMapHasValidParameters()) ? new Connector().getJobs(validators.getParams()) : validator.getJSONMessage();
    }

    public static JSONObject getAdminLogin(Map<String, String[]> parameterMap) throws JSONException {

        Validators validators = new Validators("auth_code", "pincode");
        ParameterValidator validator = new ParameterValidator(parameterMap, validators.getParams());

        if (validator.checkIfParameterMapHasValidParameters()) {
            ArrayList<String> client = new Connector().getClient(validators.getParams());
            if (client != null) {
                HashMap<String, String> map = new HashMap<>();
                map.put("client_uuid", client.get(0));
                return new Connector().getJobs(map).put("clientName", client.get(1));
            } else {
                return new JSONMessage(Response.SQL_EXCEPTION).getJSONMessage();
            }
        }
        return validator.getJSONMessage();
    }

    public static JSONObject getUserLogin(Map<String, String[]> parameterMap) throws JSONException {

        Validators validators = new Validators("name");
        ParameterValidator validator = new ParameterValidator(parameterMap, validators.getParams());

        if (validator.checkIfParameterMapHasValidParameters()) {
            ArrayList<String> tracker = new Connector().getTracker(validators.getParams());
            if (tracker != null) {
                HashMap<String, String> map = new HashMap<>();
                map.put("tracker_uuid", tracker.get(0));
                JSONObject returnMessage = new Connector().getJobs(map);
                returnMessage.put("trackerName", tracker.get(1));
                returnMessage.put("tracker_uuid", tracker.get(0));
                return returnMessage;
            } else {
                return new JSONMessage(Response.SQL_EXCEPTION).getJSONMessage();
            }
        }
        return validator.getJSONMessage();
    }

    public static JSONObject deleteClient(Map<String, String[]> parameterMap) {

        Validators validators = new Validators("client_uuid");
        ParameterValidator validator = new ParameterValidator(parameterMap, validators.getParams());

        return (validator.checkIfParameterMapHasValidParameters()) ? new Connector().deleteClient(validators.getParams()) : validator.getJSONMessage();
    }

    public static JSONObject deleteJob(Map<String, String[]> parameterMap) {

        Validators validators = new Validators("job_uuid");
        ParameterValidator validator = new ParameterValidator(parameterMap, validators.getParams());

        return (validator.checkIfParameterMapHasValidParameters()) ? new Connector().deleteJob(validators.getParams()) : validator.getJSONMessage();
    }


    public static JSONObject deleteTracker(Map<String, String[]> parameterMap) {

        Validators validators = new Validators("job_uuid","tracker_uuid","delete");
        ParameterValidator validator = new ParameterValidator(parameterMap, validators.getParams());

        return (validator.checkIfParameterMapHasValidParameters()) ? new Connector().deleteTracker(validators.getParams()) : validator.getJSONMessage();
    }

    public static JSONObject createJobTrackerLink(Map<String, String[]> parameterMap) {

        Validators validators = new Validators("job_uuid", "tracker_uuid");
        ParameterValidator validator = new ParameterValidator(parameterMap, validators.getParams());

        return (validator.checkIfParameterMapHasValidParameters()) ? new Connector().createJobTrackerLink(validators.getParams()) : validator.getJSONMessage();
    }

    public static JSONObject setClient(Map<String, String[]> parameterMap) {

        return new Connector().setClient((HashMap<String, String[]>) parameterMap);
    }

    public static JSONObject getClients() {
        return (new Connector().getClients());
    }


}

