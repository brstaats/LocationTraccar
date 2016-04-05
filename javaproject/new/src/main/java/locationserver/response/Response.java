package locationserver.response;

/**
 * Created by biezenj on 15-12-2015.
 */
public enum Response {

    NO_ADMIN_RIGHTS("Provided user has no admin rights", 400),
    WRONG_AUTHENTICATION("Wrong authentication number", 400),
    ARGUMENT_DOES_NOT_EXIST("Provided argument does not exist", 400),
    MAX_TRACKERS_EXCEEDED("Max trackers for license exceeded", 300),
    SQL_EXCEPTION("SQL exception", 400),
    NO_ROWS_UPDATED("No rows updated", 400),
    EXTRA_ARGUMENTS_PROVIDED("Arguments are missing", 400),
    DUPLICATE_VALUES("Duplicate values provided", 400),
    NULL_VALUES_PROVIDED("Empty values provided", 400),
    MISSING_ARGUMENTS("Required arguments are missing", 400),
    LICENSE_DOES_NOT_EXIST("License does not exist", 401),
    PINCODE_INCORRECT("Pincode is not valid", 401),
    JSON_ERROR("Error occured while creating JSON message", 400),
    SUCCES("Succes", 200);

    private final String message;
    private final int code;

    Response(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage(){
        return message;
    }

    public int getCode(){
        return code;
    }


}