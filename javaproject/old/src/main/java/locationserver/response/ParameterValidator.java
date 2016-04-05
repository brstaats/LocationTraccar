package locationserver.response;

import locationserver.license.LicenseChecker;
import org.json.JSONObject;

import java.util.*;
import java.util.Map.*;

/**
 * Created by biezenj on 15-12-2015.
 */
public class ParameterValidator {

    JSONObject jsonMessage;
    HashMap<String, String[]> parameterMap;
    HashMap<String, String> validators;



    public ParameterValidator(Map<String, String[]> parameterMap, HashMap<String, String> validators) {
        this.parameterMap = (HashMap<String, String[]>) parameterMap;
        this.validators = validators;
    }

    public boolean checkIfParameterMapHasValidParameters() {

        //first compare the size of provided and expected arguments
        if (parameterMap.size() < validators.size()) {
            jsonMessage = new JSONMessage(Response.MISSING_ARGUMENTS).getJSONMessage();
            return false;
        }

        for (Map.Entry<String, String[]> param : parameterMap.entrySet()) {

            //check if provided arguments have null values
            if (parametersHaveNullValues(param)) {
                jsonMessage = new JSONMessage(Response.NULL_VALUES_PROVIDED).getJSONMessage();
                return false;
            }

            //check if some required arguments are missing
            if (parametersHaveExtraArguments(param)) {
                jsonMessage = new JSONMessage(Response.EXTRA_ARGUMENTS_PROVIDED).getJSONMessage();
                return false;
            }


            if (!mapValueToKeys(param)) {
                return false;
            }


        }


        //if licensecode is provided check it
        if (!checkIfLicenseIsValid()) {
            jsonMessage = new JSONMessage(Response.LICENSE_DOES_NOT_EXIST).getJSONMessage();
            return false;
        }

        //if pincode is provided if it is correct
        if (!checkIfPinCodeIsCorrect()) {
            jsonMessage = new JSONMessage(Response.PINCODE_INCORRECT).getJSONMessage();
            return false;
        }


        return true;
    }


    private boolean mapValueToKeys(Entry<String, String[]> param) {
        if (!validators.containsKey(param.getKey())) {
            jsonMessage = new JSONMessage(Response.ARGUMENT_DOES_NOT_EXIST).getJSONMessage();
            return false;
        }

        if (validators.get(param.getKey()) != null) {
            jsonMessage = new JSONMessage(Response.DUPLICATE_VALUES).getJSONMessage();
            return false;
        }

        validators.remove(param.getKey());
        validators.put(param.getKey(), param.getValue()[0]);

        return true;
    }

    private boolean checkIfLicenseIsValid() {
        if (validators.get("licenseCode") != null) {
            if (!LicenseChecker.isValidLicense(validators.get("licenseCode"))) {
                return false;
            }
        }

        return true;
    }

    private boolean checkIfPinCodeIsCorrect() {
        if (validators.get("pincode") != null) {
            if (!LicenseChecker.isValidPincode(validators.get("pincode"))) {
                return false;
            }
        }

        return true;
    }

    //if a key or value is null --> return
    private boolean parametersHaveNullValues(Entry<String, String[]> param) {
        if (param.getValue() == null || param.getKey() == null) {
            return true;
        }
        return false;
    }

    //if the a value is not present --> return , also get license as we loop through all the values. No need to repeat later
    private boolean parametersHaveExtraArguments(Entry<String, String[]> param) {

        if (!validators.containsKey(param.getKey())) {
            return true;
        }
        return false;
    }

    public JSONObject getJSONMessage() {
        return jsonMessage;
    }

}
