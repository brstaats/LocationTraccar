package locationserver.database;

import locationserver.database.repositories.DeviceRepository;
import locationserver.model.Device;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Jim on 15-3-2016.
 */

@Component
public class DatabaseConnection {

    public DatabaseConnection() {}

    public Device getDeviceById(DeviceRepository repository, int id){
        return repository.findOne(id);
    }

    public List<Device> getDeviceByName(DeviceRepository repository, String name){
        return repository.findByName(name);
    }

    public Iterable<Device> getAllDevices(DeviceRepository repository){
        return repository.findAll();
    }

    public void deleteDevice(DeviceRepository repository, int id){
        repository.delete(id);
    }

    public Device updateDevice(DeviceRepository repository, Device device){
        return repository.save(device);
    }

    public Device addDevice(DeviceRepository repository, Device device){
        return repository.save(device);
    }

}
