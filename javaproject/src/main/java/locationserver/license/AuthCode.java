package locationserver.license;

import java.util.Random;

/**
 * Created by biezenj on 18-12-2015.
 */
public class AuthCode {

    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static final String NUM = "0123456789";

    public static String getAuthCode() {
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < 8; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

    public static String getPinCode() {
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(3);
        for (int i = 0; i < 4; i++)
            sb.append(NUM.charAt(rnd.nextInt(NUM.length())));
        return sb.toString();
    }


}
