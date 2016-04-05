package locationserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Jim on 15-3-2016.
 */
@EnableAutoConfiguration
@SpringBootApplication
public class LocationTrackerApp {


    public static void main(String[] args) {
        SpringApplication.run(LocationTrackerApp.class);
    }
}
