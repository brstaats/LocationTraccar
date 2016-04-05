import locationserver.database.DatabaseConnection;
import locationserver.model.Device;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;

/**
 * Created by biezenj on 15-12-2015.
 */
public class DatabaseConnectorTest {

   @Test
    public void checkContentDevices(){
       List<Device> devices = new DatabaseConnection().getDevices();
       assertFalse(devices.size() == 0);
   }

}
