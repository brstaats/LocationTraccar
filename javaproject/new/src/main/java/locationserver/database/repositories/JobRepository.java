package locationserver.database.repositories;

import locationserver.model.Job;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

/**
 * Created by biezenj on 11-4-2016.
 */
public interface JobRepository extends CrudRepository<Job, Integer> {

    List<Job> findByName(String name);

    Job findByUuid(UUID uuid);
}
