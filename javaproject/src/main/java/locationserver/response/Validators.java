package locationserver.response;

import java.util.HashMap;

/**
 * Created by biezenj on 16-12-2015.
 */
public class Validators {

    HashMap<String, String> params = new HashMap<>();


    public Validators(String... args)
    {

        for(int i = 0; i< args.length; i++){
            params.put(args[i], null);
        }

    }

    public HashMap<String, String> getParams(){
        return params;
    }

}
