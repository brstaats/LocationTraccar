package locationserver.model;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by biezenj on 5-4-2016.
 */

@Entity
@Table(name = "devices")
public class Device implements Serializable {

    @Id
    @GeneratedValue
    int id;
    int positionId;
    String name;
    String uniqueId;
    String status;
    Timestamp lastUpdate;

    protected Device() {
    }

    public Device(int positionId,String name, String uniqueId,String status, Timestamp lastUpdate) {
        this.positionId = positionId;
        this.name = name;
        this.uniqueId = uniqueId;
        this.status = status;
        this.lastUpdate = lastUpdate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
