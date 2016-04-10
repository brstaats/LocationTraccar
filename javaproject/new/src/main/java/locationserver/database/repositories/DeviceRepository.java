package locationserver.database.repositories;

import locationserver.model.Device;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by biezenj on 10-4-2016.
 */

@Service
public interface DeviceRepository extends CrudRepository<Device, Integer> {

    List<Device> findByName(String name);
}
