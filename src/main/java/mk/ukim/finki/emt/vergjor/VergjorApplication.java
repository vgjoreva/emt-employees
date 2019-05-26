package mk.ukim.finki.emt.vergjor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VergjorApplication {

    public static void main(String[] args) {
        SpringApplication.run(VergjorApplication.class, args);
    }

}
