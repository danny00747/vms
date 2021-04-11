package be.rentvehicle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main class, used to run the application.
 */
@SpringBootApplication
@EnableScheduling
public class VmsApplication {

    /**
     * Main method, used to run the application.
     * @param args the command line arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(VmsApplication.class, args);
    }

}
