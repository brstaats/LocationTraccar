package locationserver.license;

import locationserver.database.Connector;


import javax.servlet.http.HttpServlet;

/**
 * Created by biezenj on 7-12-2015.
 */
public class LicenseChecker{

    public static boolean isValidLicense(String number) {
        return new Connector().getLicense(number);
    }

    public static boolean isValidPincode(String pin){
        if(pin.length() == 0){
            return false;
        }
        return new Connector().getPin(pin);
    }

}
